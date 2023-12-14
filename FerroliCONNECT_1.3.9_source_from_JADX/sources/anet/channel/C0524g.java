package anet.channel;

import anet.channel.entity.C0518b;
import anet.channel.entity.EventCb;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;

/* renamed from: anet.channel.g */
/* compiled from: Taobao */
class C0524g implements EventCb {

    /* renamed from: a */
    final /* synthetic */ Session f265a;

    /* renamed from: b */
    final /* synthetic */ SessionRequest f266b;

    C0524g(SessionRequest sessionRequest, Session session) {
        this.f266b = sessionRequest;
        this.f265a = session;
    }

    public void onEvent(Session session, int i, C0518b bVar) {
        ALog.m325d("awcn.SessionRequest", "Receive session event", (String) null, "eventType", Integer.valueOf(i));
        ConnEvent connEvent = new ConnEvent();
        if (i == 512) {
            connEvent.isSuccess = true;
        }
        if (this.f266b.f129c != null) {
            connEvent.isAccs = this.f266b.f129c.isAccs;
        }
        StrategyCenter.getInstance().notifyConnEvent(this.f265a.getRealHost(), this.f265a.getConnStrategy(), connEvent);
    }
}
