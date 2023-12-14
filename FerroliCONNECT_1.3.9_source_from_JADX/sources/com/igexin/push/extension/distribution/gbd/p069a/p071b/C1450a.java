package com.igexin.push.extension.distribution.gbd.p069a.p071b;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1508g;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.b.a */
public class C1450a {

    /* renamed from: a */
    private Map<String, List<String>> f2501a;

    private C1450a() {
    }

    /* renamed from: a */
    public static synchronized C1450a m2560a() {
        C1450a a;
        synchronized (C1450a.class) {
            a = C1452c.f2502a;
        }
        return a;
    }

    /* renamed from: a */
    private void m2561a(String str, String str2) {
        try {
            byte[] b = C1541i.m3014b(str2);
            if (b != null) {
                String str3 = new String(C1196a.m1439c(b));
                if (!this.f2501a.get(str).contains(str3)) {
                    this.f2501a.get(str).add(str3);
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GGTH", th.getMessage());
        }
    }

    /* renamed from: a */
    private void m2562a(List<PackageInfo> list) {
        ServiceInfo[] serviceInfoArr;
        if (list != null) {
            try {
                if (!list.isEmpty()) {
                    for (PackageInfo next : list) {
                        if (((next.applicationInfo.flags & 1) == 0 || (next.applicationInfo.flags & 128) != 0) && (serviceInfoArr = next.services) != null) {
                            if (serviceInfoArr.length != 0) {
                                if (this.f2501a.containsKey(next.packageName)) {
                                    ArrayList arrayList = new ArrayList();
                                    for (ServiceInfo serviceInfo : serviceInfoArr) {
                                        arrayList.add(serviceInfo.name);
                                    }
                                    if (this.f2501a.get(next.packageName) != null) {
                                        this.f2501a.get(next.packageName).retainAll(arrayList);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
                C1540h.m2997b("GBD_GGTH", th.getMessage());
            }
        }
    }

    /* renamed from: a */
    private static boolean m2563a(String str) {
        try {
            return C1490c.f2747a.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (Exception e) {
            C1540h.m2996a(e);
            return false;
        }
    }

    /* renamed from: b */
    private static boolean m2564b(String str) {
        return "com.igexin.sdk.PushService".equals(str) || "com.igexin.sdk.coordinator.GexinMsgService".equals(str) || "com.igexin.sdk.coordinator.SdkMsgService".equals(str);
    }

    /* renamed from: d */
    private List<PackageInfo> m2565d() {
        ServiceInfo[] serviceInfoArr;
        List<PackageInfo> list = null;
        try {
            list = C1490c.f2747a.getPackageManager().getInstalledPackages(4);
            if (list != null) {
                if (!list.isEmpty()) {
                    for (PackageInfo next : list) {
                        if (((next.applicationInfo.flags & 1) == 0 || (next.applicationInfo.flags & 128) != 0) && (serviceInfoArr = next.services) != null) {
                            if (serviceInfoArr.length != 0) {
                                int length = serviceInfoArr.length;
                                int i = 0;
                                while (true) {
                                    if (i >= length) {
                                        break;
                                    }
                                    ServiceInfo serviceInfo = serviceInfoArr[i];
                                    if (m2564b(serviceInfo.name)) {
                                        if (!this.f2501a.containsKey(next.packageName)) {
                                            this.f2501a.put(next.packageName, new ArrayList());
                                        }
                                        if (!this.f2501a.get(next.packageName).contains(serviceInfo.name)) {
                                            this.f2501a.get(next.packageName).add(serviceInfo.name);
                                        }
                                    } else {
                                        i++;
                                    }
                                }
                            }
                        }
                    }
                    return list;
                }
            }
            return list;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GGTH", th.getMessage());
        }
    }

    /* renamed from: e */
    private void m2566e() {
        try {
            String b = C1508g.m2864a().mo15136b();
            if (!TextUtils.isEmpty(b)) {
                JSONObject jSONObject = new JSONObject(b);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    String string = jSONObject.getString(next);
                    if (!next.contains(".gtyl")) {
                        if (!this.f2501a.containsKey(next)) {
                            this.f2501a.put(next, new ArrayList());
                        }
                        for (String str : Arrays.asList(string.split("\\|"))) {
                            if (!this.f2501a.get(next).contains(str)) {
                                this.f2501a.get(next).add(str);
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBD_GGTH", th.getMessage());
        }
    }

    /* renamed from: f */
    private void m2567f() {
        String[] list;
        File file = new File("/sdcard/libs/");
        if (file.exists() && (list = file.list()) != null) {
            try {
                for (String str : list) {
                    if (!str.endsWith(".db")) {
                        if (!str.endsWith(".bin")) {
                        }
                    }
                    if (!str.equals("app.db") && !str.equals("imsi.db") && !str.equals("com.igexin.sdk.deviceId.db") && !str.equals("com.getui.sdk.deviceId.db")) {
                        String substring = str.substring(0, str.lastIndexOf("."));
                        if (!this.f2501a.containsKey(substring)) {
                            this.f2501a.put(substring, new ArrayList());
                        }
                        if (str.endsWith(".db")) {
                            if (!this.f2501a.get(substring).contains("com.igexin.sdk.PushService")) {
                                this.f2501a.get(substring).add("com.igexin.sdk.PushService");
                            }
                        } else if (str.endsWith(".bin")) {
                            m2561a(substring, "/sdcard/libs/" + "/" + str);
                        }
                    }
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
                C1540h.m2997b("GBD_GGTH", th.getMessage());
            }
        }
    }

    /* renamed from: g */
    private void m2568g() {
        String[] list;
        if (C1490c.f2738O == null) {
            C1490c.f2738O = new ConcurrentHashMap<>();
        }
        C1490c.f2738O.clear();
        File file = new File("/sdcard/libs/");
        if (file.exists() && (list = file.list()) != null && list.length != 0) {
            try {
                for (String str : list) {
                    if (str.endsWith(".db") && !str.equals("app.db") && !str.equals("imsi.db") && !str.equals("com.igexin.sdk.deviceId.db") && !str.equals("com.getui.sdk.deviceId.db")) {
                        String substring = str.substring(0, str.lastIndexOf("."));
                        if (!TextUtils.isEmpty(substring)) {
                            String e = C1541i.m3026e(substring);
                            if (!TextUtils.isEmpty(e)) {
                                Intent intent = new Intent();
                                intent.setClassName(substring, e);
                                if (C1541i.m3018c(intent, C1490c.f2747a)) {
                                    C1490c.f2738O.put(substring, e);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
                C1540h.m2997b("GBD_GGTH", th.getMessage());
            }
        }
    }

    /* renamed from: h */
    private void m2569h() {
        Iterator<Map.Entry<String, List<String>>> it = this.f2501a.entrySet().iterator();
        while (it.hasNext()) {
            if (!m2563a((String) it.next().getKey())) {
                it.remove();
            }
        }
    }

    /* renamed from: b */
    public Map<String, List<String>> mo15001b() {
        TreeMap treeMap;
        synchronized (C1450a.class) {
            treeMap = new TreeMap(this.f2501a == null ? mo15002c() : this.f2501a);
        }
        return treeMap;
    }

    /* renamed from: c */
    public Map<String, List<String>> mo15002c() {
        Map<String, List<String>> map;
        synchronized (C1450a.class) {
            if (this.f2501a == null) {
                this.f2501a = new TreeMap();
            }
            this.f2501a.clear();
            m2568g();
            m2567f();
            C1540h.m2997b("GBD_GGTH", "1-> sdcard, l =  " + this.f2501a.toString());
            m2566e();
            C1540h.m2997b("GBD_GGTH", "2-> sp, l =  " + this.f2501a.toString());
            List<PackageInfo> d = m2565d();
            C1540h.m2997b("GBD_GGTH", "3-> service, l =  " + this.f2501a.toString());
            m2569h();
            C1540h.m2997b("GBD_GGTH", "4-> remove invalid pkg, l =  " + this.f2501a.toString());
            m2562a(d);
            C1540h.m2997b("GBD_GGTH", "5-> remove invalid service, l =  " + this.f2501a.toString());
            map = this.f2501a;
        }
        return map;
    }
}
