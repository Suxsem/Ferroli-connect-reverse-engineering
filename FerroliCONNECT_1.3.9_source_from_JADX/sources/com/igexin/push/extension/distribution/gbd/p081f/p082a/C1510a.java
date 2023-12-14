package com.igexin.push.extension.distribution.gbd.p081f.p082a;

import com.igexin.push.extension.distribution.gbd.p081f.C1515c;
import com.igexin.push.extension.distribution.gbd.p081f.C1516d;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.igexin.push.extension.distribution.gbd.f.a.a */
public class C1510a extends C1516d {
    public C1510a(C1515c cVar) {
        this.f2910d = cVar;
    }

    /* renamed from: a */
    public void mo15137a(int i) {
        if (this.f2910d != null) {
            try {
                this.f2910d.mo15092a((Object) null);
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
    }

    /* renamed from: a */
    public void mo15138a(Throwable th) {
        if (this.f2910d != null) {
            try {
                this.f2910d.mo15092a((Object) null);
            } catch (Throwable th2) {
                C1540h.m2996a(th2);
            }
        }
    }

    /* renamed from: a */
    public void mo15139a(Map<String, List<String>> map, byte[] bArr) {
        try {
            if (this.f2910d != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("header", map);
                hashMap.put(Constants.KEY_DATA, new String(bArr, "utf-8"));
                this.f2910d.mo15092a(hashMap);
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }
}
