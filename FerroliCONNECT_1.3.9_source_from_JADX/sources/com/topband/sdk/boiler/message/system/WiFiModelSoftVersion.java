package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;
import com.topband.sdk.boiler.util.BinaryUtils;

public class WiFiModelSoftVersion extends Message {
    private int version;

    public short onGetDataLength() {
        return 4;
    }

    public WiFiModelSoftVersion() {
        super(0);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.version = (int) BinaryUtils.bytes2long(bArr);
                return;
            }
            throw new MessageFormatException("required data length 4, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        int i = this.version;
        return new byte[]{(byte) (i >> 32), (byte) (i >> 16), (byte) (i >> 8), (byte) (i & 255)};
    }

    public int getVersion() {
        return this.version;
    }
}
