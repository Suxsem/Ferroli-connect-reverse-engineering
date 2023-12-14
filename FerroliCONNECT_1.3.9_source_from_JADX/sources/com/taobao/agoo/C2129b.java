package com.taobao.agoo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import java.util.ArrayList;

/* renamed from: com.taobao.agoo.b */
/* compiled from: Taobao */
public class C2129b {
    public static final String TAG = "LocalStorage";

    /* renamed from: a */
    public static void m3841a(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str) || context == null) {
            ALog.m3725d(TAG, "saveAliasToken input invalid", new Object[0]);
            return;
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        if (TextUtils.isEmpty(str2)) {
            edit.remove(m3842b(str));
            String string = defaultSharedPreferences.getString("alicloud-third-push-alias-list", "");
            String a = m3839a(str);
            if (string.contains(a)) {
                edit.putString("alicloud-third-push-alias-list", string.replace(a, ""));
            }
        } else {
            edit.putString(m3842b(str), str2);
            String string2 = defaultSharedPreferences.getString("alicloud-third-push-alias-list", "");
            String a2 = m3839a(str);
            if (!string2.contains(a2)) {
                edit.putString("alicloud-third-push-alias-list", string2 + a2);
            }
        }
        edit.apply();
    }

    /* renamed from: a */
    private static String m3839a(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        return "#&#" + str + "#&#";
    }

    /* renamed from: a */
    public static String m3838a(Context context, String str) {
        if (!TextUtils.isEmpty(str) && context != null) {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(m3842b(str), (String) null);
        }
        ALog.m3725d(TAG, "getAliasToken input invalid", new Object[0]);
        return null;
    }

    /* renamed from: a */
    public static ArrayList<String> m3840a(Context context) {
        String[] split = PreferenceManager.getDefaultSharedPreferences(context).getString("alicloud-third-push-alias-list", "").split("#&#");
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (split[i] != null && !split[i].isEmpty()) {
                arrayList.add(split[i]);
            }
        }
        return arrayList;
    }

    /* renamed from: b */
    private static String m3842b(String str) {
        return "alicloud-third-push-pat-" + str;
    }
}
