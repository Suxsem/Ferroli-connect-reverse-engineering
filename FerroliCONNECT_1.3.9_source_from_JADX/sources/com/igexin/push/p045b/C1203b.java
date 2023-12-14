package com.igexin.push.p045b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.p050c.C1312h;

/* renamed from: com.igexin.push.b.b */
public class C1203b extends SQLiteOpenHelper {

    /* renamed from: a */
    private SQLiteDatabase f1713a = null;

    public C1203b(Context context) {
        super(context, "pushsdk.db", (SQLiteDatabase.CursorFactory) null, 3);
    }

    /* renamed from: a */
    private String m1462a(String[] strArr, String[] strArr2, int i) {
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
    private String m1463b(String str, String str2) {
        return "delete from " + str + " where " + str2;
    }

    /* renamed from: a */
    public int mo14354a(String str, String str2) {
        this.f1713a = getWritableDatabase();
        this.f1713a.beginTransaction();
        int i = 0;
        try {
            i = this.f1713a.delete(str, str2, (String[]) null);
            C1179b.m1354a("DBHelper|del " + i + " msg");
            this.f1713a.setTransactionSuccessful();
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.f1713a.endTransaction();
            throw th;
        }
        this.f1713a.endTransaction();
        return i;
    }

    /* renamed from: a */
    public Cursor mo14355a(String str, String[] strArr, String[] strArr2, String[] strArr3, String str2) {
        Cursor query;
        String[] strArr4 = strArr;
        String[] strArr5 = strArr2;
        this.f1713a = getReadableDatabase();
        this.f1713a.beginTransaction();
        Cursor cursor = null;
        if (strArr4 == null) {
            try {
                query = this.f1713a.query(str, strArr3, (String) null, (String[]) null, (String) null, (String) null, str2);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f1713a.endTransaction();
                throw th;
            }
        } else if (strArr4.length != 1) {
            query = this.f1713a.query(str, strArr3, m1462a(strArr, strArr2, strArr4.length), (String[]) null, (String) null, (String) null, str2);
        } else if (strArr5.length == 1) {
            SQLiteDatabase sQLiteDatabase = this.f1713a;
            query = sQLiteDatabase.query(str, strArr3, strArr4[0] + "= ?", strArr2, (String) null, (String) null, str2);
        } else {
            query = this.f1713a.query(str, strArr3, m1462a(strArr, strArr2, strArr5.length), (String[]) null, (String) null, (String) null, str2);
        }
        cursor = query;
        this.f1713a.setTransactionSuccessful();
        this.f1713a.endTransaction();
        return cursor;
    }

    /* renamed from: a */
    public void mo14356a(String str, ContentValues contentValues, String[] strArr, String[] strArr2) {
        SQLiteDatabase sQLiteDatabase;
        String a;
        this.f1713a = getWritableDatabase();
        this.f1713a.beginTransaction();
        if (strArr == null) {
            try {
                this.f1713a.update(str, contentValues, (String) null, (String[]) null);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f1713a.endTransaction();
                throw th;
            }
        } else {
            if (strArr.length != 1) {
                sQLiteDatabase = this.f1713a;
                a = m1462a(strArr, strArr2, strArr.length);
            } else if (strArr2.length == 1) {
                sQLiteDatabase = this.f1713a;
                a = strArr[0] + "='" + strArr2[0] + "'";
            } else {
                sQLiteDatabase = this.f1713a;
                a = m1462a(strArr, strArr2, strArr2.length);
            }
            sQLiteDatabase.update(str, contentValues, a, (String[]) null);
        }
        this.f1713a.setTransactionSuccessful();
        this.f1713a.endTransaction();
    }

    /* renamed from: a */
    public void mo14357a(String str, String[] strArr, String[] strArr2) {
        SQLiteDatabase sQLiteDatabase;
        String b;
        this.f1713a = getWritableDatabase();
        this.f1713a.beginTransaction();
        if (strArr == null) {
            try {
                this.f1713a.delete(str, (String) null, (String[]) null);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f1713a.endTransaction();
                throw th;
            }
        } else {
            if (strArr.length != 1) {
                sQLiteDatabase = this.f1713a;
                b = m1463b(str, m1462a(strArr, strArr2, strArr.length));
            } else if (strArr2.length == 1) {
                SQLiteDatabase sQLiteDatabase2 = this.f1713a;
                int delete = sQLiteDatabase2.delete(str, strArr[0] + " = ?", strArr2);
                C1179b.m1354a("DBHelper|del " + str + " cnt = " + delete);
            } else {
                sQLiteDatabase = this.f1713a;
                b = m1463b(str, m1462a(strArr, strArr2, strArr2.length));
            }
            sQLiteDatabase.execSQL(b);
        }
        this.f1713a.setTransactionSuccessful();
        this.f1713a.endTransaction();
    }

    /* renamed from: a */
    public boolean mo14358a(String str, ContentValues contentValues) {
        boolean z;
        this.f1713a = getWritableDatabase();
        this.f1713a.beginTransaction();
        try {
            this.f1713a.insert(str, (String) null, contentValues);
            this.f1713a.setTransactionSuccessful();
            z = true;
        } catch (Exception unused) {
            z = false;
        } catch (Throwable th) {
            this.f1713a.endTransaction();
            throw th;
        }
        this.f1713a.endTransaction();
        return z;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.execSQL("create table if not exists config (id integer primary key,value text)");
            sQLiteDatabase.execSQL("create table if not exists runtime (id integer primary key,value text)");
            sQLiteDatabase.execSQL("create table if not exists message (id integer primary key autoincrement,messageid text,taskid text,appid text,info text,msgextra blob,key text,status integer,createtime integer)");
            sQLiteDatabase.execSQL("create table if not exists ral (id integer primary key,data text,type integer,time integer)");
            sQLiteDatabase.execSQL("create table if not exists ca (pkgname text primary key,signature text,permissions text, accesstoken blob, expire integer)");
            sQLiteDatabase.execSQL("create table if not exists bi(id integer primary key autoincrement, start_service_count integer, login_count integer, loginerror_nonetwork_count integer, loginerror_timeout_count integer, loginerror_connecterror_count integer, loginerror_other_count integer, online_time long, network_time long, running_time long, create_time text, type integer)");
            sQLiteDatabase.execSQL("create table if not exists st(id integer primary key autoincrement,type integer,value blob,time integer)");
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
        C1312h.m1937a().mo14682d(sQLiteDatabase);
        try {
            sQLiteDatabase.execSQL("drop table if exists config");
        } catch (Exception unused) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists runtime");
        } catch (Exception unused2) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists message");
        } catch (Exception unused3) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists ral");
        } catch (Exception unused4) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists ca");
        } catch (Exception unused5) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists bi");
        } catch (Exception unused6) {
        }
        try {
            sQLiteDatabase.execSQL("drop table if exists st");
        } catch (Exception unused7) {
        }
        onCreate(sQLiteDatabase);
    }
}
