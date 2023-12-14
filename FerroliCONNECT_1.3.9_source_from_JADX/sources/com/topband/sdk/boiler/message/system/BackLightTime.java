package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class BackLightTime extends ByteMessage {
    public BackLightTime() {
        super(18);
    }

    public void setSeconds(int i) {
        setIntData(i);
    }

    public int getSeconds() {
        return getIntData();
    }
}
