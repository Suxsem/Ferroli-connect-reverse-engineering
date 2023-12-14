package org.eclipse.paho.android.service;

import android.os.Binder;

class MqttServiceBinder extends Binder {
    private String activityToken;
    private MqttService mqttService;

    MqttServiceBinder(MqttService mqttService2) {
        this.mqttService = mqttService2;
    }

    public MqttService getService() {
        return this.mqttService;
    }

    /* access modifiers changed from: package-private */
    public void setActivityToken(String str) {
        this.activityToken = str;
    }

    public String getActivityToken() {
        return this.activityToken;
    }
}
