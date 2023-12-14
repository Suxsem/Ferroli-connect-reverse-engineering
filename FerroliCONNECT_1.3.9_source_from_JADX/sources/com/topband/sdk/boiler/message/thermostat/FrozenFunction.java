package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class FrozenFunction extends ByteMessage {
    private static final int STATE_DISABLE = 0;
    private static final int STATE_ENABLE = 1;

    public FrozenFunction() {
        super(Message.FROSTZEN_ALLOW);
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
