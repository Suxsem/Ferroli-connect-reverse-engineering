package anet.channel.p004a;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.flow.FlowStat;
import anet.channel.flow.INetworkAnalysis;
import anet.channel.util.ALog;
import com.taobao.analysis.FlowCenter;

/* renamed from: anet.channel.a.b */
/* compiled from: Taobao */
public class C0477b implements INetworkAnalysis {

    /* renamed from: a */
    private boolean f152a;

    public C0477b() {
        try {
            Class.forName("com.taobao.analysis.FlowCenter");
            this.f152a = true;
        } catch (Exception unused) {
            this.f152a = false;
            ALog.m327e("DefaultNetworkAnalysis", "no NWNetworkAnalysisSDK sdk", (String) null, new Object[0]);
        }
    }

    public void commitFlow(FlowStat flowStat) {
        if (this.f152a) {
            FlowCenter.getInstance().commitFlow(GlobalAppRuntimeInfo.getContext(), flowStat.refer, flowStat.protocoltype, flowStat.req_identifier, flowStat.upstream, flowStat.downstream);
        }
    }
}
