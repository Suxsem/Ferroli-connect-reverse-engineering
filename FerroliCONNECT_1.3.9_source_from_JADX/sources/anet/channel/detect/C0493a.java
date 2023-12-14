package anet.channel.detect;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;

/* renamed from: anet.channel.detect.a */
/* compiled from: Taobao */
class C0493a implements NetworkStatusHelper.INetworkStatusChangeListener {

    /* renamed from: a */
    final /* synthetic */ ExceptionDetector f193a;

    C0493a(ExceptionDetector exceptionDetector) {
        this.f193a = exceptionDetector;
    }

    public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
        ThreadPoolExecutorFactory.submitDetectTask(new C0494b(this));
    }
}
