package anet.channel;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import anet.channel.Config;
import anet.channel.detect.C0506n;
import anet.channel.entity.C0519c;
import anet.channel.entity.ConnType;
import anet.channel.entity.ENV;
import anet.channel.p008e.C0508a;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IStrategyListener;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import anet.channel.util.C0610i;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import anet.channel.util.StringUtils;
import anet.channel.util.Utils;
import anetwork.channel.util.RequestConstant;
import com.p107tb.appyunsdk.Constant;
import com.taobao.accs.common.Constants;
import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* compiled from: Taobao */
public class SessionCenter {
    public static final String TAG = "awcn.SessionCenter";

    /* renamed from: a */
    static Map<Config, SessionCenter> f115a = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: j */
    public static boolean f116j = false;

    /* renamed from: b */
    Context f117b = GlobalAppRuntimeInfo.getContext();

    /* renamed from: c */
    String f118c;

    /* renamed from: d */
    Config f119d;

    /* renamed from: e */
    final C0507e f120e = new C0507e();

    /* renamed from: f */
    final LruCache<String, SessionRequest> f121f = new LruCache<>(32);

    /* renamed from: g */
    final C0487c f122g = new C0487c();

    /* renamed from: h */
    final AccsSessionManager f123h;

    /* renamed from: i */
    final C0471a f124i = new C0471a(this, (C0490d) null);

    public static synchronized void init(Context context) {
        synchronized (SessionCenter.class) {
            if (context != null) {
                GlobalAppRuntimeInfo.setContext(context.getApplicationContext());
                if (!f116j) {
                    f115a.put(Config.DEFAULT_CONFIG, new SessionCenter(Config.DEFAULT_CONFIG));
                    AppLifecycle.initialize();
                    NetworkStatusHelper.startListener(context);
                    if (!AwcnConfig.isTbNextLaunch()) {
                        StrategyCenter.getInstance().initialize(GlobalAppRuntimeInfo.getContext());
                    }
                    if (GlobalAppRuntimeInfo.isTargetProcess()) {
                        C0506n.m94a();
                        C0508a.m104a();
                    }
                    f116j = true;
                }
            } else {
                ALog.m327e(TAG, "context is null!", (String) null, new Object[0]);
                throw new NullPointerException("init failed. context is null");
            }
        }
    }

    @Deprecated
    public static synchronized void init(Context context, String str) {
        synchronized (SessionCenter.class) {
            init(context, str, GlobalAppRuntimeInfo.getEnv());
        }
    }

    public static synchronized void init(Context context, String str, ENV env) {
        synchronized (SessionCenter.class) {
            if (context != null) {
                Config config = Config.getConfig(str, env);
                if (config == null) {
                    config = new Config.Builder().setAppkey(str).setEnv(env).build();
                }
                init(context, config);
            } else {
                ALog.m327e(TAG, "context is null!", (String) null, new Object[0]);
                throw new NullPointerException("init failed. context is null");
            }
        }
    }

    public static synchronized void init(Context context, Config config) {
        synchronized (SessionCenter.class) {
            if (context == null) {
                ALog.m327e(TAG, "context is null!", (String) null, new Object[0]);
                throw new NullPointerException("init failed. context is null");
            } else if (config != null) {
                init(context);
                if (!f115a.containsKey(config)) {
                    f115a.put(config, new SessionCenter(config));
                }
            } else {
                ALog.m327e(TAG, "paramter config is null!", (String) null, new Object[0]);
                throw new NullPointerException("init failed. config is null");
            }
        }
    }

    private SessionCenter(Config config) {
        this.f119d = config;
        this.f118c = config.getAppkey();
        this.f124i.mo8687a();
        this.f123h = new AccsSessionManager(this);
        if (!config.getAppkey().equals("[default]")) {
            AmdcRuntimeInfo.setSign(new C0490d(this, config.getAppkey(), config.getSecurity()));
        }
    }

    @Deprecated
    public synchronized void switchEnv(ENV env) {
        switchEnvironment(env);
    }

