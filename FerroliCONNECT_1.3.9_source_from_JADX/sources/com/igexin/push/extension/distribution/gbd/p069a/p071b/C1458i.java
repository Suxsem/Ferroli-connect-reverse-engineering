package com.igexin.push.extension.distribution.gbd.p069a.p071b;

import android.content.ContentValues;
import android.support.p000v4.app.NotificationCompat;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.core.C1340e;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.p088g.p090b.C1575h;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.b.i */
public class C1458i {
    /* renamed from: a */
    public static void m2618a(String str) {
        String a = C1196a.m1435a(str);
        if (C1490c.f2760n == null) {
            C1490c.f2760n = new HashMap();
        }
        if (!C1490c.f2760n.containsKey(a)) {
            C1490c.f2760n.put(a, str);
        }
    }

    /* renamed from: a */
    public static void m2619a(String str, int i, String str2, int i2) {
        if ((i != 1 && i != 2 && i != 3) || C1488a.f2636T) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("value", C1196a.m1435a(str));
            contentValues.put("t", str2);
            contentValues.put("type", Integer.valueOf(i));
            contentValues.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(i2));
            C1490c.f2748b.mo15095a("look", contentValues);
        }
    }

    /* renamed from: a */
    public static void m2620a(Map<String, Object> map, int i, int i2) {
        if (map != null) {
            C1340e.m2032a().mo14703a((C1575h) new C1459j((long) (i == 1 ? 120000 : i == 2 ? 180000 : RestConstants.G_MAX_CONNECTION_TIME_OUT), map, i2, i));
        }
    }
}
