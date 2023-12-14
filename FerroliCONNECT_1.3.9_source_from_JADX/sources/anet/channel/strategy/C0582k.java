package anet.channel.strategy;

import java.util.Comparator;

/* renamed from: anet.channel.strategy.k */
/* compiled from: Taobao */
class C0582k implements Comparator<IPConnStrategy> {

    /* renamed from: a */
    final /* synthetic */ StrategyList f514a;

    C0582k(StrategyList strategyList) {
        this.f514a = strategyList;
    }

    /* renamed from: a */
    public int compare(IPConnStrategy iPConnStrategy, IPConnStrategy iPConnStrategy2) {
        int i;
        int i2;
        int a = ((ConnHistoryItem) this.f514a.f464b.get(Integer.valueOf(iPConnStrategy.getUniqueId()))).mo8969a();
        int a2 = ((ConnHistoryItem) this.f514a.f464b.get(Integer.valueOf(iPConnStrategy2.getUniqueId()))).mo8969a();
        if (a != a2) {
            return a - a2;
        }
        if (iPConnStrategy.f442a != iPConnStrategy2.f442a) {
            i = iPConnStrategy.f442a;
            i2 = iPConnStrategy2.f442a;
        } else {
            i = iPConnStrategy.protocol.isHttp;
            i2 = iPConnStrategy2.protocol.isHttp;
        }
        return i - i2;
    }
}
