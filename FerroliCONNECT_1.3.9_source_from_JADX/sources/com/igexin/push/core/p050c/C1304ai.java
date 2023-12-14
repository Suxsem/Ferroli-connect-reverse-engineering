package com.igexin.push.core.p050c;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.bean.C1287j;
import com.igexin.push.core.p051d.C1339h;
import com.igexin.push.p088g.p089a.C1564c;
import java.util.ArrayList;
import java.util.Iterator;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.core.c.ai */
public class C1304ai implements C1295a {

    /* renamed from: a */
    private static final String f2027a = "com.igexin.push.core.c.ai";

    /* renamed from: b */
    private static int f2028b = 200;

    /* renamed from: c */
    private static int f2029c = 50;

    /* renamed from: f */
    private static C1304ai f2030f;

    /* renamed from: d */
    private int f2031d;

    /* renamed from: e */
    private ArrayList<C1287j> f2032e = null;

    private C1304ai() {
    }

    /* renamed from: a */
    static /* synthetic */ int m1895a(C1304ai aiVar) {
        int i = aiVar.f2031d;
        aiVar.f2031d = i + 1;
        return i;
    }

    /* renamed from: a */
    public static C1304ai m1896a() {
        if (f2030f == null) {
            f2030f = new C1304ai();
        }
        return f2030f;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0062, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0086, code lost:
        if (r1 == null) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0089, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0060, code lost:
        if (r1 != null) goto L_0x0062;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.ArrayList<com.igexin.push.core.bean.C1287j> mo14646a(java.lang.String r11) {
        /*
            r10 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            com.igexin.push.core.e r2 = com.igexin.push.core.C1340e.m2032a()     // Catch:{ Throwable -> 0x0068 }
            com.igexin.push.b.b r3 = r2.mo14712i()     // Catch:{ Throwable -> 0x0068 }
            java.lang.String r4 = "st"
            java.lang.String r2 = "type"
            java.lang.String[] r5 = new java.lang.String[]{r2}     // Catch:{ Throwable -> 0x0068 }
            r2 = 1
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Throwable -> 0x0068 }
            r9 = 0
            r6[r9] = r11     // Catch:{ Throwable -> 0x0068 }
            r7 = 0
            r8 = 0
            android.database.Cursor r1 = r3.mo14355a(r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x0068 }
            if (r1 == 0) goto L_0x0060
            int r11 = r1.getCount()     // Catch:{ Throwable -> 0x0068 }
            if (r11 <= 0) goto L_0x0060
        L_0x002a:
            boolean r11 = r1.moveToNext()     // Catch:{ Throwable -> 0x0068 }
            if (r11 == 0) goto L_0x0060
            r11 = 2
            byte[] r11 = r1.getBlob(r11)     // Catch:{ Throwable -> 0x0068 }
            byte[] r11 = com.igexin.p032b.p042b.C1196a.m1439c(r11)     // Catch:{ Throwable -> 0x0068 }
            com.igexin.push.core.bean.j r3 = new com.igexin.push.core.bean.j     // Catch:{ Throwable -> 0x0068 }
            r3.<init>()     // Catch:{ Throwable -> 0x0068 }
            int r4 = r1.getInt(r9)     // Catch:{ Throwable -> 0x0068 }
            r3.mo14600a((int) r4)     // Catch:{ Throwable -> 0x0068 }
            int r4 = r1.getInt(r2)     // Catch:{ Throwable -> 0x0068 }
            r3.mo14604b(r4)     // Catch:{ Throwable -> 0x0068 }
            java.lang.String r4 = new java.lang.String     // Catch:{ Throwable -> 0x0068 }
            r4.<init>(r11)     // Catch:{ Throwable -> 0x0068 }
            r3.mo14602a((java.lang.String) r4)     // Catch:{ Throwable -> 0x0068 }
            r11 = 3
            long r4 = r1.getLong(r11)     // Catch:{ Throwable -> 0x0068 }
            r3.mo14601a((long) r4)     // Catch:{ Throwable -> 0x0068 }
            r0.add(r3)     // Catch:{ Throwable -> 0x0068 }
            goto L_0x002a
        L_0x0060:
            if (r1 == 0) goto L_0x0089
        L_0x0062:
            r1.close()
            goto L_0x0089
        L_0x0066:
            r11 = move-exception
            goto L_0x008a
        L_0x0068:
            r11 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0066 }
            r2.<init>()     // Catch:{ all -> 0x0066 }
            java.lang.String r3 = f2027a     // Catch:{ all -> 0x0066 }
            r2.append(r3)     // Catch:{ all -> 0x0066 }
            java.lang.String r3 = "|getThirdGuardData exception:"
            r2.append(r3)     // Catch:{ all -> 0x0066 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0066 }
            r2.append(r11)     // Catch:{ all -> 0x0066 }
            java.lang.String r11 = r2.toString()     // Catch:{ all -> 0x0066 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)     // Catch:{ all -> 0x0066 }
            if (r1 == 0) goto L_0x0089
            goto L_0x0062
        L_0x0089:
            return r0
        L_0x008a:
            if (r1 == 0) goto L_0x008f
            r1.close()
        L_0x008f:
            goto L_0x0091
        L_0x0090:
            throw r11
        L_0x0091:
            goto L_0x0090
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1304ai.mo14646a(java.lang.String):java.util.ArrayList");
    }

    /* renamed from: a */
    public void mo14442a(SQLiteDatabase sQLiteDatabase) {
        Cursor cursor = null;
        try {
            cursor = C1340e.m2032a().mo14712i().mo14355a("st", (String[]) null, (String[]) null, (String[]) null, (String) null);
            if (cursor != null) {
                this.f2031d = cursor.getCount();
            }
            if (cursor == null) {
                return;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        cursor.close();
    }

    /* renamed from: a */
    public void mo14647a(String str, String str2) {
        if (this.f2031d >= f2028b) {
            C1179b.m1354a(f2027a + "|rowCount >= 200 can not insert");
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", str);
        contentValues.put("value", C1196a.m1438b(str2.getBytes()));
        contentValues.put(AgooConstants.MESSAGE_TIME, Long.valueOf(System.currentTimeMillis()));
        C1174c.m1310b().mo14317a(new C1305aj(this, contentValues), false, true);
    }

    /* renamed from: a */
    public void mo14648a(String str, ArrayList<C1287j> arrayList) {
        try {
            String[] strArr = new String[arrayList.size()];
            int i = 0;
            Iterator<C1287j> it = arrayList.iterator();
            while (it.hasNext()) {
                C1287j next = it.next();
                strArr[i] = String.valueOf(next.mo14599a());
                this.f2032e.remove(next);
                i++;
            }
            C1340e.m2032a().mo14712i().mo14357a("st", new String[]{AgooConstants.MESSAGE_ID}, strArr);
            if (this.f2032e.size() > 0) {
                mo14650c(str);
            }
        } catch (Throwable th) {
            C1179b.m1354a(f2027a + "|onReportResult exception:" + th.toString());
        }
    }

    /* renamed from: b */
    public void mo14445b(SQLiteDatabase sQLiteDatabase) {
    }

    /* renamed from: b */
    public void mo14649b(String str) {
        this.f2032e = mo14646a(str);
        mo14650c(str);
    }

    /* renamed from: c */
    public void mo14448c(SQLiteDatabase sQLiteDatabase) {
    }

    /* renamed from: c */
    public void mo14650c(String str) {
        try {
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            Iterator<C1287j> it = this.f2032e.iterator();
            while (it.hasNext()) {
                C1287j next = it.next();
                if (arrayList.size() >= f2029c) {
                    break;
                }
                arrayList.add(next);
                sb.append(next.mo14603b());
                sb.append("\n");
            }
            String sb2 = sb.toString();
            if (!TextUtils.isEmpty(sb2)) {
                C1174c.m1310b().mo14317a(new C1564c(new C1339h(sb2.getBytes(), str, arrayList)), false, true);
            }
        } catch (Throwable th) {
            C1179b.m1354a(f2027a + "|doSTReport exception:" + th.toString());
        }
    }
}
