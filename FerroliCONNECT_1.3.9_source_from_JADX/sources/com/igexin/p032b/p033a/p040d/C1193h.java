package com.igexin.p032b.p033a.p040d;

import com.igexin.p032b.p033a.p039c.C1179b;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.igexin.b.a.d.h */
final class C1193h implements Runnable {

    /* renamed from: a */
    final BlockingQueue<C1190e> f1686a = new LinkedBlockingQueue();

    /* renamed from: b */
    C1190e f1687b;

    /* renamed from: c */
    C1190e f1688c;

    /* renamed from: d */
    volatile int f1689d;

    /* renamed from: e */
    final /* synthetic */ C1192g f1690e;

    public C1193h(C1192g gVar, C1190e eVar) {
        this.f1690e = gVar;
        this.f1687b = eVar;
    }

    /* renamed from: a */
    public final void mo14334a() {
        this.f1686a.clear();
        this.f1688c = null;
    }

    /* renamed from: a */
    public final void mo14335a(C1190e eVar) {
        if (this.f1689d == 0) {
            this.f1689d = eVar.f1660z;
        }
        C1190e eVar2 = eVar;
        boolean z = true;
        while (z) {
            try {
                eVar2.mo14232b_();
                eVar2.mo14301g();
                eVar2.mo14302g_();
                if (!eVar2.f1654t) {
                    eVar2.mo14300c();
                }
                if (!eVar2.f1646k && eVar2.f1649o && eVar2.f1655u != 0) {
                }
            } catch (Exception e) {
                C1179b.m1354a("TaskService" + e.toString());
                eVar2.f1654t = true;
                eVar2.f1636B = e;
                eVar2.mo14310u();
                eVar2.mo14305p();
                this.f1690e.f1685i.mo14319a((Object) eVar2);
                this.f1690e.f1685i.mo14323f();
                if (!eVar2.f1654t) {
                    eVar2.mo14300c();
                }
                if (!eVar2.f1646k && eVar2.f1649o && eVar2.f1655u != 0) {
                }
            } catch (Throwable th) {
                if (!eVar2.f1654t) {
                    eVar2.mo14300c();
                }
                if (eVar2.f1646k || !eVar2.f1649o || eVar2.f1655u == 0) {
                    throw th;
                }
            }
            z = false;
            eVar2 = null;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final C1190e mo14336b() {
        ReentrantLock reentrantLock;
        while (this.f1689d != 0) {
            try {
                C1190e poll = this.f1686a.poll(this.f1690e.f1681e, TimeUnit.NANOSECONDS);
                if (poll != null) {
                    return poll;
                }
                if (this.f1686a.isEmpty()) {
                    reentrantLock = this.f1690e.f1679c;
                    reentrantLock.lock();
                    if (!this.f1686a.isEmpty()) {
                        reentrantLock.unlock();
                    } else {
                        this.f1690e.f1678b.remove(Integer.valueOf(this.f1689d));
                        this.f1688c.mo14222e();
                        this.f1689d = 0;
                        reentrantLock.unlock();
                        return null;
                    }
                } else {
                    continue;
                }
            } catch (InterruptedException unused) {
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }
        return null;
    }

    public final void run() {
        boolean z = true;
        while (z) {
            try {
                C1190e eVar = this.f1687b;
                this.f1687b = null;
                while (true) {
                    if (eVar == null) {
                        eVar = mo14336b();
                        if (eVar == null && (eVar = this.f1690e.mo14326a()) == null) {
                            break;
                        }
                    }
                    this.f1688c = null;
                    mo14335a(eVar);
                    this.f1688c = eVar;
                    eVar = null;
                }
                z = this.f1690e.mo14328a(this);
                if (z) {
                }
            } catch (Exception e) {
                C1179b.m1354a("TaskService|Worker|run()|error" + e.toString());
                z = this.f1690e.mo14328a(this);
                if (z) {
                }
            } catch (Throwable th) {
                if (!this.f1690e.mo14328a(this)) {
                    mo14334a();
                }
                throw th;
            }
            mo14334a();
        }
    }
}
