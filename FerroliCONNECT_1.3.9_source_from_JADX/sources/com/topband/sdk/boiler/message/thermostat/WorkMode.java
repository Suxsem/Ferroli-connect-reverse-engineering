package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class WorkMode extends ByteMessage {
    public static final int MODE_AUTO = 0;
    public static final int MODE_DHW = 5;
    public static final int MODE_MANUAL = 1;
    public static final int MODE_OFF = 4;
    public static final int MODE_OUT = 2;
    public static final int MODE_PARTY = 3;

    public WorkMode() {
        super(Message.ROOM_WORK_MODE);
    }

    public int getMode() {
        return getIntData();
    }

    public void setMode(int i) {
        setIntData(i);
    }
}
