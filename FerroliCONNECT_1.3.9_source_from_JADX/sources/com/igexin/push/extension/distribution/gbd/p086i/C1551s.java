package com.igexin.push.extension.distribution.gbd.p086i;

import kotlin.UByte;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.s */
public class C1551s {
    /* renamed from: a */
    public static void m3046a(int[] iArr, int i, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }

    /* renamed from: a */
    public static boolean m3047a(byte[] bArr) {
        if (r0 <= 0 || r0 > 256) {
            return false;
        }
        int i = 0;
        for (byte b : bArr) {
            if ((b & UByte.MAX_VALUE) == 14 && (i = i + 1) > 3) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: a */
    public static byte[] m3048a(byte[] bArr, String str) {
        return m3049a(bArr, str.getBytes());
    }

    /* renamed from: a */
    public static byte[] m3049a(byte[] bArr, byte[] bArr2) {
        if (!m3047a(bArr2)) {
            throw new IllegalArgumentException("key is fail!");
        } else if (bArr.length >= 1) {
            int[] iArr = new int[256];
            for (int i = 0; i < iArr.length; i++) {
                iArr[i] = i;
            }
            int i2 = 0;
            for (int i3 = 0; i3 < iArr.length; i3++) {
                i2 = ((i2 + iArr[i3]) + (bArr2[i3 % bArr2.length] & UByte.MAX_VALUE)) % 256;
                m3046a(iArr, i3, i2);
            }
            byte[] bArr3 = new byte[bArr.length];
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < bArr3.length; i6++) {
                i4 = (i4 + 1) % 256;
                i5 = (i5 + iArr[i4]) % 256;
                m3046a(iArr, i4, i5);
                bArr3[i6] = (byte) (iArr[(iArr[i4] + iArr[i5]) % 256] ^ bArr[i6]);
            }
            return bArr3;
        } else {
            throw new IllegalArgumentException("data is fail!");
        }
    }

    /* renamed from: b */
    public static byte[] m3050b(byte[] bArr, String str) {
        return m3049a(bArr, str.getBytes());
    }
}
