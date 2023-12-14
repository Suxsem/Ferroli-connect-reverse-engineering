package com.igexin.push.extension.distribution.gbd.p078d;

import com.igexin.push.extension.distribution.gbd.p084h.p085a.C1525f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.p088g.p090b.C1575h;

/* renamed from: com.igexin.push.extension.distribution.gbd.d.i */
class C1499i extends C1575h {

    /* renamed from: a */
    final /* synthetic */ C1494d f2785a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1499i(C1494d dVar, long j) {
        super(j);
        this.f2785a = dVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        try {
            C1540h.m2997b(this.f1613l, "gbd config task init");
            C1525f d = C1525f.m2948d();
            if (d.mo15166c() && d.mo15165b()) {
                d.mo15164a(System.currentTimeMillis());
                d.mo15163a();
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
