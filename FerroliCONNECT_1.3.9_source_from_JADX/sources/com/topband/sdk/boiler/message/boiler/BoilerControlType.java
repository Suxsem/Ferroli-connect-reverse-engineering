package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class BoilerControlType extends ByteMessage {
    private static final int TYPE_ONOFF = 0;
    private static final int TYPE_OT = 1;

    public BoilerControlType() {
        super(Message.FLAG_BOILER_CONTROL_TYPE);
    }

    public boolean isOTType() {
        return getIntData() == 1;
    }

    public boolean isOnOffType() {
        return getIntData() == 0;
    }

    public void setTypeOT() {
        setIntData(1);
    }

    public void setTypeONOFF() {
        setIntData(0);
    }
}
