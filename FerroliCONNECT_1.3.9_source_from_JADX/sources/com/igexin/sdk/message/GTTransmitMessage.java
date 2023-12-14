package com.igexin.sdk.message;

public class GTTransmitMessage extends GTPushMessage {

    /* renamed from: a */
    private String f3085a;

    /* renamed from: b */
    private String f3086b;

    /* renamed from: c */
    private String f3087c;

    /* renamed from: d */
    private byte[] f3088d;

    public GTTransmitMessage() {
    }

    public GTTransmitMessage(String str, String str2, String str3, byte[] bArr) {
        this.f3085a = str;
        this.f3086b = str2;
        this.f3087c = str3;
        this.f3088d = bArr;
    }

    public String getMessageId() {
        return this.f3086b;
    }

    public byte[] getPayload() {
        return this.f3088d;
    }

    public String getPayloadId() {
        return this.f3087c;
    }

    public String getTaskId() {
        return this.f3085a;
    }

    public void setMessageId(String str) {
        this.f3086b = str;
    }

    public void setPayload(byte[] bArr) {
        this.f3088d = bArr;
    }

    public void setPayloadId(String str) {
        this.f3087c = str;
    }

    public void setTaskId(String str) {
        this.f3085a = str;
    }
}
