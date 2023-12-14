package com.igexin.push.extension.distribution.basic.p068j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* renamed from: com.igexin.push.extension.distribution.basic.j.b */
public final class C1434b {
    /* renamed from: a */
    public static byte[] m2501a(char[] cArr, int i) {
        int i2;
        if (cArr == null || cArr.length == 0) {
            return null;
        }
        char[] cArr2 = new char[4];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(cArr.length >> 1);
        while (i < cArr.length) {
            int i3 = 0;
            while (i < cArr.length) {
                int i4 = i + 1;
                char c = cArr[i];
                if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c) != -1 || c == '=') {
                    cArr2[i3] = c;
                    i3++;
                } else if (!(c == 13 || c == 10)) {
                    throw new IOException("bad BASE 64 In->");
                }
                if (i3 >= 4) {
                    int i5 = 0;
                    boolean z = false;
                    while (i5 < 4) {
                        if (cArr2[i5] == '=' || !z) {
                            if (!z && cArr2[i5] == '=') {
                                z = true;
                            }
                            i5++;
                        } else {
                            throw new IOException("bad BASE 64 In->");
                        }
                    }
                    if (cArr2[3] != '=') {
                        i2 = 3;
                    } else if (i4 >= cArr.length) {
                        i2 = cArr2[2] == '=' ? 1 : 2;
                    } else {
                        throw new IOException("bad BASE 64 In->");
                    }
                    int i6 = 0;
                    for (int i7 = 0; i7 < 4; i7++) {
                        if (cArr2[i7] != '=') {
                            i6 |= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(cArr2[i7]) << ((3 - i7) * 6);
                        }
                    }
                    for (int i8 = 0; i8 < i2; i8++) {
                        byteArrayOutputStream.write((i6 >>> ((2 - i8) * 8)) & 255);
                    }
                    i = i4;
                } else {
                    i = i4;
                }
            }
            if (i3 <= 0) {
                return byteArrayOutputStream.toByteArray();
            }
            throw new IOException("bad BASE 64 In->");
        }
        byteArrayOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }
}
