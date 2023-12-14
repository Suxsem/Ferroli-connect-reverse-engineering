package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class BathWaterMinTemperature extends ByteMessage {
    public BathWaterMinTemperature() {
        super(Message.FLAG_BATH_WATER_TARGET_TEMPERATURE_MIN);
    }

    public void setValue(int i) {
        setIntData(i);
    }

    public int getValue() {
        return getIntData();
    }
}
