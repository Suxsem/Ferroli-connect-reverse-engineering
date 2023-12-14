package com.igexin.push.extension.distribution.gbd.p069a.p071b;

import android.app.ActivityManager;
import anetwork.channel.util.RequestConstant;
import com.contrarywind.timer.MessageHandler;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.p088g.p090b.C1575h;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.b.j */
final class C1459j extends C1575h {

    /* renamed from: a */
    final /* synthetic */ Map f2520a;

    /* renamed from: b */
    final /* synthetic */ int f2521b;

    /* renamed from: c */
    final /* synthetic */ int f2522c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1459j(long j, Map map, int i, int i2) {
        super(j);
        this.f2520a = map;
        this.f2521b = i;
        this.f2522c = i2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        Map map;
        int i;
        try {
            List list = (List) this.f2520a.get("checkList");
            if (list != null) {
                List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) C1490c.f2747a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningServices(MessageHandler.WHAT_SMOOTH_SCROLL);
                HashMap hashMap = new HashMap();
                for (int i2 = 0; i2 < list.size(); i2++) {
                    hashMap.put(list.get(i2), Boolean.valueOf(C1541i.m3008a((String) list.get(i2), this.f2520a.get("pkgName").toString(), runningServices)));
                }
                String str = this.f1613l;
                C1540h.m2995a(str, "runing service = " + hashMap.toString());
                if (hashMap.toString().contains(RequestConstant.TRUE)) {
                    C1490c.f2749c.post(new C1460k(this));
                    return;
                }
                int i3 = 1;
                if (this.f2522c == 0) {
                    map = this.f2520a;
                    i = this.f2521b;
                } else if (this.f2522c == 1) {
                    map = this.f2520a;
                    i3 = 2;
                    i = this.f2521b;
                } else {
                    String str2 = this.f1613l;
                    C1540h.m2997b(str2, "guard failed type = " + this.f2521b + " pkg = " + this.f2520a.get("pkgName").toString());
                    return;
                }
                C1458i.m2620a(map, i3, i);
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
