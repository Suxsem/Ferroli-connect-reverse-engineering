package com.igexin.push.core;

import com.igexin.push.config.C1234k;

/* renamed from: com.igexin.push.core.j */
public class C1347j {

    /* renamed from: d */
    private static C1347j f2192d;

    /* renamed from: a */
    public long f2193a = 240000;

    /* renamed from: b */
    private C1350m f2194b = C1350m.DETECT;

    /* renamed from: c */
    private long f2195c = 0;

    private C1347j() {
    }

    /* renamed from: a */
    public static C1347j m2085a() {
        if (f2192d == null) {
            f2192d = new C1347j();
        }
        return f2192d;
    }

    /* renamed from: b */
    private void m2086b(C1349l lVar) {
        C1350m mVar;
        int i = C1348k.f2197b[lVar.ordinal()];
        if (i == 1) {
            mo14743a(Math.min(this.f2193a + 60000, 420000));
        } else if (i == 2 || i == 3) {
            this.f2195c++;
            if (this.f2195c >= 2) {
                mo14743a(Math.max(this.f2193a - 60000, 240000));
                mVar = C1350m.STABLE;
                mo14745a(mVar);
            }
            return;
        } else if (i == 4) {
            mo14743a(240000);
        } else {
            return;
        }
        mVar = C1350m.DETECT;
        mo14745a(mVar);
    }

    /* renamed from: c */
    private void m2087c(C1349l lVar) {
        C1350m mVar;
        int i = C1348k.f2197b[lVar.ordinal()];
        if (i == 1) {
            mVar = C1350m.STABLE;
        } else if (i == 2 || i == 3) {
            mo14743a(Math.max(this.f2193a - 60000, 240000));
            this.f2195c++;
            if (this.f2195c >= 2) {
                mo14743a(240000);
                mVar = C1350m.PENDING;
            } else {
                return;
            }
        } else if (i == 4) {
            mo14743a(240000);
            mVar = C1350m.DETECT;
        } else {
            return;
        }
        mo14745a(mVar);
    }

    /* renamed from: d */
    private void m2088d(C1349l lVar) {
        C1350m mVar;
        int i = C1348k.f2197b[lVar.ordinal()];
        if (i != 1) {
            if (i == 2 || i == 3) {
                mVar = C1350m.PENDING;
                mo14745a(mVar);
            } else if (i != 4) {
                return;
            }
        }
        mo14743a(240000);
        mVar = C1350m.DETECT;
        mo14745a(mVar);
    }

    /* renamed from: a */
    public void mo14743a(long j) {
        this.f2193a = j;
    }

    /* renamed from: a */
    public void mo14744a(C1349l lVar) {
        int i = C1348k.f2196a[this.f2194b.ordinal()];
        if (i == 1) {
            m2086b(lVar);
        } else if (i == 2) {
            m2087c(lVar);
        } else if (i == 3) {
            m2088d(lVar);
        }
    }

    /* renamed from: a */
    public void mo14745a(C1350m mVar) {
        this.f2194b = mVar;
        this.f2195c = 0;
    }

    /* renamed from: b */
    public long mo14746b() {
        long j = this.f2193a;
        if (C1234k.f1843d > 0) {
            j = (long) (C1234k.f1843d * 1000);
        }
        if (C1343f.f2171h && C1343f.f2175l && C1340e.m2032a().mo14710g().mo15191a()) {
            return j;
        }
        return 3600000;
    }
}
