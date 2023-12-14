package com.p107tb.appyunsdk.bean;

import java.io.Serializable;
import java.util.ArrayList;

/* renamed from: com.tb.appyunsdk.bean.XZWeatherResponse */
public class XZWeatherResponse implements Serializable {
    private ArrayList<WeatherResultBean> results;

    public ArrayList<WeatherResultBean> getResults() {
        return this.results;
    }

    public void setResults(ArrayList<WeatherResultBean> arrayList) {
        this.results = arrayList;
    }
}
