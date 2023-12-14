package org.android.agoo.control;

import android.content.Intent;

/* renamed from: org.android.agoo.control.l */
/* compiled from: Taobao */
class C2365l implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Intent f4091a;

    /* renamed from: b */
    final /* synthetic */ BaseIntentService f4092b;

    C2365l(BaseIntentService baseIntentService, Intent intent) {
        this.f4092b = baseIntentService;
        this.f4091a = intent;
    }

    public void run() {
        this.f4092b.onHandleIntent(this.f4091a);
    }
}
