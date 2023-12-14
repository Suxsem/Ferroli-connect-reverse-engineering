package com.igexin.push.p054e;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.util.C1576a;
import com.igexin.sdk.GTIntentService;

/* renamed from: com.igexin.push.e.f */
public class C1391f implements C1394i {
    /* renamed from: a */
    public long mo14844a() {
        long j;
        long j2;
        long j3;
        boolean a = C1576a.m3200a(System.currentTimeMillis());
        boolean b = C1576a.m3212b();
        C1343f.f2171h = C1576a.m3221g();
        C1179b.m1354a("NormalModel|isSdkOn = " + C1343f.f2172i + " isPushOn = " + C1343f.f2173j + " checkIsSilentTime = " + a + " isBlockEndTime = " + b + " isNetworkAvailable = " + C1343f.f2171h);
        if (!C1343f.f2171h || !C1343f.f2172i || !C1343f.f2173j || a || !b) {
            C1179b.m1354a("NormalModel|reconnect stop, interval= 1h ++++");
            return 3600000;
        }
        if (C1343f.f2112D <= 0) {
            j3 = 100;
        } else {
            if (C1343f.f2112D <= 10000) {
                j = C1343f.f2112D;
                j2 = 500;
            } else if (C1343f.f2112D <= GTIntentService.WAIT_TIME) {
                j = C1343f.f2112D;
                j2 = 1500;
            } else {
                j = C1343f.f2112D;
                j2 = 120000;
            }
            j3 = j + j2;
        }
        C1343f.f2112D = j3;
        if (C1343f.f2112D > 3600000) {
            C1343f.f2112D = 3600000;
        }
        long j4 = C1343f.f2112D;
        C1179b.m1354a("NormalModel|after add auto reconnect delay time = " + j4);
        return j4;
    }
}
