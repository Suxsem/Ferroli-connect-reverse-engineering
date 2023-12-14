package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class MasterOTStatus extends ByteMessage {
    private static final int TYPE_LIQUEFIED = 0;
    private static final int TYPE_NATURAL = 1;

    public MasterOTStatus() {
        super(Message.OT_MASTER_STATUS);
    }

    public boolean isNaturalGas() {
        return getIntData() == 1;
    }

    public boolean isLiquefiedGas() {
        return getIntData() == 0;
    }

    public void setTypeLiquefied() {
        setIntData(0);
    }

    public void setTypeNatural() {
        setIntData(1);
    }
}
