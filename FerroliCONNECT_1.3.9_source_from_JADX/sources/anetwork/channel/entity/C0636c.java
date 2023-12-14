package anetwork.channel.entity;

import android.os.RemoteException;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.ParcelableNetworkListener;
import anetwork.channel.aidl.adapter.ParcelableInputStreamImpl;
import anetwork.channel.interceptor.Callback;
import java.util.List;
import java.util.Map;

/* renamed from: anetwork.channel.entity.c */
/* compiled from: Taobao */
public class C0636c implements Callback {

    /* renamed from: a */
    private ParcelableNetworkListener f713a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public String f714b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public ParcelableInputStreamImpl f715c = null;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f716d = false;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public C0640g f717e = null;

    public C0636c(ParcelableNetworkListener parcelableNetworkListener, C0640g gVar) {
        this.f713a = parcelableNetworkListener;
        this.f717e = gVar;
        if (parcelableNetworkListener != null) {
            try {
                if ((parcelableNetworkListener.getListenerState() & 8) != 0) {
                    this.f716d = true;
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public void onResponseCode(int i, Map<String, List<String>> map) {
        if (ALog.isPrintLog(2)) {
            ALog.m328i("anet.Repeater", "[onResponseCode]", this.f714b, new Object[0]);
        }
        ParcelableNetworkListener parcelableNetworkListener = this.f713a;
        if (parcelableNetworkListener != null) {
            m416a((Runnable) new C0637d(this, parcelableNetworkListener, i, map));
        }
    }

    public void onDataReceiveSize(int i, int i2, ByteArray byteArray) {
        ParcelableNetworkListener parcelableNetworkListener = this.f713a;
        if (parcelableNetworkListener != null) {
            m416a((Runnable) new C0638e(this, i, byteArray, i2, parcelableNetworkListener));
        }
    }

    public void onFinish(DefaultFinishEvent defaultFinishEvent) {
        if (ALog.isPrintLog(2)) {
            ALog.m328i("anet.Repeater", "[onFinish] ", this.f714b, new Object[0]);
        }
        ParcelableNetworkListener parcelableNetworkListener = this.f713a;
        if (parcelableNetworkListener != null) {
            C0639f fVar = new C0639f(this, defaultFinishEvent, parcelableNetworkListener);
            RequestStatistic requestStatistic = defaultFinishEvent.f603rs;
            if (requestStatistic != null) {
                requestStatistic.rspCbDispatch = System.currentTimeMillis();
            }
            m416a((Runnable) fVar);
        }
        this.f713a = null;
    }

    /* renamed from: a */
    private void m416a(Runnable runnable) {
        if (this.f717e.mo9333c()) {
            runnable.run();
            return;
        }
        String str = this.f714b;
        C0634a.m414a(str != null ? str.hashCode() : hashCode(), runnable);
    }

    /* renamed from: a */
    public void mo9321a(String str) {
        this.f714b = str;
    }
}
