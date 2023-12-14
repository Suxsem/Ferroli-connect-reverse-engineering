package org.android.agoo.control;

import android.content.Intent;

/* renamed from: org.android.agoo.control.i */
/* compiled from: Taobao */
class C2362i implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Intent f4087a;

    /* renamed from: b */
    final /* synthetic */ C2361h f4088b;

    C2362i(C2361h hVar, Intent intent) {
        this.f4088b = hVar;
        this.f4087a = intent;
    }

    public void run() {
        this.f4088b.f4086a.onHandleIntent(this.f4087a);
    }
}
