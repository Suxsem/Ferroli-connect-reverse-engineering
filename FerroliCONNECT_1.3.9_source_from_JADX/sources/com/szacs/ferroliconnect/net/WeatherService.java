package com.szacs.ferroliconnect.net;

import com.szacs.ferroliconnect.bean.WeatherBean;
import p110io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @GET("weather")
    Observable<WeatherBean> getCurrentWeatherByCityId(@Query("id") String str, @Query("APPID") String str2);

    @GET("weather")
    Observable<WeatherBean> getCurrentWeatherByCornadiate(@Query("lat") String str, @Query("lon") String str2, @Query("APPID") String str3);

    @GET("weather")
    Observable<WeatherBean> getCurrentWeatherByName(@Query("q") String str, @Query("APPID") String str2);
}
