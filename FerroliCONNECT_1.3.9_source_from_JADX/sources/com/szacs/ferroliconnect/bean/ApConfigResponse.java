package com.szacs.ferroliconnect.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ApConfigResponse implements Serializable {
    private String DID;
    private String ExtraData;
    private String FTC;
    private String MAC;

    /* renamed from: OS */
    private String f3128OS;
    @SerializedName("wlan unconfigured")
    private String wlanUnConfigured;

    public String getMAC() {
        return this.MAC;
    }

    public void setMAC(String str) {
        this.MAC = str;
    }

    public String getOS() {
        return this.f3128OS;
    }

    public void setOS(String str) {
        this.f3128OS = str;
    }

    public String getDID() {
        return this.DID;
    }

    public void setDID(String str) {
        this.DID = str;
    }

    public String getWlanUnConfigured() {
        return this.wlanUnConfigured;
    }

    public void setWlanUnConfigured(String str) {
        this.wlanUnConfigured = str;
    }

    public String getFTC() {
        return this.FTC;
    }

    public void setFTC(String str) {
        this.FTC = str;
    }

    public String getExtraData() {
        return this.ExtraData;
    }

    public void setExtraData(String str) {
        this.ExtraData = str;
    }

    public String toString() {
        return "ApConfigResponse{MAC='" + this.MAC + '\'' + ", OS='" + this.f3128OS + '\'' + ", DID='" + this.DID + '\'' + ", wlanUnConfigured='" + this.wlanUnConfigured + '\'' + ", FTC='" + this.FTC + '\'' + ", ExtraData='" + this.ExtraData + '\'' + '}';
    }
}
