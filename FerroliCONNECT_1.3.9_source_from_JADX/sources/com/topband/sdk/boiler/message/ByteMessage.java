package com.topband.sdk.boiler.message;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;
import com.topband.sdk.boiler.util.BinaryUtils;

public class ByteMessage extends Message {
    byte mData;

    public short onGetDataLength() {
        return 1;
    }

    public ByteMessage(short s) {
        super(s);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr == null) {
            throw new MessageFormatException("can not parse null data");
        } else if (bArr.length == 1) {
            this.mData = bArr[0];
        } else {
            throw new MessageFormatException("Flag: " + getFlag() + " data length should be 1, found : " + bArr.length);
        }
    }

    public byte getData() {
        return this.mData;
    }

    public void setData(byte b) {
        this.mData = b;
    }

    public byte[] onGetData() {
        return BinaryUtils.bytes2array(this.mData);
    }

    /* access modifiers changed from: protected */
    public int getIntData() {
        return BinaryUtils.byte2int(this.mData);
    }

    /* access modifiers changed from: protected */
    public void setIntData(int i) {
        this.mData = BinaryUtils.int2byte(i);
    }
}
