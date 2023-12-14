package anet.channel.statist;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;

@Monitor(module = "networkPrefer", monitorPoint = "quic_detect")
/* compiled from: Taobao */
public class QuicDetectStat extends StatObject {
    @Dimension
    public String host;
    @Dimension

    /* renamed from: ip */
    public String f407ip;
    @Dimension
    public volatile String netType;
    @Dimension
    public String protocol;
    @Dimension
    public int ret;

    public QuicDetectStat(String str, IConnStrategy iConnStrategy) {
        this.host = str;
        if (iConnStrategy != null) {
            this.f407ip = iConnStrategy.getIp();
            this.protocol = iConnStrategy.getProtocol().protocol;
        }
        this.netType = NetworkStatusHelper.getNetworkSubType();
    }
}
