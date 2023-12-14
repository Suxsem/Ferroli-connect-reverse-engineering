package com.igexin.push.extension.distribution.basic.p066h;

import com.igexin.push.extension.distribution.basic.p061c.C1413c;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;

/* renamed from: com.igexin.push.extension.distribution.basic.h.a */
public class C1430a extends C1431b {
    public C1430a() {
        mo14984a(System.currentTimeMillis());
    }

    /* renamed from: a */
    public void mo14983a() {
        C1413c.m2411a().mo14945e();
    }

    /* renamed from: a */
    public void mo14984a(long j) {
        this.f2482a = j;
    }

    /* renamed from: b */
    public boolean mo14985b() {
        return System.currentTimeMillis() - this.f2482a > ((long) (C1416f.f2427e * 1000));
    }
}
