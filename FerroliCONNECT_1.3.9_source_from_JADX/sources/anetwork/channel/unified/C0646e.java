package anetwork.channel.unified;

import android.text.TextUtils;
import anet.channel.Config;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.NoAvailStrategyException;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.entity.C0517a;
import anet.channel.entity.C0519c;
import anet.channel.entity.ENV;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.session.C0552d;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import anet.channel.util.StringUtils;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.http.NetworkSdkSetting;
import anetwork.channel.interceptor.Callback;
import anetwork.channel.util.RequestConstant;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: anetwork.channel.unified.e */
/* compiled from: Taobao */
class C0646e implements IUnifiedTask {
    public static final int MAX_RSP_BUFFER_LENGTH = 131072;
    public static final String TAG = "anet.NetworkTask";

    /* renamed from: a */
    C0652j f754a;

    /* renamed from: b */
    Cache f755b = null;

    /* renamed from: c */
    Cache.Entry f756c = null;

    /* renamed from: d */
    ByteArrayOutputStream f757d = null;

    /* renamed from: e */
    String f758e = DispatchConstants.OTHER;

    /* renamed from: f */
    volatile Cancelable f759f = null;

    /* renamed from: g */
    volatile boolean f760g = false;

    /* renamed from: h */
    volatile AtomicBoolean f761h = null;

    /* renamed from: i */
    int f762i = 0;

    /* renamed from: j */
    int f763j = 0;

    /* renamed from: k */
    boolean f764k = false;

    /* renamed from: l */
    boolean f765l = false;

    /* renamed from: m */
    C0647a f766m = null;

    C0646e(C0652j jVar, Cache cache, Cache.Entry entry) {
        this.f754a = jVar;
        this.f761h = jVar.f790d;
        this.f755b = cache;
        this.f756c = entry;
        this.f758e = jVar.f787a.mo9338h().get(HttpConstant.F_REFER);
    }

    public void cancel() {
        this.f760g = true;
        if (this.f759f != null) {
            this.f759f.cancel();
        }
    }

