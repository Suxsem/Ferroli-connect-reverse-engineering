package com.igexin.push.extension.distribution.basic.p058a;

import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.extension.distribution.basic.p060b.C1406a;
import com.igexin.push.extension.distribution.basic.p064f.C1428g;

/* renamed from: com.igexin.push.extension.distribution.basic.a.b */
class C1400b implements C1428g {

    /* renamed from: a */
    final /* synthetic */ BaseAction f2354a;

    /* renamed from: b */
    final /* synthetic */ String f2355b;

    /* renamed from: c */
    final /* synthetic */ String f2356c;

    /* renamed from: d */
    final /* synthetic */ String f2357d;

    /* renamed from: e */
    final /* synthetic */ int f2358e;

    /* renamed from: f */
    final /* synthetic */ C1396a f2359f;

    C1400b(C1396a aVar, BaseAction baseAction, String str, String str2, String str3, int i) {
        this.f2359f = aVar;
        this.f2354a = baseAction;
        this.f2355b = str;
        this.f2356c = str2;
        this.f2357d = str3;
        this.f2358e = i;
    }

    /* renamed from: a */
    public void mo14853a(BaseAction baseAction) {
        int i = this.f2358e;
        if (i == 2) {
            ((C1406a) this.f2354a).mo14886g(true);
        } else if (i == 3) {
            ((C1406a) this.f2354a).mo14889h(true);
        } else if (i == 8) {
            ((C1406a) this.f2354a).mo14870c(true);
        }
        C1406a aVar = (C1406a) baseAction;
        if (aVar.mo14913y() && aVar.mo14914z() && aVar.mo14898l() && C1343f.m2074a(this.f2355b, true) == 0) {
            C1257f.m1711a().mo14482a(this.f2355b, this.f2356c, "1");
        }
    }

    /* renamed from: a */
    public void mo14854a(Exception exc) {
        if (((C1406a) this.f2354a).mo14855A() >= 3) {
            ((C1406a) this.f2354a).mo14886g(true);
        }
        if (((C1406a) this.f2354a).mo14856B() >= 3) {
            ((C1406a) this.f2354a).mo14889h(true);
        }
        if (((C1406a) this.f2354a).mo14899m() >= 3) {
            ((C1406a) this.f2354a).mo14870c(true);
        }
        if (!((C1406a) this.f2354a).mo14913y() || !((C1406a) this.f2354a).mo14914z() || !((C1406a) this.f2354a).mo14898l()) {
            this.f2359f.mo14848a(this.f2357d, this.f2355b, this.f2356c, this.f2354a, this.f2358e);
        } else if (C1343f.m2074a(this.f2355b, true) == 0) {
            C1257f.m1711a().mo14482a(this.f2355b, this.f2356c, "1");
        }
    }
}
