package com.p107tb.appyunsdk.listener;

import org.eclipse.paho.client.mqttv3.IMqttToken;

/* renamed from: com.tb.appyunsdk.listener.IAppYunManagerActionListener */
public interface IAppYunManagerActionListener {
    void onFailure(IMqttToken iMqttToken, Throwable th);

    void onSuccess(IMqttToken iMqttToken);
}
