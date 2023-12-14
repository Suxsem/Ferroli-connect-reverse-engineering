package com.igexin.push.p087f;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p035b.p036a.p037a.C1161f;
import com.igexin.p032b.p033a.p035b.p036a.p037a.C1168m;
import com.igexin.p032b.p033a.p035b.p036a.p037a.C1172q;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.C1238a;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1347j;
import com.igexin.push.core.C1349l;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.p046c.C1206a;
import com.igexin.push.p046c.C1214i;
import com.igexin.push.p054e.C1368b;
import com.igexin.push.p054e.C1393h;
import com.igexin.push.p054e.p055a.C1367b;
import com.igexin.push.p054e.p056b.C1369a;
import com.igexin.push.p054e.p056b.C1370b;
import com.igexin.push.p054e.p057c.C1376e;
import com.igexin.push.p054e.p057c.C1377f;
import com.igexin.push.p054e.p057c.C1380i;
import com.igexin.push.p054e.p057c.C1382k;
import com.igexin.push.p088g.p090b.C1574g;
import com.igexin.push.util.C1576a;
import com.igexin.push.util.EncryptUtils;

/* renamed from: com.igexin.push.f.a */
public class C1560a {

    /* renamed from: a */
    private static String f2972a = "com.igexin.push.f.a";

    /* renamed from: b */
    private boolean f2973b;

    /* renamed from: b */
    private void m3100b(boolean z) {
        C1179b.m1354a(f2972a + "|call setActive, param active = " + z + "; this.active = " + this.f2973b);
        boolean z2 = this.f2973b;
        if (z2 != z) {
            this.f2973b = z;
            if (this.f2973b) {
                C1179b.m1354a(f2972a + "|active = true, start connect~~~~");
                mo15195e();
                return;
            }
            C1179b.m1354a(f2972a + "|active = false, disconnect...");
            mo15190a(true);
        } else if (z2 && !C1343f.f2175l && C1343f.f2112D > 1500) {
            C1179b.m1354a(f2972a + "|start active again, online = false, reset delay");
            C1343f.f2112D = 0;
            mo15194d();
        }
    }

    /* renamed from: a */
    public int mo15186a(String str, C1376e eVar) {
        return mo15187a(str, eVar, false);
    }

    /* renamed from: a */
    public int mo15187a(String str, C1376e eVar, boolean z) {
        if (str == null || eVar == null) {
            return -1;
        }
        if (!C1343f.f2175l && !(eVar instanceof C1380i) && !(eVar instanceof C1382k) && !(eVar instanceof C1377f)) {
            C1179b.m1354a("networkLayer|sendData|not online|" + eVar.getClass().getName());
            return -3;
        } else if (!this.f2973b) {
            return 0;
        } else {
            if (!z) {
                return C1174c.m1310b().mo14260a(SDKUrlConfig.getCmAddress(), 3, C1340e.m2032a().mo14709f(), eVar, true) == null ? -2 : 0;
            }
            return C1174c.m1310b().mo14263a(SDKUrlConfig.getCmAddress(), 3, C1340e.m2032a().mo14709f(), eVar, true, C1234k.f1844e > 0 ? C1234k.f1844e : 10, new C1393h()) == null ? -2 : 0;
        }
    }

    /* renamed from: a */
    public void mo15188a(C1172q qVar) {
        C1174c b;
        Object aVar;
        if (qVar == C1172q.TCP_IO_EXCEPTION) {
            b = C1174c.m1310b();
            aVar = new C1370b();
        } else if (qVar == C1172q.TCP_DISCONNECT_SUCCESS) {
            b = C1174c.m1310b();
            aVar = new C1369a();
        } else {
            return;
        }
        b.mo14319a(aVar);
        C1174c.m1310b().mo14268c();
    }

    /* renamed from: a */
    public void mo15189a(C1376e eVar) {
        if (eVar != null) {
            C1257f.m1711a().mo14481a(eVar);
        }
    }

    /* renamed from: a */
    public void mo15190a(boolean z) {
        C1179b.m1354a(f2972a + "|call -> disconnect, reset delay = " + z);
        if (z) {
            C1343f.f2112D = 0;
        }
        C1161f.m1252a().mo14241c();
    }

    /* renamed from: a */
    public boolean mo15191a() {
        return this.f2973b;
    }

    /* renamed from: b */
    public void mo15192b() {
        boolean z = C1343f.f2172i;
        boolean z2 = C1343f.f2173j;
        boolean a = C1576a.m3200a(System.currentTimeMillis());
        boolean b = C1576a.m3212b();
        if (z && z2 && !a && b) {
            m3100b(true);
        }
    }

    /* renamed from: c */
    public void mo15193c() {
        C1179b.m1354a(f2972a + "|stop by user");
        m3100b(false);
        if (C1343f.f2175l) {
            C1343f.f2175l = false;
            C1238a.m1630a().mo14458b();
        }
        C1214i.m1500a().mo14385e().mo14375j();
    }

    /* renamed from: d */
    public void mo15194d() {
        C1343f.f2112D = C1368b.m2191a().mo14819c().mo14844a();
        C1574g.m3187i().mo15213j();
    }

    /* renamed from: e */
    public void mo15195e() {
        C1179b.m1354a(f2972a + "|call -> tryConnect and reset delay = 0");
        mo15190a(true);
    }

    /* renamed from: f */
    public void mo15196f() {
        String str;
        StringBuilder sb;
        C1214i.m1500a().mo14385e().mo14368c();
        C1206a e = C1214i.m1500a().mo14385e();
        C1347j.m2085a().mo14744a(C1349l.NETWORK_ERROR);
        e.mo14375j();
        if (mo15198h()) {
            sb = new StringBuilder();
            sb.append(f2972a);
            str = "|sdkOn = false or pushOn = false, disconect|user";
        } else {
            sb = new StringBuilder();
            sb.append(f2972a);
            str = "|disconnect by network";
        }
        sb.append(str);
        C1179b.m1354a(sb.toString());
        C1174c.m1310b().mo14318a(C1168m.class);
        mo15190a(false);
    }

    /* renamed from: g */
    public void mo15197g() {
        String str;
        StringBuilder sb;
        C1367b.f2238b = -1;
        if (!EncryptUtils.isLoadSuccess()) {
            sb = new StringBuilder();
            sb.append(f2972a);
            str = "|so error ++++++++";
        } else if (C1343f.f2163ay) {
            mo15194d();
            return;
        } else {
            sb = new StringBuilder();
            sb.append(f2972a);
            str = "|initSuccess = false";
        }
        sb.append(str);
        C1179b.m1354a(sb.toString());
    }

    /* renamed from: h */
    public boolean mo15198h() {
        return !C1343f.f2172i || !C1343f.f2173j;
    }

    /* renamed from: i */
    public void mo15199i() {
        C1347j.m2085a().mo14744a(C1349l.NETWORK_SWITCH);
        boolean g = C1576a.m3221g();
        C1179b.m1354a(f2972a + "|network changed, available = " + g + ", last = " + C1343f.f2171h);
        C1368b.m2191a().mo14818b();
        if (!g) {
            C1179b.m1354a(f2972a + "|network changed, available = false, do nothing");
        } else if (!C1343f.f2171h) {
            C1179b.m1354a(f2972a + "|network changed, try connect reset delay");
            mo15195e();
        }
        if (g) {
            C1214i.m1500a().mo14386f();
        }
        C1343f.f2171h = g;
    }
}
