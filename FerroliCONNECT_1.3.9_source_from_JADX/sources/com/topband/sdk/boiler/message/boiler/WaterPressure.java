package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class WaterPressure extends ByteMessage {
    public WaterPressure() {
        super(Message.FLAG_WATER_PRESSURE);
    }

    public float getValue() {
        return ((float) getIntData()) / 10.0f;
    }

    public void setValue(float f) {
        setIntData((int) (f * 10.0f));
    }
}
