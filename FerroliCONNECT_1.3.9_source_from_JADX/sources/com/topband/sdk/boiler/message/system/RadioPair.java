package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class RadioPair extends Message {
    private byte channel;
    private byte operation;
    private byte reserved;
    private byte respone;
    private int roomIndex;

    public short onGetDataLength() {
        return 8;
    }

    public RadioPair() {
        super(14);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.channel = bArr[0];
                this.respone = bArr[1];
                this.operation = bArr[2];
                this.reserved = bArr[3];
                this.roomIndex = bArr[7] | (bArr[4] << 24) | (bArr[5] << 16) | (bArr[6] << 8);
                return;
            }
            throw new MessageFormatException("required data length 8, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        int i = this.roomIndex;
        return new byte[]{this.channel, this.respone, this.operation, this.reserved, (byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) (i >> 0)};
    }

    public byte getChannel() {
        return this.channel;
    }

    public void setChannel(byte b) {
        this.channel = b;
    }

    public byte getRespone() {
        return this.respone;
    }

    public void setRespone(byte b) {
        this.respone = b;
    }

    public byte getOperation() {
        return this.operation;
    }

    public void setOperation(byte b) {
        this.operation = b;
    }

    public byte getReserved() {
        return this.reserved;
    }

    public void setReserved(byte b) {
        this.reserved = b;
    }

    public int getRoomIndex() {
        return this.roomIndex;
    }

    public void setRoomIndex(int i) {
        this.roomIndex = i;
    }
}
