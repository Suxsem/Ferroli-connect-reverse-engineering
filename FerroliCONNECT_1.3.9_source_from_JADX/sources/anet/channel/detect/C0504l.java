package anet.channel.detect;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;

/* renamed from: anet.channel.detect.l */
/* compiled from: Taobao */
class C0504l implements NetworkStatusHelper.INetworkStatusChangeListener {

    /* renamed from: a */
    final /* synthetic */ C0503k f212a;

    C0504l(C0503k kVar) {
        this.f212a = kVar;
    }

    public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
        ThreadPoolExecutorFactory.submitDetectTask(new C0505m(this, networkStatus));
    }
}
