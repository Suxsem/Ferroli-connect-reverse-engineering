package com.igexin.sdk.message;

public class UnBindAliasCmdMessage extends GTCmdMessage {

    /* renamed from: a */
    private String f3091a;

    /* renamed from: b */
    private String f3092b;

    public UnBindAliasCmdMessage() {
    }

    public UnBindAliasCmdMessage(String str, String str2, int i) {
        super(i);
        this.f3091a = str;
        this.f3092b = str2;
    }

    public String getCode() {
        return this.f3092b;
    }

    public String getSn() {
        return this.f3091a;
    }

    public void setCode(String str) {
        this.f3092b = str;
    }

    public void setSn(String str) {
        this.f3091a = str;
    }
}
