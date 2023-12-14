package anet.channel.detect;

import android.text.TextUtils;
import android.util.Pair;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;

/* renamed from: anet.channel.detect.c */
/* compiled from: Taobao */
class C0495c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ RequestStatistic f195a;

    /* renamed from: b */
    final /* synthetic */ ExceptionDetector f196b;

    C0495c(ExceptionDetector exceptionDetector, RequestStatistic requestStatistic) {
        this.f196b = exceptionDetector;
        this.f195a = requestStatistic;
    }

    public void run() {
        try {
            if (this.f195a != null) {
                if (!TextUtils.isEmpty(this.f195a.f408ip) && this.f195a.ret == 0) {
                    if ("guide-acs.m.taobao.com".equalsIgnoreCase(this.f195a.host)) {
                        this.f196b.f180b = this.f195a.f408ip;
                    } else if ("msgacs.m.taobao.com".equalsIgnoreCase(this.f195a.host)) {
                        this.f196b.f181c = this.f195a.f408ip;
                    } else if ("gw.alicdn.com".equalsIgnoreCase(this.f195a.host)) {
                        this.f196b.f182d = this.f195a.f408ip;
                    }
                }
                if (!TextUtils.isEmpty(this.f195a.url)) {
                    this.f196b.f183e.add(Pair.create(this.f195a.url, Integer.valueOf(this.f195a.statusCode)));
                }
                if (this.f196b.mo8770c()) {
                    this.f196b.mo8769b();
                }
            }
        } catch (Throwable th) {
            ALog.m326e("anet.ExceptionDetector", "network detect fail.", (String) null, th, new Object[0]);
        }
    }
}
