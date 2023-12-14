package com.szacs.ferroliconnect.bean;

import com.szacs.ferroliconnect.util.ACache;
import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

public class Gateway implements Serializable {
    public static final String CLOUDWARM = "008P3";
    public static final String NORITZ = "15604";
    private boolean autoDaylightSavingTime;
    private String deviceId;
    private String host;
    private String imagePath;
    private boolean isOnline;
    private boolean isReceiverOnline;
    private String localImageName;
    private String model;
    private String name;
    private int outdoorTempSource;
    private String port;
    private String projectCode;
    private String serverImageMD5;
    private String serverImageName;
    private int timeZone;
    private String version;
    private int versionCode;
    private Weather weather = new Weather();
    private String webWeatherLocation;

    public String getWebWeatherLocation() {
        return this.webWeatherLocation;
    }

    public void setWebWeatherLocation(String str) {
        this.webWeatherLocation = str;
    }

    public int getTimeZone() {
        return this.timeZone;
    }

    public int getTimeOffset() {
        int i = this.timeZone;
        return (!this.autoDaylightSavingTime || !TimeZone.getDefault().inDaylightTime(new Date())) ? i : i + ACache.TIME_HOUR;
    }

    public void setTimeZone(int i) {
        this.timeZone = i;
    }

    public boolean isAutoDaylightSavingTime() {
        return this.autoDaylightSavingTime;
    }

    public void setAutoDaylightSavingTime(boolean z) {
        this.autoDaylightSavingTime = z;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    private class Weather {
        public String location;
        public int temperature;
        public String weatherDescription;

        private Weather() {
        }
    }

    public void init(String str, String str2, String str3, String str4, boolean z, String str5, String str6, String str7, String str8, int i) {
        this.deviceId = str;
        this.host = str2;
        this.port = str3;
        this.name = str4;
        this.isOnline = z;
        this.serverImageMD5 = str5;
        this.serverImageName = str6;
        this.localImageName = str7;
        this.projectCode = str8;
        this.versionCode = i;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String str) {
        this.port = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    public void setIsOnline(boolean z) {
        this.isOnline = z;
    }

    public void setWeather(int i, String str, String str2) {
        this.weather.temperature = i;
        if (str.equals("null")) {
            this.weather.weatherDescription = "";
        } else {
            this.weather.weatherDescription = str;
        }
        if (str2.equals("null")) {
            this.weather.location = "";
        } else {
            this.weather.location = str2;
        }
    }

    public int getOutdoorTemperature() {
        return this.weather.temperature;
    }

    public String getOutdoorWeather() {
        return this.weather.weatherDescription;
    }

    public String getLocation() {
        return this.weather.location;
    }

    public int getOutdoorTempSource() {
        return this.outdoorTempSource;
    }

    public void setOutdoorTempSource(int i) {
        this.outdoorTempSource = i;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String str) {
        this.imagePath = str;
    }

    public String getServerImageMD5() {
        return this.serverImageMD5;
    }

    public void setServerImageMD5(String str) {
        this.serverImageMD5 = str;
    }

    public String getLocalImageName() {
        return this.localImageName;
    }

    public void setLocalImageName(String str) {
        this.localImageName = str;
    }

    public String getServerImageName() {
        return this.serverImageName;
    }

    public void setServerImageName(String str) {
        this.serverImageName = str;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public void setProjectCode(String str) {
        this.projectCode = str;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int i) {
        this.versionCode = i;
    }

    public boolean isReceiverOnline() {
        return this.isReceiverOnline;
    }

    public void setReveiverOnline(boolean z) {
        this.isReceiverOnline = z;
    }
}
