package anet.channel.fulltrace;

import anet.channel.statist.RequestStatistic;

/* compiled from: Taobao */
public interface IFullTraceAnalysis {
    void commitRequest(String str, RequestStatistic requestStatistic);

    String createRequest();

    C0523b getSceneInfo();
}
