package org.eclipse.paho.client.mqttv3.internal.wire;

public abstract class MqttAck extends MqttWireMessage {
    /* access modifiers changed from: protected */
    public byte getMessageInfo() {
        return 0;
    }

    public MqttAck(byte b) {
        super(b);
    }

    public String toString() {
        return String.valueOf(super.toString()) + " msgId " + this.msgId;
    }
}
