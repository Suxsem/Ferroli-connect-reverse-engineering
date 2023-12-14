package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.BufferedMessage;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class ClientComms {
    public static String BUILD_LEVEL = "L${build.level}";
    /* access modifiers changed from: private */
    public static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.ClientComms";
    private static final byte CLOSED = 4;
    private static final byte CONNECTED = 0;
    private static final byte CONNECTING = 1;
    private static final byte DISCONNECTED = 3;
    private static final byte DISCONNECTING = 2;
    public static String VERSION = "${project.version}";
    /* access modifiers changed from: private */
    public static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    /* access modifiers changed from: private */
    public CommsCallback callback;
    private IMqttAsyncClient client;
    /* access modifiers changed from: private */
    public ClientState clientState;
    private boolean closePending;
    private Object conLock;
    private MqttConnectOptions conOptions;
    private byte conState;
    private DisconnectedMessageBuffer disconnectedMessageBuffer;
    /* access modifiers changed from: private */
    public ExecutorService executorService;
    /* access modifiers changed from: private */
    public int networkModuleIndex;
    /* access modifiers changed from: private */
    public NetworkModule[] networkModules;
    private MqttClientPersistence persistence;
    private MqttPingSender pingSender;
    /* access modifiers changed from: private */
    public CommsReceiver receiver;
    private boolean resting;
    /* access modifiers changed from: private */
    public CommsSender sender;
    private boolean stoppingComms;
    /* access modifiers changed from: private */
    public CommsTokenStore tokenStore;

    public ClientComms(IMqttAsyncClient iMqttAsyncClient, MqttClientPersistence mqttClientPersistence, MqttPingSender mqttPingSender, ExecutorService executorService2) throws MqttException {
        this.stoppingComms = false;
        this.conState = 3;
        this.conLock = new Object();
        this.closePending = false;
        this.resting = false;
        this.conState = 3;
        this.client = iMqttAsyncClient;
        this.persistence = mqttClientPersistence;
        this.pingSender = mqttPingSender;
        this.pingSender.init(this);
        this.executorService = executorService2;
        this.tokenStore = new CommsTokenStore(getClient().getClientId());
        this.callback = new CommsCallback(this);
        this.clientState = new ClientState(mqttClientPersistence, this.tokenStore, this.callback, this, mqttPingSender);
        this.callback.setClientState(this.clientState);
        log.setResourceName(getClient().getClientId());
    }

    /* access modifiers changed from: package-private */
    public CommsReceiver getReceiver() {
        return this.receiver;
    }

    private void shutdownExecutorService() {
        this.executorService.shutdown();
        try {
            if (!this.executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                this.executorService.shutdownNow();
                if (!this.executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                    log.fine(CLASS_NAME, "shutdownExecutorService", "executorService did not terminate");
                }
            }
        } catch (InterruptedException unused) {
            this.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /* access modifiers changed from: package-private */
    public void internalSend(MqttWireMessage mqttWireMessage, MqttToken mqttToken) throws MqttException {
        log.fine(CLASS_NAME, "internalSend", "200", new Object[]{mqttWireMessage.getKey(), mqttWireMessage, mqttToken});
        if (mqttToken.getClient() == null) {
            mqttToken.internalTok.setClient(getClient());
            try {
                this.clientState.send(mqttWireMessage, mqttToken);
            } catch (MqttException e) {
                if (mqttWireMessage instanceof MqttPublish) {
                    this.clientState.undo((MqttPublish) mqttWireMessage);
                }
                throw e;
            }
        } else {
            log.fine(CLASS_NAME, "internalSend", "213", new Object[]{mqttWireMessage.getKey(), mqttWireMessage, mqttToken});
            throw new MqttException(32201);
        }
    }

    public void sendNoWait(MqttWireMessage mqttWireMessage, MqttToken mqttToken) throws MqttException {
        if (isConnected() || ((!isConnected() && (mqttWireMessage instanceof MqttConnect)) || (isDisconnecting() && (mqttWireMessage instanceof MqttDisconnect)))) {
            DisconnectedMessageBuffer disconnectedMessageBuffer2 = this.disconnectedMessageBuffer;
            if (disconnectedMessageBuffer2 == null || disconnectedMessageBuffer2.getMessageCount() == 0) {
                internalSend(mqttWireMessage, mqttToken);
                return;
            }
            log.fine(CLASS_NAME, "sendNoWait", "507", new Object[]{mqttWireMessage.getKey()});
            if (this.disconnectedMessageBuffer.isPersistBuffer()) {
                this.clientState.persistBufferedMessage(mqttWireMessage);
            }
            this.disconnectedMessageBuffer.putMessage(mqttWireMessage, mqttToken);
        } else if (this.disconnectedMessageBuffer != null) {
            log.fine(CLASS_NAME, "sendNoWait", "508", new Object[]{mqttWireMessage.getKey()});
            if (this.disconnectedMessageBuffer.isPersistBuffer()) {
                this.clientState.persistBufferedMessage(mqttWireMessage);
            }
            this.disconnectedMessageBuffer.putMessage(mqttWireMessage, mqttToken);
        } else {
            log.fine(CLASS_NAME, "sendNoWait", "208");
            throw ExceptionHelper.createMqttException(32104);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0062, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close(boolean r5) throws org.eclipse.paho.client.mqttv3.MqttException {
        /*
            r4 = this;
            java.lang.Object r0 = r4.conLock
            monitor-enter(r0)
            boolean r1 = r4.isClosed()     // Catch:{ all -> 0x0063 }
            if (r1 != 0) goto L_0x0061
            boolean r1 = r4.isDisconnected()     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x0011
            if (r5 == 0) goto L_0x0033
        L_0x0011:
            org.eclipse.paho.client.mqttv3.logging.Logger r5 = log     // Catch:{ all -> 0x0063 }
            java.lang.String r1 = CLASS_NAME     // Catch:{ all -> 0x0063 }
            java.lang.String r2 = "close"
            java.lang.String r3 = "224"
            r5.fine(r1, r2, r3)     // Catch:{ all -> 0x0063 }
            boolean r5 = r4.isConnecting()     // Catch:{ all -> 0x0063 }
            if (r5 != 0) goto L_0x0059
            boolean r5 = r4.isConnected()     // Catch:{ all -> 0x0063 }
            if (r5 != 0) goto L_0x0052
            boolean r5 = r4.isDisconnecting()     // Catch:{ all -> 0x0063 }
            if (r5 == 0) goto L_0x0033
            r5 = 1
            r4.closePending = r5     // Catch:{ all -> 0x0063 }
            monitor-exit(r0)     // Catch:{ all -> 0x0063 }
            return
        L_0x0033:
            r5 = 4
            r4.conState = r5     // Catch:{ all -> 0x0063 }
            r4.shutdownExecutorService()     // Catch:{ all -> 0x0063 }
            org.eclipse.paho.client.mqttv3.internal.ClientState r5 = r4.clientState     // Catch:{ all -> 0x0063 }
            r5.close()     // Catch:{ all -> 0x0063 }
            r5 = 0
            r4.clientState = r5     // Catch:{ all -> 0x0063 }
            r4.callback = r5     // Catch:{ all -> 0x0063 }
            r4.persistence = r5     // Catch:{ all -> 0x0063 }
            r4.sender = r5     // Catch:{ all -> 0x0063 }
            r4.pingSender = r5     // Catch:{ all -> 0x0063 }
            r4.receiver = r5     // Catch:{ all -> 0x0063 }
            r4.networkModules = r5     // Catch:{ all -> 0x0063 }
            r4.conOptions = r5     // Catch:{ all -> 0x0063 }
            r4.tokenStore = r5     // Catch:{ all -> 0x0063 }
            goto L_0x0061
        L_0x0052:
            r5 = 32100(0x7d64, float:4.4982E-41)
            org.eclipse.paho.client.mqttv3.MqttException r5 = org.eclipse.paho.client.mqttv3.internal.ExceptionHelper.createMqttException((int) r5)     // Catch:{ all -> 0x0063 }
            throw r5     // Catch:{ all -> 0x0063 }
        L_0x0059:
            org.eclipse.paho.client.mqttv3.MqttException r5 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x0063 }
            r1 = 32110(0x7d6e, float:4.4996E-41)
            r5.<init>((int) r1)     // Catch:{ all -> 0x0063 }
            throw r5     // Catch:{ all -> 0x0063 }
        L_0x0061:
            monitor-exit(r0)     // Catch:{ all -> 0x0063 }
            return
        L_0x0063:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0063 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientComms.close(boolean):void");
    }

    public void connect(MqttConnectOptions mqttConnectOptions, MqttToken mqttToken) throws MqttException {
        synchronized (this.conLock) {
            if (!isDisconnected() || this.closePending) {
                log.fine(CLASS_NAME, "connect", "207", new Object[]{new Byte(this.conState)});
                if (isClosed() || this.closePending) {
                    throw new MqttException(32111);
                } else if (isConnecting()) {
                    throw new MqttException(32110);
                } else if (isDisconnecting()) {
                    throw new MqttException(32102);
                } else {
                    throw ExceptionHelper.createMqttException(32100);
                }
            } else {
                log.fine(CLASS_NAME, "connect", "214");
                this.conState = 1;
                this.conOptions = mqttConnectOptions;
                MqttConnect mqttConnect = new MqttConnect(this.client.getClientId(), this.conOptions.getMqttVersion(), this.conOptions.isCleanSession(), this.conOptions.getKeepAliveInterval(), this.conOptions.getUserName(), this.conOptions.getPassword(), this.conOptions.getWillMessage(), this.conOptions.getWillDestination());
                this.clientState.setKeepAliveSecs((long) this.conOptions.getKeepAliveInterval());
                this.clientState.setCleanSession(this.conOptions.isCleanSession());
                this.clientState.setMaxInflight(this.conOptions.getMaxInflight());
                this.tokenStore.open();
                new ConnectBG(this, mqttToken, mqttConnect, this.executorService).start();
            }
        }
    }

    public void connectComplete(MqttConnack mqttConnack, MqttException mqttException) throws MqttException {
        int returnCode = mqttConnack.getReturnCode();
        synchronized (this.conLock) {
            if (returnCode == 0) {
                log.fine(CLASS_NAME, "connectComplete", "215");
                this.conState = 0;
                return;
            }
            log.fine(CLASS_NAME, "connectComplete", "204", new Object[]{new Integer(returnCode)});
            throw mqttException;
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processHandlersOutBlocks(RegionMaker.java:1008)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:978)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    /* JADX WARNING: Missing exception handler attribute for start block: B:79:0x00d7 */
    public void shutdownConnection(org.eclipse.paho.client.mqttv3.MqttToken r9, org.eclipse.paho.client.mqttv3.MqttException r10) {
        /*
            r8 = this;
            java.lang.Object r0 = r8.conLock
            monitor-enter(r0)
            boolean r1 = r8.stoppingComms     // Catch:{ all -> 0x00e1 }
            if (r1 != 0) goto L_0x00df
            boolean r1 = r8.closePending     // Catch:{ all -> 0x00e1 }
            if (r1 != 0) goto L_0x00df
            boolean r1 = r8.isClosed()     // Catch:{ all -> 0x00e1 }
            if (r1 == 0) goto L_0x0013
            goto L_0x00df
        L_0x0013:
            r1 = 1
            r8.stoppingComms = r1     // Catch:{ all -> 0x00e1 }
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = log     // Catch:{ all -> 0x00e1 }
            java.lang.String r3 = CLASS_NAME     // Catch:{ all -> 0x00e1 }
            java.lang.String r4 = "shutdownConnection"
            java.lang.String r5 = "216"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x00e1 }
            boolean r2 = r8.isConnected()     // Catch:{ all -> 0x00e1 }
            r3 = 0
            if (r2 != 0) goto L_0x0030
            boolean r2 = r8.isDisconnecting()     // Catch:{ all -> 0x00e1 }
            if (r2 != 0) goto L_0x0030
            r2 = 0
            goto L_0x0031
        L_0x0030:
            r2 = 1
        L_0x0031:
            r4 = 2
            r8.conState = r4     // Catch:{ all -> 0x00e1 }
            monitor-exit(r0)     // Catch:{ all -> 0x00e1 }
            if (r9 == 0) goto L_0x0042
            boolean r0 = r9.isComplete()
            if (r0 != 0) goto L_0x0042
            org.eclipse.paho.client.mqttv3.internal.Token r0 = r9.internalTok
            r0.setException(r10)
        L_0x0042:
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r0 = r8.callback
            if (r0 == 0) goto L_0x0049
            r0.stop()
        L_0x0049:
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver r0 = r8.receiver
            if (r0 == 0) goto L_0x0050
            r0.stop()
        L_0x0050:
            org.eclipse.paho.client.mqttv3.internal.NetworkModule[] r0 = r8.networkModules     // Catch:{ Exception -> 0x005f }
            if (r0 == 0) goto L_0x005f
            org.eclipse.paho.client.mqttv3.internal.NetworkModule[] r0 = r8.networkModules     // Catch:{ Exception -> 0x005f }
            int r4 = r8.networkModuleIndex     // Catch:{ Exception -> 0x005f }
            r0 = r0[r4]     // Catch:{ Exception -> 0x005f }
            if (r0 == 0) goto L_0x005f
            r0.stop()     // Catch:{ Exception -> 0x005f }
        L_0x005f:
            org.eclipse.paho.client.mqttv3.internal.CommsTokenStore r0 = r8.tokenStore
            org.eclipse.paho.client.mqttv3.MqttException r4 = new org.eclipse.paho.client.mqttv3.MqttException
            r5 = 32102(0x7d66, float:4.4984E-41)
            r4.<init>((int) r5)
            r0.quiesce(r4)
            org.eclipse.paho.client.mqttv3.MqttToken r9 = r8.handleOldTokens(r9, r10)
            org.eclipse.paho.client.mqttv3.internal.ClientState r0 = r8.clientState     // Catch:{ Exception -> 0x0082 }
            r0.disconnected(r10)     // Catch:{ Exception -> 0x0082 }
            org.eclipse.paho.client.mqttv3.internal.ClientState r0 = r8.clientState     // Catch:{ Exception -> 0x0082 }
            boolean r0 = r0.getCleanSession()     // Catch:{ Exception -> 0x0082 }
            if (r0 == 0) goto L_0x0083
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r0 = r8.callback     // Catch:{ Exception -> 0x0082 }
            r0.removeMessageListeners()     // Catch:{ Exception -> 0x0082 }
            goto L_0x0083
        L_0x0082:
        L_0x0083:
            org.eclipse.paho.client.mqttv3.internal.CommsSender r0 = r8.sender
            if (r0 == 0) goto L_0x008a
            r0.stop()
        L_0x008a:
            org.eclipse.paho.client.mqttv3.MqttPingSender r0 = r8.pingSender
            if (r0 == 0) goto L_0x0091
            r0.stop()
        L_0x0091:
            org.eclipse.paho.client.mqttv3.internal.DisconnectedMessageBuffer r0 = r8.disconnectedMessageBuffer     // Catch:{ Exception -> 0x009e }
            if (r0 != 0) goto L_0x009e
            org.eclipse.paho.client.mqttv3.MqttClientPersistence r0 = r8.persistence     // Catch:{ Exception -> 0x009e }
            if (r0 == 0) goto L_0x009e
            org.eclipse.paho.client.mqttv3.MqttClientPersistence r0 = r8.persistence     // Catch:{ Exception -> 0x009e }
            r0.close()     // Catch:{ Exception -> 0x009e }
        L_0x009e:
            java.lang.Object r4 = r8.conLock
            monitor-enter(r4)
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = log     // Catch:{ all -> 0x00dc }
            java.lang.String r5 = CLASS_NAME     // Catch:{ all -> 0x00dc }
            java.lang.String r6 = "shutdownConnection"
            java.lang.String r7 = "217"
            r0.fine(r5, r6, r7)     // Catch:{ all -> 0x00dc }
            r0 = 3
            r8.conState = r0     // Catch:{ all -> 0x00dc }
            r8.stoppingComms = r3     // Catch:{ all -> 0x00dc }
            monitor-exit(r4)     // Catch:{ all -> 0x00dc }
            if (r9 == 0) goto L_0x00b6
            r0 = 1
            goto L_0x00b7
        L_0x00b6:
            r0 = 0
        L_0x00b7:
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r4 = r8.callback
            if (r4 == 0) goto L_0x00bc
            r3 = 1
        L_0x00bc:
            r0 = r0 & r3
            if (r0 == 0) goto L_0x00c4
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r0 = r8.callback
            r0.asyncOperationComplete(r9)
        L_0x00c4:
            if (r2 == 0) goto L_0x00cd
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r9 = r8.callback
            if (r9 == 0) goto L_0x00cd
            r9.connectionLost(r10)
        L_0x00cd:
            java.lang.Object r9 = r8.conLock
            monitor-enter(r9)
            boolean r10 = r8.closePending     // Catch:{ all -> 0x00d9 }
            if (r10 == 0) goto L_0x00d7
            r8.close(r1)     // Catch:{ Exception -> 0x00d7 }
        L_0x00d7:
            monitor-exit(r9)     // Catch:{ all -> 0x00d9 }
            return
        L_0x00d9:
            r10 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00d9 }
            throw r10
        L_0x00dc:
            r9 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00dc }
            throw r9
        L_0x00df:
            monitor-exit(r0)     // Catch:{ all -> 0x00e1 }
            return
        L_0x00e1:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00e1 }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientComms.shutdownConnection(org.eclipse.paho.client.mqttv3.MqttToken, org.eclipse.paho.client.mqttv3.MqttException):void");
    }

    private MqttToken handleOldTokens(MqttToken mqttToken, MqttException mqttException) {
        log.fine(CLASS_NAME, "handleOldTokens", "222");
        MqttToken mqttToken2 = null;
        if (mqttToken != null) {
            try {
                if (this.tokenStore.getToken(mqttToken.internalTok.getKey()) == null) {
                    this.tokenStore.saveToken(mqttToken, mqttToken.internalTok.getKey());
                }
            } catch (Exception unused) {
            }
        }
        Enumeration elements = this.clientState.resolveOldTokens(mqttException).elements();
        while (elements.hasMoreElements()) {
            MqttToken mqttToken3 = (MqttToken) elements.nextElement();
            if (!mqttToken3.internalTok.getKey().equals(MqttDisconnect.KEY)) {
                if (!mqttToken3.internalTok.getKey().equals("Con")) {
                    this.callback.asyncOperationComplete(mqttToken3);
                }
            }
            mqttToken2 = mqttToken3;
        }
        return mqttToken2;
    }

    public void disconnect(MqttDisconnect mqttDisconnect, long j, MqttToken mqttToken) throws MqttException {
        synchronized (this.conLock) {
            if (isClosed()) {
                log.fine(CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "223");
                throw ExceptionHelper.createMqttException(32111);
            } else if (isDisconnected()) {
                log.fine(CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "211");
                throw ExceptionHelper.createMqttException(32101);
            } else if (isDisconnecting()) {
                log.fine(CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "219");
                throw ExceptionHelper.createMqttException(32102);
            } else if (Thread.currentThread() != this.callback.getThread()) {
                log.fine(CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "218");
                this.conState = 2;
                new DisconnectBG(mqttDisconnect, j, mqttToken, this.executorService).start();
            } else {
                log.fine(CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "210");
                throw ExceptionHelper.createMqttException(32107);
            }
        }
    }

    public void disconnectForcibly(long j, long j2) throws MqttException {
        disconnectForcibly(j, j2, true);
    }

    public void disconnectForcibly(long j, long j2, boolean z) throws MqttException {
        ClientState clientState2 = this.clientState;
        if (clientState2 != null) {
            clientState2.quiesce(j);
        }
        MqttToken mqttToken = new MqttToken(this.client.getClientId());
        if (z) {
            try {
                internalSend(new MqttDisconnect(), mqttToken);
                mqttToken.waitForCompletion(j2);
            } catch (Exception unused) {
            } catch (Throwable th) {
                mqttToken.internalTok.markComplete((MqttWireMessage) null, (MqttException) null);
                shutdownConnection(mqttToken, (MqttException) null);
                throw th;
            }
        }
        mqttToken.internalTok.markComplete((MqttWireMessage) null, (MqttException) null);
        shutdownConnection(mqttToken, (MqttException) null);
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 0;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.conLock) {
            z = true;
            if (this.conState != 1) {
                z = false;
            }
        }
        return z;
    }

    public boolean isDisconnected() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 3;
        }
        return z;
    }

    public boolean isDisconnecting() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 2;
        }
        return z;
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 4;
        }
        return z;
    }

    public boolean isResting() {
        boolean z;
        synchronized (this.conLock) {
            z = this.resting;
        }
        return z;
    }

    public void setCallback(MqttCallback mqttCallback) {
        this.callback.setCallback(mqttCallback);
    }

    public void setReconnectCallback(MqttCallbackExtended mqttCallbackExtended) {
        this.callback.setReconnectCallback(mqttCallbackExtended);
    }

    public void setManualAcks(boolean z) {
        this.callback.setManualAcks(z);
    }

    public void messageArrivedComplete(int i, int i2) throws MqttException {
        this.callback.messageArrivedComplete(i, i2);
    }

    public void setMessageListener(String str, IMqttMessageListener iMqttMessageListener) {
        this.callback.setMessageListener(str, iMqttMessageListener);
    }

    public void removeMessageListener(String str) {
        this.callback.removeMessageListener(str);
    }

    /* access modifiers changed from: protected */
    public MqttTopic getTopic(String str) {
        return new MqttTopic(str, this);
    }

    public void setNetworkModuleIndex(int i) {
        this.networkModuleIndex = i;
    }

    public int getNetworkModuleIndex() {
        return this.networkModuleIndex;
    }

    public NetworkModule[] getNetworkModules() {
        return this.networkModules;
    }

    public void setNetworkModules(NetworkModule[] networkModuleArr) {
        this.networkModules = networkModuleArr;
    }

    public MqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.tokenStore.getOutstandingDelTokens();
    }

    /* access modifiers changed from: protected */
    public void deliveryComplete(MqttPublish mqttPublish) throws MqttPersistenceException {
        this.clientState.deliveryComplete(mqttPublish);
    }

    /* access modifiers changed from: protected */
    public void deliveryComplete(int i) throws MqttPersistenceException {
        this.clientState.deliveryComplete(i);
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    public long getKeepAlive() {
        return this.clientState.getKeepAlive();
    }

    public ClientState getClientState() {
        return this.clientState;
    }

    public MqttConnectOptions getConOptions() {
        return this.conOptions;
    }

    public Properties getDebug() {
        Properties properties = new Properties();
        properties.put("conState", new Integer(this.conState));
        properties.put("serverURI", getClient().getServerURI());
        properties.put("callback", this.callback);
        properties.put("stoppingComms", new Boolean(this.stoppingComms));
        return properties;
    }

    private class ConnectBG implements Runnable {
        ClientComms clientComms = null;
        MqttConnect conPacket;
        MqttToken conToken;
        private String threadName;

        ConnectBG(ClientComms clientComms2, MqttToken mqttToken, MqttConnect mqttConnect, ExecutorService executorService) {
            this.clientComms = clientComms2;
            this.conToken = mqttToken;
            this.conPacket = mqttConnect;
            this.threadName = "MQTT Con: " + ClientComms.this.getClient().getClientId();
        }

        /* access modifiers changed from: package-private */
        public void start() {
            ClientComms.this.executorService.execute(this);
        }

        public void run() {
            Thread.currentThread().setName(this.threadName);
            ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "220");
            MqttException e = null;
            try {
                MqttDeliveryToken[] outstandingDelTokens = ClientComms.this.tokenStore.getOutstandingDelTokens();
                for (MqttDeliveryToken mqttDeliveryToken : outstandingDelTokens) {
                    mqttDeliveryToken.internalTok.setException((MqttException) null);
                }
                ClientComms.this.tokenStore.saveToken(this.conToken, (MqttWireMessage) this.conPacket);
                NetworkModule networkModule = ClientComms.this.networkModules[ClientComms.this.networkModuleIndex];
                networkModule.start();
                ClientComms.this.receiver = new CommsReceiver(this.clientComms, ClientComms.this.clientState, ClientComms.this.tokenStore, networkModule.getInputStream());
                ClientComms.this.receiver.start("MQTT Rec: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                ClientComms.this.sender = new CommsSender(this.clientComms, ClientComms.this.clientState, ClientComms.this.tokenStore, networkModule.getOutputStream());
                ClientComms.this.sender.start("MQTT Snd: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                ClientComms.this.callback.start("MQTT Call: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                ClientComms.this.internalSend(this.conPacket, this.conToken);
            } catch (MqttException e2) {
                e = e2;
                ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "212", (Object[]) null, e);
            } catch (Exception e3) {
                ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "209", (Object[]) null, e3);
                e = ExceptionHelper.createMqttException((Throwable) e3);
            }
            if (e != null) {
                ClientComms.this.shutdownConnection(this.conToken, e);
            }
        }
    }

    private class DisconnectBG implements Runnable {
        MqttDisconnect disconnect;
        long quiesceTimeout;
        private String threadName;
        MqttToken token;

        DisconnectBG(MqttDisconnect mqttDisconnect, long j, MqttToken mqttToken, ExecutorService executorService) {
            this.disconnect = mqttDisconnect;
            this.quiesceTimeout = j;
            this.token = mqttToken;
        }

        /* access modifiers changed from: package-private */
        public void start() {
            this.threadName = "MQTT Disc: " + ClientComms.this.getClient().getClientId();
            ClientComms.this.executorService.execute(this);
        }

        public void run() {
            Thread.currentThread().setName(this.threadName);
            ClientComms.log.fine(ClientComms.CLASS_NAME, "disconnectBG:run", "221");
            ClientComms.this.clientState.quiesce(this.quiesceTimeout);
            try {
                ClientComms.this.internalSend(this.disconnect, this.token);
                this.token.internalTok.waitUntilSent();
            } catch (MqttException unused) {
            } catch (Throwable th) {
                this.token.internalTok.markComplete((MqttWireMessage) null, (MqttException) null);
                ClientComms.this.shutdownConnection(this.token, (MqttException) null);
                throw th;
            }
            this.token.internalTok.markComplete((MqttWireMessage) null, (MqttException) null);
            ClientComms.this.shutdownConnection(this.token, (MqttException) null);
        }
    }

    public MqttToken checkForActivity() {
        return checkForActivity((IMqttActionListener) null);
    }

    public MqttToken checkForActivity(IMqttActionListener iMqttActionListener) {
        try {
            return this.clientState.checkForActivity(iMqttActionListener);
        } catch (MqttException e) {
            handleRunException(e);
            return null;
        } catch (Exception e2) {
            handleRunException(e2);
            return null;
        }
    }

    private void handleRunException(Exception exc) {
        MqttException mqttException;
        log.fine(CLASS_NAME, "handleRunException", "804", (Object[]) null, exc);
        if (!(exc instanceof MqttException)) {
            mqttException = new MqttException(32109, exc);
        } else {
            mqttException = (MqttException) exc;
        }
        shutdownConnection((MqttToken) null, mqttException);
    }

    public void setRestingState(boolean z) {
        this.resting = z;
    }

    public void setDisconnectedMessageBuffer(DisconnectedMessageBuffer disconnectedMessageBuffer2) {
        this.disconnectedMessageBuffer = disconnectedMessageBuffer2;
    }

    public int getBufferedMessageCount() {
        return this.disconnectedMessageBuffer.getMessageCount();
    }

    public MqttMessage getBufferedMessage(int i) {
        return ((MqttPublish) this.disconnectedMessageBuffer.getMessage(i).getMessage()).getMessage();
    }

    public void deleteBufferedMessage(int i) {
        this.disconnectedMessageBuffer.deleteMessage(i);
    }

    public void notifyConnect() {
        if (this.disconnectedMessageBuffer != null) {
            log.fine(CLASS_NAME, "notifyConnect", "509");
            this.disconnectedMessageBuffer.setPublishCallback(new ReconnectDisconnectedBufferCallback("notifyConnect"));
            this.executorService.execute(this.disconnectedMessageBuffer);
        }
    }

    class ReconnectDisconnectedBufferCallback implements IDisconnectedBufferCallback {
        final String methodName;

        ReconnectDisconnectedBufferCallback(String str) {
            this.methodName = str;
        }

        public void publishBufferedMessage(BufferedMessage bufferedMessage) throws MqttException {
            if (ClientComms.this.isConnected()) {
                while (ClientComms.this.clientState.getActualInFlight() >= ClientComms.this.clientState.getMaxInFlight() - 1) {
                    Thread.yield();
                }
                ClientComms.log.fine(ClientComms.CLASS_NAME, this.methodName, "510", new Object[]{bufferedMessage.getMessage().getKey()});
                ClientComms.this.internalSend(bufferedMessage.getMessage(), bufferedMessage.getToken());
                ClientComms.this.clientState.unPersistBufferedMessage(bufferedMessage.getMessage());
                return;
            }
            ClientComms.log.fine(ClientComms.CLASS_NAME, this.methodName, "208");
            throw ExceptionHelper.createMqttException(32104);
        }
    }

    public int getActualInFlight() {
        return this.clientState.getActualInFlight();
    }
}
