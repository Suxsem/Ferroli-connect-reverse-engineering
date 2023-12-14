package com.alibaba.sdk.android.logger.interceptor;

import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;

/* renamed from: com.alibaba.sdk.android.logger.interceptor.a */
public class C0739a implements ILogInterceptor {

    /* renamed from: a */
    private C0743d f953a;

    public C0739a(C0743d dVar) {
        this.f953a = dVar;
    }

    public void handle(final InterceptorManager interceptorManager, int i, LogLevel logLevel, String str, Object[] objArr) {
        this.f953a.mo9734a(logLevel, str, objArr, new ILogger() {
            public void print(LogLevel logLevel, String str, String str2) {
                interceptorManager.toNextLoggerInterceptor(-1, logLevel, str, str2);
            }
        });
    }
}
