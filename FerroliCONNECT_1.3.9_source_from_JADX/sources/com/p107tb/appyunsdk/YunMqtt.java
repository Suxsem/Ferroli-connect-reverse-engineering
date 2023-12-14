package com.p107tb.appyunsdk;

import android.content.Context;
import android.text.TextUtils;
import com.p107tb.appyunsdk.listener.IYunMqttActionListener;
import com.p107tb.appyunsdk.listener.IYunMqttListener;
import com.p107tb.appyunsdk.utils.YunLog;
import com.p107tb.appyunsdk.utils.YunUtils;
import javax.net.SocketFactory;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import p110io.reactivex.disposables.Disposable;

/* renamed from: com.tb.appyunsdk.YunMqtt */
class YunMqtt {
    private static final String TAG = "YunMqtt";
    private static final int mqttRetryMaxCount = 5;
    private MqttAndroidClient androidClient;
    private boolean autoReconnect;
    private String broker_ip;
    private boolean cleanSession;
    private String client_id;
    /* access modifiers changed from: private */
    public Disposable connectDisposable;
    private Context context;
    private boolean isForeground;
    private int keepAliveInterval;
    private MqttCallback mqttCallback;
    private MqttConnectOptions options;
    private String password;
    private int[] qos;
    private SocketFactory sslSocketFactory;
    private int timeout;
    private String[] topics;
    private String username;
    /* access modifiers changed from: private */
    public IYunMqttListener yunMqttListener;

    private YunMqtt(Builder builder) {
        this.isForeground = true;
        this.mqttCallback = new MqttCallback() {
            public void connectionLost(Throwable th) {
                YunLog.m3843d(YunMqtt.TAG, "mqtt 断开连接 " + th);
                if (YunMqtt.this.yunMqttListener != null) {
                    YunMqtt.this.yunMqttListener.connectionLost(th);
                }
            }

            public void messageArrived(String str, MqttMessage mqttMessage) {
                byte[] payload = mqttMessage.getPayload();
                int qos = mqttMessage.getQos();
                String str2 = new String(payload);
                YunLog.m3843d(YunMqtt.TAG, "消息到达topic: " + str + " ,qos: " + qos + " ,content: " + str2);
                if (YunMqtt.this.yunMqttListener != null) {
                    YunMqtt.this.yunMqttListener.messageArrived(str, mqttMessage);
                }
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                YunLog.m3843d(YunMqtt.TAG, "消息发送完成topic: " + iMqttDeliveryToken);
                if (YunMqtt.this.yunMqttListener != null) {
                    YunMqtt.this.yunMqttListener.deliveryComplete(iMqttDeliveryToken);
                }
            }
        };
        this.username = builder.username;
        this.password = builder.password;
        this.broker_ip = builder.broker_ip;
        this.client_id = builder.client_id;
        this.timeout = builder.timeout;
        this.keepAliveInterval = builder.keepAliveInterval;
        this.cleanSession = builder.cleanSession;
        this.autoReconnect = builder.autoReconnect;
        this.topics = builder.topics;
        this.qos = builder.qos;
        this.context = builder.context;
        this.sslSocketFactory = builder.sslSocketFactory;
        init();
    }

    /* renamed from: com.tb.appyunsdk.YunMqtt$Builder */
    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean autoReconnect = false;
        /* access modifiers changed from: private */
        public String broker_ip;
        /* access modifiers changed from: private */
        public boolean cleanSession = true;
        /* access modifiers changed from: private */
        public String client_id;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public int keepAliveInterval = 60;
        /* access modifiers changed from: private */
        public String password;
        /* access modifiers changed from: private */
        public int[] qos;
        /* access modifiers changed from: private */
        public SocketFactory sslSocketFactory;
        /* access modifiers changed from: private */
        public int timeout = 20;
        /* access modifiers changed from: private */
        public String[] topics;
        /* access modifiers changed from: private */
        public String username;

        public Builder username(String str) {
            this.username = str;
            return this;
        }

        public Builder password(String str) {
            this.password = str;
            return this;
        }

        public Builder broker_ip(String str) {
            this.broker_ip = str;
            return this;
        }

        public Builder client_id(String str) {
            this.client_id = str;
            return this;
        }

        public Builder timeout(int i) {
            this.timeout = i;
            return this;
        }

        public Builder keepAliveInterval(int i) {
            this.keepAliveInterval = i;
            return this;
        }

        public Builder cleanSession(boolean z) {
            this.cleanSession = z;
            return this;
        }

        public Builder autoReconnect(boolean z) {
            this.autoReconnect = z;
            return this;
        }

        public Builder topics(String[] strArr, int[] iArr) {
            this.topics = strArr;
            this.qos = iArr;
            return this;
        }

        public Builder ssl(SocketFactory socketFactory) {
            this.sslSocketFactory = socketFactory;
            return this;
        }

