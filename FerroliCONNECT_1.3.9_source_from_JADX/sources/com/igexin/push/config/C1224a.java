package com.igexin.push.config;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.push.core.p050c.C1295a;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.config.a */
public class C1224a implements C1295a {

    /* renamed from: a */
    public static final String f1806a = "com.igexin.push.config.a";

    /* renamed from: b */
    private static C1224a f1807b;

    /* renamed from: a */
    public static C1224a m1601a() {
        if (f1807b == null) {
            f1807b = new C1224a();
        }
        return f1807b;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m1602a(SQLiteDatabase sQLiteDatabase, int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AgooConstants.MESSAGE_ID, Integer.valueOf(i));
        contentValues.put("value", str);
        sQLiteDatabase.replace("config", (String) null, contentValues);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m1603a(SQLiteDatabase sQLiteDatabase, int i, byte[] bArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AgooConstants.MESSAGE_ID, Integer.valueOf(i));
        contentValues.put("value", bArr);
        sQLiteDatabase.replace("config", (String) null, contentValues);
    }

    /* renamed from: a */
    public void mo14442a(SQLiteDatabase sQLiteDatabase) {
    }

    /* renamed from: a */
    public void mo14443a(String str) {
        C1174c.m1310b().mo14317a(new C1230g(this, str), true, false);
    }

