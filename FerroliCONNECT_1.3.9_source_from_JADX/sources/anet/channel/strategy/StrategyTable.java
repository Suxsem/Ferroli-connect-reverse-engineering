package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.entity.ConnType;
import anet.channel.p008e.C0508a;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anet.channel.strategy.utils.C0594c;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import com.igexin.sdk.GTIntentService;
import com.taobao.accs.common.Constants;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: Taobao */
class StrategyTable implements Serializable {

    /* renamed from: e */
    protected static Comparator<StrategyCollection> f467e = new C0591o();

    /* renamed from: a */
    protected String f468a;

    /* renamed from: b */
    protected volatile String f469b;

    /* renamed from: c */
    Map<String, Long> f470c;

    /* renamed from: d */
    protected transient boolean f471d = false;

    /* renamed from: f */
    private HostLruCache f472f;

    /* renamed from: g */
    private volatile transient int f473g;

    /* compiled from: Taobao */
    private static class HostLruCache extends SerialLruCache<String, StrategyCollection> {
        public HostLruCache(int i) {
            super(i);
        }

        /* access modifiers changed from: protected */
        public boolean entryRemoved(Map.Entry<String, StrategyCollection> entry) {
            if (!entry.getValue().f449d) {
                return true;
            }
            Iterator it = entrySet().iterator();
            while (it.hasNext()) {
                if (!((StrategyCollection) ((Map.Entry) it.next()).getValue()).f449d) {
                    it.remove();
                    return false;
                }
            }
            return false;
        }
    }

    protected StrategyTable(String str) {
        this.f468a = str;
        mo9026a();
    }

