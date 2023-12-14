package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class HeatAllow extends ByteMessage {
    private static final int STATE_ALLOW = 1;
    private static final int STATE_FORBID = 0;

    public HeatAllow() {
        super(Message.ROOM_ALLOW_HEAT);
    }

    public boolean isEnable() {
        return getIntData() == 1;
    }

    public void setEnable(boolean z) {
        if (z) {
            setIntData(1);
        } else {
            setIntData(0);
        }
    }
}
