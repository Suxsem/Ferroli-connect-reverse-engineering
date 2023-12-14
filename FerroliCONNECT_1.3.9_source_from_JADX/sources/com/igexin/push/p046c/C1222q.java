package com.igexin.push.p046c;

import java.util.concurrent.Callable;

/* renamed from: com.igexin.push.c.q */
class C1222q implements Callable<C1215j> {

    /* renamed from: a */
    final /* synthetic */ C1221p f1798a;

    C1222q(C1221p pVar) {
        this.f1798a = pVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x013b, code lost:
        if (r4.isClosed() == false) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01b6, code lost:
        if (r4.isClosed() == false) goto L_0x013d;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.igexin.push.p046c.C1215j call() {
        /*
            r12 = this;
            com.igexin.push.c.p r0 = r12.f1798a
            boolean r0 = r0.f1792M
            if (r0 == 0) goto L_0x01c6
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            boolean r0 = r0.isInterrupted()
            if (r0 != 0) goto L_0x01c6
            r0 = 0
            com.igexin.push.c.p r1 = r12.f1798a     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            boolean r1 = r1.f1793N     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            if (r1 == 0) goto L_0x001c
            return r0
        L_0x001c:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            boolean r1 = r1.isInterrupted()     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            if (r1 == 0) goto L_0x0027
            return r0
        L_0x0027:
            java.lang.Class<com.igexin.push.c.o> r1 = com.igexin.push.p046c.C1220o.class
            monitor-enter(r1)     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            com.igexin.push.c.p r2 = r12.f1798a     // Catch:{ all -> 0x0147 }
            com.igexin.push.c.o r2 = r2.f1796i     // Catch:{ all -> 0x0147 }
            if (r2 == 0) goto L_0x0041
            com.igexin.push.c.p r2 = r12.f1798a     // Catch:{ all -> 0x0147 }
            com.igexin.push.c.o r2 = r2.f1796i     // Catch:{ all -> 0x0147 }
            com.igexin.push.c.p r3 = r12.f1798a     // Catch:{ all -> 0x0147 }
            com.igexin.push.c.j r3 = r3.f1794g     // Catch:{ all -> 0x0147 }
            r2.mo14410a(r3)     // Catch:{ all -> 0x0147 }
        L_0x0041:
            monitor-exit(r1)     // Catch:{ all -> 0x0147 }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            com.igexin.push.c.p r3 = r12.f1798a     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            com.igexin.push.c.j r3 = r3.f1794g     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            java.lang.String r3 = r3.mo14389a()     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            java.lang.String[] r3 = com.igexin.p032b.p033a.p035b.C1177f.m1333a((java.lang.String) r3)     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            java.net.Socket r4 = new java.net.Socket     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            r4.<init>()     // Catch:{ Exception -> 0x014e, all -> 0x014a }
            java.net.InetSocketAddress r5 = new java.net.InetSocketAddress     // Catch:{ Exception -> 0x0145 }
            r6 = 1
            r3 = r3[r6]     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.p r6 = r12.f1798a     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.j r6 = r6.f1794g     // Catch:{ Exception -> 0x0145 }
            int r6 = r6.mo14401d()     // Catch:{ Exception -> 0x0145 }
            r5.<init>(r3, r6)     // Catch:{ Exception -> 0x0145 }
            r3 = 10000(0x2710, float:1.4013E-41)
            r4.connect(r5, r3)     // Catch:{ Exception -> 0x0145 }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.p r3 = r12.f1798a     // Catch:{ Exception -> 0x0145 }
            boolean r3 = r3.f1793N     // Catch:{ Exception -> 0x0145 }
            if (r3 == 0) goto L_0x0086
            boolean r1 = r4.isClosed()
            if (r1 != 0) goto L_0x0085
            r4.close()     // Catch:{ Exception -> 0x0085 }
        L_0x0085:
            return r0
        L_0x0086:
            java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0145 }
            boolean r3 = r3.isInterrupted()     // Catch:{ Exception -> 0x0145 }
            if (r3 == 0) goto L_0x009a
            boolean r1 = r4.isClosed()
            if (r1 != 0) goto L_0x0099
            r4.close()     // Catch:{ Exception -> 0x0099 }
        L_0x0099:
            return r0
        L_0x009a:
            com.igexin.push.c.p r0 = r12.f1798a     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.j r6 = r0.f1794g     // Catch:{ Exception -> 0x0145 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0145 }
            r0.<init>()     // Catch:{ Exception -> 0x0145 }
            java.lang.String r3 = "socket://"
            r0.append(r3)     // Catch:{ Exception -> 0x0145 }
            java.net.InetAddress r3 = r4.getInetAddress()     // Catch:{ Exception -> 0x0145 }
            java.lang.String r3 = r3.getHostAddress()     // Catch:{ Exception -> 0x0145 }
            r0.append(r3)     // Catch:{ Exception -> 0x0145 }
            java.lang.String r3 = ":"
            r0.append(r3)     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.p r3 = r12.f1798a     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.j r3 = r3.f1794g     // Catch:{ Exception -> 0x0145 }
            int r3 = r3.mo14401d()     // Catch:{ Exception -> 0x0145 }
            r0.append(r3)     // Catch:{ Exception -> 0x0145 }
            java.lang.String r7 = r0.toString()     // Catch:{ Exception -> 0x0145 }
            long r8 = r10 - r1
            r6.mo14393a(r7, r8, r10)     // Catch:{ Exception -> 0x0145 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0145 }
            r0.<init>()     // Catch:{ Exception -> 0x0145 }
            java.lang.String r1 = com.igexin.push.p046c.C1221p.f1787b     // Catch:{ Exception -> 0x0145 }
            r0.append(r1)     // Catch:{ Exception -> 0x0145 }
            java.lang.String r1 = "|detect "
            r0.append(r1)     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.p r1 = r12.f1798a     // Catch:{ Exception -> 0x0145 }
            java.lang.String r1 = r1.m1580x()     // Catch:{ Exception -> 0x0145 }
            r0.append(r1)     // Catch:{ Exception -> 0x0145 }
            java.lang.String r1 = "ok, time: "
            r0.append(r1)     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.p r1 = r12.f1798a     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.j r1 = r1.f1794g     // Catch:{ Exception -> 0x0145 }
            long r1 = r1.mo14402e()     // Catch:{ Exception -> 0x0145 }
            r0.append(r1)     // Catch:{ Exception -> 0x0145 }
            java.lang.String r1 = " ######"
            r0.append(r1)     // Catch:{ Exception -> 0x0145 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0145 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x0145 }
            java.lang.Class<com.igexin.push.c.o> r0 = com.igexin.push.p046c.C1220o.class
            monitor-enter(r0)     // Catch:{ Exception -> 0x0145 }
            com.igexin.push.c.p r1 = r12.f1798a     // Catch:{ all -> 0x0142 }
            com.igexin.push.c.o r1 = r1.f1796i     // Catch:{ all -> 0x0142 }
            if (r1 == 0) goto L_0x0136
            com.igexin.push.c.p r1 = r12.f1798a     // Catch:{ all -> 0x0142 }
            boolean r1 = r1.f1793N     // Catch:{ all -> 0x0142 }
            if (r1 != 0) goto L_0x0136
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0142 }
            boolean r1 = r1.isInterrupted()     // Catch:{ all -> 0x0142 }
            if (r1 != 0) goto L_0x0136
            com.igexin.push.c.p r1 = r12.f1798a     // Catch:{ all -> 0x0142 }
            com.igexin.push.c.o r1 = r1.f1796i     // Catch:{ all -> 0x0142 }
            com.igexin.push.c.g r2 = com.igexin.push.p046c.C1212g.f1748a     // Catch:{ all -> 0x0142 }
            com.igexin.push.c.p r3 = r12.f1798a     // Catch:{ all -> 0x0142 }
            com.igexin.push.c.j r3 = r3.f1794g     // Catch:{ all -> 0x0142 }
            r1.mo14409a(r2, r3)     // Catch:{ all -> 0x0142 }
        L_0x0136:
            monitor-exit(r0)     // Catch:{ all -> 0x0142 }
            boolean r0 = r4.isClosed()
            if (r0 != 0) goto L_0x01c6
        L_0x013d:
            r4.close()     // Catch:{ Exception -> 0x01c6 }
            goto L_0x01c6
        L_0x0142:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0142 }
            throw r1     // Catch:{ Exception -> 0x0145 }
        L_0x0145:
            r0 = move-exception
            goto L_0x0151
        L_0x0147:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0147 }
            throw r2     // Catch:{ Exception -> 0x014e, all -> 0x014a }
        L_0x014a:
            r1 = move-exception
            r4 = r0
            r0 = r1
            goto L_0x01ba
        L_0x014e:
            r1 = move-exception
            r4 = r0
            r0 = r1
        L_0x0151:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b9 }
            r1.<init>()     // Catch:{ all -> 0x01b9 }
            java.lang.String r2 = com.igexin.push.p046c.C1221p.f1787b     // Catch:{ all -> 0x01b9 }
            r1.append(r2)     // Catch:{ all -> 0x01b9 }
            java.lang.String r2 = "|detect "
            r1.append(r2)     // Catch:{ all -> 0x01b9 }
            com.igexin.push.c.p r2 = r12.f1798a     // Catch:{ all -> 0x01b9 }
            java.lang.String r2 = r2.m1580x()     // Catch:{ all -> 0x01b9 }
            r1.append(r2)     // Catch:{ all -> 0x01b9 }
            java.lang.String r2 = "thread -->"
            r1.append(r2)     // Catch:{ all -> 0x01b9 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01b9 }
            r1.append(r0)     // Catch:{ all -> 0x01b9 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x01b9 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x01b9 }
            com.igexin.push.c.p r0 = r12.f1798a     // Catch:{ all -> 0x01b9 }
            boolean r0 = r0.f1793N     // Catch:{ all -> 0x01b9 }
            if (r0 != 0) goto L_0x01b0
            com.igexin.push.c.p r0 = r12.f1798a     // Catch:{ all -> 0x01b9 }
            com.igexin.push.c.j r0 = r0.f1794g     // Catch:{ all -> 0x01b9 }
            r0.mo14396b()     // Catch:{ all -> 0x01b9 }
            java.lang.Class<com.igexin.push.c.o> r0 = com.igexin.push.p046c.C1220o.class
            monitor-enter(r0)     // Catch:{ all -> 0x01b9 }
            com.igexin.push.c.p r1 = r12.f1798a     // Catch:{ all -> 0x01ad }
            com.igexin.push.c.o r1 = r1.f1796i     // Catch:{ all -> 0x01ad }
            if (r1 == 0) goto L_0x01ab
            com.igexin.push.c.p r1 = r12.f1798a     // Catch:{ all -> 0x01ad }
            com.igexin.push.c.o r1 = r1.f1796i     // Catch:{ all -> 0x01ad }
            com.igexin.push.c.g r2 = com.igexin.push.p046c.C1212g.EXCEPTION     // Catch:{ all -> 0x01ad }
            com.igexin.push.c.p r3 = r12.f1798a     // Catch:{ all -> 0x01ad }
            com.igexin.push.c.j r3 = r3.f1794g     // Catch:{ all -> 0x01ad }
            r1.mo14409a(r2, r3)     // Catch:{ all -> 0x01ad }
        L_0x01ab:
            monitor-exit(r0)     // Catch:{ all -> 0x01ad }
            goto L_0x01b0
        L_0x01ad:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x01ad }
            throw r1     // Catch:{ all -> 0x01b9 }
        L_0x01b0:
            if (r4 == 0) goto L_0x01c6
            boolean r0 = r4.isClosed()
            if (r0 != 0) goto L_0x01c6
            goto L_0x013d
        L_0x01b9:
            r0 = move-exception
        L_0x01ba:
            if (r4 == 0) goto L_0x01c5
            boolean r1 = r4.isClosed()
            if (r1 != 0) goto L_0x01c5
            r4.close()     // Catch:{ Exception -> 0x01c5 }
        L_0x01c5:
            throw r0
        L_0x01c6:
            com.igexin.push.c.p r0 = r12.f1798a
            com.igexin.push.c.j r0 = r0.f1794g
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p046c.C1222q.call():com.igexin.push.c.j");
    }
}
