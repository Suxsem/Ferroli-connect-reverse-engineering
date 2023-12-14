package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttPingReq extends MqttWireMessage {
    public static final String KEY = "Ping";

    public String getKey() {
        return "Ping";
    }

    /* access modifiers changed from: protected */
    public byte getMessageInfo() {
        return 0;
    }

    public boolean isMessageIdRequired() {
        return false;
    }

    public MqttPingReq() {
        super(MqttWireMessage.MESSAGE_TYPE_PINGREQ);
    }

    public MqttPingReq(byte b, byte[] bArr) throws IOException {
        super(MqttWireMessage.MESSAGE_TYPE_PINGREQ);
    }

    /* access modifiers changed from: protected */
    public byte[] getVariableHeader() throws MqttException {
        return new byte[0];
    }
}
