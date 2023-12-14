package com.igexin.assist.sdk;

import android.content.Context;

/* renamed from: com.igexin.assist.sdk.a */
public class C1146a {

    /* renamed from: a */
    private static int f1536a;

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.igexin.assist.control.AbstractPushManager m1223a(android.content.Context r5) {
        /*
            boolean r0 = m1224b(r5)     // Catch:{  }
            if (r0 == 0) goto L_0x002e
            boolean r0 = com.igexin.push.config.C1234k.f1837S     // Catch:{  }
            if (r0 == 0) goto L_0x002e
            java.lang.String r0 = "Assist_PushMangerFactory|FcmPushManager checkDevice flag = true"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{  }
            java.lang.String r0 = "com.igexin.assist.control.fcm.FcmPushManager"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ Throwable -> 0x003c }
            r1 = 1
            java.lang.Class[] r2 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x003c }
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r4 = 0
            r2[r4] = r3     // Catch:{ Throwable -> 0x003c }
            java.lang.reflect.Constructor r0 = r0.getConstructor(r2)     // Catch:{ Throwable -> 0x003c }
            if (r0 == 0) goto L_0x003c
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x003c }
            r1[r4] = r5     // Catch:{ Throwable -> 0x003c }
            java.lang.Object r5 = r0.newInstance(r1)     // Catch:{ Throwable -> 0x003c }
            com.igexin.assist.control.AbstractPushManager r5 = (com.igexin.assist.control.AbstractPushManager) r5     // Catch:{ Throwable -> 0x003c }
            return r5
        L_0x002e:
            java.lang.String r5 = "Assist_PushMangerFactory|getPushManager = null, setToken = false"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{  }
            com.igexin.push.core.c.h r5 = com.igexin.push.core.p050c.C1312h.m1937a()     // Catch:{  }
            java.lang.String r0 = "false"
            r5.mo14684d((java.lang.String) r0)     // Catch:{  }
        L_0x003c:
            java.lang.String r5 = "Assist_PushMangerFactory|OtherPushManager = null"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.assist.sdk.C1146a.m1223a(android.content.Context):com.igexin.assist.control.AbstractPushManager");
    }

    /* renamed from: b */
    public static boolean m1224b(Context context) {
        int i = f1536a;
        boolean z = false;
        if ((i & 1) != 0) {
            return (i & 2) != 0;
        }
        try {
            z = ((Boolean) Class.forName("com.igexin.assist.control.fcm.FcmPushManager").getMethod("checkFcmDevice", new Class[]{Context.class}).invoke((Object) null, new Object[]{context})).booleanValue();
            f1536a |= 1;
            if (z) {
                f1536a |= 2;
            }
        } catch (Throwable unused) {
        }
        f1536a |= 1;
        return z;
    }

    /* renamed from: c */
    public static boolean m1225c(Context context) {
        return false;
    }
}
