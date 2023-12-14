package com.alibaba.sdk.android.error;

@Deprecated
public class SystemError extends ErrorCode {
    public static final String TYPE_SYSTEM = "ANDROID";

    public SystemError(String str, String str2, String str3, String[] strArr, boolean z) {
        super(str + "_" + "ANDROID" + "_" + str2, str3, (String) null, strArr, z);
    }
}
