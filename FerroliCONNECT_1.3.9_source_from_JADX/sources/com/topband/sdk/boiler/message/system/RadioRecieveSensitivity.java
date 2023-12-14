package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class RadioRecieveSensitivity extends ByteMessage {
    public RadioRecieveSensitivity() {
        super(9);
    }

    public void setSensitivity(int i) {
        setIntData(i);
    }

    public int getSensitivity() {
        return getIntData();
    }
}
