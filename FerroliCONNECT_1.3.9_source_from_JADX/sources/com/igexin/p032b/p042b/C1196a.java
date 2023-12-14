package com.igexin.p032b.p042b;

import com.igexin.p032b.p033a.p035b.C1177f;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/* renamed from: com.igexin.b.b.a */
public class C1196a {

    /* renamed from: a */
    private static final char[] f1696a = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /* renamed from: a */
    public static String m1434a(int i) {
        if (i < 0) {
            i = 100;
        }
        if (i > 100) {
            i = 10;
        }
        Random random = new Random();
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            char[] cArr2 = f1696a;
            cArr[i2] = cArr2[random.nextInt(cArr2.length)];
        }
        return new String(cArr);
    }

    /* renamed from: a */
    public static String m1435a(String str) {
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
    public static boolean m1436a(String str, String str2) {
        return str == null ? str2 == null : str.equals(str2);
    }

    /* renamed from: a */
    public static byte[] m1437a(byte[] bArr) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException unused) {
            messageDigest = null;
        }
        if (messageDigest == null) {
            return null;
        }
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    /* renamed from: b */
    public static byte[] m1438b(byte[] bArr) {
        byte[] c;
        if (bArr == null || (c = C1177f.m1340c(bArr)) == null) {
            return null;
        }
        String a = m1435a(String.valueOf(System.currentTimeMillis()));
        int length = c.length;
        byte[] bArr2 = new byte[(length + 16)];
        byte[] bytes = a.substring(0, 8).getBytes();
        byte[] bytes2 = a.substring(24, 32).getBytes();
        System.arraycopy(bytes, 0, bArr2, 0, 8);
        System.arraycopy(c, 0, bArr2, 8, length);
        System.arraycopy(bytes2, 0, bArr2, length + 8, 8);
        return bArr2;
    }

    /* renamed from: c */
    public static byte[] m1439c(byte[] bArr) {
        if (bArr == null || bArr.length < 16) {
            return null;
        }
        byte[] bArr2 = new byte[(bArr.length - 16)];
        System.arraycopy(bArr, 8, bArr2, 0, bArr.length - 16);
        return C1177f.m1342d(bArr2);
    }
}
