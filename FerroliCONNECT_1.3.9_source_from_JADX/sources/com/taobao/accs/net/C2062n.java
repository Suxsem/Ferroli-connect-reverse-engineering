package com.taobao.accs.net;

import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.logger.ILog;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.data.C2030d;
import com.taobao.accs.data.Message;

/* renamed from: com.taobao.accs.net.n */
/* compiled from: Taobao */
class C2062n implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f3428a;

    /* renamed from: b */
    final /* synthetic */ long f3429b;

    /* renamed from: c */
    final /* synthetic */ boolean f3430c;

    /* renamed from: d */
    final /* synthetic */ C2057j f3431d;

    C2062n(C2057j jVar, String str, long j, boolean z) {
        this.f3431d = jVar;
        this.f3428a = str;
        this.f3429b = j;
        this.f3430c = z;
    }

    public void run() {
        Message a = this.f3431d.f3377e.mo18411a(this.f3428a);
        if (a != null) {
            C2030d dVar = this.f3431d.f3377e;
            ErrorBuilder copy = AccsErrorCode.REQ_TIME_OUT.copy();
            dVar.mo18415a(a, copy.msg("发送超过" + this.f3429b + "未收到回执").detail(AccsErrorCode.getAllDetails((String) null)).build());
            this.f3431d.mo18474a(this.f3428a, this.f3430c, "receive data time out");
            ILog a2 = this.f3431d.f3414t;
            a2.mo9708e(this.f3428a + "-> receive data time out!");
        }
    }
}
