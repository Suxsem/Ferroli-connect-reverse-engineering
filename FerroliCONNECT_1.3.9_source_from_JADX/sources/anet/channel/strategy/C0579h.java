package anet.channel.strategy;

import anet.channel.AwcnConfig;
import anet.channel.entity.ConnType;
import anet.channel.p008e.C0508a;
import anet.channel.util.ALog;

/* renamed from: anet.channel.strategy.h */
/* compiled from: Taobao */
class C0579h implements IStrategyFilter {

    /* renamed from: a */
    final /* synthetic */ C0578g f508a;

    C0579h(C0578g gVar) {
        this.f508a = gVar;
    }

    public boolean accept(IConnStrategy iConnStrategy) {
        String str = iConnStrategy.getProtocol().protocol;
        if (ConnType.QUIC.equals(str) || ConnType.QUIC_PLAIN.equals(str)) {
            ALog.m328i("awcn.StrategyCenter", "gquic strategy disabled", (String) null, "strategy", iConnStrategy);
            return false;
        }
        boolean isHttp3Enable = AwcnConfig.isHttp3Enable();
        boolean b = C0508a.m109b();
        if ((isHttp3Enable && b) || (!ConnType.HTTP3.equals(str) && !ConnType.HTTP3_PLAIN.equals(str))) {
            return true;
        }
        ALog.m328i("awcn.StrategyCenter", "http3 strategy disabled", (String) null, "strategy", iConnStrategy);
        return false;
    }
}
