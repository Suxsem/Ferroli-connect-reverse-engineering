package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p049b.C1276a;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.p088g.C1567b;

/* renamed from: com.igexin.push.core.a.m */
class C1264m extends C1567b {

    /* renamed from: a */
    final /* synthetic */ C1262k f1889a;

    C1264m(C1262k kVar) {
        this.f1889a = kVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14352a() {
        try {
            String b = C1276a.m1778a().mo14499b();
            if (C1343f.f2115G == 0 || !C1196a.m1435a(C1276a.m1778a().mo14501d()).equals(b)) {
                C1179b.m1354a("LoginResultAction|upload app list");
                C1312h.m1937a().mo14666a(System.currentTimeMillis());
                C1276a.m1778a().mo14500c();
            }
        } catch (Throwable unused) {
        }
    }
}
