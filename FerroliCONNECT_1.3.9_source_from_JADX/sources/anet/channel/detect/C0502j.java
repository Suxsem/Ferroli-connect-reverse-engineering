package anet.channel.detect;

import anet.channel.strategy.C0583l;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IConnStrategy;

/* renamed from: anet.channel.detect.j */
/* compiled from: Taobao */
final class C0502j implements IConnStrategy {

    /* renamed from: a */
    final /* synthetic */ C0583l.C0588e f209a;

    /* renamed from: b */
    final /* synthetic */ ConnProtocol f210b;

    public int getHeartbeat() {
        return 0;
    }

    public int getIpSource() {
        return 2;
    }

    public int getIpType() {
        return 1;
    }

    public int getRetryTimes() {
        return 0;
    }

    C0502j(C0583l.C0588e eVar, ConnProtocol connProtocol) {
        this.f209a = eVar;
        this.f210b = connProtocol;
    }

    public String getIp() {
        return this.f209a.f545a;
    }

    public int getPort() {
        return this.f209a.f546b.f515a;
    }

    public ConnProtocol getProtocol() {
        return this.f210b;
    }

    public int getConnectionTimeout() {
        return this.f209a.f546b.f517c;
    }

    public int getReadTimeout() {
        return this.f209a.f546b.f518d;
    }
}
