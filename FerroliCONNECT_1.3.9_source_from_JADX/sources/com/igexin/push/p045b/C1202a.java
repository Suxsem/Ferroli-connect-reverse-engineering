package com.igexin.push.p045b;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.p050c.C1295a;
import java.util.LinkedList;
import java.util.List;

/* renamed from: com.igexin.push.b.a */
public class C1202a extends C1190e {

    /* renamed from: e */
    private static final String f1708e = "com.igexin.push.b.a";

    /* renamed from: a */
    protected SQLiteDatabase f1709a;

    /* renamed from: b */
    protected Cursor f1710b;

    /* renamed from: c */
    List<C1295a> f1711c = new LinkedList();

    /* renamed from: d */
    boolean f1712d;

    public C1202a() {
        super(1);
    }

    /* renamed from: a */
    public void mo14353a(C1295a aVar) {
        this.f1711c.add(aVar);
    }

    /* renamed from: b */
    public final int mo14231b() {
        return -2147483639;
    }

    /* renamed from: b_ */
    public void mo14232b_() {
        super.mo14232b_();
        this.f1709a = C1340e.m2032a().mo14712i().getWritableDatabase();
        this.f1709a.setVersion(3);
        for (C1295a a : this.f1711c) {
            a.mo14442a(this.f1709a);
        }
        for (C1295a next : this.f1711c) {
            if (this.f1712d) {
                next.mo14448c(this.f1709a);
            } else {
                next.mo14445b(this.f1709a);
            }
        }
        C1174c.m1310b().mo14319a((Object) new C1204c(-980948));
        C1174c.m1310b().mo14268c();
    }

    /* renamed from: c */
    public void mo14300c() {
        super.mo14300c();
        Cursor cursor = this.f1710b;
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: d */
    public void mo14221d() {
        super.mo14221d();
        this.f1648n = true;
        this.f1642I = true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo14222e() {
    }
}
