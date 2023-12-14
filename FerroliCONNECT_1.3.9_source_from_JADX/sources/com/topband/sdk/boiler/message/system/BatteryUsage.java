package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class BatteryUsage extends ByteMessage {
    private static final int LOW_BATTERY = 1;

    public BatteryUsage() {
        super(19);
    }

    public void setUsage(int i) {
        setIntData(i);
    }

    public int getUsage() {
        return getIntData();
    }

    public boolean isLowBattery() {
        return getIntData() == 1;
    }
}
