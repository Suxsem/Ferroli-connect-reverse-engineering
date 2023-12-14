package com.igexin.p032b.p033a.p035b.p036a.p037a;

import android.os.Handler;
import com.igexin.p032b.p033a.p035b.C1173b;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p035b.C1175d;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p033a.p040d.C1191f;
import com.igexin.push.core.C1340e;
import com.igexin.push.util.C1589n;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.igexin.b.a.b.a.a.f */
public final class C1161f {

    /* renamed from: m */
    private static final Object f1554m = new Object();

    /* renamed from: a */
    public Lock f1555a;

    /* renamed from: b */
    public Condition f1556b;

    /* renamed from: c */
    ConcurrentLinkedQueue<C1168m> f1557c;

    /* renamed from: d */
    private C1173b f1558d;

    /* renamed from: e */
    private C1175d f1559e;

    /* renamed from: f */
    private Socket f1560f;

    /* renamed from: g */
    private C1167l f1561g;

    /* renamed from: h */
    private C1169n f1562h;

    /* renamed from: i */
    private C1158c f1563i;

    /* renamed from: j */
    private AtomicBoolean f1564j;

    /* renamed from: k */
    private boolean f1565k;

    /* renamed from: l */
    private List<C1168m> f1566l;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public Handler f1567n;

    /* renamed from: o */
    private long f1568o;

    /* renamed from: p */
    private final Comparator<C1168m> f1569p;

    private C1161f() {
        this.f1564j = new AtomicBoolean(false);
        this.f1555a = new ReentrantLock();
        this.f1556b = this.f1555a.newCondition();
        this.f1566l = new ArrayList();
        this.f1557c = new ConcurrentLinkedQueue<>();
        this.f1569p = new C1165j(this);
        this.f1567n = C1340e.m2032a().mo14705b();
    }

    /* synthetic */ C1161f(C1162g gVar) {
        this();
    }

