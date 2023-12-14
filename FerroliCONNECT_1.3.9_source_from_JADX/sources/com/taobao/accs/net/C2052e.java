package com.taobao.accs.net;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UtilityImpl;

/* renamed from: com.taobao.accs.net.e */
/* compiled from: Taobao */
class C2052e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Context f3394a;

    /* renamed from: b */
    final /* synthetic */ C2049b f3395b;

    C2052e(C2049b bVar, Context context) {
        this.f3395b = bVar;
        this.f3394a = context;
    }

    public void run() {
        if (UtilityImpl.m3770l(this.f3394a)) {
            ALog.m3725d(this.f3395b.mo18485d(), "startChannelService", new Object[0]);
            Intent intent = new Intent(Constants.ACTION_START_SERVICE);
            intent.putExtra(Constants.KEY_APP_KEY, this.f3395b.mo18490i());
            intent.putExtra(Constants.KEY_TTID, this.f3395b.f3373a);
            intent.putExtra(Constants.KEY_PACKAGE_NAME, this.f3394a.getPackageName());
            intent.putExtra("app_sercet", this.f3395b.f3381i.getAppSecret());
            intent.putExtra(Constants.KEY_MODE, AccsClientConfig.mEnv);
            intent.putExtra(Constants.KEY_CONFIG_TAG, this.f3395b.f3385m);
            intent.setClassName(this.f3394a.getPackageName(), AdapterUtilityImpl.channelService);
            IntentDispatch.dispatchIntent(this.f3394a, intent, AdapterUtilityImpl.channelService);
            Intent intent2 = new Intent();
            intent2.setAction("org.agoo.android.intent.action.REPORT");
            intent2.setPackage(this.f3394a.getPackageName());
            String agooCustomServiceName = AdapterGlobalClientInfo.getAgooCustomServiceName(this.f3394a);
            if (!TextUtils.isEmpty(agooCustomServiceName)) {
                intent2.setClassName(this.f3394a.getPackageName(), agooCustomServiceName);
                IntentDispatch.dispatchIntent(this.f3394a, intent2, agooCustomServiceName);
            }
        }
    }
}
