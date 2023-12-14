package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class WiFiSignal extends ByteMessage {
    public WiFiSignal() {
        super(24);
    }

    public void setStrenght(int i) {
        setIntData(i);
    }

    public int getStrenght() {
        return getIntData();
    }
}
