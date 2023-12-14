package com.igexin.p032b.p033a.p040d;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.igexin.b.a.d.i */
final class C1194i implements ThreadFactory {

    /* renamed from: a */
    final AtomicInteger f1691a = new AtomicInteger(0);

    /* renamed from: b */
    final /* synthetic */ C1192g f1692b;

    public C1194i(C1192g gVar) {
        this.f1692b = gVar;
    }

    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "TS-pool-" + this.f1691a.incrementAndGet());
    }
}
