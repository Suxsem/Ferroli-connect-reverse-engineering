package com.igexin.push.extension.distribution.gbd.p078d;

import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p081f.C1515c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.eclipse.jetty.http.HttpHeaders;

/* renamed from: com.igexin.push.extension.distribution.gbd.d.f */
class C1496f implements C1515c {

    /* renamed from: a */
    final /* synthetic */ C1494d f2782a;

    C1496f(C1494d dVar) {
        this.f2782a = dVar;
    }

    /* renamed from: a */
    public void mo15092a(Object obj) {
        List<String> list;
        if (obj != null && (obj instanceof HashMap) && (list = (List) ((Map) ((HashMap) obj).get("header")).get(HttpHeaders.DATE)) != null) {
            for (String str : list) {
                if (str.contains(":") && str.contains("GMT")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.ENGLISH);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    long time = simpleDateFormat.parse(str).getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis != time) {
                        C1490c.f2732I = time - currentTimeMillis;
                        C1540h.m2997b("GBD_Logic", "localTimeByServerTimeDiff  = " + C1490c.f2732I);
                    }
                }
            }
        }
    }
}
