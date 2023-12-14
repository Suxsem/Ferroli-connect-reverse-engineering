package com.szacs.ferroliconnect.bean;

import java.util.List;

public class Country {
    private String code;
    private String name;
    private String nameCN;
    private List<Province> provinceList;

    public Country() {
    }

    public Country(String str, String str2, String str3, List<Province> list) {
        this.name = str;
        this.nameCN = str2;
        this.code = str3;
        this.provinceList = list;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getNameCN() {
        return this.nameCN;
    }

    public void setNameCN(String str) {
        this.nameCN = str;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public List<Province> getProvinceList() {
        return this.provinceList;
    }

    public void setProvinceList(List<Province> list) {
        this.provinceList = list;
    }
}
