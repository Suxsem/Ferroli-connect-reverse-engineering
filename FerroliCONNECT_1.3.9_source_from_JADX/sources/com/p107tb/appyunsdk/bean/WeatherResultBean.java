package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.WeatherResultBean */
public class WeatherResultBean implements Serializable {
    private String last_update;
    private WeatherLocationBean location;
    private WeatherNowBean now;

    public WeatherLocationBean getLocation() {
        return this.location;
    }

    public void setLocation(WeatherLocationBean weatherLocationBean) {
        this.location = weatherLocationBean;
    }

    public WeatherNowBean getNow() {
        return this.now;
    }

    public void setNow(WeatherNowBean weatherNowBean) {
        this.now = weatherNowBean;
    }

    public String getLast_update() {
        return this.last_update;
    }

    public void setLast_update(String str) {
        this.last_update = str;
    }

    public String toString() {
        return "WeatherResultBean{location=" + this.location + ", now=" + this.now + ", last_update='" + this.last_update + '\'' + '}';
    }
}
