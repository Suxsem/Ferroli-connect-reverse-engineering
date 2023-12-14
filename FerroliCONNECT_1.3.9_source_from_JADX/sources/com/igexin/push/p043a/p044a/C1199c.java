package com.igexin.push.p043a.p044a;

import com.igexin.push.p088g.p090b.C1571d;

/* renamed from: com.igexin.push.a.a.c */
public class C1199c implements C1571d {

    /* renamed from: c */
    private static C1199c f1700c;

    /* renamed from: a */
    private long f1701a = 0;

    /* renamed from: b */
    private long f1702b = 0;

    /* renamed from: d */
    private boolean f1703d = false;

    private C1199c() {
    }

    /* renamed from: c */
    public static C1199c m1446c() {
        if (f1700c == null) {
            f1700c = new C1199c();
        }
        return f1700c;
    }

    /* renamed from: a */
    public void mo14348a() {
        mo14351d();
    }

    /* renamed from: a */
    public void mo14349a(long j) {
        this.f1701a = j;
    }

    /* renamed from: b */
    public boolean mo14350b() {
        return System.currentTimeMillis() - this.f1701a > this.f1702b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0055, code lost:
        if (r2.getTimeInMillis() < r0) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x007a, code lost:
        if (r2.getTimeInMillis() < r0) goto L_0x007c;
     */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14351d() {
        /*
            r12 = this;
            r0 = 3600000(0x36ee80, double:1.7786363E-317)
            r12.f1702b = r0
            long r0 = java.lang.System.currentTimeMillis()
            int r2 = com.igexin.push.config.C1234k.f1841b
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0087
            java.util.Calendar r2 = java.util.Calendar.getInstance()
            boolean r5 = com.igexin.push.util.C1576a.m3200a((long) r0)
            r6 = 5
            r7 = 13
            r8 = 12
            r9 = 11
            if (r5 == 0) goto L_0x0058
            boolean r5 = r12.f1703d
            if (r5 != 0) goto L_0x0031
            r12.f1703d = r3
            com.igexin.push.core.e r5 = com.igexin.push.core.C1340e.m2032a()
            com.igexin.push.f.a r5 = r5.mo14710g()
            r5.mo15193c()
        L_0x0031:
            int r5 = com.igexin.push.config.C1234k.f1840a
            int r10 = com.igexin.push.config.C1234k.f1841b
            int r5 = r5 + r10
            r10 = 24
            if (r5 <= r10) goto L_0x0041
            int r5 = com.igexin.push.config.C1234k.f1840a
            int r11 = com.igexin.push.config.C1234k.f1841b
            int r5 = r5 + r11
            int r5 = r5 - r10
            goto L_0x0046
        L_0x0041:
            int r5 = com.igexin.push.config.C1234k.f1840a
            int r10 = com.igexin.push.config.C1234k.f1841b
            int r5 = r5 + r10
        L_0x0046:
            r2.set(r9, r5)
            r2.set(r8, r4)
            r2.set(r7, r4)
            long r4 = r2.getTimeInMillis()
            int r7 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r7 >= 0) goto L_0x007f
            goto L_0x007c
        L_0x0058:
            boolean r5 = r12.f1703d
            if (r5 == 0) goto L_0x0069
            r12.f1703d = r4
            com.igexin.push.core.e r5 = com.igexin.push.core.C1340e.m2032a()
            com.igexin.push.f.a r5 = r5.mo14710g()
            r5.mo15192b()
        L_0x0069:
            int r5 = com.igexin.push.config.C1234k.f1840a
            r2.set(r9, r5)
            r2.set(r8, r4)
            r2.set(r7, r4)
            long r4 = r2.getTimeInMillis()
            int r7 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r7 >= 0) goto L_0x007f
        L_0x007c:
            r2.add(r6, r3)
        L_0x007f:
            long r4 = r2.getTimeInMillis()
            long r4 = r4 - r0
            r12.f1702b = r4
            goto L_0x0098
        L_0x0087:
            boolean r2 = r12.f1703d
            if (r2 == 0) goto L_0x0098
            r12.f1703d = r4
            com.igexin.push.core.e r2 = com.igexin.push.core.C1340e.m2032a()
            com.igexin.push.f.a r2 = r2.mo14710g()
            r2.mo15192b()
        L_0x0098:
            long r4 = com.igexin.push.config.C1234k.f1842c
            long r6 = r12.f1702b
            long r6 = r6 + r0
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x00b7
            long r4 = com.igexin.push.config.C1234k.f1842c
            long r4 = r4 - r0
            r12.f1702b = r4
            boolean r0 = r12.f1703d
            if (r0 != 0) goto L_0x00b7
            r12.f1703d = r3
            com.igexin.push.core.e r0 = com.igexin.push.core.C1340e.m2032a()
            com.igexin.push.f.a r0 = r0.mo14710g()
            r0.mo15193c()
        L_0x00b7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p043a.p044a.C1199c.mo14351d():void");
    }
}
