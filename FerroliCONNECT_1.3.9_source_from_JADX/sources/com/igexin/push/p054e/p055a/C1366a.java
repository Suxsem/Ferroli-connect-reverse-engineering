package com.igexin.push.p054e.p055a;

import com.igexin.p032b.p033a.p035b.C1173b;
import com.igexin.p032b.p033a.p035b.C1175d;
import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p040d.p041a.C1185e;
import com.igexin.push.p054e.p057c.C1372a;
import com.igexin.push.p054e.p057c.C1373b;
import com.igexin.push.p054e.p057c.C1376e;
import com.igexin.push.p054e.p057c.C1379h;
import com.igexin.push.p054e.p057c.C1381j;
import com.igexin.push.p054e.p057c.C1384m;
import com.igexin.push.p054e.p057c.C1385n;
import com.igexin.push.p054e.p057c.C1386o;
import com.igexin.push.p054e.p057c.C1388q;

/* renamed from: com.igexin.push.e.a.a */
public class C1366a extends C1173b {
    public C1366a(String str, C1173b bVar) {
        super(str, true);
        mo14256a(bVar);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        r4 = r4.getString(com.igexin.sdk.PushConsts.CMD_ACTION);
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m2177a(com.igexin.push.p054e.p057c.C1373b r4, com.igexin.push.p054e.p057c.C1376e r5) {
        /*
            r3 = this;
            java.lang.String r0 = "action"
            byte r4 = r4.f2257b
            r1 = 0
            r2 = 26
            if (r4 == r2) goto L_0x000a
            return r1
        L_0x000a:
            com.igexin.push.e.c.o r5 = (com.igexin.push.p054e.p057c.C1386o) r5
            boolean r4 = r5.mo14842a()
            if (r4 == 0) goto L_0x004e
            java.lang.Object r4 = r5.f2330e
            if (r4 == 0) goto L_0x004e
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0035 }
            java.lang.Object r5 = r5.f2330e     // Catch:{ Exception -> 0x0035 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0035 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0035 }
            boolean r5 = r4.has(r0)     // Catch:{ Exception -> 0x0035 }
            if (r5 == 0) goto L_0x004e
            java.lang.String r4 = r4.getString(r0)     // Catch:{ Exception -> 0x0035 }
            if (r4 == 0) goto L_0x004e
            java.lang.String r5 = "redirect_server"
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0035 }
            if (r4 == 0) goto L_0x004e
            r4 = 1
            return r4
        L_0x0035:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r0 = "CommandFilter|"
            r5.append(r0)
            java.lang.String r4 = r4.toString()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r4)
        L_0x004e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p054e.p055a.C1366a.m2177a(com.igexin.push.e.c.b, com.igexin.push.e.c.e):boolean");
    }

    /* renamed from: a */
    public Object mo14255a(C1176e eVar, C1175d dVar, Object obj) {
        if (obj instanceof C1376e) {
            C1376e eVar2 = (C1376e) obj;
            C1373b bVar = new C1373b();
            bVar.f2257b = (byte) eVar2.f2278i;
            bVar.mo14830a(eVar2.mo14829d());
            bVar.f2258c = eVar2.f2279j;
            bVar.f2259d = eVar2.f2280k;
            return bVar;
        } else if (!(obj instanceof C1376e[])) {
            return null;
        } else {
            C1376e[] eVarArr = (C1376e[]) obj;
            C1373b[] bVarArr = new C1373b[eVarArr.length];
            for (int i = 0; i < eVarArr.length; i++) {
                bVarArr[i] = new C1373b();
                bVarArr[i].f2257b = (byte) eVarArr[i].f2278i;
                bVarArr[i].mo14830a(eVarArr[i].mo14829d());
            }
            return bVarArr;
        }
    }

    /* renamed from: b */
    public C1185e mo14258c(C1176e eVar, C1175d dVar, Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof C1379h) {
            return (C1185e) obj;
        }
        C1373b bVar = (C1373b) obj;
        byte b = bVar.f2257b;
        C1376e jVar = b != 5 ? b != 9 ? b != 26 ? b != 28 ? b != 37 ? b != 97 ? null : new C1381j() : new C1385n() : new C1372a() : new C1386o() : new C1388q() : new C1384m();
        if ((bVar.f2261f != 1 && bVar.f2261f != 7) || jVar == null) {
            return null;
        }
        jVar.mo14826a(bVar.f2260e);
        if (bVar.f2261f == 7) {
            if (bVar.f2262g != 32 || m2177a(bVar, jVar)) {
                return jVar;
            }
            return null;
        } else if (m2177a(bVar, jVar)) {
            return jVar;
        } else {
            return null;
        }
    }
}
