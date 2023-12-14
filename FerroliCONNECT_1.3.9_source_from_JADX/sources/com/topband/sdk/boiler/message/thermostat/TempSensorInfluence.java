package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class TempSensorInfluence extends ByteMessage {
    public TempSensorInfluence() {
        super(Message.ROOM_TEMP_SENSOR_INFLUCES);
    }

    public void setValue(int i) {
        setIntData(i * 10);
    }

    public int getValue() {
        return getIntData() / 10;
    }
}
