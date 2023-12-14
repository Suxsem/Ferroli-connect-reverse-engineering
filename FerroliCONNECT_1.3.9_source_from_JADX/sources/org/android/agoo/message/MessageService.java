package org.android.agoo.message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.MsgDO;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Taobao */
public class MessageService {
    public static final String MSG_ACCS_NOTIFY_CLICK = "8";
    public static final String MSG_ACCS_NOTIFY_DISMISS = "9";
    public static final String MSG_ACCS_READY_REPORT = "4";
    public static final String MSG_DB_COMPLETE = "100";
    public static final String MSG_DB_NOTIFY_CLICK = "2";
    public static final String MSG_DB_NOTIFY_DISMISS = "3";
    public static final String MSG_DB_NOTIFY_REACHED = "1";
    public static final String MSG_DB_READY_REPORT = "0";

    /* renamed from: a */
    private static Context f4099a;

    /* renamed from: c */
    private static Map<String, Integer> f4100c;

    /* renamed from: b */
    private volatile SQLiteOpenHelper f4101b = null;

    /* renamed from: a */
    public void mo25528a(Context context) {
        f4100c = new HashMap();
        f4099a = context;
        this.f4101b = new C2367a(context);
    }

    /* renamed from: org.android.agoo.message.MessageService$a */
    /* compiled from: Taobao */
    private static class C2367a extends SQLiteOpenHelper {
        public C2367a(Context context) {
            super(context, "emas_message_accs_db", (SQLiteDatabase.CursorFactory) null, 3);
        }

        public SQLiteDatabase getWritableDatabase() {
            if (!AdapterUtilityImpl.checkIsWritable(super.getWritableDatabase().getPath(), 102400)) {
                return null;
            }
            return super.getWritableDatabase();
        }

        /* renamed from: a */
        private String m3918a() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("create table accs_message");
            stringBuffer.append("(");
            stringBuffer.append("id text UNIQUE not null,");
            stringBuffer.append("state text,");
            stringBuffer.append("message text,");
            stringBuffer.append("create_time date");
            stringBuffer.append(");");
            return stringBuffer.toString();
        }

