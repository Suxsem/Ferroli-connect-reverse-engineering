package com.taobao.accs.p101a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.taobao.accs.a.a */
/* compiled from: Taobao */
public class C2006a extends SQLiteOpenHelper {

    /* renamed from: c */
    private static volatile C2006a f3211c;

    /* renamed from: e */
    private static final Lock f3212e = new ReentrantLock();

    /* renamed from: a */
    public int f3213a = 0;

    /* renamed from: b */
    LinkedList<C2007a> f3214b = new LinkedList<>();

    /* renamed from: d */
    private Context f3215d;

    public SQLiteDatabase getWritableDatabase() {
        if (!AdapterUtilityImpl.checkIsWritable(super.getWritableDatabase().getPath(), 102400)) {
            return null;
        }
        return super.getWritableDatabase();
    }

    /* renamed from: a */
    public static C2006a m3427a(Context context) {
        if (f3211c == null) {
            synchronized (C2006a.class) {
                if (f3211c == null) {
                    f3211c = new C2006a(context, Constants.DB_NAME, (SQLiteDatabase.CursorFactory) null, 3);
                }
            }
        }
        return f3211c;
    }

    private C2006a(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
        this.f3215d = context;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            if (f3212e.tryLock()) {
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS traffic(_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, host TEXT,serviceid TEXT, bid TEXT, isbackground TEXT, size TEXT)");
            }
        } finally {
            f3212e.unlock();
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < i2) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS service");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS network");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ping");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS msg");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ack");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS election");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS bindApp");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS bindUser");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS traffic");
            onCreate(sQLiteDatabase);
        }
    }

    /* renamed from: a */
    public void mo18319a(String str, String str2, String str3, boolean z, long j, String str4) {
        if (!m3429a(str, str3, z, str4)) {
            m3428a("INSERT INTO traffic VALUES(null,?,?,?,?,?,?)", new Object[]{str4, str, str2, str3, String.valueOf(z), Long.valueOf(j)}, true);
            return;
        }
        m3428a("UPDATE traffic SET size=? WHERE date=? AND host=? AND bid=? AND isbackground=?", new Object[]{Long.valueOf(j), str4, str, str3, String.valueOf(z)}, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004e, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004f, code lost:
        if (r0 != null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0063, code lost:
        if (r0 == null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0067, code lost:
        return false;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean m3429a(java.lang.String r12, java.lang.String r13, boolean r14, java.lang.String r15) {
        /*
            r11 = this;
            monitor-enter(r11)
            r0 = 0
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r11.getWritableDatabase()     // Catch:{ Exception -> 0x0057 }
            if (r2 != 0) goto L_0x000b
            monitor-exit(r11)
            return r1
        L_0x000b:
            java.lang.String r3 = "traffic"
            java.lang.String r4 = "_id"
            java.lang.String r5 = "date"
            java.lang.String r6 = "host"
            java.lang.String r7 = "serviceid"
            java.lang.String r8 = "bid"
            java.lang.String r9 = "isbackground"
            java.lang.String r10 = "size"
            java.lang.String[] r4 = new java.lang.String[]{r4, r5, r6, r7, r8, r9, r10}     // Catch:{ Exception -> 0x0057 }
            java.lang.String r5 = "date=? AND host=? AND bid=? AND isbackground=?"
            r6 = 4
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ Exception -> 0x0057 }
            r6[r1] = r15     // Catch:{ Exception -> 0x0057 }
            r15 = 1
            r6[r15] = r12     // Catch:{ Exception -> 0x0057 }
            r12 = 2
            r6[r12] = r13     // Catch:{ Exception -> 0x0057 }
            r12 = 3
            java.lang.String r13 = java.lang.String.valueOf(r14)     // Catch:{ Exception -> 0x0057 }
            r6[r12] = r13     // Catch:{ Exception -> 0x0057 }
            r7 = 0
            r8 = 0
            r9 = 0
            r12 = 100
            java.lang.String r10 = java.lang.String.valueOf(r12)     // Catch:{ Exception -> 0x0057 }
            android.database.Cursor r0 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x0057 }
            if (r0 == 0) goto L_0x004f
            int r12 = r0.getCount()     // Catch:{ Exception -> 0x0057 }
            if (r12 <= 0) goto L_0x004f
            if (r0 == 0) goto L_0x004d
            r0.close()     // Catch:{ all -> 0x006e }
        L_0x004d:
            monitor-exit(r11)
            return r15
        L_0x004f:
            if (r0 == 0) goto L_0x0066
        L_0x0051:
            r0.close()     // Catch:{ all -> 0x006e }
            goto L_0x0066
        L_0x0055:
            r12 = move-exception
            goto L_0x0068
        L_0x0057:
            r12 = move-exception
            java.lang.String r13 = "DBHelper"
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0055 }
            java.lang.Object[] r14 = new java.lang.Object[r1]     // Catch:{ all -> 0x0055 }
            com.taobao.accs.utl.ALog.m3731w(r13, r12, r14)     // Catch:{ all -> 0x0055 }
            if (r0 == 0) goto L_0x0066
            goto L_0x0051
        L_0x0066:
            monitor-exit(r11)
            return r1
        L_0x0068:
            if (r0 == 0) goto L_0x006d
            r0.close()     // Catch:{ all -> 0x006e }
        L_0x006d:
            throw r12     // Catch:{ all -> 0x006e }
        L_0x006e:
            r12 = move-exception
            monitor-exit(r11)
            goto L_0x0072
        L_0x0071:
            throw r12
        L_0x0072:
            goto L_0x0071
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.p101a.C2006a.m3429a(java.lang.String, java.lang.String, boolean, java.lang.String):boolean");
    }

    /* renamed from: a */
    public void mo18318a() {
        m3428a("DELETE FROM traffic", (Object[]) null, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006e, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d0 A[SYNTHETIC, Splitter:B:45:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d7  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.taobao.accs.p103ut.monitor.TrafficsMonitor.C2082a> mo18317a(boolean r15) {
        /*
            r14 = this;
            monitor-enter(r14)
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x00db }
            r0.<init>()     // Catch:{ all -> 0x00db }
            r1 = 0
            r2 = 0
            android.database.sqlite.SQLiteDatabase r3 = r14.getWritableDatabase()     // Catch:{ Exception -> 0x00c2 }
            if (r3 != 0) goto L_0x0010
            monitor-exit(r14)     // Catch:{ all -> 0x00db }
            return r2
        L_0x0010:
            r12 = 1
            r4 = 100
            if (r15 == 0) goto L_0x0044
            java.lang.String r15 = "traffic"
            java.lang.String r5 = "_id"
            java.lang.String r6 = "date"
            java.lang.String r7 = "host"
            java.lang.String r8 = "serviceid"
            java.lang.String r9 = "bid"
            java.lang.String r10 = "isbackground"
            java.lang.String r11 = "size"
            java.lang.String[] r5 = new java.lang.String[]{r5, r6, r7, r8, r9, r10, r11}     // Catch:{ Exception -> 0x00c2 }
            java.lang.String r6 = "date=?"
            java.lang.String[] r7 = new java.lang.String[r12]     // Catch:{ Exception -> 0x00c2 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00c2 }
            java.lang.String r8 = com.taobao.accs.utl.UtilityImpl.m3736a((long) r8)     // Catch:{ Exception -> 0x00c2 }
            r7[r1] = r8     // Catch:{ Exception -> 0x00c2 }
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r11 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x00c2 }
            r4 = r15
            android.database.Cursor r15 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00c2 }
            goto L_0x0066
        L_0x0044:
            java.lang.String r15 = "traffic"
            java.lang.String r5 = "_id"
            java.lang.String r6 = "date"
            java.lang.String r7 = "host"
            java.lang.String r8 = "serviceid"
            java.lang.String r9 = "bid"
            java.lang.String r10 = "isbackground"
            java.lang.String r11 = "size"
            java.lang.String[] r5 = new java.lang.String[]{r5, r6, r7, r8, r9, r10, r11}     // Catch:{ Exception -> 0x00c2 }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r11 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x00c2 }
            r4 = r15
            android.database.Cursor r15 = r3.query(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x00c2 }
        L_0x0066:
            if (r15 != 0) goto L_0x006f
            if (r15 == 0) goto L_0x006d
            r15.close()     // Catch:{ all -> 0x00db }
        L_0x006d:
            monitor-exit(r14)     // Catch:{ all -> 0x00db }
            return r2
        L_0x006f:
            boolean r2 = r15.moveToFirst()     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            if (r2 == 0) goto L_0x00b1
        L_0x0075:
            java.lang.String r4 = r15.getString(r12)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            r2 = 2
            java.lang.String r8 = r15.getString(r2)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            r2 = 3
            java.lang.String r6 = r15.getString(r2)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            r2 = 4
            java.lang.String r5 = r15.getString(r2)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            r2 = 5
            java.lang.String r2 = r15.getString(r2)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            boolean r7 = r2.booleanValue()     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            r2 = 6
            long r9 = r15.getLong(r2)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            if (r5 == 0) goto L_0x00ab
            r2 = 0
            int r11 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r11 <= 0) goto L_0x00ab
            com.taobao.accs.ut.monitor.TrafficsMonitor$a r2 = new com.taobao.accs.ut.monitor.TrafficsMonitor$a     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            r3 = r2
            r3.<init>(r4, r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            r0.add(r2)     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
        L_0x00ab:
            boolean r2 = r15.moveToNext()     // Catch:{ Exception -> 0x00bb, all -> 0x00b7 }
            if (r2 != 0) goto L_0x0075
        L_0x00b1:
            if (r15 == 0) goto L_0x00d3
            r15.close()     // Catch:{ all -> 0x00db }
            goto L_0x00d3
        L_0x00b7:
            r0 = move-exception
            r2 = r15
            r15 = r0
            goto L_0x00d5
        L_0x00bb:
            r2 = move-exception
            r13 = r2
            r2 = r15
            r15 = r13
            goto L_0x00c3
        L_0x00c0:
            r15 = move-exception
            goto L_0x00d5
        L_0x00c2:
            r15 = move-exception
        L_0x00c3:
            java.lang.String r3 = "DBHelper"
            java.lang.String r15 = r15.toString()     // Catch:{ all -> 0x00c0 }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00c0 }
            com.taobao.accs.utl.ALog.m3731w(r3, r15, r1)     // Catch:{ all -> 0x00c0 }
            if (r2 == 0) goto L_0x00d3
            r2.close()     // Catch:{ all -> 0x00db }
        L_0x00d3:
            monitor-exit(r14)     // Catch:{ all -> 0x00db }
            return r0
        L_0x00d5:
            if (r2 == 0) goto L_0x00da
            r2.close()     // Catch:{ all -> 0x00db }
        L_0x00da:
            throw r15     // Catch:{ all -> 0x00db }
        L_0x00db:
            r15 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x00db }
            goto L_0x00df
        L_0x00de:
            throw r15
        L_0x00df:
            goto L_0x00de
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.p101a.C2006a.mo18317a(boolean):java.util.List");
    }

    /* renamed from: a */
    private synchronized void m3428a(String str, Object[] objArr, boolean z) {
        this.f3214b.add(new C2007a(str, objArr));
        if (this.f3214b.size() > 5 || z) {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase != null) {
                while (true) {
                    try {
                        if (this.f3214b.size() > 0) {
                            C2007a removeFirst = this.f3214b.removeFirst();
                            if (removeFirst.f3217b != null) {
                                writableDatabase.execSQL(removeFirst.f3216a, removeFirst.f3217b);
                            } else {
                                writableDatabase.execSQL(removeFirst.f3216a);
                            }
                            if (removeFirst.f3216a.contains("INSERT")) {
                                this.f3213a++;
                                if (this.f3213a > 4000) {
                                    ALog.m3725d("DBHelper", "db is full!", new Object[0]);
                                    onUpgrade(writableDatabase, 0, 1);
                                    this.f3213a = 0;
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        ALog.m3725d("DBHelper", e.toString(), new Object[0]);
                    } catch (Throwable th) {
                        writableDatabase.close();
                        throw th;
                    }
                }
                writableDatabase.close();
            } else {
                return;
            }
        }
        return;
    }

    /* renamed from: com.taobao.accs.a.a$a */
    /* compiled from: Taobao */
    private class C2007a {

        /* renamed from: a */
        String f3216a;

        /* renamed from: b */
        Object[] f3217b;

        private C2007a(String str, Object[] objArr) {
            this.f3216a = str;
            this.f3217b = objArr;
        }
    }
}
