package com.igexin.sdk;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* renamed from: com.igexin.sdk.c */
final class C1603c extends Handler {

    /* renamed from: a */
    final /* synthetic */ GTIntentService f3068a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private C1603c(GTIntentService gTIntentService, Looper looper) {
        super(looper);
        this.f3068a = gTIntentService;
    }

    public void handleMessage(Message message) {
        this.f3068a.onHandleIntent((Intent) message.obj);
        this.f3068a.stopSelf(message.arg1);
    }
}
