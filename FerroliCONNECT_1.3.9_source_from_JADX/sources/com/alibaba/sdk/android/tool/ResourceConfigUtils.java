package com.alibaba.sdk.android.tool;

import android.content.Context;
import android.text.TextUtils;

public class ResourceConfigUtils {
    /* renamed from: a */
    private static int m1037a(Context context, String str, String str2) {
        if (context != null && !TextUtils.isEmpty(str)) {
            return context.getResources().getIdentifier(str, str2, context.getPackageName());
        }
        return 0;
    }

    public static int getColorFromRes(Context context, String str) {
        int a = m1037a(context, str, "color");
        if (a == 0) {
            return -1;
        }
        try {
            return context.getResources().getColor(a);
        } catch (Exception e) {
            C0897b.m1055b("Res-Config", e.getMessage());
            return -1;
        }
    }

    public static String[] getStringArrayFromRes(Context context, String str) {
        int a = m1037a(context, str, "array");
        if (a == 0) {
            return null;
        }
        try {
            return context.getResources().getStringArray(a);
        } catch (Exception e) {
            C0897b.m1055b("Res-Config", e.getMessage());
            return null;
        }
    }

    public static String getStringFromRes(Context context, String str) {
        int a = m1037a(context, str, "string");
        if (a == 0) {
            return null;
        }
        try {
            return context.getResources().getString(a);
        } catch (Exception e) {
            C0897b.m1055b("Res-Config", e.getMessage());
            return null;
        }
    }
}
