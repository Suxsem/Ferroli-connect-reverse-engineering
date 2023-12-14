package com.taobao.accs.net;

import android.content.Context;
import android.content.Intent;
import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import anet.channel.DataFrameCb;
import anet.channel.IAuth;
import anet.channel.ISessionListener;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.SessionInfo;
import anet.channel.heartbeat.IHeartbeat;
import anet.channel.request.Request;
import anet.channel.session.TnetSpdySession;
import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.logger.ILog;
import com.szacs.ferroliconnect.util.Constant;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.AccsException;
import com.taobao.accs.AccsState;
import com.taobao.accs.ConnectionListener;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.p103ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.p103ut.p104a.C2078c;
import com.taobao.accs.utl.AccsLogger;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.OrangeAdapter;
import com.taobao.accs.utl.UtilityImpl;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.taobao.accs.net.j */
/* compiled from: Taobao */
public class C2057j extends C2049b implements DataFrameCb, ISessionListener {

    /* renamed from: n */
    private boolean f3408n = true;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public long f3409o = 3600000;

    /* renamed from: p */
    private ScheduledFuture f3410p;

    /* renamed from: q */
    private ScheduledFuture f3411q;

    /* renamed from: r */
    private boolean f3412r = false;

    /* renamed from: s */
    private ErrorCode f3413s = null;
    /* access modifiers changed from: private */

    /* renamed from: t */
    public ILog f3414t = AccsLogger.getLogger(mo18485d());

    /* renamed from: u */
    private Runnable f3415u = new C2059k(this);

    /* renamed from: v */
    private Runnable f3416v = new C2060l(this);

    /* renamed from: w */
    private Runnable f3417w = new C2065q(this);

    /* renamed from: x */
    private Set<String> f3418x = Collections.synchronizedSet(new HashSet());

    /* renamed from: a */
    public void mo18475a(boolean z, boolean z2) {
    }

    /* renamed from: c */
    public C2078c mo18483c() {
        return null;
    }

    public C2057j(Context context, int i, String str) {
        super(context, i, str);
        if (!OrangeAdapter.isTnetLogOff(true)) {
            String d = UtilityImpl.m3757d(this.f3376d, "inapp");
            ILog iLog = this.f3414t;
            iLog.mo9706d("config tnet log path:" + d);
            if (!TextUtils.isEmpty(d)) {
                Session.configTnetALog(context, d, UtilityImpl.TNET_FILE_SIZE, 5);
            }
        }
        AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
        if (configByTag == null || !configByTag.isChannelLoopStart()) {
            this.f3414t.mo9706d("channel delay start");
            ThreadPoolExecutorFactory.getScheduledExecutor().schedule(this.f3417w, 120000, TimeUnit.MILLISECONDS);
            return;
        }
        this.f3414t.mo9706d("channel loop start");
        ThreadPoolExecutorFactory.getScheduledExecutor().scheduleWithFixedDelay(this.f3417w, 120000, configByTag.getLoopInterval(), TimeUnit.MILLISECONDS);
    }

