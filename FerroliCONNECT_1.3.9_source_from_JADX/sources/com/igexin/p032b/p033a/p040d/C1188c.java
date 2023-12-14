package com.igexin.p032b.p033a.p040d;

import com.igexin.p032b.p033a.p040d.p041a.C1185e;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: com.igexin.b.a.d.c */
public class C1188c {

    /* renamed from: a */
    private final ConcurrentLinkedQueue<C1185e> f1622a = new ConcurrentLinkedQueue<>();

    /* renamed from: b */
    private final ConcurrentLinkedQueue<C1185e> f1623b = new ConcurrentLinkedQueue<>();

    /* renamed from: c */
    private ConcurrentLinkedQueue<C1185e> f1624c = this.f1622a;

    /* renamed from: a */
    public synchronized void mo14282a() {
        this.f1624c = this.f1622a;
    }

    /* renamed from: a */
    public synchronized void mo14283a(C1185e eVar) {
        this.f1624c.offer(eVar);
    }

    /* renamed from: b */
    public synchronized void mo14284b() {
        this.f1624c = this.f1623b;
        this.f1624c.addAll(this.f1622a);
        this.f1622a.clear();
    }

    /* renamed from: c */
    public synchronized boolean mo14285c() {
        return this.f1624c.isEmpty();
    }

    /* renamed from: d */
    public synchronized C1185e mo14286d() {
        return this.f1624c.poll();
    }
}
