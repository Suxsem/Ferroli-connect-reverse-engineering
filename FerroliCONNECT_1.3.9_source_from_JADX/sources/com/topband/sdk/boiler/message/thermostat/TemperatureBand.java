package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.message.ByteMessage;

public class TemperatureBand extends ByteMessage {
    public TemperatureBand() {
        super(4148);
    }

    public void setBand(int i) {
        setIntData(i);
    }

    public int getBand() {
        return getIntData();
    }
}
