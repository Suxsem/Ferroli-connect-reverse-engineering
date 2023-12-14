package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class OutTemperaturePayState extends ByteMessage {
    public static final int STATUS_CLOSED = 0;
    public static final int STATUS_OPEN = 1;

    public OutTemperaturePayState() {
        super(Message.FLAG_OUT_TEMP_PAY_STATE);
    }

    public boolean isOpen() {
        return getIntData() == 1;
    }

    public boolean isClosed() {
        return getIntData() == 0;
    }

    public void setOpen() {
        setIntData(1);
    }

    public void setClosed() {
        setIntData(0);
    }
}
