package com.taobao.accs.net;

import anet.channel.NoAvailStrategyException;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.entity.ConnType;
import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.AccsState;
import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.concurrent.TimeoutException;

/* renamed from: com.taobao.accs.net.r */
/* compiled from: Taobao */
class C2066r implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2057j f3443a;

    C2066r(C2057j jVar) {
        this.f3443a = jVar;
    }

    public void run() {
        Session session;
        ErrorCode errorCode = AccsErrorCode.SUCCESS;
        try {
            SessionCenter instance = SessionCenter.getInstance(this.f3443a.f3381i.getAppKey());
            this.f3443a.mo18514a(instance, this.f3443a.f3381i.getInappHost(), false);
            session = null;
            session = instance.getThrowsException(this.f3443a.mo18478b((String) null), ConnType.TypeLevel.SPDY, 60000);
        } catch (InvalidParameterException e) {
            errorCode = AccsErrorCode.NETWORK_INAPP_ARGS_INVALID.copy().detail(e.getMessage()).build();
        } catch (TimeoutException e2) {
            errorCode = AccsErrorCode.NETWORK_INAPP_TIMEOUT.copy().detail(AccsErrorCode.getAllDetails(e2.getMessage())).build();
        } catch (ConnectException e3) {
            errorCode = AccsErrorCode.NETWORK_INAPP_CONNECT_FAIL.copy().detail(AccsErrorCode.getAllDetails(e3.getMessage())).build();
        } catch (NoAvailStrategyException e4) {
            errorCode = AccsErrorCode.NETWORK_INAPP_NO_STRATEGY.copy().detail(e4.getMessage()).build();
        } catch (Throwable th) {
            this.f3443a.f3414t.mo9709e("sendMessage", th);
            return;
        }
        boolean z = true;
        if (session != null) {
            session.ping(true);
        } else {
            if (errorCode.getCodeInt() != AccsErrorCode.SUCCESS.getCodeInt()) {
                this.f3443a.f3414t.mo9708e(errorCode.toString());
                AccsState.getInstance().mo18229b(this.f3443a.f3385m, AccsState.RECENT_ERRORS, Integer.valueOf(errorCode.getCodeInt()));
            } else {
                this.f3443a.f3414t.mo9708e("reconnect fail");
                AccsState.getInstance().mo18229b(this.f3443a.f3385m, AccsState.RECENT_ERRORS, "reconnect session null");
            }
            z = false;
        }
        if (!z) {
            this.f3443a.m3641r();
        }
    }
}
