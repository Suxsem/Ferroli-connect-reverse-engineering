package com.taobao.accs.flowcontrol;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: Taobao */
public class FlowControl {
    public static final int DELAY_MAX = -1;
    public static final int DELAY_MAX_BRUSH = -1000;
    public static final int HIGH_FLOW_CTRL = 2;
    public static final int HIGH_FLOW_CTRL_BRUSH = 3;
    public static final int LOW_FLOW_CTRL = 1;
    public static final int NO_FLOW_CTRL = 0;
    public static final String SERVICE_ALL = "ALL";
    public static final String SERVICE_ALL_BRUSH = "ALL_BRUSH";
    public static final int STATUS_FLOW_CTRL_ALL = 420;
    public static final int STATUS_FLOW_CTRL_BRUSH = 422;
    public static final int STATUS_FLOW_CTRL_CUR = 421;

    /* renamed from: a */
    private Context f3330a;

    /* renamed from: b */
    private FlowCtrlInfoHolder f3331b;

    public FlowControl(Context context) {
        this.f3330a = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:65:0x0142 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0144  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int mo18440a(java.util.Map<java.lang.Integer, java.lang.String> r22, java.lang.String r23) {
        /*
            r21 = this;
            r1 = r21
            r0 = r22
            r2 = 422(0x1a6, float:5.91E-43)
            r3 = 0
            r5 = 0
            if (r0 == 0) goto L_0x013b
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r6 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_STATUS     // Catch:{ Throwable -> 0x012d }
            int r6 = r6.ordinal()     // Catch:{ Throwable -> 0x012d }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x012d }
            java.lang.Object r6 = r0.get(r6)     // Catch:{ Throwable -> 0x012d }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x012d }
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r7 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_DELAY     // Catch:{ Throwable -> 0x012d }
            int r7 = r7.ordinal()     // Catch:{ Throwable -> 0x012d }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x012d }
            java.lang.Object r7 = r0.get(r7)     // Catch:{ Throwable -> 0x012d }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Throwable -> 0x012d }
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r8 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_EXPIRE     // Catch:{ Throwable -> 0x012d }
            int r8 = r8.ordinal()     // Catch:{ Throwable -> 0x012d }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x012d }
            java.lang.Object r8 = r0.get(r8)     // Catch:{ Throwable -> 0x012d }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Throwable -> 0x012d }
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r9 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_BUSINESS     // Catch:{ Throwable -> 0x012d }
            int r9 = r9.ordinal()     // Catch:{ Throwable -> 0x012d }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x012d }
            java.lang.Object r0 = r0.get(r9)     // Catch:{ Throwable -> 0x012d }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x012d }
            boolean r9 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x012d }
            if (r9 == 0) goto L_0x0053
            r6 = 0
            goto L_0x005b
        L_0x0053:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x012d }
            int r6 = r6.intValue()     // Catch:{ Throwable -> 0x012d }
        L_0x005b:
            boolean r9 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x0129 }
            if (r9 == 0) goto L_0x0063
            r13 = r3
            goto L_0x006c
        L_0x0063:
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Throwable -> 0x0129 }
            long r9 = r7.longValue()     // Catch:{ Throwable -> 0x0129 }
            r13 = r9
        L_0x006c:
            boolean r7 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0125 }
            if (r7 == 0) goto L_0x0074
            r7 = r3
            goto L_0x007c
        L_0x0074:
            java.lang.Long r7 = java.lang.Long.valueOf(r8)     // Catch:{ Throwable -> 0x0125 }
            long r7 = r7.longValue()     // Catch:{ Throwable -> 0x0125 }
        L_0x007c:
            r9 = 421(0x1a5, float:5.9E-43)
            r10 = 420(0x1a4, float:5.89E-43)
            if (r6 == r10) goto L_0x0086
            if (r6 == r9) goto L_0x0086
            if (r6 != r2) goto L_0x008c
        L_0x0086:
            boolean r11 = r1.m3547a((long) r13, (long) r7)     // Catch:{ Throwable -> 0x0125 }
            if (r11 != 0) goto L_0x008d
        L_0x008c:
            return r5
        L_0x008d:
            monitor-enter(r21)     // Catch:{ Throwable -> 0x0125 }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r11 = r1.f3331b     // Catch:{ all -> 0x011c }
            if (r11 != 0) goto L_0x0099
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r11 = new com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder     // Catch:{ all -> 0x011c }
            r11.<init>()     // Catch:{ all -> 0x011c }
            r1.f3331b = r11     // Catch:{ all -> 0x011c }
        L_0x0099:
            r11 = 0
            if (r6 != r10) goto L_0x00b8
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r0 = new com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo     // Catch:{ all -> 0x011c }
            java.lang.String r10 = "ALL"
            java.lang.String r11 = ""
            long r17 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x011c }
            r9 = r0
            r12 = r6
            r19 = r13
            r15 = r7
            r9.<init>(r10, r11, r12, r13, r15, r17)     // Catch:{ all -> 0x0123 }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r7 = r1.f3331b     // Catch:{ all -> 0x0123 }
            java.lang.String r8 = "ALL"
            java.lang.String r9 = ""
            r7.put(r8, r9, r0)     // Catch:{ all -> 0x0123 }
            goto L_0x00fc
        L_0x00b8:
            r19 = r13
            if (r6 != r2) goto L_0x00d8
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r0 = new com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo     // Catch:{ all -> 0x0123 }
            java.lang.String r10 = "ALL_BRUSH"
            java.lang.String r11 = ""
            long r17 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0123 }
            r9 = r0
            r12 = r6
            r13 = r19
            r15 = r7
            r9.<init>(r10, r11, r12, r13, r15, r17)     // Catch:{ all -> 0x0123 }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r7 = r1.f3331b     // Catch:{ all -> 0x0123 }
            java.lang.String r8 = "ALL_BRUSH"
            java.lang.String r9 = ""
            r7.put(r8, r9, r0)     // Catch:{ all -> 0x0123 }
            goto L_0x00fc
        L_0x00d8:
            if (r6 != r9) goto L_0x00fb
            boolean r9 = android.text.TextUtils.isEmpty(r23)     // Catch:{ all -> 0x0123 }
            if (r9 != 0) goto L_0x00fb
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r15 = new com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo     // Catch:{ all -> 0x0123 }
            long r17 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0123 }
            r9 = r15
            r10 = r23
            r11 = r0
            r12 = r6
            r13 = r19
            r2 = r15
            r15 = r7
            r9.<init>(r10, r11, r12, r13, r15, r17)     // Catch:{ all -> 0x0123 }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r7 = r1.f3331b     // Catch:{ all -> 0x0123 }
            r8 = r23
            r7.put(r8, r0, r2)     // Catch:{ all -> 0x0123 }
            r0 = r2
            goto L_0x00fc
        L_0x00fb:
            r0 = r11
        L_0x00fc:
            if (r0 == 0) goto L_0x011a
            java.lang.String r2 = "FlowControl"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0123 }
            r7.<init>()     // Catch:{ all -> 0x0123 }
            java.lang.String r8 = "updateFlowCtrlInfo "
            r7.append(r8)     // Catch:{ all -> 0x0123 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0123 }
            r7.append(r0)     // Catch:{ all -> 0x0123 }
            java.lang.String r0 = r7.toString()     // Catch:{ all -> 0x0123 }
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ all -> 0x0123 }
            com.taobao.accs.utl.ALog.m3727e(r2, r0, r7)     // Catch:{ all -> 0x0123 }
        L_0x011a:
            monitor-exit(r21)     // Catch:{ all -> 0x0123 }
            goto L_0x013e
        L_0x011c:
            r0 = move-exception
            r19 = r13
        L_0x011f:
            monitor-exit(r21)     // Catch:{ all -> 0x0123 }
            throw r0     // Catch:{ Throwable -> 0x0121 }
        L_0x0121:
            r0 = move-exception
            goto L_0x0131
        L_0x0123:
            r0 = move-exception
            goto L_0x011f
        L_0x0125:
            r0 = move-exception
            r19 = r13
            goto L_0x0131
        L_0x0129:
            r0 = move-exception
            r19 = r3
            goto L_0x0131
        L_0x012d:
            r0 = move-exception
            r19 = r3
            r6 = 0
        L_0x0131:
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r7 = "FlowControl"
            java.lang.String r8 = "updateFlowCtrlInfo"
            com.taobao.accs.utl.ALog.m3726e(r7, r8, r0, r2)
            goto L_0x013e
        L_0x013b:
            r19 = r3
            r6 = 0
        L_0x013e:
            int r0 = (r19 > r3 ? 1 : (r19 == r3 ? 0 : -1))
            if (r0 <= 0) goto L_0x0144
            r0 = 1
            return r0
        L_0x0144:
            int r0 = (r19 > r3 ? 1 : (r19 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0149
            return r5
        L_0x0149:
            r2 = 422(0x1a6, float:5.91E-43)
            if (r2 != r6) goto L_0x014f
            r0 = 3
            return r0
        L_0x014f:
            r0 = 2
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.flowcontrol.FlowControl.mo18440a(java.util.Map, java.lang.String):int");
    }

    /* renamed from: a */
    private boolean m3547a(long j, long j2) {
        if (j != 0 && j2 > 0) {
            return true;
        }
        ALog.m3727e("FlowControl", "error flow ctrl info", new Object[0]);
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0071  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long mo18441a(java.lang.String r14, java.lang.String r15) {
        /*
            r13 = this;
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r0 = r13.f3331b
            r1 = 0
            if (r0 == 0) goto L_0x00df
            java.util.Map<java.lang.String, com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo> r0 = r0.flowCtrlMap
            if (r0 == 0) goto L_0x00df
            boolean r0 = android.text.TextUtils.isEmpty(r14)
            if (r0 == 0) goto L_0x0012
            goto L_0x00df
        L_0x0012:
            monitor-enter(r13)
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r0 = r13.f3331b     // Catch:{ all -> 0x00dc }
            java.lang.String r3 = "ALL"
            r4 = 0
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r0 = r0.get(r3, r4)     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r3 = r13.f3331b     // Catch:{ all -> 0x00dc }
            java.lang.String r5 = "ALL_BRUSH"
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r3 = r3.get(r5, r4)     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r5 = r13.f3331b     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r4 = r5.get(r14, r4)     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r5 = r13.f3331b     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r5 = r5.get(r14, r15)     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x003c
            boolean r6 = r0.isExpired()     // Catch:{ all -> 0x00dc }
            if (r6 == 0) goto L_0x0039
            goto L_0x003c
        L_0x0039:
            long r6 = r0.delayTime     // Catch:{ all -> 0x00dc }
            goto L_0x003d
        L_0x003c:
            r6 = r1
        L_0x003d:
            if (r3 == 0) goto L_0x0049
            boolean r8 = r3.isExpired()     // Catch:{ all -> 0x00dc }
            if (r8 == 0) goto L_0x0046
            goto L_0x0049
        L_0x0046:
            long r8 = r3.delayTime     // Catch:{ all -> 0x00dc }
            goto L_0x004a
        L_0x0049:
            r8 = r1
        L_0x004a:
            if (r4 == 0) goto L_0x0056
            boolean r3 = r4.isExpired()     // Catch:{ all -> 0x00dc }
            if (r3 == 0) goto L_0x0053
            goto L_0x0056
        L_0x0053:
            long r3 = r4.delayTime     // Catch:{ all -> 0x00dc }
            goto L_0x0057
        L_0x0056:
            r3 = r1
        L_0x0057:
            if (r5 == 0) goto L_0x0062
            boolean r10 = r5.isExpired()     // Catch:{ all -> 0x00dc }
            if (r10 == 0) goto L_0x0060
            goto L_0x0062
        L_0x0060:
            long r1 = r5.delayTime     // Catch:{ all -> 0x00dc }
        L_0x0062:
            r10 = -1
            int r12 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r12 == 0) goto L_0x0086
            int r12 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r12 == 0) goto L_0x0086
            int r12 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r12 != 0) goto L_0x0071
            goto L_0x0086
        L_0x0071:
            int r12 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r12 != 0) goto L_0x0078
            r10 = -1000(0xfffffffffffffc18, double:NaN)
            goto L_0x0086
        L_0x0078:
            int r8 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r8 <= 0) goto L_0x007e
            r8 = r6
            goto L_0x007f
        L_0x007e:
            r8 = r1
        L_0x007f:
            int r10 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r10 <= 0) goto L_0x0085
            r10 = r8
            goto L_0x0086
        L_0x0085:
            r10 = r3
        L_0x0086:
            if (r5 == 0) goto L_0x008e
            boolean r5 = r5.isExpired()     // Catch:{ all -> 0x00dc }
            if (r5 != 0) goto L_0x0096
        L_0x008e:
            if (r0 == 0) goto L_0x0099
            boolean r0 = r0.isExpired()     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x0099
        L_0x0096:
            r13.m3546a()     // Catch:{ all -> 0x00dc }
        L_0x0099:
            monitor-exit(r13)     // Catch:{ all -> 0x00dc }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r5 = "getFlowCtrlDelay service "
            r0.append(r5)
            r0.append(r14)
            java.lang.String r14 = " biz "
            r0.append(r14)
            r0.append(r15)
            java.lang.String r14 = " result:"
            r0.append(r14)
            r0.append(r10)
            java.lang.String r14 = " global:"
            r0.append(r14)
            r0.append(r6)
            java.lang.String r14 = " serviceDelay:"
            r0.append(r14)
            r0.append(r3)
            java.lang.String r14 = " bidDelay:"
            r0.append(r14)
            r0.append(r1)
            java.lang.String r14 = r0.toString()
            r15 = 0
            java.lang.Object[] r15 = new java.lang.Object[r15]
            java.lang.String r0 = "FlowControl"
            com.taobao.accs.utl.ALog.m3727e(r0, r14, r15)
            return r10
        L_0x00dc:
            r14 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x00dc }
            throw r14
        L_0x00df:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.flowcontrol.FlowControl.mo18441a(java.lang.String, java.lang.String):long");
    }

    /* renamed from: a */
    private void m3546a() {
        FlowCtrlInfoHolder flowCtrlInfoHolder = this.f3331b;
        if (flowCtrlInfoHolder != null && flowCtrlInfoHolder.flowCtrlMap != null) {
            synchronized (this) {
                Iterator<Map.Entry<String, FlowControlInfo>> it = this.f3331b.flowCtrlMap.entrySet().iterator();
                while (it.hasNext()) {
                    if (((FlowControlInfo) it.next().getValue()).isExpired()) {
                        it.remove();
                    }
                }
            }
        }
    }

    /* compiled from: Taobao */
    public static class FlowControlInfo implements Serializable {
        private static final long serialVersionUID = -2259991484877844919L;
        public String bizId;
        public long delayTime;
        public long expireTime;
        public String serviceId;
        public long startTime;
        public int status;

        public FlowControlInfo(String str, String str2, int i, long j, long j2, long j3) {
            this.serviceId = str;
            this.bizId = str2;
            this.status = i;
            this.delayTime = j;
            long j4 = 0;
            this.expireTime = j2 <= 0 ? 0 : j2;
            this.startTime = j3 > 0 ? j3 : j4;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - (this.startTime + this.expireTime) > 0;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("flow ctrl serviceId:");
            stringBuffer.append(this.serviceId);
            stringBuffer.append(" bizId:");
            stringBuffer.append(this.bizId);
            stringBuffer.append(" status:");
            stringBuffer.append(this.status);
            stringBuffer.append(" delayTime:");
            stringBuffer.append(this.delayTime);
            stringBuffer.append(" startTime:");
            stringBuffer.append(this.startTime);
            stringBuffer.append(" expireTime:");
            stringBuffer.append(this.expireTime);
            return stringBuffer.toString();
        }
    }

    /* compiled from: Taobao */
    public static class FlowCtrlInfoHolder implements Serializable {
        private static final long serialVersionUID = 6307563052429742524L;
        Map<String, FlowControlInfo> flowCtrlMap = null;

        public void put(String str, String str2, FlowControlInfo flowControlInfo) {
            if (!TextUtils.isEmpty(str2)) {
                str = str + "_" + str2;
            }
            if (this.flowCtrlMap == null) {
                this.flowCtrlMap = new HashMap();
            }
            this.flowCtrlMap.put(str, flowControlInfo);
        }

        public FlowControlInfo get(String str, String str2) {
            if (this.flowCtrlMap == null) {
                return null;
            }
            if (!TextUtils.isEmpty(str2)) {
                str = str + "_" + str2;
            }
            return this.flowCtrlMap.get(str);
        }
    }
}
