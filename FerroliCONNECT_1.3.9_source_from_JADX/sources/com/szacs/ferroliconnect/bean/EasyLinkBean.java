package com.szacs.ferroliconnect.bean;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;

public class EasyLinkBean {
    private String DID;
    private String ExtraData;
    private String FTC;

    /* renamed from: FW */
    private String f3130FW;

    /* renamed from: HD */
    private String f3131HD;

    /* renamed from: IP */
    private String f3132IP;
    private String MAC;

    /* renamed from: MD */
    private String f3133MD;

    /* renamed from: MF */
    private String f3134MF;

    /* renamed from: OS */
    private String f3135OS;

    /* renamed from: PO */
    private String f3136PO;
    private int PORT;

    /* renamed from: RF */
    private String f3137RF;
    @SerializedName("wlan unconfigured")
    private String wlan_unconfigured;

    public String getFW() {
        return this.f3130FW;
    }

    public void setFW(String str) {
        this.f3130FW = str;
    }

    public String getHD() {
        return this.f3131HD;
    }

    public void setHD(String str) {
        this.f3131HD = str;
    }

    public String getPO() {
        return this.f3136PO;
    }

    public void setPO(String str) {
        this.f3136PO = str;
    }

    public String getRF() {
        return this.f3137RF;
    }

    public void setRF(String str) {
        this.f3137RF = str;
    }

    public String getMAC() {
        return this.MAC;
    }

    public void setMAC(String str) {
        this.MAC = str;
    }

    public String getOS() {
        return this.f3135OS;
    }

    public void setOS(String str) {
        this.f3135OS = str;
    }

    public String getMD() {
        return this.f3133MD;
    }

    public void setMD(String str) {
        this.f3133MD = str;
    }

    public String getMF() {
        return this.f3134MF;
    }

    public void setMF(String str) {
        this.f3134MF = str;
    }

    public String getFTC() {
        return this.FTC;
    }

    public void setFTC(String str) {
        this.FTC = str;
    }

    public int getPORT() {
        return this.PORT;
    }

    public void setPORT(int i) {
        this.PORT = i;
    }

    public String getIP() {
        return this.f3132IP;
    }

    public void setIP(String str) {
        this.f3132IP = str;
    }

    public String getWlan_unconfigured() {
        return this.wlan_unconfigured;
    }

    public void setWlan_unconfigured(String str) {
        this.wlan_unconfigured = str;
    }

    public String getDID() {
        if (TextUtils.isEmpty(this.DID)) {
            return "";
        }
        return this.DID;
    }

    public void setDID(String str) {
        this.DID = str;
    }

    public String getExtraData() {
        return this.ExtraData;
    }

    public void setExtraData(String str) {
        this.ExtraData = str;
    }

    public String toString() {
        return "EasyLinkBean{FW='" + this.f3130FW + '\'' + ", HD='" + this.f3131HD + '\'' + ", PO='" + this.f3136PO + '\'' + ", RF='" + this.f3137RF + '\'' + ", MAC='" + this.MAC + '\'' + ", OS='" + this.f3135OS + '\'' + ", MD='" + this.f3133MD + '\'' + ", MF='" + this.f3134MF + '\'' + ", wlan_unconfigured='" + this.wlan_unconfigured + '\'' + ", FTC='" + this.FTC + '\'' + ", PORT=" + this.PORT + ", IP='" + this.f3132IP + '\'' + ", DID='" + this.DID + '\'' + ", ExtraData='" + this.ExtraData + '\'' + '}';
    }
}
