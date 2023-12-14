package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.i */
public class C1528i extends C1532b {

    /* renamed from: c */
    private static C1528i f2930c;

    private C1528i() {
        this.f2935b = C1488a.f2620D * 1000;
        this.f2934a = System.currentTimeMillis();
    }

    /* renamed from: d */
    public static C1528i m2959d() {
        if (f2930c == null) {
            f2930c = new C1528i();
        }
        return f2930c;
    }

    /* renamed from: a */
    public void mo15163a() {
        try {
            C1540h.m2997b("GBD_RNALT", "dotask ...");
            if (C1490c.f2749c != null) {
                Message obtain = Message.obtain();
                obtain.what = 4;
                obtain.arg1 = 41;
                C1490c.f2749c.sendMessage(obtain);
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return C1488a.f2628L;
    }
}
