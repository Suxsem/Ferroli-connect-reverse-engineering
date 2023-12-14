package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.sdk.GTIntentService;
import java.util.concurrent.TimeUnit;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.c */
public class C1522c extends C1575h {

    /* renamed from: a */
    private static C1522c f2923a;

    /* renamed from: b */
    private int f2924b;

    private C1522c() {
        super(GTIntentService.WAIT_TIME);
        this.f1649o = true;
    }

    /* renamed from: i */
    private void m2930i() {
        long j = C1488a.f2676ag * 1000;
        int i = this.f2924b;
        this.f2924b = i + 1;
        if (i < 3) {
            j = GTIntentService.WAIT_TIME;
        }
        mo14294a(j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: i_ */
    public static synchronized C1522c m2931i_() {
        C1522c cVar;
        synchronized (C1522c.class) {
            if (f2923a == null) {
                f2923a = new C1522c();
            }
            cVar = f2923a;
        }
        return cVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        try {
            if (C1490c.f2728E.split(",").length >= 200) {
                C1540h.m2997b("CATT", " CAD length > 200, clean. ");
                C1490c.f2728E = "";
                C1507f.m2840a().mo15116a(C1490c.f2728E);
            }
            C1490c.f2730G = C1541i.m3034i() - C1490c.f2729F;
            if (C1490c.f2730G < 0) {
                C1490c.f2729F = C1541i.m3034i();
                C1490c.f2730G = 0;
            }
            C1507f.m2840a().mo15129g(C1490c.f2729F);
            C1507f.m2840a().mo15130h(C1490c.f2730G);
            m2930i();
            C1540h.m2997b("CATT", "CAD  " + C1490c.f2728E);
            C1540h.m2997b("CATT", "SLST  " + C1490c.f2729F);
            C1540h.m2997b("CATT", "SAT  " + C1490c.f2730G);
        } catch (Throwable unused) {
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return -1;
    }

    /* renamed from: c */
    public void mo14300c() {
        super.mo14300c();
    }

    /* renamed from: d */
    public void mo14221d() {
    }
}
