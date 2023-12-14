package com.alibaba.sdk.android.push.common.util.p021a;

import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.push.common.p020a.C0804d;

/* renamed from: com.alibaba.sdk.android.push.common.util.a.b */
public class C0810b {

    /* renamed from: a */
    public String f1119a;

    /* renamed from: b */
    public int f1120b;

    /* renamed from: c */
    public ErrorCode f1121c;

    /* renamed from: d */
    public C0812d f1122d;

    public C0810b() {
        this.f1121c = C0804d.f1091a;
        this.f1119a = "";
        this.f1120b = 0;
        this.f1122d = C0812d.UNKNOWN_TYPE;
    }

    public C0810b(int i) {
        this.f1121c = C0804d.f1091a;
        for (C0812d dVar : C0812d.values()) {
            if (dVar.mo9894a() == i) {
                this.f1122d = dVar;
            }
        }
        this.f1119a = "";
        this.f1120b = 0;
    }
}
