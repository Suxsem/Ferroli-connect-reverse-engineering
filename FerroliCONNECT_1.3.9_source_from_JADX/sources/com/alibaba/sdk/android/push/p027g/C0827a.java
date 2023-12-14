package com.alibaba.sdk.android.push.p027g;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import anet.channel.util.AppLifecycle;
import anetwork.channel.http.NetworkSdkSetting;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.p009a.C0669a;
import com.alibaba.sdk.android.ams.common.p010b.C0672b;
import com.alibaba.sdk.android.ams.common.p010b.C0673c;
import com.alibaba.sdk.android.ams.common.util.C0680a;
import com.alibaba.sdk.android.ams.common.util.C0683c;
import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.push.PushControlService;
import com.alibaba.sdk.android.push.common.p020a.C0801a;
import com.alibaba.sdk.android.push.common.p020a.C0802b;
import com.alibaba.sdk.android.push.common.p020a.C0804d;
import com.alibaba.sdk.android.push.common.util.AppInfoUtil;
import com.alibaba.sdk.android.push.common.util.C0808a;
import com.alibaba.sdk.android.push.common.util.C0816c;
import com.alibaba.sdk.android.push.common.util.p021a.C0809a;
import com.alibaba.sdk.android.push.common.util.p021a.C0812d;
import com.alibaba.sdk.android.push.p018b.C0799f;
import com.alibaba.sdk.android.push.p019c.C0800a;
import com.alibaba.sdk.android.push.p026f.C0822b;
import com.igexin.sdk.PushConsts;
import com.p108ut.device.UTDevice;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsException;
import com.taobao.accs.AccsState;
import com.taobao.accs.ConnectionListener;
import com.taobao.accs.common.Constants;
import com.taobao.agoo.IRegister;
import com.taobao.agoo.TaobaoRegister;
import com.taobao.agoo.p105a.p106a.C2126c;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;

/* renamed from: com.alibaba.sdk.android.push.g.a */
public class C0827a {
    /* access modifiers changed from: private */

    /* renamed from: e */
    public static AmsLogger f1177e = AmsLogger.getLogger("MPS:AppRegister");

    /* renamed from: f */
    private static C0827a f1178f = null;

    /* renamed from: g */
    private static final String[] f1179g = {"amdcopen.m.taobao.com", "amdcopen.m.taobao.com", "amdcopen.m.taobao.com"};

    /* renamed from: h */
    private static final IntentFilter f1180h = new IntentFilter(PushConsts.ACTION_BROADCAST_NETWORK_CHANGE);

    /* renamed from: i */
    private static final IntentFilter f1181i = new IntentFilter(PushConsts.ACTION_BROADCAST_USER_PRESENT);

    /* renamed from: a */
    volatile C0831a<C0838d> f1182a;

    /* renamed from: b */
    volatile boolean f1183b = false;

    /* renamed from: c */
    volatile boolean f1184c = false;

    /* renamed from: d */
    volatile boolean f1185d = true;

    /* renamed from: j */
    private final C0835b f1186j = new C0835b();

    /* renamed from: com.alibaba.sdk.android.push.g.a$a */
    class C0831a<Token> extends HandlerThread {

        /* renamed from: a */
        Handler f1193a;

        /* renamed from: b */
        Handler f1194b;

        /* renamed from: c */
        C0837c<Token> f1195c;

        /* renamed from: d */
        volatile int f1196d = 0;

        /* renamed from: e */
        int f1197e = 0;

        /* renamed from: g */
        private Token f1199g;

