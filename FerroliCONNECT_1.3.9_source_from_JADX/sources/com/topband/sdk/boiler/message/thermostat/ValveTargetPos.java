package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class ValveTargetPos extends ByteMessage {
    public ValveTargetPos() {
        super(Message.VALVE_TARGET_POS);
    }

    public void setPosition(int i) {
        setIntData(i);
    }

    public int getPosition() {
        return getIntData();
    }
}
