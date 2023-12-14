package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class RadioLostFrame extends Message {
    private short count;

    public short onGetDataLength() {
        return 2;
    }

    public RadioLostFrame() {
        super(13);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.count = (short) (((short) bArr[1]) | (((short) bArr[0]) << 8));
                return;
            }
            throw new MessageFormatException("required data length 2, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        short s = this.count;
        return new byte[]{(byte) (s >> 8), (byte) (s & 255)};
    }

    public short getCount() {
        return this.count;
    }

    public void setCount(short s) {
        this.count = s;
    }
}
