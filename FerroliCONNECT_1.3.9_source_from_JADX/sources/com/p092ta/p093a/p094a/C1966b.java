package com.p092ta.p093a.p094a;

import android.text.TextUtils;
import com.p092ta.p093a.p096c.C1979e;
import com.p092ta.p093a.p096c.C1982f;
import com.p092ta.utdid2.p097a.p098a.C1983a;

/* renamed from: com.ta.a.a.b */
public class C1966b {
    /* renamed from: b */
    public static String m3336b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new String(C1983a.encode(C1979e.m3363b(str.getBytes()), 2), "UTF-8");
        } catch (Exception e) {
            C1982f.m3367a("", e, new Object[0]);
            return "";
        }
    }
}
