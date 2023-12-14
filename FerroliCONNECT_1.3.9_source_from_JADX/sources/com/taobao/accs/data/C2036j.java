package com.taobao.accs.data;

import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;

/* renamed from: com.taobao.accs.data.j */
/* compiled from: Taobao */
class C2036j implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Intent f3328a;

    /* renamed from: b */
    final /* synthetic */ MsgDistributeService f3329b;

    C2036j(MsgDistributeService msgDistributeService, Intent intent) {
        this.f3329b = msgDistributeService;
        this.f3328a = intent;
    }

    public void run() {
        ALog.m3728i("MsgDistributeService", "onStartCommand send message", new Object[0]);
        ACCSManager.AccsRequest accsRequest = (ACCSManager.AccsRequest) this.f3328a.getSerializableExtra(Constants.KEY_SEND_REQDATA);
        String stringExtra = this.f3328a.getStringExtra(Constants.KEY_PACKAGE_NAME);
        String stringExtra2 = this.f3328a.getStringExtra(Constants.KEY_APP_KEY);
        String stringExtra3 = this.f3328a.getStringExtra(Constants.KEY_CONFIG_TAG);
        if (TextUtils.isEmpty(stringExtra3)) {
            stringExtra3 = stringExtra2;
        }
        ACCSManager.getAccsInstance(this.f3329b.getApplicationContext(), stringExtra2, stringExtra3).sendRequest(this.f3329b.getApplicationContext(), accsRequest, stringExtra, true);
    }
}
