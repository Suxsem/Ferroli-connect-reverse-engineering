package com.p107tb.appyunsdk.listener;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* renamed from: com.tb.appyunsdk.listener.IMqttFunctionListener */
public interface IMqttFunctionListener {
    void connectFailed(IMqttToken iMqttToken, Throwable th);

    void connectSuccess(IMqttToken iMqttToken);

    void connectionLost(Throwable th);

    void deviceStatus(String str, String str2, MqttMessage mqttMessage);

    void deviceToAppMsg(String str, String str2, MqttMessage mqttMessage);

    void serverResponse(MqttMessage mqttMessage);

    void serverToAppNotify(MqttMessage mqttMessage);

    void serverToAppNotifyAll(MqttMessage mqttMessage);

    void therListUpdate(String str, String str2, MqttMessage mqttMessage);
}
