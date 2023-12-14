package com.alibaba.sdk.android.logger.p016b;

import com.alibaba.sdk.android.logger.LogLevel;
import com.alibaba.sdk.android.logger.interceptor.ILogInterceptor;
import com.alibaba.sdk.android.logger.interceptor.InterceptorManager;

/* renamed from: com.alibaba.sdk.android.logger.b.c */
public class C0732c implements ILogInterceptor {

    /* renamed from: a */
    private C0735e f937a;

    public C0732c(C0735e eVar) {
        this.f937a = eVar;
    }

    public void handle(InterceptorManager interceptorManager, int i, LogLevel logLevel, String str, Object[] objArr) {
        if (this.f937a.mo9730a(logLevel)) {
            interceptorManager.toNextLogInterceptor(i, logLevel, str, objArr);
        }
    }
}
