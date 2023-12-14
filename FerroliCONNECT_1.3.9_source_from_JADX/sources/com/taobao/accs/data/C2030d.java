package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.AccsState;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.flowcontrol.FlowControl;
import com.taobao.accs.net.C2049b;
import com.taobao.accs.net.C2057j;
import com.taobao.accs.p103ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.p103ut.monitor.TrafficsMonitor;
import com.taobao.accs.p103ut.p104a.C2076a;
import com.taobao.accs.p103ut.p104a.C2077b;
import com.taobao.accs.p103ut.p104a.C2079d;
import com.taobao.accs.p103ut.p104a.C2080e;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.C2091h;
import com.taobao.accs.utl.JsonUtility;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.zip.GZIPInputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.taobao.accs.data.d */
/* compiled from: Taobao */
public class C2030d {

    /* renamed from: a */
    public ConcurrentMap<String, ScheduledFuture<?>> f3305a = new ConcurrentHashMap();

    /* renamed from: b */
    public int f3306b;

    /* renamed from: c */
    protected TrafficsMonitor f3307c;

    /* renamed from: d */
    public FlowControl f3308d;

    /* renamed from: e */
    public String f3309e = "";

    /* renamed from: f */
    private ConcurrentMap<Message.C2024a, Message> f3310f = new ConcurrentHashMap();

    /* renamed from: g */
    private boolean f3311g = false;

    /* renamed from: h */
    private Context f3312h;

    /* renamed from: i */
    private C2079d f3313i;

    /* renamed from: j */
    private Message f3314j;

    /* renamed from: k */
    private C2049b f3315k;

    /* renamed from: l */
    private String f3316l = "MsgRecv_";

    /* renamed from: m */
    private LinkedHashMap<String, String> f3317m = new MessageHandler$1(this);

    /* renamed from: n */
    private Map<String, C2027a> f3318n = new HashMap();

    /* renamed from: o */
    private Runnable f3319o = new C2032f(this);

    public C2030d(Context context, C2049b bVar) {
        String str;
        this.f3312h = context;
        this.f3315k = bVar;
        this.f3307c = new TrafficsMonitor(this.f3312h);
        this.f3308d = new FlowControl(this.f3312h);
        if (bVar == null) {
            str = this.f3316l;
        } else {
            str = this.f3316l + bVar.f3385m;
        }
        this.f3316l = str;
        m3512i();
        mo18429h();
    }

    /* renamed from: a */
    public void mo18419a(byte[] bArr) throws IOException {
        mo18420a(bArr, (String) null);
    }

