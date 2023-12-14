package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class DeviceFunctionType extends ByteMessage {
    private static final int TYPE_MULTIPLE = 1;
    private static final int TYPE_SIMPLE = 0;

    public DeviceFunctionType() {
        super(Message.FLAG_DEVICE_FUNCTION_TYPE);
    }

    public boolean isSimpleType() {
        return getIntData() == 0;
    }

    public boolean isMultipleType() {
        return getIntData() == 1;
    }

    public void setTypeSimple() {
        setIntData(0);
    }

    public void setTypeMultiple() {
        setIntData(1);
    }
}
