package anet.channel.p008e;

import anet.channel.Session;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.entity.C0518b;
import anet.channel.entity.EventCb;
import anet.channel.statist.Http3DetectStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;

/* renamed from: anet.channel.e.f */
/* compiled from: Taobao */
class C0515f implements EventCb {

    /* renamed from: a */
    final /* synthetic */ IConnStrategy f238a;

    /* renamed from: b */
    final /* synthetic */ C0514e f239b;

    C0515f(C0514e eVar, IConnStrategy iConnStrategy) {
        this.f239b = eVar;
        this.f238a = iConnStrategy;
    }

    public void onEvent(Session session, int i, C0518b bVar) {
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        C0508a.f223a.mo8795a(NetworkStatusHelper.getUniqueId(this.f239b.f237b), z);
        session.close(false);
        Http3DetectStat http3DetectStat = new Http3DetectStat(C0508a.f224b, this.f238a);
        http3DetectStat.ret = z ? 1 : 0;
        AppMonitor.getInstance().commitStat(http3DetectStat);
    }
}
