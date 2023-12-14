package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.UpdateUserInfoResponse */
public class UpdateUserInfoResponse implements Serializable {
    private String avatar;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String toString() {
        return "UpdateUserInfoResponse{name='" + this.name + '\'' + ", avatar='" + this.avatar + '\'' + '}';
    }
}
