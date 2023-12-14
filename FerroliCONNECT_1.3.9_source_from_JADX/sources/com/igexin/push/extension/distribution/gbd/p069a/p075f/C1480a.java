package com.igexin.push.extension.distribution.gbd.p069a.p075f;

import android.os.Build;
import android.text.TextUtils;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.extension.distribution.gbd.p086i.C1557y;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.f.a */
public class C1480a {

    /* renamed from: a */
    private static C1480a f2587a;

    /* renamed from: a */
    public static synchronized C1480a m2722a() {
        C1480a aVar;
        synchronized (C1480a.class) {
            if (f2587a == null) {
                f2587a = new C1480a();
            }
            aVar = f2587a;
        }
        return aVar;
    }

    /* renamed from: a */
    private void m2723a(String str) {
        C1507f.m2840a().mo15120b(str);
        C1503b.m2819a().mo15109a(str, mo15035c());
    }

    /* renamed from: a */
    private void m2724a(StringBuilder sb) {
        String a = C1557y.m3078a(C1490c.f2747a);
        if (!TextUtils.isEmpty(a)) {
            sb.append(a);
        }
        String a2 = C1557y.m3077a(0, C1490c.f2747a);
        if (!TextUtils.isEmpty(a2)) {
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append(a2);
        }
        String a3 = C1557y.m3077a(1, C1490c.f2747a);
        if (!TextUtils.isEmpty(a3)) {
            if (TextUtils.isEmpty(a2)) {
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            }
            sb.append(",");
            sb.append(a3);
        }
    }

    /* renamed from: a */
    private void m2725a(StringBuilder sb, int i, int i2) {
        String b = C1557y.m3081b(C1490c.f2747a);
        if (!TextUtils.isEmpty(b)) {
            sb.append(b);
        }
        String b2 = C1557y.m3080b(i, C1490c.f2747a);
        if (!TextUtils.isEmpty(b2)) {
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append(b2);
        }
        String b3 = C1557y.m3080b(i2, C1490c.f2747a);
        if (!TextUtils.isEmpty(b3)) {
            if (TextUtils.isEmpty(b2)) {
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            }
            sb.append(",");
            sb.append(b3);
        }
    }

    /* renamed from: d */
    private void m2726d() {
        String e = m2727e();
        if (!TextUtils.isEmpty(e)) {
            m2723a(e);
        }
    }

