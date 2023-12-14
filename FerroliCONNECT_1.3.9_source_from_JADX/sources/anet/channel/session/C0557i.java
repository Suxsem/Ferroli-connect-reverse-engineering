package anet.channel.session;

import anet.channel.IAuth;
import anet.channel.entity.C0518b;
import anet.channel.statist.SessionStatistic;
import anet.channel.util.ALog;

/* renamed from: anet.channel.session.i */
/* compiled from: Taobao */
class C0557i implements IAuth.AuthCallback {

    /* renamed from: a */
    final /* synthetic */ TnetSpdySession f402a;

    C0557i(TnetSpdySession tnetSpdySession) {
        this.f402a = tnetSpdySession;
    }

    public void onAuthSuccess() {
        this.f402a.notifyStatus(4, (C0518b) null);
        this.f402a.f381z = System.currentTimeMillis();
        if (this.f402a.f372D != null) {
            this.f402a.f372D.start(this.f402a);
        }
        this.f402a.f105q.ret = 1;
        ALog.m325d("awcn.TnetSpdySession", "spdyOnStreamResponse", this.f402a.f104p, "authTime", Long.valueOf(this.f402a.f105q.authTime));
        if (this.f402a.f369A > 0) {
            this.f402a.f105q.authTime = System.currentTimeMillis() - this.f402a.f369A;
        }
    }

    public void onAuthFail(int i, String str) {
        this.f402a.notifyStatus(5, (C0518b) null);
        if (this.f402a.f105q != null) {
            SessionStatistic sessionStatistic = this.f402a.f105q;
            sessionStatistic.closeReason = "Accs_Auth_Fail:" + i;
            this.f402a.f105q.errorCode = (long) i;
        }
        this.f402a.close();
    }
}
