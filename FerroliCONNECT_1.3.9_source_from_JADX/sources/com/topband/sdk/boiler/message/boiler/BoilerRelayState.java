package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class BoilerRelayState extends ByteMessage {
    public static final int FLAG_CLOSE = 1;
    public static final int FLAG_CLOSE_ANOTHER = 16;
    public static final int FLAG_OPEN = 2;

    public BoilerRelayState() {
        super(Message.FLAG_BOILER_RELAY_STATE);
    }

    public int getBoilerRelayState() {
        return getIntData();
    }

    public boolean isClosed() {
        return getIntData() == 1 || getIntData() == 16;
    }

    public boolean isOpen() {
        return getIntData() == 2;
    }

    public void setClosed() {
        setIntData(1);
    }

    public void setOpen() {
        setIntData(2);
    }
}
