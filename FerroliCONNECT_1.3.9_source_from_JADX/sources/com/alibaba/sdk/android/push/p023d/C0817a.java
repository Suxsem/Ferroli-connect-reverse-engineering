package com.alibaba.sdk.android.push.p023d;

import android.app.Application;
import android.content.Context;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;

/* renamed from: com.alibaba.sdk.android.push.d.a */
public class C0817a {

    /* renamed from: a */
    private static AmsLogger f1162a = AmsLogger.getLogger("MPS:ReportManager");

    /* renamed from: b */
    private static C0817a f1163b = null;

    /* renamed from: c */
    private long f1164c = 0;

    /* renamed from: d */
    private Context f1165d;

    /* renamed from: e */
    private boolean f1166e = true;

    private C0817a(Context context) {
        if (context != null && (context.getApplicationContext() instanceof Application)) {
            if (this.f1164c == 0) {
                this.f1164c = System.currentTimeMillis();
            }
            this.f1165d = context;
        }
    }

    /* renamed from: a */
    public static C0817a m791a() {
        return f1163b;
    }

    /* renamed from: a */
    public static C0817a m792a(Context context) {
        if (f1163b == null) {
            synchronized (C0817a.class) {
                if (f1163b == null) {
                    f1163b = new C0817a(context);
                }
            }
        }
        return f1163b;
    }

    /* renamed from: a */
    public void mo9895a(String str) {
    }

    /* renamed from: a */
    public void mo9896a(String str, String str2, int i) {
        if (!this.f1166e) {
            f1162a.mo9541e("report switch turned off");
        }
    }

    /* renamed from: a */
    public void mo9897a(String str, String str2, long j) {
        if (!this.f1166e) {
            f1162a.mo9541e("report switch turned off");
        }
    }

    /* renamed from: a */
    public void mo9898a(String str, String str2, String str3) {
        if (!this.f1166e) {
            f1162a.mo9541e("report switch turned off");
        }
    }

    /* renamed from: a */
    public void mo9899a(String str, String str2, String str3, String str4) {
        if (!this.f1166e) {
            f1162a.mo9541e("report switch turned off");
        }
    }

    /* renamed from: a */
    public void mo9900a(boolean z) {
        synchronized (C0817a.class) {
            this.f1166e = z;
        }
    }
}
