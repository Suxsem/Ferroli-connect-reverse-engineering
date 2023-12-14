package anet.channel.status;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import anet.channel.util.ALog;
import java.util.ArrayList;

/* renamed from: anet.channel.status.c */
/* compiled from: Taobao */
final class C0561c extends ConnectivityManager.NetworkCallback {
    C0561c() {
    }

    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
        C0560b.f428l = new ArrayList(linkProperties.getDnsServers());
    }

    public void onLost(Network network) {
        super.onLost(network);
        ALog.m328i("awcn.NetworkStatusMonitor", "network onLost", (String) null, new Object[0]);
        C0560b.f418b = false;
    }

    public void onAvailable(Network network) {
        super.onAvailable(network);
        ALog.m328i("awcn.NetworkStatusMonitor", "network onAvailable", (String) null, new Object[0]);
        C0560b.f418b = true;
    }
}
