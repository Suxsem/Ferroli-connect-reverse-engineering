package com.alibaba.sdk.android.error;

public class IntCodeGenerator extends CodeGenerator {
    public Integer generateCodeInt(String str, String str2, String str3) {
        return Integer.valueOf(Integer.parseInt(str3));
    }

    public String generateCodeStr(String str, String str2, String str3) {
        return null;
    }
}
