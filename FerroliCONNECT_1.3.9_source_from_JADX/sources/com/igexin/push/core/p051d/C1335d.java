package com.igexin.push.core.p051d;

import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.p054e.p057c.C1372a;
import java.util.TimerTask;

/* renamed from: com.igexin.push.core.d.d */
class C1335d extends TimerTask {

    /* renamed from: a */
    final /* synthetic */ PushTaskBean f2081a;

    /* renamed from: b */
    final /* synthetic */ C1372a f2082b;

    /* renamed from: c */
    final /* synthetic */ C1333b f2083c;

    C1335d(C1333b bVar, PushTaskBean pushTaskBean, C1372a aVar) {
        this.f2083c = bVar;
        this.f2081a = pushTaskBean;
        this.f2082b = aVar;
    }

    public void run() {
        if (C1343f.f2145ag.containsKey(this.f2081a.getTaskId())) {
            C1343f.f2145ag.get(this.f2081a.getTaskId()).cancel();
            C1343f.f2145ag.remove(this.f2081a.getTaskId());
        }
        this.f2083c.mo14694a(this.f2081a, this.f2082b);
        C1372a aVar = this.f2082b;
        aVar.mo14827b(aVar.mo14828c() + 1);
    }
}
