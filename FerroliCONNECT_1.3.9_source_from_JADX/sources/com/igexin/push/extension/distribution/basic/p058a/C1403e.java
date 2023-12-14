package com.igexin.push.extension.distribution.basic.p058a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.p048a.C1240a;
import com.igexin.push.extension.distribution.basic.p060b.C1409d;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import com.taobao.accs.common.Constants;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.basic.a.e */
public class C1403e implements C1240a {

    /* renamed from: a */
    private static final String f2361a = ("EXT-" + C1403e.class.getName());

    /* renamed from: a */
    private void m2291a(String str) {
        if (C1416f.f2441s.mo14970a()) {
            try {
                C1179b.m1354a(f2361a + "|del condition taskid = " + str);
                C1416f.f2441s.mo14969a(Constants.SHARED_MESSAGE_ID_FILE, new String[]{"taskid"}, new String[]{str});
                C1416f.f2441s.close();
            } catch (Throwable th) {
                C1179b.m1354a(f2361a + "|del condition" + th.toString());
            }
        }
    }

    /* renamed from: a */
    public BaseAction mo14466a(JSONObject jSONObject) {
        try {
            if (!jSONObject.has("do") || !jSONObject.has("actionid") || !jSONObject.has("taskid")) {
                return null;
            }
            C1409d dVar = new C1409d();
            dVar.setType("terminatetask");
            dVar.setActionId(jSONObject.getString("actionid"));
            dVar.setDoActionId(jSONObject.getString("do"));
            dVar.mo14926a(jSONObject.getString("taskid"));
            dVar.mo14927a(jSONObject.optBoolean("force"));
            return dVar;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public C1294c mo14467a(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.success;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a9, code lost:
        if (r7 == null) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ab, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b8, code lost:
        if (r7 != null) goto L_0x00ab;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b3  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14468b(com.igexin.push.core.bean.PushTaskBean r16, com.igexin.push.core.bean.BaseAction r17) {
        /*
            r15 = this;
            r0 = r17
            com.igexin.push.extension.distribution.basic.b.d r0 = (com.igexin.push.extension.distribution.basic.p060b.C1409d) r0
            java.lang.String r1 = r0.mo14925a()
            android.content.Context r2 = com.igexin.push.core.C1343f.f2169f
            java.lang.String r3 = "notification"
            java.lang.Object r2 = r2.getSystemService(r3)
            android.app.NotificationManager r2 = (android.app.NotificationManager) r2
            r4 = 0
            r5 = 1
            java.lang.String r6 = ""
            if (r1 == 0) goto L_0x00bb
            boolean r7 = r1.equals(r6)
            if (r7 != 0) goto L_0x00bb
            r7 = 0
            com.igexin.push.core.e r8 = com.igexin.push.core.C1340e.m2032a()     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            com.igexin.push.b.b r9 = r8.mo14712i()     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            java.lang.String r10 = "message"
            java.lang.String r8 = "taskid"
            java.lang.String[] r11 = new java.lang.String[]{r8}     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            java.lang.String[] r12 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            r12[r4] = r1     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            r13 = 0
            java.lang.String r14 = "id ASC"
            android.database.Cursor r7 = r9.mo14355a(r10, r11, r12, r13, r14)     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            if (r7 == 0) goto L_0x00a8
            boolean r8 = r7.moveToFirst()     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            if (r8 == 0) goto L_0x0055
            java.lang.String r8 = "messageid"
            int r8 = r7.getColumnIndex(r8)     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            java.lang.String r8 = r7.getString(r8)     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            com.igexin.push.core.a.f r9 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            java.lang.String r8 = r9.mo14472a((java.lang.String) r1, (java.lang.String) r8)     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            goto L_0x0056
        L_0x0055:
            r8 = r6
        L_0x0056:
            boolean r9 = r8.equals(r6)     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            if (r9 != 0) goto L_0x00a8
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r9 = com.igexin.push.core.C1343f.f2142ad     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            java.lang.Object r8 = r9.get(r8)     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            com.igexin.push.core.bean.PushTaskBean r8 = (com.igexin.push.core.bean.PushTaskBean) r8     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            if (r8 == 0) goto L_0x00a8
            r8.setStop(r5)     // Catch:{ Throwable -> 0x00b7, all -> 0x00af }
            r9 = r15
            r15.m2291a((java.lang.String) r1)     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            int r10 = r8.getPerActionid()     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            java.lang.String r11 = "0"
            boolean r11 = r10.equals(r11)     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            if (r11 != 0) goto L_0x00a9
            com.igexin.push.core.bean.BaseAction r8 = r8.getBaseAction(r10)     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            java.lang.String r8 = r8.getType()     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            boolean r3 = r8.equals(r3)     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            if (r3 == 0) goto L_0x00a9
            java.util.Map<java.lang.String, java.lang.Integer> r3 = com.igexin.push.core.C1343f.f2143ae     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            boolean r3 = r3.containsKey(r1)     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            if (r3 == 0) goto L_0x00a9
            java.util.Map<java.lang.String, java.lang.Integer> r3 = com.igexin.push.core.C1343f.f2143ae     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            java.lang.Object r1 = r3.get(r1)     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            int r1 = r1.intValue()     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            r2.cancel(r1)     // Catch:{ Throwable -> 0x00a6, all -> 0x00a4 }
            r4 = 1
            goto L_0x00a9
        L_0x00a4:
            r0 = move-exception
            goto L_0x00b1
        L_0x00a6:
            goto L_0x00b8
        L_0x00a8:
            r9 = r15
        L_0x00a9:
            if (r7 == 0) goto L_0x00bc
        L_0x00ab:
            r7.close()
            goto L_0x00bc
        L_0x00af:
            r0 = move-exception
            r9 = r15
        L_0x00b1:
            if (r7 == 0) goto L_0x00b6
            r7.close()
        L_0x00b6:
            throw r0
        L_0x00b7:
            r9 = r15
        L_0x00b8:
            if (r7 == 0) goto L_0x00bc
            goto L_0x00ab
        L_0x00bb:
            r9 = r15
        L_0x00bc:
            if (r4 != 0) goto L_0x00e2
            boolean r0 = r0.mo14928b()     // Catch:{ Throwable -> 0x00e2 }
            if (r0 == 0) goto L_0x00e2
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e2 }
            r0.<init>()     // Catch:{ Throwable -> 0x00e2 }
            java.lang.String r1 = f2361a     // Catch:{ Throwable -> 0x00e2 }
            r0.append(r1)     // Catch:{ Throwable -> 0x00e2 }
            java.lang.String r1 = " | cancelAll()"
            r0.append(r1)     // Catch:{ Throwable -> 0x00e2 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00e2 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Throwable -> 0x00e2 }
            r2.cancelAll()     // Catch:{ Throwable -> 0x00e2 }
            android.content.Context r0 = com.igexin.push.core.C1343f.f2169f     // Catch:{ Throwable -> 0x00e2 }
            com.igexin.push.extension.distribution.basic.p068j.C1433a.m2500c(r0)     // Catch:{ Throwable -> 0x00e2 }
        L_0x00e2:
            java.lang.String r0 = r17.getDoActionId()
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x00ff
            com.igexin.push.core.a.f r0 = com.igexin.push.core.p047a.C1257f.m1711a()
            java.lang.String r1 = r16.getTaskId()
            java.lang.String r2 = r16.getMessageId()
            java.lang.String r3 = r17.getDoActionId()
            r0.mo14482a((java.lang.String) r1, (java.lang.String) r2, (java.lang.String) r3)
        L_0x00ff:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.p058a.C1403e.mo14468b(com.igexin.push.core.bean.PushTaskBean, com.igexin.push.core.bean.BaseAction):boolean");
    }
}
