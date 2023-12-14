package com.igexin.push.util;

import android.content.Context;
import android.widget.Toast;

/* renamed from: com.igexin.push.util.b */
final class C1577b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Context f3009a;

    /* renamed from: b */
    final /* synthetic */ String f3010b;

    C1577b(Context context, String str) {
        this.f3009a = context;
        this.f3010b = str;
    }

    public void run() {
        Toast.makeText(this.f3009a, this.f3010b, 1).show();
    }
}
