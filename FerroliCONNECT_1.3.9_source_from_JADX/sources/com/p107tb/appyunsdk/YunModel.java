package com.p107tb.appyunsdk;

import android.util.Log;
import anet.channel.strategy.dispatch.DispatchConstants;
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
import com.p107tb.appyunsdk.exception.ErrorInfoResponse;
import com.p107tb.appyunsdk.exception.ExceptionEngine;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.p107tb.appyunsdk.network.AppYunApi;
import com.p107tb.appyunsdk.network.AppYunService;
import com.p107tb.appyunsdk.network.HttpClient;
import com.taobao.accs.common.Constants;
import com.taobao.agoo.p105a.p106a.C2126c;
import java.util.HashMap;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

/* renamed from: com.tb.appyunsdk.YunModel */
class YunModel {
    private static volatile YunModel instance;
    /* access modifiers changed from: private */
    public ExceptionEngine exceptionEngine = ExceptionEngine.getInstance();

    public static YunModel getInstance() {
        if (instance == null) {
            synchronized (YunModel.class) {
                if (instance == null) {
                    instance = new YunModel();
                }
            }
        }
        return instance;
    }

    private YunModel() {
    }

    public void setErrorMsgLanguage(String str) {
        ExceptionEngine exceptionEngine2 = this.exceptionEngine;
        if (exceptionEngine2 != null) {
            exceptionEngine2.setLanguage(str);
        }
    }

