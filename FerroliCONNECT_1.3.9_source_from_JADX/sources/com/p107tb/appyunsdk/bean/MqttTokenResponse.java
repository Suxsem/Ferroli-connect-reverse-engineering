package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.MqttTokenResponse */
public class MqttTokenResponse implements Serializable {
    private String app_code;
    private String token;
    private String user_slug;

    public String getUser_slug() {
        return this.user_slug;
    }

    public void setUser_slug(String str) {
        this.user_slug = str;
    }

    public String getApp_code() {
        return this.app_code;
    }

    public void setApp_code(String str) {
        this.app_code = str;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String toString() {
        return "MqttTokenResponse{user_slug='" + this.user_slug + '\'' + ", app_code='" + this.app_code + '\'' + ", token='" + this.token + '\'' + '}';
    }
}
