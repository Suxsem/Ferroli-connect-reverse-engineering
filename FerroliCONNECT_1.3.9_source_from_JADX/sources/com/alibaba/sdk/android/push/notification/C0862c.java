package com.alibaba.sdk.android.push.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Build;

/* renamed from: com.alibaba.sdk.android.push.notification.c */
public abstract class C0862c {

    /* renamed from: a */
    protected String f1293a;

    /* renamed from: b */
    protected String f1294b;

    /* renamed from: c */
    protected int f1295c = 0;

    /* renamed from: d */
    protected String f1296d;

    /* renamed from: e */
    protected String f1297e;

    /* renamed from: f */
    protected String f1298f;

    /* renamed from: g */
    protected int f1299g = 0;

    /* renamed from: h */
    protected String f1300h;

    /* renamed from: i */
    protected String f1301i;

    /* renamed from: j */
    protected String f1302j;

    /* renamed from: k */
    protected String f1303k;

    public C0862c() {
        int i = Build.VERSION.SDK_INT;
    }

    /* renamed from: a */
    public abstract Notification mo10025a(Context context, PushData pushData, NotificationConfigure notificationConfigure);

    /* renamed from: a */
    public String mo10073a() {
        return this.f1293a;
    }

    /* renamed from: a */
    public void mo10074a(int i) {
        this.f1295c = i;
    }

    /* renamed from: a */
    public void mo10075a(String str) {
        this.f1293a = str;
    }

    /* renamed from: b */
    public String mo10076b() {
        return this.f1294b;
    }

    /* renamed from: b */
    public void mo10077b(int i) {
        this.f1299g = i;
    }

    /* renamed from: b */
    public void mo10078b(String str) {
        this.f1294b = str;
    }

    /* renamed from: c */
    public int mo10079c() {
        return this.f1295c;
    }

    /* renamed from: c */
    public void mo10080c(String str) {
        this.f1296d = str;
    }

    /* renamed from: d */
    public String mo10081d() {
        return this.f1296d;
    }

    /* renamed from: d */
    public void mo10082d(String str) {
        this.f1298f = str;
    }

    /* renamed from: e */
    public void mo10083e(String str) {
        this.f1300h = str;
    }

    /* renamed from: f */
    public void mo10084f(String str) {
        this.f1301i = str;
    }

    /* renamed from: g */
    public void mo10085g(String str) {
        this.f1302j = str;
    }

    /* renamed from: h */
    public void mo10086h(String str) {
        this.f1303k = str;
    }

    /* renamed from: i */
    public void mo10087i(String str) {
        this.f1297e = str;
    }
}
