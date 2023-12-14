package com.taobao.accs.data;

import com.taobao.accs.p103ut.monitor.TrafficsMonitor;

/* renamed from: com.taobao.accs.data.e */
/* compiled from: Taobao */
class C2031e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ TrafficsMonitor.C2082a f3320a;

    /* renamed from: b */
    final /* synthetic */ C2030d f3321b;

    C2031e(C2030d dVar, TrafficsMonitor.C2082a aVar) {
        this.f3321b = dVar;
        this.f3320a = aVar;
    }

    public void run() {
        if (this.f3321b.f3307c != null) {
            this.f3321b.f3307c.mo18576a(this.f3320a);
        }
    }
}
