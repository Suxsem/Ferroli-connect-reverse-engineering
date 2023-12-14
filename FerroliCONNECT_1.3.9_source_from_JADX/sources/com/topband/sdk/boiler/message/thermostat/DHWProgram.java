package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class DHWProgram extends ByteMessage {
    public DHWProgram() {
        super(Message.ROOM_DHW_PROGRAM);
    }
}
