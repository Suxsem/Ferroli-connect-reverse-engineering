package com.topband.sdk.boiler;

import java.util.Locale;
import kotlin.UByte;

public class FormatUtil {
    private static boolean CharInRange(char c) {
        boolean z = c >= '0' && c <= '9';
        if (c >= 'a' && c <= 'f') {
            z = true;
        }
        if (c < 'A' || c > 'F') {
            return z;
        }
        return true;
    }

    public static String nullFormat(String str) {
        return str.equals("null") ? "" : str;
    }

    public static byte[] getHexData(String str) {
        int i;
        int length = str.getBytes().length;
        if (length % 2 == 0) {
            i = length / 2;
        } else {
            i = (length / 2) + 1;
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            while (i2 >= 0 && !CharInRange(str.charAt(i2))) {
                i2++;
            }
            int i4 = i2 + 1;
            if (i4 >= length || !CharInRange(str.charAt(i4))) {
                bArr[i3] = StrToByte(String.valueOf(str.charAt(i2)));
            } else {
                bArr[i3] = StrToByte(str.substring(i2, i2 + 2));
            }
            i3++;
            i2 += 2;
        }
        return bArr;
    }

    private static byte StrToByte(String str) {
        return Integer.valueOf(String.valueOf(Integer.parseInt(str, 16))).byteValue();
    }

    public static String bytes2HexString(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            str = str + hexString.toUpperCase(Locale.getDefault());
        }
        return str;
    }
}
