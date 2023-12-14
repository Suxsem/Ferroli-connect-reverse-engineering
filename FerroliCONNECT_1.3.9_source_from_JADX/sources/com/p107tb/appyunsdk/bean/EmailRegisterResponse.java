package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.EmailRegisterResponse */
public class EmailRegisterResponse implements Serializable {
    private String create_date;
    private String email;
    private boolean is_active;
    private String mobile;
    private String partner;
    private String slug;
    private String update_date;
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
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

    public String getPartner() {
        return this.partner;
    }

    public void setPartner(String str) {
        this.partner = str;
    }

    public String getCreate_date() {
        return this.create_date;
    }

    public void setCreate_date(String str) {
        this.create_date = str;
    }

    public String getUpdate_date() {
        return this.update_date;
    }

    public void setUpdate_date(String str) {
        this.update_date = str;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String str) {
        this.slug = str;
    }

    public boolean isIs_active() {
        return this.is_active;
    }

    public void setIs_active(boolean z) {
        this.is_active = z;
    }
}
