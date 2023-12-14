package com.igexin.push.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.sdk.PushConsts;

/* renamed from: com.igexin.push.core.o */
public class C1352o extends BroadcastReceiver {

    /* renamed from: a */
    private static C1352o f2208a;

    private C1352o() {
    }

    /* renamed from: a */
    public static C1352o m2096a() {
        if (f2208a == null) {
            f2208a = new C1352o();
        }
        return f2208a;
    }

    /* renamed from: a */
    private void m2097a(Intent intent) {
        try {
            C1179b.m1354a("----------------------------------------------------------------------------------");
            C1179b.m1354a("InternalPublicReceiver|action = " + intent.getAction() + ", component = " + intent.getComponent());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String str : extras.keySet()) {
                    C1179b.m1354a("InternalPublicReceiver|key [" + str + "]: " + extras.get(str));
                }
                return;
            }
            C1179b.m1354a("InternalPublicReceiver|no extras");
        } catch (Exception unused) {
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (!(intent == null || intent.getAction() == null || !intent.getAction().equals(PushConsts.ACTION_BROADCAST_NETWORK_CHANGE))) {
            m2097a(intent);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("InternalPublicReceiver InternalPublicReceiver:");
        sb.append(intent != null ? intent.getAction() : "null");
        C1179b.m1354a(sb.toString());
        if (C1340e.m2032a() != null) {
            Message message = new Message();
            message.what = C1275b.f1900d;
            message.obj = intent;
            C1340e.m2032a().mo14702a(message);
        }
    }
}
