package com.p107tb.appyunsdk.network;

/* renamed from: com.tb.appyunsdk.network.HttpClient */
public class HttpClient {
    private static AppYunService yunService = ((AppYunService) RetrofitFactory.getRetrofit(AppYunService.class, AppYunApi.BASE_URL));

    public static AppYunService getYunService() {
        return yunService;
    }
}
