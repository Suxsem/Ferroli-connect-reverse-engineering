package com.igexin.push.core.p050c;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.core.bean.C1286i;
import com.taobao.accs.common.Constants;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.core.c.d */
public class C1308d implements C1295a {

    /* renamed from: a */
    private static C1308d f2040a;

    /* renamed from: b */
    private List<C1286i> f2041b = new CopyOnWriteArrayList();

    private C1308d() {
    }

    /* renamed from: a */
    private int m1922a(byte b) {
        int i = 0;
        for (C1286i c : this.f2041b) {
            if (c.mo14597c() == b) {
                i++;
            }
        }
        return i;
    }

    /* renamed from: a */
    private C1286i m1923a(long j) {
        for (C1286i next : this.f2041b) {
            if (next.mo14594a() == j) {
                return next;
            }
        }
        return null;
    }

    /* renamed from: a */
    public static C1308d m1924a() {
        if (f2040a == null) {
            f2040a = new C1308d();
        }
        return f2040a;
    }

    /* renamed from: b */
    private static ContentValues m1925b(C1286i iVar) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AgooConstants.MESSAGE_ID, Long.valueOf(iVar.mo14594a()));
        contentValues.put(Constants.KEY_DATA, C1196a.m1438b(iVar.mo14596b().getBytes()));
        contentValues.put("type", Byte.valueOf(iVar.mo14597c()));
        contentValues.put(AgooConstants.MESSAGE_TIME, Long.valueOf(iVar.mo14598d()));
        return contentValues;
    }

    /* renamed from: b */
    private void m1926b(byte b) {
        C1286i iVar = null;
        try {
            Iterator<C1286i> it = this.f2041b.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                C1286i next = it.next();
                if (next.mo14597c() == b) {
                    iVar = next;
                    break;
                }
            }
            mo14661a(iVar.mo14594a(), true, true);
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public void mo14661a(long j, boolean z, boolean z2) {
        C1286i a = m1923a(j);
        if (a != null) {
            this.f2041b.remove(a);
        }
        if (z) {
            C1174c.m1310b().mo14317a(new C1310f(this, m1925b(a), j), z2, !z2);
        }
    }

    /* renamed from: a */
    public void mo14442a(SQLiteDatabase sQLiteDatabase) {
    }

    /* renamed from: a */
    public void mo14662a(C1286i iVar) {
        if (iVar == null) {
            return;
        }
        if (this.f2041b.size() < 108 || iVar.mo14597c() == 2 || iVar.mo14597c() == 7) {
            byte c = iVar.mo14597c();
            if (c != 2) {
                if (c != 3) {
                    if (c != 5) {
                        if (c != 6) {
                            if (c != 7) {
                                if (c == 8 && m1922a((byte) 8) >= 3) {
                                    return;
                                }
                            }
                        } else if (m1922a((byte) 6) >= 10) {
                            return;
                        }
                    } else if (m1922a((byte) 5) >= 3) {
                        return;
                    }
                } else if (m1922a((byte) 3) >= 90) {
                    return;
                }
                this.f2041b.add(iVar);
                C1174c.m1310b().mo14317a(new C1309e(this, m1925b(iVar)), false, true);
            }
            m1926b(iVar.mo14597c());
            this.f2041b.add(iVar);
            C1174c.m1310b().mo14317a(new C1309e(this, m1925b(iVar)), false, true);
        }
    }

    /* renamed from: a */
    public boolean mo14663a(long j, long j2) {
        C1286i a = m1923a(j);
        if (a == null) {
            return false;
        }
        a.mo14595a(j2);
        C1174c.m1310b().mo14317a(new C1311g(this, m1925b(a), j), true, true);
        return true;
    }

    /* renamed from: b */
    public List<C1286i> mo14664b() {
        return this.f2041b;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String[], android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14445b(android.database.sqlite.SQLiteDatabase r15) {
        /*
            r14 = this;
            r0 = 0
            java.lang.String r1 = "select id,data,type,time from ral"
            android.database.Cursor r0 = r15.rawQuery(r1, r0)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            if (r0 == 0) goto L_0x004a
        L_0x000d:
            boolean r15 = r0.moveToNext()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            if (r15 == 0) goto L_0x004a
            r15 = 0
            long r4 = r0.getLong(r15)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r15 = 2
            int r15 = r0.getInt(r15)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            byte r7 = (byte) r15     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r15 = 3
            long r8 = r0.getLong(r15)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            long r10 = r1 - r8
            r12 = 259200000(0xf731400, double:1.280618154E-315)
            r15 = 1
            int r3 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r3 <= 0) goto L_0x0031
            r14.mo14661a(r4, r15, r15)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            goto L_0x000d
        L_0x0031:
            java.util.List<com.igexin.push.core.bean.i> r10 = r14.f2041b     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            com.igexin.push.core.bean.i r11 = new com.igexin.push.core.bean.i     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r6 = new java.lang.String     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            byte[] r15 = r0.getBlob(r15)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            byte[] r15 = com.igexin.p032b.p042b.C1196a.m1439c(r15)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r6.<init>(r15)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r3 = r11
            r3.<init>(r4, r6, r7, r8)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r10.add(r11)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            goto L_0x000d
        L_0x004a:
            if (r0 == 0) goto L_0x005a
            goto L_0x0057
        L_0x004d:
            r15 = move-exception
            if (r0 == 0) goto L_0x0053
            r0.close()
        L_0x0053:
            throw r15
        L_0x0054:
            if (r0 == 0) goto L_0x005a
        L_0x0057:
            r0.close()
        L_0x005a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1308d.mo14445b(android.database.sqlite.SQLiteDatabase):void");
    }

    /* renamed from: c */
    public void mo14448c(SQLiteDatabase sQLiteDatabase) {
    }
}
