package com.igexin.push.extension.distribution.gbd.p086i;

import android.text.TextUtils;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import java.lang.reflect.Method;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.w */
public class C1555w {
    /* renamed from: a */
    public static String m3064a(String str, byte[] bArr) {
        try {
            if (m3072e()) {
                return (String) m3065a("getHttpSignature", (Class<?>[]) new Class[]{String.class, byte[].class}).invoke((Object) null, new Object[]{str, bArr});
            }
            byte[] bytes = str.getBytes("UTF-8");
            byte[] bArr2 = new byte[(bytes.length + bArr.length)];
            int a = C1539g.m2994a(bytes, 0, bArr2, 0, bytes.length);
            if (bArr.length > 0) {
                C1539g.m2994a(bArr, 0, bArr2, a, bArr.length);
            }
            return C1535c.m2989b(C1549q.m3043b(bArr2), 2);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: a */
    private static Method m3065a(String str, Class<?>... clsArr) {
        try {
            return Class.forName("com.igexin.push.util.EncryptUtils").getMethod(str, clsArr);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: a */
    public static void m3066a() {
        C1490c.f2734K = C1533a.m2979a();
    }

    /* renamed from: a */
    public static byte[] m3067a(byte[] bArr, byte[] bArr2) {
        try {
            if (!m3072e()) {
                return C1533a.m2982a(C1490c.f2734K.getBytes("UTF-8"), bArr, bArr2);
            }
            return (byte[]) m3065a("aesEncHttp", (Class<?>[]) new Class[]{byte[].class, byte[].class}).invoke((Object) null, new Object[]{bArr, bArr2});
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: b */
    public static byte[] m3068b() {
        try {
            return m3072e() ? (byte[]) m3065a("getRSAKeyId", (Class<?>[]) new Class[0]).invoke((Object) null, new Object[0]) : "0378965443503246e2e8ff0ab1fd3221".getBytes("UTF-8");
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: b */
    public static byte[] m3069b(byte[] bArr, byte[] bArr2) {
        try {
            if (!m3072e()) {
                return C1533a.m2984b(C1490c.f2734K.getBytes("UTF-8"), bArr, bArr2);
            }
            return (byte[]) m3065a("aesDecHttp", (Class<?>[]) new Class[]{byte[].class, byte[].class}).invoke((Object) null, new Object[]{bArr, bArr2});
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: c */
    public static byte[] m3070c() {
        try {
            if (TextUtils.isEmpty(C1490c.f2734K)) {
                m3066a();
            }
            return C1552t.m3052a(C1533a.m2981a(C1490c.f2734K.getBytes("UTF-8")), C1552t.m3051a(C1535c.m2986a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfHwDefVViKHb6LkTQ3LGK4c5dZESsTAUe/Tf7GlvOIfhl5LjvFcplMztQRIR/vrF1vNzgc/c76fDBlkhg0E6U86PP4JKFbXdJ4n55PfAaYnFprnhLT71vNw1ZURVbd33rYl27nuOmm92YKg+TbL7H+ozzZWMRGTtLnwRig/CdGwIDAQAB".getBytes("UTF-8"), 0)));
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return new byte[0];
        }
    }

    /* renamed from: d */
    public static String m3071d() {
        try {
            if (m3072e()) {
                return (String) m3065a("getHttpGTCV", (Class<?>[]) new Class[0]).invoke((Object) null, new Object[0]);
            }
            byte[] c = m3070c();
            byte[] bytes = C1558z.m3083a(16).getBytes("UTF-8");
            byte[] bArr = new byte[(bytes.length + c.length)];
            C1539g.m2994a(c, 0, bArr, C1539g.m2994a(bytes, 0, bArr, 0, bytes.length), c.length);
            return C1535c.m2989b(bArr, 2);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: e */
    public static boolean m3072e() {
        try {
            Class.forName("com.igexin.push.util.EncryptUtils");
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
