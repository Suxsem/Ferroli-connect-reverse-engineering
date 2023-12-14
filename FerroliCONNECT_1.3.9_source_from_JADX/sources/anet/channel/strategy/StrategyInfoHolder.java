package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.statist.StrategyStatObject;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.strategy.utils.C0592a;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: Taobao */
class StrategyInfoHolder implements NetworkStatusHelper.INetworkStatusChangeListener {

    /* renamed from: a */
    Map<String, StrategyTable> f457a = new LruStrategyMap();

    /* renamed from: b */
    volatile StrategyConfig f458b = null;

    /* renamed from: c */
    final C0564a f459c = new C0564a();

    /* renamed from: d */
    private final StrategyTable f460d = new StrategyTable("Unknown");

    /* renamed from: e */
    private final Set<String> f461e = new HashSet();

    /* renamed from: f */
    private volatile String f462f = "";

    /* renamed from: a */
    public static StrategyInfoHolder m251a() {
        return new StrategyInfoHolder();
    }

    private StrategyInfoHolder() {
        try {
            m253e();
            m255g();
        } catch (Throwable th) {
            m254f();
            throw th;
        }
        m254f();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public void mo9015b() {
        NetworkStatusHelper.removeStatusChangeListener(this);
    }

    /* renamed from: e */
    private void m253e() {
        NetworkStatusHelper.addStatusChangeListener(this);
        this.f462f = m252a(NetworkStatusHelper.getStatus());
    }

    /* renamed from: f */
    private void m254f() {
        for (Map.Entry<String, StrategyTable> value : this.f457a.entrySet()) {
            ((StrategyTable) value.getValue()).mo9026a();
        }
        synchronized (this) {
            if (this.f458b == null) {
                StrategyConfig strategyConfig = new StrategyConfig();
                strategyConfig.mo9012b();
                strategyConfig.mo9009a(this);
                this.f458b = strategyConfig;
            }
        }
    }

    /* renamed from: g */
    private void m255g() {
        ALog.m328i("awcn.StrategyInfoHolder", "restore", (String) null, new Object[0]);
        String str = this.f462f;
        if (!AwcnConfig.isAsyncLoadStrategyEnable()) {
            if (!TextUtils.isEmpty(str)) {
                mo9014a(str, true);
            }
            this.f458b = (StrategyConfig) C0589m.m304a("StrategyConfig", (StrategyStatObject) null);
            if (this.f458b != null) {
                this.f458b.mo9012b();
                this.f458b.mo9009a(this);
            }
        }
        C0592a.m314a(new C0568d(this, str));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        r2 = (anet.channel.strategy.StrategyTable) anet.channel.strategy.C0589m.m304a(r7, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        if (r2 == null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r2.mo9026a();
        r3 = r6.f457a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r6.f457a.put(r2.f468a, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0031, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0036, code lost:
        r3 = r6.f461e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0038, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r6.f461e.remove(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003e, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003f, code lost:
        if (r8 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0041, code lost:
        if (r2 == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0043, code lost:
        r1 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0044, code lost:
        r0.isSucceed = r1;
        anet.channel.appmonitor.AppMonitor.getInstance().commitStat(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0011, code lost:
        r0 = null;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
        if (r8 == false) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
        r0 = new anet.channel.statist.StrategyStatObject(0);
        r0.readStrategyFileId = r7;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo9014a(java.lang.String r7, boolean r8) {
        /*
            r6 = this;
            java.util.Set<java.lang.String> r0 = r6.f461e
            monitor-enter(r0)
            java.util.Set<java.lang.String> r1 = r6.f461e     // Catch:{ all -> 0x0053 }
            boolean r1 = r1.contains(r7)     // Catch:{ all -> 0x0053 }
            if (r1 != 0) goto L_0x0051
            java.util.Set<java.lang.String> r1 = r6.f461e     // Catch:{ all -> 0x0053 }
            r1.add(r7)     // Catch:{ all -> 0x0053 }
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            r0 = 0
            r1 = 0
            if (r8 == 0) goto L_0x001c
            anet.channel.statist.StrategyStatObject r0 = new anet.channel.statist.StrategyStatObject
            r0.<init>(r1)
            r0.readStrategyFileId = r7
        L_0x001c:
            java.lang.Object r2 = anet.channel.strategy.C0589m.m304a(r7, r0)
            anet.channel.strategy.StrategyTable r2 = (anet.channel.strategy.StrategyTable) r2
            if (r2 == 0) goto L_0x0036
            r2.mo9026a()
            java.util.Map<java.lang.String, anet.channel.strategy.StrategyTable> r3 = r6.f457a
            monitor-enter(r3)
            java.util.Map<java.lang.String, anet.channel.strategy.StrategyTable> r4 = r6.f457a     // Catch:{ all -> 0x0033 }
            java.lang.String r5 = r2.f468a     // Catch:{ all -> 0x0033 }
            r4.put(r5, r2)     // Catch:{ all -> 0x0033 }
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            goto L_0x0036
        L_0x0033:
            r7 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            throw r7
        L_0x0036:
            java.util.Set<java.lang.String> r3 = r6.f461e
            monitor-enter(r3)
            java.util.Set<java.lang.String> r4 = r6.f461e     // Catch:{ all -> 0x004e }
            r4.remove(r7)     // Catch:{ all -> 0x004e }
            monitor-exit(r3)     // Catch:{ all -> 0x004e }
            if (r8 == 0) goto L_0x004d
            if (r2 == 0) goto L_0x0044
            r1 = 1
        L_0x0044:
            r0.isSucceed = r1
            anet.channel.appmonitor.IAppMonitor r7 = anet.channel.appmonitor.AppMonitor.getInstance()
            r7.commitStat(r0)
        L_0x004d:
            return
        L_0x004e:
            r7 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x004e }
            throw r7
        L_0x0051:
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            return
        L_0x0053:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.StrategyInfoHolder.mo9014a(java.lang.String, boolean):void");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public void mo9016c() {
        synchronized (this) {
            for (StrategyTable next : this.f457a.values()) {
                if (next.f471d) {
                    StrategyStatObject strategyStatObject = new StrategyStatObject(1);
                    strategyStatObject.writeStrategyFileId = next.f468a;
                    C0589m.m307a(next, next.f468a, strategyStatObject);
                    next.f471d = false;
                }
            }
            C0589m.m307a(this.f458b.mo9007a(), "StrategyConfig", (StrategyStatObject) null);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public StrategyTable mo9017d() {
        StrategyTable strategyTable = this.f460d;
        String str = this.f462f;
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.f457a) {
                strategyTable = this.f457a.get(str);
                if (strategyTable == null) {
                    strategyTable = new StrategyTable(str);
                    this.f457a.put(str, strategyTable);
                }
            }
        }
        return strategyTable;
    }

    /* renamed from: a */
    private String m252a(NetworkStatusHelper.NetworkStatus networkStatus) {
        if (networkStatus.isWifi()) {
            String md5ToHex = StringUtils.md5ToHex(NetworkStatusHelper.getWifiBSSID());
            if (TextUtils.isEmpty(md5ToHex)) {
                md5ToHex = "";
            }
            return "WIFI$" + md5ToHex;
        } else if (!networkStatus.isMobile()) {
            return "";
        } else {
            return networkStatus.getType() + "$" + NetworkStatusHelper.getApn();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo9013a(C0583l.C0587d dVar) {
        if (dVar.f543g != 0) {
            AmdcRuntimeInfo.updateAmdcLimit(dVar.f543g, dVar.f544h);
        }
        mo9017d().update(dVar);
        this.f458b.mo9010a(dVar);
    }

    public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f462f = m252a(networkStatus);
        String str = this.f462f;
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.f457a) {
                if (!this.f457a.containsKey(str)) {
                    C0592a.m314a(new C0576e(this, str));
                }
            }
        }
    }

    /* compiled from: Taobao */
    private static class LruStrategyMap extends SerialLruCache<String, StrategyTable> {
        public LruStrategyMap() {
            super(3);
        }

        /* access modifiers changed from: protected */
        public boolean entryRemoved(Map.Entry<String, StrategyTable> entry) {
            C0592a.m314a(new C0577f(this, entry));
            return true;
        }
    }
}
