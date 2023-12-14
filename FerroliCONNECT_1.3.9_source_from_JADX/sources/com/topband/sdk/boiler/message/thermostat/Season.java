package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class Season extends ByteMessage {
    private static final int SUMMER = 0;
    private static final int WINTER = 1;

    public Season() {
        super(Message.ROOM_SEASON);
    }

    public boolean isWinter() {
        return getIntData() == 1;
    }

    public void setWinterOrSummer(boolean z) {
        if (z) {
            setIntData(1);
        } else {
            setIntData(0);
        }
    }
}
