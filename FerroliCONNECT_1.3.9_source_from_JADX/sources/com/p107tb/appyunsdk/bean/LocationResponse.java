package com.p107tb.appyunsdk.bean;

import java.util.List;

/* renamed from: com.tb.appyunsdk.bean.LocationResponse */
public class LocationResponse {
    private int count;
    private String next;
    private List<LocationBean> results;

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public String getNext() {
        return this.next;
    }

    public void setNext(String str) {
        this.next = str;
    }

    public List<LocationBean> getResults() {
        return this.results;
    }

    public void setResults(List<LocationBean> list) {
        this.results = list;
    }
}
