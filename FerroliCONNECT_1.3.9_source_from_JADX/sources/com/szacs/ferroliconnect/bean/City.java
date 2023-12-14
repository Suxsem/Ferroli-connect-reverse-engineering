package com.szacs.ferroliconnect.bean;

import java.util.List;

public class City {
    private List<District> districtList;
    private String name;
    private String owmId;

    public City() {
    }

    public City(String str, String str2, List<District> list) {
        this.name = str;
        this.owmId = str2;
        this.districtList = list;
    }

    public String getOwmId() {
        return this.owmId;
    }

    public void setOwmId(String str) {
        this.owmId = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public List<District> getDistrictList() {
        return this.districtList;
    }

    public void setDistrictList(List<District> list) {
        this.districtList = list;
    }

    public String toString() {
        return "CityModel [name=" + this.name + ", districtList=" + this.districtList + "]";
    }
}
