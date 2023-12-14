package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.SubmitGtClientIdResponse */
public class SubmitGtClientIdResponse implements Serializable {
    private String brand;
    private String city_id;
    private String create_time;
    private String installation_date;
    private String location;
    private String model;
    private String owner_slug;
    private String slug;

    /* renamed from: sn */
    private String f3612sn;
    private String wifi_box_code;
    private String wifi_box_slug;

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String str) {
        this.brand = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String getSn() {
        return this.f3612sn;
    }

    public void setSn(String str) {
        this.f3612sn = str;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String str) {
        this.location = str;
    }

    public String getCity_id() {
        return this.city_id;
    }

    public void setCity_id(String str) {
        this.city_id = str;
    }

    public String getInstallation_date() {
        return this.installation_date;
    }

    public void setInstallation_date(String str) {
        this.installation_date = str;
    }

    public String getWifi_box_code() {
        return this.wifi_box_code;
    }

    public void setWifi_box_code(String str) {
        this.wifi_box_code = str;
    }

    public String getWifi_box_slug() {
        return this.wifi_box_slug;
    }

    public void setWifi_box_slug(String str) {
        this.wifi_box_slug = str;
    }

    public String getOwner_slug() {
        return this.owner_slug;
    }

    public void setOwner_slug(String str) {
        this.owner_slug = str;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String str) {
        this.slug = str;
    }

    public String getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(String str) {
        this.create_time = str;
    }

    public String toString() {
        return "SubmitGtClientIdResponse{brand='" + this.brand + '\'' + ", model='" + this.model + '\'' + ", sn='" + this.f3612sn + '\'' + ", location='" + this.location + '\'' + ", city_id='" + this.city_id + '\'' + ", installation_date='" + this.installation_date + '\'' + ", wifi_box_code='" + this.wifi_box_code + '\'' + ", wifi_box_slug='" + this.wifi_box_slug + '\'' + ", owner_slug='" + this.owner_slug + '\'' + ", slug='" + this.slug + '\'' + ", create_time='" + this.create_time + '\'' + '}';
    }
}
