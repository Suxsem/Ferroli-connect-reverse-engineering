package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class OTConnectState extends ByteMessage {
    private static final int TYPE_CONNECT = 1;
    private static final int TYPE_DIS_CONNECT = 0;

    public OTConnectState() {
        super(Message.FLAG_OT_STATE);
    }

    public boolean isConnect() {
        return getIntData() == 1;
    }

    public void setConnect() {
        setIntData(1);
    }

    public void setDisConnect() {
        setIntData(0);
    }
}
