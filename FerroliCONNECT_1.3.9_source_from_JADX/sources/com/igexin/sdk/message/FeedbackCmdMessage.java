package com.igexin.sdk.message;

public class FeedbackCmdMessage extends GTCmdMessage {

    /* renamed from: a */
    private String f3076a;

    /* renamed from: b */
    private String f3077b;

    /* renamed from: c */
    private String f3078c;

    /* renamed from: d */
    private long f3079d;

    public FeedbackCmdMessage() {
    }

    public FeedbackCmdMessage(String str, String str2, String str3, long j, int i) {
        super(i);
        this.f3076a = str;
        this.f3077b = str2;
        this.f3078c = str3;
        this.f3079d = j;
    }

    public String getActionId() {
        return this.f3077b;
    }

    public String getResult() {
        return this.f3078c;
    }

    public String getTaskId() {
        return this.f3076a;
    }

    public long getTimeStamp() {
        return this.f3079d;
    }

    public void setActionId(String str) {
        this.f3077b = str;
    }

    public void setResult(String str) {
        this.f3078c = str;
    }

    public void setTaskId(String str) {
        this.f3076a = str;
    }

    public void setTimeStamp(long j) {
        this.f3079d = j;
    }
}
