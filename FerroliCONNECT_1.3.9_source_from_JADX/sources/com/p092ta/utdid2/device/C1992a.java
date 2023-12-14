package com.p092ta.utdid2.device;

import android.content.Context;
import android.text.TextUtils;
import com.p092ta.p093a.C1964a;
import com.p092ta.p093a.p095b.C1971e;
import com.p092ta.p093a.p095b.C1974h;
import com.p092ta.p093a.p096c.C1977c;
import com.p092ta.p093a.p096c.C1982f;

/* renamed from: com.ta.utdid2.device.a */
public class C1992a {

    /* renamed from: a */
    private static final C1992a f3184a = new C1992a();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public static long f3185c = 3000;

    /* renamed from: d */
    private String f3186d = "";

    private C1992a() {
    }

    /* renamed from: a */
    public static C1992a m3386a() {
        return f3184a;
    }

    /* access modifiers changed from: package-private */
    public synchronized String getUtdid(Context context) {
        if (!TextUtils.isEmpty(this.f3186d)) {
            return this.f3186d;
        }
        try {
            C1977c.m3358c();
            String l = m3389l();
            if (TextUtils.isEmpty(l)) {
                l = C1996d.m3400a(context).getValue();
            }
            if (!TextUtils.isEmpty(l)) {
                this.f3186d = l;
                m3388f();
                String str = this.f3186d;
                C1977c.m3359d();
                return str;
            }
            C1977c.m3359d();
            return "ffffffffffffffffffffffff";
        } catch (Throwable th) {
            try {
                C1982f.m3367a("AppUtdid", th, new Object[0]);
                return "ffffffffffffffffffffffff";
            } finally {
                C1977c.m3359d();
            }
        }
    }

    /* renamed from: l */
    private String m3389l() {
        if (C1964a.mo18112a().getContext() == null) {
            return "";
        }
        String d = C1971e.m3344d();
        if (!C1996d.m3403c(d)) {
            return null;
        }
        C1982f.m3366a("AppUtdid", "read utdid from V5AppFile");
        C1996d.setType(7);
        return d;
    }

    /* renamed from: f */
    private void m3388f() {
        C1982f.m3372e();
        if (!TextUtils.isEmpty(this.f3186d)) {
            try {
                final Context context = C1964a.mo18112a().getContext();
                boolean c = C1995c.m3394c(context);
                C1982f.m3366a("", "isMainProcess", Boolean.valueOf(c));
                if (c) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(C1992a.f3185c);
                            } catch (Exception unused) {
                            }
                            if (!C1971e.m3339a(context)) {
                                C1982f.m3366a("", "unable upload!");
                                return;
                            }
                            new C1974h(context).run();
                        }
                    }).start();
                }
            } catch (Throwable th) {
                C1982f.m3366a("", th);
            }
        }
    }

    /* renamed from: m */
    public synchronized String mo18145m() {
        return this.f3186d;
    }
}
