package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p081f.C1509a;
import com.igexin.push.extension.distribution.gbd.p081f.p082a.C1511b;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.taobao.accs.common.Constants;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.f */
public class C1525f extends C1532b {

    /* renamed from: c */
    private static C1525f f2927c;

    private C1525f() {
        this.f2935b = Constants.CLIENT_FLUSH_INTERVAL;
        this.f2934a = C1490c.f2756j;
    }

    /* renamed from: d */
    public static C1525f m2948d() {
        if (f2927c == null) {
            f2927c = new C1525f();
        }
        return f2927c;
    }

    /* renamed from: a */
    public void mo15163a() {
        C1540h.m2997b("GBD_GCT", "doTask GBD_CONFIG");
        C1174c.m1310b().mo14317a(new C1509a(new C1511b()), false, true);
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return true;
    }
}
