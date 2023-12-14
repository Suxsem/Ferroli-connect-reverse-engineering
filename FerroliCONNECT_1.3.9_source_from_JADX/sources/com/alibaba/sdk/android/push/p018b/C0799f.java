package com.alibaba.sdk.android.push.p018b;

import com.alibaba.sdk.android.error.ErrorCode;

/* renamed from: com.alibaba.sdk.android.push.b.f */
public class C0799f extends Exception {

    /* renamed from: a */
    private ErrorCode f1063a;

    public C0799f(ErrorCode errorCode) {
        super(errorCode.toShortString());
        this.f1063a = errorCode;
    }

    /* renamed from: a */
    public ErrorCode mo9878a() {
        return this.f1063a;
    }
}
