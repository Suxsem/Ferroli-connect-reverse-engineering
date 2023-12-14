package com.igexin.push.p046c;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.push.util.C1576a;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.igexin.push.c.k */
public class C1216k extends C1575h {

    /* renamed from: a */
    public static final AtomicBoolean f1769a = new AtomicBoolean(false);

    /* renamed from: b */
    private static final String f1770b = "com.igexin.push.c.k";

    /* renamed from: c */
    private static C1216k f1771c;

    /* renamed from: e */
    private boolean f1772e;

    private C1216k() {
        super(10);
        this.f1649o = true;
    }

    /* renamed from: c_ */
    public static synchronized C1216k m1527c_() {
        C1216k kVar;
        synchronized (C1216k.class) {
            if (f1771c == null) {
                f1771c = new C1216k();
            }
            kVar = f1771c;
        }
        return kVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        mo14294a(C1211f.f1746a, TimeUnit.MILLISECONDS);
        if (this.f1772e) {
            C1179b.m1354a(f1770b + "|detect task already stop");
            return;
        }
        C1179b.m1354a(f1770b + "|" + (C1211f.f1746a / 1000) + "s passed, do task method, start redect ~~~~");
        C1343f.f2171h = C1576a.m3221g();
        if (C1343f.f2171h) {
            C1214i.m1500a().mo14384d();
            return;
        }
        C1179b.m1354a(f1770b + "|" + (C1211f.f1746a / 1000) + "s passed, network is unavailable, stop ###");
    }

    /* renamed from: a */
    public void mo14407a(long j) {
        mo14294a(j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: b */
    public int mo14231b() {
        return 20150607;
    }

    /* renamed from: d_ */
    public void mo14408d_() {
        this.f1649o = false;
        this.f1772e = true;
        mo14305p();
    }
}
