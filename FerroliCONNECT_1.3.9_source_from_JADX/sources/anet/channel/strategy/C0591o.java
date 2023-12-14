package anet.channel.strategy;

import java.util.Comparator;

/* renamed from: anet.channel.strategy.o */
/* compiled from: Taobao */
final class C0591o implements Comparator<StrategyCollection> {
    C0591o() {
    }

    /* renamed from: a */
    public int compare(StrategyCollection strategyCollection, StrategyCollection strategyCollection2) {
        if (strategyCollection.f447b != strategyCollection2.f447b) {
            return (int) (strategyCollection.f447b - strategyCollection2.f447b);
        }
        return strategyCollection.f446a.compareTo(strategyCollection2.f446a);
    }
}
