package com.aliyun.ams.emas.push;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import java.util.Map;

/* renamed from: com.aliyun.ams.emas.push.c */
/* compiled from: Taobao */
final class C0905c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ IAgooPushConfig f1416a;

    /* renamed from: b */
    final /* synthetic */ Context f1417b;

    /* renamed from: c */
    final /* synthetic */ Map f1418c;

    /* renamed from: d */
    final /* synthetic */ Handler f1419d;

    /* renamed from: e */
    final /* synthetic */ C0909g f1420e;

    C0905c(IAgooPushConfig iAgooPushConfig, Context context, Map map, Handler handler, C0909g gVar) {
        this.f1416a = iAgooPushConfig;
        this.f1417b = context;
        this.f1418c = map;
        this.f1419d = handler;
        this.f1420e = gVar;
    }

    public void run() {
        Notification customNotificationUI = this.f1416a.customNotificationUI(this.f1417b, this.f1418c);
        Handler handler = this.f1419d;
        if (handler != null) {
            handler.post(new C0906d(this, customNotificationUI));
        } else {
            this.f1420e.mo10175a(customNotificationUI);
        }
    }
}
