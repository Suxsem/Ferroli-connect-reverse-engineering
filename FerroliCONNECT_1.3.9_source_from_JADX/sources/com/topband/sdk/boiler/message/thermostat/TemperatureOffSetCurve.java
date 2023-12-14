package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class TemperatureOffSetCurve extends ByteMessage {
    public TemperatureOffSetCurve() {
        super(Message.ROOM_TEMP_CURVE);
    }

    public int getNumber() {
        return getIntData();
    }

    public void setNumber(int i) {
        setIntData(i);
    }
}
