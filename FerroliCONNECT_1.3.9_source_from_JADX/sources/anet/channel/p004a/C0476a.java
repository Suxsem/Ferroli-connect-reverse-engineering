package anet.channel.p004a;

import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.fulltrace.C0523b;
import anet.channel.fulltrace.IFullTraceAnalysis;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import com.taobao.analysis.abtest.ABTestCenter;
import com.taobao.analysis.fulltrace.FullTraceAnalysis;
import com.taobao.analysis.fulltrace.RequestInfo;
import com.taobao.analysis.scene.SceneIdentifier;

/* renamed from: anet.channel.a.a */
/* compiled from: Taobao */
public class C0476a implements IFullTraceAnalysis {

    /* renamed from: a */
    private boolean f151a;

    public C0476a() {
        try {
            Class.forName("com.taobao.analysis.fulltrace.FullTraceAnalysis");
            SceneIdentifier.setContext(GlobalAppRuntimeInfo.getContext());
            this.f151a = true;
        } catch (Exception unused) {
            this.f151a = false;
            ALog.m327e("awcn.DefaultFullTraceAnalysis", "not supoort FullTraceAnalysis", (String) null, new Object[0]);
        }
    }

    public String createRequest() {
        if (this.f151a) {
            return FullTraceAnalysis.getInstance().createRequest("network");
        }
        return null;
    }

    public void commitRequest(String str, RequestStatistic requestStatistic) {
        if (this.f151a && requestStatistic != null && !TextUtils.isEmpty(str)) {
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.host = requestStatistic.host;
            requestInfo.bizId = requestStatistic.bizId;
            requestInfo.url = requestStatistic.url;
            requestInfo.retryTimes = requestStatistic.retryTimes;
            requestInfo.netType = requestStatistic.netType;
            requestInfo.protocolType = requestStatistic.protocolType;
            requestInfo.ret = requestStatistic.ret;
            requestInfo.isCbMain = false;
            requestInfo.isReqMain = requestStatistic.isReqMain;
            requestInfo.isReqSync = requestStatistic.isReqSync;
            requestInfo.netErrorCode = String.valueOf(requestStatistic.statusCode);
            requestInfo.pTraceId = requestStatistic.pTraceId;
            requestInfo.netReqStart = requestStatistic.netReqStart;
            requestInfo.netReqServiceBindEnd = requestStatistic.reqServiceTransmissionEnd;
            requestInfo.netReqProcessStart = requestStatistic.reqStart;
            requestInfo.netReqSendStart = requestStatistic.sendStart;
            requestInfo.netRspRecvEnd = requestStatistic.rspEnd;
            requestInfo.netRspCbDispatch = requestStatistic.rspCbDispatch;
            requestInfo.netRspCbStart = requestStatistic.rspCbStart;
            requestInfo.netRspCbEnd = requestStatistic.rspCbEnd;
            requestInfo.reqDeflateSize = requestStatistic.reqHeadDeflateSize + requestStatistic.reqBodyDeflateSize;
            requestInfo.reqInflateSize = requestStatistic.reqHeadInflateSize + requestStatistic.reqBodyInflateSize;
            requestInfo.rspDeflateSize = requestStatistic.rspHeadDeflateSize + requestStatistic.rspBodyDeflateSize;
            requestInfo.rspInflateSize = requestStatistic.rspHeadInflateSize + requestStatistic.rspBodyInflateSize;
            requestInfo.serverRT = requestStatistic.serverRT;
            requestInfo.sendDataTime = requestStatistic.sendDataTime;
            requestInfo.firstDataTime = requestStatistic.firstDataTime;
            requestInfo.recvDataTime = requestStatistic.recDataTime;
            FullTraceAnalysis.getInstance().commitRequest(str, "network", requestInfo);
        }
    }

    public C0523b getSceneInfo() {
        if (!this.f151a) {
            return null;
        }
        C0523b bVar = new C0523b();
        bVar.f259b = SceneIdentifier.isUrlLaunch();
        bVar.f260c = SceneIdentifier.getAppLaunchTime();
        bVar.f261d = SceneIdentifier.getLastLaunchTime();
        bVar.f262e = SceneIdentifier.getDeviceLevel();
        bVar.f258a = SceneIdentifier.getStartType();
        bVar.f263f = SceneIdentifier.getBucketInfo();
        bVar.f264g = ABTestCenter.getUTABTestBucketId("networksdk");
        return bVar;
    }
}
