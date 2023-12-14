package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class BoostEndTime extends ByteMessage {
    public BoostEndTime() {
        super(Message.ROOM_BOOST_END_TIME);
    }
}
