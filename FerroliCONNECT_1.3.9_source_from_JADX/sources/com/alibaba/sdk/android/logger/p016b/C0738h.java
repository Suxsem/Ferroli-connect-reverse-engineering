package com.alibaba.sdk.android.logger.p016b;

import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;
import com.alibaba.sdk.android.logger.interceptor.C0743d;
import com.alibaba.sdk.android.logger.p015a.C0729a;
import java.util.ArrayList;

/* renamed from: com.alibaba.sdk.android.logger.b.h */
public class C0738h implements C0743d {

    /* renamed from: a */
    private static final String[] f946a = {"null"};

    /* renamed from: b */
    private static final String[] f947b = {"[]"};

    /* renamed from: c */
    private C0729a f948c;

    public C0738h(C0729a aVar) {
        this.f948c = aVar;
    }

    /* renamed from: a */
    private String[] m696a(Object[] objArr) {
        if (objArr == null) {
            return f946a;
        }
        if (objArr.length == 0) {
            return f947b;
        }
        String[] strArr = new String[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            strArr[i] = this.f948c.mo9722a(objArr[i]);
        }
        return strArr;
    }

    /* renamed from: a */
    private String[] m697a(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str : strArr) {
            if (str == null) {
                str = "";
            }
            if (!z) {
                sb.append(" ");
            } else {
                z = false;
            }
            if (str.contains("\n")) {
                String[] split = str.split("\n");
                boolean z2 = z;
                StringBuilder sb2 = sb;
                for (int i = 0; i < split.length; i++) {
                    sb2.append(split[i]);
                    if (i < split.length - 1) {
                        arrayList.add(sb2.toString());
                        sb2 = new StringBuilder();
                    } else if (i == split.length - 1 && str.endsWith("\n")) {
                        arrayList.add(sb2.toString());
                        sb2 = new StringBuilder();
                        z2 = true;
                    }
                }
                sb = sb2;
                z = z2;
            } else {
                sb.append(str);
            }
        }
        arrayList.add(sb.toString());
        return (String[]) arrayList.toArray(new String[0]);
    }

    /* renamed from: a */
    public void mo9734a(LogLevel logLevel, String str, Object[] objArr, ILogger iLogger) {
        String[] a = m697a(m696a(objArr));
        for (String print : a) {
            iLogger.print(logLevel, str, print);
        }
    }
}
