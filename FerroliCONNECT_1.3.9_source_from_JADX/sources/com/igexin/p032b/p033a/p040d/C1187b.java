package com.igexin.p032b.p033a.p040d;

import com.igexin.p032b.p033a.p040d.p041a.C1186f;
import java.util.concurrent.TimeUnit;

/* renamed from: com.igexin.b.a.d.b */
public abstract class C1187b implements C1186f {

    /* renamed from: a */
    protected boolean f1621a = true;

    /* renamed from: a */
    public void mo14281a() {
        this.f1621a = false;
    }

    /* renamed from: a */
    public boolean mo14279a(long j, C1190e eVar) {
        return TimeUnit.SECONDS.toMillis((long) eVar.f1659y) < j - eVar.f1657w;
    }

    /* renamed from: b */
    public long mo14280b(long j, C1190e eVar) {
        return (TimeUnit.SECONDS.toMillis((long) eVar.f1659y) + eVar.f1657w) - j;
    }
}
