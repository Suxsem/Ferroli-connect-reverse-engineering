package com.p107tb.appyunsdk.exception;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.exception.ErrorInfoResponse */
public class ErrorInfoResponse implements Serializable {
    private int code;
    private String msg;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String toString() {
        return "ErrorInfoResponse{msg='" + this.msg + '\'' + ", code=" + this.code + '}';
    }
}
