package com.taobao.accs.net;

import anet.channel.entity.ConnType;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.dispatch.HttpDispatcher;
import com.taobao.accs.utl.ALog;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.taobao.accs.net.g */
/* compiled from: Taobao */
public class C2054g {

    /* renamed from: a */
    private int f3404a = 0;

    /* renamed from: b */
    private List<IConnStrategy> f3405b = new ArrayList();

    public C2054g(String str) {
        HttpDispatcher.getInstance().addListener(new C2055h(this));
        mo18509a(str);
    }

    /* renamed from: a */
    public List<IConnStrategy> mo18509a(String str) {
        List<IConnStrategy> connStrategyListByHost;
        if ((this.f3404a == 0 || this.f3405b.isEmpty()) && (connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(str)) != null && !connStrategyListByHost.isEmpty()) {
            this.f3405b.clear();
            for (IConnStrategy next : connStrategyListByHost) {
                ConnType valueOf = ConnType.valueOf(next.getProtocol());
                if (valueOf.getTypeLevel() == ConnType.TypeLevel.SPDY && valueOf.isSSL()) {
                    this.f3405b.add(next);
                }
            }
        }
        return this.f3405b;
    }

    /* renamed from: a */
    public IConnStrategy mo18507a() {
        return mo18508a(this.f3405b);
    }

    /* renamed from: a */
    public IConnStrategy mo18508a(List<IConnStrategy> list) {
        if (list == null || list.isEmpty()) {
            ALog.m3725d("HttpDnsProvider", "strategys null or 0", new Object[0]);
            return null;
        }
        int i = this.f3404a;
        if (i < 0 || i >= list.size()) {
            this.f3404a = 0;
        }
        return list.get(this.f3404a);
    }

    /* renamed from: b */
    public void mo18510b() {
        this.f3404a++;
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.m3725d("HttpDnsProvider", "updateStrategyPos StrategyPos:" + this.f3404a, new Object[0]);
        }
    }

    /* renamed from: c */
    public int mo18512c() {
        return this.f3404a;
    }

    /* renamed from: b */
    public void mo18511b(String str) {
        StrategyCenter.getInstance().forceRefreshStrategy(str);
    }
}
