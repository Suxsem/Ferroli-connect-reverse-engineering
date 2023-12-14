package com.igexin.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1356s;
import com.igexin.push.core.p047a.C1257f;

public class PushReceiver extends BroadcastReceiver {

    /* renamed from: a */
    private static final String f3054a = "com.igexin.sdk.PushReceiver";

    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            try {
                String action = intent.getAction();
                if (!PushConsts.ACTION_BROADCAST_PUSHMANAGER.equals(action)) {
                    if (!PushConsts.ACTION_BROADCAST_TO_BOOT.equals(action) && !action.equals("android.intent.action.ACTION_POWER_CONNECTED") && !action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                        if (!action.equals("android.intent.action.MEDIA_MOUNTED")) {
                            if (PushConsts.ACTION_BROADCAST_NETWORK_CHANGE.equals(action) || PushConsts.ACTION_BROADCAST_USER_PRESENT.equals(action)) {
                                Intent intent2 = new Intent(context.getApplicationContext(), C1257f.m1711a().mo14471a(context));
                                intent2.putExtra(PushConsts.CMD_ACTION, action);
                                C1356s.m2138a().mo14782a(context, intent2);
                                return;
                            }
                            return;
                        }
                    }
                    C1356s.m2138a().mo14782a(context, new Intent(context.getApplicationContext(), C1257f.m1711a().mo14471a(context)));
                } else if (intent.getExtras() != null) {
                    Intent intent3 = new Intent(context.getApplicationContext(), C1257f.m1711a().mo14471a(context));
                    intent3.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
                    intent3.putExtra("bundle", intent.getExtras());
                    C1356s.m2138a().mo14782a(context, intent3);
                }
            } catch (Throwable th) {
                C1179b.m1354a(f3054a + "|" + th.toString());
            }
        }
    }
}
