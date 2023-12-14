package anet.channel.request;

import anet.channel.util.ALog;
import java.util.concurrent.Future;

/* renamed from: anet.channel.request.b */
/* compiled from: Taobao */
public class C0541b implements Cancelable {
    public static final C0541b NULL = new C0541b((Future<?>) null, (String) null);

    /* renamed from: a */
    private final Future<?> f358a;

    /* renamed from: b */
    private final String f359b;

    public C0541b(Future<?> future, String str) {
        this.f358a = future;
        this.f359b = str;
    }

    public void cancel() {
        if (this.f358a != null) {
            ALog.m328i("awcn.FutureCancelable", "cancel request", this.f359b, new Object[0]);
            this.f358a.cancel(true);
        }
    }
}
