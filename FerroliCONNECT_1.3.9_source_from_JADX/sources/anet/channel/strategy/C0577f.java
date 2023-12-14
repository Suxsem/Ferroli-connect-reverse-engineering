package anet.channel.strategy;

import anet.channel.statist.StrategyStatObject;
import anet.channel.strategy.StrategyInfoHolder;
import java.io.Serializable;
import java.util.Map;

/* renamed from: anet.channel.strategy.f */
/* compiled from: Taobao */
class C0577f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Map.Entry f501a;

    /* renamed from: b */
    final /* synthetic */ StrategyInfoHolder.LruStrategyMap f502b;

    C0577f(StrategyInfoHolder.LruStrategyMap lruStrategyMap, Map.Entry entry) {
        this.f502b = lruStrategyMap;
        this.f501a = entry;
    }

    public void run() {
        StrategyTable strategyTable = (StrategyTable) this.f501a.getValue();
        if (strategyTable.f471d) {
            StrategyStatObject strategyStatObject = new StrategyStatObject(1);
            strategyStatObject.writeStrategyFileId = strategyTable.f468a;
            C0589m.m307a((Serializable) this.f501a.getValue(), strategyTable.f468a, strategyStatObject);
            strategyTable.f471d = false;
        }
    }
}
