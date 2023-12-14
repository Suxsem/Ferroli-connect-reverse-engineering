package com.igexin.push.extension.distribution.basic.p058a.p059a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.sdk.PushConsts;
import java.util.Random;

/* renamed from: com.igexin.push.extension.distribution.basic.a.a.a */
public class C1397a extends BroadcastReceiver {

    /* renamed from: a */
    private static final String f2346a = ("EXT-" + C1397a.class.getName());

    /* renamed from: b */
    private static Handler f2347b = new Handler();

    public void onReceive(Context context, Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            if (extras.getInt(PushConsts.CMD_ACTION) == 10007) {
                boolean z = extras.getBoolean(PushConsts.KEY_ONLINE_STATE);
                C1179b.m1354a(f2346a + "|ExtCidReceiver onlineState = " + z);
                f2347b.postDelayed(new C1398b(this, z), (long) ((new Random().nextInt(5) + 5) * 1000));
            }
        } catch (Exception e) {
            C1179b.m1354a(f2346a + "|exception = " + e.toString());
        }
    }
}
