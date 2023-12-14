package com.igexin.push.p053d;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.support.p000v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p050c.C1304ai;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.util.C1576a;
import com.igexin.push.util.C1593r;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import p110io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.igexin.push.d.c */
public class C1362c {

    /* renamed from: a */
    private static C1362c f2230a;

    /* renamed from: b */
    private static long f2231b;

    /* renamed from: c */
    private final int f2232c = 100;

    /* renamed from: d */
    private final int f2233d = 30;

    private C1362c() {
    }

    /* renamed from: a */
    public static C1362c m2166a() {
        if (f2230a == null) {
            f2230a = new C1362c();
        }
        return f2230a;
    }

    /* renamed from: a */
    public void mo14808a(Intent intent) {
        try {
            String stringExtra = intent.getStringExtra("from");
            String stringExtra2 = intent.getStringExtra("did");
            if (!TextUtils.isEmpty(stringExtra)) {
                if (!TextUtils.isEmpty(stringExtra2)) {
                    C1304ai.m1896a().mo14647a(AgooConstants.REPORT_MESSAGE_NULL, stringExtra + "|" + C1343f.f2169f.getPackageName() + "|" + stringExtra2 + "|" + C1343f.f2187x + "|" + C1343f.f2135a + "|" + C1343f.f2182s + "|" + System.currentTimeMillis());
                    return;
                }
            }
            C1179b.m1354a("GuardHelper|doThirdGuardSt from or did is empty");
        } catch (Throwable th) {
            C1179b.m1354a("GuardHelper|doThirdGuardSt exception: " + th.toString());
        }
    }

    /* renamed from: a */
    public void mo14809a(String str) {
        PackageInfo packageInfo;
        if (!TextUtils.isEmpty(str) && str.startsWith("package:")) {
            try {
                String substring = str.substring(8);
                ArrayList arrayList = new ArrayList();
                if (!C1234k.f1823E.equals(SchedulerSupport.NONE)) {
                    arrayList.addAll(Arrays.asList(C1234k.f1823E.split(",")));
                }
                if (!C1576a.m3210a(substring, (List<String>) arrayList) && (packageInfo = C1343f.f2169f.getPackageManager().getPackageInfo(substring, 4)) != null && packageInfo.services != null) {
                    for (ServiceInfo serviceInfo : packageInfo.services) {
                        if (C1576a.m3204a(serviceInfo, packageInfo)) {
                            C1312h.m1937a().mo14681d().put(substring, serviceInfo.name);
                            return;
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: b */
    public void mo14810b() {
        if (!C1234k.f1852m || System.currentTimeMillis() - f2231b < 300000) {
            C1179b.m1354a("GuardHelper|isGuard = false or cur - last < 5min");
            return;
        }
        f2231b = System.currentTimeMillis();
        Map<String, String> d = C1312h.m1937a().mo14681d();
        if (!d.isEmpty() && C1234k.f1820B > 0) {
            int i = 0;
            for (Map.Entry next : d.entrySet()) {
                if (i < C1234k.f1820B) {
                    String str = (String) next.getKey();
                    C1360a aVar = new C1360a(str);
                    aVar.mo14807a((C1363d) new C1364e(str, (String) next.getValue()));
                    aVar.mo14806a();
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    /* renamed from: b */
    public void mo14811b(String str) {
        if (str != null && str.startsWith("package:")) {
            String substring = str.substring(8);
            if (C1312h.m1937a().mo14681d().containsKey(substring)) {
                C1312h.m1937a().mo14681d().remove(substring);
            }
        }
    }

    /* renamed from: c */
    public void mo14812c() {
        try {
            if (C1593r.m3267a(C1343f.f2169f) && C1234k.f1852m) {
                if (mo14813d()) {
                    String packageName = C1343f.f2169f.getPackageName();
                    List<PackageInfo> installedPackages = C1343f.f2169f.getPackageManager().getInstalledPackages(4);
                    if (installedPackages == null) {
                        return;
                    }
                    if (!installedPackages.isEmpty()) {
                        ArrayList arrayList = new ArrayList();
                        if (!C1234k.f1823E.equals(SchedulerSupport.NONE)) {
                            arrayList.addAll(Arrays.asList(C1234k.f1823E.split(",")));
                        }
                        for (PackageInfo next : installedPackages) {
                            if ((next.applicationInfo.flags & 1) == 0 || (next.applicationInfo.flags & 128) != 0) {
                                if (!C1576a.m3210a(next.applicationInfo.packageName, (List<String>) arrayList)) {
                                    ServiceInfo[] serviceInfoArr = next.services;
                                    if (serviceInfoArr != null) {
                                        if (serviceInfoArr.length != 0) {
                                            int length = serviceInfoArr.length;
                                            int i = 0;
                                            while (true) {
                                                if (i >= length) {
                                                    break;
                                                }
                                                ServiceInfo serviceInfo = serviceInfoArr[i];
                                                if (!C1576a.m3204a(serviceInfo, next)) {
                                                    i++;
                                                } else if (!packageName.equals(next.packageName)) {
                                                    C1312h.m1937a().mo14681d().put(next.packageName, serviceInfo.name);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: d */
    public boolean mo14813d() {
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) C1343f.f2169f.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getMemoryInfo(memoryInfo);
            long j = (memoryInfo.availMem / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
            if (memoryInfo.lowMemory) {
                C1179b.m1356b("GuardHelper", "system in lowMemory, available menmory = " + j + "M");
                return false;
            } else if (j < 100) {
                return false;
            } else {
                return (((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) + Runtime.getRuntime().freeMemory()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID > 30;
            }
        } catch (Throwable unused) {
            return false;
        }
    }
}
