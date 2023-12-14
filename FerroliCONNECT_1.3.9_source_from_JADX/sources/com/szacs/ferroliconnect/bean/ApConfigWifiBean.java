package com.szacs.ferroliconnect.bean;

public class ApConfigWifiBean {
    private String PASSWORD;
    private String SSID;

    public String getSSID() {
        return this.SSID;
    }

    public void setSSID(String str) {
        this.SSID = str;
    }

    public String getPASSWORD() {
        return this.PASSWORD;
    }

    public void setPASSWORD(String str) {
        this.PASSWORD = str;
    }

    public String toString() {
        return "ApConfigWifiBean{SSID='" + this.SSID + '\'' + ", PASSWORD='" + this.PASSWORD + '\'' + '}';
    }
}