    /* renamed from: e */
    private String m2727e() {
        try {
            if (C1343f.f2182s == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(C1541i.m3034i())));
            sb.append("|");
            sb.append(C1343f.f2182s);
            sb.append("|");
            if (C1343f.f2135a != null) {
                sb.append(C1343f.f2135a);
            }
            sb.append("|");
            sb.append("ANDROID");
            sb.append("|");
            m2724a(sb);
            sb.append("|");
            m2725a(sb, C1557y.m3082c(0, C1490c.f2747a), C1557y.m3082c(1, C1490c.f2747a));
            sb.append("|");
            if (C1343f.f2187x != null) {
                sb.append(C1343f.f2187x);
            }
            sb.append("|");
            if (C1490c.f2762p != null) {
                sb.append(C1490c.f2762p);
            }
            sb.append("|");
            if (C1490c.f2763q != null) {
                sb.append(C1490c.f2763q);
            }
            sb.append("||");
            String a = C1541i.m2998a();
            if (!TextUtils.isEmpty(a)) {
                sb.append(a);
            }
            sb.append("|");
            sb.append("|");
            if (C1343f.f2168e != null) {
                sb.append(C1343f.f2168e);
            }
            sb.append("|");
            if (Build.BRAND != null) {
                sb.append(Build.BRAND);
            }
            sb.append("|");
            sb.append(C1541i.m3015c());
            sb.append("|");
            if (Build.MODEL != null) {
                sb.append(Build.MODEL);
            }
            sb.append("|");
            sb.append("|||");
            sb.append("|");
            sb.append("|");
            String g = m2729g();
            if (!TextUtils.isEmpty(g)) {
                sb.append(g);
            }
            sb.append("|");
            sb.append(C1541i.m3033i(C1490c.f2747a));
            sb.append("|");
            String i = m2731i();
            if (TextUtils.isEmpty(i)) {
                i = "";
            }
            sb.append(i);
            sb.append("|");
            sb.append("|");
            sb.append("|");
            sb.append(m2730h());
            sb.append("|");
            String j = C1541i.m3035j();
            if (!TextUtils.isEmpty(j)) {
                sb.append(j);
            }
            sb.append("|");
            sb.append("|");
            sb.append("|");
            C1540h.m2997b("GBD_LFAction", "getlm type = " + mo15035c());
            String a2 = C1541i.m3009b(C1490c.f2747a).mo15078a();
            if (!TextUtils.isEmpty(a2)) {
                sb.append(a2);
            }
            sb.append("|");
            String f = m2728f();
            if (!TextUtils.isEmpty(f)) {
                sb.append(f);
            }
            sb.append("|");
            String e = C1541i.m3026e(C1490c.f2747a.getPackageName());
            if (!TextUtils.isEmpty(e)) {
                sb.append(e);
            }
            sb.append("|");
            sb.append("|");
            C1540h.m2997b("GBD_LFAction", " " + sb.substring(sb.length() - 10));
            return sb.toString();
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: f */
    private String m2728f() {
        byte[] b;
        try {
            String str = "/sdcard/libs//" + C1490c.f2747a.getPackageName() + ".bin";
            if (new File(str).exists() && (b = C1541i.m3014b(str)) != null) {
                return new String(C1196a.m1439c(b));
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0074, code lost:
        if (r14 == false) goto L_0x0076;
     */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m2729g() {
        /*
            r16 = this;
            java.lang.String r1 = ""
            java.lang.String r0 = ","
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x013e }
            r2.<init>()     // Catch:{ Throwable -> 0x013e }
            android.content.Context r3 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2747a     // Catch:{ Throwable -> 0x013e }
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ Throwable -> 0x013e }
            r4 = 0
            java.util.List r3 = r3.getInstalledPackages(r4)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r5 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2627K     // Catch:{ Throwable -> 0x013e }
            java.lang.String r6 = "\\|"
            java.lang.String[] r5 = r5.split(r6)     // Catch:{ Throwable -> 0x013e }
            int r6 = r5.length     // Catch:{ Throwable -> 0x013e }
            r7 = 4
            if (r6 == r7) goto L_0x0021
            return r1
        L_0x0021:
            r6 = r5[r4]     // Catch:{ Throwable -> 0x013e }
            java.lang.String[] r6 = r6.split(r0)     // Catch:{ Throwable -> 0x013e }
            r7 = 1
            r8 = r5[r7]     // Catch:{ Throwable -> 0x013e }
            java.lang.String[] r8 = r8.split(r0)     // Catch:{ Throwable -> 0x013e }
            r9 = 2
            r9 = r5[r9]     // Catch:{ Throwable -> 0x013e }
            java.lang.String[] r9 = r9.split(r0)     // Catch:{ Throwable -> 0x013e }
            r10 = 3
            r5 = r5[r10]     // Catch:{ Throwable -> 0x013e }
            java.lang.String[] r5 = r5.split(r0)     // Catch:{ Throwable -> 0x013e }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x013e }
        L_0x0040:
            boolean r10 = r3.hasNext()     // Catch:{ Throwable -> 0x013e }
            if (r10 == 0) goto L_0x00e8
            java.lang.Object r10 = r3.next()     // Catch:{ Throwable -> 0x013e }
            android.content.pm.PackageInfo r10 = (android.content.pm.PackageInfo) r10     // Catch:{ Throwable -> 0x013e }
            android.content.pm.ApplicationInfo r11 = r10.applicationInfo     // Catch:{ Throwable -> 0x013e }
            int r11 = r11.flags     // Catch:{ Throwable -> 0x013e }
            r11 = r11 & r7
            if (r11 <= 0) goto L_0x0040
            int r11 = r8.length     // Catch:{ Throwable -> 0x013e }
            java.lang.String r12 = "none"
            if (r11 <= r7) goto L_0x0078
            r11 = r8[r4]     // Catch:{ Throwable -> 0x013e }
            boolean r11 = r11.equals(r12)     // Catch:{ Throwable -> 0x013e }
            if (r11 != 0) goto L_0x0078
            int r11 = r8.length     // Catch:{ Throwable -> 0x013e }
            r13 = 0
            r14 = 1
        L_0x0063:
            if (r13 >= r11) goto L_0x0074
            r15 = r8[r13]     // Catch:{ Throwable -> 0x013e }
            java.lang.String r4 = r10.packageName     // Catch:{ Throwable -> 0x013e }
            boolean r4 = r4.contains(r15)     // Catch:{ Throwable -> 0x013e }
            if (r4 == 0) goto L_0x0070
            r14 = 0
        L_0x0070:
            int r13 = r13 + 1
            r4 = 0
            goto L_0x0063
        L_0x0074:
            if (r14 != 0) goto L_0x0078
        L_0x0076:
            r4 = 0
            goto L_0x0040
        L_0x0078:
            int r4 = r9.length     // Catch:{ Throwable -> 0x013e }
            if (r4 < r7) goto L_0x009b
            r4 = 0
            r11 = r9[r4]     // Catch:{ Throwable -> 0x013e }
            boolean r4 = r11.equals(r12)     // Catch:{ Throwable -> 0x013e }
            if (r4 != 0) goto L_0x009b
            int r4 = r9.length     // Catch:{ Throwable -> 0x013e }
            r11 = 0
        L_0x0086:
            if (r11 >= r4) goto L_0x0097
            r13 = r9[r11]     // Catch:{ Throwable -> 0x013e }
            java.lang.String r14 = r10.packageName     // Catch:{ Throwable -> 0x013e }
            boolean r13 = r14.contains(r13)     // Catch:{ Throwable -> 0x013e }
            if (r13 != 0) goto L_0x0094
            r4 = 0
            goto L_0x0098
        L_0x0094:
            int r11 = r11 + 1
            goto L_0x0086
        L_0x0097:
            r4 = 1
        L_0x0098:
            if (r4 != 0) goto L_0x009b
            goto L_0x0076
        L_0x009b:
            int r4 = r6.length     // Catch:{ Throwable -> 0x013e }
            if (r4 < r7) goto L_0x00be
            r4 = 0
            r11 = r6[r4]     // Catch:{ Throwable -> 0x013e }
            boolean r4 = r11.equals(r12)     // Catch:{ Throwable -> 0x013e }
            if (r4 != 0) goto L_0x00be
            int r4 = r6.length     // Catch:{ Throwable -> 0x013e }
            r11 = 0
        L_0x00a9:
            if (r11 >= r4) goto L_0x00ba
            r13 = r6[r11]     // Catch:{ Throwable -> 0x013e }
            java.lang.String r14 = r10.packageName     // Catch:{ Throwable -> 0x013e }
            boolean r13 = r14.startsWith(r13)     // Catch:{ Throwable -> 0x013e }
            if (r13 == 0) goto L_0x00b7
            r4 = 0
            goto L_0x00bb
        L_0x00b7:
            int r11 = r11 + 1
            goto L_0x00a9
        L_0x00ba:
            r4 = 1
        L_0x00bb:
            if (r4 != 0) goto L_0x00be
            goto L_0x0076
        L_0x00be:
            int r4 = r5.length     // Catch:{ Throwable -> 0x013e }
            if (r4 < r7) goto L_0x00e2
            r4 = 0
            r11 = r5[r4]     // Catch:{ Throwable -> 0x013e }
            boolean r11 = r11.equals(r12)     // Catch:{ Throwable -> 0x013e }
            if (r11 != 0) goto L_0x00e3
            int r11 = r5.length     // Catch:{ Throwable -> 0x013e }
            r12 = 0
        L_0x00cc:
            if (r12 >= r11) goto L_0x00dd
            r13 = r5[r12]     // Catch:{ Throwable -> 0x013e }
            java.lang.String r14 = r10.packageName     // Catch:{ Throwable -> 0x013e }
            boolean r13 = r14.endsWith(r13)     // Catch:{ Throwable -> 0x013e }
            if (r13 == 0) goto L_0x00da
            r11 = 0
            goto L_0x00de
        L_0x00da:
            int r12 = r12 + 1
            goto L_0x00cc
        L_0x00dd:
            r11 = 1
        L_0x00de:
            if (r11 != 0) goto L_0x00e3
            goto L_0x0040
        L_0x00e2:
            r4 = 0
        L_0x00e3:
            r2.add(r10)     // Catch:{ Throwable -> 0x013e }
            goto L_0x0040
        L_0x00e8:
            boolean r3 = r2.isEmpty()     // Catch:{ Throwable -> 0x013e }
            if (r3 != 0) goto L_0x0142
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013e }
            r3.<init>()     // Catch:{ Throwable -> 0x013e }
            java.util.Iterator r5 = r2.iterator()     // Catch:{ Throwable -> 0x013e }
        L_0x00f7:
            boolean r6 = r5.hasNext()     // Catch:{ Throwable -> 0x013e }
            if (r6 == 0) goto L_0x0136
            java.lang.Object r6 = r5.next()     // Catch:{ Throwable -> 0x013e }
            android.content.pm.PackageInfo r6 = (android.content.pm.PackageInfo) r6     // Catch:{ Throwable -> 0x013e }
            java.lang.String r7 = "#"
            if (r4 != 0) goto L_0x011d
            java.lang.String r8 = r6.packageName     // Catch:{ Throwable -> 0x013e }
            r3.append(r8)     // Catch:{ Throwable -> 0x013e }
            r3.append(r7)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r8 = r6.versionName     // Catch:{ Throwable -> 0x013e }
            r3.append(r8)     // Catch:{ Throwable -> 0x013e }
            r3.append(r7)     // Catch:{ Throwable -> 0x013e }
            int r6 = r6.versionCode     // Catch:{ Throwable -> 0x013e }
        L_0x0119:
            r3.append(r6)     // Catch:{ Throwable -> 0x013e }
            goto L_0x0133
        L_0x011d:
            r3.append(r0)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r8 = r6.packageName     // Catch:{ Throwable -> 0x013e }
            r3.append(r8)     // Catch:{ Throwable -> 0x013e }
            r3.append(r7)     // Catch:{ Throwable -> 0x013e }
            java.lang.String r8 = r6.versionName     // Catch:{ Throwable -> 0x013e }
            r3.append(r8)     // Catch:{ Throwable -> 0x013e }
            r3.append(r7)     // Catch:{ Throwable -> 0x013e }
            int r6 = r6.versionCode     // Catch:{ Throwable -> 0x013e }
            goto L_0x0119
        L_0x0133:
            int r4 = r4 + 1
            goto L_0x00f7
        L_0x0136:
            r2.clear()     // Catch:{ Throwable -> 0x013e }
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x013e }
            return r0
        L_0x013e:
            r0 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)
        L_0x0142:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p069a.p075f.C1480a.m2729g():java.lang.String");
    }

    /* renamed from: h */
    private String m2730h() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(C1541i.m3030g(C1490c.f2747a) ? 1 : 0);
            sb.append(",");
            sb.append("-1");
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        return sb.toString();
    }

    /* renamed from: i */
    private String m2731i() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: b */
    public void mo15034b() {
        String str;
        C1540h.m2997b("GBD_LFAction", "doSample");
        String str2 = null;
        try {
            str = C1541i.m3025e(C1490c.f2747a);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            str = null;
        }
        try {
            str2 = C1541i.m3027f(C1490c.f2747a);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
        if (C1490c.f2762p != null ? !C1490c.f2762p.equals(str) : str != null) {
            C1490c.f2762p = str;
            C1507f.m2840a().mo15121c();
        }
        if (C1490c.f2763q != null ? !C1490c.f2763q.equals(str2) : str2 != null) {
            C1490c.f2763q = str2;
            C1507f.m2840a().mo15124d();
        }
        try {
            m2726d();
        } catch (Throwable th2) {
            C1540h.m2996a(th2);
        }
    }

    /* renamed from: c */
    public int mo15035c() {
        return 31;
    }
}
