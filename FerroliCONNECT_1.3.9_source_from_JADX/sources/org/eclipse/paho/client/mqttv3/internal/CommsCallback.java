package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsCallback implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsCallback";
    private static final int INBOUND_QUEUE_SIZE = 10;
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private Future callbackFuture;
    private Thread callbackThread;
    private Hashtable callbacks;
    private ClientComms clientComms;
    private ClientState clientState;
    private Vector completeQueue;
    private Object lifecycle = new Object();
    private boolean manualAcks = false;
    private Vector messageQueue;
    private MqttCallback mqttCallback;
    private boolean quiescing = false;
    private MqttCallbackExtended reconnectInternalCallback;
    public boolean running = false;
    private final Semaphore runningSemaphore = new Semaphore(1);
    private Object spaceAvailable = new Object();
    private String threadName;
    private Object workAvailable = new Object();

    CommsCallback(ClientComms clientComms2) {
        this.clientComms = clientComms2;
        this.messageQueue = new Vector(10);
        this.completeQueue = new Vector(10);
        this.callbacks = new Hashtable();
        log.setResourceName(clientComms2.getClient().getClientId());
    }

    public void setClientState(ClientState clientState2) {
        this.clientState = clientState2;
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        synchronized (this.lifecycle) {
            if (!this.running) {
                this.messageQueue.clear();
                this.completeQueue.clear();
                this.running = true;
                this.quiescing = false;
                this.callbackFuture = executorService.submit(this);
            }
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
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void stop() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.lifecycle
            monitor-enter(r0)
            java.util.concurrent.Future r1 = r6.callbackFuture     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x000d
            java.util.concurrent.Future r1 = r6.callbackFuture     // Catch:{ all -> 0x0067 }
            r2 = 1
            r1.cancel(r2)     // Catch:{ all -> 0x0067 }
        L_0x000d:
            boolean r1 = r6.running     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x0057
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = log     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = CLASS_NAME     // Catch:{ all -> 0x0067 }
            java.lang.String r3 = "stop"
            java.lang.String r4 = "700"
            r1.fine(r2, r3, r4)     // Catch:{ all -> 0x0067 }
            r1 = 0
            r6.running = r1     // Catch:{ all -> 0x0067 }
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0067 }
            java.lang.Thread r2 = r6.callbackThread     // Catch:{ all -> 0x0067 }
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0067 }
            if (r1 != 0) goto L_0x0057
            java.lang.Object r1 = r6.workAvailable     // Catch:{ InterruptedException -> 0x0054, all -> 0x004d }
            monitor-enter(r1)     // Catch:{ InterruptedException -> 0x0054, all -> 0x004d }
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = log     // Catch:{ all -> 0x004a }
            java.lang.String r3 = CLASS_NAME     // Catch:{ all -> 0x004a }
            java.lang.String r4 = "stop"
            java.lang.String r5 = "701"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x004a }
            java.lang.Object r2 = r6.workAvailable     // Catch:{ all -> 0x004a }
            r2.notifyAll()     // Catch:{ all -> 0x004a }
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            java.util.concurrent.Semaphore r1 = r6.runningSemaphore     // Catch:{ InterruptedException -> 0x0054, all -> 0x004d }
            r1.acquire()     // Catch:{ InterruptedException -> 0x0054, all -> 0x004d }
            java.util.concurrent.Semaphore r1 = r6.runningSemaphore     // Catch:{ all -> 0x0067 }
        L_0x0046:
            r1.release()     // Catch:{ all -> 0x0067 }
            goto L_0x0057
        L_0x004a:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            throw r2     // Catch:{ InterruptedException -> 0x0054, all -> 0x004d }
        L_0x004d:
            r1 = move-exception
            java.util.concurrent.Semaphore r2 = r6.runningSemaphore     // Catch:{ all -> 0x0067 }
            r2.release()     // Catch:{ all -> 0x0067 }
            throw r1     // Catch:{ all -> 0x0067 }
        L_0x0054:
            java.util.concurrent.Semaphore r1 = r6.runningSemaphore     // Catch:{ all -> 0x0067 }
            goto L_0x0046
        L_0x0057:
            r1 = 0
            r6.callbackThread = r1     // Catch:{ all -> 0x0067 }
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = log     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = CLASS_NAME     // Catch:{ all -> 0x0067 }
            java.lang.String r3 = "stop"
            java.lang.String r4 = "703"
            r1.fine(r2, r3, r4)     // Catch:{ all -> 0x0067 }
            monitor-exit(r0)     // Catch:{ all -> 0x0067 }
            return
        L_0x0067:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0067 }
            goto L_0x006b
        L_0x006a:
            throw r1
        L_0x006b:
            goto L_0x006a
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.CommsCallback.stop():void");
    }

    public void setCallback(MqttCallback mqttCallback2) {
        this.mqttCallback = mqttCallback2;
    }

    public void setReconnectCallback(MqttCallbackExtended mqttCallbackExtended) {
        this.reconnectInternalCallback = mqttCallbackExtended;
    }

    public void setManualAcks(boolean z) {
        this.manualAcks = z;
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
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void run() {
        /*
            r9 = this;
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r9.callbackThread = r0
            java.lang.Thread r0 = r9.callbackThread
            java.lang.String r1 = r9.threadName
            r0.setName(r1)
            r0 = 0
            java.util.concurrent.Semaphore r1 = r9.runningSemaphore     // Catch:{ InterruptedException -> 0x0111 }
            r1.acquire()     // Catch:{ InterruptedException -> 0x0111 }
        L_0x0013:
            boolean r1 = r9.running
            if (r1 != 0) goto L_0x0018
            return
        L_0x0018:
            r1 = 0
            java.lang.Object r2 = r9.workAvailable     // Catch:{ InterruptedException -> 0x004b }
            monitor-enter(r2)     // Catch:{ InterruptedException -> 0x004b }
            boolean r3 = r9.running     // Catch:{ all -> 0x0042 }
            if (r3 == 0) goto L_0x0040
            java.util.Vector r3 = r9.messageQueue     // Catch:{ all -> 0x0042 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0042 }
            if (r3 == 0) goto L_0x0040
            java.util.Vector r3 = r9.completeQueue     // Catch:{ all -> 0x0042 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0042 }
            if (r3 == 0) goto L_0x0040
            org.eclipse.paho.client.mqttv3.logging.Logger r3 = log     // Catch:{ all -> 0x0042 }
            java.lang.String r4 = CLASS_NAME     // Catch:{ all -> 0x0042 }
            java.lang.String r5 = "run"
            java.lang.String r6 = "704"
            r3.fine(r4, r5, r6)     // Catch:{ all -> 0x0042 }
            java.lang.Object r3 = r9.workAvailable     // Catch:{ all -> 0x0042 }
            r3.wait()     // Catch:{ all -> 0x0042 }
        L_0x0040:
            monitor-exit(r2)     // Catch:{ all -> 0x0042 }
            goto L_0x004b
        L_0x0042:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0042 }
            throw r3     // Catch:{ InterruptedException -> 0x004b }
        L_0x0045:
            r0 = move-exception
            goto L_0x00f4
        L_0x0048:
            r2 = move-exception
            goto L_0x00bd
        L_0x004b:
            boolean r2 = r9.running     // Catch:{ Throwable -> 0x0048 }
            if (r2 == 0) goto L_0x0096
            java.util.Vector r2 = r9.completeQueue     // Catch:{ Throwable -> 0x0048 }
            monitor-enter(r2)     // Catch:{ Throwable -> 0x0048 }
            java.util.Vector r3 = r9.completeQueue     // Catch:{ all -> 0x0093 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0093 }
            if (r3 != 0) goto L_0x0068
            java.util.Vector r3 = r9.completeQueue     // Catch:{ all -> 0x0093 }
            java.lang.Object r3 = r3.elementAt(r0)     // Catch:{ all -> 0x0093 }
            org.eclipse.paho.client.mqttv3.MqttToken r3 = (org.eclipse.paho.client.mqttv3.MqttToken) r3     // Catch:{ all -> 0x0093 }
            java.util.Vector r4 = r9.completeQueue     // Catch:{ all -> 0x0093 }
            r4.removeElementAt(r0)     // Catch:{ all -> 0x0093 }
            goto L_0x0069
        L_0x0068:
            r3 = r1
        L_0x0069:
            monitor-exit(r2)     // Catch:{ all -> 0x0093 }
            if (r3 == 0) goto L_0x006f
            r9.handleActionComplete(r3)     // Catch:{ Throwable -> 0x0048 }
        L_0x006f:
            java.util.Vector r2 = r9.messageQueue     // Catch:{ Throwable -> 0x0048 }
            monitor-enter(r2)     // Catch:{ Throwable -> 0x0048 }
            java.util.Vector r3 = r9.messageQueue     // Catch:{ all -> 0x0090 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0090 }
            if (r3 != 0) goto L_0x0088
            java.util.Vector r3 = r9.messageQueue     // Catch:{ all -> 0x0090 }
            java.lang.Object r3 = r3.elementAt(r0)     // Catch:{ all -> 0x0090 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish r3 = (org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish) r3     // Catch:{ all -> 0x0090 }
            java.util.Vector r4 = r9.messageQueue     // Catch:{ all -> 0x0090 }
            r4.removeElementAt(r0)     // Catch:{ all -> 0x0090 }
            goto L_0x0089
        L_0x0088:
            r3 = r1
        L_0x0089:
            monitor-exit(r2)     // Catch:{ all -> 0x0090 }
            if (r3 == 0) goto L_0x0096
            r9.handleMessage(r3)     // Catch:{ Throwable -> 0x0048 }
            goto L_0x0096
        L_0x0090:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0090 }
            throw r3     // Catch:{ Throwable -> 0x0048 }
        L_0x0093:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0093 }
            throw r3     // Catch:{ Throwable -> 0x0048 }
        L_0x0096:
            boolean r2 = r9.quiescing     // Catch:{ Throwable -> 0x0048 }
            if (r2 == 0) goto L_0x009f
            org.eclipse.paho.client.mqttv3.internal.ClientState r2 = r9.clientState     // Catch:{ Throwable -> 0x0048 }
            r2.checkQuiesceLock()     // Catch:{ Throwable -> 0x0048 }
        L_0x009f:
            java.util.concurrent.Semaphore r1 = r9.runningSemaphore
            r1.release()
            java.lang.Object r1 = r9.spaceAvailable
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = log     // Catch:{ all -> 0x00ba }
            java.lang.String r3 = CLASS_NAME     // Catch:{ all -> 0x00ba }
            java.lang.String r4 = "run"
            java.lang.String r5 = "706"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x00ba }
            java.lang.Object r2 = r9.spaceAvailable     // Catch:{ all -> 0x00ba }
            r2.notifyAll()     // Catch:{ all -> 0x00ba }
            monitor-exit(r1)     // Catch:{ all -> 0x00ba }
            goto L_0x0013
        L_0x00ba:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00ba }
            throw r0
        L_0x00bd:
            org.eclipse.paho.client.mqttv3.logging.Logger r3 = log     // Catch:{ all -> 0x0045 }
            java.lang.String r4 = CLASS_NAME     // Catch:{ all -> 0x0045 }
            java.lang.String r5 = "run"
            java.lang.String r6 = "714"
            r7 = 0
            r8 = r2
            r3.fine(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0045 }
            r9.running = r0     // Catch:{ all -> 0x0045 }
            org.eclipse.paho.client.mqttv3.internal.ClientComms r3 = r9.clientComms     // Catch:{ all -> 0x0045 }
            org.eclipse.paho.client.mqttv3.MqttException r4 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x0045 }
            r4.<init>((java.lang.Throwable) r2)     // Catch:{ all -> 0x0045 }
            r3.shutdownConnection(r1, r4)     // Catch:{ all -> 0x0045 }
            java.util.concurrent.Semaphore r1 = r9.runningSemaphore
            r1.release()
            java.lang.Object r1 = r9.spaceAvailable
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = log     // Catch:{ all -> 0x00f1 }
            java.lang.String r3 = CLASS_NAME     // Catch:{ all -> 0x00f1 }
            java.lang.String r4 = "run"
            java.lang.String r5 = "706"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x00f1 }
            java.lang.Object r2 = r9.spaceAvailable     // Catch:{ all -> 0x00f1 }
            r2.notifyAll()     // Catch:{ all -> 0x00f1 }
            monitor-exit(r1)     // Catch:{ all -> 0x00f1 }
            goto L_0x0013
        L_0x00f1:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00f1 }
            throw r0
        L_0x00f4:
            java.util.concurrent.Semaphore r1 = r9.runningSemaphore
            r1.release()
            java.lang.Object r1 = r9.spaceAvailable
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = log     // Catch:{ all -> 0x010e }
            java.lang.String r3 = CLASS_NAME     // Catch:{ all -> 0x010e }
            java.lang.String r4 = "run"
            java.lang.String r5 = "706"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x010e }
            java.lang.Object r2 = r9.spaceAvailable     // Catch:{ all -> 0x010e }
            r2.notifyAll()     // Catch:{ all -> 0x010e }
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
            throw r0
        L_0x010e:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
            throw r0
        L_0x0111:
            r9.running = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.CommsCallback.run():void");
    }

    private void handleActionComplete(MqttToken mqttToken) throws MqttException {
        synchronized (mqttToken) {
            log.fine(CLASS_NAME, "handleActionComplete", "705", new Object[]{mqttToken.internalTok.getKey()});
            if (mqttToken.isComplete()) {
                this.clientState.notifyComplete(mqttToken);
            }
            mqttToken.internalTok.notifyComplete();
            if (!mqttToken.internalTok.isNotified()) {
                if (this.mqttCallback != null && (mqttToken instanceof MqttDeliveryToken) && mqttToken.isComplete()) {
                    this.mqttCallback.deliveryComplete((MqttDeliveryToken) mqttToken);
                }
                fireActionEvent(mqttToken);
            }
            if (mqttToken.isComplete() && ((mqttToken instanceof MqttDeliveryToken) || (mqttToken.getActionCallback() instanceof IMqttActionListener))) {
                mqttToken.internalTok.setNotified(true);
            }
        }
    }

    public void connectionLost(MqttException mqttException) {
        try {
            if (!(this.mqttCallback == null || mqttException == null)) {
                log.fine(CLASS_NAME, "connectionLost", "708", new Object[]{mqttException});
                this.mqttCallback.connectionLost(mqttException);
            }
            if (this.reconnectInternalCallback != null && mqttException != null) {
                this.reconnectInternalCallback.connectionLost(mqttException);
            }
        } catch (Throwable th) {
            log.fine(CLASS_NAME, "connectionLost", "720", new Object[]{th});
        }
    }

    public void fireActionEvent(MqttToken mqttToken) {
        IMqttActionListener actionCallback;
        if (mqttToken != null && (actionCallback = mqttToken.getActionCallback()) != null) {
            if (mqttToken.getException() == null) {
                log.fine(CLASS_NAME, "fireActionEvent", "716", new Object[]{mqttToken.internalTok.getKey()});
                actionCallback.onSuccess(mqttToken);
                return;
            }
            log.fine(CLASS_NAME, "fireActionEvent", "716", new Object[]{mqttToken.internalTok.getKey()});
            actionCallback.onFailure(mqttToken, mqttToken.getException());
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x000f */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x000f A[LOOP:0: B:6:0x000f->B:33:0x000f, LOOP_START, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void messageArrived(org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish r6) {
        /*
            r5 = this;
            org.eclipse.paho.client.mqttv3.MqttCallback r0 = r5.mqttCallback
            if (r0 != 0) goto L_0x000c
            java.util.Hashtable r0 = r5.callbacks
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0057
        L_0x000c:
            java.lang.Object r0 = r5.spaceAvailable
            monitor-enter(r0)
        L_0x000f:
            boolean r1 = r5.running     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x0035
            boolean r1 = r5.quiescing     // Catch:{ all -> 0x0058 }
            if (r1 != 0) goto L_0x0035
            java.util.Vector r1 = r5.messageQueue     // Catch:{ all -> 0x0058 }
            int r1 = r1.size()     // Catch:{ all -> 0x0058 }
            r2 = 10
            if (r1 >= r2) goto L_0x0022
            goto L_0x0035
        L_0x0022:
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = log     // Catch:{ InterruptedException -> 0x000f }
            java.lang.String r2 = CLASS_NAME     // Catch:{ InterruptedException -> 0x000f }
            java.lang.String r3 = "messageArrived"
            java.lang.String r4 = "709"
            r1.fine(r2, r3, r4)     // Catch:{ InterruptedException -> 0x000f }
            java.lang.Object r1 = r5.spaceAvailable     // Catch:{ InterruptedException -> 0x000f }
            r2 = 200(0xc8, double:9.9E-322)
            r1.wait(r2)     // Catch:{ InterruptedException -> 0x000f }
            goto L_0x000f
        L_0x0035:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            boolean r0 = r5.quiescing
            if (r0 != 0) goto L_0x0057
            java.util.Vector r0 = r5.messageQueue
            r0.addElement(r6)
            java.lang.Object r6 = r5.workAvailable
            monitor-enter(r6)
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = log     // Catch:{ all -> 0x0054 }
            java.lang.String r1 = CLASS_NAME     // Catch:{ all -> 0x0054 }
            java.lang.String r2 = "messageArrived"
            java.lang.String r3 = "710"
            r0.fine(r1, r2, r3)     // Catch:{ all -> 0x0054 }
            java.lang.Object r0 = r5.workAvailable     // Catch:{ all -> 0x0054 }
            r0.notifyAll()     // Catch:{ all -> 0x0054 }
            monitor-exit(r6)     // Catch:{ all -> 0x0054 }
            goto L_0x0057
        L_0x0054:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0054 }
            throw r0
        L_0x0057:
            return
        L_0x0058:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            goto L_0x005c
        L_0x005b:
            throw r6
        L_0x005c:
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.CommsCallback.messageArrived(org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish):void");
    }

    public void quiesce() {
        this.quiescing = true;
        synchronized (this.spaceAvailable) {
            log.fine(CLASS_NAME, "quiesce", "711");
            this.spaceAvailable.notifyAll();
        }
    }

    public boolean isQuiesced() {
        return this.quiescing && this.completeQueue.size() == 0 && this.messageQueue.size() == 0;
    }

    private void handleMessage(MqttPublish mqttPublish) throws MqttException, Exception {
        String topicName = mqttPublish.getTopicName();
        log.fine(CLASS_NAME, "handleMessage", "713", new Object[]{new Integer(mqttPublish.getMessageId()), topicName});
        deliverMessage(topicName, mqttPublish.getMessageId(), mqttPublish.getMessage());
        if (this.manualAcks) {
            return;
        }
        if (mqttPublish.getMessage().getQos() == 1) {
            this.clientComms.internalSend(new MqttPubAck(mqttPublish), new MqttToken(this.clientComms.getClient().getClientId()));
        } else if (mqttPublish.getMessage().getQos() == 2) {
            this.clientComms.deliveryComplete(mqttPublish);
            MqttPubComp mqttPubComp = new MqttPubComp(mqttPublish);
            ClientComms clientComms2 = this.clientComms;
            clientComms2.internalSend(mqttPubComp, new MqttToken(clientComms2.getClient().getClientId()));
        }
    }

    public void messageArrivedComplete(int i, int i2) throws MqttException {
        if (i2 == 1) {
            this.clientComms.internalSend(new MqttPubAck(i), new MqttToken(this.clientComms.getClient().getClientId()));
        } else if (i2 == 2) {
            this.clientComms.deliveryComplete(i);
            MqttPubComp mqttPubComp = new MqttPubComp(i);
            ClientComms clientComms2 = this.clientComms;
            clientComms2.internalSend(mqttPubComp, new MqttToken(clientComms2.getClient().getClientId()));
        }
    }

    public void asyncOperationComplete(MqttToken mqttToken) {
        if (this.running) {
            this.completeQueue.addElement(mqttToken);
            synchronized (this.workAvailable) {
                log.fine(CLASS_NAME, "asyncOperationComplete", "715", new Object[]{mqttToken.internalTok.getKey()});
                this.workAvailable.notifyAll();
            }
            return;
        }
        try {
            handleActionComplete(mqttToken);
        } catch (Throwable th) {
            log.fine(CLASS_NAME, "asyncOperationComplete", "719", (Object[]) null, th);
            this.clientComms.shutdownConnection((MqttToken) null, new MqttException(th));
        }
    }

    /* access modifiers changed from: protected */
    public Thread getThread() {
        return this.callbackThread;
    }

    public void setMessageListener(String str, IMqttMessageListener iMqttMessageListener) {
        this.callbacks.put(str, iMqttMessageListener);
    }

    public void removeMessageListener(String str) {
        this.callbacks.remove(str);
    }

    public void removeMessageListeners() {
        this.callbacks.clear();
    }

    /* access modifiers changed from: protected */
    public boolean deliverMessage(String str, int i, MqttMessage mqttMessage) throws Exception {
        Enumeration keys = this.callbacks.keys();
        boolean z = false;
        while (keys.hasMoreElements()) {
            String str2 = (String) keys.nextElement();
            if (MqttTopic.isMatched(str2, str)) {
                mqttMessage.setId(i);
                ((IMqttMessageListener) this.callbacks.get(str2)).messageArrived(str, mqttMessage);
                z = true;
            }
        }
        if (this.mqttCallback == null || z) {
            return z;
        }
        mqttMessage.setId(i);
        this.mqttCallback.messageArrived(str, mqttMessage);
        return true;
    }
}
