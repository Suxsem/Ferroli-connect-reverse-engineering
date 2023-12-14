package com.szacs.ferroliconnect.bean;

import java.io.Serializable;

public class LocationName implements Serializable {

    /* renamed from: en */
    private String f3139en;

    /* renamed from: zh */
    private String f3140zh;

    public String getZh() {
        return this.f3140zh;
    }

    public void setZh(String str) {
        this.f3140zh = str;
    }

    public String getEn() {
        return this.f3139en;
    }

    public void setEn(String str) {
        this.f3139en = str;
    }

    public String toString() {
        return "LocationName{zh='" + this.f3140zh + '\'' + ", en='" + this.f3139en + '\'' + '}';
    }
}
