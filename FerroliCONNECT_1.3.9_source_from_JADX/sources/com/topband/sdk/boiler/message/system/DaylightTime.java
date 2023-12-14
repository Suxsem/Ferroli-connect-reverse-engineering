package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class DaylightTime extends ByteMessage {
    private static final int STATUS_CLOSED = 0;
    private static final int STATUS_OPEN = 1;

    public DaylightTime() {
        super(5);
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
