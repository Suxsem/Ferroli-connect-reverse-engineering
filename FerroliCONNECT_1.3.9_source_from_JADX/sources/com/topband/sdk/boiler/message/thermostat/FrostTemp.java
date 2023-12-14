package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class FrostTemp extends Message {
    private int value;

    public short onGetDataLength() {
        return 2;
    }

    public FrostTemp() {
        super(Message.ROOM_FROST_TEMP);
    }

    public float getValue() {
        return ((float) this.value) / 10.0f;
    }

    public void setValue(float f) {
        this.value = (int) (f * 10.0f);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.value = byte2int(bArr[0], bArr[1]);
                return;
            }
            throw new MessageFormatException("required data length 2, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        int i = this.value;
        return new byte[]{(byte) (i >> 8), (byte) (i & 255)};
    }
}