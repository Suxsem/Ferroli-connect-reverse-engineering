package com.taobao.accs.net;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.AccsException;
import com.taobao.accs.client.C2021c;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.C2030d;
import com.taobao.accs.data.Message;
import com.taobao.accs.p103ut.p104a.C2078c;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;

/* renamed from: com.taobao.accs.net.b */
/* compiled from: Taobao */
public abstract class C2049b {
    public static final int ACCS_RECEIVE_TIMEOUT = 40000;
    public static final int INAPP = 1;
    public static final int SERVICE = 0;

    /* renamed from: a */
    public String f3373a;

    /* renamed from: b */
    public String f3374b = "";

    /* renamed from: c */
    protected int f3375c;

    /* renamed from: d */
    protected Context f3376d;

    /* renamed from: e */
    protected C2030d f3377e;

    /* renamed from: f */
    protected int f3378f = 0;

    /* renamed from: g */
    protected volatile boolean f3379g = false;

    /* renamed from: h */
    public C2021c f3380h;

    /* renamed from: i */
    public AccsClientConfig f3381i;

    /* renamed from: j */
    protected String f3382j;

    /* renamed from: k */
    protected String f3383k = null;

    /* renamed from: l */
    protected LinkedHashMap<Integer, Message> f3384l = new BaseConnection$1(this);

    /* renamed from: m */
    public String f3385m;

    /* renamed from: n */
    private long f3386n = 0;

    /* renamed from: o */
    private Runnable f3387o;

    /* renamed from: p */
    private ScheduledFuture<?> f3388p;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String mo18468a(int i) {
        return i != 1 ? i != 2 ? (i == 3 || i != 4) ? "DISCONNECTED" : "DISCONNECTING" : "CONNECTING" : "CONNECTED";
    }

    /* renamed from: a */
    public abstract void mo18469a();

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo18472a(Message message, boolean z);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo18474a(String str, boolean z, String str2);

    /* renamed from: a */
    public abstract void mo18475a(boolean z, boolean z2);

    /* renamed from: a */
    public abstract boolean mo18477a(String str);

    /* renamed from: b */
    public abstract void mo18479b();

    /* renamed from: c */
    public abstract C2078c mo18483c();

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public abstract String mo18485d();

