package com.p107tb.appyunsdk.listener;

import android.content.Context;
import com.p107tb.appyunsdk.bean.MqttConfigResponse;

/* renamed from: com.tb.appyunsdk.listener.MqttFunctionInterface */
public interface MqttFunctionInterface {
    String getClientId();

    void initMqtt(Context context, MqttConfigResponse mqttConfigResponse, String str, String str2, String str3);

    boolean isMqttConnected();

    boolean isUseSsl();

    void mqttDisconnect();

    void pushLogToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener);

    void sendJsonMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener);

    void sendMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener);

    void sendMsgToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener);

    void setClientId(String str);

    void setForeground(boolean z);

    void setListener(IMqttFunctionListener iMqttFunctionListener);

    void setUseSsl(boolean z);

    void subscribeApp2deviceJson(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);
}
