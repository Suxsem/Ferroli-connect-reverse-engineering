package anet.channel.session;

import anet.channel.RequestCb;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;

/* renamed from: anet.channel.session.f */
/* compiled from: Taobao */
class C0554f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Request f396a;

    /* renamed from: b */
    final /* synthetic */ RequestCb f397b;

    /* renamed from: c */
    final /* synthetic */ RequestStatistic f398c;

    /* renamed from: d */
    final /* synthetic */ C0552d f399d;

    C0554f(C0552d dVar, Request request, RequestCb requestCb, RequestStatistic requestStatistic) {
        this.f399d = dVar;
        this.f396a = request;
        this.f397b = requestCb;
        this.f398c = requestStatistic;
    }

    public void run() {
        this.f396a.f322a.sendBeforeTime = System.currentTimeMillis() - this.f396a.f322a.reqStart;
        C0549b.m214a(this.f396a, (RequestCb) new C0555g(this));
    }
}
