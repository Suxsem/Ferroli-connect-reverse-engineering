package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class WarmingWaterMaxTemperature extends ByteMessage {
    public WarmingWaterMaxTemperature() {
        super(Message.FLAG_WARMING_WATER_TARGET_TEMPERATURE_MAX);
    }

    public void setValue(int i) {
        setIntData(i);
    }

    public int getValue() {
        return getIntData();
    }
}
