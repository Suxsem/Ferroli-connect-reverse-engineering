package com.igexin.p032b.p033a.p034a;

import kotlin.UByte;

/* renamed from: com.igexin.b.a.a.a */
public class C1150a {
    /* renamed from: a */
    public static void m1229a(int[] iArr, int i, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }

    /* renamed from: a */
    public static boolean m1230a(byte[] bArr) {
        int length = bArr.length;
        if (length <= 0 || length > 256) {
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
    public static byte[] m1231a(byte[] bArr, String str) {
        return m1234c(bArr, str);
    }

    /* renamed from: a */
    public static byte[] m1232a(byte[] bArr, byte[] bArr2) {
        if (!m1230a(bArr2)) {
            throw new IllegalArgumentException("key is fail!");
        } else if (bArr.length >= 1) {
            int[] iArr = new int[256];
            for (int i = 0; i < iArr.length; i++) {
                iArr[i] = i;
            }
            int i2 = 0;
            for (int i3 = 0; i3 < iArr.length; i3++) {
                i2 = ((i2 + iArr[i3]) + (bArr2[i3 % bArr2.length] & UByte.MAX_VALUE)) % 256;
                m1229a(iArr, i3, i2);
            }
            byte[] bArr3 = new byte[bArr.length];
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < bArr3.length; i6++) {
                i4 = (i4 + 1) % 256;
                i5 = (i5 + iArr[i4]) % 256;
                m1229a(iArr, i4, i5);
                bArr3[i6] = (byte) (iArr[(iArr[i4] + iArr[i5]) % 256] ^ bArr[i6]);
            }
            return bArr3;
        } else {
            throw new IllegalArgumentException("data is fail!");
        }
    }

    /* renamed from: b */
    public static byte[] m1233b(byte[] bArr, String str) {
        return m1235d(bArr, str);
    }

    /* renamed from: c */
    public static byte[] m1234c(byte[] bArr, String str) {
        return m1232a(bArr, str.getBytes());
    }

    /* renamed from: d */
    public static byte[] m1235d(byte[] bArr, String str) {
        return m1232a(bArr, str.getBytes());
    }
}
