package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.szacs.ferroliconnect.bean.BoilerMessage;
import com.szacs.ferroliconnect.bean.MsgBean;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.Event;
import com.szacs.ferroliconnect.event.MqttEvent;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.MQTTManager;
import com.szacs.ferroliconnect.util.NetworkUtils;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.topband.sdk.boiler.MessageDataBuilder;
import com.topband.sdk.boiler.util.BinaryUtils;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class SwitchBoilerActivity extends MyNavigationActivity implements View.OnClickListener {
    private static final int NETWORK_ERROR = 4;
    private static final String TAG = "SwitchBoilerActivity";
    private boolean allowControl;
    private Disposable checkDisposable;
    private String devCode;
    private Disposable getBoilerSubscribe;
    private boolean isNewWifiVersion;
    private boolean isOnline = true;
    @BindView(2131296530)
    ImageView ivBoilerSwitch;
    @BindView(2131296543)
    ImageView ivWifiSignal;
    @BindView(2131296561)
    LinearLayout llBoiler;
    private MyHandler mhandler;
    private String productCode;
    private boolean switchStatus = false;
    @BindView(2131296797)
    TextView tvBoiler;
    private int wifiSingle;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_switch_boiler;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ButterKnife.bind((Activity) this);
        getVersion();
        init();
    }

    private void init() {
        EventBus.getDefault().register(this);
        setBaseDrawerItemVisible(false);
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muGatewayInfo, 11, getString(C1683R.string.menu_device_infomation)).setIcon(C1683R.C1684drawable.ic_menu_dev_info);
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muReflash, 13, getString(C1683R.string.menu_refresh)).setIcon(C1683R.C1684drawable.ic_menu_reflash);
        this.allowControl = getIntent().getBooleanExtra("AllowControl", false);
        this.mhandler = new MyHandler(this);
        this.llBoiler.setOnClickListener(this);
        if (UserCenter.getResultsBean() != null) {
            this.productCode = UserCenter.getResultsBean().getProduct_code();
            this.devCode = UserCenter.getResultsBean().getDevice_code();
        }
        if (this.devCode == null) {
            this.devCode = "";
        }
        setTitle(getString(C1683R.string.public_boiler_normal_title));
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
        LogUtil.m3321i(TAG, "mWifiVersion: " + i + ",isNewWifiVersion: " + this.isNewWifiVersion);
        DeviceListResponse.ResultsBean resultsBean = UserCenter.getResultsBean();
        if (resultsBean != null) {
            this.isOnline = resultsBean.isOnline();
        }
    }

    public void onClick(View view) {
        if (!checkOnlineOrNet() && view.getId() == C1683R.C1685id.llBoiler) {
            switchBoiler();
        }
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

    /* access modifiers changed from: private */
    public void hideProgress() {
        HideProgressDialog();
        this.mhandler.removeMessages(4);
    }

    private void showProgress() {
        ShowProgressDialog((String) null);
        this.mhandler.sendEmptyMessageDelayed(4, 10000);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1683R.C1687menu.menu_gateway_list, menu);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBeseEvent(BaseEvent baseEvent) {
        if (baseEvent.getEvent() == Event.SERVER_RESPONSE_SUCCESS) {
            Log.i("testHide", "hide20");
            hideProgress();
        }
    }

    private void switchBoiler() {
        if (!isFinishing()) {
            if (!this.allowControl) {
                ToastUtil.showShortToast(this.mContext, getResources().getString(C1683R.string.boiler_not_operation));
                return;
            }
            showProgress();
            MessageDataBuilder messageDataBuilder = new MessageDataBuilder();
            if (this.switchStatus) {
                messageDataBuilder.boilerRelayState(1);
            } else {
                messageDataBuilder.boilerRelayState(2);
            }
            sendMessage(messageDataBuilder.boiler().control());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MsgBean msgBean) {
        this.switchStatus = msgBean.getBoilerRelayState() == 2;
        setSwitchImg(msgBean.getBoilerRelayState());
        LogUtil.m3315d("MsgBean", "signal: " + Constant.wifiSingal + ",state: " + msgBean.getBoilerRelayState() + ",relayState: " + msgBean.getBoilerRelayState());
        setWifiSignalImage();
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

    private void setSwitchImg(int i) {
        if (i == 1) {
            this.ivBoilerSwitch.setImageDrawable(getResources().getDrawable(C1683R.C1684drawable.switch_boiler_off));
            this.tvBoiler.setText(getResources().getString(C1683R.string.boiler_switch_off));
            this.llBoiler.setBackground(getResources().getDrawable(C1683R.C1684drawable.shape_switch_boiler_off));
        }
        if (i == 2) {
            this.ivBoilerSwitch.setImageDrawable(getResources().getDrawable(C1683R.C1684drawable.switch_boiler_on));
            this.tvBoiler.setText(getResources().getString(C1683R.string.boiler_switch_on));
            this.llBoiler.setBackground(getResources().getDrawable(C1683R.C1684drawable.shape_switch_boiler_on));
        }
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
                    msgBean.setBoilerRelayState(boilerMessage.getBoilerRelayState());
                    SwitchBoilerActivity.this.onMessage(msgBean);
                    SwitchBoilerActivity.this.hideProgress();
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(SwitchBoilerActivity.TAG, "getBoilerPointsInfo");
                SwitchBoilerActivity.this.hideProgress();
                th.printStackTrace();
                Context context = SwitchBoilerActivity.this.mContext;
                ToastUtil.showShortToast(context, "getBoilerPointsInfo error: " + th.getMessage());
            }
        });
    }

    private boolean checkOnlineOrNet() {
        Log.i(TAG, "checkDeviceStatus");
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

    private void sendMessage(final byte[] bArr) {
        if (bArr != null && !checkOnlineOrNet()) {
            LogUtil.m3315d(TAG, "send bytes " + Arrays.toString(bArr));
            BinaryUtils.printBytes(TAG, bArr);
            AppYunManager.getInstance().sendMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    Log.d(SwitchBoilerActivity.TAG, "sendMessage " + BinaryUtils.bytes2String(bArr));
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    ToastUtil.showShortToast(SwitchBoilerActivity.this.mContext, SwitchBoilerActivity.this.getString(C1683R.string.public_failed_to_set));
                }
            });
        }
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

    /* access modifiers changed from: private */
    public void showToast(String str) {
        if (!isFinishing()) {
            hideProgress();
            ToastUtil.showShortToast(this.mContext, str);
        }
    }

    private class MyHandler extends Handler {
        private WeakReference<SwitchBoilerActivity> activityWeakReference;

        public MyHandler(SwitchBoilerActivity switchBoilerActivity) {
            this.activityWeakReference = new WeakReference<>(switchBoilerActivity);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            SwitchBoilerActivity switchBoilerActivity = (SwitchBoilerActivity) this.activityWeakReference.get();
            if (switchBoilerActivity != null && message.what == 4 && !switchBoilerActivity.isFinishing()) {
                SwitchBoilerActivity.this.checkDeviceStatus();
            }
        }
    }

    private void getWeather(String str) {
        String str2 = LanguageUtil.getSetLanguageLocale(getApplicationContext()).equals(Locale.CHINA) ? Constant.WEATHER_LANGUAGE_ZH : "en";
        if (str == null || str.equals("")) {
            str = "";
        }
        Log.d(TAG, " weather location  = " + str + " weather language = " + str2);
        getWeather(str, str2, Constant.WEATHER_TEMP_C);
    }

    private void getWeather(String str, String str2, String str3) {
        AppYunManager.getInstance().getWeather(str, str2, str3, new IAppYunResponseListener<XZWeatherResponse>() {
            public void onSuccess(XZWeatherResponse xZWeatherResponse) {
                Log.d(SwitchBoilerActivity.TAG, " on get weather success");
            }

            public void onFailure(int i, String str) {
                Log.d(SwitchBoilerActivity.TAG, " on get weather fail");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MqttEvent mqttEvent) {
        if (mqttEvent.getState() == 1 && NetworkUtils.isNetworkConnected(this.mContext)) {
            Log.d(TAG, " switchBoiler onEvent MQTT CONNECT SUCCESS Subscribe");
            subscribe();
        }
    }

    /* access modifiers changed from: private */
    public void checkDeviceStatus() {
        if (!MQTTManager.getInstance(this).checkMQTTStatus()) {
            Log.d(TAG, " switchBoiler mqtt disconnect reconnect");
        } else {
            this.checkDisposable = HttpUtils.getRetrofit().getDeviceInfo(this.authorzation, UserCenter.getUserInfo().getSlug(), UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DeviceListResponse.ResultsBean>() {
                public void accept(DeviceListResponse.ResultsBean resultsBean) throws Exception {
                    if (!resultsBean.isOnline()) {
                        SwitchBoilerActivity switchBoilerActivity = SwitchBoilerActivity.this;
                        switchBoilerActivity.showToast(switchBoilerActivity.getString(C1683R.string.dialog_device_offline));
                        return;
                    }
                    SwitchBoilerActivity switchBoilerActivity2 = SwitchBoilerActivity.this;
                    switchBoilerActivity2.showToast(switchBoilerActivity2.getString(C1683R.string.dialog_device_not_response));
                    SwitchBoilerActivity.this.initData(false);
                }
            }, new Consumer<Throwable>() {
                public void accept(Throwable th) throws Exception {
                    SwitchBoilerActivity.this.hideProgress();
                    SwitchBoilerActivity switchBoilerActivity = SwitchBoilerActivity.this;
                    switchBoilerActivity.showToast(switchBoilerActivity.getString(C1683R.string.dialog_device_not_response));
                    th.printStackTrace();
                }
            });
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

    private void initData() {
        sendMessage(new MessageDataBuilder().boilerRelayState(0).boiler().query());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Disposable disposable = this.checkDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.checkDisposable.dispose();
        }
        Disposable disposable2 = this.getBoilerSubscribe;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        EventBus.getDefault().unregister(this);
        hideProgress();
        super.onDestroy();
    }
}
