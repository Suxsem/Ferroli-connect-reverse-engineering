package com.igexin.push.util;

import android.content.Context;

/* renamed from: com.igexin.push.util.h */
final class C1583h implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Context f3016a;

    /* renamed from: b */
    final /* synthetic */ long f3017b;

    C1583h(Context context, long j) {
        this.f3016a = context;
        this.f3017b = j;
    }

    public void run() {
        C1581f.m3238b(this.f3016a, String.valueOf(this.f3017b));
    }
}
