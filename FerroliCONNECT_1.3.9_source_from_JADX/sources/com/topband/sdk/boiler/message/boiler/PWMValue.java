package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class PWMValue extends ByteMessage {
    public PWMValue() {
        super(Message.FLAG_PWM);
    }

    public void set(int i) {
        setIntData(i);
    }

    public int get() {
        return getIntData();
    }
}
