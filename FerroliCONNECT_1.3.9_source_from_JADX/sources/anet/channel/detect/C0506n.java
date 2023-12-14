package anet.channel.detect;

import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: anet.channel.detect.n */
/* compiled from: Taobao */
public class C0506n {

    /* renamed from: a */
    private static C0496d f215a = new C0496d();

    /* renamed from: b */
    private static ExceptionDetector f216b = new ExceptionDetector();

    /* renamed from: c */
    private static C0503k f217c = new C0503k();

    /* renamed from: d */
    private static AtomicBoolean f218d = new AtomicBoolean(false);

    /* renamed from: a */
    public static void m94a() {
        try {
            if (f218d.compareAndSet(false, true)) {
                ALog.m328i("awcn.NetworkDetector", "registerListener", (String) null, new Object[0]);
                f215a.mo8775b();
                f216b.mo8767a();
                f217c.mo8787a();
            }
        } catch (Exception e) {
            ALog.m326e("awcn.NetworkDetector", "[registerListener]error", (String) null, e, new Object[0]);
        }
    }

    /* renamed from: a */
    public static void m95a(RequestStatistic requestStatistic) {
        if (f218d.get()) {
            f216b.mo8768a(requestStatistic);
        }
    }
}
