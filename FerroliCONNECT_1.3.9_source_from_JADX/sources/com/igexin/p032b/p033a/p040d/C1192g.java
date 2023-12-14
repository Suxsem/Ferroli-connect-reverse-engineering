package com.igexin.p032b.p033a.p040d;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.igexin.b.a.d.g */
final class C1192g {

    /* renamed from: a */
    final BlockingQueue<C1190e> f1677a = new SynchronousQueue();

    /* renamed from: b */
    final HashMap<Integer, C1193h> f1678b = new HashMap<>();

    /* renamed from: c */
    final ReentrantLock f1679c = new ReentrantLock();

    /* renamed from: d */
    ThreadFactory f1680d = new C1194i(this);

    /* renamed from: e */
    volatile long f1681e = TimeUnit.SECONDS.toNanos(60);

    /* renamed from: f */
    volatile int f1682f = 0;

    /* renamed from: g */
    volatile int f1683g;

    /* renamed from: h */
    volatile int f1684h = Integer.MAX_VALUE;

    /* renamed from: i */
    final /* synthetic */ C1191f f1685i;

    public C1192g(C1191f fVar) {
        this.f1685i = fVar;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:0|1|(1:3)(1:5)|4|(2:12|7)(1:8)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:0:0x0000 */
    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:9:0x0023, LOOP_START, MTH_ENTER_BLOCK, SYNTHETIC, Splitter:B:0:0x0000] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.igexin.p032b.p033a.p040d.C1190e mo14326a() {
        /*
            r4 = this;
        L_0x0000:
            int r0 = r4.f1683g     // Catch:{ InterruptedException -> 0x0000 }
            int r1 = r4.f1682f     // Catch:{ InterruptedException -> 0x0000 }
            if (r0 <= r1) goto L_0x0013
            java.util.concurrent.BlockingQueue<com.igexin.b.a.d.e> r0 = r4.f1677a     // Catch:{ InterruptedException -> 0x0000 }
            long r1 = r4.f1681e     // Catch:{ InterruptedException -> 0x0000 }
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x0000 }
            java.lang.Object r0 = r0.poll(r1, r3)     // Catch:{ InterruptedException -> 0x0000 }
        L_0x0010:
            com.igexin.b.a.d.e r0 = (com.igexin.p032b.p033a.p040d.C1190e) r0     // Catch:{ InterruptedException -> 0x0000 }
            goto L_0x001a
        L_0x0013:
            java.util.concurrent.BlockingQueue<com.igexin.b.a.d.e> r0 = r4.f1677a     // Catch:{ InterruptedException -> 0x0000 }
            java.lang.Object r0 = r0.take()     // Catch:{ InterruptedException -> 0x0000 }
            goto L_0x0010
        L_0x001a:
            if (r0 == 0) goto L_0x001d
            return r0
        L_0x001d:
            java.util.concurrent.BlockingQueue<com.igexin.b.a.d.e> r0 = r4.f1677a     // Catch:{ InterruptedException -> 0x0000 }
            boolean r0 = r0.isEmpty()     // Catch:{ InterruptedException -> 0x0000 }
            if (r0 == 0) goto L_0x0000
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p040d.C1192g.mo14326a():com.igexin.b.a.d.e");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo14327a(C1190e eVar) {
        if (eVar != null) {
            if (eVar.f1660z != 0) {
                ReentrantLock reentrantLock = this.f1679c;
                reentrantLock.lock();
                try {
                    C1193h hVar = this.f1678b.get(Integer.valueOf(eVar.f1660z));
                    if (hVar != null) {
                        hVar.f1686a.offer(eVar);
                        return;
                    }
                    reentrantLock.unlock();
                } finally {
                    reentrantLock.unlock();
                }
            }
            mo14329b(eVar);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo14328a(C1193h hVar) {
        ReentrantLock reentrantLock = this.f1679c;
        reentrantLock.lock();
        try {
            int i = this.f1683g - 1;
            this.f1683g = i;
            if (i == 0 && !this.f1677a.isEmpty()) {
                Thread f = mo14333f((C1190e) null);
                if (f != null) {
                    f.start();
                }
            } else if (!hVar.f1686a.isEmpty()) {
                reentrantLock.unlock();
                return true;
            }
            this.f1678b.remove(Integer.valueOf(hVar.f1689d));
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo14329b(C1190e eVar) {
        if (this.f1683g < this.f1682f && mo14330c(eVar)) {
            return;
        }
        if (!this.f1677a.offer(eVar)) {
            if (!mo14331d(eVar)) {
            }
        } else if (this.f1683g == 0) {
            mo14332e(eVar);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final boolean mo14330c(C1190e eVar) {
        ReentrantLock reentrantLock = this.f1679c;
        reentrantLock.lock();
        try {
            Thread f = this.f1683g < this.f1682f ? mo14333f(eVar) : null;
            if (f == null) {
                return false;
            }
            f.start();
            return true;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final boolean mo14331d(C1190e eVar) {
        ReentrantLock reentrantLock = this.f1679c;
        reentrantLock.lock();
        try {
            Thread f = this.f1683g < this.f1684h ? mo14333f(eVar) : null;
            if (f == null) {
                return false;
            }
            f.start();
            return true;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public final void mo14332e(C1190e eVar) {
        ReentrantLock reentrantLock = this.f1679c;
        reentrantLock.lock();
        try {
            Thread thread = null;
            if (this.f1683g < Math.max(this.f1682f, 1) && !this.f1677a.isEmpty()) {
                thread = mo14333f((C1190e) null);
            }
            if (thread != null) {
                thread.start();
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: f */
    public final Thread mo14333f(C1190e eVar) {
        C1193h hVar = new C1193h(this, eVar);
        if (!(eVar == null || eVar.f1660z == 0)) {
            this.f1678b.put(Integer.valueOf(eVar.f1660z), hVar);
        }
        Thread newThread = this.f1680d.newThread(hVar);
        if (newThread != null) {
            this.f1683g++;
        }
        return newThread;
    }
}
