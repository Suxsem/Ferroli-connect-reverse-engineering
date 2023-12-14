package anet.channel;

import anet.channel.SessionRequest;
import anet.channel.util.C0610i;

/* renamed from: anet.channel.i */
/* compiled from: Taobao */
class C0529i implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Session f279a;

    /* renamed from: b */
    final /* synthetic */ SessionRequest.C0472a f280b;

    C0529i(SessionRequest.C0472a aVar, Session session) {
        this.f280b = aVar;
        this.f279a = session;
    }

    public void run() {
        try {
            SessionRequest.this.mo8697a(this.f280b.f141c, this.f279a.getConnType().getType(), C0610i.m364a(SessionRequest.this.f127a.f118c), (SessionGetCallback) null, 0);
        } catch (Exception unused) {
        }
    }
}
