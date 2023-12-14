package com.taobao.agoo.p105a.p106a;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility;

/* renamed from: com.taobao.agoo.a.a.c */
/* compiled from: Taobao */
public class C2126c extends C2125b {
    public static final String JSON_CMD_REGISTER = "register";

    /* renamed from: a */
    public String f3585a;

    /* renamed from: b */
    public String f3586b;

    /* renamed from: c */
    public String f3587c;

    /* renamed from: d */
    public String f3588d = String.valueOf(Constants.SDK_VERSION_CODE);

    /* renamed from: f */
    public String f3589f;

    /* renamed from: g */
    public String f3590g;

    /* renamed from: h */
    public String f3591h;

    /* renamed from: i */
    public String f3592i;

    /* renamed from: j */
    public String f3593j;

    /* renamed from: k */
    public String f3594k;

    /* renamed from: l */
    public String f3595l;

    /* renamed from: m */
    public String f3596m;

    /* renamed from: n */
    public String f3597n;

    /* renamed from: o */
    public String f3598o;

    /* renamed from: p */
    public String f3599p;

    /* renamed from: a */
    public byte[] mo18621a() {
        try {
            String jSONObject = new JsonUtility.JsonObjectBuilder().put(C2125b.JSON_CMD, this.f3584e).put(Constants.KEY_APP_KEY, this.f3585a).put("utdid", this.f3586b).put("appVersion", this.f3587c).put(Constants.KEY_SDK_VERSION, this.f3588d).put(Constants.KEY_TTID, this.f3589f).put(Constants.KEY_PACKAGE_NAME, this.f3590g).put("notifyEnable", this.f3591h).put("romInfo", this.f3592i).put("c0", this.f3593j).put("c1", this.f3594k).put("c2", this.f3595l).put("c3", this.f3596m).put("c4", this.f3597n).put("c5", this.f3598o).put("c6", this.f3599p).build().toString();
            ALog.m3728i("RegisterDO", "buildData", Constants.KEY_DATA, jSONObject);
            return jSONObject.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.m3726e("RegisterDO", "buildData", th, new Object[0]);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0097  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m3833a(android.content.Context r9, java.lang.String r10, java.lang.String r11) {
        /*
            java.lang.String r0 = "RegisterDO"
            r1 = 0
            r2 = 1
            r3 = 0
            java.lang.String r4 = com.taobao.accs.utl.UtilityImpl.getDeviceId(r9)     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            java.lang.String r5 = r9.getPackageName()     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            com.taobao.accs.client.GlobalClientInfo r6 = com.taobao.accs.client.GlobalClientInfo.getInstance(r9)     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            android.content.pm.PackageInfo r6 = r6.getPackageInfo()     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            java.lang.String r6 = r6.versionName     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            boolean r7 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            if (r7 != 0) goto L_0x005c
            boolean r7 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            if (r7 != 0) goto L_0x005c
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            if (r7 == 0) goto L_0x002a
            goto L_0x005c
        L_0x002a:
            com.taobao.agoo.a.a.c r7 = new com.taobao.agoo.a.a.c     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            r7.<init>()     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            java.lang.String r8 = "register"
            r7.f3584e = r8     // Catch:{ Throwable -> 0x005a }
            r7.f3585a = r10     // Catch:{ Throwable -> 0x005a }
            r7.f3586b = r4     // Catch:{ Throwable -> 0x005a }
            r7.f3587c = r6     // Catch:{ Throwable -> 0x005a }
            r7.f3589f = r11     // Catch:{ Throwable -> 0x005a }
            r7.f3590g = r5     // Catch:{ Throwable -> 0x005a }
            java.lang.String r10 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x005a }
            r7.f3593j = r10     // Catch:{ Throwable -> 0x005a }
            java.lang.String r10 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x005a }
            r7.f3594k = r10     // Catch:{ Throwable -> 0x005a }
            r7.f3595l = r3     // Catch:{ Throwable -> 0x005a }
            r7.f3596m = r3     // Catch:{ Throwable -> 0x005a }
            java.lang.String r9 = com.taobao.accs.utl.AdapterUtilityImpl.isNotificationEnabled(r9)     // Catch:{ Throwable -> 0x005a }
            r7.f3591h = r9     // Catch:{ Throwable -> 0x005a }
            com.taobao.accs.utl.RomInfoCollecter r9 = com.taobao.accs.utl.RomInfoCollecter.getCollecter()     // Catch:{ Throwable -> 0x005a }
            java.lang.String r9 = r9.collect()     // Catch:{ Throwable -> 0x005a }
            r7.f3592i = r9     // Catch:{ Throwable -> 0x005a }
            goto L_0x008f
        L_0x005a:
            r9 = move-exception
            goto L_0x0080
        L_0x005c:
            java.lang.String r9 = "buildRegister param null"
            r11 = 6
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            java.lang.String r5 = "appKey"
            r11[r1] = r5     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            r11[r2] = r10     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            r10 = 2
            java.lang.String r5 = "utdid"
            r11[r10] = r5     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            r10 = 3
            r11[r10] = r4     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            r10 = 4
            java.lang.String r4 = "appVersion"
            r11[r10] = r4     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            r10 = 5
            r11[r10] = r6     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            com.taobao.accs.utl.ALog.m3727e(r0, r9, r11)     // Catch:{ Throwable -> 0x007e, all -> 0x007b }
            return r3
        L_0x007b:
            r9 = move-exception
            r7 = r3
            goto L_0x0095
        L_0x007e:
            r9 = move-exception
            r7 = r3
        L_0x0080:
            java.lang.String r10 = "buildRegister"
            java.lang.Object[] r11 = new java.lang.Object[r2]     // Catch:{ all -> 0x0094 }
            java.lang.String r9 = r9.getMessage()     // Catch:{ all -> 0x0094 }
            r11[r1] = r9     // Catch:{ all -> 0x0094 }
            com.taobao.accs.utl.ALog.m3731w(r0, r10, r11)     // Catch:{ all -> 0x0094 }
            if (r7 == 0) goto L_0x0093
        L_0x008f:
            byte[] r3 = r7.mo18621a()
        L_0x0093:
            return r3
        L_0x0094:
            r9 = move-exception
        L_0x0095:
            if (r7 == 0) goto L_0x009a
            r7.mo18621a()
        L_0x009a:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.p105a.p106a.C2126c.m3833a(android.content.Context, java.lang.String, java.lang.String):byte[]");
    }
}
