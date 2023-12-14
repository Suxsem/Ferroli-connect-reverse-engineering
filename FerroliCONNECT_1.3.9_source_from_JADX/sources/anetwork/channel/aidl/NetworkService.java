package anetwork.channel.aidl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import anet.channel.util.ALog;
import anetwork.channel.aidl.IRemoteNetworkGetter;
import anetwork.channel.aidl.RemoteNetwork;
import anetwork.channel.degrade.DegradableNetworkDelegate;
import anetwork.channel.http.HttpNetworkDelegate;

/* compiled from: Taobao */
public class NetworkService extends Service {

    /* renamed from: a */
    IRemoteNetworkGetter.Stub f616a = new IRemoteNetworkGetter.Stub() {
        public RemoteNetwork get(int i) throws RemoteException {
            return i == 1 ? NetworkService.this.f618c : NetworkService.this.f619d;
        }
    };

    /* renamed from: b */
    private Context f617b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public RemoteNetwork.Stub f618c = null;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public RemoteNetwork.Stub f619d = null;

    public void onDestroy() {
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    public IBinder onBind(Intent intent) {
        this.f617b = getApplicationContext();
        if (ALog.isPrintLog(2)) {
            ALog.m328i("anet.NetworkService", "onBind:" + intent.getAction(), (String) null, new Object[0]);
        }
        this.f618c = new DegradableNetworkDelegate(this.f617b);
        this.f619d = new HttpNetworkDelegate(this.f617b);
        if (IRemoteNetworkGetter.class.getName().equals(intent.getAction())) {
            return this.f616a;
        }
        return null;
    }
}
