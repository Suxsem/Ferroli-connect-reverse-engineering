package com.igexin.push.p046c;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p050c.C1312h;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;

/* renamed from: com.igexin.push.c.a */
public class C1206a {

    /* renamed from: f */
    private static final String f1720f = "com.igexin.push.c.a";

    /* renamed from: a */
    public volatile C1209d f1721a = C1209d.NORMAL;

    /* renamed from: b */
    public AtomicBoolean f1722b = new AtomicBoolean(false);

    /* renamed from: c */
    protected int f1723c;

    /* renamed from: d */
    protected volatile long f1724d;

    /* renamed from: e */
    protected volatile long f1725e;

    /* renamed from: g */
    private int f1726g;

    /* renamed from: h */
    private int f1727h;

    /* renamed from: i */
    private int f1728i;

    /* renamed from: j */
    private C1215j f1729j;

    /* renamed from: k */
    private final List<C1210e> f1730k = new ArrayList();

    /* renamed from: l */
    private final List<C1215j> f1731l = new ArrayList();

    /* renamed from: m */
    private final Object f1732m = new Object();

    /* renamed from: n */
    private final Object f1733n = new Object();

    /* renamed from: o */
    private int f1734o = 0;

    /* renamed from: p */
    private boolean f1735p;

    /* renamed from: q */
    private final Comparator<C1215j> f1736q = new C1207b(this);

