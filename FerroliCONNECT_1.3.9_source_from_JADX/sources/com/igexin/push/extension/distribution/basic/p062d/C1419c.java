package com.igexin.push.extension.distribution.basic.p062d;

import android.content.Context;
import android.content.SharedPreferences;

/* renamed from: com.igexin.push.extension.distribution.basic.d.c */
public class C1419c {
    /* renamed from: a */
    public static Object m2436a(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("getui_sp", 0);
        return obj instanceof String ? sharedPreferences.getString(str, (String) obj) : obj instanceof Integer ? Integer.valueOf(sharedPreferences.getInt(str, ((Integer) obj).intValue())) : obj instanceof Boolean ? Boolean.valueOf(sharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue())) : obj instanceof Float ? Float.valueOf(sharedPreferences.getFloat(str, ((Float) obj).floatValue())) : obj instanceof Long ? Long.valueOf(sharedPreferences.getLong(str, ((Long) obj).longValue())) : obj;
    }

    /* renamed from: a */
    public static boolean m2437a(Context context) {
        if (context == null) {
            return false;
        }
        return ((Boolean) m2436a(context, "pri_authorized", false)).booleanValue();
    }
}
