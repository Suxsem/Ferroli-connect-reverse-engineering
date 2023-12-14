package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class HeatMode extends ByteMessage {
    private static final int MODE_NORMAL = 0;
    private static final int MODE_OS = 1;
    private static final int MODE_TPI = 2;

    public HeatMode() {
        super(Message.HEAT_MODE);
    }

    public int getMode() {
        return getIntData();
    }

    public void setMode(int i) {
        setIntData(i);
    }
}
