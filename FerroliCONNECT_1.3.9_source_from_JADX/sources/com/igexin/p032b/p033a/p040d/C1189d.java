package com.igexin.p032b.p033a.p040d;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.igexin.b.a.d.d */
public class C1189d<E extends C1190e> {

    /* renamed from: i */
    static final /* synthetic */ boolean f1625i = (!C1189d.class.desiredAssertionStatus());

    /* renamed from: a */
    public String f1626a = getClass().getName();

    /* renamed from: b */
    final transient ReentrantLock f1627b = new ReentrantLock();

    /* renamed from: c */
    final transient Condition f1628c = this.f1627b.newCondition();

    /* renamed from: d */
    final TreeSet<E> f1629d;

    /* renamed from: e */
    final AtomicInteger f1630e = new AtomicInteger(0);

    /* renamed from: f */
    int f1631f;

    /* renamed from: g */
    C1191f f1632g;

    /* renamed from: h */
    public final AtomicLong f1633h = new AtomicLong(-1);

    public C1189d(Comparator<? super E> comparator, C1191f fVar) {
        this.f1629d = new TreeSet<>(comparator);
        this.f1632g = fVar;
    }

    /* renamed from: e */
    private E m1381e() {
        E a = mo14288a();
        if (a != null && this.f1629d.remove(a)) {
            return a;
        }
        return null;
    }

    /* renamed from: a */
    public final int mo14287a(E e, long j, TimeUnit timeUnit) {
        ReentrantLock reentrantLock = this.f1627b;
        reentrantLock.lock();
        try {
            if (!this.f1629d.contains(e)) {
                return -1;
            }
            this.f1629d.remove(e);
            e.f1655u = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(j, timeUnit);
            int i = mo14289a(e) ? 1 : -2;
            reentrantLock.unlock();
            return i;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public E mo14288a() {
        try {
            return (C1190e) this.f1629d.first();
        } catch (NoSuchElementException unused) {
            return null;
        }
    }

    /* renamed from: a */
    public final boolean mo14289a(E e) {
        if (e == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.f1627b;
        reentrantLock.lock();
        try {
            C1190e a = mo14288a();
            int i = this.f1631f + 1;
            this.f1631f = i;
            e.f1656v = i;
            if (!this.f1629d.add(e)) {
                e.f1656v--;
                return false;
            }
            e.mo14303n();
            if (a == null || this.f1629d.comparator().compare(e, a) < 0) {
                this.f1628c.signalAll();
            }
            reentrantLock.unlock();
            return true;
        } catch (Exception unused) {
            C1179b.m1354a("ScheduleQueue|offer|error");
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* renamed from: a */
    public final boolean mo14290a(Class cls) {
        if (cls == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.f1627b;
        reentrantLock.lock();
        try {
            ArrayList arrayList = new ArrayList();
            Iterator<E> it = this.f1629d.iterator();
            while (it.hasNext()) {
                C1190e eVar = (C1190e) it.next();
                if (eVar.getClass() == cls) {
                    arrayList.add(eVar);
                }
            }
            this.f1629d.removeAll(arrayList);
            return true;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final boolean mo14291b() {
        ReentrantLock reentrantLock = this.f1627b;
        reentrantLock.lock();
        try {
            return this.f1629d.isEmpty();
        } finally {
            reentrantLock.unlock();
        }
    }

    /* renamed from: c */
    public final E mo14292c() {
        ReentrantLock reentrantLock = this.f1627b;
        reentrantLock.lockInterruptibly();
        while (true) {
            try {
                C1190e a = mo14288a();
                boolean z = false;
                if (a == null) {
                    this.f1630e.set(1);
                    this.f1631f = 0;
                    this.f1628c.await();
                } else {
                    long a2 = a.mo14295a(TimeUnit.NANOSECONDS);
                    if (a.f1646k || a.f1647m) {
                        z = true;
                    }
                    if (a2 <= 0) {
                        break;
                    } else if (z) {
                        break;
                    } else {
                        this.f1633h.set(a.f1655u);
                        C1179b.m1354a("schedule take|needAlarm = " + this.f1632g.f1676t + "|" + a.getClass().getName() + "@" + a.hashCode());
                        if (this.f1632g.f1676t) {
                            this.f1632g.mo14312a(a.f1655u);
                        }
                        this.f1628c.awaitNanos(a2);
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        E e = m1381e();
        if (!f1625i) {
            if (e == null) {
                throw new AssertionError();
            }
        }
        if (!mo14291b()) {
            this.f1628c.signalAll();
        }
        this.f1633h.set(-1);
        return e;
    }

    /* renamed from: d */
    public final void mo14293d() {
        this.f1629d.clear();
    }
}
