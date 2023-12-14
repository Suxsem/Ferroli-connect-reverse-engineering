package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class DeviceInstallationType extends ByteMessage {
    private static final int TYPE_GROUND = 1;
    private static final int TYPE_WALL = 0;

    public DeviceInstallationType() {
        super(Message.FLAG_DEVICE_INSTALLATION_TYPE);
    }

    public boolean isWallType() {
        return getIntData() == 0;
    }

    public boolean isGroundType() {
        return getIntData() == 1;
    }

    public void setTypeGround() {
        setIntData(1);
    }

    public void setTypeWall() {
        setIntData(0);
    }
}
