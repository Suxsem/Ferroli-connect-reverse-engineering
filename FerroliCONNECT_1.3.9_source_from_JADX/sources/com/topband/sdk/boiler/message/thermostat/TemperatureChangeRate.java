package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class TemperatureChangeRate extends ByteMessage {
    public TemperatureChangeRate() {
        super(Message.TPI_BURN_PERIOD);
    }

    public void setPeriod(int i) {
        setIntData(i);
    }

    public int getPeriod() {
        return getIntData();
    }
}
