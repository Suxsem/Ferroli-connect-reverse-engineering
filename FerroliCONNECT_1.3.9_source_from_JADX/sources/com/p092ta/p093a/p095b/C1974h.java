package com.p092ta.p093a.p095b;

import android.content.Context;
import android.text.TextUtils;
import com.p092ta.p093a.p094a.C1965a;
import com.p092ta.p093a.p094a.C1966b;
import com.p092ta.p093a.p096c.C1978d;
import com.p092ta.p093a.p096c.C1982f;
import com.p092ta.utdid2.device.C1992a;
import com.p092ta.utdid2.device.C1997e;

/* renamed from: com.ta.a.b.h */
public class C1974h implements Runnable {

    /* renamed from: a */
    private static volatile boolean f3159a = false;
    private Context mContext = null;

    public C1974h(Context context) {
        this.mContext = context;
    }

    public void run() {
        try {
            m3345a();
        } catch (Throwable th) {
            C1982f.m3367a("", th, new Object[0]);
        }
    }

    /* renamed from: a */
    private void m3345a() {
        C1982f.m3372e();
        if (C1978d.m3360b(this.mContext) && !f3159a) {
            f3159a = true;
            try {
                m3347b();
            } catch (Throwable th) {
                f3159a = false;
                throw th;
            }
            f3159a = false;
        }
    }

    /* renamed from: a */
    private boolean m3346a(String str) {
        C1967a a = C1968b.m3338a("https://mpush-api.aliyun.com/v2.0/a/audid/req/", str, true);
        if (a == null) {
            return false;
        }
        return C1997e.m3407a(a);
    }

    /* renamed from: b */
    private void m3347b() {
        C1982f.m3372e();
        String e = m3348e();
        if (TextUtils.isEmpty(e)) {
            C1982f.m3366a("postData is empty", new Object[0]);
        } else if (m3346a(e)) {
            C1982f.m3366a("", "upload success");
        } else {
            C1982f.m3366a("", "upload fail");
        }
    }

    /* renamed from: e */
    private String m3348e() {
        String m = C1992a.m3386a().mo18145m();
        if (TextUtils.isEmpty(m)) {
            return null;
        }
        String a = C1965a.m3334a(m);
        if (C1982f.m3364a()) {
            C1982f.m3371b("", a);
        }
        return C1966b.m3336b(a);
    }
}
