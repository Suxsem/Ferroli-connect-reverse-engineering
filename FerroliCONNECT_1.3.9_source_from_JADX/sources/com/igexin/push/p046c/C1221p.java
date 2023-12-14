package com.igexin.push.p046c;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.C1343f;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.push.util.C1576a;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.igexin.push.c.p */
public class C1221p extends C1575h {

    /* renamed from: a */
    public static final int f1786a = ((int) m1570a(20150601));
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static final String f1787b = "com.igexin.push.c.p";

    /* renamed from: c */
    private static int f1788c = SDKUrlConfig.getXfrAddress().length;

    /* renamed from: e */
    private static ExecutorService f1789e = new ThreadPoolExecutor(0, f1788c, 60, TimeUnit.SECONDS, new SynchronousQueue());

    /* renamed from: f */
    private static ExecutorService f1790f = new ThreadPoolExecutor(0, f1788c, 60, TimeUnit.SECONDS, new SynchronousQueue());

    /* renamed from: L */
    private boolean f1791L;
    /* access modifiers changed from: private */

    /* renamed from: M */
    public volatile boolean f1792M = true;
    /* access modifiers changed from: private */

    /* renamed from: N */
    public volatile boolean f1793N;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public C1215j f1794g;

    /* renamed from: h */
    private Future<C1215j> f1795h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public C1220o f1796i;

    /* renamed from: j */
    private long f1797j = -1;

    public C1221p() {
        super(604800000);
        this.f1649o = true;
    }

    /* renamed from: a */
    private static long m1570a(long j) {
        double random = Math.random();
        double d = (double) (j / 10);
        Double.isNaN(d);
        Double.isNaN(d);
        return j + ((long) (((random * d) * 2.0d) - d));
    }

    /* renamed from: e_ */
    public static void m1576e_() {
        if (f1788c == SDKUrlConfig.getXfrAddress().length) {
            C1179b.m1354a(f1787b + "|resetExecutors size eq");
            return;
        }
        C1179b.m1354a(f1787b + "|resetExecutors xfr from " + f1788c + " to " + SDKUrlConfig.getXfrAddress().length);
        f1788c = SDKUrlConfig.getXfrAddress().length;
        ExecutorService executorService = f1789e;
        if (executorService != null && !executorService.isShutdown()) {
            f1789e.shutdownNow();
        }
        ExecutorService executorService2 = f1790f;
        if (executorService2 != null && !executorService2.isShutdown()) {
            f1790f.shutdownNow();
        }
        int i = f1788c;
        if (i > 1) {
            f1789e = new ThreadPoolExecutor(0, i, 60, TimeUnit.MILLISECONDS, new SynchronousQueue());
            f1790f = new ThreadPoolExecutor(0, f1788c, 60, TimeUnit.MILLISECONDS, new SynchronousQueue());
        }
    }

    /* renamed from: v */
    private void m1578v() {
        ExecutorService executorService = this.f1791L ? f1789e : f1790f;
        if (executorService == null) {
            C1179b.m1354a(f1787b + "|error, executorService = null");
            return;
        }
        this.f1795h = executorService.submit(new C1222q(this));
    }

    /* renamed from: w */
    private void m1579w() {
        mo14294a(1800000, TimeUnit.MILLISECONDS);
        m1578v();
    }

    /* access modifiers changed from: private */
    /* renamed from: x */
    public String m1580x() {
        return this.f1794g.mo14389a() + "[" + this.f1794g.mo14400c() + "] ";
    }

    /* renamed from: y */
    private void m1581y() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(f1787b);
            sb.append("|stopThread detectResultFuture is canceled = ");
            sb.append(this.f1795h != null && this.f1795h.isCancelled());
            C1179b.m1354a(sb.toString());
            if (this.f1795h != null && !this.f1795h.isCancelled() && !this.f1795h.isDone()) {
                this.f1795h.cancel(true);
                this.f1795h = null;
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        if (!this.f1793N) {
            m1579w();
        }
    }

    /* renamed from: a */
    public void mo14431a(C1215j jVar) {
        this.f1794g = jVar;
    }

    /* renamed from: a */
    public void mo14432a(C1220o oVar) {
        synchronized (C1220o.class) {
            this.f1796i = oVar;
        }
    }

    /* renamed from: a */
    public void mo14433a(boolean z) {
        this.f1791L = z;
    }

    /* renamed from: b */
    public final int mo14231b() {
        return f1786a;
    }

    /* renamed from: b */
    public void mo14434b(boolean z) {
        C1179b.m1354a(f1787b + "|detect " + m1580x() + "finish, task stop");
        if (z) {
            m1581y();
        }
        mo14294a(604800000, TimeUnit.MILLISECONDS);
    }

    /* renamed from: c */
    public void mo14300c() {
        super.mo14300c();
    }

    /* renamed from: c */
    public void mo14435c(boolean z) {
        long j;
        if (z) {
            mo14439j();
            C1179b.m1354a(f1787b + "|detect " + m1580x() + "reset delay = 0");
        }
        C1343f.f2171h = C1576a.m3221g();
        C1179b.m1354a(f1787b + "|network available : " + C1343f.f2171h);
        if (!C1343f.f2171h) {
            this.f1797j = 604800000;
        } else {
            long j2 = this.f1797j;
            if (j2 <= 2000) {
                j = 500;
            } else {
                j = 15000;
                if (j2 <= 15000) {
                    j = 5000;
                } else if (j2 > 60000) {
                    j = 120000;
                }
            }
            this.f1797j = j2 + j;
            if (this.f1797j > 3600000) {
                this.f1797j = 3600000;
            }
            this.f1797j = m1570a(this.f1797j);
            C1179b.m1354a(f1787b + "|detect " + m1580x() + "redetect delay = " + this.f1797j);
        }
        mo14294a(this.f1797j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: d */
    public void mo14221d() {
    }

    /* renamed from: f_ */
    public C1215j mo14436f_() {
        return this.f1794g;
    }

    /* renamed from: h */
    public void mo14437h() {
        this.f1793N = true;
        this.f1792M = false;
        this.f1649o = false;
        this.f1796i = null;
        mo14305p();
        m1581y();
    }

    /* renamed from: i */
    public void mo14438i() {
        C1179b.m1354a(f1787b + "|detect " + m1580x() + "start");
        this.f1797j = 50;
        mo14294a(this.f1797j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: j */
    public void mo14439j() {
        this.f1797j = 0;
    }
}
