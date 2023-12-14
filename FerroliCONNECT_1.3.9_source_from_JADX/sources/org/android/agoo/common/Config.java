package org.android.agoo.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
public class Config {
    public static final String AGOO_CLEAR_TIME = "agoo_clear_time";
    public static final String AGOO_ENABLE_DAEMONSERVER = "agoo_enable_daemonserver";
    public static final String AGOO_UNREPORT_TIMES = "agoo_UnReport_times";
    public static final String KEY_DEVICE_TOKEN = "deviceId";
    public static final String PREFERENCES = "EMAS_Agoo_AppStore";
    public static final String PROPERTY_AGOO_SERVICE_MODE = "agoo_service_mode";
    public static final String PROPERTY_APP_KEY = "agoo_app_key";
    public static final String PROPERTY_APP_VERSION = "app_version";
    public static final String PROPERTY_DEVICE_TOKEN = "app_device_token";
    public static final String PROPERTY_PUSH_USER_TOKEN = "app_push_user_token";
    public static final String PROPERTY_TT_ID = "app_tt_id";
    public static final String TAG = "Config";

    /* renamed from: a */
    public static String f4060a;

    /* renamed from: b */
    private static String f4061b;

    /* renamed from: c */
    private static String f4062c;

    public static void setAgooAppKey(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                ALog.m3727e(TAG, "setAgooAppKey appkey null", new Object[0]);
                return;
            }
            f4061b = str;
            SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES, 4).edit();
            edit.putString(PROPERTY_APP_KEY, str);
            edit.apply();
            ALog.m3725d(TAG, "setAgooAppKey", "appkey", str);
        } catch (Throwable th) {
            ALog.m3726e(TAG, "setAgooAppKey", th, new Object[0]);
        }
    }

    /* renamed from: b */
    public static String m3894b(Context context) {
        String str = f4061b;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            str = context.getSharedPreferences(PREFERENCES, 4).getString(PROPERTY_APP_KEY, f4061b);
        } catch (Throwable th) {
            ALog.m3726e(TAG, "getAgooAppKey", th, new Object[0]);
        }
        if (TextUtils.isEmpty(str)) {
            ALog.m3727e(TAG, "getAgooAppKey null!!", new Object[0]);
        }
        ALog.m3725d(TAG, "getAgooAppKey", "appkey", str);
        return str;
    }

    /* renamed from: c */
    public static String m3896c(Context context) {
        if (TextUtils.isEmpty(f4060a)) {
            return ACCSManager.getDefaultConfig(context);
        }
        return f4060a;
    }

    /* renamed from: a */
    public static void m3893a(Context context, String str) {
        ALog.m3728i(TAG, "setDeviceToken", "token", str);
        if (!TextUtils.isEmpty(str)) {
            f4062c = str;
            try {
                SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES, 4).edit();
                edit.putString("deviceId", str);
                edit.apply();
            } catch (Throwable th) {
                ALog.m3726e(TAG, "setDeviceToken", th, new Object[0]);
            }
        }
    }

    public static String getDeviceToken(Context context) {
        String str = f4062c;
        try {
            str = context.getSharedPreferences(PREFERENCES, 4).getString("deviceId", f4062c);
        } catch (Throwable th) {
            ALog.m3726e(TAG, "getDeviceToken", th, new Object[0]);
        }
        ALog.m3728i(TAG, "getDeviceToken", "token", str);
        return str;
    }

    /* renamed from: a */
    public static void m3890a(Context context) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES, 4).edit();
            edit.putInt("app_version", Integer.MIN_VALUE);
            edit.remove(PROPERTY_DEVICE_TOKEN);
            edit.remove(PROPERTY_APP_KEY);
            edit.remove(PROPERTY_TT_ID);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public static void m3891a(Context context, int i) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, 4);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt(AGOO_UNREPORT_TIMES, sharedPreferences.getInt(AGOO_UNREPORT_TIMES, 0) + i);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    /* renamed from: d */
    public static boolean m3897d(Context context) {
        try {
            if (context.getSharedPreferences(PREFERENCES, 4).getInt(AGOO_UNREPORT_TIMES, 0) > 0) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: e */
    public static void m3898e(Context context) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES, 4).edit();
            edit.putInt(AGOO_UNREPORT_TIMES, 0);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    /* renamed from: f */
    public static int m3899f(Context context) {
        try {
            return context.getSharedPreferences(PREFERENCES, 4).getInt(AGOO_UNREPORT_TIMES, 0);
        } catch (Throwable unused) {
            return 0;
        }
    }

    /* renamed from: a */
    public static void m3892a(Context context, long j) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES, 4).edit();
            edit.putLong(AGOO_CLEAR_TIME, j);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    /* renamed from: b */
    public static boolean m3895b(Context context, long j) {
        try {
            long j2 = context.getSharedPreferences(PREFERENCES, 4).getLong(AGOO_CLEAR_TIME, 0);
            StringBuilder sb = new StringBuilder();
            sb.append("now=");
            sb.append(j);
            sb.append(",now - lastTime=");
            long j3 = j - j2;
            sb.append(j3);
            sb.append(",istrue=");
            sb.append(j3 > Constants.CLIENT_FLUSH_INTERVAL);
            ALog.m3725d("isClearTime", sb.toString(), new Object[0]);
            if (j == 0 || j3 <= Constants.CLIENT_FLUSH_INTERVAL) {
                return false;
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
