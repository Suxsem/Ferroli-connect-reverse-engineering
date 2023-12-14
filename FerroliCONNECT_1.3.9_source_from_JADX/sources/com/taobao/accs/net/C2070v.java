package com.taobao.accs.net;

import android.content.Intent;
import com.taobao.accs.common.Constants;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.utl.AdapterUtilityImpl;

/* renamed from: com.taobao.accs.net.v */
/* compiled from: Taobao */
class C2070v implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2069u f3447a;

    C2070v(C2069u uVar) {
        this.f3447a = uVar;
    }

    public void run() {
        String packageName = this.f3447a.f3398a.getPackageName();
        Intent intent = new Intent();
        intent.setPackage(packageName);
        intent.setAction(Constants.ACTION_COMMAND);
        intent.putExtra("command", 201);
        intent.setClassName(packageName, AdapterUtilityImpl.channelService);
        IntentDispatch.dispatchIntent(this.f3447a.f3398a.getApplicationContext(), intent, AdapterUtilityImpl.channelService);
    }
}
