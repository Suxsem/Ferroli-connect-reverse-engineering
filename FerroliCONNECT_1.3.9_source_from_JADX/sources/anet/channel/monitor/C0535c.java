package anet.channel.monitor;

import anet.channel.status.NetworkStatusHelper;

/* renamed from: anet.channel.monitor.c */
/* compiled from: Taobao */
class C0535c implements NetworkStatusHelper.INetworkStatusChangeListener {

    /* renamed from: a */
    final /* synthetic */ C0533b f302a;

    C0535c(C0533b bVar) {
        this.f302a = bVar;
    }

    public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f302a.f300n.mo8849a();
        C0533b.f292f = 0;
        this.f302a.mo8845d();
    }
}
