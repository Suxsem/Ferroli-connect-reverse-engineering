package com.igexin.push.extension.distribution.gbd.p069a.p073d;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.d.d */
public class C1472d {

    /* renamed from: a */
    private Map<String, List<String>> f2571a;

    private C1472d() {
    }

    /* renamed from: a */
    public static synchronized C1472d m2668a() {
        C1472d a;
        synchronized (C1472d.class) {
            a = C1474f.f2572a;
        }
        return a;
    }

    /* renamed from: a */
    private void m2669a(List<PackageInfo> list) {
        ServiceInfo[] serviceInfoArr;
        if (list != null) {
            try {
                if (!list.isEmpty()) {
                    for (PackageInfo next : list) {
                        if (((next.applicationInfo.flags & 1) == 0 || (next.applicationInfo.flags & 128) != 0) && (serviceInfoArr = next.services) != null) {
                            if (serviceInfoArr.length != 0) {
                                if (this.f2571a.containsKey(next.packageName)) {
                                    ArrayList arrayList = new ArrayList();
                                    for (ServiceInfo serviceInfo : serviceInfoArr) {
                                        arrayList.add(serviceInfo.name);
                                    }
                                    if (this.f2571a.get(next.packageName) != null) {
                                        this.f2571a.get(next.packageName).retainAll(arrayList);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
                C1540h.m2997b("GBD_GPH", th.getMessage());
            }
        }
    }

    /* renamed from: a */
    private static boolean m2670a(String str) {
        try {
            return C1490c.f2747a.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (Exception e) {
            C1540h.m2996a(e);
            return false;
        }
    }

    /* renamed from: b */
    private static boolean m2671b(String str) {
        return "com.sdk.plus.EnhService".equals(str);
    }

    /* renamed from: d */
    private List<PackageInfo> m2672d() {
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
                                    if (m2671b(serviceInfo.name)) {
                                        if (!this.f2571a.containsKey(next.packageName)) {
                                            this.f2571a.put(next.packageName, new ArrayList());
                                        }
                                        if (!this.f2571a.get(next.packageName).contains(serviceInfo.name)) {
                                            this.f2571a.get(next.packageName).add(serviceInfo.name);
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
            C1540h.m2997b("GBD_GPH", th.getMessage());
        }
    }

    /* renamed from: e */
    private void m2673e() {
        String[] list;
        String[] split;
        File file = new File("/sdcard/libs/w");
        if (file.exists() && (list = file.list()) != null && list.length != 0) {
            if (C1490c.f2739P == null || C1490c.f2740Q == null) {
                C1490c.f2739P = new ConcurrentHashMap<>();
                C1490c.f2740Q = new ConcurrentHashMap<>();
            }
            C1490c.f2739P.clear();
            C1490c.f2740Q.clear();
            for (String str : list) {
                try {
                    if (!TextUtils.isEmpty(str) && str.endsWith(".db")) {
                        String substring = str.substring(0, str.lastIndexOf("."));
                        if (!this.f2571a.containsKey(substring)) {
                            this.f2571a.put(substring, new ArrayList());
                        }
                        if (!this.f2571a.get(substring).contains("com.sdk.plus.EnhService")) {
                            this.f2571a.get(substring).add("com.sdk.plus.EnhService");
                        }
                        String b = C1541i.m3012b(substring, "/sdcard/libs/w");
                        if (!TextUtils.isEmpty(b) && (split = b.split("\\|")) != null && split.length == 5) {
                            String str2 = split[0];
                            String str3 = split[3];
                            String str4 = split[2];
                            if (!TextUtils.isEmpty(str2)) {
                                C1490c.f2746W = str2;
                            }
                            if (!TextUtils.isEmpty(str3)) {
                                if (!"null".equalsIgnoreCase(str3)) {
                                    this.f2571a.get(substring).add(str3);
                                }
                            }
                            if (!TextUtils.isEmpty(str4) && !"null".equalsIgnoreCase(str4)) {
                                C1490c.f2740Q.put(substring, str4);
                            }
                            String str5 = split[4];
                            if (!TextUtils.isEmpty(str5) && !"null".equalsIgnoreCase(str5)) {
                                Intent intent = new Intent();
                                intent.setClassName(substring, str5);
                                if (C1541i.m3018c(intent, C1490c.f2747a)) {
                                    C1490c.f2739P.put(substring, str5);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    C1540h.m2996a(th);
                    C1540h.m2997b("GBD_GPH", th.getMessage());
                }
            }
        }
    }

    /* renamed from: f */
    private void m2674f() {
        Iterator<Map.Entry<String, List<String>>> it = this.f2571a.entrySet().iterator();
        while (it.hasNext()) {
            if (!m2670a((String) it.next().getKey())) {
                it.remove();
            }
        }
    }

    /* renamed from: b */
    public Map<String, List<String>> mo15027b() {
        TreeMap treeMap;
        synchronized (C1472d.class) {
            treeMap = new TreeMap(this.f2571a == null ? mo15028c() : this.f2571a);
        }
        return treeMap;
    }

    /* renamed from: c */
    public Map<String, List<String>> mo15028c() {
        Map<String, List<String>> map;
        synchronized (C1472d.class) {
            if (this.f2571a == null) {
                this.f2571a = new TreeMap();
            }
            this.f2571a.clear();
            try {
                m2673e();
                C1540h.m2997b("GBD_GPH", "1-> sdcard, l =  " + this.f2571a.toString());
                List<PackageInfo> d = m2672d();
                C1540h.m2997b("GBD_GPH", "2-> service, l =  " + this.f2571a.toString());
                m2674f();
                C1540h.m2997b("GBD_GPH", "3-> remove invalid pkg, l =  " + this.f2571a.toString());
                m2669a(d);
                C1540h.m2997b("GBD_GPH", "4-> remove invalid service, l =  " + this.f2571a.toString());
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
            map = this.f2571a;
        }
        return map;
    }
}
