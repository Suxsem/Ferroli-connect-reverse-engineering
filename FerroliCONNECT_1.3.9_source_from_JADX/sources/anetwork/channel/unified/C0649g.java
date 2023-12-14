package anetwork.channel.unified;

import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.entity.C0519c;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.HttpUrl;

/* renamed from: anetwork.channel.unified.g */
/* compiled from: Taobao */
class C0649g implements Runnable {

    /* renamed from: a */
    final /* synthetic */ SessionCenter f771a;

    /* renamed from: b */
    final /* synthetic */ HttpUrl f772b;

    /* renamed from: c */
    final /* synthetic */ RequestStatistic f773c;

    /* renamed from: d */
    final /* synthetic */ HttpUrl f774d;

    /* renamed from: e */
    final /* synthetic */ boolean f775e;

    /* renamed from: f */
    final /* synthetic */ C0646e f776f;

    C0649g(C0646e eVar, SessionCenter sessionCenter, HttpUrl httpUrl, RequestStatistic requestStatistic, HttpUrl httpUrl2, boolean z) {
        this.f776f = eVar;
        this.f771a = sessionCenter;
        this.f772b = httpUrl;
        this.f773c = requestStatistic;
        this.f774d = httpUrl2;
        this.f775e = z;
    }

    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        Session session = this.f771a.get(this.f772b, C0519c.f249a, 3000);
        this.f773c.connWaitTime = System.currentTimeMillis() - currentTimeMillis;
        this.f773c.spdyRequestSend = session != null;
        Session a = this.f776f.m447a(session, this.f771a, this.f774d, this.f775e);
        C0646e eVar = this.f776f;
        eVar.m452a(a, eVar.f754a.f787a.mo9328a());
    }
}
