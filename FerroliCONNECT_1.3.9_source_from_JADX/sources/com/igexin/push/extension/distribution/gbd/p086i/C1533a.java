package com.igexin.push.extension.distribution.gbd.p086i;

import android.os.Build;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.a */
public class C1533a {
    /* renamed from: a */
    public static String m2979a() {
        try {
            byte[] bArr = new byte[20];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(bArr);
            return m2983b(bArr);
        } catch (Exception e) {
            C1540h.m2996a(e);
            return null;
        }
    }

    /* renamed from: a */
    private static void m2980a(StringBuffer stringBuffer, byte b) {
        stringBuffer.append("0123456789ABCDEF".charAt((b >> 4) & 15));
        stringBuffer.append("0123456789ABCDEF".charAt(b & 15));
    }

    /* renamed from: a */
    public static byte[] m2981a(byte[] bArr) {
        SecureRandom secureRandom;
        try {
            KeyGenerator instance = KeyGenerator.getInstance("AES");
            if (Build.VERSION.SDK_INT < 28) {
                if (Build.VERSION.SDK_INT >= 24) {
                    secureRandom = SecureRandom.getInstance("SHA1PRNG", new C1534b());
                } else if (Build.VERSION.SDK_INT >= 17) {
                    secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
                }
                secureRandom.setSeed(bArr);
                instance.init(128, secureRandom);
                byte[] encoded = instance.generateKey().getEncoded();
                if (Build.VERSION.SDK_INT >= 28 && C1490c.f2743T == null) {
                    C1490c.f2743T = encoded;
                }
                return encoded;
            } else if (C1490c.f2743T != null) {
                return C1490c.f2743T;
            }
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(bArr);
            instance.init(128, secureRandom);
            byte[] encoded2 = instance.generateKey().getEncoded();
            C1490c.f2743T = encoded2;
            return encoded2;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: a */
    public static byte[] m2982a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(m2981a(bArr), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr3);
            Cipher instance = Cipher.getInstance("AES/CFB/NoPadding");
            instance.init(1, secretKeySpec, ivParameterSpec);
            return instance.doFinal(bArr2);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: b */
    public static String m2983b(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte a : bArr) {
            m2980a(stringBuffer, a);
        }
        return stringBuffer.toString();
    }

    /* renamed from: b */
    public static byte[] m2984b(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(m2981a(bArr), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr3);
            Cipher instance = Cipher.getInstance("AES/CFB/NoPadding");
            instance.init(2, secretKeySpec, ivParameterSpec);
            return instance.doFinal(bArr2);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }
}
