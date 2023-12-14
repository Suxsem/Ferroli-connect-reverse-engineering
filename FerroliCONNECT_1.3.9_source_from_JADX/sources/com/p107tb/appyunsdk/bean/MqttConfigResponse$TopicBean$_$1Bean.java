package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.MqttConfigResponse$TopicBean$_$1Bean */
public class MqttConfigResponse$TopicBean$_$1Bean implements Serializable {
    private String DEV_REALT;
    private String FROM_DEV;
    private String SER_NOTI;
    private String SER_NOTI_ALLCLI;
    private String SER_RES;

    public String getSER_RES() {
        return this.SER_RES;
    }

    public void setSER_RES(String str) {
        this.SER_RES = str;
    }

    public String getSER_NOTI() {
        return this.SER_NOTI;
    }

    public void setSER_NOTI(String str) {
        this.SER_NOTI = str;
    }

    public String getSER_NOTI_ALLCLI() {
        return this.SER_NOTI_ALLCLI;
    }

    public void setSER_NOTI_ALLCLI(String str) {
        this.SER_NOTI_ALLCLI = str;
    }

    public String getFROM_DEV() {
        return this.FROM_DEV;
    }

    public void setFROM_DEV(String str) {
        this.FROM_DEV = str;
    }

    public String getDEV_REALT() {
        return this.DEV_REALT;
    }

    public void setDEV_REALT(String str) {
        this.DEV_REALT = str;
    }

    public String toString() {
        return "_$1Bean{SER_RES='" + this.SER_RES + '\'' + ", SER_NOTI='" + this.SER_NOTI + '\'' + ", SER_NOTI_ALLCLI='" + this.SER_NOTI_ALLCLI + '\'' + ", FROM_DEV='" + this.FROM_DEV + '\'' + ", DEV_REALT='" + this.DEV_REALT + '\'' + '}';
    }
}
