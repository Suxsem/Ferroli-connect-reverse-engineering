package anet.channel.p008e;

import anet.channel.entity.ConnType;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IConnStrategy;

/* renamed from: anet.channel.e.g */
/* compiled from: Taobao */
final class C0516g implements IConnStrategy {

    /* renamed from: a */
    final /* synthetic */ IConnStrategy f240a;

    C0516g(IConnStrategy iConnStrategy) {
        this.f240a = iConnStrategy;
    }

    public String getIp() {
        return this.f240a.getIp();
    }

    public int getIpType() {
        return this.f240a.getIpType();
    }

    public int getIpSource() {
        return this.f240a.getIpSource();
    }

    public int getPort() {
        return this.f240a.getPort();
    }

    public ConnProtocol getProtocol() {
        this.f240a.getProtocol();
        return ConnProtocol.valueOf(ConnType.HTTP3_1RTT, (String) null, (String) null);
    }

    public int getConnectionTimeout() {
        return this.f240a.getConnectionTimeout();
    }

    public int getReadTimeout() {
        return this.f240a.getReadTimeout();
    }

    public int getRetryTimes() {
        return this.f240a.getRetryTimes();
    }

    public int getHeartbeat() {
        return this.f240a.getHeartbeat();
    }
}
