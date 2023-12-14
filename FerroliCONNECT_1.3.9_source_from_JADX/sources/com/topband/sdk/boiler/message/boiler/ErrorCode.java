package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class ErrorCode extends ByteMessage {
    private byte code;

    public ErrorCode() {
        super(Message.FLAG_DEVICE_ERROR);
    }

    public int getCode() {
        return getIntData();
    }

    public void setCode(int i) {
        setIntData(i);
    }
}
