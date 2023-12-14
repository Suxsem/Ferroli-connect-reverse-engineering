package com.igexin.p032b.p033a.p040d;

import com.igexin.p032b.p033a.p040d.p041a.C1183c;
import com.igexin.p032b.p033a.p040d.p041a.C1184d;
import com.igexin.p032b.p033a.p040d.p041a.C1186f;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.igexin.b.a.d.e */
public abstract class C1190e extends C1180a {

    /* renamed from: E */
    protected static C1191f f1634E;

    /* renamed from: A */
    public int f1635A;

    /* renamed from: B */
    public Exception f1636B;

    /* renamed from: C */
    public Object f1637C;

    /* renamed from: D */
    public C1186f f1638D;

    /* renamed from: F */
    protected final ReentrantLock f1639F;

    /* renamed from: G */
    protected final Condition f1640G;

    /* renamed from: H */
    Thread f1641H;

    /* renamed from: I */
    protected volatile boolean f1642I;

    /* renamed from: J */
    int f1643J;

    /* renamed from: K */
    protected C1183c f1644K;

    /* renamed from: a */
    private byte f1645a;

    /* renamed from: k */
    protected volatile boolean f1646k;

    /* renamed from: m */
    protected volatile boolean f1647m;

    /* renamed from: n */
    protected volatile boolean f1648n;

    /* renamed from: o */
    protected volatile boolean f1649o;

    /* renamed from: p */
    protected volatile boolean f1650p;

    /* renamed from: q */
    protected volatile boolean f1651q;

    /* renamed from: r */
    protected volatile boolean f1652r;

    /* renamed from: s */
    protected volatile boolean f1653s;

    /* renamed from: t */
    protected volatile boolean f1654t;

    /* renamed from: u */
    protected volatile long f1655u;

    /* renamed from: v */
    volatile int f1656v;

    /* renamed from: w */
    public long f1657w;

    /* renamed from: x */
    public int f1658x;

    /* renamed from: y */
    public int f1659y;

    /* renamed from: z */
    public int f1660z;

    public C1190e(int i) {
        this(i, (C1183c) null);
    }

    public C1190e(int i, C1183c cVar) {
        this.f1660z = i;
        this.f1644K = cVar;
        this.f1639F = new ReentrantLock();
        this.f1640G = this.f1639F.newCondition();
    }

    /* renamed from: a */
    public final int mo14294a(long j, TimeUnit timeUnit) {
        if (j > 0) {
            int a = f1634E.f1667k.mo14287a(this, j, timeUnit);
            if (a == -2) {
                return -2;
            }
            if (a != -1) {
                return a != 1 ? 0 : 1;
            }
            this.f1655u = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(j, timeUnit);
            return -1;
        }
    }

    /* renamed from: a */
    public long mo14295a(TimeUnit timeUnit) {
        return timeUnit.convert(mo14304o(), TimeUnit.MILLISECONDS);
    }

    /* renamed from: a */
    public final void mo14296a(int i) {
        this.f1645a = (byte) (this.f1645a & 15);
        this.f1645a = (byte) (((i & 15) << 4) | this.f1645a);
    }

    /* renamed from: a */
    public final void mo14297a(int i, C1186f fVar) {
        if (i >= 0) {
            this.f1659y = i;
            this.f1638D = fVar;
            return;
        }
        throw new IllegalArgumentException("second must > 0");
    }

    /* renamed from: a */
    public final void mo14298a(C1183c cVar) {
        this.f1644K = cVar;
    }

    /* renamed from: b */
    public final void mo14299b(long j) {
        this.f1657w = j;
    }

    /* renamed from: b_ */
    public void mo14232b_() {
        this.f1641H = Thread.currentThread();
        this.f1650p = true;
    }

    /* renamed from: c */
    public void mo14300c() {
        if (this.f1646k || this.f1647m) {
            mo14233f();
        }
    }

    /* renamed from: d */
    public void mo14221d() {
        this.f1653s = true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public abstract void mo14222e();

    /* renamed from: f */
    public void mo14233f() {
        this.f1637C = null;
        this.f1636B = null;
        this.f1641H = null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public void mo14301g() {
    }

    /* access modifiers changed from: protected */
    /* renamed from: g_ */
    public void mo14302g_() {
        if (!this.f1649o && !this.f1651q && !this.f1652r) {
            this.f1646k = true;
        } else if ((!this.f1651q || this.f1646k) && (!this.f1649o || this.f1648n || this.f1646k)) {
            return;
        }
        this.f1650p = false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: n */
    public final void mo14303n() {
        this.f1643J++;
        this.f1643J &= 1090519038;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: o */
    public long mo14304o() {
        return this.f1655u - System.currentTimeMillis();
    }

    /* renamed from: p */
    public final void mo14305p() {
        this.f1646k = true;
    }

    /* renamed from: q */
    public final boolean mo14306q() {
        return this.f1648n;
    }

    /* renamed from: r */
    public final boolean mo14307r() {
        return this.f1647m;
    }

    /* renamed from: s */
    public final Thread mo14308s() {
        return this.f1641H;
    }

    /* renamed from: t */
    public final void mo14309t() {
        this.f1647m = true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: u */
    public void mo14310u() {
        C1183c cVar = this.f1644K;
        if (cVar != null) {
            cVar.mo14277a(C1184d.error);
        }
    }
}
