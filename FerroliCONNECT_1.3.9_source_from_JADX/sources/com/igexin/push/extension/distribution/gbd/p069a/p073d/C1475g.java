package com.igexin.push.extension.distribution.gbd.p069a.p073d;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
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

/* renamed from: com.igexin.push.extension.distribution.gbd.a.d.g */
public class C1475g {

    /* renamed from: d */
    private static C1475g f2573d;

    /* renamed from: a */
    private Context f2574a;

    /* renamed from: b */
    private SimpleDateFormat f2575b;

    /* renamed from: c */
    private HashMap<String, String> f2576c = new HashMap<>();

    /* renamed from: e */
    private Map<String, C1478j> f2577e = new HashMap();

    private C1475g(Context context) {
        this.f2574a = context;
        this.f2575b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    /* renamed from: a */
    public static synchronized C1475g m2678a() {
        C1475g gVar;
        synchronized (C1475g.class) {
            if (f2573d == null) {
                f2573d = new C1475g(C1490c.f2747a);
            }
            gVar = f2573d;
        }
        return gVar;
    }

    /* renamed from: a */
    private C1477i m2679a(int i, String str, List<String> list, boolean z, String str2, String str3) {
        int i2;
        Intent intent;
        String str4 = str;
        boolean z2 = z;
        String d = m2701d(list);
        try {
            C1478j jVar = this.f2577e.get(str4);
            i2 = i;
            if (i2 == 1) {
                try {
                    C1540h.m2997b("GBD_GPSA", "start aGuard pkg = " + str4);
                    if (!(jVar == null || jVar == C1478j.GACTIVITY)) {
                        C1540h.m2997b("GBD_GPSA", "start aGuard, pkg = " + str4 + "|not support act");
                    }
                    if (jVar == null || jVar == C1478j.GACTIVITY) {
                        boolean g = m2707g(str4);
                        Intent intent2 = new Intent();
                        if (!C1488a.f2650aG) {
                            intent = null;
                        } else if (C1488a.f2661aR) {
                            C1540h.m2997b("GBD_GPSA", "d-a from local.");
                            intent = m2704f(str4);
                        } else {
                            C1540h.m2997b("GBD_GPSA", "d-a from config.");
                            intent = m2702e(str4);
                        }
                        if (intent != null) {
                            i2 = 3;
                            C1540h.m2997b("GBD_GPSA", "in DA mode.");
                            intent2 = intent;
                        } else {
                            intent2.setClassName(str4, "com.igexin.sdk.MActivity");
                        }
                        if (g) {
                            intent2.putExtra(PushConsts.CMD_ACTION, C1489b.f2722b);
                            intent2.putExtra("isSlave", false);
                        }
                        Intent b = m2692b(str4, intent2);
                        if (!C1541i.m3018c(b, this.f2574a) || !m2689a(str4, b)) {
                            b.setClassName(str4, "com.sdk.plus.EnhActivity");
                            if (!C1541i.m3018c(b, this.f2574a) || !m2689a(str4, b)) {
                                i2 = 1;
                            } else {
                                C1540h.m2997b("GBD_GPSA", "EnhA guard success, force = " + g);
                                return new C1477i(1, true, d);
                            }
                        } else {
                            C1540h.m2997b("GBD_GPSA", "MA or DA guard success, force = " + g);
                            return new C1477i(i2, true, d);
                        }
                    }
                    C1540h.m2997b("GBD_GPSA", "aGuard failed, useServiceGuard = " + z2);
                    if (!z2) {
                        return new C1477i(i2, false, d);
                    }
                    i2 = 2;
                } catch (Throwable th) {
                    th = th;
                    C1540h.m2996a(th);
                    C1540h.m2997b("GBD_GPSA", "startGuard exception = " + th.getMessage());
                    C1469a.m2664a(str2, i2, str3, 2);
                    return new C1477i(i2, false, d);
                }
            }
            if (i2 == 2) {
                C1540h.m2997b("GBD_GPSA", "start sGuard = " + str4);
                if (!(jVar == null || jVar == C1478j.SERVICE)) {
                    C1540h.m2997b("GBD_GPSA", "start sGuard = " + str4 + "|not support service, return");
                }
                if (jVar == null || jVar == C1478j.SERVICE) {
                    m2693b(str4, d);
                }
            }
            return new C1477i(i2, true, d);
        } catch (Throwable th2) {
            th = th2;
            i2 = i;
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GPSA", "startGuard exception = " + th.getMessage());
            C1469a.m2664a(str2, i2, str3, 2);
            return new C1477i(i2, false, d);
        }
    }

    /* renamed from: a */
    private List<String> m2680a(int i, boolean z) {
        Map<String, C1478j> map;
        C1478j jVar;
        int i2 = i;
        if (TextUtils.isEmpty(C1488a.f2670aa)) {
            C1540h.m2997b("GBD_GPSA", "pMBlacklist is empty or null");
            return null;
        }
        String[] split = C1488a.f2670aa.split(",");
        if (split.length == 0) {
            C1540h.m2997b("GBD_GPSA", "pMBlacklist is empty or null");
            return null;
        }
        String b = C1541i.m3010b();
        if (TextUtils.isEmpty(b)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        this.f2577e.clear();
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
                        C1540h.m2997b("GBD_GPSA", "pkg = " + str2 + " not install");
                    } else {
                        boolean parseBoolean = Boolean.parseBoolean(split2[2]);
                        boolean parseBoolean2 = Boolean.parseBoolean(split2[3]);
                        C1540h.m2997b("GBD_GPSA", "brand = " + b + "|pkg = " + str2 + "|aEnable = " + parseBoolean + "|sEnable = " + parseBoolean2);
                        if (!parseBoolean || !parseBoolean2) {
                            if (parseBoolean || parseBoolean2) {
                                if (i2 == 2 && !parseBoolean2) {
                                    C1540h.m2997b("GBD_GPSA", "guard type = " + i2 + "|pkg = " + str2 + " matched, in pm black list ###");
                                } else if (i2 == 1) {
                                    if (parseBoolean) {
                                        Intent intent = new Intent();
                                        intent.setClassName(str2, "com.igexin.sdk.MActivity");
                                        boolean c2 = C1541i.m3018c(intent, this.f2574a);
                                        intent.setClassName(str2, GActivity.TAG);
                                        if (!c2 && !C1541i.m3018c(intent, this.f2574a)) {
                                            z2 = false;
                                        }
                                        if (!z2) {
                                            arrayList.add(str2);
                                            C1540h.m2997b("GBD_GPSA", "getPMGuardBlackList-> " + str2 + " activitySet = false, add to pMGuardBlackList");
                                        } else {
                                            map = this.f2577e;
                                            jVar = C1478j.GACTIVITY;
                                        }
                                    } else if (z) {
                                        map = this.f2577e;
                                        jVar = C1478j.SERVICE;
                                    }
                                    map.put(str2, jVar);
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
        C1540h.m2997b("GBD_GPSA", "PMGuardBlack-> = " + arrayList.toString());
        return arrayList;
    }

    /* renamed from: a */
    private List<String> m2681a(Map<String, List<String>> map, List<String> list, int i, boolean z) {
        Map<String, List<String>> map2 = map;
        List<String> list2 = list;
        ArrayList<String> arrayList = new ArrayList<>();
        if (map2 != null && !map.isEmpty()) {
            if (!SchedulerSupport.NONE.equalsIgnoreCase(C1488a.f2655aL) && !TextUtils.isEmpty(C1488a.f2655aL)) {
                try {
                    if (this.f2576c == null) {
                        this.f2576c = new HashMap<>();
                    }
                    this.f2576c.clear();
                    String[] split = C1488a.f2655aL.split(",");
                    if (split != null && split.length > 0) {
                        for (String split2 : split) {
                            String[] split3 = split2.split(":");
                            if (split3 != null) {
                                if (split3.length == 3) {
                                    this.f2576c.put(split3[0], split3[1] + ":" + split3[2]);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    C1540h.m2996a(th);
                }
            }
            if (list2 != null && !list.isEmpty()) {
                C1540h.m2997b("GBD_GPSA", "remove all running, running =  " + list.toString());
                Iterator<Map.Entry<String, List<String>>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    if (list2.contains((String) it.next().getKey())) {
                        it.remove();
                    }
                }
            }
            C1540h.m2997b("GBD_GPSA", "after remove running, guardList =  " + map.toString());
            if (!C1488a.f2669aZ.equals(SchedulerSupport.NONE)) {
                ArrayList arrayList2 = new ArrayList(Arrays.asList(C1488a.f2669aZ.split(",")));
                Iterator<Map.Entry<String, List<String>>> it2 = map.entrySet().iterator();
                while (it2.hasNext()) {
                    if (m2691a((String) it2.next().getKey(), (List<String>) arrayList2)) {
                        it2.remove();
                    }
                }
            }
            C1540h.m2997b("GBD_GPSA", "after remove blacklist, guardList =  " + map.toString());
            List<String> a = m2680a(i, z);
            if (a != null && !a.isEmpty()) {
                Iterator<Map.Entry<String, List<String>>> it3 = map.entrySet().iterator();
                while (it3.hasNext()) {
                    if (a.contains((String) it3.next().getKey())) {
                        it3.remove();
                    }
                }
            }
            C1540h.m2997b("GBD_GPSA", "after remove pm blacklist pkgs, guardList =  " + map.toString());
            if (!TextUtils.isEmpty(C1488a.f2668aY) && !C1488a.f2668aY.equals(SchedulerSupport.NONE) && !map.isEmpty()) {
                ArrayList<String> arrayList3 = new ArrayList<>(Arrays.asList(C1488a.f2668aY.split(",")));
                C1540h.m2997b("GBD_GPSA", "white list = " + arrayList3.toString());
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
            C1540h.m2997b("GBD_GPSA", "after add all whitelist, guardList =  " + arrayList.toString());
        }
        return arrayList;
    }

    /* renamed from: a */
    private Map<String, List<String>> m2682a(List<String> list) {
        Map<String, List<String>> b = C1472d.m2668a().mo15027b();
        if (b.isEmpty()) {
            return b;
        }
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) this.f2574a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningServices(MessageHandler.WHAT_SMOOTH_SCROLL);
        if (runningServices == null || runningServices.isEmpty()) {
            C1540h.m2997b("GBD_GPSA", "rsi empty.");
            return b;
        }
        C1540h.m2997b("GBD_GPSA", "rsi " + runningServices.size());
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
    private void m2683a(int i) {
        try {
            ArrayList arrayList = new ArrayList();
            Map<String, List<String>> a = m2682a((List<String>) arrayList);
            if (a.size() <= 0) {
                C1540h.m2997b("GBD_GPSA", "hasServiceAppList size <= 0");
                return;
            }
            if (i == 2) {
                C1469a.m2664a(SchedulerSupport.NONE, i, this.f2575b.format(new Date()), 3);
            }
            m2686a((List<String>) arrayList, a, i, false);
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: a */
    private void m2684a(String str) {
        String str2 = C1490c.f2740Q.get(str);
        if (!TextUtils.isEmpty(str2) && m2690a(str, str2)) {
            try {
                ContentResolver contentResolver = C1490c.f2747a.getContentResolver();
                Uri parse = Uri.parse("content://com.sdk.plus." + str + "/*");
                if (parse != null) {
                    C1540h.m2997b("GBD_GPSA", "dyn pd.");
                    Cursor query = contentResolver.query(parse, (String[]) null, (String) null, (String[]) null, (String) null);
                    if (query != null) {
                        query.close();
                    }
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        } else if (m2690a(str, "com.sdk.plus.EnhProvider")) {
            try {
                ContentResolver contentResolver2 = C1490c.f2747a.getContentResolver();
                Uri parse2 = Uri.parse("content://com.sdk.plus." + str + "/*");
                if (parse2 != null) {
                    C1540h.m2997b("GBD_GPSA", "enh pd.");
                    Cursor query2 = contentResolver2.query(parse2, (String[]) null, (String) null, (String[]) null, (String) null);
                    if (query2 != null) {
                        query2.close();
                    }
                }
            } catch (Throwable th2) {
                C1540h.m2996a(th2);
            }
        }
    }

    /* renamed from: a */
    private void m2685a(String str, int i, String str2, String str3) {
        try {
            C1469a.m2664a(str, i, str3, 0);
            String str4 = str.split(",")[0];
            String str5 = str.split(",")[1];
            C1540h.m2997b("GBD_GPSA_guard", "success start " + str4 + " type = " + i);
            ArrayList arrayList = new ArrayList();
            arrayList.add(str2);
            HashMap hashMap = new HashMap();
            hashMap.put("pkgName", str4);
            hashMap.put("srvName", str5);
            hashMap.put("datetime", str3);
            hashMap.put("checkList", arrayList);
            C1469a.m2665a(hashMap, 0, i);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GPSA", "saveResultAndCheck exception = " + th.getMessage());
        }
    }

    /* renamed from: a */
    private void m2686a(List<String> list, Map<String, List<String>> map, int i, boolean z) {
        Map<String, List<String>> map2 = map;
        int i2 = i;
        boolean z2 = z;
        try {
            C1540h.m2997b("GBD_GPSA", "plus guard cnt = " + C1488a.f2667aX + ", running cnt = " + list.size() + ", has cnt = " + map.size() + ", type = " + i2 + ", useService = " + z2);
            if (m2687a(list.size(), map.size())) {
                List<String> a = m2681a(map2, list, i2, z2);
                if (!a.isEmpty()) {
                    int min = Math.min(C1488a.f2667aX - list.size(), a.size());
                    C1540h.m2997b("GBD_GPSA", "need guard cnt = " + min + " #######");
                    if (min > 0) {
                        String format = this.f2575b.format(new Date());
                        int i3 = 0;
                        for (String next : a) {
                            List list2 = map2.get(next);
                            String packageName = this.f2574a.getPackageName();
                            if (list2 != null && !list2.isEmpty() && !TextUtils.isEmpty(next)) {
                                if (!next.equals(packageName)) {
                                    String d = m2700d(next);
                                    if (TextUtils.isEmpty(d)) {
                                        C1540h.m2997b("GBD_GPSA", "guard pkg = " + next + ", appid is empty, ignore this ###");
                                    } else {
                                        String str = next + "," + d;
                                        C1469a.m2663a(str);
                                        String str2 = str;
                                        C1477i a2 = m2679a(i, next, list2, z, str, format);
                                        if (a2.f2579b) {
                                            m2685a(str2, a2.f2578a, a2.f2580c, format);
                                            i3++;
                                            C1540h.m2997b("GBD_GPSA", "has guard cnt = " + i3);
                                            if (i3 >= min) {
                                                return;
                                            }
                                        }
                                    }
                                    map2 = map;
                                }
                            }
                            C1540h.m2997b("GBD_GPSA", "pkg =  " + next + " service is empty");
                            map2 = map;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GPSA", " startSDK " + th.getMessage());
        }
    }

    /* renamed from: a */
    private boolean m2687a(int i, int i2) {
        String str;
        if (i == i2) {
            str = "running s count = all list, need't guard ~~~";
        } else if (i < C1488a.f2667aX) {
            return true;
        } else {
            str = "running cnt > " + C1488a.f2667aX + ", need't guard ~~~";
        }
        C1540h.m2997b("GBD_GPSA", str);
        return false;
    }

    /* renamed from: a */
    private boolean m2688a(C1478j jVar) {
        String str;
        if (!C1488a.f2660aQ) {
            str = "isGEnable = false";
        } else if (!C1541i.m3005a(this.f2574a)) {
            str = "|canScan = false";
        } else if (m2709i()) {
            str = "|romOrASdkInBlack = true";
        } else if ((jVar == C1478j.GACTIVITY && !C1488a.f2663aT) || ((jVar == C1478j.SERVICE && !C1488a.f2664aU) || (jVar == C1478j.ONEOF && !C1488a.f2663aT && !C1488a.f2664aU))) {
            str = jVar + "|aGuardEnable = " + C1488a.f2663aT + "|sGuardEnable = " + C1488a.f2664aU;
        } else if (jVar != C1478j.GACTIVITY) {
            return m2694b(jVar);
        } else {
            if (!m2694b(C1478j.GACTIVITY)) {
                str = "pMGuardEnable = false";
            } else if (C1541i.m3017c(this.f2574a)) {
                return m2703e();
            } else {
                C1540h.m2997b("GBD_GPSA", "isScreenOn = false, gEnable = true");
                return true;
            }
        }
        C1540h.m2997b("GBD_GPSA", str);
        return false;
    }

    /* renamed from: a */
    private boolean m2689a(String str, Intent intent) {
        try {
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            this.f2574a.startActivity(intent);
            C1540h.m2997b("GBD_GPSA", "type = start by activity, pkg = " + str);
            return true;
        } catch (Exception e) {
            C1540h.m2997b("GBD_GPSA", e.getMessage());
            C1540h.m2996a(e);
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m2690a(String str, String str2) {
        PackageInfo packageInfo;
        ProviderInfo[] providerInfoArr;
        try {
            if (TextUtils.isEmpty(str2) || (packageInfo = C1490c.f2747a.getPackageManager().getPackageInfo(str, 8)) == null || (providerInfoArr = packageInfo.providers) == null) {
                return false;
            }
            for (ProviderInfo providerInfo : providerInfoArr) {
                if (providerInfo.name.equals(str2)) {
                    if (providerInfo.authority.equals("com.sdk.plus." + str)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: a */
    private boolean m2691a(String str, List<String> list) {
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
    private Intent m2692b(String str, Intent intent) {
        String[] split;
        if (intent == null) {
            return null;
        }
        try {
            if (this.f2576c != null && !this.f2576c.isEmpty() && this.f2576c.containsKey(str)) {
                String str2 = this.f2576c.get(str);
                if (!TextUtils.isEmpty(str2) && (split = str2.split(":")) != null && split.length == 2) {
                    intent.putExtra(split[0], split[1]);
                    C1540h.m2997b("GBD_GPSA", "intent info " + split[0] + " " + split[1]);
                }
            }
            return intent;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a3 A[Catch:{ Throwable -> 0x00ce }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ed A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:40:? A[ADDED_TO_REGION, ORIG_RETURN, RETURN, SYNTHETIC] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2693b(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            java.lang.String r0 = "|force = "
            java.lang.String r1 = "action"
            java.lang.String r2 = "GBD_GPSA"
            boolean r3 = r9.m2707g(r10)
            boolean r4 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2665aV     // Catch:{ Throwable -> 0x0012 }
            if (r4 == 0) goto L_0x0016
            r9.m2684a((java.lang.String) r10)     // Catch:{ Throwable -> 0x0012 }
            goto L_0x0016
        L_0x0012:
            r4 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r4)
        L_0x0016:
            android.content.Intent r4 = new android.content.Intent
            r4.<init>()
            r5 = 1
            r6 = 0
            r4.setClassName(r10, r11)     // Catch:{ Throwable -> 0x0061 }
            android.content.Intent r4 = r9.m2692b((java.lang.String) r10, (android.content.Intent) r4)     // Catch:{ Throwable -> 0x0061 }
            android.content.Context r7 = r9.f2574a     // Catch:{ Throwable -> 0x0061 }
            boolean r7 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3007a((android.content.Intent) r4, (android.content.Context) r7)     // Catch:{ Throwable -> 0x0061 }
            if (r7 == 0) goto L_0x005f
            if (r3 == 0) goto L_0x0033
            java.lang.String r7 = com.igexin.push.extension.distribution.gbd.p077c.C1489b.f2722b     // Catch:{ Throwable -> 0x0061 }
            r4.putExtra(r1, r7)     // Catch:{ Throwable -> 0x0061 }
        L_0x0033:
            android.content.Context r7 = r9.f2574a     // Catch:{ Throwable -> 0x0061 }
            r7.startService(r4)     // Catch:{ Throwable -> 0x0061 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x005c }
            r4.<init>()     // Catch:{ Throwable -> 0x005c }
            java.lang.String r7 = "start s by pkg = "
            r4.append(r7)     // Catch:{ Throwable -> 0x005c }
            r4.append(r10)     // Catch:{ Throwable -> 0x005c }
            java.lang.String r7 = "|s = "
            r4.append(r7)     // Catch:{ Throwable -> 0x005c }
            r4.append(r11)     // Catch:{ Throwable -> 0x005c }
            r4.append(r0)     // Catch:{ Throwable -> 0x005c }
            r4.append(r3)     // Catch:{ Throwable -> 0x005c }
            java.lang.String r11 = r4.toString()     // Catch:{ Throwable -> 0x005c }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r2, r11)     // Catch:{ Throwable -> 0x005c }
            r11 = 1
            goto L_0x007f
        L_0x005c:
            r11 = move-exception
            r4 = 1
            goto L_0x0063
        L_0x005f:
            r11 = 0
            goto L_0x007f
        L_0x0061:
            r11 = move-exception
            r4 = 0
        L_0x0063:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "startEnhS error|"
            r7.append(r8)
            java.lang.String r8 = r11.getMessage()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r2, r7)
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r11)
            r11 = r4
        L_0x007f:
            android.content.Intent r4 = new android.content.Intent     // Catch:{ Throwable -> 0x00ce }
            r4.<init>()     // Catch:{ Throwable -> 0x00ce }
            r4.setPackage(r10)     // Catch:{ Throwable -> 0x00ce }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ce }
            r7.<init>()     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r8 = "com.sdk.plus.action."
            r7.append(r8)     // Catch:{ Throwable -> 0x00ce }
            r7.append(r10)     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00ce }
            r4.setAction(r7)     // Catch:{ Throwable -> 0x00ce }
            android.content.Context r7 = r9.f2574a     // Catch:{ Throwable -> 0x00ce }
            boolean r7 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3007a((android.content.Intent) r4, (android.content.Context) r7)     // Catch:{ Throwable -> 0x00ce }
            if (r7 == 0) goto L_0x00cc
            if (r3 == 0) goto L_0x00aa
            java.lang.String r7 = com.igexin.push.extension.distribution.gbd.p077c.C1489b.f2722b     // Catch:{ Throwable -> 0x00ce }
            r4.putExtra(r1, r7)     // Catch:{ Throwable -> 0x00ce }
        L_0x00aa:
            android.content.Context r1 = r9.f2574a     // Catch:{ Throwable -> 0x00ce }
            r1.startService(r4)     // Catch:{ Throwable -> 0x00ce }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ca }
            r1.<init>()     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r4 = "start s by action, pkg = "
            r1.append(r4)     // Catch:{ Throwable -> 0x00ca }
            r1.append(r10)     // Catch:{ Throwable -> 0x00ca }
            r1.append(r0)     // Catch:{ Throwable -> 0x00ca }
            r1.append(r3)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r10 = r1.toString()     // Catch:{ Throwable -> 0x00ca }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r2, r10)     // Catch:{ Throwable -> 0x00ca }
            goto L_0x00eb
        L_0x00ca:
            r10 = move-exception
            goto L_0x00d0
        L_0x00cc:
            r5 = 0
            goto L_0x00eb
        L_0x00ce:
            r10 = move-exception
            r5 = 0
        L_0x00d0:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "start ie EnhS error|"
            r0.append(r1)
            java.lang.String r1 = r10.getMessage()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r2, r0)
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r10)
        L_0x00eb:
            if (r5 != 0) goto L_0x00f8
            if (r11 == 0) goto L_0x00f0
            goto L_0x00f8
        L_0x00f0:
            java.lang.Throwable r10 = new java.lang.Throwable
            java.lang.String r11 = "start s error"
            r10.<init>(r11)
            throw r10
        L_0x00f8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p069a.p073d.C1475g.m2693b(java.lang.String, java.lang.String):void");
    }

    /* renamed from: b */
    private boolean m2694b(C1478j jVar) {
        if (TextUtils.isEmpty(C1488a.f2670aa)) {
            C1540h.m2997b("GBD_GPSA", "pMBlacklist is empty or null");
            return true;
        }
        try {
            String[] split = C1488a.f2670aa.split(",");
            if (split.length == 0) {
                C1540h.m2997b("GBD_GPSA", "pMBlacklist is empty or null");
                return true;
            }
            String b = C1541i.m3010b();
            if (TextUtils.isEmpty(b)) {
                return true;
            }
            C1540h.m2997b("GBD_GPSA", "brand = " + b);
            for (String str : split) {
                if (!TextUtils.isEmpty(str)) {
                    String[] split2 = str.split(":");
                    if (split2.length != 3) {
                        continue;
                    } else if (b.equalsIgnoreCase(split2[0])) {
                        boolean parseBoolean = Boolean.parseBoolean(split2[1]);
                        boolean parseBoolean2 = Boolean.parseBoolean(split2[2]);
                        C1540h.m2997b("GBD_GPSA", "brand = " + b + "|aEnable = " + parseBoolean + "|sEnable = " + parseBoolean2);
                        if (jVar == C1478j.GACTIVITY) {
                            return parseBoolean;
                        }
                        if (jVar == C1478j.SERVICE) {
                            return parseBoolean2;
                        }
                        if (jVar == C1478j.ALL) {
                            return parseBoolean && parseBoolean2;
                        }
                        if (jVar == C1478j.ONEOF) {
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
    private boolean m2695b(String str) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(C1488a.f2641Y)) {
            for (String equals : C1488a.f2641Y.split(",")) {
                if (str.equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: b */
    private boolean m2696b(List<String> list) {
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
    private Intent m2697c(String str, String str2) {
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
                    C1540h.m2997b("GBD_GPSA_guard", "dynamic p-a " + str4 + "  " + str5);
                    return intent;
                }
                str3 = "dynamic p-a " + str4 + "  check = false";
            } else {
                str3 = str4 + " d-a null.";
            }
            C1540h.m2997b("GBD_GPSA_guard", str3);
            return null;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: c */
    private boolean m2698c(String str) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(C1488a.f2649aF)) {
            for (String equals : C1488a.f2649aF.split(",")) {
                if (str.equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: c */
    private boolean m2699c(List<String> list) {
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
    private String m2700d(String str) {
        String str2 = null;
        try {
            str2 = C1541i.m3003a(str, this.f2574a);
            if (TextUtils.isEmpty(str2) && !TextUtils.isEmpty(C1490c.f2746W)) {
                str2 = C1490c.f2746W;
            }
            C1540h.m2997b("GBD_GPSA", "guard appid = " + str2 + "|pkg = " + str);
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        return str2;
    }

    /* renamed from: d */
    private String m2701d(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "com.sdk.plus.EnhService";
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        for (String next : list) {
            if (!next.equals("com.igexin.sdk.PushService") && !next.equals("com.sdk.plus.EnhService")) {
                return next;
            }
        }
        return "com.sdk.plus.EnhService";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0034, code lost:
        r7 = r7.split("\\|");
     */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.content.Intent m2702e(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            java.lang.String r1 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2662aS     // Catch:{ Throwable -> 0x0052 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0052 }
            if (r1 != 0) goto L_0x004a
            java.lang.String r1 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2662aS     // Catch:{ Throwable -> 0x0052 }
            boolean r1 = r1.contains(r7)     // Catch:{ Throwable -> 0x0052 }
            if (r1 == 0) goto L_0x004a
            java.lang.String r1 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2662aS     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r2 = ","
            java.lang.String[] r1 = r1.split(r2)     // Catch:{ Throwable -> 0x0052 }
            int r2 = r1.length     // Catch:{ Throwable -> 0x0052 }
            r3 = 0
        L_0x001b:
            if (r3 >= r2) goto L_0x0056
            r4 = r1[r3]     // Catch:{ Throwable -> 0x0052 }
            boolean r5 = r7.equals(r4)     // Catch:{ Throwable -> 0x0052 }
            if (r5 != 0) goto L_0x0028
            int r3 = r3 + 1
            goto L_0x001b
        L_0x0028:
            java.lang.String r7 = "/sdcard/libs/w"
            java.lang.String r7 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3012b((java.lang.String) r4, (java.lang.String) r7)     // Catch:{ Throwable -> 0x0052 }
            boolean r1 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x0052 }
            if (r1 != 0) goto L_0x0044
            java.lang.String r1 = "\\|"
            java.lang.String[] r7 = r7.split(r1)     // Catch:{ Throwable -> 0x0052 }
            if (r7 == 0) goto L_0x0044
            int r1 = r7.length     // Catch:{ Throwable -> 0x0052 }
            r2 = 5
            if (r1 != r2) goto L_0x0044
            r1 = 4
            r7 = r7[r1]     // Catch:{ Throwable -> 0x0052 }
            goto L_0x0045
        L_0x0044:
            r7 = r0
        L_0x0045:
            android.content.Intent r7 = r6.m2697c(r4, r7)     // Catch:{ Throwable -> 0x0052 }
            return r7
        L_0x004a:
            java.lang.String r7 = "GBD_GPSA_guard"
            java.lang.String r1 = " not in d-a config list."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r7, r1)     // Catch:{ Throwable -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r7 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r7)
        L_0x0056:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p069a.p073d.C1475g.m2702e(java.lang.String):android.content.Intent");
    }

    /* renamed from: e */
    private boolean m2703e() {
        C1540h.m2997b("GBD_GPSA", "Build.VERSION = " + Build.VERSION.SDK_INT);
        boolean h = m2708h();
        boolean g = m2706g();
        if (Build.VERSION.SDK_INT >= 24 && h) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 26 && g) {
            return false;
        }
        if (g || h) {
            if (Build.VERSION.SDK_INT < 21) {
                try {
                    List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) C1490c.f2747a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningTasks(1);
                    if (runningTasks == null || runningTasks.isEmpty()) {
                        return true;
                    }
                    ComponentName componentName = runningTasks.get(0).topActivity;
                    if (componentName != null) {
                        boolean b = m2695b(componentName.getPackageName());
                        boolean c = m2698c(componentName.getPackageName());
                        C1540h.m2997b("GBD_GPSA", "Build.VERSION < 21, top app = " + componentName.getPackageName() + ",  isInBL = " + b);
                        C1540h.m2997b("GBD_GPSA", "Build.VERSION < 21, top app = " + componentName.getPackageName() + ",  isInPBL = " + c);
                        return !b && !c;
                    }
                } catch (Throwable th) {
                    C1540h.m2997b("GBD_GPSA", th.getMessage());
                    C1540h.m2996a(th);
                }
            } else if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT >= 24) {
                List<String> f = m2705f();
                if (f != null && !f.isEmpty()) {
                    if (f.size() != 1 || !f.get(0).equals(this.f2574a.getPackageName())) {
                        boolean b2 = m2696b(f);
                        C1540h.m2997b("GBD_GPSA", "Build.VERSION >= 24, isInBlackList = " + b2);
                        return !b2;
                    }
                }
                C1540h.m2997b("GBD_GPSA", "Build.VERSION >= 26, rs = null, guard = false");
            } else {
                List<String> a = C1553u.m3060a(false, true);
                if (a != null && !a.isEmpty()) {
                    if (a.size() != 1 || !a.get(0).equals(this.f2574a.getPackageName())) {
                        boolean b3 = m2696b(a);
                        boolean c2 = m2699c(a);
                        C1540h.m2997b("GBD_GPSA", "Build.VERSION >= 21 <24, isInBL = " + b3);
                        C1540h.m2997b("GBD_GPSA", "Build.VERSION >= 21 <24, isInPBL = " + c2);
                        return !b3 && !c2;
                    }
                }
                C1540h.m2997b("GBD_GPSA", "Build.VERSION >= 21 <24, recentList = null, guard = false");
                return false;
            }
            return false;
        }
        C1540h.m2997b("GBD_GPSA", "checkBlackListInstall = false, check pbl = false, gEnable = true");
        return true;
    }

    /* renamed from: f */
    private Intent m2704f(String str) {
        try {
            if (C1490c.f2739P != null) {
                if (!C1490c.f2739P.isEmpty()) {
                    if (!C1490c.f2739P.keySet().contains(str)) {
                        C1540h.m2997b("GBD_GPSA_guard", " not in d-a local list.");
                        return null;
                    }
                    if (!TextUtils.isEmpty(C1488a.f2648aE)) {
                        ArrayList arrayList = new ArrayList(Arrays.asList(C1488a.f2648aE.split(",")));
                        if (!arrayList.isEmpty() && arrayList.contains(str)) {
                            C1540h.m2997b("GBD_GPSA_guard", " in d-a black list.");
                            return null;
                        }
                    }
                    return m2697c(str, C1490c.f2739P.get(str));
                }
            }
            C1540h.m2997b("GBD_GPSA_guard", "d-a map null.");
            return null;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: f */
    private List<String> m2705f() {
        ArrayList arrayList = new ArrayList();
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) this.f2574a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningServices(MessageHandler.WHAT_SMOOTH_SCROLL);
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

    /* renamed from: g */
    private boolean m2706g() {
        if (!TextUtils.isEmpty(C1488a.f2641Y)) {
            for (String str : C1488a.f2641Y.split(",")) {
                if (C1541i.m3019c(str, this.f2574a)) {
                    C1540h.m2997b("GBD_GPSA", str + " install, in blacklist");
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: g */
    private boolean m2707g(String str) {
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

    /* renamed from: h */
    private boolean m2708h() {
        if (!TextUtils.isEmpty(C1488a.f2649aF)) {
            for (String str : C1488a.f2649aF.split(",")) {
                if (C1541i.m3019c(str, this.f2574a)) {
                    C1540h.m2997b("GBD_GPSA", str + " install, in pbl.");
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: i */
    private boolean m2709i() {
        if (TextUtils.isEmpty(C1488a.f2642Z)) {
            C1540h.m2997b("GBD_GPSA", "romSdkIntBlack is empty or null ");
            return false;
        }
        try {
            for (String str : C1488a.f2642Z.split(",")) {
                if (!TextUtils.isEmpty(str)) {
                    String[] split = str.split(":");
                    if (split.length == 2) {
                        String str2 = split[0];
                        if (C1541i.m3015c().equals(str2) && Build.VERSION.SDK_INT == Integer.valueOf(split[1]).intValue()) {
                            C1540h.m2997b("GBD_GPSA", "SDK_INT = " + Build.VERSION.SDK_INT + "|blacklist version int = " + Integer.valueOf(split[1]) + "|rominfo = " + str2 + "|inblacklist");
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
    public synchronized void mo15029b() {
        try {
            C1540h.m2997b("GBD_GPSA", "first start");
            if (!C1541i.m3005a(this.f2574a)) {
                C1540h.m2997b("GBD_GPSA", "first, scan apps = false");
                return;
            }
            ArrayList arrayList = new ArrayList();
            Map<String, List<String>> a = m2682a((List<String>) arrayList);
            for (Map.Entry<String, List<String>> key : a.entrySet()) {
                String str = (String) key.getKey();
                String a2 = C1541i.m3003a(str, this.f2574a);
                if (!TextUtils.isEmpty(a2)) {
                    C1469a.m2663a(str + "," + a2);
                }
            }
            if (a.size() > 0) {
                if (m2688a(C1478j.ONEOF)) {
                    String format = this.f2575b.format(new Date());
                    boolean a3 = m2688a(C1478j.SERVICE);
                    boolean a4 = m2688a(C1478j.GACTIVITY);
                    C1540h.m2997b("GBD_GPSA", "first, sEnable = " + a3 + "|aEnable = " + a4);
                    int i = 2;
                    boolean z = true;
                    if (!C1488a.f2666aW || !a4) {
                        if (!C1541i.m3017c(this.f2574a)) {
                            C1540h.m2997b("GBD_GPSA", "first, screenOn = false|aEnable = " + a4);
                            if (a4) {
                            }
                        } else if (!a3) {
                            C1540h.m2997b("GBD_GPSA", "first, sEnable = false|screenOn = true");
                            return;
                        }
                        if (i == 1 || !a3) {
                            z = false;
                        }
                        m2686a((List<String>) arrayList, a, i, z);
                        C1469a.m2664a(SchedulerSupport.NONE, i, format, 4);
                    }
                    i = 1;
                    if (i == 1) {
                    }
                    z = false;
                    m2686a((List<String>) arrayList, a, i, z);
                    C1469a.m2664a(SchedulerSupport.NONE, i, format, 4);
                }
            }
            C1540h.m2997b("GBD_GPSA", "first, available = false");
            return;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GPSA", th.toString());
        }
        return;
    }

    /* renamed from: c */
    public synchronized void mo15030c() {
        C1540h.m2997b("GBD_GPSA", "start aGuard ~~~");
        if (!m2688a(C1478j.GACTIVITY)) {
            C1540h.m2997b("GBD_GPSA", "start aGuard, available = false");
        } else {
            m2683a(1);
        }
    }

    /* renamed from: d */
    public synchronized void mo15031d() {
        C1540h.m2997b("GBD_GPSA", "start sGuard ~~~");
        if (!m2688a(C1478j.SERVICE)) {
            C1540h.m2997b("GBD_GPSA", "start sGuard, available = false");
        } else {
            m2683a(2);
        }
    }
}
