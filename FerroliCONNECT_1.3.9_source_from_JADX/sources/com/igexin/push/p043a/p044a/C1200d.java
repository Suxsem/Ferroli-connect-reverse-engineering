package com.igexin.push.p043a.p044a;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.push.config.C1234k;
import com.igexin.push.p088g.p090b.C1571d;

/* renamed from: com.igexin.push.a.a.d */
public class C1200d implements C1571d {

    /* renamed from: a */
    private static final String f1704a = "com.igexin.push.a.a.d";

    /* renamed from: b */
    private long f1705b = 0;

    /* renamed from: c */
    private long f1706c = 0;

    /* renamed from: c */
    private void m1451c() {
        C1174c.m1310b().mo14317a(new C1201e(this), false, true);
    }

    /* renamed from: a */
    public void mo14348a() {
        if (C1234k.f1860u && System.currentTimeMillis() - this.f1706c >= 3600000) {
            m1451c();
            this.f1706c = System.currentTimeMillis();
        }
    }

    /* renamed from: a */
    public void mo14349a(long j) {
        this.f1705b = j;
    }

    /* renamed from: b */
    public boolean mo14350b() {
        return System.currentTimeMillis() - this.f1705b > 1800000;
    }
}
