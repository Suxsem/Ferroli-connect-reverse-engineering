package com.igexin.push.extension.distribution.basic.p068j;

import com.igexin.p032b.p033a.p035b.C1177f;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* renamed from: com.igexin.push.extension.distribution.basic.j.m */
public class C1445m {
    /* renamed from: a */
    public static String m2534a(String str) {
        MessageDigest messageDigest;
        byte[] bytes = str.getBytes();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException unused) {
            messageDigest = null;
        }
        if (messageDigest == null) {
            return null;
        }
        messageDigest.update(bytes);
        byte[] digest = messageDigest.digest();
        char[] cArr2 = new char[32];
        int i = 0;
        for (int i2 = 0; i2 < 16; i2++) {
            byte b = digest[i2];
            int i3 = i + 1;
            cArr2[i] = cArr[(b >>> 4) & 15];
            i = i3 + 1;
            cArr2[i3] = cArr[b & 15];
        }
        return new String(cArr2);
    }

    /* renamed from: a */
    public static byte[] m2535a(byte[] bArr) {
        byte[] a;
        if (bArr == null || (a = C1177f.m1332a(bArr)) == null) {
            return null;
        }
        String a2 = m2534a(String.valueOf(System.currentTimeMillis()));
        int length = a.length;
        byte[] bArr2 = new byte[(length + 16)];
        byte[] bytes = a2.substring(0, 8).getBytes();
        byte[] bytes2 = a2.substring(24, 32).getBytes();
        System.arraycopy(bytes, 0, bArr2, 0, 8);
        System.arraycopy(a, 0, bArr2, 8, length);
        System.arraycopy(bytes2, 0, bArr2, length + 8, 8);
        return bArr2;
    }

    /* renamed from: b */
    public static byte[] m2536b(byte[] bArr) {
        if (bArr == null || bArr.length < 16) {
            return null;
        }
        byte[] bArr2 = new byte[(bArr.length - 16)];
        System.arraycopy(bArr, 8, bArr2, 0, bArr.length - 16);
        return C1177f.m1337b(bArr2);
    }
}
