package com.alibaba.sdk.android.sender;

import com.alibaba.sdk.android.logger.BaseSdkLogApi;
import com.alibaba.sdk.android.logger.ILog;
import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;

public class SenderLog {

    /* renamed from: com.alibaba.sdk.android.sender.SenderLog$a */
    private static class C0877a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final BaseSdkLogApi f1327a = new BaseSdkLogApi("Sender", false);
    }

    private SenderLog() {
    }

    public static void addILogger(ILogger iLogger) {
        C0877a.f1327a.addILogger(iLogger);
    }

    public static void enable(boolean z) {
        C0877a.f1327a.enable(z);
    }

    public static ILog getLogger(Object obj) {
        return C0877a.f1327a.getLogger(obj);
    }

    public static void removeILogger(ILogger iLogger) {
        C0877a.f1327a.removeILogger(iLogger);
    }

    public static void setILogger(ILogger iLogger) {
        C0877a.f1327a.setILogger(iLogger);
    }

    public static void setLevel(LogLevel logLevel) {
        C0877a.f1327a.setLevel(logLevel);
    }
}
