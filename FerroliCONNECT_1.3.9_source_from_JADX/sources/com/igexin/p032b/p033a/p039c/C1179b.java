package com.igexin.p032b.p033a.p039c;

import android.util.Log;
import com.igexin.push.config.C1237n;
import com.igexin.push.core.C1343f;
import com.igexin.push.p088g.C1561a;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.igexin.b.a.c.b */
public class C1179b {

    /* renamed from: a */
    public static boolean f1610a = C1237n.f1867a.equals(MqttServiceConstants.TRACE_DEBUG);

    /* renamed from: a */
    public static void m1354a(String str) {
        if (f1610a || (C1343f.f2121M && C1343f.f2122N >= System.currentTimeMillis())) {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault()).format(new Date());
            C1561a.m3115i().mo15200a(format + "|" + str);
        }
    }

    /* renamed from: a */
    public static void m1355a(String str, String str2) {
        if (f1610a) {
            Log.d(str, str2);
        }
    }

    /* renamed from: b */
    public static void m1356b(String str, String str2) {
        if (f1610a) {
            Log.i(str, str2);
        }
    }
}