    /* renamed from: a */
    private synchronized void m1476a(C1209d dVar) {
        String str;
        C1179b.m1354a(f1720f + "|set domain type = " + dVar);
        if (C1234k.f1845f) {
            if (this.f1721a != dVar) {
                mo14364a((List<C1210e>) null);
            }
            int i = C1208c.f1738a[dVar.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    this.f1722b.set(true);
                    if (this.f1721a != dVar) {
                        this.f1724d = System.currentTimeMillis();
                    }
                    SDKUrlConfig.setCmAddress(SDKUrlConfig.XFR_ADDRESS_BAK[0]);
                    str = f1720f + "|set domain type backup cm = " + SDKUrlConfig.getCmAddress();
                    C1179b.m1354a(str);
                    this.f1721a = dVar;
                    C1214i.m1500a().mo14388h().mo14427o();
                } else if (i != 3) {
                    this.f1721a = dVar;
                    C1214i.m1500a().mo14388h().mo14427o();
                } else if (this.f1721a != dVar) {
                    this.f1734o = 0;
                }
            }
            this.f1726g = 0;
            SDKUrlConfig.setCmAddress(m1478b(true));
            if (dVar == C1209d.NORMAL) {
                this.f1722b.set(false);
            }
            str = f1720f + "|set domain type normal cm = " + SDKUrlConfig.getCmAddress();
            C1179b.m1354a(str);
            this.f1721a = dVar;
            C1214i.m1500a().mo14388h().mo14427o();
        }
    }

    /* renamed from: a */
    private void m1477a(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                this.f1730k.add(new C1210e().mo14379a(jSONArray.getJSONObject(i)));
            }
            C1179b.m1354a(f1720f + "|get cm from cache, isWifi = " + this.f1735p + ", lastCmList = " + str);
        } catch (Throwable th) {
            C1179b.m1354a(f1720f + "|" + th.toString());
        }
    }

    /* renamed from: b */
    private String m1478b(boolean z) {
        String b;
        synchronized (this.f1732m) {
            this.f1726g = this.f1726g >= this.f1731l.size() ? 0 : this.f1726g;
            this.f1729j = this.f1731l.get(this.f1726g);
            b = this.f1729j.mo14395b(z);
        }
        return b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x010d, code lost:
        return r2;
     */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m1479c(boolean r11) {
        /*
            r10 = this;
            r0 = 0
            java.lang.Object r1 = r10.f1733n     // Catch:{ Exception -> 0x0111 }
            monitor-enter(r1)     // Catch:{ Exception -> 0x0111 }
            boolean r2 = r10.f1735p     // Catch:{ all -> 0x010e }
            if (r2 == 0) goto L_0x000b
            java.lang.String r2 = com.igexin.push.core.C1343f.f2160av     // Catch:{ all -> 0x010e }
            goto L_0x000d
        L_0x000b:
            java.lang.String r2 = com.igexin.push.core.C1343f.f2161aw     // Catch:{ all -> 0x010e }
        L_0x000d:
            java.util.List<com.igexin.push.c.e> r3 = r10.f1730k     // Catch:{ all -> 0x010e }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x010e }
            r4 = 0
            if (r3 == 0) goto L_0x0038
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x010e }
            if (r3 == 0) goto L_0x0038
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x010e }
            r11.<init>()     // Catch:{ all -> 0x010e }
            java.lang.String r2 = f1720f     // Catch:{ all -> 0x010e }
            r11.append(r2)     // Catch:{ all -> 0x010e }
            java.lang.String r2 = "cm list size = 0"
            r11.append(r2)     // Catch:{ all -> 0x010e }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x010e }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)     // Catch:{ all -> 0x010e }
            r10.f1728i = r4     // Catch:{ all -> 0x010e }
            r10.f1727h = r4     // Catch:{ all -> 0x010e }
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
            return r0
        L_0x0038:
            java.util.List<com.igexin.push.c.e> r3 = r10.f1730k     // Catch:{ all -> 0x010e }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x010e }
            if (r3 == 0) goto L_0x0049
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x010e }
            if (r3 != 0) goto L_0x0049
            r10.m1477a((java.lang.String) r2)     // Catch:{ all -> 0x010e }
        L_0x0049:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x010e }
            r2.<init>()     // Catch:{ all -> 0x010e }
            java.lang.String r3 = f1720f     // Catch:{ all -> 0x010e }
            r2.append(r3)     // Catch:{ all -> 0x010e }
            java.lang.String r3 = "cm try = "
            r2.append(r3)     // Catch:{ all -> 0x010e }
            int r3 = r10.f1728i     // Catch:{ all -> 0x010e }
            r2.append(r3)     // Catch:{ all -> 0x010e }
            java.lang.String r3 = " times"
            r2.append(r3)     // Catch:{ all -> 0x010e }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x010e }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r2)     // Catch:{ all -> 0x010e }
            int r2 = r10.f1728i     // Catch:{ all -> 0x010e }
            java.util.List<com.igexin.push.c.e> r3 = r10.f1730k     // Catch:{ all -> 0x010e }
            int r3 = r3.size()     // Catch:{ all -> 0x010e }
            int r3 = r3 * 3
            if (r2 < r3) goto L_0x0096
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x010e }
            r11.<init>()     // Catch:{ all -> 0x010e }
            java.lang.String r2 = f1720f     // Catch:{ all -> 0x010e }
            r11.append(r2)     // Catch:{ all -> 0x010e }
            java.lang.String r2 = "cm invalid"
            r11.append(r2)     // Catch:{ all -> 0x010e }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x010e }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)     // Catch:{ all -> 0x010e }
            r10.f1728i = r4     // Catch:{ all -> 0x010e }
            r10.f1727h = r4     // Catch:{ all -> 0x010e }
            java.util.List<com.igexin.push.c.e> r11 = r10.f1730k     // Catch:{ all -> 0x010e }
            r11.clear()     // Catch:{ all -> 0x010e }
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
            return r0
        L_0x0096:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x010e }
            java.util.List<com.igexin.push.c.e> r5 = r10.f1730k     // Catch:{ all -> 0x010e }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ all -> 0x010e }
        L_0x00a0:
            boolean r6 = r5.hasNext()     // Catch:{ all -> 0x010e }
            if (r6 == 0) goto L_0x00d6
            java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x010e }
            com.igexin.push.c.e r6 = (com.igexin.push.p046c.C1210e) r6     // Catch:{ all -> 0x010e }
            long r7 = r6.f1745b     // Catch:{ all -> 0x010e }
            int r9 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r9 >= 0) goto L_0x00a0
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x010e }
            r7.<init>()     // Catch:{ all -> 0x010e }
            java.lang.String r8 = f1720f     // Catch:{ all -> 0x010e }
            r7.append(r8)     // Catch:{ all -> 0x010e }
            java.lang.String r8 = "|add["
            r7.append(r8)     // Catch:{ all -> 0x010e }
            java.lang.String r6 = r6.f1744a     // Catch:{ all -> 0x010e }
            r7.append(r6)     // Catch:{ all -> 0x010e }
            java.lang.String r6 = "] outDate"
            r7.append(r6)     // Catch:{ all -> 0x010e }
            java.lang.String r6 = r7.toString()     // Catch:{ all -> 0x010e }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r6)     // Catch:{ all -> 0x010e }
            r5.remove()     // Catch:{ all -> 0x010e }
            goto L_0x00a0
        L_0x00d6:
            r10.mo14369d()     // Catch:{ all -> 0x010e }
            java.util.List<com.igexin.push.c.e> r2 = r10.f1730k     // Catch:{ all -> 0x010e }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x010e }
            if (r2 == 0) goto L_0x00e3
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
            return r0
        L_0x00e3:
            int r2 = r10.f1727h     // Catch:{ all -> 0x010e }
            java.util.List<com.igexin.push.c.e> r3 = r10.f1730k     // Catch:{ all -> 0x010e }
            int r3 = r3.size()     // Catch:{ all -> 0x010e }
            if (r2 < r3) goto L_0x00ee
            goto L_0x00f0
        L_0x00ee:
            int r4 = r10.f1727h     // Catch:{ all -> 0x010e }
        L_0x00f0:
            r10.f1727h = r4     // Catch:{ all -> 0x010e }
            java.util.List<com.igexin.push.c.e> r2 = r10.f1730k     // Catch:{ all -> 0x010e }
            int r3 = r10.f1727h     // Catch:{ all -> 0x010e }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x010e }
            com.igexin.push.c.e r2 = (com.igexin.push.p046c.C1210e) r2     // Catch:{ all -> 0x010e }
            java.lang.String r2 = r2.f1744a     // Catch:{ all -> 0x010e }
            int r3 = r10.f1727h     // Catch:{ all -> 0x010e }
            int r3 = r3 + 1
            r10.f1727h = r3     // Catch:{ all -> 0x010e }
            if (r11 == 0) goto L_0x010c
            int r11 = r10.f1728i     // Catch:{ all -> 0x010e }
            int r11 = r11 + 1
            r10.f1728i = r11     // Catch:{ all -> 0x010e }
        L_0x010c:
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
            return r2
        L_0x010e:
            r11 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x010e }
            throw r11     // Catch:{ Exception -> 0x0111 }
        L_0x0111:
            r11 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = f1720f
            r1.append(r2)
            java.lang.String r2 = "|"
            r1.append(r2)
            java.lang.String r11 = r11.toString()
            r1.append(r11)
            java.lang.String r11 = r1.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p046c.C1206a.m1479c(boolean):java.lang.String");
    }

    /* renamed from: k */
    private void m1480k() {
        C1209d dVar;
        C1179b.m1354a(f1720f + "|before disconnect, type = " + this.f1721a);
        int i = C1208c.f1738a[this.f1721a.ordinal()];
        if (i != 1) {
            if (i == 2) {
                if (System.currentTimeMillis() - this.f1724d > C1234k.f1862w) {
                    dVar = C1209d.TRY_NORMAL;
                } else {
                    return;
                }
            } else {
                return;
            }
        } else if (System.currentTimeMillis() - this.f1725e > Constants.CLIENT_FLUSH_INTERVAL && this.f1723c > C1234k.f1864y) {
            dVar = C1209d.BACKUP;
        } else {
            return;
        }
        m1476a(dVar);
    }

    /* renamed from: a */
    public void mo14363a() {
        try {
            int i = 0;
            boolean z = !C1340e.m2032a().mo14710g().mo15198h();
            String c = m1479c(z);
            C1179b.m1354a(f1720f + "|get from cm = " + c);
            if (c == null) {
                if (!C1234k.f1845f || this.f1721a != C1209d.BACKUP) {
                    if (this.f1729j != null && !this.f1729j.mo14403f()) {
                        this.f1726g++;
                    }
                    c = m1478b(z);
                } else {
                    if (this.f1726g < SDKUrlConfig.XFR_ADDRESS_BAK.length) {
                        i = this.f1726g;
                    }
                    this.f1726g = i;
                    c = SDKUrlConfig.XFR_ADDRESS_BAK[this.f1726g];
                    this.f1726g++;
                }
            }
            if (!SDKUrlConfig.getCmAddress().equals(c)) {
                C1179b.m1354a(f1720f + "|address changed : form [" + SDKUrlConfig.getCmAddress() + "] to [" + c + "]");
            }
            SDKUrlConfig.setCmAddress(c);
        } catch (Exception e) {
            e.printStackTrace();
            C1179b.m1354a(f1720f + "|switch address|" + e.toString());
        }
    }

    /* renamed from: a */
    public void mo14364a(List<C1210e> list) {
        synchronized (this.f1733n) {
            this.f1727h = 0;
            this.f1728i = 0;
            this.f1730k.clear();
            if (list != null) {
                this.f1730k.addAll(list);
                C1179b.m1354a(f1720f + "|set cm list: " + list.toString());
            }
            mo14369d();
        }
    }

    /* renamed from: a */
    public void mo14365a(boolean z) {
        this.f1735p = z;
    }

    /* renamed from: b */
    public synchronized void mo14366b() {
        this.f1728i = 0;
        if (this.f1729j != null) {
            this.f1729j.mo14404g();
        }
    }

    /* renamed from: b */
    public void mo14367b(List<C1215j> list) {
        synchronized (this.f1732m) {
            this.f1731l.clear();
            this.f1731l.addAll(list);
            Collections.sort(this.f1731l, this.f1736q);
        }
    }

    /* renamed from: c */
    public synchronized void mo14368c() {
        this.f1723c++;
        C1179b.m1354a(f1720f + "|loginFailedlCnt = " + this.f1723c);
    }

    /* renamed from: d */
    public void mo14369d() {
        JSONArray jSONArray = new JSONArray();
        for (C1210e a : this.f1730k) {
            jSONArray.put(a.mo14380a());
        }
        C1312h.m1937a().mo14680c(jSONArray.length() == 0 ? "null" : jSONArray.toString(), !this.f1735p);
    }

    /* renamed from: e */
    public void mo14370e() {
        synchronized (this.f1732m) {
            this.f1726g = 0;
            Collections.sort(this.f1731l, this.f1736q);
            try {
                int length = SDKUrlConfig.getXfrAddress().length;
                if (length >= 3) {
                    for (int i = 0; i < this.f1731l.size(); i++) {
                        this.f1731l.get(i).mo14397b(length - i);
                    }
                }
            } catch (Exception e) {
                C1179b.m1354a(f1720f + "|" + e.toString());
            }
        }
    }

    /* renamed from: f */
    public void mo14371f() {
        C1179b.m1354a(f1720f + "|detect success, current type = " + this.f1721a);
        if (this.f1721a == C1209d.BACKUP) {
            m1476a(C1209d.TRY_NORMAL);
            C1340e.m2032a().mo14710g().mo15190a(true);
        }
    }

    /* renamed from: g */
    public void mo14372g() {
        C1179b.m1354a(f1720f + "|detect max cnt ,set type = backup");
        m1476a(C1209d.BACKUP);
        C1340e.m2032a().mo14710g().mo15190a(true);
    }

    /* renamed from: h */
    public void mo14373h() {
        if (C1208c.f1738a[this.f1721a.ordinal()] == 2) {
            if (System.currentTimeMillis() - this.f1724d > C1234k.f1862w) {
                m1476a(C1209d.TRY_NORMAL);
            }
        }
    }

    /* renamed from: i */
    public void mo14374i() {
        if (this.f1721a != C1209d.BACKUP) {
            this.f1723c = 0;
        }
        int i = C1208c.f1738a[this.f1721a.ordinal()];
        if (i == 1) {
            this.f1725e = System.currentTimeMillis();
            C1214i.m1500a().mo14388h().mo14427o();
        } else if (i != 2 && i == 3) {
            m1476a(C1209d.NORMAL);
        } else {
            return;
        }
        this.f1722b.set(false);
    }

    /* renamed from: j */
    public void mo14375j() {
        m1480k();
        if (C1343f.f2175l && this.f1721a != C1209d.BACKUP) {
            this.f1725e = System.currentTimeMillis();
            C1214i.m1500a().mo14388h().mo14427o();
        }
        int i = C1208c.f1738a[this.f1721a.ordinal()];
        if (i != 1 && i != 2 && i == 3) {
            int i2 = this.f1734o + 1;
            this.f1734o = i2;
            if (i2 >= 10) {
                this.f1723c = 0;
                this.f1724d = System.currentTimeMillis();
                m1476a(C1209d.BACKUP);
            }
        }
    }
}
