package com.igexin.sdk.message;

public class SetTagCmdMessage extends GTCmdMessage {

    /* renamed from: a */
    private String f3089a;

    /* renamed from: b */
    private String f3090b;

    public SetTagCmdMessage() {
    }

    public SetTagCmdMessage(String str, String str2, int i) {
        super(i);
        this.f3089a = str;
        this.f3090b = str2;
    }

    public String getCode() {
        return this.f3090b;
    }

    public String getSn() {
        return this.f3089a;
    }

    public void setCode(String str) {
        this.f3090b = str;
    }

    public void setSn(String str) {
        this.f3089a = str;
    }
}
