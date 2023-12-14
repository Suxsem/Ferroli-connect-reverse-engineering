package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.C1278a;
import com.igexin.push.core.bean.C1286i;
import com.igexin.push.core.p050c.C1308d;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.p054e.p057c.C1375d;
import com.igexin.push.p087f.C1560a;
import com.igexin.push.p088g.C1567b;

/* renamed from: com.igexin.push.core.a.g */
class C1258g extends C1567b {

    /* renamed from: a */
    final /* synthetic */ C1257f f1883a;

    C1258g(C1257f fVar) {
        this.f1883a = fVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14352a() {
        try {
            C1278a aVar = new C1278a();
            long j = aVar.f1952m;
            String a = C1278a.m1785a(aVar);
            if (a != null) {
                C1179b.m1354a("addphoneinfo");
                C1308d a2 = C1308d.m1924a();
                if (a2 != null) {
                    a2.mo14662a(new C1286i(j, a, (byte) 5, j));
                }
                C1375d dVar = new C1375d();
                dVar.mo14836a();
                dVar.f2270a = (int) j;
                dVar.f2273d = "17258000";
                dVar.f2274e = a;
                dVar.f2276g = C1343f.f2182s;
                C1560a g = C1340e.m2032a().mo14710g();
                g.mo15186a("C-" + C1343f.f2182s, dVar);
                if (!C1196a.m1436a(C1343f.f2189z, C1343f.f2188y)) {
                    C1312h.m1937a().mo14687e(C1343f.f2188y);
                }
            }
        } catch (Throwable unused) {
        }
    }
}