    /* renamed from: a */
    public synchronized void mo18469a() {
        this.f3414t.mo9706d("start");
        this.f3408n = true;
        mo18470a(this.f3376d);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18472a(Message message, boolean z) {
        if (!this.f3408n || message == null) {
            ILog iLog = this.f3414t;
            iLog.mo9713w("not running or msg null! " + this.f3408n);
            return;
        }
        try {
            if (ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size() <= 1000) {
                long j = message.f3258Q;
                if (j <= 0) {
                    j = 1;
                }
                ScheduledFuture<?> schedule = ThreadPoolExecutorFactory.getSendScheduledExecutor().schedule(new C2061m(this, message), j, TimeUnit.MILLISECONDS);
                if (message.mo18386a() == 1 && message.f3256O != null) {
                    if (message.mo18391c() && mo18477a(message.f3256O)) {
                        this.f3377e.mo18423b(message);
                    }
                    this.f3377e.f3305a.put(message.f3256O, schedule);
                }
                NetPerformanceMonitor e = message.mo18393e();
                if (e != null) {
                    e.setDeviceId(UtilityImpl.getDeviceId(this.f3376d));
                    e.setConnType(this.f3375c);
                    e.onEnterQueueData();
                    return;
                }
                return;
            }
            throw new RejectedExecutionException("accs");
        } catch (RejectedExecutionException unused) {
            int size = ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size();
            ErrorBuilder copy = AccsErrorCode.MESSAGE_QUEUE_FULL.copy();
            ErrorCode build = copy.detail("inapp " + size).build();
            this.f3377e.mo18415a(message, build);
            this.f3414t.mo9710e("send queue full", NotificationCompat.CATEGORY_ERROR, build);
        } catch (Throwable th) {
            this.f3414t.mo9709e("send error", th);
            this.f3377e.mo18415a(message, AccsErrorCode.SEND_LOCAL_EXCEPTION.copy().detail(AccsErrorCode.getExceptionInfo(th)).build());
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18473a(String str, boolean z, long j) {
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new C2062n(this, str, j, z), j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: e */
    public void mo18486e() {
        this.f3414t.mo9708e("shut down");
        this.f3408n = false;
    }

    /* renamed from: b */
    public void mo18479b() {
        this.f3378f = 0;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18474a(String str, boolean z, String str2) {
        Session session;
        try {
            Message b = this.f3377e.mo18421b(str);
            if (b != null && b.f3270f != null && (session = SessionCenter.getInstance(this.f3381i.getAppKey()).get(b.f3270f.toString(), 0)) != null) {
                if (z) {
                    session.close(true);
                } else {
                    session.ping(true);
                }
            }
        } catch (Exception e) {
            this.f3414t.mo9709e("onTimeOut", e);
        }
    }

    public void onDataReceive(TnetSpdySession tnetSpdySession, byte[] bArr, int i, int i2) {
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new C2063o(this, i2, i, bArr, tnetSpdySession));
    }

    public void onException(int i, int i2, boolean z, String str) {
        this.f3414t.mo9710e("onException", Constants.KEY_DATA_ID, Integer.valueOf(i), "errorId", Integer.valueOf(i2), "needRetry", Boolean.valueOf(z), "detail", str);
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new C2064p(this, i2, str, i, z));
    }

    /* renamed from: a */
    public boolean mo18477a(String str) {
        if (str == null) {
            return false;
        }
        ScheduledFuture scheduledFuture = (ScheduledFuture) this.f3377e.f3305a.get(str);
        boolean cancel = scheduledFuture != null ? scheduledFuture.cancel(false) : false;
        if (cancel) {
            this.f3414t.mo9712i("cancel", "customDataId", str);
        }
        return cancel;
    }

    /* renamed from: a */
    public void mo18516a(JSONObject jSONObject) {
        if (jSONObject == null) {
            this.f3414t.mo9708e("onReceiveAccsHeartbeatResp response data is null");
            return;
        }
        this.f3414t.mo9712i("onReceiveAccsHeartbeatResp", Constants.KEY_DATA, jSONObject);
        try {
            int i = jSONObject.getInt("timeInterval");
            if (i != -1) {
                long j = (long) (i * 1000);
                if (this.f3409o != j) {
                    if (i == 0) {
                        j = 3600000;
                    }
                    this.f3409o = j;
                    this.f3410p = ThreadPoolExecutorFactory.getScheduledExecutor().scheduleAtFixedRate(this.f3416v, this.f3409o, this.f3409o, TimeUnit.MILLISECONDS);
                }
            } else if (this.f3410p != null) {
                this.f3410p.cancel(true);
            }
        } catch (JSONException e) {
            this.f3414t.mo9709e("onReceiveAccsHeartbeatResp", e);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public String mo18485d() {
        return "InAppConn_" + this.f3385m;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18470a(Context context) {
        try {
            if (!this.f3379g) {
                super.mo18470a(context);
                String inappHost = this.f3381i.getInappHost();
                boolean z = false;
                if (!mo18489h() || !this.f3381i.isKeepalive()) {
                    this.f3414t.mo9706d("initAwcn close keepalive");
                } else {
                    z = true;
                }
                mo18514a(SessionCenter.getInstance(this.f3381i.getAppKey()), inappHost, z);
                this.f3379g = true;
                this.f3414t.mo9711i("initAwcn success!");
            }
        } catch (Throwable th) {
            this.f3414t.mo9709e("initAwcn", th);
        }
    }

    /* renamed from: a */
    public void mo18514a(SessionCenter sessionCenter, String str, boolean z) {
        if (!this.f3418x.contains(str)) {
            SessionInfo create = SessionInfo.create(str, z, true, new C2058a(this, str), (IHeartbeat) null, this);
            sessionCenter.registerAccsSessionListener(this);
            sessionCenter.registerPublicKey(str, this.f3381i.getInappPubKey());
            sessionCenter.registerSessionInfo(create);
            this.f3418x.add(str);
            this.f3414t.mo9712i("registerSessionInfo", Constants.KEY_HOST, str);
        }
    }

    /* renamed from: a */
    public void mo18515a(AccsClientConfig accsClientConfig) {
        if (accsClientConfig == null) {
            this.f3414t.mo9711i("updateConfig null");
        } else if (accsClientConfig.equals(this.f3381i)) {
            this.f3414t.mo9711i("updateConfig not any changed");
        } else {
            try {
                boolean z = false;
                this.f3414t.mo9712i("updateConfig", "old", this.f3381i, "new", accsClientConfig);
                String inappHost = this.f3381i.getInappHost();
                String inappHost2 = accsClientConfig.getInappHost();
                SessionCenter instance = SessionCenter.getInstance(this.f3381i.getAppKey());
                if (instance == null) {
                    this.f3414t.mo9706d("old session not exist, no need update");
                    return;
                }
                instance.unregisterSessionInfo(inappHost);
                if (this.f3418x.contains(inappHost)) {
                    this.f3418x.remove(inappHost);
                }
                String appKey = this.f3381i.getAppKey();
                this.f3381i = accsClientConfig;
                this.f3374b = this.f3381i.getAppKey();
                this.f3385m = this.f3381i.getTag();
                if (!appKey.equals(this.f3374b)) {
                    instance = SessionCenter.getInstance(this.f3374b);
                }
                if (!mo18489h() || !this.f3381i.isKeepalive()) {
                    this.f3414t.mo9711i("updateConfig close keepalive");
                } else {
                    z = true;
                }
                mo18514a(instance, inappHost2, z);
                this.f3414t.mo9711i("updateConfig done");
            } catch (Throwable th) {
                this.f3414t.mo9709e("updateConfig", th);
            }
        }
    }

    public void onConnectionChanged(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(Constants.KEY_CONNECT_AVAILABLE, false);
        String stringExtra = intent.getStringExtra(Constants.KEY_HOST);
        ErrorCode errorCode = Constants.getErrorCode(intent);
        if (!TextUtils.isEmpty(stringExtra)) {
            if (!booleanExtra) {
                if (errorCode.getCodeInt() == AccsErrorCode.SUCCESS.getCodeInt()) {
                    if (UtilityImpl.m3765g(this.f3376d)) {
                        errorCode = AccsErrorCode.INAPP_CON_DISCONNECTED.copy().detail(AccsErrorCode.getAllDetails("lost connect")).build();
                    } else {
                        errorCode = AccsErrorCode.NO_NETWORK.copy().detail(AccsErrorCode.getAllDetails("lost connect")).build();
                    }
                }
                this.f3414t.mo9710e("InAppConnection Not Available ", "error", errorCode);
                m3641r();
            } else {
                this.f3414t.mo9712i("InAppConnection Available. last status", Boolean.valueOf(this.f3412r));
                m3642s();
                if (!this.f3412r) {
                    mo18481b(this.f3376d);
                }
            }
            this.f3412r = booleanExtra;
            this.f3413s = errorCode;
            if (errorCode == null || errorCode.getCodeInt() == AccsErrorCode.SUCCESS.getCodeInt()) {
                AccsState instance = AccsState.getInstance();
                instance.mo18228b(AccsState.CONNECTION_CHANGE, "h" + stringExtra + "a" + booleanExtra);
            } else {
                AccsState instance2 = AccsState.getInstance();
                instance2.mo18228b(AccsState.CONNECTION_CHANGE, "h" + stringExtra + "a" + booleanExtra + Constant.WEATHER_TEMP_C + errorCode.getCodeInt());
                AccsState.getInstance().mo18228b(AccsState.RECENT_ERRORS, Integer.valueOf(errorCode.getCodeInt()));
            }
            m3636a(booleanExtra, errorCode);
        }
    }

    /* renamed from: a */
    private void m3636a(boolean z, ErrorCode errorCode) {
        try {
            for (ConnectionListener next : ACCSClient.getAccsClient(this.f3385m).getConnectionListeners()) {
                if (z) {
                    next.onConnect();
                } else {
                    next.onDisconnect(errorCode.getCodeInt(), errorCode.getMsg());
                }
            }
        } catch (AccsException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: com.taobao.accs.net.j$a */
    /* compiled from: Taobao */
    public static class C2058a implements IAuth {

        /* renamed from: a */
        private String f3419a;

        /* renamed from: b */
        private int f3420b;

        /* renamed from: c */
        private String f3421c;
        /* access modifiers changed from: private */

        /* renamed from: d */
        public C2049b f3422d;
        /* access modifiers changed from: private */

        /* renamed from: e */
        public ILog f3423e;

        public C2058a(C2049b bVar, String str) {
            this.f3421c = bVar.mo18485d();
            this.f3419a = bVar.mo18484c("https://" + str + "/accs/");
            this.f3420b = bVar.f3375c;
            this.f3422d = bVar;
            this.f3423e = AccsLogger.getLogger(bVar.mo18485d());
        }

        public void auth(Session session, IAuth.AuthCallback authCallback) {
            this.f3423e.mo9712i(BaseMonitor.ALARM_POINT_AUTH, "URL", this.f3419a);
            session.request(new Request.Builder().setUrl(this.f3419a).build(), new C2067s(this, authCallback));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q */
    public void m3640q() {
        if (this.f3381i.isAccsHeartbeatEnable()) {
            ScheduledThreadPoolExecutor scheduledExecutor = ThreadPoolExecutorFactory.getScheduledExecutor();
            Runnable runnable = this.f3416v;
            long j = this.f3409o;
            this.f3410p = scheduledExecutor.scheduleAtFixedRate(runnable, j, j, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: r */
    public void m3641r() {
        m3642s();
        this.f3414t.mo9711i("startReconnectTask");
        this.f3411q = ThreadPoolExecutorFactory.getScheduledExecutor().scheduleWithFixedDelay(this.f3415u, 120, 120, TimeUnit.SECONDS);
    }

    /* renamed from: s */
    private void m3642s() {
        ScheduledFuture scheduledFuture = this.f3411q;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.f3411q = null;
        }
    }

    /* renamed from: l */
    public boolean mo18493l() {
        return this.f3412r;
    }

    /* renamed from: m */
    public int mo18494m() {
        ErrorCode errorCode = this.f3413s;
        if (errorCode != null) {
            return errorCode.getCodeInt();
        }
        return 0;
    }

    /* renamed from: n */
    public void mo18495n() {
        SessionCenter instance = SessionCenter.getInstance(this.f3381i.getAppKey());
        if (instance != null) {
            String inappHost = this.f3381i.getInappHost();
            instance.unregisterSessionInfo(inappHost);
            if (this.f3418x.contains(inappHost)) {
                this.f3418x.remove(inappHost);
            }
        }
    }

    /* renamed from: o */
    public void mo18496o() {
        this.f3414t.mo9711i("reconnect begin");
        this.f3379g = false;
        mo18470a(this.f3376d);
        ThreadPoolExecutorFactory.getSendScheduledExecutor().execute(new C2066r(this));
    }
}
