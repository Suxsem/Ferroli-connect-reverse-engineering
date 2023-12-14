package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class FlameStatus extends ByteMessage {
    private static final int STATUS_DEAD = 0;
    private static final int STATUS_FIRING = 1;

    public FlameStatus() {
        super(Message.FLAG_FLAME_STATUS);
    }

    public boolean isFiring() {
        return getIntData() == 1;
    }

    public boolean isDead() {
        return getIntData() == 0;
    }

    public void setFiring() {
        setIntData(1);
    }

    public void setDead() {
        setIntData(0);
    }
}
