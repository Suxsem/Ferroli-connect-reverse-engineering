package com.igexin.push.p088g.p090b;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1347j;
import com.igexin.push.core.p047a.C1257f;
import java.util.concurrent.TimeUnit;

/* renamed from: com.igexin.push.g.b.c */
public class C1570c extends C1575h {

    /* renamed from: a */
    private static C1570c f2995a;

    public C1570c() {
        super(C1347j.m2085a().mo14746b());
        this.f1649o = true;
    }

    /* renamed from: i */
    public static C1570c m3170i() {
        if (f2995a == null) {
            f2995a = new C1570c();
        }
        return f2995a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        C1257f.m1711a().mo14497k();
        C1343f.f2113E = System.currentTimeMillis();
        if (C1343f.f2175l) {
            C1179b.m1354a("heartbeatReq");
            C1340e.m2032a().mo14711h().mo14484b();
            return;
        }
        C1179b.m1354a("HeartBeatTimerTask doTaskMethod isOnline = false, refresh wait time !!!!!!");
        mo15208j();
    }

    /* renamed from: b */
    public final int mo14231b() {
        return -2147483642;
    }

    /* renamed from: c */
    public void mo14300c() {
        super.mo14300c();
        if (!this.f1646k) {
            mo15208j();
        }
    }

    /* renamed from: d */
    public void mo14221d() {
    }

    /* renamed from: j */
    public void mo15208j() {
        mo14294a(C1347j.m2085a().mo14746b(), TimeUnit.MILLISECONDS);
    }

    /* renamed from: k */
    public void mo15209k() {
    }
}
