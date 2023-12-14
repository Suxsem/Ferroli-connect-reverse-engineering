package anet.channel;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.entity.C0517a;
import anet.channel.entity.C0518b;
import anet.channel.entity.ConnType;
import anet.channel.entity.EventCb;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.statist.SessionStatistic;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpHelper;
import anet.channel.util.StringUtils;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* compiled from: Taobao */
public abstract class Session implements Comparable<Session> {

    /* renamed from: v */
    static ExecutorService f88v = Executors.newSingleThreadExecutor();

    /* renamed from: a */
    public Context f89a;

    /* renamed from: b */
    Map<EventCb, Integer> f90b = new LinkedHashMap();

    /* renamed from: c */
    public String f91c;

    /* renamed from: d */
    public String f92d;

    /* renamed from: e */
    public String f93e;

    /* renamed from: f */
    public String f94f;

    /* renamed from: g */
    public int f95g;

    /* renamed from: h */
    public String f96h;

    /* renamed from: i */
    public int f97i;

    /* renamed from: j */
    public ConnType f98j;

    /* renamed from: k */
    public IConnStrategy f99k;

    /* renamed from: l */
    public String f100l;

    /* renamed from: m */
    public boolean f101m;

    /* renamed from: n */
    public int f102n;

    /* renamed from: o */
    protected Runnable f103o;

    /* renamed from: p */
    public final String f104p;

    /* renamed from: q */
    public final SessionStatistic f105q;

    /* renamed from: r */
    public int f106r;

    /* renamed from: s */
    public int f107s;

    /* renamed from: t */
    public boolean f108t;

    /* renamed from: u */
    protected boolean f109u;

    /* renamed from: w */
    private boolean f110w;

    /* renamed from: x */
    private Future<?> f111x;

    /* renamed from: y */
    private List<Long> f112y;

    /* renamed from: z */
    private long f113z;

    public abstract void close();

    public void connect() {
    }

    public abstract Runnable getRecvTimeOutRunnable();

    public abstract boolean isAvailable();

    public void onDisconnect() {
    }

    public void ping(boolean z) {
    }

    public void ping(boolean z, int i) {
    }

    public abstract Cancelable request(Request request, RequestCb requestCb);

    public void sendCustomFrame(int i, byte[] bArr, int i2) {
    }

    /* renamed from: anet.channel.Session$a */
    /* compiled from: Taobao */
    public static class C0470a {
        public static final int AUTHING = 3;
        public static final int AUTH_FAIL = 5;
        public static final int AUTH_SUCC = 4;
        public static final int CONNECTED = 0;
        public static final int CONNECTING = 1;
        public static final int CONNETFAIL = 2;
        public static final int DISCONNECTED = 6;
        public static final int DISCONNECTING = 7;

        /* renamed from: a */
        static final String[] f114a = {"CONNECTED", "CONNECTING", "CONNETFAIL", "AUTHING", "AUTH_SUCC", "AUTH_FAIL", "DISCONNECTED", "DISCONNECTING"};

        /* renamed from: a */
        static String m22a(int i) {
            return f114a[i];
        }
    }

    public int compareTo(Session session) {
        return ConnType.compare(this.f98j, session.f98j);
    }

    public Session(Context context, C0517a aVar) {
        boolean z = false;
        this.f110w = false;
        this.f100l = null;
        this.f101m = false;
        this.f102n = 6;
        this.f108t = false;
        this.f109u = true;
        this.f112y = null;
        this.f113z = 0;
        this.f89a = context;
        this.f93e = aVar.mo8814a();
        this.f94f = this.f93e;
        this.f95g = aVar.mo8815b();
        this.f98j = aVar.mo8816c();
        this.f91c = aVar.mo8819f();
        String str = this.f91c;
        this.f92d = str.substring(str.indexOf(HttpConstant.SCHEME_SPLIT) + 3);
        this.f107s = aVar.mo8818e();
        this.f106r = aVar.mo8817d();
        this.f99k = aVar.f241a;
        IConnStrategy iConnStrategy = this.f99k;
        if (iConnStrategy != null && iConnStrategy.getIpType() == -1) {
            z = true;
        }
        this.f101m = z;
        this.f104p = aVar.mo8821h();
        this.f105q = new SessionStatistic(aVar);
        this.f105q.host = this.f92d;
    }

    public void checkAvailable() {
        ping(true);
    }

