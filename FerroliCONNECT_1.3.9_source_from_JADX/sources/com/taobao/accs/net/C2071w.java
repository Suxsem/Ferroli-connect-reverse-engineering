package com.taobao.accs.net;

import android.content.Context;
import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.strategy.IConnStrategy;
import anet.channel.util.HttpConstant;
import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.C2030d;
import com.taobao.accs.data.Message;
import com.taobao.accs.p103ut.monitor.SessionMonitor;
import com.taobao.accs.p103ut.p104a.C2078c;
import com.taobao.accs.p103ut.p104a.C2079d;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.C2089f;
import com.taobao.accs.utl.OrangeAdapter;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.Utils;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.UByte;
import org.android.spdy.RequestPriority;
import org.android.spdy.SessionCb;
import org.android.spdy.SessionInfo;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdyDataProvider;
import org.android.spdy.SpdyRequest;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.Spdycb;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;

/* renamed from: com.taobao.accs.net.w */
/* compiled from: Taobao */
public class C2071w extends C2049b implements SessionCb, Spdycb {

    /* renamed from: A */
    private Object f3448A = new Object();
    /* access modifiers changed from: private */

    /* renamed from: B */
    public long f3449B;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public long f3450C;

    /* renamed from: D */
    private long f3451D;

    /* renamed from: E */
    private long f3452E;

    /* renamed from: F */
    private int f3453F = -1;

    /* renamed from: G */
    private String f3454G = null;
    /* access modifiers changed from: private */

    /* renamed from: H */
    public SessionMonitor f3455H;

    /* renamed from: I */
    private C2078c f3456I;
    /* access modifiers changed from: private */

    /* renamed from: J */
    public boolean f3457J = false;
    /* access modifiers changed from: private */

    /* renamed from: K */
    public String f3458K = "";
    /* access modifiers changed from: private */

    /* renamed from: L */
    public boolean f3459L = false;
    /* access modifiers changed from: private */

    /* renamed from: M */
    public C2054g f3460M = new C2054g(mo18527r());
    /* access modifiers changed from: private */

    /* renamed from: N */
    public String f3461N;

    /* renamed from: n */
    protected ScheduledFuture<?> f3462n;

    /* renamed from: o */
    protected String f3463o;

    /* renamed from: p */
    protected int f3464p;

    /* renamed from: q */
    protected String f3465q;

    /* renamed from: r */
    protected int f3466r;
    /* access modifiers changed from: private */

    /* renamed from: s */
    public int f3467s = 3;
    /* access modifiers changed from: private */

    /* renamed from: t */
    public LinkedList<Message> f3468t = new LinkedList<>();

    /* renamed from: u */
    private C2072a f3469u;
    /* access modifiers changed from: private */

    /* renamed from: v */
    public boolean f3470v = true;

    /* renamed from: w */
    private String f3471w;

    /* renamed from: x */
    private String f3472x;

    /* renamed from: y */
    private SpdyAgent f3473y = null;
    /* access modifiers changed from: private */

    /* renamed from: z */
    public SpdySession f3474z = null;

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public boolean mo18489h() {
        return false;
    }

    /* renamed from: m */
    public int mo18494m() {
        return 0;
    }

    public C2071w(Context context, int i, String str) {
        super(context, i, str);
        m3689w();
    }

    /* renamed from: a */
    public void mo18469a() {
        this.f3470v = true;
        ALog.m3725d(mo18485d(), "start", new Object[0]);
        mo18470a(this.f3376d);
        if (this.f3469u == null) {
            ALog.m3728i(mo18485d(), "start thread", new Object[0]);
            this.f3469u = new C2072a("NetworkThread_" + this.f3385m);
            this.f3469u.setPriority(2);
            this.f3469u.start();
        }
        mo18475a(false, false);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18472a(Message message, boolean z) {
        if (!this.f3470v || message == null) {
            String d = mo18485d();
            ALog.m3727e(d, "not running or msg null! " + this.f3470v, new Object[0]);
            return;
        }
        try {
            if (ThreadPoolExecutorFactory.getScheduledExecutor().getQueue().size() <= 1000) {
                ScheduledFuture<?> schedule = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new C2073x(this, message, z), message.f3258Q, TimeUnit.MILLISECONDS);
                if (message.mo18386a() == 1 && message.f3256O != null) {
                    if (message.mo18391c()) {
                        mo18477a(message.f3256O);
                    }
                    this.f3377e.f3305a.put(message.f3256O, schedule);
                }
                if (message.mo18393e() != null) {
                    message.mo18393e().setDeviceId(UtilityImpl.getDeviceId(this.f3376d));
                    message.mo18393e().setConnType(this.f3375c);
                    message.mo18393e().onEnterQueueData();
                    return;
                }
                return;
            }
            throw new RejectedExecutionException("accs");
        } catch (RejectedExecutionException unused) {
            int size = ThreadPoolExecutorFactory.getScheduledExecutor().getQueue().size();
            C2030d dVar = this.f3377e;
            ErrorBuilder copy = AccsErrorCode.MESSAGE_QUEUE_FULL.copy();
            dVar.mo18415a(message, copy.detail("channel " + size).build());
            String d2 = mo18485d();
            ALog.m3727e(d2, "send queue full count:" + size, new Object[0]);
        } catch (Throwable th) {
            ALog.m3726e(mo18485d(), "send error", th, new Object[0]);
            this.f3377e.mo18415a(message, AccsErrorCode.SEND_LOCAL_EXCEPTION.copy().detail(AccsErrorCode.getExceptionInfo(th)).build());
        }
    }

