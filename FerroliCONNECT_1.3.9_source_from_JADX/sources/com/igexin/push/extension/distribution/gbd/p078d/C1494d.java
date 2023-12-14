package com.igexin.push.extension.distribution.gbd.p078d;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.push.core.C1340e;
import com.igexin.push.extension.distribution.gbd.p069a.p070a.C1448a;
import com.igexin.push.extension.distribution.gbd.p069a.p070a.C1449b;
import com.igexin.push.extension.distribution.gbd.p069a.p072c.C1463c;
import com.igexin.push.extension.distribution.gbd.p069a.p075f.C1480a;
import com.igexin.push.extension.distribution.gbd.p076b.C1481a;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.C1501a;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1502a;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p081f.C1509a;
import com.igexin.push.extension.distribution.gbd.p081f.p082a.C1510a;
import com.igexin.push.extension.distribution.gbd.p083g.C1517a;
import com.igexin.push.extension.distribution.gbd.p083g.C1518b;
import com.igexin.push.extension.distribution.gbd.p084h.C1519a;
import com.igexin.push.extension.distribution.gbd.p084h.p085a.C1522c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.extension.distribution.gbd.p086i.C1555w;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.utl.UtilityImpl;
import java.util.List;
import java.util.Random;

/* renamed from: com.igexin.push.extension.distribution.gbd.d.d */
public class C1494d {

    /* renamed from: a */
    private static C1494d f2775a;

    /* renamed from: b */
    private Context f2776b;

    /* renamed from: c */
    private C1500j f2777c = new C1500j();

    /* renamed from: d */
    private C1517a f2778d;

    /* renamed from: e */
    private C1518b f2779e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public C1519a f2780f;

    private C1494d() {
    }

    /* renamed from: a */
    public static C1494d m2778a() {
        if (f2775a == null) {
            f2775a = new C1494d();
        }
        return f2775a;
    }

    /* renamed from: f */
    private void m2782f() {
        boolean a = C1340e.m2032a().mo14703a((C1575h) new C1495e(this, (long) ((C1488a.f2658aO + new Random().nextInt(5)) * 1000)));
        C1540h.m2997b("GBD_Logic", "add data TimerTask result = " + a);
    }

