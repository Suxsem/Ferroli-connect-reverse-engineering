package com.alibaba.sdk.android.logger.p016b;

import android.util.Log;
import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;

/* renamed from: com.alibaba.sdk.android.logger.b.d */
public class C0733d implements ILogger {

    /* renamed from: com.alibaba.sdk.android.logger.b.d$1 */
    static /* synthetic */ class C07341 {

        /* renamed from: a */
        static final /* synthetic */ int[] f938a = new int[LogLevel.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.alibaba.sdk.android.logger.LogLevel[] r0 = com.alibaba.sdk.android.logger.LogLevel.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f938a = r0
                int[] r0 = f938a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.DEBUG     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f938a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.INFO     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f938a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.WARN     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f938a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.ERROR     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.logger.p016b.C0733d.C07341.<clinit>():void");
        }
    }

    public void print(LogLevel logLevel, String str, String str2) {
        String str3;
        if (str2.length() > 4000) {
            str3 = str2.substring(4000);
            str2 = str2.substring(0, 4000);
        } else {
            str3 = null;
        }
        int i = C07341.f938a[logLevel.ordinal()];
        if (i == 1) {
            Log.d(str, str2);
        } else if (i == 2) {
            Log.i(str, str2);
        } else if (i == 3) {
            Log.w(str, str2);
        } else if (i == 4) {
            Log.e(str, str2);
        }
        if (str3 != null) {
            print(logLevel, str, str3);
        }
    }
}
