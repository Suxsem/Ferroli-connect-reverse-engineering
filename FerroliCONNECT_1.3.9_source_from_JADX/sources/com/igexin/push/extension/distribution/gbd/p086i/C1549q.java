package com.igexin.push.extension.distribution.gbd.p086i;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.q */
public class C1549q {
    /* renamed from: a */
    public static byte[] m3042a(byte[] bArr) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            C1540h.m2996a(e);
            messageDigest = null;
        }
        if (messageDigest == null) {
            return null;
        }
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    /* renamed from: b */
    public static byte[] m3043b(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(bArr);
        } catch (Exception e) {
            C1540h.m2996a(e);
            return null;
        }
    }
}
