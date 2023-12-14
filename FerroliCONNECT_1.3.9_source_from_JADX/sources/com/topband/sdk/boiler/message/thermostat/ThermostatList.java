package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class ThermostatList extends Message {
    private byte receiveCount;
    private byte receiveSensitivityIn;
    private byte receiveSensitivitySelf;
    private byte sendCount;

    public short onGetDataLength() {
        return 4;
    }

    public ThermostatList() {
        super(Message.THERMOSTAT_LIST);
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
}
