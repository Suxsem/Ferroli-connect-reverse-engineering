package anetwork.channel.unified;

import anet.channel.bytes.ByteArray;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import org.eclipse.jetty.http.HttpHeaderValues;

/* renamed from: anetwork.channel.unified.a */
/* compiled from: Taobao */
public class C0642a implements IUnifiedTask {

    /* renamed from: a */
    private C0652j f742a = null;

    /* renamed from: b */
    private Cache f743b = null;

    /* renamed from: c */
    private volatile boolean f744c = false;

    public C0642a(C0652j jVar, Cache cache) {
        this.f742a = jVar;
        this.f743b = cache;
    }

    public void cancel() {
        this.f744c = true;
        this.f742a.f787a.f731b.ret = 2;
    }

    public void run() {
        Cache.Entry entry;
        boolean z;
        if (!this.f744c) {
            RequestStatistic requestStatistic = this.f742a.f787a.f731b;
            if (this.f743b != null) {
                String g = this.f742a.f787a.mo9337g();
                Request a = this.f742a.f787a.mo9328a();
                String str = a.getHeaders().get("Cache-Control");
                boolean equals = "no-store".equals(str);
                long currentTimeMillis = System.currentTimeMillis();
                if (equals) {
                    this.f743b.remove(g);
                    z = false;
                    entry = null;
                } else {
                    z = HttpHeaderValues.NO_CACHE.equals(str);
                    Cache.Entry entry2 = this.f743b.get(g);
                    if (ALog.isPrintLog(2)) {
                        String str2 = this.f742a.f789c;
                        Object[] objArr = new Object[8];
                        objArr[0] = "hit";
                        objArr[1] = Boolean.valueOf(entry2 != null);
                        objArr[2] = "cost";
                        objArr[3] = Long.valueOf(requestStatistic.cacheTime);
                        objArr[4] = "length";
                        objArr[5] = Integer.valueOf(entry2 != null ? entry2.data.length : 0);
                        objArr[6] = "key";
                        objArr[7] = g;
                        ALog.m328i("anet.CacheTask", "read cache", str2, objArr);
                    }
                    entry = entry2;
                }
                long currentTimeMillis2 = System.currentTimeMillis();
                requestStatistic.cacheTime = currentTimeMillis2 - currentTimeMillis;
                if (entry == null || z || !entry.isFresh()) {
                    if (!this.f744c) {
                        C0646e eVar = new C0646e(this.f742a, equals ? null : this.f743b, entry);
                        this.f742a.f791e = eVar;
                        eVar.run();
                    }
                } else if (this.f742a.f790d.compareAndSet(false, true)) {
                    this.f742a.mo9369a();
                    requestStatistic.ret = 1;
                    requestStatistic.statusCode = 200;
                    requestStatistic.msg = HttpConstant.SUCCESS;
                    requestStatistic.protocolType = "cache";
                    requestStatistic.rspEnd = currentTimeMillis2;
                    requestStatistic.processTime = currentTimeMillis2 - requestStatistic.start;
                    if (ALog.isPrintLog(2)) {
                        ALog.m328i("anet.CacheTask", "hit fresh cache", this.f742a.f789c, "URL", this.f742a.f787a.mo9336f().urlString());
                    }
                    this.f742a.f788b.onResponseCode(200, entry.responseHeaders);
                    this.f742a.f788b.onDataReceiveSize(1, entry.data.length, ByteArray.wrap(entry.data));
                    this.f742a.f788b.onFinish(new DefaultFinishEvent(200, HttpConstant.SUCCESS, a));
                }
            }
        }
    }
}
