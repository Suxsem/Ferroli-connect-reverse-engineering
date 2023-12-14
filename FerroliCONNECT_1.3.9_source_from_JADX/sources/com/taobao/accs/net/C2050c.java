package com.taobao.accs.net;

import com.alibaba.sdk.android.error.ErrorBuilder;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.data.C2030d;
import com.taobao.accs.data.Message;
import com.taobao.accs.utl.ALog;

/* renamed from: com.taobao.accs.net.c */
/* compiled from: Taobao */
class C2050c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f3389a;

    /* renamed from: b */
    final /* synthetic */ long f3390b;

    /* renamed from: c */
    final /* synthetic */ boolean f3391c;

    /* renamed from: d */
    final /* synthetic */ C2049b f3392d;

    C2050c(C2049b bVar, String str, long j, boolean z) {
        this.f3392d = bVar;
        this.f3389a = str;
        this.f3390b = j;
        this.f3391c = z;
    }

    public void run() {
        Message a = this.f3392d.f3377e.mo18411a(this.f3389a);
        if (a != null) {
            C2030d dVar = this.f3392d.f3377e;
            ErrorBuilder copy = AccsErrorCode.REQ_TIME_OUT.copy();
            dVar.mo18415a(a, copy.msg("发送超过" + this.f3390b + "未收到回执").detail(AccsErrorCode.getAllDetails((String) null)).build());
            this.f3392d.mo18474a(this.f3389a, this.f3391c, "receive data time out");
            String d = this.f3392d.mo18485d();
            ALog.m3727e(d, this.f3389a + "-> receive data time out!", new Object[0]);
        }
    }
}
