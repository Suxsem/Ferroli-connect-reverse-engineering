package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class ValveRatio extends ByteMessage {
    public ValveRatio() {
        super(Message.VALVE_OPEN_PERCENT);
    }
}
