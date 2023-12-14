package com.igexin.push.p088g.p090b;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.push.core.p047a.C1257f;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.igexin.push.g.b.a */
public class C1568a extends C1575h {

    /* renamed from: b */
    private static C1568a f2991b;

    /* renamed from: a */
    private List<C1571d> f2992a = new ArrayList();

    private C1568a() {
        super(60000);
        this.f1649o = true;
    }

    /* renamed from: i */
    public static C1568a m3162i() {
        if (f2991b == null) {
            f2991b = new C1568a();
        }
        return f2991b;
    }

    /* renamed from: j */
    private void m3163j() {
        mo14294a(360000, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        C1257f.m1711a().mo14497k();
        for (C1571d next : this.f2992a) {
            if (next.mo14350b()) {
                next.mo14348a();
                next.mo14349a(System.currentTimeMillis());
            }
        }
        m3163j();
        C1174c.m1310b().mo14319a((Object) this);
    }

    /* renamed from: a */
    public boolean mo15207a(C1571d dVar) {
        List<C1571d> list = this.f2992a;
        return list != null && !list.contains(dVar) && this.f2992a.add(dVar);
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
