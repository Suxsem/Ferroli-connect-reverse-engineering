package com.taobao.accs.p103ut.monitor;

import anet.channel.statist.Dimension;
import anet.channel.statist.Measure;
import anet.channel.statist.Monitor;
import com.taobao.accs.utl.BaseMonitor;

@Monitor(module = "accs", monitorPoint = "assemble")
/* renamed from: com.taobao.accs.ut.monitor.AssembleMonitor */
/* compiled from: Taobao */
public class AssembleMonitor extends BaseMonitor {
    @Measure
    public long assembleLength;
    @Measure
    public long assembleTimes;
    @Dimension
    public String dataId;
    @Dimension
    public String errorCode;

    public AssembleMonitor(String str, String str2) {
        this.dataId = str;
        this.errorCode = str2;
    }
}
