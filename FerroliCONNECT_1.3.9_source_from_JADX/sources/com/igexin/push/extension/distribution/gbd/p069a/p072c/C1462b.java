package com.igexin.push.extension.distribution.gbd.p069a.p072c;

import android.location.GpsStatus;
import android.location.Location;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p078d.C1494d;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.c.b */
class C1462b implements GpsStatus.Listener {

    /* renamed from: a */
    final /* synthetic */ C1461a f2532a;

    C1462b(C1461a aVar) {
        this.f2532a = aVar;
    }

    public void onGpsStatusChanged(int i) {
        Location a;
        if (i == 1) {
            long unused = this.f2532a.f2528e = System.currentTimeMillis();
        } else if (i != 2 && i != 3 && i == 4) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.f2532a.f2528e > C1488a.f2712r * 1000) {
                    long unused2 = this.f2532a.f2528e = currentTimeMillis;
                    int unused3 = this.f2532a.f2530g = 0;
                }
                if (this.f2532a.f2530g < 3 && currentTimeMillis - this.f2532a.f2529f >= C1488a.f2714t * 1000) {
                    C1461a.m2632d(this.f2532a);
                    long unused4 = this.f2532a.f2529f = currentTimeMillis;
                    if (C1494d.m2778a().mo15091e() && (a = this.f2532a.mo15010a(true)) != null && "gps".equals(a.getProvider())) {
                        if (this.f2532a.f2524a == null || a.distanceTo(this.f2532a.f2524a) >= ((float) C1488a.f2713s)) {
                            this.f2532a.f2526c.mo15015a(a);
                            this.f2532a.f2524a = new Location(a);
                        }
                    }
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
    }
}
