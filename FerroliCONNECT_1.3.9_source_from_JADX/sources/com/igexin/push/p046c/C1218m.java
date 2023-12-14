package com.igexin.push.p046c;

import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.p050c.C1312h;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.c.m */
public abstract class C1218m {

    /* renamed from: e */
    private static final String f1775e = "com.igexin.push.c.m";

    /* renamed from: a */
    protected long f1776a;

    /* renamed from: b */
    protected final Map<String, C1221p> f1777b = new LinkedHashMap();

    /* renamed from: c */
    protected final Map<String, C1215j> f1778c = new HashMap();

    /* renamed from: d */
    protected C1206a f1779d = new C1206a();

    /* renamed from: f */
    private final Object f1780f = new Object();

    /* renamed from: g */
    private final Object f1781g = new Object();

    /* renamed from: h */
    private int f1782h;

    /* renamed from: i */
    private final Comparator<Map.Entry<String, C1215j>> f1783i = new C1219n(this);

    /* renamed from: j */
    private int f1784j = m1547r();

    public C1218m(String str, String str2) {
        if (!C1214i.f1755a) {
            m1542b(str);
            m1544c(str2);
            return;
        }
        m1541a(false);
    }

    /* renamed from: a */
    private C1215j m1537a(JSONObject jSONObject) {
        if (!jSONObject.has(DispatchConstants.DOMAIN)) {
            return null;
        }
        C1215j jVar = new C1215j();
        jVar.mo14392a(jSONObject.getString(DispatchConstants.DOMAIN));
        if (jSONObject.has("port")) {
            jVar.mo14390a(jSONObject.getInt("port"));
        }
        if (jSONObject.has("ip")) {
            jVar.mo14399b(jSONObject.getString("ip"));
        }
        if (jSONObject.has("consumeTime")) {
            jVar.mo14391a(jSONObject.getLong("consumeTime"));
        }
        if (jSONObject.has("detectSuccessTime")) {
            jVar.mo14398b(jSONObject.getLong("detectSuccessTime"));
        }
        if (jSONObject.has("isDomain")) {
            jVar.mo14394a(jSONObject.getBoolean("isDomain"));
        }
        if (jSONObject.has("connectTryCnt")) {
            jVar.mo14397b(jSONObject.getInt("connectTryCnt"));
        }
        return jVar;
    }

