package com.alibaba.sdk.android.push.common.util.p021a;

import com.alibaba.sdk.android.error.ErrorCode;

/* renamed from: com.alibaba.sdk.android.push.common.util.a.a */
public class C0809a extends Exception {

    /* renamed from: a */
    private ErrorCode f1118a;

    public C0809a(ErrorCode errorCode) {
        super(errorCode.toShortString());
        this.f1118a = errorCode;
    }

    /* renamed from: a */
    public ErrorCode mo9886a() {
        return this.f1118a;
    }
}
