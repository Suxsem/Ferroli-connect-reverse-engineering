package com.alibaba.sdk.android.logger.p016b;

import com.alibaba.sdk.android.logger.LogLevel;
import com.alibaba.sdk.android.logger.interceptor.C0742c;
import com.alibaba.sdk.android.logger.interceptor.InterceptorManager;

/* renamed from: com.alibaba.sdk.android.logger.b.a */
public class C0730a implements C0742c {
    /* renamed from: a */
    private String m675a() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int i = 1;
        while (i < stackTrace.length) {
            if (stackTrace[i].getClassName().startsWith("com.alibaba.sdk.android.logger")) {
                i++;
            } else {
                return "(" + stackTrace[i].getFileName() + ":" + stackTrace[i].getLineNumber() + ")";
            }
        }
        return "";
    }

    /* renamed from: a */
    public void mo9724a(InterceptorManager interceptorManager, int i, LogLevel logLevel, String str, String str2) {
        interceptorManager.toNextLoggerInterceptor(i, logLevel, str, str2 + m675a());
    }
}
