package org.android.agoo.control;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.p000v4.app.NotificationCompat;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.messenger.MessengerService;
import com.taobao.accs.utl.ALog;

/* renamed from: org.android.agoo.control.h */
/* compiled from: Taobao */
class C2361h extends Handler {

    /* renamed from: a */
    final /* synthetic */ BaseIntentService f4086a;

    C2361h(BaseIntentService baseIntentService) {
        this.f4086a = baseIntentService;
    }

    public void handleMessage(Message message) {
        if (message != null) {
            ALog.m3728i("BaseIntentService", "handleMessage on receive msg", NotificationCompat.CATEGORY_MESSAGE, message.toString());
            Intent intent = (Intent) message.getData().getParcelable(MessengerService.INTENT);
            if (intent != null) {
                ALog.m3728i("BaseIntentService", "handleMessage get intent success", MessengerService.INTENT, intent.toString());
                ThreadPoolExecutorFactory.execute(new C2362i(this, intent));
            }
        }
    }
}
