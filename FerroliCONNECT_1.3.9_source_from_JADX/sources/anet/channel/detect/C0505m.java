package anet.channel.detect;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;

/* renamed from: anet.channel.detect.m */
/* compiled from: Taobao */
class C0505m implements Runnable {

    /* renamed from: a */
    final /* synthetic */ NetworkStatusHelper.NetworkStatus f213a;

    /* renamed from: b */
    final /* synthetic */ C0504l f214b;

    C0505m(C0504l lVar, NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f214b = lVar;
        this.f213a = networkStatus;
    }

    public void run() {
        try {
            if (this.f213a == NetworkStatusHelper.NetworkStatus.NO) {
                return;
            }
            if (this.f213a != NetworkStatusHelper.NetworkStatus.NONE) {
                this.f214b.f212a.m92a(NetworkStatusHelper.getUniqueId(this.f213a));
            }
        } catch (Throwable th) {
            ALog.m326e("anet.MTUDetector", "MTU detecet fail.", (String) null, th, new Object[0]);
        }
    }
}
