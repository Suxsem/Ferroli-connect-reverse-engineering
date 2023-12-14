package com.taobao.accs.messenger;

import android.content.Intent;

/* renamed from: com.taobao.accs.messenger.c */
/* compiled from: Taobao */
class C2043c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Intent f3355a;

    /* renamed from: b */
    final /* synthetic */ C2042b f3356b;

    C2043c(C2042b bVar, Intent intent) {
        this.f3356b = bVar;
        this.f3355a = intent;
    }

    public void run() {
        MessengerService.m3574a();
    }
}
