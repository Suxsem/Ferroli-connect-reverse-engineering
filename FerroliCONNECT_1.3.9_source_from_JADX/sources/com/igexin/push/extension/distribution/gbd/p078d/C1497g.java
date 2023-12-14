package com.igexin.push.extension.distribution.gbd.p078d;

import com.igexin.push.extension.distribution.gbd.p069a.p071b.C1453d;
import com.igexin.push.extension.distribution.gbd.p069a.p071b.C1457h;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.p088g.p090b.C1575h;

/* renamed from: com.igexin.push.extension.distribution.gbd.d.g */
class C1497g extends C1575h {

    /* renamed from: a */
    final /* synthetic */ C1494d f2783a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1497g(C1494d dVar, long j) {
        super(j);
        this.f2783a = dVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        try {
            C1540h.m2997b(this.f1613l, "gbd guard task init");
            C1453d.m2573a().mo15003b();
            if (C1490c.f2749c != null) {
                C1490c.f2749c.postDelayed(new C1498h(this), 5000);
            }
            C1457h.m2610a().mo15007b();
            this.f2783a.m2788l();
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
