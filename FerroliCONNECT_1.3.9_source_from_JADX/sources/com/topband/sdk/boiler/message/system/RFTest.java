package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class RFTest extends Message {
    private byte receiveCount;
    private byte receiveSensitivityIn;
    private byte receiveSensitivitySelf;
    private byte sendCount;

    public short onGetDataLength() {
        return 4;
    }

    public RFTest() {
        super(16);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.sendCount = bArr[0];
                this.receiveCount = bArr[1];
                this.receiveSensitivitySelf = bArr[2];
                this.receiveSensitivityIn = bArr[3];
                return;
            }
            throw new MessageFormatException("required data length 4, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        return new byte[]{this.sendCount, this.receiveCount, this.receiveSensitivitySelf, this.receiveSensitivityIn};
    }

    public byte getSendCount() {
        return this.sendCount;
    }

    public void setSendCount(byte b) {
        this.sendCount = b;
    }

    public byte getReceiveCount() {
        return this.receiveCount;
    }

    public void setReceiveCount(byte b) {
        this.receiveCount = b;
    }

    public byte getReceiveSensitivitySelf() {
        return this.receiveSensitivitySelf;
    }

    public void setReceiveSensitivitySelf(byte b) {
        this.receiveSensitivitySelf = b;
    }

    public byte getReceiveSensitivityIn() {
        return this.receiveSensitivityIn;
    }

    public void setReceiveSensitivityIn(byte b) {
        this.receiveSensitivityIn = b;
    }
}
