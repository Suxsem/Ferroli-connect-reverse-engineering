package com.igexin.push.p054e;

import android.content.Intent;
import android.os.Bundle;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.p088g.p090b.C1572e;
import com.igexin.push.util.C1576a;

/* renamed from: com.igexin.push.e.b */
public class C1368b {

    /* renamed from: a */
    private int f2240a;

    /* renamed from: b */
    private int f2241b;

    /* renamed from: c */
    private boolean f2242c;

    /* renamed from: d */
    private int f2243d;

    /* renamed from: e */
    private long f2244e;

    /* renamed from: f */
    private int f2245f;

    /* renamed from: g */
    private long f2246g;

    /* renamed from: h */
    private C1389d f2247h;

    /* renamed from: i */
    private C1394i f2248i;

    private C1368b() {
        this.f2240a = C1234k.f1824F;
        this.f2241b = C1234k.f1826H;
        this.f2248i = new C1391f();
        this.f2247h = C1576a.m3216c() ? C1389d.WIFI : C1389d.MOBILE;
    }

    /* renamed from: a */
    public static C1368b m2191a() {
        return C1390e.f2341a;
    }

    /* renamed from: a */
    private void m2192a(int i) {
        if (C1343f.f2169f != null) {
            try {
                Intent intent = new Intent();
                intent.setAction("com.igexin.sdk.action.polling");
                Bundle bundle = new Bundle();
                bundle.putInt("code", i);
                intent.putExtras(bundle);
                C1343f.f2169f.sendBroadcast(intent);
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: h */
    private void m2193h() {
        C1179b.m1354a("ConnectModelCoordinator|reset current model = normal");
        C1394i iVar = this.f2248i;
        if (iVar != null && !(iVar instanceof C1391f)) {
            this.f2248i = new C1391f();
        }
        C1572e.m3181i().mo15211k();
        this.f2245f = 0;
        this.f2243d = 0;
        this.f2242c = false;
        C1312h.m1937a().mo14676b(this.f2242c);
    }

    /* renamed from: i */
    private void m2194i() {
        m2192a(0);
    }

    /* renamed from: j */
    private void m2195j() {
        m2192a(1);
    }

    /* renamed from: a */
    public void mo14817a(boolean z) {
        this.f2242c = z;
        C1179b.m1354a("ConnectModelCoordinator|init, current is polling model = " + z);
        if (z) {
            C1572e.m3181i().mo15210j();
        }
    }

    /* renamed from: b */
    public synchronized void mo14818b() {
        C1389d dVar = C1576a.m3216c() ? C1389d.WIFI : C1389d.MOBILE;
        if (dVar != this.f2247h) {
            C1179b.m1354a("ConnectModelCoordinator|net type changed " + this.f2247h + "->" + dVar);
            m2193h();
            this.f2247h = dVar;
        }
    }

    /* renamed from: c */
    public C1394i mo14819c() {
        return this.f2248i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0067, code lost:
        return;
     */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo14820d() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.f2242c     // Catch:{ all -> 0x0068 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r5)
            return
        L_0x0007:
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0068 }
            long r2 = r5.f2244e     // Catch:{ all -> 0x0068 }
            long r0 = r0 - r2
            r2 = 20000(0x4e20, double:9.8813E-320)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0066
            r2 = 200000(0x30d40, double:9.8813E-319)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x0066
            int r2 = r5.f2243d     // Catch:{ all -> 0x0068 }
            r3 = 1
            int r2 = r2 + r3
            r5.f2243d = r2     // Catch:{ all -> 0x0068 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0068 }
            r2.<init>()     // Catch:{ all -> 0x0068 }
            java.lang.String r4 = "ConnectModelCoordinator|read len = -1, interval = "
            r2.append(r4)     // Catch:{ all -> 0x0068 }
            r2.append(r0)     // Catch:{ all -> 0x0068 }
            java.lang.String r0 = ", disconnect ="
            r2.append(r0)     // Catch:{ all -> 0x0068 }
            int r0 = r5.f2243d     // Catch:{ all -> 0x0068 }
            r2.append(r0)     // Catch:{ all -> 0x0068 }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x0068 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x0068 }
            int r0 = r5.f2243d     // Catch:{ all -> 0x0068 }
            int r1 = r5.f2240a     // Catch:{ all -> 0x0068 }
            if (r0 < r1) goto L_0x0066
            java.lang.String r0 = "ConnectModelCoordinator|enter polling mode ####"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x0068 }
            r5.m2194i()     // Catch:{ all -> 0x0068 }
            r5.f2242c = r3     // Catch:{ all -> 0x0068 }
            com.igexin.push.e.g r0 = new com.igexin.push.e.g     // Catch:{ all -> 0x0068 }
            r0.<init>()     // Catch:{ all -> 0x0068 }
            r5.f2248i = r0     // Catch:{ all -> 0x0068 }
            com.igexin.push.g.b.e r0 = com.igexin.push.p088g.p090b.C1572e.m3181i()     // Catch:{ all -> 0x0068 }
            r0.mo15210j()     // Catch:{ all -> 0x0068 }
            com.igexin.push.core.c.h r0 = com.igexin.push.core.p050c.C1312h.m1937a()     // Catch:{ all -> 0x0068 }
            boolean r1 = r5.f2242c     // Catch:{ all -> 0x0068 }
            r0.mo14676b((boolean) r1)     // Catch:{ all -> 0x0068 }
        L_0x0066:
            monitor-exit(r5)
            return
        L_0x0068:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p054e.C1368b.mo14820d():void");
    }

    /* renamed from: e */
    public synchronized void mo14821e() {
        if (this.f2242c) {
            if (System.currentTimeMillis() - this.f2246g >= 120000) {
                this.f2245f++;
                C1179b.m1354a("ConnectModelCoordinator|polling mode, cur hearbeat =" + this.f2245f);
                if (this.f2245f >= this.f2241b) {
                    C1179b.m1354a("ConnectModelCoordinator|enter normal mode ####");
                    m2195j();
                    C1343f.f2112D = 0;
                    m2193h();
                }
            }
            this.f2246g = System.currentTimeMillis();
        }
    }

    /* renamed from: f */
    public void mo14822f() {
        this.f2244e = System.currentTimeMillis();
        if (this.f2242c) {
            this.f2248i = new C1392g();
            C1572e.m3181i().mo15210j();
            this.f2245f = 0;
        }
    }

    /* renamed from: g */
    public void mo14823g() {
        C1394i iVar;
        if (this.f2242c && (iVar = this.f2248i) != null && !(iVar instanceof C1391f)) {
            this.f2248i = new C1391f();
        }
    }
}
