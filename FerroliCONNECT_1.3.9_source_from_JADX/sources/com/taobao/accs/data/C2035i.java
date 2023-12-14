package com.taobao.accs.data;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.p000v4.app.NotificationCompat;
import com.taobao.accs.messenger.MessengerService;
import com.taobao.accs.utl.ALog;

/* renamed from: com.taobao.accs.data.i */
/* compiled from: Taobao */
class C2035i extends Handler {

    /* renamed from: a */
    final /* synthetic */ MsgDistributeService f3327a;

    C2035i(MsgDistributeService msgDistributeService) {
        this.f3327a = msgDistributeService;
    }

    public void handleMessage(Message message) {
        if (message != null) {
            ALog.m3728i("MsgDistributeService", "handleMessage on receive msg", NotificationCompat.CATEGORY_MESSAGE, message.toString());
            Intent intent = (Intent) message.getData().getParcelable(MessengerService.INTENT);
            if (intent != null) {
                ALog.m3728i("MsgDistributeService", "handleMessage get intent success", MessengerService.INTENT, intent.toString());
                this.f3327a.onStartCommand(intent, 0, 0);
            }
        }
    }
}
