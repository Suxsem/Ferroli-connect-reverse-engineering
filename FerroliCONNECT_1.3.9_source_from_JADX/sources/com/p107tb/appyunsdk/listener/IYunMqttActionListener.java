package com.p107tb.appyunsdk.listener;

import org.eclipse.paho.client.mqttv3.IMqttToken;

/* renamed from: com.tb.appyunsdk.listener.IYunMqttActionListener */
public interface IYunMqttActionListener {
    void onFailure(IMqttToken iMqttToken, Throwable th);

    void onSuccess(IMqttToken iMqttToken);
}
