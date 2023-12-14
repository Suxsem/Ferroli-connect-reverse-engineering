package com.igexin.push.extension.distribution.gbd.p086i;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.t */
public final class C1552t {

    /* renamed from: a */
    private static String f2963a = "RSA";

    /* renamed from: b */
    private static String f2964b = "RSA/NONE/OAEPWithSHA1AndMGF1Padding";

    /* renamed from: a */
    public static PublicKey m3051a(byte[] bArr) {
        try {
            return KeyFactory.getInstance(f2963a).generatePublic(new X509EncodedKeySpec(bArr));
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: a */
    public static byte[] m3052a(byte[] bArr, PublicKey publicKey) {
        try {
            Cipher instance = Cipher.getInstance(f2964b);
            instance.init(1, publicKey);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
