package com.igexin.push.extension.distribution.gbd.p069a.p072c;

import android.os.Process;
import com.igexin.push.extension.distribution.gbd.p077c.C1489b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.c.d */
class C1464d extends Thread {

    /* renamed from: a */
    int f2551a;

    /* renamed from: b */
    final /* synthetic */ C1463c f2552b;

    public C1464d(C1463c cVar, int i) {
        this.f2552b = cVar;
        this.f2551a = i;
    }

    public void run() {
        synchronized (this.f2552b.f2546n) {
            try {
                Process.setThreadPriority(10);
                this.f2552b.f2548p.f2557a = C1465e.SCAN_END;
                this.f2552b.f2548p.mo15020a(this.f2551a);
                Thread.sleep((long) C1489b.f2723c);
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
    }
}
