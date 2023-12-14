package org.eclipse.paho.client.mqttv3.internal;

import anetwork.channel.util.RequestConstant;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class Token {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.Token";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private IMqttActionListener callback = null;
    private IMqttAsyncClient client = null;
    private volatile boolean completed = false;
    private MqttException exception = null;
    private String key;
    protected MqttMessage message = null;
    private int messageID = 0;
    private boolean notified = false;
    private boolean pendingComplete = false;
    private MqttWireMessage response = null;
    private Object responseLock = new Object();
    private boolean sent = false;
    private Object sentLock = new Object();
    private String[] topics = null;
    private Object userContext = null;

    public Token(String str) {
        log.setResourceName(str);
    }

    public int getMessageID() {
        return this.messageID;
    }

    public void setMessageID(int i) {
        this.messageID = i;
    }

    public boolean checkResult() throws MqttException {
        if (getException() == null) {
            return true;
        }
        throw getException();
    }

    public MqttException getException() {
        return this.exception;
    }

    public boolean isComplete() {
        return this.completed;
    }

    /* access modifiers changed from: protected */
    public boolean isCompletePending() {
        return this.pendingComplete;
    }

    /* access modifiers changed from: protected */
    public boolean isInUse() {
        return getClient() != null && !isComplete();
    }

    public void setActionCallback(IMqttActionListener iMqttActionListener) {
        this.callback = iMqttActionListener;
    }

    public IMqttActionListener getActionCallback() {
        return this.callback;
    }

    public void waitForCompletion() throws MqttException {
        waitForCompletion(-1);
    }

    public void waitForCompletion(long j) throws MqttException {
        log.fine(CLASS_NAME, "waitForCompletion", "407", new Object[]{getKey(), new Long(j), this});
        if (waitForResponse(j) != null || this.completed) {
            checkResult();
            return;
        }
        log.fine(CLASS_NAME, "waitForCompletion", "406", new Object[]{getKey(), this});
        this.exception = new MqttException(32000);
        throw this.exception;
    }

    /* access modifiers changed from: protected */
    public MqttWireMessage waitForResponse() throws MqttException {
        return waitForResponse(-1);
    }

    /* access modifiers changed from: protected */
    public MqttWireMessage waitForResponse(long j) throws MqttException {
        synchronized (this.responseLock) {
            Logger logger = log;
            String str = CLASS_NAME;
            Object[] objArr = new Object[7];
            objArr[0] = getKey();
            objArr[1] = new Long(j);
            objArr[2] = new Boolean(this.sent);
            objArr[3] = new Boolean(this.completed);
            objArr[4] = this.exception == null ? RequestConstant.FALSE : RequestConstant.TRUE;
            objArr[5] = this.response;
            objArr[6] = this;
            logger.fine(str, "waitForResponse", "400", objArr, this.exception);
            while (true) {
                if (this.completed) {
                    break;
                }
                if (this.exception == null) {
                    try {
                        log.fine(CLASS_NAME, "waitForResponse", "408", new Object[]{getKey(), new Long(j)});
                        if (j <= 0) {
                            this.responseLock.wait();
                        } else {
                            this.responseLock.wait(j);
                        }
                    } catch (InterruptedException e) {
                        this.exception = new MqttException((Throwable) e);
                    }
                }
                if (!this.completed) {
                    if (this.exception != null) {
                        log.fine(CLASS_NAME, "waitForResponse", "401", (Object[]) null, this.exception);
                        throw this.exception;
                    } else if (j > 0) {
                        break;
                    }
                }
            }
        }
        log.fine(CLASS_NAME, "waitForResponse", "402", new Object[]{getKey(), this.response});
        return this.response;
    }

    /* access modifiers changed from: protected */
    public void markComplete(MqttWireMessage mqttWireMessage, MqttException mqttException) {
        log.fine(CLASS_NAME, "markComplete", "404", new Object[]{getKey(), mqttWireMessage, mqttException});
        synchronized (this.responseLock) {
            if (mqttWireMessage instanceof MqttAck) {
                this.message = null;
            }
            this.pendingComplete = true;
            this.response = mqttWireMessage;
            this.exception = mqttException;
        }
    }

    /* access modifiers changed from: protected */
    public void notifyComplete() {
        log.fine(CLASS_NAME, "notifyComplete", "404", new Object[]{getKey(), this.response, this.exception});
        synchronized (this.responseLock) {
            if (this.exception != null || !this.pendingComplete) {
                this.pendingComplete = false;
            } else {
                this.completed = true;
                this.pendingComplete = false;
            }
            this.responseLock.notifyAll();
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:22|23|36|34|10|9) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x000b */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0022 A[SYNTHETIC, Splitter:B:22:0x0022] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitUntilSent() throws org.eclipse.paho.client.mqttv3.MqttException {
        /*
            r8 = this;
            java.lang.Object r0 = r8.sentLock
            monitor-enter(r0)
            java.lang.Object r1 = r8.responseLock     // Catch:{ all -> 0x0043 }
            monitor-enter(r1)     // Catch:{ all -> 0x0043 }
            org.eclipse.paho.client.mqttv3.MqttException r2 = r8.exception     // Catch:{ all -> 0x0040 }
            if (r2 != 0) goto L_0x003d
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
        L_0x000b:
            boolean r1 = r8.sent     // Catch:{ all -> 0x0043 }
            if (r1 == 0) goto L_0x0022
            boolean r1 = r8.sent     // Catch:{ all -> 0x0043 }
            if (r1 != 0) goto L_0x0020
            org.eclipse.paho.client.mqttv3.MqttException r1 = r8.exception     // Catch:{ all -> 0x0043 }
            if (r1 != 0) goto L_0x001d
            r1 = 6
            org.eclipse.paho.client.mqttv3.MqttException r1 = org.eclipse.paho.client.mqttv3.internal.ExceptionHelper.createMqttException((int) r1)     // Catch:{ all -> 0x0043 }
            throw r1     // Catch:{ all -> 0x0043 }
        L_0x001d:
            org.eclipse.paho.client.mqttv3.MqttException r1 = r8.exception     // Catch:{ all -> 0x0043 }
            throw r1     // Catch:{ all -> 0x0043 }
        L_0x0020:
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return
        L_0x0022:
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = log     // Catch:{ InterruptedException -> 0x000b }
            java.lang.String r2 = CLASS_NAME     // Catch:{ InterruptedException -> 0x000b }
            java.lang.String r3 = "waitUntilSent"
            java.lang.String r4 = "409"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InterruptedException -> 0x000b }
            r6 = 0
            java.lang.String r7 = r8.getKey()     // Catch:{ InterruptedException -> 0x000b }
            r5[r6] = r7     // Catch:{ InterruptedException -> 0x000b }
            r1.fine(r2, r3, r4, r5)     // Catch:{ InterruptedException -> 0x000b }
            java.lang.Object r1 = r8.sentLock     // Catch:{ InterruptedException -> 0x000b }
            r1.wait()     // Catch:{ InterruptedException -> 0x000b }
            goto L_0x000b
        L_0x003d:
            org.eclipse.paho.client.mqttv3.MqttException r2 = r8.exception     // Catch:{ all -> 0x0040 }
            throw r2     // Catch:{ all -> 0x0040 }
        L_0x0040:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
            throw r2     // Catch:{ all -> 0x0043 }
        L_0x0043:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            goto L_0x0047
        L_0x0046:
            throw r1
        L_0x0047:
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.Token.waitUntilSent():void");
    }

    /* access modifiers changed from: protected */
    public void notifySent() {
        log.fine(CLASS_NAME, "notifySent", "403", new Object[]{getKey()});
        synchronized (this.responseLock) {
            this.response = null;
            this.completed = false;
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    /* access modifiers changed from: protected */
    public void setClient(IMqttAsyncClient iMqttAsyncClient) {
        this.client = iMqttAsyncClient;
    }

    public void reset() throws MqttException {
        if (!isInUse()) {
            log.fine(CLASS_NAME, "reset", "410", new Object[]{getKey()});
            this.client = null;
            this.completed = false;
            this.response = null;
            this.sent = false;
            this.exception = null;
            this.userContext = null;
            return;
        }
        throw new MqttException(32201);
    }

    public MqttMessage getMessage() {
        return this.message;
    }

    public MqttWireMessage getWireMessage() {
        return this.response;
    }

    public void setMessage(MqttMessage mqttMessage) {
        this.message = mqttMessage;
    }

    public String[] getTopics() {
        return this.topics;
    }

    public void setTopics(String[] strArr) {
        this.topics = strArr;
    }

    public Object getUserContext() {
        return this.userContext;
    }

    public void setUserContext(Object obj) {
        this.userContext = obj;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setException(MqttException mqttException) {
        synchronized (this.responseLock) {
            this.exception = mqttException;
        }
    }

    public boolean isNotified() {
        return this.notified;
    }

    public void setNotified(boolean z) {
        this.notified = z;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("key=");
        stringBuffer.append(getKey());
        stringBuffer.append(" ,topics=");
        if (getTopics() != null) {
            for (String append : getTopics()) {
                stringBuffer.append(append);
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append(" ,usercontext=");
        stringBuffer.append(getUserContext());
        stringBuffer.append(" ,isComplete=");
        stringBuffer.append(isComplete());
        stringBuffer.append(" ,isNotified=");
        stringBuffer.append(isNotified());
        stringBuffer.append(" ,exception=");
        stringBuffer.append(getException());
        stringBuffer.append(" ,actioncallback=");
        stringBuffer.append(getActionCallback());
        return stringBuffer.toString();
    }

    public int[] getGrantedQos() {
        int[] iArr = new int[0];
        MqttWireMessage mqttWireMessage = this.response;
        return mqttWireMessage instanceof MqttSuback ? ((MqttSuback) mqttWireMessage).getGrantedQos() : iArr;
    }

    public boolean getSessionPresent() {
        MqttWireMessage mqttWireMessage = this.response;
        if (mqttWireMessage instanceof MqttConnack) {
            return ((MqttConnack) mqttWireMessage).getSessionPresent();
        }
        return false;
    }

    public MqttWireMessage getResponse() {
        return this.response;
    }
}
