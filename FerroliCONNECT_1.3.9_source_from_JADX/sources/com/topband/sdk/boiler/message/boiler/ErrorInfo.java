package com.topband.sdk.boiler.message.boiler;

import android.util.Log;
import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class ErrorInfo extends ByteMessage {
    private static final int STATUS_CLOSED = 18;
    private static final int STATUS_OPEN = 17;

    public ErrorInfo() {
        super(Message.FLAG_DEVICE_ERROR_INFO);
    }

    public void setOpen() {
        setIntData(17);
    }

    public void setClosed() {
        setIntData(18);
    }

    public char[] getErrorCArr() {
        String binaryString = Integer.toBinaryString(super.getIntData());
        Log.d("ErrorInfo", " 十进制错误信息 = " + super.getIntData());
        int length = 8 - binaryString.length();
        StringBuilder sb = new StringBuilder(binaryString);
        for (int i = 0; i < length; i++) {
            sb.insert(0, "0");
        }
        return sb.toString().toCharArray();
    }
}
