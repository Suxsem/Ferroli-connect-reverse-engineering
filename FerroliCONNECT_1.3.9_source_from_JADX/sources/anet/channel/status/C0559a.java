package anet.channel.status;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.Iterator;

/* renamed from: anet.channel.status.a */
/* compiled from: Taobao */
final class C0559a implements Runnable {

    /* renamed from: a */
    final /* synthetic */ NetworkStatusHelper.NetworkStatus f416a;

    C0559a(NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f416a = networkStatus;
    }

    public void run() {
        try {
            Iterator<NetworkStatusHelper.INetworkStatusChangeListener> it = NetworkStatusHelper.listeners.iterator();
            while (it.hasNext()) {
                NetworkStatusHelper.INetworkStatusChangeListener next = it.next();
                long currentTimeMillis = System.currentTimeMillis();
                next.onNetworkStatusChanged(this.f416a);
                if (System.currentTimeMillis() - currentTimeMillis > 500) {
                    ALog.m327e("awcn.NetworkStatusHelper", "call back cost too much time", (String) null, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, next);
                }
            }
        } catch (Exception unused) {
        }
    }
}
