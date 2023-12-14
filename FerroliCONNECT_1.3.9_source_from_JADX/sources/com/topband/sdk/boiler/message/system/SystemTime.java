package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class SystemTime extends Message {
    private int seconds;

    public short onGetDataLength() {
        return 4;
    }

    public SystemTime() {
        super(3);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.seconds = (((short) bArr[3]) & 255) | ((((short) bArr[0]) & 255) << 24) | ((((short) bArr[1]) & 255) << 16) | ((((short) bArr[2]) & 255) << 8);
                return;
            }
            throw new MessageFormatException("required data length 4, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        int i = this.seconds;
        return new byte[]{(byte) (i >> 24), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 0) & 255)};
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int i) {
        this.seconds = i;
    }
}
