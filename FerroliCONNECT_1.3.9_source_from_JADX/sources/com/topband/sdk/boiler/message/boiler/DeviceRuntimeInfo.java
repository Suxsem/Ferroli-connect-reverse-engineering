package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class DeviceRuntimeInfo extends ByteMessage {
    public static final int STATUS_BREAK_DOWN = 3;
    public static final int STATUS_SHUT_DOWN = 0;
    public static final int STATUS_SLEEPING = 1;
    public static final int STATUS_WORKING = 2;

    public DeviceRuntimeInfo() {
        super(Message.FLAG_DEVICE_RUNTIME_INFO);
    }

    public int getDeviceStatus() {
        return getIntData();
    }

    public void setDeviceStatus(int i) {
        setIntData(i);
    }
}
