package com.p092ta.utdid2.device;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.p092ta.p093a.p096c.C1982f;
import java.lang.reflect.Method;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.ta.utdid2.device.c */
class C1995c {

    /* renamed from: e */
    private static String f3190e;

    /* renamed from: c */
    public static boolean m3395c(Context context) {
        try {
            String e = m3397e(context);
            String c = m3394c(context);
            C1982f.m3366a("", "curProcessName", c);
            if (!TextUtils.isEmpty(c)) {
                if (!TextUtils.isEmpty(e)) {
                    return c.equals(e);
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: c */
    private static String m3394c(Context context) {
        if (!TextUtils.isEmpty(f3190e)) {
            return f3190e;
        }
        f3190e = m3398n();
        C1982f.m3366a("", "currentProcessName", f3190e);
        if (!TextUtils.isEmpty(f3190e)) {
            return f3190e;
        }
        f3190e = m3399o();
        C1982f.m3366a("", "currentProcessName2", f3190e);
        if (!TextUtils.isEmpty(f3190e)) {
            return f3190e;
        }
        f3190e = m3396d(context);
        C1982f.m3366a("", "currentProcessName3", f3190e);
        return f3190e;
    }

    /* renamed from: n */
    private static String m3398n() {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                return Application.getProcessName();
            } catch (Exception unused) {
            }
        }
        return null;
    }

    /* renamed from: o */
    private static String m3399o() {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke((Object) null, new Object[0]);
            if (invoke instanceof String) {
                return (String) invoke;
            }
            return null;
        } catch (Throwable th) {
            C1982f.m3367a("", th, new Object[0]);
            return null;
        }
    }

    /* renamed from: d */
    private static String m3396d(Context context) {
        try {
            int myPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningAppProcesses()) {
                if (next.pid == myPid) {
                    return next.processName;
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    /* renamed from: e */
    private static String m3397e(Context context) {
        PackageInfo a = m3393a(context);
        return a != null ? a.packageName : "";
    }

    /* renamed from: a */
    private static PackageInfo m3393a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e) {
            C1982f.m3367a("", e, new Object[0]);
            return null;
        }
    }
}
