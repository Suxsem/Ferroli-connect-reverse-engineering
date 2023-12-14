package com.p092ta.p093a.p096c;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

/* renamed from: com.ta.a.c.f */
public class C1982f {

    /* renamed from: b */
    private static boolean f3168b = false;

    /* renamed from: c */
    private static boolean f3169c = false;

    /* renamed from: a */
    public static boolean m3369a() {
        return f3168b;
    }

    /* renamed from: e */
    public static void m3372e() {
        if (f3168b) {
            Log.d(m3373g(), m3366a((String) null, new Object[0]));
        }
    }

    /* renamed from: a */
    public static void m3368a(String str, Object... objArr) {
        if (f3168b) {
            Log.d(m3373g(), m3366a(str, objArr));
        }
    }

    /* renamed from: b */
    public static void m3371b(String str, Object... objArr) {
        if (f3169c) {
            Log.d(m3373g(), m3366a(str, objArr));
        }
    }

    /* renamed from: a */
    public static void m3367a(String str, Throwable th, Object... objArr) {
        if (f3168b) {
            Log.e(m3373g(), m3366a(str, objArr), th);
        }
    }

    /* renamed from: b */
    public static void m3370b(String str, Throwable th, Object... objArr) {
        if (f3169c) {
            Log.e(m3373g(), m3366a(str, objArr), th);
        }
    }

    /* renamed from: a */
    private static String m3365a(Object obj, Object obj2) {
        Object[] objArr = new Object[2];
        if (obj == null) {
            obj = "";
        }
        objArr[0] = obj;
        if (obj2 == null) {
            obj2 = "";
        }
        objArr[1] = obj2;
        return String.format("%s:%s", objArr);
    }

    /* renamed from: g */
    private static String m3373g() {
        String str;
        String str2;
        StackTraceElement a = m3364a();
        if (a != null) {
            String className = a.getClassName();
            if (!TextUtils.isEmpty(className)) {
                str = className.substring(className.lastIndexOf(46) + 1);
            } else {
                str = "";
            }
            str2 = a.getMethodName();
        } else {
            str2 = "";
            str = str2;
        }
        return "Utdid." + str + "." + str2 + "." + String.valueOf(Process.myPid()) + "." + (Thread.currentThread().getId() + "");
    }

    /* renamed from: a */
    private static String m3366a(String str, Object... objArr) {
        if (str == null && objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Object[] objArr2 = new Object[1];
        if (str == null) {
            str = "-";
        }
        int i = 0;
        objArr2[0] = str;
        sb.append(String.format("[%s] ", objArr2));
        if (objArr != null) {
            int length = objArr.length;
            while (true) {
                int i2 = i + 1;
                if (i2 >= objArr.length) {
                    break;
                }
                sb.append(m3365a(objArr[i], objArr[i2]));
                if (i2 < length - 1) {
                    sb.append(",");
                }
                i = i2 + 1;
            }
            if (i == objArr.length - 1) {
                sb.append(objArr[i]);
            }
        }
        return sb.toString();
    }

    /* renamed from: a */
    private static StackTraceElement m3364a() {
        try {
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (!stackTraceElement.isNativeMethod()) {
                    if (!stackTraceElement.getClassName().equals(Thread.class.getName())) {
                        if (!stackTraceElement.getClassName().equals(C1982f.class.getName())) {
                            return stackTraceElement;
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return null;
    }
}