    /* renamed from: b */
    public void mo14444b() {
        C1174c.m1310b().mo14317a(new C1225b(this), false, true);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:7|8|(3:26|(1:28)|29)(2:24|25)|(3:32|(3:(1:(1:36)(2:150|245))(1:151)|66|190)(2:152|(2:154|247)(1:246))|172)(2:174|172)|5|4) */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0352, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0353, code lost:
        r1 = f1806a + "|" + r1.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x036f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0373, code lost:
        if (r11 != null) goto L_0x0383;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x037c, code lost:
        r11.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x0381, code lost:
        if (r11 != null) goto L_0x0383;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x0383, code lost:
        r11.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0386, code lost:
        com.igexin.p032b.p033a.p039c.C1179b.m1354a(f1806a + "|current ver = " + com.igexin.sdk.PushBuildConfig.sdk_conf_version + ", last ver = " + com.igexin.push.core.C1343f.f2120L);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x03b1, code lost:
        if (com.igexin.sdk.PushBuildConfig.sdk_conf_version.equals(com.igexin.push.core.C1343f.f2120L) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x03b3, code lost:
        com.igexin.push.core.p050c.C1312h.m1937a().mo14689f(com.igexin.sdk.PushBuildConfig.sdk_conf_version);
        com.igexin.push.core.p050c.C1312h.m1937a().mo14686e(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:249:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x007e, code lost:
        if (r4.equals("null") != false) goto L_0x00b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0080, code lost:
        com.igexin.push.config.C1234k.f1839U = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ba, code lost:
        if (r4.equals("null") != false) goto L_0x0009;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00bc, code lost:
        com.igexin.push.config.C1234k.f1837S = java.lang.Boolean.valueOf(r4).booleanValue();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0009 */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x036f A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x0009] */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x037c  */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x0009 A[LOOP:0: B:4:0x0009->B:172:0x0009, LOOP_START, SYNTHETIC, Splitter:B:4:0x0009] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14445b(android.database.sqlite.SQLiteDatabase r11) {
        /*
            r10 = this;
            r0 = 0
            java.lang.String r1 = "select id, value from config order by id"
            android.database.Cursor r11 = r11.rawQuery(r1, r0)     // Catch:{ Throwable -> 0x0380, all -> 0x0376 }
            if (r11 == 0) goto L_0x0373
        L_0x0009:
            boolean r1 = r11.moveToNext()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 == 0) goto L_0x0373
            r1 = 1
            r2 = 0
            int r3 = r11.getInt(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r4 = 20
            r5 = 58
            r6 = 56
            if (r3 == r4) goto L_0x0041
            r4 = 21
            if (r3 == r4) goto L_0x0041
            r4 = 22
            if (r3 == r4) goto L_0x0041
            r4 = 24
            if (r3 == r4) goto L_0x0041
            r4 = 26
            if (r3 == r4) goto L_0x0041
            r4 = 45
            if (r3 == r4) goto L_0x0041
            r4 = 51
            if (r3 == r4) goto L_0x0041
            if (r3 == r6) goto L_0x0041
            if (r3 != r5) goto L_0x003a
            goto L_0x0041
        L_0x003a:
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Throwable -> 0x0352, all -> 0x036f }
            r4 = r1
            r1 = r0
            goto L_0x004e
        L_0x0041:
            byte[] r1 = r11.getBlob(r1)     // Catch:{ Throwable -> 0x0352, all -> 0x036f }
            if (r1 == 0) goto L_0x004d
            java.lang.String r4 = com.igexin.push.core.C1343f.f2110B     // Catch:{ Throwable -> 0x0352, all -> 0x036f }
            byte[] r1 = com.igexin.p032b.p033a.p034a.C1150a.m1234c(r1, r4)     // Catch:{ Throwable -> 0x0352, all -> 0x036f }
        L_0x004d:
            r4 = r0
        L_0x004e:
            if (r1 != 0) goto L_0x0053
            if (r4 != 0) goto L_0x0053
            goto L_0x0009
        L_0x0053:
            r7 = 55
            java.lang.String r8 = "null"
            if (r3 == r7) goto L_0x0340
            if (r3 == r6) goto L_0x031f
            if (r3 == r5) goto L_0x0316
            switch(r3) {
                case 1: goto L_0x0304;
                case 2: goto L_0x02f2;
                case 3: goto L_0x02e0;
                case 4: goto L_0x02ce;
                case 5: goto L_0x0009;
                case 6: goto L_0x02bc;
                case 7: goto L_0x02aa;
                case 8: goto L_0x0298;
                case 9: goto L_0x0286;
                default: goto L_0x0060;
            }
        L_0x0060:
            switch(r3) {
                case 13: goto L_0x0274;
                case 14: goto L_0x0262;
                case 15: goto L_0x0250;
                case 16: goto L_0x023e;
                case 17: goto L_0x022c;
                case 18: goto L_0x021a;
                case 19: goto L_0x0208;
                case 20: goto L_0x0009;
                case 21: goto L_0x01ff;
                case 22: goto L_0x01f6;
                case 23: goto L_0x01e4;
                case 24: goto L_0x01da;
                case 25: goto L_0x01c8;
                case 26: goto L_0x01b5;
                case 27: goto L_0x01a3;
                case 28: goto L_0x0199;
                case 29: goto L_0x0187;
                case 30: goto L_0x017d;
                default: goto L_0x0063;
            }
        L_0x0063:
            switch(r3) {
                case 40: goto L_0x016b;
                case 41: goto L_0x0158;
                case 42: goto L_0x0146;
                case 43: goto L_0x0134;
                default: goto L_0x0066;
            }
        L_0x0066:
            switch(r3) {
                case 45: goto L_0x0110;
                case 46: goto L_0x00fe;
                case 47: goto L_0x00ec;
                case 48: goto L_0x00da;
                case 49: goto L_0x00c8;
                case 50: goto L_0x00b6;
                case 51: goto L_0x0083;
                case 52: goto L_0x006a;
                case 53: goto L_0x007a;
                default: goto L_0x0069;
            }
        L_0x0069:
            goto L_0x0009
        L_0x006a:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x007a
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1838T = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
        L_0x007a:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x00b6
            com.igexin.push.config.C1234k.f1839U = r4     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x00b6
        L_0x0083:
            java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1829K = r2     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.<init>()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = f1806a     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = "|read from db IDBlackList ="
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = com.igexin.push.config.C1234k.f1829K     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r1 = com.igexin.push.config.C1234k.f1829K     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            com.igexin.push.core.r r1 = com.igexin.push.core.C1355r.m2114a()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.mo14775d()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x00b6:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1837S = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x00c8:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1836R = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x00da:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1835Q = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x00ec:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1834P = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x00fe:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1833O = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0110:
            java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1828J = r2     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.<init>()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = f1806a     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = "|read from db hideRightIconBlackList = "
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = com.igexin.push.config.C1234k.f1828J     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
        L_0x012f:
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0134:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1827I = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0146:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1826H = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0158:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            long r1 = (long) r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1825G = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x016b:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1824F = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x017d:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            com.igexin.push.config.C1234k.f1823E = r4     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0187:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1822D = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0199:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            com.igexin.push.config.C1234k.f1821C = r4     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x01a3:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1820B = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x01b5:
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x0009 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0009 }
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch:{ Exception -> 0x0009 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0009 }
            java.lang.String[] r1 = com.igexin.push.core.p047a.C1269r.m1770a(r1)     // Catch:{ Exception -> 0x0009 }
            com.igexin.push.config.SDKUrlConfig.setIdcConfigUrl(r1)     // Catch:{ Exception -> 0x0009 }
            goto L_0x0009
        L_0x01c8:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1860u = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x01da:
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x0009 }
            r3.<init>(r1)     // Catch:{ Exception -> 0x0009 }
            com.igexin.push.config.C1236m.m1628a((java.lang.String) r3, (boolean) r2)     // Catch:{ Exception -> 0x0009 }
            goto L_0x0009
        L_0x01e4:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1858s = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x01f6:
            java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1856q = r2     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x01ff:
            java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1855p = r2     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0208:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1854o = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x021a:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1853n = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x022c:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1852m = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x023e:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1844e = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0250:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1843d = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0262:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1851l = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0274:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1850k = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0286:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1849j = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0298:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1848i = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x02aa:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1847h = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x02bc:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1846g = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x02ce:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1845f = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x02e0:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Long r1 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            long r1 = r1.longValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1842c = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x02f2:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1841b = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0304:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1840a = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0316:
            java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1832N = r2     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x031f:
            java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1831M = r2     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.<init>()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = f1806a     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = "|read from db applinkDomains ="
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r2 = com.igexin.push.config.C1234k.f1831M     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r1.append(r2)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x012f
        L_0x0340:
            boolean r1 = r4.equals(r8)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            if (r1 != 0) goto L_0x0009
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            com.igexin.push.config.C1234k.f1830L = r1     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x0009
        L_0x0352:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.<init>()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r3 = f1806a     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.append(r3)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r3 = "|"
            r2.append(r3)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            r2.append(r1)     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            java.lang.String r1 = r2.toString()     // Catch:{ Throwable -> 0x0371, all -> 0x036f }
            goto L_0x012f
        L_0x036f:
            r0 = move-exception
            goto L_0x037a
        L_0x0371:
            goto L_0x0381
        L_0x0373:
            if (r11 == 0) goto L_0x0386
            goto L_0x0383
        L_0x0376:
            r11 = move-exception
            r9 = r0
            r0 = r11
            r11 = r9
        L_0x037a:
            if (r11 == 0) goto L_0x037f
            r11.close()
        L_0x037f:
            throw r0
        L_0x0380:
            r11 = r0
        L_0x0381:
            if (r11 == 0) goto L_0x0386
        L_0x0383:
            r11.close()
        L_0x0386:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r0 = f1806a
            r11.append(r0)
            java.lang.String r0 = "|current ver = "
            r11.append(r0)
            java.lang.String r0 = "4.3.8.0"
            r11.append(r0)
            java.lang.String r1 = ", last ver = "
            r11.append(r1)
            java.lang.String r1 = com.igexin.push.core.C1343f.f2120L
            r11.append(r1)
            java.lang.String r11 = r11.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)
            java.lang.String r11 = com.igexin.push.core.C1343f.f2120L
            boolean r11 = r0.equals(r11)
            if (r11 != 0) goto L_0x03c3
            com.igexin.push.core.c.h r11 = com.igexin.push.core.p050c.C1312h.m1937a()
            r11.mo14689f((java.lang.String) r0)
            com.igexin.push.core.c.h r11 = com.igexin.push.core.p050c.C1312h.m1937a()
            r0 = 0
            r11.mo14686e((long) r0)
        L_0x03c3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.config.C1224a.mo14445b(android.database.sqlite.SQLiteDatabase):void");
    }

    /* renamed from: b */
    public void mo14446b(String str) {
        C1174c.m1310b().mo14317a(new C1231h(this, str), true, false);
    }

    /* renamed from: c */
    public void mo14447c() {
        C1174c.m1310b().mo14317a(new C1226c(this), false, true);
    }

    /* renamed from: c */
    public void mo14448c(SQLiteDatabase sQLiteDatabase) {
        m1602a(sQLiteDatabase, 1, String.valueOf(C1234k.f1840a));
        m1602a(sQLiteDatabase, 2, String.valueOf(C1234k.f1841b));
        m1602a(sQLiteDatabase, 3, String.valueOf(C1234k.f1842c));
        m1602a(sQLiteDatabase, 4, String.valueOf(C1234k.f1845f));
        m1602a(sQLiteDatabase, 6, String.valueOf(C1234k.f1846g));
        m1602a(sQLiteDatabase, 7, String.valueOf(C1234k.f1847h));
        m1602a(sQLiteDatabase, 8, String.valueOf(C1234k.f1848i));
        m1602a(sQLiteDatabase, 9, String.valueOf(C1234k.f1849j));
        m1602a(sQLiteDatabase, 13, String.valueOf(C1234k.f1850k));
        m1602a(sQLiteDatabase, 14, String.valueOf(C1234k.f1851l));
        m1602a(sQLiteDatabase, 15, String.valueOf(C1234k.f1843d));
        m1602a(sQLiteDatabase, 3, String.valueOf(C1234k.f1842c));
        m1602a(sQLiteDatabase, 17, String.valueOf(C1234k.f1852m));
        m1602a(sQLiteDatabase, 18, String.valueOf(C1234k.f1853n));
        m1602a(sQLiteDatabase, 19, String.valueOf(C1234k.f1854o));
        m1602a(sQLiteDatabase, 25, String.valueOf(C1234k.f1860u));
    }

    /* renamed from: d */
    public void mo14449d() {
        C1174c.m1310b().mo14317a(new C1227d(this), false, true);
    }

    /* renamed from: e */
    public void mo14450e() {
        C1174c.m1310b().mo14317a(new C1228e(this), false, true);
    }

    /* renamed from: f */
    public void mo14451f() {
        C1174c.m1310b().mo14317a(new C1229f(this), false, true);
    }
}
