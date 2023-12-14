package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.h */
public class C1527h extends C1532b {

    /* renamed from: c */
    private static C1527h f2929c;

    private C1527h() {
        this.f2935b = C1488a.f2619C * 1000;
    }

    /* renamed from: d */
    public static C1527h m2955d() {
        if (f2929c == null) {
            f2929c = new C1527h();
        }
        return f2929c;
    }

    /* renamed from: a */
    public void mo15163a() {
        C1540h.m2997b("GBD_RGLT", "doTask...");
        if (C1490c.f2749c != null) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.arg1 = 22;
            C1490c.f2749c.sendMessage(obtain);
        }
    }

    /* renamed from: a */
    public void mo15164a(long j) {
        super.mo15164a(j);
        this.f2935b = C1488a.f2619C * 1000;
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return true;
    }
}
