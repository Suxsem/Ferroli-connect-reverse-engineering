package com.p107tb.appyunsdk.bean;

import java.util.List;

/* renamed from: com.tb.appyunsdk.bean.LocationBean */
public class LocationBean {
    private List<LocationBean> cities;
    private String name;
    private String province_slug;
    private String slug;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String str) {
        this.slug = str;
    }

    public List<LocationBean> getCities() {
        return this.cities;
    }

    public void setCities(List<LocationBean> list) {
        this.cities = list;
    }

    public String getProvince_slug() {
        return this.province_slug;
    }

    public void setProvince_slug(String str) {
        this.province_slug = str;
    }
}
