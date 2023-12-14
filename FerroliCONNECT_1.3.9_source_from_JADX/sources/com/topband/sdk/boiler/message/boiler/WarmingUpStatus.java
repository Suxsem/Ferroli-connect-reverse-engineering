package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class WarmingUpStatus extends ByteMessage {
    private static final int STATUS_CLOSED = 0;
    private static final int STATUS_OPEN = 1;

    public WarmingUpStatus() {
        super(Message.FLAG_WARMING_UP_STATUS);
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
