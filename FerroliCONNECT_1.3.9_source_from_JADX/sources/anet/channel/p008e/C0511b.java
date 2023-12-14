package anet.channel.p008e;

import anet.channel.entity.ConnType;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.IStrategyFilter;

/* renamed from: anet.channel.e.b */
/* compiled from: Taobao */
final class C0511b implements IStrategyFilter {
    C0511b() {
    }

    public boolean accept(IConnStrategy iConnStrategy) {
        String str = iConnStrategy.getProtocol().protocol;
        return ConnType.HTTP3.equals(str) || ConnType.HTTP3_PLAIN.equals(str);
    }
}