        public C0831a() {
            super("ConnectionWorker");
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x0217, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
            r12 = com.alibaba.sdk.android.push.p027g.C0827a.m829g();
            r12.mo9538d("Catch StopProcessException: " + r0.mo9878a() + " stack:" + android.util.Log.getStackTraceString(r0));
            r12 = new com.alibaba.sdk.android.push.p027g.C0839e(r0.mo9878a());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x0249, code lost:
            r13 = java.lang.System.currentTimeMillis() - r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
            r0 = com.alibaba.sdk.android.push.p027g.C0827a.m829g();
            r0.mo9546i("connState=" + r1.f1197e + ";estimatedTime=" + r13 + ";response=" + r12.mo9926a() + ";network=" + com.alibaba.sdk.android.push.common.util.C0808a.m778b(r7), (java.lang.Throwable) null, 1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:0x0281, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x0282, code lost:
            com.alibaba.sdk.android.push.p027g.C0827a.m829g().mo9542e("ut log error", r0);
         */
        /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x00e1 */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00e5 A[Catch:{ f -> 0x0217, Throwable -> 0x019a, all -> 0x0197 }] */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x0135 A[SYNTHETIC, Splitter:B:43:0x0135] */
        /* JADX WARNING: Removed duplicated region for block: B:67:0x0217 A[ExcHandler: f (r0v0 'e' com.alibaba.sdk.android.push.b.f A[CUSTOM_DECLARE]), Splitter:B:28:0x00ce] */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.alibaba.sdk.android.push.p027g.C0839e m844a(Token r20) {
            /*
                r19 = this;
                r1 = r19
                java.lang.String r2 = ";network="
                java.lang.String r3 = ";response="
                java.lang.String r4 = ";estimatedTime="
                java.lang.String r5 = "connState="
                java.lang.String r6 = "ut log error"
                android.content.Context r7 = com.alibaba.sdk.android.ams.common.p009a.C0669a.m467b()
                long r8 = java.lang.System.currentTimeMillis()
                r10 = 1
                r11 = 0
                android.content.Context r0 = r7.getApplicationContext()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                boolean r0 = com.alibaba.sdk.android.push.common.util.C0816c.m790a(r0)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                r12 = 2
                if (r0 != 0) goto L_0x006b
                r1.f1197e = r12     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                com.alibaba.sdk.android.push.g.e r12 = new com.alibaba.sdk.android.push.g.e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                com.alibaba.sdk.android.error.ErrorCode r0 = com.alibaba.sdk.android.push.common.p020a.C0804d.f1091a     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                r12.<init>(r0)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                long r13 = java.lang.System.currentTimeMillis()
                long r13 = r13 - r8
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ Exception -> 0x0062 }
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0062 }
                r8.<init>()     // Catch:{ Exception -> 0x0062 }
                r8.append(r5)     // Catch:{ Exception -> 0x0062 }
                int r5 = r1.f1197e     // Catch:{ Exception -> 0x0062 }
                r8.append(r5)     // Catch:{ Exception -> 0x0062 }
                r8.append(r4)     // Catch:{ Exception -> 0x0062 }
                r8.append(r13)     // Catch:{ Exception -> 0x0062 }
                r8.append(r3)     // Catch:{ Exception -> 0x0062 }
                com.alibaba.sdk.android.error.ErrorCode r3 = r12.mo9926a()     // Catch:{ Exception -> 0x0062 }
                r8.append(r3)     // Catch:{ Exception -> 0x0062 }
                r8.append(r2)     // Catch:{ Exception -> 0x0062 }
                com.alibaba.sdk.android.push.common.util.b.a$a r2 = com.alibaba.sdk.android.push.common.util.C0808a.m778b(r7)     // Catch:{ Exception -> 0x0062 }
                r8.append(r2)     // Catch:{ Exception -> 0x0062 }
                java.lang.String r2 = r8.toString()     // Catch:{ Exception -> 0x0062 }
                r0.mo9546i(r2, r11, r10)     // Catch:{ Exception -> 0x0062 }
                goto L_0x006a
            L_0x0062:
                r0 = move-exception
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e
                r2.mo9542e(r6, r0)
            L_0x006a:
                return r12
            L_0x006b:
                int r0 = r1.f1197e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                if (r0 != 0) goto L_0x009e
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                r13.<init>()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                java.lang.String r14 = "is debug："
                r13.append(r14)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                boolean r14 = com.alibaba.sdk.android.push.common.p020a.C0802b.m769e()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                r13.append(r14)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                java.lang.String r13 = r13.toString()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                r0.mo9538d(r13)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                boolean r0 = com.alibaba.sdk.android.push.common.p020a.C0802b.m769e()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                if (r0 == 0) goto L_0x009b
                com.alibaba.sdk.android.push.g.a r0 = com.alibaba.sdk.android.push.p027g.C0827a.this     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                r0.m832j()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                com.alibaba.sdk.android.push.g.a r0 = com.alibaba.sdk.android.push.p027g.C0827a.this     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                r0.m831i()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
            L_0x009b:
                r1.m845a((android.content.Context) r7)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
            L_0x009e:
                boolean r0 = com.alibaba.sdk.android.ams.common.p009a.C0669a.m468c()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                if (r0 != 0) goto L_0x00e1
                boolean r0 = com.alibaba.sdk.android.push.notification.C0864e.m987a((android.content.Context) r7)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                if (r0 != 0) goto L_0x00e1
                long r13 = android.os.SystemClock.elapsedRealtime()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
            L_0x00ae:
                boolean r0 = com.alibaba.sdk.android.push.notification.C0864e.m987a((android.content.Context) r7)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                if (r0 != 0) goto L_0x00ce
                long r15 = android.os.SystemClock.elapsedRealtime()     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                long r15 = r15 - r13
                r17 = 10000(0x2710, double:4.9407E-320)
                int r0 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
                if (r0 >= 0) goto L_0x00ce
                r15 = 1000(0x3e8, double:4.94E-321)
                java.lang.Thread.sleep(r15)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                java.lang.String r15 = "wait for app come to foreground"
                r0.mo9538d(r15)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                goto L_0x00ae
            L_0x00ce:
                boolean r0 = com.alibaba.sdk.android.push.notification.C0864e.m987a((android.content.Context) r7)     // Catch:{ Throwable -> 0x00e1, f -> 0x0217 }
                if (r0 != 0) goto L_0x00e1
                r0 = 0
                anet.channel.AwcnConfig.setIpv6Enable(r0)     // Catch:{ Throwable -> 0x00e1, f -> 0x0217 }
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ Throwable -> 0x00e1, f -> 0x0217 }
                java.lang.String r13 = "APP is background, disable ipv6 test"
                r0.mo9538d(r13)     // Catch:{ Throwable -> 0x00e1, f -> 0x0217 }
            L_0x00e1:
                int r0 = r1.f1197e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                if (r0 != r10) goto L_0x0135
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                java.lang.String r12 = "accs init."
                r0.mo9538d(r12)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                com.alibaba.sdk.android.push.g.e r12 = r1.m846b(r7)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                long r13 = java.lang.System.currentTimeMillis()
                long r13 = r13 - r8
                if (r12 == 0) goto L_0x0134
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ Exception -> 0x012c }
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x012c }
                r8.<init>()     // Catch:{ Exception -> 0x012c }
                r8.append(r5)     // Catch:{ Exception -> 0x012c }
                int r5 = r1.f1197e     // Catch:{ Exception -> 0x012c }
                r8.append(r5)     // Catch:{ Exception -> 0x012c }
                r8.append(r4)     // Catch:{ Exception -> 0x012c }
                r8.append(r13)     // Catch:{ Exception -> 0x012c }
                r8.append(r3)     // Catch:{ Exception -> 0x012c }
                com.alibaba.sdk.android.error.ErrorCode r3 = r12.mo9926a()     // Catch:{ Exception -> 0x012c }
                r8.append(r3)     // Catch:{ Exception -> 0x012c }
                r8.append(r2)     // Catch:{ Exception -> 0x012c }
                com.alibaba.sdk.android.push.common.util.b.a$a r2 = com.alibaba.sdk.android.push.common.util.C0808a.m778b(r7)     // Catch:{ Exception -> 0x012c }
                r8.append(r2)     // Catch:{ Exception -> 0x012c }
                java.lang.String r2 = r8.toString()     // Catch:{ Exception -> 0x012c }
                r0.mo9546i(r2, r11, r10)     // Catch:{ Exception -> 0x012c }
                goto L_0x0134
            L_0x012c:
                r0 = move-exception
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e
                r2.mo9542e(r6, r0)
            L_0x0134:
                return r12
            L_0x0135:
                int r0 = r1.f1197e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                if (r0 != r12) goto L_0x0146
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                java.lang.String r12 = "accs connected.setBindStop."
                r0.mo9538d(r12)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                java.lang.System.currentTimeMillis()
                return r11
            L_0x0146:
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                java.lang.String r12 = "cant entry this block..."
                r0.mo9541e(r12)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                com.alibaba.sdk.android.push.g.e r12 = new com.alibaba.sdk.android.push.g.e     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                com.alibaba.sdk.android.error.ErrorCode r0 = com.alibaba.sdk.android.push.common.p020a.C0804d.f1102l     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                r12.<init>(r0)     // Catch:{ f -> 0x0217, Throwable -> 0x019a }
                long r13 = java.lang.System.currentTimeMillis()
                long r13 = r13 - r8
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ Exception -> 0x018e }
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x018e }
                r8.<init>()     // Catch:{ Exception -> 0x018e }
                r8.append(r5)     // Catch:{ Exception -> 0x018e }
                int r5 = r1.f1197e     // Catch:{ Exception -> 0x018e }
                r8.append(r5)     // Catch:{ Exception -> 0x018e }
                r8.append(r4)     // Catch:{ Exception -> 0x018e }
                r8.append(r13)     // Catch:{ Exception -> 0x018e }
                r8.append(r3)     // Catch:{ Exception -> 0x018e }
                com.alibaba.sdk.android.error.ErrorCode r3 = r12.mo9926a()     // Catch:{ Exception -> 0x018e }
                r8.append(r3)     // Catch:{ Exception -> 0x018e }
                r8.append(r2)     // Catch:{ Exception -> 0x018e }
                com.alibaba.sdk.android.push.common.util.b.a$a r2 = com.alibaba.sdk.android.push.common.util.C0808a.m778b(r7)     // Catch:{ Exception -> 0x018e }
                r8.append(r2)     // Catch:{ Exception -> 0x018e }
                java.lang.String r2 = r8.toString()     // Catch:{ Exception -> 0x018e }
                r0.mo9546i(r2, r11, r10)     // Catch:{ Exception -> 0x018e }
                goto L_0x0196
            L_0x018e:
                r0 = move-exception
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e
                r2.mo9542e(r6, r0)
            L_0x0196:
                return r12
            L_0x0197:
                r0 = move-exception
                goto L_0x028a
            L_0x019a:
                r0 = move-exception
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r12 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ all -> 0x0197 }
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0197 }
                r13.<init>()     // Catch:{ all -> 0x0197 }
                java.lang.String r14 = "Catch RuntimeException: "
                r13.append(r14)     // Catch:{ all -> 0x0197 }
                java.lang.String r14 = r0.getMessage()     // Catch:{ all -> 0x0197 }
                r13.append(r14)     // Catch:{ all -> 0x0197 }
                java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0197 }
                r12.mo9538d(r13)     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.push.g.e r12 = new com.alibaba.sdk.android.push.g.e     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.error.ErrorCode r13 = com.alibaba.sdk.android.push.common.p020a.C0804d.f1101k     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.error.ErrorBuilder r13 = r13.copy()     // Catch:{ all -> 0x0197 }
                java.lang.String r14 = r0.getMessage()     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.error.ErrorBuilder r13 = r13.msg(r14)     // Catch:{ all -> 0x0197 }
                java.lang.String r0 = android.util.Log.getStackTraceString(r0)     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.error.ErrorBuilder r0 = r13.detail(r0)     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.error.ErrorCode r0 = r0.build()     // Catch:{ all -> 0x0197 }
                r12.<init>(r0)     // Catch:{ all -> 0x0197 }
                long r13 = java.lang.System.currentTimeMillis()
                long r13 = r13 - r8
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ Exception -> 0x020e }
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x020e }
                r8.<init>()     // Catch:{ Exception -> 0x020e }
                r8.append(r5)     // Catch:{ Exception -> 0x020e }
                int r5 = r1.f1197e     // Catch:{ Exception -> 0x020e }
                r8.append(r5)     // Catch:{ Exception -> 0x020e }
                r8.append(r4)     // Catch:{ Exception -> 0x020e }
                r8.append(r13)     // Catch:{ Exception -> 0x020e }
                r8.append(r3)     // Catch:{ Exception -> 0x020e }
                com.alibaba.sdk.android.error.ErrorCode r3 = r12.mo9926a()     // Catch:{ Exception -> 0x020e }
                r8.append(r3)     // Catch:{ Exception -> 0x020e }
                r8.append(r2)     // Catch:{ Exception -> 0x020e }
                com.alibaba.sdk.android.push.common.util.b.a$a r2 = com.alibaba.sdk.android.push.common.util.C0808a.m778b(r7)     // Catch:{ Exception -> 0x020e }
                r8.append(r2)     // Catch:{ Exception -> 0x020e }
                java.lang.String r2 = r8.toString()     // Catch:{ Exception -> 0x020e }
                r0.mo9546i(r2, r11, r10)     // Catch:{ Exception -> 0x020e }
                goto L_0x0216
            L_0x020e:
                r0 = move-exception
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e
                r2.mo9542e(r6, r0)
            L_0x0216:
                return r12
            L_0x0217:
                r0 = move-exception
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r12 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ all -> 0x0197 }
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0197 }
                r13.<init>()     // Catch:{ all -> 0x0197 }
                java.lang.String r14 = "Catch StopProcessException: "
                r13.append(r14)     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.error.ErrorCode r14 = r0.mo9878a()     // Catch:{ all -> 0x0197 }
                r13.append(r14)     // Catch:{ all -> 0x0197 }
                java.lang.String r14 = " stack:"
                r13.append(r14)     // Catch:{ all -> 0x0197 }
                java.lang.String r14 = android.util.Log.getStackTraceString(r0)     // Catch:{ all -> 0x0197 }
                r13.append(r14)     // Catch:{ all -> 0x0197 }
                java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0197 }
                r12.mo9538d(r13)     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.push.g.e r12 = new com.alibaba.sdk.android.push.g.e     // Catch:{ all -> 0x0197 }
                com.alibaba.sdk.android.error.ErrorCode r0 = r0.mo9878a()     // Catch:{ all -> 0x0197 }
                r12.<init>(r0)     // Catch:{ all -> 0x0197 }
                long r13 = java.lang.System.currentTimeMillis()
                long r13 = r13 - r8
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e     // Catch:{ Exception -> 0x0281 }
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0281 }
                r8.<init>()     // Catch:{ Exception -> 0x0281 }
                r8.append(r5)     // Catch:{ Exception -> 0x0281 }
                int r5 = r1.f1197e     // Catch:{ Exception -> 0x0281 }
                r8.append(r5)     // Catch:{ Exception -> 0x0281 }
                r8.append(r4)     // Catch:{ Exception -> 0x0281 }
                r8.append(r13)     // Catch:{ Exception -> 0x0281 }
                r8.append(r3)     // Catch:{ Exception -> 0x0281 }
                com.alibaba.sdk.android.error.ErrorCode r3 = r12.mo9926a()     // Catch:{ Exception -> 0x0281 }
                r8.append(r3)     // Catch:{ Exception -> 0x0281 }
                r8.append(r2)     // Catch:{ Exception -> 0x0281 }
                com.alibaba.sdk.android.push.common.util.b.a$a r2 = com.alibaba.sdk.android.push.common.util.C0808a.m778b(r7)     // Catch:{ Exception -> 0x0281 }
                r8.append(r2)     // Catch:{ Exception -> 0x0281 }
                java.lang.String r2 = r8.toString()     // Catch:{ Exception -> 0x0281 }
                r0.mo9546i(r2, r11, r10)     // Catch:{ Exception -> 0x0281 }
                goto L_0x0289
            L_0x0281:
                r0 = move-exception
                com.alibaba.sdk.android.ams.common.logger.AmsLogger r2 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e
                r2.mo9542e(r6, r0)
            L_0x0289:
                return r12
            L_0x028a:
                java.lang.System.currentTimeMillis()
                goto L_0x028f
            L_0x028e:
                throw r0
            L_0x028f:
                goto L_0x028e
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.p027g.C0827a.C0831a.m844a(java.lang.Object):com.alibaba.sdk.android.push.g.e");
        }

        /* renamed from: a */
        private void m845a(Context context) {
            AmsLogger g = C0827a.f1177e;
            g.mo9538d("load utdid: " + UTDevice.getUtdid(context));
            C0672b a = C0673c.m489a();
            String c = a.mo9530c();
            C0827a.f1177e.mo9538d("vip init.");
            String b = a.mo9528b();
            if (!StringUtil.isEmpty(b) && !StringUtil.isBlank(c) && c.equals(UTDevice.getUtdid(context))) {
                AmsLogger importantLogger = AmsLogger.getImportantLogger();
                importantLogger.mo9544i("Got deviceId from preference: " + b);
                this.f1197e = 1;
            } else if (C0808a.m777a(context)) {
                String c2 = m847c();
                AmsLogger importantLogger2 = AmsLogger.getImportantLogger();
                importantLogger2.mo9544i("Got deviceId from remote server: " + c2);
                if (!StringUtil.isEmpty(c2)) {
                    a.mo9526a(c2);
                    a.mo9529b(UTDevice.getUtdid(context));
                    this.f1197e = 1;
                    AmsLogger.getImportantLogger().mo9544i("vip init success");
                    return;
                }
                throw new C0799f(C0804d.f1097g.copy().msg("获取设备ID失败").detail("getDeviceIdFromServer").build());
            } else {
                throw new C0799f(C0804d.f1100j);
            }
        }

        /* renamed from: b */
        private C0839e m846b(Context context) {
            String str;
            C0827a.f1177e.mo9538d("initAccsChannel...");
            NetworkSdkSetting.init(context.getApplicationContext());
            C0672b a = C0673c.m489a();
            String a2 = a.mo9524a();
            String d = a.mo9532d();
            AmsLogger importantLogger = AmsLogger.getImportantLogger();
            importantLogger.mo9544i("register agoo appkey:" + a2);
            final C0822b bVar = new C0822b();
            final C0839e[] eVarArr = {null};
            try {
                AmsLogger g = C0827a.f1177e;
                g.mo9538d("init aliyun accs. context:" + context.getPackageName() + " -- appkey:" + a2);
                ACCSClient.getAccsClient("AliyunPush").cleanLocalBindInfo();
                AppLifecycle.onForeground();
                TaobaoRegister.register(context.getApplicationContext(), "AliyunPush", a2, d, "aliyun", new IRegister() {
                    public void onFailure(String str, String str2) {
                        AmsLogger importantLogger = AmsLogger.getImportantLogger();
                        importantLogger.mo9544i("agoo errorcode:" + str + ";errorMsg:" + str2);
                        eVarArr[0] = new C0839e(C0804d.m775a(str, str2).detail(C2126c.JSON_CMD_REGISTER).build());
                        bVar.mo9902a();
                    }

                    public void onSuccess(String str) {
                        AmsLogger.getImportantLogger().mo9544i("agoo init success.");
                        C0831a.this.f1197e = 2;
                        eVarArr[0] = new C0839e(C0804d.f1091a);
                        bVar.mo9902a();
                    }
                });
            } catch (Throwable th) {
                C0827a.f1177e.mo9542e("accs config failed", th);
                eVarArr[0] = new C0839e(C0804d.f1101k.copy().msg(th.getMessage()).detail(Log.getStackTraceString(th)).build());
                bVar.mo9902a();
            }
            if (!C0816c.m790a(context.getApplicationContext())) {
                C0827a.this.f1185d = true;
                C0827a.f1177e.mo9538d("not main process");
                return new C0839e(C0804d.f1104n);
            }
            AmsLogger g2 = C0827a.f1177e;
            g2.mo9538d("lock" + bVar.toString());
            bVar.mo9903a(150);
            if (eVarArr[0] == null) {
                try {
                    str = AccsState.getInstance().getStateByKey(AccsState.RECENT_ERRORS);
                } catch (Exception unused) {
                    str = "accs time out";
                }
                ErrorBuilder msg = C0804d.f1105o.copy().msg(str);
                eVarArr[0] = new C0839e(msg.detail("connected:" + C0827a.this.mo9908c()).build());
            }
            AmsLogger importantLogger2 = AmsLogger.getImportantLogger();
            importantLogger2.mo9538d("register agoo result " + eVarArr[0].mo9926a());
            return eVarArr[0];
        }

        /* renamed from: c */
        private String m847c() {
            C0672b a = C0673c.m489a();
            String e = C0669a.m470e();
            Context b = C0669a.m467b();
            HttpURLConnection httpURLConnection = null;
            try {
                HashMap hashMap = new HashMap();
                hashMap.put(Constants.KEY_APP_KEY, a.mo9524a());
                hashMap.put("deviceId", UTDevice.getUtdid(b));
                hashMap.put(Constants.SP_KEY_VERSION, "3.7.6");
                hashMap.put("utdid", UTDevice.getUtdid(b));
                hashMap.put(Constants.KEY_OS_VERSION, "2");
                hashMap.put("package", C0669a.m472g());
                httpURLConnection = C0680a.m512a(e, C0683c.m536a(hashMap), "POST");
                if (httpURLConnection != null) {
                    String a2 = C0858i.m902a(C0812d.CONFIG.mo9894a(), httpURLConnection);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return a2;
                }
                C0827a.f1177e.mo9541e("failed to loadConfigFromRemote!");
                throw new C0809a(C0804d.f1106p.copy().msg("getDeviceId创建请求连接失败").build());
            } catch (IOException e2) {
                throw new C0799f(C0804d.f1106p.copy().msg(e2.getMessage()).detail(Log.getStackTraceString(e2)).build());
            } catch (C0799f e3) {
                throw e3;
            } catch (Throwable th) {
                try {
                    C0827a.f1177e.mo9548w("loadConfigFromRemote failed! error:", th);
                    throw new C0799f(C0804d.f1101k.copy().msg(th.getMessage()).detail(Log.getStackTraceString(th)).build());
                } catch (Throwable th2) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th2;
                }
            }
        }

        /* renamed from: a */
        public synchronized void mo9917a() {
            this.f1196d = 0;
            if (!C0827a.this.f1185d || this.f1197e != 2) {
                this.f1193a.sendMessage(this.f1193a.obtainMessage(1, this.f1199g));
            }
        }

        /* renamed from: a */
        public void mo9918a(C0837c<Token> cVar) {
            this.f1195c = cVar;
        }

        /* renamed from: a */
        public synchronized boolean mo9919a(C0839e eVar) {
            if (this.f1197e == 2 || this.f1196d >= 5) {
                return false;
            }
            AmsLogger g = C0827a.f1177e;
            g.mo9538d("init retry:" + this.f1196d);
            this.f1196d = this.f1196d + 1;
            this.f1193a.sendMessageDelayed(this.f1193a.obtainMessage(2, this.f1199g), (long) (((int) Math.pow(3.0d, (double) this.f1196d)) * DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT));
            return true;
        }

        /* renamed from: b */
        public void mo9920b() {
            this.f1193a.removeMessages(1);
            this.f1193a.removeMessages(2);
        }

        /* access modifiers changed from: protected */
        @SuppressLint({"HandlerLeak"})
        public void onLooperPrepared() {
            this.f1194b = new Handler(Looper.getMainLooper());
            this.f1193a = new Handler() {
                /* JADX WARNING: Code restructure failed: missing block: B:9:0x0035, code lost:
                    r7 = com.alibaba.sdk.android.push.p027g.C0827a.C0831a.m843a(r6.f1200a, r0);
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void handleMessage(android.os.Message r7) {
                    /*
                        r6 = this;
                        int r0 = r7.what
                        r1 = 2
                        r2 = 1
                        if (r0 == r2) goto L_0x000a
                        int r0 = r7.what
                        if (r0 != r1) goto L_0x0057
                    L_0x000a:
                        java.lang.Object r0 = r7.obj
                        com.alibaba.sdk.android.ams.common.logger.AmsLogger r3 = com.alibaba.sdk.android.push.p027g.C0827a.f1177e
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        r4.<init>()
                        java.lang.String r5 = "Looping handleMessage: "
                        r4.append(r5)
                        int r5 = r7.what
                        r4.append(r5)
                        java.lang.String r4 = r4.toString()
                        r3.mo9538d(r4)
                        int r7 = r7.what
                        if (r7 != r2) goto L_0x002d
                        r6.removeMessages(r1)
                    L_0x002d:
                        com.alibaba.sdk.android.push.g.a$a r7 = com.alibaba.sdk.android.push.p027g.C0827a.C0831a.this
                        com.alibaba.sdk.android.push.g.a r7 = com.alibaba.sdk.android.push.p027g.C0827a.this
                        boolean r7 = r7.f1185d
                        if (r7 != 0) goto L_0x0057
                        com.alibaba.sdk.android.push.g.a$a r7 = com.alibaba.sdk.android.push.p027g.C0827a.C0831a.this
                        com.alibaba.sdk.android.push.g.e r7 = r7.m844a(r0)
                        if (r7 == 0) goto L_0x0057
                        com.alibaba.sdk.android.push.g.a$a r1 = com.alibaba.sdk.android.push.p027g.C0827a.C0831a.this
                        boolean r1 = r1.mo9919a((com.alibaba.sdk.android.push.p027g.C0839e) r7)
                        if (r1 == 0) goto L_0x004b
                        com.alibaba.sdk.android.push.g.a$a r1 = com.alibaba.sdk.android.push.p027g.C0827a.C0831a.this
                        int r1 = r1.f1196d
                        if (r1 > r2) goto L_0x0057
                    L_0x004b:
                        com.alibaba.sdk.android.push.g.a$a r1 = com.alibaba.sdk.android.push.p027g.C0827a.C0831a.this
                        android.os.Handler r1 = r1.f1194b
                        com.alibaba.sdk.android.push.g.a$a$1$1 r2 = new com.alibaba.sdk.android.push.g.a$a$1$1
                        r2.<init>(r0, r7)
                        r1.post(r2)
                    L_0x0057:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.p027g.C0827a.C0831a.C08321.handleMessage(android.os.Message):void");
                }
            };
            C0827a.f1177e.mo9538d("Looping Prepared.");
            C0827a.this.f1183b = true;
            mo9917a();
        }
    }

    /* renamed from: com.alibaba.sdk.android.push.g.a$b */
    class C0835b extends BroadcastReceiver {
        C0835b() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (PushConsts.ACTION_BROADCAST_NETWORK_CHANGE.equals(intent.getAction())) {
                    if (intent.getBooleanExtra("noConnectivity", false)) {
                        C0827a.f1177e.mo9541e("Network has lost");
                        return;
                    } else if (C0827a.this.f1185d || !C0827a.this.f1183b) {
                        return;
                    }
                } else if (!PushConsts.ACTION_BROADCAST_USER_PRESENT.equals(intent.getAction()) || !C0808a.m777a(context) || C0827a.this.f1185d || !C0827a.this.f1183b) {
                    return;
                }
                C0827a.this.f1182a.mo9917a();
            }
        }
    }

    private C0827a() {
    }

    /* renamed from: a */
    public static C0827a m825a() {
        if (f1178f == null) {
            synchronized (C0827a.class) {
                if (f1178f == null) {
                    f1178f = new C0827a();
                }
            }
        }
        return f1178f;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x007e, code lost:
        r8.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x007d, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0033 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0036 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0039 */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m828b(boolean r8, long r9) {
        /*
            r7 = this;
            java.lang.String r0 = "AliyunPush"
            android.content.Context r1 = com.alibaba.sdk.android.ams.common.p009a.C0669a.m467b()
            com.alibaba.sdk.android.ams.common.b.b r2 = com.alibaba.sdk.android.ams.common.p010b.C0673c.m489a()
            java.lang.String r3 = r2.mo9524a()
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r4 = com.alibaba.sdk.android.ams.common.logger.AmsLogger.getImportantLogger()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "init agoo config appkey:"
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            r4.mo9544i(r5)
            java.lang.String r4 = r2.mo9532d()
            java.lang.String[] r5 = f1179g
            anet.channel.strategy.dispatch.DispatchConstants.setAmdcServerDomain(r5)
            r5 = 0
            anet.channel.AwcnConfig.setWifiInfoEnable(r5)     // Catch:{ Throwable -> 0x0033 }
        L_0x0033:
            anet.channel.AwcnConfig.setCarrierInfoEnable(r5)     // Catch:{ Throwable -> 0x0036 }
        L_0x0036:
            anet.channel.AwcnConfig.setAccsSessionCreateForbiddenInBg(r5)     // Catch:{ Throwable -> 0x0039 }
        L_0x0039:
            com.taobao.agoo.TaobaoRegister.setEnv(r1, r5)     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.AccsClientConfig$Builder r6 = new com.taobao.accs.AccsClientConfig$Builder     // Catch:{ AccsException -> 0x007d }
            r6.<init>()     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.AccsClientConfig$Builder r3 = r6.setAppKey(r3)     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.AccsClientConfig$Builder r3 = r3.setAppSecret(r4)     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.AccsClientConfig$Builder r3 = r3.setTag(r0)     // Catch:{ AccsException -> 0x007d }
            java.lang.String r4 = "acs4public.m.taobao.com"
            com.taobao.accs.AccsClientConfig$Builder r3 = r3.setInappHost(r4)     // Catch:{ AccsException -> 0x007d }
            java.lang.String r4 = "accscdn4public.m.taobao.com"
            com.taobao.accs.AccsClientConfig$Builder r3 = r3.setChannelHost(r4)     // Catch:{ AccsException -> 0x007d }
            r4 = 1
            com.taobao.accs.AccsClientConfig$Builder r3 = r3.setAccsHeartbeatEnable(r4)     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.AccsClientConfig$Builder r3 = r3.setConfigEnv(r5)     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.AccsClientConfig$Builder r8 = r3.loopChannelStart(r8)     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.AccsClientConfig$Builder r8 = r8.loopChannelInterval(r9)     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.AccsClientConfig r8 = r8.build()     // Catch:{ AccsException -> 0x007d }
            com.taobao.agoo.TaobaoRegister.setAccsConfigTag(r1, r0)     // Catch:{ AccsException -> 0x007d }
            com.taobao.accs.ACCSClient.init(r1, r8)     // Catch:{ AccsException -> 0x007d }
            com.alibaba.sdk.android.push.g.a$1 r8 = new com.alibaba.sdk.android.push.g.a$1     // Catch:{ AccsException -> 0x007d }
            r8.<init>(r2)     // Catch:{ AccsException -> 0x007d }
            com.taobao.agoo.TaobaoRegister.setReportPushArrive(r8)     // Catch:{ AccsException -> 0x007d }
            goto L_0x0081
        L_0x007d:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0081:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.p027g.C0827a.m828b(boolean, long):void");
    }

    /* renamed from: h */
    private void m830h() {
        Context b = C0669a.m467b();
        if (C0816c.m790a(b)) {
            try {
                b.registerReceiver(this.f1186j, f1180h);
                b.registerReceiver(this.f1186j, f1181i);
            } catch (Exception e) {
                f1177e.mo9542e("Fail to register broad", e);
            }
        }
        if (AppInfoUtil.isChannelProcess(b)) {
            C0800a.m760a(b);
            C0800a.m759a().mo9879b();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public void m831i() {
        String channelServiceData = AppInfoUtil.getChannelServiceData(C0669a.m467b(), 1);
        int lastIndexOf = channelServiceData.lastIndexOf(":");
        if (-1 != lastIndexOf) {
            String substring = channelServiceData.substring(lastIndexOf);
            if (StringUtil.isEmpty(substring) || !substring.equals(C0802b.m768d())) {
                throw new C0799f(C0804d.f1108r);
            }
            String e = C0673c.m489a().mo9534e();
            if (StringUtil.isEmpty(e) || e.length() > 32) {
                throw new C0799f(C0804d.f1109s);
            }
            return;
        }
        throw new C0799f(C0804d.f1110t);
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public void m832j() {
        for (C0801a aVar : C0801a.values()) {
            if (!AppInfoUtil.isComponentExists(C0669a.m467b(), aVar.mo9882a(), aVar.mo9883b())) {
                if (!aVar.mo9884c()) {
                    f1177e.mo9547w("未配置" + aVar.mo9882a() + "; 建议配置,可有效提高推送到达率");
                } else {
                    throw new C0799f(C0804d.f1111u.copy().msg(aVar.mo9882a() + "未配置").build());
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0020, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo9904a(final com.alibaba.sdk.android.push.CommonCallback r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.f1184c     // Catch:{ all -> 0x0063 }
            if (r0 == 0) goto L_0x0021
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r0 = com.alibaba.sdk.android.ams.common.logger.AmsLogger.getImportantLogger()     // Catch:{ all -> 0x0063 }
            java.lang.String r1 = "Already startReg, skip."
            r0.mo9538d(r1)     // Catch:{ all -> 0x0063 }
            if (r3 == 0) goto L_0x001f
            com.alibaba.sdk.android.error.ErrorCode r0 = com.alibaba.sdk.android.push.common.p020a.C0804d.f1115y     // Catch:{ all -> 0x0063 }
            java.lang.String r0 = r0.getCode()     // Catch:{ all -> 0x0063 }
            com.alibaba.sdk.android.error.ErrorCode r1 = com.alibaba.sdk.android.push.common.p020a.C0804d.f1115y     // Catch:{ all -> 0x0063 }
            java.lang.String r1 = r1.getMsg()     // Catch:{ all -> 0x0063 }
            r3.onFailed(r0, r1)     // Catch:{ all -> 0x0063 }
        L_0x001f:
            monitor-exit(r2)
            return
        L_0x0021:
            r0 = 1
            r2.f1184c = r0     // Catch:{ all -> 0x0063 }
            r2.m830h()     // Catch:{ all -> 0x0063 }
            r0 = 0
            r2.f1185d = r0     // Catch:{ all -> 0x0063 }
            com.alibaba.sdk.android.push.g.a$a<com.alibaba.sdk.android.push.g.d> r0 = r2.f1182a     // Catch:{ all -> 0x0063 }
            if (r0 == 0) goto L_0x0044
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0040 }
            r1 = 18
            if (r0 < r1) goto L_0x003a
            com.alibaba.sdk.android.push.g.a$a<com.alibaba.sdk.android.push.g.d> r0 = r2.f1182a     // Catch:{ Exception -> 0x0040 }
            r0.quitSafely()     // Catch:{ Exception -> 0x0040 }
            goto L_0x0044
        L_0x003a:
            com.alibaba.sdk.android.push.g.a$a<com.alibaba.sdk.android.push.g.d> r0 = r2.f1182a     // Catch:{ Exception -> 0x0040 }
            r0.quit()     // Catch:{ Exception -> 0x0040 }
            goto L_0x0044
        L_0x0040:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0063 }
        L_0x0044:
            com.alibaba.sdk.android.push.g.a$a r0 = new com.alibaba.sdk.android.push.g.a$a     // Catch:{ all -> 0x0063 }
            r0.<init>()     // Catch:{ all -> 0x0063 }
            r2.f1182a = r0     // Catch:{ all -> 0x0063 }
            com.alibaba.sdk.android.push.g.a$a<com.alibaba.sdk.android.push.g.d> r0 = r2.f1182a     // Catch:{ all -> 0x0063 }
            com.alibaba.sdk.android.push.g.a$2 r1 = new com.alibaba.sdk.android.push.g.a$2     // Catch:{ all -> 0x0063 }
            r1.<init>(r3)     // Catch:{ all -> 0x0063 }
            r0.mo9918a(r1)     // Catch:{ all -> 0x0063 }
            com.alibaba.sdk.android.push.g.a$a<com.alibaba.sdk.android.push.g.d> r3 = r2.f1182a     // Catch:{ all -> 0x0063 }
            r3.start()     // Catch:{ all -> 0x0063 }
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r3 = f1177e     // Catch:{ all -> 0x0063 }
            java.lang.String r0 = "getLooper called."
            r3.mo9538d(r0)     // Catch:{ all -> 0x0063 }
            monitor-exit(r2)
            return
        L_0x0063:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.p027g.C0827a.mo9904a(com.alibaba.sdk.android.push.CommonCallback):void");
    }

    /* renamed from: a */
    public void mo9905a(final PushControlService.ConnectionChangeListener connectionChangeListener) {
        try {
            ACCSClient.getAccsClient("AliyunPush").addConnectionListener(new ConnectionListener() {
                public void onConnect() {
                    connectionChangeListener.onConnect();
                }

                public void onDisconnect(int i, String str) {
                    ErrorCode build = C0804d.m774a(i, str).build();
                    connectionChangeListener.onDisconnect(build.getCode(), build.getMsg());
                }
            });
        } catch (AccsException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void mo9906a(boolean z, long j) {
        m828b(z, j);
    }

    /* renamed from: b */
    public void mo9907b() {
        m828b(false, 0);
    }

    /* renamed from: c */
    public boolean mo9908c() {
        try {
            return ACCSClient.getAccsClient("AliyunPush").isConnected();
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    /* renamed from: d */
    public void mo9909d() {
        try {
            ACCSClient.getAccsClient("AliyunPush").reconnect();
        } catch (AccsException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: e */
    public void mo9910e() {
        TaobaoRegister.reset();
        this.f1184c = false;
    }

    /* renamed from: f */
    public void mo9911f() {
        try {
            ACCSClient.getAccsClient("AliyunPush").disconnect();
        } catch (AccsException e) {
            e.printStackTrace();
        }
    }
}
