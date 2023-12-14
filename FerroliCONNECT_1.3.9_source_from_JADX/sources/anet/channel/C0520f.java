package anet.channel;

import anet.channel.SessionRequest;
import anet.channel.entity.C0518b;
import anet.channel.entity.EventCb;
import anet.channel.util.ALog;

/* renamed from: anet.channel.f */
/* compiled from: Taobao */
class C0520f implements EventCb {

    /* renamed from: a */
    final /* synthetic */ SessionRequest.IConnCb f252a;

    /* renamed from: b */
    final /* synthetic */ long f253b;

    /* renamed from: c */
    final /* synthetic */ SessionRequest f254c;

    C0520f(SessionRequest sessionRequest, SessionRequest.IConnCb iConnCb, long j) {
        this.f254c = sessionRequest;
        this.f252a = iConnCb;
        this.f253b = j;
    }

    public void onEvent(Session session, int i, C0518b bVar) {
        String str;
        Session session2 = session;
        int i2 = i;
        C0518b bVar2 = bVar;
        if (session2 != null) {
            int i3 = bVar2 == null ? 0 : bVar2.f247b;
            if (bVar2 == null) {
                str = "";
            } else {
                str = bVar2.f248c;
            }
            if (i2 == 2) {
                ALog.m325d("awcn.SessionRequest", (String) null, session2 != null ? session2.f104p : null, "Session", session2, "EventType", Integer.valueOf(i), "Event", bVar2);
                this.f254c.mo8700a(session2, i3, str);
                if (this.f254c.f128b.mo8794c(this.f254c, session2)) {
                    this.f252a.onDisConnect(session2, this.f253b, i2);
                } else {
                    this.f252a.onFailed(session, this.f253b, i, i3);
                }
            } else if (i2 == 256) {
                ALog.m325d("awcn.SessionRequest", (String) null, session2 != null ? session2.f104p : null, "Session", session2, "EventType", Integer.valueOf(i), "Event", bVar2);
                this.f252a.onFailed(session, this.f253b, i, i3);
            } else if (i2 == 512) {
                ALog.m325d("awcn.SessionRequest", (String) null, session2 != null ? session2.f104p : null, "Session", session2, "EventType", Integer.valueOf(i), "Event", bVar2);
                this.f254c.mo8700a(session2, 0, (String) null);
                this.f252a.onSuccess(session2, this.f253b);
            }
        }
    }
}
