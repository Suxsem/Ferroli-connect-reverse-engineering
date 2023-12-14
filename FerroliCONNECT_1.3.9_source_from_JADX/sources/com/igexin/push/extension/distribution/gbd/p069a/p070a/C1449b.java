package com.igexin.push.extension.distribution.gbd.p069a.p070a;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.extension.distribution.gbd.p086i.C1553u;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.android.agoo.common.AgooConstants;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import p110io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.a.b */
public class C1449b {

    /* renamed from: c */
    private static C1449b f2497c;

    /* renamed from: a */
    private long f2498a = 0;

    /* renamed from: b */
    private long f2499b = 0;

    /* renamed from: d */
    private Context f2500d;

    private C1449b(Context context) {
        this.f2500d = context;
    }

    /* renamed from: a */
    public static C1449b m2546a() {
        if (f2497c == null) {
            f2497c = new C1449b(C1343f.f2169f);
        }
        return f2497c;
    }

    /* renamed from: a */
    private String m2547a(int i, int i2) {
        try {
            if (!C1507f.m2840a().mo15127e()) {
                return null;
            }
            List<String> g = Build.VERSION.SDK_INT < 21 ? m2554g() : null;
            List<String> h = m2555h();
            if (g == null && h == null) {
                return null;
            }
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(C1541i.m3034i()));
            StringBuilder sb = new StringBuilder();
            sb.append(format);
            sb.append("|");
            sb.append(C1343f.f2182s);
            sb.append("|");
            sb.append(C1343f.f2135a);
            sb.append("|");
            if (i != 0) {
                sb.append(i);
            }
            sb.append("|");
            if (i2 != 0) {
                sb.append(i2);
            }
            sb.append("|");
            sb.append("|");
            sb.append(m2548a(g, 10));
            sb.append("|");
            sb.append(m2551b(h, C1488a.f2643a));
            sb.append("|");
            sb.append("ANDROID");
            String f = m2553f();
            if (!TextUtils.isEmpty(f)) {
                sb.append(f);
            }
            return sb.toString();
        } catch (Exception e) {
            C1540h.m2996a(e);
            return null;
        }
    }

    /* renamed from: a */
    private String m2548a(List<String> list, int i) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        for (String next : list) {
            if (!arrayList.contains(next)) {
                arrayList.add(next);
                if (i2 != 0) {
                    sb.append(",");
                }
                sb.append(next);
                i2++;
                if (i2 >= i) {
                    break;
                }
            }
        }
        return sb.toString();
    }

    /* renamed from: a */
    private List<String> m2549a(List<ActivityManager.RecentTaskInfo> list) {
        ArrayList arrayList = new ArrayList();
        for (ActivityManager.RecentTaskInfo recentTaskInfo : list) {
            arrayList.add(recentTaskInfo.baseIntent.getComponent().getPackageName());
        }
        return arrayList;
    }

    /* renamed from: a */
    private void m2550a(String str) {
        C1503b.m2819a().mo15111b(str, mo15000e());
        C1540h.m2997b("GBD_RNRAA", "save type = " + mo15000e());
    }

    /* renamed from: b */
    private String m2551b(List<String> list, int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList arrayList3 = new ArrayList();
        if (list == null || list.size() == 0) {
            return "";
        }
        for (String next : list) {
            if (!arrayList2.contains(next)) {
                arrayList2.add(next);
            }
        }
        for (String str : arrayList2) {
            if (str.contains(":")) {
                String[] split = str.split(":");
                if (split.length == 2) {
                    if (arrayList3.contains(split[0])) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= arrayList.size()) {
                                break;
                            }
                            String str2 = (String) arrayList.get(i2);
                            if (str2.contains(split[0] + ":")) {
                                arrayList.set(i2, str2 + DispatchConstants.SIGN_SPLIT_SYMBOL + split[1]);
                                break;
                            }
                            i2++;
                        }
                    } else {
                        arrayList3.add(split[0]);
                    }
                }
            }
            arrayList.add(str);
        }
        int min = Math.min(arrayList.size(), i);
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < min; i3++) {
            try {
                String str3 = (String) arrayList.get(i3);
                sb.append(str3);
                if (str3.contains(":")) {
                    str3 = str3.split(":")[0];
                }
                long j = 0;
                long longValue = C1490c.f2759m.containsKey(str3) ? C1490c.f2759m.get(str3).longValue() : 0;
                if (longValue <= C1541i.m3034i()) {
                    j = longValue;
                }
                sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
                sb.append(j);
                if (i3 < min - 1) {
                    sb.append(",");
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
        return sb.toString();
    }

    /* renamed from: b */
    private boolean m2552b(String str) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (str.contains(":")) {
                str = str.split(":")[0];
            }
            String[] split = C1488a.f2626J.split("\\|");
            if (split.length != 4) {
                return false;
            }
            String[] split2 = split[0].split(",");
            String[] split3 = split[1].split(",");
            String[] split4 = split[2].split(",");
            String[] split5 = split[3].split(",");
            if (split2.length >= 1) {
                if (!split2[0].equals(SchedulerSupport.NONE)) {
                    int length = split2.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        } else if (str.startsWith(split2[i])) {
                            z = false;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            z = true;
            if (split3.length >= 1 && !split3[0].equals(SchedulerSupport.NONE)) {
                int length2 = split3.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length2) {
                        break;
                    } else if (str.contains(split3[i2])) {
                        z2 = false;
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            z2 = true;
            if (split4.length >= 1 && !split4[0].equals(SchedulerSupport.NONE)) {
                int length3 = split4.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length3) {
                        break;
                    } else if (!str.contains(split4[i3])) {
                        z3 = false;
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            z3 = true;
            if (split5.length >= 1 && !split5[0].equals(SchedulerSupport.NONE)) {
                int length4 = split5.length;
                int i4 = 0;
                while (true) {
                    if (i4 >= length4) {
                        break;
                    } else if (str.endsWith(split5[i4])) {
                        z4 = false;
                        break;
                    } else {
                        i4++;
                    }
                }
                return !z ? false : false;
            }
            z4 = true;
            return !z ? false : false;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b7 A[Catch:{ Throwable -> 0x0188 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c3 A[Catch:{ Throwable -> 0x0188 }] */
    /* renamed from: f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m2553f() {
        /*
            r14 = this;
            r0 = 0
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ Throwable -> 0x0188 }
            r1.<init>()     // Catch:{ Throwable -> 0x0188 }
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ Throwable -> 0x0188 }
            r2.<init>()     // Catch:{ Throwable -> 0x0188 }
            android.content.Context r3 = r14.f2500d     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r4 = "activity"
            java.lang.Object r3 = r3.getSystemService(r4)     // Catch:{ Throwable -> 0x0188 }
            android.app.ActivityManager r3 = (android.app.ActivityManager) r3     // Catch:{ Throwable -> 0x0188 }
            r4 = 2000(0x7d0, float:2.803E-42)
            java.util.List r3 = r3.getRunningServices(r4)     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r4 = "GBD_RNRAA"
            if (r3 == 0) goto L_0x0182
            boolean r5 = r3.isEmpty()     // Catch:{ Throwable -> 0x0188 }
            if (r5 == 0) goto L_0x0027
            goto L_0x0182
        L_0x0027:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0188 }
            r5.<init>()     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r6 = "rsl "
            r5.append(r6)     // Catch:{ Throwable -> 0x0188 }
            int r6 = r3.size()     // Catch:{ Throwable -> 0x0188 }
            r5.append(r6)     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0188 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r4, r5)     // Catch:{ Throwable -> 0x0188 }
            int r5 = r3.size()     // Catch:{ Throwable -> 0x0188 }
            r6 = 200(0xc8, float:2.8E-43)
            if (r5 <= r6) goto L_0x0051
            java.lang.String r5 = "rsl > 200, cut."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r4, r5)     // Catch:{ Throwable -> 0x0188 }
            r5 = 0
            java.util.List r3 = r3.subList(r5, r6)     // Catch:{ Throwable -> 0x0188 }
        L_0x0051:
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x0188 }
        L_0x0055:
            boolean r5 = r3.hasNext()     // Catch:{ Throwable -> 0x0188 }
            r6 = -1
            if (r5 == 0) goto L_0x00cd
            java.lang.Object r5 = r3.next()     // Catch:{ Throwable -> 0x0188 }
            android.app.ActivityManager$RunningServiceInfo r5 = (android.app.ActivityManager.RunningServiceInfo) r5     // Catch:{ Throwable -> 0x0188 }
            android.content.ComponentName r8 = r5.service     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r8 = r8.getPackageName()     // Catch:{ Throwable -> 0x0188 }
            android.content.ComponentName r9 = r5.service     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r9 = r9.getClassName()     // Catch:{ Throwable -> 0x0188 }
            boolean r10 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0188 }
            if (r10 != 0) goto L_0x0055
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x0188 }
            if (r10 == 0) goto L_0x007c
            goto L_0x0055
        L_0x007c:
            long r10 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3034i()     // Catch:{ Throwable -> 0x0188 }
            long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Throwable -> 0x0188 }
            long r10 = r10 - r12
            long r12 = r5.activeSince     // Catch:{ Throwable -> 0x0188 }
            long r10 = r10 + r12
            r12 = 0
            int r5 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r5 > 0) goto L_0x008f
            goto L_0x0090
        L_0x008f:
            r6 = r10
        L_0x0090:
            boolean r5 = r2.containsKey(r8)     // Catch:{ Throwable -> 0x0188 }
            if (r5 != 0) goto L_0x009e
            java.lang.Long r5 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x0188 }
        L_0x009a:
            r2.put(r8, r5)     // Catch:{ Throwable -> 0x0188 }
            goto L_0x00b1
        L_0x009e:
            java.lang.Object r5 = r2.get(r8)     // Catch:{ Throwable -> 0x0188 }
            java.lang.Long r5 = (java.lang.Long) r5     // Catch:{ Throwable -> 0x0188 }
            long r10 = r5.longValue()     // Catch:{ Throwable -> 0x0188 }
            int r5 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x00b1
            java.lang.Long r5 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x0188 }
            goto L_0x009a
        L_0x00b1:
            boolean r5 = r1.containsKey(r8)     // Catch:{ Throwable -> 0x0188 }
            if (r5 != 0) goto L_0x00c3
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0188 }
            r5.<init>()     // Catch:{ Throwable -> 0x0188 }
            r5.add(r9)     // Catch:{ Throwable -> 0x0188 }
            r1.put(r8, r5)     // Catch:{ Throwable -> 0x0188 }
            goto L_0x0055
        L_0x00c3:
            java.lang.Object r5 = r1.get(r8)     // Catch:{ Throwable -> 0x0188 }
            java.util.List r5 = (java.util.List) r5     // Catch:{ Throwable -> 0x0188 }
            r5.add(r9)     // Catch:{ Throwable -> 0x0188 }
            goto L_0x0055
        L_0x00cd:
            boolean r3 = r1.isEmpty()     // Catch:{ Throwable -> 0x0188 }
            if (r3 == 0) goto L_0x00d9
            java.lang.String r1 = "rs empty."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r4, r1)     // Catch:{ Throwable -> 0x0188 }
            return r0
        L_0x00d9:
            java.util.Set r1 = r1.entrySet()     // Catch:{ Throwable -> 0x0188 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Throwable -> 0x0188 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0188 }
            r3.<init>()     // Catch:{ Throwable -> 0x0188 }
        L_0x00e6:
            boolean r4 = r1.hasNext()     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r5 = ","
            if (r4 == 0) goto L_0x016a
            java.lang.Object r4 = r1.next()     // Catch:{ Throwable -> 0x0188 }
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4     // Catch:{ Throwable -> 0x0188 }
            java.lang.Object r8 = r4.getKey()     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Throwable -> 0x0188 }
            java.lang.Object r4 = r4.getValue()     // Catch:{ Throwable -> 0x0188 }
            java.util.List r4 = (java.util.List) r4     // Catch:{ Throwable -> 0x0188 }
            boolean r9 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2653aJ     // Catch:{ Throwable -> 0x0188 }
            if (r9 != 0) goto L_0x010b
            boolean r9 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3023d((java.lang.String) r8)     // Catch:{ Throwable -> 0x0188 }
            if (r9 == 0) goto L_0x010b
            goto L_0x00e6
        L_0x010b:
            r3.append(r8)     // Catch:{ Throwable -> 0x0188 }
            java.lang.Object r8 = r2.get(r8)     // Catch:{ Throwable -> 0x0188 }
            java.lang.Long r8 = (java.lang.Long) r8     // Catch:{ Throwable -> 0x0188 }
            if (r8 == 0) goto L_0x011b
            long r8 = r8.longValue()     // Catch:{ Throwable -> 0x0188 }
            goto L_0x011c
        L_0x011b:
            r8 = r6
        L_0x011c:
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ Throwable -> 0x0188 }
            boolean r9 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3028f()     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r10 = "#"
            if (r9 != 0) goto L_0x0132
            r3.append(r10)     // Catch:{ Throwable -> 0x0188 }
            r3.append(r8)     // Catch:{ Throwable -> 0x0188 }
        L_0x012e:
            r3.append(r5)     // Catch:{ Throwable -> 0x0188 }
            goto L_0x00e6
        L_0x0132:
            java.lang.String r9 = ":"
            r3.append(r9)     // Catch:{ Throwable -> 0x0188 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Throwable -> 0x0188 }
        L_0x013b:
            boolean r9 = r4.hasNext()     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r11 = "&"
            if (r9 == 0) goto L_0x0150
            java.lang.Object r9 = r4.next()     // Catch:{ Throwable -> 0x0188 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Throwable -> 0x0188 }
            r3.append(r9)     // Catch:{ Throwable -> 0x0188 }
            r3.append(r11)     // Catch:{ Throwable -> 0x0188 }
            goto L_0x013b
        L_0x0150:
            java.lang.String r4 = r3.toString()     // Catch:{ Throwable -> 0x0188 }
            boolean r4 = r4.endsWith(r11)     // Catch:{ Throwable -> 0x0188 }
            if (r4 == 0) goto L_0x0163
            int r4 = r3.length()     // Catch:{ Throwable -> 0x0188 }
            int r4 = r4 + -1
            r3.deleteCharAt(r4)     // Catch:{ Throwable -> 0x0188 }
        L_0x0163:
            r3.append(r10)     // Catch:{ Throwable -> 0x0188 }
            r3.append(r8)     // Catch:{ Throwable -> 0x0188 }
            goto L_0x012e
        L_0x016a:
            java.lang.String r1 = r3.toString()     // Catch:{ Throwable -> 0x0188 }
            boolean r1 = r1.endsWith(r5)     // Catch:{ Throwable -> 0x0188 }
            if (r1 == 0) goto L_0x017d
            int r1 = r3.length()     // Catch:{ Throwable -> 0x0188 }
            int r1 = r1 + -1
            r3.deleteCharAt(r1)     // Catch:{ Throwable -> 0x0188 }
        L_0x017d:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x0188 }
            return r0
        L_0x0182:
            java.lang.String r1 = "rsl empty."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r4, r1)     // Catch:{ Throwable -> 0x0188 }
            return r0
        L_0x0188:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p069a.p070a.C1449b.m2553f():java.lang.String");
    }

    /* renamed from: g */
    private List<String> m2554g() {
        try {
            return m2549a(((ActivityManager) this.f2500d.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRecentTasks(10, 1));
        } catch (Exception e) {
            C1540h.m2996a(e);
            return null;
        }
    }

    /* renamed from: h */
    private List<String> m2555h() {
        try {
            List<String> a = C1553u.m3060a(true, false);
            if (a == null || a.isEmpty() || (a.size() == 1 && a.get(0).equals(this.f2500d.getPackageName()))) {
                return null;
            }
            try {
                Iterator<String> it = a.iterator();
                while (it.hasNext()) {
                    if (!m2552b(it.next())) {
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
            return a;
        } catch (Exception e) {
            C1540h.m2996a(e);
            return null;
        }
    }

    /* renamed from: b */
    public void mo14997b() {
        try {
            C1540h.m2997b("GBD_RNRAA", "unlock.");
            this.f2498a = System.currentTimeMillis();
            if (this.f2498a - this.f2499b >= ((long) (C1488a.f2659aP * 1000))) {
                String a = m2547a(1, 0);
                if (!TextUtils.isEmpty(a)) {
                    m2550a(a);
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: c */
    public void mo14998c() {
        try {
            C1540h.m2997b("GBD_RNRAA", "lock.");
            this.f2499b = System.currentTimeMillis();
            if (this.f2499b - this.f2498a >= ((long) (C1488a.f2659aP * 1000))) {
                String a = m2547a(2, 0);
                if (!TextUtils.isEmpty(a)) {
                    m2550a(a);
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: d */
    public void mo14999d() {
        C1540h.m2997b("GBD_RNRAA", "doSample");
        try {
            if (C1541i.m3017c(this.f2500d) && System.currentTimeMillis() - this.f2498a >= C1488a.f2620D * 1000) {
                String a = m2547a(0, 1);
                if (!TextUtils.isEmpty(a)) {
                    m2550a(a);
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: e */
    public int mo15000e() {
        return 24;
    }
}
