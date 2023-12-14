package com.aliyun.ams.emas.push.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

/* renamed from: com.aliyun.ams.emas.push.notification.e */
/* compiled from: Taobao */
public class C0915e {
    public static final int TYPE_FROM_ACTIVITY = 1;
    public static final int TYPE_FROM_SERVICE = 0;

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00fb, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00fc, code lost:
        r2 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00fe, code lost:
        r4 = r21;
        r14 = r17;
        r3 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x016a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        com.taobao.accs.utl.ALog.m3730w(r2, "move task to front fail", r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01a2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01df, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01e0, code lost:
        r4 = r21;
        r14 = r17;
        r3 = r18;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x01df A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0067] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int mo10235a(android.content.Intent r21, android.content.Context r22, int r23) {
        /*
            r20 = this;
            r0 = r21
            r1 = r22
            java.lang.String r2 = "Delete msg("
            java.lang.String r3 = "Open msg("
            java.lang.Class r4 = com.aliyun.ams.emas.push.C0910h.m1072a()
            java.lang.String r5 = "action_type"
            java.lang.String r6 = r0.getStringExtra(r5)
            java.lang.String r7 = "notification_open"
            boolean r6 = r7.equals(r6)
            java.lang.String r7 = "messageId"
            java.lang.String r8 = "extraMap"
            java.lang.String r9 = "notificationOpenType"
            java.lang.String r10 = "extData"
            java.lang.String r11 = "summary"
            java.lang.String r12 = "title"
            java.lang.String r13 = "msgId"
            java.lang.String r15 = ")"
            java.lang.String r14 = "MPS:CPushServiceListener"
            r16 = r2
            if (r6 == 0) goto L_0x0230
            android.os.Bundle r5 = r21.getExtras()
            java.lang.String r6 = "realIntent"
            java.lang.Object r5 = r5.get(r6)
            android.content.Intent r5 = (android.content.Intent) r5
            r6 = 335544320(0x14000000, float:6.4623485E-27)
            r5.setFlags(r6)
            java.lang.String r6 = r5.getStringExtra(r13)
            java.lang.String r13 = r5.getStringExtra(r12)
            java.lang.String r2 = r5.getStringExtra(r11)
            java.lang.String r10 = r0.getStringExtra(r10)
            r21 = r10
            r10 = 1
            int r0 = r5.getIntExtra(r9, r10)
            java.lang.String r10 = "notificationId"
            r18 = r3
            r17 = r15
            r15 = 0
            int r3 = r5.getIntExtra(r10, r15)
            java.lang.String r15 = r5.getStringExtra(r8)
            r19 = r14
            android.content.Intent r14 = new android.content.Intent     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r14.<init>()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r16 = r5
            java.lang.String r5 = r22.getPackageName()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r14.setPackage(r5)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.String r5 = "com.alibaba.push2.action.NOTIFICATION_OPENED"
            r14.setAction(r5)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r14.putExtra(r7, r6)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r14.putExtra(r12, r13)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r14.putExtra(r11, r2)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r14.putExtra(r8, r15)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r14.putExtra(r9, r0)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r14.putExtra(r10, r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r3 = 12
            if (r2 < r3) goto L_0x0097
            r2 = 32
            r14.setFlags(r2)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
        L_0x0097:
            if (r4 != 0) goto L_0x00b2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r2.<init>()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.String r3 = r22.getPackageName()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r2.append(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.String r3 = ".AGOO"
            r2.append(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r1.sendBroadcast(r14, r2)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            goto L_0x00bc
        L_0x00b2:
            r14.setClass(r1, r4)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.String r2 = r4.getName()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            com.taobao.accs.dispatch.IntentDispatch.dispatchIntent(r1, r14, r2)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
        L_0x00bc:
            if (r23 != 0) goto L_0x00ec
            java.lang.String r2 = "android.intent.action.MAIN"
            java.lang.String r3 = r16.getAction()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            boolean r2 = r2.equals(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            if (r2 == 0) goto L_0x00ec
            boolean r2 = com.aliyun.ams.emas.push.notification.C0917g.m1134a(r22)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            if (r2 == 0) goto L_0x00ec
            com.alibaba.sdk.android.logger.ILog r0 = com.aliyun.ams.emas.push.C0910h.imortantLogger     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r2.<init>()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.String r3 = "app is in front, action:"
            r2.append(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.String r3 = r16.getAction()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r2.append(r3)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            r0.mo9706d((java.lang.String) r2)     // Catch:{ Throwable -> 0x01e7, all -> 0x01df }
            goto L_0x01c1
        L_0x00ec:
            r2 = 4
            if (r0 != r2) goto L_0x0106
            java.lang.String r0 = "open with no action"
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00fb, all -> 0x01df }
            r2 = r19
            com.taobao.accs.utl.ALog.m3728i(r2, r0, r3)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            goto L_0x01c1
        L_0x00fb:
            r0 = move-exception
            r2 = r19
        L_0x00fe:
            r4 = r21
            r14 = r17
            r3 = r18
            goto L_0x01f0
        L_0x0106:
            r2 = r19
            r3 = 1
            if (r0 != r3) goto L_0x01a5
            java.lang.String r0 = "open app"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            com.taobao.accs.utl.ALog.m3728i(r2, r0, r4)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            r3 = 11
            if (r0 < r3) goto L_0x0194
            if (r23 != 0) goto L_0x0194
            java.lang.String r0 = "android.permission.GET_TASKS"
            int r0 = android.support.p000v4.content.ContextCompat.checkSelfPermission(r1, r0)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            if (r0 != 0) goto L_0x0186
            java.lang.String r0 = "android.permission.REORDER_TASKS"
            int r0 = android.support.p000v4.content.ContextCompat.checkSelfPermission(r1, r0)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            if (r0 != 0) goto L_0x0186
            java.lang.String r0 = "activity"
            java.lang.Object r0 = r1.getSystemService(r0)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            r3 = r0
            android.app.ActivityManager r3 = (android.app.ActivityManager) r3     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            r0 = 2147483647(0x7fffffff, float:NaN)
            java.util.List r0 = r3.getRunningTasks(r0)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            java.util.Iterator r4 = r0.iterator()     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
        L_0x013f:
            boolean r0 = r4.hasNext()     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            if (r0 == 0) goto L_0x0174
            java.lang.Object r0 = r4.next()     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            android.app.ActivityManager$RunningTaskInfo r0 = (android.app.ActivityManager.RunningTaskInfo) r0     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            android.content.ComponentName r5 = r0.topActivity     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            java.lang.String r5 = r5.getPackageName()     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            java.lang.String r7 = r22.getPackageName()     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            boolean r5 = r5.equals(r7)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            if (r5 == 0) goto L_0x013f
            java.lang.String r5 = "move task to front"
            r7 = 0
            java.lang.Object[] r8 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            com.taobao.accs.utl.ALog.m3725d(r2, r5, r8)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            int r0 = r0.id     // Catch:{ Throwable -> 0x016a, all -> 0x01df }
            r3.moveTaskToFront(r0, r7)     // Catch:{ Throwable -> 0x016a, all -> 0x01df }
            r0 = 0
            goto L_0x0175
        L_0x016a:
            r0 = move-exception
            java.lang.String r5 = "move task to front fail"
            r7 = 0
            java.lang.Object[] r8 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            com.taobao.accs.utl.ALog.m3730w(r2, r5, r0, r8)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            goto L_0x013f
        L_0x0174:
            r0 = 1
        L_0x0175:
            r3 = 1
            if (r3 != r0) goto L_0x01c1
            java.lang.String r0 = "do not find corresponing running task, start app with launch activity"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            com.taobao.accs.utl.ALog.m3731w(r2, r0, r4)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            r5 = r16
            r1.startActivity(r5)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            goto L_0x01c1
        L_0x0186:
            r5 = r16
            java.lang.String r0 = "no get tasks and reorder tasks permission, start app with launch activity"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            com.taobao.accs.utl.ALog.m3725d(r2, r0, r4)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            r1.startActivity(r5)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            goto L_0x01c1
        L_0x0194:
            r5 = r16
            java.lang.String r0 = "sdk version < 11, start app with launch activity"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            com.taobao.accs.utl.ALog.m3731w(r2, r0, r4)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            r1.startActivity(r5)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            goto L_0x01c1
        L_0x01a2:
            r0 = move-exception
            goto L_0x00fe
        L_0x01a5:
            r5 = r16
            r3 = 2
            if (r0 != r3) goto L_0x01b3
            java.lang.String r0 = "open activity"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            com.taobao.accs.utl.ALog.m3725d(r2, r0, r4)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            goto L_0x01be
        L_0x01b3:
            r3 = 3
            if (r0 != r3) goto L_0x01be
            java.lang.String r0 = "open url"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
            com.taobao.accs.utl.ALog.m3725d(r2, r0, r4)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
        L_0x01be:
            r1.startActivity(r5)     // Catch:{ Throwable -> 0x01a2, all -> 0x01df }
        L_0x01c1:
            com.alibaba.sdk.android.logger.ILog r0 = com.aliyun.ams.emas.push.C0910h.imortantLogger
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r3 = r18
            r2.append(r3)
            r2.append(r6)
            r14 = r17
            r2.append(r14)
            java.lang.String r2 = r2.toString()
            r0.mo9711i((java.lang.String) r2)
            r4 = r21
            goto L_0x020f
        L_0x01df:
            r0 = move-exception
            r4 = r21
            r14 = r17
            r3 = r18
            goto L_0x0215
        L_0x01e7:
            r0 = move-exception
            r4 = r21
            r14 = r17
            r3 = r18
            r2 = r19
        L_0x01f0:
            java.lang.String r5 = "startActivity error"
            r7 = 0
            java.lang.Object[] r8 = new java.lang.Object[r7]     // Catch:{ all -> 0x0214 }
            com.taobao.accs.utl.ALog.m3726e(r2, r5, r0, r8)     // Catch:{ all -> 0x0214 }
            com.alibaba.sdk.android.logger.ILog r0 = com.aliyun.ams.emas.push.C0910h.imortantLogger
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r3)
            r2.append(r6)
            r2.append(r14)
            java.lang.String r2 = r2.toString()
            r0.mo9711i((java.lang.String) r2)
        L_0x020f:
            com.taobao.agoo.TaobaoRegister.clickMessage(r1, r6, r4)
            goto L_0x032b
        L_0x0214:
            r0 = move-exception
        L_0x0215:
            com.alibaba.sdk.android.logger.ILog r2 = com.aliyun.ams.emas.push.C0910h.imortantLogger
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r6)
            r5.append(r14)
            java.lang.String r3 = r5.toString()
            r2.mo9711i((java.lang.String) r3)
            com.taobao.agoo.TaobaoRegister.clickMessage(r1, r6, r4)
            throw r0
        L_0x0230:
            r2 = r14
            r14 = r15
            java.lang.String r3 = r0.getStringExtra(r5)
            java.lang.String r6 = "notification_delete"
            boolean r3 = r6.equals(r3)
            if (r3 == 0) goto L_0x02fc
            java.lang.String r3 = r0.getStringExtra(r13)
            java.lang.String r5 = "task_id"
            r0.getStringExtra(r5)
            java.lang.String r5 = r0.getStringExtra(r10)
            android.content.Intent r6 = new android.content.Intent     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r6.<init>()     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r10 = r22.getPackageName()     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r6.setPackage(r10)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r10 = "com.alibaba.push2.action.NOTIFICATION_REMOVED"
            r6.setAction(r10)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r6.putExtra(r7, r3)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r7 = r0.getStringExtra(r12)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r6.putExtra(r12, r7)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r7 = r0.getStringExtra(r11)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r6.putExtra(r11, r7)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r7 = r0.getStringExtra(r8)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r6.putExtra(r8, r7)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r7 = 1
            int r0 = r0.getIntExtra(r9, r7)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r6.putExtra(r9, r0)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r7 = 12
            if (r0 < r7) goto L_0x0287
            r0 = 32
            r6.setFlags(r0)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
        L_0x0287:
            if (r4 != 0) goto L_0x02a2
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r0.<init>()     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r4 = r22.getPackageName()     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r0.append(r4)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r4 = ".AGOO"
            r0.append(r4)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            r1.sendBroadcast(r6, r0)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            goto L_0x02ac
        L_0x02a2:
            r6.setClass(r1, r4)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            java.lang.String r0 = r4.getName()     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
            com.taobao.accs.dispatch.IntentDispatch.dispatchIntent(r1, r6, r0)     // Catch:{ Throwable -> 0x02ba, all -> 0x02b6 }
        L_0x02ac:
            com.alibaba.sdk.android.logger.ILog r0 = com.aliyun.ams.emas.push.C0910h.imortantLogger
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r4 = r16
            goto L_0x02cc
        L_0x02b6:
            r0 = move-exception
            r4 = r16
            goto L_0x02e1
        L_0x02ba:
            r0 = move-exception
            r4 = r16
            java.lang.String r6 = "send intent failed."
            r7 = 0
            java.lang.Object[] r8 = new java.lang.Object[r7]     // Catch:{ all -> 0x02e0 }
            com.taobao.accs.utl.ALog.m3726e(r2, r6, r0, r8)     // Catch:{ all -> 0x02e0 }
            com.alibaba.sdk.android.logger.ILog r0 = com.aliyun.ams.emas.push.C0910h.imortantLogger
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
        L_0x02cc:
            r2.append(r4)
            r2.append(r3)
            r2.append(r14)
            java.lang.String r2 = r2.toString()
            r0.mo9711i((java.lang.String) r2)
            com.taobao.agoo.TaobaoRegister.dismissMessage(r1, r3, r5)
            goto L_0x032b
        L_0x02e0:
            r0 = move-exception
        L_0x02e1:
            com.alibaba.sdk.android.logger.ILog r2 = com.aliyun.ams.emas.push.C0910h.imortantLogger
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r4)
            r6.append(r3)
            r6.append(r14)
            java.lang.String r4 = r6.toString()
            r2.mo9711i((java.lang.String) r4)
            com.taobao.agoo.TaobaoRegister.dismissMessage(r1, r3, r5)
            throw r0
        L_0x02fc:
            java.lang.String r2 = r0.getStringExtra(r5)
            java.lang.String r3 = "message_open"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0314
            java.lang.String r2 = r0.getStringExtra(r13)
            java.lang.String r0 = r0.getStringExtra(r10)
            com.taobao.agoo.TaobaoRegister.clickMessage(r1, r2, r0)
            goto L_0x032b
        L_0x0314:
            java.lang.String r2 = r0.getStringExtra(r5)
            java.lang.String r3 = "message_delete"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x032b
            java.lang.String r2 = r0.getStringExtra(r13)
            java.lang.String r0 = r0.getStringExtra(r10)
            com.taobao.agoo.TaobaoRegister.dismissMessage(r1, r2, r0)
        L_0x032b:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.ams.emas.push.notification.C0915e.mo10235a(android.content.Intent, android.content.Context, int):int");
    }

    @SuppressLint({"MissingPermission"})
    /* renamed from: a */
    public int mo10234a(Intent intent, Context context) {
        return mo10235a(intent, context, 0);
    }
}
