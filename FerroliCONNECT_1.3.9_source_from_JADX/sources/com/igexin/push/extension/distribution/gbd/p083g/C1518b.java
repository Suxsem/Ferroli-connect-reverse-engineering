package com.igexin.push.extension.distribution.gbd.p083g;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.sdk.PushConsts;

/* renamed from: com.igexin.push.extension.distribution.gbd.g.b */
public class C1518b extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Message obtain;
        if (C1490c.f2749c != null) {
            if (PushConsts.ACTION_BROADCAST_USER_PRESENT.equals(intent.getAction())) {
                obtain = Message.obtain();
                obtain.what = 5;
                obtain.arg1 = 51;
                obtain.obj = intent;
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                obtain = Message.obtain();
                obtain.what = 5;
                obtain.arg1 = 52;
            } else {
                return;
            }
            C1490c.f2749c.sendMessage(obtain);
        }
    }
}
