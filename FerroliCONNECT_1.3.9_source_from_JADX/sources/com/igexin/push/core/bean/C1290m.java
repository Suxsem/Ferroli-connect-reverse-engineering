package com.igexin.push.core.bean;

import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;

/* renamed from: com.igexin.push.core.bean.m */
public class C1290m extends BaseAction {

    /* renamed from: a */
    private String f1997a;

    /* renamed from: b */
    private boolean f1998b;

    /* renamed from: c */
    private boolean f1999c;

    /* renamed from: d */
    private String f2000d;

    /* renamed from: a */
    public String mo14617a() {
        return this.f1997a;
    }

    /* renamed from: a */
    public void mo14618a(String str) {
        this.f1997a = str;
    }

    /* renamed from: a */
    public void mo14619a(boolean z) {
        this.f1998b = z;
    }

    /* renamed from: b */
    public String mo14620b() {
        return this.f2000d;
    }

    /* renamed from: b */
    public void mo14621b(String str) {
        this.f2000d = str;
    }

    /* renamed from: b */
    public void mo14622b(boolean z) {
        this.f1999c = z;
    }

    /* renamed from: c */
    public String mo14623c() {
        String j;
        StringBuilder sb;
        String str;
        StringBuilder sb2;
        String str2;
        String str3 = this.f1997a;
        if (this.f1998b) {
            if (str3.indexOf("?") > 0) {
                sb2 = new StringBuilder();
                sb2.append(str3);
                str2 = "&cid=";
            } else {
                sb2 = new StringBuilder();
                sb2.append(str3);
                str2 = "?cid=";
            }
            sb2.append(str2);
            sb2.append(C1343f.f2182s);
            str3 = sb2.toString();
        }
        if (!this.f1999c || (j = C1340e.m2032a().mo14713j()) == null) {
            return str3;
        }
        if (str3.indexOf("?") > 0) {
            sb = new StringBuilder();
            sb.append(str3);
            str = "&nettype=";
        } else {
            sb = new StringBuilder();
            sb.append(str3);
            str = "?nettype=";
        }
        sb.append(str);
        sb.append(j);
        return sb.toString();
    }
}
