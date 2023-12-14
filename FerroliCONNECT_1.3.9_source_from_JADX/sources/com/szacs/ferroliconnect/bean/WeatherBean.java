package com.szacs.ferroliconnect.bean;

import java.util.List;

public class WeatherBean {
    private String base;
    private CloudsBean clouds;
    private int cod;
    private CoordBean coord;

    /* renamed from: dt */
    private int f3141dt;

    /* renamed from: id */
    private int f3142id;
    private MainBean main;
    private String name;
    private SysBean sys;
    private int visibility;
    private List<Weather> weather;
    private WindBean wind;

    public String getIcon() {
        return getWeather().get(0).getIcon();
    }

    public double getTemp() {
        return getMain().getTemp() - 273.15d;
    }

    public CoordBean getCoord() {
        return this.coord;
    }

    public void setCoord(CoordBean coordBean) {
        this.coord = coordBean;
    }

    public String getBase() {
        return this.base;
    }

    public void setBase(String str) {
        this.base = str;
    }

    public MainBean getMain() {
        return this.main;
    }

    public void setMain(MainBean mainBean) {
        this.main = mainBean;
    }

    public int getVisibility() {
        return this.visibility;
    }

    public void setVisibility(int i) {
        this.visibility = i;
    }

    public WindBean getWind() {
        return this.wind;
    }

    public void setWind(WindBean windBean) {
        this.wind = windBean;
    }

    public CloudsBean getClouds() {
        return this.clouds;
    }

    public void setClouds(CloudsBean cloudsBean) {
        this.clouds = cloudsBean;
    }

    public int getDt() {
        return this.f3141dt;
    }

    public void setDt(int i) {
        this.f3141dt = i;
    }

    public SysBean getSys() {
        return this.sys;
    }

    public void setSys(SysBean sysBean) {
        this.sys = sysBean;
    }

    public int getId() {
        return this.f3142id;
    }

    public void setId(int i) {
        this.f3142id = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getCod() {
        return this.cod;
    }

    public void setCod(int i) {
        this.cod = i;
    }

    public List<Weather> getWeather() {
        return this.weather;
    }

    public void setWeather(List<Weather> list) {
        this.weather = list;
    }

    public static class CoordBean {
        private double lat;
        private double lon;

        public double getLon() {
            return this.lon;
        }

        public void setLon(double d) {
            this.lon = d;
        }

        public double getLat() {
            return this.lat;
        }

        public void setLat(double d) {
            this.lat = d;
        }
    }

    public static class MainBean {
        private float humidity;
        private float pressure;
        private double temp;
        private double temp_max;
        private double temp_min;

        public double getTemp() {
            return this.temp;
        }

        public void setTemp(double d) {
            this.temp = d;
        }

        public float getPressure() {
            return this.pressure;
        }

        public void setPressure(float f) {
            this.pressure = f;
        }

        public float getHumidity() {
            return this.humidity;
        }

        public void setHumidity(float f) {
            this.humidity = f;
        }

        public double getTemp_min() {
            return this.temp_min;
        }

        public void setTemp_min(double d) {
            this.temp_min = d;
        }

        public double getTemp_max() {
            return this.temp_max;
        }

        public void setTemp_max(double d) {
            this.temp_max = d;
        }
    }

    public static class WindBean {
        private float deg;
        private double speed;

        public double getSpeed() {
            return this.speed;
        }

        public void setSpeed(double d) {
            this.speed = d;
        }

        public float getDeg() {
            return this.deg;
        }

        public void setDeg(float f) {
            this.deg = f;
        }
    }

    public static class CloudsBean {
        private int all;

        public int getAll() {
            return this.all;
        }

        public void setAll(int i) {
            this.all = i;
        }
    }

    public static class SysBean {
        private String country;

        /* renamed from: id */
        private int f3143id;
        private double message;
        private int sunrise;
        private int sunset;
        private int type;

        public int getType() {
            return this.type;
        }

        public void setType(int i) {
            this.type = i;
        }

        public int getId() {
            return this.f3143id;
        }

        public void setId(int i) {
            this.f3143id = i;
        }

        public double getMessage() {
            return this.message;
        }

        public void setMessage(double d) {
            this.message = d;
        }

        public String getCountry() {
            return this.country;
        }

        public void setCountry(String str) {
            this.country = str;
        }

        public int getSunrise() {
            return this.sunrise;
        }

        public void setSunrise(int i) {
            this.sunrise = i;
        }

        public int getSunset() {
            return this.sunset;
        }

        public void setSunset(int i) {
            this.sunset = i;
        }
    }

    public static class Weather {
        private String description;
        private String icon;

        /* renamed from: id */
        private int f3144id;
        private String main;

        public int getId() {
            return this.f3144id;
        }

        public void setId(int i) {
            this.f3144id = i;
        }

        public String getMain() {
            return this.main;
        }

        public void setMain(String str) {
            this.main = str;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String str) {
            this.icon = str;
        }
    }

    public String toString() {
        return "WeatherBean{coord=" + this.coord + ", base='" + this.base + '\'' + ", main=" + this.main + ", visibility=" + this.visibility + ", wind=" + this.wind + ", clouds=" + this.clouds + ", dt=" + this.f3141dt + ", sys=" + this.sys + ", id=" + this.f3142id + ", name='" + this.name + '\'' + ", cod=" + this.cod + ", weather=" + this.weather + '}';
    }
}
