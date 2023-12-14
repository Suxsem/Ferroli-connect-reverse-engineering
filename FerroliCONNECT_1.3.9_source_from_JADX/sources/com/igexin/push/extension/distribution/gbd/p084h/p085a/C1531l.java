package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.l */
public class C1531l extends C1532b {

    /* renamed from: c */
    private static C1531l f2933c;

    private C1531l() {
        this.f2935b = C1488a.f2707m * 1000;
        this.f2934a = C1490c.f2772z;
    }

    /* renamed from: d */
    public static C1531l m2970d() {
        if (f2933c == null) {
            f2933c = new C1531l();
        }
        return f2933c;
    }

    /* renamed from: a */
    public void mo15163a() {
        C1540h.m2997b("GBD_WST", "doTask");
        if (C1490c.f2749c != null) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.arg1 = 11;
            C1490c.f2749c.sendMessage(obtain);
        }
    }

    /* renamed from: a */
    public void mo15164a(long j) {
        this.f2934a = j;
        C1507f.m2840a().mo15125d(j);
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return true;
    }

    /* renamed from: e */
    public void mo15169e() {
        this.f2935b = C1488a.f2707m * 1000;
    }
}
