package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class Unit extends ByteMessage {
    private static final int UNIT_CEN = 0;
    private static final int UNIT_FAH = 1;

    public Unit() {
        super(Message.ROOM_TEMP_UNIT);
    }

    public boolean isCentigrade() {
        return getIntData() == 0;
    }

    public void setCen() {
        setIntData(0);
    }

    public void setFah() {
        setIntData(1);
    }
}
