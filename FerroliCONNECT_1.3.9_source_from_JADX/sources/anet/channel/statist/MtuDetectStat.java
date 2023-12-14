package anet.channel.statist;

import anet.channel.status.NetworkStatusHelper;

@Monitor(module = "networkPrefer", monitorPoint = "mtuDetect")
/* compiled from: Taobao */
public class MtuDetectStat extends StatObject {
    @Dimension
    public String bssid = NetworkStatusHelper.getWifiBSSID();
    @Dimension
    public int errCode;
    @Dimension
    public String mnc = NetworkStatusHelper.getSimOp();
    @Dimension
    public int mtu;
    @Dimension
    public String nettype = NetworkStatusHelper.getNetworkSubType();
    @Dimension
    public int pingSuccessCount;
    @Dimension
    public int pingTimeoutCount;
    @Dimension
    public String rtt;
}
