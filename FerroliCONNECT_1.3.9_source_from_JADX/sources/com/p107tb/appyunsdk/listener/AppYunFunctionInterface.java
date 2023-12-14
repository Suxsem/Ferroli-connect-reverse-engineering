package com.p107tb.appyunsdk.listener;

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
import com.p107tb.appyunsdk.bean.RegisterResponse;
import com.p107tb.appyunsdk.bean.SubmitGtClientIdResponse;
import com.p107tb.appyunsdk.bean.UpdateUserInfoResponse;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.p107tb.appyunsdk.bean.XZWeatherResponse;

/* renamed from: com.tb.appyunsdk.listener.AppYunFunctionInterface */
public interface AppYunFunctionInterface {
    void bindDevice(String str, String str2, IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener);

    void bindDeviceNew(String str, String str2, int i, IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener);

    void bindEmailByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener);

    void bindPhoneByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener);

    void checkVersion(String str, String str2, String str3, IAppYunResponseListener<CheckVersionResponse> iAppYunResponseListener);

    void disconnect();

    String getAuthorization();

    void getDevice(String str, String str2, int i, int i2, IAppYunResponseListener<DeviceListResponse.ResultsBean> iAppYunResponseListener);

    void getDeviceList(String str, int i, int i2, IAppYunResponseListener<DeviceListResponse> iAppYunResponseListener);

    void getJuheCityList(int i, int i2, IAppYunResponseListener<LocationResponse> iAppYunResponseListener);

    void getJuheCityWeather(String str, IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener);

    void getJuheGEOWeather(String str, String str2, IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener);

    void getUserInfo(IAppYunResponseListener<UserResponse> iAppYunResponseListener);

    void getWeather(String str, String str2, String str3, IAppYunResponseListener<XZWeatherResponse> iAppYunResponseListener);

    void initMqtt();

    boolean isConnected();

    boolean isUseSsl();

    void login(String str, String str2, IYunManagerLoginListener iYunManagerLoginListener);

    void loginByToken(String str, IYunManagerLoginListener iYunManagerLoginListener);

    void modifyChildDeviceName(String str, String str2, String str3, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener);

    void modifyDeviceName(String str, String str2, String str3, String str4, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener);

    void modifyDeviceOwner(String str, String str2, String str3, String str4, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener);

    void modifyEmailByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener);

    void modifyPasswordByCode(String str, String str2, String str3, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener);

    void modifyPasswordByEmail(String str, String str2, String str3, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener);

    void modifyPhoneByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener);

    void pushLogToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener);

    void regitsterByCode(String str, String str2, String str3, IAppYunResponseListener<RegisterResponse> iAppYunResponseListener);

    void regitsterByEmail(String str, String str2, String str3, String str4, IAppYunResponseListener<EmailRegisterResponse> iAppYunResponseListener);

    void release();

    void sendCodeForBind(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener);

    void sendCodeForPhone(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener);

    void sendCodeForPwd(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener);

    void sendCodeForRisgister(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener);

    void sendCodeToEmail(String str, String str2, IAppYunResponseListener<EmailCodeResponse> iAppYunResponseListener);

    void sendJsonMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener);

    void sendMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener);

    void sendMsgToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener);

    void setAutoInitMqtt(boolean z);

    void setErrorMsgLanguage(String str);

    void setForeground(boolean z);

    void setUseSsl(boolean z);

    void setYunManagerListener(IAppYunManagerListener iAppYunManagerListener);

    void submitGtClientId(String str, IAppYunResponseListener<SubmitGtClientIdResponse> iAppYunResponseListener);

    void subscribeApp2deviceJson(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener);

    void subscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void unBindDevice(String str, String str2, String str3, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener);

    void unsubscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener);

    void unsubscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener);

    void updateHttpToken(String str, IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener);

    void updateUserInfo(String str, IAppYunResponseListener<UpdateUserInfoResponse> iAppYunResponseListener);

    void verifyHttpToken(String str, IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener);
}
