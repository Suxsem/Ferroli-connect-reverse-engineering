package com.taobao.accs.utl;

import com.alibaba.sdk.android.logger.LogLevel;
import com.taobao.accs.utl.ALog;

/* renamed from: com.taobao.accs.utl.b */
/* compiled from: Taobao */
/* synthetic */ class C2085b {

    /* renamed from: a */
    static final /* synthetic */ int[] f3551a = new int[LogLevel.values().length];

    /* renamed from: b */
    static final /* synthetic */ int[] f3552b = new int[ALog.Level.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(22:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|(2:17|18)|19|21|22|23|24|25|26|27|28|30) */
    /* JADX WARNING: Can't wrap try/catch for region: R(23:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|(2:17|18)|19|21|22|23|24|25|26|27|28|30) */
    /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|16|(2:17|18)|19|21|22|23|24|25|26|27|28|30) */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0035 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0040 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0068 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0072 */
    static {
        /*
            com.taobao.accs.utl.ALog$Level[] r0 = com.taobao.accs.utl.ALog.Level.values()
            int r0 = r0.length
            int[] r0 = new int[r0]
            f3552b = r0
            r0 = 1
            int[] r1 = f3552b     // Catch:{ NoSuchFieldError -> 0x0014 }
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ NoSuchFieldError -> 0x0014 }
            int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2
            int[] r2 = f3552b     // Catch:{ NoSuchFieldError -> 0x001f }
            com.taobao.accs.utl.ALog$Level r3 = com.taobao.accs.utl.ALog.Level.V     // Catch:{ NoSuchFieldError -> 0x001f }
            int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = 3
            int[] r3 = f3552b     // Catch:{ NoSuchFieldError -> 0x002a }
            com.taobao.accs.utl.ALog$Level r4 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ NoSuchFieldError -> 0x002a }
            int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
            r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r3 = 4
            int[] r4 = f3552b     // Catch:{ NoSuchFieldError -> 0x0035 }
            com.taobao.accs.utl.ALog$Level r5 = com.taobao.accs.utl.ALog.Level.W     // Catch:{ NoSuchFieldError -> 0x0035 }
            int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
            r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            int[] r4 = f3552b     // Catch:{ NoSuchFieldError -> 0x0040 }
            com.taobao.accs.utl.ALog$Level r5 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ NoSuchFieldError -> 0x0040 }
            int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
            r6 = 5
            r4[r5] = r6     // Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            int[] r4 = f3552b     // Catch:{ NoSuchFieldError -> 0x004b }
            com.taobao.accs.utl.ALog$Level r5 = com.taobao.accs.utl.ALog.Level.L     // Catch:{ NoSuchFieldError -> 0x004b }
            int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
            r6 = 6
            r4[r5] = r6     // Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            com.alibaba.sdk.android.logger.LogLevel[] r4 = com.alibaba.sdk.android.logger.LogLevel.values()
            int r4 = r4.length
            int[] r4 = new int[r4]
            f3551a = r4
            int[] r4 = f3551a     // Catch:{ NoSuchFieldError -> 0x005e }
            com.alibaba.sdk.android.logger.LogLevel r5 = com.alibaba.sdk.android.logger.LogLevel.DEBUG     // Catch:{ NoSuchFieldError -> 0x005e }
            int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x005e }
            r4[r5] = r0     // Catch:{ NoSuchFieldError -> 0x005e }
        L_0x005e:
            int[] r0 = f3551a     // Catch:{ NoSuchFieldError -> 0x0068 }
            com.alibaba.sdk.android.logger.LogLevel r4 = com.alibaba.sdk.android.logger.LogLevel.INFO     // Catch:{ NoSuchFieldError -> 0x0068 }
            int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0068 }
            r0[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0068 }
        L_0x0068:
            int[] r0 = f3551a     // Catch:{ NoSuchFieldError -> 0x0072 }
            com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.WARN     // Catch:{ NoSuchFieldError -> 0x0072 }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0072 }
            r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0072 }
        L_0x0072:
            int[] r0 = f3551a     // Catch:{ NoSuchFieldError -> 0x007c }
            com.alibaba.sdk.android.logger.LogLevel r1 = com.alibaba.sdk.android.logger.LogLevel.ERROR     // Catch:{ NoSuchFieldError -> 0x007c }
            int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007c }
            r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x007c }
        L_0x007c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.C2085b.<clinit>():void");
    }
}
