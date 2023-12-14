package com.alibaba.sdk.android.tbrest.request;

public class BizResponse {
    static final int DENY_SERVICE = 107;
    static final int NGX_ADASH_DENY_SERVICE = 116;
    static final int NGX_ADASH_DISABLE_RTLOG = 115;
    static final int NGX_ADASH_DOWNGRADE_V1 = 109;
    static final int NO_ERROR = 0;
    static final int UNKNOWN_ERROR = -1;
    public String data;
    int errCode = -1;

    /* renamed from: rt */
    long f1336rt = 0;

    public boolean isSuccess() {
        return this.errCode == 0;
    }
}
