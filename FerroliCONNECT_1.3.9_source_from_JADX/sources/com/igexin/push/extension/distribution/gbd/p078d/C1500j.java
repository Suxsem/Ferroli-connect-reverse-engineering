package com.igexin.push.extension.distribution.gbd.p078d;

import android.os.Looper;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.d.j */
public class C1500j extends Thread {
    public void run() {
        Thread.currentThread().setName("GBD-Thread");
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        try {
            if (C1490c.f2749c == null) {
                C1490c.f2749c = new C1491a();
            }
            C1494d.m2778a().mo15088b();
        } catch (Throwable th) {
            C1540h.m2996a(th);
            C1540h.m2997b("GBDThread", th.toString());
        }
        Looper.loop();
    }
}
