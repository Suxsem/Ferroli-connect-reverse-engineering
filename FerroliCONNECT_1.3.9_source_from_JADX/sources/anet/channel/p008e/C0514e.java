package anet.channel.p008e;

import android.support.p000v4.view.InputDeviceCompat;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.entity.C0517a;
import anet.channel.session.TnetSpdySession;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import java.util.List;

/* renamed from: anet.channel.e.e */
/* compiled from: Taobao */
final class C0514e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ List f236a;

    /* renamed from: b */
    final /* synthetic */ NetworkStatusHelper.NetworkStatus f237b;

    C0514e(List list, NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f236a = list;
        this.f237b = networkStatus;
    }

    public void run() {
        IConnStrategy iConnStrategy = (IConnStrategy) this.f236a.get(0);
        TnetSpdySession tnetSpdySession = new TnetSpdySession(GlobalAppRuntimeInfo.getContext(), new C0517a("https://" + C0508a.f224b, "Http3Detect" + C0508a.f230h.getAndIncrement(), C0508a.m108b(iConnStrategy)));
        tnetSpdySession.registerEventcb(InputDeviceCompat.SOURCE_KEYBOARD, new C0515f(this, iConnStrategy));
        tnetSpdySession.f105q.isCommitted = true;
        tnetSpdySession.connect();
    }
}
