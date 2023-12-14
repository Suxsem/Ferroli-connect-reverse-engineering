package com.igexin.push.extension.mod;

import android.util.Log;
import com.igexin.p030a.C1136f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.sdk.PushConsts;

/* renamed from: com.igexin.push.extension.mod.a */
final class C1559a implements C1136f {
    C1559a() {
    }

    /* renamed from: a */
    public void mo14169a() {
        C1179b.m1354a(SecurityUtils.f2969a + "|load so by new success ^_^");
        SecurityUtils.f2970b = true;
        SecurityUtils.f2971c = "";
    }

    /* renamed from: a */
    public void mo14170a(Throwable th) {
        Log.e(PushConsts.KEY_CLIENT_ID, "load2 so error = " + th.getMessage());
        C1179b.m1354a(SecurityUtils.f2969a + "|load so by new error = " + th.getMessage());
        SecurityUtils.f2970b = false;
        SecurityUtils.f2971c += th.toString() + " + " + th.getMessage();
    }
}
