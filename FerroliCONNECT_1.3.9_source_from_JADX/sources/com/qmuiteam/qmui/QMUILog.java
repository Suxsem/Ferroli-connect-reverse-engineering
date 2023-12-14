package com.qmuiteam.qmui;

public class QMUILog {
    private static QMUILogDelegate sDelegete;

    public interface QMUILogDelegate {
        /* renamed from: d */
        void mo15395d(String str, String str2, Object... objArr);

        /* renamed from: e */
        void mo15396e(String str, String str2, Object... objArr);

        /* renamed from: i */
        void mo15397i(String str, String str2, Object... objArr);

        void printErrStackTrace(String str, Throwable th, String str2, Object... objArr);

        /* renamed from: w */
        void mo15399w(String str, String str2, Object... objArr);
    }

    public static void setDelegete(QMUILogDelegate qMUILogDelegate) {
        sDelegete = qMUILogDelegate;
    }

    /* renamed from: e */
    public static void m3307e(String str, String str2, Object... objArr) {
        QMUILogDelegate qMUILogDelegate = sDelegete;
        if (qMUILogDelegate != null) {
            qMUILogDelegate.mo15396e(str, str2, objArr);
        }
    }

    /* renamed from: w */
    public static void m3309w(String str, String str2, Object... objArr) {
        QMUILogDelegate qMUILogDelegate = sDelegete;
        if (qMUILogDelegate != null) {
            qMUILogDelegate.mo15399w(str, str2, objArr);
        }
    }

    /* renamed from: i */
    public static void m3308i(String str, String str2, Object... objArr) {
        QMUILogDelegate qMUILogDelegate = sDelegete;
        if (qMUILogDelegate != null) {
            qMUILogDelegate.mo15397i(str, str2, objArr);
        }
    }

    /* renamed from: d */
    public static void m3306d(String str, String str2, Object... objArr) {
        QMUILogDelegate qMUILogDelegate = sDelegete;
        if (qMUILogDelegate != null) {
            qMUILogDelegate.mo15395d(str, str2, objArr);
        }
    }

    public static void printErrStackTrace(String str, Throwable th, String str2, Object... objArr) {
        QMUILogDelegate qMUILogDelegate = sDelegete;
        if (qMUILogDelegate != null) {
            qMUILogDelegate.printErrStackTrace(str, th, str2, objArr);
        }
    }
}
