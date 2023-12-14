package anet.channel.strategy;

import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.PolicyVersionStat;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import com.taobao.accs.common.Constants;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/* compiled from: Taobao */
class StrategyCollection implements Serializable {

    /* renamed from: a */
    String f446a;

    /* renamed from: b */
    volatile long f447b = 0;

    /* renamed from: c */
    volatile String f448c = null;

    /* renamed from: d */
    boolean f449d = false;

    /* renamed from: e */
    int f450e = 0;

    /* renamed from: f */
    private StrategyList f451f = null;

    /* renamed from: g */
    private transient long f452g = 0;

    /* renamed from: h */
    private transient boolean f453h = true;

    public StrategyCollection() {
    }

    protected StrategyCollection(String str) {
        this.f446a = str;
        this.f449d = DispatchConstants.isAmdcServerDomain(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void checkInit() {
        /*
            r5 = this;
            monitor-enter(r5)
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x001f }
            long r2 = r5.f447b     // Catch:{ all -> 0x001f }
            long r0 = r0 - r2
            r2 = 172800000(0xa4cb800, double:8.53745436E-316)
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0014
            r0 = 0
            r5.f451f = r0     // Catch:{ all -> 0x001f }
            monitor-exit(r5)
            return
        L_0x0014:
            anet.channel.strategy.StrategyList r0 = r5.f451f     // Catch:{ all -> 0x001f }
            if (r0 == 0) goto L_0x001d
            anet.channel.strategy.StrategyList r0 = r5.f451f     // Catch:{ all -> 0x001f }
            r0.checkInit()     // Catch:{ all -> 0x001f }
        L_0x001d:
            monitor-exit(r5)
            return
        L_0x001f:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.StrategyCollection.checkInit():void");
    }

    public synchronized List<IConnStrategy> queryStrategyList() {
        if (this.f451f == null) {
            return Collections.EMPTY_LIST;
        }
        if (this.f453h) {
            this.f453h = false;
            PolicyVersionStat policyVersionStat = new PolicyVersionStat(this.f446a, this.f450e);
            policyVersionStat.reportType = 0;
            AppMonitor.getInstance().commitStat(policyVersionStat);
        }
        return this.f451f.getStrategyList();
    }

    public synchronized void notifyConnEvent(IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if (this.f451f != null) {
            this.f451f.notifyConnEvent(iConnStrategy, connEvent);
            if (!connEvent.isSuccess && this.f451f.shouldRefresh()) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.f452g > 60000) {
                    StrategyCenter.getInstance().forceRefreshStrategy(this.f446a);
                    this.f452g = currentTimeMillis;
                }
            }
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.f447b;
    }

    public synchronized void update(C0583l.C0585b bVar) {
        this.f447b = System.currentTimeMillis() + (((long) bVar.f524b) * 1000);
        if (!bVar.f523a.equalsIgnoreCase(this.f446a)) {
            ALog.m327e("StrategyCollection", "update error!", (String) null, Constants.KEY_HOST, this.f446a, "dnsInfo.host", bVar.f523a);
            return;
        }
        if (this.f450e != bVar.f534l) {
            this.f450e = bVar.f534l;
            PolicyVersionStat policyVersionStat = new PolicyVersionStat(this.f446a, this.f450e);
            policyVersionStat.reportType = 1;
            AppMonitor.getInstance().commitStat(policyVersionStat);
        }
        this.f448c = bVar.f526d;
        if (bVar.f528f == null || bVar.f528f.length == 0 || bVar.f530h == null || bVar.f530h.length == 0) {
            if (bVar.f531i != null) {
                if (bVar.f531i.length == 0) {
                }
            }
            this.f451f = null;
            return;
        }
        if (this.f451f == null) {
            this.f451f = new StrategyList();
        }
        this.f451f.update(bVar);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("\nStrategyList = ");
        sb.append(this.f447b);
        StrategyList strategyList = this.f451f;
        if (strategyList != null) {
            sb.append(strategyList.toString());
        } else if (this.f448c != null) {
            sb.append('[');
            sb.append(this.f446a);
            sb.append("=>");
            sb.append(this.f448c);
            sb.append(']');
        } else {
            sb.append("[]");
        }
        return sb.toString();
    }
}
