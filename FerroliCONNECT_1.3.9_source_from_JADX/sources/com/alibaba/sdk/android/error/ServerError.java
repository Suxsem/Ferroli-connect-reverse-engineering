package com.alibaba.sdk.android.error;

@Deprecated
public class ServerError extends ErrorCode {
    public static final String TYPE_SERVER = "SERVER";

    public ServerError(String str, String str2, String str3, String[] strArr, boolean z) {
        super(str + "_" + "SERVER" + "_" + str2, str3, (String) null, strArr, z);
    }
}
