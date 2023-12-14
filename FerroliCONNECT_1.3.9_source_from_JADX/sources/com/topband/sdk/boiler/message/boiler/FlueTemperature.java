package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class FlueTemperature extends ByteMessage {
    public FlueTemperature() {
        super(Message.FLAG_FLUE_TEMP);
    }

    public void setValue(int i) {
        setIntData(i);
    }

    public int getValue() {
        return getIntData();
    }
}
