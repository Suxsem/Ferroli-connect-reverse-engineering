package com.p107tb.appyunsdk.bean;

/* renamed from: com.tb.appyunsdk.bean.BoilerTypeBean */
public class BoilerTypeBean {
    private String boiler_id;
    private boolean online;
    private String type;

    public String getBoiler_id() {
        return this.boiler_id;
    }

    public void setBoiler_id(String str) {
        this.boiler_id = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public boolean isOnline() {
        return this.online;
    }

    public void setOnline(boolean z) {
        this.online = z;
    }

    public String toString() {
        return "BoilerTypeBean{boiler_id='" + this.boiler_id + '\'' + ", type='" + this.type + '\'' + ", online=" + this.online + '}';
    }
}
