package com.igexin.push.extension.distribution.basic.p067i;

import android.content.Context;

/* renamed from: com.igexin.push.extension.distribution.basic.i.a */
public class C1432a {

    /* renamed from: b */
    private static C1432a f2484b;

    /* renamed from: a */
    private Context f2485a;

    private C1432a(Context context) {
        this.f2485a = context;
    }

    /* renamed from: a */
    public static C1432a m2496a(Context context) {
        if (f2484b == null) {
            f2484b = new C1432a(context);
        }
        return f2484b;
    }

    /* renamed from: a */
    public int mo14986a(String str, String str2) {
        return this.f2485a.getResources().getIdentifier(str, str2, this.f2485a.getApplicationInfo().packageName);
    }
}
