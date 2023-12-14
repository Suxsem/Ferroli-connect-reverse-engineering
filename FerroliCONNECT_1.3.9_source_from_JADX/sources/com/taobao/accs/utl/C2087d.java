package com.taobao.accs.utl;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

/* renamed from: com.taobao.accs.utl.d */
/* compiled from: Taobao */
public final class C2087d {

    /* renamed from: a */
    private static byte[] f3555a = {82, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75};

    /* renamed from: b */
    private static ThreadLocal<Cipher> f3556b = new ThreadLocal<>();

    /* renamed from: c */
    private static final AlgorithmParameterSpec f3557c = new IvParameterSpec(f3555a);

    /* renamed from: a */
    public static final byte[] m3778a(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Throwable th) {
            throw new RuntimeException("md5 value Throwable", th);
        }
    }
}
