package com.p107tb.appyunsdk;

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
import com.p107tb.appyunsdk.bean.MqttConfigResponse;
import com.p107tb.appyunsdk.bean.MqttTokenResponse;
import com.p107tb.appyunsdk.bean.RegisterResponse;
import com.p107tb.appyunsdk.bean.SubmitGtClientIdResponse;
import com.p107tb.appyunsdk.bean.UpdateUserInfoResponse;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.p107tb.appyunsdk.bean.XZWeatherResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.p107tb.appyunsdk.utils.YunUtils;
import p110io.reactivex.disposables.Disposable;

/* renamed from: com.tb.appyunsdk.YunService */
class YunService {
    private static volatile YunService instance;
    /* access modifiers changed from: private */
    public String app_slug;
    /* access modifiers changed from: private */
    public String authorization;
    private boolean autoInitMqtt = true;
    /* access modifiers changed from: private */
    public String httpToken;
    private Disposable httpTokenDisposable;
    /* access modifiers changed from: private */
    public IYunServiceInitListener iYunServiceInitListener;
    /* access modifiers changed from: private */
    public Disposable mqttConfigDisposable;
    private Disposable mqttTokenDisposable;
    /* access modifiers changed from: private */
    public String url;
    private Disposable userMsgDisposable;
    /* access modifiers changed from: private */
    public final YunModel yunModel;

    /* renamed from: com.tb.appyunsdk.YunService$IYunServiceInitListener */
    public interface IYunServiceInitListener {
        void mqttInitCompleted(MqttConfigResponse mqttConfigResponse, MqttTokenResponse mqttTokenResponse);

        void mqttInitFailure(int i, String str);

        void userInitCompleted(String str, String str2, UserResponse userResponse);

        void userInitFailure(int i, String str);
    }

    public static YunService getInstance(String str, String str2) {
        if (instance == null) {
            synchronized (YunService.class) {
                if (instance == null) {
                    instance = new YunService(str, str2);
                }
            }
        }
        return instance;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setAppSlug(String str) {
        this.app_slug = str;
    }

    private YunService(String str, String str2) {
        this.url = str;
        this.app_slug = str2;
        this.yunModel = YunModel.getInstance();
    }

    public void setYunServiceInitListener(IYunServiceInitListener iYunServiceInitListener2) {
        this.iYunServiceInitListener = iYunServiceInitListener2;
    }

    public void verifyHttpToken(String str, IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        this.yunModel.verifyHttpToken(this.app_slug, this.url, str, iAppYunResponseListener);
    }

    public void updateHttpToken(String str, IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        this.yunModel.updateHttpToken(this.app_slug, this.url, str, iAppYunResponseListener);
    }

    public void bindDevice(String str, String str2, IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener) {
        this.yunModel.bindDevice(this.url, this.authorization, this.app_slug, str, str2, iAppYunResponseListener);
    }

    public void bindDeviceNew(String str, String str2, int i, IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener) {
        this.yunModel.bindDeviceNew(this.url, this.authorization, this.app_slug, str, str2, i, iAppYunResponseListener);
    }

    public void getDeviceList(String str, int i, int i2, IAppYunResponseListener<DeviceListResponse> iAppYunResponseListener) {
        this.yunModel.getDeviceList(this.url, this.authorization, this.app_slug, str, i, i2, iAppYunResponseListener);
    }

    public void modifyDeviceName(String str, String str2, String str3, String str4, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        this.yunModel.modifyDeviceName(this.url, this.authorization, str, str2, str3, str4, iAppYunResponseListener);
    }

    public void unBindDevice(String str, String str2, String str3, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        this.yunModel.unBindDevice(this.url, this.authorization, str, str2, str3, iAppYunResponseListener);
    }

    public void modifyDeviceOwner(String str, String str2, String str3, String str4, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        this.yunModel.modifyDeviceOwner(this.url, this.authorization, str, str2, str3, str4, iAppYunResponseListener);
    }

    public void regitsterByCode(String str, String str2, String str3, IAppYunResponseListener<RegisterResponse> iAppYunResponseListener) {
        this.yunModel.regitsterByCode(this.url, this.app_slug, str, str2, str3, iAppYunResponseListener);
    }

    public void modifyPhoneByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunModel.modifyPhoneByCode(this.url, str, str2, this.authorization, this.app_slug, iAppYunResponseListener);
    }

