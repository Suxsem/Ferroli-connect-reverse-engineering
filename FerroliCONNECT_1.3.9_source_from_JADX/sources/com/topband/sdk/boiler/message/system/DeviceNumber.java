package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class DeviceNumber extends ByteMessage {
    public DeviceNumber() {
        super(2);
    }

    public int getNumber() {
        return getIntData();
    }

    public void setNumber(int i) {
        setIntData(i);
    }
}
