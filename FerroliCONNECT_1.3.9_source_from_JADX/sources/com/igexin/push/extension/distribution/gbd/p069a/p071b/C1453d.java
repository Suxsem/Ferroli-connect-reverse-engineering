package com.igexin.push.extension.distribution.gbd.p069a.p071b;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.contrarywind.timer.MessageHandler;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1489b;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.extension.distribution.gbd.p086i.C1553u;
import com.igexin.sdk.GActivity;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.common.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.json.JSONObject;
import p110io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.b.d */
public class C1453d {

    /* renamed from: d */
    private static C1453d f2503d;

    /* renamed from: a */
    private Context f2504a;

    /* renamed from: b */
    private SimpleDateFormat f2505b;

    /* renamed from: c */
    private HashMap<String, String> f2506c = new HashMap<>();

    /* renamed from: e */
    private Map<String, C1456g> f2507e = new HashMap();

    private C1453d(Context context) {
        this.f2504a = context;
        this.f2505b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    /* renamed from: a */
    public static synchronized C1453d m2573a() {
        C1453d dVar;
        synchronized (C1453d.class) {
            if (f2503d == null) {
                f2503d = new C1453d(C1490c.f2747a);
            }
            dVar = f2503d;
        }
        return dVar;
    }

    /* renamed from: a */
    private C1455f m2574a(int i, String str, List<String> list, boolean z, String str2, String str3) {
        int i2;
        Intent intent;
        String str4 = str;
        boolean z2 = z;
        String d = m2595d(list);
        try {
            C1456g gVar = this.f2507e.get(str4);
            i2 = i;
            if (i2 == 1) {
                try {
                    C1540h.m2997b("GBD_GGTSA", "start aGuard pkg = " + str4);
                    if (!(gVar == null || gVar == C1456g.GACTIVITY)) {
                        C1540h.m2997b("GBD_GGTSA", "start aGuard, pkg = " + str4 + "|not support act");
                    }
                    if (gVar == null || gVar == C1456g.GACTIVITY) {
                        boolean f = m2598f(str4);
                        Intent intent2 = new Intent();
                        if (!C1488a.f2650aG) {
                            intent = null;
                        } else if (C1488a.f2647aD) {
                            C1540h.m2997b("GBD_GGTSA", "d-a from local.");
                            intent = m2596e(str4);
                        } else {
                            C1540h.m2997b("GBD_GGTSA", "d-a from config.");
                            intent = m2594d(str4);
                        }
                        if (intent != null) {
                            i2 = 3;
                            C1540h.m2997b("GBD_GGTSA", "in DA mode.");
                            intent2 = intent;
                        } else {
                            intent2.setClassName(str4, "com.igexin.sdk.MActivity");
                        }
                        if (f) {
                            intent2.putExtra(PushConsts.CMD_ACTION, C1489b.f2722b);
                            intent2.putExtra("isSlave", false);
                        }
                        Intent b = m2587b(str4, intent2);
                        if (!C1541i.m3018c(b, this.f2504a) || !m2585a(str4, b)) {
                            b.setClassName(str4, GActivity.TAG);
                            if (!C1541i.m3018c(b, this.f2504a) || !m2585a(str4, b)) {
                                i2 = 1;
                            } else {
                                C1540h.m2997b("GBD_GGTSA", "GA guard success, force = " + f);
                                return new C1455f(1, true, d);
                            }
                        } else {
                            C1540h.m2997b("GBD_GGTSA", "MA or DA guard success, force = " + f);
                            return new C1455f(i2, true, d);
                        }
                    }
                    C1540h.m2997b("GBD_GGTSA", "aGuard failed, useServiceGuard = " + z2);
                    if (!z2) {
                        return new C1455f(i2, false, d);
                    }
                    i2 = 2;
                } catch (Throwable th) {
                    th = th;
                    C1540h.m2996a(th);
                    C1540h.m2997b("GBD_GGTSA", "startGuard exception = " + th.getMessage());
                    C1458i.m2619a(str2, i2, str3, 2);
                    return new C1455f(i2, false, d);
                }
            }
            if (i2 == 2) {
                C1540h.m2997b("GBD_GGTSA", "start sGuard = " + str4);
                if (!(gVar == null || gVar == C1456g.SERVICE)) {
                    C1540h.m2997b("GBD_GGTSA", "start sGuard = " + str4 + "|not support service, return");
                }
                if (gVar == null || gVar == C1456g.SERVICE) {
                    m2580a(str4, d);
                }
            }
            return new C1455f(i2, true, d);
        } catch (Throwable th2) {
            th = th2;
            i2 = i;
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GGTSA", "startGuard exception = " + th.getMessage());
            C1458i.m2619a(str2, i2, str3, 2);
            return new C1455f(i2, false, d);
        }
    }

    /* renamed from: a */
    private List<String> m2575a(int i, boolean z) {
        Map<String, C1456g> map;
        C1456g gVar;
        int i2 = i;
        if (TextUtils.isEmpty(C1488a.f2670aa)) {
            C1540h.m2997b("GBD_GGTSA", "pMBlacklist is empty or null");
            return null;
        }
        String[] split = C1488a.f2670aa.split(",");
        if (split.length == 0) {
            C1540h.m2997b("GBD_GGTSA", "pMBlacklist is empty or null");
            return null;
        }
        String b = C1541i.m3010b();
        if (TextUtils.isEmpty(b)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        this.f2507e.clear();
        int length = split.length;
        char c = 0;
        int i3 = 0;
        while (i3 < length) {
            String str = split[i3];
            if (!TextUtils.isEmpty(str)) {
                String[] split2 = str.split(":");
                boolean z2 = true;
                String str2 = split2[1];
                if (split2.length == 4 && b.equalsIgnoreCase(split2[c]) && !TextUtils.isEmpty(str2) && !arrayList.contains(str2)) {
                    if (!C1541i.m3019c(str2, C1490c.f2747a)) {
                        C1540h.m2997b("GBD_GGTSA", "pkg = " + str2 + " not install");
                    } else {
                        boolean parseBoolean = Boolean.parseBoolean(split2[2]);
                        boolean parseBoolean2 = Boolean.parseBoolean(split2[3]);
                        C1540h.m2997b("GBD_GGTSA", "brand = " + b + "|pkg = " + str2 + "|aEnable = " + parseBoolean + "|sEnable = " + parseBoolean2);
                        if (!parseBoolean || !parseBoolean2) {
                            if (parseBoolean || parseBoolean2) {
                                if (i2 == 2 && !parseBoolean2) {
                                    C1540h.m2997b("GBD_GGTSA", "guard type = " + i2 + "|pkg = " + str2 + " matched, in pm black list ###");
                                } else if (i2 == 1) {
                                    if (parseBoolean) {
                                        Intent intent = new Intent();
                                        intent.setClassName(str2, "com.igexin.sdk.MActivity");
                                        boolean c2 = C1541i.m3018c(intent, this.f2504a);
                                        intent.setClassName(str2, GActivity.TAG);
                                        if (!c2 && !C1541i.m3018c(intent, this.f2504a)) {
                                            z2 = false;
                                        }
                                        if (!z2) {
                                            arrayList.add(str2);
                                            C1540h.m2997b("GBD_GGTSA", "getPMGuardBlackList-> " + str2 + " activitySet = false, add to pMGuardBlackList");
                                        } else {
                                            map = this.f2507e;
                                            gVar = C1456g.GACTIVITY;
                                        }
                                    } else if (z) {
                                        map = this.f2507e;
                                        gVar = C1456g.SERVICE;
                                    }
                                    map.put(str2, gVar);
                                }
                            }
                            arrayList.add(str2);
                        }
                    }
                }
            }
            i3++;
            c = 0;
        }
        C1540h.m2997b("GBD_GGTSA", "PMGuardBlack-> = " + arrayList.toString());
        return arrayList;
    }

    /* renamed from: a */
    private List<String> m2576a(Map<String, List<String>> map, List<String> list, int i, boolean z) {
        Map<String, List<String>> map2 = map;
        List<String> list2 = list;
        ArrayList<String> arrayList = new ArrayList<>();
        if (map2 != null && !map.isEmpty()) {
            if (!SchedulerSupport.NONE.equalsIgnoreCase(C1488a.f2655aL) && !TextUtils.isEmpty(C1488a.f2655aL)) {
                try {
                    if (this.f2506c == null) {
                        this.f2506c = new HashMap<>();
                    }
                    this.f2506c.clear();
                    String[] split = C1488a.f2655aL.split(",");
                    if (split != null && split.length > 0) {
                        for (String split2 : split) {
                            String[] split3 = split2.split(":");
                            if (split3 != null) {
                                if (split3.length == 3) {
                                    this.f2506c.put(split3[0], split3[1] + ":" + split3[2]);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    C1540h.m2996a(th);
                }
            }
            if (list2 != null && !list.isEmpty()) {
                C1540h.m2997b("GBD_GGTSA", "remove all running, running =  " + list.toString());
                Iterator<Map.Entry<String, List<String>>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    if (list2.contains((String) it.next().getKey())) {
                        it.remove();
                    }
                }
            }
            C1540h.m2997b("GBD_GGTSA", "after remove running, guardList =  " + map.toString());
            if (!C1488a.f2617A.equals(SchedulerSupport.NONE)) {
                ArrayList arrayList2 = new ArrayList(Arrays.asList(C1488a.f2617A.split(",")));
                Iterator<Map.Entry<String, List<String>>> it2 = map.entrySet().iterator();
                while (it2.hasNext()) {
                    if (m2586a((String) it2.next().getKey(), (List<String>) arrayList2)) {
                        it2.remove();
                    }
                }
            }
            C1540h.m2997b("GBD_GGTSA", "after remove blacklist, guardList =  " + map.toString());
            List<String> a = m2575a(i, z);
            if (a != null && !a.isEmpty()) {
                Iterator<Map.Entry<String, List<String>>> it3 = map.entrySet().iterator();
                while (it3.hasNext()) {
                    if (a.contains((String) it3.next().getKey())) {
                        it3.remove();
                    }
                }
            }
            C1540h.m2997b("GBD_GGTSA", "after remove pm blacklist pkgs, guardList =  " + map.toString());
            if (!TextUtils.isEmpty(C1488a.f2618B) && !C1488a.f2618B.equals(SchedulerSupport.NONE) && !map.isEmpty()) {
                ArrayList<String> arrayList3 = new ArrayList<>(Arrays.asList(C1488a.f2618B.split(",")));
                C1540h.m2997b("GBD_GGTSA", "white list = " + arrayList3.toString());
                if (!arrayList3.isEmpty()) {
                    for (String str : arrayList3) {
                        if (map2.containsKey(str)) {
                            arrayList.add(str);
                        }
                    }
                }
            }
            if (!map.isEmpty()) {
                ArrayList arrayList4 = new ArrayList(map.keySet());
                for (String str2 : arrayList) {
                    if (arrayList4.contains(str2)) {
                        arrayList4.remove(str2);
                    }
                }
                Collections.shuffle(arrayList4);
                arrayList.addAll(arrayList4);
            }
            C1540h.m2997b("GBD_GGTSA", "after add all whitelist, guardList =  " + arrayList.toString());
        }
        return arrayList;
    }

    /* renamed from: a */
    private Map<String, List<String>> m2577a(List<String> list) {
        Map<String, List<String>> b = C1450a.m2560a().mo15001b();
        if (b.isEmpty()) {
            return b;
        }
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) this.f2504a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningServices(MessageHandler.WHAT_SMOOTH_SCROLL);
        if (runningServices == null || runningServices.isEmpty()) {
            C1540h.m2997b("GBD_GGTSA", "rsi empty.");
            return b;
        }
        C1540h.m2997b("GBD_GGTSA", "rsi " + runningServices.size());
        for (Map.Entry next : b.entrySet()) {
            String str = (String) next.getKey();
            Iterator it = ((List) next.getValue()).iterator();
            while (true) {
                if (it.hasNext()) {
                    if (C1541i.m3008a((String) it.next(), str, runningServices) && !list.contains(str)) {
                        list.add(str);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return b;
    }

    /* renamed from: a */
    private void m2578a(int i) {
        try {
            ArrayList arrayList = new ArrayList();
            Map<String, List<String>> a = m2577a((List<String>) arrayList);
            if (a.size() <= 1) {
                C1540h.m2997b("GBD_GGTSA", "hasServiceAppList size <= 1");
                return;
            }
            if (i == 2) {
                C1458i.m2619a(SchedulerSupport.NONE, i, this.f2505b.format(new Date()), 3);
            }
            m2581a((List<String>) arrayList, a, i, false);
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: a */
    private void m2579a(String str, int i, String str2, String str3) {
        try {
            C1458i.m2619a(str, i, str3, 0);
            String str4 = str.split(",")[0];
            String str5 = str.split(",")[1];
            C1540h.m2997b("GBD_GGTSA_guard", "success start " + str4 + " type = " + i);
            ArrayList arrayList = new ArrayList();
            arrayList.add(str2);
            HashMap hashMap = new HashMap();
            hashMap.put("pkgName", str4);
            hashMap.put("srvName", str5);
            hashMap.put("datetime", str3);
            hashMap.put("checkList", arrayList);
            C1458i.m2620a(hashMap, 0, i);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GGTSA", "saveResultAndCheck exception = " + th.getMessage());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0088 A[Catch:{ Throwable -> 0x00b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00d0 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[ADDED_TO_REGION, ORIG_RETURN, RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2580a(java.lang.String r11, java.lang.String r12) {
        /*
            r10 = this;
            java.lang.String r0 = "|force = "
            java.lang.String r1 = "action"
            java.lang.String r2 = "startGTService error|"
            java.lang.String r3 = "GBD_GGTSA"
            boolean r4 = r10.m2598f(r11)
            android.content.Intent r5 = new android.content.Intent
            r5.<init>()
            r6 = 1
            r7 = 0
            r5.setClassName(r11, r12)     // Catch:{ Throwable -> 0x0057 }
            android.content.Intent r5 = r10.m2587b((java.lang.String) r11, (android.content.Intent) r5)     // Catch:{ Throwable -> 0x0057 }
            android.content.Context r8 = r10.f2504a     // Catch:{ Throwable -> 0x0057 }
            boolean r8 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3007a((android.content.Intent) r5, (android.content.Context) r8)     // Catch:{ Throwable -> 0x0057 }
            if (r8 == 0) goto L_0x0055
            if (r4 == 0) goto L_0x0029
            java.lang.String r8 = com.igexin.push.extension.distribution.gbd.p077c.C1489b.f2722b     // Catch:{ Throwable -> 0x0057 }
            r5.putExtra(r1, r8)     // Catch:{ Throwable -> 0x0057 }
        L_0x0029:
            android.content.Context r8 = r10.f2504a     // Catch:{ Throwable -> 0x0057 }
            r8.startService(r5)     // Catch:{ Throwable -> 0x0057 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0052 }
            r5.<init>()     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r8 = "start service by pkg = "
            r5.append(r8)     // Catch:{ Throwable -> 0x0052 }
            r5.append(r11)     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r8 = "|service = "
            r5.append(r8)     // Catch:{ Throwable -> 0x0052 }
            r5.append(r12)     // Catch:{ Throwable -> 0x0052 }
            r5.append(r0)     // Catch:{ Throwable -> 0x0052 }
            r5.append(r4)     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r12 = r5.toString()     // Catch:{ Throwable -> 0x0052 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r12)     // Catch:{ Throwable -> 0x0052 }
            r12 = 1
            goto L_0x0073
        L_0x0052:
            r12 = move-exception
            r5 = 1
            goto L_0x0059
        L_0x0055:
            r12 = 0
            goto L_0x0073
        L_0x0057:
            r12 = move-exception
            r5 = 0
        L_0x0059:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r2)
            java.lang.String r9 = r12.getMessage()
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r8)
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r12)
            r12 = r5
        L_0x0073:
            android.content.Intent r5 = new android.content.Intent     // Catch:{ Throwable -> 0x00b3 }
            r5.<init>()     // Catch:{ Throwable -> 0x00b3 }
            r5.setPackage(r11)     // Catch:{ Throwable -> 0x00b3 }
            java.lang.String r8 = "com.igexin.sdk.action.service.message"
            r5.setAction(r8)     // Catch:{ Throwable -> 0x00b3 }
            android.content.Context r8 = r10.f2504a     // Catch:{ Throwable -> 0x00b3 }
            boolean r8 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3007a((android.content.Intent) r5, (android.content.Context) r8)     // Catch:{ Throwable -> 0x00b3 }
            if (r8 == 0) goto L_0x00b1
            if (r4 == 0) goto L_0x008f
            java.lang.String r8 = com.igexin.push.extension.distribution.gbd.p077c.C1489b.f2722b     // Catch:{ Throwable -> 0x00b3 }
            r5.putExtra(r1, r8)     // Catch:{ Throwable -> 0x00b3 }
        L_0x008f:
            android.content.Context r1 = r10.f2504a     // Catch:{ Throwable -> 0x00b3 }
            r1.startService(r5)     // Catch:{ Throwable -> 0x00b3 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00af }
            r1.<init>()     // Catch:{ Throwable -> 0x00af }
            java.lang.String r5 = "start service by action, pkg = "
            r1.append(r5)     // Catch:{ Throwable -> 0x00af }
            r1.append(r11)     // Catch:{ Throwable -> 0x00af }
            r1.append(r0)     // Catch:{ Throwable -> 0x00af }
            r1.append(r4)     // Catch:{ Throwable -> 0x00af }
            java.lang.String r11 = r1.toString()     // Catch:{ Throwable -> 0x00af }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r11)     // Catch:{ Throwable -> 0x00af }
            goto L_0x00ce
        L_0x00af:
            r11 = move-exception
            goto L_0x00b5
        L_0x00b1:
            r6 = 0
            goto L_0x00ce
        L_0x00b3:
            r11 = move-exception
            r6 = 0
        L_0x00b5:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            java.lang.String r1 = r11.getMessage()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r3, r0)
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r11)
        L_0x00ce:
            if (r6 != 0) goto L_0x00db
            if (r12 == 0) goto L_0x00d3
            goto L_0x00db
        L_0x00d3:
            java.lang.Throwable r11 = new java.lang.Throwable
            java.lang.String r12 = "startGTService error"
            r11.<init>(r12)
            throw r11
        L_0x00db:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p069a.p071b.C1453d.m2580a(java.lang.String, java.lang.String):void");
    }

    /* renamed from: a */
    private void m2581a(List<String> list, Map<String, List<String>> map, int i, boolean z) {
        Map<String, List<String>> map2 = map;
        int i2 = i;
        boolean z2 = z;
        try {
            C1540h.m2997b("GBD_GGTSA", "guard cnt = " + C1488a.f2720z + ", running cnt = " + list.size() + ", has cnt = " + map.size() + ", type = " + i2 + ", useService = " + z2);
            if (m2582a(list.size(), map.size())) {
                List<String> a = m2576a(map2, list, i2, z2);
                if (!a.isEmpty()) {
                    int min = Math.min(C1488a.f2720z - (list.size() - 1), a.size());
                    C1540h.m2997b("GBD_GGTSA", "need guard cnt = " + min + " #######");
                    if (min > 0) {
                        String format = this.f2505b.format(new Date());
                        int i3 = 0;
                        for (String next : a) {
                            List list2 = map2.get(next);
                            String packageName = this.f2504a.getPackageName();
                            if (list2 != null && !list2.isEmpty() && !TextUtils.isEmpty(next)) {
                                if (!next.equals(packageName)) {
                                    String c = m2592c(next);
                                    if (TextUtils.isEmpty(c)) {
                                        C1540h.m2997b("GBD_GGTSA", "guard pkg = " + next + ", appid is empty, ignore this ###");
                                    } else {
                                        String str = next + "," + c;
                                        C1458i.m2618a(str);
                                        String str2 = str;
                                        C1455f a2 = m2574a(i, next, list2, z, str, format);
                                        if (a2.f2509b) {
                                            m2579a(str2, a2.f2508a, a2.f2510c, format);
                                            i3++;
                                            C1540h.m2997b("GBD_GGTSA", "has guard cnt = " + i3);
                                            if (i3 >= min) {
                                                return;
                                            }
                                        }
                                    }
                                    map2 = map;
                                }
                            }
                            C1540h.m2997b("GBD_GGTSA", "pkg =  " + next + " service is empty");
                            map2 = map;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GGTSA", " startSDK " + th.getMessage());
        }
    }

    /* renamed from: a */
    private boolean m2582a(int i, int i2) {
        String str;
        if (i == i2) {
            str = "running s count = all list, need't guard ~~~";
        } else if (i - 1 < C1488a.f2720z) {
            return true;
        } else {
            str = "running cnt > " + C1488a.f2720z + ", need't guard ~~~";
        }
        C1540h.m2997b("GBD_GGTSA", str);
        return false;
    }

    /* renamed from: a */
    private boolean m2583a(C1456g gVar) {
        String str;
        if (!C1488a.f2717w) {
            str = "isGEnable = false";
        } else if (!C1541i.m3005a(this.f2504a)) {
            str = "|canScan = false";
        } else if (m2602j()) {
            str = "|romOrASdkInBlack = true";
        } else if ((gVar == C1456g.GACTIVITY && !C1488a.f2688as) || ((gVar == C1456g.SERVICE && !C1488a.f2689at) || (gVar == C1456g.ONEOF && !C1488a.f2688as && !C1488a.f2689at))) {
            str = gVar + "|aGuardEnable = " + C1488a.f2688as + "|sGuardEnable = " + C1488a.f2689at;
        } else if (gVar != C1456g.GACTIVITY) {
            return m2589b(gVar);
        } else {
            if (!m2589b(C1456g.GACTIVITY)) {
                str = "pMGuardEnable = false";
            } else if (C1541i.m3017c(this.f2504a)) {
                return m2597f();
            } else {
                C1540h.m2997b("GBD_GGTSA", "isScreenOn = false, gEnable = true");
                return true;
            }
        }
        C1540h.m2997b("GBD_GGTSA", str);
        return false;
    }

    /* renamed from: a */
    private boolean m2584a(String str) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(C1488a.f2641Y)) {
            for (String equals : C1488a.f2641Y.split(",")) {
                if (str.equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: a */
    private boolean m2585a(String str, Intent intent) {
        try {
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            this.f2504a.startActivity(intent);
            C1540h.m2997b("GBD_GGTSA", "type = start by activity, pkg = " + str);
            return true;
        } catch (Exception e) {
            C1540h.m2997b("GBD_GGTSA", e.getMessage());
            C1540h.m2996a(e);
            return false;
        }
    }

    /* renamed from: a */
    private boolean m2586a(String str, List<String> list) {
        boolean z;
        if (list != null && !list.isEmpty()) {
            for (String next : list) {
                try {
                    if (!TextUtils.isEmpty(next)) {
                        if (!next.contains(Constraint.ANY_ROLE)) {
                            if (next.equals(str)) {
                                return true;
                            }
                        } else if (next.indexOf(Constraint.ANY_ROLE) != next.length() - 1) {
                            String[] split = str.split("\\.");
                            String[] split2 = next.split("\\.");
                            if (split.length >= split2.length) {
                                int i = 0;
                                while (true) {
                                    if (i < split2.length) {
                                        if (!split2[i].equals(Constraint.ANY_ROLE) && !split2[i].equals(split[i])) {
                                            z = false;
                                            break;
                                        }
                                        i++;
                                    } else {
                                        z = true;
                                        break;
                                    }
                                }
                                if (z) {
                                    return true;
                                }
                            }
                        } else if (str.startsWith(next.replace(Constraint.ANY_ROLE, ""))) {
                            return true;
                        }
                    }
                } catch (Throwable th) {
                    C1540h.m2996a(th);
                }
            }
        }
        return false;
    }

    /* renamed from: b */
    private Intent m2587b(String str, Intent intent) {
        String[] split;
        if (intent == null) {
            return null;
        }
        try {
            if (this.f2506c != null && !this.f2506c.isEmpty() && this.f2506c.containsKey(str)) {
                String str2 = this.f2506c.get(str);
                if (!TextUtils.isEmpty(str2) && (split = str2.split(":")) != null && split.length == 2) {
                    intent.putExtra(split[0], split[1]);
                    C1540h.m2997b("GBD_GGTSA", "intent info " + split[0] + " " + split[1]);
                }
            }
            return intent;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: b */
    private Intent m2588b(String str, String str2) {
        String str3;
        long j;
        int i;
        String str4 = str;
        String str5 = str2;
        try {
            if (!TextUtils.isEmpty(str2)) {
                JSONObject jSONObject = TextUtils.isEmpty(C1490c.f2733J) ? new JSONObject() : new JSONObject(C1490c.f2733J);
                long j2 = 0;
                boolean z = false;
                if (jSONObject.has(str4)) {
                    String[] split = jSONObject.getString(str4).split(MqttTopic.MULTI_LEVEL_WILDCARD);
                    j2 = Long.valueOf(split[0]).longValue();
                    j = Long.valueOf(split[1]).longValue();
                    i = Integer.valueOf(split[2]).intValue();
                } else {
                    j = 0;
                    i = 0;
                }
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - j2 >= Constants.CLIENT_FLUSH_INTERVAL) {
                    j2 = currentTimeMillis;
                    i = 0;
                }
                if (currentTimeMillis - j >= C1488a.f2695az * 1000 && i < C1488a.f2694ay) {
                    z = true;
                }
                if (z) {
                    jSONObject.put(str4, j2 + MqttTopic.MULTI_LEVEL_WILDCARD + currentTimeMillis + MqttTopic.MULTI_LEVEL_WILDCARD + (i + 1));
                    C1507f.m2840a().mo15123c(jSONObject.toString());
                    Intent intent = new Intent();
                    intent.setClassName(str4, str5);
                    C1540h.m2997b("GBD_GGTSA_guard", "dynamic p-a " + str4 + "  " + str5);
                    return intent;
                }
                str3 = "dynamic p-a " + str4 + "  check = false";
            } else {
                str3 = str4 + " d-a null.";
            }
            C1540h.m2997b("GBD_GGTSA_guard", str3);
            return null;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: b */
    private boolean m2589b(C1456g gVar) {
        if (TextUtils.isEmpty(C1488a.f2670aa)) {
            C1540h.m2997b("GBD_GGTSA", "pMBlacklist is empty or null");
            return true;
        }
        try {
            String[] split = C1488a.f2670aa.split(",");
            if (split.length == 0) {
                C1540h.m2997b("GBD_GGTSA", "pMBlacklist is empty or null");
                return true;
            }
            String b = C1541i.m3010b();
            if (TextUtils.isEmpty(b)) {
                return true;
            }
            C1540h.m2997b("GBD_GGTSA", "brand = " + b);
            for (String str : split) {
                if (!TextUtils.isEmpty(str)) {
                    String[] split2 = str.split(":");
                    if (split2.length != 3) {
                        continue;
                    } else if (b.equalsIgnoreCase(split2[0])) {
                        boolean parseBoolean = Boolean.parseBoolean(split2[1]);
                        boolean parseBoolean2 = Boolean.parseBoolean(split2[2]);
                        C1540h.m2997b("GBD_GGTSA", "brand = " + b + "|aEnable = " + parseBoolean + "|sEnable = " + parseBoolean2);
                        if (gVar == C1456g.GACTIVITY) {
                            return parseBoolean;
                        }
                        if (gVar == C1456g.SERVICE) {
                            return parseBoolean2;
                        }
                        if (gVar == C1456g.ALL) {
                            return parseBoolean && parseBoolean2;
                        }
                        if (gVar == C1456g.ONEOF) {
                            return parseBoolean || parseBoolean2;
                        }
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: b */
    private boolean m2590b(String str) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(C1488a.f2649aF)) {
            for (String equals : C1488a.f2649aF.split(",")) {
                if (str.equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: b */
    private boolean m2591b(List<String> list) {
        if (list == null || list.isEmpty() || TextUtils.isEmpty(C1488a.f2641Y)) {
            return false;
        }
        List asList = Arrays.asList(C1488a.f2641Y.split(","));
        for (String contains : list) {
            if (asList.contains(contains)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: c */
    private String m2592c(String str) {
        String str2 = null;
        try {
            str2 = C1541i.m3011b(str, this.f2504a);
            if (TextUtils.isEmpty(str2)) {
                str2 = C1541i.m3016c(str);
            }
            C1540h.m2997b("GBD_GGTSA", "guard appid = " + str2 + "|pkg = " + str);
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        return str2;
    }

    /* renamed from: c */
    private boolean m2593c(List<String> list) {
        if (list == null || list.isEmpty() || TextUtils.isEmpty(C1488a.f2649aF)) {
            return false;
        }
        List asList = Arrays.asList(C1488a.f2649aF.split(","));
        for (String contains : list) {
            if (asList.contains(contains)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: d */
    private Intent m2594d(String str) {
        try {
            if (TextUtils.isEmpty(C1488a.f2693ax) || !C1488a.f2693ax.contains(str)) {
                C1540h.m2997b("GBD_GGTSA_guard", " not in d-a config list.");
                return null;
            }
            for (String str2 : C1488a.f2693ax.split(",")) {
                if (str.equals(str2)) {
                    return m2588b(str2, C1541i.m3026e(str2));
                }
            }
            return null;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: d */
    private String m2595d(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "com.igexin.sdk.PushService";
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        for (String next : list) {
            if (!next.equals("com.igexin.sdk.PushService") && !next.equals("com.igexin.sdk.coordinator.GexinMsgService") && !next.equals("com.igexin.sdk.coordinator.SdkMsgService")) {
                return next;
            }
        }
        return "com.igexin.sdk.PushService";
    }

    /* renamed from: e */
    private Intent m2596e(String str) {
        try {
            if (C1490c.f2738O != null) {
                if (!C1490c.f2738O.isEmpty()) {
                    if (!C1490c.f2738O.keySet().contains(str)) {
                        C1540h.m2997b("GBD_GGTSA_guard", " not in d-a local list.");
                        return null;
                    }
                    if (!TextUtils.isEmpty(C1488a.f2648aE)) {
                        ArrayList arrayList = new ArrayList(Arrays.asList(C1488a.f2648aE.split(",")));
                        if (!arrayList.isEmpty() && arrayList.contains(str)) {
                            C1540h.m2997b("GBD_GGTSA_guard", " in d-a black list.");
                            return null;
                        }
                    }
                    return m2588b(str, C1490c.f2738O.get(str));
                }
            }
            C1540h.m2997b("GBD_GGTSA_guard", "d-a map null.");
            return null;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: f */
    private boolean m2597f() {
        C1540h.m2997b("GBD_GGTSA", "Build.VERSION = " + Build.VERSION.SDK_INT);
        boolean i = m2601i();
        boolean h = m2600h();
        if (Build.VERSION.SDK_INT >= 24 && i) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 26 && h) {
            return false;
        }
        if (h || i) {
            if (Build.VERSION.SDK_INT < 21) {
                try {
                    List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) C1490c.f2747a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningTasks(1);
                    if (runningTasks == null || runningTasks.isEmpty()) {
                        return true;
                    }
                    ComponentName componentName = runningTasks.get(0).topActivity;
                    if (componentName != null) {
                        boolean a = m2584a(componentName.getPackageName());
                        boolean b = m2590b(componentName.getPackageName());
                        C1540h.m2997b("GBD_GGTSA", "Build.VERSION < 21, top app = " + componentName.getPackageName() + ",  isInBL = " + a);
                        C1540h.m2997b("GBD_GGTSA", "Build.VERSION < 21, top app = " + componentName.getPackageName() + ",  isInPBL = " + b);
                        return !a && !b;
                    }
                } catch (Throwable th) {
                    C1540h.m2997b("GBD_GGTSA", th.getMessage());
                    C1540h.m2996a(th);
                }
            } else if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT >= 24) {
                List<String> g = m2599g();
                if (g != null && !g.isEmpty()) {
                    if (g.size() != 1 || !g.get(0).equals(this.f2504a.getPackageName())) {
                        boolean b2 = m2591b(g);
                        C1540h.m2997b("GBD_GGTSA", "Build.VERSION >= 24, isInBlackList = " + b2);
                        return !b2;
                    }
                }
                C1540h.m2997b("GBD_GGTSA", "Build.VERSION >= 26, rs = null, guard = false");
            } else {
                List<String> a2 = C1553u.m3060a(false, true);
                if (a2 != null && !a2.isEmpty()) {
                    if (a2.size() != 1 || !a2.get(0).equals(this.f2504a.getPackageName())) {
                        boolean b3 = m2591b(a2);
                        boolean c = m2593c(a2);
                        C1540h.m2997b("GBD_GGTSA", "Build.VERSION >= 21 <24, isInBL = " + b3);
                        C1540h.m2997b("GBD_GGTSA", "Build.VERSION >= 21 <24, isInPBL = " + c);
                        return !b3 && !c;
                    }
                }
                C1540h.m2997b("GBD_GGTSA", "Build.VERSION >= 21 <24, recentList = null, guard = false");
                return false;
            }
            return false;
        }
        C1540h.m2997b("GBD_GGTSA", "checkBlackListInstall = false, check pbl = false, gEnable = true");
        return true;
    }

    /* renamed from: f */
    private boolean m2598f(String str) {
        try {
            if (C1488a.f2690au && !TextUtils.isEmpty(C1488a.f2691av) && !SchedulerSupport.NONE.equals(C1488a.f2691av)) {
                if (!TextUtils.isEmpty(str)) {
                    String[] split = C1488a.f2691av.split(",");
                    if (split.length == 0) {
                        return false;
                    }
                    for (String equals : split) {
                        if (str.equals(equals)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: g */
    private List<String> m2599g() {
        ArrayList arrayList = new ArrayList();
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) this.f2504a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningServices(MessageHandler.WHAT_SMOOTH_SCROLL);
            if (runningServices != null) {
                if (!runningServices.isEmpty()) {
                    for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                        String packageName = runningServiceInfo.service.getPackageName();
                        if (!arrayList.contains(packageName)) {
                            arrayList.add(packageName);
                        }
                    }
                    return arrayList;
                }
            }
            return null;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: h */
    private boolean m2600h() {
        if (!TextUtils.isEmpty(C1488a.f2641Y)) {
            for (String str : C1488a.f2641Y.split(",")) {
                if (C1541i.m3019c(str, this.f2504a)) {
                    C1540h.m2997b("GBD_GGTSA", str + " install, in blacklist");
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: i */
    private boolean m2601i() {
        if (!TextUtils.isEmpty(C1488a.f2649aF)) {
            for (String str : C1488a.f2649aF.split(",")) {
                if (C1541i.m3019c(str, this.f2504a)) {
                    C1540h.m2997b("GBD_GGTSA", str + " install, in pbl.");
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: j */
    private boolean m2602j() {
        if (TextUtils.isEmpty(C1488a.f2642Z)) {
            C1540h.m2997b("GBD_GGTSA", "romSdkIntBlack is empty or null ");
            return false;
        }
        try {
            for (String str : C1488a.f2642Z.split(",")) {
                if (!TextUtils.isEmpty(str)) {
                    String[] split = str.split(":");
                    if (split.length == 2) {
                        String str2 = split[0];
                        if (C1541i.m3015c().equals(str2) && Build.VERSION.SDK_INT == Integer.valueOf(split[1]).intValue()) {
                            C1540h.m2997b("GBD_GGTSA", "SDK_INT = " + Build.VERSION.SDK_INT + "|blacklist version int = " + Integer.valueOf(split[1]) + "|rominfo = " + str2 + "|inblacklist");
                            return true;
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        return false;
    }

    /* renamed from: b */
    public synchronized void mo15003b() {
        try {
            C1540h.m2997b("GBD_GGTSA", "first start");
            if (!C1541i.m3005a(this.f2504a)) {
                C1540h.m2997b("GBD_GGTSA", "first, scan apps = false");
                return;
            }
            ArrayList arrayList = new ArrayList();
            Map<String, List<String>> a = m2577a((List<String>) arrayList);
            for (Map.Entry<String, List<String>> key : a.entrySet()) {
                String str = (String) key.getKey();
                String b = C1541i.m3011b(str, this.f2504a);
                if (!TextUtils.isEmpty(b)) {
                    C1458i.m2618a(str + "," + b);
                }
            }
            boolean z = true;
            if (a.size() > 1) {
                if (m2583a(C1456g.ONEOF)) {
                    String format = this.f2505b.format(new Date());
                    boolean a2 = m2583a(C1456g.SERVICE);
                    boolean a3 = m2583a(C1456g.GACTIVITY);
                    C1540h.m2997b("GBD_GGTSA", "first, sEnable = " + a2 + "|aEnable = " + a3);
                    int i = 2;
                    if (!C1488a.f2633Q || !a3) {
                        if (!C1541i.m3017c(this.f2504a)) {
                            C1540h.m2997b("GBD_GGTSA", "first, screenOn = false|aEnable = " + a3);
                            if (a3) {
                            }
                        } else if (!a2) {
                            C1540h.m2997b("GBD_GGTSA", "first, sEnable = false|screenOn = true");
                            return;
                        }
                        if (i == 1 || !a2) {
                            z = false;
                        }
                        m2581a((List<String>) arrayList, a, i, z);
                        C1458i.m2619a(SchedulerSupport.NONE, i, format, 4);
                    }
                    i = 1;
                    if (i == 1) {
                    }
                    z = false;
                    m2581a((List<String>) arrayList, a, i, z);
                    C1458i.m2619a(SchedulerSupport.NONE, i, format, 4);
                }
            }
            C1540h.m2997b("GBD_GGTSA", "first, available = false");
            return;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GGTSA", th.toString());
        }
        return;
    }

    /* renamed from: c */
    public synchronized void mo15004c() {
        C1540h.m2997b("GBD_GGTSA", "start aGuard ~~~");
        if (!m2583a(C1456g.GACTIVITY)) {
            C1540h.m2997b("GBD_GGTSA", "start aGuard, available = false");
        } else {
            m2578a(1);
        }
    }

    /* renamed from: d */
    public synchronized void mo15005d() {
        C1540h.m2997b("GBD_GGTSA", "start sGuard ~~~");
        if (!m2583a(C1456g.SERVICE)) {
            C1540h.m2997b("GBD_GGTSA", "start sGuard, available = false");
        } else {
            m2578a(2);
        }
    }

    /* renamed from: e */
    public boolean mo15006e() {
        String str;
        if (m2602j()) {
            str = "|ThirdGuardActivity romOrASdk in blacklist.";
        } else if (!m2589b(C1456g.GACTIVITY)) {
            str = "|ThirdGuardActivity pmGuardEnable false.";
        } else if (!C1541i.m3017c(this.f2504a)) {
            C1540h.m2997b("GBD_GGTSA", "ScreenOff ThirdGuardActivity true.");
            return true;
        } else if (m2597f()) {
            return true;
        } else {
            str = "|ThirdGuardActivity ActivityGuard unavailable.";
        }
        C1540h.m2997b("GBD_GGTSA", str);
        return false;
    }
}