    /* renamed from: a */
    public static C1161f m1252a() {
        return C1166k.f1574a;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m1255b(C1168m mVar) {
        if (mVar.f1659y <= 0 || mVar.f1638D == null) {
            mVar.mo14305p();
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        mVar.mo14299b(currentTimeMillis);
        synchronized (f1554m) {
            this.f1566l.add(mVar);
            Collections.sort(this.f1566l, this.f1569p);
            this.f1568o = TimeUnit.SECONDS.toMillis((long) this.f1566l.get(0).f1659y);
            if (this.f1568o > 0 && this.f1566l.size() == 1) {
                C1179b.m1354a("GS-M|add : " + mVar.toString() + " --- " + mVar.f1608c.getClass().getName() + " set alarm " + "delay = " + (this.f1568o + C1191f.f1662u));
                C1174c.m1310b().mo14320b(currentTimeMillis + this.f1568o + C1191f.f1662u);
            }
        }
    }

    /* renamed from: b */
    private void m1256b(Socket socket) {
        this.f1561g = new C1167l(new C1170o(socket.getInputStream()), this.f1558d, this.f1559e);
        this.f1561g.mo14249a(new C1163h(this));
        C1174c.m1310b().mo14316a((C1190e) this.f1561g, true);
    }

    /* renamed from: c */
    private void m1257c(Socket socket) {
        this.f1562h = new C1169n(new C1171p(socket.getOutputStream()), this.f1558d, this.f1559e);
        this.f1562h.mo14251a(new C1164i(this));
        C1174c.m1310b().mo14316a((C1190e) this.f1562h, true);
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public void m1258i() {
        if (!this.f1564j.getAndSet(true)) {
            this.f1567n.sendEmptyMessage(C1172q.TCP_IO_EXCEPTION.ordinal());
        }
    }

    /* renamed from: j */
    private void m1259j() {
        this.f1564j.set(false);
        C1340e.m2032a().mo14710g().mo15188a(C1172q.TCP_DISCONNECT_SUCCESS);
    }

    /* renamed from: k */
    private void m1260k() {
        C1179b.m1354a("GS-M|disconnect");
        C1158c cVar = this.f1563i;
        if (cVar != null) {
            cVar.mo14234j();
        }
        C1169n nVar = this.f1562h;
        if (nVar != null) {
            nVar.mo14252j();
        }
        C1167l lVar = this.f1561g;
        if (lVar != null) {
            lVar.mo14250j();
        }
        Socket socket = this.f1560f;
        if (socket != null) {
            try {
                if (!socket.isClosed()) {
                    this.f1560f.close();
                }
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: l */
    private void m1261l() {
        C1169n nVar = this.f1562h;
        if (nVar != null) {
            nVar.f1583j = null;
            this.f1562h = null;
        }
        C1167l lVar = this.f1561g;
        if (lVar != null) {
            lVar.f1578i = null;
            this.f1561g = null;
        }
        this.f1563i = null;
        this.f1560f = null;
        this.f1559e = null;
    }

    /* renamed from: m */
    private boolean m1262m() {
        C1158c cVar = this.f1563i;
        if (cVar != null && !cVar.f1543e) {
            return false;
        }
        C1167l lVar = this.f1561g;
        if (lVar != null && !lVar.f1543e) {
            return false;
        }
        C1169n nVar = this.f1562h;
        if (nVar != null && !nVar.f1543e) {
            return false;
        }
        m1261l();
        return true;
    }

    /* renamed from: n */
    private boolean m1263n() {
        Socket socket = this.f1560f;
        return socket != null && !socket.isClosed();
    }

    /* renamed from: o */
    private void m1264o() {
        if (!C1589n.m3262d()) {
            C1174c.m1310b().mo14322e();
            C1179b.m1354a("GS-M|cancel alrm");
            synchronized (f1554m) {
                if (!this.f1566l.isEmpty()) {
                    for (C1168m p : this.f1566l) {
                        p.mo14305p();
                    }
                    this.f1566l.clear();
                }
            }
        }
        if (!this.f1557c.isEmpty()) {
            Iterator<C1168m> it = this.f1557c.iterator();
            while (it.hasNext()) {
                it.next().mo14305p();
            }
            this.f1557c.clear();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x000f */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14236a(com.igexin.p032b.p033a.p035b.p036a.p037a.C1168m r2) {
        /*
            r1 = this;
            java.util.concurrent.locks.Lock r0 = r1.f1555a     // Catch:{ Exception -> 0x000f, all -> 0x0015 }
            r0.lock()     // Catch:{ Exception -> 0x000f, all -> 0x0015 }
            java.util.concurrent.ConcurrentLinkedQueue<com.igexin.b.a.b.a.a.m> r0 = r1.f1557c     // Catch:{ Exception -> 0x000f, all -> 0x0015 }
            r0.offer(r2)     // Catch:{ Exception -> 0x000f, all -> 0x0015 }
            java.util.concurrent.locks.Condition r2 = r1.f1556b     // Catch:{ Exception -> 0x000f, all -> 0x0015 }
            r2.signalAll()     // Catch:{ Exception -> 0x000f, all -> 0x0015 }
        L_0x000f:
            java.util.concurrent.locks.Lock r2 = r1.f1555a     // Catch:{ Exception -> 0x001c }
            r2.unlock()     // Catch:{ Exception -> 0x001c }
            goto L_0x001c
        L_0x0015:
            r2 = move-exception
            java.util.concurrent.locks.Lock r0 = r1.f1555a     // Catch:{ Exception -> 0x001b }
            r0.unlock()     // Catch:{ Exception -> 0x001b }
        L_0x001b:
            throw r2
        L_0x001c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p035b.p036a.p037a.C1161f.mo14236a(com.igexin.b.a.b.a.a.m):void");
    }

    /* renamed from: a */
    public void mo14237a(C1173b bVar) {
        this.f1558d = bVar;
        C1167l lVar = this.f1561g;
        if (lVar != null) {
            lVar.f1579j = bVar;
        }
        C1169n nVar = this.f1562h;
        if (nVar != null) {
            nVar.f1582i = bVar;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00cb, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14238a(java.lang.String r11) {
        /*
            r10 = this;
            boolean r0 = com.igexin.push.util.C1589n.m3262d()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            long r0 = java.lang.System.currentTimeMillis()
            java.lang.Object r2 = f1554m
            monitor-enter(r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cc }
            r3.<init>()     // Catch:{ all -> 0x00cc }
            java.lang.String r4 = "GS-M|receive: "
            r3.append(r4)     // Catch:{ all -> 0x00cc }
            r3.append(r11)     // Catch:{ all -> 0x00cc }
            java.lang.String r11 = " -- resp"
            r3.append(r11)     // Catch:{ all -> 0x00cc }
            java.lang.String r11 = r3.toString()     // Catch:{ all -> 0x00cc }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)     // Catch:{ all -> 0x00cc }
            java.util.List<com.igexin.b.a.b.a.a.m> r11 = r10.f1566l     // Catch:{ all -> 0x00cc }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ all -> 0x00cc }
        L_0x002d:
            boolean r3 = r11.hasNext()     // Catch:{ all -> 0x00cc }
            r4 = 0
            r6 = 0
            if (r3 == 0) goto L_0x0066
            java.lang.Object r3 = r11.next()     // Catch:{ all -> 0x00cc }
            com.igexin.b.a.b.a.a.m r3 = (com.igexin.p032b.p033a.p035b.p036a.p037a.C1168m) r3     // Catch:{ all -> 0x00cc }
            com.igexin.b.a.d.a.f r7 = r3.f1638D     // Catch:{ all -> 0x00cc }
            boolean r7 = r7.mo14279a(r0, r3)     // Catch:{ all -> 0x00cc }
            if (r7 == 0) goto L_0x0051
            r3.mo14305p()     // Catch:{ all -> 0x00cc }
            com.igexin.b.a.d.a.f r7 = r3.f1638D     // Catch:{ all -> 0x00cc }
            r7.mo14278a(r3)     // Catch:{ all -> 0x00cc }
            r3 = 1
            r11.remove()     // Catch:{ all -> 0x00cc }
            goto L_0x0067
        L_0x0051:
            com.igexin.b.a.d.a.f r6 = r3.f1638D     // Catch:{ all -> 0x00cc }
            long r6 = r6.mo14280b(r0, r3)     // Catch:{ all -> 0x00cc }
            long r8 = r10.f1568o     // Catch:{ all -> 0x00cc }
            int r3 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r3 < 0) goto L_0x0063
            long r3 = r10.f1568o     // Catch:{ all -> 0x00cc }
            int r5 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x002d
        L_0x0063:
            r10.f1568o = r6     // Catch:{ all -> 0x00cc }
            goto L_0x002d
        L_0x0066:
            r3 = 0
        L_0x0067:
            com.igexin.b.a.b.c r11 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()     // Catch:{ all -> 0x00cc }
            r11.mo14322e()     // Catch:{ all -> 0x00cc }
            if (r3 == 0) goto L_0x007a
            java.lang.String r11 = "GS-M|time out"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)     // Catch:{ all -> 0x00cc }
            r10.mo14243e()     // Catch:{ all -> 0x00cc }
            monitor-exit(r2)     // Catch:{ all -> 0x00cc }
            return
        L_0x007a:
            java.util.List<com.igexin.b.a.b.a.a.m> r11 = r10.f1566l     // Catch:{ all -> 0x00cc }
            int r11 = r11.size()     // Catch:{ all -> 0x00cc }
            if (r11 <= 0) goto L_0x0099
            java.util.List<com.igexin.b.a.b.a.a.m> r11 = r10.f1566l     // Catch:{ all -> 0x00cc }
            java.lang.Object r11 = r11.get(r6)     // Catch:{ all -> 0x00cc }
            com.igexin.b.a.b.a.a.m r11 = (com.igexin.p032b.p033a.p035b.p036a.p037a.C1168m) r11     // Catch:{ all -> 0x00cc }
            r11.mo14305p()     // Catch:{ all -> 0x00cc }
            com.igexin.b.a.b.c r3 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()     // Catch:{ all -> 0x00cc }
            r3.mo14319a((java.lang.Object) r11)     // Catch:{ all -> 0x00cc }
            java.util.List<com.igexin.b.a.b.a.a.m> r3 = r10.f1566l     // Catch:{ all -> 0x00cc }
            r3.remove(r11)     // Catch:{ all -> 0x00cc }
        L_0x0099:
            java.util.List<com.igexin.b.a.b.a.a.m> r11 = r10.f1566l     // Catch:{ all -> 0x00cc }
            int r11 = r11.size()     // Catch:{ all -> 0x00cc }
            if (r11 <= 0) goto L_0x00ca
            long r6 = r10.f1568o     // Catch:{ all -> 0x00cc }
            int r11 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r11 <= 0) goto L_0x00ca
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cc }
            r11.<init>()     // Catch:{ all -> 0x00cc }
            java.lang.String r3 = "GS-M|set alarm = "
            r11.append(r3)     // Catch:{ all -> 0x00cc }
            long r3 = r10.f1568o     // Catch:{ all -> 0x00cc }
            r11.append(r3)     // Catch:{ all -> 0x00cc }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x00cc }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)     // Catch:{ all -> 0x00cc }
            com.igexin.b.a.b.c r11 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()     // Catch:{ all -> 0x00cc }
            long r3 = r10.f1568o     // Catch:{ all -> 0x00cc }
            long r0 = r0 + r3
            long r3 = com.igexin.p032b.p033a.p040d.C1191f.f1662u     // Catch:{ all -> 0x00cc }
            long r0 = r0 + r3
            r11.mo14320b(r0)     // Catch:{ all -> 0x00cc }
        L_0x00ca:
            monitor-exit(r2)     // Catch:{ all -> 0x00cc }
            return
        L_0x00cc:
            r11 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00cc }
            goto L_0x00d0
        L_0x00cf:
            throw r11
        L_0x00d0:
            goto L_0x00cf
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p035b.p036a.p037a.C1161f.mo14238a(java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo14239a(Socket socket) {
        try {
            if (!this.f1563i.mo14223i()) {
                this.f1560f = socket;
                this.f1559e = new C1175d();
                m1256b(socket);
                m1257c(socket);
            }
        } catch (Exception e) {
            C1179b.m1354a("GS-M|" + e.toString());
            m1258i();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public void mo14240b() {
        C1340e.m2032a().mo14710g().mo15188a(C1172q.TCP_IO_EXCEPTION);
    }

    /* renamed from: c */
    public synchronized void mo14241c() {
        this.f1567n.sendEmptyMessage(C1172q.TCP_DISCONNECT.ordinal());
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public void mo14242d() {
        this.f1567n.sendEmptyMessage(C1172q.TCP_START_CONNECT.ordinal());
        this.f1565k = false;
    }

    /* renamed from: e */
    public void mo14243e() {
        C1179b.m1354a("GS-M|alarm timeout~~");
        m1258i();
    }

    /* renamed from: f */
    public void mo14244f() {
        m1264o();
        if (!(this.f1563i == null && this.f1562h == null && this.f1561g == null) && !m1262m()) {
            m1260k();
        } else {
            m1259j();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: g */
    public void mo14245g() {
        boolean n = m1263n();
        if (n || this.f1563i != null) {
            C1179b.m1354a("GS-Mstart connect, isConnected = " + n + ", ctask = " + this.f1563i);
            return;
        }
        C1179b.m1354a("GS-M|disconnect = true, reconnect");
        this.f1563i = new C1158c(new C1162g(this));
        C1174c.m1310b().mo14316a((C1190e) this.f1563i, true);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: h */
    public void mo14246h() {
        if (m1262m() && !this.f1565k) {
            m1259j();
            this.f1565k = true;
        }
    }
}