    /* renamed from: b */
    private void m267b() {
        if (HttpDispatcher.getInstance().isInitHostsChanged(this.f468a)) {
            for (String next : HttpDispatcher.getInstance().getInitHosts()) {
                this.f472f.put(next, new StrategyCollection(next));
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo9026a() {
        if (this.f472f == null) {
            this.f472f = new HostLruCache(256);
            m267b();
        }
        for (StrategyCollection checkInit : this.f472f.values()) {
            checkInit.checkInit();
        }
        int i = 0;
        ALog.m328i("awcn.StrategyTable", "strategy map", (String) null, "size", Integer.valueOf(this.f472f.size()));
        if (!GlobalAppRuntimeInfo.isTargetProcess()) {
            i = -1;
        }
        this.f473g = i;
        if (this.f470c == null) {
            this.f470c = new ConcurrentHashMap();
        }
    }

    public List<IConnStrategy> queryByHost(String str) {
        StrategyCollection strategyCollection;
        if (TextUtils.isEmpty(str) || !C0594c.m321c(str)) {
            return Collections.EMPTY_LIST;
        }
        m269c();
        synchronized (this.f472f) {
            strategyCollection = (StrategyCollection) this.f472f.get(str);
            if (strategyCollection == null) {
                strategyCollection = new StrategyCollection(str);
                this.f472f.put(str, strategyCollection);
            }
        }
        if (strategyCollection.f447b == 0 || (strategyCollection.isExpired() && AmdcRuntimeInfo.getAmdcLimitLevel() == 0)) {
            m265a(str);
        }
        return strategyCollection.queryStrategyList();
    }

    public String getCnameByHost(String str) {
        StrategyCollection strategyCollection;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.f472f) {
            strategyCollection = (StrategyCollection) this.f472f.get(str);
        }
        if (strategyCollection != null && strategyCollection.isExpired() && AmdcRuntimeInfo.getAmdcLimitLevel() == 0) {
            m265a(str);
        }
        if (strategyCollection != null) {
            return strategyCollection.f448c;
        }
        return null;
    }

    public void update(C0583l.C0587d dVar) {
        ALog.m328i("awcn.StrategyTable", "update strategyTable with httpDns response", this.f468a, new Object[0]);
        try {
            this.f469b = dVar.f537a;
            this.f473g = dVar.f542f;
            C0583l.C0585b[] bVarArr = dVar.f538b;
            if (bVarArr != null) {
                synchronized (this.f472f) {
                    for (C0583l.C0585b bVar : bVarArr) {
                        if (bVar != null) {
                            if (bVar.f523a != null) {
                                if (bVar.f532j) {
                                    this.f472f.remove(bVar.f523a);
                                } else {
                                    StrategyCollection strategyCollection = (StrategyCollection) this.f472f.get(bVar.f523a);
                                    if (strategyCollection == null) {
                                        strategyCollection = new StrategyCollection(bVar.f523a);
                                        this.f472f.put(bVar.f523a, strategyCollection);
                                    }
                                    strategyCollection.update(bVar);
                                }
                            }
                        }
                    }
                }
                this.f471d = true;
                if (ALog.isPrintLog(1)) {
                    StringBuilder sb = new StringBuilder("uniqueId : ");
                    sb.append(this.f468a);
                    sb.append("\n-------------------------domains:------------------------------------");
                    ALog.m325d("awcn.StrategyTable", sb.toString(), (String) null, new Object[0]);
                    synchronized (this.f472f) {
                        for (Map.Entry entry : this.f472f.entrySet()) {
                            sb.setLength(0);
                            sb.append((String) entry.getKey());
                            sb.append(" = ");
                            sb.append(((StrategyCollection) entry.getValue()).toString());
                            ALog.m325d("awcn.StrategyTable", sb.toString(), (String) null, new Object[0]);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            ALog.m326e("awcn.StrategyTable", "fail to update strategyTable", this.f468a, th, new Object[0]);
        }
    }

    /* renamed from: a */
    private void m265a(String str) {
        TreeSet treeSet = new TreeSet();
        treeSet.add(str);
        m266a((Set<String>) treeSet);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo9028a(String str, boolean z) {
        StrategyCollection strategyCollection;
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.f472f) {
                strategyCollection = (StrategyCollection) this.f472f.get(str);
                if (strategyCollection == null) {
                    strategyCollection = new StrategyCollection(str);
                    this.f472f.put(str, strategyCollection);
                }
            }
            if (z || strategyCollection.f447b == 0 || (strategyCollection.isExpired() && AmdcRuntimeInfo.getAmdcLimitLevel() == 0)) {
                m265a(str);
            }
        }
    }

    /* renamed from: a */
    private void m266a(Set<String> set) {
        if (set != null && !set.isEmpty()) {
            if ((!GlobalAppRuntimeInfo.isAppBackground() || AppLifecycle.lastEnterBackgroundTime <= 0) && NetworkStatusHelper.isConnected()) {
                int amdcLimitLevel = AmdcRuntimeInfo.getAmdcLimitLevel();
                if (amdcLimitLevel != 3) {
                    long currentTimeMillis = System.currentTimeMillis();
                    synchronized (this.f472f) {
                        for (String str : set) {
                            StrategyCollection strategyCollection = (StrategyCollection) this.f472f.get(str);
                            if (strategyCollection != null) {
                                strategyCollection.f447b = GTIntentService.WAIT_TIME + currentTimeMillis;
                            }
                        }
                    }
                    if (amdcLimitLevel == 0) {
                        m268b(set);
                    }
                    HttpDispatcher.getInstance().sendAmdcRequest(set, this.f473g);
                    return;
                }
                return;
            }
            ALog.m328i("awcn.StrategyTable", "app in background or no network", this.f468a, new Object[0]);
        }
    }

    /* renamed from: b */
    private void m268b(Set<String> set) {
        TreeSet treeSet = new TreeSet(f467e);
        synchronized (this.f472f) {
            treeSet.addAll(this.f472f.values());
        }
        long currentTimeMillis = System.currentTimeMillis();
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            StrategyCollection strategyCollection = (StrategyCollection) it.next();
            if (strategyCollection.isExpired() && set.size() < 40) {
                strategyCollection.f447b = GTIntentService.WAIT_TIME + currentTimeMillis;
                set.add(strategyCollection.f446a);
            } else {
                return;
            }
        }
    }

    /* renamed from: c */
    private void m269c() {
        try {
            if (HttpDispatcher.getInstance().isInitHostsChanged(this.f468a)) {
                TreeSet treeSet = null;
                synchronized (this.f472f) {
                    for (String next : HttpDispatcher.getInstance().getInitHosts()) {
                        if (!this.f472f.containsKey(next)) {
                            this.f472f.put(next, new StrategyCollection(next));
                            if (treeSet == null) {
                                treeSet = new TreeSet();
                            }
                            treeSet.add(next);
                        }
                    }
                }
                if (treeSet != null) {
                    m266a((Set<String>) treeSet);
                }
            }
        } catch (Exception e) {
            ALog.m326e("awcn.StrategyTable", "checkInitHost failed", this.f468a, e, new Object[0]);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo9027a(String str, IConnStrategy iConnStrategy, ConnEvent connEvent) {
        StrategyCollection strategyCollection;
        if (ALog.isPrintLog(1)) {
            ALog.m325d("awcn.StrategyTable", "[notifyConnEvent]", (String) null, "Host", str, "IConnStrategy", iConnStrategy, "ConnEvent", connEvent);
        }
        String str2 = iConnStrategy.getProtocol().protocol;
        if (ConnType.HTTP3.equals(str2) || ConnType.HTTP3_PLAIN.equals(str2)) {
            C0508a.m107a(connEvent.isSuccess);
            ALog.m327e("awcn.StrategyTable", "enable http3", (String) null, "uniqueId", this.f468a, "enable", Boolean.valueOf(connEvent.isSuccess));
        }
        if (!connEvent.isSuccess && C0594c.m320b(iConnStrategy.getIp())) {
            this.f470c.put(str, Long.valueOf(System.currentTimeMillis()));
            ALog.m327e("awcn.StrategyTable", "disable ipv6", (String) null, "uniqueId", this.f468a, Constants.KEY_HOST, str);
        }
        synchronized (this.f472f) {
            strategyCollection = (StrategyCollection) this.f472f.get(str);
        }
        if (strategyCollection != null) {
            strategyCollection.notifyConnEvent(iConnStrategy, connEvent);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public boolean mo9029a(String str, long j) {
        Long l = this.f470c.get(str);
        if (l == null) {
            return false;
        }
        if (l.longValue() + j >= System.currentTimeMillis()) {
            return true;
        }
        this.f470c.remove(str);
        return false;
    }
}
