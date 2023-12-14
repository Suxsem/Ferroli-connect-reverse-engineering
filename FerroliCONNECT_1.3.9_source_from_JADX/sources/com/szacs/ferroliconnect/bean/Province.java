package com.szacs.ferroliconnect.bean;

import java.util.List;

public class Province {
    private List<City> cityList;
    private String name;

    public Province() {
    }

    public Province(String str, List<City> list) {
        this.name = str;
        this.cityList = list;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public List<City> getCityList() {
        return this.cityList;
    }

    public void setCityList(List<City> list) {
        this.cityList = list;
    }

    public String toString() {
        return "ProvinceModel [name=" + this.name + ", cityList=" + this.cityList + "]";
    }
}
