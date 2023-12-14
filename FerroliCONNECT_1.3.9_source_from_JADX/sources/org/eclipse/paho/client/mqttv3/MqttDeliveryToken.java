package org.eclipse.paho.client.mqttv3;

public class MqttDeliveryToken extends MqttToken implements IMqttDeliveryToken {
    public MqttDeliveryToken() {
    }

    public MqttDeliveryToken(String str) {
        super(str);
    }

    public MqttMessage getMessage() throws MqttException {
        return this.internalTok.getMessage();
    }

    /* access modifiers changed from: protected */
    public void setMessage(MqttMessage mqttMessage) {
        this.internalTok.setMessage(mqttMessage);
    }
}
