package com.szacs.ferroliconnect.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationGroup implements Serializable {
    private String city_id = "";
    private String code = "";
    private ArrayList<LocationGroup> group;

    /* renamed from: id */
    private int f3138id;
    private LocationName name;
    private String timezone = "";

    public int getId() {
        return this.f3138id;
    }

    public void setId(int i) {
        this.f3138id = i;
    }

    public String getCity_id() {
        return this.city_id;
    }

    public void setCity_id(String str) {
        this.city_id = str;
    }

    public LocationName getName() {
        return this.name;
    }

    public void setName(LocationName locationName) {
        this.name = locationName;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String str) {
        this.timezone = str;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public ArrayList<LocationGroup> getGroup() {
        return this.group;
    }

    public void setGroup(ArrayList<LocationGroup> arrayList) {
        this.group = arrayList;
    }

    public String toString() {
        return "LocationGroup{id=" + this.f3138id + ", city_id='" + this.city_id + '\'' + ", name=" + this.name + ", group=" + this.group + ", timezone='" + this.timezone + '\'' + ", code='" + this.code + '\'' + '}';
    }
}
