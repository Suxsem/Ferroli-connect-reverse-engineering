package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.j */
public class C1529j extends C1532b {

    /* renamed from: c */
    private static C1529j f2931c;

    private C1529j() {
        this.f2935b = C1488a.f2706l * 1000;
    }

    /* renamed from: d */
    public static C1529j m2962d() {
        if (f2931c == null) {
            f2931c = new C1529j();
        }
        return f2931c;
    }

    /* renamed from: a */
    public void mo15163a() {
        C1540h.m2997b("GBD_RT", "doTask");
        if (C1490c.f2749c != null) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.arg1 = 21;
            C1490c.f2749c.sendMessage(obtain);
        }
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return true;
    }

    /* renamed from: e */
    public void mo15168e() {
        this.f2935b = C1488a.f2706l * 1000;
    }
}
