package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class RadioNoise extends ByteMessage {
    public RadioNoise() {
        super(10);
    }

    public void setNoise(int i) {
        setIntData(i);
    }

    public int getNoise() {
        return getIntData();
    }
}
