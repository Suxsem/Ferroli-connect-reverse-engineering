package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.JuHeWeatherResponse */
public class JuHeWeatherResponse implements Serializable {
    private JuHeWeatherTodayBean today;

    public JuHeWeatherTodayBean getToday() {
        return this.today;
    }

    public void setToday(JuHeWeatherTodayBean juHeWeatherTodayBean) {
        this.today = juHeWeatherTodayBean;
    }
}
