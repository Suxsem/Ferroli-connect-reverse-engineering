package com.p107tb.appyunsdk.network;

import com.p107tb.appyunsdk.bean.BindDeviceResponse;
import com.p107tb.appyunsdk.bean.CheckVersionResponse;
import com.p107tb.appyunsdk.bean.CodeResponse;
import com.p107tb.appyunsdk.bean.DeviceListResponse;
import com.p107tb.appyunsdk.bean.EmailCodeResponse;
import com.p107tb.appyunsdk.bean.EmailRegisterResponse;
import com.p107tb.appyunsdk.bean.HttpTokenResponse;
import com.p107tb.appyunsdk.bean.JuHeWeatherResponse;
import com.p107tb.appyunsdk.bean.LocationResponse;
import com.p107tb.appyunsdk.bean.ModifyDeviceMsgResponse;
import com.p107tb.appyunsdk.bean.ModifyResponse;
import com.p107tb.appyunsdk.bean.MqttConfigResponse;
import com.p107tb.appyunsdk.bean.MqttTokenResponse;
import com.p107tb.appyunsdk.bean.RegisterResponse;
import com.p107tb.appyunsdk.bean.SubmitGtClientIdResponse;
import com.p107tb.appyunsdk.bean.UpdateUserInfoResponse;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.p107tb.appyunsdk.bean.XZWeatherResponse;
import java.util.Map;
import p110io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/* renamed from: com.tb.appyunsdk.network.AppYunService */
public interface AppYunService {
    @FormUrlEncoded
    @POST
    Observable<BindDeviceResponse> bindDevice(@Url String str, @Header("APPSLUG") String str2, @Header("Authorization") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<BindDeviceResponse> bindDevice2(@Url String str, @Header("APPSLUG") String str2, @Header("Authorization") String str3, @FieldMap Map<String, Object> map);

    @GET
    Observable<CheckVersionResponse> checkVersion(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @PATCH
    Observable<ModifyResponse> emailModifyPwd(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<EmailRegisterResponse> emailResgister(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @GET
    Observable<DeviceListResponse.ResultsBean> getDevice(@Url String str, @Header("APPSLUG") String str2, @Header("Authorization") String str3, @QueryMap Map<String, String> map);

    @GET
    Observable<DeviceListResponse> getDeviceList(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<HttpTokenResponse> getHttpToken(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @GET
    Observable<JuHeWeatherResponse> getJuHeCityWeather(@Url String str, @Header("APPSLUG") String str2, @QueryMap Map<String, String> map);

    @GET
    Observable<JuHeWeatherResponse> getJuHeGEOWeather(@Url String str, @Header("APPSLUG") String str2, @QueryMap Map<String, String> map);

    @GET
    Observable<LocationResponse> getJuheCityList(@Url String str, @Header("APPSLUG") String str2, @QueryMap Map<String, Integer> map);

    @GET
    Observable<MqttConfigResponse> getMqttConfig(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @QueryMap Map<String, String> map);

    @GET
    Observable<MqttTokenResponse> getMqttToken(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @QueryMap Map<String, String> map);

    @GET
    Observable<UserResponse> getUserMsg(@Url String str, @Header("APPSLUG") String str2, @Header("Authorization") String str3);

    @GET
    Observable<XZWeatherResponse> getXZWeather(@Url String str, @Header("APPSLUG") String str2, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @PUT
    Observable<ModifyDeviceMsgResponse> modifyChildDeviceMsg(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @PUT
    Observable<ModifyDeviceMsgResponse> modifyDeviceMsg(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<CodeResponse> sendVerifyCode(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<EmailCodeResponse> sendVerifyCodeToEmail(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<SubmitGtClientIdResponse> submitGtClientId(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<HttpTokenResponse> updateHttpToken(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @PUT
    Observable<UpdateUserInfoResponse> updateUserInfo(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<ModifyResponse> verifyCodeAddEmail(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<ModifyResponse> verifyCodeAddPhone(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @PATCH
    Observable<ModifyResponse> verifyCodeModifyEmail(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<ModifyResponse> verifyCodeModifyPhone(@Url String str, @Header("Authorization") String str2, @Header("APPSLUG") String str3, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<ModifyResponse> verifyCodeModifyPwd(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<RegisterResponse> verifyCodeResgister(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<HttpTokenResponse> verifyHttpToken(@Url String str, @Header("APPSLUG") String str2, @FieldMap Map<String, String> map);
}
