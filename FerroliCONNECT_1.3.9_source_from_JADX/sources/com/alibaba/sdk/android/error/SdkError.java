package com.alibaba.sdk.android.error;

@Deprecated
public class SdkError extends ErrorCode {
    public static final String TYPE_SDK = "SDK";

    public SdkError(String str, String str2, String str3, String[] strArr, boolean z) {
        super(str + "_" + "SDK" + "_" + str2, str3, (String) null, strArr, z);
    }
}
