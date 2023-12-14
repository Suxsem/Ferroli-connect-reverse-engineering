package anet.channel.session;

import anet.channel.RequestCb;
import anet.channel.entity.C0518b;
import anet.channel.request.Request;
import anet.channel.session.C0549b;

/* renamed from: anet.channel.session.e */
/* compiled from: Taobao */
class C0553e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Request f394a;

    /* renamed from: b */
    final /* synthetic */ C0552d f395b;

    C0553e(C0552d dVar, Request request) {
        this.f395b = dVar;
        this.f394a = request;
    }

    public void run() {
        C0549b.C0550a a = C0549b.m214a(this.f394a, (RequestCb) null);
        if (a.f387a > 0) {
            this.f395b.notifyStatus(4, new C0518b(1));
        } else {
            this.f395b.handleCallbacks(256, new C0518b(256, a.f387a, "Http connect fail"));
        }
    }
}
