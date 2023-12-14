package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class PackageUtil {
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("PackageUtil", "error:", e);
            return "未知版本";
        }
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("PackageUtil", "error:", e);
            return 0;
        }
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static boolean isInstallApp(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 8192);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
