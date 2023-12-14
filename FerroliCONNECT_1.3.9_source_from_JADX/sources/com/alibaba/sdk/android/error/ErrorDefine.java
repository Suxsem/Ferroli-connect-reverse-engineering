package com.alibaba.sdk.android.error;

public class ErrorDefine {
    public static final String TYPE_ANDROID = "ANDROID";
    public static final String TYPE_SDK = "SDK";
    public static final String TYPE_SERVER = "SERVER";
    private CodeGenerator codeGenerator;
    private String label;

    public ErrorDefine(String str) {
        this.label = str;
        this.codeGenerator = new CodeGenerator();
    }

    public ErrorDefine(String str, CodeGenerator codeGenerator2) {
        this.label = str;
        this.codeGenerator = codeGenerator2;
    }

    private static Code formCode(CodeGenerator codeGenerator2, String str, String str2, String str3) {
        return new Code(codeGenerator2.generateCodeStr(str, str2, str3), codeGenerator2.generateCodeInt(str, str2, str3));
    }

    public ErrorBuilder defineAndroidError(String str) {
        return ErrorBuilder.builder(formCode(this.codeGenerator, this.label, "ANDROID", str));
    }

    public ErrorBuilder defineError(String str, String str2) {
        return ErrorBuilder.builder(formCode(this.codeGenerator, this.label, str, str2));
    }

    public ErrorBuilder defineSdkError(String str) {
        return ErrorBuilder.builder(formCode(this.codeGenerator, this.label, "SDK", str));
    }

    public ErrorBuilder defineServerError(String str) {
        return ErrorBuilder.builder(formCode(this.codeGenerator, this.label, "SERVER", str));
    }
}
