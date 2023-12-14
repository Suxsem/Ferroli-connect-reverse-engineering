package com.igexin.sdk.message;

public class BindAliasCmdMessage extends GTCmdMessage {

    /* renamed from: a */
    private String f3074a;

    /* renamed from: b */
    private String f3075b;

    public BindAliasCmdMessage() {
    }

    public BindAliasCmdMessage(String str, String str2, int i) {
        super(i);
        this.f3074a = str;
        this.f3075b = str2;
    }

    public String getCode() {
        return this.f3075b;
    }

    public String getSn() {
        return this.f3074a;
    }

    public void setCode(String str) {
        this.f3075b = str;
    }

    public void setSn(String str) {
        this.f3074a = str;
    }
}
