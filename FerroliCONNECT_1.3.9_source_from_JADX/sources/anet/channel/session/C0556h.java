package anet.channel.session;

import anet.channel.entity.C0518b;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;

/* renamed from: anet.channel.session.h */
/* compiled from: Taobao */
class C0556h implements Runnable {

    /* renamed from: a */
    final /* synthetic */ TnetSpdySession f401a;

    C0556h(TnetSpdySession tnetSpdySession) {
        this.f401a = tnetSpdySession;
    }

    public void run() {
        if (this.f401a.f380y) {
            ALog.m327e("awcn.TnetSpdySession", "send msg time out!", this.f401a.f104p, "pingUnRcv:", Boolean.valueOf(this.f401a.f380y));
            try {
                this.f401a.handleCallbacks(2048, (C0518b) null);
                if (this.f401a.f105q != null) {
                    this.f401a.f105q.closeReason = "ping time out";
                }
                ConnEvent connEvent = new ConnEvent();
                connEvent.isSuccess = false;
                connEvent.isAccs = this.f401a.f377I;
                StrategyCenter.getInstance().notifyConnEvent(this.f401a.f92d, this.f401a.f99k, connEvent);
                this.f401a.close(true);
            } catch (Exception unused) {
            }
        }
    }
}
