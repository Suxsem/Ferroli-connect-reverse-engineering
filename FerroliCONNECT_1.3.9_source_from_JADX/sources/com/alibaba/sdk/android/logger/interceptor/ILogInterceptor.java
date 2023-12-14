package com.alibaba.sdk.android.logger.interceptor;

import com.alibaba.sdk.android.logger.LogLevel;

public interface ILogInterceptor {
    void handle(InterceptorManager interceptorManager, int i, LogLevel logLevel, String str, Object[] objArr);
}
