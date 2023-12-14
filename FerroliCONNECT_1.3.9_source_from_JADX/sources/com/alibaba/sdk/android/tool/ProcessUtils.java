package com.alibaba.sdk.android.tool;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import java.lang.reflect.Method;
import java.util.List;
import org.android.agoo.common.AgooConstants;

public class ProcessUtils {
    /* renamed from: a */
    private static String m1034a(Context context) {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, context.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            C0897b.m1054a("Utils", "getProcessNameByActivityThread error: " + e);
            return null;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0063 A[SYNTHETIC, Splitter:B:21:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006d A[SYNTHETIC, Splitter:B:27:0x006d] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m1035b(android.content.Context r6) {
        /*
            int r6 = android.os.Process.myPid()
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r2.<init>()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r3 = "/proc/"
            r2.append(r3)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r2.append(r6)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r6 = "/cmdline"
            r2.append(r6)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r6 = r2.toString()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r1.<init>(r6)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            boolean r6 = r1.exists()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            if (r6 == 0) goto L_0x003b
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r6.<init>(r2)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r1 = r6.readLine()     // Catch:{ Exception -> 0x0039 }
            java.lang.String r0 = r1.trim()     // Catch:{ Exception -> 0x0039 }
            goto L_0x003c
        L_0x0039:
            r1 = move-exception
            goto L_0x004b
        L_0x003b:
            r6 = r0
        L_0x003c:
            if (r6 == 0) goto L_0x0066
            r6.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0066
        L_0x0042:
            r6 = move-exception
            r6.printStackTrace()
            goto L_0x0066
        L_0x0047:
            r6 = move-exception
            goto L_0x006b
        L_0x0049:
            r1 = move-exception
            r6 = r0
        L_0x004b:
            java.lang.String r2 = "Utils"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0067 }
            r3.<init>()     // Catch:{ all -> 0x0067 }
            java.lang.String r4 = "getProcessNameByPid error: "
            r3.append(r4)     // Catch:{ all -> 0x0067 }
            r3.append(r1)     // Catch:{ all -> 0x0067 }
            java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x0067 }
            com.alibaba.sdk.android.tool.C0897b.m1054a(r2, r1)     // Catch:{ all -> 0x0067 }
            if (r6 == 0) goto L_0x0066
            r6.close()     // Catch:{ IOException -> 0x0042 }
        L_0x0066:
            return r0
        L_0x0067:
            r0 = move-exception
            r5 = r0
            r0 = r6
            r6 = r5
        L_0x006b:
            if (r0 == 0) goto L_0x0075
            r0.close()     // Catch:{ IOException -> 0x0071 }
            goto L_0x0075
        L_0x0071:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0075:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tool.ProcessUtils.m1035b(android.content.Context):java.lang.String");
    }

    /* renamed from: c */
    private static String m1036c(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
        if (activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null) {
            return "";
        }
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo next : runningAppProcesses) {
            if (next.pid == myPid) {
                return next.processName;
            }
        }
        return "";
    }

    public static String getProcessName(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Application.getProcessName();
        }
        String a = m1034a(context);
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        String b = m1035b(context);
        return !TextUtils.isEmpty(b) ? b : m1036c(context);
    }

    public static boolean isMainProcess(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageName().equalsIgnoreCase(getProcessName(context));
    }
}
