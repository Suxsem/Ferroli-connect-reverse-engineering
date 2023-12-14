package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class FrozenPreventionStatus extends ByteMessage {
    private static final int STATUS_CLOSED = 0;
    private static final int STATUS_OPEN = 1;

    public FrozenPreventionStatus() {
        super(Message.FLAG_FROZEN_PREVENTION_STATUS);
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
