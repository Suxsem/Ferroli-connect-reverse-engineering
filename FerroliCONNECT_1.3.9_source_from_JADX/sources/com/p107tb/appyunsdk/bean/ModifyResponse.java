package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.ModifyResponse */
public class ModifyResponse implements Serializable {
    private String email;
    private String mobile;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String str) {
        this.mobile = str;
    }

    public String toString() {
        return "RegisterResponse{name='" + this.name + '\'' + ", email='" + this.email + '\'' + ", mobile='" + this.mobile + '\'' + '}';
    }
}