    public static void configTnetALog(Context context, String str, int i, int i2) {
        SpdyAgent instance = SpdyAgent.getInstance(context, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        if (instance == null || !SpdyAgent.checkLoadSucc()) {
            ALog.m327e("agent null or configTnetALog load so fail!!!", (String) null, "loadso", Boolean.valueOf(SpdyAgent.checkLoadSucc()));
            return;
        }
        instance.configLogFile(str, i, i2);
    }

    public void close(boolean z) {
        this.f108t = z;
        close();
    }

    public void registerEventcb(int i, EventCb eventCb) {
        Map<EventCb, Integer> map = this.f90b;
        if (map != null) {
            map.put(eventCb, Integer.valueOf(i));
        }
    }

    public String getIp() {
        return this.f93e;
    }

    public int getPort() {
        return this.f95g;
    }

    public ConnType getConnType() {
        return this.f98j;
    }

    public String getHost() {
        return this.f91c;
    }

    public String getRealHost() {
        return this.f92d;
    }

    public IConnStrategy getConnStrategy() {
        return this.f99k;
    }

    public String getUnit() {
        return this.f100l;
    }

    public void handleCallbacks(int i, C0518b bVar) {
        f88v.submit(new C0480b(this, i, bVar));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void notifyStatus(int r9, anet.channel.entity.C0518b r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            java.lang.String r0 = "awcn.Session"
            java.lang.String r1 = "notifyStatus"
            java.lang.String r2 = r8.f104p     // Catch:{ all -> 0x0060 }
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x0060 }
            java.lang.String r5 = "status"
            r6 = 0
            r4[r6] = r5     // Catch:{ all -> 0x0060 }
            java.lang.String r5 = anet.channel.Session.C0470a.m22a(r9)     // Catch:{ all -> 0x0060 }
            r7 = 1
            r4[r7] = r5     // Catch:{ all -> 0x0060 }
            anet.channel.util.ALog.m327e(r0, r1, r2, r4)     // Catch:{ all -> 0x0060 }
            int r0 = r8.f102n     // Catch:{ all -> 0x0060 }
            if (r9 != r0) goto L_0x002a
            java.lang.String r9 = "awcn.Session"
            java.lang.String r10 = "ignore notifyStatus"
            java.lang.String r0 = r8.f104p     // Catch:{ all -> 0x0060 }
            java.lang.Object[] r1 = new java.lang.Object[r6]     // Catch:{ all -> 0x0060 }
            anet.channel.util.ALog.m328i(r9, r10, r0, r1)     // Catch:{ all -> 0x0060 }
            monitor-exit(r8)
            return
        L_0x002a:
            r8.f102n = r9     // Catch:{ all -> 0x0060 }
            int r9 = r8.f102n     // Catch:{ all -> 0x0060 }
            switch(r9) {
                case 0: goto L_0x005b;
                case 1: goto L_0x005e;
                case 2: goto L_0x0055;
                case 3: goto L_0x005e;
                case 4: goto L_0x0043;
                case 5: goto L_0x003d;
                case 6: goto L_0x0032;
                case 7: goto L_0x005e;
                default: goto L_0x0031;
            }     // Catch:{ all -> 0x0060 }
        L_0x0031:
            goto L_0x005e
        L_0x0032:
            r8.onDisconnect()     // Catch:{ all -> 0x0060 }
            boolean r9 = r8.f110w     // Catch:{ all -> 0x0060 }
            if (r9 != 0) goto L_0x005e
            r8.handleCallbacks(r3, r10)     // Catch:{ all -> 0x0060 }
            goto L_0x005e
        L_0x003d:
            r9 = 1024(0x400, float:1.435E-42)
            r8.handleCallbacks(r9, r10)     // Catch:{ all -> 0x0060 }
            goto L_0x005e
        L_0x0043:
            anet.channel.strategy.IStrategyInstance r9 = anet.channel.strategy.StrategyCenter.getInstance()     // Catch:{ all -> 0x0060 }
            java.lang.String r0 = r8.f92d     // Catch:{ all -> 0x0060 }
            java.lang.String r9 = r9.getUnitByHost(r0)     // Catch:{ all -> 0x0060 }
            r8.f100l = r9     // Catch:{ all -> 0x0060 }
            r9 = 512(0x200, float:7.175E-43)
            r8.handleCallbacks(r9, r10)     // Catch:{ all -> 0x0060 }
            goto L_0x005e
        L_0x0055:
            r9 = 256(0x100, float:3.59E-43)
            r8.handleCallbacks(r9, r10)     // Catch:{ all -> 0x0060 }
            goto L_0x005e
        L_0x005b:
            r8.handleCallbacks(r7, r10)     // Catch:{ all -> 0x0060 }
        L_0x005e:
            monitor-exit(r8)
            return
        L_0x0060:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.Session.notifyStatus(int, anet.channel.entity.b):void");
    }

    public void setPingTimeout(int i) {
        if (this.f103o == null) {
            this.f103o = getRecvTimeOutRunnable();
        }
        mo8638a();
        Runnable runnable = this.f103o;
        if (runnable != null) {
            this.f111x = ThreadPoolExecutorFactory.submitScheduledTask(runnable, (long) i, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo8638a() {
        Future<?> future;
        if (this.f103o != null && (future = this.f111x) != null) {
            future.cancel(true);
        }
    }

    public String toString() {
        return "Session@[" + this.f104p + '|' + this.f98j + ']';
    }

    public void handleResponseCode(Request request, int i) {
        if (request.getHeaders().containsKey(HttpConstant.X_PV) && i >= 500 && i < 600) {
            synchronized (this) {
                if (this.f112y == null) {
                    this.f112y = new LinkedList();
                }
                if (this.f112y.size() < 5) {
                    this.f112y.add(Long.valueOf(System.currentTimeMillis()));
                } else {
                    long longValue = this.f112y.remove(0).longValue();
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - longValue <= 60000) {
                        StrategyCenter.getInstance().forceRefreshStrategy(request.getHost());
                        this.f112y.clear();
                    } else {
                        this.f112y.add(Long.valueOf(currentTimeMillis));
                    }
                }
            }
        }
    }

    public void handleResponseHeaders(Request request, Map<String, List<String>> map) {
        try {
            if (map.containsKey(HttpConstant.X_SWITCH_UNIT)) {
                String singleHeaderFieldByKey = HttpHelper.getSingleHeaderFieldByKey(map, HttpConstant.X_SWITCH_UNIT);
                if (TextUtils.isEmpty(singleHeaderFieldByKey)) {
                    singleHeaderFieldByKey = null;
                }
                if (!StringUtils.isStringEqual(this.f100l, singleHeaderFieldByKey)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.f113z > 60000) {
                        StrategyCenter.getInstance().forceRefreshStrategy(request.getHost());
                        this.f113z = currentTimeMillis;
                    }
                }
            }
        } catch (Exception unused) {
        }
    }
}
