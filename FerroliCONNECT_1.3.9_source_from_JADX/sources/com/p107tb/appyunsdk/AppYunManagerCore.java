package com.p107tb.appyunsdk;

import android.content.Context;
import com.p107tb.appyunsdk.YunService;
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
import com.p107tb.appyunsdk.listener.AppYunFunctionInterface;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.p107tb.appyunsdk.listener.IAppYunManagerListener;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.p107tb.appyunsdk.listener.IMqttFunctionListener;
import com.p107tb.appyunsdk.listener.IYunManagerLoginListener;
import com.p107tb.appyunsdk.listener.MqttFunctionInterface;
import com.taobao.agoo.p105a.p106a.C2126c;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* renamed from: com.tb.appyunsdk.AppYunManagerCore */
class AppYunManagerCore implements AppYunFunctionInterface {
    private static volatile AppYunManagerCore instance;
    /* access modifiers changed from: private */
    public IAppYunManagerListener appYunManagerListener;
    /* access modifiers changed from: private */
    public MqttFunctionInterface mqttService = MqttService.getInstance();
    /* access modifiers changed from: private */
    public IYunManagerLoginListener yunManagerLoginListener;
    private YunService yunService;

    public static AppYunManagerCore getInstance(Context context, String str, String str2) {
        if (instance == null) {
            synchronized (AppYunManagerCore.class) {
                if (instance == null) {
                    instance = new AppYunManagerCore(context, str, str2);
                }
            }
        }
        return instance;
    }

    public void setUrl(String str) {
        YunService yunService2 = this.yunService;
        if (yunService2 != null) {
            yunService2.setUrl(str);
        }
    }

    public void setAppSlug(String str) {
        YunService yunService2 = this.yunService;
        if (yunService2 != null) {
            yunService2.setAppSlug(str);
        }
    }

