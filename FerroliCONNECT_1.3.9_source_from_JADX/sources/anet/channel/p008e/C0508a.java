package anet.channel.p008e;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.IStrategyFilter;
import anet.channel.strategy.IStrategyListener;
import anet.channel.strategy.StrategyCenter;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.android.agoo.common.AgooConstants;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: anet.channel.e.a */
/* compiled from: Taobao */
public class C0508a {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static C0510b f223a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static String f224b;

    /* renamed from: c */
    private static AtomicBoolean f225c = new AtomicBoolean(false);

    /* renamed from: d */
    private static AtomicBoolean f226d = new AtomicBoolean(false);
    /* access modifiers changed from: private */

    /* renamed from: e */
    public static long f227e = 21600000;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public static SharedPreferences f228f;

    /* renamed from: g */
    private static IStrategyFilter f229g = new C0511b();
    /* access modifiers changed from: private */

    /* renamed from: h */
    public static AtomicInteger f230h = new AtomicInteger(1);

    /* renamed from: i */
    private static IStrategyListener f231i = new C0512c();

    /* renamed from: j */
    private static NetworkStatusHelper.INetworkStatusChangeListener f232j = new C0513d();

    /* renamed from: a */
    public static void m106a(NetworkStatusHelper.NetworkStatus networkStatus) {
        if (!AwcnConfig.isHttp3Enable()) {
            ALog.m328i("awcn.Http3ConnDetector", "startDetect", (String) null, "http3 global config close.");
        } else if (f226d.get()) {
            ALog.m327e("awcn.Http3ConnDetector", "tnet exception.", (String) null, new Object[0]);
        } else if (NetworkStatusHelper.isConnected()) {
            if (TextUtils.isEmpty(f224b)) {
                ALog.m327e("awcn.Http3ConnDetector", "startDetect", (String) null, "host is null");
                return;
            }
            List<IConnStrategy> connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(f224b, f229g);
            if (connStrategyListByHost.isEmpty()) {
                ALog.m327e("awcn.Http3ConnDetector", "startDetect", (String) null, "http3 strategy is null.");
                return;
            }
            if (f225c.compareAndSet(false, true)) {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION).InitializeSecurityStuff();
                    ALog.m327e("awcn.Http3ConnDetector", "tnet init http3.", (String) null, "cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                } catch (Throwable th) {
                    ALog.m326e("awcn.Http3ConnDetector", "tnet init http3 error.", (String) null, th, new Object[0]);
                    f226d.set(true);
                    return;
                }
            }
            if (f223a == null) {
                f223a = new C0510b();
            }
            if (f223a.mo8796a(NetworkStatusHelper.getUniqueId(networkStatus))) {
                ThreadPoolExecutorFactory.submitDetectTask(new C0514e(connStrategyListByHost, networkStatus));
            }
        }
    }

    /* renamed from: a */
    public static void m104a() {
        try {
            ALog.m327e("awcn.Http3ConnDetector", "registerListener", (String) null, "http3Enable", Boolean.valueOf(AwcnConfig.isHttp3Enable()));
            f228f = PreferenceManager.getDefaultSharedPreferences(GlobalAppRuntimeInfo.getContext());
            f224b = f228f.getString("http3_detector_host", "");
            m106a(NetworkStatusHelper.getStatus());
            NetworkStatusHelper.addStatusChangeListener(f232j);
            StrategyCenter.getInstance().registerListener(f231i);
        } catch (Exception e) {
            ALog.m326e("awcn.Http3ConnDetector", "[registerListener]error", (String) null, e, new Object[0]);
        }
    }

    /* renamed from: a */
    public static void m105a(long j) {
        if (j >= 0) {
            f227e = j;
        }
    }

    /* renamed from: b */
    public static boolean m109b() {
        C0510b bVar = f223a;
        if (bVar != null) {
            return bVar.mo8797b(NetworkStatusHelper.getUniqueId(NetworkStatusHelper.getStatus()));
        }
        return false;
    }

    /* renamed from: a */
    public static void m107a(boolean z) {
        C0510b bVar = f223a;
        if (bVar != null) {
            bVar.mo8795a(NetworkStatusHelper.getUniqueId(NetworkStatusHelper.getStatus()), z);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static IConnStrategy m108b(IConnStrategy iConnStrategy) {
        return new C0516g(iConnStrategy);
    }

    /* renamed from: anet.channel.e.a$a */
    /* compiled from: Taobao */
    private static class C0509a {

        /* renamed from: a */
        long f233a;

        /* renamed from: b */
        boolean f234b;

        private C0509a() {
        }

        /* synthetic */ C0509a(C0511b bVar) {
            this();
        }
    }

    /* renamed from: anet.channel.e.a$b */
    /* compiled from: Taobao */
    private static class C0510b {

        /* renamed from: a */
        private Map<String, C0509a> f235a = new ConcurrentHashMap();

        C0510b() {
            m115a();
        }

        /* renamed from: a */
        private void m115a() {
            String string = C0508a.f228f.getString("networksdk_http3_history_records", (String) null);
            if (!TextUtils.isEmpty(string)) {
                try {
                    JSONArray jSONArray = new JSONArray(string);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                        C0509a aVar = new C0509a((C0511b) null);
                        String string2 = jSONObject.getString("networkUniqueId");
                        aVar.f233a = jSONObject.getLong(AgooConstants.MESSAGE_TIME);
                        aVar.f234b = jSONObject.getBoolean("enable");
                        if (m116a(aVar.f233a)) {
                            synchronized (this.f235a) {
                                this.f235a.put(string2, aVar);
                            }
                        }
                    }
                } catch (JSONException unused) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
            return r1;
         */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean mo8796a(java.lang.String r5) {
            /*
                r4 = this;
                java.util.Map<java.lang.String, anet.channel.e.a$a> r0 = r4.f235a
                monitor-enter(r0)
                java.util.Map<java.lang.String, anet.channel.e.a$a> r1 = r4.f235a     // Catch:{ all -> 0x001c }
                java.lang.Object r5 = r1.get(r5)     // Catch:{ all -> 0x001c }
                anet.channel.e.a$a r5 = (anet.channel.p008e.C0508a.C0509a) r5     // Catch:{ all -> 0x001c }
                r1 = 1
                if (r5 != 0) goto L_0x0010
                monitor-exit(r0)     // Catch:{ all -> 0x001c }
                return r1
            L_0x0010:
                long r2 = r5.f233a     // Catch:{ all -> 0x001c }
                boolean r5 = r4.m116a((long) r2)     // Catch:{ all -> 0x001c }
                if (r5 != 0) goto L_0x0019
                goto L_0x001a
            L_0x0019:
                r1 = 0
            L_0x001a:
                monitor-exit(r0)     // Catch:{ all -> 0x001c }
                return r1
            L_0x001c:
                r5 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x001c }
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: anet.channel.p008e.C0508a.C0510b.mo8796a(java.lang.String):boolean");
        }

        /* renamed from: a */
        private boolean m116a(long j) {
            return System.currentTimeMillis() - j < C0508a.f227e;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public void mo8795a(String str, boolean z) {
            C0509a aVar = new C0509a((C0511b) null);
            aVar.f234b = z;
            aVar.f233a = System.currentTimeMillis();
            JSONArray jSONArray = new JSONArray();
            synchronized (this.f235a) {
                this.f235a.put(str, aVar);
                for (Map.Entry next : this.f235a.entrySet()) {
                    String str2 = (String) next.getKey();
                    C0509a aVar2 = (C0509a) next.getValue();
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("networkUniqueId", str2);
                        jSONObject.put(AgooConstants.MESSAGE_TIME, aVar2.f233a);
                        jSONObject.put("enable", aVar2.f234b);
                        jSONArray.put(jSONObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            C0508a.f228f.edit().putString("networksdk_http3_history_records", jSONArray.toString()).apply();
        }

        /* access modifiers changed from: package-private */
        /* renamed from: b */
        public boolean mo8797b(String str) {
            synchronized (this.f235a) {
                C0509a aVar = this.f235a.get(str);
                if (aVar == null) {
                    return false;
                }
                boolean z = aVar.f234b;
                return z;
            }
        }
    }
}
