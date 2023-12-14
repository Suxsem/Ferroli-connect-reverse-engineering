package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.WeatherLocationBean */
public class WeatherLocationBean implements Serializable {
    private String country;

    /* renamed from: id */
    private String f3613id;
    private String name;
    private String path;
    private String timezone;
    private String timezone_offset;

    public String getId() {
        return this.f3613id;
    }

    public void setId(String str) {
        this.f3613id = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String str) {
        this.timezone = str;
    }

    public String getTimezone_offset() {
        return this.timezone_offset;
    }

    public void setTimezone_offset(String str) {
        this.timezone_offset = str;
    }

    public String toString() {
        return "WeatherLocationBean{id='" + this.f3613id + '\'' + ", name='" + this.name + '\'' + ", country='" + this.country + '\'' + ", path='" + this.path + '\'' + ", timezone='" + this.timezone + '\'' + ", timezone_offset='" + this.timezone_offset + '\'' + '}';
    }
}
