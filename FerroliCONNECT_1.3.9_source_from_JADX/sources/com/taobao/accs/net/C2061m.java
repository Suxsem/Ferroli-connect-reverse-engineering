package com.taobao.accs.net;

import com.taobao.accs.data.Message;

/* renamed from: com.taobao.accs.net.m */
/* compiled from: Taobao */
class C2061m implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Message f3426a;

    /* renamed from: b */
    final /* synthetic */ C2057j f3427b;

    C2061m(C2057j jVar, Message message) {
        this.f3427b = jVar;
        this.f3426a = message;
    }

    /* JADX WARNING: type inference failed for: r3v3, types: [boolean] */
    /* JADX WARNING: type inference failed for: r5v4, types: [boolean] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x011f A[Catch:{ InvalidParameterException -> 0x0109, TimeoutException -> 0x00f1, ConnectException -> 0x00d9, NoAvailStrategyException -> 0x00c5, Throwable -> 0x0090, Throwable -> 0x0334 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0246 A[Catch:{ InvalidParameterException -> 0x0109, TimeoutException -> 0x00f1, ConnectException -> 0x00d9, NoAvailStrategyException -> 0x00c5, Throwable -> 0x0090, Throwable -> 0x0334 }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x02ac  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r26 = this;
            r1 = r26
            java.lang.String r2 = "sendMessage"
            java.lang.String r0 = "type"
            java.lang.String r3 = "accs"
            java.lang.String r4 = "status"
            java.lang.String r5 = "sendMessage end"
            java.lang.String r6 = "dataId"
            com.taobao.accs.data.Message r7 = r1.f3426a
            if (r7 == 0) goto L_0x03aa
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r7 = r7.mo18393e()
            if (r7 == 0) goto L_0x0021
            com.taobao.accs.data.Message r7 = r1.f3426a
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r7 = r7.mo18393e()
            r7.onTakeFromQueue()
        L_0x0021:
            com.alibaba.sdk.android.error.ErrorCode r7 = com.taobao.accs.AccsErrorCode.SUCCESS
            com.taobao.accs.data.Message r8 = r1.f3426a
            int r8 = r8.mo18386a()
            r10 = 5
            r11 = 3
            r12 = 2
            r13 = 0
            r14 = 1
            com.taobao.accs.net.j r15 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.logger.ILog r15 = r15.f3414t     // Catch:{ Throwable -> 0x0334 }
            java.lang.Object[] r9 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r17 = "sendMessage start"
            r9[r13] = r17     // Catch:{ Throwable -> 0x0334 }
            r9[r14] = r6     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r10 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r10 = r10.f3281q     // Catch:{ Throwable -> 0x0334 }
            r9[r12] = r10     // Catch:{ Throwable -> 0x0334 }
            r9[r11] = r0     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r10 = com.taobao.accs.data.Message.C2026c.m3490b(r8)     // Catch:{ Throwable -> 0x0334 }
            r16 = 4
            r9[r16] = r10     // Catch:{ Throwable -> 0x0334 }
            r15.mo9707d((java.lang.Object[]) r9)     // Catch:{ Throwable -> 0x0334 }
            if (r8 != r14) goto L_0x028f
            com.taobao.accs.data.Message r0 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.net.URL r0 = r0.f3270f     // Catch:{ Throwable -> 0x0334 }
            if (r0 != 0) goto L_0x0064
            com.taobao.accs.net.j r0 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.d r0 = r0.f3377e     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r10 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r15 = com.taobao.accs.AccsErrorCode.MESSAGE_HOST_NULL     // Catch:{ Throwable -> 0x0334 }
            r0.mo18415a((com.taobao.accs.data.Message) r10, (com.alibaba.sdk.android.error.ErrorCode) r15)     // Catch:{ Throwable -> 0x0334 }
            goto L_0x02aa
        L_0x0064:
            com.taobao.accs.net.j r0 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.AccsClientConfig r0 = r0.f3381i     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = r0.getAppKey()     // Catch:{ Throwable -> 0x0334 }
            anet.channel.SessionCenter r0 = anet.channel.SessionCenter.getInstance((java.lang.String) r0)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r10 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r15 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.net.URL r15 = r15.f3270f     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r15 = r15.getHost()     // Catch:{ Throwable -> 0x0334 }
            r10.mo18514a((anet.channel.SessionCenter) r0, (java.lang.String) r15, (boolean) r13)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r10 = r1.f3426a     // Catch:{ InvalidParameterException -> 0x0109, TimeoutException -> 0x00f1, ConnectException -> 0x00d9, NoAvailStrategyException -> 0x00c5, Throwable -> 0x0090 }
            java.net.URL r10 = r10.f3270f     // Catch:{ InvalidParameterException -> 0x0109, TimeoutException -> 0x00f1, ConnectException -> 0x00d9, NoAvailStrategyException -> 0x00c5, Throwable -> 0x0090 }
            java.lang.String r10 = r10.toString()     // Catch:{ InvalidParameterException -> 0x0109, TimeoutException -> 0x00f1, ConnectException -> 0x00d9, NoAvailStrategyException -> 0x00c5, Throwable -> 0x0090 }
            anet.channel.entity.ConnType$TypeLevel r15 = anet.channel.entity.ConnType.TypeLevel.SPDY     // Catch:{ InvalidParameterException -> 0x0109, TimeoutException -> 0x00f1, ConnectException -> 0x00d9, NoAvailStrategyException -> 0x00c5, Throwable -> 0x0090 }
            r11 = 60000(0xea60, double:2.9644E-319)
            anet.channel.Session r0 = r0.getThrowsException((java.lang.String) r10, (anet.channel.entity.ConnType.TypeLevel) r15, (long) r11)     // Catch:{ InvalidParameterException -> 0x0109, TimeoutException -> 0x00f1, ConnectException -> 0x00d9, NoAvailStrategyException -> 0x00c5, Throwable -> 0x0090 }
            goto L_0x011d
        L_0x0090:
            r0 = move-exception
            com.taobao.accs.net.j r7 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            android.content.Context r7 = r7.f3376d     // Catch:{ Throwable -> 0x0334 }
            boolean r7 = com.taobao.accs.utl.UtilityImpl.m3765g(r7)     // Catch:{ Throwable -> 0x0334 }
            if (r7 == 0) goto L_0x00b2
            com.alibaba.sdk.android.error.ErrorCode r7 = com.taobao.accs.AccsErrorCode.NETWORK_INAPP_EXCEPTION     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r7 = r7.copy()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = com.taobao.accs.AccsErrorCode.getExceptionInfo(r0)     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = com.taobao.accs.AccsErrorCode.getAllDetails(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r7.detail(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r7 = r0.build()     // Catch:{ Throwable -> 0x0334 }
            goto L_0x011c
        L_0x00b2:
            com.alibaba.sdk.android.error.ErrorCode r7 = com.taobao.accs.AccsErrorCode.NO_NETWORK     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r7 = r7.copy()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = com.taobao.accs.AccsErrorCode.getExceptionInfo(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r7.detail(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r7 = r0.build()     // Catch:{ Throwable -> 0x0334 }
            goto L_0x011c
        L_0x00c5:
            r0 = move-exception
            com.alibaba.sdk.android.error.ErrorCode r7 = com.taobao.accs.AccsErrorCode.NETWORK_INAPP_NO_STRATEGY     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r7 = r7.copy()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r7.detail(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r7 = r0.build()     // Catch:{ Throwable -> 0x0334 }
            goto L_0x011c
        L_0x00d9:
            r0 = move-exception
            com.alibaba.sdk.android.error.ErrorCode r7 = com.taobao.accs.AccsErrorCode.NETWORK_INAPP_CONNECT_FAIL     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r7 = r7.copy()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = com.taobao.accs.AccsErrorCode.getAllDetails(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r7.detail(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r7 = r0.build()     // Catch:{ Throwable -> 0x0334 }
            goto L_0x011c
        L_0x00f1:
            r0 = move-exception
            com.alibaba.sdk.android.error.ErrorCode r7 = com.taobao.accs.AccsErrorCode.NETWORK_INAPP_TIMEOUT     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r7 = r7.copy()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = com.taobao.accs.AccsErrorCode.getAllDetails(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r7.detail(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r7 = r0.build()     // Catch:{ Throwable -> 0x0334 }
            goto L_0x011c
        L_0x0109:
            r0 = move-exception
            com.alibaba.sdk.android.error.ErrorCode r7 = com.taobao.accs.AccsErrorCode.NETWORK_INAPP_ARGS_INVALID     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r7 = r7.copy()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r7.detail(r0)     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r7 = r0.build()     // Catch:{ Throwable -> 0x0334 }
        L_0x011c:
            r0 = 0
        L_0x011d:
            if (r0 == 0) goto L_0x0246
            com.taobao.accs.data.Message r10 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r11 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            android.content.Context r11 = r11.f3376d     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r12 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            int r12 = r12.f3375c     // Catch:{ Throwable -> 0x0334 }
            byte[] r10 = r10.mo18389a((android.content.Context) r11, (int) r12)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r11 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.logger.ILog r11 = r11.f3414t     // Catch:{ Throwable -> 0x0334 }
            r12 = 11
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x0334 }
            r12[r13] = r2     // Catch:{ Throwable -> 0x0334 }
            r12[r14] = r6     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r15 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r15 = r15.mo18390b()     // Catch:{ Throwable -> 0x0334 }
            r19 = 2
            r12[r19] = r15     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r15 = "command"
            r18 = 3
            r12[r18] = r15     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r15 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.lang.Integer r15 = r15.f3284t     // Catch:{ Throwable -> 0x0334 }
            r16 = 4
            r12[r16] = r15     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r15 = "host"
            r17 = 5
            r12[r17] = r15     // Catch:{ Throwable -> 0x0334 }
            r15 = 6
            com.taobao.accs.data.Message r9 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.net.URL r9 = r9.f3270f     // Catch:{ Throwable -> 0x0334 }
            r12[r15] = r9     // Catch:{ Throwable -> 0x0334 }
            r9 = 7
            java.lang.String r15 = "len"
            r12[r9] = r15     // Catch:{ Throwable -> 0x0334 }
            r9 = 8
            if (r10 != 0) goto L_0x016b
            r15 = 0
            goto L_0x016c
        L_0x016b:
            int r15 = r10.length     // Catch:{ Throwable -> 0x0334 }
        L_0x016c:
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ Throwable -> 0x0334 }
            r12[r9] = r15     // Catch:{ Throwable -> 0x0334 }
            r9 = 9
            java.lang.String r15 = "utdid"
            r12[r9] = r15     // Catch:{ Throwable -> 0x0334 }
            r9 = 10
            com.taobao.accs.net.j r15 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r15 = r15.f3382j     // Catch:{ Throwable -> 0x0334 }
            r12[r9] = r15     // Catch:{ Throwable -> 0x0334 }
            r11.mo9712i((java.lang.Object[]) r12)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r9 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0334 }
            r9.mo18388a((long) r11)     // Catch:{ Throwable -> 0x0334 }
            int r9 = r10.length     // Catch:{ Throwable -> 0x0334 }
            r11 = 16384(0x4000, float:2.2959E-41)
            if (r9 <= r11) goto L_0x01aa
            com.taobao.accs.data.Message r9 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.lang.Integer r9 = r9.f3284t     // Catch:{ Throwable -> 0x0334 }
            int r9 = r9.intValue()     // Catch:{ Throwable -> 0x0334 }
            r11 = 102(0x66, float:1.43E-43)
            if (r9 == r11) goto L_0x01aa
            com.taobao.accs.net.j r0 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.d r0 = r0.f3377e     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r9 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r10 = com.taobao.accs.AccsErrorCode.MESSAGE_TOO_LARGE     // Catch:{ Throwable -> 0x0334 }
            r0.mo18415a((com.taobao.accs.data.Message) r9, (com.alibaba.sdk.android.error.ErrorCode) r10)     // Catch:{ Throwable -> 0x0334 }
            goto L_0x02a9
        L_0x01aa:
            com.taobao.accs.net.j r9 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.d r9 = r9.f3377e     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r11 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            r9.mo18414a((com.taobao.accs.data.Message) r11)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r9 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            boolean r9 = r9.f3267c     // Catch:{ Throwable -> 0x0334 }
            if (r9 == 0) goto L_0x01c5
            com.taobao.accs.data.Message r9 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message$a r9 = r9.mo18392d()     // Catch:{ Throwable -> 0x0334 }
            int r9 = r9.mo18398a()     // Catch:{ Throwable -> 0x0334 }
            int r9 = -r9
            goto L_0x01cf
        L_0x01c5:
            com.taobao.accs.data.Message r9 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message$a r9 = r9.mo18392d()     // Catch:{ Throwable -> 0x0334 }
            int r9 = r9.mo18398a()     // Catch:{ Throwable -> 0x0334 }
        L_0x01cf:
            com.taobao.accs.data.Message r11 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            boolean r11 = r11.f3267c     // Catch:{ Throwable -> 0x0334 }
            if (r11 == 0) goto L_0x01e2
            com.taobao.accs.net.j r11 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            java.util.LinkedHashMap r11 = r11.f3384l     // Catch:{ Throwable -> 0x0334 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r15 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            r11.put(r12, r15)     // Catch:{ Throwable -> 0x0334 }
        L_0x01e2:
            com.taobao.accs.AccsState r11 = com.taobao.accs.AccsState.getInstance()     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r12 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r12 = r12.f3385m     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r15 = "lmst"
            java.lang.Integer r14 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x0334 }
            r11.mo18227a(r12, r15, r14)     // Catch:{ Throwable -> 0x0334 }
            r11 = 200(0xc8, float:2.8E-43)
            r0.sendCustomFrame(r9, r10, r11)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r0 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r0 = r0.mo18393e()     // Catch:{ Throwable -> 0x0334 }
            if (r0 == 0) goto L_0x0209
            com.taobao.accs.data.Message r0 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r0 = r0.mo18393e()     // Catch:{ Throwable -> 0x0334 }
            r0.onSendData()     // Catch:{ Throwable -> 0x0334 }
        L_0x0209:
            com.taobao.accs.net.j r0 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r9 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r9 = r9.mo18390b()     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r11 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.AccsClientConfig r11 = r11.f3381i     // Catch:{ Throwable -> 0x0334 }
            boolean r11 = r11.isQuickReconnect()     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r12 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            int r12 = r12.f3260S     // Catch:{ Throwable -> 0x0334 }
            long r14 = (long) r12     // Catch:{ Throwable -> 0x0334 }
            r0.mo18473a((java.lang.String) r9, (boolean) r11, (long) r14)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r0 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.d r0 = r0.f3377e     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.ut.monitor.TrafficsMonitor$a r9 = new com.taobao.accs.ut.monitor.TrafficsMonitor$a     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r11 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r11 = r11.f3249H     // Catch:{ Throwable -> 0x0334 }
            boolean r22 = anet.channel.GlobalAppRuntimeInfo.isAppBackground()     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.data.Message r12 = r1.f3426a     // Catch:{ Throwable -> 0x0334 }
            java.net.URL r12 = r12.f3270f     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r23 = r12.toString()     // Catch:{ Throwable -> 0x0334 }
            int r10 = r10.length     // Catch:{ Throwable -> 0x0334 }
            long r14 = (long) r10     // Catch:{ Throwable -> 0x0334 }
            r20 = r9
            r21 = r11
            r24 = r14
            r20.<init>(r21, r22, r23, r24)     // Catch:{ Throwable -> 0x0334 }
            r0.mo18418a((com.taobao.accs.p103ut.monitor.TrafficsMonitor.C2082a) r9)     // Catch:{ Throwable -> 0x0334 }
            goto L_0x02a9
        L_0x0246:
            int r0 = r7.getCodeInt()     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.error.ErrorCode r9 = com.taobao.accs.AccsErrorCode.SUCCESS     // Catch:{ Throwable -> 0x0334 }
            int r9 = r9.getCodeInt()     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r10 = "re"
            if (r0 == r9) goto L_0x0275
            com.taobao.accs.net.j r0 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.logger.ILog r0 = r0.f3414t     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r9 = r7.toString()     // Catch:{ Throwable -> 0x0334 }
            r0.mo9708e((java.lang.String) r9)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.AccsState r0 = com.taobao.accs.AccsState.getInstance()     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r9 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r9 = r9.f3385m     // Catch:{ Throwable -> 0x0334 }
            int r11 = r7.getCodeInt()     // Catch:{ Throwable -> 0x0334 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ Throwable -> 0x0334 }
            r0.mo18229b(r9, r10, r11)     // Catch:{ Throwable -> 0x0334 }
            goto L_0x028d
        L_0x0275:
            com.taobao.accs.net.j r0 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.logger.ILog r0 = r0.f3414t     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r9 = "sendMessage session is null"
            r0.mo9708e((java.lang.String) r9)     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.AccsState r0 = com.taobao.accs.AccsState.getInstance()     // Catch:{ Throwable -> 0x0334 }
            com.taobao.accs.net.j r9 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r9 = r9.f3385m     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r11 = "send session null"
            r0.mo18229b(r9, r10, r11)     // Catch:{ Throwable -> 0x0334 }
        L_0x028d:
            r14 = 0
            goto L_0x02aa
        L_0x028f:
            com.taobao.accs.net.j r9 = r1.f3427b     // Catch:{ Throwable -> 0x0334 }
            com.alibaba.sdk.android.logger.ILog r9 = r9.f3414t     // Catch:{ Throwable -> 0x0334 }
            r10 = 3
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r10 = "sendMessage skip"
            r11[r13] = r10     // Catch:{ Throwable -> 0x0334 }
            r10 = 1
            r11[r10] = r0     // Catch:{ Throwable -> 0x0334 }
            java.lang.String r0 = com.taobao.accs.data.Message.C2026c.m3490b(r8)     // Catch:{ Throwable -> 0x0334 }
            r10 = 2
            r11[r10] = r0     // Catch:{ Throwable -> 0x0334 }
            r9.mo9715w((java.lang.Object[]) r11)     // Catch:{ Throwable -> 0x0334 }
        L_0x02a9:
            r14 = 1
        L_0x02aa:
            if (r14 != 0) goto L_0x030d
            int r0 = r7.getCodeInt()
            com.alibaba.sdk.android.error.ErrorCode r2 = com.taobao.accs.AccsErrorCode.SUCCESS
            int r2 = r2.getCodeInt()
            if (r0 != r2) goto L_0x02cb
            com.alibaba.sdk.android.error.ErrorCode r0 = com.taobao.accs.AccsErrorCode.INAPP_CON_DISCONNECTED
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r0.copy()
            r2 = 0
            java.lang.String r2 = com.taobao.accs.AccsErrorCode.getAllDetails(r2)
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r0.detail(r2)
            com.alibaba.sdk.android.error.ErrorCode r7 = r0.build()
        L_0x02cb:
            r2 = 1
            if (r8 != r2) goto L_0x0304
            com.taobao.accs.data.Message r0 = r1.f3426a
            boolean r0 = r0.mo18395g()
            if (r0 != 0) goto L_0x02e2
            com.taobao.accs.net.j r0 = r1.f3427b
            com.taobao.accs.data.Message r2 = r1.f3426a
            r8 = 2000(0x7d0, float:2.803E-42)
            boolean r0 = r0.mo18476a((com.taobao.accs.data.Message) r2, (int) r8)
            if (r0 != 0) goto L_0x02eb
        L_0x02e2:
            com.taobao.accs.net.j r0 = r1.f3427b
            com.taobao.accs.data.d r0 = r0.f3377e
            com.taobao.accs.data.Message r2 = r1.f3426a
            r0.mo18415a((com.taobao.accs.data.Message) r2, (com.alibaba.sdk.android.error.ErrorCode) r7)
        L_0x02eb:
            com.taobao.accs.data.Message r0 = r1.f3426a
            int r0 = r0.f3259R
            r2 = 1
            if (r0 != r2) goto L_0x030d
            com.taobao.accs.data.Message r0 = r1.f3426a
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r0 = r0.mo18393e()
            if (r0 == 0) goto L_0x030d
            r7 = 0
            java.lang.String r0 = "resend"
            java.lang.String r2 = "total_accs"
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r3, r0, r2, r7)
            goto L_0x030d
        L_0x0304:
            com.taobao.accs.net.j r0 = r1.f3427b
            com.taobao.accs.data.d r0 = r0.f3377e
            com.taobao.accs.data.Message r2 = r1.f3426a
            r0.mo18415a((com.taobao.accs.data.Message) r2, (com.alibaba.sdk.android.error.ErrorCode) r7)
        L_0x030d:
            com.taobao.accs.net.j r0 = r1.f3427b
            com.alibaba.sdk.android.logger.ILog r0 = r0.f3414t
            r2 = 5
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r13] = r5
            r3 = 1
            r2[r3] = r6
            com.taobao.accs.data.Message r3 = r1.f3426a
            java.lang.String r3 = r3.mo18390b()
            r5 = 2
            r2[r5] = r3
            r3 = 3
            r2[r3] = r4
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r14)
            r4 = 4
            r2[r4] = r3
            r0.mo9712i((java.lang.Object[]) r2)
            goto L_0x03aa
        L_0x0332:
            r0 = move-exception
            goto L_0x0385
        L_0x0334:
            r0 = move-exception
            java.lang.String r7 = "send_fail"
            com.taobao.accs.data.Message r8 = r1.f3426a     // Catch:{ all -> 0x0332 }
            java.lang.String r8 = r8.f3249H     // Catch:{ all -> 0x0332 }
            java.lang.String r9 = ""
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0332 }
            r10.<init>()     // Catch:{ all -> 0x0332 }
            com.taobao.accs.net.j r11 = r1.f3427b     // Catch:{ all -> 0x0332 }
            int r11 = r11.f3375c     // Catch:{ all -> 0x0332 }
            r10.append(r11)     // Catch:{ all -> 0x0332 }
            java.lang.String r11 = r0.toString()     // Catch:{ all -> 0x0332 }
            r10.append(r11)     // Catch:{ all -> 0x0332 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0332 }
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r3, r7, r8, r9, r10)     // Catch:{ all -> 0x0332 }
            com.taobao.accs.net.j r3 = r1.f3427b     // Catch:{ all -> 0x0332 }
            com.alibaba.sdk.android.logger.ILog r3 = r3.f3414t     // Catch:{ all -> 0x0332 }
            r3.mo9709e(r2, r0)     // Catch:{ all -> 0x0332 }
            com.taobao.accs.net.j r0 = r1.f3427b
            com.alibaba.sdk.android.logger.ILog r0 = r0.f3414t
            r2 = 5
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r13] = r5
            r3 = 1
            r2[r3] = r6
            com.taobao.accs.data.Message r5 = r1.f3426a
            java.lang.String r5 = r5.mo18390b()
            r6 = 2
            r2[r6] = r5
            r5 = 3
            r2[r5] = r4
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            r4 = 4
            r2[r4] = r3
            r0.mo9712i((java.lang.Object[]) r2)
            goto L_0x03aa
        L_0x0385:
            com.taobao.accs.net.j r2 = r1.f3427b
            com.alibaba.sdk.android.logger.ILog r2 = r2.f3414t
            r3 = 5
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r13] = r5
            r5 = 1
            r3[r5] = r6
            com.taobao.accs.data.Message r6 = r1.f3426a
            java.lang.String r6 = r6.mo18390b()
            r7 = 2
            r3[r7] = r6
            r6 = 3
            r3[r6] = r4
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r5)
            r5 = 4
            r3[r5] = r4
            r2.mo9712i((java.lang.Object[]) r3)
            throw r0
        L_0x03aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.C2061m.run():void");
    }
}
