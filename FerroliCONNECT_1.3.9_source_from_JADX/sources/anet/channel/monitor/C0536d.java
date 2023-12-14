package anet.channel.monitor;

import anet.channel.util.ALog;

/* renamed from: anet.channel.monitor.d */
/* compiled from: Taobao */
class C0536d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ long f303a;

    /* renamed from: b */
    final /* synthetic */ long f304b;

    /* renamed from: c */
    final /* synthetic */ long f305c;

    /* renamed from: d */
    final /* synthetic */ C0533b f306d;

    C0536d(C0533b bVar, long j, long j2, long j3) {
        this.f306d = bVar;
        this.f303a = j;
        this.f304b = j2;
        this.f305c = j3;
    }

    public void run() {
        C0533b.f287a++;
        C0533b.f291e += this.f303a;
        if (C0533b.f287a == 1) {
            C0533b.f290d = this.f304b - this.f305c;
        }
        if (C0533b.f287a >= 2 && C0533b.f287a <= 3) {
            if (this.f305c >= C0533b.f289c) {
                C0533b.f290d += this.f304b - this.f305c;
            } else if (this.f305c < C0533b.f289c && this.f304b >= C0533b.f289c) {
                C0533b.f290d += this.f304b - this.f305c;
                C0533b.f290d -= C0533b.f289c - this.f305c;
            }
        }
        C0533b.f288b = this.f305c;
        C0533b.f289c = this.f304b;
        if (C0533b.f287a == 3) {
            C0533b.f295i = (double) ((long) this.f306d.f300n.mo8848a((double) C0533b.f291e, (double) C0533b.f290d));
            C0533b.f292f++;
            C0533b.m140b(this.f306d);
            if (C0533b.f292f > 30) {
                this.f306d.f300n.mo8849a();
                C0533b.f292f = 3;
            }
            double d = (C0533b.f295i * 0.68d) + (C0533b.f294h * 0.27d) + (C0533b.f293g * 0.05d);
            C0533b.f293g = C0533b.f294h;
            C0533b.f294h = C0533b.f295i;
            if (C0533b.f295i < C0533b.f293g * 0.65d || C0533b.f295i > C0533b.f293g * 2.0d) {
                C0533b.f295i = d;
            }
            int i = 5;
            if (ALog.isPrintLog(1)) {
                ALog.m325d("awcn.BandWidthSampler", "NetworkSpeed", (String) null, "mKalmanDataSize", Long.valueOf(C0533b.f291e), "mKalmanTimeUsed", Long.valueOf(C0533b.f290d), "speed", Double.valueOf(C0533b.f295i), "mSpeedKalmanCount", Long.valueOf(C0533b.f292f));
            }
            if (this.f306d.f299m > 5 || C0533b.f292f == 2) {
                C0532a.m133a().mo8839a(C0533b.f295i);
                int unused = this.f306d.f299m = 0;
                C0533b bVar = this.f306d;
                if (C0533b.f295i < C0533b.f296j) {
                    i = 1;
                }
                int unused2 = bVar.f298l = i;
                ALog.m328i("awcn.BandWidthSampler", "NetworkSpeed notification!", (String) null, "Send Network quality notification.");
            }
            C0533b.f290d = 0;
            C0533b.f291e = 0;
            C0533b.f287a = 0;
        }
    }
}
