package com.p107tb.appyunsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.p107tb.appyunsdk.YunMqtt;
import com.p107tb.appyunsdk.bean.MqttConfigResponse;
import com.p107tb.appyunsdk.bean.MqttConfigResponse$TopicBean$_$1Bean;
import com.p107tb.appyunsdk.bean.MqttConfigResponse$TopicBean$_$2Bean;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.p107tb.appyunsdk.listener.IMqttFunctionListener;
import com.p107tb.appyunsdk.listener.IYunMqttActionListener;
import com.p107tb.appyunsdk.listener.IYunMqttListener;
import com.p107tb.appyunsdk.listener.MqttFunctionInterface;
import com.p107tb.appyunsdk.utils.SSLContextGenerator;
import com.p107tb.appyunsdk.utils.YunLog;
import com.p107tb.appyunsdk.utils.YunUtils;
import java.io.InputStream;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* renamed from: com.tb.appyunsdk.MqttService */
class MqttService implements MqttFunctionInterface {
    private static final String SSL_PWD = "201890678330787";
    private static final String TAG = "MqttService";
    @SuppressLint({"StaticFieldLeak"})
    private static volatile MqttService instance;
    private String app_code;
    /* access modifiers changed from: private */
    public String clientId = "";
    private Context context;
    /* access modifiers changed from: private */
    public String dev_realt;
    /* access modifiers changed from: private */
    public String from_dev;
    /* access modifiers changed from: private */
    public IMqttFunctionListener listener;
    private String req_ser;
    /* access modifiers changed from: private */
    public String ser_noti;
    /* access modifiers changed from: private */
    public String ser_noti_allcli;
    /* access modifiers changed from: private */
    public String ser_res;
    /* access modifiers changed from: private */
    public String thermostat_list_update;
    private String to_dev;
    private boolean useSsl = false;
    private String user_slug;
    private YunMqtt yunMqtt;

    public static MqttService getInstance() {
        if (instance == null) {
            synchronized (MqttService.class) {
                if (instance == null) {
                    instance = new MqttService();
                }
            }
        }
        return instance;
    }

    private MqttService() {
    }

    public void setListener(IMqttFunctionListener iMqttFunctionListener) {
        this.listener = iMqttFunctionListener;
    }

