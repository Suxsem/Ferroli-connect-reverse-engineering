package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class RecoveryMachineError extends ByteMessage {
    public static final short DEFAULT_ERROR = 170;

    public RecoveryMachineError() {
        super(Message.FLAG_RECOVERY_MACHINE_ERROR);
    }

    public void setErrorCode(int i) {
        setIntData(i);
    }

    public int getErrorCode() {
        return getIntData();
    }
}
