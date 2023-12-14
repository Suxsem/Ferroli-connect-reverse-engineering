package anet.channel.strategy;

import anet.channel.strategy.C0583l;
import anet.channel.strategy.StrategyList;

/* renamed from: anet.channel.strategy.j */
/* compiled from: Taobao */
class C0581j implements StrategyList.Predicate<IPConnStrategy> {

    /* renamed from: a */
    final /* synthetic */ C0583l.C0584a f510a;

    /* renamed from: b */
    final /* synthetic */ String f511b;

    /* renamed from: c */
    final /* synthetic */ ConnProtocol f512c;

    /* renamed from: d */
    final /* synthetic */ StrategyList f513d;

    C0581j(StrategyList strategyList, C0583l.C0584a aVar, String str, ConnProtocol connProtocol) {
        this.f513d = strategyList;
        this.f510a = aVar;
        this.f511b = str;
        this.f512c = connProtocol;
    }

    /* renamed from: a */
    public boolean apply(IPConnStrategy iPConnStrategy) {
        return iPConnStrategy.getPort() == this.f510a.f515a && iPConnStrategy.getIp().equals(this.f511b) && iPConnStrategy.protocol.equals(this.f512c);
    }
}
