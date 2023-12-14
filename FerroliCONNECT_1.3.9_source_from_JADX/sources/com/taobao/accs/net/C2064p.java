package com.taobao.accs.net;

import com.contrarywind.timer.MessageHandler;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.AccsState;
import com.taobao.accs.data.Message;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import java.util.Iterator;

/* renamed from: com.taobao.accs.net.p */
/* compiled from: Taobao */
class C2064p implements Runnable {

    /* renamed from: a */
    final /* synthetic */ int f3437a;

    /* renamed from: b */
    final /* synthetic */ String f3438b;

    /* renamed from: c */
    final /* synthetic */ int f3439c;

    /* renamed from: d */
    final /* synthetic */ boolean f3440d;

    /* renamed from: e */
    final /* synthetic */ C2057j f3441e;

    C2064p(C2057j jVar, int i, String str, int i2, boolean z) {
        this.f3441e = jVar;
        this.f3437a = i;
        this.f3438b = str;
        this.f3439c = i2;
        this.f3440d = z;
    }

    public void run() {
        Message.C2024a aVar;
        Message b;
        AccsState.getInstance().mo18229b(this.f3441e.f3385m, AccsState.RECENT_ERRORS, "oe " + this.f3437a + " " + this.f3438b);
        int i = this.f3439c;
        if (i > 0) {
            Message.C2024a aVar2 = new Message.C2024a(i, "");
            Iterator<Message.C2024a> it = this.f3441e.f3377e.mo18427f().iterator();
            while (true) {
                if (!it.hasNext()) {
                    aVar = null;
                    break;
                }
                aVar = it.next();
                if (aVar.equals(aVar2)) {
                    break;
                }
            }
            if (!(aVar == null || (b = this.f3441e.f3377e.mo18421b(aVar.mo18399b())) == null)) {
                if (this.f3440d) {
                    if (!this.f3441e.mo18476a(b, (int) MessageHandler.WHAT_SMOOTH_SCROLL)) {
                        this.f3441e.f3377e.mo18415a(b, AccsErrorCode.convertNetworkSdkError(this.f3437a, this.f3438b).detail(AccsErrorCode.getAllDetails((String) null)).build());
                    }
                    if (b.mo18393e() != null) {
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "total_tnet", 0.0d);
                    }
                } else {
                    this.f3441e.f3377e.mo18415a(b, AccsErrorCode.convertNetworkSdkError(this.f3437a, this.f3438b).detail(AccsErrorCode.getAllDetails((String) null)).build());
                }
            }
        }
        int i2 = this.f3439c;
        if (i2 < 0 && this.f3440d) {
            this.f3441e.mo18480b(i2);
        }
    }
}
