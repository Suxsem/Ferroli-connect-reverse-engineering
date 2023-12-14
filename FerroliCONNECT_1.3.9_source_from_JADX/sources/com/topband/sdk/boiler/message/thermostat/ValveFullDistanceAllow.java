package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class ValveFullDistanceAllow extends ByteMessage {
    private static final int STATUS_CLOSED = 1;
    private static final int STATUS_OPEN = 0;

    public ValveFullDistanceAllow() {
        super(Message.VALVE_FULL_POS_ALLOW);
    }

    public boolean isAllow() {
        return getIntData() == 1;
    }

    public void setAllow(boolean z) {
        if (z) {
            setIntData(1);
        } else {
            setIntData(0);
        }
    }
}
