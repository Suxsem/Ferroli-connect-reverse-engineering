package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class Reset extends Message {
    public byte[] onGetData() {
        return null;
    }

    public short onGetDataLength() {
        return 0;
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr == null) {
        }
    }

    public Reset() {
        super(20);
    }
}
