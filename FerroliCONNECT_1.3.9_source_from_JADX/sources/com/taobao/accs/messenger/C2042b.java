package com.taobao.accs.messenger;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/* renamed from: com.taobao.accs.messenger.b */
/* compiled from: Taobao */
class C2042b extends Handler {

    /* renamed from: a */
    final /* synthetic */ MessengerService f3354a;

    C2042b(MessengerService messengerService) {
        this.f3354a = messengerService;
    }

    public void handleMessage(Message message) {
        Intent intent;
        if (message != null && (intent = (Intent) message.getData().getParcelable(MessengerService.INTENT)) != null) {
            this.f3354a.f3350a.execute(new C2043c(this, intent));
        }
    }
}
