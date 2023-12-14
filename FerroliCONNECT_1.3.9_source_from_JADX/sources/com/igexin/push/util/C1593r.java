package com.igexin.push.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1356s;

/* renamed from: com.igexin.push.util.r */
public class C1593r {
    /* renamed from: a */
    public static void m3265a(Context context, Intent intent) {
        try {
            if (intent.hasExtra("us")) {
                String stringExtra = intent.getStringExtra("us");
                String name = C1356s.m2138a().mo14786c(context).getName();
                if (!name.equals(stringExtra)) {
                    if (!stringExtra.equals(C1275b.f1913q)) {
                        m3266a(context, "us", stringExtra);
                    } else if (!TextUtils.isEmpty(name)) {
                        m3266a(context, "us", "");
                    }
                }
            }
            if (intent.hasExtra("uis")) {
                Class d = C1356s.m2138a().mo14788d(context);
                String stringExtra2 = intent.getStringExtra("uis");
                if (!TextUtils.isEmpty(stringExtra2)) {
                    if (d == null || !stringExtra2.equals(d.getName())) {
                        m3266a(context, "uis", stringExtra2);
                    }
                } else if (d != null) {
                    m3266a(context, "uis", "");
                }
            }
            if (intent.hasExtra("ua")) {
                m3266a(context, "ua", intent.getStringExtra("ua"));
            }
            if (intent.hasExtra("pri_authorized")) {
                m3266a(context, "pri_alert", true);
                m3266a(context, "pri_authorized", Boolean.valueOf(intent.getBooleanExtra("pri_authorized", false)));
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public static void m3266a(Context context, String str, Object obj) {
        if (context != null) {
            SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences("getui_sp", 0).edit();
            if (obj instanceof String) {
                edit.putString(str, (String) obj);
            } else if (obj instanceof Integer) {
                edit.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Boolean) {
                edit.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Float) {
                edit.putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Long) {
                edit.putLong(str, ((Long) obj).longValue());
            }
            edit.apply();
        }
    }

    /* renamed from: a */
    public static boolean m3267a(Context context) {
        if (context == null) {
            return false;
        }
        return ((Boolean) m3270c(context, "pri_authorized", false)).booleanValue();
    }

    /* renamed from: b */
    public static void m3268b(Context context, String str, Object obj) {
        if (context != null) {
            SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences("getui_sp", 0).edit();
            if (obj instanceof String) {
                edit.putString(str, (String) obj);
            } else if (obj instanceof Integer) {
                edit.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Boolean) {
                edit.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Float) {
                edit.putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Long) {
                edit.putLong(str, ((Long) obj).longValue());
            }
            edit.commit();
        }
    }

    /* renamed from: b */
    public static boolean m3269b(Context context) {
        if (context == null) {
            return false;
        }
        return ((Boolean) m3270c(context, "pri_alert", false)).booleanValue();
    }

    /* renamed from: c */
    public static Object m3270c(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("getui_sp", 0);
        return obj instanceof String ? sharedPreferences.getString(str, (String) obj) : obj instanceof Integer ? Integer.valueOf(sharedPreferences.getInt(str, ((Integer) obj).intValue())) : obj instanceof Boolean ? Boolean.valueOf(sharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue())) : obj instanceof Float ? Float.valueOf(sharedPreferences.getFloat(str, ((Float) obj).floatValue())) : obj instanceof Long ? Long.valueOf(sharedPreferences.getLong(str, ((Long) obj).longValue())) : obj;
    }
}
