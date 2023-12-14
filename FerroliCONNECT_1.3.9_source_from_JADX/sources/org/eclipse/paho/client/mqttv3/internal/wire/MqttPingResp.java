package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttPingResp extends MqttAck {
    public static final String KEY = "Ping";

    public String getKey() {
        return "Ping";
    }

    public boolean isMessageIdRequired() {
        return false;
    }

    public MqttPingResp(byte b, byte[] bArr) {
        super((byte) 13);
    }

    /* access modifiers changed from: protected */
    public byte[] getVariableHeader() throws MqttException {
        return new byte[0];
    }
}
