package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class Channel extends ByteMessage {
    public Channel() {
        super(7);
    }

    public void setChannel(int i) {
        setIntData(i);
    }

    public int getChannel() {
        return getIntData();
    }
}
