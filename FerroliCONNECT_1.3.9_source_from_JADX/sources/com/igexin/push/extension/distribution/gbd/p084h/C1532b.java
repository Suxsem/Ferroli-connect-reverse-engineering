package com.igexin.push.extension.distribution.gbd.p084h;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.b */
public abstract class C1532b {

    /* renamed from: a */
    protected long f2934a = 0;

    /* renamed from: b */
    protected long f2935b = 0;

    /* renamed from: a */
    public abstract void mo15163a();

    /* renamed from: a */
    public void mo15164a(long j) {
        this.f2934a = j;
    }

    /* renamed from: b */
    public boolean mo15165b() {
        return System.currentTimeMillis() - this.f2934a > this.f2935b;
    }

    /* renamed from: c */
    public abstract boolean mo15166c();
}
