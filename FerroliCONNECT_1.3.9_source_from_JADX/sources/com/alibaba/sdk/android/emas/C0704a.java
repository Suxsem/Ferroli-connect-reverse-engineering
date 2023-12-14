package com.alibaba.sdk.android.emas;

import android.util.Base64;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.alibaba.sdk.android.emas.a */
/* compiled from: AESCrypt */
public final class C0704a {

    /* renamed from: a */
    public static boolean f888a = false;

    /* renamed from: a */
    private static final byte[] f889a = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    /* renamed from: a */
    private static SecretKeySpec m608a(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        byte[] bytes = str.getBytes("UTF-8");
        instance.update(bytes, 0, bytes.length);
        return new SecretKeySpec(instance.digest(), "AES");
    }

    /* renamed from: a */
    public static String m607a(String str, String str2) throws GeneralSecurityException {
        try {
            return Base64.encodeToString(m609a(m608a(str), f889a, str2.getBytes("UTF-8")), 2);
        } catch (UnsupportedEncodingException e) {
            if (!f888a) {
                return null;
            }
            Log.e("AESCrypt", "UnsupportedEncodingException ", e);
            return null;
        }
    }

    /* renamed from: a */
    public static byte[] m609a(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
        instance.init(1, secretKeySpec, new IvParameterSpec(bArr));
        return instance.doFinal(bArr2);
    }

    /* renamed from: b */
    public static String m610b(String str, String str2) throws GeneralSecurityException {
        try {
            return new String(m611b(m608a(str), f889a, Base64.decode(str2, 2)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (!f888a) {
                return null;
            }
            Log.e("AESCrypt", "UnsupportedEncodingException ", e);
            return null;
        }
    }

    /* renamed from: b */
    public static byte[] m611b(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
        instance.init(2, secretKeySpec, new IvParameterSpec(bArr));
        return instance.doFinal(bArr2);
    }
}
