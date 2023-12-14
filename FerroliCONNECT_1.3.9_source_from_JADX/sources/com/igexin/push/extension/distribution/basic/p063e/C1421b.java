package com.igexin.push.extension.distribution.basic.p063e;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: com.igexin.push.extension.distribution.basic.e.b */
public class C1421b extends SQLiteOpenHelper {

    /* renamed from: a */
    SQLiteDatabase f2456a = null;

    /* renamed from: b */
    private boolean f2457b = true;

    public C1421b(Context context) {
        super(context, "pushsdk.db", (SQLiteDatabase.CursorFactory) null, 3);
    }

    /* renamed from: a */
    private String m2446a(String str, String str2) {
        return "delete from " + str + " where " + str2;
    }

    /* renamed from: a */
    private String m2447a(String[] strArr, String[] strArr2, int i) {
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
    public void mo14968a(String str, String str2, ContentValues contentValues) {
        this.f2456a = getWritableDatabase();
        try {
            this.f2456a.replace(str, str2, contentValues);
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    public void mo14969a(String str, String[] strArr, String[] strArr2) {
        SQLiteDatabase sQLiteDatabase;
        String a;
        this.f2456a = getWritableDatabase();
        this.f2456a.beginTransaction();
        if (strArr == null) {
            try {
                this.f2456a.delete(str, (String) null, (String[]) null);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f2456a.endTransaction();
                throw th;
            }
        } else {
            if (strArr.length != 1) {
                sQLiteDatabase = this.f2456a;
                a = m2446a(str, m2447a(strArr, strArr2, strArr.length));
            } else if (strArr2.length == 1) {
                SQLiteDatabase sQLiteDatabase2 = this.f2456a;
                sQLiteDatabase2.delete(str, strArr[0] + " = ?", strArr2);
            } else {
                sQLiteDatabase = this.f2456a;
                a = m2446a(str, m2447a(strArr, strArr2, strArr2.length));
            }
            sQLiteDatabase.execSQL(a);
        }
        this.f2456a.setTransactionSuccessful();
        this.f2456a.endTransaction();
    }

    /* renamed from: a */
    public boolean mo14970a() {
        return this.f2457b;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.f2457b = false;
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        this.f2457b = false;
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        this.f2457b = false;
    }
}