    private AppYunManagerCore(final Context context, String str, String str2) {
        this.yunService = YunService.getInstance(str, str2);
        this.yunService.setYunServiceInitListener(new YunService.IYunServiceInitListener() {
            public void mqttInitCompleted(MqttConfigResponse mqttConfigResponse, MqttTokenResponse mqttTokenResponse) {
                AppYunManagerCore.this.mqttService.initMqtt(context.getApplicationContext(), mqttConfigResponse, mqttTokenResponse.getUser_slug(), mqttTokenResponse.getApp_code(), mqttTokenResponse.getToken());
            }

            public void mqttInitFailure(int i, String str) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.yunPrepareFailure(i, str);
                }
            }

            public void userInitCompleted(String str, String str2, UserResponse userResponse) {
                if (AppYunManagerCore.this.yunManagerLoginListener != null) {
                    AppYunManagerCore.this.yunManagerLoginListener.userLoginSuccess(str2, userResponse);
                }
            }

            public void userInitFailure(int i, String str) {
                if (AppYunManagerCore.this.yunManagerLoginListener != null) {
                    AppYunManagerCore.this.yunManagerLoginListener.userLoginFailure(i, str);
                }
            }
        });
        this.mqttService.setListener(new IMqttFunctionListener() {
            public void connectionLost(Throwable th) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.yunConnectLost(th);
                }
            }

            public void connectSuccess(IMqttToken iMqttToken) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.yunConnectSuccess(iMqttToken);
                }
            }

            public void connectFailed(IMqttToken iMqttToken, Throwable th) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.yunConnectFailed(iMqttToken, th);
                }
            }

            public void serverResponse(MqttMessage mqttMessage) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.serverResponse(mqttMessage);
                }
            }

            public void serverToAppNotify(MqttMessage mqttMessage) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.serverToAppNotify(mqttMessage);
                }
            }

            public void serverToAppNotifyAll(MqttMessage mqttMessage) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.serverToAppNotifyAll(mqttMessage);
                }
            }

            public void deviceToAppMsg(String str, String str2, MqttMessage mqttMessage) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.deviceToAppMsg(str, str2, mqttMessage);
                }
            }

            public void deviceStatus(String str, String str2, MqttMessage mqttMessage) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.deviceStatus(str, str2, mqttMessage);
                }
            }

            public void therListUpdate(String str, String str2, MqttMessage mqttMessage) {
                if (AppYunManagerCore.this.appYunManagerListener != null) {
                    AppYunManagerCore.this.appYunManagerListener.therListUpdate(str, str2, mqttMessage);
                }
            }
        });
    }

    public void release() {
        YunService yunService2 = this.yunService;
        if (yunService2 != null) {
            yunService2.setYunServiceInitListener((YunService.IYunServiceInitListener) null);
            this.yunService = null;
        }
        MqttFunctionInterface mqttFunctionInterface = this.mqttService;
        if (mqttFunctionInterface != null) {
            mqttFunctionInterface.setListener((IMqttFunctionListener) null);
        }
        this.appYunManagerListener = null;
    }

    public void login(String str, String str2, IYunManagerLoginListener iYunManagerLoginListener) {
        this.yunManagerLoginListener = iYunManagerLoginListener;
        this.yunService.login(str, str2);
    }

    public void loginByToken(String str, IYunManagerLoginListener iYunManagerLoginListener) {
        this.yunManagerLoginListener = iYunManagerLoginListener;
        this.yunService.loginByToken(str);
    }

    public boolean isConnected() {
        return this.mqttService.isMqttConnected();
    }

    public void disconnect() {
        this.mqttService.mqttDisconnect();
    }

    public void setYunManagerListener(IAppYunManagerListener iAppYunManagerListener) {
        this.appYunManagerListener = iAppYunManagerListener;
    }

    public void subscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.subscribeMsgFromServer(iAppYunManagerActionListener);
    }

    public void subscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.subscribeMsgFromServerNotify(iAppYunManagerActionListener);
    }

    public void subscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.subscribeMsgFromServerNotifyAll(iAppYunManagerActionListener);
    }

    public void subscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.subscribeDeviceMsg(str, str2, iAppYunManagerActionListener);
    }

    public void subscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.subscribeDeviceStatus(str, str2, iAppYunManagerActionListener);
    }

    public void subscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.subscribeThermostatListUpdate(str, str2, iAppYunManagerActionListener);
    }

    public void unsubscribeMsgFromServer(IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.unsubscribeMsgFromServer(iAppYunManagerActionListener);
    }

    public void unsubscribeMsgFromServerNotify(IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.unsubscribeMsgFromServerNotify(iAppYunManagerActionListener);
    }

    public void unsubscribeMsgFromServerNotifyAll(IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.unsubscribeMsgFromServerNotifyAll(iAppYunManagerActionListener);
    }

    public void unsubscribeDeviceMsg(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.unsubscribeDeviceMsg(str, str2, iAppYunManagerActionListener);
    }

    public void unsubscribeDeviceStatus(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.unsubscribeDeviceStatus(str, str2, iAppYunManagerActionListener);
    }

    public void unsubscribeThermostatListUpdate(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.unsubscribeThermostatListUpdate(str, str2, iAppYunManagerActionListener);
    }

    public void sendMsgToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.sendMsgToServer(bArr, iAppYunManagerActionListener);
    }

    public void sendMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.sendMsgToDevice(str, str2, bArr, iAppYunManagerActionListener);
    }

    public void getDeviceList(String str, int i, int i2, IAppYunResponseListener<DeviceListResponse> iAppYunResponseListener) {
        this.yunService.getDeviceList(str, i, i2, iAppYunResponseListener);
    }

    public void verifyHttpToken(String str, IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        this.yunService.verifyHttpToken(str, iAppYunResponseListener);
    }

    public void updateHttpToken(String str, IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        this.yunService.updateHttpToken(str, iAppYunResponseListener);
    }

    public void bindDevice(String str, String str2, IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener) {
        this.yunService.bindDevice(str, str2, iAppYunResponseListener);
    }

    public void bindDeviceNew(String str, String str2, int i, IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener) {
        this.yunService.bindDeviceNew(str, str2, i, iAppYunResponseListener);
    }

    public void modifyDeviceName(String str, String str2, String str3, String str4, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        this.yunService.modifyDeviceName(str, str2, str3, str4, iAppYunResponseListener);
    }

    public void unBindDevice(String str, String str2, String str3, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        this.yunService.unBindDevice(str, str2, str3, iAppYunResponseListener);
    }

    public void modifyDeviceOwner(String str, String str2, String str3, String str4, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        this.yunService.modifyDeviceOwner(str, str2, str3, str4, iAppYunResponseListener);
    }

    public void sendCodeForRisgister(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        this.yunService.sendCode(str, C2126c.JSON_CMD_REGISTER, iAppYunResponseListener);
    }

    public void sendCodeForPwd(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        this.yunService.sendCode(str, "reset_secret", iAppYunResponseListener);
    }

    public void sendCodeForPhone(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        this.yunService.sendCode(str, "reset_phone", iAppYunResponseListener);
    }

    public void sendCodeForBind(String str, IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        this.yunService.sendCode(str, "add_phone", iAppYunResponseListener);
    }

    public void regitsterByCode(String str, String str2, String str3, IAppYunResponseListener<RegisterResponse> iAppYunResponseListener) {
        this.yunService.regitsterByCode(str, str2, str3, iAppYunResponseListener);
    }

    public void modifyPasswordByCode(String str, String str2, String str3, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunService.modifyPasswordByCode(str, str2, str3, iAppYunResponseListener);
    }

    public void modifyPhoneByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunService.modifyPhoneByCode(str, str2, iAppYunResponseListener);
    }

    public void bindPhoneByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunService.bindPhoneByCode(str, str2, iAppYunResponseListener);
    }

    public String getAuthorization() {
        return this.yunService.getAuthorization();
    }

    public void pushLogToServer(byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.pushLogToServer(bArr, iAppYunManagerActionListener);
    }

    public void modifyEmailByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunService.modifyEmailByCode(str, str2, iAppYunResponseListener);
    }

    public void sendCodeToEmail(String str, String str2, IAppYunResponseListener<EmailCodeResponse> iAppYunResponseListener) {
        this.yunService.sendCodeToEmail(str, str2, iAppYunResponseListener);
    }

    public void bindEmailByCode(String str, String str2, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunService.bindEmailByCode(str, str2, iAppYunResponseListener);
    }

    public void sendJsonMsgToDevice(String str, String str2, byte[] bArr, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.sendJsonMsgToDevice(str, str2, bArr, iAppYunManagerActionListener);
    }

    public void modifyPasswordByEmail(String str, String str2, String str3, IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        this.yunService.modifyPasswordByEmail(str, str2, str3, iAppYunResponseListener);
    }

    public void modifyChildDeviceName(String str, String str2, String str3, IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        this.yunService.modifyChildDeviceName(str, str2, str3, iAppYunResponseListener);
    }

    public void regitsterByEmail(String str, String str2, String str3, String str4, IAppYunResponseListener<EmailRegisterResponse> iAppYunResponseListener) {
        this.yunService.regitsterByEmail(str, str2, str3, str4, iAppYunResponseListener);
    }

    public void getWeather(String str, String str2, String str3, IAppYunResponseListener<XZWeatherResponse> iAppYunResponseListener) {
        this.yunService.getWeather(str, str2, str3, iAppYunResponseListener);
    }

    public void submitGtClientId(String str, IAppYunResponseListener<SubmitGtClientIdResponse> iAppYunResponseListener) {
        this.yunService.submitGtClientId(str, iAppYunResponseListener);
    }

    public void getUserInfo(IAppYunResponseListener<UserResponse> iAppYunResponseListener) {
        this.yunService.getUserInfo(iAppYunResponseListener);
    }

    public void updateUserInfo(String str, IAppYunResponseListener<UpdateUserInfoResponse> iAppYunResponseListener) {
        this.yunService.updateUserInfo(str, iAppYunResponseListener);
    }

    public void checkVersion(String str, String str2, String str3, IAppYunResponseListener<CheckVersionResponse> iAppYunResponseListener) {
        this.yunService.checkVersion(str, str2, str3, iAppYunResponseListener);
    }

    public void getJuheCityList(int i, int i2, IAppYunResponseListener<LocationResponse> iAppYunResponseListener) {
        this.yunService.getJuheCityList(i, i2, iAppYunResponseListener);
    }

    public void getJuheGEOWeather(String str, String str2, IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener) {
        this.yunService.getJuheGEOWeather(str, str2, iAppYunResponseListener);
    }

    public void getJuheCityWeather(String str, IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener) {
        this.yunService.getJuheCityWeather(str, iAppYunResponseListener);
    }

    public void setAutoInitMqtt(boolean z) {
        this.yunService.setAutoInitMqtt(z);
    }

    public void initMqtt() {
        this.yunService.initMqtt();
    }

    public boolean isUseSsl() {
        return this.mqttService.isUseSsl();
    }

    public void setUseSsl(boolean z) {
        this.mqttService.setUseSsl(z);
    }

    public void setErrorMsgLanguage(String str) {
        this.yunService.setErrorMsgLanguage(str);
    }

    public void subscribeApp2deviceJson(String str, String str2, IAppYunManagerActionListener iAppYunManagerActionListener) {
        this.mqttService.subscribeApp2deviceJson(str, str2, iAppYunManagerActionListener);
    }

    public void setForeground(boolean z) {
        this.mqttService.setForeground(z);
    }

    public void getDevice(String str, String str2, int i, int i2, IAppYunResponseListener<DeviceListResponse.ResultsBean> iAppYunResponseListener) {
        this.yunService.getDevice(str, str2, i, i2, iAppYunResponseListener);
    }

    public void setMqttClientId(String str) {
        this.mqttService.setClientId(str);
    }

    public String getMqttClientId() {
        return this.mqttService.getClientId();
    }
}
