package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.C2018a;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.net.C2049b;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.C2086c;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* renamed from: com.taobao.accs.data.g */
/* compiled from: Taobao */
public class C2033g {

    /* renamed from: a */
    private static volatile C2033g f3323a;

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public String mo18436b() {
        return "com.taobao.accs.data.MsgDistributeService";
    }

    /* renamed from: a */
    public static C2033g m3533a() {
        if (f3323a == null) {
            synchronized (C2033g.class) {
                if (f3323a == null) {
                    f3323a = new C2033g();
                }
            }
        }
        return f3323a;
    }

    /* renamed from: a */
    public static void m3534a(Context context, Intent intent) {
        m3535a(context, (C2049b) null, intent);
    }

    /* renamed from: a */
    public static void m3535a(Context context, C2049b bVar, Intent intent) {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(new C2034h(context, bVar, intent));
        } catch (Throwable th) {
            if (!(bVar == null || intent == null)) {
                String stringExtra = intent.getStringExtra(Constants.KEY_CONFIG_TAG);
                String stringExtra2 = intent.getStringExtra(Constants.KEY_DATA_ID);
                if (!TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2)) {
                    bVar.mo18482b(Message.m3459a(stringExtra2, intent.getStringExtra(Constants.KEY_SERVICE_ID), bVar.mo18478b((String) null), 3), true);
                }
            }
            ALog.m3726e("MsgDistribute", "distribMessage", th, new Object[0]);
            UTMini instance = UTMini.getInstance();
            instance.commitEvent(66001, "MsgToBuss8", "distribMessage" + th.toString(), Integer.valueOf(Constants.SDK_VERSION_CODE));
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x02ce  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m3539b(android.content.Context r28, com.taobao.accs.net.C2049b r29, android.content.Intent r30) {
        /*
            r27 = this;
            r10 = r27
            r0 = r28
            r11 = r29
            r12 = r30
            java.lang.String r13 = "accs"
            java.lang.String r14 = "dataId"
            java.lang.String r15 = r12.getStringExtra(r14)
            java.lang.String r9 = "serviceId"
            java.lang.String r8 = r12.getStringExtra(r9)
            java.lang.String r1 = r30.getAction()
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.D
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)
            r6 = 2
            r5 = 3
            r4 = 1
            java.lang.String r3 = "MsgDistribute"
            r7 = 0
            if (r2 == 0) goto L_0x0042
            r2 = 6
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r18 = "action"
            r2[r7] = r18
            r2[r4] = r1
            r2[r6] = r14
            r2[r5] = r15
            r17 = 4
            r2[r17] = r9
            r16 = 5
            r2[r16] = r8
            java.lang.String r6 = "distribute ready"
            com.taobao.accs.utl.ALog.m3725d(r3, r6, r2)
        L_0x0042:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            r6 = 0
            if (r2 == 0) goto L_0x0070
            if (r11 == 0) goto L_0x0056
            java.lang.String r0 = r11.mo18478b((java.lang.String) r6)
            com.taobao.accs.data.Message r0 = com.taobao.accs.data.Message.m3459a((java.lang.String) r15, (java.lang.String) r8, (java.lang.String) r0, (int) r5)
            r11.mo18482b(r0, r4)
        L_0x0056:
            java.lang.Object[] r0 = new java.lang.Object[r7]
            java.lang.String r1 = "action null"
            com.taobao.accs.utl.ALog.m3727e(r3, r1, r0)
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()
            r2 = 221(0xdd, float:3.1E-43)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.String r3 = "MsgToBuss9"
            r4 = 66001(0x101d1, float:9.2487E-41)
            r0.commitEvent(r4, r3, r1, r2)
            return
        L_0x0070:
            java.lang.String r2 = "com.taobao.accs.intent.action.RECEIVE"
            boolean r2 = android.text.TextUtils.equals(r1, r2)     // Catch:{ Throwable -> 0x02ac }
            if (r2 == 0) goto L_0x0267
            java.lang.String r1 = "command"
            r2 = -1
            int r2 = r12.getIntExtra(r1, r2)     // Catch:{ Throwable -> 0x0258 }
            java.lang.String r1 = "userInfo"
            java.lang.String r16 = r12.getStringExtra(r1)     // Catch:{ Throwable -> 0x024a }
            com.alibaba.sdk.android.error.ErrorCode r1 = com.taobao.accs.common.Constants.getErrorCode(r30)     // Catch:{ Throwable -> 0x024a }
            java.lang.String r6 = "appKey"
            java.lang.String r6 = r12.getStringExtra(r6)     // Catch:{ Throwable -> 0x023d }
            java.lang.String r5 = "configTag"
            java.lang.String r5 = r12.getStringExtra(r5)     // Catch:{ Throwable -> 0x023d }
            java.lang.String r19 = r30.getPackage()     // Catch:{ Throwable -> 0x023d }
            if (r19 != 0) goto L_0x00b4
            java.lang.String r4 = r28.getPackageName()     // Catch:{ Throwable -> 0x00a3 }
            r12.setPackage(r4)     // Catch:{ Throwable -> 0x00a3 }
            goto L_0x00b4
        L_0x00a3:
            r0 = move-exception
            r1 = r2
            r2 = r8
            r26 = r9
            r23 = r13
            r24 = r14
            r4 = 4
        L_0x00ad:
            r14 = 0
            r25 = 2
            r13 = r3
        L_0x00b1:
            r3 = 0
            goto L_0x02bb
        L_0x00b4:
            boolean r4 = r13.equals(r8)     // Catch:{ Throwable -> 0x023d }
            java.lang.String r7 = "distribute start"
            if (r4 == 0) goto L_0x00e1
            r19 = r1
            r4 = 4
            java.lang.Object[] r1 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r4 = "appkey"
            r20 = 0
            r1[r20] = r4     // Catch:{ Throwable -> 0x00a3 }
            r4 = 1
            r1[r4] = r6     // Catch:{ Throwable -> 0x00a3 }
            java.lang.String r4 = "config"
            r6 = 2
            r1[r6] = r4     // Catch:{ Throwable -> 0x00a3 }
            r4 = 3
            r1[r4] = r5     // Catch:{ Throwable -> 0x00a3 }
            com.taobao.accs.utl.ALog.m3728i(r3, r7, r1)     // Catch:{ Throwable -> 0x00a3 }
            r4 = 4
            goto L_0x00fc
        L_0x00d7:
            r0 = move-exception
            r1 = r2
            r2 = r8
            r26 = r9
            r23 = r13
            r24 = r14
            goto L_0x00ad
        L_0x00e1:
            r19 = r1
            r4 = 4
            java.lang.Object[] r1 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x022e }
            java.lang.String r17 = "appkey"
            r20 = 0
            r1[r20] = r17     // Catch:{ Throwable -> 0x022e }
            r17 = 1
            r1[r17] = r6     // Catch:{ Throwable -> 0x022e }
            java.lang.String r6 = "config"
            r17 = 2
            r1[r17] = r6     // Catch:{ Throwable -> 0x022e }
            r6 = 3
            r1[r6] = r5     // Catch:{ Throwable -> 0x022e }
            com.taobao.accs.utl.ALog.m3725d(r3, r7, r1)     // Catch:{ Throwable -> 0x022e }
        L_0x00fc:
            boolean r1 = r10.m3538a(r12)     // Catch:{ Throwable -> 0x022e }
            if (r1 == 0) goto L_0x010b
            java.lang.String r0 = "routingMsgAck, should not happen!"
            r1 = 0
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x00d7 }
            com.taobao.accs.utl.ALog.m3731w(r3, r0, r5)     // Catch:{ Throwable -> 0x00d7 }
            return
        L_0x010b:
            if (r2 >= 0) goto L_0x0149
            if (r11 == 0) goto L_0x012b
            r1 = 0
            java.lang.String r0 = r11.mo18478b((java.lang.String) r1)     // Catch:{ Throwable -> 0x011e }
            r1 = 3
            com.taobao.accs.data.Message r0 = com.taobao.accs.data.Message.m3459a((java.lang.String) r15, (java.lang.String) r8, (java.lang.String) r0, (int) r1)     // Catch:{ Throwable -> 0x00d7 }
            r1 = 1
            r11.mo18482b(r0, r1)     // Catch:{ Throwable -> 0x00d7 }
            goto L_0x012b
        L_0x011e:
            r0 = move-exception
            r26 = r9
            r23 = r13
            r24 = r14
            r25 = 2
            r14 = r1
            r1 = r2
            goto L_0x0239
        L_0x012b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d7 }
            r0.<init>()     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r1 = "command error:"
            r0.append(r1)     // Catch:{ Throwable -> 0x00d7 }
            r0.append(r2)     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00d7 }
            r6 = 2
            java.lang.Object[] r1 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x00d7 }
            r5 = 0
            r1[r5] = r9     // Catch:{ Throwable -> 0x00d7 }
            r5 = 1
            r1[r5] = r8     // Catch:{ Throwable -> 0x00d7 }
            com.taobao.accs.utl.ALog.m3727e(r3, r0, r1)     // Catch:{ Throwable -> 0x00d7 }
            return
        L_0x0149:
            r6 = 2
            boolean r1 = r10.mo18434a((int) r2, (java.lang.String) r8)     // Catch:{ Throwable -> 0x022e }
            if (r1 == 0) goto L_0x017c
            if (r11 == 0) goto L_0x0170
            r7 = 0
            java.lang.String r0 = r11.mo18478b((java.lang.String) r7)     // Catch:{ Throwable -> 0x0161 }
            r1 = 3
            com.taobao.accs.data.Message r0 = com.taobao.accs.data.Message.m3459a((java.lang.String) r15, (java.lang.String) r8, (java.lang.String) r0, (int) r1)     // Catch:{ Throwable -> 0x0161 }
            r5 = 1
            r11.mo18482b(r0, r5)     // Catch:{ Throwable -> 0x0161 }
            goto L_0x0173
        L_0x0161:
            r0 = move-exception
            r1 = r2
            r2 = r8
            r26 = r9
            r23 = r13
            r24 = r14
            r25 = 2
            r13 = r3
            r14 = r7
            goto L_0x00b1
        L_0x0170:
            r1 = 3
            r5 = 1
            r7 = 0
        L_0x0173:
            java.lang.String r0 = "check space failed"
            r12 = 0
            java.lang.Object[] r1 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x0161 }
            com.taobao.accs.utl.ALog.m3727e(r3, r0, r1)     // Catch:{ Throwable -> 0x0161 }
            return
        L_0x017c:
            r7 = 0
            r17 = 1
            boolean r1 = r10.m3540b(r0, r12)     // Catch:{ Throwable -> 0x0221 }
            if (r1 == 0) goto L_0x018e
            java.lang.String r0 = "routingMsg, should not happen!"
            r1 = 0
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0161 }
            com.taobao.accs.utl.ALog.m3731w(r3, r0, r5)     // Catch:{ Throwable -> 0x0161 }
            return
        L_0x018e:
            r1 = 0
            boolean r18 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0221 }
            if (r18 != 0) goto L_0x01a6
            com.taobao.accs.client.a r1 = com.taobao.accs.client.C2018a.m3435a()     // Catch:{ Throwable -> 0x0161 }
            java.util.ArrayList r1 = r1.mo18373a(r5)     // Catch:{ Throwable -> 0x0161 }
            r18 = r19
            r20 = 3
            r21 = 0
            r19 = r1
            goto L_0x01ae
        L_0x01a6:
            r18 = r19
            r20 = 3
            r21 = 0
            r19 = r7
        L_0x01ae:
            r1 = r27
            r22 = r2
            r2 = r28
            r23 = r13
            r13 = r3
            r3 = r8
            r24 = r14
            r14 = 1
            r17 = 4
            r4 = r15
            r20 = r5
            r5 = r30
            r25 = 2
            r6 = r19
            boolean r1 = r1.mo18435a(r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x021a }
            if (r1 == 0) goto L_0x01cd
            return
        L_0x01cd:
            r1 = r27
            r2 = r30
            r3 = r20
            r4 = r22
            r5 = r16
            r6 = r8
            r14 = r7
            r7 = r15
            r21 = r8
            r8 = r19
            r26 = r9
            r9 = r18
            r1.m3536a((android.content.Intent) r2, (java.lang.String) r3, (int) r4, (java.lang.String) r5, (java.lang.String) r6, (java.lang.String) r7, (java.util.ArrayList<com.taobao.accs.IAppReceiver>) r8, (com.alibaba.sdk.android.error.ErrorCode) r9)     // Catch:{ Throwable -> 0x0213 }
            boolean r1 = android.text.TextUtils.isEmpty(r21)     // Catch:{ Throwable -> 0x0213 }
            if (r1 != 0) goto L_0x0208
            r1 = r27
            r2 = r28
            r3 = r29
            r4 = r19
            r5 = r30
            r6 = r21
            r7 = r15
            r8 = r22
            r9 = r18
            r1.mo18433a((android.content.Context) r2, (com.taobao.accs.net.C2049b) r3, (java.util.ArrayList<com.taobao.accs.IAppReceiver>) r4, (android.content.Intent) r5, (java.lang.String) r6, (java.lang.String) r7, (int) r8, (com.alibaba.sdk.android.error.ErrorCode) r9)     // Catch:{ Throwable -> 0x0201 }
            goto L_0x02fa
        L_0x0201:
            r0 = move-exception
            r2 = r21
            r1 = r22
            goto L_0x0265
        L_0x0208:
            r2 = r18
            r1 = r22
            r10.mo18432a((android.content.Context) r0, (android.content.Intent) r12, (int) r1, (com.alibaba.sdk.android.error.ErrorCode) r2)     // Catch:{ Throwable -> 0x0211 }
            goto L_0x02fa
        L_0x0211:
            r0 = move-exception
            goto L_0x0216
        L_0x0213:
            r0 = move-exception
            r1 = r22
        L_0x0216:
            r2 = r21
            goto L_0x0265
        L_0x021a:
            r0 = move-exception
            r14 = r7
            r26 = r9
            r1 = r22
            goto L_0x0256
        L_0x0221:
            r0 = move-exception
            r1 = r2
            r26 = r9
            r23 = r13
            r24 = r14
            r25 = 2
            r13 = r3
            r14 = r7
            goto L_0x023a
        L_0x022e:
            r0 = move-exception
            r1 = r2
            r26 = r9
            r23 = r13
            r24 = r14
            r14 = 0
            r25 = 2
        L_0x0239:
            r13 = r3
        L_0x023a:
            r2 = r8
            goto L_0x00b1
        L_0x023d:
            r0 = move-exception
            r1 = r2
            r26 = r9
            r23 = r13
            r24 = r14
            r14 = 0
            r25 = 2
            r13 = r3
            goto L_0x0256
        L_0x024a:
            r0 = move-exception
            r1 = r2
            r26 = r9
            r23 = r13
            r24 = r14
            r25 = 2
            r13 = r3
            r14 = r6
        L_0x0256:
            r2 = r8
            goto L_0x0265
        L_0x0258:
            r0 = move-exception
            r26 = r9
            r23 = r13
            r24 = r14
            r25 = 2
            r13 = r3
            r14 = r6
            r2 = r8
        L_0x0264:
            r1 = 0
        L_0x0265:
            r3 = 0
            goto L_0x02ba
        L_0x0267:
            r21 = r8
            r26 = r9
            r23 = r13
            r24 = r14
            r25 = 2
            r13 = r3
            r14 = r6
            if (r11 == 0) goto L_0x028b
            java.lang.String r0 = r11.mo18478b((java.lang.String) r14)     // Catch:{ Throwable -> 0x0287 }
            r2 = r21
            r3 = 5
            com.taobao.accs.data.Message r0 = com.taobao.accs.data.Message.m3459a((java.lang.String) r15, (java.lang.String) r2, (java.lang.String) r0, (int) r3)     // Catch:{ Throwable -> 0x0285 }
            r3 = 1
            r11.mo18482b(r0, r3)     // Catch:{ Throwable -> 0x0285 }
            goto L_0x028d
        L_0x0285:
            r0 = move-exception
            goto L_0x0264
        L_0x0287:
            r0 = move-exception
            r2 = r21
            goto L_0x0264
        L_0x028b:
            r2 = r21
        L_0x028d:
            java.lang.String r0 = "distribMessage action error"
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x02a8 }
            com.taobao.accs.utl.ALog.m3727e(r13, r0, r4)     // Catch:{ Throwable -> 0x02a8 }
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x02a8 }
            java.lang.String r4 = "MsgToBuss10"
            r5 = 221(0xdd, float:3.1E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x02a8 }
            r6 = 66001(0x101d1, float:9.2487E-41)
            r0.commitEvent(r6, r4, r1, r5)     // Catch:{ Throwable -> 0x02a8 }
            goto L_0x02fa
        L_0x02a8:
            r0 = move-exception
            goto L_0x02b9
        L_0x02aa:
            r0 = move-exception
            goto L_0x02b8
        L_0x02ac:
            r0 = move-exception
            r2 = r8
            r26 = r9
            r23 = r13
            r24 = r14
            r25 = 2
            r13 = r3
            r14 = r6
        L_0x02b8:
            r3 = 0
        L_0x02b9:
            r1 = 0
        L_0x02ba:
            r4 = 4
        L_0x02bb:
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r3] = r24
            r3 = 1
            r4[r3] = r15
            r4[r25] = r26
            r5 = 3
            r4[r5] = r2
            java.lang.String r6 = "distribMessage"
            com.taobao.accs.utl.ALog.m3726e(r13, r6, r0, r4)
            if (r11 == 0) goto L_0x02d9
            java.lang.String r4 = r11.mo18478b((java.lang.String) r14)
            com.taobao.accs.data.Message r4 = com.taobao.accs.data.Message.m3459a((java.lang.String) r15, (java.lang.String) r2, (java.lang.String) r4, (int) r5)
            r11.mo18482b(r4, r3)
        L_0x02d9:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "distribute error "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r0 = com.taobao.accs.utl.UtilityImpl.m3739a((java.lang.Throwable) r0)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            java.lang.String r1 = "send_fail"
            java.lang.String r3 = "1"
            r4 = r23
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r4, r1, r2, r3, r0)
        L_0x02fa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.C2033g.m3539b(android.content.Context, com.taobao.accs.net.b, android.content.Intent):void");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo18434a(int i, String str) {
        if (i != 100 && !GlobalClientInfo.AGOO_SERVICE_ID.equals(str)) {
            long c = UtilityImpl.m3753c();
            if (c != -1 && c <= 5242880) {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", "space low " + c);
                ALog.m3727e("MsgDistribute", "user space low, don't distribute", "size", Long.valueOf(c), Constants.KEY_SERVICE_ID, str);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo18435a(Context context, String str, String str2, Intent intent, ArrayList<IAppReceiver> arrayList) {
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            String str3 = null;
            if (arrayList != null) {
                Iterator<IAppReceiver> it = arrayList.iterator();
                while (it.hasNext()) {
                    str3 = it.next().getService(str);
                    if (!TextUtils.isEmpty(str3)) {
                        break;
                    }
                }
            }
            if (TextUtils.isEmpty(str3)) {
                str3 = GlobalClientInfo.getInstance(context).getService(str);
            }
            if (!TextUtils.isEmpty(str3) || UtilityImpl.isMainProcess(context)) {
                return false;
            }
            if ("accs".equals(str)) {
                ALog.m3727e("MsgDistribute", "start MsgDistributeService", Constants.KEY_DATA_ID, str2);
            } else {
                ALog.m3728i("MsgDistribute", "start MsgDistributeService", Constants.KEY_DATA_ID, str2);
            }
            intent.setClassName(intent.getPackage(), mo18436b());
            IntentDispatch.dispatchIntent(context, intent, mo18436b());
            return true;
        } catch (Throwable th) {
            ALog.m3726e("MsgDistribute", "handleMsgInChannelProcess", th, new Object[0]);
            return false;
        }
    }

    /* renamed from: a */
    private void m3536a(Intent intent, String str, int i, String str2, String str3, String str4, ArrayList<IAppReceiver> arrayList, ErrorCode errorCode) {
        int i2;
        int i3;
        int i4 = i;
        String str5 = str2;
        String str6 = str3;
        String str7 = str4;
        ErrorCode errorCode2 = errorCode;
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.m3725d("MsgDistribute", "handleControlMsg", Constants.KEY_CONFIG_TAG, str, Constants.KEY_DATA_ID, str7, Constants.KEY_SERVICE_ID, str6, "command", Integer.valueOf(i), Constants.KEY_ERROR_CODE, errorCode2);
            if (arrayList != null) {
                Iterator<IAppReceiver> it = arrayList.iterator();
                while (it.hasNext()) {
                    IAppReceiver next = it.next();
                    Object[] objArr = new Object[2];
                    objArr[0] = "appReceiver";
                    objArr[1] = next == null ? null : next.getClass().getName();
                    ALog.m3725d("MsgDistribute", "handleControlMsg", objArr);
                }
            }
        }
        if (errorCode.getCodeInt() == AccsErrorCode.SUCCESS.getCodeInt() || i4 == 103 || i4 == 101) {
            i3 = 2;
            i2 = 3;
        } else {
            i3 = 2;
            i2 = 3;
            ALog.m3727e("MsgDistribute", "handleControlMsg", "command", Integer.valueOf(i), Constants.KEY_ERROR_CODE, errorCode2);
        }
        if (arrayList != null) {
            if (i4 == 1) {
                ALog.m3725d("MsgDistribute", "onBindApp", "code", errorCode2);
                Iterator<IAppReceiver> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    C2086c.m3775a(errorCode2, it2.next(), (String) null);
                }
            } else if (i4 == i3) {
                ALog.m3725d("MsgDistribute", "onUnbindApp", "code", errorCode2);
                Iterator<IAppReceiver> it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    C2086c.m3774a(errorCode2, it3.next());
                }
            } else if (i4 == i2) {
                ALog.m3725d("MsgDistribute", "onBindUser", "code", errorCode2);
                Iterator<IAppReceiver> it4 = arrayList.iterator();
                while (it4.hasNext()) {
                    C2086c.m3777b(errorCode2, it4.next(), str5);
                }
            } else if (i4 == 4) {
                ALog.m3725d("MsgDistribute", "onUnbindUser", "code", errorCode2);
                Iterator<IAppReceiver> it5 = arrayList.iterator();
                while (it5.hasNext()) {
                    C2086c.m3776b(errorCode2, it5.next());
                }
            } else if (i4 != 100) {
                if (i4 == 101 && TextUtils.isEmpty(str3)) {
                    ALog.m3725d("MsgDistribute", "handleControlMsg serviceId isEmpty", new Object[0]);
                    byte[] byteArrayExtra = intent.getByteArrayExtra(Constants.KEY_DATA);
                    if (byteArrayExtra != null) {
                        ALog.m3725d("MsgDistribute", "onData", "code", errorCode2);
                        Iterator<IAppReceiver> it6 = arrayList.iterator();
                        while (it6.hasNext()) {
                            it6.next().onData(str5, str7, byteArrayExtra);
                        }
                    }
                }
            } else if (TextUtils.isEmpty(str3)) {
                ALog.m3725d("MsgDistribute", "handleControlMsg COMMAND_SEND_DATA serviceId isEmpty", new Object[0]);
                ALog.m3725d("MsgDistribute", "onSendData", "code", errorCode2);
                Iterator<IAppReceiver> it7 = arrayList.iterator();
                while (it7.hasNext()) {
                    it7.next().onSendData(str7, errorCode.getCodeInt());
                }
            }
        }
        if ((arrayList == null || arrayList.size() == 0) && i4 != 104 && i4 != 103) {
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str6, "1", "appReceiver null return");
            UTMini.getInstance().commitEvent(66001, "MsgToBuss7", "commandId=" + i4, "serviceId=" + str6 + " errorCode=" + errorCode2 + " dataId=" + str7, Integer.valueOf(Constants.SDK_VERSION_CODE));
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18433a(Context context, C2049b bVar, ArrayList<IAppReceiver> arrayList, Intent intent, String str, String str2, int i, ErrorCode errorCode) {
        String str3;
        ALog.m3728i("MsgDistribute", "handleBusinessMsg start", Constants.KEY_DATA_ID, str2, Constants.KEY_SERVICE_ID, str, "command", Integer.valueOf(i));
        if (arrayList != null) {
            Iterator<IAppReceiver> it = arrayList.iterator();
            str3 = null;
            while (it.hasNext()) {
                str3 = it.next().getService(str);
                if (!TextUtils.isEmpty(str3)) {
                    break;
                }
            }
        } else {
            str3 = null;
        }
        if (TextUtils.isEmpty(str3)) {
            str3 = GlobalClientInfo.getInstance(context).getService(str);
        }
        if (!TextUtils.isEmpty(str3)) {
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.m3725d("MsgDistribute", "handleBusinessMsg to start service", "className", str3);
            }
            intent.setClassName(context, str3);
            IntentDispatch.dispatchIntent(context, intent, str3);
        } else {
            AccsAbstractDataListener listener = GlobalClientInfo.getInstance(context).getListener(str);
            if (listener != null) {
                if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.m3725d("MsgDistribute", "handleBusinessMsg getListener not null", new Object[0]);
                }
                AccsAbstractDataListener.onReceiveData(context, intent, listener);
            } else {
                if (bVar != null) {
                    bVar.mo18482b(Message.m3459a(str2, str, bVar.mo18478b((String) null), 0), true);
                }
                ALog.m3727e("MsgDistribute", "handleBusinessMsg getListener also null", new Object[0]);
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, "1", "service is null");
            }
        }
        UTMini.getInstance().commitEvent(66001, "MsgToBuss", "commandId=" + i, "serviceId=" + str + " errorCode=" + errorCode + " dataId=" + str2, Integer.valueOf(Constants.SDK_VERSION_CODE));
        StringBuilder sb = new StringBuilder();
        sb.append("2commandId=");
        sb.append(i);
        sb.append("serviceId=");
        sb.append(str);
        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_TO_BUSS, sb.toString(), 0.0d);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18432a(Context context, Intent intent, int i, ErrorCode errorCode) {
        ALog.m3728i("MsgDistribute", "handBroadCastMsg", "command", Integer.valueOf(i));
        HashMap hashMap = new HashMap();
        ArrayList<IAppReceiver> b = C2018a.m3435a().mo18375b();
        if (b != null) {
            Iterator<IAppReceiver> it = b.iterator();
            while (it.hasNext()) {
                Map<String, String> allServices = it.next().getAllServices();
                if (allServices != null) {
                    hashMap.putAll(allServices);
                }
            }
        }
        if (i == 103) {
            for (String str : hashMap.keySet()) {
                if ("accs".equals(str) || "windvane".equals(str) || "motu-remote".equals(str)) {
                    String str2 = (String) hashMap.get(str);
                    if (TextUtils.isEmpty(str2)) {
                        str2 = GlobalClientInfo.getInstance(context).getService(str);
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        intent.setClassName(context, str2);
                        IntentDispatch.dispatchIntent(context, intent, str2);
                    }
                }
            }
            boolean booleanExtra = intent.getBooleanExtra(Constants.KEY_CONNECT_AVAILABLE, false);
            String stringExtra = intent.getStringExtra(Constants.KEY_HOST);
            boolean booleanExtra2 = intent.getBooleanExtra(Constants.KEY_TYPE_INAPP, false);
            boolean booleanExtra3 = intent.getBooleanExtra(Constants.KEY_CENTER_HOST, false);
            TaoBaseService.ConnectInfo connectInfo = null;
            if (!TextUtils.isEmpty(stringExtra)) {
                if (booleanExtra) {
                    connectInfo = new TaoBaseService.ConnectInfo(stringExtra, booleanExtra2, booleanExtra3);
                } else {
                    ALog.m3727e("MsgDistribute", "InAppConnection Not Available ", "error", errorCode);
                    connectInfo = new TaoBaseService.ConnectInfo(stringExtra, booleanExtra2, booleanExtra3, errorCode.getCodeInt(), errorCode.getMsg());
                }
                connectInfo.connected = booleanExtra;
            }
            if (connectInfo != null) {
                ALog.m3725d("MsgDistribute", "handBroadCastMsg ACTION_CONNECT_INFO", connectInfo);
                Intent intent2 = new Intent(Constants.ACTION_CONNECT_INFO);
                intent2.setPackage(context.getPackageName());
                intent2.putExtra(Constants.KEY_CONNECT_INFO, connectInfo);
                context.sendBroadcast(intent2, context.getPackageName() + ".ACCS");
                return;
            }
            ALog.m3727e("MsgDistribute", "handBroadCastMsg connect info null, host empty", new Object[0]);
        } else if (i == 104) {
            for (String str3 : hashMap.keySet()) {
                String str4 = (String) hashMap.get(str3);
                if (TextUtils.isEmpty(str4)) {
                    str4 = GlobalClientInfo.getInstance(context).getService(str3);
                }
                if (!TextUtils.isEmpty(str4)) {
                    intent.setClassName(context, str4);
                    IntentDispatch.dispatchIntent(context, intent, str4);
                }
            }
        } else if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
            ALog.m3725d("MsgDistribute", "handBroadCastMsg not handled command " + i, new Object[0]);
        } else {
            ALog.m3731w("MsgDistribute", "handBroadCastMsg not handled command " + i, new Object[0]);
        }
    }

    /* renamed from: a */
    private boolean m3538a(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra("routingAck", false);
        intent.getBooleanExtra("routingMsg", false);
        return booleanExtra;
    }

    /* renamed from: b */
    private boolean m3540b(Context context, Intent intent) {
        return !context.getPackageName().equals(intent.getPackage());
    }
}
