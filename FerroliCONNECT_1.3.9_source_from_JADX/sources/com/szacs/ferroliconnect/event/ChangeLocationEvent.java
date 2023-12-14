package com.szacs.ferroliconnect.event;

public class ChangeLocationEvent {
    private String city;
    private String country;
    private String countryCode;
    private String owmId;
    private String province;
    private String zip;

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String str) {
        this.countryCode = str;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String str) {
        this.province = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getOwmId() {
        return this.owmId;
    }

    public void setOwmId(String str) {
        this.owmId = str;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String str) {
        this.zip = str;
    }
}
