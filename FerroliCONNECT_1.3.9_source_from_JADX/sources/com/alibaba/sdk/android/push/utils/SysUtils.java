package com.alibaba.sdk.android.push.utils;

import android.content.Context;
import android.os.Process;
import com.taobao.accs.utl.ALog;

public class SysUtils {
    private static final String TAG = "com.alibaba.sdk.android.push.utils.SysUtils";
    private static Boolean isMainProcess;

    public static boolean isMainProcess(Context context) {
        boolean z;
        Boolean bool = isMainProcess;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.processName;
            boolean z2 = !"".equals(str) && str.equalsIgnoreCase(getProcessName(context, Process.myPid()));
            try {
                isMainProcess = Boolean.valueOf(z2);
                return z2;
            } catch (Throwable th) {
                Throwable th2 = th;
                z = z2;
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            z = false;
            ALog.m3726e(TAG, "isMainProcess:get process name failed.", th, new Object[0]);
            return z;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:10|11|19) */
    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        com.taobao.accs.utl.ALog.m3727e(TAG, "获取进程名失败", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0026 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getProcessName(android.content.Context r5, int r6) {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.lang.String r2 = "activity"
            java.lang.Object r5 = r5.getSystemService(r2)     // Catch:{ Throwable -> 0x0030 }
            android.app.ActivityManager r5 = (android.app.ActivityManager) r5     // Catch:{ Throwable -> 0x0030 }
            java.util.List r5 = r5.getRunningAppProcesses()     // Catch:{ Throwable -> 0x0030 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ Throwable -> 0x0030 }
        L_0x0013:
            boolean r2 = r5.hasNext()     // Catch:{ Throwable -> 0x0030 }
            if (r2 == 0) goto L_0x003a
            java.lang.Object r2 = r5.next()     // Catch:{ Throwable -> 0x0030 }
            android.app.ActivityManager$RunningAppProcessInfo r2 = (android.app.ActivityManager.RunningAppProcessInfo) r2     // Catch:{ Throwable -> 0x0030 }
            int r3 = r2.pid     // Catch:{ Exception -> 0x0026 }
            if (r3 != r6) goto L_0x0013
            java.lang.String r0 = r2.processName     // Catch:{ Exception -> 0x0026 }
            goto L_0x0013
        L_0x0026:
            java.lang.String r2 = TAG     // Catch:{ Throwable -> 0x0030 }
            java.lang.String r3 = "获取进程名失败"
            java.lang.Object[] r4 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0030 }
            com.taobao.accs.utl.ALog.m3727e(r2, r3, r4)     // Catch:{ Throwable -> 0x0030 }
            goto L_0x0013
        L_0x0030:
            r5 = move-exception
            java.lang.String r6 = TAG
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = "getProcessName:get process name failed."
            com.taobao.accs.utl.ALog.m3726e(r6, r2, r5, r1)
        L_0x003a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.utils.SysUtils.getProcessName(android.content.Context, int):java.lang.String");
    }
}
