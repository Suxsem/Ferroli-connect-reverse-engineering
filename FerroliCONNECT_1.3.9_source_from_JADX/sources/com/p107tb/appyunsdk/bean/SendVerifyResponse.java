package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.SendVerifyResponse */
public class SendVerifyResponse implements Serializable {
    private String detail;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String str) {
        this.detail = str;
    }

    public String toString() {
        return "ErrorResponse{status=" + this.status + ", detail='" + this.detail + '\'' + '}';
    }
}
