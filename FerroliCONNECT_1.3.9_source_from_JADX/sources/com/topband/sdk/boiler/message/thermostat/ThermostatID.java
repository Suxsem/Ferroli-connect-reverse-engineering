package com.topband.sdk.boiler.message.thermostat;

import android.util.Log;
import com.topband.sdk.boiler.FormatUtil;
import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;
import java.util.Arrays;

public class ThermostatID extends Message {

    /* renamed from: id */
    private long f3616id;

    public short onGetDataLength() {
        return 4;
    }

    public ThermostatID() {
        super(23);
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.f3616id = Long.parseLong(FormatUtil.bytes2HexString(bArr), 16);
                Log.e("ThermostatActivity", "id: " + this.f3616id);
                return;
            }
            throw new MessageFormatException("required data length 4, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        Log.e("ThermostatActivity", "onGetData: " + Arrays.toString(FormatUtil.getHexData(Long.toHexString(this.f3616id))) + ",id: " + this.f3616id);
        return FormatUtil.getHexData(Long.toHexString(this.f3616id));
    }

    public long getId() {
        return this.f3616id;
    }

    public void setId(long j) {
        this.f3616id = j;
    }
}
