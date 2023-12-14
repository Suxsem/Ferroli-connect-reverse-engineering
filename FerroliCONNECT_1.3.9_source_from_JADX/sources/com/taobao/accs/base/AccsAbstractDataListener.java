package com.taobao.accs.base;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public abstract class AccsAbstractDataListener implements AccsDataListenerV2 {
    private static final String TAG = "AccsAbstractDataListener";

    public void onAntiBrush(boolean z, TaoBaseService.ExtraInfo extraInfo) {
    }

    public void onBind(String str, int i, TaoBaseService.ExtraInfo extraInfo) {
    }

    public void onConnected(TaoBaseService.ConnectInfo connectInfo) {
    }

    public void onDisconnected(TaoBaseService.ConnectInfo connectInfo) {
    }

    public void onResponse(String str, String str2, int i, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
    }

    public void onSendData(String str, String str2, int i, TaoBaseService.ExtraInfo extraInfo) {
    }

    public void onUnbind(String str, int i, TaoBaseService.ExtraInfo extraInfo) {
    }

    public void onBind(String str, int i, String str2, TaoBaseService.ExtraInfo extraInfo) {
        onBind(str, i, extraInfo);
    }

    public void onUnbind(String str, int i, String str2, TaoBaseService.ExtraInfo extraInfo) {
        onUnbind(str, i, extraInfo);
    }

    public void onResponse(String str, String str2, int i, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
        onResponse(str, str2, i, bArr, extraInfo);
    }

    public void onSendData(String str, String str2, int i, String str3, TaoBaseService.ExtraInfo extraInfo) {
        onSendData(str, str2, i, extraInfo);
    }

    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r7v6 */
    /* JADX WARNING: type inference failed for: r7v15 */
    /* JADX WARNING: type inference failed for: r7v17 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int onReceiveData(android.content.Context r28, android.content.Intent r29, com.taobao.accs.base.AccsDataListenerV2 r30) {
        /*
            r0 = r28
            r1 = r29
            r2 = r30
            java.lang.String r3 = "serviceId="
            java.lang.String r7 = "onReceiveData"
            java.lang.String r4 = "serviceId"
            java.lang.String r8 = "1"
            java.lang.String r9 = "send_fail"
            java.lang.String r5 = "command"
            java.lang.String r6 = "dataId"
            java.lang.String r10 = "accs"
            java.lang.String r12 = "AccsAbstractDataListener"
            if (r2 == 0) goto L_0x02b3
            if (r0 != 0) goto L_0x001e
            goto L_0x02b3
        L_0x001e:
            if (r1 == 0) goto L_0x02b1
            java.lang.String r14 = ""
            r15 = -1
            int r15 = r1.getIntExtra(r5, r15)     // Catch:{ Exception -> 0x028d }
            com.alibaba.sdk.android.error.ErrorCode r16 = com.taobao.accs.common.Constants.getErrorCode(r29)     // Catch:{ Exception -> 0x028d }
            java.lang.String r11 = "userInfo"
            java.lang.String r11 = r1.getStringExtra(r11)     // Catch:{ Exception -> 0x028d }
            java.lang.String r13 = r1.getStringExtra(r6)     // Catch:{ Exception -> 0x028d }
            java.lang.String r14 = r1.getStringExtra(r4)     // Catch:{ Exception -> 0x028d }
            com.taobao.accs.utl.ALog$Level r19 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Exception -> 0x028d }
            boolean r19 = com.taobao.accs.utl.ALog.isPrintLog(r19)     // Catch:{ Exception -> 0x028d }
            r20 = r8
            if (r19 == 0) goto L_0x0073
            r8 = 8
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x026b }
            r18 = 0
            r8[r18] = r6     // Catch:{ Exception -> 0x026b }
            r22 = 1
            r8[r22] = r13     // Catch:{ Exception -> 0x026b }
            r17 = 2
            r8[r17] = r4     // Catch:{ Exception -> 0x026b }
            r4 = 3
            r8[r4] = r14     // Catch:{ Exception -> 0x026b }
            r4 = 4
            r8[r4] = r5     // Catch:{ Exception -> 0x026b }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r15)     // Catch:{ Exception -> 0x026b }
            r5 = 5
            r8[r5] = r4     // Catch:{ Exception -> 0x026b }
            java.lang.String r4 = "className"
            r5 = 6
            r8[r5] = r4     // Catch:{ Exception -> 0x026b }
            r4 = 7
            java.lang.Class r5 = r30.getClass()     // Catch:{ Exception -> 0x026b }
            java.lang.String r5 = r5.getName()     // Catch:{ Exception -> 0x026b }
            r8[r4] = r5     // Catch:{ Exception -> 0x026b }
            com.taobao.accs.utl.ALog.m3728i(r12, r7, r8)     // Catch:{ Exception -> 0x026b }
        L_0x0073:
            java.lang.String r4 = "onReceiveData command not handled "
            if (r15 <= 0) goto L_0x0270
            com.taobao.accs.utl.UTMini r22 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Exception -> 0x026b }
            r23 = 66001(0x101d1, float:9.2487E-41)
            java.lang.String r24 = "MsgToBuss5"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x026b }
            r5.<init>()     // Catch:{ Exception -> 0x026b }
            java.lang.String r8 = "commandId="
            r5.append(r8)     // Catch:{ Exception -> 0x026b }
            r5.append(r15)     // Catch:{ Exception -> 0x026b }
            java.lang.String r25 = r5.toString()     // Catch:{ Exception -> 0x026b }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x026b }
            r5.<init>()     // Catch:{ Exception -> 0x026b }
            r5.append(r3)     // Catch:{ Exception -> 0x026b }
            r5.append(r14)     // Catch:{ Exception -> 0x026b }
            java.lang.String r8 = " dataId="
            r5.append(r8)     // Catch:{ Exception -> 0x026b }
            r5.append(r13)     // Catch:{ Exception -> 0x026b }
            java.lang.String r26 = r5.toString()     // Catch:{ Exception -> 0x026b }
            r5 = 221(0xdd, float:3.1E-43)
            java.lang.Integer r27 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x026b }
            r22.commitEvent(r23, r24, r25, r26, r27)     // Catch:{ Exception -> 0x026b }
            java.lang.String r5 = "to_buss"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x026b }
            r8.<init>()     // Catch:{ Exception -> 0x026b }
            java.lang.String r13 = "3commandId="
            r8.append(r13)     // Catch:{ Exception -> 0x026b }
            r8.append(r15)     // Catch:{ Exception -> 0x026b }
            r8.append(r3)     // Catch:{ Exception -> 0x026b }
            r8.append(r14)     // Catch:{ Exception -> 0x026b }
            java.lang.String r3 = r8.toString()     // Catch:{ Exception -> 0x026b }
            r13 = r7
            r7 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r10, r5, r3, r7)     // Catch:{ Exception -> 0x0269 }
            r3 = 5
            if (r15 == r3) goto L_0x025b
            r3 = 6
            if (r15 == r3) goto L_0x024d
            r3 = 100
            if (r15 == r3) goto L_0x0203
            r3 = 101(0x65, float:1.42E-43)
            if (r15 == r3) goto L_0x0166
            r0 = 103(0x67, float:1.44E-43)
            if (r15 == r0) goto L_0x0121
            r0 = 104(0x68, float:1.46E-43)
            if (r15 == r0) goto L_0x00fd
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0269 }
            r0.<init>()     // Catch:{ Exception -> 0x0269 }
            r0.append(r4)     // Catch:{ Exception -> 0x0269 }
            r0.append(r15)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0269 }
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0269 }
            com.taobao.accs.utl.ALog.m3731w(r12, r0, r2)     // Catch:{ Exception -> 0x0269 }
            goto L_0x02b1
        L_0x00fd:
            java.lang.String r0 = "anti_brush_ret"
            r3 = 0
            boolean r0 = r1.getBooleanExtra(r0, r3)     // Catch:{ Exception -> 0x0269 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0269 }
            r1.<init>()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r3 = "onReceiveData anti brush result:"
            r1.append(r3)     // Catch:{ Exception -> 0x0269 }
            r1.append(r0)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0269 }
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0269 }
            com.taobao.accs.utl.ALog.m3727e(r12, r1, r4)     // Catch:{ Exception -> 0x0269 }
            r1 = 0
            r2.onAntiBrush(r0, r1)     // Catch:{ Exception -> 0x0269 }
            goto L_0x02b1
        L_0x0121:
            java.lang.String r0 = "connect_avail"
            r3 = 0
            boolean r0 = r1.getBooleanExtra(r0, r3)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r4 = "host"
            java.lang.String r4 = r1.getStringExtra(r4)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r5 = "type_inapp"
            boolean r5 = r1.getBooleanExtra(r5, r3)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r6 = "is_center_host"
            boolean r1 = r1.getBooleanExtra(r6, r3)     // Catch:{ Exception -> 0x0269 }
            boolean r3 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0269 }
            if (r3 != 0) goto L_0x02b1
            if (r0 == 0) goto L_0x014c
            com.taobao.accs.base.TaoBaseService$ConnectInfo r0 = new com.taobao.accs.base.TaoBaseService$ConnectInfo     // Catch:{ Exception -> 0x0269 }
            r0.<init>(r4, r5, r1)     // Catch:{ Exception -> 0x0269 }
            r2.onConnected(r0)     // Catch:{ Exception -> 0x0269 }
            goto L_0x02b1
        L_0x014c:
            com.taobao.accs.base.TaoBaseService$ConnectInfo r0 = new com.taobao.accs.base.TaoBaseService$ConnectInfo     // Catch:{ Exception -> 0x0269 }
            int r25 = r16.getCodeInt()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r26 = r16.getMsg()     // Catch:{ Exception -> 0x0269 }
            r21 = r0
            r22 = r4
            r23 = r5
            r24 = r1
            r21.<init>(r22, r23, r24, r25, r26)     // Catch:{ Exception -> 0x0269 }
            r2.onDisconnected(r0)     // Catch:{ Exception -> 0x0269 }
            goto L_0x02b1
        L_0x0166:
            java.lang.String r3 = "data"
            byte[] r4 = r1.getByteArrayExtra(r3)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r3 = "bizAck"
            r5 = 0
            boolean r3 = r1.getBooleanExtra(r3, r5)     // Catch:{ Exception -> 0x0269 }
            if (r4 == 0) goto L_0x01f2
            java.lang.String r5 = r1.getStringExtra(r6)     // Catch:{ Exception -> 0x0269 }
            com.taobao.accs.utl.ALog$Level r6 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ Exception -> 0x0269 }
            boolean r6 = com.taobao.accs.utl.ALog.isPrintLog(r6)     // Catch:{ Exception -> 0x0269 }
            if (r6 == 0) goto L_0x01a0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0269 }
            r6.<init>()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r15 = "onReceiveData COMMAND_RECEIVE_DATA onData dataId:"
            r6.append(r15)     // Catch:{ Exception -> 0x0269 }
            r6.append(r5)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r15 = " serviceId:"
            r6.append(r15)     // Catch:{ Exception -> 0x0269 }
            r6.append(r14)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0269 }
            r15 = 0
            java.lang.Object[] r7 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x0269 }
            com.taobao.accs.utl.ALog.m3725d(r12, r6, r7)     // Catch:{ Exception -> 0x0269 }
        L_0x01a0:
            com.taobao.accs.base.TaoBaseService$ExtraInfo r6 = getExtraInfo(r29)     // Catch:{ Exception -> 0x0269 }
            if (r3 == 0) goto L_0x01c2
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0269 }
            r3.<init>()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r7 = "onReceiveData try to send biz ack dataId "
            r3.append(r7)     // Catch:{ Exception -> 0x0269 }
            r3.append(r5)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0269 }
            r7 = 0
            java.lang.Object[] r8 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x0269 }
            com.taobao.accs.utl.ALog.m3728i(r12, r3, r8)     // Catch:{ Exception -> 0x0269 }
            java.util.Map<java.lang.Integer, java.lang.String> r3 = r6.oriExtHeader     // Catch:{ Exception -> 0x0269 }
            sendBusinessAck(r0, r1, r5, r3)     // Catch:{ Exception -> 0x0269 }
        L_0x01c2:
            java.lang.String r0 = "monitor"
            java.io.Serializable r0 = r1.getSerializableExtra(r0)     // Catch:{ Exception -> 0x0269 }
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r0 = (com.taobao.accs.p103ut.monitor.NetPerformanceMonitor) r0     // Catch:{ Exception -> 0x0269 }
            if (r0 == 0) goto L_0x01cf
            r0.onToAccsTime()     // Catch:{ Exception -> 0x0269 }
        L_0x01cf:
            java.lang.String r0 = "to_buss_success"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0269 }
            r1.<init>()     // Catch:{ Exception -> 0x0269 }
            java.lang.String r3 = "1commandId=101serviceId="
            r1.append(r3)     // Catch:{ Exception -> 0x0269 }
            r1.append(r14)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0269 }
            r7 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r10, r0, r1, r7)     // Catch:{ Exception -> 0x0269 }
            r0 = r30
            r1 = r14
            r2 = r11
            r3 = r5
            r5 = r6
            r0.onData(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0269 }
            goto L_0x02b1
        L_0x01f2:
            java.lang.String r0 = "onReceiveData COMMAND_RECEIVE_DATA msg null"
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0269 }
            com.taobao.accs.utl.ALog.m3727e(r12, r0, r2)     // Catch:{ Exception -> 0x0269 }
            java.lang.String r0 = "COMMAND_RECEIVE_DATA msg null"
            r7 = r20
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r10, r9, r14, r7, r0)     // Catch:{ Exception -> 0x0289 }
            goto L_0x02b1
        L_0x0203:
            r7 = r20
            java.lang.String r3 = r1.getStringExtra(r6)     // Catch:{ Exception -> 0x0289 }
            java.lang.String r0 = "res"
            java.lang.String r4 = "send_type"
            java.lang.String r4 = r1.getStringExtra(r4)     // Catch:{ Exception -> 0x0289 }
            boolean r0 = android.text.TextUtils.equals(r0, r4)     // Catch:{ Exception -> 0x0289 }
            if (r0 == 0) goto L_0x0235
            java.lang.String r0 = "data"
            byte[] r5 = r1.getByteArrayExtra(r0)     // Catch:{ Exception -> 0x0289 }
            int r4 = r16.getCodeInt()     // Catch:{ Exception -> 0x0289 }
            java.lang.String r6 = r16.getMsg()     // Catch:{ Exception -> 0x0289 }
            com.taobao.accs.base.TaoBaseService$ExtraInfo r8 = getExtraInfo(r29)     // Catch:{ Exception -> 0x0289 }
            r0 = r30
            r1 = r14
            r2 = r3
            r3 = r4
            r4 = r6
            r6 = r8
            r0.onResponse(r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x0289 }
            goto L_0x02b1
        L_0x0235:
            int r4 = r16.getCodeInt()     // Catch:{ Exception -> 0x0289 }
            java.lang.String r5 = r16.getMsg()     // Catch:{ Exception -> 0x0289 }
            com.taobao.accs.base.TaoBaseService$ExtraInfo r6 = getExtraInfo(r29)     // Catch:{ Exception -> 0x0289 }
            r0 = r30
            r1 = r14
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r0.onSendData(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0289 }
            goto L_0x02b1
        L_0x024d:
            r7 = r20
            int r0 = r16.getCodeInt()     // Catch:{ Exception -> 0x0289 }
            com.taobao.accs.base.TaoBaseService$ExtraInfo r1 = getExtraInfo(r29)     // Catch:{ Exception -> 0x0289 }
            r2.onUnbind(r14, r0, r1)     // Catch:{ Exception -> 0x0289 }
            goto L_0x02b1
        L_0x025b:
            r7 = r20
            int r0 = r16.getCodeInt()     // Catch:{ Exception -> 0x0289 }
            com.taobao.accs.base.TaoBaseService$ExtraInfo r1 = getExtraInfo(r29)     // Catch:{ Exception -> 0x0289 }
            r2.onBind(r14, r0, r1)     // Catch:{ Exception -> 0x0289 }
            goto L_0x02b1
        L_0x0269:
            r0 = move-exception
            goto L_0x026d
        L_0x026b:
            r0 = move-exception
            r13 = r7
        L_0x026d:
            r7 = r20
            goto L_0x0290
        L_0x0270:
            r13 = r7
            r7 = r20
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0289 }
            r0.<init>()     // Catch:{ Exception -> 0x0289 }
            r0.append(r4)     // Catch:{ Exception -> 0x0289 }
            r0.append(r15)     // Catch:{ Exception -> 0x0289 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0289 }
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0289 }
            com.taobao.accs.utl.ALog.m3731w(r12, r0, r2)     // Catch:{ Exception -> 0x0289 }
            goto L_0x02b1
        L_0x0289:
            r0 = move-exception
            goto L_0x0290
        L_0x028b:
            r0 = move-exception
            goto L_0x02b0
        L_0x028d:
            r0 = move-exception
            r13 = r7
            r7 = r8
        L_0x0290:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x028b }
            r1.<init>()     // Catch:{ all -> 0x028b }
            java.lang.String r2 = "callback error"
            r1.append(r2)     // Catch:{ all -> 0x028b }
            java.lang.String r2 = r0.toString()     // Catch:{ all -> 0x028b }
            r1.append(r2)     // Catch:{ all -> 0x028b }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x028b }
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r10, r9, r14, r7, r1)     // Catch:{ all -> 0x028b }
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x028b }
            r2 = r13
            com.taobao.accs.utl.ALog.m3726e(r12, r2, r0, r1)     // Catch:{ all -> 0x028b }
            goto L_0x02b1
        L_0x02b0:
            throw r0
        L_0x02b1:
            r1 = 2
            return r1
        L_0x02b3:
            r1 = 2
            r2 = 0
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r2 = "onReceiveData listener or context null"
            com.taobao.accs.utl.ALog.m3727e(r12, r2, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.base.AccsAbstractDataListener.onReceiveData(android.content.Context, android.content.Intent, com.taobao.accs.base.AccsDataListenerV2):int");
    }

    private static Map<TaoBaseService.ExtHeaderType, String> getExtHeader(Map<Integer, String> map) {
        HashMap hashMap;
        if (map == null) {
            return null;
        }
        try {
            hashMap = new HashMap();
            try {
                for (TaoBaseService.ExtHeaderType extHeaderType : TaoBaseService.ExtHeaderType.values()) {
                    String str = map.get(Integer.valueOf(extHeaderType.ordinal()));
                    if (!TextUtils.isEmpty(str)) {
                        hashMap.put(extHeaderType, str);
                    }
                }
            } catch (Exception e) {
                e = e;
                ALog.m3726e(TAG, "getExtHeader", e, new Object[0]);
                return hashMap;
            }
        } catch (Exception e2) {
            e = e2;
            hashMap = null;
            ALog.m3726e(TAG, "getExtHeader", e, new Object[0]);
            return hashMap;
        }
        return hashMap;
    }

    private static TaoBaseService.ExtraInfo getExtraInfo(Intent intent) {
        TaoBaseService.ExtraInfo extraInfo = new TaoBaseService.ExtraInfo();
        try {
            HashMap hashMap = (HashMap) intent.getSerializableExtra(TaoBaseService.ExtraInfo.EXT_HEADER);
            Map<TaoBaseService.ExtHeaderType, String> extHeader = getExtHeader(hashMap);
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_HOST);
            extraInfo.connType = intent.getIntExtra(Constants.KEY_CONN_TYPE, 0);
            extraInfo.extHeader = extHeader;
            extraInfo.oriExtHeader = hashMap;
            extraInfo.fromPackage = stringExtra;
            extraInfo.fromHost = stringExtra2;
        } catch (Throwable th) {
            ALog.m3726e(TAG, "getExtraInfo", th, new Object[0]);
        }
        return extraInfo;
    }

    private static void sendBusinessAck(Context context, Intent intent, String str, Map<Integer, String> map) {
        Intent intent2 = intent;
        try {
            ALog.m3728i(TAG, "sendBusinessAck", Constants.KEY_DATA_ID, str);
            if (intent2 != null) {
                String stringExtra = intent2.getStringExtra(Constants.KEY_HOST);
                String stringExtra2 = intent2.getStringExtra("source");
                String stringExtra3 = intent2.getStringExtra(Constants.KEY_TARGET);
                String stringExtra4 = intent2.getStringExtra(Constants.KEY_APP_KEY);
                String stringExtra5 = intent2.getStringExtra(Constants.KEY_CONFIG_TAG);
                short shortExtra = intent2.getShortExtra(Constants.KEY_FLAGS, 0);
                IACCSManager accsInstance = ACCSManager.getAccsInstance(context, stringExtra4, stringExtra5);
                if (accsInstance != null) {
                    accsInstance.sendBusinessAck(stringExtra3, stringExtra2, str, shortExtra, stringExtra, map);
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_BUSINESS_ACK_SUCC, "", 0.0d);
                    return;
                }
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_BUSINESS_ACK_FAIL, "no acsmgr", 0.0d);
            }
        } catch (Throwable th) {
            ALog.m3726e(TAG, "sendBusinessAck", th, new Object[0]);
            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_BUSINESS_ACK_FAIL, th.toString(), 0.0d);
        }
    }
}
