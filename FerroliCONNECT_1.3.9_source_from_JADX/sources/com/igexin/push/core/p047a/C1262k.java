package com.igexin.push.core.p047a;

import android.text.TextUtils;
import com.igexin.assist.sdk.C1146a;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.config.C1234k;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.C1238a;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1353p;
import com.igexin.push.core.p050c.C1304ai;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.core.p051d.C1337f;
import com.igexin.push.p046c.C1214i;
import com.igexin.push.p054e.C1368b;
import com.igexin.push.p054e.p057c.C1384m;
import com.igexin.push.p088g.p089a.C1564c;
import com.igexin.push.util.C1581f;
import com.igexin.push.util.C1593r;
import com.taobao.accs.common.Constants;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.core.a.k */
public class C1262k extends C1239a {
    /* renamed from: b */
    private void m1751b() {
        C1368b.m2191a().mo14822f();
        C1179b.m1354a("loginRsp|" + C1343f.f2182s + "|success");
        StringBuilder sb = new StringBuilder();
        sb.append("isCidBroadcasted|");
        sb.append(C1343f.f2176m);
        C1179b.m1354a(sb.toString());
        if (!C1343f.f2176m) {
            C1238a.m1630a().mo14461c();
            C1343f.f2176m = true;
        }
        C1343f.f2175l = true;
        C1238a.m1630a().mo14458b();
        C1257f.m1711a().mo14490d();
        if (TextUtils.isEmpty(C1343f.f2187x)) {
            C1179b.m1354a("LoginResultAction device id is empty, get device id from server +++++");
            C1257f.m1711a().mo14491e();
        }
        C1581f.m3242f();
        if (C1593r.m3267a(C1343f.f2169f)) {
            m1754e();
        }
        mo14498a();
        m1753d();
        C1312h.m1937a().mo14671b();
        m1755f();
        m1756g();
        m1757h();
    }

    /* renamed from: c */
    private void m1752c() {
        C1179b.m1354a("loginRsp|" + C1343f.f2182s + "|failed");
        C1179b.m1354a("LoginResultAction login failed, clear session or cid");
        C1312h.m1937a().mo14677c();
        C1353p.m2098a().mo14750c();
    }

    /* renamed from: d */
    private void m1753d() {
        try {
            if ((System.currentTimeMillis() - C1343f.f2117I) - Constants.CLIENT_FLUSH_INTERVAL > 0) {
                C1174c.m1310b().mo14317a(new C1564c(new C1337f(SDKUrlConfig.getConfigServiceUrl())), false, true);
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: e */
    private void m1754e() {
        if ((System.currentTimeMillis() - C1343f.f2115G) - 259200000 >= 0 && C1234k.f1846g) {
            C1174c.m1310b().mo14317a(new C1264m(this), false, true);
        }
    }

    /* renamed from: f */
    private void m1755f() {
        if (!C1343f.f2182s.equals(C1343f.f2183t)) {
            C1343f.f2183t = C1343f.f2182s;
        }
    }

    /* renamed from: g */
    private void m1756g() {
        try {
            if (System.currentTimeMillis() - C1343f.f2119K > Constants.CLIENT_FLUSH_INTERVAL) {
                C1312h.m1937a().mo14690g(System.currentTimeMillis());
                C1304ai.m1896a().mo14649b(AgooConstants.REPORT_MESSAGE_NULL);
            }
        } catch (Throwable th) {
            C1179b.m1354a("LoginResultAction|report third party guard exception :" + th.toString());
        }
    }

    /* renamed from: h */
    private void m1757h() {
        if (C1146a.m1225c(C1343f.f2169f)) {
            C1174c.m1310b().mo14317a(new C1265n(this), false, true);
        }
    }

    /* renamed from: a */
    public void mo14498a() {
        boolean z = (System.currentTimeMillis() - C1343f.f2114F) - Constants.CLIENT_FLUSH_INTERVAL > 0;
        boolean z2 = !C1196a.m1436a(C1343f.f2189z, C1343f.f2188y);
        boolean equals = true ^ C1343f.f2182s.equals(C1343f.f2183t);
        if (!z && !z2 && !equals) {
            return;
        }
        if (TextUtils.isEmpty(C1343f.f2187x)) {
            if (C1343f.f2154ap != null) {
                C1343f.f2154ap.mo14309t();
                C1343f.f2154ap = null;
            }
            C1343f.f2154ap = new C1263l(this, 5000);
            C1340e.m2032a().mo14703a(C1343f.f2154ap);
            return;
        }
        C1257f.m1711a().mo14492f();
    }

    /* renamed from: a */
    public boolean mo14464a(C1190e eVar) {
        return false;
    }

    /* renamed from: a */
    public boolean mo14465a(Object obj) {
        if (!(obj instanceof C1384m)) {
            return true;
        }
        C1343f.f2112D = 0;
        if (C1343f.f2175l) {
            return true;
        }
        C1214i.m1500a().mo14385e().mo14374i();
        if (((C1384m) obj).f2315a) {
            m1751b();
            return true;
        }
        m1752c();
        return true;
    }
}
