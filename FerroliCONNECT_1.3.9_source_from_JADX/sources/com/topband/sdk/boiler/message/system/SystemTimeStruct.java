package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class SystemTimeStruct extends Message {
    private byte day;
    private byte hour;
    private byte minute;
    private byte month;
    private byte second;
    private byte week;
    private short year;

    public short onGetDataLength() {
        return 8;
    }

    public SystemTimeStruct() {
        super(4);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.year = (short) ((((short) bArr[0]) << 8) | ((short) bArr[1]));
                this.month = bArr[2];
                this.day = bArr[3];
                this.week = bArr[4];
                this.hour = bArr[5];
                this.minute = bArr[6];
                this.second = bArr[7];
                return;
            }
            throw new MessageFormatException("required data length 8, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        short s = this.year;
        return new byte[]{(byte) (s >> 8), (byte) s, this.month, this.day, this.week, this.hour, this.minute, this.second};
    }

    public short getYear() {
        return this.year;
    }

    public void setYear(short s) {
        this.year = s;
    }

    public byte getMonth() {
        return this.month;
    }

    public void setMonth(byte b) {
        this.month = b;
    }

    public byte getDay() {
        return this.day;
    }

    public void setDay(byte b) {
        this.day = b;
    }

    public byte getWeek() {
        return this.week;
    }

    public void setWeek(byte b) {
        this.week = b;
    }

    public byte getHour() {
        return this.hour;
    }

    public void setHour(byte b) {
        this.hour = b;
    }

    public byte getMinute() {
        return this.minute;
    }

    public void setMinute(byte b) {
        this.minute = b;
    }

    public byte getSecond() {
        return this.second;
    }

    public void setSecond(byte b) {
        this.second = b;
    }
}
