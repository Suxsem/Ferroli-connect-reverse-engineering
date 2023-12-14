package com.aliyun.ams.emas.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.alibaba.sdk.android.logger.ILog;
import com.aliyun.ams.emas.push.notification.CPushMessage;
import com.taobao.accs.utl.AccsLogger;
import java.util.Random;

/* renamed from: com.aliyun.ams.emas.push.h */
/* compiled from: Taobao */
public class C0910h {

    /* renamed from: a */
    public static String f1433a = "com.alibaba.sdk.android.push.NOTIFY_ACTION";

    /* renamed from: b */
    private static Class f1434b = null;

    /* renamed from: c */
    private static C0908f f1435c = null;

    /* renamed from: d */
    private static IReportPushArrive f1436d = null;

    /* renamed from: e */
    private static int f1437e = 0;

    /* renamed from: f */
    private static int f1438f = 0;

    /* renamed from: g */
    private static SharedPreferences f1439g = null;

    /* renamed from: h */
    private static Random f1440h = null;
    public static final ILog imortantLogger = AccsLogger.getLogger("[MPS]");

    /* renamed from: a */
    public static void m1074a(Context context) {
        f1435c = new C0908f(context.getApplicationContext());
        f1439g = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    /* renamed from: a */
    public static void m1076a(IReportPushArrive iReportPushArrive) {
        f1436d = iReportPushArrive;
    }

    /* renamed from: a */
    public static void m1078a(Class cls) {
        f1434b = cls;
    }

    /* renamed from: a */
    public static Class m1072a() {
        return f1434b;
    }

    /* renamed from: a */
    public static void m1079a(boolean z) {
        f1435c.mo10178a(z);
    }

    /* renamed from: a */
    public static void m1073a(int i, int i2, int i3, int i4, CommonCallback commonCallback) {
        f1435c.mo10176a(i, i2, i3, i4, commonCallback);
    }

    /* renamed from: b */
    public static boolean m1081b() {
        return f1435c.mo10179a();
    }

    /* renamed from: a */
    public static void m1075a(Context context, String str, int i) {
        IReportPushArrive iReportPushArrive = f1436d;
        if (iReportPushArrive != null) {
            iReportPushArrive.reportPushArrive(context, str, i);
        }
    }

    /* renamed from: c */
    public static int m1082c() {
        if (f1438f == 0) {
            if (f1440h == null) {
                f1440h = new Random(System.currentTimeMillis());
            }
            f1438f = f1440h.nextInt(1000000);
            int i = f1438f;
            if (i < 0) {
                f1438f = i * -1;
            }
        }
        int i2 = f1438f;
        f1438f = i2 + 1;
        return i2;
    }

    /* renamed from: d */
    public static int m1083d() {
        if (f1437e == 0) {
            if (f1440h == null) {
                f1440h = new Random(System.currentTimeMillis());
            }
            f1437e = f1440h.nextInt(1000000);
            int i = f1437e;
            if (i < 0) {
                f1437e = i * -1;
            }
        }
        int i2 = f1437e;
        f1437e = i2 + 1;
        return i2;
    }

    /* renamed from: a */
    public static void m1077a(CPushMessage cPushMessage) {
        f1435c.mo10177a(cPushMessage);
    }

    /* renamed from: b */
    public static void m1080b(CPushMessage cPushMessage) {
        f1435c.mo10180b(cPushMessage);
    }
}
