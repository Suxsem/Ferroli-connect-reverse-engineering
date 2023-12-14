package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class AutoTest extends Message {
    private byte MaxNum;
    private byte Reserved;
    private byte ThresholdRxRssi;
    private byte ThresholdSendPower;
    private short receiveCount;
    private short sendCount;
    private byte sendPower;
    private byte ssid;
    private short version;

    public short onGetDataLength() {
        return 12;
    }

    public AutoTest() {
        super(22);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.version = (short) ((((short) bArr[0]) << 8) | ((short) bArr[1]));
                this.sendCount = (short) ((((short) bArr[2]) << 8) | ((short) bArr[3]));
                this.receiveCount = (short) ((((short) bArr[4]) << 8) | ((short) bArr[5]));
                this.ssid = bArr[6];
                this.sendPower = bArr[7];
                this.ThresholdSendPower = bArr[8];
                this.ThresholdRxRssi = bArr[9];
                this.MaxNum = bArr[10];
                this.Reserved = bArr[11];
                return;
            }
            throw new MessageFormatException("required data length 12, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        short s = this.version;
        short s2 = this.sendCount;
        short s3 = this.receiveCount;
        return new byte[]{(byte) (s >> 8), (byte) s, (byte) (s2 >> 8), (byte) s2, (byte) (s3 >> 8), (byte) s3, this.ssid, this.sendPower, this.ThresholdSendPower, this.ThresholdRxRssi, this.MaxNum, this.Reserved};
    }
}
