package com.alibaba.sdk.android.ams.common.logger;

import android.util.Log;
import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;
import com.taobao.accs.utl.AccsLogger;
import java.util.ArrayList;
import java.util.List;

public class AmsLogger {
    public static final int DEBUG = 2;
    public static final int ERROR = 0;
    private static final String IMPORTANT_LOG_TAG = "[MPS]";
    public static final int INFO = 1;
    private static final AmsLogger importantLogger = getLogger(IMPORTANT_LOG_TAG);
    static List<LoggerListener> listener = new ArrayList();
    public static volatile int log_level = 3;
    String TAG;

    /* renamed from: com.alibaba.sdk.android.ams.common.logger.AmsLogger$2 */
    static /* synthetic */ class C06792 {

        /* renamed from: a */
        static final /* synthetic */ int[] f812a = new int[LogLevel.values().length];

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
                f812a = r0
                int[] r0 = f812a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.ERROR     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f812a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.WARN     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f812a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.INFO     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f812a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.DEBUG     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.ams.common.logger.AmsLogger.C06792.<clinit>():void");
        }
    }

    public static void addListener(final LoggerListener loggerListener) {
        listener.add(loggerListener);
        AccsLogger.addILogger(new ILogger() {
            public void print(LogLevel logLevel, String str, String str2) {
                int i = C06792.f812a[logLevel.ordinal()];
                if (i == 1) {
                    loggerListener.mo9553e(str, str2, (Throwable) null, 0);
                } else if (i == 2) {
                    loggerListener.mo9555w(str, str2, (Throwable) null, 0);
                } else if (i == 3) {
                    loggerListener.mo9554i(str, str2, (Throwable) null, 0);
                } else if (i == 4) {
                    loggerListener.mo9552d(str, str2, (Throwable) null, 0);
                }
            }
        });
    }

    public static void clearListeners() {
        listener.clear();
    }

    public static AmsLogger getImportantLogger() {
        return importantLogger;
    }

    public static AmsLogger getLogger(String str) {
        AmsLogger amsLogger = new AmsLogger();
        amsLogger.TAG = str;
        return amsLogger;
    }

    /* renamed from: d */
    public void mo9538d(String str) {
        mo9540d(str, (Throwable) null, 0);
    }

    /* renamed from: d */
    public void mo9539d(String str, Throwable th) {
        mo9540d(str, th, 0);
    }

    /* renamed from: d */
    public void mo9540d(String str, Throwable th, int i) {
        if (log_level >= 2) {
            if (th == null) {
                Log.d(this.TAG, str);
            } else {
                Log.d(this.TAG, str, th);
            }
        }
        for (LoggerListener d : listener) {
            d.mo9552d(this.TAG, str, th, i);
        }
    }

    /* renamed from: e */
    public void mo9541e(String str) {
        mo9543e(str, (Throwable) null, 0);
    }

    /* renamed from: e */
    public void mo9542e(String str, Throwable th) {
        mo9543e(str, th, 0);
    }

    /* renamed from: e */
    public void mo9543e(String str, Throwable th, int i) {
        if (log_level >= 0) {
            if (th == null) {
                Log.e(this.TAG, str);
            } else {
                Log.e(this.TAG, str, th);
            }
        }
        for (LoggerListener e : listener) {
            e.mo9553e(this.TAG, str, th, i);
        }
    }

    /* renamed from: i */
    public void mo9544i(String str) {
        mo9546i(str, (Throwable) null, 0);
    }

    /* renamed from: i */
    public void mo9545i(String str, Throwable th) {
        mo9546i(str, th, 0);
    }

    /* renamed from: i */
    public void mo9546i(String str, Throwable th, int i) {
        if (log_level >= 1) {
            if (th == null) {
                Log.i(this.TAG, str);
            } else {
                Log.i(this.TAG, str, th);
            }
        }
        for (LoggerListener i2 : listener) {
            i2.mo9554i(this.TAG, str, th, i);
        }
    }

    /* renamed from: w */
    public void mo9547w(String str) {
        mo9549w(str, (Throwable) null, 0);
    }

    /* renamed from: w */
    public void mo9548w(String str, Throwable th) {
        mo9549w(str, th, 0);
    }

    /* renamed from: w */
    public void mo9549w(String str, Throwable th, int i) {
        if (log_level >= 1) {
            if (th == null) {
                Log.w(this.TAG, str);
            } else if (str == null) {
                Log.w(this.TAG, th);
            } else {
                Log.w(this.TAG, str, th);
            }
        }
        for (LoggerListener w : listener) {
            w.mo9555w(this.TAG, str, th, i);
        }
    }

    /* renamed from: w */
    public void mo9550w(Throwable th) {
        mo9549w((String) null, th, 0);
    }
}
