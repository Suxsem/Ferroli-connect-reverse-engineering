package com.igexin.push.core;

import android.content.Context;
import android.content.Intent;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.util.C1576a;
import com.igexin.sdk.JobSender;
import com.igexin.sdk.PushService;

/* renamed from: com.igexin.push.core.u */
class C1358u implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Context f2222a;

    /* renamed from: b */
    final /* synthetic */ Intent f2223b;

    /* renamed from: c */
    final /* synthetic */ int f2224c;

    /* renamed from: d */
    final /* synthetic */ C1356s f2225d;

    C1358u(C1356s sVar, Context context, Intent intent, int i) {
        this.f2225d = sVar;
        this.f2222a = context;
        this.f2223b = intent;
        this.f2224c = i;
    }

    /* renamed from: a */
    private void m2158a() {
        if (this.f2224c == 1902141359) {
            this.f2223b.setClass(this.f2222a, PushService.class);
        }
        JobSender.getInstance().runJob(this.f2222a, this.f2223b, this.f2224c);
    }

    public void run() {
        try {
            if (!C1576a.m3223i() || C1576a.m3213b(this.f2222a)) {
                this.f2222a.getApplicationContext().startService(this.f2223b);
                return;
            }
            m2158a();
            C1179b.m1354a("ServiceManager|startService by job");
        } catch (Throwable th) {
            C1179b.m1354a("ServiceManager|startPushService err：" + th.toString());
            if (th instanceof IllegalStateException) {
                m2158a();
            }
        }
    }
}
