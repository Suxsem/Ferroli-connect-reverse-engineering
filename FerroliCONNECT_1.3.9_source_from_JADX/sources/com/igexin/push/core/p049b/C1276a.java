package com.igexin.push.core.p049b;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.C1292o;
import com.igexin.push.core.p051d.C1332a;
import com.igexin.push.p088g.p089a.C1564c;
import com.igexin.push.util.C1593r;
import com.igexin.sdk.PushConsts;
import com.p107tb.appyunsdk.Constant;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.b.a */
public class C1276a {

    /* renamed from: a */
    private static C1276a f1917a;

    private C1276a() {
    }

    /* renamed from: a */
    public static C1276a m1778a() {
        if (f1917a == null) {
            f1917a = new C1276a();
        }
        return f1917a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0050 A[SYNTHETIC, Splitter:B:19:0x0050] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0057 A[SYNTHETIC, Splitter:B:25:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m1779a(java.lang.String r4) {
        /*
            r3 = this;
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r2 = com.igexin.push.core.C1343f.f2130V     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            if (r2 != 0) goto L_0x0032
            boolean r2 = r1.createNewFile()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            if (r2 != 0) goto L_0x0032
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r4.<init>()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r2 = "create file "
            r4.append(r2)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r4.append(r1)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r1 = " failed######"
            r4.append(r1)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r4)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            return
        L_0x0032:
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r2 = com.igexin.push.core.C1343f.f2130V     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r4 = com.igexin.p032b.p042b.C1196a.m1435a((java.lang.String) r4)     // Catch:{ Exception -> 0x004b, all -> 0x0048 }
            byte[] r4 = r4.getBytes()     // Catch:{ Exception -> 0x004b, all -> 0x0048 }
            r1.write(r4)     // Catch:{ Exception -> 0x004b, all -> 0x0048 }
            r1.close()     // Catch:{ Exception -> 0x005a }
            goto L_0x005a
        L_0x0048:
            r4 = move-exception
            r0 = r1
            goto L_0x004e
        L_0x004b:
            r0 = r1
            goto L_0x0055
        L_0x004d:
            r4 = move-exception
        L_0x004e:
            if (r0 == 0) goto L_0x0053
            r0.close()     // Catch:{ Exception -> 0x0053 }
        L_0x0053:
            throw r4
        L_0x0054:
        L_0x0055:
            if (r0 == 0) goto L_0x005a
            r0.close()     // Catch:{ Exception -> 0x005a }
        L_0x005a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p049b.C1276a.m1779a(java.lang.String):void");
    }

    /* renamed from: a */
    private void m1780a(List<C1292o> list) {
        if (C1593r.m3267a(C1343f.f2169f)) {
            C1277b bVar = new C1277b(this);
            PackageManager packageManager = C1343f.f2169f.getPackageManager();
            List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
            for (int i = 0; i < installedPackages.size(); i++) {
                try {
                    PackageInfo packageInfo = installedPackages.get(i);
                    ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                    if ((applicationInfo.flags & 1) <= 0) {
                        C1292o oVar = new C1292o();
                        oVar.mo14631b(applicationInfo.loadLabel(packageManager).toString());
                        oVar.mo14635d(applicationInfo.packageName);
                        oVar.mo14633c(String.valueOf(packageInfo.versionCode));
                        oVar.mo14629a(packageInfo.versionName);
                        list.add(oVar);
                    }
                } catch (Exception unused) {
                }
            }
            Collections.sort(list, bVar);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(12:3|4|5|6|(3:7|8|(1:10)(1:45))|11|12|13|14|15|16|49) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0036 */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0048 A[SYNTHETIC, Splitter:B:27:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x004f A[SYNTHETIC, Splitter:B:31:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0057 A[SYNTHETIC, Splitter:B:38:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x005e A[SYNTHETIC, Splitter:B:42:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String mo14499b() {
        /*
            r6 = this;
            java.io.File r0 = new java.io.File
            java.lang.String r1 = com.igexin.push.core.C1343f.f2130V
            r0.<init>(r1)
            boolean r0 = r0.exists()
            r1 = 0
            if (r0 == 0) goto L_0x0061
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0053, all -> 0x0044 }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2130V     // Catch:{ Exception -> 0x0053, all -> 0x0044 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0053, all -> 0x0044 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0042, all -> 0x0040 }
            r3.<init>()     // Catch:{ Exception -> 0x0042, all -> 0x0040 }
        L_0x001e:
            int r4 = r2.read(r0)     // Catch:{ Exception -> 0x003e, all -> 0x003b }
            r5 = -1
            if (r4 == r5) goto L_0x002a
            r5 = 0
            r3.write(r0, r5, r4)     // Catch:{ Exception -> 0x003e, all -> 0x003b }
            goto L_0x001e
        L_0x002a:
            byte[] r0 = r3.toByteArray()     // Catch:{ Exception -> 0x003e, all -> 0x003b }
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x003e, all -> 0x003b }
            r4.<init>(r0)     // Catch:{ Exception -> 0x003e, all -> 0x003b }
            r2.close()     // Catch:{ Exception -> 0x0036 }
        L_0x0036:
            r3.close()     // Catch:{ Exception -> 0x0039 }
        L_0x0039:
            r1 = r4
            goto L_0x0061
        L_0x003b:
            r0 = move-exception
            r1 = r3
            goto L_0x0046
        L_0x003e:
            goto L_0x0055
        L_0x0040:
            r0 = move-exception
            goto L_0x0046
        L_0x0042:
            r3 = r1
            goto L_0x0055
        L_0x0044:
            r0 = move-exception
            r2 = r1
        L_0x0046:
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Exception -> 0x004c }
            goto L_0x004d
        L_0x004c:
        L_0x004d:
            if (r1 == 0) goto L_0x0052
            r1.close()     // Catch:{ Exception -> 0x0052 }
        L_0x0052:
            throw r0
        L_0x0053:
            r2 = r1
            r3 = r2
        L_0x0055:
            if (r2 == 0) goto L_0x005c
            r2.close()     // Catch:{ Exception -> 0x005b }
            goto L_0x005c
        L_0x005b:
        L_0x005c:
            if (r3 == 0) goto L_0x0061
            r3.close()     // Catch:{ Exception -> 0x0061 }
        L_0x0061:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p049b.C1276a.mo14499b():java.lang.String");
    }

    /* renamed from: c */
    public void mo14500c() {
        ArrayList arrayList = new ArrayList();
        m1780a((List<C1292o>) arrayList);
        if (!arrayList.isEmpty()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PushConsts.CMD_ACTION, "reportapplist");
                jSONObject.put("session_last", C1343f.f2181r);
                JSONArray jSONArray = new JSONArray();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("appid", ((C1292o) arrayList.get(i)).mo14634d());
                    jSONObject2.put(Constant.NAME, ((C1292o) arrayList.get(i)).mo14630b());
                    jSONObject2.put(Constants.SP_KEY_VERSION, ((C1292o) arrayList.get(i)).mo14632c());
                    jSONObject2.put("versionName", ((C1292o) arrayList.get(i)).mo14628a());
                    jSONArray.put(jSONObject2);
                }
                jSONObject.put("applist", jSONArray);
            } catch (Exception unused) {
            }
            C1174c.m1310b().mo14317a(new C1564c(new C1332a(SDKUrlConfig.getBiUploadServiceUrl(), jSONObject.toString().getBytes())), false, true);
            m1779a(m1778a().mo14501d());
            C1179b.m1354a("reportapplist");
        }
    }

    /* renamed from: d */
    public String mo14501d() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        m1780a((List<C1292o>) arrayList2);
        int size = arrayList2.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                arrayList.add(((C1292o) arrayList2.get(i)).mo14634d());
            }
        }
        return arrayList.toString();
    }
}
