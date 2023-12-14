package com.szacs.ferroliconnect.bean;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TherListUpdateBean {
    private MqttMessage message;

    public TherListUpdateBean(MqttMessage mqttMessage) {
        this.message = mqttMessage;
    }
}
