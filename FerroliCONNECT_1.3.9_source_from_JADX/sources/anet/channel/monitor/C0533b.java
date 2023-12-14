package anet.channel.monitor;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;

/* renamed from: anet.channel.monitor.b */
/* compiled from: Taobao */
public class C0533b {

    /* renamed from: a */
    static int f287a = 0;

    /* renamed from: b */
    static long f288b = 0;

    /* renamed from: c */
    static long f289c = 0;

    /* renamed from: d */
    static long f290d = 0;

    /* renamed from: e */
    static long f291e = 0;

    /* renamed from: f */
    static long f292f = 0;

    /* renamed from: g */
    static double f293g = 0.0d;

    /* renamed from: h */
    static double f294h = 0.0d;

    /* renamed from: i */
    static double f295i = 0.0d;

    /* renamed from: j */
    static double f296j = 40.0d;

    /* renamed from: k */
    private static volatile boolean f297k = false;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public int f298l;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public int f299m;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public C0537e f300n;

    /* synthetic */ C0533b(C0535c cVar) {
        this();
    }

    /* renamed from: b */
    static /* synthetic */ int m140b(C0533b bVar) {
        int i = bVar.f299m;
        bVar.f299m = i + 1;
        return i;
    }

    /* renamed from: anet.channel.monitor.b$a */
    /* compiled from: Taobao */
    static class C0534a {

        /* renamed from: a */
        static C0533b f301a = new C0533b((C0535c) null);

        C0534a() {
        }
    }

    /* renamed from: a */
    public static C0533b m138a() {
        return C0534a.f301a;
    }

    private C0533b() {
        this.f298l = 5;
        this.f299m = 0;
        this.f300n = new C0537e();
        NetworkStatusHelper.addStatusChangeListener(new C0535c(this));
    }

    /* renamed from: b */
    public int mo8843b() {
        if (NetworkStatusHelper.getStatus() == NetworkStatusHelper.NetworkStatus.G2) {
            return 1;
        }
        return this.f298l;
    }

    /* renamed from: c */
    public double mo8844c() {
        return f295i;
    }

    /* renamed from: d */
    public synchronized void mo8845d() {
        try {
            ALog.m328i("awcn.BandWidthSampler", "[startNetworkMeter]", (String) null, "NetworkStatus", NetworkStatusHelper.getStatus());
            if (NetworkStatusHelper.getStatus() == NetworkStatusHelper.NetworkStatus.G2) {
                f297k = false;
                return;
            }
            f297k = true;
        } catch (Exception e) {
            ALog.m329w("awcn.BandWidthSampler", "startNetworkMeter fail.", (String) null, e, new Object[0]);
        }
        return;
    }

    /* renamed from: e */
    public void mo8846e() {
        f297k = false;
    }

    /* renamed from: a */
    public void mo8842a(long j, long j2, long j3) {
        if (f297k) {
            if (ALog.isPrintLog(1)) {
                ALog.m325d("awcn.BandWidthSampler", "onDataReceived", (String) null, "mRequestStartTime", Long.valueOf(j), "mRequestFinishedTime", Long.valueOf(j2), "mRequestDataSize", Long.valueOf(j3));
            }
            if (j3 > 3000 && j < j2) {
                ThreadPoolExecutorFactory.submitScheduledTask(new C0536d(this, j3, j2, j));
            }
        }
    }
}
