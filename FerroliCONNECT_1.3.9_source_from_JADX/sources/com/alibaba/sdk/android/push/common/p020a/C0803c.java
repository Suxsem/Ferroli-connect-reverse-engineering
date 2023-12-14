package com.alibaba.sdk.android.push.common.p020a;

import android.graphics.Bitmap;

/* renamed from: com.alibaba.sdk.android.push.common.a.c */
public class C0803c {
    /* renamed from: a */
    public static synchronized void m770a(int i) {
        synchronized (C0803c.class) {
            C0802b.f1085e = i;
        }
    }

    /* renamed from: a */
    public static synchronized void m771a(Bitmap bitmap) {
        synchronized (C0803c.class) {
            C0802b.f1082b = bitmap;
        }
    }

    /* renamed from: a */
    public static synchronized void m772a(String str) {
        synchronized (C0803c.class) {
            if (str != null) {
                if (str.length() > 0) {
                    C0802b.f1081a = str;
                }
            }
        }
    }

    /* renamed from: a */
    public static synchronized void m773a(boolean z) {
        synchronized (C0803c.class) {
            C0802b.f1088h = z;
        }
    }
}
