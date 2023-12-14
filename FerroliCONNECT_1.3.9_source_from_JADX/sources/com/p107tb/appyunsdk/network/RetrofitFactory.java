package com.p107tb.appyunsdk.network;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/* renamed from: com.tb.appyunsdk.network.RetrofitFactory */
public class RetrofitFactory {
    private static final String TAG = "okHttp";
    private static final long TIME_OUT = 30;

    public static OkHttpClient.Builder getOkhttpClientBuilder() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            public void log(String str) {
                Log.i(RetrofitFactory.TAG, str);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).readTimeout(TIME_OUT, TimeUnit.SECONDS).connectTimeout(TIME_OUT, TimeUnit.SECONDS);
    }

    public static Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder().client(getOkhttpClientBuilder().build()).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static <T> T getRetrofit(Class<T> cls, String str) {
        return getRetrofitBuilder().baseUrl(str).build().create(cls);
    }
}