        public YunMqtt build(Context context2) {
            this.context = context2.getApplicationContext();
            return new YunMqtt(this);
        }
    }

    private void init() {
        this.androidClient = new MqttAndroidClient(this.context, this.broker_ip, this.client_id, (MqttClientPersistence) new MemoryPersistence());
        this.androidClient.setCallback(this.mqttCallback);
        this.options = new MqttConnectOptions();
        this.options.setCleanSession(this.cleanSession);
        this.options.setAutomaticReconnect(this.autoReconnect);
        this.options.setConnectionTimeout(this.timeout);
        this.options.setKeepAliveInterval(this.keepAliveInterval);
        SocketFactory socketFactory = this.sslSocketFactory;
        if (socketFactory != null) {
            this.options.setSocketFactory(socketFactory);
        }
        if (!TextUtils.isEmpty(this.username) && !TextUtils.isEmpty(this.password)) {
            this.options.setUserName(this.username);
            this.options.setPassword(this.password.toCharArray());
        }
    }

    public void connect(final IYunMqttListener iYunMqttListener) {
        this.yunMqttListener = iYunMqttListener;
        try {
            this.androidClient.connect(this.options, (Object) null, new IMqttActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    YunLog.m3843d(YunMqtt.TAG, "mqtt 连接成功 " + iMqttToken);
                    YunUtils.cancelDisposable(YunMqtt.this.connectDisposable);
                    IYunMqttListener iYunMqttListener = iYunMqttListener;
                    if (iYunMqttListener != null) {
                        iYunMqttListener.connectSuccess(iMqttToken);
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    YunLog.m3843d(YunMqtt.TAG, "mqtt 连接失败 IMqttToken, " + iMqttToken + " ,Throwable" + th);
                    IYunMqttListener iYunMqttListener = iYunMqttListener;
                    if (iYunMqttListener != null) {
                        iYunMqttListener.connectFailed(iMqttToken, th);
                    }
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        YunUtils.cancelDisposable(this.connectDisposable);
        if (isConnected()) {
            try {
                this.androidClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        if (this.yunMqttListener != null) {
            this.yunMqttListener = null;
        }
    }

    public void subscribe(String[] strArr, int[] iArr) {
        try {
            this.androidClient.subscribe(strArr, iArr);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String str, int i) {
        try {
            this.androidClient.subscribe(str, i);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribe(String str, final IYunMqttActionListener iYunMqttActionListener) {
        try {
            this.androidClient.unsubscribe(str, (Object) null, (IMqttActionListener) new IMqttActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    IYunMqttActionListener iYunMqttActionListener = iYunMqttActionListener;
                    if (iYunMqttActionListener != null) {
                        iYunMqttActionListener.onSuccess(iMqttToken);
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    IYunMqttActionListener iYunMqttActionListener = iYunMqttActionListener;
                    if (iYunMqttActionListener != null) {
                        iYunMqttActionListener.onFailure(iMqttToken, th);
                    }
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            if (iYunMqttActionListener != null) {
                iYunMqttActionListener.onFailure((IMqttToken) null, e);
            }
        }
    }

    public void subscribe(String str, int i, final IYunMqttActionListener iYunMqttActionListener) {
        try {
            this.androidClient.subscribe(str, i, (Object) null, (IMqttActionListener) new IMqttActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    IYunMqttActionListener iYunMqttActionListener = iYunMqttActionListener;
                    if (iYunMqttActionListener != null) {
                        iYunMqttActionListener.onSuccess(iMqttToken);
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    IYunMqttActionListener iYunMqttActionListener = iYunMqttActionListener;
                    if (iYunMqttActionListener != null) {
                        iYunMqttActionListener.onFailure(iMqttToken, th);
                    }
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            if (iYunMqttActionListener != null) {
                iYunMqttActionListener.onFailure((IMqttToken) null, e);
            }
        }
    }

    public void publish(String str, int i, byte[] bArr, final IYunMqttActionListener iYunMqttActionListener) {
        if (isConnected() && YunUtils.isConnectIsNomarl(this.context)) {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setRetained(false);
            mqttMessage.setQos(i);
            mqttMessage.setPayload(bArr);
            try {
                this.androidClient.publish(str, mqttMessage, (Object) null, (IMqttActionListener) new IMqttActionListener() {
                    public void onSuccess(IMqttToken iMqttToken) {
                        IYunMqttActionListener iYunMqttActionListener = iYunMqttActionListener;
                        if (iYunMqttActionListener != null) {
                            iYunMqttActionListener.onSuccess(iMqttToken);
                        }
                    }

                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        IYunMqttActionListener iYunMqttActionListener = iYunMqttActionListener;
                        if (iYunMqttActionListener != null) {
                            iYunMqttActionListener.onFailure(iMqttToken, th);
                        }
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
                if (iYunMqttActionListener != null) {
                    iYunMqttActionListener.onFailure((IMqttToken) null, e);
                }
            }
        }
    }

    public boolean isConnected() {
        try {
            return this.androidClient.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isForeground() {
        return this.isForeground;
    }

    public void setForeground(boolean z) {
        this.isForeground = z;
    }
}
