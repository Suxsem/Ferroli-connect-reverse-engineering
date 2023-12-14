package com.igexin.push.core.p052e;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;

/* renamed from: com.igexin.push.core.e.a */
public abstract class C1341a {

    /* renamed from: a */
    protected Long f2104a = Long.valueOf(System.currentTimeMillis());

    /* renamed from: b */
    protected Activity f2105b;

    /* renamed from: c */
    protected String f2106c;

    /* renamed from: a */
    public Long mo14714a() {
        return this.f2104a;
    }

    /* renamed from: a */
    public void mo14715a(Activity activity) {
        this.f2105b = activity;
    }

    /* renamed from: a */
    public abstract void mo14716a(Intent intent);

    /* renamed from: a */
    public abstract void mo14717a(Configuration configuration);

    /* renamed from: a */
    public void mo14718a(Long l) {
        this.f2104a = l;
    }

    /* renamed from: a */
    public void mo14719a(String str) {
        this.f2106c = str;
    }

    /* renamed from: a */
    public abstract boolean mo14720a(int i, KeyEvent keyEvent);

    /* renamed from: a */
    public abstract boolean mo14721a(Menu menu);

    /* renamed from: b */
    public String mo14722b() {
        return this.f2106c;
    }

    /* renamed from: c */
    public abstract void mo14723c();

    /* renamed from: d */
    public abstract void mo14724d();

    /* renamed from: e */
    public abstract void mo14725e();

    /* renamed from: f */
    public abstract void mo14726f();

    /* renamed from: g */
    public abstract void mo14727g();

    /* renamed from: h */
    public abstract void mo14728h();

    /* renamed from: i */
    public abstract void mo14729i();
}
