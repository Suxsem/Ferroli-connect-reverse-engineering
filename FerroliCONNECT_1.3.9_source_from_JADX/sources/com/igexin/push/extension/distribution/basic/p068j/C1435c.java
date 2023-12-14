package com.igexin.push.extension.distribution.basic.p068j;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.extension.distribution.basic.j.c */
public class C1435c {

    /* renamed from: a */
    private static String f2488a = "";

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0063, code lost:
        if (r5 != null) goto L_0x0052;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005e A[SYNTHETIC, Splitter:B:21:0x005e] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList<java.lang.String[]> m2502a(java.lang.String r5, java.lang.String r6) {
        /*
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            java.lang.Process r5 = r0.exec(r5)     // Catch:{ Throwable -> 0x0062, all -> 0x005a }
            java.io.InputStream r5 = r5.getInputStream()     // Catch:{ Throwable -> 0x0062, all -> 0x005a }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0062, all -> 0x005a }
            r0.<init>(r5)     // Catch:{ Throwable -> 0x0062, all -> 0x005a }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0062, all -> 0x005a }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x0062, all -> 0x005a }
        L_0x001c:
            java.lang.String r0 = r5.readLine()     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            if (r0 == 0) goto L_0x0052
            java.lang.String r2 = " +"
            java.lang.String[] r0 = r0.split(r2)     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            int r2 = r0.length     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            r3 = 1
            int r2 = r2 - r3
            r2 = r0[r2]     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            boolean r2 = r6.equals(r2)     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            if (r2 == 0) goto L_0x0036
            r1.add(r0)     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
        L_0x0036:
            int r2 = r0.length     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            int r2 = r2 - r3
            r2 = r0[r2]     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            java.lang.String r4 = "zygote"
            boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            if (r2 == 0) goto L_0x001c
            r2 = 0
            r2 = r0[r2]     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            java.lang.String r4 = "root"
            boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            if (r2 == 0) goto L_0x001c
            r0 = r0[r3]     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            f2488a = r0     // Catch:{ Throwable -> 0x0058, all -> 0x0056 }
            goto L_0x001c
        L_0x0052:
            r5.close()     // Catch:{ Exception -> 0x0066 }
            goto L_0x0066
        L_0x0056:
            r6 = move-exception
            goto L_0x005c
        L_0x0058:
            goto L_0x0063
        L_0x005a:
            r6 = move-exception
            r5 = r2
        L_0x005c:
            if (r5 == 0) goto L_0x0061
            r5.close()     // Catch:{ Exception -> 0x0061 }
        L_0x0061:
            throw r6
        L_0x0062:
            r5 = r2
        L_0x0063:
            if (r5 == 0) goto L_0x0066
            goto L_0x0052
        L_0x0066:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p068j.C1435c.m2502a(java.lang.String, java.lang.String):java.util.ArrayList");
    }

    /* renamed from: a */
    public static boolean m2503a() {
        try {
            Class.forName("com.igexin.push.util.EncryptUtils");
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m2504a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m2505a(Intent intent, Context context) {
        if (intent == null || context == null) {
            return false;
        }
        try {
            List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            return queryIntentActivities != null && queryIntentActivities.size() > 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m2506a(String str) {
        try {
            C1343f.f2169f.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m2507a(List<String> list) {
        if (list == null || list.isEmpty() || TextUtils.isEmpty(C1416f.f2437o)) {
            return false;
        }
        List asList = Arrays.asList(C1416f.f2437o.split(","));
        for (String contains : list) {
            if (asList.contains(contains)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    public static boolean m2508b() {
        try {
            for (String lowerCase : C1416f.f2435m.split(",")) {
                if (Build.MODEL.toLowerCase().contains(lowerCase.toLowerCase())) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    /* renamed from: b */
    public static boolean m2509b(String str) {
        try {
            Context context = C1416f.f2423a;
            ActivityManager activityManager = (ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
            if (Build.VERSION.SDK_INT < 21) {
                ComponentName componentName = activityManager.getRunningTasks(1).get(0).topActivity;
                if (componentName != null && componentName.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            } else {
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                if (runningAppProcesses != null && !runningAppProcesses.isEmpty()) {
                    String packageName = context.getPackageName();
                    for (ActivityManager.RunningAppProcessInfo next : runningAppProcesses) {
                        if (packageName.equals(next.processName) && next.importance == 100) {
                            return true;
                        }
                    }
                }
            }
            Iterator<String[]> it = m2502a("ps -P", str).iterator();
            while (it.hasNext()) {
                String[] next2 = it.next();
                if (str.equals(next2[next2.length - 1]) && "fg".equals(next2[5]) && f2488a.equals(next2[2])) {
                    return true;
                }
            }
        } catch (Throwable th) {
            C1179b.m1354a("CoreAction_CheckUtils|" + th.toString());
        }
        return false;
    }

    /* renamed from: c */
    public static boolean m2510c() {
        if (!TextUtils.isEmpty(C1416f.f2437o)) {
            for (String str : C1416f.f2437o.split(",")) {
                if (m2506a(str)) {
                    C1179b.m1354a("CoreAction_CheckUtils|" + str + " in gactivityblacklist");
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: c */
    public static boolean m2511c(String str) {
        if (TextUtils.isEmpty(C1416f.f2438p)) {
            C1179b.m1354a("CoreAction_CheckUtils|pMBlacklist is empty or null");
            return false;
        }
        String[] split = C1416f.f2438p.split(",");
        if (split.length == 0) {
            C1179b.m1354a("CoreAction_CheckUtils|pMBlacklist is empty or null");
            return false;
        }
        String str2 = Build.BRAND;
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        C1179b.m1354a("CoreAction_CheckUtils|brand = " + str2);
        for (String str3 : split) {
            if (!TextUtils.isEmpty(str3)) {
                String[] split2 = str3.split(":");
                if (!str2.equalsIgnoreCase(split2[0])) {
                    continue;
                } else if (split2.length == 3) {
                    if (!Boolean.parseBoolean(split2[1])) {
                        return true;
                    }
                } else if (split2.length == 4) {
                    String str4 = split2[1];
                    boolean parseBoolean = Boolean.parseBoolean(split2[2]);
                    if (str.equals(str4) && !parseBoolean) {
                        return true;
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    /* renamed from: d */
    public static boolean m2512d() {
        String str = C1416f.f2430h;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            for (String str2 : str.split(",")) {
                if (str2.contains("|")) {
                    if (str2.contains("~")) {
                        String substring = str2.substring(0, str2.indexOf("|"));
                        String[] split = str2.substring(str2.indexOf("|") + 1).split("~");
                        if (split.length == 2) {
                            int parseInt = Integer.parseInt(split[0]);
                            int parseInt2 = Integer.parseInt(split[1]);
                            if (Build.BRAND.equalsIgnoreCase(substring) && Build.VERSION.SDK_INT >= parseInt && Build.VERSION.SDK_INT <= parseInt2) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    /* renamed from: d */
    public static boolean m2513d(String str) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(C1416f.f2437o)) {
            for (String equals : C1416f.f2437o.split(",")) {
                if (str.equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }
}
