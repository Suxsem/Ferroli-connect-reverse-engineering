package com.alibaba.sdk.android.push.common.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Process;
import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.push.common.p020a.C0801a;
import com.alibaba.sdk.android.push.common.p020a.C0802b;
import java.lang.reflect.Method;
import java.util.List;
import org.android.agoo.common.AgooConstants;

public class AppInfoUtil {
    public static final int CHANNEL_SERVICE_PROCESS = 1;
    private static final String TAG = "MPS:AppInfoUtil";
    private static AmsLogger sLogger = AmsLogger.getLogger(TAG);

    public static String getAppVersionName(Context context) {
        if (context != null) {
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                sLogger.mo9542e("version name not found!", e);
                return null;
            }
        } else {
            sLogger.mo9541e("Get app version name failed: context null");
            return null;
        }
    }

    public static String getChannelServiceData(Context context, int i) {
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context.getPackageName(), C0801a.CHANNEL_SERVICE.mo9882a()), 131584);
            if (i != 1) {
                return null;
            }
            return serviceInfo.processName;
        } catch (PackageManager.NameNotFoundException unused) {
            AmsLogger amsLogger = sLogger;
            amsLogger.mo9541e("Meta data name " + C0801a.CHANNEL_SERVICE.mo9882a() + " not found!");
            return null;
        }
    }

    public static String getProcessName(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                return Application.getProcessName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String processNameByActivityThread = getProcessNameByActivityThread(context);
        if (!TextUtils.isEmpty(processNameByActivityThread)) {
            return processNameByActivityThread;
        }
        String processNameByPid = getProcessNameByPid();
        return !TextUtils.isEmpty(processNameByPid) ? processNameByPid : getProcessNameByAm(context);
    }

    private static String getProcessNameByActivityThread(Context context) {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, context.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            AmsLogger amsLogger = sLogger;
            amsLogger.mo9538d("getProcessNameByActivityThread error: " + e);
            return null;
        }
    }

    private static String getProcessNameByAm(Context context) {
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0063 A[SYNTHETIC, Splitter:B:20:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x006d A[SYNTHETIC, Splitter:B:26:0x006d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getProcessNameByPid() {
        /*
            int r0 = android.os.Process.myPid()
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r3.<init>()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r4 = "/proc/"
            r3.append(r4)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r3.append(r0)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r0 = "/cmdline"
            r3.append(r0)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r0 = r3.toString()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            if (r0 == 0) goto L_0x003b
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r0.<init>(r3)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r2 = r0.readLine()     // Catch:{ Exception -> 0x0039 }
            java.lang.String r1 = r2.trim()     // Catch:{ Exception -> 0x0039 }
            goto L_0x003c
        L_0x0039:
            r2 = move-exception
            goto L_0x004b
        L_0x003b:
            r0 = r1
        L_0x003c:
            if (r0 == 0) goto L_0x0066
            r0.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0066
        L_0x0042:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0066
        L_0x0047:
            r0 = move-exception
            goto L_0x006b
        L_0x0049:
            r2 = move-exception
            r0 = r1
        L_0x004b:
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r3 = sLogger     // Catch:{ all -> 0x0067 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0067 }
            r4.<init>()     // Catch:{ all -> 0x0067 }
            java.lang.String r5 = "getProcessNameByPid error: "
            r4.append(r5)     // Catch:{ all -> 0x0067 }
            r4.append(r2)     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x0067 }
            r3.mo9538d(r2)     // Catch:{ all -> 0x0067 }
            if (r0 == 0) goto L_0x0066
            r0.close()     // Catch:{ IOException -> 0x0042 }
        L_0x0066:
            return r1
        L_0x0067:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x006b:
            if (r1 == 0) goto L_0x0075
            r1.close()     // Catch:{ IOException -> 0x0071 }
            goto L_0x0075
        L_0x0071:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0075:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.common.util.AppInfoUtil.getProcessNameByPid():java.lang.String");
    }

    public static boolean isChannelProcess(Context context) {
        try {
            String str = context.getPackageName() + C0802b.m768d();
            return !str.equals("") && str.equalsIgnoreCase(getProcessName(context));
        } catch (Throwable th) {
            sLogger.mo9542e("isChannelProcess:get process name failed.", th);
            return false;
        }
    }

    public static boolean isComponentExists(Context context, String str, String str2) {
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            ComponentName componentName = new ComponentName(context.getPackageName(), str);
            try {
                if (str2.equals(NotificationCompat.CATEGORY_SERVICE)) {
                    packageManager.getServiceInfo(componentName, 131584);
                } else if (str2.equals(AgooConstants.OPEN_ACTIIVTY_NAME)) {
                    packageManager.getActivityInfo(componentName, 131584);
                } else if (!str2.equals("receiver")) {
                    return false;
                } else {
                    packageManager.getReceiverInfo(componentName, 131584);
                }
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                AmsLogger amsLogger = sLogger;
                amsLogger.mo9541e("component:" + str + " not found!");
                return false;
            }
        } else {
            sLogger.mo9541e("Get component info failed: context null");
            return false;
        }
    }

    public static boolean isMainProcess(Context context) {
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.processName;
            return !str.equals("") && str.equalsIgnoreCase(getProcessName(context));
        } catch (Throwable th) {
            sLogger.mo9542e("isMainProcess:get process name failed.", th);
            return false;
        }
    }

    public static boolean isPermissionGranted(Context context, String str) {
        if (context != null) {
            try {
                return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
            } catch (Throwable th) {
                sLogger.mo9542e("isPermissionGranted:Get permission info failed.", th);
                return false;
            }
        } else {
            sLogger.mo9541e("Get permission info failed: context null");
            return false;
        }
    }
}
