package com.szacs.ferroliconnect.net;

import com.p107tb.appyunsdk.bean.DeviceListResponse;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.szacs.ferroliconnect.bean.BoilerInfoBean;
import com.szacs.ferroliconnect.bean.BoilerMessage;
import com.szacs.ferroliconnect.bean.DeviceBean;
import com.szacs.ferroliconnect.bean.TemperatureMessage;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import p110io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface CommonSevice {
    @FormUrlEncoded
    @PUT("/apps/topiot_ferroli_temperature_controller/boiler_info/{wifi_box_slug} ")
    Observable<String> UpdateBoilerInfo(@Header("Authorization") String str, @Header("APPSLUG") String str2, @Path("wifi_box_slug") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("apps/topiot_ferroli_temperature_controller/feedback")
    Observable<String> feedback(@FieldMap Map<String, String> map, @Header("Authorization") String str, @Header("APPSLUG") String str2);

    @GET
    Call<ResponseBody> getAvatar(@Url String str);

    @GET("/apps/topiot_ferroli_temperature_controller/boiler_info/{wifi_box_slug} ")
    Observable<BoilerInfoBean> getBoilerInfo(@Header("Authorization") String str, @Header("APPSLUG") String str2, @Path("wifi_box_slug") String str3);

    @GET("/apps/topiot_ferroli_temperature_controller/boiler_points/{wifi_box_slug}")
    Observable<BoilerMessage> getBoilerPointsInfo(@Header("Authorization") String str, @Header("APPSLUG") String str2, @Path("wifi_box_slug") String str3);

    @GET("/api/users/{user_slug}/devices/{device_slug}")
    Observable<DeviceListResponse.ResultsBean> getDeviceInfo(@Header("Authorization") String str, @Path("user_slug") String str2, @Path("device_slug") String str3);

    @GET("/apps/topiot_ferroli_temperature_controller/thermostat_points/{wifi_box_code}/{sdid}")
    Observable<TemperatureMessage> getTemperatureInfo(@Header("Authorization") String str, @Header("APPSLUG") String str2, @Path("wifi_box_code") String str3, @Path("sdid") String str4);

    @GET("api/current_user")
    Observable<UserResponse> getUserInfo(@Header("Authorization") String str);

    @FormUrlEncoded
    @PUT("api/current_user")
    Observable<String> reName(@Header("Authorization") String str, @FieldMap Map<String, String> map);

    @PUT("api/current_user")
    @Multipart
    Observable<String> saveAvatar(@Header("Authorization") String str, @Part MultipartBody.Part part);

    @GET
    Observable<DeviceBean> scanQrCode(@Url String str, @Header("Authorization") String str2, @Header("Content-Type") String str3);

    @FormUrlEncoded
    @POST
    Observable<String> testPost(@Url String str, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @PUT
    Observable<String> testPut(@Url String str, @Header("Authorization") String str2, @FieldMap Map<String, String> map);
}
