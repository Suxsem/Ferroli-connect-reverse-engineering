package com.igexin.push.core;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.p046c.C1214i;
import com.igexin.push.p054e.p057c.C1377f;
import com.igexin.push.p054e.p057c.C1380i;
import com.igexin.push.p054e.p057c.C1382k;
import com.igexin.push.p087f.C1560a;
import com.igexin.push.util.C1576a;
import java.util.Random;

/* renamed from: com.igexin.push.core.p */
public class C1353p {

    /* renamed from: a */
    private static C1353p f2209a;

    /* renamed from: a */
    public static C1353p m2098a() {
        if (f2209a == null) {
            f2209a = new C1353p();
        }
        return f2209a;
    }

    /* renamed from: b */
    public int mo14749b() {
        if (!C1343f.f2172i || !C1343f.f2173j || C1576a.m3200a(System.currentTimeMillis()) || !C1576a.m3212b()) {
            C1179b.m1354a("LoginInteractor|keyNegotiate stop ++++++++++");
            return -1;
        }
        C1380i iVar = new C1380i();
        iVar.f2302a = C1343f.f2135a;
        return C1340e.m2032a().mo14710g().mo15187a("K-", iVar, true) < 0 ? 0 : 1;
    }

    /* renamed from: c */
    public void mo14750c() {
        if (C1343f.f2174k) {
            C1343f.f2174k = false;
            C1343f.f2118J = System.currentTimeMillis() + (((long) Math.abs(new Random().nextInt() % 24)) * 3600000);
        }
        C1214i.m1500a().mo14385e().mo14373h();
        boolean z = true;
        if (C1343f.f2181r == 0) {
            C1179b.m1354a("registerReq #####");
            C1377f fVar = new C1377f(C1343f.f2184u, C1343f.f2185v, C1343f.f2109A, C1343f.f2135a);
            C1560a g = C1340e.m2032a().mo14710g();
            if (g.mo15187a("R-" + C1343f.f2109A, fVar, true) < 0) {
                z = false;
            }
            C1179b.m1354a("registerReq|" + z + "|" + C1343f.f2109A);
            return;
        }
        C1382k d = mo14751d();
        C1179b.m1354a("loginReqBefore|" + d.f2308a);
        C1560a g2 = C1340e.m2032a().mo14710g();
        if (g2.mo15187a("S-" + String.valueOf(C1343f.f2181r), d, true) < 0) {
            z = false;
        }
        if (z) {
            C1179b.m1354a("LoginInteractor|loginReq|" + C1343f.f2182s);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:4|5|6|(1:10)|11|(3:14|15|(3:17|(1:19)|(1:21)))|22|23|(1:25)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0080 */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0086 A[Catch:{ Throwable -> 0x0088 }] */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.igexin.push.p054e.p057c.C1382k mo14751d() {
        /*
            r6 = this;
            com.igexin.push.e.c.k r0 = new com.igexin.push.e.c.k
            r0.<init>()
            long r1 = com.igexin.push.core.C1343f.f2181r
            r0.f2308a = r1
            r1 = 0
            r0.f2309b = r1
            r1 = 65280(0xff00, float:9.1477E-41)
            r0.f2310c = r1
            java.lang.String r1 = com.igexin.push.core.C1343f.f2135a
            r0.f2311d = r1
            boolean r1 = com.igexin.push.util.C1576a.m3199a()     // Catch:{ Throwable -> 0x0088 }
            r2 = -1
            if (r1 == 0) goto L_0x0088
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0088 }
            r1.<init>()     // Catch:{ Throwable -> 0x0088 }
            android.content.Context r3 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x0048 }
            java.lang.String r4 = "connectivity"
            java.lang.Object r3 = r3.getSystemService(r4)     // Catch:{ Throwable -> 0x0048 }
            android.net.ConnectivityManager r3 = (android.net.ConnectivityManager) r3     // Catch:{ Throwable -> 0x0048 }
            if (r3 == 0) goto L_0x0048
            android.net.NetworkInfo r3 = r3.getActiveNetworkInfo()     // Catch:{ Throwable -> 0x0048 }
            if (r3 == 0) goto L_0x0048
            int r2 = r3.getType()     // Catch:{ Throwable -> 0x0048 }
            com.igexin.push.e.c.l r3 = new com.igexin.push.e.c.l     // Catch:{ Throwable -> 0x0048 }
            r3.<init>()     // Catch:{ Throwable -> 0x0048 }
            r4 = 2
            r3.f2313a = r4     // Catch:{ Throwable -> 0x0048 }
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0048 }
            r3.f2314b = r4     // Catch:{ Throwable -> 0x0048 }
            r1.add(r3)     // Catch:{ Throwable -> 0x0048 }
        L_0x0048:
            r3 = 1
            if (r2 != r3) goto L_0x0080
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x0080 }
            java.lang.String r4 = "wifi"
            java.lang.Object r2 = r2.getSystemService(r4)     // Catch:{ Throwable -> 0x0080 }
            android.net.wifi.WifiManager r2 = (android.net.wifi.WifiManager) r2     // Catch:{ Throwable -> 0x0080 }
            android.net.wifi.WifiInfo r2 = r2.getConnectionInfo()     // Catch:{ Throwable -> 0x0080 }
            if (r2 == 0) goto L_0x0080
            java.lang.String r4 = r2.getSSID()     // Catch:{ Throwable -> 0x0080 }
            java.lang.String r2 = r2.getBSSID()     // Catch:{ Throwable -> 0x0080 }
            if (r4 == 0) goto L_0x0071
            com.igexin.push.e.c.l r5 = new com.igexin.push.e.c.l     // Catch:{ Throwable -> 0x0080 }
            r5.<init>()     // Catch:{ Throwable -> 0x0080 }
            r5.f2313a = r3     // Catch:{ Throwable -> 0x0080 }
            r5.f2314b = r4     // Catch:{ Throwable -> 0x0080 }
            r1.add(r5)     // Catch:{ Throwable -> 0x0080 }
        L_0x0071:
            if (r2 == 0) goto L_0x0080
            com.igexin.push.e.c.l r3 = new com.igexin.push.e.c.l     // Catch:{ Throwable -> 0x0080 }
            r3.<init>()     // Catch:{ Throwable -> 0x0080 }
            r4 = 4
            r3.f2313a = r4     // Catch:{ Throwable -> 0x0080 }
            r3.f2314b = r2     // Catch:{ Throwable -> 0x0080 }
            r1.add(r3)     // Catch:{ Throwable -> 0x0080 }
        L_0x0080:
            boolean r2 = r1.isEmpty()     // Catch:{ Throwable -> 0x0088 }
            if (r2 != 0) goto L_0x0088
            r0.f2312e = r1     // Catch:{ Throwable -> 0x0088 }
        L_0x0088:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1353p.mo14751d():com.igexin.push.e.c.k");
    }
}
