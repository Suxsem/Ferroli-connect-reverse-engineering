package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttPersistable;

public class MqttPersistentData implements MqttPersistable {
    private int hLength = 0;
    private int hOffset = 0;
    private byte[] header = null;
    private String key = null;
    private int pLength = 0;
    private int pOffset = 0;
    private byte[] payload = null;

    public MqttPersistentData(String str, byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        this.key = str;
        this.header = bArr;
        this.hOffset = i;
        this.hLength = i2;
        this.payload = bArr2;
        this.pOffset = i3;
        this.pLength = i4;
    }

    public String getKey() {
        return this.key;
    }

    public byte[] getHeaderBytes() {
        return this.header;
    }

    public int getHeaderLength() {
        return this.hLength;
    }

    public int getHeaderOffset() {
        return this.hOffset;
    }

    public byte[] getPayloadBytes() {
        return this.payload;
    }

    public int getPayloadLength() {
        if (this.payload == null) {
            return 0;
        }
        return this.pLength;
    }

    public int getPayloadOffset() {
        return this.pOffset;
    }
}
