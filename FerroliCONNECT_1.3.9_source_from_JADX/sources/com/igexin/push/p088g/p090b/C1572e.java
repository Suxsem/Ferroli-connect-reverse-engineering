package com.igexin.push.p088g.p090b;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1343f;
import com.igexin.push.p054e.C1368b;
import com.igexin.push.util.C1576a;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.igexin.push.g.b.e */
public class C1572e extends C1575h {

    /* renamed from: a */
    private long f2996a = C1234k.f1825G;

    /* renamed from: b */
    private AtomicBoolean f2997b = new AtomicBoolean(false);

    public C1572e() {
        super(604800000);
        this.f1649o = true;
    }

    /* renamed from: a */
    private void m3180a(long j) {
        mo14294a(j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: i */
    public static C1572e m3181i() {
        return C1573f.f2998a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        mo14294a(this.f2996a, TimeUnit.MILLISECONDS);
        boolean a = C1576a.m3200a(System.currentTimeMillis());
        if (!C1343f.f2175l && C1343f.f2171h && C1343f.f2172i && C1343f.f2173j && !a && C1576a.m3212b()) {
            C1179b.m1354a("PollingTimerTask|run = true");
            C1368b.m2191a().mo14823g();
            C1343f.f2112D = 100;
            C1574g.m3187i().mo15213j();
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 20160629;
    }

    /* renamed from: j */
    public void mo15210j() {
        if (!this.f2997b.get()) {
            C1174c.m1310b().mo14317a(this, false, true);
            this.f2997b.set(true);
        }
        m3180a(this.f2996a);
    }

    /* renamed from: k */
    public void mo15211k() {
        mo14294a(604800000, TimeUnit.MILLISECONDS);
    }
}
