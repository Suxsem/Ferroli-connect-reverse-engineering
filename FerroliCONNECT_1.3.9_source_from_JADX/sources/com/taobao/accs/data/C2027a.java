package com.taobao.accs.data;

import com.igexin.sdk.GTIntentService;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* renamed from: com.taobao.accs.data.a */
/* compiled from: Taobao */
public class C2027a {
    public static final int SPLITTED_DATA_INDEX = 17;
    public static final int SPLITTED_DATA_MD5 = 18;
    public static final int SPLITTED_DATA_NUMS = 16;
    public static final int SPLITTED_TIME_OUT = 15;

    /* renamed from: a */
    private static final char[] f3295a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /* access modifiers changed from: private */

    /* renamed from: b */
    public String f3296b;

    /* renamed from: c */
    private int f3297c;

    /* renamed from: d */
    private String f3298d;

    /* renamed from: e */
    private long f3299e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public volatile int f3300f = 0;

    /* renamed from: g */
    private ScheduledFuture<?> f3301g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public Map<Integer, byte[]> f3302h = new TreeMap(new C2028b(this));

    public C2027a(String str, int i, String str2) {
        this.f3296b = str;
        this.f3297c = i;
        this.f3298d = str2;
    }

    /* renamed from: a */
    public void mo18406a(long j) {
        if (j <= 0) {
            j = GTIntentService.WAIT_TIME;
        }
        this.f3301g = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new C2029c(this), j, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0143, code lost:
        return r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x015a, code lost:
        return null;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] mo18407a(int r13, int r14, byte[] r15) {
        /*
            r12 = this;
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            r1 = 4
            r2 = 3
            r3 = 1
            r4 = 2
            r5 = 0
            if (r0 == 0) goto L_0x0028
            java.lang.Object[] r0 = new java.lang.Object[r1]
            java.lang.String r6 = "dataId"
            r0[r5] = r6
            java.lang.String r6 = r12.f3296b
            r0[r3] = r6
            java.lang.String r6 = "index"
            r0[r4] = r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r13)
            r0[r2] = r6
            java.lang.String r6 = "AssembleMessage"
            java.lang.String r7 = "putBurst"
            com.taobao.accs.utl.ALog.m3725d(r6, r7, r0)
        L_0x0028:
            int r0 = r12.f3297c
            r6 = 0
            if (r14 == r0) goto L_0x0037
            java.lang.Object[] r13 = new java.lang.Object[r5]
            java.lang.String r14 = "AssembleMessage"
            java.lang.String r15 = "putBurst fail as burstNums not match"
            com.taobao.accs.utl.ALog.m3727e(r14, r15, r13)
            return r6
        L_0x0037:
            if (r13 < 0) goto L_0x015e
            if (r13 < r14) goto L_0x003d
            goto L_0x015e
        L_0x003d:
            monitor-enter(r12)
            int r14 = r12.f3300f     // Catch:{ all -> 0x015b }
            if (r14 != 0) goto L_0x0144
            java.util.Map<java.lang.Integer, byte[]> r14 = r12.f3302h     // Catch:{ all -> 0x015b }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x015b }
            java.lang.Object r14 = r14.get(r0)     // Catch:{ all -> 0x015b }
            if (r14 == 0) goto L_0x0059
            java.lang.String r13 = "AssembleMessage"
            java.lang.String r14 = "putBurst fail as exist old"
            java.lang.Object[] r15 = new java.lang.Object[r5]     // Catch:{ all -> 0x015b }
            com.taobao.accs.utl.ALog.m3727e(r13, r14, r15)     // Catch:{ all -> 0x015b }
            monitor-exit(r12)     // Catch:{ all -> 0x015b }
            return r6
        L_0x0059:
            java.util.Map<java.lang.Integer, byte[]> r14 = r12.f3302h     // Catch:{ all -> 0x015b }
            boolean r14 = r14.isEmpty()     // Catch:{ all -> 0x015b }
            if (r14 == 0) goto L_0x0067
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x015b }
            r12.f3299e = r7     // Catch:{ all -> 0x015b }
        L_0x0067:
            java.util.Map<java.lang.Integer, byte[]> r14 = r12.f3302h     // Catch:{ all -> 0x015b }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x015b }
            r14.put(r13, r15)     // Catch:{ all -> 0x015b }
            java.util.Map<java.lang.Integer, byte[]> r13 = r12.f3302h     // Catch:{ all -> 0x015b }
            int r13 = r13.size()     // Catch:{ all -> 0x015b }
            int r14 = r12.f3297c     // Catch:{ all -> 0x015b }
            if (r13 != r14) goto L_0x0159
            java.util.Map<java.lang.Integer, byte[]> r13 = r12.f3302h     // Catch:{ all -> 0x015b }
            java.util.Collection r13 = r13.values()     // Catch:{ all -> 0x015b }
            java.util.Iterator r13 = r13.iterator()     // Catch:{ all -> 0x015b }
            r14 = r6
        L_0x0085:
            boolean r15 = r13.hasNext()     // Catch:{ all -> 0x015b }
            if (r15 == 0) goto L_0x00a5
            java.lang.Object r15 = r13.next()     // Catch:{ all -> 0x015b }
            byte[] r15 = (byte[]) r15     // Catch:{ all -> 0x015b }
            if (r14 != 0) goto L_0x0095
            r14 = r15
            goto L_0x0085
        L_0x0095:
            int r0 = r14.length     // Catch:{ all -> 0x015b }
            int r7 = r15.length     // Catch:{ all -> 0x015b }
            int r0 = r0 + r7
            byte[] r0 = new byte[r0]     // Catch:{ all -> 0x015b }
            int r7 = r14.length     // Catch:{ all -> 0x015b }
            java.lang.System.arraycopy(r14, r5, r0, r5, r7)     // Catch:{ all -> 0x015b }
            int r14 = r14.length     // Catch:{ all -> 0x015b }
            int r7 = r15.length     // Catch:{ all -> 0x015b }
            java.lang.System.arraycopy(r15, r5, r0, r14, r7)     // Catch:{ all -> 0x015b }
            r14 = r0
            goto L_0x0085
        L_0x00a5:
            java.lang.String r13 = r12.f3298d     // Catch:{ all -> 0x015b }
            boolean r13 = android.text.TextUtils.isEmpty(r13)     // Catch:{ all -> 0x015b }
            r15 = 5
            r0 = 6
            if (r13 != 0) goto L_0x00e6
            java.lang.String r13 = new java.lang.String     // Catch:{ all -> 0x015b }
            byte[] r7 = com.taobao.accs.utl.C2087d.m3778a(r14)     // Catch:{ all -> 0x015b }
            char[] r7 = m3493a((byte[]) r7)     // Catch:{ all -> 0x015b }
            r13.<init>(r7)     // Catch:{ all -> 0x015b }
            java.lang.String r7 = r12.f3298d     // Catch:{ all -> 0x015b }
            boolean r7 = r7.equals(r13)     // Catch:{ all -> 0x015b }
            if (r7 != 0) goto L_0x00e6
            java.lang.String r14 = "AssembleMessage"
            java.lang.String r7 = "putBurst fail"
            java.lang.Object[] r8 = new java.lang.Object[r0]     // Catch:{ all -> 0x015b }
            java.lang.String r9 = "dataId"
            r8[r5] = r9     // Catch:{ all -> 0x015b }
            java.lang.String r9 = r12.f3296b     // Catch:{ all -> 0x015b }
            r8[r3] = r9     // Catch:{ all -> 0x015b }
            java.lang.String r9 = "dataMd5"
            r8[r4] = r9     // Catch:{ all -> 0x015b }
            java.lang.String r9 = r12.f3298d     // Catch:{ all -> 0x015b }
            r8[r2] = r9     // Catch:{ all -> 0x015b }
            java.lang.String r9 = "finalDataMd5"
            r8[r1] = r9     // Catch:{ all -> 0x015b }
            r8[r15] = r13     // Catch:{ all -> 0x015b }
            com.taobao.accs.utl.ALog.m3731w(r14, r7, r8)     // Catch:{ all -> 0x015b }
            r12.f3300f = r2     // Catch:{ all -> 0x015b }
            r14 = r6
        L_0x00e6:
            r6 = 0
            if (r14 == 0) goto L_0x011b
            int r13 = r14.length     // Catch:{ all -> 0x015b }
            long r6 = (long) r13     // Catch:{ all -> 0x015b }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x015b }
            long r10 = r12.f3299e     // Catch:{ all -> 0x015b }
            long r8 = r8 - r10
            r12.f3300f = r4     // Catch:{ all -> 0x015b }
            java.lang.String r13 = "AssembleMessage"
            java.lang.String r10 = "putBurst completed"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x015b }
            java.lang.String r11 = "dataId"
            r0[r5] = r11     // Catch:{ all -> 0x015b }
            java.lang.String r11 = r12.f3296b     // Catch:{ all -> 0x015b }
            r0[r3] = r11     // Catch:{ all -> 0x015b }
            java.lang.String r3 = "length"
            r0[r4] = r3     // Catch:{ all -> 0x015b }
            java.lang.Long r3 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x015b }
            r0[r2] = r3     // Catch:{ all -> 0x015b }
            java.lang.String r2 = "cost"
            r0[r1] = r2     // Catch:{ all -> 0x015b }
            java.lang.Long r1 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x015b }
            r0[r15] = r1     // Catch:{ all -> 0x015b }
            com.taobao.accs.utl.ALog.m3728i(r13, r10, r0)     // Catch:{ all -> 0x015b }
            goto L_0x011c
        L_0x011b:
            r8 = r6
        L_0x011c:
            com.taobao.accs.ut.monitor.AssembleMonitor r13 = new com.taobao.accs.ut.monitor.AssembleMonitor     // Catch:{ all -> 0x015b }
            java.lang.String r15 = r12.f3296b     // Catch:{ all -> 0x015b }
            int r0 = r12.f3300f     // Catch:{ all -> 0x015b }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x015b }
            r13.<init>(r15, r0)     // Catch:{ all -> 0x015b }
            r13.assembleLength = r6     // Catch:{ all -> 0x015b }
            r13.assembleTimes = r8     // Catch:{ all -> 0x015b }
            anet.channel.appmonitor.IAppMonitor r15 = anet.channel.appmonitor.AppMonitor.getInstance()     // Catch:{ all -> 0x015b }
            r15.commitStat(r13)     // Catch:{ all -> 0x015b }
            java.util.Map<java.lang.Integer, byte[]> r13 = r12.f3302h     // Catch:{ all -> 0x015b }
            r13.clear()     // Catch:{ all -> 0x015b }
            java.util.concurrent.ScheduledFuture<?> r13 = r12.f3301g     // Catch:{ all -> 0x015b }
            if (r13 == 0) goto L_0x0142
            java.util.concurrent.ScheduledFuture<?> r13 = r12.f3301g     // Catch:{ all -> 0x015b }
            r13.cancel(r5)     // Catch:{ all -> 0x015b }
        L_0x0142:
            monitor-exit(r12)     // Catch:{ all -> 0x015b }
            return r14
        L_0x0144:
            java.lang.String r13 = "AssembleMessage"
            java.lang.String r14 = "putBurst fail"
            java.lang.Object[] r15 = new java.lang.Object[r4]     // Catch:{ all -> 0x015b }
            java.lang.String r0 = "status"
            r15[r5] = r0     // Catch:{ all -> 0x015b }
            int r0 = r12.f3300f     // Catch:{ all -> 0x015b }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x015b }
            r15[r3] = r0     // Catch:{ all -> 0x015b }
            com.taobao.accs.utl.ALog.m3727e(r13, r14, r15)     // Catch:{ all -> 0x015b }
        L_0x0159:
            monitor-exit(r12)     // Catch:{ all -> 0x015b }
            return r6
        L_0x015b:
            r13 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x015b }
            throw r13
        L_0x015e:
            java.lang.Object[] r13 = new java.lang.Object[r5]
            java.lang.String r14 = "AssembleMessage"
            java.lang.String r15 = "putBurst fail as burstIndex invalid"
            com.taobao.accs.utl.ALog.m3727e(r14, r15, r13)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.C2027a.mo18407a(int, int, byte[]):byte[]");
    }

    /* renamed from: a */
    private static char[] m3493a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[(length << 1)];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            char[] cArr2 = f3295a;
            cArr[i] = cArr2[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr[i3] = cArr2[bArr[i2] & 15];
        }
        return cArr;
    }
}
