package com.igexin.p032b.p033a.p035b.p036a.p037a;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p035b.C1173b;
import com.igexin.p032b.p033a.p035b.C1175d;
import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.p036a.p037a.p038a.C1154b;
import com.igexin.p032b.p033a.p039c.C1179b;

/* renamed from: com.igexin.b.a.b.a.a.l */
public final class C1167l extends C1152a {

    /* renamed from: L */
    private C1154b f1575L;

    /* renamed from: M */
    private byte[] f1576M;

    /* renamed from: N */
    private C1175d f1577N;

    /* renamed from: i */
    C1170o f1578i;

    /* renamed from: j */
    C1173b f1579j;

    public C1167l(C1170o oVar, C1173b bVar, C1175d dVar) {
        super(-2035, (String) null, bVar);
        this.f1579j = bVar;
        this.f1578i = oVar;
        this.f1577N = dVar;
    }

    /* renamed from: a */
    public void mo14249a(C1154b bVar) {
        this.f1575L = bVar;
    }

    /* renamed from: b */
    public final int mo14231b() {
        return -2035;
    }

    /* renamed from: b_ */
    public void mo14232b_() {
        super.mo14232b_();
        Thread currentThread = Thread.currentThread();
        C1179b.m1354a("GS-R|" + currentThread + " running");
        while (this.f1546h && !currentThread.isInterrupted() && !this.f1543e) {
            try {
                this.f1579j.mo14258c((C1176e) null, this.f1577N, this.f1578i);
                this.f1544f = C1157b.NORMAL;
            } catch (Throwable th) {
                this.f1546h = false;
                if (this.f1544f != C1157b.INTERRUPT) {
                    this.f1544f = C1157b.EXCEPTION;
                    this.f1545g = (th.getMessage() == null || !th.getMessage().equals("read = -1, end of stream !")) ? th.toString() : "end of stream";
                }
            }
        }
        this.f1543e = true;
        C1179b.m1354a("GS-R|finish ~~~~~~");
    }

    /* renamed from: f */
    public void mo14233f() {
        super.mo14233f();
        C1179b.m1354a("GS-R|rt dispose");
        if (this.f1575L != null) {
            if (this.f1544f != C1157b.EXCEPTION) {
                this.f1575L.mo14224a(this);
            } else if (!TextUtils.isEmpty(this.f1545g)) {
                this.f1575L.mo14225a(new Exception(this.f1545g));
            }
        }
        if (this.f1576M != null) {
            this.f1576M = null;
        }
        this.f1575L = null;
    }

    /* renamed from: j */
    public void mo14250j() {
        this.f1546h = false;
        this.f1544f = C1157b.INTERRUPT;
    }
}
