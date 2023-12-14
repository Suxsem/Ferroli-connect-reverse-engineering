package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.CheckVersionResponse */
public class CheckVersionResponse implements Serializable {
    private String app_code;
    private String app_name;
    private String app_slug;
    private String app_type;
    private boolean beta;
    private String create_date;
    private String file;
    private boolean force_update;
    private boolean is_active;
    private String md5;
    private String slug;
    private String update_date;
    private String version;
    private int version_num;

    public String getApp_slug() {
        return this.app_slug;
    }

    public void setApp_slug(String str) {
        this.app_slug = str;
    }

    public String getApp_code() {
        return this.app_code;
    }

    public void setApp_code(String str) {
        this.app_code = str;
    }

    public String getApp_name() {
        return this.app_name;
    }

    public void setApp_name(String str) {
        this.app_name = str;
    }

    public String getApp_type() {
        return this.app_type;
    }

    public void setApp_type(String str) {
        this.app_type = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public int getVersion_num() {
        return this.version_num;
    }

    public void setVersion_num(int i) {
        this.version_num = i;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String str) {
        this.file = str;
    }

    public boolean isBeta() {
        return this.beta;
    }

    public void setBeta(boolean z) {
        this.beta = z;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String str) {
        this.md5 = str;
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

    public boolean getIs_active() {
        return this.is_active;
    }

    public void setIs_active(boolean z) {
        this.is_active = z;
    }

    public boolean getForce_update() {
        return this.force_update;
    }

    public void setForce_update(boolean z) {
        this.force_update = z;
    }

    public String toString() {
        return "CheckVersionResponse{app_slug='" + this.app_slug + '\'' + ", app_code='" + this.app_code + '\'' + ", app_name='" + this.app_name + '\'' + ", app_type='" + this.app_type + '\'' + ", version='" + this.version + '\'' + ", version_num=" + this.version_num + ", file='" + this.file + '\'' + ", beta=" + this.beta + ", md5='" + this.md5 + '\'' + ", create_date='" + this.create_date + '\'' + ", update_date='" + this.update_date + '\'' + ", slug='" + this.slug + '\'' + ", is_active='" + this.is_active + '\'' + ", force_update='" + this.force_update + '\'' + '}';
    }
}
