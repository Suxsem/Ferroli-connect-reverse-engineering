package com.igexin.push.extension.distribution.basic.p066h;

/* renamed from: com.igexin.push.extension.distribution.basic.h.b */
public abstract class C1431b {

    /* renamed from: a */
    protected long f2482a = 0;

    /* renamed from: b */
    protected long f2483b = 0;

    /* renamed from: a */
    public abstract void mo14983a();

    /* renamed from: a */
    public void mo14984a(long j) {
        this.f2482a = j;
    }

    /* renamed from: b */
    public boolean mo14985b() {
        return System.currentTimeMillis() - this.f2482a > this.f2483b;
    }
}
