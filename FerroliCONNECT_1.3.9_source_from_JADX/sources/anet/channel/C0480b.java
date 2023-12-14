package anet.channel;

import anet.channel.entity.C0518b;
import anet.channel.entity.EventCb;
import anet.channel.util.ALog;

/* renamed from: anet.channel.b */
/* compiled from: Taobao */
class C0480b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ int f160a;

    /* renamed from: b */
    final /* synthetic */ C0518b f161b;

    /* renamed from: c */
    final /* synthetic */ Session f162c;

    C0480b(Session session, int i, C0518b bVar) {
        this.f162c = session;
        this.f160a = i;
        this.f161b = bVar;
    }

    public void run() {
        try {
            if (this.f162c.f90b != null) {
                for (EventCb next : this.f162c.f90b.keySet()) {
                    if (!(next == null || (this.f162c.f90b.get(next).intValue() & this.f160a) == 0)) {
                        try {
                            next.onEvent(this.f162c, this.f160a, this.f161b);
                        } catch (Exception e) {
                            ALog.m327e("awcn.Session", e.toString(), this.f162c.f104p, new Object[0]);
                        }
                    }
                }
            }
        } catch (Exception e2) {
            ALog.m326e("awcn.Session", "handleCallbacks", this.f162c.f104p, e2, new Object[0]);
        }
    }
}
