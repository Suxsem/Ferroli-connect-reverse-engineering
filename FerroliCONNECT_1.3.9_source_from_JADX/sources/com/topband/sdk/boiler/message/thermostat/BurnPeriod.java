package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class BurnPeriod extends ByteMessage {
    public BurnPeriod() {
        super(Message.TEMPERATURE_CHANGE_RATIO);
    }

    public void setRate(int i) {
        setIntData(i);
    }

    public int getRate() {
        return getIntData();
    }
}
