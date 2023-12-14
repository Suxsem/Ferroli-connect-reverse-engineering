package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.p054e.p057c.C1372a;
import com.igexin.push.p054e.p057c.C1374c;
import com.igexin.push.p088g.p090b.C1569b;

/* renamed from: com.igexin.push.core.a.e */
public class C1256e extends C1239a {
    /* renamed from: a */
    private void m1708a(String str, C1372a aVar) {
        if (str != null) {
            String substring = str.substring(3, str.length());
            if (substring.contains("@")) {
                String[] split = substring.split("\\@");
                String str2 = split[0];
                if (split[1].contains("|")) {
                    String[] split2 = split[1].split("\\|");
                    String str3 = split2[0];
                    String str4 = split2[1];
                    if (str2 != null && str3 != null && str4 != null) {
                        PushTaskBean pushTaskBean = new PushTaskBean();
                        pushTaskBean.setAppid(C1343f.f2135a);
                        pushTaskBean.setMessageId(str2);
                        pushTaskBean.setTaskId(str3);
                        pushTaskBean.setId(str2);
                        pushTaskBean.setAppKey(C1343f.f2165b);
                        pushTaskBean.setCurrentActionid(1);
                        C1257f.m1711a().mo14475a(pushTaskBean);
                        C1257f.m1711a().mo14479a(str4, aVar, pushTaskBean);
                    }
                }
            }
        }
    }

    /* renamed from: a */
    public boolean mo14464a(C1190e eVar) {
        return super.mo14464a(eVar);
    }

    /* renamed from: a */
    public boolean mo14465a(Object obj) {
        C1569b e;
        if (!(obj instanceof C1372a)) {
            return true;
        }
        C1372a aVar = (C1372a) obj;
        if (aVar.f2251c == null) {
            return true;
        }
        String str = (String) aVar.f2251c;
        C1179b.m1354a("cdnpushmessage|" + str);
        if (str.startsWith("RCV")) {
            String substring = str.substring(3, str.length());
            if (!C1343f.f2146ah.containsKey(substring)) {
                return true;
            }
            C1374c cVar = C1343f.f2146ah.get(substring);
            C1343f.f2146ah.remove(substring);
            if (cVar == null || (e = cVar.mo14835e()) == null) {
                return true;
            }
            e.mo14309t();
            return true;
        } else if (!str.contains("CDN")) {
            return true;
        } else {
            m1708a(str, aVar);
            return true;
        }
    }
}
