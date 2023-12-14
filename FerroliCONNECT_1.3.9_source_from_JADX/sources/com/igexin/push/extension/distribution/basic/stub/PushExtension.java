package com.igexin.push.extension.distribution.basic.stub;

import android.content.Context;
import android.content.IntentFilter;
import android.util.DisplayMetrics;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.p048a.C1240a;
import com.igexin.push.extension.distribution.basic.p058a.C1396a;
import com.igexin.push.extension.distribution.basic.p058a.C1401c;
import com.igexin.push.extension.distribution.basic.p058a.C1402d;
import com.igexin.push.extension.distribution.basic.p058a.C1403e;
import com.igexin.push.extension.distribution.basic.p058a.C1404f;
import com.igexin.push.extension.distribution.basic.p058a.p059a.C1397a;
import com.igexin.push.extension.distribution.basic.p058a.p059a.C1399c;
import com.igexin.push.extension.distribution.basic.p061c.C1413c;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import com.igexin.push.extension.distribution.basic.p062d.C1418b;
import com.igexin.push.extension.distribution.basic.p063e.C1420a;
import com.igexin.push.extension.distribution.basic.p063e.C1421b;
import com.igexin.push.extension.distribution.basic.p068j.C1440h;
import com.igexin.push.extension.stub.IPushExtension;
import com.igexin.push.p088g.p090b.C1575h;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class PushExtension implements IPushExtension {

    /* renamed from: a */
    private static Map<String, C1240a> f2491a;

    /* renamed from: b */
    private static C1397a f2492b;

    public PushExtension() {
        f2491a = new HashMap();
        f2491a.put("notification", new C1396a());
        f2491a.put("terminatetask", new C1403e());
        f2491a.put("startintent", new C1401c());
        f2491a.put("startmyactivity", new C1402d());
        f2491a.put("wakeupsdk", new C1404f());
    }

    public boolean executeAction(PushTaskBean pushTaskBean, BaseAction baseAction) {
        C1240a aVar;
        if (pushTaskBean == null || baseAction == null || (aVar = f2491a.get(baseAction.getType())) == null || pushTaskBean.isStop()) {
            return false;
        }
        return aVar.mo14468b(pushTaskBean, baseAction);
    }

    public boolean init(Context context) {
        C1179b.m1354a("EXT-PushExtension|ext init ###");
        if (context == null) {
            C1179b.m1354a("EXT-PushExtension|context = null");
            return false;
        }
        C1416f.f2423a = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        if (i2 > i) {
            C1416f.f2424b = i2;
            C1416f.f2425c = i;
        } else {
            C1416f.f2424b = i;
            C1416f.f2425c = i2;
        }
        try {
            C1416f.f2441s = new C1421b(context);
            C1413c.m2411a().mo14945e();
        } catch (Throwable unused) {
        }
        C1413c.m2411a().mo14943c();
        C1413c.m2411a().mo14944d();
        C1416f.f2428f = new C1420a(context);
        C1418b.m2428a().mo14955b();
        if (C1416f.f2426d == null) {
            C1416f.f2426d = C1196a.m1435a(C1343f.f2169f.getPackageName() + System.currentTimeMillis());
            C1418b.m2428a().mo14951a(4, C1416f.f2426d);
        }
        if (C1343f.f2175l) {
            C1399c.m2281a().mo14852a(C1343f.f2175l);
        }
        f2492b = new C1397a();
        C1397a aVar = f2492b;
        context.registerReceiver(aVar, new IntentFilter("com.igexin.sdk.action." + C1343f.f2135a));
        if (System.currentTimeMillis() - C1416f.f2429g > Constants.CLIENT_FLUSH_INTERVAL) {
            boolean a = C1340e.m2032a().mo14703a((C1575h) new C1446a(this, 20000));
            C1179b.m1354a("EXT-PushExtension|init addTimerTask getConfigTask result = " + a);
        }
        boolean a2 = C1340e.m2032a().mo14703a((C1575h) new C1447b(this, 180000));
        C1179b.m1354a("EXT-PushExtension|init addTimerTask result = " + a2);
        if (C1440h.m2524a()) {
            return true;
        }
        C1416f.f2440r = context.getCacheDir() + "/ImgCache/";
        return true;
    }

    public boolean isActionSupported(String str) {
        return (str == null || f2491a.get(str) == null) ? false : true;
    }

    public void onDestroy() {
        C1413c.m2411a().mo14946f();
        try {
            if (f2492b != null && C1343f.f2169f != null) {
                C1343f.f2169f.unregisterReceiver(f2492b);
            }
        } catch (Exception unused) {
        }
    }

    public BaseAction parseAction(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.has("type")) {
            return null;
        }
        try {
            C1240a aVar = f2491a.get(jSONObject.getString("type"));
            if (aVar != null) {
                return aVar.mo14466a(jSONObject);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = f2491a.get(r4.getType());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.igexin.push.core.C1294c prepareExecuteAction(com.igexin.push.core.bean.PushTaskBean r3, com.igexin.push.core.bean.BaseAction r4) {
        /*
            r2 = this;
            if (r3 == 0) goto L_0x0017
            if (r4 == 0) goto L_0x0017
            java.util.Map<java.lang.String, com.igexin.push.core.a.a.a> r0 = f2491a
            java.lang.String r1 = r4.getType()
            java.lang.Object r0 = r0.get(r1)
            com.igexin.push.core.a.a.a r0 = (com.igexin.push.core.p047a.p048a.C1240a) r0
            if (r0 == 0) goto L_0x0017
            com.igexin.push.core.c r3 = r0.mo14467a(r3, r4)
            return r3
        L_0x0017:
            com.igexin.push.core.c r3 = com.igexin.push.core.C1294c.stop
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.basic.stub.PushExtension.prepareExecuteAction(com.igexin.push.core.bean.PushTaskBean, com.igexin.push.core.bean.BaseAction):com.igexin.push.core.c");
    }
}