    /* renamed from: g */
    private void m2783g() {
        try {
            if (!C1541i.m3032h(this.f2776b)) {
                C1510a aVar = new C1510a(new C1496f(this));
                aVar.mo15143a("http://sdk.open.phone.igexin.com/api/addr.htm");
                C1174c.m1310b().mo14317a(new C1509a(aVar), false, true);
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: h */
    private void m2784h() {
        long nextInt = (long) ((C1488a.f2634R + new Random().nextInt(C1488a.f2635S)) * 1000);
        boolean a = C1340e.m2032a().mo14703a((C1575h) new C1497g(this, nextInt));
        C1540h.m2997b("GBD_Logic", "add guard TimerTask result = " + a + "|guardTime = " + nextInt);
    }

    /* renamed from: i */
    private void m2785i() {
        boolean a = C1340e.m2032a().mo14703a((C1575h) new C1499i(this, 5000));
        C1540h.m2997b("GBD_Logic", "add gbd config TimerTask result = " + a);
    }

    /* renamed from: j */
    private void m2786j() {
        C1490c.f2748b = new C1501a(this.f2776b);
        C1502a.m2812a().mo15107b();
        C1507f.m2840a().mo15118b();
        C1503b.m2819a().mo15110b();
    }

    /* renamed from: k */
    private void m2787k() {
        try {
            C1490c.f2750d = (WifiManager) this.f2776b.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            if (this.f2776b.getPackageManager().checkPermission("android.permission.ACCESS_WIFI_STATE", this.f2776b.getPackageName()) == 0) {
                C1490c.f2752f = true;
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: l */
    public void m2788l() {
        this.f2779e = new C1518b();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PushConsts.ACTION_BROADCAST_USER_PRESENT);
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.f2776b.registerReceiver(this.f2779e, intentFilter);
    }

    /* access modifiers changed from: private */
    /* renamed from: m */
    public void m2789m() {
        this.f2778d = new C1517a();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        this.f2776b.registerReceiver(this.f2778d, intentFilter);
    }

    /* renamed from: n */
    private void m2790n() {
        if (C1541i.m3031h()) {
            boolean a = C1174c.m1310b().mo14317a(C1522c.m2931i_(), true, true);
            C1540h.m2997b("GBD_Logic", "initCAT  result = " + a);
        }
    }

    /* renamed from: o */
    private void m2791o() {
        try {
            boolean h = C1541i.m3031h();
            C1540h.m2997b("GBD_Logic", "CAE  " + h);
            if (!h) {
                C1490c.f2729F = 0;
                C1490c.f2730G = 0;
                C1490c.f2728E = "";
                C1507f.m2840a().mo15129g(C1490c.f2729F);
                C1507f.m2840a().mo15130h(C1490c.f2730G);
                C1507f.m2840a().mo15116a(C1490c.f2728E);
                C1540h.m2997b("GBD_Logic", "clean CAD. ");
                return;
            }
            if (C1490c.f2728E.split(",").length >= 200) {
                C1540h.m2997b("GBD_Logic", " CAD length > 200, clean. ");
                C1490c.f2728E = "";
                C1507f.m2840a().mo15116a(C1490c.f2728E);
            }
            long i = C1541i.m3034i();
            long j = i - (C1490c.f2729F + C1490c.f2730G);
            if (C1490c.f2729F == 0) {
                C1490c.f2729F = i;
                C1490c.f2730G = 0;
                C1507f.m2840a().mo15129g(C1490c.f2729F);
                C1507f.m2840a().mo15130h(C1490c.f2730G);
            } else if (j <= C1488a.f2678ai * 1000) {
                C1490c.f2730G = i - C1490c.f2729F;
                if (C1490c.f2730G < 0) {
                    C1490c.f2729F = i;
                    C1490c.f2730G = 0;
                }
                C1507f.m2840a().mo15129g(C1490c.f2729F);
                C1507f.m2840a().mo15130h(C1490c.f2730G);
            } else {
                if (C1490c.f2730G >= 10000) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(C1490c.f2728E);
                    sb.append((C1490c.f2729F / 1000) + ":" + (C1490c.f2730G / 1000) + ",");
                    C1490c.f2728E = sb.toString();
                    C1507f.m2840a().mo15116a(C1490c.f2728E);
                    C1540h.m2997b("GBD_Logic", "CAD update =  " + C1490c.f2728E);
                }
                C1490c.f2729F = i;
                C1490c.f2730G = 0;
                C1507f.m2840a().mo15129g(C1490c.f2729F);
                C1507f.m2840a().mo15130h(C1490c.f2730G);
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: a */
    public void mo15087a(Context context) {
        this.f2776b = context;
        this.f2777c.start();
    }

    /* renamed from: b */
    public void mo15088b() {
        C1540h.m2997b("GBD_Logic", "gbd logic init");
        C1490c.f2747a = this.f2776b;
        C1555w.m3066a();
        m2786j();
        m2791o();
        m2790n();
        m2785i();
        if (C1488a.f2696b) {
            m2787k();
            m2783g();
            C1463c.m2637a();
            C1449b.m2546a();
            C1448a.m2541a();
            C1480a.m2722a();
            this.f2780f = new C1519a();
            m2784h();
            m2782f();
        }
    }

    /* renamed from: c */
    public void mo15089c() {
        PackageManager packageManager = this.f2776b.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            try {
                PackageInfo packageInfo = installedPackages.get(i);
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if ((applicationInfo.flags & 1) <= 0) {
                    C1481a aVar = new C1481a();
                    aVar.mo15037b(applicationInfo.loadLabel(packageManager).toString());
                    aVar.mo15036a(applicationInfo.packageName);
                    aVar.mo15038c(packageInfo.versionName);
                    aVar.mo15039d(String.valueOf(packageInfo.versionCode));
                    C1490c.f2758l.put(applicationInfo.packageName, aVar);
                }
            } catch (Exception e) {
                C1540h.m2996a(e);
            }
        }
    }

    /* renamed from: d */
    public void mo15090d() {
        if (this.f2776b != null) {
            C1540h.m2995a("GBD_Logic", "onDestroy");
            C1517a aVar = this.f2778d;
            if (aVar != null) {
                this.f2776b.unregisterReceiver(aVar);
                this.f2778d = null;
            }
            C1518b bVar = this.f2779e;
            if (bVar != null) {
                this.f2776b.unregisterReceiver(bVar);
                this.f2779e = null;
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo15091e() {
        /*
            r17 = this;
            r1 = r17
            java.lang.String r0 = "#"
            java.lang.String r2 = ","
            java.lang.String r3 = "none"
            r4 = 0
            java.lang.String r5 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2697c     // Catch:{ Throwable -> 0x0167 }
            boolean r5 = r3.equals(r5)     // Catch:{ Throwable -> 0x0167 }
            if (r5 != 0) goto L_0x0165
            java.lang.String r5 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2697c     // Catch:{ Throwable -> 0x0167 }
            java.lang.String[] r5 = r5.split(r2)     // Catch:{ Throwable -> 0x0167 }
            r6 = 0
        L_0x0018:
            int r7 = r5.length     // Catch:{ Throwable -> 0x0167 }
            java.lang.String r8 = "GBD_Logic"
            if (r6 >= r7) goto L_0x003f
            r7 = r5[r6]     // Catch:{ Throwable -> 0x0167 }
            android.content.Context r9 = r1.f2776b     // Catch:{ Throwable -> 0x0167 }
            boolean r9 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3019c((java.lang.String) r7, (android.content.Context) r9)     // Catch:{ Throwable -> 0x0167 }
            if (r9 == 0) goto L_0x003c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0167 }
            r0.<init>()     // Catch:{ Throwable -> 0x0167 }
            java.lang.String r2 = "checkSafeStatus pkgName = "
            r0.append(r2)     // Catch:{ Throwable -> 0x0167 }
            r0.append(r7)     // Catch:{ Throwable -> 0x0167 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0167 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2995a(r8, r0)     // Catch:{ Throwable -> 0x0167 }
            return r4
        L_0x003c:
            int r6 = r6 + 1
            goto L_0x0018
        L_0x003f:
            java.lang.String r5 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2703i     // Catch:{ Throwable -> 0x0167 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0167 }
            r6 = 1
            if (r5 != 0) goto L_0x010e
            java.lang.String r5 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2703i     // Catch:{ Throwable -> 0x0167 }
            boolean r5 = r3.equalsIgnoreCase(r5)     // Catch:{ Throwable -> 0x0167 }
            if (r5 != 0) goto L_0x010e
            java.lang.String r5 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2703i     // Catch:{ Throwable -> 0x0167 }
            java.lang.String[] r5 = r5.split(r2)     // Catch:{ Throwable -> 0x0167 }
            int r7 = r5.length     // Catch:{ Throwable -> 0x0167 }
            r9 = 0
        L_0x0058:
            if (r9 >= r7) goto L_0x010e
            r10 = r5[r9]     // Catch:{ Throwable -> 0x0167 }
            boolean r11 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x0167 }
            if (r11 != 0) goto L_0x0109
            java.lang.String r11 = ":"
            java.lang.String[] r10 = r10.split(r11)     // Catch:{ Throwable -> 0x0167 }
            int r11 = r10.length     // Catch:{ Throwable -> 0x0167 }
            r12 = 3
            if (r11 != r12) goto L_0x0104
            r11 = r10[r4]     // Catch:{ Throwable -> 0x0167 }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x0167 }
            if (r11 != 0) goto L_0x0104
            r11 = r10[r4]     // Catch:{ Throwable -> 0x0167 }
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0167 }
            r13 = r10[r6]     // Catch:{ Throwable -> 0x0167 }
            java.lang.String[] r13 = r13.split(r0)     // Catch:{ Throwable -> 0x0167 }
            java.util.List r13 = java.util.Arrays.asList(r13)     // Catch:{ Throwable -> 0x0167 }
            r12.<init>(r13)     // Catch:{ Throwable -> 0x0167 }
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0167 }
            r14 = 2
            r10 = r10[r14]     // Catch:{ Throwable -> 0x0167 }
            java.lang.String[] r10 = r10.split(r0)     // Catch:{ Throwable -> 0x0167 }
            java.util.List r10 = java.util.Arrays.asList(r10)     // Catch:{ Throwable -> 0x0167 }
            r13.<init>(r10)     // Catch:{ Throwable -> 0x0167 }
            java.lang.String r10 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3010b()     // Catch:{ Throwable -> 0x0167 }
            java.lang.String r14 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3020d()     // Catch:{ Throwable -> 0x0167 }
            java.lang.String r14 = r14.toLowerCase()     // Catch:{ Throwable -> 0x0167 }
            int r15 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0167 }
            java.lang.String r4 = java.lang.String.valueOf(r15)     // Catch:{ Throwable -> 0x0167 }
            boolean r10 = r11.equalsIgnoreCase(r10)     // Catch:{ Throwable -> 0x0167 }
            if (r10 == 0) goto L_0x0109
            java.lang.String r0 = "11 checkStatus brand match."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r8, r0)     // Catch:{ Throwable -> 0x0167 }
            int r0 = r12.size()     // Catch:{ Throwable -> 0x0167 }
            if (r0 != r6) goto L_0x00f2
            int r0 = r13.size()     // Catch:{ Throwable -> 0x0167 }
            if (r0 != r6) goto L_0x00f2
            r2 = 0
            java.lang.Object r0 = r12.get(r2)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00ef }
            java.lang.Double r0 = java.lang.Double.valueOf(r0)     // Catch:{ Throwable -> 0x00ef }
            double r3 = r0.doubleValue()     // Catch:{ Throwable -> 0x00ef }
            java.lang.Object r0 = r13.get(r2)     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0167 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0167 }
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x0167 }
            java.lang.Double r2 = java.lang.Double.valueOf(r14)     // Catch:{ Throwable -> 0x0167 }
            double r7 = r2.doubleValue()     // Catch:{ Throwable -> 0x0167 }
            int r2 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r2 < 0) goto L_0x00ec
            if (r15 < r0) goto L_0x00ec
            r16 = 1
            goto L_0x00ee
        L_0x00ec:
            r16 = 0
        L_0x00ee:
            return r16
        L_0x00ef:
            r0 = move-exception
            goto L_0x0169
        L_0x00f2:
            boolean r0 = r12.contains(r14)     // Catch:{ Throwable -> 0x0167 }
            if (r0 == 0) goto L_0x0101
            boolean r0 = r13.contains(r4)     // Catch:{ Throwable -> 0x0167 }
            if (r0 == 0) goto L_0x0101
            r16 = 1
            goto L_0x0103
        L_0x0101:
            r16 = 0
        L_0x0103:
            return r16
        L_0x0104:
            java.lang.String r4 = "11 rom format error,continue."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r8, r4)     // Catch:{ Throwable -> 0x0167 }
        L_0x0109:
            int r9 = r9 + 1
            r4 = 0
            goto L_0x0058
        L_0x010e:
            java.lang.String r0 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2698d     // Catch:{ Throwable -> 0x0167 }
            boolean r0 = r3.equals(r0)     // Catch:{ Throwable -> 0x0167 }
            if (r0 != 0) goto L_0x0163
            android.content.Context r0 = r1.f2776b     // Catch:{ Throwable -> 0x0167 }
            boolean r0 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3005a((android.content.Context) r0)     // Catch:{ Throwable -> 0x0167 }
            if (r0 == 0) goto L_0x0163
            java.lang.String r0 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2698d     // Catch:{ Throwable -> 0x0167 }
            java.lang.String[] r0 = r0.split(r2)     // Catch:{ Throwable -> 0x0167 }
            android.content.Context r2 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2747a     // Catch:{ Throwable -> 0x0167 }
            android.content.pm.PackageManager r2 = r2.getPackageManager()     // Catch:{ Throwable -> 0x0167 }
            r3 = 4
            java.util.List r2 = r2.getInstalledPackages(r3)     // Catch:{ Throwable -> 0x0167 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ Throwable -> 0x0167 }
        L_0x0133:
            boolean r3 = r2.hasNext()     // Catch:{ Throwable -> 0x0167 }
            if (r3 == 0) goto L_0x0162
            java.lang.Object r3 = r2.next()     // Catch:{ Throwable -> 0x0167 }
            android.content.pm.PackageInfo r3 = (android.content.pm.PackageInfo) r3     // Catch:{ Throwable -> 0x0167 }
            android.content.pm.ServiceInfo[] r3 = r3.services     // Catch:{ Throwable -> 0x0167 }
            if (r3 == 0) goto L_0x0133
            int r4 = r3.length     // Catch:{ Throwable -> 0x0167 }
            if (r4 <= 0) goto L_0x0133
            int r4 = r3.length     // Catch:{ Throwable -> 0x0167 }
            r5 = 0
        L_0x0148:
            if (r5 >= r4) goto L_0x0133
            r7 = r3[r5]     // Catch:{ Throwable -> 0x0167 }
            int r8 = r0.length     // Catch:{ Throwable -> 0x0167 }
            r9 = 0
        L_0x014e:
            if (r9 >= r8) goto L_0x015f
            r10 = r0[r9]     // Catch:{ Throwable -> 0x0167 }
            java.lang.String r11 = r7.name     // Catch:{ Throwable -> 0x0167 }
            boolean r10 = r10.equals(r11)     // Catch:{ Throwable -> 0x0167 }
            if (r10 == 0) goto L_0x015c
            r10 = 0
            return r10
        L_0x015c:
            int r9 = r9 + 1
            goto L_0x014e
        L_0x015f:
            int r5 = r5 + 1
            goto L_0x0148
        L_0x0162:
            return r6
        L_0x0163:
            r2 = 0
            return r2
        L_0x0165:
            r2 = 0
            return r2
        L_0x0167:
            r0 = move-exception
            r2 = 0
        L_0x0169:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p078d.C1494d.mo15091e():boolean");
    }
}
