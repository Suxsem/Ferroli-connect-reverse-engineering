package com.topband.sdk.boiler.message.thermostat;

import android.util.Log;
import com.topband.sdk.boiler.FormatUtil;
import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;

public class ThermostatID2 extends Message {

    /* renamed from: id */
    private String f3617id;

    public short onGetDataLength() {
        return 4;
    }

    public ThermostatID2() {
        super(23);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        Log.i("testThermostatID", "onParseData");
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.f3617id = FormatUtil.bytes2HexString(bArr);
                return;
            }
            throw new MessageFormatException("required data length 4, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        Log.i("testThermostatID", "onGetData");
        return FormatUtil.getHexData(this.f3617id);
    }

    public String getId() {
        return this.f3617id;
    }

    public void setId(String str) {
        this.f3617id = str;
    }
}