    /* renamed from: a */
    public void mo18420a(byte[] bArr, String str) throws IOException {
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.m3728i(this.f3316l, "onMessage", Constants.KEY_HOST, str);
        }
        C2091h hVar = new C2091h(bArr);
        try {
            int a = hVar.mo18599a();
            int i = (a & 240) >> 4;
            if (ALog.isPrintLog(ALog.Level.D)) {
                String str2 = this.f3316l;
                ALog.m3725d(str2, "version:" + i, new Object[0]);
            }
            int i2 = a & 15;
            if (ALog.isPrintLog(ALog.Level.D)) {
                String str3 = this.f3316l;
                ALog.m3725d(str3, "compress:" + i2, new Object[0]);
            }
            hVar.mo18599a();
            int b = hVar.mo18601b();
            if (ALog.isPrintLog(ALog.Level.D)) {
                String str4 = this.f3316l;
                ALog.m3725d(str4, "totalLen:" + b, new Object[0]);
            }
            int i3 = 0;
            while (i3 < b) {
                int b2 = hVar.mo18601b();
                int i4 = i3 + 2;
                if (b2 > 0) {
                    byte[] bArr2 = new byte[b2];
                    hVar.read(bArr2);
                    if (ALog.isPrintLog(ALog.Level.D)) {
                        String str5 = this.f3316l;
                        ALog.m3725d(str5, "buf len:" + bArr2.length, new Object[0]);
                    }
                    i3 = i4 + bArr2.length;
                    m3500a(i2, bArr2, str, i);
                } else {
                    throw new IOException("data format error");
                }
            }
        } catch (Throwable th) {
            hVar.close();
            throw th;
        }
        hVar.close();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v28, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v34, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v28, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v41, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v38, resolved type: com.taobao.accs.ut.monitor.TrafficsMonitor$a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v47, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v76, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v77, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v97, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v98, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v99, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v78, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v79, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v80, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v100, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v81, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v101, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v102, resolved type: int} */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r1v20 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r1v45 */
    /* JADX WARNING: type inference failed for: r4v27 */
    /* JADX WARNING: type inference failed for: r1v74 */
    /* JADX WARNING: type inference failed for: r4v28 */
    /* JADX WARNING: type inference failed for: r4v29 */
    /* JADX WARNING: type inference failed for: r1v82 */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:41|42|43|44|45) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x013d */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0563 A[Catch:{ Exception -> 0x0700 }] */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x056d A[Catch:{ Exception -> 0x0700 }] */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x0576 A[Catch:{ Exception -> 0x0700 }] */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x05c8  */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x05d2 A[Catch:{ Exception -> 0x03bf }] */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x0639 A[Catch:{ Exception -> 0x06fb }] */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x063b A[Catch:{ Exception -> 0x06fb }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m3500a(int r42, byte[] r43, java.lang.String r44, int r45) throws java.io.IOException {
        /*
            r41 = this;
            r7 = r41
            r8 = r42
            r9 = r43
            r14 = r44
            java.lang.String r15 = "accs"
            java.lang.String r13 = ""
            com.taobao.accs.utl.h r1 = new com.taobao.accs.utl.h
            r1.<init>(r9)
            int r0 = r1.mo18601b()
            long r10 = (long) r0
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            r12 = 0
            if (r0 == 0) goto L_0x003c
            java.lang.String r0 = r7.f3316l
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "flag:"
            r2.append(r3)
            int r3 = (int) r10
            java.lang.String r3 = java.lang.Integer.toHexString(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r12]
            com.taobao.accs.utl.ALog.m3725d(r0, r2, r3)
        L_0x003c:
            int r0 = r1.mo18599a()
            java.lang.String r6 = r1.mo18600a(r0)
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            if (r0 == 0) goto L_0x0064
            java.lang.String r0 = r7.f3316l
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "target:"
            r2.append(r3)
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r12]
            com.taobao.accs.utl.ALog.m3725d(r0, r2, r3)
        L_0x0064:
            int r0 = r1.mo18599a()
            java.lang.String r5 = r1.mo18600a(r0)
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            if (r0 == 0) goto L_0x008c
            java.lang.String r0 = r7.f3316l
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "source:"
            r2.append(r3)
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r12]
            com.taobao.accs.utl.ALog.m3725d(r0, r2, r3)
        L_0x008c:
            int r0 = r1.mo18599a()     // Catch:{ Exception -> 0x0795 }
            java.lang.String r4 = r1.mo18600a(r0)     // Catch:{ Exception -> 0x0795 }
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            if (r0 == 0) goto L_0x00b4
            java.lang.String r0 = r7.f3316l
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "dataId:"
            r2.append(r3)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r12]
            com.taobao.accs.utl.ALog.m3725d(r0, r2, r3)
        L_0x00b4:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r5)
            r0.append(r4)
            java.lang.String r3 = r0.toString()
            int r0 = r1.available()
            r12 = 2
            r17 = r15
            r15 = 1
            if (r0 <= 0) goto L_0x0114
            r0 = r45
            if (r0 != r12) goto L_0x00f5
            java.util.Map r0 = r7.m3499a((com.taobao.accs.utl.C2091h) r1)
            if (r0 == 0) goto L_0x00f2
            r18 = 16
            java.lang.Integer r2 = java.lang.Integer.valueOf(r18)
            boolean r2 = r0.containsKey(r2)
            if (r2 == 0) goto L_0x00f2
            r2 = 17
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            boolean r2 = r0.containsKey(r2)
            if (r2 == 0) goto L_0x00f2
            r2 = r0
            r0 = 1
            goto L_0x00f7
        L_0x00f2:
            r2 = r0
            r0 = 0
            goto L_0x00f7
        L_0x00f5:
            r0 = 0
            r2 = 0
        L_0x00f7:
            if (r8 == 0) goto L_0x0109
            if (r0 == 0) goto L_0x00fc
            goto L_0x0109
        L_0x00fc:
            if (r8 != r15) goto L_0x0103
            byte[] r18 = r7.m3505a((java.io.InputStream) r1)
            goto L_0x010d
        L_0x0103:
            r18 = r0
            r45 = r2
            r2 = 0
            goto L_0x0119
        L_0x0109:
            byte[] r18 = r1.mo18602c()
        L_0x010d:
            r45 = r2
            r2 = r18
            r18 = r0
            goto L_0x0119
        L_0x0114:
            r45 = 0
            r2 = 0
            r18 = 0
        L_0x0119:
            r1.close()
            java.lang.String r1 = "handleMessage"
            if (r2 != 0) goto L_0x012d
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x0757 }
            java.lang.String r12 = "oriData is null"
            r23 = r3
            r15 = 0
            java.lang.Object[] r3 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x0757 }
            com.taobao.accs.utl.ALog.m3725d(r0, r12, r3)     // Catch:{ Exception -> 0x0757 }
            goto L_0x0168
        L_0x012d:
            r23 = r3
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ Exception -> 0x0757 }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Exception -> 0x0757 }
            if (r0 == 0) goto L_0x0168
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x013d }
            r0.<init>(r2)     // Catch:{ Exception -> 0x013d }
            goto L_0x014f
        L_0x013d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0757 }
            r0.<init>()     // Catch:{ Exception -> 0x0757 }
            java.lang.String r3 = "binary "
            r0.append(r3)     // Catch:{ Exception -> 0x0757 }
            int r3 = r2.length     // Catch:{ Exception -> 0x0757 }
            r0.append(r3)     // Catch:{ Exception -> 0x0757 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0757 }
        L_0x014f:
            java.lang.String r3 = r7.f3316l     // Catch:{ Exception -> 0x0757 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0757 }
            r12.<init>()     // Catch:{ Exception -> 0x0757 }
            java.lang.String r15 = "oriData:"
            r12.append(r15)     // Catch:{ Exception -> 0x0757 }
            r12.append(r0)     // Catch:{ Exception -> 0x0757 }
            java.lang.String r0 = r12.toString()     // Catch:{ Exception -> 0x0757 }
            r12 = 0
            java.lang.Object[] r15 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x0757 }
            com.taobao.accs.utl.ALog.m3725d(r3, r0, r15)     // Catch:{ Exception -> 0x0757 }
        L_0x0168:
            r0 = 15
            long r24 = r10 >> r0
            r26 = 1
            r15 = r13
            long r12 = r24 & r26
            int r0 = (int) r12
            int r12 = com.taobao.accs.data.Message.C2026c.m3489a(r0)     // Catch:{ Exception -> 0x0751 }
            r0 = 13
            long r24 = r10 >> r0
            r28 = 3
            long r13 = r24 & r28
            int r0 = (int) r13     // Catch:{ Exception -> 0x0751 }
            com.taobao.accs.data.Message$ReqType r13 = com.taobao.accs.data.Message.ReqType.valueOf((int) r0)     // Catch:{ Exception -> 0x0751 }
            r0 = 12
            long r24 = r10 >> r0
            r28 = r15
            long r14 = r24 & r26
            int r3 = (int) r14
            r0 = 11
            long r14 = r10 >> r0
            long r14 = r14 & r26
            int r0 = (int) r14
            int r14 = com.taobao.accs.data.Message.C2025b.m3487a(r0)     // Catch:{ Exception -> 0x0748 }
            r15 = 6
            long r24 = r10 >> r15
            r29 = r10
            long r10 = r24 & r26
            int r0 = (int) r10     // Catch:{ Exception -> 0x0748 }
            r10 = 1
            if (r0 != r10) goto L_0x01a4
            r11 = 1
            goto L_0x01a5
        L_0x01a4:
            r11 = 0
        L_0x01a5:
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Exception -> 0x0748 }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Exception -> 0x0748 }
            java.lang.String r10 = "target"
            java.lang.String r15 = "dataId"
            if (r0 == 0) goto L_0x01f7
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x0748 }
            r27 = r5
            r5 = 10
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0748 }
            r16 = 0
            r5[r16] = r15     // Catch:{ Exception -> 0x0748 }
            r22 = 1
            r5[r22] = r4     // Catch:{ Exception -> 0x0748 }
            java.lang.String r31 = "type"
            r20 = 2
            r5[r20] = r31     // Catch:{ Exception -> 0x0748 }
            java.lang.String r31 = com.taobao.accs.data.Message.C2026c.m3490b(r12)     // Catch:{ Exception -> 0x0748 }
            r26 = 3
            r5[r26] = r31     // Catch:{ Exception -> 0x0748 }
            java.lang.String r31 = "reqType"
            r25 = 4
            r5[r25] = r31     // Catch:{ Exception -> 0x0748 }
            java.lang.String r31 = r13.name()     // Catch:{ Exception -> 0x0748 }
            r21 = 5
            r5[r21] = r31     // Catch:{ Exception -> 0x0748 }
            java.lang.String r31 = "resType"
            r24 = 6
            r5[r24] = r31     // Catch:{ Exception -> 0x0748 }
            r31 = 7
            java.lang.String r32 = com.taobao.accs.data.Message.C2025b.m3488b(r14)     // Catch:{ Exception -> 0x0748 }
            r5[r31] = r32     // Catch:{ Exception -> 0x0748 }
            r31 = 8
            r5[r31] = r10     // Catch:{ Exception -> 0x0748 }
            r31 = 9
            r5[r31] = r6     // Catch:{ Exception -> 0x0748 }
            com.taobao.accs.utl.ALog.m3728i(r0, r1, r5)     // Catch:{ Exception -> 0x0748 }
            goto L_0x01f9
        L_0x01f7:
            r27 = r5
        L_0x01f9:
            r5 = 1
            if (r12 != r5) goto L_0x033d
            com.taobao.accs.data.Message$ReqType r0 = com.taobao.accs.data.Message.ReqType.ACK     // Catch:{ Exception -> 0x0748 }
            if (r13 == r0) goto L_0x0204
            com.taobao.accs.data.Message$ReqType r0 = com.taobao.accs.data.Message.ReqType.RES     // Catch:{ Exception -> 0x0748 }
            if (r13 != r0) goto L_0x033d
        L_0x0204:
            java.util.concurrent.ConcurrentMap<com.taobao.accs.data.Message$a, com.taobao.accs.data.Message> r0 = r7.f3310f     // Catch:{ Exception -> 0x0748 }
            com.taobao.accs.data.Message$a r5 = new com.taobao.accs.data.Message$a     // Catch:{ Exception -> 0x0748 }
            r31 = r6
            r6 = 0
            r5.<init>(r6, r4)     // Catch:{ Exception -> 0x0748 }
            java.lang.Object r0 = r0.remove(r5)     // Catch:{ Exception -> 0x0748 }
            r6 = r0
            com.taobao.accs.data.Message r6 = (com.taobao.accs.data.Message) r6     // Catch:{ Exception -> 0x0748 }
            if (r6 == 0) goto L_0x0306
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ Exception -> 0x0748 }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Exception -> 0x0748 }
            if (r0 == 0) goto L_0x022e
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x0748 }
            java.lang.String r5 = "handleMessage reqMessage not null"
            r32 = r4
            r33 = r14
            r4 = 0
            java.lang.Object[] r14 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x02fb }
            com.taobao.accs.utl.ALog.m3725d(r0, r5, r14)     // Catch:{ Exception -> 0x02fb }
            goto L_0x0232
        L_0x022e:
            r32 = r4
            r33 = r14
        L_0x0232:
            com.alibaba.sdk.android.error.ErrorCode r0 = com.taobao.accs.AccsErrorCode.SUCCESS     // Catch:{ Exception -> 0x02fb }
            r4 = 1
            if (r3 != r4) goto L_0x029b
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Throwable -> 0x024c }
            java.lang.String r4 = new java.lang.String     // Catch:{ Throwable -> 0x024c }
            r4.<init>(r2)     // Catch:{ Throwable -> 0x024c }
            r0.<init>(r4)     // Catch:{ Throwable -> 0x024c }
            java.lang.String r4 = "code"
            int r0 = r0.getInt(r4)     // Catch:{ Throwable -> 0x024c }
            com.alibaba.sdk.android.error.ErrorCode r0 = com.taobao.accs.AccsErrorCode.parseHttpCode(r0)     // Catch:{ Throwable -> 0x024c }
            goto L_0x027d
        L_0x024c:
            r0 = move-exception
            com.alibaba.sdk.android.error.ErrorCode r4 = com.taobao.accs.AccsErrorCode.RESPONSE_PARSE_ERROR     // Catch:{ Exception -> 0x02fb }
            com.alibaba.sdk.android.error.ErrorBuilder r4 = r4.copy()     // Catch:{ Exception -> 0x02fb }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02fb }
            r5.<init>()     // Catch:{ Exception -> 0x02fb }
            java.lang.String r14 = "data:"
            r5.append(r14)     // Catch:{ Exception -> 0x02fb }
            java.lang.String r14 = new java.lang.String     // Catch:{ Exception -> 0x02fb }
            r14.<init>(r2)     // Catch:{ Exception -> 0x02fb }
            r5.append(r14)     // Catch:{ Exception -> 0x02fb }
            java.lang.String r14 = ", tr:"
            r5.append(r14)     // Catch:{ Exception -> 0x02fb }
            java.lang.String r0 = r0.getMessage()     // Catch:{ Exception -> 0x02fb }
            r5.append(r0)     // Catch:{ Exception -> 0x02fb }
            java.lang.String r0 = r5.toString()     // Catch:{ Exception -> 0x02fb }
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r4.detail(r0)     // Catch:{ Exception -> 0x02fb }
            com.alibaba.sdk.android.error.ErrorCode r0 = r0.build()     // Catch:{ Exception -> 0x02fb }
        L_0x027d:
            int r4 = r0.getCodeInt()     // Catch:{ Exception -> 0x02fb }
            com.alibaba.sdk.android.error.ErrorCode r5 = com.taobao.accs.AccsErrorCode.SUCCESS     // Catch:{ Exception -> 0x02fb }
            int r5 = r5.getCodeInt()     // Catch:{ Exception -> 0x02fb }
            if (r4 == r5) goto L_0x029b
            java.lang.String r4 = r7.f3316l     // Catch:{ Exception -> 0x02fb }
            r5 = 2
            java.lang.Object[] r14 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x02fb }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x02fb }
            r5 = 0
            r14[r5] = r3     // Catch:{ Exception -> 0x02fb }
            r3 = 1
            r14[r3] = r0     // Catch:{ Exception -> 0x02fb }
            com.taobao.accs.utl.ALog.m3727e(r4, r1, r14)     // Catch:{ Exception -> 0x02fb }
        L_0x029b:
            r3 = r0
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r0 = r6.mo18393e()     // Catch:{ Exception -> 0x02fb }
            if (r0 == 0) goto L_0x02a9
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r0 = r6.mo18393e()     // Catch:{ Exception -> 0x02fb }
            r0.onRecAck()     // Catch:{ Exception -> 0x02fb }
        L_0x02a9:
            com.taobao.accs.data.Message$ReqType r0 = com.taobao.accs.data.Message.ReqType.RES     // Catch:{ Exception -> 0x02fb }
            if (r13 != r0) goto L_0x02ce
            r14 = r1
            r1 = r41
            r5 = r2
            r4 = 0
            r2 = r6
            r19 = r14
            r14 = r23
            r23 = r11
            r11 = r32
            r32 = r10
            r10 = r4
            r4 = r13
            r34 = r27
            r27 = r5
            r10 = r6
            r35 = r31
            r6 = r45
            r1.mo18416a(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x03bf }
            r5 = r45
            goto L_0x02e4
        L_0x02ce:
            r5 = r45
            r19 = r1
            r14 = r23
            r34 = r27
            r35 = r31
            r27 = r2
            r23 = r11
            r11 = r32
            r32 = r10
            r10 = r6
            r7.mo18417a((com.taobao.accs.data.Message) r10, (com.alibaba.sdk.android.error.ErrorCode) r3, (java.util.Map<java.lang.Integer, java.lang.String>) r5)     // Catch:{ Exception -> 0x03bf }
        L_0x02e4:
            com.taobao.accs.ut.monitor.TrafficsMonitor$a r0 = new com.taobao.accs.ut.monitor.TrafficsMonitor$a     // Catch:{ Exception -> 0x03bf }
            java.lang.String r2 = r10.f3249H     // Catch:{ Exception -> 0x03bf }
            boolean r3 = anet.channel.GlobalAppRuntimeInfo.isAppBackground()     // Catch:{ Exception -> 0x03bf }
            int r1 = r9.length     // Catch:{ Exception -> 0x03bf }
            r10 = r5
            long r5 = (long) r1     // Catch:{ Exception -> 0x03bf }
            r1 = r0
            r4 = r44
            r1.<init>(r2, r3, r4, r5)     // Catch:{ Exception -> 0x03bf }
            r7.mo18418a((com.taobao.accs.p103ut.monitor.TrafficsMonitor.C2082a) r0)     // Catch:{ Exception -> 0x03bf }
            r5 = r35
            goto L_0x034f
        L_0x02fb:
            r0 = move-exception
            r40 = r1
            r6 = r17
            r1 = r28
            r2 = r32
            goto L_0x075e
        L_0x0306:
            r19 = r1
            r32 = r10
            r33 = r14
            r14 = r23
            r34 = r27
            r35 = r31
            r10 = r45
            r27 = r2
            r23 = r11
            r11 = r4
            com.taobao.accs.net.b r0 = r7.f3315k     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.net.b r1 = r7.f3315k     // Catch:{ Exception -> 0x03bf }
            r2 = 0
            java.lang.String r1 = r1.mo18478b((java.lang.String) r2)     // Catch:{ Exception -> 0x03bf }
            r5 = r35
            r2 = 5
            com.taobao.accs.data.Message r1 = com.taobao.accs.data.Message.m3459a((java.lang.String) r11, (java.lang.String) r5, (java.lang.String) r1, (int) r2)     // Catch:{ Exception -> 0x03bf }
            r2 = 1
            r0.mo18482b(r1, r2)     // Catch:{ Exception -> 0x03bf }
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x03bf }
            java.lang.String r1 = "handleMessage data ack/res reqMessage is null"
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x03bf }
            r3 = 0
            r4[r3] = r15     // Catch:{ Exception -> 0x03bf }
            r4[r2] = r11     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.utl.ALog.m3727e(r0, r1, r4)     // Catch:{ Exception -> 0x03bf }
            goto L_0x034f
        L_0x033d:
            r19 = r1
            r5 = r6
            r32 = r10
            r33 = r14
            r14 = r23
            r34 = r27
            r10 = r45
            r27 = r2
            r23 = r11
            r11 = r4
        L_0x034f:
            if (r12 != 0) goto L_0x03c7
            com.taobao.accs.data.Message$ReqType r0 = com.taobao.accs.data.Message.ReqType.RES     // Catch:{ Exception -> 0x03bf }
            if (r13 != r0) goto L_0x03c7
            java.util.concurrent.ConcurrentMap<com.taobao.accs.data.Message$a, com.taobao.accs.data.Message> r0 = r7.f3310f     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.data.Message$a r1 = new com.taobao.accs.data.Message$a     // Catch:{ Exception -> 0x03bf }
            r2 = 0
            r1.<init>(r2, r11)     // Catch:{ Exception -> 0x03bf }
            java.lang.Object r0 = r0.remove(r1)     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.data.Message r0 = (com.taobao.accs.data.Message) r0     // Catch:{ Exception -> 0x03bf }
            if (r0 == 0) goto L_0x036d
            r6 = r44
            r1 = r27
            r7.m3502a((com.taobao.accs.data.Message) r0, (byte[]) r1, (byte[]) r9, (java.lang.String) r6)     // Catch:{ Exception -> 0x03bf }
            return
        L_0x036d:
            r6 = r44
            r1 = r27
            java.lang.String r0 = "4|sal|st"
            r4 = r34
            boolean r0 = r4.contains(r0)     // Catch:{ Exception -> 0x03bf }
            if (r0 != 0) goto L_0x038d
            com.taobao.accs.net.b r0 = r7.f3315k     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.net.b r2 = r7.f3315k     // Catch:{ Exception -> 0x03bf }
            r3 = 0
            java.lang.String r2 = r2.mo18478b((java.lang.String) r3)     // Catch:{ Exception -> 0x03bf }
            r3 = 5
            com.taobao.accs.data.Message r2 = com.taobao.accs.data.Message.m3459a((java.lang.String) r11, (java.lang.String) r5, (java.lang.String) r2, (int) r3)     // Catch:{ Exception -> 0x03bf }
            r3 = 1
            r0.mo18482b(r2, r3)     // Catch:{ Exception -> 0x03bf }
        L_0x038d:
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x03bf }
            java.lang.String r2 = "handleMessage contorl ACK reqMessage is null"
            r3 = 2
            java.lang.Object[] r9 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x03bf }
            r3 = 0
            r9[r3] = r15     // Catch:{ Exception -> 0x03bf }
            r3 = 1
            r9[r3] = r11     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.utl.ALog.m3727e(r0, r2, r9)     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ Exception -> 0x03bf }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Exception -> 0x03bf }
            if (r0 == 0) goto L_0x03cd
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x03bf }
            java.lang.String r2 = "handleMessage not handled"
            r3 = 2
            java.lang.Object[] r9 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x03bf }
            java.lang.String r3 = "body"
            r16 = 0
            r9[r16] = r3     // Catch:{ Exception -> 0x03bf }
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x03bf }
            r3.<init>(r1)     // Catch:{ Exception -> 0x03bf }
            r22 = 1
            r9[r22] = r3     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.utl.ALog.m3725d(r0, r2, r9)     // Catch:{ Exception -> 0x03bf }
            goto L_0x03cd
        L_0x03bf:
            r0 = move-exception
            r2 = r11
            r6 = r17
        L_0x03c3:
            r40 = r19
            goto L_0x074e
        L_0x03c7:
            r6 = r44
            r1 = r27
            r4 = r34
        L_0x03cd:
            r2 = 1
            if (r12 != r2) goto L_0x0794
            com.taobao.accs.data.Message$ReqType r0 = com.taobao.accs.data.Message.ReqType.DATA     // Catch:{ Exception -> 0x03bf }
            if (r13 != r0) goto L_0x0794
            if (r5 != 0) goto L_0x03ea
            com.taobao.accs.net.b r0 = r7.f3315k     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.net.b r1 = r7.f3315k     // Catch:{ Exception -> 0x03bf }
            r2 = 0
            java.lang.String r1 = r1.mo18478b((java.lang.String) r2)     // Catch:{ Exception -> 0x03bf }
            r13 = r28
            r2 = 1
            com.taobao.accs.data.Message r1 = com.taobao.accs.data.Message.m3459a((java.lang.String) r11, (java.lang.String) r13, (java.lang.String) r1, (int) r2)     // Catch:{ Exception -> 0x0740 }
            r0.mo18482b(r1, r2)     // Catch:{ Exception -> 0x0740 }
            return
        L_0x03ea:
            r13 = r28
            java.lang.String r0 = "\\|"
            java.lang.String[] r0 = r5.split(r0)     // Catch:{ Exception -> 0x0740 }
            int r2 = r0.length     // Catch:{ Exception -> 0x0740 }
            r3 = 2
            if (r2 >= r3) goto L_0x0408
            com.taobao.accs.net.b r0 = r7.f3315k     // Catch:{ Exception -> 0x0740 }
            com.taobao.accs.net.b r1 = r7.f3315k     // Catch:{ Exception -> 0x0740 }
            r2 = 0
            java.lang.String r1 = r1.mo18478b((java.lang.String) r2)     // Catch:{ Exception -> 0x0740 }
            r2 = 1
            com.taobao.accs.data.Message r1 = com.taobao.accs.data.Message.m3459a((java.lang.String) r11, (java.lang.String) r13, (java.lang.String) r1, (int) r2)     // Catch:{ Exception -> 0x0740 }
            r0.mo18482b(r1, r2)     // Catch:{ Exception -> 0x0740 }
            return
        L_0x0408:
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ Exception -> 0x0740 }
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ Exception -> 0x0740 }
            if (r2 == 0) goto L_0x0428
            java.lang.String r2 = r7.f3316l     // Catch:{ Exception -> 0x0740 }
            java.lang.String r3 = "handleMessage onPush"
            r9 = 2
            java.lang.Object[] r12 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x0740 }
            java.lang.String r9 = "isBurstData"
            r16 = 0
            r12[r16] = r9     // Catch:{ Exception -> 0x0740 }
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r18)     // Catch:{ Exception -> 0x0740 }
            r22 = 1
            r12[r22] = r9     // Catch:{ Exception -> 0x0740 }
            com.taobao.accs.utl.ALog.m3725d(r2, r3, r12)     // Catch:{ Exception -> 0x0740 }
        L_0x0428:
            com.taobao.accs.ut.a.d r2 = r7.f3313i     // Catch:{ Exception -> 0x0740 }
            if (r2 == 0) goto L_0x0431
            com.taobao.accs.ut.a.d r2 = r7.f3313i     // Catch:{ Exception -> 0x0740 }
            r2.mo18540a()     // Catch:{ Exception -> 0x0740 }
        L_0x0431:
            com.taobao.accs.ut.a.d r2 = new com.taobao.accs.ut.a.d     // Catch:{ Exception -> 0x0740 }
            r2.<init>()     // Catch:{ Exception -> 0x0740 }
            r7.f3313i = r2     // Catch:{ Exception -> 0x0740 }
            com.taobao.accs.ut.a.d r2 = r7.f3313i     // Catch:{ Exception -> 0x0740 }
            long r27 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0740 }
            java.lang.String r3 = java.lang.String.valueOf(r27)     // Catch:{ Exception -> 0x0740 }
            r2.f3511c = r3     // Catch:{ Exception -> 0x0740 }
            android.content.Context r2 = r7.f3312h     // Catch:{ Exception -> 0x0740 }
            r3 = 1
            r9 = r0[r3]     // Catch:{ Exception -> 0x0740 }
            boolean r2 = com.taobao.accs.utl.UtilityImpl.m3755c(r2, r9)     // Catch:{ Exception -> 0x0740 }
            if (r2 == 0) goto L_0x0705
            int r2 = r0.length     // Catch:{ Exception -> 0x0740 }
            r3 = 3
            if (r2 < r3) goto L_0x0458
            r2 = 2
            r3 = r0[r2]     // Catch:{ Exception -> 0x0740 }
            r12 = r3
            goto L_0x0459
        L_0x0458:
            r12 = 0
        L_0x0459:
            com.taobao.accs.ut.a.d r2 = r7.f3313i     // Catch:{ Exception -> 0x0740 }
            r2.f3513e = r12     // Catch:{ Exception -> 0x0740 }
            boolean r2 = r7.m3510c((java.lang.String) r14)     // Catch:{ Exception -> 0x0740 }
            if (r2 == 0) goto L_0x0498
            com.taobao.accs.net.b r0 = r7.f3315k     // Catch:{ Exception -> 0x0740 }
            com.taobao.accs.net.b r1 = r7.f3315k     // Catch:{ Exception -> 0x0740 }
            r2 = 0
            java.lang.String r1 = r1.mo18478b((java.lang.String) r2)     // Catch:{ Exception -> 0x0740 }
            r2 = 2
            com.taobao.accs.data.Message r1 = com.taobao.accs.data.Message.m3459a((java.lang.String) r11, (java.lang.String) r12, (java.lang.String) r1, (int) r2)     // Catch:{ Exception -> 0x0740 }
            r3 = 1
            r0.mo18482b(r1, r3)     // Catch:{ Exception -> 0x0740 }
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x0740 }
            java.lang.String r1 = "handleMessage msg duplicate"
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0740 }
            r2 = 0
            r8[r2] = r15     // Catch:{ Exception -> 0x0740 }
            r8[r3] = r11     // Catch:{ Exception -> 0x0740 }
            com.taobao.accs.utl.ALog.m3727e(r0, r1, r8)     // Catch:{ Exception -> 0x0740 }
            com.taobao.accs.ut.a.d r0 = r7.f3313i     // Catch:{ Exception -> 0x0740 }
            r0.f3516h = r3     // Catch:{ Exception -> 0x0740 }
            r24 = r5
            r25 = r13
            r8 = r17
            r9 = r23
            r13 = r29
            r1 = r33
            r2 = 1
            r23 = r4
            goto L_0x0681
        L_0x0498:
            if (r18 == 0) goto L_0x04f2
            byte[] r2 = r7.m3506a((java.lang.String) r14, (java.util.Map<java.lang.Integer, java.lang.String>) r10, (byte[]) r1)     // Catch:{ Exception -> 0x0740 }
            if (r2 != 0) goto L_0x04b2
            com.taobao.accs.net.b r0 = r7.f3315k     // Catch:{ Exception -> 0x0740 }
            com.taobao.accs.net.b r1 = r7.f3315k     // Catch:{ Exception -> 0x0740 }
            r9 = 0
            java.lang.String r1 = r1.mo18478b((java.lang.String) r9)     // Catch:{ Exception -> 0x0740 }
            r2 = 1
            com.taobao.accs.data.Message r1 = com.taobao.accs.data.Message.m3459a((java.lang.String) r11, (java.lang.String) r12, (java.lang.String) r1, (int) r2)     // Catch:{ Exception -> 0x0740 }
            r0.mo18482b(r1, r2)     // Catch:{ Exception -> 0x0740 }
            return
        L_0x04b2:
            r1 = 1
            r9 = 0
            if (r8 != r1) goto L_0x04ef
            com.taobao.accs.utl.h r1 = new com.taobao.accs.utl.h     // Catch:{ Exception -> 0x0740 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0740 }
            byte[] r2 = r7.m3505a((java.io.InputStream) r1)     // Catch:{ Exception -> 0x0740 }
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ Exception -> 0x0740 }
            boolean r3 = com.taobao.accs.utl.ALog.isPrintLog(r3)     // Catch:{ Exception -> 0x0740 }
            if (r3 == 0) goto L_0x04e9
            java.lang.String r3 = r7.f3316l     // Catch:{ Exception -> 0x0740 }
            java.lang.String r8 = "handleMessage gzip completeOriData"
            r28 = r13
            r9 = 4
            java.lang.Object[] r13 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x03bf }
            r9 = 0
            r13[r9] = r15     // Catch:{ Exception -> 0x03bf }
            r9 = 1
            r13[r9] = r14     // Catch:{ Exception -> 0x03bf }
            java.lang.String r9 = "length"
            r18 = 2
            r13[r18] = r9     // Catch:{ Exception -> 0x03bf }
            int r9 = r2.length     // Catch:{ Exception -> 0x03bf }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Exception -> 0x03bf }
            r18 = 3
            r13[r18] = r9     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.utl.ALog.m3725d(r3, r8, r13)     // Catch:{ Exception -> 0x03bf }
            goto L_0x04eb
        L_0x04e9:
            r28 = r13
        L_0x04eb:
            r1.close()     // Catch:{ Exception -> 0x03bf }
            goto L_0x04f5
        L_0x04ef:
            r28 = r13
            goto L_0x04f5
        L_0x04f2:
            r28 = r13
            r2 = r1
        L_0x04f5:
            r7.m3511d(r14)     // Catch:{ Exception -> 0x03bf }
            r14 = r17
            boolean r1 = r14.equals(r12)     // Catch:{ Exception -> 0x0700 }
            java.lang.String r3 = "serviceId"
            if (r1 == 0) goto L_0x0522
            java.lang.String r1 = r7.f3316l     // Catch:{ Exception -> 0x0700 }
            java.lang.String r8 = "handleMessage try deliverMsg"
            r9 = 6
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x0700 }
            r13 = 0
            r9[r13] = r15     // Catch:{ Exception -> 0x0700 }
            r13 = 1
            r9[r13] = r11     // Catch:{ Exception -> 0x0700 }
            r17 = 2
            r9[r17] = r32     // Catch:{ Exception -> 0x0700 }
            r17 = r0[r13]     // Catch:{ Exception -> 0x0700 }
            r13 = 3
            r9[r13] = r17     // Catch:{ Exception -> 0x0700 }
            r13 = 4
            r9[r13] = r3     // Catch:{ Exception -> 0x0700 }
            r13 = 5
            r9[r13] = r12     // Catch:{ Exception -> 0x0700 }
            com.taobao.accs.utl.ALog.m3727e(r1, r8, r9)     // Catch:{ Exception -> 0x0700 }
            goto L_0x054a
        L_0x0522:
            com.taobao.accs.utl.ALog$Level r1 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Exception -> 0x0700 }
            boolean r1 = com.taobao.accs.utl.ALog.isPrintLog(r1)     // Catch:{ Exception -> 0x0700 }
            if (r1 == 0) goto L_0x054a
            java.lang.String r1 = r7.f3316l     // Catch:{ Exception -> 0x0700 }
            java.lang.String r8 = "handleMessage try deliverMsg"
            r9 = 6
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x0700 }
            r13 = 0
            r9[r13] = r15     // Catch:{ Exception -> 0x0700 }
            r13 = 1
            r9[r13] = r11     // Catch:{ Exception -> 0x0700 }
            r17 = 2
            r9[r17] = r32     // Catch:{ Exception -> 0x0700 }
            r17 = r0[r13]     // Catch:{ Exception -> 0x0700 }
            r13 = 3
            r9[r13] = r17     // Catch:{ Exception -> 0x0700 }
            r13 = 4
            r9[r13] = r3     // Catch:{ Exception -> 0x0700 }
            r13 = 5
            r9[r13] = r12     // Catch:{ Exception -> 0x0700 }
            com.taobao.accs.utl.ALog.m3728i(r1, r8, r9)     // Catch:{ Exception -> 0x0700 }
            goto L_0x054b
        L_0x054a:
            r13 = 5
        L_0x054b:
            android.content.Intent r1 = new android.content.Intent     // Catch:{ Exception -> 0x0700 }
            java.lang.String r8 = "com.taobao.accs.intent.action.RECEIVE"
            r1.<init>(r8)     // Catch:{ Exception -> 0x0700 }
            r8 = 1
            r9 = r0[r8]     // Catch:{ Exception -> 0x0700 }
            r1.setPackage(r9)     // Catch:{ Exception -> 0x0700 }
            java.lang.String r8 = "command"
            r9 = 101(0x65, float:1.42E-43)
            r1.putExtra(r8, r9)     // Catch:{ Exception -> 0x0700 }
            int r8 = r0.length     // Catch:{ Exception -> 0x0700 }
            r9 = 3
            if (r8 < r9) goto L_0x0569
            r8 = 2
            r9 = r0[r8]     // Catch:{ Exception -> 0x0700 }
            r1.putExtra(r3, r9)     // Catch:{ Exception -> 0x0700 }
        L_0x0569:
            int r3 = r0.length     // Catch:{ Exception -> 0x0700 }
            r8 = 4
            if (r3 < r8) goto L_0x0576
            r3 = 3
            r0 = r0[r3]     // Catch:{ Exception -> 0x0700 }
            java.lang.String r3 = "userInfo"
            r1.putExtra(r3, r0)     // Catch:{ Exception -> 0x0700 }
            goto L_0x0578
        L_0x0576:
            r0 = r28
        L_0x0578:
            java.lang.String r3 = "data"
            r1.putExtra(r3, r2)     // Catch:{ Exception -> 0x0700 }
            r1.putExtra(r15, r11)     // Catch:{ Exception -> 0x0700 }
            java.lang.String r3 = "packageName"
            android.content.Context r8 = r7.f3312h     // Catch:{ Exception -> 0x0700 }
            java.lang.String r8 = r8.getPackageName()     // Catch:{ Exception -> 0x0700 }
            r1.putExtra(r3, r8)     // Catch:{ Exception -> 0x0700 }
            java.lang.String r3 = "host"
            r1.putExtra(r3, r6)     // Catch:{ Exception -> 0x0700 }
            java.lang.String r3 = "conn_type"
            int r8 = r7.f3306b     // Catch:{ Exception -> 0x0700 }
            r1.putExtra(r3, r8)     // Catch:{ Exception -> 0x0700 }
            java.lang.String r3 = "bizAck"
            r9 = r23
            r1.putExtra(r3, r9)     // Catch:{ Exception -> 0x0700 }
            java.lang.String r3 = "appKey"
            com.taobao.accs.net.b r8 = r7.f3315k     // Catch:{ Exception -> 0x0700 }
            java.lang.String r8 = r8.mo18490i()     // Catch:{ Exception -> 0x0700 }
            r1.putExtra(r3, r8)     // Catch:{ Exception -> 0x0700 }
            java.lang.String r3 = "configTag"
            com.taobao.accs.net.b r8 = r7.f3315k     // Catch:{ Exception -> 0x0700 }
            java.lang.String r8 = r8.f3385m     // Catch:{ Exception -> 0x0700 }
            r1.putExtra(r3, r8)     // Catch:{ Exception -> 0x0700 }
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r3 = new com.taobao.accs.ut.monitor.NetPerformanceMonitor     // Catch:{ Exception -> 0x0700 }
            r3.<init>()     // Catch:{ Exception -> 0x0700 }
            r8 = 4
            r3.setMsgType(r8)     // Catch:{ Exception -> 0x0700 }
            r3.onReceiveData()     // Catch:{ Exception -> 0x0700 }
            java.lang.String r8 = "monitor"
            r1.putExtra(r8, r3)     // Catch:{ Exception -> 0x0700 }
            r7.m3504a((java.util.Map<java.lang.Integer, java.lang.String>) r10, (android.content.Intent) r1)     // Catch:{ Exception -> 0x0700 }
            if (r9 == 0) goto L_0x05d2
            r17 = r14
            r13 = r29
            int r3 = (int) r13
            short r3 = (short) r3
            r7.m3501a((android.content.Intent) r1, (java.lang.String) r4, (java.lang.String) r5, (short) r3)     // Catch:{ Exception -> 0x03bf }
            goto L_0x05d6
        L_0x05d2:
            r17 = r14
            r13 = r29
        L_0x05d6:
            android.content.Context r3 = r7.f3312h     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.net.b r8 = r7.f3315k     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.data.C2033g.m3535a(r3, r8, r1)     // Catch:{ Exception -> 0x03bf }
            com.taobao.accs.utl.UTMini r34 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Exception -> 0x03bf }
            r35 = 66001(0x101d1, float:9.2487E-41)
            java.lang.String r36 = "MsgToBussPush"
            java.lang.String r37 = "commandId=101"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03bf }
            r1.<init>()     // Catch:{ Exception -> 0x03bf }
            java.lang.String r3 = "serviceId="
            r1.append(r3)     // Catch:{ Exception -> 0x03bf }
            r1.append(r12)     // Catch:{ Exception -> 0x03bf }
            java.lang.String r3 = " dataId="
            r1.append(r3)     // Catch:{ Exception -> 0x03bf }
            r1.append(r11)     // Catch:{ Exception -> 0x03bf }
            java.lang.String r38 = r1.toString()     // Catch:{ Exception -> 0x03bf }
            r1 = 221(0xdd, float:3.1E-43)
            java.lang.Integer r39 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x03bf }
            r34.commitEvent(r35, r36, r37, r38, r39)     // Catch:{ Exception -> 0x03bf }
            java.lang.String r1 = "to_buss"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03bf }
            r3.<init>()     // Catch:{ Exception -> 0x03bf }
            java.lang.String r8 = "1commandId=101serviceId="
            r3.append(r8)     // Catch:{ Exception -> 0x03bf }
            r3.append(r12)     // Catch:{ Exception -> 0x03bf }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x03bf }
            r34 = r4
            r35 = r5
            r4 = 0
            r8 = r17
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r8, r1, r3, r4)     // Catch:{ Exception -> 0x06fb }
            com.taobao.accs.ut.a.d r1 = r7.f3313i     // Catch:{ Exception -> 0x06fb }
            r1.f3510b = r11     // Catch:{ Exception -> 0x06fb }
            com.taobao.accs.ut.a.d r1 = r7.f3313i     // Catch:{ Exception -> 0x06fb }
            r1.f3517i = r0     // Catch:{ Exception -> 0x06fb }
            com.taobao.accs.ut.a.d r0 = r7.f3313i     // Catch:{ Exception -> 0x06fb }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06fb }
            r1.<init>()     // Catch:{ Exception -> 0x06fb }
            if (r2 != 0) goto L_0x063b
            r2 = 0
            goto L_0x063c
        L_0x063b:
            int r2 = r2.length     // Catch:{ Exception -> 0x06fb }
        L_0x063c:
            r1.append(r2)     // Catch:{ Exception -> 0x06fb }
            r5 = r28
            r1.append(r5)     // Catch:{ Exception -> 0x06f6 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x06f6 }
            r0.f3514f = r1     // Catch:{ Exception -> 0x06f6 }
            com.taobao.accs.ut.a.d r0 = r7.f3313i     // Catch:{ Exception -> 0x06f6 }
            android.content.Context r1 = r7.f3312h     // Catch:{ Exception -> 0x06f6 }
            java.lang.String r1 = com.taobao.accs.utl.UtilityImpl.getDeviceId(r1)     // Catch:{ Exception -> 0x06f6 }
            r0.f3509a = r1     // Catch:{ Exception -> 0x06f6 }
            com.taobao.accs.ut.a.d r0 = r7.f3313i     // Catch:{ Exception -> 0x06f6 }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x06f6 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x06f6 }
            r0.f3512d = r1     // Catch:{ Exception -> 0x06f6 }
            com.taobao.accs.ut.monitor.TrafficsMonitor$a r0 = new com.taobao.accs.ut.monitor.TrafficsMonitor$a     // Catch:{ Exception -> 0x06f6 }
            boolean r3 = anet.channel.GlobalAppRuntimeInfo.isAppBackground()     // Catch:{ Exception -> 0x06f6 }
            r1 = r43
            int r1 = r1.length     // Catch:{ Exception -> 0x06f6 }
            long r1 = (long) r1
            r17 = r1
            r1 = r0
            r2 = r12
            r23 = r34
            r4 = r44
            r25 = r5
            r24 = r35
            r5 = r17
            r1.<init>(r2, r3, r4, r5)     // Catch:{ Exception -> 0x06ed }
            r7.mo18418a((com.taobao.accs.p103ut.monitor.TrafficsMonitor.C2082a) r0)     // Catch:{ Exception -> 0x06ed }
            r1 = r33
            r2 = 1
        L_0x0681:
            if (r1 != r2) goto L_0x0794
            boolean r0 = r8.equals(r12)     // Catch:{ Exception -> 0x06ed }
            if (r0 == 0) goto L_0x069c
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x06ed }
            java.lang.String r1 = "handleMessage try sendAck dataId"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x06ed }
            r3 = 0
            r2[r3] = r15     // Catch:{ Exception -> 0x06ed }
            r3 = 1
            r2[r3] = r11     // Catch:{ Exception -> 0x06ed }
            com.taobao.accs.utl.ALog.m3727e(r0, r1, r2)     // Catch:{ Exception -> 0x06ed }
            r3 = 0
            r4 = 1
            goto L_0x06ac
        L_0x069c:
            java.lang.String r0 = r7.f3316l     // Catch:{ Exception -> 0x06ed }
            java.lang.String r1 = "handleMessage try sendAck dataId"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x06ed }
            r3 = 0
            r2[r3] = r15     // Catch:{ Exception -> 0x06ed }
            r4 = 1
            r2[r4] = r11     // Catch:{ Exception -> 0x06e4 }
            com.taobao.accs.utl.ALog.m3728i(r0, r1, r2)     // Catch:{ Exception -> 0x06e4 }
        L_0x06ac:
            com.taobao.accs.net.b r0 = r7.f3315k     // Catch:{ Exception -> 0x06e4 }
            r1 = 0
            int r2 = (int) r13
            short r13 = (short) r2
            r2 = r8
            r8 = r0
            r16 = r9
            r5 = 0
            r9 = r24
            r6 = r5
            r5 = r10
            r10 = r23
            r15 = r11
            r3 = r12
            r4 = 0
            r14 = 5
            r12 = r1
            r1 = r25
            r40 = r19
            r14 = r44
            r6 = r2
            r2 = r15
            r4 = 1
            r15 = r5
            com.taobao.accs.data.Message r0 = com.taobao.accs.data.Message.m3455a(r8, r9, r10, r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x073e }
            com.taobao.accs.net.b r5 = r7.f3315k     // Catch:{ Exception -> 0x073e }
            r5.mo18482b(r0, r4)     // Catch:{ Exception -> 0x073e }
            java.lang.String r0 = r0.f3281q     // Catch:{ Exception -> 0x073e }
            r7.m3503a((java.lang.String) r0, (java.lang.String) r3)     // Catch:{ Exception -> 0x073e }
            if (r16 == 0) goto L_0x0794
            java.lang.String r0 = "ack"
            r8 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r6, r0, r1, r8)     // Catch:{ Exception -> 0x073e }
            goto L_0x0794
        L_0x06e4:
            r0 = move-exception
            r6 = r8
            r2 = r11
            r40 = r19
            r1 = r25
            goto L_0x075f
        L_0x06ed:
            r0 = move-exception
            r6 = r8
            r2 = r11
            r40 = r19
            r1 = r25
            goto L_0x075e
        L_0x06f6:
            r0 = move-exception
            r1 = r5
            r6 = r8
            r2 = r11
            goto L_0x0745
        L_0x06fb:
            r0 = move-exception
            r6 = r8
            r2 = r11
            goto L_0x03c3
        L_0x0700:
            r0 = move-exception
            r2 = r11
            r6 = r14
            goto L_0x03c3
        L_0x0705:
            r2 = r11
            r1 = r13
            r6 = r17
            r40 = r19
            r4 = 1
            java.lang.String r3 = r7.f3316l     // Catch:{ Exception -> 0x073e }
            java.lang.String r5 = "handleMessage not exist, unbind it"
            r8 = 2
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x073e }
            java.lang.String r9 = "package"
            r10 = 0
            r8[r10] = r9     // Catch:{ Exception -> 0x073e }
            r9 = r0[r4]     // Catch:{ Exception -> 0x073e }
            r8[r4] = r9     // Catch:{ Exception -> 0x073e }
            com.taobao.accs.utl.ALog.m3727e(r3, r5, r8)     // Catch:{ Exception -> 0x073e }
            com.taobao.accs.net.b r3 = r7.f3315k     // Catch:{ Exception -> 0x073e }
            com.taobao.accs.net.b r5 = r7.f3315k     // Catch:{ Exception -> 0x073e }
            r8 = 0
            java.lang.String r5 = r5.mo18478b((java.lang.String) r8)     // Catch:{ Exception -> 0x073e }
            r8 = 4
            com.taobao.accs.data.Message r5 = com.taobao.accs.data.Message.m3459a((java.lang.String) r2, (java.lang.String) r1, (java.lang.String) r5, (int) r8)     // Catch:{ Exception -> 0x073e }
            r3.mo18482b(r5, r4)     // Catch:{ Exception -> 0x073e }
            com.taobao.accs.net.b r3 = r7.f3315k     // Catch:{ Exception -> 0x073e }
            com.taobao.accs.net.b r5 = r7.f3315k     // Catch:{ Exception -> 0x073e }
            r0 = r0[r4]     // Catch:{ Exception -> 0x073e }
            com.taobao.accs.data.Message r0 = com.taobao.accs.data.Message.m3454a((com.taobao.accs.net.C2049b) r5, (java.lang.String) r0)     // Catch:{ Exception -> 0x073e }
            r3.mo18482b(r0, r4)     // Catch:{ Exception -> 0x073e }
            goto L_0x0794
        L_0x073e:
            r0 = move-exception
            goto L_0x075f
        L_0x0740:
            r0 = move-exception
            r2 = r11
            r1 = r13
            r6 = r17
        L_0x0745:
            r40 = r19
            goto L_0x075e
        L_0x0748:
            r0 = move-exception
            r40 = r1
            r2 = r4
            r6 = r17
        L_0x074e:
            r1 = r28
            goto L_0x075e
        L_0x0751:
            r0 = move-exception
            r40 = r1
            r2 = r4
            r1 = r15
            goto L_0x075c
        L_0x0757:
            r0 = move-exception
            r40 = r1
            r2 = r4
            r1 = r13
        L_0x075c:
            r6 = r17
        L_0x075e:
            r4 = 1
        L_0x075f:
            java.lang.String r3 = r7.f3316l
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r8 = r40
            com.taobao.accs.utl.ALog.m3726e(r3, r8, r0, r5)
            com.taobao.accs.net.b r3 = r7.f3315k
            r5 = 0
            java.lang.String r5 = r3.mo18478b((java.lang.String) r5)
            r8 = 5
            com.taobao.accs.data.Message r2 = com.taobao.accs.data.Message.m3459a((java.lang.String) r2, (java.lang.String) r1, (java.lang.String) r5, (int) r8)
            r3.mo18482b(r2, r4)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            int r3 = r7.f3306b
            r2.append(r3)
            java.lang.String r0 = r0.toString()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            java.lang.String r2 = "send_fail"
            java.lang.String r3 = "1"
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r6, r2, r1, r3, r0)
        L_0x0794:
            return
        L_0x0795:
            r0 = move-exception
            r2 = r13
            r6 = r15
            java.lang.String r3 = r7.f3316l
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "dataId read error "
            r4.append(r5)
            java.lang.String r5 = r0.toString()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            com.taobao.accs.utl.ALog.m3727e(r3, r4, r5)
            r1.close()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r3 = r7.f3306b
            r1.append(r3)
            java.lang.String r3 = "data id read error"
            r1.append(r3)
            java.lang.String r0 = r0.toString()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "send_fail"
            java.lang.String r3 = "1"
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r6, r1, r2, r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.C2030d.m3500a(int, byte[], java.lang.String, int):void");
    }

    /* renamed from: a */
    private byte[] m3505a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        GZIPInputStream gZIPInputStream = new GZIPInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int read = gZIPInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                gZIPInputStream.close();
                byteArrayOutputStream.close();
            } catch (Exception unused) {
            }
            return byteArray;
        } catch (Exception e) {
            String str = this.f3316l;
            ALog.m3727e(str, "uncompress data error " + e.toString(), new Object[0]);
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", this.f3306b + " uncompress data error " + e.toString());
            try {
                gZIPInputStream.close();
                byteArrayOutputStream.close();
            } catch (Exception unused2) {
            }
            return null;
        } catch (Throwable th) {
            try {
                gZIPInputStream.close();
                byteArrayOutputStream.close();
            } catch (Exception unused3) {
            }
            throw th;
        }
    }

    /* renamed from: a */
    private void m3502a(Message message, byte[] bArr, byte[] bArr2, String str) {
        JSONObject jSONObject;
        ErrorCode parseHttpCode;
        JSONArray optJSONArray;
        Message message2 = message;
        ErrorCode errorCode = AccsErrorCode.SUCCESS;
        try {
            try {
                jSONObject = new JSONObject(new String(bArr));
                if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.m3725d(this.f3316l, "handleControlMessage parse", "json", jSONObject.toString());
                }
                if (message2.f3284t.intValue() == 100) {
                    parseHttpCode = AccsErrorCode.SUCCESS;
                } else {
                    parseHttpCode = AccsErrorCode.parseHttpCode(jSONObject.getInt("code"));
                }
                errorCode = parseHttpCode;
                if (errorCode.getCodeInt() == AccsErrorCode.SUCCESS.getCodeInt()) {
                    int intValue = message2.f3284t.intValue();
                    if (intValue == 1) {
                        AccsState.getInstance().mo18227a(this.f3315k.f3385m, AccsState.BIND_APP_FROM_CACHE, false);
                        UtilityImpl.saveUtdid(Constants.SP_FILE_NAME, this.f3312h);
                        this.f3315k.mo18491j().mo18378a(this.f3312h.getPackageName());
                        JSONObject jSONObject2 = jSONObject.getJSONObject(Constants.KEY_DATA);
                        this.f3309e = JsonUtility.getString(jSONObject2, Constants.KEY_DEVICE_TOKEN, (String) null);
                        if (!(jSONObject2 == null || (optJSONArray = jSONObject2.optJSONArray(Constants.KEY_PACKAGE_NAMES)) == null)) {
                            for (int i = 0; i < optJSONArray.length(); i++) {
                                String string = optJSONArray.getString(i);
                                if (UtilityImpl.m3755c(this.f3312h, string)) {
                                    this.f3315k.mo18491j().mo18378a(message2.f3283s);
                                } else {
                                    ALog.m3728i(this.f3316l, "unbind app", "pkg", string);
                                    this.f3315k.mo18482b(Message.m3454a(this.f3315k, string), true);
                                }
                            }
                        }
                    } else if (intValue == 2) {
                        this.f3315k.mo18491j().mo18379b(message2.f3283s);
                    } else if (intValue == 100) {
                        if ((this.f3315k instanceof C2057j) && !message2.f3278n.equals(Constants.TARGET_SERVICE_ST)) {
                            ((C2057j) this.f3315k).mo18516a(jSONObject);
                        }
                    }
                } else if (errorCode.getCodeInt() == AccsErrorCode.APP_NOT_BIND.getCodeInt()) {
                    this.f3315k.mo18491j().mo18379b(message2.f3283s);
                }
            } catch (Throwable th) {
                th = th;
                ALog.m3726e(this.f3316l, "handleControlMessage", th, new Object[0]);
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "handleControlMessage", "", this.f3306b + th.toString());
                mo18416a(message, errorCode, (Message.ReqType) null, bArr, (Map<Integer, String>) null);
                mo18418a(new TrafficsMonitor.C2082a(message2.f3249H, GlobalAppRuntimeInfo.isAppBackground(), str, (long) bArr2.length));
            }
            if (errorCode.getCodeInt() != AccsErrorCode.SUCCESS.getCodeInt()) {
                ALog.m3727e(this.f3316l, "handleControlMessage parse", "json", jSONObject.toString());
            }
        } catch (Throwable th2) {
            th = th2;
            byte[] bArr3 = bArr;
            ALog.m3726e(this.f3316l, "handleControlMessage", th, new Object[0]);
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "handleControlMessage", "", this.f3306b + th.toString());
            mo18416a(message, errorCode, (Message.ReqType) null, bArr, (Map<Integer, String>) null);
            mo18418a(new TrafficsMonitor.C2082a(message2.f3249H, GlobalAppRuntimeInfo.isAppBackground(), str, (long) bArr2.length));
        }
        mo18416a(message, errorCode, (Message.ReqType) null, bArr, (Map<Integer, String>) null);
        mo18418a(new TrafficsMonitor.C2082a(message2.f3249H, GlobalAppRuntimeInfo.isAppBackground(), str, (long) bArr2.length));
    }

    /* renamed from: a */
    private Map<Integer, String> m3499a(C2091h hVar) {
        HashMap hashMap;
        if (hVar == null) {
            return null;
        }
        try {
            int b = hVar.mo18601b();
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.m3725d(this.f3316l, "extHeaderLen:" + b, new Object[0]);
            }
            hashMap = null;
            int i = 0;
            while (i < b) {
                try {
                    int b2 = hVar.mo18601b();
                    int i2 = (64512 & b2) >> 10;
                    int i3 = b2 & Message.EXT_HEADER_VALUE_MAX_LEN;
                    String a = hVar.mo18600a(i3);
                    i = i + 2 + i3;
                    if (hashMap == null) {
                        hashMap = new HashMap();
                    }
                    hashMap.put(Integer.valueOf(i2), a);
                    if (ALog.isPrintLog(ALog.Level.D)) {
                        ALog.m3725d(this.f3316l, "", "extHeaderType", Integer.valueOf(i2), "value", a);
                    }
                } catch (Exception e) {
                    e = e;
                    ALog.m3726e(this.f3316l, "parseExtHeader", e, new Object[0]);
                    return hashMap;
                }
            }
        } catch (Exception e2) {
            e = e2;
            hashMap = null;
            ALog.m3726e(this.f3316l, "parseExtHeader", e, new Object[0]);
            return hashMap;
        }
        return hashMap;
    }

    /* renamed from: a */
    public void mo18415a(Message message, ErrorCode errorCode) {
        mo18416a(message, errorCode, (Message.ReqType) null, (byte[]) null, (Map<Integer, String>) null);
    }

    /* renamed from: a */
    public void mo18417a(Message message, ErrorCode errorCode, Map<Integer, String> map) {
        mo18416a(message, errorCode, (Message.ReqType) null, (byte[]) null, map);
    }

    /* renamed from: a */
    public void mo18416a(Message message, ErrorCode errorCode, Message.ReqType reqType, byte[] bArr, Map<Integer, String> map) {
        Map<Integer, String> map2;
        byte[] bArr2;
        Message.ReqType reqType2;
        ErrorCode errorCode2;
        Message message2 = message;
        if (message2.f3284t == null || message.mo18386a() < 0 || message.mo18386a() == 2) {
            ALog.m3725d(this.f3316l, "onError, skip ping/ack", new Object[0]);
            return;
        }
        if (message2.f3256O != null) {
            this.f3305a.remove(message2.f3256O);
        }
        Map<Integer, String> map3 = map;
        int a = this.f3308d.mo18440a(map3, message2.f3249H);
        if (a != 0) {
            if (a == 2) {
                errorCode2 = AccsErrorCode.SERVIER_HIGH_LIMIT;
            } else if (a == 3) {
                errorCode2 = AccsErrorCode.SERVIER_HIGH_LIMIT_BRUSH;
            } else {
                errorCode2 = AccsErrorCode.SERVIER_LOW_LIMIT;
            }
            reqType2 = null;
            bArr2 = null;
            map2 = null;
        } else {
            errorCode2 = errorCode;
            bArr2 = bArr;
            map2 = map3;
            reqType2 = reqType;
        }
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.m3725d(this.f3316l, "onResult", "command", message2.f3284t, "erorcode", errorCode2);
        }
        if (message2.f3284t.intValue() != 102) {
            if ((message2.f3284t.intValue() == 1 && errorCode2.getCodeInt() != AccsErrorCode.SUCCESS.getCodeInt()) || errorCode2.getCodeInt() == AccsErrorCode.APP_NOT_BIND.getCodeInt()) {
                this.f3315k.mo18491j().mo18379b(message2.f3283s);
            }
            if (message2.f3269e) {
                ALog.m3727e(this.f3316l, "onResult message is cancel", "command", message2.f3284t);
            } else if (!m3508b(errorCode2) || message2.f3284t.intValue() == 100 || message2.f3259R > Message.f3240a) {
                Intent c = m3509c(message);
                c.putExtra(Constants.KEY_ERROR_OBJ, errorCode2);
                Message.ReqType valueOf = Message.ReqType.valueOf(3 & (message2.f3275k >> 13));
                if (reqType2 == Message.ReqType.RES || valueOf == Message.ReqType.REQ) {
                    c.putExtra(Constants.KEY_SEND_TYPE, Constants.SEND_TYPE_RES);
                }
                if (errorCode2.getCodeInt() == AccsErrorCode.SUCCESS.getCodeInt()) {
                    c.putExtra(Constants.KEY_DATA, bArr2);
                }
                c.putExtra(Constants.KEY_APP_KEY, this.f3315k.f3374b);
                c.putExtra(Constants.KEY_CONFIG_TAG, this.f3315k.f3385m);
                m3504a(map2, c);
                C2033g.m3535a(this.f3312h, this.f3315k, c);
                if (!TextUtils.isEmpty(message2.f3249H)) {
                    UTMini.getInstance().commitEvent(66001, "MsgToBuss0", "commandId=" + message2.f3284t, "serviceId=" + message2.f3249H + " errorCode=" + errorCode2 + " dataId=" + message2.f3281q, Integer.valueOf(Constants.SDK_VERSION_CODE));
                    StringBuilder sb = new StringBuilder();
                    sb.append("1commandId=");
                    sb.append(message2.f3284t);
                    sb.append("serviceId=");
                    sb.append(message2.f3249H);
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_TO_BUSS, sb.toString(), 0.0d);
                }
            } else {
                message2.f3261T = System.currentTimeMillis();
                message2.f3259R++;
                ALog.m3725d(this.f3316l, "onResult", "retryTimes", Integer.valueOf(message2.f3259R));
                this.f3315k.mo18482b(message2, true);
            }
            NetPerformanceMonitor e = message.mo18393e();
            if (e != null) {
                e.onToBizDate();
                String url = message2.f3270f == null ? null : message2.f3270f.toString();
                if (errorCode2.getCodeInt() == AccsErrorCode.SUCCESS.getCodeInt()) {
                    e.setRet(true);
                    if (message2.f3259R > 0) {
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "succ", 0.0d);
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "succ_" + message2.f3259R, 0.0d);
                    } else {
                        AppMonitorAdapter.commitAlarmSuccess("accs", BaseMonitor.ALARM_POINT_REQUEST, url);
                    }
                } else {
                    if (message2.f3259R > 0) {
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "fail＿" + errorCode2, 0.0d);
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "fail", 0.0d);
                    } else if (errorCode2.getCodeInt() != AccsErrorCode.NO_NETWORK.getCodeInt()) {
                        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQUEST, url, UtilityImpl.m3735a(errorCode2.getCodeInt()), this.f3306b + message2.f3249H + message2.f3260S);
                    }
                    e.setRet(false);
                    e.setFailReason(errorCode2);
                }
                AppMonitor.getInstance().commitStat(message.mo18393e());
            }
            m3507b(message2, errorCode2);
        }
    }

    /* renamed from: b */
    private boolean m3508b(ErrorCode errorCode) {
        return errorCode.getCodeInt() == AccsErrorCode.SPDY_CONNECTION_DISCONNECTED_WHEN_SEND_DATA.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.REQ_TIME_OUT.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.SPDY_CON_DISCONNECTED.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.INAPP_CON_DISCONNECTED.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.SPDY_PING_TIME_OUT.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.NO_NETWORK.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.NETWORKSDK_SPDY_CLOSE_ERROR.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.NETWORK_INAPP_TIMEOUT.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.NETWORK_INAPP_CONNECT_FAIL.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.NETWORK_INAPP_NO_STRATEGY.getCodeInt() || errorCode.getCodeInt() == AccsErrorCode.NETWORK_INAPP_EXCEPTION.getCodeInt();
    }

    /* renamed from: a */
    public void mo18412a() {
        ALog.m3725d(this.f3316l, "onSendPing", new Object[0]);
        synchronized (C2030d.class) {
            this.f3311g = true;
        }
    }

    /* renamed from: b */
    public void mo18422b() {
        ALog.m3725d(this.f3316l, "onRcvPing", new Object[0]);
        synchronized (C2030d.class) {
            this.f3311g = false;
        }
    }

    /* renamed from: c */
    public boolean mo18424c() {
        return this.f3311g;
    }

    /* renamed from: a */
    public void mo18414a(Message message) {
        if (!(this.f3314j == null || message.f3256O == null || message.f3249H == null || !this.f3314j.f3256O.equals(message.f3256O) || !this.f3314j.f3249H.equals(message.f3249H))) {
            UTMini.getInstance().commitEvent(66001, "SEND_REPEAT", message.f3249H, message.f3256O, Long.valueOf(Thread.currentThread().getId()));
        }
        if (message.mo18386a() != -1 && message.mo18386a() != 2 && !message.f3267c) {
            this.f3310f.put(message.mo18392d(), message);
        }
    }

    /* renamed from: a */
    public void mo18413a(ErrorCode errorCode) {
        this.f3311g = false;
        Message.C2024a[] aVarArr = (Message.C2024a[]) this.f3310f.keySet().toArray(new Message.C2024a[0]);
        if (aVarArr.length > 0) {
            ALog.m3725d(this.f3316l, "onNetworkFail", new Object[0]);
            for (Message.C2024a remove : aVarArr) {
                Message message = (Message) this.f3310f.remove(remove);
                if (message != null) {
                    mo18415a(message, errorCode);
                }
            }
        }
    }

    /* renamed from: b */
    public void mo18423b(Message message) {
        if (this.f3310f.keySet().size() > 0) {
            for (Message.C2024a aVar : this.f3310f.keySet()) {
                Message message2 = (Message) this.f3310f.get(aVar);
                if (!(message2 == null || message2.f3284t == null || !message2.mo18394f().equals(message.mo18394f()))) {
                    switch (message.f3284t.intValue()) {
                        case 1:
                        case 2:
                            if (message2.f3284t.intValue() == 1 || message2.f3284t.intValue() == 2) {
                                message2.f3269e = true;
                                break;
                            }
                        case 3:
                        case 4:
                            if (message2.f3284t.intValue() == 3 || message2.f3284t.intValue() == 4) {
                                message2.f3269e = true;
                                break;
                            }
                        case 5:
                        case 6:
                            if (message2.f3284t.intValue() == 5 || message2.f3284t.intValue() == 6) {
                                message2.f3269e = true;
                                break;
                            }
                    }
                }
                if (message2 != null && message2.f3269e) {
                    ALog.m3727e(this.f3316l, "cancelControlMessage", "command", message2.f3284t);
                }
            }
        }
    }

    /* renamed from: d */
    public int mo18425d() {
        return this.f3310f.size();
    }

    /* renamed from: e */
    public Collection<Message> mo18426e() {
        return this.f3310f.values();
    }

    /* renamed from: f */
    public Set<Message.C2024a> mo18427f() {
        return this.f3310f.keySet();
    }

    /* renamed from: a */
    public Message mo18411a(String str) {
        return (Message) this.f3310f.get(new Message.C2024a(0, str));
    }

    /* renamed from: b */
    public Message mo18421b(String str) {
        if (!TextUtils.isEmpty(str)) {
            return (Message) this.f3310f.remove(new Message.C2024a(0, str));
        }
        return null;
    }

    /* renamed from: c */
    private boolean m3510c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f3317m.containsKey(str);
    }

    /* renamed from: a */
    private byte[] m3506a(String str, Map<Integer, String> map, byte[] bArr) {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    int parseInt = Integer.parseInt(map.get(17));
                    int parseInt2 = Integer.parseInt(map.get(16));
                    if (parseInt2 <= 1) {
                        throw new RuntimeException("burstNums <= 1");
                    } else if (parseInt < 0 || parseInt >= parseInt2) {
                        throw new RuntimeException(String.format("burstNums:%s burstIndex:%s", new Object[]{Integer.valueOf(parseInt2), Integer.valueOf(parseInt)}));
                    } else {
                        String str2 = map.get(18);
                        long j = 0;
                        String str3 = map.get(15);
                        if (!TextUtils.isEmpty(str3)) {
                            j = Long.parseLong(str3);
                        }
                        C2027a aVar = this.f3318n.get(str);
                        if (aVar == null) {
                            if (ALog.isPrintLog(ALog.Level.I)) {
                                ALog.m3728i(this.f3316l, "putBurstMessage", Constants.KEY_DATA_ID, str, "burstLength", Integer.valueOf(parseInt2));
                            }
                            aVar = new C2027a(str, parseInt2, str2);
                            aVar.mo18406a(j);
                            this.f3318n.put(str, aVar);
                        }
                        return aVar.mo18407a(parseInt, parseInt2, bArr);
                    }
                }
            } catch (Throwable th) {
                ALog.m3730w(this.f3316l, "putBurstMessage", th, new Object[0]);
                return null;
            }
        }
        throw new RuntimeException("burstLength == 0");
    }

    /* renamed from: d */
    private void m3511d(String str) {
        if (!TextUtils.isEmpty(str) && !this.f3317m.containsKey(str)) {
            this.f3317m.put(str, str);
            m3513j();
        }
    }

    /* renamed from: i */
    private void m3512i() {
        try {
            File dir = this.f3312h.getDir(Constants.SHARED_FOLDER, 0);
            File file = new File(dir, Constants.SHARED_MESSAGE_ID_FILE + this.f3315k.mo18490i());
            if (!file.exists()) {
                ALog.m3725d(this.f3316l, "message file not exist", new Object[0]);
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    this.f3317m.put(readLine, readLine);
                } else {
                    bufferedReader.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: j */
    private void m3513j() {
        try {
            File dir = this.f3312h.getDir(Constants.SHARED_FOLDER, 0);
            FileWriter fileWriter = new FileWriter(new File(dir, Constants.SHARED_MESSAGE_ID_FILE + this.f3315k.mo18490i()));
            fileWriter.write("");
            for (String append : this.f3317m.keySet()) {
                fileWriter.append(append).append("\r\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: c */
    private Intent m3509c(Message message) {
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(message.f3283s);
        intent.putExtra("command", message.f3284t);
        intent.putExtra(Constants.KEY_SERVICE_ID, message.f3249H);
        intent.putExtra(Constants.KEY_USER_ID, message.f3248G);
        if (message.f3284t != null && message.f3284t.intValue() == 100) {
            intent.putExtra(Constants.KEY_DATA_ID, message.f3256O);
        }
        return intent;
    }

    /* renamed from: a */
    private void m3504a(Map<Integer, String> map, Intent intent) {
        if (map != null && intent != null) {
            intent.putExtra(TaoBaseService.ExtraInfo.EXT_HEADER, (HashMap) map);
        }
    }

    /* renamed from: a */
    private void m3501a(Intent intent, String str, String str2, short s) {
        if (intent != null) {
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("source", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                intent.putExtra(Constants.KEY_TARGET, str2);
            }
            intent.putExtra(Constants.KEY_FLAGS, s);
        }
    }

    /* renamed from: g */
    public C2079d mo18428g() {
        return this.f3313i;
    }

    /* renamed from: b */
    private void m3507b(Message message, ErrorCode errorCode) {
        if (message != null) {
            String deviceId = UtilityImpl.getDeviceId(this.f3312h);
            String str = System.currentTimeMillis() + "";
            boolean z = errorCode.getCodeInt() == AccsErrorCode.SUCCESS.getCodeInt();
            int intValue = message.f3284t.intValue();
            if (intValue == 1) {
                C2076a aVar = new C2076a();
                aVar.f3485a = deviceId;
                aVar.f3486b = str;
                aVar.f3487c = z;
                aVar.mo18534a(errorCode);
                aVar.mo18533a();
            } else if (intValue == 3) {
                C2077b bVar = new C2077b();
                bVar.f3491a = deviceId;
                bVar.f3492b = str;
                bVar.f3493c = z;
                bVar.f3495e = message.f3248G;
                bVar.mo18537a(errorCode);
                bVar.mo18536a();
            }
        }
    }

    /* renamed from: a */
    private void m3503a(String str, String str2) {
        C2080e eVar = new C2080e();
        eVar.f3520a = UtilityImpl.getDeviceId(this.f3312h);
        eVar.f3522c = str;
        eVar.f3523d = "" + System.currentTimeMillis();
        eVar.f3525f = "";
        eVar.f3524e = str2;
        eVar.f3521b = "";
        eVar.mo18541a();
    }

    /* renamed from: h */
    public void mo18429h() {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(this.f3319o);
        } catch (Throwable th) {
            ALog.m3726e(this.f3316l, "restoreTraffics", th, new Object[0]);
        }
    }

    /* renamed from: a */
    public void mo18418a(TrafficsMonitor.C2082a aVar) {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(new C2031e(this, aVar));
        } catch (Throwable th) {
            ALog.m3726e(this.f3316l, "addTrafficsInfo", th, new Object[0]);
        }
    }
}
