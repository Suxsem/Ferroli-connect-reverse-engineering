package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.e */
public class C1524e extends C1532b {

    /* renamed from: c */
    private static C1524e f2926c;

    private C1524e() {
        this.f2935b = C1488a.f2621E * 1000;
        this.f2934a = C1490c.f2761o;
    }

    /* renamed from: d */
    public static C1524e m2943d() {
        if (f2926c == null) {
            f2926c = new C1524e();
        }
        return f2926c;
    }

    /* renamed from: a */
    public void mo15163a() {
        C1540h.m2997b("GBD_DGLT", "do Task");
        try {
            if (C1490c.f2749c != null) {
                Message obtain = Message.obtain();
                obtain.what = 6;
                C1490c.f2749c.sendMessage(obtain);
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: a */
    public void mo15164a(long j) {
        this.f2934a = j;
        C1507f.m2840a().mo15133k(j);
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return true;
    }

    /* renamed from: e */
    public void mo15167e() {
        this.f2935b = C1488a.f2621E * 1000;
    }
}
