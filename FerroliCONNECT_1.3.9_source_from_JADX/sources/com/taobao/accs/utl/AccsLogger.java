package com.taobao.accs.utl;

import com.alibaba.sdk.android.logger.BaseSdkLogApi;
import com.alibaba.sdk.android.logger.ILog;
import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;

/* compiled from: Taobao */
public class AccsLogger {

    /* renamed from: com.taobao.accs.utl.AccsLogger$a */
    /* compiled from: Taobao */
    private static class C2083a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final BaseSdkLogApi f3548a = new BaseSdkLogApi("EMASNAccs", false);

        private C2083a() {
        }
    }

    public static void enable(boolean z) {
        C2083a.f3548a.enable(z);
    }

    public static void setLevel(LogLevel logLevel) {
        C2083a.f3548a.setLevel(logLevel);
    }

    public static void setILogger(ILogger iLogger) {
        C2083a.f3548a.setILogger(iLogger);
    }

    public static void addILogger(ILogger iLogger) {
        C2083a.f3548a.addILogger(iLogger);
    }

    public static void removeILogger(ILogger iLogger) {
        C2083a.f3548a.removeILogger(iLogger);
    }

    public static ILog getLogger(Object obj) {
        return C2083a.f3548a.getLogger(obj);
    }
}
