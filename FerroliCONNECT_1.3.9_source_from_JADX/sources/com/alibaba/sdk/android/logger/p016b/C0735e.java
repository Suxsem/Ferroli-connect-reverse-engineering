package com.alibaba.sdk.android.logger.p016b;

import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.ILoggerWithControl;
import com.alibaba.sdk.android.logger.LogLevel;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.alibaba.sdk.android.logger.b.e */
public class C0735e implements ILogger {

    /* renamed from: a */
    private static final ILogger f939a = new C0733d();

    /* renamed from: b */
    private ILogger f940b = f939a;

    /* renamed from: c */
    private ArrayList<ILogger> f941c = new ArrayList<>();

    /* renamed from: d */
    private C0731b f942d;

    public C0735e(C0731b bVar) {
        this.f942d = bVar;
    }

    /* renamed from: a */
    private boolean m680a(ILogger iLogger, LogLevel logLevel) {
        if (iLogger == null || !(iLogger instanceof ILoggerWithControl)) {
            return this.f942d.mo9727b(logLevel);
        }
        try {
            return ((ILoggerWithControl) iLogger).isEnabled() && ((ILoggerWithControl) iLogger).isPrint(logLevel);
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: a */
    public void mo9729a(ILogger iLogger) {
        if (iLogger == null) {
            iLogger = f939a;
        }
        this.f940b = iLogger;
    }

    /* renamed from: a */
    public boolean mo9730a(LogLevel logLevel) {
        if (m680a(this.f940b, logLevel)) {
            return true;
        }
        try {
            Iterator<ILogger> it = this.f941c.iterator();
            while (it.hasNext()) {
                if (m680a(it.next(), logLevel)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: b */
    public void mo9731b(ILogger iLogger) {
        if (iLogger != null) {
            this.f941c.add(iLogger);
        }
    }

    /* renamed from: c */
    public void mo9732c(ILogger iLogger) {
        if (iLogger != null) {
            this.f941c.remove(iLogger);
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x000d */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0019 A[Catch:{ Throwable -> 0x0029 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void print(com.alibaba.sdk.android.logger.LogLevel r4, java.lang.String r5, java.lang.String r6) {
        /*
            r3 = this;
            com.alibaba.sdk.android.logger.ILogger r0 = r3.f940b
            boolean r0 = r3.m680a(r0, r4)
            if (r0 == 0) goto L_0x000d
            com.alibaba.sdk.android.logger.ILogger r0 = r3.f940b     // Catch:{ Throwable -> 0x000d }
            r0.print(r4, r5, r6)     // Catch:{ Throwable -> 0x000d }
        L_0x000d:
            java.util.ArrayList<com.alibaba.sdk.android.logger.ILogger> r0 = r3.f941c     // Catch:{ Throwable -> 0x0029 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Throwable -> 0x0029 }
        L_0x0013:
            boolean r1 = r0.hasNext()     // Catch:{ Throwable -> 0x0029 }
            if (r1 == 0) goto L_0x0029
            java.lang.Object r1 = r0.next()     // Catch:{ Throwable -> 0x0029 }
            com.alibaba.sdk.android.logger.ILogger r1 = (com.alibaba.sdk.android.logger.ILogger) r1     // Catch:{ Throwable -> 0x0029 }
            boolean r2 = r3.m680a(r1, r4)     // Catch:{ Throwable -> 0x0029 }
            if (r2 == 0) goto L_0x0013
            r1.print(r4, r5, r6)     // Catch:{ Throwable -> 0x0013 }
            goto L_0x0013
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.logger.p016b.C0735e.print(com.alibaba.sdk.android.logger.LogLevel, java.lang.String, java.lang.String):void");
    }
}
