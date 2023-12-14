package com.taobao.accs.utl;

import android.content.Context;
import android.content.SharedPreferences;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;

/* compiled from: Taobao */
public class OrangeAdapter {
    public static final String NAMESPACE = "accs";
    private static final String TAG = "OrangeAdapter";
    private static final String TNET_LOG_KEY = "tnet_log_off";
    public static final boolean mOrangeValid = false;

    public static String getConfig(String str, String str2, String str3) {
        return str3;
    }

    public static boolean isSmartHb() {
        boolean z;
        try {
            z = getConfigFromSP(GlobalClientInfo.getContext(), Constants.SP_KEY_HB_SMART_ENABLE, true);
        } catch (Throwable th) {
            ALog.m3726e(TAG, "isSmartHb", th, new Object[0]);
            z = true;
        }
        ALog.m3725d(TAG, "isSmartHb", "result", Boolean.valueOf(z));
        return z;
    }

    public static boolean isBindService(Context context) {
        boolean z;
        try {
            z = getConfigFromSP(context, Constants.SP_KEY_BIND_SERVICE_ENABLE, true);
        } catch (Throwable th) {
            ALog.m3726e(TAG, "isBindService", th, new Object[0]);
            z = true;
        }
        ALog.m3725d(TAG, "isBindService", "result", Boolean.valueOf(z));
        return z;
    }

    public static boolean isTnetLogOff(boolean z) {
        boolean z2;
        Throwable th;
        boolean z3;
        String str;
        if (z) {
            try {
                str = getConfig("accs", "tnet_log_off", "default");
            } catch (Throwable th2) {
                th = th2;
                z3 = true;
                ALog.m3726e(TAG, "isTnetLogOff", th, new Object[0]);
                z2 = z3;
                ALog.m3728i(TAG, "isTnetLogOff", "result", Boolean.valueOf(z2));
                return z2;
            }
        } else {
            str = "default";
        }
        if (str.equals("default")) {
            z2 = getConfigFromSP(GlobalClientInfo.getContext(), "tnet_log_off", true);
        } else {
            z2 = Boolean.valueOf(str).booleanValue();
            try {
                saveConfigToSP(GlobalClientInfo.getContext(), "tnet_log_off", z2);
            } catch (Throwable th3) {
                Throwable th4 = th3;
                z3 = z2;
                th = th4;
            }
        }
        ALog.m3728i(TAG, "isTnetLogOff", "result", Boolean.valueOf(z2));
        return z2;
    }

    private static boolean getConfigFromSP(Context context, String str, boolean z) {
        try {
            return context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getBoolean(str, z);
        } catch (Exception e) {
            ALog.m3726e(TAG, "getConfigFromSP fail:", e, "key", str);
            return z;
        }
    }

    private static void saveConfigToSP(Context context, String str, boolean z) {
        if (context == null) {
            try {
                ALog.m3727e(TAG, "saveTLogOffToSP context null", new Object[0]);
            } catch (Exception e) {
                ALog.m3726e(TAG, "saveConfigToSP fail:", e, "key", str, "value", Boolean.valueOf(z));
            }
        } else {
            SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.putBoolean(str, z);
            edit.apply();
            ALog.m3728i(TAG, "saveConfigToSP", "key", str, "value", Boolean.valueOf(z));
        }
    }

    public static void saveConfigToSP(Context context, String str, int i) {
        if (context == null) {
            try {
                ALog.m3727e(TAG, "saveTLogOffToSP context null", new Object[0]);
            } catch (Exception e) {
                ALog.m3726e(TAG, "saveConfigToSP fail:", e, "key", str, "value", Integer.valueOf(i));
            }
        } else {
            SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.putInt(str, i);
            edit.apply();
            ALog.m3728i(TAG, "saveConfigToSP", "key", str, "value", Integer.valueOf(i));
        }
    }
}
