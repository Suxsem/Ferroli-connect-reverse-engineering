package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p081f.C1509a;
import com.igexin.push.extension.distribution.gbd.p081f.p082a.C1512c;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.k */
public class C1530k extends C1532b {

    /* renamed from: c */
    private static C1530k f2932c;

    private C1530k() {
        this.f2935b = C1488a.f2683an * 1000;
        this.f2934a = C1490c.f2724A;
    }

    /* renamed from: d */
    public static C1530k m2966d() {
        if (f2932c == null) {
            f2932c = new C1530k();
        }
        return f2932c;
    }

    /* renamed from: a */
    public void mo15163a() {
        try {
            C1540h.m2997b("GBD_UST", "dotask ...");
            C1174c.m1310b().mo14317a(new C1509a(new C1512c()), false, true);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: a */
    public void mo15164a(long j) {
        this.f2934a = j;
        C1507f.m2840a().mo15126e(j);
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return true;
    }
}
