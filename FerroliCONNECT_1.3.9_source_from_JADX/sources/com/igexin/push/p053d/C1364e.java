package com.igexin.push.p053d;

import android.content.Intent;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.util.C1576a;

/* renamed from: com.igexin.push.d.e */
public class C1364e implements C1363d {

    /* renamed from: a */
    private String f2234a;

    /* renamed from: b */
    private String f2235b;

    public C1364e(String str, String str2) {
        this.f2234a = str;
        this.f2235b = str2;
    }

    /* renamed from: a */
    public void mo14806a() {
        String str;
        if (!TextUtils.isEmpty(this.f2234a) && !TextUtils.isEmpty(this.f2235b)) {
            try {
                Intent intent = new Intent();
                intent.setPackage(this.f2234a);
                intent.setAction("com.igexin.sdk.action.service.message");
                if (C1576a.m3203a(intent, C1343f.f2169f)) {
                    C1343f.f2169f.startService(intent);
                    str = "ServiceGuardTask|startService by action";
                } else {
                    Intent intent2 = new Intent();
                    intent2.setClassName(this.f2234a, this.f2235b);
                    C1343f.f2169f.startService(intent2);
                    str = "ServiceGuardTask|startService by service name";
                }
                C1179b.m1354a(str);
            } catch (Throwable th) {
                C1179b.m1354a("ServiceGuardTask|startService pkgName = " + this.f2234a + " srvName = " + this.f2235b + ", exception : " + th.toString());
            }
        }
    }
}