    public void initMqtt(Context context2, MqttConfigResponse mqttConfigResponse, String str, String str2, String str3) {
        this.context = context2;
        this.app_code = str2;
        this.user_slug = str;
        String domain = mqttConfigResponse.getDomain();
        int ssl_port = mqttConfigResponse.getSsl_port();
        String host = mqttConfigResponse.getHost();
        int port = mqttConfigResponse.getPort();
        mqttConfigResponse.getWs_port();
        MqttConfigResponse.TopicBean topic = mqttConfigResponse.getTopic();
        MqttConfigResponse$TopicBean$_$1Bean _$1 = topic.get_$1();
        MqttConfigResponse$TopicBean$_$2Bean _$2 = topic.get_$2();
        this.dev_realt = _$1.getDEV_REALT();
        this.from_dev = _$1.getFROM_DEV();
        this.ser_noti = _$1.getSER_NOTI();
        this.ser_noti_allcli = _$1.getSER_NOTI_ALLCLI();
        this.ser_res = _$1.getSER_RES();
        this.req_ser = _$2.getREQ_SER();
        this.to_dev = _$2.getTO_DEV();
        this.thermostat_list_update = "/dev2app/{product_code}/{device_code}/json";
        String str4 = "ssl://" + domain + ":" + ssl_port;
        String str5 = "tcp://" + host + ":" + port;
        if (!this.useSsl) {
            str4 = str5;
        }
        SSLSocketFactory sSLSocketFactory = null;
        if (this.useSsl) {
            sSLSocketFactory = SSLContextGenerator.getSslContext((InputStream) null, context2.getResources().openRawResource(C2137R.raw.trust), SSL_PWD).getSocketFactory();
        }
        String format = String.format("%s$of$%s", new Object[]{str, str2});
        if (this.clientId.equals("")) {
            this.clientId = System.currentTimeMillis() + "_" + format;
        }
        YunLog.m3843d("MqttService", "broker_ip " + str4);
        YunLog.m3843d("MqttService", "mqttUsername " + format);
        YunLog.m3843d("MqttService", "mqttPassword " + str3);
        YunLog.m3843d("MqttService", "clientId  " + this.clientId);
        YunLog.m3843d("MqttService", "--------订阅---------");
        YunLog.m3843d("MqttService", "dev_realt  " + this.dev_realt);
        YunLog.m3843d("MqttService", "from_dev  " + this.from_dev);
        YunLog.m3843d("MqttService", "ser_noti  " + this.ser_noti);
        YunLog.m3843d("MqttService", "ser_noti_allcli  " + this.ser_noti_allcli);
        YunLog.m3843d("MqttService", "ser_res  " + this.ser_res);
        YunLog.m3843d("MqttService", "thermostat_list  " + this.thermostat_list_update);
        YunLog.m3843d("MqttService", "--------发送---------");
        YunLog.m3843d("MqttService", "req_ser  " + this.req_ser);
        YunLog.m3843d("MqttService", "to_dev  " + this.to_dev);
        this.yunMqtt = new YunMqtt.Builder().broker_ip(str4).username(format).password(str3).client_id(this.clientId).cleanSession(false).ssl(sSLSocketFactory).build(context2);
        this.yunMqtt.connect(new IYunMqttListener() {
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }

            public void messageArrived(String str, MqttMessage mqttMessage) {
                YunLog.m3843d("MqttService", "messageArrived " + str + ", " + mqttMessage);
                String[] split = str.split("/");
                String str2 = split[1];
                if (MqttService.this.listener != null) {
                    if (MqttService.this.ser_res.contains(str2)) {
                        MqttService.this.listener.serverResponse(mqttMessage);
                    }
                    if (MqttService.this.ser_noti.contains(str2) && split.length == 5) {
                        MqttService.this.listener.serverToAppNotify(mqttMessage);
                    }
                    if (MqttService.this.ser_noti_allcli.contains(str2) && split.length == 3) {
                        MqttService.this.listener.serverToAppNotifyAll(mqttMessage);
                    }
                    if (MqttService.this.from_dev.contains(str2) && split.length == 5) {
                        MqttService.this.listener.deviceToAppMsg(split[2], split[3], mqttMessage);
                    }
                    if (MqttService.this.dev_realt.contains(str2) && split.length == 4) {
                        MqttService.this.listener.deviceStatus(split[2], split[3], mqttMessage);
                    }
                    if (MqttService.this.thermostat_list_update.contains(str2)) {
                        MqttService.this.listener.therListUpdate(split[2], split[3], mqttMessage);
                    }
                }
            }

            public void connectionLost(Throwable th) {
                if (MqttService.this.listener != null) {
                    MqttService.this.listener.connectionLost(th);
                    String unused = MqttService.this.clientId = "";
                }
            }

            public void connectSuccess(IMqttToken iMqttToken) {
                if (MqttService.this.listener != null) {
                    MqttService.this.listener.connectSuccess(iMqttToken);
                }
            }

            public void connectFailed(IMqttToken iMqttToken, Throwable th) {
                if (MqttService.this.listener != null) {
                    MqttService.this.listener.connectFailed(iMqttToken, th);
                    String unused = MqttService.this.clientId = "";
                }
            }
        });
    }

    public void mqttDisconnect() {
        if (isMqttConnected()) {
            this.yunMqtt.disconnect();
        }
    }

    public boolean isMqttConnected() {
        YunMqtt yunMqtt2 = this.yunMqtt;
        return yunMqtt2 != null && yunMqtt2.isConnected();
    }

    public void subscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.ser_res) && !TextUtils.isEmpty(this.app_code) && !TextUtils.isEmpty(this.user_slug) && !TextUtils.isEmpty(this.clientId)) {
            String str = this.ser_res;
            if (str.contains("{app_code}")) {
                str = str.replace("{app_code}", this.app_code);
            }
            if (str.contains("{user_slug}")) {
                str = str.replace("{user_slug}", this.user_slug);
            }
            if (str.contains("{app_client_id}")) {
                str = str.replace("{app_client_id}", this.clientId);
            }
            subscribeTopic(str, 2, iAppYunManagerActionListener);
        }
    }

    public void subscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.ser_noti) && !TextUtils.isEmpty(this.app_code) && !TextUtils.isEmpty(this.user_slug) && !TextUtils.isEmpty(this.clientId)) {
            String str = this.ser_noti;
            if (str.contains("{app_code}")) {
                str = str.replace("{app_code}", this.app_code);
            }
            if (str.contains("{user_slug}")) {
                str = str.replace("{user_slug}", this.user_slug);
            }
            if (str.contains("{app_client_id}")) {
                str = str.replace("{app_client_id}", this.clientId);
            }
            subscribeTopic(str, 2, iAppYunManagerActionListener);
        }
    }

    public void subscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.ser_noti_allcli) && !TextUtils.isEmpty(this.app_code)) {
            String str = this.ser_noti_allcli;
            if (str.contains("{app_code}")) {
                str = str.replace("{app_code}", this.app_code);
            }
            subscribeTopic(str, 2, iAppYunManagerActionListener);
        }
    }

    public void subscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.from_dev) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(this.clientId)) {
            String str3 = this.from_dev;
            if (str3.contains("{product_code}")) {
                str3 = str3.replace("{product_code}", str);
            }
            if (str3.contains("{device_code}")) {
                str3 = str3.replace("{device_code}", str2);
            }
            if (str3.contains("{app_client_id}")) {
                str3 = str3.replace("{app_client_id}", this.clientId);
            }
            subscribeTopic(str3, 2, iAppYunManagerActionListener);
        }
    }

    public void subscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.dev_realt) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String str3 = this.dev_realt;
            if (str3.contains("{product_code}")) {
                str3 = str3.replace("{product_code}", str);
            }
            if (str3.contains("{device_code}")) {
                str3 = str3.replace("{device_code}", str2);
            }
            subscribeTopic(str3, 2, iAppYunManagerActionListener);
        }
    }

    public void subscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.thermostat_list_update) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String str3 = this.thermostat_list_update;
            if (str3.contains("{product_code}")) {
                str3 = str3.replace("{product_code}", str);
            }
            if (str3.contains("{device_code}")) {
                str3 = str3.replace("{device_code}", str2);
            }
            subscribeTopic(str3, 2, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.ser_res) && !TextUtils.isEmpty(this.app_code) && !TextUtils.isEmpty(this.user_slug) && !TextUtils.isEmpty(this.clientId)) {
            String str = this.ser_res;
            if (str.contains("{app_code}")) {
                str = str.replace("{app_code}", this.app_code);
            }
            if (str.contains("{user_slug}")) {
                str = str.replace("{user_slug}", this.user_slug);
            }
            if (str.contains("{app_client_id}")) {
                str = str.replace("{app_client_id}", this.clientId);
            }
            unsubscribeTopic(str, 0, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.ser_noti) && !TextUtils.isEmpty(this.app_code) && !TextUtils.isEmpty(this.user_slug) && !TextUtils.isEmpty(this.clientId)) {
            String str = this.ser_noti;
            if (str.contains("{app_code}")) {
                str = str.replace("{app_code}", this.app_code);
            }
            if (str.contains("{user_slug}")) {
                str = str.replace("{user_slug}", this.user_slug);
            }
            if (str.contains("{app_client_id}")) {
                str = str.replace("{app_client_id}", this.clientId);
            }
            unsubscribeTopic(str, 0, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.ser_noti_allcli) && !TextUtils.isEmpty(this.app_code)) {
            String str = this.ser_noti_allcli;
            if (str.contains("{app_code}")) {
                str = str.replace("{app_code}", this.app_code);
            }
            unsubscribeTopic(str, 0, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.from_dev) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(this.clientId)) {
            String str3 = this.from_dev;
            if (str3.contains("{product_code}")) {
                str3 = str3.replace("{product_code}", str);
            }
            if (str3.contains("{device_code}")) {
                str3 = str3.replace("{device_code}", str2);
            }
            if (str3.contains("{app_client_id}")) {
                str3 = str3.replace("{app_client_id}", this.clientId);
            }
            unsubscribeTopic(str3, 0, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.dev_realt) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String str3 = this.dev_realt;
            if (str3.contains("{product_code}")) {
                str3 = str3.replace("{product_code}", str);
            }
            if (str3.contains("{device_code}")) {
                str3 = str3.replace("{device_code}", str2);
            }
            unsubscribeTopic(str3, 0, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.thermostat_list_update) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String str3 = this.thermostat_list_update;
            if (str3.contains("{product_code}")) {
                str3 = str3.replace("{product_code}", str);
            }
            if (str3.contains("{device_code}")) {
                str3 = str3.replace("{device_code}", str2);
            }
            unsubscribeTopic(str3, 0, iAppYunManagerActionListener);
        }
    }

    public void sendMsgToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.req_ser) && !TextUtils.isEmpty(this.app_code) && !TextUtils.isEmpty(this.user_slug) && !TextUtils.isEmpty(this.clientId)) {
            String str = this.req_ser;
            if (str.contains("{app_code}")) {
                str = str.replace("{app_code}", this.app_code);
            }
            if (str.contains("{user_slug}")) {
                str = str.replace("{user_slug}", this.user_slug);
            }
            if (str.contains("{app_client_id}")) {
                str = str.replace("{app_client_id}", this.clientId);
            }
            YunLog.m3843d("MqttService", "sendMsgToServer resultTopic" + str + ",msg " + new String(bArr));
            publishTopic(str, 1, bArr, iAppYunManagerActionListener);
        }
    }

    public void sendMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(this.to_dev) && !TextUtils.isEmpty(this.clientId) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String str3 = this.to_dev;
            if (str3.contains("{product_code}")) {
                str3 = str3.replace("{product_code}", str);
            }
            if (str3.contains("{device_code}")) {
                str3 = str3.replace("{device_code}", str2);
            }
            if (str3.contains("{app_client_id}")) {
                str3 = str3.replace("{app_client_id}", this.clientId);
            }
            YunLog.m3843d("MqttService", "sendMsgToDevice resultTopic" + str3 + ",msg " + new String(bArr));
            publishTopic(str3, 1, bArr, iAppYunManagerActionListener);
        }
    }

    public void pushLogToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend("/app_log/{app_code}/{user_slug}/{app_client_id}")) {
            String replace = "/app_log/{app_code}/{user_slug}/{app_client_id}".replace("{app_code}", this.app_code);
            if (replace.contains("{user_slug}")) {
                replace = replace.replace("{user_slug}", this.user_slug);
            }
            if (replace.contains("{app_client_id}")) {
                replace = replace.replace("{app_client_id}", this.clientId);
            }
            YunLog.m3843d("MqttService", "pushLogToServer resultTopic " + replace + ",msg " + new String(bArr));
            publishTopic(replace, 1, bArr, iAppYunManagerActionListener);
        }
    }

    public void sendJsonMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend("/app2dev/{product_code}/{device_code}/json") && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String replace = "/app2dev/{product_code}/{device_code}/json".replace("{product_code}", str);
            if (replace.contains("{device_code}")) {
                replace = replace.replace("{device_code}", str2);
            }
            if (replace.contains("{app_client_id}")) {
                replace = replace.replace("{app_client_id}", this.clientId);
            }
            YunLog.m3843d("MqttService", "sendMsgToDevice resultTopic " + replace + ",msg " + new String(bArr));
            publishTopic(replace, 1, bArr, iAppYunManagerActionListener);
        }
    }

    private void unsubscribeTopic(String str, int i, final IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(str)) {
            this.yunMqtt.unsubscribe(str, new IYunMqttActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    IAppYunManagerActionListener iAppYunManagerActionListener = iAppYunManagerActionListener;
                    if (iAppYunManagerActionListener != null) {
                        iAppYunManagerActionListener.onSuccess(iMqttToken);
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    IAppYunManagerActionListener iAppYunManagerActionListener = iAppYunManagerActionListener;
                    if (iAppYunManagerActionListener != null) {
                        iAppYunManagerActionListener.onFailure(iMqttToken, th);
                    }
                }
            });
        }
    }

    private void subscribeTopic(String str, int i, final IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(str)) {
            this.yunMqtt.subscribe(str, i, new IYunMqttActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    IAppYunManagerActionListener iAppYunManagerActionListener = iAppYunManagerActionListener;
                    if (iAppYunManagerActionListener != null) {
                        iAppYunManagerActionListener.onSuccess(iMqttToken);
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    IAppYunManagerActionListener iAppYunManagerActionListener = iAppYunManagerActionListener;
                    if (iAppYunManagerActionListener != null) {
                        iAppYunManagerActionListener.onFailure(iMqttToken, th);
                    }
                }
            });
        }
    }

    private void publishTopic(String str, int i, byte[] bArr, final IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend(str)) {
            this.yunMqtt.publish(str, i, bArr, new IYunMqttActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    IAppYunManagerActionListener iAppYunManagerActionListener = iAppYunManagerActionListener;
                    if (iAppYunManagerActionListener != null) {
                        iAppYunManagerActionListener.onSuccess(iMqttToken);
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    IAppYunManagerActionListener iAppYunManagerActionListener = iAppYunManagerActionListener;
                    if (iAppYunManagerActionListener != null) {
                        iAppYunManagerActionListener.onFailure(iMqttToken, th);
                    }
                }
            });
        }
    }

    private boolean isTopicCanSend(String str) {
        return isMqttConnected() && YunUtils.isConnectIsNomarl(this.context) && !TextUtils.isEmpty(str);
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String str) {
        this.clientId = str;
    }

    public boolean isUseSsl() {
        return this.useSsl;
    }

    public void setUseSsl(boolean z) {
        this.useSsl = z;
    }

    public void subscribeApp2deviceJson(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        if (isTopicCanSend("/app2dev/{produce_code}/{device_code}/json")) {
            String replace = "/app2dev/{produce_code}/{device_code}/json".replace("{produce_code}", this.app_code);
            if (replace.contains("{device_code}")) {
                replace = replace.replace("{device_code}", this.user_slug);
            }
            YunLog.m3843d("MqttService", "subscribeApp2deviceJson resultTopic " + replace);
            subscribeTopic(replace, 2, iAppYunManagerActionListener);
        }
    }

    public void setForeground(boolean z) {
        if (this.yunMqtt != null) {
            YunLog.m3843d("MqttService", " setForeground = " + z);
            this.yunMqtt.setForeground(z);
        }
    }
}
