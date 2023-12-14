package com.taobao.accs.net;

import android.support.p000v4.app.NotificationCompat;
import anet.channel.session.TnetSpdySession;
import com.alibaba.sdk.android.logger.ILog;
import com.taobao.accs.AccsState;
import com.taobao.accs.common.Constants;
import com.taobao.accs.p103ut.p104a.C2079d;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;

/* renamed from: com.taobao.accs.net.o */
/* compiled from: Taobao */
class C2063o implements Runnable {

    /* renamed from: a */
    final /* synthetic */ int f3432a;

    /* renamed from: b */
    final /* synthetic */ int f3433b;

    /* renamed from: c */
    final /* synthetic */ byte[] f3434c;

    /* renamed from: d */
    final /* synthetic */ TnetSpdySession f3435d;

    /* renamed from: e */
    final /* synthetic */ C2057j f3436e;

    C2063o(C2057j jVar, int i, int i2, byte[] bArr, TnetSpdySession tnetSpdySession) {
        this.f3436e = jVar;
        this.f3432a = i;
        this.f3433b = i2;
        this.f3434c = bArr;
        this.f3435d = tnetSpdySession;
    }

    public void run() {
        this.f3436e.f3414t.mo9712i("onDataReceive", "type", Integer.valueOf(this.f3432a), Constants.KEY_DATA_ID, Integer.valueOf(this.f3433b));
        AccsState.getInstance().mo18227a(this.f3436e.f3385m, AccsState.LAST_MSG_RECEIVE_TIME, Integer.valueOf(this.f3433b));
        if (this.f3432a == 200) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                this.f3436e.f3377e.mo18420a(this.f3434c, this.f3435d.getHost());
                C2079d g = this.f3436e.f3377e.mo18428g();
                if (g != null) {
                    g.f3511c = String.valueOf(currentTimeMillis);
                    g.f3515g = this.f3436e.f3375c == 0 ? NotificationCompat.CATEGORY_SERVICE : "inapp";
                    g.mo18540a();
                }
            } catch (Throwable th) {
                this.f3436e.f3414t.mo9709e("onDataReceive", th);
                UTMini.getInstance().commitEvent(66001, "DATA_RECEIVE", UtilityImpl.m3739a(th));
            }
        } else {
            ILog a = this.f3436e.f3414t;
            a.mo9708e("drop frame len:" + this.f3434c.length + " frameType" + this.f3432a);
        }
    }
}
