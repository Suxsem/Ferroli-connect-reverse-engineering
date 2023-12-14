package com.igexin.push.extension.distribution.gbd.p086i;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.p107tb.appyunsdk.Constant;
import java.lang.reflect.Method;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.y */
public class C1557y {
    /* renamed from: a */
    private static Object m3076a(int i, String str, Context context) {
        if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
            return null;
        }
        try {
            if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
                C1540h.m2997b("scu", "no rps.");
                return null;
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Constant.PHONE);
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            Method method = telephonyManager.getClass().getMethod(str, m3079a(str));
            if (i < 0) {
                return null;
            }
            return method.invoke(telephonyManager, new Object[]{Integer.valueOf(i)});
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: a */
    public static String m3077a(int i, Context context) {
        if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        try {
            if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
                C1540h.m2997b("scu", "no rps.");
                return "";
            }
            Object a = m3076a(i, "getDeviceId", context);
            return a != null ? (String) a : "";
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "";
        }
    }

    /* renamed from: a */
    public static String m3078a(Context context) {
        if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        try {
            if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
                C1540h.m2997b("scu", "no rps.");
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Constant.PHONE);
            return !TextUtils.isEmpty(telephonyManager.getDeviceId()) ? telephonyManager.getDeviceId() : "";
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "";
        }
    }

    /* renamed from: a */
    private static Class[] m3079a(String str) {
        Class[] clsArr = null;
        try {
            Method[] declaredMethods = TelephonyManager.class.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                if (str.equals(declaredMethods[i].getName())) {
                    clsArr = declaredMethods[i].getParameterTypes();
                    if (clsArr.length >= 1) {
                        break;
                    }
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        return clsArr;
    }

    /* renamed from: b */
    public static String m3080b(int i, Context context) {
        if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        try {
            if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
                C1540h.m2997b("scu", "no rps.");
                return "";
            }
            Object a = m3076a(i, "getSubscriberId", context);
            return a != null ? (String) a : "";
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "";
        }
    }

    /* renamed from: b */
    public static String m3081b(Context context) {
        if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        try {
            if (!C1541i.m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
                C1540h.m2997b("scu", "no rps.");
                return "";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Constant.PHONE);
            return !TextUtils.isEmpty(telephonyManager.getSubscriberId()) ? telephonyManager.getSubscriberId() : "";
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "";
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0056, code lost:
        if (r1 != null) goto L_0x005f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005d, code lost:
        if (r1 == null) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005f, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0062, code lost:
        return -1;
     */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int m3082c(int r10, android.content.Context r11) {
        /*
            java.lang.String r0 = "_id"
            android.content.Context r1 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2747a
            java.lang.String r2 = "android.permission.READ_PHONE_STATE"
            boolean r1 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3006a((android.content.Context) r1, (java.lang.String) r2)
            r3 = -1
            if (r1 != 0) goto L_0x0015
            java.lang.String r10 = "scu"
            java.lang.String r11 = "no rps."
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r10, r11)
            return r3
        L_0x0015:
            java.lang.String r1 = "content://telephony/siminfo"
            android.net.Uri r5 = android.net.Uri.parse(r1)
            r1 = 0
            android.content.ContentResolver r4 = r11.getContentResolver()
            android.content.Context r11 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2747a
            boolean r11 = com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3006a((android.content.Context) r11, (java.lang.String) r2)
            if (r11 != 0) goto L_0x0029
            return r3
        L_0x0029:
            java.lang.String r11 = "sim_id"
            java.lang.String[] r6 = new java.lang.String[]{r0, r11}     // Catch:{ Throwable -> 0x0059 }
            java.lang.String r7 = "sim_id = ?"
            r11 = 1
            java.lang.String[] r8 = new java.lang.String[r11]     // Catch:{ Throwable -> 0x0059 }
            r11 = 0
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ Throwable -> 0x0059 }
            r8[r11] = r10     // Catch:{ Throwable -> 0x0059 }
            r9 = 0
            android.database.Cursor r1 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x0059 }
            if (r1 == 0) goto L_0x0056
            boolean r10 = r1.moveToFirst()     // Catch:{ Throwable -> 0x0059 }
            if (r10 == 0) goto L_0x0056
            int r10 = r1.getColumnIndex(r0)     // Catch:{ Throwable -> 0x0059 }
            int r10 = r1.getInt(r10)     // Catch:{ Throwable -> 0x0059 }
            if (r1 == 0) goto L_0x0055
            r1.close()
        L_0x0055:
            return r10
        L_0x0056:
            if (r1 == 0) goto L_0x0062
            goto L_0x005f
        L_0x0059:
            r10 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r10)     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x0062
        L_0x005f:
            r1.close()
        L_0x0062:
            return r3
        L_0x0063:
            r10 = move-exception
            if (r1 == 0) goto L_0x0069
            r1.close()
        L_0x0069:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1557y.m3082c(int, android.content.Context):int");
    }
}
