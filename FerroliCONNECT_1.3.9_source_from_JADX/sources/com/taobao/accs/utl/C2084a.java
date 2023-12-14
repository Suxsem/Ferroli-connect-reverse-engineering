package com.taobao.accs.utl;

import anet.channel.util.ALog;
import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;

/* renamed from: com.taobao.accs.utl.a */
/* compiled from: Taobao */
final class C2084a implements ILogger {

    /* renamed from: a */
    final /* synthetic */ ALog.ILog f3550a;

    C2084a(ALog.ILog iLog) {
        this.f3550a = iLog;
    }

    public void print(LogLevel logLevel, String str, String str2) {
        try {
            int i = C2085b.f3551a[logLevel.ordinal()];
            if (i == 1) {
                this.f3550a.mo8758d(str, str2);
            } else if (i == 2) {
                this.f3550a.mo8761i(str, str2);
            } else if (i == 3) {
                this.f3550a.mo8765w(str, str2);
            } else if (i == 4) {
                this.f3550a.mo8759e(str, str2);
            }
        } catch (Throwable unused) {
        }
    }
}
