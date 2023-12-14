package com.igexin.push.p046c;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.util.C1576a;
import java.util.List;

/* renamed from: com.igexin.push.c.i */
public class C1214i {

    /* renamed from: a */
    public static boolean f1755a = false;

    /* renamed from: b */
    private static final String f1756b = "com.igexin.push.c.i";

    /* renamed from: c */
    private static C1214i f1757c;

    /* renamed from: d */
    private static C1213h f1758d;

    private C1214i() {
        mo14382b();
        f1758d = C1576a.m3216c() ? C1213h.WIFI : C1213h.MOBILE;
    }

    /* renamed from: a */
    public static synchronized C1214i m1500a() {
        C1214i iVar;
        synchronized (C1214i.class) {
            if (f1757c == null) {
                f1757c = new C1214i();
            }
            iVar = f1757c;
        }
        return iVar;
    }

    /* renamed from: b */
    public void mo14382b() {
        f1755a = SDKUrlConfig.realXfrListIsOnly();
    }

    /* renamed from: c */
    public void mo14383c() {
        if (f1755a) {
            C1179b.m1354a(f1756b + "|xfr len = 1, detect = false");
            return;
        }
        C1174c.m1310b().mo14317a(C1216k.m1527c_(), false, true);
    }

    /* renamed from: d */
    public void mo14384d() {
        try {
            mo14388h().mo14416d();
        } catch (Throwable th) {
            C1179b.m1354a(f1756b + "|" + th.toString());
        }
    }

    /* renamed from: e */
    public C1206a mo14385e() {
        return mo14388h().f1779d;
    }

    /* renamed from: f */
    public void mo14386f() {
        if (!f1755a) {
            C1218m h = mo14388h();
            if (!h.mo14424l()) {
                C1179b.m1354a(f1756b + "|startDetect detect = false");
                return;
            }
            C1179b.m1354a(f1756b + "|network changed detect = true, reset detect delay");
            h.mo14418f();
        }
    }

    /* renamed from: g */
    public void mo14387g() {
        mo14382b();
        C1221p.m1576e_();
        if (f1755a) {
            C1216k.m1527c_().mo14408d_();
            try {
                C1217l.m1532a().f1779d.mo14364a((List<C1210e>) null);
                C1223r.m1596a().f1779d.mo14364a((List<C1210e>) null);
                C1223r.m1596a().mo14420h();
                C1217l.m1532a().mo14420h();
                C1223r.m1596a().mo14423k();
            } catch (Throwable th) {
                C1179b.m1354a(f1756b + "|" + th.toString());
            }
        } else {
            try {
                C1223r.m1596a().mo14423k();
                C1223r.m1596a().mo14419g();
                C1217l.m1532a().mo14419g();
                C1218m h = mo14388h();
                if (h != null) {
                    h.mo14421i();
                }
            } catch (Throwable th2) {
                C1179b.m1354a(f1756b + "|" + th2.toString());
            }
        }
    }

    /* renamed from: h */
    public synchronized C1218m mo14388h() {
        C1218m a;
        a = C1576a.m3216c() ? C1223r.m1596a() : C1217l.m1532a();
        C1213h b = a.mo14411b();
        if (b != f1758d) {
            if (b == C1213h.WIFI) {
                C1217l.m1532a().mo14417e();
            } else if (b == C1213h.MOBILE) {
                C1223r.m1596a().mo14417e();
            }
        }
        f1758d = b;
        return a;
    }
}
