package com.taobao.accs.p103ut.monitor;

import anet.channel.statist.Dimension;
import anet.channel.statist.Measure;
import anet.channel.statist.Monitor;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.BaseMonitor;
import p110io.reactivex.annotations.SchedulerSupport;

@Monitor(module = "accs", monitorPoint = "session")
/* renamed from: com.taobao.accs.ut.monitor.SessionMonitor */
/* compiled from: Taobao */
public class SessionMonitor extends BaseMonitor {

    /* renamed from: a */
    private long f3528a;
    @Measure(constantValue = 0.0d, max = 15000.0d, min = 0.0d)
    public long auth_time;

    /* renamed from: b */
    private long f3529b;
    @Dimension
    public int close_connection_type = 2;
    @Dimension
    public String close_reasons = SchedulerSupport.NONE;
    @Dimension
    public String connect_type = SchedulerSupport.NONE;
    public long connection_stop_date;
    @Dimension
    public int fail_reasons = 0;
    @Dimension
    public boolean isProxy = false;
    @Measure(constantValue = 0.0d, max = 86400.0d, min = 0.0d)
    public long live_time;
    @Measure(constantValue = 0.0d)
    public int ping_rec_times;
    @Measure(constantValue = 0.0d)
    public int ping_send_times;
    @Dimension
    public boolean ret = false;
    @Dimension
    public int retry_times;
    @Dimension
    public String sdkv = String.valueOf(Constants.SDK_VERSION_CODE);
    @Measure(constantValue = 0.0d, max = 15000.0d, min = 0.0d)
    public long ssl_time;
    @Measure(constantValue = 0.0d, max = 15000.0d, min = 0.0d)
    public long tcp_time;

    public void setRet(boolean z) {
        this.ret = z;
    }

    public boolean getRet() {
        return this.ret;
    }

    public void setFailReason(int i) {
        this.fail_reasons = i;
    }

    public void onStartConnect() {
        this.f3528a = System.currentTimeMillis();
    }

    public void onConnectStop() {
        this.connection_stop_date = System.currentTimeMillis();
    }

    public void onCloseConnect() {
        this.f3529b = System.currentTimeMillis();
    }

    public void setCloseType(int i) {
        this.close_connection_type = i;
    }

    public void setCloseReason(String str) {
        this.close_reasons = str;
    }

    public void onSendPing() {
        this.ping_send_times++;
    }

    public void onPingCBReceive() {
        this.ping_rec_times++;
    }

    public String getCloseReason() {
        return this.close_reasons;
    }

    public void setRetryTimes(int i) {
        this.retry_times = i;
    }

    public void setConnectType(String str) {
        this.connect_type = str;
    }

    public long getConStopDate() {
        return this.connection_stop_date;
    }

    public long getConCloseDate() {
        return this.f3529b;
    }
}
