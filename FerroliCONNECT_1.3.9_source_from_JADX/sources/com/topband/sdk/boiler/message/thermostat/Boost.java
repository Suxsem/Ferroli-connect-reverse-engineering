package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class Boost extends ByteMessage {
    private static final int STATE_ACTIVE = 1;
    private static final int STATE_INACTIVE = 0;

    public Boost() {
        super(Message.ROOM_BOOST_STATE);
    }

    public boolean isActive() {
        return getIntData() == 1;
    }

    public void setActive(boolean z) {
        if (z) {
            setIntData(1);
        } else {
            setIntData(0);
        }
    }
}
