package com.taobao.accs.utl;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.text.TextUtils;
import com.p092ta.utdid2.device.UTDevice;
import com.taobao.accs.ChannelService;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.data.MsgDistributeService;
import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import org.android.agoo.common.AgooConstants;

/* compiled from: Taobao */
public class AdapterUtilityImpl {
    public static String BACK_APP_KEY = "";
    private static final String TAG = "AdapterUtilityImpl";
    public static final String channelService = ChannelService.class.getName();
    public static String mAgooAppSecret = null;
    private static boolean mChecked = false;
    private static boolean mIsMainProc = true;
    public static final String msgService = MsgDistributeService.class.getName();

    public static boolean isMainProcess(Context context) {
        if (mChecked) {
            return mIsMainProc;
        }
        mIsMainProc = context.getPackageName().equalsIgnoreCase(getProcessName(context));
        mChecked = true;
        return mIsMainProc;
    }

    public static String getProcessName(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                return Application.getProcessName();
            }
        } catch (Exception unused) {
        }
        String processNameByActivityThread = getProcessNameByActivityThread(context);
        if (!TextUtils.isEmpty(processNameByActivityThread)) {
            return processNameByActivityThread;
        }
        String processNameByPid = getProcessNameByPid();
        if (!TextUtils.isEmpty(processNameByPid)) {
            return processNameByPid;
        }
        return getProcessNameByAm(context);
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
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0057 A[SYNTHETIC, Splitter:B:21:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0061 A[SYNTHETIC, Splitter:B:27:0x0061] */
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
            if (r0 == 0) goto L_0x005a
            r0.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x005a
        L_0x0042:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x005a
        L_0x0047:
            r0 = move-exception
            goto L_0x005f
        L_0x0049:
            r2 = move-exception
            r0 = r1
        L_0x004b:
            java.lang.String r3 = "AdapterUtilityImpl"
            java.lang.String r4 = "getProcessNameByPid error: "
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x005b }
            com.taobao.accs.utl.ALog.m3730w(r3, r4, r2, r5)     // Catch:{ all -> 0x005b }
            if (r0 == 0) goto L_0x005a
            r0.close()     // Catch:{ IOException -> 0x0042 }
        L_0x005a:
            return r1
        L_0x005b:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x005f:
            if (r1 == 0) goto L_0x0069
            r1.close()     // Catch:{ IOException -> 0x0065 }
            goto L_0x0069
        L_0x0065:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0069:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.AdapterUtilityImpl.getProcessNameByPid():java.lang.String");
    }

    private static String getProcessNameByActivityThread(Context context) {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, context.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            ALog.m3730w(TAG, "getProcessNameByActivityThread error: ", e, new Object[0]);
            return null;
        }
    }

    public static long getUsableSpace() {
        try {
            File dataDirectory = Environment.getDataDirectory();
            if (dataDirectory == null) {
                return -1;
            }
            return dataDirectory.getUsableSpace();
        } catch (Throwable th) {
            ALog.m3726e(TAG, "getUsableSpace", th, new Object[0]);
            return -1;
        }
    }

    public static String getStackMsg(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString());
                    stringBuffer.append("\n");
                }
            }
        } catch (Exception unused) {
        }
        return stringBuffer.toString();
    }

    public static String getDeviceId(Context context) {
        return UTDevice.getUtdid(context);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context == null) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = AdapterGlobalClientInfo.getInstance(context).getConnectivityManager().getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnected();
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static final boolean checkIsWritable(String str, int i) {
        if (str == null) {
            return false;
        }
        StatFs statFs = new StatFs(str);
        int blockSize = statFs.getBlockSize();
        boolean z = statFs.getAvailableBlocks() > 10 && ((long) statFs.getAvailableBlocks()) * ((long) blockSize) > ((long) i);
        if (!z) {
            ALog.m3731w("FileCheckUtils", "target : " + i + " st.getAvailableBlocks()=" + statFs.getAvailableBlocks() + ",st.getAvailableBlocks() * blockSize=" + (((long) statFs.getAvailableBlocks()) * ((long) blockSize)), new Object[0]);
        }
        return z;
    }

    public static String isNotificationEnabled(Context context) {
        boolean z = true;
        if (Utils.isTarget26(context)) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            String packageName = context.getApplicationContext().getPackageName();
            int i = applicationInfo.uid;
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                Method declaredMethod = notificationManager.getClass().getDeclaredMethod("getService", new Class[0]);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(notificationManager, new Object[0]);
                Method declaredMethod2 = invoke.getClass().getDeclaredMethod("areNotificationsEnabledForPackage", new Class[]{String.class, Integer.TYPE});
                declaredMethod2.setAccessible(true);
                return String.valueOf(declaredMethod2.invoke(invoke, new Object[]{packageName, Integer.valueOf(i)}));
            } catch (Throwable th) {
                ALog.m3726e(TAG, "Android O isNotificationEnabled", th, new Object[0]);
                return "unknown";
            }
        } else {
            try {
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
                ApplicationInfo applicationInfo2 = context.getApplicationInfo();
                String packageName2 = context.getApplicationContext().getPackageName();
                int i2 = applicationInfo2.uid;
                Class<?> cls = Class.forName(AppOpsManager.class.getName());
                if (((Integer) cls.getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(appOpsManager)).intValue()), Integer.valueOf(i2), packageName2})).intValue() != 0) {
                    z = false;
                }
                return String.valueOf(z);
            } catch (Throwable th2) {
                ALog.m3726e(TAG, "isNotificationEnabled", th2, new Object[0]);
                return "unknown";
            }
        }
    }
}
