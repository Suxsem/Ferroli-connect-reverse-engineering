package com.igexin.push.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

/* renamed from: com.igexin.push.core.n */
public class C1351n extends BroadcastReceiver {

    /* renamed from: a */
    private static C1351n f2207a;

    private C1351n() {
    }

    /* renamed from: a */
    public static C1351n m2095a() {
        if (f2207a == null) {
            f2207a = new C1351n();
        }
        return f2207a;
    }

    public void onReceive(Context context, Intent intent) {
        if (C1340e.m2032a() != null) {
            Message message = new Message();
            message.what = C1275b.f1900d;
            message.obj = intent;
            C1340e.m2032a().mo14702a(message);
        }
    }
}
