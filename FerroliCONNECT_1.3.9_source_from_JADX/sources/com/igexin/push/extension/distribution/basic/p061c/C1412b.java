package com.igexin.push.extension.distribution.basic.p061c;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p033a.p040d.C1191f;
import com.igexin.p032b.p033a.p040d.p041a.C1182b;
import com.igexin.p032b.p033a.p040d.p041a.C1185e;
import com.igexin.push.extension.distribution.basic.p066h.C1431b;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.igexin.push.extension.distribution.basic.c.b */
public class C1412b implements C1182b {

    /* renamed from: a */
    private List<C1431b> f2413a = new ArrayList();

    /* renamed from: a */
    public boolean mo14273a(C1185e eVar, C1191f fVar) {
        for (C1431b next : this.f2413a) {
            if (next.mo14985b()) {
                next.mo14984a(System.currentTimeMillis());
                C1179b.m1354a("--> " + next.getClass().getName() + " isMatched...");
                try {
                    next.mo14983a();
                } catch (Throwable th) {
                    C1179b.m1354a("ExtensionCronTask|" + th.toString());
                }
            }
        }
        C1179b.m1354a("--> ExtensionCronTask ioHandle==================");
        return false;
    }

    /* renamed from: a */
    public boolean mo14274a(C1190e eVar, C1191f fVar) {
        return false;
    }

    /* renamed from: a */
    public boolean mo14940a(C1431b bVar) {
        return !this.f2413a.contains(bVar) && this.f2413a.add(bVar);
    }

    /* renamed from: k */
    public boolean mo14275k() {
        return true;
    }

    /* renamed from: l */
    public long mo14276l() {
        return 139859;
    }
}