    public void run() {
        if (!this.f760g) {
            RequestStatistic requestStatistic = this.f754a.f787a.f731b;
            requestStatistic.f_refer = this.f758e;
            if (!NetworkStatusHelper.isConnected()) {
                if (!NetworkConfigCenter.isRequestDelayRetryForNoNetwork() || requestStatistic.statusCode == -200) {
                    if (ALog.isPrintLog(2)) {
                        ALog.m328i(TAG, "network unavailable", this.f754a.f789c, "NetworkStatus", NetworkStatusHelper.getStatus());
                    }
                    this.f761h.set(true);
                    this.f754a.mo9369a();
                    requestStatistic.isDone.set(true);
                    requestStatistic.statusCode = ErrorConstant.ERROR_NO_NETWORK;
                    requestStatistic.msg = ErrorConstant.getErrMsg(ErrorConstant.ERROR_NO_NETWORK);
                    requestStatistic.rspEnd = System.currentTimeMillis();
                    this.f754a.f788b.onFinish(new DefaultFinishEvent((int) ErrorConstant.ERROR_NO_NETWORK, (String) null, this.f754a.f787a.mo9328a()));
                    return;
                }
                requestStatistic.statusCode = ErrorConstant.ERROR_NO_NETWORK;
                ThreadPoolExecutorFactory.submitScheduledTask(new C0648f(this), 1000, TimeUnit.MILLISECONDS);
            } else if (!NetworkConfigCenter.isBgRequestForbidden() || !GlobalAppRuntimeInfo.isAppBackground() || AppLifecycle.lastEnterBackgroundTime <= 0 || AppLifecycle.isGoingForeground || System.currentTimeMillis() - AppLifecycle.lastEnterBackgroundTime <= ((long) NetworkConfigCenter.getBgForbidRequestThreshold()) || NetworkConfigCenter.isUrlInWhiteList(this.f754a.f787a.mo9336f()) || NetworkConfigCenter.isBizInWhiteList(this.f754a.f787a.mo9328a().getBizId()) || this.f754a.f787a.mo9328a().isAllowRequestInBg()) {
                if (ALog.isPrintLog(2)) {
                    ALog.m328i(TAG, "exec request", this.f754a.f789c, "retryTimes", Integer.valueOf(this.f754a.f787a.f730a));
                }
                if (NetworkConfigCenter.isGetSessionAsyncEnable()) {
                    m455c();
                    return;
                }
                try {
                    Session b = m454b();
                    if (b != null) {
                        m452a(b, this.f754a.f787a.mo9328a());
                    }
                } catch (Exception e) {
                    ALog.m326e(TAG, "send request failed.", this.f754a.f789c, e, new Object[0]);
                }
            } else {
                this.f761h.set(true);
                this.f754a.mo9369a();
                if (ALog.isPrintLog(2)) {
                    ALog.m328i(TAG, "request forbidden in background", this.f754a.f789c, "url", this.f754a.f787a.mo9336f());
                }
                requestStatistic.isDone.set(true);
                requestStatistic.statusCode = ErrorConstant.ERROR_REQUEST_FORBIDDEN_IN_BG;
                requestStatistic.msg = ErrorConstant.getErrMsg(ErrorConstant.ERROR_REQUEST_FORBIDDEN_IN_BG);
                requestStatistic.rspEnd = System.currentTimeMillis();
                this.f754a.f788b.onFinish(new DefaultFinishEvent((int) ErrorConstant.ERROR_REQUEST_FORBIDDEN_IN_BG, (String) null, this.f754a.f787a.mo9328a()));
                ExceptionStatistic exceptionStatistic = new ExceptionStatistic(ErrorConstant.ERROR_REQUEST_FORBIDDEN_IN_BG, (String) null, "rt");
                exceptionStatistic.host = this.f754a.f787a.mo9336f().host();
                exceptionStatistic.url = this.f754a.f787a.mo9337g();
                AppMonitor.getInstance().commitStat(exceptionStatistic);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0016, code lost:
        r0 = anet.channel.util.HttpUrl.parse(r4.urlString().replaceFirst(r4.host(), r0));
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private anet.channel.util.HttpUrl m451a(anet.channel.util.HttpUrl r4) {
        /*
            r3 = this;
            anetwork.channel.unified.j r0 = r3.f754a
            anetwork.channel.entity.g r0 = r0.f787a
            java.util.Map r0 = r0.mo9338h()
            java.lang.String r1 = "x-host-cname"
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0029
            java.lang.String r1 = r4.urlString()
            java.lang.String r2 = r4.host()
            java.lang.String r0 = r1.replaceFirst(r2, r0)
            anet.channel.util.HttpUrl r0 = anet.channel.util.HttpUrl.parse(r0)
            if (r0 == 0) goto L_0x0029
            r4 = r0
        L_0x0029:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.unified.C0646e.m451a(anet.channel.util.HttpUrl):anet.channel.util.HttpUrl");
    }

    /* renamed from: a */
    private SessionCenter m449a() {
        String a = this.f754a.f787a.mo9329a(RequestConstant.APPKEY);
        if (TextUtils.isEmpty(a)) {
            return SessionCenter.getInstance();
        }
        ENV env = ENV.ONLINE;
        String a2 = this.f754a.f787a.mo9329a(RequestConstant.ENVIRONMENT);
        if (RequestConstant.ENV_PRE.equalsIgnoreCase(a2)) {
            env = ENV.PREPARE;
        } else if (RequestConstant.ENV_TEST.equalsIgnoreCase(a2)) {
            env = ENV.TEST;
        }
        if (env != NetworkSdkSetting.CURRENT_ENV) {
            NetworkSdkSetting.CURRENT_ENV = env;
            SessionCenter.switchEnvironment(env);
        }
        Config config = Config.getConfig(a, env);
        if (config == null) {
            config = new Config.Builder().setAppkey(a).setEnv(env).setAuthCode(this.f754a.f787a.mo9329a(RequestConstant.AUTH_CODE)).build();
        }
        return SessionCenter.getInstance(config);
    }

    /* renamed from: b */
    private Session m454b() {
        Session session;
        SessionCenter a = m449a();
        HttpUrl f = this.f754a.f787a.mo9336f();
        boolean containsNonDefaultPort = f.containsNonDefaultPort();
        RequestStatistic requestStatistic = this.f754a.f787a.f731b;
        if (this.f754a.f787a.f735f != 1 || !NetworkConfigCenter.isSpdyEnabled() || this.f754a.f787a.f730a != 0 || containsNonDefaultPort) {
            return m447a((Session) null, a, f, containsNonDefaultPort);
        }
        HttpUrl a2 = m451a(f);
        try {
            session = a.getThrowsException(a2, C0519c.f249a, 0);
        } catch (NoAvailStrategyException unused) {
            return m447a((Session) null, a, f, containsNonDefaultPort);
        } catch (Exception unused2) {
            session = null;
        }
        if (session == null) {
            ThreadPoolExecutorFactory.submitPriorityTask(new C0649g(this, a, a2, requestStatistic, f, containsNonDefaultPort), ThreadPoolExecutorFactory.Priority.NORMAL);
            return null;
        }
        ALog.m328i(TAG, "tryGetSession", this.f754a.f789c, "Session", session);
        requestStatistic.spdyRequestSend = true;
        return session;
    }

    /* renamed from: c */
    private void m455c() {
        SessionCenter a = m449a();
        HttpUrl f = this.f754a.f787a.mo9336f();
        boolean containsNonDefaultPort = f.containsNonDefaultPort();
        RequestStatistic requestStatistic = this.f754a.f787a.f731b;
        Request a2 = this.f754a.f787a.mo9328a();
        if (this.f754a.f787a.f735f != 1 || !NetworkConfigCenter.isSpdyEnabled() || this.f754a.f787a.f730a != 0 || containsNonDefaultPort) {
            m452a(m447a((Session) null, a, f, containsNonDefaultPort), a2);
            return;
        }
        HttpUrl a3 = m451a(f);
        long currentTimeMillis = System.currentTimeMillis();
        a.asyncGet(a3, C0519c.f249a, 3000, new C0650h(this, requestStatistic, currentTimeMillis, a2, a, f, containsNonDefaultPort));
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public Session m447a(C0552d dVar, SessionCenter sessionCenter, HttpUrl httpUrl, boolean z) {
        RequestStatistic requestStatistic = this.f754a.f787a.f731b;
        if (dVar == null && this.f754a.f787a.mo9335e() && !z && !NetworkStatusHelper.isProxy()) {
            dVar = sessionCenter.get(httpUrl, C0519c.f250b, 0);
        }
        if (dVar == null) {
            ALog.m328i(TAG, "create HttpSession with local DNS", this.f754a.f789c, new Object[0]);
            dVar = new C0552d(GlobalAppRuntimeInfo.getContext(), new C0517a(StringUtils.concatString(httpUrl.scheme(), HttpConstant.SCHEME_SPLIT, httpUrl.host()), this.f754a.f789c, (IConnStrategy) null));
        }
        if (requestStatistic.spdyRequestSend) {
            requestStatistic.degraded = 1;
        }
        ALog.m328i(TAG, "tryGetHttpSession", this.f754a.f789c, "Session", dVar);
        return dVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private anet.channel.request.Request m450a(anet.channel.request.Request r7) {
        /*
            r6 = this;
            anetwork.channel.unified.j r0 = r6.f754a
            anetwork.channel.entity.g r0 = r0.f787a
            boolean r0 = r0.mo9339i()
            if (r0 == 0) goto L_0x003c
            anetwork.channel.unified.j r0 = r6.f754a
            anetwork.channel.entity.g r0 = r0.f787a
            java.lang.String r0 = r0.mo9337g()
            java.lang.String r0 = anetwork.channel.cookie.CookieManager.getCookie(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x003c
            anet.channel.request.Request$Builder r1 = r7.newBuilder()
            java.util.Map r2 = r7.getHeaders()
            java.lang.String r3 = "Cookie"
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x0038
            java.lang.String r4 = "; "
            java.lang.String r0 = anet.channel.util.StringUtils.concatString(r2, r4, r0)
        L_0x0038:
            r1.addHeader(r3, r0)
            goto L_0x003d
        L_0x003c:
            r1 = 0
        L_0x003d:
            anetwork.channel.cache.Cache$Entry r0 = r6.f756c
            if (r0 == 0) goto L_0x006d
            if (r1 != 0) goto L_0x0047
            anet.channel.request.Request$Builder r1 = r7.newBuilder()
        L_0x0047:
            anetwork.channel.cache.Cache$Entry r0 = r6.f756c
            java.lang.String r0 = r0.etag
            if (r0 == 0) goto L_0x0056
            anetwork.channel.cache.Cache$Entry r0 = r6.f756c
            java.lang.String r0 = r0.etag
            java.lang.String r2 = "If-None-Match"
            r1.addHeader(r2, r0)
        L_0x0056:
            anetwork.channel.cache.Cache$Entry r0 = r6.f756c
            long r2 = r0.lastModified
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x006d
            anetwork.channel.cache.Cache$Entry r0 = r6.f756c
            long r2 = r0.lastModified
            java.lang.String r0 = anetwork.channel.cache.C0625a.m387a((long) r2)
            java.lang.String r2 = "If-Modified-Since"
            r1.addHeader(r2, r0)
        L_0x006d:
            anetwork.channel.unified.j r0 = r6.f754a
            anetwork.channel.entity.g r0 = r0.f787a
            int r0 = r0.f730a
            if (r0 != 0) goto L_0x008b
            java.lang.String r0 = r6.f758e
            java.lang.String r2 = "weex"
            boolean r0 = r2.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x008b
            if (r1 != 0) goto L_0x0086
            anet.channel.request.Request$Builder r0 = r7.newBuilder()
            r1 = r0
        L_0x0086:
            r0 = 3000(0xbb8, float:4.204E-42)
            r1.setReadTimeout(r0)
        L_0x008b:
            if (r1 != 0) goto L_0x008e
            goto L_0x0092
        L_0x008e:
            anet.channel.request.Request r7 = r1.build()
        L_0x0092:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.unified.C0646e.m450a(anet.channel.request.Request):anet.channel.request.Request");
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m452a(Session session, Request request) {
        if (session != null && !this.f760g) {
            Request a = m450a(request);
            RequestStatistic requestStatistic = this.f754a.f787a.f731b;
            requestStatistic.reqStart = System.currentTimeMillis();
            this.f759f = session.request(a, new C0651i(this, a, requestStatistic));
        }
    }

    /* renamed from: anetwork.channel.unified.e$a */
    /* compiled from: Taobao */
    private static class C0647a {

        /* renamed from: a */
        int f767a;

        /* renamed from: b */
        Map<String, List<String>> f768b;

        /* renamed from: c */
        List<ByteArray> f769c = new ArrayList();

        C0647a(int i, Map<String, List<String>> map) {
            this.f767a = i;
            this.f768b = map;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public void mo9366a() {
            for (ByteArray recycle : this.f769c) {
                recycle.recycle();
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public int mo9365a(Callback callback, int i) {
            callback.onResponseCode(this.f767a, this.f768b);
            int i2 = 1;
            for (ByteArray onDataReceiveSize : this.f769c) {
                callback.onDataReceiveSize(i2, i, onDataReceiveSize);
                i2++;
            }
            return i2;
        }
    }
}
