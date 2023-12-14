package anetwork.channel.aidl.adapter;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import anet.channel.util.ALog;
import anetwork.channel.aidl.IRemoteNetworkGetter;

/* renamed from: anetwork.channel.aidl.adapter.e */
/* compiled from: Taobao */
final class C0618e implements ServiceConnection {
    C0618e() {
    }

    public void onServiceDisconnected(ComponentName componentName) {
        if (ALog.isPrintLog(2)) {
            ALog.m328i("anet.RemoteGetter", "ANet_Service Disconnected", (String) null, new Object[0]);
        }
        C0617d.f630a = null;
        C0617d.f632c = false;
        if (C0617d.f633d != null) {
            C0617d.f633d.countDown();
        }
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (ALog.isPrintLog(2)) {
            ALog.m328i("anet.RemoteGetter", "[onServiceConnected]ANet_Service start success. ANet run with service mode", (String) null, new Object[0]);
        }
        synchronized (C0617d.class) {
            C0617d.f630a = IRemoteNetworkGetter.Stub.asInterface(iBinder);
            if (C0617d.f633d != null) {
                C0617d.f633d.countDown();
            }
        }
        C0617d.f631b = false;
        C0617d.f632c = false;
    }
}
