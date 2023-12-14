package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.WeatherNowBean */
public class WeatherNowBean implements Serializable {
    private String clouds;
    private String code;
    private String dew_point;
    private String feels_like;
    private String humidity;
    private String pressure;
    private String temperature;
    private String text;
    private String visibility;
    private String wind_direction;
    private String wind_direction_degree;
    private String wind_scale;
    private String wind_speed;

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String str) {
        this.temperature = str;
    }

    public String getFeels_like() {
        return this.feels_like;
    }

    public void setFeels_like(String str) {
        this.feels_like = str;
    }

    public String getPressure() {
        return this.pressure;
    }

    public void setPressure(String str) {
        this.pressure = str;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public void setHumidity(String str) {
        this.humidity = str;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public void setVisibility(String str) {
        this.visibility = str;
    }

    public String getWind_direction() {
        return this.wind_direction;
    }

    public void setWind_direction(String str) {
        this.wind_direction = str;
    }

    public String getWind_direction_degree() {
        return this.wind_direction_degree;
    }

    public void setWind_direction_degree(String str) {
        this.wind_direction_degree = str;
    }

    public String getWind_speed() {
        return this.wind_speed;
    }

    public void setWind_speed(String str) {
        this.wind_speed = str;
    }

    public String getWind_scale() {
        return this.wind_scale;
    }

    public void setWind_scale(String str) {
        this.wind_scale = str;
    }

    public String getClouds() {
        return this.clouds;
    }

    public void setClouds(String str) {
        this.clouds = str;
    }

    public String getDew_point() {
        return this.dew_point;
    }

    public void setDew_point(String str) {
        this.dew_point = str;
    }

    public String toString() {
        return "WeatherNowBean{text='" + this.text + '\'' + ", code='" + this.code + '\'' + ", temperature='" + this.temperature + '\'' + ", feels_like='" + this.feels_like + '\'' + ", pressure='" + this.pressure + '\'' + ", humidity='" + this.humidity + '\'' + ", visibility='" + this.visibility + '\'' + ", wind_direction='" + this.wind_direction + '\'' + ", wind_direction_degree='" + this.wind_direction_degree + '\'' + ", wind_speed='" + this.wind_speed + '\'' + ", wind_scale='" + this.wind_scale + '\'' + ", clouds='" + this.clouds + '\'' + ", dew_point='" + this.dew_point + '\'' + '}';
    }
}
