package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class DeviceType extends Message {
    private short type;

    public short onGetDataLength() {
        return 2;
    }

    public DeviceType() {
        super(1);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.type = (short) ((int) ((((long) bArr[0]) << 8) | ((long) bArr[1])));
                return;
            }
            throw new MessageFormatException("required data length 2, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        short s = this.type;
        return new byte[]{(byte) (s >> 8), (byte) (s >> 0)};
    }

    public short getType() {
        return this.type;
    }

    public void setType(short s) {
        this.type = s;
    }
}
