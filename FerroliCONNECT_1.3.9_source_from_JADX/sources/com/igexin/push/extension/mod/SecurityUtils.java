package com.igexin.push.extension.mod;

import android.util.Log;
import com.igexin.p030a.C1133c;
import com.igexin.p030a.C1137g;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1356s;
import com.igexin.sdk.PushConsts;

public class SecurityUtils {

    /* renamed from: a */
    public static final String f2969a = "com.igexin.push.extension.mod.SecurityUtils";

    /* renamed from: b */
    public static boolean f2970b = false;

    /* renamed from: c */
    public static String f2971c = "";

    static {
        try {
            C1179b.m1354a(f2969a + "|load so by system start #######");
            System.loadLibrary("getuiext3");
            f2970b = true;
            C1179b.m1354a(f2969a + "|load so by system success ^_^");
        } catch (UnsatisfiedLinkError e) {
            Log.e(PushConsts.KEY_CLIENT_ID, "load1 so error = " + e.toString());
            C1179b.m1354a(f2969a + "|load so by system error = " + e.toString());
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            sb.append(" + ");
            f2971c = sb.toString();
            C1179b.m1354a(f2969a + "|load so by new start !!");
            if (C1356s.f2214a == null) {
                C1179b.m1354a(f2969a + "|load so by new context = null ~~~~");
                f2970b = false;
                f2971c = e.getMessage();
                return;
            }
            C1133c.m1178a((C1137g) null).mo14172a().mo14180b().mo14177a(C1356s.f2214a, "getuiext3", (String) null, new C1559a());
        } catch (Throwable th) {
            C1179b.m1354a(f2969a + "|load so error not UnsatisfiedLinkError");
            C1179b.m1354a(f2969a + "|load so error e = " + th.toString());
            f2970b = false;
            f2971c += th.toString() + " + " + th.getMessage();
        }
    }

    /* renamed from: a */
    public static native byte[] m3085a();

    /* renamed from: b */
    public static native byte[] m3086b(byte[] bArr, byte[] bArr2, byte[] bArr3);

    /* renamed from: c */
    public static native byte[] m3087c(byte[] bArr, byte[] bArr2, byte[] bArr3);

    /* renamed from: d */
    public static native byte[] m3088d(byte[] bArr);

    /* renamed from: e */
    public static native byte[] m3089e();

    /* renamed from: f */
    public static native byte[] m3090f(byte[] bArr, byte[] bArr2, byte[] bArr3);

    /* renamed from: g */
    public static native byte[] m3091g(byte[] bArr, byte[] bArr2, byte[] bArr3);

    /* renamed from: h */
    public static native byte[] m3092h(byte[] bArr);

    /* renamed from: i */
    public static native byte[] m3093i(byte[] bArr);

    /* renamed from: j */
    public static native byte[] m3094j();

    /* renamed from: k */
    public static native byte[] m3095k();

    /* renamed from: l */
    public static native byte[] m3096l(byte[] bArr, byte[] bArr2);

    /* renamed from: m */
    public static native byte[] m3097m(byte[] bArr, byte[] bArr2);
}
