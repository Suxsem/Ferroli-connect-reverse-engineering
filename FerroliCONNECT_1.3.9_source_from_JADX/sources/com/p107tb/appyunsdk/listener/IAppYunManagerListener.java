package com.p107tb.appyunsdk.listener;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* renamed from: com.tb.appyunsdk.listener.IAppYunManagerListener */
public interface IAppYunManagerListener {
    void deviceStatus(String str, String str2, MqttMessage mqttMessage);

    void deviceToAppMsg(String str, String str2, MqttMessage mqttMessage);

    void serverResponse(MqttMessage mqttMessage);

    void serverToAppNotify(MqttMessage mqttMessage);

    void serverToAppNotifyAll(MqttMessage mqttMessage);

    void therListUpdate(String str, String str2, MqttMessage mqttMessage);

    void yunConnectFailed(IMqttToken iMqttToken, Throwable th);

    void yunConnectLost(Throwable th);

    void yunConnectSuccess(IMqttToken iMqttToken);

    void yunPrepareFailure(int i, String str);
}
