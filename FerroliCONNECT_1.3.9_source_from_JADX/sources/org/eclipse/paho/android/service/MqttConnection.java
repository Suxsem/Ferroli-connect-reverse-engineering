package org.eclipse.paho.android.service;

import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.paho.android.service.MessageStore;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

class MqttConnection implements MqttCallbackExtended {
    private static final String NOT_CONNECTED = "not connected";
    private static final String TAG = "MqttConnection";
    private AlarmPingSender alarmPingSender = null;
    private DisconnectedBufferOptions bufferOpts = null;
    private boolean cleanSession = true;
    /* access modifiers changed from: private */
    public String clientHandle;
    private String clientId;
    private MqttConnectOptions connectOptions;
    private volatile boolean disconnected = true;
    private volatile boolean isConnecting = false;
    private MqttAsyncClient myClient = null;
    private MqttClientPersistence persistence = null;
    private String reconnectActivityToken = null;
    private Map<IMqttDeliveryToken, String> savedActivityTokens = new HashMap();
    private Map<IMqttDeliveryToken, String> savedInvocationContexts = new HashMap();
    private Map<IMqttDeliveryToken, MqttMessage> savedSentMessages = new HashMap();
    private Map<IMqttDeliveryToken, String> savedTopics = new HashMap();
    private String serverURI;
    /* access modifiers changed from: private */
    public MqttService service = null;
    private String wakeLockTag = null;
    private PowerManager.WakeLock wakelock = null;

    public String getServerURI() {
        return this.serverURI;
    }

    public void setServerURI(String str) {
        this.serverURI = str;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String str) {
        this.clientId = str;
    }

    public MqttConnectOptions getConnectOptions() {
        return this.connectOptions;
    }

    public void setConnectOptions(MqttConnectOptions mqttConnectOptions) {
        this.connectOptions = mqttConnectOptions;
    }

    public String getClientHandle() {
        return this.clientHandle;
    }

    public void setClientHandle(String str) {
        this.clientHandle = str;
    }

    MqttConnection(MqttService mqttService, String str, String str2, MqttClientPersistence mqttClientPersistence, String str3) {
        this.serverURI = str;
        this.service = mqttService;
        this.clientId = str2;
        this.persistence = mqttClientPersistence;
        this.clientHandle = str3;
        this.wakeLockTag = getClass().getCanonicalName() + " " + str2 + " " + "on host " + str;
    }

