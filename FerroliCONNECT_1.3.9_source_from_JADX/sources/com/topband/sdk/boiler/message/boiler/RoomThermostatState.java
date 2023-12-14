package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class RoomThermostatState extends ByteMessage {
    private static final int FLAGE_ACTIVE = 1;
    private static final int FLAGE_NO_ACTIVE = 0;

    public RoomThermostatState() {
        super(Message.FLAG_IS_THERMOSTAT_ACTIVE);
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