    public Disposable getHttpToken(String str, String str2, String str3, String str4, final IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.APP_USERNAME, str3);
        hashMap.put("password", str4);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.getHttpToken(str2 + AppYunApi.LOGIN, str, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpTokenResponse>() {
            public void accept(HttpTokenResponse httpTokenResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(httpTokenResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable regitsterByEmail(String str, String str2, String str3, String str4, String str5, String str6, final IAppYunResponseListener<EmailRegisterResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.APP_USERNAME, str3);
        hashMap.put("email", str5);
        hashMap.put("code", str6);
        hashMap.put("password", str4);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.emailResgister(str + AppYunApi.EMAIL_REGISTER, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EmailRegisterResponse>() {
            public void accept(EmailRegisterResponse emailRegisterResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(emailRegisterResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                }
            }
        });
    }

    public Disposable verifyHttpToken(String str, String str2, String str3, final IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("token", str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.verifyHttpToken(str2 + AppYunApi.HTTP_TOKE_VERIFICATION, str, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpTokenResponse>() {
            public void accept(HttpTokenResponse httpTokenResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(httpTokenResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable updateHttpToken(String str, String str2, String str3, final IAppYunResponseListener<HttpTokenResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("token", str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.updateHttpToken(str2 + AppYunApi.HTTP_TOKEN_REFASHION, str, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpTokenResponse>() {
            public void accept(HttpTokenResponse httpTokenResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(httpTokenResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable getUserMsg(String str, String str2, String str3, final IAppYunResponseListener<UserResponse> iAppYunResponseListener) {
        AppYunService yunService = HttpClient.getYunService();
        return yunService.getUserMsg(str2 + AppYunApi.GET_USER_MSG, str, str3).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserResponse>() {
            public void accept(UserResponse userResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(userResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable getMqttToken(String str, String str2, String str3, final IAppYunResponseListener<MqttTokenResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.APP_SLUG, str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.getMqttToken(str + AppYunApi.GET_MQTT_TOKEN, str2, str3, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MqttTokenResponse>() {
            public void accept(MqttTokenResponse mqttTokenResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(mqttTokenResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable getMqttConfig(String str, String str2, String str3, final IAppYunResponseListener<MqttConfigResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.APP_SLUG, str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.getMqttConfig(str + AppYunApi.GET_MQTT_CONFIG, str2, str3, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MqttConfigResponse>() {
            public void accept(MqttConfigResponse mqttConfigResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(mqttConfigResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable getDeviceList(String str, String str2, String str3, String str4, int i, int i2, final IAppYunResponseListener<DeviceListResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.PAGE_SIZE, String.valueOf(i));
        hashMap.put(Constant.PAGE, String.valueOf(i2));
        hashMap.put(Constant.APP_SLUG, str3);
        return HttpClient.getYunService().getDeviceList(String.format("%sapi/users/%s/devices", new Object[]{str, str4}), str2, str3, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DeviceListResponse>() {
            public void accept(DeviceListResponse deviceListResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(deviceListResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable getDevice(String str, String str2, String str3, String str4, String str5, int i, int i2, final IAppYunResponseListener<DeviceListResponse.ResultsBean> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.PAGE_SIZE, String.valueOf(i));
        hashMap.put(Constant.PAGE, String.valueOf(i2));
        return HttpClient.getYunService().getDevice(String.format("%sapi/users/%s/devices/%s", new Object[]{str, str5, str4}), str3, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DeviceListResponse.ResultsBean>() {
            public void accept(DeviceListResponse.ResultsBean resultsBean) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(resultsBean);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable bindDevice(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.DEVICE_CODE, str4);
        hashMap.put(Constant.MAC_ADDRESS, str5);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.bindDevice(str + AppYunApi.BINDING_DEVICE, str3, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BindDeviceResponse>() {
            public void accept(BindDeviceResponse bindDeviceResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(bindDeviceResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable bindDeviceNew(String str, String str2, String str3, String str4, String str5, int i, final IAppYunResponseListener<BindDeviceResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.DEVICE_CODE, str4);
        hashMap.put(Constant.MAC_ADDRESS, str5);
        hashMap.put(Constant.TIMEZONE, Integer.valueOf(i));
        AppYunService yunService = HttpClient.getYunService();
        return yunService.bindDevice2(str + AppYunApi.BINDING_DEVICE_NEW, str3, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BindDeviceResponse>() {
            public void accept(BindDeviceResponse bindDeviceResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(bindDeviceResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable modifyDeviceName(String str, String str2, String str3, String str4, String str5, String str6, final IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.NAME, str5);
        return HttpClient.getYunService().modifyDeviceMsg(String.format("%sapi/users/%s/devices/%s", new Object[]{str, str3, str4}), str2, str6, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyDeviceMsgResponse>() {
            public void accept(ModifyDeviceMsgResponse modifyDeviceMsgResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyDeviceMsgResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable unBindDevice(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.OWNER_SLUG, "");
        return HttpClient.getYunService().modifyDeviceMsg(String.format("%sapi/users/%s/devices/%s", new Object[]{str, str3, str4}), str2, str5, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyDeviceMsgResponse>() {
            public void accept(ModifyDeviceMsgResponse modifyDeviceMsgResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyDeviceMsgResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable modifyDeviceOwner(String str, String str2, String str3, String str4, String str5, String str6, final IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.OWNER_SLUG, str5);
        return HttpClient.getYunService().modifyDeviceMsg(String.format("%sapi/users/%s/devices/%s", new Object[]{str, str3, str4}), str2, str6, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyDeviceMsgResponse>() {
            public void accept(ModifyDeviceMsgResponse modifyDeviceMsgResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyDeviceMsgResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable sendCode(String str, String str2, String str3, String str4, final IAppYunResponseListener<CodeResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.PHONE, str3);
        hashMap.put(Constant.CODE_TYPE, str4);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.sendVerifyCode(str + AppYunApi.SEND_VERIFICATION_CODE, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeResponse>() {
            public void accept(CodeResponse codeResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(codeResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable regitsterByCode(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<RegisterResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.PHONE, str3);
        hashMap.put(Constant.CODE_TYPE, C2126c.JSON_CMD_REGISTER);
        hashMap.put("code", str4);
        hashMap.put("password", str5);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.verifyCodeResgister(str + AppYunApi.VERIFICATION_CODE_REGISTER, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RegisterResponse>() {
            public void accept(RegisterResponse registerResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(registerResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable modifyPasswordByCode(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.PHONE, str3);
        hashMap.put(Constant.CODE_TYPE, "reset_secret");
        hashMap.put("code", str4);
        hashMap.put("password", str5);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.verifyCodeModifyPwd(str + AppYunApi.VERIFICATION_CODE_RESET, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyResponse>() {
            public void accept(ModifyResponse modifyResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable modifyPhoneByCode(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.PHONE, str2);
        hashMap.put(Constant.CODE_TYPE, "reset_phone");
        hashMap.put("code", str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.verifyCodeModifyPhone(str + AppYunApi.VERIFICATION_CODE_RESET_PHONE, str4, str5, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyResponse>() {
            public void accept(ModifyResponse modifyResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                }
            }
        });
    }

    public Disposable bindPhoneByCode(String str, String str2, String str3, String str4, final IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.PHONE, str2);
        hashMap.put(Constant.CODE_TYPE, "add_phone");
        hashMap.put("code", str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.verifyCodeAddPhone(str + AppYunApi.VERIFICATION_CODE_BIND_PHONE, str4, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyResponse>() {
            public void accept(ModifyResponse modifyResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                    th.printStackTrace();
                }
            }
        });
    }

    public Disposable modifyEmailByCode(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("email", str2);
        hashMap.put("code", str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.verifyCodeModifyEmail(str + AppYunApi.VERIFICATION_CODE_RESET_EMAIL, str4, str5, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyResponse>() {
            public void accept(ModifyResponse modifyResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                }
            }
        });
    }

    public Disposable sendCodeToEmail(String str, String str2, String str3, String str4, final IAppYunResponseListener<EmailCodeResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("email", str3);
        hashMap.put(Constant.SUBJECT, str4);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.sendVerifyCodeToEmail(str + AppYunApi.SEND_EMAIL_VERIFICATION_CODE, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EmailCodeResponse>() {
            public void accept(EmailCodeResponse emailCodeResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(emailCodeResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.d("sendCodeToEmail", "throwable " + th);
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                }
            }
        });
    }

    public Disposable bindEmailByCode(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("email", str2);
        hashMap.put("code", str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.verifyCodeAddEmail(str + AppYunApi.VERIFICATION_CODE_BIND_EMAIL, str4, str5, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyResponse>() {
            public void accept(ModifyResponse modifyResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                }
            }
        });
    }

    public Disposable modifyPasswordByEmail(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<ModifyResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("email", str3);
        hashMap.put("code", str4);
        hashMap.put("password", str5);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.emailModifyPwd(str + AppYunApi.EMAIL_CODE_RESET, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyResponse>() {
            public void accept(ModifyResponse modifyResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                }
            }
        });
    }

    public Disposable modifyChildDeviceName(String str, String str2, String str3, String str4, String str5, String str6, final IAppYunResponseListener<ModifyDeviceMsgResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.PARENT_DEVICE_CODE, str4);
        hashMap.put(Constant.NAME, str6);
        return HttpClient.getYunService().modifyChildDeviceMsg(String.format("%sapi/child_devices/%s", new Object[]{str, str5}), str2, str3, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ModifyDeviceMsgResponse>() {
            public void accept(ModifyDeviceMsgResponse modifyDeviceMsgResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(modifyDeviceMsgResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                if (iAppYunResponseListener != null) {
                    ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                    iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
                }
            }
        });
    }

    public Disposable getWeather(String str, String str2, String str3, String str4, String str5, final IAppYunResponseListener<XZWeatherResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.WEATHER_LOCATION, str3);
        hashMap.put("language", str4);
        hashMap.put(Constant.WEATHER_UNIT, str5);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.getXZWeather(str + AppYunApi.WEATHER_INFO, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<XZWeatherResponse>() {
            public void accept(XZWeatherResponse xZWeatherResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(xZWeatherResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
            }
        });
    }

    public Disposable submitGtClientId(String str, String str2, String str3, String str4, final IAppYunResponseListener<SubmitGtClientIdResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("cid", str4);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.submitGtClientId(str + AppYunApi.SUBMIT_GT_CLIENT_ID, str2, str3, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<SubmitGtClientIdResponse>() {
            public void accept(SubmitGtClientIdResponse submitGtClientIdResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(submitGtClientIdResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
            }
        });
    }

    public Disposable updateUserInfo(String str, String str2, String str3, String str4, final IAppYunResponseListener<UpdateUserInfoResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.NAME, str4);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.updateUserInfo(str + AppYunApi.GET_USER_MSG, str2, str3, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UpdateUserInfoResponse>() {
            public void accept(UpdateUserInfoResponse updateUserInfoResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(updateUserInfoResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
            }
        });
    }

    public Disposable checkVersion(String str, String str2, String str3, String str4, String str5, String str6, final IAppYunResponseListener<CheckVersionResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("app_code", str3);
        hashMap.put(Constants.SP_KEY_VERSION, str4);
        hashMap.put("beta", str5);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.checkVersion(str + AppYunApi.CHECK_VERSION, str2, str6, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CheckVersionResponse>() {
            public void accept(CheckVersionResponse checkVersionResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(checkVersionResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
            }
        });
    }

    public Disposable getJuheCityList(String str, String str2, int i, int i2, final IAppYunResponseListener<LocationResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        if (i != -1) {
            hashMap.put(Constant.PAGE, Integer.valueOf(i));
        }
        if (i2 != -1) {
            hashMap.put(Constant.PAGE_SIZE, Integer.valueOf(i2));
        }
        AppYunService yunService = HttpClient.getYunService();
        return yunService.getJuheCityList(str + AppYunApi.JUHE_CITY_LIST, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<LocationResponse>() {
            public void accept(LocationResponse locationResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(locationResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
            }
        });
    }

    public Disposable getJuheGEOWeather(String str, String str2, String str3, String str4, final IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put(DispatchConstants.LATITUDE, str3);
        hashMap.put("lon", str4);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.getJuHeGEOWeather(str + AppYunApi.JUHE_GEO_WEATHER, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JuHeWeatherResponse>() {
            public void accept(JuHeWeatherResponse juHeWeatherResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(juHeWeatherResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
            }
        });
    }

    public Disposable getJuheCityWeather(String str, String str2, String str3, final IAppYunResponseListener<JuHeWeatherResponse> iAppYunResponseListener) {
        HashMap hashMap = new HashMap();
        hashMap.put("cityname", str3);
        AppYunService yunService = HttpClient.getYunService();
        return yunService.getJuHeCityWeather(str + AppYunApi.JUHE_CITY_WEATHER, str2, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JuHeWeatherResponse>() {
            public void accept(JuHeWeatherResponse juHeWeatherResponse) throws Exception {
                IAppYunResponseListener iAppYunResponseListener = iAppYunResponseListener;
                if (iAppYunResponseListener != null) {
                    iAppYunResponseListener.onSuccess(juHeWeatherResponse);
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ErrorInfoResponse errorInfo = YunModel.this.exceptionEngine.getErrorInfo(th);
                iAppYunResponseListener.onFailure(errorInfo.getCode(), errorInfo.getMsg());
            }
        });
    }
}
