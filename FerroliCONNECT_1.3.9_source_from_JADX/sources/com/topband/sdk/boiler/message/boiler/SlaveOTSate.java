package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class SlaveOTSate extends ByteMessage {
    private static final int TYPE_CONNECT = 1;
    private static final int TYPE_DIS_CONNECT = 0;

    public SlaveOTSate() {
        super(Message.OT_SLAVE_STATUS);
    }

    public void setActive(boolean z) {
        if (z) {
            setIntData(1);
        } else {
            setIntData(0);
        }
    }

    public boolean isHeatingModeOn() {
        return (getData() & 2) == 2;
    }

    public boolean isDhwModeOn() {
        return (getData() & 4) == 4;
    }
}
