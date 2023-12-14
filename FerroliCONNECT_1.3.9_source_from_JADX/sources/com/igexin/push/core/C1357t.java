package com.igexin.push.core;

import android.app.Service;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.util.C1580e;

/* renamed from: com.igexin.push.core.t */
class C1357t implements C1580e {

    /* renamed from: a */
    final /* synthetic */ Service f2220a;

    /* renamed from: b */
    final /* synthetic */ C1356s f2221b;

    C1357t(C1356s sVar, Service service) {
        this.f2221b = sVar;
        this.f2220a = service;
    }

    /* renamed from: a */
    public void mo14804a(boolean z) {
        C1179b.m1354a("ServiceManager|load so error, report bi result = " + z + " ###########");
        this.f2220a.stopSelf();
    }
}