    public static synchronized void switchEnvironment(ENV env) {
        synchronized (SessionCenter.class) {
            try {
                if (GlobalAppRuntimeInfo.getEnv() != env) {
                    ALog.m328i(TAG, "switch env", (String) null, "old", GlobalAppRuntimeInfo.getEnv(), "new", env);
                    GlobalAppRuntimeInfo.setEnv(env);
                    StrategyCenter.getInstance().switchEnv();
                    SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION).switchAccsServer(env == ENV.TEST ? 0 : 1);
                }
                Iterator<Map.Entry<Config, SessionCenter>> it = f115a.entrySet().iterator();
                while (it.hasNext()) {
                    SessionCenter sessionCenter = (SessionCenter) it.next().getValue();
                    if (sessionCenter.f119d.getEnv() != env) {
                        ALog.m328i(TAG, "remove instance", sessionCenter.f118c, RequestConstant.ENVIRONMENT, sessionCenter.f119d.getEnv());
                        sessionCenter.f123h.forceCloseSession(false);
                        sessionCenter.f124i.mo8688b();
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                ALog.m326e(TAG, "switch env error.", (String) null, th, new Object[0]);
            }
        }
        return;
    }

    public static synchronized SessionCenter getInstance(String str) {
        SessionCenter instance;
        synchronized (SessionCenter.class) {
            Config configByTag = Config.getConfigByTag(str);
            if (configByTag != null) {
                instance = getInstance(configByTag);
            } else {
                throw new RuntimeException("tag not exist!");
            }
        }
        return instance;
    }

    public static synchronized SessionCenter getInstance(Config config) {
        SessionCenter sessionCenter;
        Context appContext;
        synchronized (SessionCenter.class) {
            if (config != null) {
                if (!f116j && (appContext = Utils.getAppContext()) != null) {
                    init(appContext);
                }
                sessionCenter = f115a.get(config);
                if (sessionCenter == null) {
                    sessionCenter = new SessionCenter(config);
                    f115a.put(config, sessionCenter);
                }
            } else {
                throw new NullPointerException("config is null!");
            }
        }
        return sessionCenter;
    }

    @Deprecated
    public static synchronized SessionCenter getInstance() {
        Context appContext;
        synchronized (SessionCenter.class) {
            if (!f116j && (appContext = Utils.getAppContext()) != null) {
                init(appContext);
            }
            SessionCenter sessionCenter = null;
            for (Map.Entry next : f115a.entrySet()) {
                SessionCenter sessionCenter2 = (SessionCenter) next.getValue();
                if (next.getKey() != Config.DEFAULT_CONFIG) {
                    return sessionCenter2;
                }
                sessionCenter = sessionCenter2;
            }
            return sessionCenter;
        }
    }

    public Session getThrowsException(String str, long j) throws Exception {
        return mo8666a(HttpUrl.parse(str), C0519c.f251c, j, (SessionGetCallback) null);
    }

    @Deprecated
    public Session getThrowsException(String str, ConnType.TypeLevel typeLevel, long j) throws Exception {
        return mo8666a(HttpUrl.parse(str), typeLevel == ConnType.TypeLevel.SPDY ? C0519c.f249a : C0519c.f250b, j, (SessionGetCallback) null);
    }

    public Session getThrowsException(HttpUrl httpUrl, int i, long j) throws Exception {
        return mo8666a(httpUrl, i, j, (SessionGetCallback) null);
    }

    @Deprecated
    public Session getThrowsException(HttpUrl httpUrl, ConnType.TypeLevel typeLevel, long j) throws Exception {
        return mo8666a(httpUrl, typeLevel == ConnType.TypeLevel.SPDY ? C0519c.f249a : C0519c.f250b, j, (SessionGetCallback) null);
    }

    public Session get(String str, long j) {
        return get(HttpUrl.parse(str), C0519c.f251c, j);
    }

    @Deprecated
    public Session get(String str, ConnType.TypeLevel typeLevel, long j) {
        return get(HttpUrl.parse(str), typeLevel == ConnType.TypeLevel.SPDY ? C0519c.f249a : C0519c.f250b, j);
    }

    @Deprecated
    public Session get(HttpUrl httpUrl, ConnType.TypeLevel typeLevel, long j) {
        return get(httpUrl, typeLevel == ConnType.TypeLevel.SPDY ? C0519c.f249a : C0519c.f250b, j);
    }

    public Session get(HttpUrl httpUrl, int i, long j) {
        try {
            return mo8666a(httpUrl, i, j, (SessionGetCallback) null);
        } catch (InvalidParameterException e) {
            InvalidParameterException invalidParameterException = e;
            ALog.m326e(TAG, "[Get]param url is invalid", this.f118c, invalidParameterException, "url", httpUrl);
            return null;
        } catch (TimeoutException e2) {
            TimeoutException timeoutException = e2;
            ALog.m326e(TAG, "[Get]timeout exception", this.f118c, timeoutException, "url", httpUrl.urlString());
            return null;
        } catch (ConnectException e3) {
            ConnectException connectException = e3;
            ALog.m327e(TAG, "[Get]connect exception", this.f118c, "errMsg", connectException.getMessage(), "url", httpUrl.urlString());
            return null;
        } catch (NoAvailStrategyException e4) {
            NoAvailStrategyException noAvailStrategyException = e4;
            ALog.m328i(TAG, "[Get]" + noAvailStrategyException.getMessage(), this.f118c, null, "url", httpUrl.urlString());
            return null;
        } catch (Exception e5) {
            Exception exc = e5;
            ALog.m326e(TAG, "[Get]" + exc.getMessage(), this.f118c, (Throwable) null, "url", httpUrl.urlString());
            return null;
        }
    }

    public void asyncGet(HttpUrl httpUrl, int i, long j, SessionGetCallback sessionGetCallback) {
        if (sessionGetCallback == null) {
            throw new NullPointerException("cb is null");
        } else if (j > 0) {
            try {
                mo8669b(httpUrl, i, j, sessionGetCallback);
            } catch (Exception unused) {
                sessionGetCallback.onSessionGetFail();
            }
        } else {
            throw new InvalidParameterException("timeout must > 0");
        }
    }

    public void registerSessionInfo(SessionInfo sessionInfo) {
        this.f122g.mo8746a(sessionInfo);
        if (sessionInfo.isKeepAlive) {
            this.f123h.checkAndStartSession();
        }
    }

    public void unregisterSessionInfo(String str) {
        SessionInfo a = this.f122g.mo8744a(str);
        if (a != null && a.isKeepAlive) {
            this.f123h.checkAndStartSession();
        }
    }

    public void registerAccsSessionListener(ISessionListener iSessionListener) {
        this.f123h.registerListener(iSessionListener);
    }

    public void unregisterAccsSessionListener(ISessionListener iSessionListener) {
        this.f123h.unregisterListener(iSessionListener);
    }

    public void registerPublicKey(String str, int i) {
        this.f122g.mo8747a(str, i);
    }

    public static void checkAndStartAccsSession() {
        for (SessionCenter sessionCenter : f115a.values()) {
            sessionCenter.f123h.checkAndStartSession();
        }
    }

    public void forceRecreateAccsSession() {
        this.f123h.forceCloseSession(true);
    }

    /* renamed from: a */
    private SessionRequest m23a(HttpUrl httpUrl) {
        String cNameByHost = StrategyCenter.getInstance().getCNameByHost(httpUrl.host());
        if (cNameByHost == null) {
            cNameByHost = httpUrl.host();
        }
        String scheme = httpUrl.scheme();
        if (!httpUrl.isSchemeLocked()) {
            scheme = StrategyCenter.getInstance().getSchemeByHost(cNameByHost, scheme);
        }
        return mo8667a(StringUtils.concatString(scheme, HttpConstant.SCHEME_SPLIT, cNameByHost));
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Session mo8666a(HttpUrl httpUrl, int i, long j, SessionGetCallback sessionGetCallback) throws Exception {
        SessionInfo b;
        int i2 = i;
        long j2 = j;
        if (!f116j) {
            ALog.m327e(TAG, "getInternal not inited!", this.f118c, new Object[0]);
            throw new IllegalStateException("getInternal not inited");
        } else if (httpUrl != null) {
            String str = this.f118c;
            Object[] objArr = new Object[6];
            objArr[0] = "u";
            objArr[1] = httpUrl.urlString();
            objArr[2] = "sessionType";
            objArr[3] = i2 == C0519c.f249a ? "LongLink" : "ShortLink";
            objArr[4] = "timeout";
            objArr[5] = Long.valueOf(j);
            ALog.m325d(TAG, "getInternal", str, objArr);
            SessionRequest a = m23a(httpUrl);
            Session a2 = this.f120e.mo8789a(a, i);
            if (a2 != null) {
                ALog.m325d(TAG, "get internal hit cache session", this.f118c, "session", a2);
            } else if (this.f119d != Config.DEFAULT_CONFIG || i2 == C0519c.f250b) {
                if (!GlobalAppRuntimeInfo.isAppBackground() || i2 != C0519c.f249a || !AwcnConfig.isAccsSessionCreateForbiddenInBg() || (b = this.f122g.mo8748b(httpUrl.host())) == null || !b.isAccs) {
                    a.mo8697a(this.f117b, i, C0610i.m364a(this.f118c), sessionGetCallback, j);
                    if (sessionGetCallback == null && j2 > 0 && (i2 == C0519c.f251c || a.mo8703b() == i2)) {
                        a.mo8696a(j2);
                        a2 = this.f120e.mo8789a(a, i);
                        if (a2 == null) {
                            throw new ConnectException("session connecting failed or timeout");
                        }
                    }
                } else {
                    ALog.m330w(TAG, "app background, forbid to create accs session", this.f118c, new Object[0]);
                    throw new ConnectException("accs session connecting forbidden in background");
                }
            } else if (sessionGetCallback == null) {
                return null;
            } else {
                sessionGetCallback.onSessionGetFail();
                return null;
            }
            return a2;
        } else {
            throw new InvalidParameterException("httpUrl is null");
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo8669b(HttpUrl httpUrl, int i, long j, SessionGetCallback sessionGetCallback) throws Exception {
        SessionInfo b;
        int i2 = i;
        SessionGetCallback sessionGetCallback2 = sessionGetCallback;
        if (!f116j) {
            ALog.m327e(TAG, "getInternal not inited!", this.f118c, new Object[0]);
            throw new IllegalStateException("getInternal not inited");
        } else if (httpUrl == null) {
            throw new InvalidParameterException("httpUrl is null");
        } else if (sessionGetCallback2 != null) {
            String str = this.f118c;
            Object[] objArr = new Object[6];
            objArr[0] = "u";
            objArr[1] = httpUrl.urlString();
            objArr[2] = "sessionType";
            objArr[3] = i2 == C0519c.f249a ? "LongLink" : "ShortLink";
            objArr[4] = "timeout";
            objArr[5] = Long.valueOf(j);
            ALog.m325d(TAG, "getInternal", str, objArr);
            SessionRequest a = m23a(httpUrl);
            Session a2 = this.f120e.mo8789a(a, i);
            if (a2 != null) {
                ALog.m325d(TAG, "get internal hit cache session", this.f118c, "session", a2);
                sessionGetCallback2.onSessionGetSuccess(a2);
            } else if (this.f119d == Config.DEFAULT_CONFIG && i2 != C0519c.f250b) {
                sessionGetCallback.onSessionGetFail();
            } else if (!GlobalAppRuntimeInfo.isAppBackground() || i2 != C0519c.f249a || !AwcnConfig.isAccsSessionCreateForbiddenInBg() || (b = this.f122g.mo8748b(httpUrl.host())) == null || !b.isAccs) {
                a.mo8704b(this.f117b, i, C0610i.m364a(this.f118c), sessionGetCallback, j);
            } else {
                ALog.m330w(TAG, "app background, forbid to create accs session", this.f118c, new Object[0]);
                throw new ConnectException("accs session connecting forbidden in background");
            }
        } else {
            throw new InvalidParameterException("sessionGetCallback is null");
        }
    }

    @Deprecated
    public void enterBackground() {
        AppLifecycle.onBackground();
    }

    @Deprecated
    public void enterForeground() {
        AppLifecycle.onForeground();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m26a(C0583l.C0587d dVar) {
        try {
            C0583l.C0585b[] bVarArr = dVar.f538b;
            for (C0583l.C0585b bVar : bVarArr) {
                if (bVar.f533k) {
                    m28b(bVar);
                }
                if (bVar.f527e != null) {
                    m25a(bVar);
                }
            }
        } catch (Exception e) {
            ALog.m326e(TAG, "checkStrategy failed", this.f118c, e, new Object[0]);
        }
    }

    /* renamed from: a */
    private void m25a(C0583l.C0585b bVar) {
        for (Session next : this.f120e.mo8791a(mo8667a(StringUtils.buildKey(bVar.f525c, bVar.f523a)))) {
            if (!StringUtils.isStringEqual(next.f100l, bVar.f527e)) {
                ALog.m328i(TAG, "unit change", next.f104p, "session unit", next.f100l, Constant.WEATHER_UNIT, bVar.f527e);
                next.close(true);
            }
        }
    }

    /* renamed from: b */
    private void m28b(C0583l.C0585b bVar) {
        boolean z;
        boolean z2;
        ALog.m328i(TAG, "find effectNow", this.f118c, Constants.KEY_HOST, bVar.f523a);
        C0583l.C0584a[] aVarArr = bVar.f530h;
        String[] strArr = bVar.f528f;
        for (Session next : this.f120e.mo8791a(mo8667a(StringUtils.buildKey(bVar.f525c, bVar.f523a)))) {
            if (!next.getConnType().isHttpType()) {
                int i = 0;
                while (true) {
                    if (i >= strArr.length) {
                        z = false;
                        break;
                    } else if (next.getIp().equals(strArr[i])) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    if (ALog.isPrintLog(2)) {
                        ALog.m328i(TAG, "ip not match", next.f104p, "session ip", next.getIp(), "ips", Arrays.toString(strArr));
                    }
                    next.close(true);
                } else {
                    int i2 = 0;
                    while (true) {
                        if (i2 < aVarArr.length) {
                            if (next.getPort() == aVarArr[i2].f515a && next.getConnType().equals(ConnType.valueOf(ConnProtocol.valueOf(aVarArr[i2])))) {
                                z2 = true;
                                break;
                            }
                            i2++;
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    if (!z2) {
                        if (ALog.isPrintLog(2)) {
                            ALog.m328i(TAG, "aisle not match", next.f104p, "port", Integer.valueOf(next.getPort()), "connType", next.getConnType(), "aisle", Arrays.toString(aVarArr));
                        }
                        next.close(true);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public SessionRequest mo8667a(String str) {
        SessionRequest sessionRequest;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.f121f) {
            sessionRequest = this.f121f.get(str);
            if (sessionRequest == null) {
                sessionRequest = new SessionRequest(str, this);
                this.f121f.put(str, sessionRequest);
            }
        }
        return sessionRequest;
    }

    /* renamed from: anet.channel.SessionCenter$a */
    /* compiled from: Taobao */
    private class C0471a implements NetworkStatusHelper.INetworkStatusChangeListener, IStrategyListener, AppLifecycle.AppLifecycleListener {

        /* renamed from: a */
        boolean f125a;

        private C0471a() {
            this.f125a = false;
        }

        /* synthetic */ C0471a(SessionCenter sessionCenter, C0490d dVar) {
            this();
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public void mo8687a() {
            AppLifecycle.registerLifecycleListener(this);
            NetworkStatusHelper.addStatusChangeListener(this);
            StrategyCenter.getInstance().registerListener(this);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: b */
        public void mo8688b() {
            StrategyCenter.getInstance().unregisterListener(this);
            AppLifecycle.unregisterLifecycleListener(this);
            NetworkStatusHelper.removeStatusChangeListener(this);
        }

        public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
            ALog.m327e(SessionCenter.TAG, "onNetworkStatusChanged.", SessionCenter.this.f118c, "networkStatus", networkStatus);
            List<SessionRequest> a = SessionCenter.this.f120e.mo8790a();
            if (!a.isEmpty()) {
                for (SessionRequest a2 : a) {
                    ALog.m325d(SessionCenter.TAG, "network change, try recreate session", SessionCenter.this.f118c, new Object[0]);
                    a2.mo8701a((String) null);
                }
            }
            SessionCenter.this.f123h.checkAndStartSession();
        }

        public void onStrategyUpdated(C0583l.C0587d dVar) {
            SessionCenter.this.m26a(dVar);
            SessionCenter.this.f123h.checkAndStartSession();
        }

        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0054 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void forground() {
            /*
                r7 = this;
                anet.channel.SessionCenter r0 = anet.channel.SessionCenter.this
                java.lang.String r0 = r0.f118c
                r1 = 0
                java.lang.Object[] r2 = new java.lang.Object[r1]
                java.lang.String r3 = "awcn.SessionCenter"
                java.lang.String r4 = "[forground]"
                anet.channel.util.ALog.m328i(r3, r4, r0, r2)
                anet.channel.SessionCenter r0 = anet.channel.SessionCenter.this
                android.content.Context r0 = r0.f117b
                if (r0 != 0) goto L_0x0015
                return
            L_0x0015:
                boolean r0 = r7.f125a
                if (r0 == 0) goto L_0x001a
                return
            L_0x001a:
                r0 = 1
                r7.f125a = r0
                boolean r2 = anet.channel.SessionCenter.f116j
                if (r2 != 0) goto L_0x002f
                anet.channel.SessionCenter r0 = anet.channel.SessionCenter.this
                java.lang.String r0 = r0.f118c
                java.lang.Object[] r1 = new java.lang.Object[r1]
                java.lang.String r2 = "forground not inited!"
                anet.channel.util.ALog.m327e(r3, r2, r0, r1)
                return
            L_0x002f:
                long r2 = anet.channel.util.AppLifecycle.lastEnterBackgroundTime     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
                r4 = 0
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 == 0) goto L_0x004d
                long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
                long r4 = anet.channel.util.AppLifecycle.lastEnterBackgroundTime     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
                long r2 = r2 - r4
                r4 = 60000(0xea60, double:2.9644E-319)
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 <= 0) goto L_0x004d
                anet.channel.SessionCenter r2 = anet.channel.SessionCenter.this     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
                anet.channel.AccsSessionManager r2 = r2.f123h     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
                r2.forceCloseSession(r0)     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
                goto L_0x0054
            L_0x004d:
                anet.channel.SessionCenter r0 = anet.channel.SessionCenter.this     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
                anet.channel.AccsSessionManager r0 = r0.f123h     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
                r0.checkAndStartSession()     // Catch:{ Exception -> 0x0054, all -> 0x0057 }
            L_0x0054:
                r7.f125a = r1     // Catch:{ Exception -> 0x005b }
                goto L_0x005b
            L_0x0057:
                r0 = move-exception
                r7.f125a = r1     // Catch:{ Exception -> 0x005b }
                throw r0     // Catch:{ Exception -> 0x005b }
            L_0x005b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: anet.channel.SessionCenter.C0471a.forground():void");
        }

        public void background() {
            ALog.m328i(SessionCenter.TAG, "[background]", SessionCenter.this.f118c, new Object[0]);
            if (!SessionCenter.f116j) {
                ALog.m327e(SessionCenter.TAG, "background not inited!", SessionCenter.this.f118c, new Object[0]);
                return;
            }
            try {
                StrategyCenter.getInstance().saveData();
                if (AwcnConfig.isAccsSessionCreateForbiddenInBg() && "OPPO".equalsIgnoreCase(Build.BRAND)) {
                    ALog.m328i(SessionCenter.TAG, "close session for OPPO", SessionCenter.this.f118c, new Object[0]);
                    SessionCenter.this.f123h.forceCloseSession(false);
                }
            } catch (Exception unused) {
            }
        }
    }
}
