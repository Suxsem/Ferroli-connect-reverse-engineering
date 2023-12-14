package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class HeatWaterTempMin extends Message {
    private short value;

    public short onGetDataLength() {
        return 2;
    }

    public HeatWaterTempMin() {
        super(Message.TEMP_DHW_MIN);
    }

    public float getValue() {
        return (float) this.value;
    }

    public void setValue(float f) {
        this.value = (short) ((int) f);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.value = (short) (((short) bArr[1]) | (((short) bArr[0]) << 8));
                return;
            }
            throw new MessageFormatException("required data length 2, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        short s = this.value;
        return new byte[]{(byte) (s >> 8), (byte) (s & 255)};
    }
}
