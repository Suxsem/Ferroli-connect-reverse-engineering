package com.igexin.push.p088g.p090b;

import com.igexin.p032b.p033a.p040d.C1190e;
import java.util.concurrent.TimeUnit;

/* renamed from: com.igexin.push.g.b.h */
public abstract class C1575h extends C1190e {

    /* renamed from: d */
    long f3002d;

    public C1575h(long j) {
        this(0, j);
    }

    public C1575h(long j, long j2) {
        super(5);
        this.f3002d = j > 0 ? j2 + (j - System.currentTimeMillis()) : j2;
        mo14294a(this.f3002d, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo14406a();

    /* renamed from: b_ */
    public final void mo14232b_() {
        super.mo14232b_();
        mo14406a();
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo14222e() {
    }
}
