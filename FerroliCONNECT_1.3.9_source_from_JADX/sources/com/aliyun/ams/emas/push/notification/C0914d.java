package com.aliyun.ams.emas.push.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Build;

/* renamed from: com.aliyun.ams.emas.push.notification.d */
/* compiled from: Taobao */
public abstract class C0914d {

    /* renamed from: a */
    protected String f1457a;

    /* renamed from: b */
    protected String f1458b;

    /* renamed from: c */
    protected int f1459c = 0;

    /* renamed from: d */
    protected String f1460d;

    /* renamed from: a */
    public abstract Notification mo10193a(Context context);

    public C0914d() {
        int i = Build.VERSION.SDK_INT;
    }

    /* renamed from: a */
    public String mo10226a() {
        return this.f1457a;
    }

    /* renamed from: a */
    public void mo10228a(String str) {
        this.f1457a = str;
    }

    /* renamed from: b */
    public String mo10229b() {
        return this.f1458b;
    }

    /* renamed from: b */
    public void mo10230b(String str) {
        this.f1458b = str;
    }

    /* renamed from: a */
    public void mo10227a(int i) {
        this.f1459c = i;
    }

    /* renamed from: c */
    public int mo10231c() {
        return this.f1459c;
    }

    /* renamed from: c */
    public void mo10232c(String str) {
        this.f1460d = str;
    }

    /* renamed from: d */
    public String mo10233d() {
        return this.f1460d;
    }
}