        /* renamed from: b */
        private String m3919b() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("create table message");
            stringBuffer.append("(");
            stringBuffer.append("id text UNIQUE not null,");
            stringBuffer.append("state integer,");
            stringBuffer.append("body_code integer,");
            stringBuffer.append("report long,");
            stringBuffer.append("target_time long,");
            stringBuffer.append("interval integer,");
            stringBuffer.append("type text,");
            stringBuffer.append("message text,");
            stringBuffer.append("notify integer,");
            stringBuffer.append("create_time date");
            stringBuffer.append(");");
            return stringBuffer.toString();
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.execSQL(m3919b());
                    sQLiteDatabase.execSQL("CREATE INDEX id_index ON message(id)");
                    sQLiteDatabase.execSQL("CREATE INDEX body_code_index ON message(body_code)");
                    sQLiteDatabase.execSQL(m3918a());
                } catch (Throwable th) {
                    ALog.m3726e("MessageService", "messagedbhelper create", th, new Object[0]);
                }
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.execSQL("delete from message where create_time< date('now','-7 day') and state=1");
                } catch (Throwable th) {
                    ALog.m3726e("MessageService", "MessageService onUpgrade is error", th, new Object[0]);
                    return;
                }
            }
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS accs_message");
                sQLiteDatabase.execSQL(m3918a());
                return;
            } catch (Throwable th2) {
                ALog.m3726e("MessageService", "MessageService onUpgrade is error", th2, new Object[0]);
                return;
            }
            throw th;
        }
    }

    /* renamed from: a */
    public void mo25529a(String str, String str2) {
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.m3728i("MessageService", "updateAccsMessage sqlite3--->[" + str + ",state=" + str2 + "]", new Object[0]);
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (!TextUtils.isEmpty(str2)) {
                sQLiteDatabase = this.f4101b.getWritableDatabase();
                if (sQLiteDatabase != null) {
                    if (TextUtils.equals(str2, "1")) {
                        sQLiteDatabase.execSQL("UPDATE accs_message set state = ? where id = ? and state = ?", new Object[]{str2, str, "0"});
                    } else {
                        sQLiteDatabase.execSQL("UPDATE accs_message set state = ? where id = ?", new Object[]{str2, str});
                    }
                    if (sQLiteDatabase == null) {
                        return;
                    }
                    sQLiteDatabase.close();
                } else if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            }
        } catch (Throwable th) {
            if (sQLiteDatabase != null) {
                sQLiteDatabase.close();
            }
            throw th;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: android.database.Cursor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX WARNING: type inference failed for: r9v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r9v2, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r9v4 */
    /* JADX WARNING: type inference failed for: r9v5 */
    /* JADX WARNING: type inference failed for: r9v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a9 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:66:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo25530a(java.lang.String r8, java.lang.String r9, java.lang.String r10) {
        /*
            r7 = this;
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            java.lang.String r1 = "MessageService"
            r2 = 0
            if (r0 == 0) goto L_0x0036
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "addAccsMessage sqlite3--->["
            r0.append(r3)
            r0.append(r8)
            java.lang.String r3 = ",message="
            r0.append(r3)
            r0.append(r9)
            java.lang.String r3 = ",state="
            r0.append(r3)
            r0.append(r10)
            java.lang.String r3 = "]"
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.taobao.accs.utl.ALog.m3728i(r1, r0, r3)
        L_0x0036:
            r0 = 0
            boolean r3 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            if (r3 != 0) goto L_0x009b
            boolean r3 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            if (r3 == 0) goto L_0x0044
            goto L_0x009b
        L_0x0044:
            android.database.sqlite.SQLiteOpenHelper r3 = r7.f4101b     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            android.database.sqlite.SQLiteDatabase r3 = r3.getWritableDatabase()     // Catch:{ Throwable -> 0x009f, all -> 0x009c }
            if (r3 != 0) goto L_0x0052
            if (r3 == 0) goto L_0x0051
            r3.close()
        L_0x0051:
            return
        L_0x0052:
            java.lang.String r4 = "select count(1) from accs_message where id = ?"
            r5 = 1
            java.lang.String[] r6 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            r6[r2] = r8     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            android.database.Cursor r0 = r3.rawQuery(r4, r6)     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            if (r0 == 0) goto L_0x0079
            boolean r4 = r0.moveToFirst()     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            if (r4 == 0) goto L_0x0079
            int r4 = r0.getInt(r2)     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            if (r4 <= 0) goto L_0x0079
            r0.close()     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            if (r0 == 0) goto L_0x0073
            r0.close()
        L_0x0073:
            if (r3 == 0) goto L_0x0078
            r3.close()
        L_0x0078:
            return
        L_0x0079:
            java.lang.String r4 = "INSERT INTO accs_message VALUES(?,?,?,date('now'))"
            r6 = 3
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            r6[r2] = r8     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            r6[r5] = r10     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            r8 = 2
            r6[r8] = r9     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            r3.execSQL(r4, r6)     // Catch:{ Throwable -> 0x0097, all -> 0x0093 }
            if (r0 == 0) goto L_0x008d
            r0.close()
        L_0x008d:
            if (r3 == 0) goto L_0x00ed
            r3.close()
            goto L_0x00ed
        L_0x0093:
            r8 = move-exception
            r9 = r0
            r0 = r3
            goto L_0x00ef
        L_0x0097:
            r8 = move-exception
            r9 = r0
            r0 = r3
            goto L_0x00a1
        L_0x009b:
            return
        L_0x009c:
            r8 = move-exception
            r9 = r0
            goto L_0x00ef
        L_0x009f:
            r8 = move-exception
            r9 = r0
        L_0x00a1:
            com.taobao.accs.utl.ALog$Level r10 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ all -> 0x00ee }
            boolean r10 = com.taobao.accs.utl.ALog.isPrintLog(r10)     // Catch:{ all -> 0x00ee }
            if (r10 == 0) goto L_0x00cb
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ee }
            r10.<init>()     // Catch:{ all -> 0x00ee }
            java.lang.String r3 = "addAccsMessage error,e--->["
            r10.append(r3)     // Catch:{ all -> 0x00ee }
            r10.append(r8)     // Catch:{ all -> 0x00ee }
            java.lang.String r3 = "],ex="
            r10.append(r3)     // Catch:{ all -> 0x00ee }
            java.lang.String r3 = r7.m3906a((java.lang.Throwable) r8)     // Catch:{ all -> 0x00ee }
            r10.append(r3)     // Catch:{ all -> 0x00ee }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00ee }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00ee }
            com.taobao.accs.utl.ALog.m3727e(r1, r10, r2)     // Catch:{ all -> 0x00ee }
        L_0x00cb:
            com.taobao.accs.utl.UTMini r1 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ all -> 0x00ee }
            r2 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r3 = "accs.add_agoo_message"
            android.content.Context r10 = f4099a     // Catch:{ all -> 0x00ee }
            java.lang.String r4 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r10)     // Catch:{ all -> 0x00ee }
            java.lang.String r5 = "addAccsMessageFailed"
            java.lang.String r6 = r8.toString()     // Catch:{ all -> 0x00ee }
            r1.commitEvent(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00ee }
            if (r9 == 0) goto L_0x00e8
            r9.close()
        L_0x00e8:
            if (r0 == 0) goto L_0x00ed
            r0.close()
        L_0x00ed:
            return
        L_0x00ee:
            r8 = move-exception
        L_0x00ef:
            if (r9 == 0) goto L_0x00f4
            r9.close()
        L_0x00f4:
            if (r0 == 0) goto L_0x00f9
            r0.close()
        L_0x00f9:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.mo25530a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* renamed from: a */
    private String m3906a(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                stringBuffer.append(stackTraceElement.toString());
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }

    /* renamed from: a */
    public void mo25531a(String str, String str2, String str3, int i) {
        m3907a(str, str2, str3, 1, -1, -1, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01a9, code lost:
        com.taobao.accs.utl.UTMini.getInstance().commitEvent(org.android.agoo.common.AgooConstants.AGOO_EVENT_ID, "accs.add_agoo_message", com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(f4099a), "addMessageDBcloseFailed", r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0146 A[Catch:{ all -> 0x01cd }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0184 A[SYNTHETIC, Splitter:B:51:0x0184] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01d2 A[SYNTHETIC, Splitter:B:60:0x01d2] */
    /* JADX WARNING: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m3907a(java.lang.String r15, java.lang.String r16, java.lang.String r17, int r18, long r19, int r21, int r22) {
        /*
            r14 = this;
            r0 = r15
            java.lang.String r1 = "addMessage,db.close(),error,e--->["
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "add sqlite3--->["
            r2.append(r3)
            r2.append(r15)
            java.lang.String r3 = "]"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.String r6 = "MessageService"
            com.taobao.accs.utl.ALog.m3725d(r6, r2, r5)
            r2 = 0
            boolean r5 = android.text.TextUtils.isEmpty(r16)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            java.lang.String r7 = ""
            if (r5 == 0) goto L_0x002d
            r5 = -1
            r8 = r7
            goto L_0x0033
        L_0x002d:
            int r5 = r16.hashCode()     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            r8 = r16
        L_0x0033:
            boolean r9 = android.text.TextUtils.isEmpty(r17)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            if (r9 == 0) goto L_0x003a
            goto L_0x003c
        L_0x003a:
            r7 = r17
        L_0x003c:
            java.util.Map<java.lang.String, java.lang.Integer> r9 = f4100c     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            boolean r9 = r9.containsKey(r15)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            if (r9 != 0) goto L_0x0079
            java.util.Map<java.lang.String, java.lang.Integer> r9 = f4100c     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            r9.put(r15, r10)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            com.taobao.accs.utl.ALog$Level r9 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            boolean r9 = com.taobao.accs.utl.ALog.isPrintLog(r9)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            if (r9 == 0) goto L_0x0079
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            r9.<init>()     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            java.lang.String r10 = "addMessage,messageId="
            r9.append(r10)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            r9.append(r15)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            java.lang.String r10 = ",messageStores＝"
            r9.append(r10)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            java.util.Map<java.lang.String, java.lang.Integer> r10 = f4100c     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            r9.append(r10)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
            com.taobao.accs.utl.ALog.m3728i(r6, r9, r10)     // Catch:{ Throwable -> 0x013c, all -> 0x0135 }
        L_0x0079:
            r9 = r14
            android.database.sqlite.SQLiteOpenHelper r10 = r9.f4101b     // Catch:{ Throwable -> 0x0133 }
            android.database.sqlite.SQLiteDatabase r2 = r10.getWritableDatabase()     // Catch:{ Throwable -> 0x0133 }
            if (r2 != 0) goto L_0x00cd
            if (r2 == 0) goto L_0x00cc
            r2.close()     // Catch:{ Throwable -> 0x0088 }
            goto L_0x00cc
        L_0x0088:
            r0 = move-exception
            r2 = r0
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.E
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            if (r0 == 0) goto L_0x00a9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r2)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.Object[] r1 = new java.lang.Object[r4]
            com.taobao.accs.utl.ALog.m3727e(r6, r0, r1)
        L_0x00a9:
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()
            r1 = 66002(0x101d2, float:9.2489E-41)
            android.content.Context r3 = f4099a
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r4 = "accs.add_agoo_message"
            java.lang.String r5 = "addMessageDBcloseFailed"
            r15 = r0
            r16 = r1
            r17 = r4
            r18 = r3
            r19 = r5
            r20 = r2
            r15.commitEvent(r16, r17, r18, r19, r20)
        L_0x00cc:
            return
        L_0x00cd:
            java.lang.String r10 = "INSERT INTO message VALUES(?,?,?,?,?,?,?,?,?,date('now'))"
            r11 = 9
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x0133 }
            r11[r4] = r0     // Catch:{ Throwable -> 0x0133 }
            r0 = 1
            java.lang.Integer r12 = java.lang.Integer.valueOf(r18)     // Catch:{ Throwable -> 0x0133 }
            r11[r0] = r12     // Catch:{ Throwable -> 0x0133 }
            r0 = 2
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x0133 }
            r11[r0] = r5     // Catch:{ Throwable -> 0x0133 }
            r0 = 3
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0133 }
            r11[r0] = r5     // Catch:{ Throwable -> 0x0133 }
            r0 = 4
            java.lang.Long r5 = java.lang.Long.valueOf(r19)     // Catch:{ Throwable -> 0x0133 }
            r11[r0] = r5     // Catch:{ Throwable -> 0x0133 }
            r0 = 5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r21)     // Catch:{ Throwable -> 0x0133 }
            r11[r0] = r5     // Catch:{ Throwable -> 0x0133 }
            r0 = 6
            r11[r0] = r7     // Catch:{ Throwable -> 0x0133 }
            r0 = 7
            r11[r0] = r8     // Catch:{ Throwable -> 0x0133 }
            r0 = 8
            java.lang.Integer r5 = java.lang.Integer.valueOf(r22)     // Catch:{ Throwable -> 0x0133 }
            r11[r0] = r5     // Catch:{ Throwable -> 0x0133 }
            r2.execSQL(r10, r11)     // Catch:{ Throwable -> 0x0133 }
            if (r2 == 0) goto L_0x01cc
            r2.close()     // Catch:{ Throwable -> 0x0110 }
            goto L_0x01cc
        L_0x0110:
            r0 = move-exception
            r2 = r0
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.E
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            if (r0 == 0) goto L_0x01a9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r2)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.Object[] r1 = new java.lang.Object[r4]
            com.taobao.accs.utl.ALog.m3727e(r6, r0, r1)
            goto L_0x01a9
        L_0x0133:
            r0 = move-exception
            goto L_0x013e
        L_0x0135:
            r0 = move-exception
            r9 = r14
        L_0x0137:
            r13 = r2
            r2 = r0
            r0 = r13
            goto L_0x01d0
        L_0x013c:
            r0 = move-exception
            r9 = r14
        L_0x013e:
            com.taobao.accs.utl.ALog$Level r5 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ all -> 0x01cd }
            boolean r5 = com.taobao.accs.utl.ALog.isPrintLog(r5)     // Catch:{ all -> 0x01cd }
            if (r5 == 0) goto L_0x015f
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cd }
            r5.<init>()     // Catch:{ all -> 0x01cd }
            java.lang.String r7 = "addMessage error,e--->["
            r5.append(r7)     // Catch:{ all -> 0x01cd }
            r5.append(r0)     // Catch:{ all -> 0x01cd }
            r5.append(r3)     // Catch:{ all -> 0x01cd }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x01cd }
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x01cd }
            com.taobao.accs.utl.ALog.m3727e(r6, r5, r7)     // Catch:{ all -> 0x01cd }
        L_0x015f:
            com.taobao.accs.utl.UTMini r5 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ all -> 0x01cd }
            r7 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r8 = "accs.add_agoo_message"
            android.content.Context r10 = f4099a     // Catch:{ all -> 0x01cd }
            java.lang.String r10 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r10)     // Catch:{ all -> 0x01cd }
            java.lang.String r11 = "addMessageFailed"
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01cd }
            r15 = r5
            r16 = r7
            r17 = r8
            r18 = r10
            r19 = r11
            r20 = r0
            r15.commitEvent(r16, r17, r18, r19, r20)     // Catch:{ all -> 0x01cd }
            if (r2 == 0) goto L_0x01cc
            r2.close()     // Catch:{ Throwable -> 0x0188 }
            goto L_0x01cc
        L_0x0188:
            r0 = move-exception
            r2 = r0
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.E
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            if (r0 == 0) goto L_0x01a9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r2)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.Object[] r1 = new java.lang.Object[r4]
            com.taobao.accs.utl.ALog.m3727e(r6, r0, r1)
        L_0x01a9:
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()
            r1 = 66002(0x101d2, float:9.2489E-41)
            android.content.Context r3 = f4099a
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r4 = "accs.add_agoo_message"
            java.lang.String r5 = "addMessageDBcloseFailed"
            r15 = r0
            r16 = r1
            r17 = r4
            r18 = r3
            r19 = r5
            r20 = r2
            r15.commitEvent(r16, r17, r18, r19, r20)
        L_0x01cc:
            return
        L_0x01cd:
            r0 = move-exception
            goto L_0x0137
        L_0x01d0:
            if (r0 == 0) goto L_0x021a
            r0.close()     // Catch:{ Throwable -> 0x01d6 }
            goto L_0x021a
        L_0x01d6:
            r0 = move-exception
            r5 = r0
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.E
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            if (r0 == 0) goto L_0x01f7
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            r0.append(r5)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.Object[] r1 = new java.lang.Object[r4]
            com.taobao.accs.utl.ALog.m3727e(r6, r0, r1)
        L_0x01f7:
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()
            r1 = 66002(0x101d2, float:9.2489E-41)
            android.content.Context r3 = f4099a
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r3)
            java.lang.String r4 = r5.toString()
            java.lang.String r5 = "accs.add_agoo_message"
            java.lang.String r6 = "addMessageDBcloseFailed"
            r15 = r0
            r16 = r1
            r17 = r5
            r18 = r3
            r19 = r6
            r20 = r4
            r15.commitEvent(r16, r17, r18, r19, r20)
        L_0x021a:
            goto L_0x021c
        L_0x021b:
            throw r2
        L_0x021c:
            goto L_0x021b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.m3907a(java.lang.String, java.lang.String, java.lang.String, int, long, int, int):void");
    }

    /* renamed from: a */
    public void mo25527a() {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = this.f4101b.getWritableDatabase();
            if (sQLiteDatabase != null) {
                sQLiteDatabase.execSQL("delete from message where create_time< date('now','-7 day') and state=1");
                sQLiteDatabase.execSQL("delete from accs_message where create_time< date('now','-1 day') ");
                if (sQLiteDatabase == null) {
                    return;
                }
                try {
                    sQLiteDatabase.close();
                    return;
                } catch (Throwable unused) {
                    return;
                }
            } else if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.close();
                    return;
                } catch (Throwable unused2) {
                    return;
                }
            } else {
                return;
            }
        } catch (Throwable th) {
            try {
                ALog.m3726e("MessageService", "deleteCacheMessage sql Throwable", th, new Object[0]);
                if (sQLiteDatabase == null) {
                    return;
                }
            } catch (Throwable unused3) {
            }
        }
        throw th;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:61:0x013b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x013c, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x013e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x013f, code lost:
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0152, code lost:
        com.taobao.accs.utl.ALog.m3727e("MessageService", "getUnReportMsg, e: " + r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x016f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0173, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x017d, code lost:
        if (com.taobao.accs.utl.ALog.isPrintLog(com.taobao.accs.utl.ALog.Level.E) != false) goto L_0x017f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x017f, code lost:
        com.taobao.accs.utl.ALog.m3727e("MessageService", "getUnReportMsg close cursor or db, e: " + r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x019e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01a2, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01ac, code lost:
        if (com.taobao.accs.utl.ALog.isPrintLog(com.taobao.accs.utl.ALog.Level.E) != false) goto L_0x01ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01ae, code lost:
        com.taobao.accs.utl.ALog.m3727e("MessageService", "getUnReportMsg close cursor or db, e: " + r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x013b A[ExcHandler: all (r0v12 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:12:0x003b] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0152 A[Catch:{ all -> 0x0195 }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x016b A[SYNTHETIC, Splitter:B:76:0x016b] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0173 A[Catch:{ Throwable -> 0x016f }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x019a A[SYNTHETIC, Splitter:B:89:0x019a] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01a2 A[Catch:{ Throwable -> 0x019e }] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.ArrayList<org.android.agoo.common.MsgDO> mo25534b() {
        /*
            r19 = this;
            r1 = r19
            java.lang.String r0 = "3"
            java.lang.String r2 = "2"
            java.lang.String r3 = "0"
            java.lang.String r4 = "getUnReportMsg close cursor or db, e: "
            java.lang.String r5 = "MessageService"
            r6 = 0
            r7 = 0
            android.database.sqlite.SQLiteOpenHelper r8 = r1.f4101b     // Catch:{ Throwable -> 0x0146, all -> 0x0141 }
            android.database.sqlite.SQLiteDatabase r8 = r8.getReadableDatabase()     // Catch:{ Throwable -> 0x0146, all -> 0x0141 }
            if (r8 != 0) goto L_0x003b
            if (r8 == 0) goto L_0x003a
            r8.close()     // Catch:{ Throwable -> 0x001c }
            goto L_0x003a
        L_0x001c:
            r0 = move-exception
            r2 = r0
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.E
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            if (r0 == 0) goto L_0x003a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r4)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.Object[] r2 = new java.lang.Object[r7]
            com.taobao.accs.utl.ALog.m3727e(r5, r0, r2)
        L_0x003a:
            return r6
        L_0x003b:
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            r9.<init>()     // Catch:{ Throwable -> 0x013e, all -> 0x013b }
            java.lang.String r10 = "select * from accs_message where state = ? or state = ? or state = ?"
            java.lang.String[] r11 = new java.lang.String[]{r3, r2, r0}     // Catch:{ Throwable -> 0x0138, all -> 0x013b }
            android.database.Cursor r10 = r8.rawQuery(r10, r11)     // Catch:{ Throwable -> 0x0138, all -> 0x013b }
            if (r10 == 0) goto L_0x010b
            java.lang.String r11 = "id"
            int r11 = r10.getColumnIndex(r11)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r12 = "state"
            int r12 = r10.getColumnIndex(r12)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r13 = "message"
            int r13 = r10.getColumnIndex(r13)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r14 = "create_time"
            int r14 = r10.getColumnIndex(r14)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
        L_0x0064:
            boolean r15 = r10.moveToNext()     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            if (r15 == 0) goto L_0x010b
            java.lang.String r15 = r10.getString(r13)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            boolean r15 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            if (r15 != 0) goto L_0x010b
            java.lang.String r15 = r10.getString(r12)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r6 = r10.getString(r13)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            com.taobao.accs.utl.ALog$Level r16 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            boolean r16 = com.taobao.accs.utl.ALog.isPrintLog(r16)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            if (r16 == 0) goto L_0x00c0
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r7.<init>()     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r17 = r12
            java.lang.String r12 = "state: "
            r7.append(r12)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r7.append(r15)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r12 = " ,cursor.message:"
            r7.append(r12)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r7.append(r6)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r12 = " ,cursor.id:"
            r7.append(r12)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r12 = r10.getString(r11)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r7.append(r12)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r12 = " ,cursor.time:"
            r7.append(r12)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r12 = r10.getString(r14)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r7.append(r12)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r18 = r11
            r12 = 0
            java.lang.Object[] r11 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            com.taobao.accs.utl.ALog.m3728i(r5, r7, r11)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            goto L_0x00c4
        L_0x00c0:
            r18 = r11
            r17 = r12
        L_0x00c4:
            boolean r7 = android.text.TextUtils.equals(r3, r15)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            if (r7 == 0) goto L_0x00cd
            java.lang.String r7 = "4"
            goto L_0x00e0
        L_0x00cd:
            boolean r7 = android.text.TextUtils.equals(r2, r15)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            if (r7 == 0) goto L_0x00d6
            java.lang.String r7 = "8"
            goto L_0x00e0
        L_0x00d6:
            boolean r7 = android.text.TextUtils.equals(r0, r15)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            if (r7 == 0) goto L_0x00df
            java.lang.String r7 = "9"
            goto L_0x00e0
        L_0x00df:
            r7 = 0
        L_0x00e0:
            org.android.agoo.common.MsgDO r11 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r11.<init>()     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            boolean r11 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            if (r11 != 0) goto L_0x00fc
            boolean r11 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            if (r11 != 0) goto L_0x00fc
            org.android.agoo.common.MsgDO r6 = r1.m3909b(r6, r7)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            java.lang.String r7 = "cache"
            r6.messageSource = r7     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
            r9.add(r6)     // Catch:{ Throwable -> 0x0108, all -> 0x0104 }
        L_0x00fc:
            r12 = r17
            r11 = r18
            r6 = 0
            r7 = 0
            goto L_0x0064
        L_0x0104:
            r0 = move-exception
            r2 = r0
            goto L_0x0198
        L_0x0108:
            r0 = move-exception
            r6 = r10
            goto L_0x014a
        L_0x010b:
            if (r10 == 0) goto L_0x0113
            r10.close()     // Catch:{ Throwable -> 0x0111 }
            goto L_0x0113
        L_0x0111:
            r0 = move-exception
            goto L_0x011a
        L_0x0113:
            if (r8 == 0) goto L_0x0194
            r8.close()     // Catch:{ Throwable -> 0x0111 }
            goto L_0x0194
        L_0x011a:
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)
            if (r2 == 0) goto L_0x0194
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.taobao.accs.utl.ALog.m3727e(r5, r0, r2)
            goto L_0x0194
        L_0x0138:
            r0 = move-exception
            r6 = 0
            goto L_0x014a
        L_0x013b:
            r0 = move-exception
            r2 = r0
            goto L_0x0144
        L_0x013e:
            r0 = move-exception
            r6 = 0
            goto L_0x0149
        L_0x0141:
            r0 = move-exception
            r2 = r0
            r8 = 0
        L_0x0144:
            r10 = 0
            goto L_0x0198
        L_0x0146:
            r0 = move-exception
            r6 = 0
            r8 = 0
        L_0x0149:
            r9 = 0
        L_0x014a:
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ all -> 0x0195 }
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ all -> 0x0195 }
            if (r2 == 0) goto L_0x0169
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0195 }
            r2.<init>()     // Catch:{ all -> 0x0195 }
            java.lang.String r3 = "getUnReportMsg, e: "
            r2.append(r3)     // Catch:{ all -> 0x0195 }
            r2.append(r0)     // Catch:{ all -> 0x0195 }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x0195 }
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x0195 }
            com.taobao.accs.utl.ALog.m3727e(r5, r0, r3)     // Catch:{ all -> 0x0195 }
        L_0x0169:
            if (r6 == 0) goto L_0x0171
            r6.close()     // Catch:{ Throwable -> 0x016f }
            goto L_0x0171
        L_0x016f:
            r0 = move-exception
            goto L_0x0177
        L_0x0171:
            if (r8 == 0) goto L_0x0194
            r8.close()     // Catch:{ Throwable -> 0x016f }
            goto L_0x0194
        L_0x0177:
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)
            if (r2 == 0) goto L_0x0194
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r4)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            com.taobao.accs.utl.ALog.m3727e(r5, r0, r2)
        L_0x0194:
            return r9
        L_0x0195:
            r0 = move-exception
            r2 = r0
            r10 = r6
        L_0x0198:
            if (r10 == 0) goto L_0x01a0
            r10.close()     // Catch:{ Throwable -> 0x019e }
            goto L_0x01a0
        L_0x019e:
            r0 = move-exception
            goto L_0x01a6
        L_0x01a0:
            if (r8 == 0) goto L_0x01c3
            r8.close()     // Catch:{ Throwable -> 0x019e }
            goto L_0x01c3
        L_0x01a6:
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.E
            boolean r3 = com.taobao.accs.utl.ALog.isPrintLog(r3)
            if (r3 == 0) goto L_0x01c3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r4)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.m3727e(r5, r0, r3)
        L_0x01c3:
            goto L_0x01c5
        L_0x01c4:
            throw r2
        L_0x01c5:
            goto L_0x01c4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.mo25534b():java.util.ArrayList");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: android.database.Cursor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v10 ?
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:189)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.collectCodeVars(ProcessVariables.java:122)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:45)
        */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
        if (r4 != 0) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005c, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0062, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0064, code lost:
        r8 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0065, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0078, code lost:
        if (r4 != 0) goto L_0x005c;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0064 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0002] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0068 A[SYNTHETIC, Splitter:B:37:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x006d A[Catch:{ Throwable -> 0x0070 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0075 A[SYNTHETIC, Splitter:B:47:0x0075] */
    /* renamed from: a */
    public boolean mo25532a(java.lang.String r8) {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            java.util.Map<java.lang.String, java.lang.Integer> r2 = f4100c     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            boolean r2 = r2.containsKey(r8)     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            r3 = 1
            if (r2 == 0) goto L_0x002d
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            if (r2 == 0) goto L_0x002b
            java.lang.String r2 = "MessageService"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            r4.<init>()     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            java.lang.String r5 = "hasMessageDuplicate,msgid="
            r4.append(r5)     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            r4.append(r8)     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
            com.taobao.accs.utl.ALog.m3727e(r2, r4, r5)     // Catch:{ Throwable -> 0x0071, all -> 0x0064 }
        L_0x002b:
            r2 = 1
            goto L_0x002e
        L_0x002d:
            r2 = 0
        L_0x002e:
            android.database.sqlite.SQLiteOpenHelper r4 = r7.f4101b     // Catch:{ Throwable -> 0x0062, all -> 0x0064 }
            android.database.sqlite.SQLiteDatabase r4 = r4.getReadableDatabase()     // Catch:{ Throwable -> 0x0062, all -> 0x0064 }
            if (r4 != 0) goto L_0x003c
            if (r4 == 0) goto L_0x003b
            r4.close()     // Catch:{ Throwable -> 0x003b }
        L_0x003b:
            return r2
        L_0x003c:
            java.lang.String r5 = "select count(1) from message where id = ?"
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x0073, all -> 0x0060 }
            r6[r1] = r8     // Catch:{ Throwable -> 0x0073, all -> 0x0060 }
            android.database.Cursor r0 = r4.rawQuery(r5, r6)     // Catch:{ Throwable -> 0x0073, all -> 0x0060 }
            if (r0 == 0) goto L_0x0055
            boolean r8 = r0.moveToFirst()     // Catch:{ Throwable -> 0x0073, all -> 0x0060 }
            if (r8 == 0) goto L_0x0055
            int r8 = r0.getInt(r1)     // Catch:{ Throwable -> 0x0073, all -> 0x0060 }
            if (r8 <= 0) goto L_0x0055
            r2 = 1
        L_0x0055:
            if (r0 == 0) goto L_0x005a
            r0.close()     // Catch:{ Throwable -> 0x007b }
        L_0x005a:
            if (r4 == 0) goto L_0x007b
        L_0x005c:
            r4.close()     // Catch:{ Throwable -> 0x007b }
            goto L_0x007b
        L_0x0060:
            r8 = move-exception
            goto L_0x0066
        L_0x0062:
            r4 = r0
            goto L_0x0073
        L_0x0064:
            r8 = move-exception
            r4 = r0
        L_0x0066:
            if (r0 == 0) goto L_0x006b
            r0.close()     // Catch:{ Throwable -> 0x0070 }
        L_0x006b:
            if (r4 == 0) goto L_0x0070
            r4.close()     // Catch:{ Throwable -> 0x0070 }
        L_0x0070:
            throw r8
        L_0x0071:
            r4 = r0
            r2 = 0
        L_0x0073:
            if (r0 == 0) goto L_0x0078
            r0.close()     // Catch:{ Throwable -> 0x007b }
        L_0x0078:
            if (r4 == 0) goto L_0x007b
            goto L_0x005c
        L_0x007b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.mo25532a(java.lang.String):boolean");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: android.database.Cursor} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v11 ?
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:189)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.collectCodeVars(ProcessVariables.java:122)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:45)
        */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007a, code lost:
        if (r4 != 0) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007c, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0082, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0084, code lost:
        r9 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0085, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0098, code lost:
        if (r4 != 0) goto L_0x007c;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0084 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0002] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0088 A[SYNTHETIC, Splitter:B:39:0x0088] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x008d A[Catch:{ Throwable -> 0x0090 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0095 A[SYNTHETIC, Splitter:B:49:0x0095] */
    /* renamed from: a */
    public boolean mo25533a(java.lang.String r9, int r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            java.util.Map<java.lang.String, java.lang.Integer> r2 = f4100c     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            boolean r2 = r2.containsKey(r9)     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            r3 = 1
            if (r2 == 0) goto L_0x0039
            java.util.Map<java.lang.String, java.lang.Integer> r2 = f4100c     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            boolean r2 = r2.containsValue(r4)     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            if (r2 == 0) goto L_0x0039
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            if (r2 == 0) goto L_0x0037
            java.lang.String r2 = "MessageService"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            r4.<init>()     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            java.lang.String r5 = "addMessage,messageStores hasMessageDuplicate,msgid="
            r4.append(r5)     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            r4.append(r9)     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
            com.taobao.accs.utl.ALog.m3728i(r2, r4, r5)     // Catch:{ Throwable -> 0x0091, all -> 0x0084 }
        L_0x0037:
            r2 = 1
            goto L_0x003a
        L_0x0039:
            r2 = 0
        L_0x003a:
            android.database.sqlite.SQLiteOpenHelper r4 = r8.f4101b     // Catch:{ Throwable -> 0x0082, all -> 0x0084 }
            android.database.sqlite.SQLiteDatabase r4 = r4.getReadableDatabase()     // Catch:{ Throwable -> 0x0082, all -> 0x0084 }
            if (r4 != 0) goto L_0x0048
            if (r4 == 0) goto L_0x0047
            r4.close()     // Catch:{ Throwable -> 0x0047 }
        L_0x0047:
            return r2
        L_0x0048:
            java.lang.String r5 = "select count(1) from message where id = ? and body_code=? and create_time< date('now','-1 day')"
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            r6[r1] = r9     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            r9.<init>()     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            java.lang.String r7 = ""
            r9.append(r7)     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            r9.append(r10)     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            r6[r3] = r9     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            android.database.Cursor r0 = r4.rawQuery(r5, r6)     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            if (r0 == 0) goto L_0x0075
            boolean r9 = r0.moveToFirst()     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            if (r9 == 0) goto L_0x0075
            int r9 = r0.getInt(r1)     // Catch:{ Throwable -> 0x0093, all -> 0x0080 }
            if (r9 <= 0) goto L_0x0075
            r2 = 1
        L_0x0075:
            if (r0 == 0) goto L_0x007a
            r0.close()     // Catch:{ Throwable -> 0x009b }
        L_0x007a:
            if (r4 == 0) goto L_0x009b
        L_0x007c:
            r4.close()     // Catch:{ Throwable -> 0x009b }
            goto L_0x009b
        L_0x0080:
            r9 = move-exception
            goto L_0x0086
        L_0x0082:
            r4 = r0
            goto L_0x0093
        L_0x0084:
            r9 = move-exception
            r4 = r0
        L_0x0086:
            if (r0 == 0) goto L_0x008b
            r0.close()     // Catch:{ Throwable -> 0x0090 }
        L_0x008b:
            if (r4 == 0) goto L_0x0090
            r4.close()     // Catch:{ Throwable -> 0x0090 }
        L_0x0090:
            throw r9
        L_0x0091:
            r4 = r0
            r2 = 0
        L_0x0093:
            if (r0 == 0) goto L_0x0098
            r0.close()     // Catch:{ Throwable -> 0x009b }
        L_0x0098:
            if (r4 == 0) goto L_0x009b
            goto L_0x007c
        L_0x009b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.message.MessageService.mo25533a(java.lang.String, int):boolean");
    }

    /* renamed from: b */
    private MsgDO m3909b(String str, String str2) {
        String str3;
        int i;
        int i2;
        boolean z;
        String str4 = str;
        String str5 = "ext";
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.m3728i("MessageService", "msgRecevie,message--->[" + str4 + "],utdid=" + AdapterUtilityImpl.getDeviceId(f4099a), new Object[0]);
        }
        if (TextUtils.isEmpty(str)) {
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.dealMessage", AdapterUtilityImpl.getDeviceId(f4099a), "message==null");
            if (ALog.isPrintLog(ALog.Level.I)) {
                ALog.m3728i("MessageService", "handleMessage message==null,utdid=" + AdapterUtilityImpl.getDeviceId(f4099a), new Object[0]);
            }
            return null;
        }
        MsgDO msgDO = new MsgDO();
        try {
            JSONArray jSONArray = new JSONArray(str4);
            int length = jSONArray.length();
            new Bundle();
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            String str6 = null;
            int i3 = 0;
            while (i3 < length) {
                JSONObject jSONObject = jSONArray.getJSONObject(i3);
                if (jSONObject == null) {
                    i = length;
                    str3 = str5;
                    i2 = i3;
                } else {
                    String string = jSONObject.getString("p");
                    String string2 = jSONObject.getString("i");
                    String string3 = jSONObject.getString("b");
                    int i4 = i3;
                    long j = jSONObject.getLong("f");
                    sb.append(string2);
                    if (!jSONObject.isNull(str5)) {
                        str6 = jSONObject.getString(str5);
                    }
                    int i5 = length - 1;
                    i = length;
                    i2 = i4;
                    if (i2 < i5) {
                        sb.append(",");
                    }
                    msgDO.msgIds = string2;
                    msgDO.extData = str6;
                    str3 = str5;
                    msgDO.messageSource = "accs";
                    msgDO.type = "cache";
                    if (TextUtils.isEmpty(string3)) {
                        msgDO.errorCode = AgooConstants.ACK_BODY_NULL;
                    } else if (TextUtils.isEmpty(string)) {
                        msgDO.errorCode = AgooConstants.ACK_PACK_NULL;
                    } else if (j == -1) {
                        msgDO.errorCode = AgooConstants.ACK_FLAG_NULL;
                    } else if (!m3908a(f4099a, string)) {
                        ALog.m3725d("MessageService", "ondata checkpackage is del,pack=" + string, new Object[0]);
                        String str7 = ",";
                        UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.dealMessage", AdapterUtilityImpl.getDeviceId(f4099a), "deletePack", string);
                        sb3.append(string);
                        sb2.append(string2);
                        msgDO.removePacks = string;
                        if (i2 < i5) {
                            sb3.append(str7);
                            sb2.append(str7);
                        }
                    } else {
                        String string4 = m3905a(j, msgDO).getString(AgooConstants.MESSAGE_ENCRYPTED);
                        if (!f4099a.getPackageName().equals(string)) {
                            z = true;
                        } else if (TextUtils.equals(Integer.toString(0), string4) || TextUtils.equals(Integer.toString(4), string4)) {
                            z = false;
                        } else {
                            msgDO.errorCode = AgooConstants.ACK_PACK_ERROR;
                            ALog.m3727e("MessageService", "error encrypted: " + string4, new Object[0]);
                        }
                        msgDO.agooFlag = z;
                        if (!TextUtils.isEmpty(str2)) {
                            msgDO.msgStatus = str2;
                            i3 = i2 + 1;
                            length = i;
                            str5 = str3;
                        }
                    }
                }
                String str8 = str2;
                i3 = i2 + 1;
                length = i;
                str5 = str3;
            }
        } catch (Throwable th) {
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.m3727e("MessageService", "createMsg is error,e: " + th, new Object[0]);
            }
        }
        return msgDO;
    }

    /* renamed from: a */
    public static final boolean m3908a(Context context, String str) {
        try {
            if (context.getPackageManager().getApplicationInfo(str, 0) != null) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    private static Bundle m3905a(long j, MsgDO msgDO) {
        Bundle bundle = new Bundle();
        try {
            char[] charArray = Long.toBinaryString(j).toCharArray();
            if (charArray != null && 8 <= charArray.length) {
                if (8 <= charArray.length) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(Integer.parseInt("" + charArray[1] + charArray[2] + charArray[3] + charArray[4], 2));
                    bundle.putString(AgooConstants.MESSAGE_ENCRYPTED, sb.toString());
                    if (charArray[6] == '1') {
                        bundle.putString(AgooConstants.MESSAGE_REPORT, "1");
                        msgDO.reportStr = "1";
                    }
                    if (charArray[7] == '1') {
                        bundle.putString(AgooConstants.MESSAGE_NOTIFICATION, "1");
                    }
                }
                if (9 <= charArray.length && charArray[8] == '1') {
                    bundle.putString(AgooConstants.MESSAGE_HAS_TEST, "1");
                }
                if (10 <= charArray.length && charArray[9] == '1') {
                    bundle.putString("duplicate", "1");
                }
                if (11 <= charArray.length && charArray[10] == '1') {
                    bundle.putInt(AgooConstants.MESSAGE_POPUP, 1);
                }
            }
        } catch (Throwable unused) {
        }
        return bundle;
    }
}
