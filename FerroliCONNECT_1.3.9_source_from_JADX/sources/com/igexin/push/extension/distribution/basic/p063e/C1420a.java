package com.igexin.push.extension.distribution.basic.p063e;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: com.igexin.push.extension.distribution.basic.e.a */
public class C1420a extends SQLiteOpenHelper {

    /* renamed from: a */
    SQLiteDatabase f2455a = null;

    public C1420a(Context context) {
        super(context, "pushext.db", (SQLiteDatabase.CursorFactory) null, 4);
    }

    /* renamed from: a */
    private String m2438a(String str, String str2) {
        return "delete from " + str + " where " + str2;
    }

    /* renamed from: a */
    private String m2439a(String[] strArr, String[] strArr2, int i) {
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

    /* renamed from: a */
    public Cursor mo14958a(String str, String[] strArr) {
        this.f2455a = getReadableDatabase();
        try {
            return this.f2455a.rawQuery(str, strArr);
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public Cursor mo14959a(String str, String[] strArr, String str2) {
        this.f2455a = getReadableDatabase();
        return this.f2455a.query(str, strArr, str2, (String[]) null, (String) null, (String) null, (String) null);
    }

    /* renamed from: a */
    public Cursor mo14960a(String str, String[] strArr, String[] strArr2, String[] strArr3, String str2) {
        Cursor query;
        String[] strArr4 = strArr;
        String[] strArr5 = strArr2;
        this.f2455a = getReadableDatabase();
        this.f2455a.beginTransaction();
        Cursor cursor = null;
        if (strArr4 == null) {
            try {
                query = this.f2455a.query(str, strArr3, (String) null, (String[]) null, (String) null, (String) null, str2);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f2455a.endTransaction();
                throw th;
            }
        } else if (strArr4.length != 1) {
            query = this.f2455a.query(str, strArr3, m2439a(strArr, strArr2, strArr4.length), (String[]) null, (String) null, (String) null, str2);
        } else if (strArr5.length == 1) {
            SQLiteDatabase sQLiteDatabase = this.f2455a;
            query = sQLiteDatabase.query(str, strArr3, strArr4[0] + " = ? ", strArr2, (String) null, (String) null, str2);
        } else {
            query = this.f2455a.query(str, strArr3, m2439a(strArr, strArr2, strArr5.length), (String[]) null, (String) null, (String) null, str2);
        }
        cursor = query;
        this.f2455a.setTransactionSuccessful();
        this.f2455a.endTransaction();
        return cursor;
    }

    /* renamed from: a */
    public void mo14961a(String str, ContentValues contentValues) {
        this.f2455a = getWritableDatabase();
        this.f2455a.beginTransaction();
        try {
            this.f2455a.insert(str, (String) null, contentValues);
            this.f2455a.setTransactionSuccessful();
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.f2455a.endTransaction();
            throw th;
        }
        this.f2455a.endTransaction();
    }

    /* renamed from: a */
    public void mo14962a(String str, String str2, ContentValues contentValues) {
        this.f2455a = getWritableDatabase();
        try {
            this.f2455a.replace(str, str2, contentValues);
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    public void mo14963a(String str, String[] strArr, String[] strArr2) {
        SQLiteDatabase sQLiteDatabase;
        String a;
        this.f2455a = getWritableDatabase();
        this.f2455a.beginTransaction();
        if (strArr == null) {
            try {
                this.f2455a.delete(str, (String) null, (String[]) null);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f2455a.endTransaction();
                throw th;
            }
        } else {
            if (strArr.length != 1) {
                sQLiteDatabase = this.f2455a;
                a = m2438a(str, m2439a(strArr, strArr2, strArr.length));
            } else if (strArr2.length == 1) {
                SQLiteDatabase sQLiteDatabase2 = this.f2455a;
                sQLiteDatabase2.delete(str, strArr[0] + " = ?", strArr2);
            } else {
                sQLiteDatabase = this.f2455a;
                a = m2438a(str, m2439a(strArr, strArr2, strArr2.length));
            }
            sQLiteDatabase.execSQL(a);
        }
        this.f2455a.setTransactionSuccessful();
        this.f2455a.endTransaction();
    }

    public void close() {
        try {
            this.f2455a.close();
        } catch (Exception unused) {
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.beginTransaction();
            sQLiteDatabase.execSQL("create table if not exists image(id integer primary key autoincrement, imageurl text, imagesrc text, taskid text, createtime bigint)");
            sQLiteDatabase.setTransactionSuccessful();
        } catch (Exception unused) {
        } catch (Throwable th) {
            sQLiteDatabase.endTransaction();
            throw th;
        }
        sQLiteDatabase.endTransaction();
        try {
            sQLiteDatabase.beginTransaction();
            sQLiteDatabase.execSQL("create table if not exists message(id integer primary key autoincrement,messageid text,taskid text,appid text,info text,msgextra blob,key text,createtime integer)");
            sQLiteDatabase.setTransactionSuccessful();
        } catch (Exception unused2) {
        } catch (Throwable th2) {
            sQLiteDatabase.endTransaction();
            throw th2;
        }
        sQLiteDatabase.endTransaction();
        try {
            sQLiteDatabase.beginTransaction();
            sQLiteDatabase.execSQL("create table if not exists sc(id integer primary key autoincrement, title text, value text)");
            sQLiteDatabase.setTransactionSuccessful();
        } catch (Exception unused3) {
        } catch (Throwable th3) {
            sQLiteDatabase.endTransaction();
            throw th3;
        }
        sQLiteDatabase.endTransaction();
        try {
            sQLiteDatabase.beginTransaction();
            sQLiteDatabase.execSQL("create table if not exists extconfig (key integer primary key, value text)");
            sQLiteDatabase.setTransactionSuccessful();
        } catch (Exception unused4) {
        } catch (Throwable th4) {
            sQLiteDatabase.endTransaction();
            throw th4;
        }
        sQLiteDatabase.endTransaction();
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onUpgrade(sQLiteDatabase, i2, i);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            sQLiteDatabase.execSQL("drop table if exists image");
        } catch (Exception unused) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists message");
        } catch (Exception unused2) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists sc");
        } catch (Exception unused3) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists extconfig");
        } catch (Exception unused4) {
        }
        onCreate(sQLiteDatabase);
    }
}
