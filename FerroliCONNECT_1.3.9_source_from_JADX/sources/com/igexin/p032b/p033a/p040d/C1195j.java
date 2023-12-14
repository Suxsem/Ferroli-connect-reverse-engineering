package com.igexin.p032b.p033a.p040d;

/* renamed from: com.igexin.b.a.d.j */
final class C1195j extends Thread {

    /* renamed from: a */
    volatile boolean f1693a = true;

    /* renamed from: b */
    C1192g f1694b;

    /* renamed from: c */
    final /* synthetic */ C1191f f1695c;

    public C1195j(C1191f fVar) {
        this.f1695c = fVar;
        setName("TS-processor");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
        if (r10.f1694b != null) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0028, code lost:
        r10.f1694b = new com.igexin.p032b.p033a.p040d.C1192g(r10.f1695c);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007e, code lost:
        if (r5.f1650p == false) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0080, code lost:
        r5.f1635A = 0;
        r0.mo14289a(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c6, code lost:
        if (r5.f1650p == false) goto L_0x0080;
     */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00de A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r10 = this;
            r0 = -2
            android.os.Process.setThreadPriority(r0)
            com.igexin.b.a.d.f r0 = r10.f1695c
            com.igexin.b.a.d.d<com.igexin.b.a.d.e> r0 = r0.f1667k
            r1 = -1
            r2 = 0
            r3 = 1
        L_0x000b:
            r5 = r2
        L_0x000c:
            r4 = 1
        L_0x000d:
            boolean r6 = r10.f1693a
            if (r6 == 0) goto L_0x011a
            if (r4 == r1) goto L_0x001b
            if (r4 == 0) goto L_0x0062
            if (r4 == r3) goto L_0x00c9
            r6 = 2
            if (r4 == r6) goto L_0x00de
            goto L_0x000d
        L_0x001b:
            r5.mo14221d()     // Catch:{ Exception -> 0x00ff }
            boolean r4 = r5.mo14306q()     // Catch:{ Exception -> 0x00ff }
            if (r4 == 0) goto L_0x0037
            com.igexin.b.a.d.g r4 = r10.f1694b     // Catch:{ Exception -> 0x00ff }
            if (r4 != 0) goto L_0x0031
            com.igexin.b.a.d.g r4 = new com.igexin.b.a.d.g     // Catch:{ Exception -> 0x00ff }
            com.igexin.b.a.d.f r6 = r10.f1695c     // Catch:{ Exception -> 0x00ff }
            r4.<init>(r6)     // Catch:{ Exception -> 0x00ff }
            r10.f1694b = r4     // Catch:{ Exception -> 0x00ff }
        L_0x0031:
            com.igexin.b.a.d.g r4 = r10.f1694b     // Catch:{ Exception -> 0x00ff }
            r4.mo14327a((com.igexin.p032b.p033a.p040d.C1190e) r5)     // Catch:{ Exception -> 0x00ff }
            goto L_0x000b
        L_0x0037:
            boolean r4 = r5.f1649o     // Catch:{ Exception -> 0x00ff }
            if (r4 == 0) goto L_0x0062
            long r6 = r5.f1655u     // Catch:{ Exception -> 0x00ff }
            r8 = 0
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 != 0) goto L_0x0062
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ff }
            r4.<init>()     // Catch:{ Exception -> 0x00ff }
            java.lang.String r6 = "TaskService|"
            r4.append(r6)     // Catch:{ Exception -> 0x00ff }
            r4.append(r5)     // Catch:{ Exception -> 0x00ff }
            java.lang.String r6 = "|isBlock = false|cycyle = true|doTime = 0, "
            r4.append(r6)     // Catch:{ Exception -> 0x00ff }
            java.lang.String r6 = "invalid ###########"
            r4.append(r6)     // Catch:{ Exception -> 0x00ff }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00ff }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r4)     // Catch:{ Exception -> 0x00ff }
            goto L_0x000c
        L_0x0062:
            r4 = 0
            r5.mo14232b_()     // Catch:{ Exception -> 0x008a }
            r5.mo14301g()     // Catch:{ Exception -> 0x008a }
            r5.mo14302g_()     // Catch:{ Exception -> 0x008a }
            com.igexin.b.a.d.f r6 = r10.f1695c
            r6.mo14324g()
            boolean r6 = r5.f1654t
            if (r6 != 0) goto L_0x0078
            r5.mo14300c()
        L_0x0078:
            boolean r6 = r5.f1646k
            if (r6 != 0) goto L_0x0085
            boolean r6 = r5.f1650p
            if (r6 != 0) goto L_0x0085
        L_0x0080:
            r5.f1635A = r4
            r0.mo14289a(r5)
        L_0x0085:
            r5 = r2
            r4 = 1
            goto L_0x00c9
        L_0x0088:
            r1 = move-exception
            goto L_0x00e5
        L_0x008a:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0088 }
            r7.<init>()     // Catch:{ all -> 0x0088 }
            java.lang.String r8 = "TaskService|SERVICE_PROCESSING|error|"
            r7.append(r8)     // Catch:{ all -> 0x0088 }
            java.lang.String r8 = r6.toString()     // Catch:{ all -> 0x0088 }
            r7.append(r8)     // Catch:{ all -> 0x0088 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0088 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r7)     // Catch:{ all -> 0x0088 }
            r5.f1654t = r3     // Catch:{ all -> 0x0088 }
            r5.f1636B = r6     // Catch:{ all -> 0x0088 }
            r5.mo14310u()     // Catch:{ all -> 0x0088 }
            r5.mo14305p()     // Catch:{ all -> 0x0088 }
            com.igexin.b.a.d.f r6 = r10.f1695c     // Catch:{ all -> 0x0088 }
            com.igexin.b.a.d.c r6 = r6.f1666j     // Catch:{ all -> 0x0088 }
            r6.mo14283a(r5)     // Catch:{ all -> 0x0088 }
            com.igexin.b.a.d.f r6 = r10.f1695c
            r6.mo14324g()
            boolean r6 = r5.f1654t
            if (r6 != 0) goto L_0x00c0
            r5.mo14300c()
        L_0x00c0:
            boolean r6 = r5.f1646k
            if (r6 != 0) goto L_0x0085
            boolean r6 = r5.f1650p
            if (r6 != 0) goto L_0x0085
            goto L_0x0080
        L_0x00c9:
            com.igexin.b.a.d.e r5 = r0.mo14292c()     // Catch:{ InterruptedException -> 0x00cd }
        L_0x00cd:
            if (r5 == 0) goto L_0x00de
            boolean r6 = r5.f1646k
            if (r6 != 0) goto L_0x00db
            boolean r6 = r5.f1647m
            if (r6 == 0) goto L_0x00d8
            goto L_0x00db
        L_0x00d8:
            r4 = -1
            goto L_0x000d
        L_0x00db:
            r5 = r2
            goto L_0x000d
        L_0x00de:
            com.igexin.b.a.d.f r4 = r10.f1695c
            r4.mo14324g()
            goto L_0x000c
        L_0x00e5:
            com.igexin.b.a.d.f r2 = r10.f1695c
            r2.mo14324g()
            boolean r2 = r5.f1654t
            if (r2 != 0) goto L_0x00f1
            r5.mo14300c()
        L_0x00f1:
            boolean r2 = r5.f1646k
            if (r2 != 0) goto L_0x00fe
            boolean r2 = r5.f1650p
            if (r2 != 0) goto L_0x00fe
            r5.f1635A = r4
            r0.mo14289a(r5)
        L_0x00fe:
            throw r1
        L_0x00ff:
            r4 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "TaskService|TASK_INIT|error|"
            r6.append(r7)
            java.lang.String r4 = r4.toString()
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r4)
            goto L_0x000c
        L_0x011a:
            r0.mo14293d()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p040d.C1195j.run():void");
    }
}
