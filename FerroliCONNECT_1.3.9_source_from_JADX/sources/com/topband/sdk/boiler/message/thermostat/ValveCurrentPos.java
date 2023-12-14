package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class ValveCurrentPos extends ByteMessage {
    public ValveCurrentPos() {
        super(Message.VALVE_CURRNET_POS);
    }

    public void setPosition(int i) {
        setIntData(i);
    }

    public int getPosition() {
        return getIntData();
    }
}
