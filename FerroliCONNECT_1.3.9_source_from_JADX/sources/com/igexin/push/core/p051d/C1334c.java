package com.igexin.push.core.p051d;

import com.igexin.push.core.p047a.C1257f;
import java.util.TimerTask;

/* renamed from: com.igexin.push.core.d.c */
class C1334c extends TimerTask {

    /* renamed from: a */
    final /* synthetic */ C1333b f2080a;

    C1334c(C1333b bVar) {
        this.f2080a = bVar;
    }

    public void run() {
        C1257f.m1711a().mo14479a(this.f2080a.f2077g, this.f2080a.f2078h, this.f2080a.f2079i);
        this.f2080a.f2078h.mo14825a(this.f2080a.f2078h.mo14824a() + 1);
    }
}
