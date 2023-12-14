package com.igexin.push.p088g.p090b;

import android.os.SystemClock;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1353p;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.util.C1576a;
import java.util.concurrent.TimeUnit;

/* renamed from: com.igexin.push.g.b.g */
public class C1574g extends C1575h {

    /* renamed from: a */
    private static C1574g f2999a;

    /* renamed from: b */
    private long f3000b = System.currentTimeMillis();

    /* renamed from: c */
    private long f3001c = SystemClock.elapsedRealtime();

    private C1574g() {
        super(3600000);
        this.f1649o = true;
    }

    /* renamed from: i */
    public static synchronized C1574g m3187i() {
        C1574g gVar;
        synchronized (C1574g.class) {
            if (f2999a == null) {
                f2999a = new C1574g();
            }
            gVar = f2999a;
        }
        return gVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        long j;
        C1257f.m1711a().mo14497k();
        boolean a = C1576a.m3200a(System.currentTimeMillis());
        boolean b = C1576a.m3212b();
        C1343f.f2171h = C1576a.m3221g();
        C1179b.m1354a("RTTask|networkAvailable = " + C1343f.f2171h + ",sdkOnline = " + C1343f.f2175l + ", " + "sdkOn= " + C1343f.f2172i + ", pushOn =" + C1343f.f2173j + ", isSilentTime= " + a + ", blockEndTime= " + b);
        if (!C1343f.f2171h || !C1343f.f2172i || !C1343f.f2173j || C1343f.f2175l || a || !b) {
            C1179b.m1354a("RTTask reconnect timer task stop, connect interval= 1h #######");
            j = 3600000;
            C1343f.f2112D = 3600000;
        } else if (C1576a.m3222h() || !TextUtils.isEmpty(C1343f.f2182s)) {
            C1179b.m1354a("RTTask reconnect timer task isOnline = false, try login...");
            if (System.currentTimeMillis() - this.f3000b < 2500) {
                C1343f.f2178o++;
            }
            if (C1343f.f2178o > 30 && ((double) Math.abs(SystemClock.elapsedRealtime() - this.f3001c)) < 72000.0d) {
                C1312h.m1937a().mo14685e();
            }
            this.f3000b = System.currentTimeMillis();
            C1353p.m2098a().mo14749b();
            j = 1800000;
        } else {
            mo14294a(900000, TimeUnit.MILLISECONDS);
            C1179b.m1354a("RTTask|date is error, set connect interval = 15min");
            return;
        }
        mo14294a(j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: a */
    public void mo15212a(long j) {
        this.f3001c = j;
    }

    /* renamed from: b */
    public final int mo14231b() {
        return -2147483641;
    }

    /* renamed from: c */
    public void mo14300c() {
        super.mo14300c();
    }

    /* renamed from: d */
    public void mo14221d() {
    }

    /* renamed from: j */
    public void mo15213j() {
        long j = C1343f.f2112D;
        C1179b.m1354a("RTTask|refreshDelayTime, delay = " + j);
        mo14294a(j, TimeUnit.MILLISECONDS);
    }
}
