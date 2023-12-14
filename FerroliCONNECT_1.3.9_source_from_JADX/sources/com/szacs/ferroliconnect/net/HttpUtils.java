package com.szacs.ferroliconnect.net;

import com.p107tb.appyunsdk.network.RetrofitFactory;
import com.szacs.ferroliconnect.MainApplication;

public class HttpUtils {
    private static CommonSevice retrofit = ((CommonSevice) RetrofitFactory.getRetrofit(CommonSevice.class, MainApplication.getBaseUrl()));
    private static WeatherService weatherService = ((WeatherService) RetrofitFactory.getRetrofit(WeatherService.class, WeatherService.BASE_URL));

    public static CommonSevice getRetrofit() {
        return retrofit;
    }

    public static WeatherService getWeatherService() {
        return weatherService;
    }
}
