package com.alibaba.sdk.android.ams.common.p009a;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;

/* renamed from: com.alibaba.sdk.android.ams.common.a.a */
public class C0669a {

    /* renamed from: a */
    static volatile Context f805a = null;

    /* renamed from: b */
    static volatile Application f806b = null;

    /* renamed from: c */
    static volatile boolean f807c = false;

    /* renamed from: a */
    public static Application m465a() {
        return f806b;
    }

    /* renamed from: a */
    public static String m466a(String str) {
        try {
            ApplicationInfo applicationInfo = f805a.getPackageManager().getApplicationInfo(f805a.getPackageName(), 128);
            if (applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey(str)) {
                return null;
            }
            return String.valueOf(applicationInfo.metaData.get(str));
        } catch (PackageManager.NameNotFoundException unused) {
            AmsLogger importantLogger = AmsLogger.getImportantLogger();
            importantLogger.mo9541e("Meta data name " + str + " not found!");
            return null;
        }
    }

    /* renamed from: b */
    public static Context m467b() {
        return f805a;
    }

    /* renamed from: c */
    public static boolean m468c() {
        return f807c;
    }

    /* renamed from: d */
    public static String m469d() {
        return "mpush-api.aliyun.com";
    }

    /* renamed from: e */
    public static String m470e() {
        return "https://" + m469d() + "/config";
    }

    /* renamed from: f */
    public static SharedPreferences m471f() {
        return PreferenceManager.getDefaultSharedPreferences(f805a);
    }

    /* renamed from: g */
    public static String m472g() {
        return f805a.getPackageName();
    }
}
