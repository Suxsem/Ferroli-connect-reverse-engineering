package com.igexin.push.extension;

import android.content.Context;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.stub.IPushExtension;
import com.igexin.push.util.C1593r;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.igexin.push.extension.a */
public class C1395a {

    /* renamed from: a */
    private static String f2342a = "com.igexin.push.extension.a";

    /* renamed from: c */
    private static C1395a f2343c;

    /* renamed from: b */
    private List<IPushExtension> f2344b = new ArrayList();

    private C1395a() {
    }

    /* renamed from: a */
    public static C1395a m2261a() {
        if (f2343c == null) {
            f2343c = new C1395a();
        }
        return f2343c;
    }

    /* renamed from: c */
    private void m2262c() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("com.igexin.push.extension.distribution.basic.stub.PushExtension");
        if (C1593r.m3267a(C1343f.f2169f)) {
            C1179b.m1354a(f2342a + "|pri_authorized = true");
            arrayList.add("com.igexin.push.extension.distribution.gbd.stub.PushExtension");
        } else {
            C1179b.m1354a(f2342a + "|pri_authorized = false");
        }
        for (String str : arrayList) {
            try {
                IPushExtension iPushExtension = (IPushExtension) Class.forName(str).newInstance();
                iPushExtension.init(C1343f.f2169f);
                this.f2344b.add(iPushExtension);
                C1179b.m1354a("init " + str);
            } catch (Exception e) {
                C1179b.m1354a(f2342a + e.toString());
            }
        }
    }

    /* renamed from: a */
    public boolean mo14845a(Context context) {
        try {
            m2262c();
            return true;
        } catch (Throwable th) {
            C1179b.m1354a(f2342a + "|" + th.toString());
            return false;
        }
    }

    /* renamed from: b */
    public List<IPushExtension> mo14846b() {
        return this.f2344b;
    }
}
