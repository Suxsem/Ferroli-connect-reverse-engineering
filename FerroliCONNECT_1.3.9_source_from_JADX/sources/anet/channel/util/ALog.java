package anet.channel.util;

import android.text.TextUtils;
import android.util.Log;

/* compiled from: Taobao */
public class ALog {
    private static Object LOG_BREAK = "|";
    private static boolean canUseTlog = true;
    private static boolean isPrintLog = true;
    private static volatile ILog log = logcat;
    public static Logcat logcat = new Logcat();

    /* compiled from: Taobao */
    public interface ILog {
        /* renamed from: d */
        void mo8758d(String str, String str2);

        /* renamed from: e */
        void mo8759e(String str, String str2);

        /* renamed from: e */
        void mo8760e(String str, String str2, Throwable th);

        /* renamed from: i */
        void mo8761i(String str, String str2);

        boolean isPrintLog(int i);

        boolean isValid();

        void setLogLevel(int i);

        /* renamed from: w */
        void mo8765w(String str, String str2);

        /* renamed from: w */
        void mo8766w(String str, String str2, Throwable th);
    }

    /* compiled from: Taobao */
    public static class Level {

        /* renamed from: D */
        public static final int f569D = 1;

        /* renamed from: E */
        public static final int f570E = 4;

        /* renamed from: I */
        public static final int f571I = 2;

        /* renamed from: N */
        public static final int f572N = 5;

        /* renamed from: V */
        public static final int f573V = 0;

        /* renamed from: W */
        public static final int f574W = 3;
    }

    private static String buildLogTag(String str) {
        return str;
    }

    /* compiled from: Taobao */
    public static class Logcat implements ILog {
        int defaultLevel = 1;

        public boolean isValid() {
            return true;
        }

        /* renamed from: d */
        public void mo8758d(String str, String str2) {
            Log.d(str, str2);
        }

        /* renamed from: i */
        public void mo8761i(String str, String str2) {
            Log.i(str, str2);
        }

        /* renamed from: w */
        public void mo8765w(String str, String str2) {
            Log.w(str, str2);
        }

        /* renamed from: w */
        public void mo8766w(String str, String str2, Throwable th) {
            Log.w(str, str2, th);
        }

        /* renamed from: e */
        public void mo8759e(String str, String str2) {
            Log.e(str, str2);
        }

        /* renamed from: e */
        public void mo8760e(String str, String str2, Throwable th) {
            Log.e(str, str2, th);
        }

        public boolean isPrintLog(int i) {
            return i >= this.defaultLevel;
        }

        public void setLogLevel(int i) {
            if (i < 0 || i > 5) {
                this.defaultLevel = 5;
            } else {
                this.defaultLevel = i;
            }
        }
    }

    public static void setLog(ILog iLog) {
        if (iLog != null) {
            if ((canUseTlog || !iLog.getClass().getSimpleName().toLowerCase().contains("tlog")) && iLog.isValid()) {
                log = iLog;
            }
        }
    }

    public static ILog getLog() {
        return log;
    }

    public static void setPrintLog(boolean z) {
        isPrintLog = z;
    }

    public static void setLevel(int i) {
        if (log != null) {
            log.setLogLevel(i);
        }
    }

    public static boolean isPrintLog(int i) {
        if (isPrintLog && log != null) {
            return log.isPrintLog(i);
        }
        return false;
    }

    /* renamed from: d */
    public static void m325d(String str, String str2, String str3, Object... objArr) {
        if (isPrintLog(1) && log != null) {
            log.mo8758d(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        }
    }

    /* renamed from: i */
    public static void m328i(String str, String str2, String str3, Object... objArr) {
        if (isPrintLog(2) && log != null) {
            log.mo8761i(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        }
    }

    /* renamed from: w */
    public static void m330w(String str, String str2, String str3, Object... objArr) {
        if (isPrintLog(3) && log != null) {
            log.mo8765w(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        }
    }

    /* renamed from: w */
    public static void m329w(String str, String str2, String str3, Throwable th, Object... objArr) {
        if (isPrintLog(3) && log != null) {
            log.mo8766w(buildLogTag(str), buildLogMsg(str2, str3, objArr), th);
        }
    }

    /* renamed from: e */
    public static void m327e(String str, String str2, String str3, Object... objArr) {
        if (isPrintLog(4) && log != null) {
            log.mo8759e(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        }
    }

    /* renamed from: e */
    public static void m326e(String str, String str2, String str3, Throwable th, Object... objArr) {
        if (isPrintLog(4) && log != null) {
            log.mo8760e(buildLogTag(str), buildLogMsg(str2, str3, objArr), th);
        }
    }

    private static String buildLogMsg(String str, String str2, Object... objArr) {
        Object obj;
        Object obj2;
        if (str == null && str2 == null && objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(64);
        if (!TextUtils.isEmpty(str2)) {
            sb.append(LOG_BREAK);
            sb.append("[seq:");
            sb.append(str2);
            sb.append("]");
        }
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
                if (objArr[i] != null) {
                    obj = objArr[i];
                } else {
                    obj = "";
                }
                sb.append(obj);
                sb.append(":");
                if (objArr[i2] != null) {
                    obj2 = objArr[i2];
                } else {
                    obj2 = "";
                }
                sb.append(obj2);
                i += 2;
            }
            if (i < objArr.length) {
                sb.append(" ");
                sb.append(objArr[i]);
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static void setUseTlog(boolean z) {
        if (!z) {
            canUseTlog = false;
            log = logcat;
            return;
        }
        canUseTlog = true;
    }
}
