package com.igexin.push.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1343f;
import com.p107tb.appyunsdk.Constant;
import java.util.Arrays;

/* renamed from: com.igexin.push.util.n */
public class C1589n {

    /* renamed from: a */
    private static final String f3036a = (TextUtils.isEmpty(Build.BRAND) ? Build.MANUFACTURER : Build.BRAND);

    /* renamed from: a */
    public static String m3257a() {
        try {
            return Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0];
        } catch (Throwable unused) {
            return "";
        }
    }

    /* renamed from: a */
    public static String m3258a(Context context) {
        try {
            return C1343f.f2169f.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", C1343f.f2169f.getPackageName()) == 0 ? ((TelephonyManager) context.getSystemService(Constant.PHONE)).getDeviceId() : "";
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: b */
    public static int m3259b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.targetSdkVersion;
        } catch (Exception unused) {
            return 0;
        }
    }

    /* renamed from: b */
    public static String m3260b() {
        if (!C1593r.m3267a(C1343f.f2169f)) {
            return "";
        }
        try {
            return Settings.Secure.getString(C1343f.f2169f.getContentResolver(), "android_id");
        } catch (Throwable unused) {
            return "";
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x004a */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3261c() {
        /*
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f
            boolean r0 = com.igexin.push.util.C1593r.m3267a(r0)
            java.lang.String r1 = ""
            if (r0 != 0) goto L_0x000b
            return r1
        L_0x000b:
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch:{  }
            android.os.Looper r2 = android.os.Looper.getMainLooper()     // Catch:{  }
            if (r0 != r2) goto L_0x0016
            return r1
        L_0x0016:
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x0057 }
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch:{ Throwable -> 0x0057 }
            java.lang.String r2 = "com.android.vending"
            r3 = 0
            r0.getPackageInfo(r2, r3)     // Catch:{ Throwable -> 0x0057 }
            android.content.Intent r0 = new android.content.Intent     // Catch:{  }
            java.lang.String r2 = "com.google.android.gms.ads.identifier.service.START"
            r0.<init>(r2)     // Catch:{  }
            java.lang.String r2 = "com.google.android.gms"
            r0.setPackage(r2)     // Catch:{  }
            com.igexin.push.util.p r2 = new com.igexin.push.util.p     // Catch:{  }
            r3 = 0
            r2.<init>()     // Catch:{  }
            android.content.Context r3 = com.igexin.push.core.C1343f.f2169f     // Catch:{  }
            r4 = 1
            boolean r0 = r3.bindService(r0, r2, r4)     // Catch:{  }
            if (r0 == 0) goto L_0x0057
            com.igexin.push.util.q r0 = new com.igexin.push.util.q     // Catch:{ Exception -> 0x004a, all -> 0x0050 }
            android.os.IBinder r3 = r2.mo15219a()     // Catch:{ Exception -> 0x004a, all -> 0x0050 }
            r0.<init>(r3)     // Catch:{ Exception -> 0x004a, all -> 0x0050 }
            java.lang.String r1 = r0.mo15222a()     // Catch:{ Exception -> 0x004a, all -> 0x0050 }
        L_0x004a:
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f     // Catch:{  }
            r0.unbindService(r2)     // Catch:{  }
            goto L_0x0057
        L_0x0050:
            r0 = move-exception
            android.content.Context r3 = com.igexin.push.core.C1343f.f2169f     // Catch:{  }
            r3.unbindService(r2)     // Catch:{  }
            throw r0     // Catch:{  }
        L_0x0057:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1589n.m3261c():java.lang.String");
    }

    /* renamed from: d */
    public static boolean m3262d() {
        try {
            boolean contains = Arrays.asList(C1234k.f1832N.toUpperCase().split(",")).contains(f3036a.toUpperCase());
            C1179b.m1354a("PhoneInfoUtils|shouldBrandDelAlarm = " + contains);
            return contains;
        } catch (Exception e) {
            C1179b.m1354a("PhoneInfoUtils|delAlarm " + C1234k.f1832N + " err " + e.toString());
            return false;
        }
    }
}
