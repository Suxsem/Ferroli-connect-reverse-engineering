package org.android.agoo.control;

import com.taobao.accs.client.AdapterGlobalClientInfo;
import org.android.agoo.message.MessageService;

/* renamed from: org.android.agoo.control.k */
/* compiled from: Taobao */
class C2364k implements Runnable {

    /* renamed from: a */
    final /* synthetic */ BaseIntentService f4090a;

    C2364k(BaseIntentService baseIntentService) {
        this.f4090a = baseIntentService;
    }

    public void run() {
        AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
        BaseIntentService baseIntentService = this.f4090a;
        AgooFactory unused = baseIntentService.agooFactory = AgooFactory.getInstance(baseIntentService.getApplicationContext());
        BaseIntentService baseIntentService2 = this.f4090a;
        NotifManager unused2 = baseIntentService2.notifyManager = baseIntentService2.agooFactory.getNotifyManager();
        BaseIntentService baseIntentService3 = this.f4090a;
        MessageService unused3 = baseIntentService3.messageService = baseIntentService3.agooFactory.getMessageService();
    }
}
