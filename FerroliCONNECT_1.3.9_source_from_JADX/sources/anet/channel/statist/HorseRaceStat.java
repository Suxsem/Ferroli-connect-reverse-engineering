package anet.channel.statist;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.ConnProtocol;
import anet.channel.util.C0604c;

@Monitor(module = "networkPrefer", monitorPoint = "horseRace")
/* compiled from: Taobao */
public class HorseRaceStat extends StatObject {
    @Dimension
    public volatile String bssid = NetworkStatusHelper.getWifiBSSID();
    @Dimension
    public volatile int connErrorCode;
    @Dimension
    public volatile int connRet = 0;
    @Measure
    public volatile long connTime;
    @Dimension
    public volatile String host;
    @Dimension

    /* renamed from: ip */
    public volatile String f405ip;
    @Dimension
    public volatile int ipStackType;
    @Dimension
    public volatile String localIP;
    @Dimension
    public volatile String mnc = NetworkStatusHelper.getSimOp();
    @Dimension
    public volatile String nettype = NetworkStatusHelper.getNetworkSubType();
    @Dimension
    public volatile String path;
    @Dimension
    public volatile int pingSuccessCount;
    @Dimension
    public volatile int pingTimeoutCount;
    @Dimension
    public volatile int port;
    @Dimension
    public volatile String protocol;
    @Dimension
    public volatile int reqErrorCode;
    @Dimension
    public volatile int reqRet = 0;
    @Measure
    public volatile long reqTime;

    public HorseRaceStat(String str, C0583l.C0588e eVar) {
        this.host = str;
        this.f405ip = eVar.f545a;
        this.port = eVar.f546b.f515a;
        this.protocol = ConnProtocol.valueOf(eVar.f546b).name;
        this.path = eVar.f547c;
        this.ipStackType = C0604c.m351c();
    }
}
