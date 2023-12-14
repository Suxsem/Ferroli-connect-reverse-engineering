package anet.channel.strategy;

/* renamed from: anet.channel.strategy.b */
/* compiled from: Taobao */
class C0565b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f477a;

    /* renamed from: b */
    final /* synthetic */ Object f478b;

    /* renamed from: c */
    final /* synthetic */ C0564a f479c;

    C0565b(C0564a aVar, String str, Object obj) {
        this.f479c = aVar;
        this.f477a = str;
        this.f478b = obj;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ad, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b3, code lost:
        if (anet.channel.util.ALog.isPrintLog(1) != false) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b5, code lost:
        anet.channel.util.ALog.m325d("awcn.LocalDnsStrategyTable", "resolve ip by local dns failed", (java.lang.String) null, com.taobao.accs.common.Constants.KEY_HOST, r13.f477a);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c6, code lost:
        r13.f479c.f475a.put(r13.f477a, java.util.Collections.EMPTY_LIST);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00d5, code lost:
        monitor-enter(r13.f479c.f476b);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r13.f479c.f476b.remove(r13.f477a);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00e2, code lost:
        monitor-enter(r13.f478b);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r13.f478b.notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00f4, code lost:
        monitor-enter(r13.f479c.f476b);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        r13.f479c.f476b.remove(r13.f477a);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0101, code lost:
        monitor-enter(r13.f478b);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        r13.f478b.notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0108, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00af */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r13 = this;
            r0 = 0
            r1 = 2
            r2 = 0
            r3 = 1
            java.lang.String r4 = r13.f477a     // Catch:{ Exception -> 0x00af }
            java.net.InetAddress r4 = java.net.InetAddress.getByName(r4)     // Catch:{ Exception -> 0x00af }
            java.lang.String r4 = r4.getHostAddress()     // Catch:{ Exception -> 0x00af }
            java.util.LinkedList r12 = new java.util.LinkedList     // Catch:{ Exception -> 0x00af }
            r12.<init>()     // Catch:{ Exception -> 0x00af }
            anet.channel.strategy.StrategyTemplate r5 = anet.channel.strategy.StrategyTemplate.getInstance()     // Catch:{ Exception -> 0x00af }
            java.lang.String r6 = r13.f477a     // Catch:{ Exception -> 0x00af }
            anet.channel.strategy.ConnProtocol r7 = r5.getConnProtocol(r6)     // Catch:{ Exception -> 0x00af }
            if (r7 == 0) goto L_0x003e
            anet.channel.strategy.a r5 = r13.f479c     // Catch:{ Exception -> 0x00af }
            boolean r5 = r5.mo9038a((anet.channel.strategy.ConnProtocol) r7)     // Catch:{ Exception -> 0x00af }
            if (r5 != 0) goto L_0x002c
            r5 = 80
            r6 = 80
            goto L_0x0030
        L_0x002c:
            r5 = 443(0x1bb, float:6.21E-43)
            r6 = 443(0x1bb, float:6.21E-43)
        L_0x0030:
            r8 = 0
            r9 = 0
            r10 = 1
            r11 = 45000(0xafc8, float:6.3058E-41)
            r5 = r4
            anet.channel.strategy.IPConnStrategy r5 = anet.channel.strategy.IPConnStrategy.m243a(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00af }
            r12.add(r5)     // Catch:{ Exception -> 0x00af }
        L_0x003e:
            r6 = 80
            anet.channel.strategy.ConnProtocol r7 = anet.channel.strategy.ConnProtocol.HTTP     // Catch:{ Exception -> 0x00af }
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r5 = r4
            anet.channel.strategy.IPConnStrategy r5 = anet.channel.strategy.IPConnStrategy.m243a(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00af }
            r12.add(r5)     // Catch:{ Exception -> 0x00af }
            r6 = 443(0x1bb, float:6.21E-43)
            anet.channel.strategy.ConnProtocol r7 = anet.channel.strategy.ConnProtocol.HTTPS     // Catch:{ Exception -> 0x00af }
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r5 = r4
            anet.channel.strategy.IPConnStrategy r5 = anet.channel.strategy.IPConnStrategy.m243a(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00af }
            r12.add(r5)     // Catch:{ Exception -> 0x00af }
            anet.channel.strategy.a r5 = r13.f479c     // Catch:{ Exception -> 0x00af }
            java.util.concurrent.ConcurrentHashMap<java.lang.String, java.util.List<anet.channel.strategy.IPConnStrategy>> r5 = r5.f475a     // Catch:{ Exception -> 0x00af }
            java.lang.String r6 = r13.f477a     // Catch:{ Exception -> 0x00af }
            r5.put(r6, r12)     // Catch:{ Exception -> 0x00af }
            boolean r5 = anet.channel.util.ALog.isPrintLog(r3)     // Catch:{ Exception -> 0x00af }
            if (r5 == 0) goto L_0x008e
            java.lang.String r5 = "awcn.LocalDnsStrategyTable"
            java.lang.String r6 = "resolve ip by local dns"
            r7 = 6
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x00af }
            java.lang.String r8 = "host"
            r7[r0] = r8     // Catch:{ Exception -> 0x00af }
            java.lang.String r8 = r13.f477a     // Catch:{ Exception -> 0x00af }
            r7[r3] = r8     // Catch:{ Exception -> 0x00af }
            java.lang.String r8 = "ip"
            r7[r1] = r8     // Catch:{ Exception -> 0x00af }
            r8 = 3
            r7[r8] = r4     // Catch:{ Exception -> 0x00af }
            r4 = 4
            java.lang.String r8 = "list"
            r7[r4] = r8     // Catch:{ Exception -> 0x00af }
            r4 = 5
            r7[r4] = r12     // Catch:{ Exception -> 0x00af }
            anet.channel.util.ALog.m325d(r5, r6, r2, r7)     // Catch:{ Exception -> 0x00af }
        L_0x008e:
            anet.channel.strategy.a r0 = r13.f479c
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r0.f476b
            monitor-enter(r0)
            anet.channel.strategy.a r1 = r13.f479c     // Catch:{ all -> 0x00aa }
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r1.f476b     // Catch:{ all -> 0x00aa }
            java.lang.String r2 = r13.f477a     // Catch:{ all -> 0x00aa }
            r1.remove(r2)     // Catch:{ all -> 0x00aa }
            monitor-exit(r0)     // Catch:{ all -> 0x00aa }
            java.lang.Object r1 = r13.f478b
            monitor-enter(r1)
            java.lang.Object r0 = r13.f478b     // Catch:{ all -> 0x00a7 }
            r0.notifyAll()     // Catch:{ all -> 0x00a7 }
            monitor-exit(r1)     // Catch:{ all -> 0x00a7 }
            goto L_0x00e9
        L_0x00a7:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00a7 }
            throw r0
        L_0x00aa:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00aa }
            throw r1
        L_0x00ad:
            r0 = move-exception
            goto L_0x00f0
        L_0x00af:
            boolean r4 = anet.channel.util.ALog.isPrintLog(r3)     // Catch:{ all -> 0x00ad }
            if (r4 == 0) goto L_0x00c6
            java.lang.String r4 = "awcn.LocalDnsStrategyTable"
            java.lang.String r5 = "resolve ip by local dns failed"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00ad }
            java.lang.String r6 = "host"
            r1[r0] = r6     // Catch:{ all -> 0x00ad }
            java.lang.String r0 = r13.f477a     // Catch:{ all -> 0x00ad }
            r1[r3] = r0     // Catch:{ all -> 0x00ad }
            anet.channel.util.ALog.m325d(r4, r5, r2, r1)     // Catch:{ all -> 0x00ad }
        L_0x00c6:
            anet.channel.strategy.a r0 = r13.f479c     // Catch:{ all -> 0x00ad }
            java.util.concurrent.ConcurrentHashMap<java.lang.String, java.util.List<anet.channel.strategy.IPConnStrategy>> r0 = r0.f475a     // Catch:{ all -> 0x00ad }
            java.lang.String r1 = r13.f477a     // Catch:{ all -> 0x00ad }
            java.util.List r2 = java.util.Collections.EMPTY_LIST     // Catch:{ all -> 0x00ad }
            r0.put(r1, r2)     // Catch:{ all -> 0x00ad }
            anet.channel.strategy.a r0 = r13.f479c
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r0.f476b
            monitor-enter(r0)
            anet.channel.strategy.a r1 = r13.f479c     // Catch:{ all -> 0x00ed }
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r1.f476b     // Catch:{ all -> 0x00ed }
            java.lang.String r2 = r13.f477a     // Catch:{ all -> 0x00ed }
            r1.remove(r2)     // Catch:{ all -> 0x00ed }
            monitor-exit(r0)     // Catch:{ all -> 0x00ed }
            java.lang.Object r1 = r13.f478b
            monitor-enter(r1)
            java.lang.Object r0 = r13.f478b     // Catch:{ all -> 0x00ea }
            r0.notifyAll()     // Catch:{ all -> 0x00ea }
            monitor-exit(r1)     // Catch:{ all -> 0x00ea }
        L_0x00e9:
            return
        L_0x00ea:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00ea }
            throw r0
        L_0x00ed:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ed }
            throw r1
        L_0x00f0:
            anet.channel.strategy.a r1 = r13.f479c
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r1.f476b
            monitor-enter(r1)
            anet.channel.strategy.a r2 = r13.f479c     // Catch:{ all -> 0x010c }
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r2.f476b     // Catch:{ all -> 0x010c }
            java.lang.String r3 = r13.f477a     // Catch:{ all -> 0x010c }
            r2.remove(r3)     // Catch:{ all -> 0x010c }
            monitor-exit(r1)     // Catch:{ all -> 0x010c }
            java.lang.Object r2 = r13.f478b
            monitor-enter(r2)
            java.lang.Object r1 = r13.f478b     // Catch:{ all -> 0x0109 }
            r1.notifyAll()     // Catch:{ all -> 0x0109 }
            monitor-exit(r2)     // Catch:{ all -> 0x0109 }
            throw r0
        L_0x0109:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0109 }
            throw r0
        L_0x010c:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x010c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.C0565b.run():void");
    }
}
