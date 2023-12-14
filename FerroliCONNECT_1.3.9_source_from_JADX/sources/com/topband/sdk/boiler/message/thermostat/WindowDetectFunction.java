package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class WindowDetectFunction extends ByteMessage {
    private static final int STATUS_CLOSED = 1;
    private static final int STATUS_OPEN = 0;

    public WindowDetectFunction() {
        super(Message.WINDOW_DETECT);
    }

    public boolean isOpen() {
        return getIntData() == 0;
    }

    public boolean isClosed() {
        return getIntData() == 1;
    }

    public void setOpen() {
        setIntData(0);
    }

    public void setClosed() {
        setIntData(1);
    }
}
