package com.igexin.sdk.p091a;

import android.content.Context;
import com.igexin.push.core.stub.PushCore;
import com.igexin.sdk.IPushCore;

/* renamed from: com.igexin.sdk.a.a */
public class C1598a {

    /* renamed from: a */
    private static String f3060a = "PushSdk";

    /* renamed from: c */
    private static C1598a f3061c;

    /* renamed from: b */
    private IPushCore f3062b;

    private C1598a() {
    }

    /* renamed from: a */
    public static C1598a m3293a() {
        if (f3061c == null) {
            f3061c = new C1598a();
        }
        return f3061c;
    }

    /* renamed from: a */
    public void mo15288a(IPushCore iPushCore) {
        this.f3062b = iPushCore;
    }

    /* renamed from: a */
    public boolean mo15289a(Context context) {
        try {
            mo15288a((IPushCore) new PushCore());
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: b */
    public IPushCore mo15290b() {
        return this.f3062b;
    }
}
