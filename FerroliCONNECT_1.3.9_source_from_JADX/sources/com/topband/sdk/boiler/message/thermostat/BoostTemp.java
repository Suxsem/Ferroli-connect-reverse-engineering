package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class BoostTemp extends ByteMessage {
    public BoostTemp() {
        super(Message.ROOM_BOOST_TEMP);
    }
}
