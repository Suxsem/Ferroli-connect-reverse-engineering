package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class WinterSummerMode extends ByteMessage {
    public static final int MODE_OFF = 2;
    public static final int MODE_SUMMER = 0;
    public static final int MODE_WINTER = 1;

    public WinterSummerMode() {
        super(Message.FLAG_WINTER_SUMMER_MODE);
    }

    public boolean isWinterMode() {
        return getIntData() == 1;
    }

    public boolean isSummerMode() {
        return getIntData() == 0;
    }

    public void setWinterMode() {
        setIntData(1);
    }

    public void setSummerMode() {
        setIntData(0);
    }

    public int getMode() {
        return getIntData();
    }

    public void setMode(int i) {
        setIntData(i);
    }
}
