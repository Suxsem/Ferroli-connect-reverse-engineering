package anetwork.channel.entity;

import android.os.RemoteException;
import anetwork.channel.aidl.ParcelableHeader;
import anetwork.channel.aidl.ParcelableNetworkListener;
import java.util.Map;

/* renamed from: anetwork.channel.entity.d */
/* compiled from: Taobao */
class C0637d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ ParcelableNetworkListener f718a;

    /* renamed from: b */
    final /* synthetic */ int f719b;

    /* renamed from: c */
    final /* synthetic */ Map f720c;

    /* renamed from: d */
    final /* synthetic */ C0636c f721d;

    C0637d(C0636c cVar, ParcelableNetworkListener parcelableNetworkListener, int i, Map map) {
        this.f721d = cVar;
        this.f718a = parcelableNetworkListener;
        this.f719b = i;
        this.f720c = map;
    }

    public void run() {
        try {
            this.f718a.onResponseCode(this.f719b, new ParcelableHeader(this.f719b, this.f720c));
        } catch (RemoteException unused) {
        }
    }
}
