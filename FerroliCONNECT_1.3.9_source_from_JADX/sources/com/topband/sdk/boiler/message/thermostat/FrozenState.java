package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class FrozenState extends ByteMessage {
    private static final int STATE_DISABLE = 0;
    private static final int STATE_ENABLE = 1;

    public FrozenState() {
        super(Message.FROSTZEN_STATE);
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