    public void modifyPasswordByCode(String str, String str2, String str3, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunModel.modifyPasswordByCode(this.url, this.app_slug, str, str2, str3, iAppYunResponseListener);
    }

    public void modifyPasswordByEmail(String str, String str2, String str3, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunModel.modifyPasswordByEmail(this.url, this.app_slug, str, str2, str3, iAppYunResponseListener);
    }

    public void modifyEmailByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunModel.modifyEmailByCode(this.url, str, str2, this.authorization, this.app_slug, iAppYunResponseListener);
    }

    public void modifyChildDeviceName(String str, String str2, String str3, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        this.yunModel.modifyChildDeviceName(this.url, this.authorization, this.app_slug, str, str2, str3, iAppYunResponseListener);
    }

    public void bindPhoneByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunModel.bindPhoneByCode(this.url, str, str2, this.app_slug, iAppYunResponseListener);
    }

    public void sendCode(String str, String str2, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        this.yunModel.sendCode(this.url, this.app_slug, str, str2, iAppYunResponseListener);
    }

    public void sendCodeToEmail(String str, String str2, IAppYunResponseListener<EmailCodeResponse> iAppYunResponseListener) {
        this.yunModel.sendCodeToEmail(this.url, this.app_slug, str, str2, iAppYunResponseListener);
    }

    public void bindEmailByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunModel.bindEmailByCode(this.url, str, str2, this.authorization, this.app_slug, iAppYunResponseListener);
    }

    public void regitsterByEmail(String str, String str2, String str3, String str4, IAppYunResponseListener<EmailRegisterResponse> iAppYunResponseListener) {
        this.yunModel.regitsterByEmail(this.url, this.app_slug, str, str2, str3, str4, iAppYunResponseListener);
    }

    public void getWeather(String str, String str2, String str3, IAppYunResponseListener<XZWeatherResponse> iAppYunResponseListener) {
        this.yunModel.getWeather(this.url, this.app_slug, str, str2, str3, iAppYunResponseListener);
    }

    public void submitGtClientId(String str, IAppYunResponseListener<SubmitGtClientIdResponse> iAppYunResponseListener) {
        this.yunModel.submitGtClientId(this.url, this.authorization, this.app_slug, str, iAppYunResponseListener);
    }

    public void getUserInfo(IAppYunResponseListener<UserResponse> iAppYunResponseListener) {
        this.yunModel.getUserMsg(this.app_slug, this.url, this.authorization, iAppYunResponseListener);
    }

    public void updateUserInfo(String str, IAppYunResponseListener<UpdateUserInfoResponse> iAppYunResponseListener) {
        this.yunModel.updateUserInfo(this.url, this.authorization, this.app_slug, str, iAppYunResponseListener);
    }

    public void checkVersion(String str, String str2, String str3, IAppYunResponseListener<CheckVersionResponse> iAppYunResponseListener) {
        this.yunModel.checkVersion(this.url, this.authorization, str, str2, str3, this.app_slug, iAppYunResponseListener);
    }

    public void getJuheCityList(int i, int i2, IAppYunResponseListener<LocationResponse> iAppYunResponseListener) {
        this.yunModel.getJuheCityList(this.url, this.app_slug, i, i2, iAppYunResponseListener);
    }

    public void getJuheGEOWeather(String str, String str2, IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener) {
        this.yunModel.getJuheGEOWeather(this.url, this.app_slug, str, str2, iAppYunResponseListener);
    }

    public void getJuheCityWeather(String str, IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener) {
        this.yunModel.getJuheCityWeather(this.url, this.app_slug, str, iAppYunResponseListener);
    }

    public void getDevice(String str, String str2, int i, int i2, IAppYunResponseListener<DeviceListResponse.ResultsBean> iAppYunResponseListener) {
        this.yunModel.getDevice(this.url, this.authorization, this.app_slug, str, str2, i, i2, iAppYunResponseListener);
    }

    public void login(String str, String str2) {
        YunUtils.cancelDisposable(this.httpTokenDisposable);
        this.httpTokenDisposable = this.yunModel.getHttpToken(this.app_slug, this.url, str, str2, new IAppYunResponseListener<HttpTokenResponse>() {
            public void onSuccess(HttpTokenResponse httpTokenResponse) {
                YunService.this.loginByToken(httpTokenResponse.getToken());
            }

            public void onFailure(int i, String str) {
                if (YunService.this.iYunServiceInitListener != null) {
                    YunService.this.iYunServiceInitListener.userInitFailure(i, str);
                }
            }
        });
    }

    public void loginByToken(String str) {
        this.authorization = String.format("JWT %s", new Object[]{str});
        this.httpToken = str;
        initUser();
        if (this.autoInitMqtt) {
            initMqtt();
        }
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public void initMqtt() {
        if (!TextUtils.isEmpty(this.authorization)) {
            YunUtils.cancelDisposable(this.mqttTokenDisposable);
            this.mqttTokenDisposable = this.yunModel.getMqttToken(this.url, this.authorization, this.app_slug, new IAppYunResponseListener<MqttTokenResponse>() {
                public void onSuccess(final MqttTokenResponse mqttTokenResponse) {
                    YunUtils.cancelDisposable(YunService.this.mqttConfigDisposable);
                    YunService yunService = YunService.this;
                    Disposable unused = yunService.mqttConfigDisposable = yunService.yunModel.getMqttConfig(YunService.this.url, YunService.this.authorization, YunService.this.app_slug, new IAppYunResponseListener<MqttConfigResponse>() {
                        public void onSuccess(MqttConfigResponse mqttConfigResponse) {
                            if (YunService.this.iYunServiceInitListener != null) {
                                YunService.this.iYunServiceInitListener.mqttInitCompleted(mqttConfigResponse, mqttTokenResponse);
                            }
                        }

                        public void onFailure(int i, String str) {
                            if (YunService.this.iYunServiceInitListener != null) {
                                YunService.this.iYunServiceInitListener.mqttInitFailure(i, str);
                            }
                        }
                    });
                }

                public void onFailure(int i, String str) {
                    if (YunService.this.iYunServiceInitListener != null) {
                        YunService.this.iYunServiceInitListener.mqttInitFailure(i, str);
                    }
                }
            });
        }
    }

    private void initUser() {
        if (!TextUtils.isEmpty(this.authorization)) {
            YunUtils.cancelDisposable(this.userMsgDisposable);
            this.userMsgDisposable = this.yunModel.getUserMsg(this.app_slug, this.url, this.authorization, new IAppYunResponseListener<UserResponse>() {
                public void onSuccess(UserResponse userResponse) {
                    if (YunService.this.iYunServiceInitListener != null) {
                        YunService.this.iYunServiceInitListener.userInitCompleted(YunService.this.authorization, YunService.this.httpToken, userResponse);
                    }
                }

                public void onFailure(int i, String str) {
                    if (YunService.this.iYunServiceInitListener != null) {
                        YunService.this.iYunServiceInitListener.userInitFailure(i, str);
                    }
                }
            });
        }
    }

    public void setErrorMsgLanguage(String str) {
        this.yunModel.setErrorMsgLanguage(str);
    }

    public boolean isAutoInitMqtt() {
        return this.autoInitMqtt;
    }

    public void setAutoInitMqtt(boolean z) {
        this.autoInitMqtt = z;
    }
}