    public void connect(MqttConnectOptions mqttConnectOptions, String str, String str2) {
        this.connectOptions = mqttConnectOptions;
        this.reconnectActivityToken = str2;
        if (mqttConnectOptions != null) {
            this.cleanSession = mqttConnectOptions.isCleanSession();
        }
        if (this.connectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "Connecting {" + this.serverURI + "} as {" + this.clientId + "}");
        final Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str2);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, "connect");
        try {
            if (this.persistence == null) {
                File externalFilesDir = this.service.getExternalFilesDir(TAG);
                if (externalFilesDir == null && (externalFilesDir = this.service.getDir(TAG, 0)) == null) {
                    bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, "Error! No external and internal storage available");
                    bundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, new MqttPersistenceException());
                    this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
                    return;
                }
                this.persistence = new MqttDefaultFilePersistence(externalFilesDir.getAbsolutePath());
            }
            C24811 r0 = new MqttConnectionListener(bundle) {
                public void onSuccess(IMqttToken iMqttToken) {
                    MqttConnection.this.doAfterConnectSuccess(bundle);
                    MqttConnection.this.service.traceDebug(MqttConnection.TAG, "connect success!");
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, th.getLocalizedMessage());
                    bundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, th);
                    MqttService access$200 = MqttConnection.this.service;
                    access$200.traceError(MqttConnection.TAG, "connect fail, call connect to reconnect.reason:" + th.getMessage());
                    MqttConnection.this.doAfterConnectFail(bundle);
                }
            };
            if (this.myClient == null) {
                this.alarmPingSender = new AlarmPingSender(this.service);
                this.myClient = new MqttAsyncClient(this.serverURI, this.clientId, this.persistence, this.alarmPingSender);
                this.myClient.setCallback(this);
                this.service.traceDebug(TAG, "Do Real connect!");
                setConnectingState(true);
                this.myClient.connect(this.connectOptions, str, r0);
            } else if (this.isConnecting) {
                this.service.traceDebug(TAG, "myClient != null and the client is connecting. Connect return directly.");
                MqttService mqttService2 = this.service;
                mqttService2.traceDebug(TAG, "Connect return:isConnecting:" + this.isConnecting + ".disconnected:" + this.disconnected);
            } else if (!this.disconnected) {
                this.service.traceDebug(TAG, "myClient != null and the client is connected and notify!");
                doAfterConnectSuccess(bundle);
            } else {
                this.service.traceDebug(TAG, "myClient != null and the client is not connected");
                this.service.traceDebug(TAG, "Do Real connect!");
                setConnectingState(true);
                this.myClient.connect(this.connectOptions, str, r0);
            }
        } catch (Exception e) {
            MqttService mqttService3 = this.service;
            mqttService3.traceError(TAG, "Exception occurred attempting to connect: " + e.getMessage());
            setConnectingState(false);
            handleException(bundle, e);
        }
    }

    /* access modifiers changed from: private */
    public void doAfterConnectSuccess(Bundle bundle) {
        acquireWakeLock();
        this.service.callbackToActivity(this.clientHandle, Status.OK, bundle);
        deliverBacklog();
        setConnectingState(false);
        this.disconnected = false;
        releaseWakeLock();
    }

    public void connectComplete(boolean z, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.CONNECT_EXTENDED_ACTION);
        bundle.putBoolean(MqttServiceConstants.CALLBACK_RECONNECT, z);
        bundle.putString(MqttServiceConstants.CALLBACK_SERVER_URI, str);
        this.service.callbackToActivity(this.clientHandle, Status.OK, bundle);
    }

    /* access modifiers changed from: private */
    public void doAfterConnectFail(Bundle bundle) {
        acquireWakeLock();
        this.disconnected = true;
        setConnectingState(false);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
        releaseWakeLock();
    }

    private void handleException(Bundle bundle, Exception exc) {
        bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, exc.getLocalizedMessage());
        bundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, exc);
        this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
    }

    private void deliverBacklog() {
        Iterator<MessageStore.StoredMessage> allArrivedMessages = this.service.messageStore.getAllArrivedMessages(this.clientHandle);
        while (allArrivedMessages.hasNext()) {
            MessageStore.StoredMessage next = allArrivedMessages.next();
            Bundle messageToBundle = messageToBundle(next.getMessageId(), next.getTopic(), next.getMessage());
            messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.MESSAGE_ARRIVED_ACTION);
            this.service.callbackToActivity(this.clientHandle, Status.OK, messageToBundle);
        }
    }

    private Bundle messageToBundle(String str, String str2, MqttMessage mqttMessage) {
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_MESSAGE_ID, str);
        bundle.putString(MqttServiceConstants.CALLBACK_DESTINATION_NAME, str2);
        bundle.putParcelable(MqttServiceConstants.CALLBACK_MESSAGE_PARCEL, new ParcelableMqttMessage(mqttMessage));
        return bundle;
    }

    /* access modifiers changed from: package-private */
    public void close() {
        this.service.traceDebug(TAG, "close()");
        try {
            if (this.myClient != null) {
                this.myClient.close();
            }
        } catch (MqttException e) {
            handleException(new Bundle(), e);
        }
    }

    /* access modifiers changed from: package-private */
    public void disconnect(long j, String str, String str2) {
        this.service.traceDebug(TAG, "disconnect()");
        this.disconnected = true;
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str2);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.DISCONNECT_ACTION);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.DISCONNECT_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
        } else {
            try {
                this.myClient.disconnect(j, str, new MqttConnectionListener(bundle));
            } catch (Exception e) {
                handleException(bundle, e);
            }
        }
        MqttConnectOptions mqttConnectOptions = this.connectOptions;
        if (mqttConnectOptions != null && mqttConnectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        releaseWakeLock();
    }

    /* access modifiers changed from: package-private */
    public void disconnect(String str, String str2) {
        this.service.traceDebug(TAG, "disconnect()");
        this.disconnected = true;
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str2);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.DISCONNECT_ACTION);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.DISCONNECT_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
        } else {
            try {
                this.myClient.disconnect(str, new MqttConnectionListener(bundle));
            } catch (Exception e) {
                handleException(bundle, e);
            }
        }
        MqttConnectOptions mqttConnectOptions = this.connectOptions;
        if (mqttConnectOptions != null && mqttConnectOptions.isCleanSession()) {
            this.service.messageStore.clearArrivedMessages(this.clientHandle);
        }
        releaseWakeLock();
    }

    public boolean isConnected() {
        MqttAsyncClient mqttAsyncClient = this.myClient;
        return mqttAsyncClient != null && mqttAsyncClient.isConnected();
    }

    public IMqttDeliveryToken publish(String str, byte[] bArr, int i, boolean z, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SEND_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str3);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str2);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        IMqttDeliveryToken iMqttDeliveryToken = null;
        if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SEND_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return null;
        }
        MqttConnectionListener mqttConnectionListener = new MqttConnectionListener(bundle);
        try {
            MqttMessage mqttMessage = new MqttMessage(bArr);
            mqttMessage.setQos(i);
            mqttMessage.setRetained(z);
            IMqttDeliveryToken publish = this.myClient.publish(str, bArr, i, z, str2, mqttConnectionListener);
            try {
                storeSendDetails(str, mqttMessage, publish, str2, str3);
                return publish;
            } catch (Exception e) {
                e = e;
                iMqttDeliveryToken = publish;
                handleException(bundle, e);
                return iMqttDeliveryToken;
            }
        } catch (Exception e2) {
            e = e2;
            handleException(bundle, e);
            return iMqttDeliveryToken;
        }
    }

    public IMqttDeliveryToken publish(String str, MqttMessage mqttMessage, String str2, String str3) {
        DisconnectedBufferOptions disconnectedBufferOptions;
        IMqttDeliveryToken publish;
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SEND_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str3);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str2);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        IMqttDeliveryToken iMqttDeliveryToken = null;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                publish = this.myClient.publish(str, mqttMessage, (Object) str2, (IMqttActionListener) new MqttConnectionListener(bundle));
                try {
                    storeSendDetails(str, mqttMessage, publish, str2, str3);
                } catch (Exception e) {
                    e = e;
                    iMqttDeliveryToken = publish;
                    handleException(bundle, e);
                    return iMqttDeliveryToken;
                }
            } catch (Exception e2) {
                e = e2;
                handleException(bundle, e);
                return iMqttDeliveryToken;
            }
        } else if (this.myClient == null || (disconnectedBufferOptions = this.bufferOpts) == null || !disconnectedBufferOptions.isBufferEnabled()) {
            Log.i(TAG, "Client is not connected, so not sending message");
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SEND_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return null;
        } else {
            try {
                publish = this.myClient.publish(str, mqttMessage, (Object) str2, (IMqttActionListener) new MqttConnectionListener(bundle));
                try {
                    storeSendDetails(str, mqttMessage, publish, str2, str3);
                } catch (Exception e3) {
                    e = e3;
                    iMqttDeliveryToken = publish;
                }
            } catch (Exception e4) {
                e = e4;
                handleException(bundle, e);
                return iMqttDeliveryToken;
            }
        }
        return publish;
    }

    public void subscribe(String str, int i, String str2, String str3) {
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "subscribe({" + str + "}," + i + ",{" + str2 + "}, {" + str3 + "}");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SUBSCRIBE_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str3);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str2);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SUBSCRIBE_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return;
        }
        try {
            this.myClient.subscribe(str, i, (Object) str2, (IMqttActionListener) new MqttConnectionListener(bundle));
        } catch (Exception e) {
            handleException(bundle, e);
        }
    }

    public void subscribe(String[] strArr, int[] iArr, String str, String str2) {
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "subscribe({" + Arrays.toString(strArr) + "}," + Arrays.toString(iArr) + ",{" + str + "}, {" + str2 + "}");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SUBSCRIBE_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str2);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SUBSCRIBE_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return;
        }
        try {
            this.myClient.subscribe(strArr, iArr, (Object) str, (IMqttActionListener) new MqttConnectionListener(bundle));
        } catch (Exception e) {
            handleException(bundle, e);
        }
    }

    public void subscribe(String[] strArr, int[] iArr, String str, String str2, IMqttMessageListener[] iMqttMessageListenerArr) {
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "subscribe({" + Arrays.toString(strArr) + "}," + Arrays.toString(iArr) + ",{" + str + "}, {" + str2 + "}");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SUBSCRIBE_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str2);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SUBSCRIBE_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return;
        }
        new MqttConnectionListener(bundle);
        try {
            this.myClient.subscribe(strArr, iArr, iMqttMessageListenerArr);
        } catch (Exception e) {
            handleException(bundle, e);
        }
    }

    /* access modifiers changed from: package-private */
    public void unsubscribe(String str, String str2, String str3) {
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "unsubscribe({" + str + "},{" + str2 + "}, {" + str3 + "})");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.UNSUBSCRIBE_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str3);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str2);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SUBSCRIBE_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return;
        }
        try {
            this.myClient.unsubscribe(str, (Object) str2, (IMqttActionListener) new MqttConnectionListener(bundle));
        } catch (Exception e) {
            handleException(bundle, e);
        }
    }

    /* access modifiers changed from: package-private */
    public void unsubscribe(String[] strArr, String str, String str2) {
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "unsubscribe({" + Arrays.toString(strArr) + "},{" + str + "}, {" + str2 + "})");
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.UNSUBSCRIBE_ACTION);
        bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, str2);
        bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, str);
        MqttAsyncClient mqttAsyncClient = this.myClient;
        if (mqttAsyncClient == null || !mqttAsyncClient.isConnected()) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, NOT_CONNECTED);
            this.service.traceError(MqttServiceConstants.SUBSCRIBE_ACTION, NOT_CONNECTED);
            this.service.callbackToActivity(this.clientHandle, Status.ERROR, bundle);
            return;
        }
        try {
            this.myClient.unsubscribe(strArr, (Object) str, (IMqttActionListener) new MqttConnectionListener(bundle));
        } catch (Exception e) {
            handleException(bundle, e);
        }
    }

    public IMqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.myClient.getPendingDeliveryTokens();
    }

    public void connectionLost(Throwable th) {
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "connectionLost(" + th.getMessage() + ")");
        this.disconnected = true;
        try {
            if (!this.connectOptions.isAutomaticReconnect()) {
                this.myClient.disconnect((Object) null, new IMqttActionListener() {
                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    }

                    public void onSuccess(IMqttToken iMqttToken) {
                    }
                });
            } else {
                this.alarmPingSender.schedule(100);
            }
        } catch (Exception unused) {
        }
        Bundle bundle = new Bundle();
        bundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.ON_CONNECTION_LOST_ACTION);
        if (th != null) {
            bundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, th.getMessage());
            if (th instanceof MqttException) {
                bundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, th);
            }
            bundle.putString(MqttServiceConstants.CALLBACK_EXCEPTION_STACK, Log.getStackTraceString(th));
        }
        this.service.callbackToActivity(this.clientHandle, Status.OK, bundle);
        releaseWakeLock();
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "deliveryComplete(" + iMqttDeliveryToken + ")");
        MqttMessage remove = this.savedSentMessages.remove(iMqttDeliveryToken);
        if (remove != null) {
            String remove2 = this.savedActivityTokens.remove(iMqttDeliveryToken);
            String remove3 = this.savedInvocationContexts.remove(iMqttDeliveryToken);
            Bundle messageToBundle = messageToBundle((String) null, this.savedTopics.remove(iMqttDeliveryToken), remove);
            if (remove2 != null) {
                messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.SEND_ACTION);
                messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, remove2);
                messageToBundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, remove3);
                this.service.callbackToActivity(this.clientHandle, Status.OK, messageToBundle);
            }
            messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.MESSAGE_DELIVERED_ACTION);
            this.service.callbackToActivity(this.clientHandle, Status.OK, messageToBundle);
        }
    }

    public void messageArrived(String str, MqttMessage mqttMessage) throws Exception {
        MqttService mqttService = this.service;
        mqttService.traceDebug(TAG, "messageArrived(" + str + ",{" + mqttMessage.toString() + "})");
        String storeArrived = this.service.messageStore.storeArrived(this.clientHandle, str, mqttMessage);
        Bundle messageToBundle = messageToBundle(storeArrived, str, mqttMessage);
        messageToBundle.putString(MqttServiceConstants.CALLBACK_ACTION, MqttServiceConstants.MESSAGE_ARRIVED_ACTION);
        messageToBundle.putString(MqttServiceConstants.CALLBACK_MESSAGE_ID, storeArrived);
        this.service.callbackToActivity(this.clientHandle, Status.OK, messageToBundle);
    }

    private void storeSendDetails(String str, MqttMessage mqttMessage, IMqttDeliveryToken iMqttDeliveryToken, String str2, String str3) {
        this.savedTopics.put(iMqttDeliveryToken, str);
        this.savedSentMessages.put(iMqttDeliveryToken, mqttMessage);
        this.savedActivityTokens.put(iMqttDeliveryToken, str3);
        this.savedInvocationContexts.put(iMqttDeliveryToken, str2);
    }

    private void acquireWakeLock() {
        if (this.wakelock == null) {
            this.wakelock = ((PowerManager) this.service.getSystemService("power")).newWakeLock(1, this.wakeLockTag);
        }
        this.wakelock.acquire();
    }

    private void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.wakelock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.wakelock.release();
        }
    }

    private class MqttConnectionListener implements IMqttActionListener {
        private final Bundle resultBundle;

        private MqttConnectionListener(Bundle bundle) {
            this.resultBundle = bundle;
        }

        public void onSuccess(IMqttToken iMqttToken) {
            MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.OK, this.resultBundle);
        }

        public void onFailure(IMqttToken iMqttToken, Throwable th) {
            this.resultBundle.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, th.getLocalizedMessage());
            this.resultBundle.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, th);
            MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.ERROR, this.resultBundle);
        }
    }

    /* access modifiers changed from: package-private */
    public void offline() {
        if (!this.disconnected && !this.cleanSession) {
            connectionLost(new Exception("Android offline"));
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void reconnect() {
        if (this.myClient == null) {
            this.service.traceError(TAG, "Reconnect myClient = null. Will not do reconnect");
        } else if (this.isConnecting) {
            this.service.traceDebug(TAG, "The client is connecting. Reconnect return directly.");
        } else if (!this.service.isOnline()) {
            this.service.traceDebug(TAG, "The network is not reachable. Will not do reconnect");
        } else if (this.connectOptions.isAutomaticReconnect()) {
            Log.i(TAG, "Requesting Automatic reconnect using New Java AC");
            Bundle bundle = new Bundle();
            bundle.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, this.reconnectActivityToken);
            bundle.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, (String) null);
            bundle.putString(MqttServiceConstants.CALLBACK_ACTION, "connect");
            try {
                this.myClient.reconnect();
            } catch (MqttException e) {
                Log.e(TAG, "Exception occurred attempting to reconnect: " + e.getMessage());
                setConnectingState(false);
                handleException(bundle, e);
            }
        } else if (this.disconnected && !this.cleanSession) {
            this.service.traceDebug(TAG, "Do Real Reconnect!");
            final Bundle bundle2 = new Bundle();
            bundle2.putString(MqttServiceConstants.CALLBACK_ACTIVITY_TOKEN, this.reconnectActivityToken);
            bundle2.putString(MqttServiceConstants.CALLBACK_INVOCATION_CONTEXT, (String) null);
            bundle2.putString(MqttServiceConstants.CALLBACK_ACTION, "connect");
            try {
                this.myClient.connect(this.connectOptions, (Object) null, new MqttConnectionListener(bundle2) {
                    public void onSuccess(IMqttToken iMqttToken) {
                        MqttConnection.this.service.traceDebug(MqttConnection.TAG, "Reconnect Success!");
                        MqttConnection.this.service.traceDebug(MqttConnection.TAG, "DeliverBacklog when reconnect.");
                        MqttConnection.this.doAfterConnectSuccess(bundle2);
                    }

                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        bundle2.putString(MqttServiceConstants.CALLBACK_ERROR_MESSAGE, th.getLocalizedMessage());
                        bundle2.putSerializable(MqttServiceConstants.CALLBACK_EXCEPTION, th);
                        MqttConnection.this.service.callbackToActivity(MqttConnection.this.clientHandle, Status.ERROR, bundle2);
                        MqttConnection.this.doAfterConnectFail(bundle2);
                    }
                });
                setConnectingState(true);
            } catch (MqttException e2) {
                MqttService mqttService = this.service;
                mqttService.traceError(TAG, "Cannot reconnect to remote server." + e2.getMessage());
                setConnectingState(false);
                handleException(bundle2, e2);
            } catch (Exception e3) {
                MqttService mqttService2 = this.service;
                mqttService2.traceError(TAG, "Cannot reconnect to remote server." + e3.getMessage());
                setConnectingState(false);
                handleException(bundle2, new MqttException(6, e3.getCause()));
            }
        }
    }

    private synchronized void setConnectingState(boolean z) {
        this.isConnecting = z;
    }

    public void setBufferOpts(DisconnectedBufferOptions disconnectedBufferOptions) {
        this.bufferOpts = disconnectedBufferOptions;
        this.myClient.setBufferOpts(disconnectedBufferOptions);
    }

    public int getBufferedMessageCount() {
        return this.myClient.getBufferedMessageCount();
    }

    public MqttMessage getBufferedMessage(int i) {
        return this.myClient.getBufferedMessage(i);
    }

    public void deleteBufferedMessage(int i) {
        this.myClient.deleteBufferedMessage(i);
    }
}
