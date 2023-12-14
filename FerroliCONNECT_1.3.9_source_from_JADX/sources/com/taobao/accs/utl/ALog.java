package com.taobao.accs.utl;

import anet.channel.util.ALog;
import com.alibaba.sdk.android.logger.ILog;
import com.alibaba.sdk.android.logger.LogLevel;

@Deprecated
/* compiled from: Taobao */
public class ALog {
    private static ILog log = AccsLogger.getLogger("DefaultLog");

    /* compiled from: Taobao */
    public enum Level {
        V,
        D,
        I,
        W,
        E,
        L
    }

    @Deprecated
    public static void initALog(String str, int i) {
    }

    @Deprecated
    public static boolean isPrintLog() {
        return true;
    }

    public static boolean isPrintLog(Level level) {
        return true;
    }

    @Deprecated
    private static boolean isTLogExist() {
        return false;
    }

    @Deprecated
    public static void setEnableTLog(boolean z) {
    }

    @Deprecated
    public static void setUseTlog(boolean z) {
    }

    @Deprecated
    public static void setLogger(ALog.ILog iLog) {
        if (iLog != null) {
            AccsLogger.addILogger(new C2084a(iLog));
        }
    }

    public static void setPrintLog(boolean z) {
        AccsLogger.enable(z);
    }

    @Deprecated
    public static void setLogLevel(Level level) {
        LogLevel logLevel = LogLevel.ERROR;
        switch (C2085b.f3552b[level.ordinal()]) {
            case 1:
            case 2:
                logLevel = LogLevel.DEBUG;
                break;
            case 3:
                logLevel = LogLevel.INFO;
                break;
            case 4:
                logLevel = LogLevel.WARN;
                break;
            case 5:
            case 6:
                logLevel = LogLevel.ERROR;
                break;
        }
        AccsLogger.setLevel(logLevel);
    }

    /* renamed from: v */
    public static void m3729v(String str, String str2, Object... objArr) {
        ILog iLog = log;
        iLog.mo9706d("[" + str + "]" + buildLogMsg(str2, objArr));
    }

    /* renamed from: d */
    public static void m3725d(String str, String str2, Object... objArr) {
        ILog iLog = log;
        iLog.mo9706d("[" + str + "]" + buildLogMsg(str2, objArr));
    }

    /* renamed from: i */
    public static void m3728i(String str, String str2, Object... objArr) {
        ILog iLog = log;
        iLog.mo9711i("[" + str + "]" + buildLogMsg(str2, objArr));
    }

    /* renamed from: w */
    public static void m3731w(String str, String str2, Object... objArr) {
        ILog iLog = log;
        iLog.mo9713w("[" + str + "]" + buildLogMsg(str2, objArr));
    }

    /* renamed from: w */
    public static void m3730w(String str, String str2, Throwable th, Object... objArr) {
        ILog iLog = log;
        iLog.mo9714w("[" + str + "]" + buildLogMsg(str2, objArr), th);
    }

    /* renamed from: e */
    public static void m3727e(String str, String str2, Object... objArr) {
        ILog iLog = log;
        iLog.mo9708e("[" + str + "]" + buildLogMsg(str2, objArr));
    }

    /* renamed from: e */
    public static void m3726e(String str, String str2, Throwable th, Object... objArr) {
        ILog iLog = log;
        iLog.mo9709e("[" + str + "]" + buildLogMsg(str2, objArr), th);
    }

    private static String formatKv(Object obj, Object obj2) {
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            obj = "";
        }
        sb.append(obj);
        sb.append(":");
        if (obj2 == null) {
            obj2 = "";
        }
        sb.append(obj2);
        return sb.toString();
    }

    private static String buildLogMsg(String str, Object... objArr) {
        if (str == null && objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(" ");
            sb.append(str);
        }
        if (objArr != null) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                if (i2 >= objArr.length) {
                    break;
                }
                sb.append(" ");
                sb.append(formatKv(objArr[i], objArr[i2]));
                i = i2 + 1;
            }
            if (i == objArr.length - 1) {
                sb.append(" ");
                sb.append(objArr[i]);
            }
        }
        return sb.toString();
    }
}
