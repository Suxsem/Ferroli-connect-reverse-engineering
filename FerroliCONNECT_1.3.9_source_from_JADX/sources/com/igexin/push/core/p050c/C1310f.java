package com.igexin.push.core.p050c;

import android.content.ContentValues;
import com.igexin.push.p045b.C1205d;

/* renamed from: com.igexin.push.core.c.f */
class C1310f extends C1205d {

    /* renamed from: a */
    final /* synthetic */ long f2043a;

    /* renamed from: b */
    final /* synthetic */ C1308d f2044b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1310f(C1308d dVar, ContentValues contentValues, long j) {
        super(contentValues);
        this.f2044b = dVar;
        this.f2043a = j;
    }

    /* renamed from: a */
    public void mo14362a() {
        this.f1716d.delete("ral", "id=?", new String[]{String.valueOf(this.f2043a)});
    }
}
