package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;
import kotlin.UByte;

public class BoilerID extends Message {

    /* renamed from: id */
    private int f3615id = 1431677610;

    public short onGetDataLength() {
        return 4;
    }

    public BoilerID() {
        super(Message.BOILER_ID);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.f3615id = ((bArr[3] & UByte.MAX_VALUE) << 0) | ((bArr[0] & UByte.MAX_VALUE) << 24) | ((bArr[1] & UByte.MAX_VALUE) << 16) | ((bArr[2] & UByte.MAX_VALUE) << 8);
                return;
            }
            throw new MessageFormatException("required data length 4, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        int i = this.f3615id;
        return new byte[]{(byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) (i >> 0)};
    }
}
