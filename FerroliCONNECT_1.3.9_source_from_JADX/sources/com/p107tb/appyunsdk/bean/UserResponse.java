package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.UserResponse */
public class UserResponse implements Serializable {
    private String avatar;
    private String create_date;
    private String email;
    private boolean is_active;
    private boolean is_staff;
    private boolean is_superuser;
    private String mobile;
    private String name;
    private String slug;
    private String update_date;

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

    public boolean isIs_staff() {
        return this.is_staff;
    }

    public void setIs_staff(boolean z) {
        this.is_staff = z;
    }

    public boolean isIs_superuser() {
        return this.is_superuser;
    }

    public void setIs_superuser(boolean z) {
        this.is_superuser = z;
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

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String str) {
        this.mobile = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String toString() {
        return "UserResponse{name='" + this.name + '\'' + ", email='" + this.email + '\'' + ", is_staff=" + this.is_staff + ", is_superuser=" + this.is_superuser + ", create_date='" + this.create_date + '\'' + ", update_date='" + this.update_date + '\'' + ", slug='" + this.slug + '\'' + ", is_active=" + this.is_active + ", mobile='" + this.mobile + '\'' + ", avatar='" + this.avatar + '\'' + '}';
    }
}
