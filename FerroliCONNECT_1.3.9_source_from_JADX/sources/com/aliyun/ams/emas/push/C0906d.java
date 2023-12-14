package com.aliyun.ams.emas.push;

import android.app.Notification;

/* renamed from: com.aliyun.ams.emas.push.d */
/* compiled from: Taobao */
class C0906d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Notification f1421a;

    /* renamed from: b */
    final /* synthetic */ C0905c f1422b;

    C0906d(C0905c cVar, Notification notification) {
        this.f1422b = cVar;
        this.f1421a = notification;
    }

    public void run() {
        this.f1422b.f1420e.mo10175a(this.f1421a);
    }
}
