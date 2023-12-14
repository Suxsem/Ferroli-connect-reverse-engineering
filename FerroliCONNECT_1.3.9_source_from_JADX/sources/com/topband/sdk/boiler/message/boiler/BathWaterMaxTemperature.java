package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class BathWaterMaxTemperature extends ByteMessage {
    public BathWaterMaxTemperature() {
        super(Message.FLAG_BATH_WATER_TARGET_TEMPERATURE_MAX);
    }

    public void setValue(int i) {
        setIntData(i);
    }

    public int getValue() {
        return getIntData();
    }
}
