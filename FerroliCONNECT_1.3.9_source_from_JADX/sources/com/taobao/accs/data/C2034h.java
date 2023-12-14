package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import com.taobao.accs.net.C2049b;

/* renamed from: com.taobao.accs.data.h */
/* compiled from: Taobao */
final class C2034h implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Context f3324a;

    /* renamed from: b */
    final /* synthetic */ C2049b f3325b;

    /* renamed from: c */
    final /* synthetic */ Intent f3326c;

    C2034h(Context context, C2049b bVar, Intent intent) {
        this.f3324a = context;
        this.f3325b = bVar;
        this.f3326c = intent;
    }

    public void run() {
        C2033g.m3533a().m3539b(this.f3324a, this.f3325b, this.f3326c);
    }
}
