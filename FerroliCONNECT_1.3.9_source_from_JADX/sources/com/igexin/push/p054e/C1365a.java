package com.igexin.push.p054e;

import android.content.Context;
import com.igexin.p032b.p033a.p035b.C1173b;
import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.p036a.p037a.C1168m;
import com.igexin.p032b.p033a.p040d.p041a.C1181a;
import com.igexin.push.core.C1343f;

/* renamed from: com.igexin.push.e.a */
public class C1365a implements C1181a<String, Integer, C1173b, C1176e> {

    /* renamed from: a */
    public Context f2236a;

    public C1365a(Context context) {
        this.f2236a = context;
    }

    /* renamed from: a */
    public C1176e mo14272a(String str, Integer num, C1173b bVar) {
        if (!str.startsWith("socket") || !C1343f.f2171h) {
            return null;
        }
        return new C1168m(str, bVar);
    }
}
