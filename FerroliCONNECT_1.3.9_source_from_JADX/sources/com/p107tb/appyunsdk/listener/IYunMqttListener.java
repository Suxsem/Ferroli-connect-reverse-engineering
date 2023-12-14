package com.p107tb.appyunsdk.listener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* renamed from: com.tb.appyunsdk.listener.IYunMqttListener */
public interface IYunMqttListener {
    void connectFailed(IMqttToken iMqttToken, Throwable th);

    void connectSuccess(IMqttToken iMqttToken);

    void connectionLost(Throwable th);

    void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken);

    void messageArrived(String str, MqttMessage mqttMessage);
}
