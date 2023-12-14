package com.alibaba.sdk.android.logger.p016b;

import com.alibaba.sdk.android.logger.ILog;
import com.alibaba.sdk.android.logger.LogLevel;
import com.alibaba.sdk.android.logger.interceptor.InterceptorManager;

/* renamed from: com.alibaba.sdk.android.logger.b.f */
public class C0736f implements ILog {

    /* renamed from: a */
    private String f943a;

    /* renamed from: b */
    private InterceptorManager f944b;

    public C0736f(String str, InterceptorManager interceptorManager) {
        this.f943a = str;
        this.f944b = interceptorManager;
    }

    /* renamed from: d */
    public void mo9706d(String str) {
        mo9707d(str);
    }

    /* renamed from: d */
    public void mo9707d(Object... objArr) {
        this.f944b.mo9736a(LogLevel.DEBUG, this.f943a, objArr);
    }

    /* renamed from: e */
    public void mo9708e(String str) {
        mo9710e(str);
    }

    /* renamed from: e */
    public void mo9709e(String str, Throwable th) {
        mo9710e(str, th);
    }

    /* renamed from: e */
    public void mo9710e(Object... objArr) {
        this.f944b.mo9736a(LogLevel.ERROR, this.f943a, objArr);
    }

    /* renamed from: i */
    public void mo9711i(String str) {
        mo9712i(str);
    }

    /* renamed from: i */
    public void mo9712i(Object... objArr) {
        this.f944b.mo9736a(LogLevel.INFO, this.f943a, objArr);
    }

    /* renamed from: w */
    public void mo9713w(String str) {
        mo9715w(str);
    }

    /* renamed from: w */
    public void mo9714w(String str, Throwable th) {
        mo9715w(str, th);
    }

    /* renamed from: w */
    public void mo9715w(Object... objArr) {
        this.f944b.mo9736a(LogLevel.WARN, this.f943a, objArr);
    }
}
