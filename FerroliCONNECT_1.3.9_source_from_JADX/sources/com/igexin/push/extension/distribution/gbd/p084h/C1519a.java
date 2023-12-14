package com.igexin.push.extension.distribution.gbd.p084h;

import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p033a.p040d.C1191f;
import com.igexin.p032b.p033a.p040d.p041a.C1182b;
import com.igexin.p032b.p033a.p040d.p041a.C1185e;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p084h.p085a.C1521b;
import com.igexin.push.extension.distribution.gbd.p084h.p085a.C1524e;
import com.igexin.push.extension.distribution.gbd.p084h.p085a.C1525f;
import com.igexin.push.extension.distribution.gbd.p084h.p085a.C1527h;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a */
public class C1519a implements C1182b {

    /* renamed from: a */
    private List<C1532b> f2920a = new CopyOnWriteArrayList();

    /* renamed from: a */
    public boolean mo14273a(C1185e eVar, C1191f fVar) {
        boolean k = C1541i.m3038k(C1490c.f2747a);
        C1540h.m2997b("GBD_CronTask", "gbd safe check = " + k);
        Iterator<C1532b> it = this.f2920a.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                return false;
            }
            C1532b next = it.next();
            if ((next instanceof C1525f) || (next instanceof C1521b) || (next instanceof C1527h) || (next instanceof C1524e)) {
                z = true;
            }
            C1540h.m2995a("GBD_CronTask", next.getClass().getSimpleName() + "|step=" + next.f2935b + "|enable=" + next.mo15166c() + "|match=" + next.mo15165b() + "|isIgnore=" + z);
            if ((z || k) && next.mo15166c() && next.mo15165b()) {
                C1540h.m2995a("GBD_CronTask", next.getClass().getSimpleName() + "|fresh and doTask");
                next.mo15164a(System.currentTimeMillis());
                next.mo15163a();
            }
        }
    }

    /* renamed from: a */
    public boolean mo14274a(C1190e eVar, C1191f fVar) {
        return false;
    }

    /* renamed from: a */
    public boolean mo15162a(C1532b bVar) {
        return !this.f2920a.contains(bVar) && this.f2920a.add(bVar);
    }

    /* renamed from: k */
    public boolean mo14275k() {
        return true;
    }

    /* renamed from: l */
    public long mo14276l() {
        return -423462;
    }
}
