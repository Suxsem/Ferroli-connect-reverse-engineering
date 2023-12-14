package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class PreStart extends ByteMessage {
    public PreStart() {
        super(Message.HEAT_OPTIMIZE_START);
    }

    public void setValue(int i) {
        setIntData(i);
    }

    public int getValue() {
        return getIntData();
    }
}
