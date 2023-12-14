package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.g */
public class C1526g extends C1532b {

    /* renamed from: c */
    private static C1526g f2928c;

    private C1526g() {
        this.f2935b = C1488a.f2623G * 1000;
        this.f2934a = C1490c.f2765s;
    }

    /* renamed from: d */
    public static C1526g m2951d() {
        if (f2928c == null) {
            f2928c = new C1526g();
        }
        return f2928c;
    }

    /* renamed from: a */
    public void mo15163a() {
        try {
            C1540h.m2997b("GBD_LFTask", "dotask ...");
            if (C1490c.f2749c != null) {
                Message obtain = Message.obtain();
                obtain.what = 13;
                C1490c.f2749c.sendMessage(obtain);
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: a */
    public void mo15164a(long j) {
        this.f2934a = j;
        C1507f.m2840a().mo15134l(j);
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return C1488a.f2622F;
    }
}
