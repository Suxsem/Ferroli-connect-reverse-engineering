package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.DeviceListResponse;
import com.p107tb.appyunsdk.bean.XZWeatherResponse;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.BoilerInfoBean;
import com.szacs.ferroliconnect.bean.BoilerMessage;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.bean.LocationGroup;
import com.szacs.ferroliconnect.bean.MsgBean;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.Event;
import com.szacs.ferroliconnect.event.MqttEvent;
import com.szacs.ferroliconnect.fragment.ChooseLocationFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.MQTTManager;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.szacs.ferroliconnect.util.NetworkUtils;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.szacs.ferroliconnect.widget.wheel.StrericWheelAdapter;
import com.szacs.ferroliconnect.widget.wheel.WheelView;
import com.taobao.accs.common.Constants;
import com.topband.sdk.boiler.MessageDataBuilder;
import com.topband.sdk.boiler.util.BinaryUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class BoilerUnControlActivity extends MyNavigationActivity implements View.OnClickListener {
    private static final int CHANGE_DHW_TEMP = 2;
    private static final int CHANGE_HEATING_TEMP = 1;
    private static final int NETWORK_ERROR = 4;
    private static final String TAG = "BoilerUnControlActivity";
    private int BATH_WATER_TARGET_TEMPERATURE_MAX = 60;
    private int BATH_WATER_TARGET_TEMPERATURE_MIN = 20;
    private int WARMING_WATER_TARGET_TEMPERATURE_MAX = 85;
    private int WARMING_WATER_TARGET_TEMPERATURE_MIN = 20;
    private boolean bGetBoilerInfo = false;
    private boolean bOutTempPayState = false;
    /* access modifiers changed from: private */
    public BoilerInfoBean boilerInfoBean;
    private Disposable checkDisposable;
    private ChooseLocationFragment chooseLocationFragment;
    private String devCode;
    private Disposable getBoilerSubscribe;
    private boolean isNewWifiVersion;
    private boolean isOnline = true;
    @BindView(2131296533)
    ImageView ivDhw;
    @BindView(2131296534)
    ImageView ivError;
    @BindView(2131296535)
    ImageView ivFlame;
    @BindView(2131296536)
    ImageView ivHeart;
    @BindView(2131296539)
    ImageView ivSetDhwTemp;
    @BindView(2131296540)
    ImageView ivSetHeatTemp;
    @BindView(2131296542)
    ImageView ivWeather;
    @BindView(2131296543)
    ImageView ivWifiSignal;
    /* access modifiers changed from: private */
    public MyHandler mhandler;
    private String productCode;
    @BindView(2131296695)
    RelativeLayout rlTempLocal;
    @BindView(2131296904)
    TextView tvDhw;
    @BindView(2131296906)
    TextView tvErrorInfo;
    @BindView(2131296907)
    TextView tvFlame;
    @BindView(2131296909)
    TextView tvHeart;
    @BindView(2131296911)
    TextView tvLocal;
    @BindView(2131296917)
    TextView tvTemp;
    private int wifiSingle;
    private String xzWeatherTemp = "";

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_boiler_uncontrol;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        getVersion();
        ButterKnife.bind((Activity) this);
        initWidget();
    }

    private void getVersion() {
        String stringExtra = getIntent().getStringExtra("McuSoftware");
        this.wifiSingle = getIntent().getIntExtra("wifiSingnal", Integer.MIN_VALUE);
        LogUtil.m3321i(TAG, "mWifiVersion: " + stringExtra);
        int i = -1;
        if (!TextUtils.isEmpty(stringExtra)) {
            if (stringExtra.contains("MCUT")) {
                i = Integer.parseInt(stringExtra.replace("MCUT", ""));
            } else if (stringExtra.contains("MCU")) {
                i = Integer.parseInt(stringExtra.replace("MCU", ""));
            }
            this.isNewWifiVersion = i >= 1080;
        }
        DeviceListResponse.ResultsBean resultsBean = UserCenter.getResultsBean();
        if (resultsBean != null) {
            this.isOnline = resultsBean.isOnline();
        }
        LogUtil.m3321i(TAG, "mWifiVersion: " + i + ",isNewWifiVersion: " + this.isNewWifiVersion);
    }

    private void initWidget() {
        if (this.isNewWifiVersion) {
            this.ivSetHeatTemp.setVisibility(0);
            this.ivSetDhwTemp.setVisibility(0);
        } else {
            this.ivSetHeatTemp.setVisibility(8);
            this.ivSetDhwTemp.setVisibility(8);
        }
        EventBus.getDefault().register(this);
        setBaseDrawerItemVisible(false);
        this.mhandler = new MyHandler(this);
        if (UserCenter.getResultsBean() != null) {
            this.productCode = UserCenter.getResultsBean().getProduct_code();
            this.devCode = UserCenter.getResultsBean().getDevice_code();
        }
        if (this.devCode == null) {
            this.devCode = "";
        }
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muGatewayInfo, 11, getString(C1683R.string.menu_device_infomation)).setIcon(C1683R.C1684drawable.ic_menu_dev_info);
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muReflash, 13, getString(C1683R.string.menu_refresh)).setIcon(C1683R.C1684drawable.ic_menu_reflash);
        setTitle(getString(C1683R.string.public_boiler_normal_title));
        this.rlTempLocal.setOnClickListener(this);
        this.rlTempLocal.setClickable(false);
        this.ivSetHeatTemp.setOnClickListener(this);
        this.ivSetDhwTemp.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MsgBean msgBean) {
        if (msgBean != null) {
            UserCenter.setMsgBean(msgBean);
            Log.d(TAG, " errInfo = " + msgBean.getErrorInfo());
            if (msgBean.getErrorCode() != 0) {
                String str = msgBean.getErrorInfo() == 1 ? "A" : "F";
                TextView textView = this.tvErrorInfo;
                textView.setText(str + msgBean.getErrorCode());
                this.tvErrorInfo.setTextColor(ContextCompat.getColor(this, C1683R.color.cloudwarm_error));
                this.ivError.setImageResource(C1683R.C1684drawable.error);
            } else {
                this.tvErrorInfo.setText(getString(C1683R.string.boiler_normal));
                this.tvErrorInfo.setTextColor(ContextCompat.getColor(this, C1683R.color.cloudwarm_grey));
                this.ivError.setImageResource(C1683R.C1684drawable.normal);
            }
            int i = 0;
            this.bOutTempPayState = msgBean.getOutProbeModel() == 1;
            this.rlTempLocal.setClickable(true);
            TextView textView2 = this.tvLocal;
            if (this.bOutTempPayState) {
                i = 8;
            }
            textView2.setVisibility(i);
            if (this.bOutTempPayState) {
                TextView textView3 = this.tvTemp;
                textView3.setText(checkTemp(msgBean.getOutTemp()) + "°C");
            }
            if (!this.bGetBoilerInfo) {
                this.bGetBoilerInfo = true;
                Log.d(TAG, " getBoilerInfo");
                getBoilerInfo();
            }
            Log.d(TAG, " onMessage boilerBean.getBathCurrentTemp() = " + msgBean.getBathCurrentTemp() + "onMessage boilerBean.getPwm() = " + msgBean.getPwm());
            TextView textView4 = this.tvHeart;
            StringBuilder sb = new StringBuilder();
            sb.append(Math.floor((double) msgBean.getHeatCurrentTemp()));
            sb.append("°C");
            textView4.setText(sb.toString());
            TextView textView5 = this.tvDhw;
            textView5.setText(Math.floor((double) msgBean.getBathCurrentTemp()) + "°C");
            TextView textView6 = this.tvFlame;
            textView6.setText(Math.floor((double) msgBean.getPwm()) + "%");
            this.ivFlame.setImageResource(msgBean.getFlameStatus() == 1 ? C1683R.C1684drawable.flame : C1683R.C1684drawable.noflame);
            this.ivHeart.setImageResource(msgBean.isHeatingMode() ? C1683R.C1684drawable.heating_icon : C1683R.C1684drawable.heating_icon_gray);
            this.ivDhw.setImageResource(msgBean.isDhwMode() ? C1683R.C1684drawable.domestic_icon : C1683R.C1684drawable.summer_button_gray);
            LogUtil.m3315d("MsgBean", "signal: " + Constant.wifiSingal);
            setWifiSignalImage();
        }
    }

    private void setWifiSignalImage() {
        if (this.wifiSingle == Integer.MIN_VALUE) {
            this.ivWifiSignal.setVisibility(8);
            return;
        }
        this.ivWifiSignal.setVisibility(0);
        int i = this.wifiSingle;
        if (i >= -50) {
            this.ivWifiSignal.setBackgroundResource(C1683R.C1684drawable.signal_05);
        } else if (i < -60 || i >= -50) {
            int i2 = this.wifiSingle;
            if (i2 < -70 || i2 >= -60) {
                int i3 = this.wifiSingle;
                if (i3 < -80 || i3 >= -70) {
                    this.ivWifiSignal.setBackgroundResource(C1683R.C1684drawable.signal_01);
                } else {
                    this.ivWifiSignal.setBackgroundResource(C1683R.C1684drawable.signal_02);
                }
            } else {
                this.ivWifiSignal.setBackgroundResource(C1683R.C1684drawable.signal_03);
            }
        } else {
            this.ivWifiSignal.setBackgroundResource(C1683R.C1684drawable.signal_04);
        }
    }

    private String checkTemp(float f) {
        double d = (double) f;
        if (d > 127.0d || d < -40.0d) {
            return !this.xzWeatherTemp.equals("") ? this.xzWeatherTemp : "--";
        }
        return String.format("%.1f", new Object[]{Float.valueOf(f)}).replace(",", ".");
    }

    public void onClick(View view) {
        if (!checkOnlineOrNet()) {
            switch (view.getId()) {
                case C1683R.C1685id.iv_set_dhw_temp:
                    showDialog(false);
                    return;
                case C1683R.C1685id.iv_set_heat_temp:
                    showDialog(true);
                    return;
                case C1683R.C1685id.rl_temp_local:
                    showChooseLocationDialog();
                    return;
                default:
                    return;
            }
        }
    }

    private void showDialog(final boolean z) {
        float f;
        if (UserCenter.getMsgBean() != null) {
            View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_set_target_temp, (ViewGroup) null);
            final AlertDialog show = new AlertDialog.Builder(this).setView(inflate).show();
            final WheelView wheelView = (WheelView) inflate.findViewById(C1683R.C1685id.passw_1);
            TextView textView = (TextView) inflate.findViewById(C1683R.C1685id.tv_title);
            ArrayList arrayList = new ArrayList();
            if (z) {
                textView.setText("Set Max CH temperature");
                if (UserCenter.getMsgBean() != null) {
                    this.WARMING_WATER_TARGET_TEMPERATURE_MIN = (int) Math.max((float) this.WARMING_WATER_TARGET_TEMPERATURE_MIN, UserCenter.getMsgBean().getHeatMinTemp());
                    this.WARMING_WATER_TARGET_TEMPERATURE_MAX = (int) Math.min((float) this.WARMING_WATER_TARGET_TEMPERATURE_MAX, UserCenter.getMsgBean().getHeatMaxTemp());
                }
                for (int i = this.WARMING_WATER_TARGET_TEMPERATURE_MIN; i <= this.WARMING_WATER_TARGET_TEMPERATURE_MAX; i++) {
                    arrayList.add(i + "");
                }
            } else {
                textView.setText("Set DHW temperature");
                if (UserCenter.getMsgBean() != null) {
                    this.BATH_WATER_TARGET_TEMPERATURE_MIN = (int) Math.max((float) this.BATH_WATER_TARGET_TEMPERATURE_MIN, UserCenter.getMsgBean().getBathMinTemp());
                    this.BATH_WATER_TARGET_TEMPERATURE_MAX = (int) Math.min((float) this.BATH_WATER_TARGET_TEMPERATURE_MAX, UserCenter.getMsgBean().getBathMaxTemp());
                }
                for (int i2 = this.BATH_WATER_TARGET_TEMPERATURE_MIN; i2 <= this.BATH_WATER_TARGET_TEMPERATURE_MAX; i2++) {
                    arrayList.add(i2 + "");
                }
            }
            String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            float f2 = 0.0f;
            if (UserCenter.getMsgBean() != null) {
                float heatTargetTemp = UserCenter.getMsgBean().getHeatTargetTemp();
                float heatTargetTemp2 = UserCenter.getMsgBean().getHeatTargetTemp();
                int i3 = this.WARMING_WATER_TARGET_TEMPERATURE_MAX;
                if (heatTargetTemp2 > ((float) i3)) {
                    heatTargetTemp = (float) i3;
                }
                f2 = heatTargetTemp;
                f = UserCenter.getMsgBean().getBathTargetTemp();
                float bathTargetTemp = UserCenter.getMsgBean().getBathTargetTemp();
                int i4 = this.BATH_WATER_TARGET_TEMPERATURE_MAX;
                if (bathTargetTemp > ((float) i4)) {
                    f = (float) i4;
                }
            } else {
                f = 0.0f;
            }
            initWheel(wheelView, strArr, (int) (z ? f2 - ((float) this.WARMING_WATER_TARGET_TEMPERATURE_MIN) : f - ((float) this.BATH_WATER_TARGET_TEMPERATURE_MIN)));
            ((TextView) inflate.findViewById(C1683R.C1685id.ivCancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    show.cancel();
                }
            });
            ((TextView) inflate.findViewById(C1683R.C1685id.ivConfirm)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int i = 1;
                    BoilerUnControlActivity.this.mhandler.removeMessages(z ? 1 : 2);
                    MyHandler access$000 = BoilerUnControlActivity.this.mhandler;
                    if (!z) {
                        i = 2;
                    }
                    Message obtainMessage = access$000.obtainMessage(i);
                    obtainMessage.obj = BoilerUnControlActivity.this.getWheelValue(wheelView);
                    BoilerUnControlActivity.this.mhandler.sendMessageDelayed(obtainMessage, 2000);
                    show.cancel();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void initWheel(WheelView wheelView, String[] strArr, int i) {
        wheelView.setAdapter(new StrericWheelAdapter(strArr));
        wheelView.setCurrentItem(i);
        wheelView.setCyclic(false);
        wheelView.setInterpolator(new AnticipateOvershootInterpolator());
    }

    /* access modifiers changed from: protected */
    public String getWheelValue(WheelView wheelView) {
        return wheelView.getCurrentItemValue();
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (checkOnlineOrNet()) {
            return super.onNavigationItemSelected(menuItem);
        }
        int itemId = menuItem.getItemId();
        if (itemId == C1683R.C1685id.muGatewayInfo) {
            Intent intent = new Intent();
            intent.setClass(this, BoilerInfoActivity.class);
            startActivityImmediately(intent);
        } else if (itemId == C1683R.C1685id.muReflash) {
            showProgress();
            initData();
        }
        return super.onNavigationItemSelected(menuItem);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        unSubscribe();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        subscribe();
        initData(false);
    }

    private void unSubscribe() {
        AppYunManager.getInstance().unsubscribeDeviceMsg(this.productCode, this.devCode, (IAppYunManagerActionListener) null);
        AppYunManager.getInstance().unsubscribeDeviceStatus(this.productCode, this.devCode, (IAppYunManagerActionListener) null);
    }

    private void subscribe() {
        showProgress();
        AppYunManager.getInstance().subscribeDeviceMsg(this.productCode, this.devCode, new IAppYunManagerActionListener() {
            public void onFailure(IMqttToken iMqttToken, Throwable th) {
            }

            public void onSuccess(IMqttToken iMqttToken) {
            }
        });
        AppYunManager.getInstance().subscribeDeviceStatus(this.productCode, this.devCode, new IAppYunManagerActionListener() {
            public void onFailure(IMqttToken iMqttToken, Throwable th) {
            }

            public void onSuccess(IMqttToken iMqttToken) {
            }
        });
    }

    private void initData() {
        sendMessage(new MessageDataBuilder().warmingWaterMaxTemperature().warmingWaterMinTemperature().bathWaterMaxTemperature().bathWaterMinTemperature().slaveOTState(0).roomThermostatState(false).warmingWaterRealTemp(0).warmingWaterTargetTemp(0).warmingMaxTemp(0).warmingMinTemp(0).warmingMaxSetPoint(0).bathWaterRealTemp(0).bathWaterTargetTemp(0).bathMaxTemp(0).bathMinTemp(0).windPressureSwitch(true).winterSummerMode(0).waterSupplySwitch(true).flameStatus(false).warmingUpStatus(true).deviceError().ErrorInfo().fuelGasType().deviceFunctionType().deviceInstallationType().boilerControllType().waterPressure(0.0f).pwmValue(0).ReturnWaterTemp(0).OutWaterTemp(0).outTemp(0.0f).FlueTemperature(0).WaterRate(0.0f).outTempProbeModel(0).boiler().query());
    }

    /* access modifiers changed from: private */
    public void initData(boolean z) {
        if (z) {
            checkOnlineOrNet();
        }
        this.getBoilerSubscribe = HttpUtils.getRetrofit().getBoilerPointsInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BoilerMessage>() {
            public void accept(BoilerMessage boilerMessage) throws Exception {
                if (boilerMessage != null) {
                    MsgBean msgBean = new MsgBean();
                    msgBean.setErrorInfo(boilerMessage.getErrorInfo());
                    msgBean.setErrorCode(boilerMessage.getErrorCode());
                    msgBean.setOutProbeModel(boilerMessage.getOutProbeModel());
                    msgBean.setOutTemp(boilerMessage.getOutTemp());
                    msgBean.setHeatCurrentTemp(boilerMessage.getHeatCurrentTemp());
                    msgBean.setBathCurrentTemp(boilerMessage.getBathCurrentTemp());
                    msgBean.setPwm(boilerMessage.getPwm());
                    msgBean.setFlameStatus(boilerMessage.getFlameStatus());
                    msgBean.setHeatingMode(boilerMessage.isHeatingMode());
                    msgBean.setDhwMode(boilerMessage.isDhwMode());
                    msgBean.setHeatMinTemp((float) boilerMessage.getHeatMinTemp());
                    msgBean.setHeatMaxTemp((float) boilerMessage.getHeatMaxTemp());
                    msgBean.setBathMinTemp((float) boilerMessage.getBathMinTemp());
                    msgBean.setBathMaxTemp(boilerMessage.getBathMaxTemp());
                    msgBean.setHeatTargetTemp((float) boilerMessage.getHeatTargetTemp());
                    msgBean.setBathTargetTemp((float) boilerMessage.getBathTargetTemp());
                    BoilerUnControlActivity.this.onMessage(msgBean);
                    BoilerUnControlActivity.this.hideProgress();
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.e(BoilerUnControlActivity.TAG, "getBoilerPointsInfo fail", th);
                BoilerUnControlActivity.this.hideProgress();
                th.printStackTrace();
                ToastUtil.showShortToast(BoilerUnControlActivity.this.mContext, "Get boiler info fail");
            }
        });
    }

    private void sendMessage(final byte[] bArr) {
        if (!checkOnlineOrNet() && bArr != null) {
            LogUtil.m3315d(TAG, "send bytes " + Arrays.toString(bArr));
            BinaryUtils.printBytes(TAG, bArr);
            AppYunManager.getInstance().sendMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    Log.d(BoilerUnControlActivity.TAG, "sendMessage " + BinaryUtils.bytes2String(bArr));
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    ToastUtil.showShortToast(BoilerUnControlActivity.this.mContext, BoilerUnControlActivity.this.getString(C1683R.string.public_failed_to_set));
                }
            });
        }
    }

    private boolean checkOnlineOrNet() {
        if (!NetworkUtils.isNetworkConnected(this.mContext)) {
            ToastUtil.showShortToast(this.mContext, getString(C1683R.string.public_network_error));
            hideProgress();
            return true;
        } else if (this.isOnline) {
            return false;
        } else {
            ToastUtil.showShortToast(this.mContext, getString(C1683R.string.device_list_device_disconnected));
            hideProgress();
            return true;
        }
    }

    public void showToast(String str) {
        if (!isFinishing()) {
            hideProgress();
            ToastUtil.showShortToast(this.mContext, str);
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Disposable disposable = this.checkDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.checkDisposable.dispose();
        }
        Disposable disposable2 = this.getBoilerSubscribe;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        hideProgress();
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLocation(LocationGroup locationGroup) {
        this.city = locationGroup.getName().getEn();
        this.cityID = locationGroup.getCity_id();
        SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
        edit.putString("city", this.city);
        edit.putString("editor", this.cityID);
        edit.commit();
        this.tvLocal.setText(this.city);
        getWeather(this.cityID);
        BoilerInfoBean boilerInfoBean2 = this.boilerInfoBean;
        String str = "";
        String brand = (boilerInfoBean2 == null || boilerInfoBean2.getBrand() == null) ? str : this.boilerInfoBean.getBrand();
        BoilerInfoBean boilerInfoBean3 = this.boilerInfoBean;
        String model = (boilerInfoBean3 == null || boilerInfoBean3.getModel() == null) ? str : this.boilerInfoBean.getModel();
        BoilerInfoBean boilerInfoBean4 = this.boilerInfoBean;
        String sn = (boilerInfoBean4 == null || boilerInfoBean4.getSn() == null) ? str : this.boilerInfoBean.getSn();
        String str2 = this.city;
        String str3 = this.cityID;
        BoilerInfoBean boilerInfoBean5 = this.boilerInfoBean;
        if (!(boilerInfoBean5 == null || boilerInfoBean5.getInstallation_date() == null)) {
            str = this.boilerInfoBean.getInstallation_date();
        }
        updateBoilerInfo(brand, model, sn, str2, str3, str);
    }

    private void updateBoilerInfo(String str, String str2, String str3, String str4, String str5, String str6) {
        showProgress();
        HashMap hashMap = new HashMap();
        hashMap.put(Constants.KEY_BRAND, str);
        hashMap.put(Constants.KEY_MODEL, str2);
        hashMap.put("sn", str3);
        hashMap.put(com.p107tb.appyunsdk.Constant.WEATHER_LOCATION, str4);
        hashMap.put("city_id", str5);
        hashMap.put("installation_date", str6);
        HttpUtils.getRetrofit().UpdateBoilerInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug(), hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) throws Exception {
                LogUtil.m3315d(BoilerUnControlActivity.TAG, "set user info success");
                Log.i("testHide", "hide9");
                BoilerUnControlActivity.this.hideProgress();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(BoilerUnControlActivity.TAG, "set user info fail");
                Log.i("testHide", "hide10");
                BoilerUnControlActivity.this.hideProgress();
                th.printStackTrace();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1683R.C1687menu.menu_gateway_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == C1683R.C1685id.muAddGateway) {
            if (this.drawer.isDrawerOpen(5)) {
                this.drawer.closeDrawer(5);
            } else {
                this.drawer.openDrawer(5);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBeseEvent(BaseEvent baseEvent) {
        if (baseEvent.getEvent() == Event.SERVER_RESPONSE_SUCCESS) {
            Log.i("testHide", "hide11");
            hideProgress();
        }
    }

    private class MyHandler extends Handler {
        private WeakReference<BoilerUnControlActivity> activityWeakReference;

        public MyHandler(BoilerUnControlActivity boilerUnControlActivity) {
            this.activityWeakReference = new WeakReference<>(boilerUnControlActivity);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            BoilerUnControlActivity boilerUnControlActivity = (BoilerUnControlActivity) this.activityWeakReference.get();
            if (boilerUnControlActivity != null) {
                int i = message.what;
                if (i == 1) {
                    String str = (String) message.obj;
                    if (!str.isEmpty()) {
                        boilerUnControlActivity.setTemp(1, Integer.parseInt(str));
                    }
                } else if (i == 2) {
                    String str2 = (String) message.obj;
                    if (!str2.isEmpty()) {
                        boilerUnControlActivity.setTemp(2, Integer.parseInt(str2));
                    }
                } else if (i == 4 && !boilerUnControlActivity.isFinishing()) {
                    BoilerUnControlActivity.this.checkDeviceStatus();
                }
            }
        }
    }

    public void setTemp(int i, int i2) {
        if (!isFinishing()) {
            showProgress();
            if (i == 1) {
                sendMessage(new MessageDataBuilder().warmingWaterTargetTemp(i2).boiler().control());
            } else {
                sendMessage(new MessageDataBuilder().bathWaterTargetTemp(i2).boiler().control());
            }
        }
    }

    private void getBoilerInfo() {
        HttpUtils.getRetrofit().getBoilerInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BoilerInfoBean>() {
            public void accept(BoilerInfoBean boilerInfoBean) throws Exception {
                LogUtil.m3315d(BoilerUnControlActivity.TAG, "onGet boiler info success");
                BoilerUnControlActivity.this.getWeather(boilerInfoBean.getCity_id());
                BoilerInfoBean unused = BoilerUnControlActivity.this.boilerInfoBean = boilerInfoBean;
                BoilerUnControlActivity.this.tvLocal.setText(boilerInfoBean.getLocation());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(BoilerUnControlActivity.TAG, "onGet boiler info fail");
                th.printStackTrace();
            }
        });
    }

    /* access modifiers changed from: private */
    public void getWeather(String str) {
        String str2 = LanguageUtil.getSetLanguageLocale(getApplicationContext()).equals(Locale.CHINA) ? Constant.WEATHER_LANGUAGE_ZH : "en";
        if (str != null && !str.equals("")) {
            Log.d(TAG, " weather location  = " + str + " weather language = " + str2);
            getWeather(str, str2, Constant.WEATHER_TEMP_C);
        } else if (!this.bOutTempPayState) {
            showChooseLocationDialog();
        }
    }

    private void getWeather(String str, String str2, String str3) {
        AppYunManager.getInstance().getWeather(str, str2, str3, new IAppYunResponseListener<XZWeatherResponse>() {
            public void onSuccess(XZWeatherResponse xZWeatherResponse) {
                Log.d(BoilerUnControlActivity.TAG, " on get weather success");
                BoilerUnControlActivity.this.setWeatherInfo(xZWeatherResponse);
            }

            public void onFailure(int i, String str) {
                ToastUtil.showShortToast(BoilerUnControlActivity.this.mContext, HttpError.getMessage(BoilerUnControlActivity.this.mContext, i));
                Log.d(BoilerUnControlActivity.TAG, " on get weather fail");
            }
        });
    }

    /* access modifiers changed from: private */
    public void setWeatherInfo(XZWeatherResponse xZWeatherResponse) {
        Drawable drawable;
        this.xzWeatherTemp = xZWeatherResponse.getResults().get(0).getNow().getTemperature();
        sendNetWeatherTemp(this.xzWeatherTemp);
        if (!this.bOutTempPayState) {
            TextView textView = this.tvTemp;
            textView.setText(this.xzWeatherTemp + "°C");
        }
        int resId = getResId(Constant.WEATHER_IMG_PREFEX + xZWeatherResponse.getResults().get(0).getNow().getCode());
        Log.d(TAG, " setWeatherInfo weather img  = " + resId);
        ImageView imageView = this.ivWeather;
        if (this.bOutTempPayState) {
            drawable = getResources().getDrawable(C1683R.C1684drawable.ic_out_temp);
        } else {
            drawable = getResources().getDrawable(resId);
        }
        imageView.setImageDrawable(drawable);
    }

    public int getResId(String str) {
        try {
            return C1683R.C1684drawable.class.getField(str).getInt(str);
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            return 0;
        }
    }

    private void showChooseLocationDialog() {
        if (!this.bOutTempPayState) {
            if (getFragmentManager().findFragmentByTag("chooseLocationFragment") == null) {
                this.chooseLocationFragment = new ChooseLocationFragment();
            }
            if (this.chooseLocationFragment == null || getFragmentManager().findFragmentByTag("chooseLocationFragment") == null || this.chooseLocationFragment.getDialog() == null || !this.chooseLocationFragment.getDialog().isShowing()) {
                this.chooseLocationFragment.show(getFragmentManager(), "chooseLocationFragment");
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MqttEvent mqttEvent) {
        if (mqttEvent.getState() == 1 && NetworkUtils.isNetworkConnected(this.mContext)) {
            Log.d(TAG, " unControlBoiler onEvent MQTT CONNECT SUCCESS Subscribe");
            subscribe();
        }
    }

    /* access modifiers changed from: private */
    public void checkDeviceStatus() {
        if (!MQTTManager.getInstance(this).checkMQTTStatus()) {
            Log.d(TAG, " BoilerUnControl mqtt disconnect reconnect");
        } else {
            this.checkDisposable = HttpUtils.getRetrofit().getDeviceInfo(this.authorzation, UserCenter.getUserInfo().getSlug(), UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DeviceListResponse.ResultsBean>() {
                public void accept(DeviceListResponse.ResultsBean resultsBean) throws Exception {
                    if (!resultsBean.isOnline()) {
                        BoilerUnControlActivity boilerUnControlActivity = BoilerUnControlActivity.this;
                        boilerUnControlActivity.showToast(boilerUnControlActivity.getString(C1683R.string.dialog_device_offline));
                        return;
                    }
                    BoilerUnControlActivity boilerUnControlActivity2 = BoilerUnControlActivity.this;
                    boilerUnControlActivity2.showToast(boilerUnControlActivity2.getString(C1683R.string.dialog_device_not_response));
                    BoilerUnControlActivity.this.initData(false);
                }
            }, new Consumer<Throwable>() {
                public void accept(Throwable th) throws Exception {
                    BoilerUnControlActivity.this.hideProgress();
                    BoilerUnControlActivity boilerUnControlActivity = BoilerUnControlActivity.this;
                    boilerUnControlActivity.showToast(boilerUnControlActivity.getString(C1683R.string.dialog_device_not_response));
                    th.printStackTrace();
                }
            });
        }
    }

    private void sendJsonMessage(byte[] bArr) {
        if (bArr != null) {
            AppYunManager.getInstance().sendJsonMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    if (!BoilerUnControlActivity.this.isFinishing()) {
                        Log.d(BoilerUnControlActivity.TAG, " sendJsonMessage Remove Thermostat Msg Send Success");
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    if (!BoilerUnControlActivity.this.isFinishing()) {
                        Log.d(BoilerUnControlActivity.TAG, " sendJsonMessage Remove Thermostat Msg Send Failed");
                        BoilerUnControlActivity.this.hideProgress();
                        ToastUtil.showShortToast(BoilerUnControlActivity.this.mContext, BoilerUnControlActivity.this.getString(C1683R.string.public_failed_to_set));
                    }
                }
            });
        }
    }

    private void sendNetWeatherTemp(String str) {
        sendJsonMessage("{\"type\":\"status\",\"data\":{\"gatway\":{\"temperature\":\"temperatureData\"}}}".replace("temperatureData", str).getBytes());
    }

    /* access modifiers changed from: protected */
    public void ShowProgressDialog(String str) {
        Log.e(TAG, "show progress dialog");
        if (this.progress == null) {
            this.progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(str).setCancellable(true).setAnimationSpeed(1).setDimAmount(0.5f);
        } else {
            this.progress.setLabel(str);
        }
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
    }

    /* access modifiers changed from: protected */
    public void HideProgressDialog() {
        Log.e(TAG, "hide progress dialog");
        if (this.progress != null && this.progress.isShowing()) {
            this.progress.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void hideProgress() {
        HideProgressDialog();
        this.mhandler.removeMessages(4);
    }

    private void showProgress() {
        ShowProgressDialog((String) null);
        this.mhandler.sendEmptyMessageDelayed(4, 10000);
    }
}
