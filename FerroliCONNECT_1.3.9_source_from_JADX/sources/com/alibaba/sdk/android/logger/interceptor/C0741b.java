package com.alibaba.sdk.android.logger.interceptor;

import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;

/* renamed from: com.alibaba.sdk.android.logger.interceptor.b */
public class C0741b implements C0742c {

    /* renamed from: a */
    private ILogger f956a;

    public C0741b(ILogger iLogger) {
        this.f956a = iLogger;
    }

    /* renamed from: a */
    public void mo9724a(InterceptorManager interceptorManager, int i, LogLevel logLevel, String str, String str2) {
        this.f956a.print(logLevel, str, str2);
    }
}
