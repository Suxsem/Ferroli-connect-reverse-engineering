package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class ReturnTempOn extends ByteMessage {
    public ReturnTempOn() {
        super(Message.ROOM_TEMP_RETURN_ON);
    }

    public void setValue(int i) {
        setIntData(i * 10);
    }

    public int getValue() {
        return getIntData() / 10;
    }
}
