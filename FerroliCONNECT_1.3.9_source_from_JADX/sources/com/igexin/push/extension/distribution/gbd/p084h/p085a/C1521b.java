package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.b */
public class C1521b extends C1532b {

    /* renamed from: c */
    private static C1521b f2922c;

    private C1521b() {
        this.f2935b = C1488a.f2677ah * 1000;
        this.f2934a = C1490c.f2727D;
    }

    /* renamed from: d */
    public static synchronized C1521b m2926d() {
        C1521b bVar;
        synchronized (C1521b.class) {
            if (f2922c == null) {
                f2922c = new C1521b();
            }
            bVar = f2922c;
        }
        return bVar;
    }

    /* renamed from: a */
    public void mo15163a() {
        try {
            C1540h.m2997b("GBD_CAReportTask", "dotask ...");
            if (C1490c.f2749c != null) {
                Message obtain = Message.obtain();
                obtain.what = 17;
                C1490c.f2749c.sendMessage(obtain);
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: a */
    public void mo15164a(long j) {
        this.f2934a = j;
        C1507f.m2840a().mo15128f(j);
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return C1541i.m3031h();
    }
}
