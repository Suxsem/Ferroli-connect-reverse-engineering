package com.igexin.push.extension.distribution.gbd.p079e;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.e.a */
public class C1501a extends SQLiteOpenHelper {

    /* renamed from: a */
    private SQLiteDatabase f2786a = null;

    public C1501a(Context context) {
        super(context, "pushg.db", (SQLiteDatabase.CursorFactory) null, 7);
    }

    /* renamed from: a */
    private String m2804a(String[] strArr, String[] strArr2, int i) {
        StringBuilder sb = new StringBuilder(" ");
        if (strArr.length == 1) {
            for (int i2 = 0; i2 < i; i2++) {
                sb.append(strArr[0]);
                sb.append(" = '");
                sb.append(strArr2[i2]);
                sb.append("'");
                if (i2 < i - 1) {
                    sb.append(" or ");
                }
            }
        } else {
            for (int i3 = 0; i3 < i; i3++) {
                sb.append(strArr[i3]);
                sb.append(" = '");
                sb.append(strArr2[i3]);
                sb.append("'");
                if (i3 < i - 1) {
                    sb.append(" and ");
                }
            }
        }
        return sb.toString();
    }

    /* renamed from: b */
    private String m2805b(String str, String str2) {
        return "delete from " + str + " where " + str2;
    }

    /* renamed from: a */
    public long mo15095a(String str, ContentValues contentValues) {
        this.f2786a = getWritableDatabase();
        this.f2786a.beginTransaction();
        long j = -1;
        try {
            j = this.f2786a.insert(str, (String) null, contentValues);
            this.f2786a.setTransactionSuccessful();
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.f2786a.endTransaction();
            throw th;
        }
        this.f2786a.endTransaction();
        return j;
    }

    /* renamed from: a */
    public Cursor mo15096a(String str, String[] strArr) {
        this.f2786a = getReadableDatabase();
        try {
            return this.f2786a.rawQuery(str, strArr);
        } catch (Exception e) {
            C1540h.m2996a(e);
            return null;
        }
    }

    /* renamed from: a */
    public void mo15097a(String str, ContentValues contentValues, String[] strArr, String[] strArr2) {
        SQLiteDatabase sQLiteDatabase;
        String a;
        this.f2786a = getWritableDatabase();
        this.f2786a.beginTransaction();
        if (strArr == null) {
            try {
                this.f2786a.update(str, contentValues, (String) null, (String[]) null);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f2786a.endTransaction();
                throw th;
            }
        } else {
            if (strArr.length != 1) {
                sQLiteDatabase = this.f2786a;
                a = m2804a(strArr, strArr2, strArr.length);
            } else if (strArr2.length == 1) {
                sQLiteDatabase = this.f2786a;
                a = strArr[0] + "='" + strArr2[0] + "'";
            } else {
                sQLiteDatabase = this.f2786a;
                a = m2804a(strArr, strArr2, strArr2.length);
            }
            sQLiteDatabase.update(str, contentValues, a, (String[]) null);
        }
        this.f2786a.setTransactionSuccessful();
        this.f2786a.endTransaction();
    }

    /* renamed from: a */
    public void mo15098a(String str, String str2) {
        this.f2786a = getWritableDatabase();
        this.f2786a.delete(str, str2, (String[]) null);
    }

    /* renamed from: a */
    public void mo15099a(String str, String str2, ContentValues contentValues) {
        this.f2786a = getWritableDatabase();
        try {
            this.f2786a.replace(str, str2, contentValues);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: a */
    public void mo15100a(String str, String[] strArr, String[] strArr2) {
        SQLiteDatabase sQLiteDatabase;
        String b;
        this.f2786a = getWritableDatabase();
        this.f2786a.beginTransaction();
        if (strArr == null) {
            try {
                this.f2786a.delete(str, (String) null, (String[]) null);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f2786a.endTransaction();
                throw th;
            }
        } else {
            if (strArr.length != 1) {
                sQLiteDatabase = this.f2786a;
                b = m2805b(str, m2804a(strArr, strArr2, strArr.length));
            } else if (strArr2.length == 1) {
                SQLiteDatabase sQLiteDatabase2 = this.f2786a;
                sQLiteDatabase2.delete(str, strArr[0] + " = ?", strArr2);
            } else {
                sQLiteDatabase = this.f2786a;
                b = m2805b(str, m2804a(strArr, strArr2, strArr2.length));
            }
            sQLiteDatabase.execSQL(b);
        }
        this.f2786a.setTransactionSuccessful();
        this.f2786a.endTransaction();
    }

    public void close() {
        try {
            this.f2786a.close();
        } catch (Exception unused) {
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.execSQL("create table if not exists runtime(key integer primary key, value text)");
            sQLiteDatabase.execSQL("create table if not exists ral (id integer primary key  , key integer, value blob,  t integer,  num integer default 0)");
            sQLiteDatabase.execSQL("create table if not exists config (key integer primary key, value text)");
            sQLiteDatabase.execSQL("create table if not exists look (id integer primary key  , value text,  t datetime,type integer,status integer, stage integer)");
            sQLiteDatabase.setTransactionSuccessful();
        } catch (Exception unused) {
        } catch (Throwable th) {
            sQLiteDatabase.endTransaction();
            throw th;
        }
        sQLiteDatabase.endTransaction();
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onUpgrade(sQLiteDatabase, i2, i);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (sQLiteDatabase == null) {
            return;
        }
        if (i == 6 && i2 == 7) {
            sQLiteDatabase.beginTransaction();
            try {
                C1540h.m2997b("GBD_DBHelper", "DBHelper upgrade v=7.");
                sQLiteDatabase.execSQL("drop table if exists ral");
                sQLiteDatabase.execSQL("create table if not exists ral (id integer primary key  , key integer, value blob,  t integer,  num integer default 0)");
                sQLiteDatabase.setTransactionSuccessful();
            } catch (Throwable th) {
                sQLiteDatabase.endTransaction();
                throw th;
            }
            sQLiteDatabase.endTransaction();
            return;
        }
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.execSQL("drop table if exists runtime");
        } catch (Throwable th2) {
            try {
                C1540h.m2996a(th2);
            } catch (Throwable th3) {
                sQLiteDatabase.endTransaction();
                throw th3;
            }
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists ral");
        } catch (Throwable th4) {
            C1540h.m2996a(th4);
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists config");
        } catch (Throwable th5) {
            C1540h.m2996a(th5);
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists look");
        } catch (Throwable th6) {
            C1540h.m2996a(th6);
        }
        sQLiteDatabase.setTransactionSuccessful();
        sQLiteDatabase.endTransaction();
        onCreate(sQLiteDatabase);
    }
}