    /* renamed from: a */
    private List<String> m1538a() {
        String[] xfrAddress = SDKUrlConfig.getXfrAddress();
        ArrayList arrayList = new ArrayList();
        for (String str : xfrAddress) {
            if (!arrayList.contains(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private List<String> m1539a(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < jSONArray.length()) {
            try {
                arrayList.add(jSONArray.getJSONObject(i).getString(DispatchConstants.DOMAIN));
                i++;
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private void mo14410a(C1215j jVar) {
        C1221p pVar = new C1221p();
        pVar.mo14433a(mo14411b() == C1213h.WIFI);
        pVar.mo14432a(mo14412c());
        pVar.mo14431a(jVar);
        synchronized (this.f1781g) {
            this.f1777b.put(jVar.mo14389a(), pVar);
        }
        C1174c.m1310b().mo14317a(pVar, true, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0035  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m1541a(boolean r8) {
        /*
            r7 = this;
            r0 = 0
            r7.f1776a = r0
            boolean r0 = r7.m1548s()
            java.lang.String r1 = "null"
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = com.igexin.push.core.C1343f.f2156ar
            if (r0 == 0) goto L_0x0022
            com.igexin.push.core.c.h r0 = com.igexin.push.core.p050c.C1312h.m1937a()
            r2 = 1
            goto L_0x001f
        L_0x0016:
            java.lang.String r0 = com.igexin.push.core.C1343f.f2157as
            if (r0 == 0) goto L_0x0022
            com.igexin.push.core.c.h r0 = com.igexin.push.core.p050c.C1312h.m1937a()
            r2 = 0
        L_0x001f:
            r0.mo14675b((java.lang.String) r1, (boolean) r2)
        L_0x0022:
            java.util.List r0 = r7.m1538a()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Iterator r2 = r0.iterator()
        L_0x002f:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0054
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            com.igexin.push.c.j r4 = new com.igexin.push.c.j
            java.lang.String[] r5 = com.igexin.p032b.p033a.p035b.C1177f.m1333a((java.lang.String) r3)
            r6 = 2
            r5 = r5[r6]
            int r5 = java.lang.Integer.parseInt(r5)
            r4.<init>(r3, r5)
            if (r8 == 0) goto L_0x0050
            r7.mo14410a((com.igexin.push.p046c.C1215j) r4)
        L_0x0050:
            r1.add(r4)
            goto L_0x002f
        L_0x0054:
            com.igexin.push.c.a r8 = r7.f1779d
            r8.mo14367b((java.util.List<com.igexin.push.p046c.C1215j>) r1)
            r0.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p046c.C1218m.m1541a(boolean):void");
    }

    /* renamed from: b */
    private void m1542b(String str) {
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str)) {
            m1541a(true);
            return;
        }
        JSONArray jSONArray = null;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException unused) {
            jSONObject = null;
        }
        if (jSONObject == null || jSONObject.length() == 0) {
            m1541a(true);
            return;
        }
        if (jSONObject.has("lastDetectTime")) {
            try {
                this.f1776a = jSONObject.getLong("lastDetectTime");
            } catch (JSONException unused2) {
            }
        }
        if (Math.abs(System.currentTimeMillis() - this.f1776a) >= C1211f.f1746a) {
            m1541a(true);
            return;
        }
        if (jSONObject.has("list")) {
            try {
                jSONArray = jSONObject.getJSONArray("list");
            } catch (JSONException unused3) {
            }
        }
        if (jSONArray == null || jSONArray.length() == 0) {
            m1541a(true);
            return;
        }
        List<String> a = m1539a(jSONArray);
        if (a == null || a.isEmpty()) {
            m1541a(true);
            return;
        }
        List<String> a2 = m1538a();
        ArrayList arrayList = new ArrayList(a2);
        arrayList.retainAll(a);
        if (arrayList.size() != a.size()) {
            C1179b.m1354a(f1775e + " | db cache xfr != default, use default");
            arrayList.clear();
            a2.clear();
            a.clear();
            m1541a(true);
            return;
        }
        C1179b.m1354a(f1775e + " | db cache xfr == default, use cache");
        m1543b(jSONArray);
    }

    /* renamed from: b */
    private void m1543b(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < jSONArray.length()) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                C1215j a = m1537a(jSONObject);
                if (a != null) {
                    this.f1778c.put(a.mo14389a(), a);
                } else {
                    try {
                        a = m1545d(jSONObject.getString(DispatchConstants.DOMAIN));
                    } catch (Exception e) {
                        C1179b.m1354a(f1775e + "|initWithCacheData exception " + e.toString());
                        this.f1778c.clear();
                        m1541a(true);
                        return;
                    }
                }
                if (a != null) {
                    mo14410a(a);
                    arrayList.add(a);
                }
                i++;
            } catch (Exception e2) {
                C1179b.m1354a(f1775e + "|initWithCacheData exception " + e2.toString());
                return;
            }
        }
        this.f1779d.mo14367b((List<C1215j>) arrayList);
    }

    /* renamed from: c */
    private void m1544c(String str) {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = null;
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException unused) {
            }
            if (jSONObject != null && jSONObject.length() != 0) {
                if (jSONObject.has("detectFailedCnt")) {
                    try {
                        this.f1782h = jSONObject.getInt("detectFailedCnt");
                    } catch (JSONException unused2) {
                    }
                }
                if (jSONObject.has("loginFailedlCnt")) {
                    try {
                        this.f1779d.f1723c = jSONObject.getInt("loginFailedlCnt");
                    } catch (JSONException unused3) {
                    }
                }
                if (jSONObject.has("lastChange2BackupTime")) {
                    try {
                        this.f1779d.f1724d = jSONObject.getLong("lastChange2BackupTime");
                    } catch (JSONException unused4) {
                    }
                }
                if (jSONObject.has("lastOfflineTime")) {
                    try {
                        this.f1779d.f1725e = jSONObject.getLong("lastOfflineTime");
                    } catch (JSONException unused5) {
                    }
                }
                if (jSONObject.has("domainType")) {
                    try {
                        this.f1779d.f1721a = C1209d.m1495a(jSONObject.getInt("domainType"));
                        if (this.f1779d.f1721a == C1209d.BACKUP) {
                            this.f1779d.f1722b.set(true);
                        }
                    } catch (JSONException unused6) {
                    }
                }
            }
        }
    }

    /* renamed from: d */
    private C1215j m1545d(String str) {
        C1215j jVar = new C1215j();
        String[] a = C1177f.m1333a(str);
        jVar.mo14392a(str);
        jVar.mo14390a(Integer.parseInt(a[2]));
        return jVar;
    }

    /* renamed from: q */
    private void m1546q() {
        synchronized (this.f1780f) {
            this.f1778c.clear();
        }
    }

    /* renamed from: r */
    private int m1547r() {
        return m1538a().size();
    }

    /* renamed from: s */
    private boolean m1548s() {
        return mo14411b() == C1213h.MOBILE;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public C1221p mo14413a(String str) {
        synchronized (this.f1781g) {
            for (Map.Entry next : this.f1777b.entrySet()) {
                if (((String) next.getKey()).equals(str)) {
                    C1221p pVar = (C1221p) next.getValue();
                    return pVar;
                }
            }
            return null;
        }
    }

    /* renamed from: b */
    public abstract C1213h mo14411b();

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo14414b(C1215j jVar) {
        synchronized (this.f1780f) {
            this.f1778c.put(jVar.mo14389a(), jVar);
        }
        this.f1779d.mo14370e();
    }

    /* renamed from: c */
    public abstract C1220o mo14412c();

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public String mo14415c(C1215j jVar) {
        return jVar.mo14389a() + "[" + jVar.mo14400c() + "] ";
    }

    /* renamed from: d */
    public void mo14416d() {
        if (!mo14424l()) {
            C1179b.m1354a(f1775e + "|startDetect detect = false, return !!!");
            return;
        }
        C1179b.m1354a(f1775e + "|startDetect detect = true, start detect !!!");
        mo14421i();
    }

    /* renamed from: e */
    public void mo14417e() {
        synchronized (this.f1781g) {
            for (Map.Entry next : this.f1777b.entrySet()) {
                ((C1221p) next.getValue()).mo14432a((C1220o) null);
                ((C1221p) next.getValue()).mo14434b(true);
            }
        }
    }

    /* renamed from: f */
    public void mo14418f() {
        synchronized (this.f1781g) {
            for (Map.Entry next : this.f1777b.entrySet()) {
                ((C1221p) next.getValue()).mo14432a(mo14412c());
                ((C1221p) next.getValue()).mo14435c(true);
            }
        }
    }

    /* renamed from: g */
    public void mo14419g() {
        mo14417e();
        m1546q();
        List<String> a = m1538a();
        synchronized (this.f1781g) {
            int size = this.f1777b.size();
            if (a.size() < size) {
                int size2 = size - a.size();
                Iterator<Map.Entry<String, C1221p>> it = this.f1777b.entrySet().iterator();
                int i = 0;
                while (it.hasNext() && i < size2) {
                    ((C1221p) it.next().getValue()).mo14437h();
                    it.remove();
                    i++;
                }
            }
            ArrayList arrayList = new ArrayList(this.f1777b.values());
            this.f1777b.clear();
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < a.size(); i2++) {
                C1215j jVar = new C1215j();
                String[] a2 = C1177f.m1333a(a.get(i2));
                jVar.mo14392a(a.get(i2));
                jVar.mo14390a(Integer.parseInt(a2[2]));
                if (i2 < size) {
                    C1221p pVar = (C1221p) arrayList.get(i2);
                    pVar.mo14431a(jVar);
                    this.f1777b.put(jVar.mo14389a(), pVar);
                } else {
                    mo14410a(jVar);
                }
                arrayList2.add(jVar);
            }
            this.f1779d.mo14367b((List<C1215j>) arrayList2);
        }
    }

    /* renamed from: h */
    public void mo14420h() {
        mo14417e();
        m1546q();
        List<String> a = m1538a();
        synchronized (this.f1781g) {
            for (Map.Entry<String, C1221p> value : this.f1777b.entrySet()) {
                ((C1221p) value.getValue()).mo14437h();
            }
            this.f1777b.clear();
            ArrayList arrayList = new ArrayList();
            C1215j jVar = new C1215j();
            String[] a2 = C1177f.m1333a(a.get(0));
            jVar.mo14392a(a.get(0));
            jVar.mo14390a(Integer.parseInt(a2[2]));
            arrayList.add(jVar);
            this.f1779d.mo14367b((List<C1215j>) arrayList);
            arrayList.clear();
        }
    }

    /* renamed from: i */
    public void mo14421i() {
        this.f1776a = System.currentTimeMillis();
        synchronized (this.f1781g) {
            for (Map.Entry next : this.f1777b.entrySet()) {
                ((C1221p) next.getValue()).mo14432a(mo14412c());
                if (((C1221p) next.getValue()).mo14436f_() != null) {
                    ((C1221p) next.getValue()).mo14436f_().mo14396b();
                }
                ((C1221p) next.getValue()).mo14438i();
            }
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004c */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0053  */
    /* renamed from: j */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo14422j() {
        /*
            r6 = this;
            monitor-enter(r6)
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0076 }
            r6.f1776a = r0     // Catch:{ all -> 0x0076 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ all -> 0x0076 }
            r0.<init>()     // Catch:{ all -> 0x0076 }
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch:{ all -> 0x0076 }
            r1.<init>()     // Catch:{ all -> 0x0076 }
            java.lang.Object r2 = r6.f1781g     // Catch:{ all -> 0x0076 }
            monitor-enter(r2)     // Catch:{ all -> 0x0076 }
            java.lang.String r3 = "lastDetectTime"
            long r4 = r6.f1776a     // Catch:{ Exception -> 0x004c }
            r0.put(r3, r4)     // Catch:{ Exception -> 0x004c }
            java.lang.String r3 = "list"
            r0.put(r3, r1)     // Catch:{ Exception -> 0x004c }
            java.util.Map<java.lang.String, com.igexin.push.c.p> r3 = r6.f1777b     // Catch:{ Exception -> 0x004c }
            java.util.Set r3 = r3.entrySet()     // Catch:{ Exception -> 0x004c }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Exception -> 0x004c }
        L_0x002a:
            boolean r4 = r3.hasNext()     // Catch:{ Exception -> 0x004c }
            if (r4 == 0) goto L_0x004c
            java.lang.Object r4 = r3.next()     // Catch:{ Exception -> 0x004c }
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4     // Catch:{ Exception -> 0x004c }
            java.lang.Object r4 = r4.getValue()     // Catch:{ Exception -> 0x004c }
            com.igexin.push.c.p r4 = (com.igexin.push.p046c.C1221p) r4     // Catch:{ Exception -> 0x004c }
            com.igexin.push.c.j r4 = r4.mo14436f_()     // Catch:{ Exception -> 0x004c }
            org.json.JSONObject r4 = r4.mo14405h()     // Catch:{ Exception -> 0x004c }
            if (r4 == 0) goto L_0x002a
            r1.put(r4)     // Catch:{ Exception -> 0x004c }
            goto L_0x002a
        L_0x004a:
            r0 = move-exception
            goto L_0x0074
        L_0x004c:
            monitor-exit(r2)     // Catch:{ all -> 0x004a }
            int r1 = r0.length()     // Catch:{ all -> 0x0076 }
            if (r1 <= 0) goto L_0x0072
            boolean r1 = r6.m1548s()     // Catch:{ all -> 0x0076 }
            if (r1 == 0) goto L_0x0066
            com.igexin.push.core.c.h r1 = com.igexin.push.core.p050c.C1312h.m1937a()     // Catch:{ all -> 0x0076 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0076 }
            r2 = 1
            r1.mo14675b((java.lang.String) r0, (boolean) r2)     // Catch:{ all -> 0x0076 }
            goto L_0x0072
        L_0x0066:
            com.igexin.push.core.c.h r1 = com.igexin.push.core.p050c.C1312h.m1937a()     // Catch:{ all -> 0x0076 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0076 }
            r2 = 0
            r1.mo14675b((java.lang.String) r0, (boolean) r2)     // Catch:{ all -> 0x0076 }
        L_0x0072:
            monitor-exit(r6)
            return
        L_0x0074:
            monitor-exit(r2)     // Catch:{ all -> 0x004a }
            throw r0     // Catch:{ all -> 0x0076 }
        L_0x0076:
            r0 = move-exception
            monitor-exit(r6)
            goto L_0x007a
        L_0x0079:
            throw r0
        L_0x007a:
            goto L_0x0079
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p046c.C1218m.mo14422j():void");
    }

    /* access modifiers changed from: protected */
    /* renamed from: k */
    public void mo14423k() {
        C1312h.m1937a().mo14675b("null", true);
        C1312h.m1937a().mo14675b("null", false);
    }

    /* access modifiers changed from: protected */
    /* renamed from: l */
    public boolean mo14424l() {
        long abs = Math.abs(System.currentTimeMillis() - this.f1776a);
        if (abs >= C1211f.f1746a - 3600) {
            C1179b.m1354a(f1775e + "|current time - last detect time > " + (C1211f.f1746a / 1000) + " s, detect = true");
            C1216k.f1769a.set(true);
            return true;
        } else if (C1216k.f1769a.getAndSet(true)) {
            return false;
        } else {
            long abs2 = Math.abs(C1211f.f1746a - abs);
            C1216k.m1527c_().mo14407a(abs2);
            C1179b.m1354a(f1775e + "|set next detect time = " + abs2);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: m */
    public synchronized void mo14425m() {
        if (this.f1782h != 0) {
            this.f1782h = 0;
            mo14427o();
        }
        this.f1779d.mo14371f();
    }

    /* access modifiers changed from: protected */
    /* renamed from: n */
    public synchronized void mo14426n() {
        this.f1782h++;
        C1179b.m1354a(f1775e + "|detect failed cnt = " + (this.f1782h / this.f1784j));
        int i = this.f1782h / this.f1784j;
        if (i <= C1234k.f1863x) {
            mo14427o();
        }
        if (i >= C1234k.f1863x && !this.f1779d.f1722b.get()) {
            C1179b.m1354a(f1775e + "|detect failed cnt = " + (this.f1782h / this.f1784j) + ", enter backup ++++++++");
            this.f1779d.mo14372g();
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:1|2|3|4|5|6|7|(2:9|(1:11)(1:12))|13) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0035 */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x003b  */
    /* renamed from: o */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo14427o() {
        /*
            r4 = this;
            monitor-enter(r4)
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ all -> 0x005c }
            r0.<init>()     // Catch:{ all -> 0x005c }
            java.lang.String r1 = "detectFailedCnt"
            int r2 = r4.f1782h     // Catch:{ Exception -> 0x0035 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0035 }
            java.lang.String r1 = "loginFailedlCnt"
            com.igexin.push.c.a r2 = r4.f1779d     // Catch:{ Exception -> 0x0035 }
            int r2 = r2.f1723c     // Catch:{ Exception -> 0x0035 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0035 }
            java.lang.String r1 = "lastChange2BackupTime"
            com.igexin.push.c.a r2 = r4.f1779d     // Catch:{ Exception -> 0x0035 }
            long r2 = r2.f1724d     // Catch:{ Exception -> 0x0035 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0035 }
            java.lang.String r1 = "lastOfflineTime"
            com.igexin.push.c.a r2 = r4.f1779d     // Catch:{ Exception -> 0x0035 }
            long r2 = r2.f1725e     // Catch:{ Exception -> 0x0035 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0035 }
            java.lang.String r1 = "domainType"
            com.igexin.push.c.a r2 = r4.f1779d     // Catch:{ Exception -> 0x0035 }
            com.igexin.push.c.d r2 = r2.f1721a     // Catch:{ Exception -> 0x0035 }
            int r2 = r2.mo14378b()     // Catch:{ Exception -> 0x0035 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0035 }
        L_0x0035:
            int r1 = r0.length()     // Catch:{ all -> 0x005c }
            if (r1 <= 0) goto L_0x005a
            boolean r1 = r4.m1548s()     // Catch:{ all -> 0x005c }
            if (r1 == 0) goto L_0x004e
            com.igexin.push.core.c.h r1 = com.igexin.push.core.p050c.C1312h.m1937a()     // Catch:{ all -> 0x005c }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x005c }
            r2 = 1
            r1.mo14669a((java.lang.String) r0, (boolean) r2)     // Catch:{ all -> 0x005c }
            goto L_0x005a
        L_0x004e:
            com.igexin.push.core.c.h r1 = com.igexin.push.core.p050c.C1312h.m1937a()     // Catch:{ all -> 0x005c }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x005c }
            r2 = 0
            r1.mo14669a((java.lang.String) r0, (boolean) r2)     // Catch:{ all -> 0x005c }
        L_0x005a:
            monitor-exit(r4)
            return
        L_0x005c:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p046c.C1218m.mo14427o():void");
    }

    /* access modifiers changed from: protected */
    /* renamed from: p */
    public boolean mo14428p() {
        synchronized (this.f1780f) {
            for (Map.Entry<String, C1215j> value : this.f1778c.entrySet()) {
                if (((C1215j) value.getValue()).mo14402e() != 2147483647L) {
                    return true;
                }
            }
            return false;
        }
    }
}
