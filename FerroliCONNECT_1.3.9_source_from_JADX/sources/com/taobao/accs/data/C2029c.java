package com.taobao.accs.data;

import anet.channel.appmonitor.AppMonitor;
import com.taobao.accs.common.Constants;
import com.taobao.accs.p103ut.monitor.AssembleMonitor;
import com.taobao.accs.utl.ALog;

/* renamed from: com.taobao.accs.data.c */
/* compiled from: Taobao */
class C2029c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2027a f3304a;

    C2029c(C2027a aVar) {
        this.f3304a = aVar;
    }

    public void run() {
        synchronized (this.f3304a) {
            if (this.f3304a.f3300f == 0) {
                ALog.m3727e("AssembleMessage", "timeout", Constants.KEY_DATA_ID, this.f3304a.f3296b);
                int unused = this.f3304a.f3300f = 1;
                this.f3304a.f3302h.clear();
                AppMonitor.getInstance().commitStat(new AssembleMonitor(this.f3304a.f3296b, String.valueOf(this.f3304a.f3300f)));
            }
        }
    }
}
