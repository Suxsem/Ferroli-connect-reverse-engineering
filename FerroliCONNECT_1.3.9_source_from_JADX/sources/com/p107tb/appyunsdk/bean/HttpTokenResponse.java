package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.HttpTokenResponse */
public class HttpTokenResponse implements Serializable {
    private String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String toString() {
        return "HttpTokenResponse{token='" + this.token + '\'' + '}';
    }
}
