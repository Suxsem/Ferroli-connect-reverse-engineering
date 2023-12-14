package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;
import kotlin.UByte;

public class Holiday extends Message {
    private byte enable;
    private int endTime;
    private int startTime;

    public short onGetDataLength() {
        return 9;
    }

    public Holiday() {
        super(Message.ROOM_HOLIDAY_DATA);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.enable = bArr[8];
                this.startTime = ((bArr[4] & UByte.MAX_VALUE) << 24) | ((bArr[5] & UByte.MAX_VALUE) << 16) | ((bArr[6] & UByte.MAX_VALUE) << 8) | (bArr[7] & UByte.MAX_VALUE);
                byte b = bArr[0] & UByte.MAX_VALUE;
                byte b2 = bArr[1] & UByte.MAX_VALUE;
                this.endTime = (bArr[3] & UByte.MAX_VALUE) | ((bArr[2] & UByte.MAX_VALUE) << 8) | (b << 24) | (b2 << 16);
                return;
            }
            throw new MessageFormatException("required data length 9, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        byte[] bArr = new byte[9];
        int i = this.startTime;
        bArr[4] = (byte) (i >> 24);
        bArr[5] = (byte) (i >> 16);
        bArr[6] = (byte) (i >> 8);
        bArr[7] = (byte) (i >> 0);
        int i2 = this.endTime;
        bArr[0] = (byte) (i2 >> 24);
        bArr[1] = (byte) (i2 >> 16);
        bArr[2] = (byte) (i2 >> 8);
        bArr[3] = (byte) (i2 >> 0);
        bArr[8] = this.enable;
        return bArr;
    }

    public byte getEnable() {
        return this.enable;
    }

    public void setEnable(byte b) {
        this.enable = b;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int i) {
        this.startTime = i;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int i) {
        this.endTime = i;
    }
}
