package anetwork.channel.unified;

import android.support.p000v4.app.NotificationCompat;
import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpHelper;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cookie.CookieManager;
import java.util.List;
import java.util.Map;

/* renamed from: anetwork.channel.unified.c */
/* compiled from: Taobao */
class C0644c implements RequestCb {

    /* renamed from: a */
    final /* synthetic */ C0643b f751a;

    C0644c(C0643b bVar) {
        this.f751a = bVar;
    }

    public void onResponseCode(int i, Map<String, List<String>> map) {
        if (!this.f751a.f747c.f790d.get()) {
            this.f751a.f747c.mo9369a();
            CookieManager.setCookie(this.f751a.f747c.f787a.mo9337g(), map);
            int unused = this.f751a.f748d = HttpHelper.parseContentLength(map);
            if (this.f751a.f747c.f788b != null) {
                this.f751a.f747c.f788b.onResponseCode(i, map);
            }
        }
    }

    public void onDataReceive(ByteArray byteArray, boolean z) {
        if (!this.f751a.f747c.f790d.get()) {
            C0643b.m441b(this.f751a);
            if (this.f751a.f747c.f788b != null) {
                this.f751a.f747c.f788b.onDataReceiveSize(this.f751a.f749e, this.f751a.f748d, byteArray);
            }
        }
    }

    public void onFinish(int i, String str, RequestStatistic requestStatistic) {
        if (!this.f751a.f747c.f790d.getAndSet(true)) {
            if (ALog.isPrintLog(2)) {
                ALog.m328i("anet.DegradeTask", "[onFinish]", this.f751a.f747c.f789c, "code", Integer.valueOf(i), NotificationCompat.CATEGORY_MESSAGE, str);
            }
            this.f751a.f747c.mo9369a();
            requestStatistic.isDone.set(true);
            if (this.f751a.f747c.f788b != null) {
                this.f751a.f747c.f788b.onFinish(new DefaultFinishEvent(i, str, this.f751a.f750f));
            }
        }
    }
}
