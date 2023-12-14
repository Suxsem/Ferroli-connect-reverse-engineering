package com.igexin.push.core.p047a.p048a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.C1291n;
import com.igexin.push.core.bean.PushTaskBean;
import com.taobao.accs.common.Constants;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.a.k */
public class C1250k implements C1240a {

    /* renamed from: a */
    private static final String f1873a = C1233j.f1818a;

    /* renamed from: a */
    private void m1684a(String... strArr) {
        try {
            C1179b.m1354a(f1873a + "|del condition taskid = " + strArr.toString());
            C1340e.m2032a().mo14712i().mo14357a(Constants.SHARED_MESSAGE_ID_FILE, new String[]{"taskid"}, strArr);
        } catch (Throwable th) {
            C1179b.m1354a(f1873a + "|del condition" + th.toString());
        }
    }

    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (!jSONObject.has("do") || !jSONObject.has("actionid") || !jSONObject.has("taskid")) {
                return null;
            }
            C1291n nVar = new C1291n();
            nVar.setType("terminatetask");
            nVar.setActionId(jSONObject.getString("actionid"));
            nVar.setDoActionId(jSONObject.getString("do"));
            nVar.mo14625a(jSONObject.getString("taskid"));
            nVar.mo14626a(jSONObject.optBoolean("force"));
            return nVar;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008c, code lost:
        if (r3 != null) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008e, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0092, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0093, code lost:
        if (r3 != null) goto L_0x0095;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0095, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0098, code lost:
        throw r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009a, code lost:
        if (r3 != null) goto L_0x008e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0092 A[ExcHandler: all (r14v2 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r3 
      PHI: (r3v5 android.database.Cursor) = (r3v2 android.database.Cursor), (r3v7 android.database.Cursor), (r3v7 android.database.Cursor), (r3v7 android.database.Cursor) binds: [B:3:0x001a, B:10:0x0052, B:19:0x0083, B:20:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:3:0x001a] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14468b(com.igexin.push.core.bean.PushTaskBean r14, com.igexin.push.core.bean.BaseAction r15) {
        /*
            r13 = this;
            r0 = r15
            com.igexin.push.core.bean.n r0 = (com.igexin.push.core.bean.C1291n) r0
            java.lang.String r1 = r0.mo14624a()
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f
            java.lang.String r3 = "notification"
            java.lang.Object r2 = r2.getSystemService(r3)
            android.app.NotificationManager r2 = (android.app.NotificationManager) r2
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            r4 = 0
            r5 = 1
            if (r3 != 0) goto L_0x009d
            r3 = 0
            com.igexin.push.core.e r6 = com.igexin.push.core.C1340e.m2032a()     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            com.igexin.push.b.b r7 = r6.mo14712i()     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.lang.String r8 = "message"
            java.lang.String r6 = "taskid"
            java.lang.String[] r9 = new java.lang.String[]{r6}     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.lang.String[] r10 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            r10[r4] = r1     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            r11 = 0
            java.lang.String r12 = "id ASC"
            android.database.Cursor r3 = r7.mo14355a(r8, r9, r10, r11, r12)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            if (r3 == 0) goto L_0x008c
            boolean r6 = r3.moveToFirst()     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            if (r6 == 0) goto L_0x0050
            java.lang.String r6 = "messageid"
            int r6 = r3.getColumnIndex(r6)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.lang.String r6 = r3.getString(r6)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            com.igexin.push.core.a.f r7 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.lang.String r6 = r7.mo14472a((java.lang.String) r1, (java.lang.String) r6)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            goto L_0x0052
        L_0x0050:
            java.lang.String r6 = ""
        L_0x0052:
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            if (r7 != 0) goto L_0x008c
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r7 = com.igexin.push.core.C1343f.f2142ad     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.lang.Object r6 = r7.get(r6)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            com.igexin.push.core.bean.PushTaskBean r6 = (com.igexin.push.core.bean.PushTaskBean) r6     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            if (r6 == 0) goto L_0x0065
            r6.setStop(r5)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
        L_0x0065:
            java.lang.String[] r6 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            r6[r4] = r1     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            r13.m1684a((java.lang.String[]) r6)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.util.Map<java.lang.String, java.lang.Integer> r6 = com.igexin.push.core.C1343f.f2143ae     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            boolean r6 = r6.containsKey(r1)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            if (r6 == 0) goto L_0x008c
            java.util.Map<java.lang.String, java.lang.Integer> r6 = com.igexin.push.core.C1343f.f2143ae     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.lang.Object r6 = r6.get(r1)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.lang.Integer r6 = (java.lang.Integer) r6     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            int r6 = r6.intValue()     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            r2.cancel(r6)     // Catch:{ Throwable -> 0x0099, all -> 0x0092 }
            java.util.Set<java.lang.String> r4 = com.igexin.push.core.C1343f.f2144af     // Catch:{ Throwable -> 0x008a, all -> 0x0092 }
            r4.remove(r1)     // Catch:{ Throwable -> 0x008a, all -> 0x0092 }
            r4 = 1
            goto L_0x008c
        L_0x008a:
            r4 = 1
            goto L_0x009a
        L_0x008c:
            if (r3 == 0) goto L_0x009d
        L_0x008e:
            r3.close()
            goto L_0x009d
        L_0x0092:
            r14 = move-exception
            if (r3 == 0) goto L_0x0098
            r3.close()
        L_0x0098:
            throw r14
        L_0x0099:
        L_0x009a:
            if (r3 == 0) goto L_0x009d
            goto L_0x008e
        L_0x009d:
            if (r4 != 0) goto L_0x00be
            boolean r0 = r0.mo14627b()     // Catch:{ Throwable -> 0x00be }
            if (r0 == 0) goto L_0x00be
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00be }
            r0.<init>()     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = f1873a     // Catch:{ Throwable -> 0x00be }
            r0.append(r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r1 = " | cancelAll()"
            r0.append(r1)     // Catch:{ Throwable -> 0x00be }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00be }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Throwable -> 0x00be }
            r2.cancelAll()     // Catch:{ Throwable -> 0x00be }
        L_0x00be:
            java.lang.String r0 = r15.getDoActionId()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x00db
            com.igexin.push.core.r r0 = com.igexin.push.core.C1355r.m2114a()
            java.lang.String r1 = r14.getTaskId()
            java.lang.String r14 = r14.getMessageId()
            java.lang.String r15 = r15.getDoActionId()
            r0.mo14772b(r1, r14, r15)
        L_0x00db:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p047a.p048a.C1250k.mo14468b(com.igexin.push.core.bean.PushTaskBean, com.igexin.push.core.bean.BaseAction):boolean");
    }
}
