package anetwork.channel.entity;

import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anetwork.channel.aidl.DefaultProgressEvent;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.adapter.ParcelableInputStreamImpl;

/* renamed from: anetwork.channel.entity.e */
/* compiled from: Taobao */
class C0638e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ int f722a;

    /* renamed from: b */
    final /* synthetic */ ByteArray f723b;

    /* renamed from: c */
    final /* synthetic */ int f724c;

    /* renamed from: d */
    final /* synthetic */ ParcelableNetworkListener f725d;

    /* renamed from: e */
    final /* synthetic */ C0636c f726e;

    C0638e(C0636c cVar, int i, ByteArray byteArray, int i2, ParcelableNetworkListener parcelableNetworkListener) {
        this.f726e = cVar;
        this.f722a = i;
        this.f723b = byteArray;
        this.f724c = i2;
        this.f725d = parcelableNetworkListener;
    }

    public void run() {
        if (!this.f726e.f716d) {
            try {
                this.f725d.onDataReceived(new DefaultProgressEvent(this.f722a, this.f723b.getDataLength(), this.f724c, this.f723b.getBuffer()));
            } catch (RemoteException unused) {
            }
        } else {
            try {
                if (this.f726e.f715c == null) {
                    ParcelableInputStreamImpl unused2 = this.f726e.f715c = new ParcelableInputStreamImpl();
                    this.f726e.f715c.init(this.f726e.f717e, this.f724c);
                    this.f726e.f715c.write(this.f723b);
                    this.f725d.onInputStreamGet(this.f726e.f715c);
                    return;
                }
                this.f726e.f715c.write(this.f723b);
            } catch (Exception unused3) {
                if (this.f726e.f715c != null) {
                    this.f726e.f715c.close();
                }
            }
        }
    }
}
