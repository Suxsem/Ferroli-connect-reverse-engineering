package com.igexin.push.core.p050c;

import android.content.ContentValues;
import com.igexin.push.p045b.C1205d;

/* renamed from: com.igexin.push.core.c.g */
class C1311g extends C1205d {

    /* renamed from: a */
    final /* synthetic */ long f2045a;

    /* renamed from: b */
    final /* synthetic */ C1308d f2046b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1311g(C1308d dVar, ContentValues contentValues, long j) {
        super(contentValues);
        this.f2046b = dVar;
        this.f2045a = j;
    }

    /* renamed from: a */
    public void mo14362a() {
        this.f1716d.update("ral", this.f1718f, "id=?", new String[]{String.valueOf(this.f2045a)});
    }
}
