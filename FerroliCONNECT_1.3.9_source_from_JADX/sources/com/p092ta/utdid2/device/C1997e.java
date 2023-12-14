package com.p092ta.utdid2.device;

import com.p092ta.p093a.p095b.C1967a;
import com.p092ta.p093a.p096c.C1982f;

/* renamed from: com.ta.utdid2.device.e */
public class C1997e {
    /* renamed from: a */
    public static boolean m3407a(C1967a aVar) {
        String str = "";
        try {
            str = new String(aVar.data, "UTF-8");
        } catch (Exception e) {
            C1982f.m3366a(str, e);
        }
        if (C1967a.m3337a(str, aVar.f3150a)) {
            return C1994b.m3392a(C1994b.m3391a(str).f3189d);
        }
        return false;
    }
}
