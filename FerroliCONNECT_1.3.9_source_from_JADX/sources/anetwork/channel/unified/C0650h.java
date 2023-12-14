package anetwork.channel.unified;

import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.SessionGetCallback;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;

/* renamed from: anetwork.channel.unified.h */
/* compiled from: Taobao */
class C0650h implements SessionGetCallback {

    /* renamed from: a */
    final /* synthetic */ RequestStatistic f777a;

    /* renamed from: b */
    final /* synthetic */ long f778b;

    /* renamed from: c */
    final /* synthetic */ Request f779c;

    /* renamed from: d */
    final /* synthetic */ SessionCenter f780d;

    /* renamed from: e */
    final /* synthetic */ HttpUrl f781e;

    /* renamed from: f */
    final /* synthetic */ boolean f782f;

    /* renamed from: g */
    final /* synthetic */ C0646e f783g;

    C0650h(C0646e eVar, RequestStatistic requestStatistic, long j, Request request, SessionCenter sessionCenter, HttpUrl httpUrl, boolean z) {
        this.f783g = eVar;
        this.f777a = requestStatistic;
        this.f778b = j;
        this.f779c = request;
        this.f780d = sessionCenter;
        this.f781e = httpUrl;
        this.f782f = z;
    }

    public void onSessionGetSuccess(Session session) {
        ALog.m328i(C0646e.TAG, "onSessionGetSuccess", this.f783g.f754a.f789c, "Session", session);
        this.f777a.connWaitTime = System.currentTimeMillis() - this.f778b;
        this.f777a.spdyRequestSend = true;
        this.f783g.m452a(session, this.f779c);
    }

    public void onSessionGetFail() {
        ALog.m327e(C0646e.TAG, "onSessionGetFail", this.f783g.f754a.f789c, "url", this.f777a.url);
        this.f777a.connWaitTime = System.currentTimeMillis() - this.f778b;
        C0646e eVar = this.f783g;
        eVar.m452a(eVar.m447a((Session) null, this.f780d, this.f781e, this.f782f), this.f779c);
    }
}
