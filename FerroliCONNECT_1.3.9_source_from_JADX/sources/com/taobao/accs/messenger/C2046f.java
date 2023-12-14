package com.taobao.accs.messenger;

import android.content.Intent;

/* renamed from: com.taobao.accs.messenger.f */
/* compiled from: Taobao */
class C2046f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f3366a;

    /* renamed from: b */
    final /* synthetic */ Intent f3367b;

    /* renamed from: c */
    final /* synthetic */ C2045e f3368c;

    C2046f(C2045e eVar, String str, Intent intent) {
        this.f3368c = eVar;
        this.f3366a = str;
        this.f3367b = intent;
    }

    public void run() {
        this.f3368c.m3585b(this.f3366a, this.f3367b);
    }
}
