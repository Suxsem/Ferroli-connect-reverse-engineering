package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class FaultCode extends ByteMessage {
    public FaultCode() {
        super(21);
    }

    public void setCode(int i) {
        setIntData(i);
    }

    public int getCode() {
        return getIntData();
    }
}
