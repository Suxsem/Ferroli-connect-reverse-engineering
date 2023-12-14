package com.alibaba.sdk.android.ams.common.p009a;

import android.app.Application;
import android.content.Context;

/* renamed from: com.alibaba.sdk.android.ams.common.a.b */
public class C0670b {
    /* renamed from: a */
    public static void m473a(Application application) {
        if (application != null) {
            C0669a.f806b = application;
        }
    }

    /* renamed from: a */
    public static void m474a(Context context) {
        if (context != null) {
            C0669a.f805a = context;
            return;
        }
        throw new IllegalArgumentException("null applicationContext!");
    }

    /* renamed from: a */
    public static void m475a(boolean z) {
        C0669a.f807c = z;
    }
}
