package com.p107tb.appyunsdk.bean;

import java.util.ArrayList;

/* renamed from: com.tb.appyunsdk.bean.ExtraInfoBean */
public class ExtraInfoBean {
    private ArrayList<BoilerTypeBean> boiler;

    public ArrayList<BoilerTypeBean> getBoiler() {
        return this.boiler;
    }

    public void setBoiler(ArrayList<BoilerTypeBean> arrayList) {
        this.boiler = arrayList;
    }

    public String toString() {
        return "ExtraInfoBean{boiler=" + this.boiler + '}';
    }
}
