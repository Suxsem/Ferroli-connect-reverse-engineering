package com.taobao.accs.utl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.taobao.accs.common.Constants;
import com.taobao.accs.p102b.C2010a;

/* compiled from: Taobao */
public class Utils {
    public static final String SP_AGOO_BIND_FILE_NAME = "EMAS_AGOO_BIND";
    private static final String TAG = "Utils";
    private static int debugMode = 0;
    private static final byte[] mLock = new byte[0];
    private static int targetSdkVersion = -1;

    public static boolean isTarget26(Context context) {
        if (context == null) {
            return false;
        }
        if (targetSdkVersion == -1) {
            targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        }
        if (targetSdkVersion < 26 || Build.VERSION.SDK_INT < 26) {
            return false;
        }
        return true;
    }

    public static void setMode(Context context, int i) {
        debugMode = i;
    }

    public static int getMode(Context context) {
        return debugMode;
    }

    public static void clearAllSharePreferences(Context context) {
        try {
            synchronized (mLock) {
                SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.clear();
                edit.apply();
            }
        } catch (Throwable th) {
            ALog.m3726e(TAG, "clearAllSharePreferences", th, new Object[0]);
        }
    }

    public static void killService(Context context) {
        try {
            Class<?> loadClass = C2010a.m3433a().mo18324b().loadClass("com.taobao.accs.utl.UtilityImpl");
            loadClass.getMethod("killService", new Class[]{Context.class}).invoke(loadClass, new Object[]{context});
        } catch (Throwable th) {
            ALog.m3726e(TAG, "killService", th, new Object[0]);
        }
    }

    public static boolean isMainProcess(Context context) {
        boolean z;
        try {
            Class<?> loadClass = C2010a.m3433a().mo18324b().loadClass("com.taobao.accs.utl.UtilityImpl");
            z = ((Boolean) loadClass.getMethod("isMainProcess", new Class[]{Context.class}).invoke(loadClass, new Object[]{context})).booleanValue();
        } catch (Throwable th) {
            ALog.m3726e(TAG, "killservice", th, new Object[0]);
            th.printStackTrace();
            z = true;
        }
        ALog.m3728i(TAG, "isMainProcess", "result", Boolean.valueOf(z));
        return z;
    }

    public static void setAgooAppkey(Context context, String str) {
        try {
            Class<?> loadClass = C2010a.m3433a().mo18324b().loadClass("org.android.agoo.common.Config");
            loadClass.getMethod("setAgooAppKey", new Class[]{Context.class, String.class}).invoke(loadClass, new Object[]{context, str});
        } catch (Throwable th) {
            ALog.m3726e(TAG, "setAgooAppkey", th, new Object[0]);
            th.printStackTrace();
        }
    }

    public static void setSpValue(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            ALog.m3727e(TAG, "setSpValue null", new Object[0]);
            return;
        }
        try {
            synchronized (mLock) {
                SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.putString(str, str2);
                edit.apply();
            }
            ALog.m3728i(TAG, "setSpValue", "key", str, "value", str2);
        } catch (Exception e) {
            ALog.m3726e(TAG, "setSpValue fail", e, new Object[0]);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        com.taobao.accs.utl.ALog.m3728i(TAG, "getSpValue", "value", r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        if (android.text.TextUtils.isEmpty(r4) == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0027, code lost:
        com.taobao.accs.utl.ALog.m3727e(TAG, "getSpValue use default!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
        r5 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003c, code lost:
        com.taobao.accs.utl.ALog.m3726e(TAG, "getSpValue fail", r5, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getSpValue(android.content.Context r4, java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            r1 = 0
            byte[] r2 = mLock     // Catch:{ Throwable -> 0x003a }
            monitor-enter(r2)     // Catch:{ Throwable -> 0x003a }
            java.lang.String r3 = "EMAS_ACCS_SDK"
            android.content.SharedPreferences r4 = r4.getSharedPreferences(r3, r1)     // Catch:{ all -> 0x0037 }
            java.lang.String r4 = r4.getString(r5, r0)     // Catch:{ all -> 0x0037 }
            monitor-exit(r2)     // Catch:{ all -> 0x0034 }
            java.lang.String r5 = "Utils"
            java.lang.String r0 = "getSpValue"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r3 = "value"
            r2[r1] = r3     // Catch:{ Throwable -> 0x0032 }
            r3 = 1
            r2[r3] = r4     // Catch:{ Throwable -> 0x0032 }
            com.taobao.accs.utl.ALog.m3728i(r5, r0, r2)     // Catch:{ Throwable -> 0x0032 }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0032 }
            if (r5 == 0) goto L_0x0045
            java.lang.String r5 = "Utils"
            java.lang.String r0 = "getSpValue use default!"
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0032 }
            com.taobao.accs.utl.ALog.m3727e(r5, r0, r2)     // Catch:{ Throwable -> 0x0032 }
            r4 = r6
            goto L_0x0045
        L_0x0032:
            r5 = move-exception
            goto L_0x003c
        L_0x0034:
            r5 = move-exception
            r0 = r4
            goto L_0x0038
        L_0x0037:
            r5 = move-exception
        L_0x0038:
            monitor-exit(r2)     // Catch:{ all -> 0x0037 }
            throw r5     // Catch:{ Throwable -> 0x003a }
        L_0x003a:
            r5 = move-exception
            r4 = r0
        L_0x003c:
            java.lang.Object[] r6 = new java.lang.Object[r1]
            java.lang.String r0 = "Utils"
            java.lang.String r1 = "getSpValue fail"
            com.taobao.accs.utl.ALog.m3726e(r0, r1, r5, r6)
        L_0x0045:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.Utils.getSpValue(android.content.Context, java.lang.String, java.lang.String):java.lang.String");
    }

    public static void clearAgooBindCache(Context context) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences("EMAS_AGOO_BIND", 0).edit();
            edit.clear();
            edit.apply();
        } catch (Exception e) {
            ALog.m3726e(TAG, "clearAgooBindCache", e, new Object[0]);
        }
    }

    public static Bundle getMetaInfo(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                return applicationInfo.metaData;
            }
            return null;
        } catch (Throwable th) {
            ALog.m3726e(TAG, "getMetaInfo", th, new Object[0]);
            return null;
        }
    }

    public static boolean isIPV6Address(String str) {
        int i;
        boolean z;
        int i2;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length < 2) {
            return false;
        }
        if (charArray[0] != ':') {
            i2 = 0;
            z = false;
            i = 0;
        } else if (charArray[1] != ':') {
            return false;
        } else {
            i2 = 1;
            z = false;
            i = 1;
        }
        int i3 = 0;
        boolean z2 = true;
        while (i2 < charArray.length) {
            char c = charArray[i2];
            int digit = Character.digit(c, 16);
            if (digit != -1) {
                i3 = (i3 << 4) + digit;
                if (i3 > 65535) {
                    return false;
                }
                z2 = false;
            } else if (c != ':' || (i = i + 1) > 7) {
                return false;
            } else {
                if (!z2) {
                    i3 = 0;
                    z2 = true;
                } else if (z) {
                    return false;
                } else {
                    z = true;
                }
            }
            i2++;
        }
        if (z || i >= 7) {
            return true;
        }
        return false;
    }
}
