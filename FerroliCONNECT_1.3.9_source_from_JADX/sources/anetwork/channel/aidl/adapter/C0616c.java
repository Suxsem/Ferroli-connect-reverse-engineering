package anetwork.channel.aidl.adapter;

/* renamed from: anetwork.channel.aidl.adapter.c */
/* compiled from: Taobao */
class C0616c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ byte f627a;

    /* renamed from: b */
    final /* synthetic */ Object f628b;

    /* renamed from: c */
    final /* synthetic */ ParcelableNetworkListenerWrapper f629c;

    C0616c(ParcelableNetworkListenerWrapper parcelableNetworkListenerWrapper, byte b, Object obj) {
        this.f629c = parcelableNetworkListenerWrapper;
        this.f627a = b;
        this.f628b = obj;
    }

    public void run() {
        this.f629c.dispatchCallback(this.f627a, this.f628b);
    }
}
