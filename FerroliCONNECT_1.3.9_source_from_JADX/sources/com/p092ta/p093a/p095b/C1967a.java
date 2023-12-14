package com.p092ta.p093a.p095b;

import android.text.TextUtils;
import com.p092ta.p093a.p096c.C1976b;
import com.p092ta.p093a.p096c.C1982f;
import com.p092ta.utdid2.p097a.p098a.C1983a;

/* renamed from: com.ta.a.b.a */
public class C1967a {

    /* renamed from: a */
    public int f3149a = -1;

    /* renamed from: a */
    public String f3150a = "";

    /* renamed from: b */
    public long f3151b = 0;
    public byte[] data = null;
    public long timestamp = 0;

    /* renamed from: a */
    public static boolean m3337a(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                C1982f.m3371b("", "result", str, "signature", str2);
                if (str2.equals(C1983a.encodeToString(C1976b.m3356d(str).getBytes(), 2))) {
                    C1982f.m3366a("", "signature is ok");
                    return true;
                }
                C1982f.m3366a("", "signature is error");
            }
        } catch (Exception e) {
            C1982f.m3366a("", e);
        }
        return false;
    }
}
