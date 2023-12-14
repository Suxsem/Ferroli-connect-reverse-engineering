package org.android.agoo.common;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: org.android.agoo.common.a */
/* compiled from: Taobao */
public final class C2353a {

    /* renamed from: a */
    private static byte[] f4063a = {82, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75};

    /* renamed from: b */
    private static ThreadLocal<Cipher> f4064b = new ThreadLocal<>();

    /* renamed from: c */
    private static final AlgorithmParameterSpec f4065c = new IvParameterSpec(f4063a);

    /* renamed from: a */
    public static final byte[] m3903a(byte[] bArr, SecretKeySpec secretKeySpec, byte[] bArr2) throws IllegalArgumentException {
        try {
            return m3901a(secretKeySpec, bArr2, 2).doFinal(bArr);
        } catch (IllegalBlockSizeException e) {
            throw new IllegalArgumentException("AES decrypt error:" + e.getMessage(), e);
        } catch (BadPaddingException e2) {
            throw new IllegalArgumentException("AES decrypt error:" + e2.getMessage(), e2);
        }
    }

    /* renamed from: a */
    private static final Cipher m3901a(SecretKeySpec secretKeySpec, byte[] bArr, int i) {
        Cipher a = m3900a();
        try {
            a.init(i, secretKeySpec, new IvParameterSpec(bArr));
            return a;
        } catch (InvalidKeyException e) {
            throw new RuntimeException("init Chipher error:" + e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new RuntimeException("init Chipher error:" + e2.getMessage(), e2);
        } catch (IllegalArgumentException e3) {
            throw new RuntimeException("init Chipher error:" + e3.getMessage(), e3);
        }
    }

    /* renamed from: a */
    private static final Cipher m3900a() {
        Cipher cipher = f4064b.get();
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                f4064b.set(cipher);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("get Chipher error:" + e.getMessage(), e);
            } catch (NoSuchPaddingException e2) {
                throw new RuntimeException("get Chipher error:" + e2.getMessage(), e2);
            }
        }
        return cipher;
    }

    /* renamed from: a */
    public static final byte[] m3902a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Throwable th) {
            throw new RuntimeException("md5 value Throwable", th);
        }
    }

    /* renamed from: a */
    public static byte[] m3904a(byte[] bArr, byte[] bArr2) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "HmacSHA1");
        try {
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(secretKeySpec);
            return instance.doFinal(bArr2);
        } catch (Throwable th) {
            throw new RuntimeException("HmacSHA1 Throwable", th);
        }
    }
}
