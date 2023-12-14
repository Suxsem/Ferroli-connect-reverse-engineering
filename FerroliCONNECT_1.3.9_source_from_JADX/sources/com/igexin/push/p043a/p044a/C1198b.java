package com.igexin.push.p043a.p044a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1355r;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.p088g.p090b.C1571d;

/* renamed from: com.igexin.push.a.a.b */
public class C1198b implements C1571d {

    /* renamed from: a */
    public static final String f1698a = "com.igexin.push.a.a.b";

    /* renamed from: b */
    private long f1699b = 0;

    /* renamed from: a */
    public void mo14348a() {
        C1179b.m1354a("start cron-keep task");
        C1257f.m1711a().mo14495i();
        C1355r.m2114a().mo14774c();
        C1355r.m2114a().mo14777f();
        C1257f.m1711a().mo14493g();
        C1257f.m1711a().mo14496j();
    }

    /* renamed from: a */
    public void mo14349a(long j) {
        this.f1699b = j;
    }

    /* renamed from: b */
    public boolean mo14350b() {
        return System.currentTimeMillis() - this.f1699b > 3600000;
    }
}
