package com.aliyun.ams.emas.push;

import android.app.Notification;
import android.content.Context;
import com.alibaba.sdk.android.logger.ILog;
import com.aliyun.ams.emas.push.notification.C0913c;
import com.aliyun.ams.emas.push.notification.C0916f;

/* renamed from: com.aliyun.ams.emas.push.e */
/* compiled from: Taobao */
final class C0907e implements C0909g {

    /* renamed from: a */
    final /* synthetic */ C0913c f1423a;

    /* renamed from: b */
    final /* synthetic */ C0916f f1424b;

    /* renamed from: c */
    final /* synthetic */ Context f1425c;

    /* renamed from: d */
    final /* synthetic */ IAgooPushCallback f1426d;

    C0907e(C0913c cVar, C0916f fVar, Context context, IAgooPushCallback iAgooPushCallback) {
        this.f1423a = cVar;
        this.f1424b = fVar;
        this.f1425c = context;
        this.f1426d = iAgooPushCallback;
    }

    /* renamed from: a */
    public void mo10175a(Notification notification) {
        ILog iLog = C0910h.imortantLogger;
        iLog.mo9706d("push created notification" + this.f1423a.mo10202b());
        this.f1424b.mo10237a(this.f1425c, notification, this.f1423a);
        ILog iLog2 = C0910h.imortantLogger;
        iLog2.mo9706d("push onNotificationShow " + this.f1423a.mo10202b());
        this.f1426d.onNotificationShow(this.f1425c, this.f1423a.mo10202b(), this.f1423a.mo10205c(), this.f1423a.mo10209e());
    }
}
