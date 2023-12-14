package com.igexin.push.extension.distribution.gbd.p081f;

import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.szacs.ferroliconnect.util.APConfig.UDPSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.http.MimeTypes;

/* renamed from: com.igexin.push.extension.distribution.gbd.f.d */
public abstract class C1516d {

    /* renamed from: a */
    public String f2907a;

    /* renamed from: b */
    public byte[] f2908b;

    /* renamed from: c */
    public boolean f2909c;

    /* renamed from: d */
    public C1515c f2910d;

    /* renamed from: e */
    public boolean f2911e;

    /* renamed from: f */
    public boolean f2912f;

    /* renamed from: g */
    public int f2913g = UDPSocket.CLIENT_PORT;

    /* renamed from: h */
    public int f2914h = UDPSocket.CLIENT_PORT;

    /* renamed from: i */
    public boolean f2915i = true;

    /* renamed from: j */
    public boolean f2916j = true;

    /* renamed from: k */
    public boolean f2917k = false;

    /* renamed from: l */
    public boolean f2918l = true;

    /* renamed from: m */
    public HashMap<String, String> f2919m = new HashMap<>();

    public C1516d() {
    }

    public C1516d(String str) {
        C1540h.m2995a("HttpPlugin", "http url:" + str);
        this.f2907a = str;
        mo15144a("Content-Type", MimeTypes.FORM_ENCODED);
    }

    /* renamed from: a */
    public void mo15137a(int i) {
    }

    /* renamed from: a */
    public void mo15142a(C1515c cVar) {
        this.f2910d = cVar;
    }

    /* renamed from: a */
    public void mo15143a(String str) {
        this.f2907a = str;
    }

    /* renamed from: a */
    public void mo15144a(String str, String str2) {
        this.f2919m.put(str, str2);
    }

    /* renamed from: a */
    public void mo15138a(Throwable th) {
    }

    /* renamed from: a */
    public void mo15139a(Map<String, List<String>> map, byte[] bArr) {
    }

    /* renamed from: a */
    public void mo15145a(boolean z) {
        this.f2909c = z;
    }

    /* renamed from: a */
    public void mo15146a(byte[] bArr) {
        this.f2908b = bArr;
    }

    /* renamed from: a */
    public boolean mo15147a() {
        return this.f2911e;
    }

    /* renamed from: b */
    public boolean mo15148b() {
        return this.f2912f;
    }

    /* renamed from: c */
    public boolean mo15149c() {
        return this.f2918l;
    }

    /* renamed from: d */
    public boolean mo15150d() {
        return this.f2917k;
    }

    /* renamed from: e */
    public int mo15151e() {
        return this.f2913g;
    }

    /* renamed from: f */
    public int mo15152f() {
        return this.f2914h;
    }

    /* renamed from: g */
    public boolean mo15153g() {
        return this.f2915i;
    }

    /* renamed from: h */
    public boolean mo15154h() {
        return this.f2916j;
    }

    /* renamed from: i */
    public HashMap<String, String> mo15155i() {
        return this.f2919m;
    }

    /* renamed from: j */
    public boolean mo15156j() {
        return this.f2909c;
    }

    /* renamed from: k */
    public String mo15157k() {
        return this.f2907a;
    }

    /* renamed from: l */
    public byte[] mo15158l() {
        return this.f2908b;
    }

    /* renamed from: m */
    public void mo15159m() {
    }
}
