package org.android.agoo.control;

/* renamed from: org.android.agoo.control.f */
/* compiled from: Taobao */
class C2359f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f4080a;

    /* renamed from: b */
    final /* synthetic */ String f4081b;

    /* renamed from: c */
    final /* synthetic */ AgooFactory f4082c;

    C2359f(AgooFactory agooFactory, String str, String str2) {
        this.f4082c = agooFactory;
        this.f4080a = str;
        this.f4081b = str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r8 = this;
            java.lang.String r0 = "8"
            java.lang.String r1 = "AgooFactory"
            r2 = 0
            r3 = 0
            com.taobao.accs.utl.ALog$Level r4 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0065 }
            boolean r4 = com.taobao.accs.utl.ALog.isPrintLog(r4)     // Catch:{ Throwable -> 0x0065 }
            if (r4 == 0) goto L_0x0029
            java.lang.String r4 = "clickMessage"
            r5 = 4
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r6 = "msgid"
            r5[r2] = r6     // Catch:{ Throwable -> 0x0065 }
            r6 = 1
            java.lang.String r7 = r8.f4080a     // Catch:{ Throwable -> 0x0065 }
            r5[r6] = r7     // Catch:{ Throwable -> 0x0065 }
            r6 = 2
            java.lang.String r7 = "extData"
            r5[r6] = r7     // Catch:{ Throwable -> 0x0065 }
            r6 = 3
            java.lang.String r7 = r8.f4081b     // Catch:{ Throwable -> 0x0065 }
            r5[r6] = r7     // Catch:{ Throwable -> 0x0065 }
            com.taobao.accs.utl.ALog.m3728i(r1, r4, r5)     // Catch:{ Throwable -> 0x0065 }
        L_0x0029:
            java.lang.String r4 = "accs"
            java.lang.String r5 = r8.f4080a     // Catch:{ Throwable -> 0x0065 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0065 }
            if (r5 == 0) goto L_0x003b
            java.lang.String r0 = "messageId == null"
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0065 }
            com.taobao.accs.utl.ALog.m3725d(r1, r0, r4)     // Catch:{ Throwable -> 0x0065 }
            return
        L_0x003b:
            org.android.agoo.common.MsgDO r5 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x0065 }
            r5.<init>()     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r3 = r8.f4080a     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.msgIds = r3     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            java.lang.String r3 = r8.f4081b     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.extData = r3     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.messageSource = r4     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r5.msgStatus = r0     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            org.android.agoo.control.AgooFactory r3 = r8.f4082c     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            java.lang.String r4 = r8.f4080a     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            r3.updateMsgStatus(r4, r0)     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            org.android.agoo.control.AgooFactory r0 = r8.f4082c
            org.android.agoo.control.NotifManager r0 = r0.notifyManager
            r0.reportNotifyMessage(r5)
            goto L_0x0087
        L_0x005d:
            r0 = move-exception
            r3 = r5
            goto L_0x0088
        L_0x0060:
            r0 = move-exception
            r3 = r5
            goto L_0x0066
        L_0x0063:
            r0 = move-exception
            goto L_0x0088
        L_0x0065:
            r0 = move-exception
        L_0x0066:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0063 }
            r4.<init>()     // Catch:{ all -> 0x0063 }
            java.lang.String r5 = "clickMessage,error="
            r4.append(r5)     // Catch:{ all -> 0x0063 }
            r4.append(r0)     // Catch:{ all -> 0x0063 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0063 }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0063 }
            com.taobao.accs.utl.ALog.m3727e(r1, r0, r2)     // Catch:{ all -> 0x0063 }
            if (r3 == 0) goto L_0x0087
            org.android.agoo.control.AgooFactory r0 = r8.f4082c
            org.android.agoo.control.NotifManager r0 = r0.notifyManager
            r0.reportNotifyMessage(r3)
        L_0x0087:
            return
        L_0x0088:
            if (r3 == 0) goto L_0x0093
            org.android.agoo.control.AgooFactory r1 = r8.f4082c
            org.android.agoo.control.NotifManager r1 = r1.notifyManager
            r1.reportNotifyMessage(r3)
        L_0x0093:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.control.C2359f.run():void");
    }
}
