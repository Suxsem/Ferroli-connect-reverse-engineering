package com.p092ta.p093a.p096c;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* renamed from: com.ta.a.c.b */
public class C1976b {

    /* renamed from: a */
    private static final char[] f3160a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: d */
    public static String m3356d(String str) {
        return m3352a(m3357f(), str);
    }

    /* renamed from: a */
    private static String m3352a(String str, String str2) {
        try {
            byte[] a = m3355a(str.getBytes(), str2.getBytes());
            if (a != null) {
                return m3353a(a);
            }
            return "0000000000000000";
        } catch (Exception e) {
            C1982f.m3366a("", e);
            return "0000000000000000";
        }
    }

    /* renamed from: a */
    private static byte[] m3354a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Exception e) {
            C1982f.m3367a("", e, new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    private static String m3353a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(f3160a[(bArr[i] & 240) >>> 4]);
            sb.append(f3160a[bArr[i] & 15]);
        }
        return sb.toString();
    }

    /* renamed from: a */
    private static byte[] m3355a(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException {
        byte[] bArr3 = new byte[64];
        byte[] bArr4 = new byte[64];
        for (int i = 0; i < 64; i++) {
            bArr3[i] = 54;
            bArr4[i] = 92;
        }
        byte[] bArr5 = new byte[64];
        if (bArr.length > 64) {
            bArr = m3353a(bArr);
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr5[i2] = bArr[i2];
        }
        if (bArr.length < 64) {
            for (int length = bArr.length; length < bArr5.length; length++) {
                bArr5[length] = 0;
            }
        }
        byte[] bArr6 = new byte[64];
        for (int i3 = 0; i3 < 64; i3++) {
            bArr6[i3] = (byte) (bArr5[i3] ^ bArr3[i3]);
        }
        byte[] bArr7 = new byte[(bArr6.length + bArr2.length)];
        for (int i4 = 0; i4 < bArr6.length; i4++) {
            bArr7[i4] = bArr6[i4];
        }
        for (int i5 = 0; i5 < bArr2.length; i5++) {
            bArr7[bArr5.length + i5] = bArr2[i5];
        }
        byte[] a = m3353a(bArr7);
        byte[] bArr8 = new byte[64];
        for (int i6 = 0; i6 < 64; i6++) {
            bArr8[i6] = (byte) (bArr5[i6] ^ bArr4[i6]);
        }
        byte[] bArr9 = new byte[(bArr8.length + a.length)];
        for (int i7 = 0; i7 < bArr8.length; i7++) {
            bArr9[i7] = bArr8[i7];
        }
        for (int i8 = 0; i8 < a.length; i8++) {
            bArr9[bArr5.length + i8] = a[i8];
        }
        return m3353a(bArr9);
    }

    /* renamed from: f */
    private static String m3357f() {
        byte[] bytes = "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn".getBytes();
        byte b = 0;
        while (b < 32) {
            try {
                bytes[b] = (byte) (bytes[b] + b);
                b = (byte) (b + 1);
            } catch (Exception unused) {
                return null;
            }
        }
        return m3353a(bytes);
    }
}
