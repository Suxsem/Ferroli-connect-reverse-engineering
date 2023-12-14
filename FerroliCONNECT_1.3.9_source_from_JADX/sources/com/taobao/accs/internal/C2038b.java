package com.taobao.accs.internal;

import com.taobao.accs.ConnectionListener;

/* renamed from: com.taobao.accs.internal.b */
/* compiled from: Taobao */
class C2038b implements ConnectionListener {

    /* renamed from: a */
    final /* synthetic */ C2037a f3344a;

    public void onDisconnect(int i, String str) {
    }

    C2038b(C2037a aVar) {
        this.f3344a = aVar;
    }

    public void onConnect() {
        if (this.f3344a.f3343c.f3332a.mo18491j().mo18382e(this.f3344a.f3342b.getPackageName()) && this.f3344a.f3343c.f3334c) {
            this.f3344a.f3343c.m3553a(this.f3344a.f3342b, this.f3344a.f3343c.f3332a.f3374b, this.f3344a.f3343c.f3332a.f3373a);
        }
    }
}
