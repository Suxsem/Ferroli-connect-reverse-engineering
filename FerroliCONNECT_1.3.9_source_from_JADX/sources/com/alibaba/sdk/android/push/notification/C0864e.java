package com.alibaba.sdk.android.push.notification;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import java.util.List;
import java.util.Random;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.alibaba.sdk.android.push.notification.e */
public class C0864e {

    /* renamed from: a */
    private static Random f1305a;

    /* renamed from: a */
    public static int m985a() {
        if (f1305a == null) {
            f1305a = new Random(System.currentTimeMillis());
        }
        return f1305a.nextInt();
    }

    /* renamed from: a */
    private static String m986a(String str) {
        try {
            Class[] clsArr = {String.class};
            Object[] objArr = {str};
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod("get", clsArr).invoke(cls, objArr);
        } catch (Throwable unused) {
            return "";
        }
    }

    /* renamed from: a */
    public static boolean m987a(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
            if (Build.VERSION.SDK_INT > 16) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
                ActivityManager.getMyMemoryState(runningAppProcessInfo);
                return runningAppProcessInfo.importance == 100;
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            return runningTasks.isEmpty() || runningTasks.get(0).topActivity.getPackageName().equals(context.getPackageName());
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: b */
    public static boolean m988b() {
        try {
            String a = m986a("ro.vivo.os.build.display.id");
            if (!Build.MANUFACTURER.equalsIgnoreCase(AgooConstants.MESSAGE_SYSTEM_SOURCE_VIVO)) {
                if (!a.startsWith("Funtouch")) {
                    String a2 = m986a("ro.iqoo.os.build.display.id");
                    if (a2 == null || TextUtils.isEmpty(a2.trim())) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
