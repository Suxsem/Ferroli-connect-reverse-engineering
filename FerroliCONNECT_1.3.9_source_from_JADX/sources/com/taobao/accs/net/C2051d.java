package com.taobao.accs.net;

import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.utl.ALog;

/* renamed from: com.taobao.accs.net.d */
/* compiled from: Taobao */
class C2051d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2049b f3393a;

    C2051d(C2049b bVar) {
        this.f3393a = bVar;
    }

    public void run() {
        if (this.f3393a.f3377e.mo18424c()) {
            ALog.m3727e(this.f3393a.mo18485d(), "receive ping time out! ", new Object[0]);
            C2053f.m3619a(this.f3393a.f3376d).mo18503c();
            this.f3393a.mo18474a("", false, "receive ping timeout");
            this.f3393a.f3377e.mo18413a(AccsErrorCode.SPDY_PING_TIME_OUT.copy().detail(AccsErrorCode.getAllDetails((String) null)).build());
        }
    }
}
