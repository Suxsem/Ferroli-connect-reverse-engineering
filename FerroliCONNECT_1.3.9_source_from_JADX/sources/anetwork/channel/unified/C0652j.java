package anetwork.channel.unified;

import anetwork.channel.entity.C0640g;
import anetwork.channel.interceptor.Callback;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: anetwork.channel.unified.j */
/* compiled from: Taobao */
class C0652j {

    /* renamed from: a */
    public final C0640g f787a;

    /* renamed from: b */
    public Callback f788b;

    /* renamed from: c */
    public final String f789c;

    /* renamed from: d */
    public volatile AtomicBoolean f790d = new AtomicBoolean();

    /* renamed from: e */
    public volatile IUnifiedTask f791e = null;

    /* renamed from: f */
    public volatile Future f792f = null;

    public C0652j(C0640g gVar, Callback callback) {
        this.f787a = gVar;
        this.f789c = gVar.f734e;
        this.f788b = callback;
    }

    /* renamed from: a */
    public void mo9369a() {
        Future future = this.f792f;
        if (future != null) {
            future.cancel(true);
            this.f792f = null;
        }
    }

    /* renamed from: b */
    public void mo9370b() {
        if (this.f791e != null) {
            this.f791e.cancel();
            this.f791e = null;
        }
    }
}
