package com.topband.sdk.boiler.message.system;

import com.topband.sdk.boiler.message.ByteMessage;

public class WiFiConnectState extends ByteMessage {
    private static final int CONNECT_OK = 2;
    private static final int ROUTER_NO_CONNECT = 0;
    private static final int SERVER_NO_CONNECT = 1;

    public WiFiConnectState() {
        super(17);
    }

    private boolean isConnectOK() {
        return getIntData() == 2;
    }

    private boolean isConnectToRouter() {
        return getIntData() != 0;
    }
}
