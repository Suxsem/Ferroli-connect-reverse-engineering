package com.igexin.push.extension.distribution.basic.p068j;

import android.content.Context;
import com.igexin.p032b.p033a.p039c.C1179b;

/* renamed from: com.igexin.push.extension.distribution.basic.j.a */
public class C1433a {

    /* renamed from: a */
    private static int f2486a;

    /* renamed from: b */
    private static int f2487b;

    /* renamed from: a */
    public static boolean m2498a(Context context) {
        int i = f2487b;
        boolean z = false;
        if ((i & 1) != 0) {
            return (i & 2) != 0;
        }
        try {
            z = ((Boolean) Class.forName("com.igexin.assist.control.xiaomi.MiuiPushManager").getMethod("checkXMDevice", new Class[]{Context.class}).invoke((Object) null, new Object[]{context})).booleanValue();
            if (z) {
                f2487b |= 2;
            }
        } catch (Throwable unused) {
        }
        f2487b |= 1;
        return z;
    }

    /* renamed from: b */
    public static boolean m2499b(Context context) {
        int i = f2486a;
        boolean z = false;
        if ((i & 1) != 0) {
            return (i & 2) != 0;
        }
        try {
            z = ((Boolean) Class.forName("com.igexin.assist.control.meizu.FlymePushManager").getMethod("checkMZDevice", new Class[]{Context.class}).invoke((Object) null, new Object[]{context})).booleanValue();
            if (z) {
                f2486a |= 2;
            }
        } catch (Throwable unused) {
        }
        f2486a |= 1;
        return z;
    }

    /* renamed from: c */
    public static void m2500c(Context context) {
        String str;
        try {
            if (m2498a(context)) {
                Class.forName("com.xiaomi.mipush.sdk.MiPushClient").getDeclaredMethod("clearNotification", new Class[]{Context.class}).invoke((Object) null, new Object[]{context});
                str = "AssistUtil | cancelAllAssistNotification() XM ";
            } else if (m2499b(context)) {
                Class.forName("com.meizu.cloud.pushsdk.PushManager").getDeclaredMethod("clearNotification", new Class[]{Context.class}).invoke((Object) null, new Object[]{context});
                str = "AssistUtil | cancelAllAssistNotification() MZ ";
            } else {
                return;
            }
            C1179b.m1354a(str);
        } catch (Throwable th) {
            C1179b.m1354a("AssistUtil | cancelAllAssistNotification() err " + th.toString());
        }
    }
}
