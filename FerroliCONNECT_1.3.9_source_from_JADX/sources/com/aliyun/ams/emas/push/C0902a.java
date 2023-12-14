package com.aliyun.ams.emas.push;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.taobao.accs.messenger.MessengerService;

/* renamed from: com.aliyun.ams.emas.push.a */
/* compiled from: Taobao */
class C0902a extends Handler {

    /* renamed from: a */
    final /* synthetic */ AgooMessageIntentService f1413a;

    C0902a(AgooMessageIntentService agooMessageIntentService) {
        this.f1413a = agooMessageIntentService;
    }

    public void handleMessage(Message message) {
        Intent intent;
        if (message != null && (intent = (Intent) message.getData().getParcelable(MessengerService.INTENT)) != null) {
            this.f1413a.onStartCommand(intent, 0, 0);
        }
    }
}
