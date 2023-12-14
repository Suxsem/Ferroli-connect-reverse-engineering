package com.igexin.push.extension.distribution.gbd.p083g;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;

/* renamed from: com.igexin.push.extension.distribution.gbd.g.a */
public class C1517a extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (C1490c.f2749c != null) {
            Message message = new Message();
            message.what = 3;
            message.obj = intent;
            C1490c.f2749c.sendMessage(message);
        }
    }
}
