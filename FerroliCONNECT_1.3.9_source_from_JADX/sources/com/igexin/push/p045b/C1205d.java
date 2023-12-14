package com.igexin.push.p045b;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1340e;

/* renamed from: com.igexin.push.b.d */
public abstract class C1205d extends C1190e {

    /* renamed from: a */
    private static final String f1715a = C1233j.f1818a;

    /* renamed from: d */
    protected SQLiteDatabase f1716d;

    /* renamed from: e */
    protected Cursor f1717e;

    /* renamed from: f */
    protected ContentValues f1718f;

    /* renamed from: g */
    public C1204c f1719g;

    public C1205d() {
        super(1);
    }

    public C1205d(ContentValues contentValues) {
        super(1);
        this.f1718f = contentValues;
    }

    /* renamed from: a */
    public abstract void mo14362a();

    /* renamed from: b */
    public final int mo14231b() {
        return -2147483640;
    }

    /* renamed from: b_ */
    public void mo14232b_() {
        super.mo14232b_();
        this.f1716d = C1340e.m2032a().mo14712i().getWritableDatabase();
        mo14362a();
        if (this.f1719g != null) {
            C1174c.m1310b().mo14319a((Object) this.f1719g);
            C1174c.m1310b().mo14268c();
        }
    }

    /* renamed from: c */
    public void mo14300c() {
        super.mo14300c();
        Cursor cursor = this.f1717e;
        if (cursor != null && !cursor.isClosed()) {
            try {
                this.f1717e.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: d */
    public void mo14221d() {
        this.f1648n = true;
        this.f1642I = true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo14222e() {
    }
}
