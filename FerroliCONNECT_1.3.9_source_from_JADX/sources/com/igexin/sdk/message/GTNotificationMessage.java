package com.igexin.sdk.message;

public class GTNotificationMessage extends GTPushMessage {

    /* renamed from: a */
    private String f3081a;

    /* renamed from: b */
    private String f3082b;

    /* renamed from: c */
    private String f3083c;

    /* renamed from: d */
    private String f3084d;

    public GTNotificationMessage() {
    }

    public GTNotificationMessage(String str, String str2, String str3, String str4) {
        this.f3081a = str;
        this.f3082b = str2;
        this.f3083c = str3;
        this.f3084d = str4;
    }

    public String getContent() {
        return this.f3084d;
    }

    public String getMessageId() {
        return this.f3082b;
    }

    public String getTaskId() {
        return this.f3081a;
    }

    public String getTitle() {
        return this.f3083c;
    }

    public void setContent(String str) {
        this.f3084d = str;
    }

    public void setMessageId(String str) {
        this.f3082b = str;
    }

    public void setTaskId(String str) {
        this.f3081a = str;
    }

    public void setTitle(String str) {
        this.f3083c = str;
    }
}
