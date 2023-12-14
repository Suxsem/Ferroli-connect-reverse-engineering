package com.topband.sdk.boiler.message.thermostat;

import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;
import com.topband.sdk.boiler.util.BinaryUtils;

public class Program extends Message {
    private int[] data = new int[24];
    private byte lenght = 24;
    private byte type;

    public short onGetDataLength() {
        return 26;
    }

    private Program(short s) {
        super(s);
    }

    public static Program newInstance(int i) {
        switch (i) {
            case 0:
                return new Program(Message.TEMP_HEAT_PROGRAM_SUN);
            case 1:
                return new Program(Message.TEMP_HEAT_PROGRAM_MON);
            case 2:
                return new Program(Message.TEMP_HEAT_PROGRAM_TUE);
            case 3:
                return new Program(Message.TEMP_HEAT_PROGRAM_WEB);
            case 4:
                return new Program(Message.TEMP_HEAT_PROGRAM_THUR);
            case 5:
                return new Program(Message.TEMP_HEAT_PROGRAM_FRI);
            case 6:
                return new Program(Message.TEMP_HEAT_PROGRAM_SAT);
            default:
                return new Program(Message.TEMP_HEAT_PROGRAM_SUN);
        }
    }

    public void onParseData(byte[] bArr) throws MessageFormatException {
        if (bArr != null) {
            if (bArr.length == onGetDataLength()) {
                this.type = bArr[bArr.length - 1];
                this.lenght = 24;
                this.data = new int[this.lenght];
                for (int i = 0; i < this.lenght; i++) {
                    this.data[i] = BinaryUtils.byte2int(bArr[i]);
                }
                return;
            }
            throw new MessageFormatException("required data length 26, found : " + bArr.length);
        }
    }

    public byte[] onGetData() {
        byte b = this.lenght;
        byte[] bArr = new byte[(b + 2)];
        bArr[b + 1] = this.type;
        bArr[b] = b;
        for (int i = 0; i < this.lenght; i++) {
            bArr[i] = BinaryUtils.int2byte(this.data[i]);
        }
        return bArr;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte b) {
        this.type = b;
    }

    public byte getLenght() {
        return this.lenght;
    }

    public void setLenght(byte b) {
        this.lenght = b;
    }

    public int[] getData() {
        int[] iArr = new int[48];
        int i = 0;
        while (true) {
            int[] iArr2 = this.data;
            if (i >= iArr2.length) {
                return iArr;
            }
            int i2 = i * 2;
            iArr[i2] = (iArr2[(iArr2.length - 1) - i] >>> 4) & 15;
            iArr[i2 + 1] = iArr2[(iArr2.length - 1) - i] & 15;
            i++;
        }
    }

    public void setData(int[] iArr) {
        if (iArr.length == 48) {
            for (int i = 0; i < this.data.length; i++) {
                int i2 = i * 2;
                int i3 = iArr[i2];
                int i4 = iArr[i2 + 1];
                if (i3 > 2) {
                    i3 = 2;
                }
                if (i4 > 2) {
                    i4 = 2;
                }
                int[] iArr2 = this.data;
                iArr2[(iArr2.length - 1) - i] = (i4 & 15) | ((i3 << 4) & 240);
            }
        } else if (iArr.length == 24) {
            this.data = iArr;
        }
    }
}
