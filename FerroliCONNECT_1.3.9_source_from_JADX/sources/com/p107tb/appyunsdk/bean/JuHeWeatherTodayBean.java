package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.JuHeWeatherTodayBean */
public class JuHeWeatherTodayBean implements Serializable {
    private String city;
    private String comfort_index;
    private String date_y;
    private String dressing_advice;
    private String dressing_index;
    private String drying_index;
    private String exercise_index;
    private String temperature;
    private String travel_index;
    private String uv_index;
    private String wash_index;
    private String weather;
    private String weather_icon;
    private JuHeWeatherId weather_id;
    private String week;
    private String wind;

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String str) {
        this.temperature = str;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setWeather(String str) {
        this.weather = str;
    }

    public JuHeWeatherId getWeather_id() {
        return this.weather_id;
    }

    public void setWeather_id(JuHeWeatherId juHeWeatherId) {
        this.weather_id = juHeWeatherId;
    }

    public String getWind() {
        return this.wind;
    }

    public void setWind(String str) {
        this.wind = str;
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String str) {
        this.week = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getDate_y() {
        return this.date_y;
    }

    public void setDate_y(String str) {
        this.date_y = str;
    }

    public String getDressing_index() {
        return this.dressing_index;
    }

    public void setDressing_index(String str) {
        this.dressing_index = str;
    }

    public String getDressing_advice() {
        return this.dressing_advice;
    }

    public void setDressing_advice(String str) {
        this.dressing_advice = str;
    }

    public String getUv_index() {
        return this.uv_index;
    }

    public void setUv_index(String str) {
        this.uv_index = str;
    }

    public String getComfort_index() {
        return this.comfort_index;
    }

    public void setComfort_index(String str) {
        this.comfort_index = str;
    }

    public String getWash_index() {
        return this.wash_index;
    }

    public void setWash_index(String str) {
        this.wash_index = str;
    }

    public String getTravel_index() {
        return this.travel_index;
    }

    public void setTravel_index(String str) {
        this.travel_index = str;
    }

    public String getExercise_index() {
        return this.exercise_index;
    }

    public void setExercise_index(String str) {
        this.exercise_index = str;
    }

    public String getDrying_index() {
        return this.drying_index;
    }

    public void setDrying_index(String str) {
        this.drying_index = str;
    }

    public String getWeather_icon() {
        return this.weather_icon;
    }

    public void setWeather_icon(String str) {
        this.weather_icon = str;
    }

    public String toString() {
        return "JuHeWeatherTodayBean{temperature='" + this.temperature + '\'' + ", weather='" + this.weather + '\'' + ", weather_id=" + this.weather_id + ", wind='" + this.wind + '\'' + ", week='" + this.week + '\'' + ", city='" + this.city + '\'' + ", date_y='" + this.date_y + '\'' + ", dressing_index='" + this.dressing_index + '\'' + ", dressing_advice='" + this.dressing_advice + '\'' + ", uv_index='" + this.uv_index + '\'' + ", comfort_index='" + this.comfort_index + '\'' + ", wash_index='" + this.wash_index + '\'' + ", travel_index='" + this.travel_index + '\'' + ", exercise_index='" + this.exercise_index + '\'' + ", drying_index='" + this.drying_index + '\'' + ", weather_icon='" + this.weather_icon + '\'' + '}';
    }
}
