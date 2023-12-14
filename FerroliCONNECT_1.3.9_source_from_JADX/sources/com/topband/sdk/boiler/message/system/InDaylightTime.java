package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class InDaylightTime extends ByteMessage {
    private static final int STATUS_CLOSED = 0;
    private static final int STATUS_OPEN = 1;

    public InDaylightTime() {
        super(6);
    }

    public boolean isInDaylightTime() {
        return getIntData() == 1;
    }
}
