package com.igexin.assist.util;

import com.igexin.push.util.C1584i;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.igexin.assist.util.a */
public class C1149a {
    /* renamed from: a */
    public static String m1227a(String str, String str2) {
        try {
            byte[] a = C1584i.m3246a(str, 0);
            SecretKeySpec secretKeySpec = new SecretKeySpec(m1228a(new StringBuilder(str2).reverse().toString().getBytes()), "AES");
            Cipher instance = Cipher.getInstance("AES/CFB128/NoPadding");
            instance.init(2, secretKeySpec, new IvParameterSpec(m1228a("".getBytes())));
            byte[] doFinal = instance.doFinal(a);
            if (doFinal != null) {
                return new String(doFinal);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: a */
    public static byte[] m1228a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
