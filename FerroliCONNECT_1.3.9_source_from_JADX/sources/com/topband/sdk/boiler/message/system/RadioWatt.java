package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class RadioWatt extends ByteMessage {
    public RadioWatt() {
        super(8);
    }

    public void setWatt(int i) {
        setIntData(i);
    }

    public int getWatt() {
        return getIntData();
    }
}
