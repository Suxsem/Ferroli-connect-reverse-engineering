package com.alibaba.sdk.android.push.common.util;

import android.content.Context;
import android.preference.PreferenceManager;

/* renamed from: com.alibaba.sdk.android.push.common.util.b */
public class C0813b {
    /* renamed from: a */
    public static long m788a(Context context, String str) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(str, 0);
    }

    /* renamed from: a */
    public static void m789a(Context context, String str, long j) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(str, j).commit();
    }
}