    /* renamed from: e */
    public void mo18486e() {
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public boolean mo18489h() {
        return true;
    }

    /* renamed from: l */
    public abstract boolean mo18493l();

    /* renamed from: m */
    public abstract int mo18494m();

    /* renamed from: n */
    public abstract void mo18495n();

    /* renamed from: o */
    public abstract void mo18496o();

    protected C2049b(Context context, int i, String str) {
        this.f3375c = i;
        this.f3376d = context.getApplicationContext();
        AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
        if (configByTag == null) {
            ALog.m3727e(mo18485d(), "BaseConnection config null!!", new Object[0]);
            try {
                configByTag = new AccsClientConfig.Builder().setAppKey(ACCSManager.getDefaultAppkey(context)).setTag(str).build();
            } catch (AccsException e) {
                ALog.m3726e(mo18485d(), "BaseConnection build config", e, new Object[0]);
            }
        }
        this.f3385m = configByTag.getTag();
        this.f3374b = configByTag.getAppKey();
        this.f3381i = configByTag;
        this.f3377e = new C2030d(context, this);
        this.f3377e.f3306b = this.f3375c;
        ALog.m3725d(mo18485d(), "new connection", new Object[0]);
    }

    /* renamed from: b */
    public void mo18482b(Message message, boolean z) {
        Message message2 = message;
        if (message2.f3267c || UtilityImpl.m3765g(this.f3376d)) {
            long a = message.mo18386a() != 2 ? this.f3377e.f3308d.mo18441a(message2.f3249H, message2.f3263V) : 0;
            if (a == -1) {
                ALog.m3727e(mo18485d(), "sendMessage ready server limit high", Constants.KEY_DATA_ID, message2.f3281q);
                this.f3377e.mo18415a(message2, AccsErrorCode.SERVIER_HIGH_LIMIT);
            } else if (a == -1000) {
                ALog.m3727e(mo18485d(), "sendMessage ready server limit high for brush", Constants.KEY_DATA_ID, message2.f3281q);
                this.f3377e.mo18415a(message2, AccsErrorCode.SERVIER_HIGH_LIMIT_BRUSH);
            } else {
                if (a > 0) {
                    long currentTimeMillis = System.currentTimeMillis();
                    long j = this.f3386n;
                    if (currentTimeMillis > j) {
                        message2.f3258Q = a;
                    } else {
                        message2.f3258Q = (j + a) - System.currentTimeMillis();
                    }
                    this.f3386n = System.currentTimeMillis() + message2.f3258Q;
                    ALog.m3727e(mo18485d(), "sendMessage ready delayed", Constants.KEY_DATA_ID, message2.f3281q, "type", Message.C2026c.m3490b(message.mo18386a()), "delay", Long.valueOf(message2.f3258Q));
                } else if ("accs".equals(message2.f3249H)) {
                    ALog.m3728i(mo18485d(), "sendMessage ready", Constants.KEY_DATA_ID, message2.f3281q, "type", Message.C2026c.m3490b(message.mo18386a()), "delay", Long.valueOf(message2.f3258Q));
                } else if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.m3725d(mo18485d(), "sendMessage ready", Constants.KEY_DATA_ID, message2.f3281q, "type", Message.C2026c.m3490b(message.mo18386a()), "delay", Long.valueOf(message2.f3258Q));
                }
                try {
                    if (TextUtils.isEmpty(this.f3382j)) {
                        this.f3382j = UtilityImpl.getDeviceId(this.f3376d);
                    }
                    if (!message.mo18395g()) {
                        mo18472a(message, z);
                    } else {
                        this.f3377e.mo18415a(message2, AccsErrorCode.REQ_TIME_OUT.copy().msg("重试或者延期时超时，不发送").detail(AccsErrorCode.getAllDetails((String) null)).build());
                    }
                } catch (RejectedExecutionException unused) {
                    int size = ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size();
                    C2030d dVar = this.f3377e;
                    ErrorBuilder copy = AccsErrorCode.MESSAGE_QUEUE_FULL.copy();
                    dVar.mo18415a(message2, copy.detail(" " + size).build());
                    ALog.m3727e(mo18485d(), "sendMessage ready queue full", "size", Integer.valueOf(size));
                }
            }
        } else {
            ALog.m3727e(mo18485d(), "sendMessage ready no network", Constants.KEY_DATA_ID, message2.f3281q);
            this.f3377e.mo18415a(message2, AccsErrorCode.NO_NETWORK);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18473a(String str, boolean z, long j) {
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new C2050c(this, str, j, z), j, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo18476a(Message message, int i) {
        boolean z = true;
        try {
            if (message.f3259R > 3) {
                return false;
            }
            message.f3259R++;
            message.f3258Q = (long) i;
            ALog.m3727e(mo18485d(), "reSend dataid:" + message.f3281q + " retryTimes:" + message.f3259R, new Object[0]);
            mo18482b(message, true);
            try {
                if (message.mo18393e() != null) {
                    message.mo18393e().take_date = 0;
                    message.mo18393e().to_tnet_date = 0;
                    message.mo18393e().retry_times = message.f3259R;
                    if (message.f3259R == 1) {
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "total", 0.0d);
                    }
                }
            } catch (Throwable th) {
                th = th;
                ALog.m3726e(mo18485d(), "reSend error", th, new Object[0]);
                this.f3377e.mo18415a(message, AccsErrorCode.SEND_LOCAL_EXCEPTION.copy().detail(AccsErrorCode.getExceptionInfo(th)).build());
                return z;
            }
            return z;
        } catch (Throwable th2) {
            th = th2;
            z = false;
            ALog.m3726e(mo18485d(), "reSend error", th, new Object[0]);
            this.f3377e.mo18415a(message, AccsErrorCode.SEND_LOCAL_EXCEPTION.copy().detail(AccsErrorCode.getExceptionInfo(th)).build());
            return z;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo18480b(int i) {
        if (i < 0) {
            ALog.m3727e(mo18485d(), "reSendAck", Constants.KEY_DATA_ID, Integer.valueOf(i));
            Message message = this.f3384l.get(Integer.valueOf(i));
            if (message != null) {
                mo18476a(message, (int) DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, BaseMonitor.COUNT_ACK, 0.0d);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public void mo18487f() {
        if (this.f3387o == null) {
            this.f3387o = new C2051d(this);
        }
        mo18488g();
        this.f3388p = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(this.f3387o, 40000, TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public void mo18488g() {
        ScheduledFuture<?> scheduledFuture = this.f3388p;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    /* renamed from: b */
    public String mo18478b(String str) {
        String inappHost = this.f3381i.getInappHost();
        StringBuilder sb = new StringBuilder();
        sb.append("https://");
        sb.append(TextUtils.isEmpty(str) ? "" : str);
        sb.append(inappHost);
        String sb2 = sb.toString();
        try {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("https://");
            if (TextUtils.isEmpty(str)) {
                str = "";
            }
            sb3.append(str);
            sb3.append(inappHost);
            return sb3.toString();
        } catch (Throwable th) {
            ALog.m3726e("InAppConnection", "getHost", th, new Object[0]);
            return sb2;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(10:1|2|(1:4)(2:5|(1:7))|8|9|10|11|(1:15)|16|20) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x001b */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo18470a(android.content.Context r6) {
        /*
            r5 = this;
            r0 = 0
            anet.channel.entity.ENV r1 = anet.channel.entity.ENV.ONLINE     // Catch:{ Throwable -> 0x007d }
            int r2 = com.taobao.accs.AccsClientConfig.mEnv     // Catch:{ Throwable -> 0x007d }
            r3 = 2
            if (r2 != r3) goto L_0x000e
            anet.channel.entity.ENV r1 = anet.channel.entity.ENV.TEST     // Catch:{ Throwable -> 0x007d }
            anet.channel.SessionCenter.switchEnvironment(r1)     // Catch:{ Throwable -> 0x007d }
            goto L_0x0018
        L_0x000e:
            int r2 = com.taobao.accs.AccsClientConfig.mEnv     // Catch:{ Throwable -> 0x007d }
            r3 = 1
            if (r2 != r3) goto L_0x0018
            anet.channel.entity.ENV r1 = anet.channel.entity.ENV.PREPARE     // Catch:{ Throwable -> 0x007d }
            anet.channel.SessionCenter.switchEnvironment(r1)     // Catch:{ Throwable -> 0x007d }
        L_0x0018:
            anet.channel.AwcnConfig.setSendConnectInfoByService(r0)     // Catch:{ Throwable -> 0x001b }
        L_0x001b:
            anet.channel.Config$Builder r2 = new anet.channel.Config$Builder     // Catch:{ Throwable -> 0x007d }
            r2.<init>()     // Catch:{ Throwable -> 0x007d }
            java.lang.String r3 = r5.f3374b     // Catch:{ Throwable -> 0x007d }
            anet.channel.Config$Builder r2 = r2.setAppkey(r3)     // Catch:{ Throwable -> 0x007d }
            com.taobao.accs.AccsClientConfig r3 = r5.f3381i     // Catch:{ Throwable -> 0x007d }
            java.lang.String r3 = r3.getAppSecret()     // Catch:{ Throwable -> 0x007d }
            anet.channel.Config$Builder r2 = r2.setAppSecret(r3)     // Catch:{ Throwable -> 0x007d }
            com.taobao.accs.AccsClientConfig r3 = r5.f3381i     // Catch:{ Throwable -> 0x007d }
            java.lang.String r3 = r3.getAuthCode()     // Catch:{ Throwable -> 0x007d }
            anet.channel.Config$Builder r2 = r2.setAuthCode(r3)     // Catch:{ Throwable -> 0x007d }
            anet.channel.Config$Builder r1 = r2.setEnv(r1)     // Catch:{ Throwable -> 0x007d }
            com.taobao.accs.AccsClientConfig r2 = r5.f3381i     // Catch:{ Throwable -> 0x007d }
            java.lang.String r2 = r2.getAppKey()     // Catch:{ Throwable -> 0x007d }
            anet.channel.Config$Builder r1 = r1.setTag(r2)     // Catch:{ Throwable -> 0x007d }
            anet.channel.Config r1 = r1.build()     // Catch:{ Throwable -> 0x007d }
            anet.channel.SessionCenter.init((android.content.Context) r6, (anet.channel.Config) r1)     // Catch:{ Throwable -> 0x007d }
            java.lang.String r6 = "acs"
            com.taobao.accs.AccsClientConfig r1 = r5.f3381i     // Catch:{ Throwable -> 0x007d }
            int r1 = r1.getInappPubKey()     // Catch:{ Throwable -> 0x007d }
            r2 = 10
            if (r1 == r2) goto L_0x0065
            com.taobao.accs.AccsClientConfig r1 = r5.f3381i     // Catch:{ Throwable -> 0x007d }
            int r1 = r1.getInappPubKey()     // Catch:{ Throwable -> 0x007d }
            r2 = 11
            if (r1 != r2) goto L_0x0067
        L_0x0065:
            java.lang.String r6 = "open"
        L_0x0067:
            anet.channel.strategy.StrategyTemplate r1 = anet.channel.strategy.StrategyTemplate.getInstance()     // Catch:{ Throwable -> 0x007d }
            com.taobao.accs.AccsClientConfig r2 = r5.f3381i     // Catch:{ Throwable -> 0x007d }
            java.lang.String r2 = r2.getInappHost()     // Catch:{ Throwable -> 0x007d }
            java.lang.String r3 = "http2"
            java.lang.String r4 = "0rtt"
            anet.channel.strategy.ConnProtocol r6 = anet.channel.strategy.ConnProtocol.valueOf(r3, r4, r6, r0)     // Catch:{ Throwable -> 0x007d }
            r1.registerConnProtocol(r2, r6)     // Catch:{ Throwable -> 0x007d }
            goto L_0x0089
        L_0x007d:
            r6 = move-exception
            java.lang.String r1 = r5.mo18485d()
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r2 = "initAwcn"
            com.taobao.accs.utl.ALog.m3726e(r1, r2, r6, r0)
        L_0x0089:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.C2049b.mo18470a(android.content.Context):void");
    }

    /* renamed from: a */
    public void mo18471a(Message message, ErrorCode errorCode) {
        this.f3377e.mo18415a(message, errorCode);
    }

    /* renamed from: i */
    public String mo18490i() {
        return this.f3374b;
    }

    /* renamed from: j */
    public C2021c mo18491j() {
        if (this.f3380h == null) {
            ALog.m3725d(mo18485d(), "new ClientManager", Constants.KEY_CONFIG_TAG, this.f3385m);
            this.f3380h = new C2021c(this.f3376d, this.f3385m, this.f3381i.getInappHost(), this.f3374b);
        }
        return this.f3380h;
    }

    /* renamed from: b */
    public void mo18481b(Context context) {
        try {
            ThreadPoolExecutorFactory.schedule(new C2052e(this, context), 10000, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            ALog.m3730w(mo18485d(), "startChannelService", th, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public String mo18484c(String str) {
        String deviceId = UtilityImpl.getDeviceId(this.f3376d);
        try {
            deviceId = URLEncoder.encode(deviceId);
        } catch (Throwable th) {
            ALog.m3726e(mo18485d(), "buildAuthUrl", th, new Object[0]);
        }
        String a = UtilityImpl.m3738a(mo18490i(), this.f3381i.getAppSecret(), UtilityImpl.getDeviceId(this.f3376d));
        StringBuilder sb = new StringBuilder(256);
        sb.append(str);
        sb.append("auth?1=");
        sb.append(deviceId);
        sb.append("&2=");
        sb.append(a);
        sb.append("&3=");
        sb.append(mo18490i());
        if (this.f3383k != null) {
            sb.append("&4=");
            sb.append(this.f3383k);
        }
        sb.append("&5=");
        sb.append(this.f3375c);
        sb.append("&6=");
        sb.append(UtilityImpl.m3760e(this.f3376d));
        sb.append("&7=");
        sb.append(UtilityImpl.m3749b());
        sb.append("&8=");
        sb.append(this.f3375c == 1 ? "1.1.2" : Integer.valueOf(Constants.SDK_VERSION_CODE));
        sb.append("&9=");
        sb.append(System.currentTimeMillis());
        sb.append("&10=");
        sb.append(1);
        sb.append("&11=");
        sb.append(Build.VERSION.SDK_INT);
        sb.append("&12=");
        sb.append(this.f3376d.getPackageName());
        sb.append("&13=");
        sb.append(UtilityImpl.m3767i(this.f3376d));
        sb.append("&14=");
        sb.append(this.f3373a);
        sb.append("&15=");
        sb.append(UtilityImpl.m3750b(Build.MODEL));
        sb.append("&16=");
        sb.append(UtilityImpl.m3750b(Build.BRAND));
        sb.append("&17=");
        sb.append(Constants.SDK_VERSION_CODE);
        sb.append("&19=");
        sb.append(mo18492k() ^ true ? 1 : 0);
        sb.append("&20=");
        sb.append(this.f3381i.getStoreId());
        return sb.toString();
    }

    /* renamed from: k */
    public boolean mo18492k() {
        return 2 == this.f3381i.getSecurity();
    }

    /* renamed from: p */
    public void mo18497p() {
        C2021c cVar = this.f3380h;
        if (cVar != null) {
            cVar.mo18377a();
        }
        this.f3379g = false;
    }
}