    /* renamed from: e */
    public void mo18486e() {
        super.mo18486e();
        this.f3470v = false;
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new C2074y(this));
        ALog.m3727e(mo18485d(), "shut down", new Object[0]);
    }

    /* renamed from: a */
    public void mo18475a(boolean z, boolean z2) {
        String d = mo18485d();
        ALog.m3725d(d, "try ping, force:" + z, new Object[0]);
        if (this.f3375c == 1) {
            ALog.m3725d(mo18485d(), "INAPP, skip", new Object[0]);
        } else {
            mo18482b(Message.m3460a(z, (int) (z2 ? Math.random() * 10.0d * 1000.0d : 0.0d)), z);
        }
    }

    /* renamed from: q */
    public void mo18526q() {
        ALog.m3727e(mo18485d(), " force close!", new Object[0]);
        try {
            this.f3474z.closeSession();
            this.f3455H.setCloseType(1);
        } catch (Exception unused) {
        }
        m3678c(3);
    }

    /* renamed from: c */
    public C2078c mo18483c() {
        if (this.f3456I == null) {
            this.f3456I = new C2078c();
        }
        this.f3456I.f3499b = this.f3375c;
        this.f3456I.f3501d = this.f3468t.size();
        this.f3456I.f3506i = UtilityImpl.m3765g(this.f3376d);
        C2078c cVar = this.f3456I;
        cVar.f3503f = this.f3458K;
        cVar.f3498a = this.f3467s;
        SessionMonitor sessionMonitor = this.f3455H;
        int i = 0;
        cVar.f3500c = sessionMonitor != null && sessionMonitor.getRet();
        this.f3456I.f3507j = mo18528s();
        C2078c cVar2 = this.f3456I;
        if (this.f3377e != null) {
            i = this.f3377e.mo18425d();
        }
        cVar2.f3502e = i;
        C2078c cVar3 = this.f3456I;
        cVar3.f3504g = this.f3472x;
        return cVar3;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m3669a(Message message) {
        if (message.f3284t != null && this.f3468t.size() != 0) {
            for (int size = this.f3468t.size() - 1; size >= 0; size--) {
                Message message2 = this.f3468t.get(size);
                if (!(message2 == null || message2.f3284t == null || !message2.mo18394f().equals(message.mo18394f()))) {
                    switch (message.f3284t.intValue()) {
                        case 1:
                        case 2:
                            if (message2.f3284t.intValue() == 1 || message2.f3284t.intValue() == 2) {
                                this.f3468t.remove(size);
                                break;
                            }
                        case 3:
                        case 4:
                            if (message2.f3284t.intValue() == 3 || message2.f3284t.intValue() == 4) {
                                this.f3468t.remove(size);
                                break;
                            }
                        case 5:
                        case 6:
                            if (message2.f3284t.intValue() == 5 || message2.f3284t.intValue() == 6) {
                                this.f3468t.remove(size);
                                break;
                            }
                    }
                    ALog.m3725d(mo18485d(), "clearRepeatControlCommand message:" + message2.f3284t + "/" + message2.mo18394f(), new Object[0]);
                }
            }
            if (this.f3377e != null) {
                this.f3377e.mo18423b(message);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public void m3680d(String str) {
        SessionInfo sessionInfo;
        String str2 = str;
        int i = this.f3467s;
        if (i != 2 && i != 1) {
            if (this.f3460M == null) {
                this.f3460M = new C2054g(mo18527r());
            }
            List<IConnStrategy> a = this.f3460M.mo18509a(mo18527r());
            int i2 = Constants.PORT;
            if (a == null || a.size() <= 0) {
                if (str2 != null) {
                    this.f3463o = str2;
                } else {
                    this.f3463o = mo18527r();
                }
                if (System.currentTimeMillis() % 2 == 0) {
                    i2 = 80;
                }
                this.f3464p = i2;
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_DNS, "localdns", 0.0d);
                ALog.m3728i(mo18485d(), "connect get ip from amdc fail!!", new Object[0]);
            } else {
                for (IConnStrategy next : a) {
                    if (next != null) {
                        ALog.m3728i(mo18485d(), "connect", "ip", next.getIp(), "port", Integer.valueOf(next.getPort()));
                    }
                }
                if (this.f3459L) {
                    this.f3460M.mo18510b();
                    this.f3459L = false;
                }
                IConnStrategy a2 = this.f3460M.mo18507a();
                this.f3463o = a2 == null ? mo18527r() : a2.getIp();
                if (a2 != null) {
                    i2 = a2.getPort();
                }
                this.f3464p = i2;
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_DNS, "httpdns", 0.0d);
                ALog.m3728i(mo18485d(), "connect from amdc succ", "ip", this.f3463o, "port", Integer.valueOf(this.f3464p), "originPos", Integer.valueOf(this.f3460M.mo18512c()));
            }
            if (Utils.isIPV6Address(this.f3463o)) {
                this.f3471w = "https://[" + this.f3463o + "]:" + this.f3464p + "/accs/";
            } else {
                this.f3471w = "https://" + this.f3463o + ":" + this.f3464p + "/accs/";
            }
            ALog.m3728i(mo18485d(), "connect", "URL", this.f3471w);
            this.f3461N = String.valueOf(System.currentTimeMillis());
            if (this.f3455H != null) {
                AppMonitor.getInstance().commitStat(this.f3455H);
            }
            this.f3455H = new SessionMonitor();
            this.f3455H.setConnectType(this.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp");
            if (this.f3473y != null) {
                try {
                    this.f3451D = System.currentTimeMillis();
                    this.f3452E = System.nanoTime();
                    this.f3465q = UtilityImpl.m3737a(this.f3376d);
                    this.f3466r = UtilityImpl.m3748b(this.f3376d);
                    this.f3449B = System.currentTimeMillis();
                    this.f3455H.onStartConnect();
                    m3678c(2);
                    synchronized (this.f3448A) {
                        try {
                            if (TextUtils.isEmpty(this.f3465q) || this.f3466r < 0 || !this.f3457J) {
                                ALog.m3728i(mo18485d(), "connect normal", new Object[0]);
                                String str3 = this.f3463o;
                                int i3 = this.f3464p;
                                sessionInfo = new SessionInfo(str3, i3, mo18527r() + "_" + this.f3374b, (String) null, 0, this.f3461N, this, 4226);
                                this.f3458K = "";
                            } else {
                                ALog.m3728i(mo18485d(), "connect", "proxy", this.f3465q, "port", Integer.valueOf(this.f3466r));
                                String str4 = this.f3463o;
                                int i4 = this.f3464p;
                                sessionInfo = new SessionInfo(str4, i4, mo18527r() + "_" + this.f3374b, this.f3465q, this.f3466r, this.f3461N, this, 4226);
                                this.f3458K = this.f3465q + ":" + this.f3466r;
                            }
                            sessionInfo.setPubKeySeqNum(m3686t());
                            sessionInfo.setConnectionTimeoutMs(C2049b.ACCS_RECEIVE_TIMEOUT);
                            this.f3474z = this.f3473y.createSession(sessionInfo);
                            this.f3455H.connection_stop_date = 0;
                            this.f3448A.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            this.f3457J = false;
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    /* renamed from: t */
    private int m3686t() {
        boolean k = mo18492k();
        if (AccsClientConfig.mEnv == 2) {
            return 0;
        }
        int channelPubKey = this.f3381i.getChannelPubKey();
        if (channelPubKey <= 0) {
            return k ? 4 : 3;
        }
        ALog.m3728i(mo18485d(), "getPublicKeyType use custom pub key", "pubKey", Integer.valueOf(channelPubKey));
        return channelPubKey;
    }

    /* renamed from: u */
    private void m3687u() {
        if (this.f3474z == null) {
            m3678c(3);
            return;
        }
        try {
            String encode = URLEncoder.encode(UtilityImpl.getDeviceId(this.f3376d));
            String a = UtilityImpl.m3738a(mo18490i(), this.f3381i.getAppSecret(), UtilityImpl.getDeviceId(this.f3376d));
            String c = mo18484c(this.f3471w);
            ALog.m3728i(mo18485d(), BaseMonitor.ALARM_POINT_AUTH, "url", c);
            this.f3472x = c;
            if (!m3672a(encode, mo18490i(), a)) {
                ErrorBuilder copy = AccsErrorCode.SPDY_AUTH_PARAM_ERROR.copy();
                ErrorCode build = copy.detail("device " + encode + " key " + mo18490i() + " sign " + a).build();
                ALog.m3727e(mo18485d(), "auth param error!", "code", build);
                m3668a(build);
                return;
            }
            new URL(c);
            SpdyRequest spdyRequest = new SpdyRequest(new URL(c), "GET", RequestPriority.DEFAULT_PRIORITY, 80000, (int) C2049b.ACCS_RECEIVE_TIMEOUT);
            spdyRequest.setDomain(mo18527r());
            this.f3474z.submitRequest(spdyRequest, new SpdyDataProvider((byte[]) null), mo18527r(), this);
        } catch (Throwable th) {
            ALog.m3726e(mo18485d(), "auth exception ", th, new Object[0]);
            m3668a(AccsErrorCode.SPDY_AUTH_EXCEPTION.copy().detail(AccsErrorCode.getExceptionInfo(th)).build());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        if (android.text.TextUtils.isEmpty(r14) != false) goto L_0x0038;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0052  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m3672a(java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            r11 = this;
            android.content.Context r0 = r11.f3376d
            int r0 = com.taobao.accs.utl.Utils.getMode(r0)
            r1 = 2
            r2 = 1
            if (r0 != r1) goto L_0x000b
            return r2
        L_0x000b:
            boolean r0 = android.text.TextUtils.isEmpty(r12)
            r3 = 0
            if (r0 != 0) goto L_0x001e
            boolean r0 = android.text.TextUtils.isEmpty(r13)
            if (r0 != 0) goto L_0x001e
            boolean r0 = android.text.TextUtils.isEmpty(r14)
            if (r0 == 0) goto L_0x00b0
        L_0x001e:
            r0 = 3
            r11.m3678c((int) r0)
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 == 0) goto L_0x002a
        L_0x0028:
            r0 = 1
            goto L_0x0038
        L_0x002a:
            boolean r12 = android.text.TextUtils.isEmpty(r13)
            if (r12 == 0) goto L_0x0032
            r0 = 2
            goto L_0x0038
        L_0x0032:
            boolean r12 = android.text.TextUtils.isEmpty(r14)
            if (r12 == 0) goto L_0x0028
        L_0x0038:
            com.taobao.accs.ut.monitor.SessionMonitor r12 = r11.f3455H
            r12.setFailReason(r0)
            com.taobao.accs.ut.monitor.SessionMonitor r12 = r11.f3455H
            r12.onConnectStop()
            int r12 = r11.f3375c
            if (r12 != 0) goto L_0x0049
            java.lang.String r12 = "service"
            goto L_0x004b
        L_0x0049:
            java.lang.String r12 = "inapp"
        L_0x004b:
            com.taobao.accs.net.w$a r13 = r11.f3469u
            if (r13 == 0) goto L_0x0052
            int r13 = r13.f3475a
            goto L_0x0053
        L_0x0052:
            r13 = 0
        L_0x0053:
            com.taobao.accs.utl.UTMini r4 = com.taobao.accs.utl.UTMini.getInstance()
            r5 = 66001(0x101d1, float:9.2487E-41)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r6 = "DISCONNECT "
            r14.append(r6)
            r14.append(r12)
            java.lang.String r6 = r14.toString()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r13)
            r12 = 221(0xdd, float:3.1E-43)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r12)
            java.lang.String[] r10 = new java.lang.String[r1]
            java.lang.String r12 = r11.f3472x
            r10[r3] = r12
            java.lang.String r12 = r11.f3458K
            r10[r2] = r12
            r4.commitEvent((int) r5, (java.lang.String) r6, (java.lang.Object) r7, (java.lang.Object) r8, (java.lang.Object) r9, (java.lang.String[]) r10)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r14 = "retrytimes:"
            r12.append(r14)
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r0)
            java.lang.String r14 = ""
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            java.lang.String r0 = "accs"
            java.lang.String r1 = "connect"
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r0, r1, r12, r13, r14)
            r2 = 0
        L_0x00b0:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.C2071w.m3672a(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    /* renamed from: v */
    public synchronized void m3688v() {
        if (this.f3375c != 1) {
            this.f3449B = System.currentTimeMillis();
            this.f3450C = System.nanoTime();
            C2053f.m3619a(this.f3376d).mo18501a();
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0062 */
    /* renamed from: c */
    private synchronized void m3678c(int r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            java.lang.String r0 = r9.mo18485d()     // Catch:{ all -> 0x00e9 }
            java.lang.String r1 = "notifyStatus start"
            r2 = 2
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x00e9 }
            java.lang.String r4 = "status"
            r5 = 0
            r3[r5] = r4     // Catch:{ all -> 0x00e9 }
            java.lang.String r4 = r9.mo18468a((int) r10)     // Catch:{ all -> 0x00e9 }
            r6 = 1
            r3[r6] = r4     // Catch:{ all -> 0x00e9 }
            com.taobao.accs.utl.ALog.m3728i(r0, r1, r3)     // Catch:{ all -> 0x00e9 }
            int r0 = r9.f3467s     // Catch:{ all -> 0x00e9 }
            if (r10 != r0) goto L_0x002a
            java.lang.String r10 = r9.mo18485d()     // Catch:{ all -> 0x00e9 }
            java.lang.String r0 = "ignore notifyStatus"
            java.lang.Object[] r1 = new java.lang.Object[r5]     // Catch:{ all -> 0x00e9 }
            com.taobao.accs.utl.ALog.m3725d(r10, r0, r1)     // Catch:{ all -> 0x00e9 }
            monitor-exit(r9)
            return
        L_0x002a:
            r9.f3467s = r10     // Catch:{ all -> 0x00e9 }
            if (r10 == r6) goto L_0x00a1
            if (r10 == r2) goto L_0x0084
            r0 = 3
            if (r10 == r0) goto L_0x0036
            r0 = 4
            goto L_0x00ce
        L_0x0036:
            java.lang.String r0 = r9.mo18485d()     // Catch:{ all -> 0x00e9 }
            java.lang.String r1 = "notifyStatus"
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x00e9 }
            java.lang.String r4 = "status"
            r3[r5] = r4     // Catch:{ all -> 0x00e9 }
            java.lang.String r4 = r9.mo18468a((int) r10)     // Catch:{ all -> 0x00e9 }
            r3[r6] = r4     // Catch:{ all -> 0x00e9 }
            com.taobao.accs.utl.ALog.m3731w(r0, r1, r3)     // Catch:{ all -> 0x00e9 }
            r9.m3688v()     // Catch:{ all -> 0x00e9 }
            android.content.Context r0 = r9.f3376d     // Catch:{ all -> 0x00e9 }
            com.taobao.accs.net.f r0 = com.taobao.accs.net.C2053f.m3619a((android.content.Context) r0)     // Catch:{ all -> 0x00e9 }
            r0.mo18504d()     // Catch:{ all -> 0x00e9 }
            java.lang.Object r0 = r9.f3448A     // Catch:{ all -> 0x00e9 }
            monitor-enter(r0)     // Catch:{ all -> 0x00e9 }
            java.lang.Object r1 = r9.f3448A     // Catch:{ Exception -> 0x0062 }
            r1.notifyAll()     // Catch:{ Exception -> 0x0062 }
            goto L_0x0062
        L_0x0060:
            r10 = move-exception
            goto L_0x0082
        L_0x0062:
            monitor-exit(r0)     // Catch:{ all -> 0x0060 }
            com.taobao.accs.data.d r0 = r9.f3377e     // Catch:{ all -> 0x00e9 }
            com.alibaba.sdk.android.error.ErrorCode r1 = com.taobao.accs.AccsErrorCode.SPDY_CON_DISCONNECTED     // Catch:{ all -> 0x00e9 }
            com.alibaba.sdk.android.error.ErrorBuilder r1 = r1.copy()     // Catch:{ all -> 0x00e9 }
            com.taobao.accs.utl.i r3 = com.taobao.accs.utl.C2092i.m3788a()     // Catch:{ all -> 0x00e9 }
            java.lang.String r3 = r3.mo18604b()     // Catch:{ all -> 0x00e9 }
            com.alibaba.sdk.android.error.ErrorBuilder r1 = r1.detail(r3)     // Catch:{ all -> 0x00e9 }
            com.alibaba.sdk.android.error.ErrorCode r1 = r1.build()     // Catch:{ all -> 0x00e9 }
            r0.mo18413a((com.alibaba.sdk.android.error.ErrorCode) r1)     // Catch:{ all -> 0x00e9 }
            r9.mo18475a((boolean) r5, (boolean) r6)     // Catch:{ all -> 0x00e9 }
            goto L_0x00ce
        L_0x0082:
            monitor-exit(r0)     // Catch:{ all -> 0x0060 }
            throw r10     // Catch:{ all -> 0x00e9 }
        L_0x0084:
            java.util.concurrent.ScheduledFuture<?> r0 = r9.f3462n     // Catch:{ all -> 0x00e9 }
            if (r0 == 0) goto L_0x008d
            java.util.concurrent.ScheduledFuture<?> r0 = r9.f3462n     // Catch:{ all -> 0x00e9 }
            r0.cancel(r6)     // Catch:{ all -> 0x00e9 }
        L_0x008d:
            java.lang.String r0 = r9.f3461N     // Catch:{ all -> 0x00e9 }
            java.util.concurrent.ScheduledThreadPoolExecutor r1 = com.taobao.accs.common.ThreadPoolExecutorFactory.getScheduledExecutor()     // Catch:{ all -> 0x00e9 }
            com.taobao.accs.net.z r3 = new com.taobao.accs.net.z     // Catch:{ all -> 0x00e9 }
            r3.<init>(r9, r0)     // Catch:{ all -> 0x00e9 }
            r7 = 120000(0x1d4c0, double:5.9288E-319)
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x00e9 }
            r1.schedule(r3, r7, r0)     // Catch:{ all -> 0x00e9 }
            goto L_0x00ce
        L_0x00a1:
            android.content.Context r0 = r9.f3376d     // Catch:{ all -> 0x00e9 }
            com.taobao.accs.net.f r0 = com.taobao.accs.net.C2053f.m3619a((android.content.Context) r0)     // Catch:{ all -> 0x00e9 }
            r0.mo18506f()     // Catch:{ all -> 0x00e9 }
            r9.m3688v()     // Catch:{ all -> 0x00e9 }
            java.util.concurrent.ScheduledFuture<?> r0 = r9.f3462n     // Catch:{ all -> 0x00e9 }
            if (r0 == 0) goto L_0x00b6
            java.util.concurrent.ScheduledFuture<?> r0 = r9.f3462n     // Catch:{ all -> 0x00e9 }
            r0.cancel(r6)     // Catch:{ all -> 0x00e9 }
        L_0x00b6:
            java.lang.Object r0 = r9.f3448A     // Catch:{ all -> 0x00e9 }
            monitor-enter(r0)     // Catch:{ all -> 0x00e9 }
            java.lang.Object r1 = r9.f3448A     // Catch:{ Exception -> 0x00c1 }
            r1.notifyAll()     // Catch:{ Exception -> 0x00c1 }
            goto L_0x00c1
        L_0x00bf:
            r10 = move-exception
            goto L_0x00e7
        L_0x00c1:
            monitor-exit(r0)     // Catch:{ all -> 0x00bf }
            java.util.LinkedList<com.taobao.accs.data.Message> r0 = r9.f3468t     // Catch:{ all -> 0x00e9 }
            monitor-enter(r0)     // Catch:{ all -> 0x00e9 }
            java.util.LinkedList<com.taobao.accs.data.Message> r1 = r9.f3468t     // Catch:{ Exception -> 0x00cd }
            r1.notifyAll()     // Catch:{ Exception -> 0x00cd }
            goto L_0x00cd
        L_0x00cb:
            r10 = move-exception
            goto L_0x00e5
        L_0x00cd:
            monitor-exit(r0)     // Catch:{ all -> 0x00cb }
        L_0x00ce:
            java.lang.String r0 = r9.mo18485d()     // Catch:{ all -> 0x00e9 }
            java.lang.String r1 = "notifyStatus end"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00e9 }
            java.lang.String r3 = "status"
            r2[r5] = r3     // Catch:{ all -> 0x00e9 }
            java.lang.String r10 = r9.mo18468a((int) r10)     // Catch:{ all -> 0x00e9 }
            r2[r6] = r10     // Catch:{ all -> 0x00e9 }
            com.taobao.accs.utl.ALog.m3728i(r0, r1, r2)     // Catch:{ all -> 0x00e9 }
            monitor-exit(r9)
            return
        L_0x00e5:
            monitor-exit(r0)     // Catch:{ all -> 0x00cb }
            throw r10     // Catch:{ all -> 0x00e9 }
        L_0x00e7:
            monitor-exit(r0)     // Catch:{ all -> 0x00bf }
            throw r10     // Catch:{ all -> 0x00e9 }
        L_0x00e9:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.C2071w.m3678c(int):void");
    }

    /* renamed from: r */
    public String mo18527r() {
        String channelHost = this.f3381i.getChannelHost();
        ALog.m3728i(mo18485d(), "getChannelHost", Constants.KEY_HOST, channelHost);
        return channelHost == null ? "" : channelHost;
    }

    /* renamed from: w */
    private void m3689w() {
        try {
            SpdyAgent.enableDebug = true;
            this.f3473y = SpdyAgent.getInstance(this.f3376d, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
            if (SpdyAgent.checkLoadSucc()) {
                C2089f.m3779a();
                if (!mo18492k()) {
                    this.f3473y.setAccsSslCallback(new C2048aa(this));
                }
                if (!OrangeAdapter.isTnetLogOff(false)) {
                    String str = this.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp";
                    ALog.m3725d(mo18485d(), "into--[setTnetLogPath]", new Object[0]);
                    String d = UtilityImpl.m3757d(this.f3376d, str);
                    String d2 = mo18485d();
                    ALog.m3725d(d2, "config tnet log path:" + d, new Object[0]);
                    if (!TextUtils.isEmpty(d)) {
                        this.f3473y.configLogFile(d, UtilityImpl.TNET_FILE_SIZE, 5);
                        return;
                    }
                    return;
                }
                return;
            }
            ALog.m3727e(mo18485d(), "initClient", new Object[0]);
            C2089f.m3780b();
        } catch (Throwable th) {
            ALog.m3726e(mo18485d(), "initClient", th, new Object[0]);
        }
    }

    /* renamed from: com.taobao.accs.net.w$a */
    /* compiled from: Taobao */
    private class C2072a extends Thread {

        /* renamed from: a */
        public int f3475a = 0;

        /* renamed from: b */
        long f3476b;

        /* renamed from: d */
        private final String f3478d = getName();

        public C2072a(String str) {
            super(str);
        }

        /* renamed from: a */
        private void m3709a(boolean z) {
            if (C2071w.this.f3467s != 1) {
                ALog.m3725d(C2071w.this.mo18485d(), "tryConnect", "force", Boolean.valueOf(z));
                if (!UtilityImpl.m3765g(C2071w.this.f3376d)) {
                    ALog.m3727e(this.f3478d, "Network not available", new Object[0]);
                    return;
                }
                if (z) {
                    this.f3475a = 0;
                }
                ALog.m3728i(this.f3478d, "tryConnect", "force", Boolean.valueOf(z), "failTimes", Integer.valueOf(this.f3475a));
                if (C2071w.this.f3467s != 1 && this.f3475a >= 4) {
                    boolean unused = C2071w.this.f3457J = true;
                    ALog.m3727e(this.f3478d, "tryConnect fail", "maxTimes", 4);
                } else if (C2071w.this.f3467s != 1) {
                    if (C2071w.this.f3375c == 1 && this.f3475a == 0) {
                        ALog.m3728i(this.f3478d, "tryConnect in app, no sleep", new Object[0]);
                    } else {
                        ALog.m3728i(this.f3478d, "tryConnect, need sleep", new Object[0]);
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String unused2 = C2071w.this.f3458K = "";
                    if (this.f3475a == 3) {
                        C2071w.this.f3460M.mo18511b(C2071w.this.mo18527r());
                    }
                    C2071w.this.m3680d((String) null);
                    C2071w.this.f3455H.setRetryTimes(this.f3475a);
                    if (C2071w.this.f3467s != 1) {
                        this.f3475a++;
                        ALog.m3727e(this.f3478d, "try connect fail, ready for reconnect", new Object[0]);
                        m3709a(false);
                        return;
                    }
                    this.f3476b = System.currentTimeMillis();
                }
            } else if (C2071w.this.f3467s == 1 && System.currentTimeMillis() - this.f3476b > 5000) {
                this.f3475a = 0;
            }
        }

        /* JADX INFO: finally extract failed */
        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            	at java.base/java.util.Objects.checkIndex(Objects.java:372)
            	at java.base/java.util.ArrayList.get(ArrayList.java:458)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        public void run() {
            /*
                r21 = this;
                r1 = r21
                java.lang.String r2 = r1.f3478d
                r3 = 0
                java.lang.Object[] r4 = new java.lang.Object[r3]
                java.lang.String r5 = "NetworkThread run"
                com.taobao.accs.utl.ALog.m3728i(r2, r5, r4)
                r1.f3475a = r3
                r2 = 0
            L_0x000f:
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this
                boolean r4 = r4.f3470v
                if (r4 == 0) goto L_0x0524
                java.lang.String r4 = r1.f3478d
                java.lang.Object[] r5 = new java.lang.Object[r3]
                java.lang.String r6 = "ready to get message"
                com.taobao.accs.utl.ALog.m3725d(r4, r6, r5)
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this
                java.util.LinkedList r4 = r4.f3468t
                monitor-enter(r4)
                com.taobao.accs.net.w r5 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0520 }
                java.util.LinkedList r5 = r5.f3468t     // Catch:{ all -> 0x0520 }
                int r5 = r5.size()     // Catch:{ all -> 0x0520 }
                if (r5 != 0) goto L_0x004e
                java.lang.String r5 = r1.f3478d     // Catch:{ InterruptedException -> 0x0046 }
                java.lang.String r6 = "no message, wait"
                java.lang.Object[] r7 = new java.lang.Object[r3]     // Catch:{ InterruptedException -> 0x0046 }
                com.taobao.accs.utl.ALog.m3725d(r5, r6, r7)     // Catch:{ InterruptedException -> 0x0046 }
                com.taobao.accs.net.w r5 = com.taobao.accs.net.C2071w.this     // Catch:{ InterruptedException -> 0x0046 }
                java.util.LinkedList r5 = r5.f3468t     // Catch:{ InterruptedException -> 0x0046 }
                r5.wait()     // Catch:{ InterruptedException -> 0x0046 }
                goto L_0x004e
            L_0x0046:
                r0 = move-exception
                r2 = r0
                r2.printStackTrace()     // Catch:{ all -> 0x0520 }
                monitor-exit(r4)     // Catch:{ all -> 0x0520 }
                goto L_0x0524
            L_0x004e:
                java.lang.String r5 = r1.f3478d     // Catch:{ all -> 0x0520 }
                java.lang.String r6 = "try get message"
                java.lang.Object[] r7 = new java.lang.Object[r3]     // Catch:{ all -> 0x0520 }
                com.taobao.accs.utl.ALog.m3725d(r5, r6, r7)     // Catch:{ all -> 0x0520 }
                com.taobao.accs.net.w r5 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0520 }
                java.util.LinkedList r5 = r5.f3468t     // Catch:{ all -> 0x0520 }
                int r5 = r5.size()     // Catch:{ all -> 0x0520 }
                if (r5 == 0) goto L_0x007c
                com.taobao.accs.net.w r2 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0520 }
                java.util.LinkedList r2 = r2.f3468t     // Catch:{ all -> 0x0520 }
                java.lang.Object r2 = r2.getFirst()     // Catch:{ all -> 0x0520 }
                com.taobao.accs.data.Message r2 = (com.taobao.accs.data.Message) r2     // Catch:{ all -> 0x0520 }
                com.taobao.accs.ut.monitor.NetPerformanceMonitor r5 = r2.mo18393e()     // Catch:{ all -> 0x0520 }
                if (r5 == 0) goto L_0x007c
                com.taobao.accs.ut.monitor.NetPerformanceMonitor r5 = r2.mo18393e()     // Catch:{ all -> 0x0520 }
                r5.onTakeFromQueue()     // Catch:{ all -> 0x0520 }
            L_0x007c:
                monitor-exit(r4)     // Catch:{ all -> 0x0520 }
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this
                boolean r4 = r4.f3470v
                if (r4 != 0) goto L_0x0087
                goto L_0x0524
            L_0x0087:
                if (r2 == 0) goto L_0x000f
                java.lang.String r4 = r1.f3478d
                java.lang.Object[] r5 = new java.lang.Object[r3]
                java.lang.String r6 = "sendMessage not null"
                com.taobao.accs.utl.ALog.m3725d(r4, r6, r5)
                r4 = 201(0xc9, float:2.82E-43)
                r5 = 100
                r6 = 1
                int r7 = r2.mo18386a()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r8 = r1.f3478d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r9 = "sendMessage"
                r10 = 4
                java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r12 = "type"
                r11[r3] = r12     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r12 = com.taobao.accs.data.Message.C2026c.m3490b(r7)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r11[r6] = r12     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r12 = "status"
                r13 = 2
                r11[r13] = r12     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r12 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r12 = r12.f3467s     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r14 = 3
                r11[r14] = r12     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.utl.ALog.m3728i(r8, r9, r11)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r7 != r13) goto L_0x01bf
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r7 = r7.f3375c     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r7 != r6) goto L_0x00ff
                java.lang.String r7 = r1.f3478d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r8 = "sendMessage INAPP ping, skip"
                java.lang.Object[] r9 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.utl.ALog.m3725d(r7, r8, r9)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r4 = r1.f3478d     // Catch:{ Throwable -> 0x00f2 }
                java.lang.String r5 = "send succ, remove it"
                java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00f2 }
                com.taobao.accs.utl.ALog.m3725d(r4, r5, r6)     // Catch:{ Throwable -> 0x00f2 }
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x00f2 }
                java.util.LinkedList r4 = r4.f3468t     // Catch:{ Throwable -> 0x00f2 }
                monitor-enter(r4)     // Catch:{ Throwable -> 0x00f2 }
                com.taobao.accs.net.w r5 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x00ee }
                java.util.LinkedList r5 = r5.f3468t     // Catch:{ all -> 0x00ee }
                r5.remove(r2)     // Catch:{ all -> 0x00ee }
                monitor-exit(r4)     // Catch:{ all -> 0x00ee }
                goto L_0x000f
            L_0x00ee:
                r0 = move-exception
                r5 = r0
                monitor-exit(r4)     // Catch:{ all -> 0x00ee }
                throw r5     // Catch:{ Throwable -> 0x00f2 }
            L_0x00f2:
                r0 = move-exception
                r4 = r0
                java.lang.String r5 = r1.f3478d
                java.lang.Object[] r6 = new java.lang.Object[r3]
                java.lang.String r7 = " run finally error"
                com.taobao.accs.utl.ALog.m3726e(r5, r7, r4, r6)
                goto L_0x000f
            L_0x00ff:
                long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r11 = r9.f3449B     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r7 = r7 - r11
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                android.content.Context r9 = r9.f3376d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.f r9 = com.taobao.accs.net.C2053f.m3619a((android.content.Context) r9)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r9 = r9.mo18502b()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r9 = r9 - r6
                int r9 = r9 * 1000
                long r11 = (long) r9     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r9 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
                if (r9 >= 0) goto L_0x0128
                boolean r7 = r2.f3268d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r7 == 0) goto L_0x0123
                goto L_0x0128
            L_0x0123:
                r1.m3709a(r3)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                goto L_0x02dc
            L_0x0128:
                java.lang.String r7 = r1.f3478d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r8 = "sendMessage"
                java.lang.Object[] r9 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r10 = "force"
                r9[r3] = r10     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                boolean r10 = r2.f3268d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.Boolean r10 = java.lang.Boolean.valueOf(r10)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r9[r6] = r10     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r10 = "last ping"
                r9[r13] = r10     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r12 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r12 = r12.f3449B     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r10 = r10 - r12
                java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r9[r14] = r10     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.utl.ALog.m3725d(r7, r8, r9)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r1.m3709a(r6)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                org.android.spdy.SpdySession r7 = r7.f3474z     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r7 == 0) goto L_0x02c4
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r7 = r7.f3467s     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r7 != r6) goto L_0x02c4
                long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r9 = r9.f3449B     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r7 = r7 - r9
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                android.content.Context r9 = r9.f3376d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.f r9 = com.taobao.accs.net.C2053f.m3619a((android.content.Context) r9)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r9 = r9.mo18502b()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r9 = r9 - r6
                int r9 = r9 * 1000
                long r9 = (long) r9     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r11 < 0) goto L_0x02dc
                java.lang.String r7 = r1.f3478d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r8 = "sendMessage onSendPing"
                java.lang.Object[] r9 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.utl.ALog.m3728i(r7, r8, r9)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.data.d r7 = r7.f3377e     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r7.mo18412a()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                org.android.spdy.SpdySession r7 = r7.f3474z     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r7.submitPing()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.ut.monitor.SessionMonitor r7 = r7.f3455H     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r7.onSendPing()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long unused = r7.f3449B = r8     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r8 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long unused = r7.f3450C = r8     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r7.mo18487f()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                goto L_0x02dc
            L_0x01bf:
                if (r7 != r6) goto L_0x02c6
                r1.m3709a(r6)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r7 = r7.f3467s     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r7 != r6) goto L_0x02c4
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                org.android.spdy.SpdySession r7 = r7.f3474z     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r7 == 0) goto L_0x02c4
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                android.content.Context r7 = r7.f3376d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r8 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r8 = r8.f3375c     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                byte[] r7 = r2.mo18389a((android.content.Context) r7, (int) r8)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r2.mo18388a((long) r8)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r8 = r7.length     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r9 = 16384(0x4000, float:2.2959E-41)
                if (r8 <= r9) goto L_0x0201
                java.lang.Integer r8 = r2.f3284t     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r8 = r8.intValue()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r9 = 102(0x66, float:1.43E-43)
                if (r8 == r9) goto L_0x0201
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.data.d r7 = r7.f3377e     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.alibaba.sdk.android.error.ErrorCode r8 = com.taobao.accs.AccsErrorCode.MESSAGE_TOO_LARGE     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r7.mo18415a((com.taobao.accs.data.Message) r2, (com.alibaba.sdk.android.error.ErrorCode) r8)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                goto L_0x02dc
            L_0x0201:
                boolean r8 = r2.f3267c     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r8 == 0) goto L_0x020f
                com.taobao.accs.data.Message$a r8 = r2.mo18392d()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r8 = r8.mo18398a()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r8 = -r8
                goto L_0x0217
            L_0x020f:
                com.taobao.accs.data.Message$a r8 = r2.mo18392d()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r8 = r8.mo18398a()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
            L_0x0217:
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                org.android.spdy.SpdySession r15 = r9.f3474z     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r17 = 200(0xc8, float:2.8E-43)
                r18 = 0
                if (r7 != 0) goto L_0x0226
                r19 = 0
                goto L_0x0229
            L_0x0226:
                int r9 = r7.length     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r19 = r9
            L_0x0229:
                r16 = r8
                r20 = r7
                r15.sendCustomControlFrame(r16, r17, r18, r19, r20)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r9 = r1.f3478d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r11 = "send data"
                r12 = 6
                java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r15 = "length"
                r12[r3] = r15     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r7 != 0) goto L_0x023f
                r15 = 0
                goto L_0x0240
            L_0x023f:
                int r15 = r7.length     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
            L_0x0240:
                java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r12[r6] = r15     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r15 = "dataId"
                r12[r13] = r15     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r15 = r2.mo18390b()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r12[r14] = r15     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r14 = "utdid"
                r12[r10] = r14     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r10 = 5
                com.taobao.accs.net.w r14 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r14 = r14.f3382j     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r12[r10] = r14     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.utl.ALog.m3728i(r9, r11, r12)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.data.d r9 = r9.f3377e     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r9.mo18414a((com.taobao.accs.data.Message) r2)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                boolean r9 = r2.f3267c     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r9 == 0) goto L_0x0287
                java.lang.String r9 = r1.f3478d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r10 = "sendCFrame end ack"
                java.lang.Object[] r11 = new java.lang.Object[r13]     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r12 = "dataId"
                r11[r3] = r12     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.Integer r12 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r11[r6] = r12     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.utl.ALog.m3728i(r9, r10, r11)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.util.LinkedHashMap r9 = r9.f3384l     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r9.put(r8, r2)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
            L_0x0287:
                com.taobao.accs.ut.monitor.NetPerformanceMonitor r8 = r2.mo18393e()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                if (r8 == 0) goto L_0x0294
                com.taobao.accs.ut.monitor.NetPerformanceMonitor r8 = r2.mo18393e()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r8.onSendData()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
            L_0x0294:
                com.taobao.accs.net.w r8 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r9 = r2.mo18390b()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r10 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.AccsClientConfig r10 = r10.f3381i     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                boolean r10 = r10.isQuickReconnect()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r11 = r2.f3260S     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r11 = (long) r11     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r8.mo18473a((java.lang.String) r9, (boolean) r10, (long) r11)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r8 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.data.d r8 = r8.f3377e     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.ut.monitor.TrafficsMonitor$a r15 = new com.taobao.accs.ut.monitor.TrafficsMonitor$a     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r10 = r2.f3249H     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                boolean r11 = anet.channel.GlobalAppRuntimeInfo.isAppBackground()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r12 = r9.mo18527r()     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                int r7 = r7.length     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                long r13 = (long) r7     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r9 = r15
                r9.<init>(r10, r11, r12, r13)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r8.mo18418a((com.taobao.accs.p103ut.monitor.TrafficsMonitor.C2082a) r15)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                goto L_0x02dc
            L_0x02c4:
                r7 = 0
                goto L_0x02dd
            L_0x02c6:
                r1.m3709a(r3)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r8 = r1.f3478d     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r9 = "skip msg"
                java.lang.Object[] r10 = new java.lang.Object[r13]     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.String r11 = "type"
                r10[r3] = r11     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                r10[r6] = r7     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
                com.taobao.accs.utl.ALog.m3728i(r8, r9, r10)     // Catch:{ Throwable -> 0x039a, all -> 0x0395 }
            L_0x02dc:
                r7 = 1
            L_0x02dd:
                com.taobao.accs.net.w r8 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0391, all -> 0x038d }
                r8.m3688v()     // Catch:{ Throwable -> 0x0391, all -> 0x038d }
                if (r7 != 0) goto L_0x0360
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0380 }
                r7.mo18526q()     // Catch:{ Throwable -> 0x0380 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0380 }
                com.taobao.accs.ut.monitor.SessionMonitor r7 = r7.f3455H     // Catch:{ Throwable -> 0x0380 }
                if (r7 == 0) goto L_0x02fc
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0380 }
                com.taobao.accs.ut.monitor.SessionMonitor r7 = r7.f3455H     // Catch:{ Throwable -> 0x0380 }
                java.lang.String r8 = "send fail"
                r7.setCloseReason(r8)     // Catch:{ Throwable -> 0x0380 }
            L_0x02fc:
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0380 }
                java.util.LinkedList r7 = r7.f3468t     // Catch:{ Throwable -> 0x0380 }
                monitor-enter(r7)     // Catch:{ Throwable -> 0x0380 }
                com.taobao.accs.net.w r8 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x035c }
                java.util.LinkedList r8 = r8.f3468t     // Catch:{ all -> 0x035c }
                int r8 = r8.size()     // Catch:{ all -> 0x035c }
                int r8 = r8 - r6
            L_0x030e:
                if (r8 < 0) goto L_0x0347
                com.taobao.accs.net.w r6 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x035c }
                java.util.LinkedList r6 = r6.f3468t     // Catch:{ all -> 0x035c }
                java.lang.Object r6 = r6.get(r8)     // Catch:{ all -> 0x035c }
                com.taobao.accs.data.Message r6 = (com.taobao.accs.data.Message) r6     // Catch:{ all -> 0x035c }
                if (r6 == 0) goto L_0x0344
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x035c }
                if (r9 == 0) goto L_0x0344
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x035c }
                int r9 = r9.intValue()     // Catch:{ all -> 0x035c }
                if (r9 == r5) goto L_0x0332
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x035c }
                int r9 = r9.intValue()     // Catch:{ all -> 0x035c }
                if (r9 != r4) goto L_0x0344
            L_0x0332:
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x035c }
                com.taobao.accs.data.d r9 = r9.f3377e     // Catch:{ all -> 0x035c }
                com.alibaba.sdk.android.error.ErrorCode r10 = com.taobao.accs.AccsErrorCode.SPDY_CONNECTION_DISCONNECTED_WHEN_SEND_DATA     // Catch:{ all -> 0x035c }
                r9.mo18415a((com.taobao.accs.data.Message) r6, (com.alibaba.sdk.android.error.ErrorCode) r10)     // Catch:{ all -> 0x035c }
                com.taobao.accs.net.w r6 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x035c }
                java.util.LinkedList r6 = r6.f3468t     // Catch:{ all -> 0x035c }
                r6.remove(r8)     // Catch:{ all -> 0x035c }
            L_0x0344:
                int r8 = r8 + -1
                goto L_0x030e
            L_0x0347:
                java.lang.String r4 = r1.f3478d     // Catch:{ all -> 0x035c }
                java.lang.String r5 = "network disconnected, wait"
                java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ all -> 0x035c }
                com.taobao.accs.utl.ALog.m3727e(r4, r5, r6)     // Catch:{ all -> 0x035c }
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x035c }
                java.util.LinkedList r4 = r4.f3468t     // Catch:{ all -> 0x035c }
                r4.wait()     // Catch:{ all -> 0x035c }
                monitor-exit(r7)     // Catch:{ all -> 0x035c }
                goto L_0x000f
            L_0x035c:
                r0 = move-exception
                r4 = r0
                monitor-exit(r7)     // Catch:{ all -> 0x035c }
                throw r4     // Catch:{ Throwable -> 0x0380 }
            L_0x0360:
                java.lang.String r4 = r1.f3478d     // Catch:{ Throwable -> 0x0380 }
                java.lang.String r5 = "send succ, remove it"
                java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0380 }
                com.taobao.accs.utl.ALog.m3725d(r4, r5, r6)     // Catch:{ Throwable -> 0x0380 }
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0380 }
                java.util.LinkedList r4 = r4.f3468t     // Catch:{ Throwable -> 0x0380 }
                monitor-enter(r4)     // Catch:{ Throwable -> 0x0380 }
                com.taobao.accs.net.w r5 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x037c }
                java.util.LinkedList r5 = r5.f3468t     // Catch:{ all -> 0x037c }
                r5.remove(r2)     // Catch:{ all -> 0x037c }
                monitor-exit(r4)     // Catch:{ all -> 0x037c }
                goto L_0x000f
            L_0x037c:
                r0 = move-exception
                r5 = r0
                monitor-exit(r4)     // Catch:{ all -> 0x037c }
                throw r5     // Catch:{ Throwable -> 0x0380 }
            L_0x0380:
                r0 = move-exception
                r4 = r0
                java.lang.String r5 = r1.f3478d
                java.lang.Object[] r6 = new java.lang.Object[r3]
                java.lang.String r7 = " run finally error"
                com.taobao.accs.utl.ALog.m3726e(r5, r7, r4, r6)
                goto L_0x000f
            L_0x038d:
                r0 = move-exception
                r8 = r7
                goto L_0x0477
            L_0x0391:
                r0 = move-exception
                r8 = r7
                r7 = r0
                goto L_0x039d
            L_0x0395:
                r0 = move-exception
                r7 = r0
                r8 = 1
                goto L_0x0478
            L_0x039a:
                r0 = move-exception
                r7 = r0
                r8 = 1
            L_0x039d:
                java.lang.String r9 = "accs"
                java.lang.String r10 = "send_fail"
                java.lang.String r11 = r2.f3249H     // Catch:{ all -> 0x0476 }
                java.lang.String r12 = "1"
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0476 }
                r13.<init>()     // Catch:{ all -> 0x0476 }
                com.taobao.accs.net.w r14 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0476 }
                int r14 = r14.f3375c     // Catch:{ all -> 0x0476 }
                r13.append(r14)     // Catch:{ all -> 0x0476 }
                java.lang.String r14 = r7.toString()     // Catch:{ all -> 0x0476 }
                r13.append(r14)     // Catch:{ all -> 0x0476 }
                java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0476 }
                com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r9, r10, r11, r12, r13)     // Catch:{ all -> 0x0476 }
                r7.printStackTrace()     // Catch:{ all -> 0x0476 }
                java.lang.String r9 = r1.f3478d     // Catch:{ all -> 0x0476 }
                java.lang.String r10 = "service connection run"
                java.lang.Object[] r11 = new java.lang.Object[r3]     // Catch:{ all -> 0x0476 }
                com.taobao.accs.utl.ALog.m3726e(r9, r10, r7, r11)     // Catch:{ all -> 0x0476 }
                if (r8 != 0) goto L_0x0449
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0469 }
                r7.mo18526q()     // Catch:{ Throwable -> 0x0469 }
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0469 }
                com.taobao.accs.ut.monitor.SessionMonitor r7 = r7.f3455H     // Catch:{ Throwable -> 0x0469 }
                if (r7 == 0) goto L_0x03e5
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0469 }
                com.taobao.accs.ut.monitor.SessionMonitor r7 = r7.f3455H     // Catch:{ Throwable -> 0x0469 }
                java.lang.String r8 = "send fail"
                r7.setCloseReason(r8)     // Catch:{ Throwable -> 0x0469 }
            L_0x03e5:
                com.taobao.accs.net.w r7 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0469 }
                java.util.LinkedList r7 = r7.f3468t     // Catch:{ Throwable -> 0x0469 }
                monitor-enter(r7)     // Catch:{ Throwable -> 0x0469 }
                com.taobao.accs.net.w r8 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0445 }
                java.util.LinkedList r8 = r8.f3468t     // Catch:{ all -> 0x0445 }
                int r8 = r8.size()     // Catch:{ all -> 0x0445 }
                int r8 = r8 - r6
            L_0x03f7:
                if (r8 < 0) goto L_0x0430
                com.taobao.accs.net.w r6 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0445 }
                java.util.LinkedList r6 = r6.f3468t     // Catch:{ all -> 0x0445 }
                java.lang.Object r6 = r6.get(r8)     // Catch:{ all -> 0x0445 }
                com.taobao.accs.data.Message r6 = (com.taobao.accs.data.Message) r6     // Catch:{ all -> 0x0445 }
                if (r6 == 0) goto L_0x042d
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x0445 }
                if (r9 == 0) goto L_0x042d
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x0445 }
                int r9 = r9.intValue()     // Catch:{ all -> 0x0445 }
                if (r9 == r5) goto L_0x041b
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x0445 }
                int r9 = r9.intValue()     // Catch:{ all -> 0x0445 }
                if (r9 != r4) goto L_0x042d
            L_0x041b:
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0445 }
                com.taobao.accs.data.d r9 = r9.f3377e     // Catch:{ all -> 0x0445 }
                com.alibaba.sdk.android.error.ErrorCode r10 = com.taobao.accs.AccsErrorCode.SPDY_CONNECTION_DISCONNECTED_WHEN_SEND_DATA     // Catch:{ all -> 0x0445 }
                r9.mo18415a((com.taobao.accs.data.Message) r6, (com.alibaba.sdk.android.error.ErrorCode) r10)     // Catch:{ all -> 0x0445 }
                com.taobao.accs.net.w r6 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0445 }
                java.util.LinkedList r6 = r6.f3468t     // Catch:{ all -> 0x0445 }
                r6.remove(r8)     // Catch:{ all -> 0x0445 }
            L_0x042d:
                int r8 = r8 + -1
                goto L_0x03f7
            L_0x0430:
                java.lang.String r4 = r1.f3478d     // Catch:{ all -> 0x0445 }
                java.lang.String r5 = "network disconnected, wait"
                java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ all -> 0x0445 }
                com.taobao.accs.utl.ALog.m3727e(r4, r5, r6)     // Catch:{ all -> 0x0445 }
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0445 }
                java.util.LinkedList r4 = r4.f3468t     // Catch:{ all -> 0x0445 }
                r4.wait()     // Catch:{ all -> 0x0445 }
                monitor-exit(r7)     // Catch:{ all -> 0x0445 }
                goto L_0x000f
            L_0x0445:
                r0 = move-exception
                r4 = r0
                monitor-exit(r7)     // Catch:{ all -> 0x0445 }
                throw r4     // Catch:{ Throwable -> 0x0469 }
            L_0x0449:
                java.lang.String r4 = r1.f3478d     // Catch:{ Throwable -> 0x0469 }
                java.lang.String r5 = "send succ, remove it"
                java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0469 }
                com.taobao.accs.utl.ALog.m3725d(r4, r5, r6)     // Catch:{ Throwable -> 0x0469 }
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0469 }
                java.util.LinkedList r4 = r4.f3468t     // Catch:{ Throwable -> 0x0469 }
                monitor-enter(r4)     // Catch:{ Throwable -> 0x0469 }
                com.taobao.accs.net.w r5 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0465 }
                java.util.LinkedList r5 = r5.f3468t     // Catch:{ all -> 0x0465 }
                r5.remove(r2)     // Catch:{ all -> 0x0465 }
                monitor-exit(r4)     // Catch:{ all -> 0x0465 }
                goto L_0x000f
            L_0x0465:
                r0 = move-exception
                r5 = r0
                monitor-exit(r4)     // Catch:{ all -> 0x0465 }
                throw r5     // Catch:{ Throwable -> 0x0469 }
            L_0x0469:
                r0 = move-exception
                r4 = r0
                java.lang.String r5 = r1.f3478d
                java.lang.Object[] r6 = new java.lang.Object[r3]
                java.lang.String r7 = " run finally error"
                com.taobao.accs.utl.ALog.m3726e(r5, r7, r4, r6)
                goto L_0x000f
            L_0x0476:
                r0 = move-exception
            L_0x0477:
                r7 = r0
            L_0x0478:
                if (r8 != 0) goto L_0x04f5
                com.taobao.accs.net.w r2 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0514 }
                r2.mo18526q()     // Catch:{ Throwable -> 0x0514 }
                com.taobao.accs.net.w r2 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0514 }
                com.taobao.accs.ut.monitor.SessionMonitor r2 = r2.f3455H     // Catch:{ Throwable -> 0x0514 }
                if (r2 == 0) goto L_0x0492
                com.taobao.accs.net.w r2 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0514 }
                com.taobao.accs.ut.monitor.SessionMonitor r2 = r2.f3455H     // Catch:{ Throwable -> 0x0514 }
                java.lang.String r8 = "send fail"
                r2.setCloseReason(r8)     // Catch:{ Throwable -> 0x0514 }
            L_0x0492:
                com.taobao.accs.net.w r2 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0514 }
                java.util.LinkedList r2 = r2.f3468t     // Catch:{ Throwable -> 0x0514 }
                monitor-enter(r2)     // Catch:{ Throwable -> 0x0514 }
                com.taobao.accs.net.w r8 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x04f1 }
                java.util.LinkedList r8 = r8.f3468t     // Catch:{ all -> 0x04f1 }
                int r8 = r8.size()     // Catch:{ all -> 0x04f1 }
                int r8 = r8 - r6
            L_0x04a4:
                if (r8 < 0) goto L_0x04dd
                com.taobao.accs.net.w r6 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x04f1 }
                java.util.LinkedList r6 = r6.f3468t     // Catch:{ all -> 0x04f1 }
                java.lang.Object r6 = r6.get(r8)     // Catch:{ all -> 0x04f1 }
                com.taobao.accs.data.Message r6 = (com.taobao.accs.data.Message) r6     // Catch:{ all -> 0x04f1 }
                if (r6 == 0) goto L_0x04da
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x04f1 }
                if (r9 == 0) goto L_0x04da
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x04f1 }
                int r9 = r9.intValue()     // Catch:{ all -> 0x04f1 }
                if (r9 == r5) goto L_0x04c8
                java.lang.Integer r9 = r6.f3284t     // Catch:{ all -> 0x04f1 }
                int r9 = r9.intValue()     // Catch:{ all -> 0x04f1 }
                if (r9 != r4) goto L_0x04da
            L_0x04c8:
                com.taobao.accs.net.w r9 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x04f1 }
                com.taobao.accs.data.d r9 = r9.f3377e     // Catch:{ all -> 0x04f1 }
                com.alibaba.sdk.android.error.ErrorCode r10 = com.taobao.accs.AccsErrorCode.SPDY_CONNECTION_DISCONNECTED_WHEN_SEND_DATA     // Catch:{ all -> 0x04f1 }
                r9.mo18415a((com.taobao.accs.data.Message) r6, (com.alibaba.sdk.android.error.ErrorCode) r10)     // Catch:{ all -> 0x04f1 }
                com.taobao.accs.net.w r6 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x04f1 }
                java.util.LinkedList r6 = r6.f3468t     // Catch:{ all -> 0x04f1 }
                r6.remove(r8)     // Catch:{ all -> 0x04f1 }
            L_0x04da:
                int r8 = r8 + -1
                goto L_0x04a4
            L_0x04dd:
                java.lang.String r4 = r1.f3478d     // Catch:{ all -> 0x04f1 }
                java.lang.String r5 = "network disconnected, wait"
                java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ all -> 0x04f1 }
                com.taobao.accs.utl.ALog.m3727e(r4, r5, r6)     // Catch:{ all -> 0x04f1 }
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x04f1 }
                java.util.LinkedList r4 = r4.f3468t     // Catch:{ all -> 0x04f1 }
                r4.wait()     // Catch:{ all -> 0x04f1 }
                monitor-exit(r2)     // Catch:{ all -> 0x04f1 }
                goto L_0x051f
            L_0x04f1:
                r0 = move-exception
                r4 = r0
                monitor-exit(r2)     // Catch:{ all -> 0x04f1 }
                throw r4     // Catch:{ Throwable -> 0x0514 }
            L_0x04f5:
                java.lang.String r4 = r1.f3478d     // Catch:{ Throwable -> 0x0514 }
                java.lang.String r5 = "send succ, remove it"
                java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0514 }
                com.taobao.accs.utl.ALog.m3725d(r4, r5, r6)     // Catch:{ Throwable -> 0x0514 }
                com.taobao.accs.net.w r4 = com.taobao.accs.net.C2071w.this     // Catch:{ Throwable -> 0x0514 }
                java.util.LinkedList r4 = r4.f3468t     // Catch:{ Throwable -> 0x0514 }
                monitor-enter(r4)     // Catch:{ Throwable -> 0x0514 }
                com.taobao.accs.net.w r5 = com.taobao.accs.net.C2071w.this     // Catch:{ all -> 0x0510 }
                java.util.LinkedList r5 = r5.f3468t     // Catch:{ all -> 0x0510 }
                r5.remove(r2)     // Catch:{ all -> 0x0510 }
                monitor-exit(r4)     // Catch:{ all -> 0x0510 }
                goto L_0x051f
            L_0x0510:
                r0 = move-exception
                r2 = r0
                monitor-exit(r4)     // Catch:{ all -> 0x0510 }
                throw r2     // Catch:{ Throwable -> 0x0514 }
            L_0x0514:
                r0 = move-exception
                r2 = r0
                java.lang.String r4 = r1.f3478d
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.String r5 = " run finally error"
                com.taobao.accs.utl.ALog.m3726e(r4, r5, r2, r3)
            L_0x051f:
                throw r7
            L_0x0520:
                r0 = move-exception
                r2 = r0
                monitor-exit(r4)     // Catch:{ all -> 0x0520 }
                throw r2
            L_0x0524:
                com.taobao.accs.net.w r2 = com.taobao.accs.net.C2071w.this
                r2.mo18526q()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.C2071w.C2072a.run():void");
        }
    }

    /* renamed from: s */
    public boolean mo18528s() {
        return this.f3470v;
    }

    public void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e) {
                ALog.m3727e(mo18485d(), "session cleanUp has exception: " + e, new Object[0]);
            }
        }
        C2072a aVar = this.f3469u;
        int i2 = aVar != null ? aVar.f3475a : 0;
        ALog.m3727e(mo18485d(), "spdySessionFailedError", "retryTimes", Integer.valueOf(i2), "errorId", Integer.valueOf(i));
        this.f3457J = false;
        this.f3459L = true;
        m3678c(3);
        this.f3455H.setFailReason(i);
        this.f3455H.onConnectStop();
        String str = this.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp";
        UTMini.getInstance().commitEvent(66001, "DISCONNECT " + str, (Object) Integer.valueOf(i), (Object) Integer.valueOf(i2), (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), this.f3472x, this.f3458K);
        AppMonitorAdapter.commitAlarmFail("accs", "connect", "retrytimes:" + i2, i + "", "");
    }

    public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        SuperviseConnectInfo superviseConnectInfo2 = superviseConnectInfo;
        this.f3453F = superviseConnectInfo2.connectTime;
        int i = superviseConnectInfo2.handshakeTime;
        ALog.m3728i(mo18485d(), "spdySessionConnectCB", "sessionConnectInterval", Integer.valueOf(this.f3453F), "sslTime", Integer.valueOf(i), "reuse", Integer.valueOf(superviseConnectInfo2.sessionTicketReused));
        m3687u();
        this.f3455H.setRet(true);
        this.f3455H.onConnectStop();
        SessionMonitor sessionMonitor = this.f3455H;
        sessionMonitor.tcp_time = (long) this.f3453F;
        sessionMonitor.ssl_time = (long) i;
        String str = this.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp";
        UTMini instance = UTMini.getInstance();
        instance.commitEvent(66001, "CONNECTED " + str + " " + superviseConnectInfo2.sessionTicketReused, (Object) String.valueOf(this.f3453F), (Object) String.valueOf(i), (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), String.valueOf(superviseConnectInfo2.sessionTicketReused), this.f3472x, this.f3458K);
        AppMonitorAdapter.commitAlarmSuccess("accs", "connect", "");
    }

    public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
        SuperviseConnectInfo superviseConnectInfo2 = superviseConnectInfo;
        ALog.m3727e(mo18485d(), "spdySessionCloseCallback", Constants.KEY_ERROR_CODE, Integer.valueOf(i));
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e) {
                Exception exc = e;
                String d = mo18485d();
                ALog.m3727e(d, "session cleanUp has exception: " + exc, new Object[0]);
            }
        }
        m3678c(3);
        this.f3455H.onCloseConnect();
        if (this.f3455H.getConCloseDate() > 0 && this.f3455H.getConStopDate() > 0) {
            this.f3455H.getConCloseDate();
            this.f3455H.getConStopDate();
        }
        this.f3455H.setCloseReason(this.f3455H.getCloseReason() + "tnet error:" + i);
        if (superviseConnectInfo2 != null) {
            this.f3455H.live_time = (long) superviseConnectInfo2.keepalive_period_second;
        }
        AppMonitor.getInstance().commitStat(this.f3455H);
        for (Message next : this.f3377e.mo18426e()) {
            if (next.mo18393e() != null) {
                next.mo18393e().setFailReason("session close");
                AppMonitor.getInstance().commitStat(next.mo18393e());
            }
        }
        String str = this.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp";
        String d2 = mo18485d();
        ALog.m3725d(d2, "spdySessionCloseCallback, conKeepTime:" + this.f3455H.live_time + " connectType:" + str, new Object[0]);
        UTMini instance = UTMini.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("DISCONNECT CLOSE ");
        sb.append(str);
        instance.commitEvent(66001, sb.toString(), (Object) Integer.valueOf(i), (Object) Long.valueOf(this.f3455H.live_time), (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), this.f3472x, this.f3458K);
    }

    public void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj) {
        String d = mo18485d();
        ALog.m3725d(d, "spdyPingRecvCallback uniId:" + j, new Object[0]);
        if (j >= 0) {
            this.f3377e.mo18422b();
            C2053f.m3619a(this.f3376d).mo18505e();
            C2053f.m3619a(this.f3376d).mo18501a();
            this.f3455H.onPingCBReceive();
            if (this.f3455H.ping_rec_times % 2 == 0) {
                UtilityImpl.m3743a(this.f3376d, Constants.SP_KEY_SERVICE_END, System.currentTimeMillis());
            }
        }
    }

    public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        m3688v();
        ALog.m3728i(mo18485d(), "onFrame", "type", Integer.valueOf(i2), "len", Integer.valueOf(bArr.length));
        StringBuilder sb = new StringBuilder();
        if (ALog.isPrintLog(ALog.Level.D) && bArr.length < 512) {
            long currentTimeMillis = System.currentTimeMillis();
            for (byte b : bArr) {
                sb.append(Integer.toHexString(b & UByte.MAX_VALUE));
                sb.append(" ");
            }
            ALog.m3725d(mo18485d(), sb + " log time:" + (System.currentTimeMillis() - currentTimeMillis), new Object[0]);
        }
        if (i2 == 200) {
            try {
                long currentTimeMillis2 = System.currentTimeMillis();
                this.f3377e.mo18419a(bArr);
                C2079d g = this.f3377e.mo18428g();
                if (g != null) {
                    g.f3511c = String.valueOf(currentTimeMillis2);
                    g.f3515g = this.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp";
                    g.mo18540a();
                }
            } catch (Throwable th) {
                ALog.m3726e(mo18485d(), "onDataReceive ", th, new Object[0]);
                UTMini.getInstance().commitEvent(66001, "SERVICE_DATA_RECEIVE", UtilityImpl.m3739a(th));
            }
            ALog.m3725d(mo18485d(), "try handle msg", new Object[0]);
            mo18488g();
        } else {
            ALog.m3727e(mo18485d(), "drop frame", "len", Integer.valueOf(bArr.length));
        }
        ALog.m3725d(mo18485d(), "spdyCustomControlFrameRecvCallback", new Object[0]);
    }

    public void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, Object obj, SuperviseData superviseData) {
        ALog.m3725d(mo18485d(), "spdyStreamCloseCallback", new Object[0]);
        if (i != 0) {
            ALog.m3727e(mo18485d(), "spdyStreamCloseCallback", "statusCode", Integer.valueOf(i));
            ErrorBuilder copy = AccsErrorCode.NETWORKSDK_SPDY_CLOSE_ERROR.copy();
            m3668a(copy.detail("channel code " + i).build());
        }
    }

    public void spdyRequestRecvCallback(SpdySession spdySession, long j, Object obj) {
        ALog.m3725d(mo18485d(), "spdyRequestRecvCallback", new Object[0]);
    }

    public void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, Object obj) {
        this.f3449B = System.currentTimeMillis();
        this.f3450C = System.nanoTime();
        try {
            Map<String, String> a = UtilityImpl.m3741a(map);
            ALog.m3725d("SilenceConn_", "spdyOnStreamResponse", "header", map);
            int parseInt = Integer.parseInt(a.get(HttpConstant.STATUS));
            if (parseInt == 200) {
                ALog.m3728i(mo18485d(), "spdyOnStreamResponse", "httpStatusCode", Integer.valueOf(parseInt));
                m3678c(1);
                String str = a.get("x-at");
                if (!TextUtils.isEmpty(str)) {
                    this.f3383k = str;
                }
                SessionMonitor sessionMonitor = this.f3455H;
                long j2 = 0;
                if (this.f3455H.connection_stop_date > 0) {
                    j2 = System.currentTimeMillis() - this.f3455H.connection_stop_date;
                }
                sessionMonitor.auth_time = j2;
                String str2 = this.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp";
                UTMini instance = UTMini.getInstance();
                instance.commitEvent(66001, "CONNECTED 200 " + str2, (Object) this.f3472x, (Object) this.f3458K, (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), "0");
                AppMonitorAdapter.commitAlarmSuccess("accs", BaseMonitor.ALARM_POINT_AUTH, "");
            } else {
                ALog.m3727e(mo18485d(), "spdyOnStreamResponse", "httpStatusCode", Integer.valueOf(parseInt));
                ErrorBuilder copy = AccsErrorCode.NETWORKSDK_SPDY_RES_ERROR.copy();
                m3668a(copy.detail("channel code " + parseInt).build());
            }
        } catch (Exception e) {
            ALog.m3727e(mo18485d(), e.toString(), new Object[0]);
            mo18526q();
            this.f3455H.setCloseReason(MqttServiceConstants.TRACE_EXCEPTION);
        }
        ALog.m3725d(mo18485d(), "spdyOnStreamResponse", new Object[0]);
    }

    /* renamed from: a */
    private void m3668a(ErrorCode errorCode) {
        this.f3383k = null;
        mo18526q();
        C2072a aVar = this.f3469u;
        int i = aVar != null ? aVar.f3475a : 0;
        SessionMonitor sessionMonitor = this.f3455H;
        sessionMonitor.setCloseReason("code not 200 is" + errorCode.getCodeInt());
        this.f3459L = true;
        String str = this.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp";
        UTMini instance = UTMini.getInstance();
        instance.commitEvent(66001, "CONNECTED NO 200 " + str, (Object) errorCode, (Object) Integer.valueOf(i), (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), this.f3472x, this.f3458K);
        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_AUTH, "", errorCode + "", "");
    }

    public void spdyDataSendCallback(SpdySession spdySession, boolean z, long j, int i, Object obj) {
        ALog.m3725d(mo18485d(), "spdyDataSendCallback", new Object[0]);
    }

    public void spdyDataRecvCallback(SpdySession spdySession, boolean z, long j, int i, Object obj) {
        ALog.m3725d(mo18485d(), "spdyDataRecvCallback", new Object[0]);
    }

    /* renamed from: b */
    public void mo18479b() {
        this.f3457J = false;
        this.f3378f = 0;
    }

    public void bioPingRecvCallback(SpdySession spdySession, int i) {
        String d = mo18485d();
        ALog.m3731w(d, "bioPingRecvCallback uniId:" + i, new Object[0]);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18474a(String str, boolean z, String str2) {
        try {
            m3678c(4);
            mo18526q();
            this.f3455H.setCloseReason(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public boolean mo18477a(String str) {
        boolean z;
        synchronized (this.f3468t) {
            z = true;
            int size = this.f3468t.size() - 1;
            while (true) {
                if (size >= 0) {
                    Message message = this.f3468t.get(size);
                    if (message != null && message.mo18386a() == 1 && message.f3256O != null && message.f3256O.equals(str)) {
                        this.f3468t.remove(size);
                        break;
                    }
                    size--;
                } else {
                    z = false;
                    break;
                }
            }
        }
        return z;
    }

    public byte[] getSSLMeta(SpdySession spdySession) {
        return UtilityImpl.m3747a(this.f3376d, this.f3374b, spdySession.getDomain());
    }

    public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        return UtilityImpl.m3733a(this.f3376d, this.f3374b, spdySession.getDomain(), bArr);
    }

    public void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, long j, SpdyByteArray spdyByteArray, Object obj) {
        ALog.m3725d(mo18485d(), "spdyDataChunkRecvCB", new Object[0]);
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public String mo18485d() {
        return "SilenceConn_" + this.f3385m;
    }

    public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        mo18480b(i);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18470a(Context context) {
        if (!this.f3379g) {
            super.mo18470a(context);
            GlobalAppRuntimeInfo.setBackground(false);
            this.f3379g = true;
            ALog.m3728i(mo18485d(), "init awcn success!", new Object[0]);
        }
    }

    /* renamed from: b */
    public String mo18478b(String str) {
        return "https://" + this.f3381i.getChannelHost();
    }

    /* renamed from: l */
    public boolean mo18493l() {
        return this.f3467s == 1;
    }

    /* renamed from: n */
    public void mo18495n() {
        mo18526q();
    }

    /* renamed from: o */
    public void mo18496o() {
        mo18475a(true, false);
    }
}
