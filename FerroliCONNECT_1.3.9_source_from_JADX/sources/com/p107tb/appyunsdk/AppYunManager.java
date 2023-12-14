package com.p107tb.appyunsdk;

import android.content.Context;
import android.text.TextUtils;
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
import com.p107tb.appyunsdk.listener.AppYunFunctionInterface;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.p107tb.appyunsdk.listener.IAppYunManagerListener;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.p107tb.appyunsdk.listener.IYunManagerLoginListener;
import com.p107tb.appyunsdk.network.AppYunApi;
import com.p107tb.appyunsdk.utils.YunLog;
import com.p107tb.appyunsdk.utils.YunUtils;

/* renamed from: com.tb.appyunsdk.AppYunManager */
public class AppYunManager implements AppYunFunctionInterface {
    private static volatile AppYunManager instance;
    private AppYunManagerCore appYunManagerCore;

    public static AppYunManager getInstance() {
        if (instance == null) {
            synchronized (AppYunManager.class) {
                if (instance == null) {
                    instance = new AppYunManager();
                }
            }
        }
        return instance;
    }

    public AppYunManager openDebug(boolean z) {
        YunLog.DEBUG = z;
        return this;
    }

    public AppYunManager init(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("缺少初始化配置参数,请检查!");
        } else if (YunUtils.checkUrl(str)) {
            this.appYunManagerCore = AppYunManagerCore.getInstance(context, str, str2);
            return this;
        } else {
            throw new IllegalArgumentException("url非法");
        }
    }

    public AppYunManager init(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("缺少初始化配置参数,请检查!");
        }
        this.appYunManagerCore = AppYunManagerCore.getInstance(context, AppYunApi.BASE_URL, str);
        return this;
    }

    public void release() {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.release();
            this.appYunManagerCore = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    public void login(String str, String str2, IYunManagerLoginListener iYunManagerLoginListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.login(str, str2, iYunManagerLoginListener);
        }
    }

    public void loginByToken(String str, IYunManagerLoginListener iYunManagerLoginListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.loginByToken(str, iYunManagerLoginListener);
        }
    }

    public boolean isConnected() {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        return appYunManagerCore2 != null && appYunManagerCore2.isConnected();
    }

    public void disconnect() {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.disconnect();
        }
    }

    public void setYunManagerListener(IAppYunManagerListener iAppYunManagerListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.setYunManagerListener(iAppYunManagerListener);
        }
    }

    public void subscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.subscribeMsgFromServer(iAppYunManagerActionListener);
        }
    }

    public void subscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.subscribeMsgFromServerNotify(iAppYunManagerActionListener);
        }
    }

    public void subscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.subscribeMsgFromServerNotifyAll(iAppYunManagerActionListener);
        }
    }

    public void subscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.subscribeDeviceMsg(str, str2, iAppYunManagerActionListener);
        }
    }

    public void subscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.subscribeDeviceStatus(str, str2, iAppYunManagerActionListener);
        }
    }

    public void subscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.subscribeThermostatListUpdate(str, str2, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.unsubscribeMsgFromServer(iAppYunManagerActionListener);
        }
    }

    public void unsubscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.unsubscribeMsgFromServerNotify(iAppYunManagerActionListener);
        }
    }

    public void unsubscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.unsubscribeMsgFromServerNotifyAll(iAppYunManagerActionListener);
        }
    }

    public void unsubscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.unsubscribeDeviceMsg(str, str2, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.unsubscribeDeviceStatus(str, str2, iAppYunManagerActionListener);
        }
    }

    public void unsubscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.unsubscribeThermostatListUpdate(str, str2, iAppYunManagerActionListener);
        }
    }

    public void sendMsgToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.sendMsgToServer(bArr, iAppYunManagerActionListener);
        }
    }

    public void sendMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.sendMsgToDevice(str, str2, bArr, iAppYunManagerActionListener);
        }
    }

    public void getDeviceList(String str, int i, int i2, IAppYunResponseListener<DeviceListResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.getDeviceList(str, i, i2, iAppYunResponseListener);
        }
    }

    public void verifyHttpToken(String str, IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.verifyHttpToken(str, iAppYunResponseListener);
        }
    }

    public void updateHttpToken(String str, IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.updateHttpToken(str, iAppYunResponseListener);
        }
    }

    public void bindDevice(String str, String str2, IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.bindDevice(str, str2, iAppYunResponseListener);
        }
    }

    public void bindDeviceNew(String str, String str2, int i, IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.bindDeviceNew(str, str2, i, iAppYunResponseListener);
        }
    }

    public void modifyDeviceName(String str, String str2, String str3, String str4, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.modifyDeviceName(str, str2, str3, str4, iAppYunResponseListener);
        }
    }

    public void unBindDevice(String str, String str2, String str3, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.unBindDevice(str, str2, str3, iAppYunResponseListener);
        }
    }

    public void modifyDeviceOwner(String str, String str2, String str3, String str4, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        if (this.appYunManagerCore != null) {
            modifyDeviceOwner(str, str2, str3, str4, iAppYunResponseListener);
        }
    }

    public void sendCodeForRisgister(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.sendCodeForRisgister(str, iAppYunResponseListener);
        }
    }

    public void sendCodeForPwd(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.sendCodeForPwd(str, iAppYunResponseListener);
        }
    }

    public void sendCodeForPhone(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.sendCodeForPhone(str, iAppYunResponseListener);
        }
    }

    public void sendCodeForBind(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.sendCodeForBind(str, iAppYunResponseListener);
        }
    }

    public void regitsterByCode(String str, String str2, String str3, IAppYunResponseListener<RegisterResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.regitsterByCode(str, str2, str3, iAppYunResponseListener);
        }
    }

    public void modifyPasswordByCode(String str, String str2, String str3, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.modifyPasswordByCode(str, str2, str3, iAppYunResponseListener);
        }
    }

    public void modifyPhoneByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.modifyPhoneByCode(str, str2, iAppYunResponseListener);
        }
    }

    public void bindPhoneByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.bindPhoneByCode(str, str2, iAppYunResponseListener);
        }
    }

    public String getAuthorization() {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        return appYunManagerCore2 != null ? appYunManagerCore2.getAuthorization() : "";
    }

    public void pushLogToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.pushLogToServer(bArr, iAppYunManagerActionListener);
        }
    }

    public void modifyEmailByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.modifyEmailByCode(str, str2, iAppYunResponseListener);
        }
    }

    public void sendCodeToEmail(String str, String str2, IAppYunResponseListener<EmailCodeResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.sendCodeToEmail(str, str2, iAppYunResponseListener);
        }
    }

    public void bindEmailByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.bindEmailByCode(str, str2, iAppYunResponseListener);
        }
    }

    public void sendJsonMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.sendJsonMsgToDevice(str, str2, bArr, iAppYunManagerActionListener);
        }
    }

    public void modifyPasswordByEmail(String str, String str2, String str3, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.modifyPasswordByEmail(str, str2, str3, iAppYunResponseListener);
        }
    }

    public void modifyChildDeviceName(String str, String str2, String str3, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.modifyChildDeviceName(str, str2, str3, iAppYunResponseListener);
        }
    }

    public void regitsterByEmail(String str, String str2, String str3, String str4, IAppYunResponseListener<EmailRegisterResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.regitsterByEmail(str, str2, str3, str4, iAppYunResponseListener);
        }
    }

    public void getWeather(String str, String str2, String str3, IAppYunResponseListener<XZWeatherResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.getWeather(str, str2, str3, iAppYunResponseListener);
        }
    }

    public void submitGtClientId(String str, IAppYunResponseListener<SubmitGtClientIdResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.submitGtClientId(str, iAppYunResponseListener);
        }
    }

    public void getUserInfo(IAppYunResponseListener<UserResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.getUserInfo(iAppYunResponseListener);
        }
    }

    public void updateUserInfo(String str, IAppYunResponseListener<UpdateUserInfoResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.updateUserInfo(str, iAppYunResponseListener);
        }
    }

    public void checkVersion(String str, String str2, String str3, IAppYunResponseListener<CheckVersionResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.checkVersion(str, str2, str3, iAppYunResponseListener);
        }
    }

    public void getJuheCityList(int i, int i2, IAppYunResponseListener<LocationResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.getJuheCityList(i, i2, iAppYunResponseListener);
        }
    }

    public void getJuheGEOWeather(String str, String str2, IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.getJuheGEOWeather(str, str2, iAppYunResponseListener);
        }
    }

    public void getJuheCityWeather(String str, IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.getJuheCityWeather(str, iAppYunResponseListener);
        }
    }

    public void setAutoInitMqtt(boolean z) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.setAutoInitMqtt(z);
        }
    }

    public void initMqtt() {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.initMqtt();
        }
    }

    public boolean isUseSsl() {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            return appYunManagerCore2.isUseSsl();
        }
        return false;
    }

    public void setUseSsl(boolean z) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.setUseSsl(z);
        }
    }

    public void setErrorMsgLanguage(String str) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.setErrorMsgLanguage(str);
        }
    }

    public void subscribeApp2deviceJson(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.subscribeApp2deviceJson(str, str2, iAppYunManagerActionListener);
        }
    }

    public void setForeground(boolean z) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.setForeground(z);
        }
    }

    public void getDevice(String str, String str2, int i, int i2, IAppYunResponseListener<DeviceListResponse.ResultsBean> iAppYunResponseListener) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.getDevice(str, str2, i, i2, iAppYunResponseListener);
        }
    }

    public void setUrl(String str) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.setUrl(str);
        }
    }

    public void setAppSlug(String str) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.setAppSlug(str);
        }
    }

    public void setMqttClientId(String str) {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        if (appYunManagerCore2 != null) {
            appYunManagerCore2.setMqttClientId(str);
        }
    }

    public String getMqttClientId() {
        AppYunManagerCore appYunManagerCore2 = this.appYunManagerCore;
        return appYunManagerCore2 != null ? appYunManagerCore2.getMqttClientId() : "";
    }
}
