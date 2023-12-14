package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class OTData extends Message {
    private int value;

    public short onGetDataLength() {
        return 4;
    }

    public OTData() {
        super(Message.FLAG_READ_WRITE_OT_DATA);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.value = bArr[3] | (bArr[0] << 24) | (bArr[1] << 16) | (bArr[2] << 8);
                return;
            }
            throw new MessageFormatException("required data length 4, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        int i = this.value;
        return new byte[]{(byte) (i >> 24), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 0) & 255)};
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }
}
