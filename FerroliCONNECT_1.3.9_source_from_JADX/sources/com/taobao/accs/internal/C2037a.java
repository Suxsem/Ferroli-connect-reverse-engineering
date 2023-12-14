package com.taobao.accs.internal;

import android.content.Context;
import com.taobao.accs.ACCSClient;

/* renamed from: com.taobao.accs.internal.a */
/* compiled from: Taobao */
class C2037a implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f3341a;

    /* renamed from: b */
    final /* synthetic */ Context f3342b;

    /* renamed from: c */
    final /* synthetic */ ACCSManagerImpl f3343c;

    C2037a(ACCSManagerImpl aCCSManagerImpl, String str, Context context) {
        this.f3343c = aCCSManagerImpl;
        this.f3341a = str;
        this.f3342b = context;
    }

    public void run() {
        try {
            ACCSClient.getAccsClient(this.f3341a).addConnectionListener(new C2038b(this));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
