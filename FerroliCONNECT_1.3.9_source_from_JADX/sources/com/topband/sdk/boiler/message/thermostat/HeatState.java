package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class HeatState extends ByteMessage {
    private static final int STATE_HEATING = 1;
    private static final int STATE_STOP = 0;

    public HeatState() {
        super(Message.ROOM_HEAT_STATUS);
    }

    public boolean isHeating() {
        return getIntData() == 1;
    }

    public void setState(int i) {
        setIntData(i);
    }
}
