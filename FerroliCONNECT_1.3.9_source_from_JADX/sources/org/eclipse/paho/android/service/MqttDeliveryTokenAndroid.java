package org.eclipse.paho.android.service;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

class MqttDeliveryTokenAndroid extends MqttTokenAndroid implements IMqttDeliveryToken {
    private MqttMessage message;

    MqttDeliveryTokenAndroid(MqttAndroidClient mqttAndroidClient, Object obj, IMqttActionListener iMqttActionListener, MqttMessage mqttMessage) {
        super(mqttAndroidClient, obj, iMqttActionListener);
        this.message = mqttMessage;
    }

    public MqttMessage getMessage() throws MqttException {
        return this.message;
    }

    /* access modifiers changed from: package-private */
    public void setMessage(MqttMessage mqttMessage) {
        this.message = mqttMessage;
    }

    /* access modifiers changed from: package-private */
    public void notifyDelivery(MqttMessage mqttMessage) {
        this.message = mqttMessage;
        super.notifyComplete();
    }
}
