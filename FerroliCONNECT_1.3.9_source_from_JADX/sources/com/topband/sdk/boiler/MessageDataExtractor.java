package com.topband.sdk.boiler;

import com.topband.sdk.boiler.util.BinaryUtils;
import java.util.ArrayList;
import java.util.List;

class MessageDataExtractor {
    MessageDataExtractor() {
    }

    public static List<byte[]> extractMessageData(byte[] bArr) throws PacketFormatException, MessageFormatException {
        if (bArr == null) {
            return null;
        }
        if (bArr.length >= 7) {
            int byte2int = 3 + BinaryUtils.byte2int(bArr[2]);
            ArrayList arrayList = new ArrayList();
            while (byte2int < bArr.length - 1) {
                try {
                    int i = byte2int + 2;
                    short bytes2shortHtL = BinaryUtils.bytes2shortHtL(bArr[i], bArr[i + 1]);
                    int i2 = 4 + bytes2shortHtL;
                    if (bArr.length >= byte2int + i2) {
                        byte[] bArr2 = new byte[i2];
                        System.arraycopy(bArr, byte2int, bArr2, 0, i2);
                        arrayList.add(bArr2);
                        byte2int += bytes2shortHtL + 4;
                    } else {
                        byte[] bArr3 = new byte[byte2int];
                        System.arraycopy(bArr, 0, bArr3, 0, byte2int);
                        byte[] bArr4 = new byte[(bArr.length - byte2int)];
                        System.arraycopy(bArr, byte2int, bArr4, 0, bArr.length - byte2int);
                        throw new MessageFormatException("accepted data : " + BinaryUtils.bytes2String(bArr3) + "; error message data : " + BinaryUtils.bytes2String(bArr4));
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                    throw new MessageFormatException(th.getMessage());
                }
            }
            if (byte2int == bArr.length) {
                return arrayList;
            }
            throw new MessageFormatException("illegal packet data.");
        }
        throw new PacketFormatException("packetData should not short than 7 bytes.");
    }
}
