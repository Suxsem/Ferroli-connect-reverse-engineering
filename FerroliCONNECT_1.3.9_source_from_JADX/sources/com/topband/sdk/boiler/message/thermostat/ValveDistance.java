package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class ValveDistance extends ByteMessage {
    public ValveDistance() {
        super(Message.VALVE_TARGET_RANGE);
    }

    public void setDistance(int i) {
        setIntData(i);
    }

    public int getDistance() {
        return getIntData();
    }
}
