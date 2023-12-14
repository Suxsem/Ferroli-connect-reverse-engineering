package com.szacs.ferroliconnect.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import org.android.agoo.common.AgooConstants;

public class LaunchUtil {
    public static boolean appIsExist(Context context, String str) {
        if ("".equals(str.trim())) {
            return false;
        }
        for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(8192)) {
            if (packageInfo.packageName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static void setTopApp(Context context) {
        if (!isRunningForeground(context)) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
            for (ActivityManager.RunningTaskInfo next : activityManager.getRunningTasks(100)) {
                if (next.topActivity.getPackageName().equals(context.getPackageName())) {
                    activityManager.moveTaskToFront(next.id, 0);
                    return;
                }
            }
        }
    }

    public static boolean isRunningForeground(Context context) {
        for (ActivityManager.RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningAppProcesses()) {
            if (next.importance == 100 && next.processName.equals(context.getApplicationInfo().processName)) {
                return true;
            }
        }
        return false;
    }
}
