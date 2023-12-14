package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttUnsubAck extends MqttAck {
    public MqttUnsubAck(byte b, byte[] bArr) throws IOException {
        super(MqttWireMessage.MESSAGE_TYPE_UNSUBACK);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.msgId = dataInputStream.readUnsignedShort();
        dataInputStream.close();
    }

    /* access modifiers changed from: protected */
    public byte[] getVariableHeader() throws MqttException {
        return new byte[0];
    }
}
