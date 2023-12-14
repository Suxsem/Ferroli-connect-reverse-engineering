package com.taobao.accs.net;

import android.text.TextUtils;
import anet.channel.IAuth;
import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.RequestStatistic;
import com.taobao.accs.AccsState;
import com.taobao.accs.net.C2057j;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import java.util.List;
import java.util.Map;

/* renamed from: com.taobao.accs.net.s */
/* compiled from: Taobao */
class C2067s implements RequestCb {

    /* renamed from: a */
    final /* synthetic */ IAuth.AuthCallback f3444a;

    /* renamed from: b */
    final /* synthetic */ C2057j.C2058a f3445b;

    public void onDataReceive(ByteArray byteArray, boolean z) {
    }

    C2067s(C2057j.C2058a aVar, IAuth.AuthCallback authCallback) {
        this.f3445b = aVar;
        this.f3444a = authCallback;
    }

    public void onResponseCode(int i, Map<String, List<String>> map) {
        Map<String, String> a = UtilityImpl.m3741a(map);
        this.f3445b.f3423e.mo9707d(BaseMonitor.ALARM_POINT_AUTH, "header", a);
        String str = a.get("x-at");
        if (!TextUtils.isEmpty(str)) {
            this.f3445b.f3422d.f3383k = str;
        }
        if (i == 200) {
            this.f3445b.f3423e.mo9712i(BaseMonitor.ALARM_POINT_AUTH, "httpStatusCode", Integer.valueOf(i));
            this.f3444a.onAuthSuccess();
            if (this.f3445b.f3422d instanceof C2057j) {
                ((C2057j) this.f3445b.f3422d).m3640q();
                return;
            }
            return;
        }
        this.f3445b.f3423e.mo9710e(BaseMonitor.ALARM_POINT_AUTH, "httpStatusCode", Integer.valueOf(i));
        AccsState instance = AccsState.getInstance();
        instance.mo18228b(AccsState.RECENT_ERRORS, "auth fail " + a.get("s-accs-retcode"));
        this.f3444a.onAuthFail(i, "auth fail");
    }

    public void onFinish(int i, String str, RequestStatistic requestStatistic) {
        if (i < 0) {
            this.f3445b.f3423e.mo9710e("auth onFinish", "statusCode", Integer.valueOf(i));
            this.f3444a.onAuthFail(i, "onFinish auth fail");
        }
    }
}
