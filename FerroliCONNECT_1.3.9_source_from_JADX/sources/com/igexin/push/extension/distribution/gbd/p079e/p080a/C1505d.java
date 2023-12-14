package com.igexin.push.extension.distribution.gbd.p079e.p080a;

import com.igexin.push.extension.distribution.gbd.p076b.C1482b;
import com.igexin.push.extension.distribution.gbd.p076b.C1486f;
import com.igexin.push.extension.distribution.gbd.p081f.C1515c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import java.util.List;

/* renamed from: com.igexin.push.extension.distribution.gbd.e.a.d */
class C1505d implements C1515c {

    /* renamed from: a */
    final /* synthetic */ C1503b f2897a;

    C1505d(C1503b bVar) {
        this.f2897a = bVar;
    }

    /* renamed from: a */
    public void mo15092a(Object obj) {
        String str;
        try {
            if (obj instanceof C1482b) {
                C1482b bVar = (C1482b) obj;
                boolean a = bVar.mo15042a();
                List<C1486f> b = bVar.mo15043b();
                if (b != null && !b.isEmpty()) {
                    if (a) {
                        str = "instant bir report s.";
                    } else {
                        C1486f fVar = b.get(0);
                        this.f2897a.mo15111b(fVar.mo15073c(), fVar.mo15071b());
                        str = "instant bir report f, insert ral.";
                    }
                    C1540h.m2997b("GBD_RALDataManager", str);
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }
}
