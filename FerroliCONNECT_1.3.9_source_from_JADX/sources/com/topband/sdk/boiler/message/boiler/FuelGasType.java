package com.topband.sdk.boiler.message.boiler;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.message.ByteMessage;

public class FuelGasType extends ByteMessage {
    private static final int TYPE_LIQUEFIED = 0;
    private static final int TYPE_NATURAL = 1;

    public FuelGasType() {
        super(Message.FLAG_FUEL_GAS_TYPE);
    }

    public boolean isNaturalGas() {
        return getIntData() == 1;
    }

    public boolean isLiquefiedGas() {
        return getIntData() == 0;
    }

    public void setTypeLiquefied() {
        setIntData(0);
    }

    public void setTypeNatural() {
        setIntData(1);
    }
}
