package anetwork.channel.unified;

import android.text.TextUtils;
import anet.channel.RequestCb;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.session.C0549b;
import anet.channel.util.StringUtils;
import anetwork.channel.cookie.CookieManager;

/* renamed from: anetwork.channel.unified.b */
/* compiled from: Taobao */
public class C0643b implements IUnifiedTask {

    /* renamed from: a */
    volatile Cancelable f745a = null;

    /* renamed from: b */
    private volatile boolean f746b = false;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public C0652j f747c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public int f748d = 0;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public int f749e = 0;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public Request f750f;

    /* renamed from: b */
    static /* synthetic */ int m441b(C0643b bVar) {
        int i = bVar.f749e;
        bVar.f749e = i + 1;
        return i;
    }

    public C0643b(C0652j jVar) {
        this.f747c = jVar;
        this.f750f = jVar.f787a.mo9328a();
    }

    public void cancel() {
        this.f746b = true;
        if (this.f745a != null) {
            this.f745a.cancel();
        }
    }

    public void run() {
        if (!this.f746b) {
            if (this.f747c.f787a.mo9339i()) {
                String cookie = CookieManager.getCookie(this.f747c.f787a.mo9337g());
                if (!TextUtils.isEmpty(cookie)) {
                    Request.Builder newBuilder = this.f750f.newBuilder();
                    String str = this.f750f.getHeaders().get("Cookie");
                    if (!TextUtils.isEmpty(str)) {
                        cookie = StringUtils.concatString(str, "; ", cookie);
                    }
                    newBuilder.addHeader("Cookie", cookie);
                    this.f750f = newBuilder.build();
                }
            }
            this.f750f.f322a.degraded = 2;
            this.f750f.f322a.sendBeforeTime = System.currentTimeMillis() - this.f750f.f322a.reqStart;
            C0549b.m214a(this.f750f, (RequestCb) new C0644c(this));
        }
    }
}
