package com.szacs.ferroliconnect.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.p000v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.igexin.assist.sdk.AssistPushConsts;
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
import com.szacs.ferroliconnect.bean.LocationGroup;
import com.szacs.ferroliconnect.bean.MsgBean;
import com.szacs.ferroliconnect.bean.TemperatureMessage;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.bean.WeatherBean;
import com.szacs.ferroliconnect.dialog.ChooseLocationDialog;
import com.szacs.ferroliconnect.dialog.PickerDateDialog;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.DayLightEvent;
import com.szacs.ferroliconnect.event.Event;
import com.szacs.ferroliconnect.event.HolidayTimeEvent;
import com.szacs.ferroliconnect.event.MqttEvent;
import com.szacs.ferroliconnect.event.TimeZoneEvent;
import com.szacs.ferroliconnect.fragment.ChooseLocationFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.MQTTManager;
import com.szacs.ferroliconnect.util.NetworkUtils;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.szacs.ferroliconnect.widget.TemperLayout;
import com.szacs.ferroliconnect.widget.wheel.BarViewBeSmart196;
import com.taobao.accs.common.Constants;
import com.topband.sdk.boiler.MessageDataBuilder;
import com.topband.sdk.boiler.util.BinaryUtils;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import org.android.agoo.common.AgooConstants;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p110io.reactivex.Observable;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.BiFunction;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class ThermostatActivity extends MyNavigationActivity implements TemperLayout.TempChangeListener {
    private static final int CHANGE_MODE = 2;
    private static final int CHANGE_OFF_FROST_TEMP = 9;
    private static final int CHANGE_OVERRIDE = 7;
    private static final int CHANGE_OVERRIDE_TEMP = 8;
    private static final int CHANGE_TEMP = 1;
    private static final int HIDE_PROGRESS = 3;
    private static final int NETWORK_ERROR = 4;
    private static final int SET_HOLIDAY_DATE = 5;
    private static final int SYNC_TIME = 6;
    private static final String TAG = "ThermostatActivity";
    public static final int THERMOSTAT_INFO_CHANGED = 1;
    public static final int THERMOSTAT_LOCATION_CHANGED = 2;
    /* access modifiers changed from: private */
    public boolean bGetBoilerInfo = false;
    /* access modifiers changed from: private */
    public boolean bOutTempPayState;
    /* access modifiers changed from: private */
    public BoilerInfoBean boilerInfoBean;
    @BindView(2131296356)
    BarViewBeSmart196 bvProgram;
    private Disposable checkDisposable;
    private ChooseLocationDialog chooseLocationDialog;
    private ChooseLocationFragment chooseLocationFragment;
    private String comfortTemp = "32";
    private String currentTemp = AgooConstants.ACK_REMOVE_PACKAGE;
    private int day = 0;
    private String devCode;
    private String econTemp = "25";
    /* access modifiers changed from: private */
    public int endTime;
    private Disposable getThermostatSubscribe;
    @BindView(2131296475)
    ImageView holidaySetImg;
    private int hour = 0;
    @BindView(2131296479)
    HorizontalScrollView hsvProgram;
    private boolean isNewWifiVersion = false;
    private boolean isOnline = true;
    private boolean isSubscribeing = false;
    @BindView(2131296507)
    ImageView ivHeating;
    @BindView(2131296509)
    ImageView ivHeating3;
    @BindView(2131296513)
    ImageView ivHoliday;
    @BindView(2131296537)
    ImageView ivLowBattery;
    @BindView(2131296514)
    ImageView ivManual;
    @BindView(2131296521)
    ImageView ivStandby;
    @BindView(2131296523)
    ImageView ivTempSettingIcon;
    @BindView(2131296525)
    ImageView ivTiming;
    @BindView(2131296528)
    ImageView ivWeather;
    @BindView(2131296569)
    LinearLayout llManual;
    @BindView(2131296573)
    LinearLayout llOff;
    @BindView(2131296575)
    LinearLayout llProgram;
    @BindView(2131296582)
    TemperLayout llTempSetting;
    @BindView(2131296585)
    LinearLayout llWeather;
    @BindView(2131296592)
    LinearLayout llholiday;
    @BindView(2131296601)
    LinearLayout mainLinearLayout;
    private String manualTemp = AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_OPPO;
    /* access modifiers changed from: private */
    public MyHandler mhandler;
    private int min = 0;
    private int newWifiVersion = 42;
    @BindView(2131296645)
    ImageView proHeatImageView;
    @BindView(2131296646)
    LinearLayout proHeatLayout;
    @BindView(2131296647)
    TextView proHeatTextview;
    private String productCode;
    private KProgressHUD progress;
    @BindView(2131296675)
    RelativeLayout rlHoliday;
    @BindView(2131296676)
    RelativeLayout rlManualButton;
    @BindView(2131296678)
    RelativeLayout rlNotice;
    @BindView(2131296681)
    RelativeLayout rlStandbyButton;
    @BindView(2131296685)
    RelativeLayout rlTimingButton;
    private ScaleAnimation saGone;
    private ScaleAnimation saVisible;
    private int serverTz = 0;
    private Disposable subscribe;
    private int subscribeCount = 0;
    private String targetTemp = "0";
    private MsgBean therBean;
    /* access modifiers changed from: private */
    public String therid;
    @BindView(2131296879)
    TextView tvCurrentTemp;
    @BindView(2131296428)
    TextView tvEndDate;
    @BindView(2131296823)
    TextView tvHeating;
    @BindView(2131296825)
    TextView tvHeating3;
    @BindView(2131296912)
    TextView tvLocation1;
    @BindView(2131296843)
    TextView tvNotice;
    @BindView(2131296883)
    TextView tvOutdoorTemperature;
    @BindView(2131296830)
    TextView tvWeatherLocation;
    private String wifi_box_code;
    private String xzWeatherTemp = "";

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_thermostat_ferroli;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getVersion();
        initWidget();
        initWidgetFunction();
        subscribe();
        initData(true);
        initData();
    }

    private void getVersion() {
        if (UserCenter.getChildDevicesBean() != null) {
            String version = UserCenter.getChildDevicesBean().getVersion();
            if (TextUtils.isEmpty(version)) {
                this.isNewWifiVersion = false;
            } else if (Integer.parseInt(version, 16) >= this.newWifiVersion) {
                LogUtil.m3321i(TAG, "转换version: " + Integer.parseInt(version, 16));
                this.isNewWifiVersion = true;
            } else {
                this.isNewWifiVersion = false;
            }
            LogUtil.m3321i(TAG, "version: " + version + ",isNewWifiVersion: " + this.isNewWifiVersion);
            this.isOnline = UserCenter.getChildDevicesBean().isOnline();
        }
    }

    private void Test(int i) {
        String binaryString = Integer.toBinaryString(i);
        Log.d("ErrorInfo", " 十进制错误信息 = " + i + " 二进制未补位 = " + binaryString);
        int length = 8 - binaryString.length();
        StringBuilder sb = new StringBuilder(binaryString);
        for (int i2 = 0; i2 < length; i2++) {
            sb.insert(0, "0");
        }
        Log.d(TAG, " FULL BINARY STR = " + sb.toString());
        char[] charArray = sb.toString().toCharArray();
        for (int i3 = 0; i3 < charArray.length; i3++) {
            Log.d(TAG, " INDEX = " + i3 + " VAL = " + charArray[i3]);
        }
    }

    /* access modifiers changed from: private */
    public void jumpToThermostatProgramActivity() {
        Intent intent = new Intent();
        intent.putExtra("isNewWifiVersion", this.isNewWifiVersion);
        intent.setClass(this, ThermostatProgramActivity.class);
        startActivityImmediately(intent);
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (checkNetOrOnline()) {
            return super.onNavigationItemSelected(menuItem);
        }
        switch (menuItem.getItemId()) {
            case C1683R.C1685id.muGatewayInfo:
                Intent intent = new Intent();
                intent.setClass(this, GatewayInfoActivity.class);
                intent.putExtra("is_daylight", this.therBean.isDayLightTime());
                startActivityImmediatelyForResult(intent, 1);
                break;
            case C1683R.C1685id.muHeatingHistory:
                Intent intent2 = new Intent();
                intent2.setClass(this, HeatingHistoryActivity.class);
                startActivityImmediately(intent2);
                break;
            case C1683R.C1685id.muProgram:
                jumpToThermostatProgramActivity();
                break;
            case C1683R.C1685id.muReflash:
                showProgress();
                initData();
                break;
        }
        return super.onNavigationItemSelected(menuItem);
    }

    private void initWidget() {
        EventBus.getDefault().register(this);
        this.mhandler = new MyHandler(this);
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muProgram, 10, getString(C1683R.string.menu_program)).setIcon(C1683R.C1684drawable.ic_menu_plan);
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muGatewayInfo, 12, getString(C1683R.string.menu_thermostat_infomation)).setIcon(C1683R.C1684drawable.ic_menu_dev_info);
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muReflash, 13, getString(C1683R.string.menu_refresh)).setIcon(C1683R.C1684drawable.ic_menu_reflash);
        setBaseDrawerItemVisible(false);
        this.saVisible = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        this.saVisible.setDuration(200);
        this.saGone = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f);
        this.saGone.setDuration(200);
        Calendar instance = Calendar.getInstance();
        this.day = Integer.parseInt(String.valueOf(instance.get(7))) - 1;
        this.hour = instance.get(11);
        this.min = instance.get(12);
        if (UserCenter.getResultsBean() != null) {
            this.productCode = UserCenter.getResultsBean().getProduct_code();
            this.devCode = UserCenter.getResultsBean().getDevice_code();
        }
        if (UserCenter.getChildDevicesBean() != null) {
            this.therid = UserCenter.getChildDevicesBean().getSdid();
            this.wifi_box_code = UserCenter.getResultsBean().getDevice_code();
            setTitle(UserCenter.getChildDevicesBean().getName());
        }
        if (this.devCode == null) {
            this.devCode = "";
        }
    }

    /* access modifiers changed from: protected */
    public void ShowProgressDialog(String str) {
        Log.e(TAG, "show progress dialog");
        KProgressHUD kProgressHUD = this.progress;
        if (kProgressHUD == null) {
            this.progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(str).setCancellable(true).setAnimationSpeed(1).setDimAmount(0.5f);
        } else {
            kProgressHUD.setLabel(str);
        }
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
    }

    /* access modifiers changed from: protected */
    public void HideProgressDialog() {
        Log.e(TAG, "hide progress dialog");
        KProgressHUD kProgressHUD = this.progress;
        if (kProgressHUD != null && kProgressHUD.isShowing()) {
            this.progress.dismiss();
        }
    }

    private void initWidgetFunction() {
        WidgetActionListener widgetActionListener = new WidgetActionListener();
        this.rlManualButton.setOnClickListener(widgetActionListener);
        this.rlTimingButton.setOnClickListener(widgetActionListener);
        this.rlHoliday.setOnClickListener(widgetActionListener);
        this.rlStandbyButton.setOnClickListener(widgetActionListener);
        this.bvProgram.setOnClickListener(widgetActionListener);
        this.bvProgram.setOnClickListener(widgetActionListener);
        this.llWeather.setOnClickListener(widgetActionListener);
        this.llTempSetting.setTempChangeListener(this);
        this.llProgram.setOnClickListener(widgetActionListener);
        this.hsvProgram.setOnClickListener(widgetActionListener);
        this.holidaySetImg.setOnClickListener(widgetActionListener);
        this.tvLocation1.setOnClickListener(widgetActionListener);
        this.bvProgram.setOnCanvasClickListener(new BarViewBeSmart196.OnCanvasClickListener() {
            public void onCanvasClick() {
                ThermostatActivity.this.jumpToThermostatProgramActivity();
            }
        });
        this.llWeather.setClickable(false);
    }

    private void getBoilerInfo() {
        this.subscribe = HttpUtils.getRetrofit().getBoilerInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BoilerInfoBean>() {
            public void accept(BoilerInfoBean boilerInfoBean) throws Exception {
                LogUtil.m3315d(ThermostatActivity.TAG, "onGet boiler info success");
                if (boilerInfoBean != null) {
                    if (!ThermostatActivity.this.bGetBoilerInfo) {
                        boolean unused = ThermostatActivity.this.bGetBoilerInfo = true;
                        Log.d(ThermostatActivity.TAG, " getBoilerInfo");
                        if (!ThermostatActivity.this.bOutTempPayState) {
                            if (boilerInfoBean.getLocation() != null && !boilerInfoBean.getLocation().trim().equals("")) {
                                ThermostatActivity.this.tvWeatherLocation.setText(boilerInfoBean.getLocation());
                                ThermostatActivity.this.tvLocation1.setText(boilerInfoBean.getLocation());
                                ThermostatActivity.this.tvWeatherLocation.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    public void onGlobalLayout() {
                                        int lineCount = ThermostatActivity.this.tvWeatherLocation.getLineCount();
                                        Log.d(ThermostatActivity.TAG, " getBoilerInfo tvWeatherLocation lines = " + lineCount);
                                        if (lineCount > 1) {
                                            ThermostatActivity.this.tvWeatherLocation.setVisibility(4);
                                            ThermostatActivity.this.tvLocation1.setVisibility(0);
                                            return;
                                        }
                                        ThermostatActivity.this.tvWeatherLocation.setVisibility(0);
                                        ThermostatActivity.this.tvLocation1.setVisibility(4);
                                    }
                                });
                                ThermostatActivity.this.tvWeatherLocation.setVisibility(ThermostatActivity.this.bOutTempPayState ? 8 : 0);
                            }
                            ThermostatActivity.this.getWeather(boilerInfoBean.getCity_id());
                        }
                    }
                    ThermostatActivity.this.setCurProgram((TextUtils.isEmpty(boilerInfoBean.getTimezone()) || boilerInfoBean.getTimezone().equals("null")) ? 12 : Integer.parseInt(boilerInfoBean.getTimezone()));
                    BoilerInfoBean unused2 = ThermostatActivity.this.boilerInfoBean = boilerInfoBean;
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.e(ThermostatActivity.TAG, "onGet boiler info fail", th);
                th.printStackTrace();
            }
        });
    }

    /* access modifiers changed from: private */
    public void setCurProgram(int i) {
        MsgBean msgBean = this.therBean;
        if (msgBean != null) {
            this.serverTz = (!msgBean.isDayLightTime() || !this.therBean.isInDayLightTime()) ? i - 12 : (i - 12) + 1;
            Log.d(TAG, " isDayLightTime = " + this.therBean.isDayLightTime() + " tz = " + this.serverTz);
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            StringBuilder sb = new StringBuilder();
            sb.append(" setCurProgram UTC timeZone offset = ");
            sb.append(timeZone.getRawOffset());
            Log.d(TAG, sb.toString());
            timeZone.setRawOffset(this.serverTz * 3600000);
            Log.d(TAG, " BoilerInfo timezone = " + this.serverTz);
            long j = this.therBean.getmWifiStamp();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(timeZone);
            String format = simpleDateFormat.format(Long.valueOf(j));
            Log.d(TAG, " Device Time data = " + format + " Device time stamp = " + j);
            Calendar instance = Calendar.getInstance();
            this.day = Integer.parseInt(String.valueOf(instance.get(7))) - 1;
            instance.setTimeZone(timeZone);
            instance.setTimeInMillis(j);
            this.hour = instance.get(11);
            this.min = instance.get(12);
            Log.d(TAG, " setCurProgram day = " + this.day + " hour = " + this.hour + " min = " + this.min);
            int[] program = this.therBean.getProgram(this.day);
            for (int i2 = 0; i2 < program.length; i2++) {
                this.bvProgram.setTemperature(i2, program[i2]);
            }
            this.bvProgram.clearBarStatus();
            this.bvProgram.setBarColors(((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 2.0f, ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
            this.bvProgram.setBarTextHidden(((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 2.0f, false);
            this.bvProgram.setBarTextColor(ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
            if (this.therBean != null) {
                BarViewBeSmart196 barViewBeSmart196 = this.bvProgram;
                barViewBeSmart196.setSideText(new String[]{"T1 " + this.therBean.getFrostTemp(), "T2 " + this.therBean.getEconTemp(), "T3 " + this.therBean.getComfortTemp()});
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int i3 = displayMetrics.widthPixels;
            int width = ((int) ((((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 48.0f) * ((float) this.bvProgram.getWidth()))) - (i3 / 2);
            if (width > 0) {
                this.hsvProgram.scrollTo(width, 0);
            }
            if (this.therBean.getMode() == 0) {
                if (this.therBean.getOverride() == 1) {
                    TemperLayout temperLayout = this.llTempSetting;
                    temperLayout.setText(this.therBean.getOverrideTemp() + "");
                } else {
                    TemperLayout temperLayout2 = this.llTempSetting;
                    temperLayout2.setText(parseAutoTemp() + "");
                }
                this.llTempSetting.setMaxValue(35.0f);
                this.llTempSetting.setMinValue(5.0f);
            }
        }
    }

    /* access modifiers changed from: private */
    public void getWeather(String str) {
        String str2 = LanguageUtil.getSetLanguageLocale(getApplicationContext()).equals(Locale.CHINA) ? Constant.WEATHER_LANGUAGE_ZH : "en";
        if (str != null && !str.trim().equals("")) {
            Log.d(TAG, " weather location  = " + str + " weather language = " + str2);
            getWeather(str, str2, Constant.WEATHER_TEMP_C);
        } else if (!this.bOutTempPayState) {
            Log.d(TAG, " getWeather bOutTempPayState = " + this.bOutTempPayState + " cityId = " + str);
            showChooseLocationDialog();
        }
    }

    private void getWeather(String str, String str2, String str3) {
        AppYunManager.getInstance().getWeather(str, str2, str3, new IAppYunResponseListener<XZWeatherResponse>() {
            public void onFailure(int i, String str) {
            }

            public void onSuccess(XZWeatherResponse xZWeatherResponse) {
                Log.d(ThermostatActivity.TAG, " on get weather success");
                ThermostatActivity.this.setWeatherInfo(xZWeatherResponse);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setWeatherInfo(XZWeatherResponse xZWeatherResponse) {
        Drawable drawable;
        this.xzWeatherTemp = xZWeatherResponse.getResults().get(0).getNow().getTemperature();
        Log.d(TAG, "xzWeatherTemp: " + this.xzWeatherTemp);
        sendNetWeatherTemp(this.xzWeatherTemp);
        if (!this.bOutTempPayState) {
            this.tvOutdoorTemperature.setText(this.xzWeatherTemp);
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

    public void onTemperatureChange(String str) {
        if (!checkNetOrOnline() && this.therBean != null) {
            Log.e(TAG, "temp: " + str + ",mode: " + this.therBean.getMode());
            if (this.therBean.getMode() == 0) {
                if (this.therBean.getOverride() == 0) {
                    this.mhandler.removeMessages(7);
                    Message obtainMessage = this.mhandler.obtainMessage(7);
                    obtainMessage.obj = true;
                    this.mhandler.sendMessageDelayed(obtainMessage, 2000);
                }
                this.mhandler.removeMessages(8);
                Message obtainMessage2 = this.mhandler.obtainMessage(8);
                obtainMessage2.obj = str;
                this.mhandler.sendMessageDelayed(obtainMessage2, 2000);
                return;
            }
            MsgBean msgBean = this.therBean;
            if (msgBean == null || msgBean.getMode() != 4) {
                this.targetTemp = str;
                this.mhandler.removeMessages(1);
                Message obtainMessage3 = this.mhandler.obtainMessage(1);
                obtainMessage3.obj = str;
                this.mhandler.sendMessageDelayed(obtainMessage3, 2000);
                return;
            }
            this.mhandler.removeMessages(9);
            Message obtainMessage4 = this.mhandler.obtainMessage(9);
            obtainMessage4.obj = str;
            this.mhandler.sendMessageDelayed(obtainMessage4, 2000);
        }
    }

    /* access modifiers changed from: private */
    public boolean checkNetOrOnline() {
        if (!NetworkUtils.isNetworkConnected(this.mContext)) {
            hideProgress();
            ToastUtil.showShortToast(this.mContext, getString(C1683R.string.public_network_error));
            return true;
        } else if (this.isOnline) {
            return false;
        } else {
            hideProgress();
            ToastUtil.showShortToast(this.mContext, getString(C1683R.string.device_list_device_disconnected));
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void hideProgress() {
        HideProgressDialog();
        this.mhandler.removeMessages(4);
    }

    /* access modifiers changed from: private */
    public void showProgress() {
        ShowProgressDialog((String) null);
        this.mhandler.sendEmptyMessageDelayed(4, 10000);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == 1) {
            String stringExtra = intent.getStringExtra("device_name");
            if (stringExtra != null) {
                setTitle(stringExtra);
            }
        } else if (i2 == 2) {
            Log.d(TAG, " 温控器信息修改地址");
            getBoilerInfo();
        }
    }

    private class WidgetActionListener implements View.OnClickListener {
        private WidgetActionListener() {
        }

        public void onClick(View view) {
            ThermostatActivity.this.showProgress();
            int id = view.getId();
            if (!ThermostatActivity.this.checkNetOrOnline()) {
                switch (id) {
                    case C1683R.C1685id.bvProgram:
                    case C1683R.C1685id.hsvProgram:
                    case C1683R.C1685id.llProgram:
                        ThermostatActivity.this.jumpToThermostatProgramActivity();
                        return;
                    case C1683R.C1685id.holiday_set:
                        ThermostatActivity thermostatActivity = ThermostatActivity.this;
                        new PickerDateDialog(thermostatActivity, thermostatActivity.endTime).show();
                        return;
                    case C1683R.C1685id.llWeather:
                    case C1683R.C1685id.tv_location1:
                        if (!ThermostatActivity.this.bOutTempPayState) {
                            ThermostatActivity.this.showChooseLocationDialog();
                            return;
                        }
                        return;
                    case C1683R.C1685id.rlHoliday:
                        if (ThermostatActivity.this.checkDataNPE()) {
                            ThermostatActivity.this.modeVisible(2, true);
                            ThermostatActivity.this.mhandler.removeMessages(2);
                            Message obtainMessage = ThermostatActivity.this.mhandler.obtainMessage(2);
                            obtainMessage.arg1 = 2;
                            ThermostatActivity.this.mhandler.sendMessageDelayed(obtainMessage, 2000);
                            return;
                        }
                        return;
                    case C1683R.C1685id.rlManualButton:
                        if (ThermostatActivity.this.checkDataNPE()) {
                            ThermostatActivity.this.modeVisible(1, true);
                            ThermostatActivity.this.mhandler.removeMessages(2);
                            Message obtainMessage2 = ThermostatActivity.this.mhandler.obtainMessage(2);
                            obtainMessage2.arg1 = 1;
                            ThermostatActivity.this.mhandler.sendMessageDelayed(obtainMessage2, 2000);
                            return;
                        }
                        return;
                    case C1683R.C1685id.rlStandbyButton:
                        if (ThermostatActivity.this.checkDataNPE()) {
                            ThermostatActivity.this.modeVisible(4, true);
                            ThermostatActivity.this.mhandler.removeMessages(2);
                            Message obtainMessage3 = ThermostatActivity.this.mhandler.obtainMessage(2);
                            obtainMessage3.arg1 = 4;
                            ThermostatActivity.this.mhandler.sendMessageDelayed(obtainMessage3, 2000);
                            return;
                        }
                        return;
                    case C1683R.C1685id.rlTimingButton:
                        if (ThermostatActivity.this.checkDataNPE()) {
                            ThermostatActivity.this.modeVisible(0, true);
                            ThermostatActivity.this.mhandler.removeMessages(2);
                            Message obtainMessage4 = ThermostatActivity.this.mhandler.obtainMessage(2);
                            obtainMessage4.arg1 = 0;
                            ThermostatActivity.this.mhandler.sendMessageDelayed(obtainMessage4, 2000);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private String checkTemp(float f) {
        double d = (double) f;
        if (d > 127.0d || d < -40.0d) {
            return !this.xzWeatherTemp.equals("") ? this.xzWeatherTemp : "--";
        }
        return String.format("%.1f", new Object[]{Float.valueOf(f)}).replace(",", ".");
    }

    public void onGetWeatherSuccess(WeatherBean weatherBean) {
        if (weatherBean != null) {
            this.tvWeatherLocation.setText(this.city);
            String icon = weatherBean.getIcon();
            char c = 65535;
            switch (icon.hashCode()) {
                case 47747:
                    if (icon.equals("01d")) {
                        c = 0;
                        break;
                    }
                    break;
                case 47757:
                    if (icon.equals("01n")) {
                        c = 1;
                        break;
                    }
                    break;
                case 47778:
                    if (icon.equals("02d")) {
                        c = 2;
                        break;
                    }
                    break;
                case 47788:
                    if (icon.equals("02n")) {
                        c = 3;
                        break;
                    }
                    break;
                case 47809:
                    if (icon.equals("03d")) {
                        c = 4;
                        break;
                    }
                    break;
                case 47819:
                    if (icon.equals("03n")) {
                        c = 5;
                        break;
                    }
                    break;
                case 47840:
                    if (icon.equals("04d")) {
                        c = 6;
                        break;
                    }
                    break;
                case 47850:
                    if (icon.equals("04n")) {
                        c = 7;
                        break;
                    }
                    break;
                case 47995:
                    if (icon.equals("09d")) {
                        c = 8;
                        break;
                    }
                    break;
                case 48005:
                    if (icon.equals("09n")) {
                        c = 9;
                        break;
                    }
                    break;
                case 48677:
                    if (icon.equals("10d")) {
                        c = 10;
                        break;
                    }
                    break;
                case 48687:
                    if (icon.equals("10n")) {
                        c = 11;
                        break;
                    }
                    break;
                case 48708:
                    if (icon.equals("11d")) {
                        c = 12;
                        break;
                    }
                    break;
                case 48718:
                    if (icon.equals("11n")) {
                        c = 13;
                        break;
                    }
                    break;
                case 48770:
                    if (icon.equals("13d")) {
                        c = 14;
                        break;
                    }
                    break;
                case 48780:
                    if (icon.equals("13n")) {
                        c = 15;
                        break;
                    }
                    break;
                case 52521:
                    if (icon.equals("50d")) {
                        c = 16;
                        break;
                    }
                    break;
                case 52531:
                    if (icon.equals("50n")) {
                        c = 17;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_sun);
                    return;
                case 2:
                case 3:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_cloud);
                    return;
                case 4:
                case 5:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_scattered);
                    return;
                case 6:
                case 7:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_scattered);
                    return;
                case 8:
                case 9:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_rain);
                    return;
                case 10:
                case 11:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_rain);
                    return;
                case 12:
                case 13:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_rain);
                    return;
                case 14:
                case 15:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_snow);
                    return;
                case 16:
                case 17:
                    this.ivWeather.setImageResource(C1683R.C1684drawable.weather_scattered);
                    return;
                default:
                    return;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLocation(LocationGroup locationGroup) {
        this.city = locationGroup.getName().getEn();
        Log.d(TAG, " system language is en and city = " + this.city);
        this.cityID = locationGroup.getCity_id();
        SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
        edit.putString("city", this.city);
        edit.putString("editor", this.cityID);
        edit.commit();
        this.tvWeatherLocation.setText(this.city);
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
                ThermostatActivity.this.hideProgress();
                LogUtil.m3315d(ThermostatActivity.TAG, "set user info success");
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.e(ThermostatActivity.TAG, "set user info fail", th);
                th.printStackTrace();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBeseEvent(BaseEvent baseEvent) {
        if (baseEvent.getEvent() == Event.SERVER_RESPONSE_SUCCESS) {
            hideProgress();
        } else if (baseEvent.getEvent() == Event.HOLIDAY_TIME) {
            this.mhandler.removeMessages(5);
            Message obtainMessage = this.mhandler.obtainMessage(5);
            obtainMessage.obj = Long.valueOf(((HolidayTimeEvent) baseEvent).getSeconds());
            this.mhandler.sendMessageDelayed(obtainMessage, 1000);
        } else if (baseEvent.getEvent() == Event.EVENT_CHANGE_TIMEZONE) {
            TimeZoneEvent timeZoneEvent = (TimeZoneEvent) baseEvent;
            this.mhandler.removeMessages(6);
            Message obtainMessage2 = this.mhandler.obtainMessage(6);
            obtainMessage2.arg1 = timeZoneEvent.getSeconds();
            obtainMessage2.arg2 = timeZoneEvent.isAutoDaylight() ? 1 : 0;
            this.mhandler.sendMessageDelayed(obtainMessage2, 1000);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MsgBean msgBean) {
        if (msgBean != null) {
            Log.i(TAG, "ssid: " + msgBean.getSsid());
            Log.i(TAG, "THEID: " + this.therid);
            if (this.therid.equalsIgnoreCase(msgBean.getSsid())) {
                this.therBean = msgBean;
                UserCenter.setMsgBean(msgBean);
                this.endTime = msgBean.getHolidayEndTime();
                Log.d(TAG, " onMessage 度假模式结束时间 = " + this.endTime + ",formatData: " + getformatDateStr((long) this.endTime));
                this.tvEndDate.setText(getformatDateStr((long) this.endTime));
                TextView textView = this.tvCurrentTemp;
                textView.setText(msgBean.getCurrentTemp() + "");
                ImageView imageView = this.ivHeating;
                int heatState = msgBean.getHeatState();
                int i = C1683R.C1684drawable.heating_on;
                imageView.setImageResource(heatState == 1 ? C1683R.C1684drawable.heating_on : C1683R.C1684drawable.heating_off);
                this.ivHeating3.setImageResource(msgBean.getHeatState() == 1 ? C1683R.C1684drawable.heating_on : C1683R.C1684drawable.heating_off);
                TextView textView2 = this.tvHeating;
                int heatState2 = msgBean.getHeatState();
                int i2 = C1683R.string.room_heating_status_heating;
                textView2.setText(heatState2 == 1 ? C1683R.string.room_heating_status_heating : C1683R.string.room_heating_status_stop);
                this.tvHeating3.setText(msgBean.getHeatState() == 1 ? C1683R.string.room_heating_status_heating : C1683R.string.room_heating_status_stop);
                TextView textView3 = this.proHeatTextview;
                if (msgBean.getTherHeatStatus() != 1) {
                    i2 = C1683R.string.room_heating_status_stop;
                }
                textView3.setText(i2);
                ImageView imageView2 = this.proHeatImageView;
                if (msgBean.getTherHeatStatus() != 1) {
                    i = C1683R.C1684drawable.heating_off;
                }
                imageView2.setImageResource(i);
                this.targetTemp = msgBean.getTargetTemp() + "";
                this.comfortTemp = msgBean.getComfortTemp() + "";
                this.currentTemp = msgBean.getCurrentTemp() + "";
                this.manualTemp = msgBean.getManualTemp() + "";
                Log.d(TAG, " onMessage mode = " + msgBean.getMode());
                int i3 = 0;
                modeVisible(msgBean.getMode(), false);
                TimeZone timeZone = TimeZone.getTimeZone("UTC");
                Log.d(TAG, " setCurProgram UTC timeZone offset = " + timeZone.getRawOffset());
                timeZone.setRawOffset(this.serverTz * 3600000);
                Log.d(TAG, " BoilerInfo timezone = " + this.serverTz);
                long j = this.therBean.getmWifiStamp();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                simpleDateFormat.setTimeZone(timeZone);
                String format = simpleDateFormat.format(Long.valueOf(j));
                Log.d(TAG, " Device Time data = " + format + " Device time stamp = " + j);
                Calendar instance = Calendar.getInstance();
                this.day = Integer.parseInt(String.valueOf(instance.get(7))) - 1;
                instance.setTimeZone(timeZone);
                instance.setTimeInMillis(j);
                this.hour = instance.get(11);
                this.min = instance.get(12);
                Log.d(TAG, " setCurProgram day = " + this.day + " hour = " + this.hour + " min = " + this.min);
                int[] program = this.therBean.getProgram(this.day);
                for (int i4 = 0; i4 < program.length; i4++) {
                    this.bvProgram.setTemperature(i4, program[i4]);
                }
                this.bvProgram.clearBarStatus();
                this.bvProgram.setBarColors(((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 2.0f, ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
                this.bvProgram.setBarTextHidden(((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 2.0f, false);
                this.bvProgram.setBarTextColor(ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
                BarViewBeSmart196 barViewBeSmart196 = this.bvProgram;
                barViewBeSmart196.setSideText(new String[]{"T1 " + this.therBean.getFrostTemp(), "T2 " + this.therBean.getEconTemp(), "T3 " + this.therBean.getComfortTemp()});
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = ((int) ((((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 48.0f) * ((float) this.bvProgram.getWidth()))) - (displayMetrics.widthPixels / 2);
                if (width > 0) {
                    this.hsvProgram.scrollTo(width, 0);
                }
                this.bOutTempPayState = msgBean.getOutProbeModel() == 1;
                this.tvWeatherLocation.setVisibility(this.bOutTempPayState ? 8 : 0);
                if (this.bOutTempPayState) {
                    this.tvOutdoorTemperature.setText(checkTemp(msgBean.getOutTemp()));
                }
                getBoilerInfo();
                this.llWeather.setClickable(true);
                DayLightEvent dayLightEvent = new DayLightEvent();
                dayLightEvent.setDayLight(msgBean.isDayLightTime());
                EventBus.getDefault().post(dayLightEvent);
                if (this.isNewWifiVersion) {
                    ImageView imageView3 = this.ivLowBattery;
                    if (!msgBean.isLowBattery()) {
                        i3 = 8;
                    }
                    imageView3.setVisibility(i3);
                    return;
                }
                this.ivLowBattery.setVisibility(8);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Constant.PARSE_DATA = true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        Constant.PARSE_DATA = false;
    }

    /* access modifiers changed from: private */
    public void modeVisible(int i, boolean z) {
        this.rlManualButton.setBackgroundColor(i == 1 ? getResources().getColor(C1683R.color.ferroli_green) : 0);
        this.rlTimingButton.setBackgroundColor(i == 0 ? getResources().getColor(C1683R.color.ferroli_green) : 0);
        this.rlHoliday.setBackgroundColor(i == 2 ? getResources().getColor(C1683R.color.ferroli_green) : 0);
        this.rlStandbyButton.setBackgroundColor(i == 4 ? getResources().getColor(C1683R.color.ferroli_green) : 0);
        this.ivManual.setImageResource(i == 1 ? C1683R.C1684drawable.manual_button : C1683R.C1684drawable.manual_button_gray);
        this.ivTiming.setImageResource(i == 0 ? C1683R.C1684drawable.timming_button : C1683R.C1684drawable.timming_button_gray);
        this.ivHoliday.setImageResource(i == 2 ? C1683R.C1684drawable.icon_holiday_normal : C1683R.C1684drawable.icon_holiday_gray);
        this.ivStandby.setImageResource(i == 4 ? C1683R.C1684drawable.standby_button : C1683R.C1684drawable.standby_button_gray);
        this.proHeatLayout.setVisibility(0);
        if (i == 0) {
            if (this.isNewWifiVersion) {
                this.llTempSetting.setCanTouch(true);
                this.llTempSetting.setMaxValue(35.0f);
                this.llTempSetting.setMinValue(5.0f);
                MsgBean msgBean = this.therBean;
                if (msgBean != null) {
                    if (msgBean.getOverride() == 1) {
                        TemperLayout temperLayout = this.llTempSetting;
                        temperLayout.setText(this.therBean.getOverrideTemp() + "");
                    } else {
                        TemperLayout temperLayout2 = this.llTempSetting;
                        temperLayout2.setText(parseAutoTemp() + "");
                    }
                }
            } else {
                this.llTempSetting.setCanTouch(false);
                TemperLayout temperLayout3 = this.llTempSetting;
                temperLayout3.setText(parseAutoTemp() + "");
            }
            this.llManual.setVisibility(8);
            this.llholiday.setVisibility(8);
            this.llOff.setVisibility(8);
            this.llProgram.setVisibility(0);
            if (z) {
                this.llProgram.startAnimation(this.saVisible);
            }
        } else if (i == 1) {
            this.llTempSetting.setCanTouch(true);
            this.llProgram.setVisibility(8);
            this.llholiday.setVisibility(8);
            this.llOff.setVisibility(8);
            this.llManual.setVisibility(0);
            Log.i(TAG, "econTemp: " + getEconTemp());
            if (this.isNewWifiVersion) {
                this.llTempSetting.setMinValue(5.0f);
            } else {
                this.llTempSetting.setMinValue(getEconTemp() + 0.5f);
            }
            this.llTempSetting.setMaxValue(35.0f);
            if (this.isNewWifiVersion) {
                this.llTempSetting.setText(this.manualTemp);
            } else {
                this.llTempSetting.setText(this.comfortTemp);
            }
            if (z) {
                this.llManual.startAnimation(this.saVisible);
            }
        } else if (i == 2) {
            this.llTempSetting.setCanTouch(false);
            this.llManual.setVisibility(8);
            this.llOff.setVisibility(0);
            this.llProgram.setVisibility(8);
            this.llholiday.setVisibility(0);
            if (this.therBean != null) {
                if (this.isNewWifiVersion) {
                    TemperLayout temperLayout4 = this.llTempSetting;
                    temperLayout4.setText(this.therBean.getOffFrostTemp() + "");
                } else {
                    TemperLayout temperLayout5 = this.llTempSetting;
                    temperLayout5.setText(this.therBean.getFrostTemp() + "");
                }
            }
            if (z) {
                this.llholiday.startAnimation(this.saVisible);
            }
        } else if (i == 4) {
            if (this.isNewWifiVersion) {
                this.llTempSetting.setCanTouch(true);
                this.llTempSetting.setMaxValue(15.0f);
                this.llTempSetting.setMinValue(5.0f);
                if (this.therBean != null) {
                    TemperLayout temperLayout6 = this.llTempSetting;
                    temperLayout6.setText(this.therBean.getOffFrostTemp() + "");
                }
            } else {
                this.llTempSetting.setCanTouch(false);
                if (this.therBean != null) {
                    TemperLayout temperLayout7 = this.llTempSetting;
                    temperLayout7.setText(this.therBean.getFrostTemp() + "");
                }
            }
            this.llManual.setVisibility(8);
            this.llProgram.setVisibility(8);
            this.llholiday.setVisibility(8);
            this.llOff.setVisibility(0);
            if (z) {
                this.llOff.startAnimation(this.saVisible);
            }
        }
    }

    private float parseAutoTemp() {
        MsgBean msgBean = UserCenter.getMsgBean();
        int i = msgBean.getProgram(this.day)[(this.hour * 2) + (this.min > 30 ? 1 : 0)];
        if (i == 2) {
            return msgBean.getComfortTemp();
        }
        if (i == 1) {
            return msgBean.getEconTemp();
        }
        return msgBean.getFrostTemp();
    }

    private float getEconTemp() {
        return UserCenter.getMsgBean().getEconTemp();
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

    private void unSubscribe() {
        AppYunManager.getInstance().unsubscribeDeviceMsg(this.productCode, this.devCode, (IAppYunManagerActionListener) null);
        AppYunManager.getInstance().unsubscribeDeviceStatus(this.productCode, this.devCode, (IAppYunManagerActionListener) null);
    }

    private void subscribe() {
        this.isSubscribeing = true;
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
        try {
            sendMessage(new MessageDataBuilder().batteryUsage(0).TheromstatMode(0).TheromstatUnit(0).TheromstatManualTemp(0.0f).TheromstatNightTemp(0).TheromstatCurrentTemp(0).TheromstatTargetTemp(0.0f).TheromstatTargetMaxTemp(0).TheromstatTargetMinTemp(0).TheromstatHeatWaterMaxTemp(0).TheromstatHeatWaterMinTemp(0).TheromstatComfortTemp(0.0f).TheromstatEconTemp(0.0f).TheromstatFrostTemp(0.0f).TheromstatOffFrostTemp(0.0f).thermostat(this.therid).TheromstatBoost(true).TheromstatOverride(true).TheromstatOverrideTemp(0.0f).TheromstatSeason(true).TheromstatHeatState(0).TheromstatHeatMode(0).TheromstatHeatAllow(true).TheromstatHoliday(0, 0).TheromstatTime(100).TheromstatRelay(true).TheromstatFrozenState(true).TheromstatFrozenAllow(true).TheromstatTempBand(0).TheromstatTempChangeRate(0).TheromstatTempCurve(0).TheromstatTemporaryState(true).TheromstatSensorInfluence(0).TheromstatSensorState(true).TheromstatReturnTempOn(0).TheromstatReturnTempOff(0).TheromstatBurnPeroid(0).TheromstatProgram(this.day).TheromstatPrestart(0).systemTimeSeconds(0).outTempProbeModel(0).daylightTime(true).inDaylightTime(0).outTemp(0.0f).query());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void initData(boolean z) {
        if (z) {
            checkNetOrOnline();
        }
        this.getThermostatSubscribe = Observable.zip(HttpUtils.getRetrofit().getTemperatureInfo(this.authorzation, MainApplication.appSlug, this.wifi_box_code, this.therid), HttpUtils.getRetrofit().getBoilerPointsInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug()), new BiFunction<TemperatureMessage, BoilerMessage, MsgBean>() {
            public MsgBean apply(TemperatureMessage temperatureMessage, BoilerMessage boilerMessage) throws Exception {
                if (temperatureMessage == null) {
                    return null;
                }
                Log.i(ThermostatActivity.TAG, "getTemperatureInfo： " + temperatureMessage.toString());
                MsgBean msgBean = new MsgBean();
                msgBean.setSsid(ThermostatActivity.this.therid);
                msgBean.setmWifiStamp(Long.valueOf(temperatureMessage.getUtcMilliseconds(), 16).longValue());
                msgBean.setHolidayEndTime(temperatureMessage.getRoomHolidayData().intValue());
                msgBean.setHeatState(temperatureMessage.getHeatState());
                msgBean.setTherHeatStatus(temperatureMessage.getTherHeatStatus());
                msgBean.setTargetTemp(temperatureMessage.getTargetTemp());
                msgBean.setComfortTemp(temperatureMessage.getComfortTemp());
                msgBean.setCurrentTemp(temperatureMessage.getRoomCurrentTemp());
                msgBean.setManualTemp(temperatureMessage.getRoomManualTemp());
                msgBean.setMode(temperatureMessage.getMode());
                msgBean.setOutProbeModel(temperatureMessage.getOutProbeModel());
                msgBean.setOutTemp(temperatureMessage.getOutTemp());
                msgBean.setDayLightTime(temperatureMessage.isDayLightTime());
                msgBean.setLowBattery(temperatureMessage.isLowBattery());
                msgBean.setInDayLightTime(temperatureMessage.isInDayLightTime());
                msgBean.setProgram(0, temperatureMessage.getProgram(0));
                msgBean.setProgram(1, temperatureMessage.getProgram(1));
                msgBean.setProgram(2, temperatureMessage.getProgram(2));
                msgBean.setProgram(3, temperatureMessage.getProgram(3));
                msgBean.setProgram(4, temperatureMessage.getProgram(4));
                msgBean.setProgram(5, temperatureMessage.getProgram(5));
                msgBean.setProgram(6, temperatureMessage.getProgram(6));
                msgBean.setFrostTemp(temperatureMessage.getFrostTemp());
                msgBean.setEconTemp(temperatureMessage.getEconTemp());
                msgBean.setComfortTemp(temperatureMessage.getComfortTemp());
                msgBean.setOverride(temperatureMessage.getOverride());
                msgBean.setOverrideTemp(temperatureMessage.getOverrideTemp());
                msgBean.setOffFrostTemp(temperatureMessage.getOffFrostTemp());
                if (boilerMessage != null) {
                    msgBean.setOutProbeModel(boilerMessage.getOutProbeModel());
                }
                return msgBean;
            }
        }).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MsgBean>() {
            public void accept(MsgBean msgBean) throws Exception {
                Log.i("testHide", "hide24");
                ThermostatActivity.this.hideProgress();
                ThermostatActivity.this.onMessage(msgBean);
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.e(ThermostatActivity.TAG, "getTemperatureInfo error", th);
                ThermostatActivity.this.hideProgress();
                th.printStackTrace();
                Context context = ThermostatActivity.this.mContext;
                ToastUtil.showShortToast(context, "getTemperatureInfo error: " + th.getMessage());
            }
        });
    }

    public void sendMessage(final byte[] bArr) {
        if (!checkNetOrOnline() && bArr != null) {
            LogUtil.m3315d(TAG, "send bytes " + BinaryUtils.bytes2String(bArr) + ",productCode: " + this.productCode + ",devCode: " + this.devCode);
            AppYunManager.getInstance().sendMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    Log.d(ThermostatActivity.TAG, "sendMessage " + BinaryUtils.bytes2String(bArr));
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    ToastUtil.showShortToast(ThermostatActivity.this.mContext, ThermostatActivity.this.getString(C1683R.string.public_failed_to_set));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        unSubscribe();
        Disposable disposable = this.checkDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.checkDisposable.dispose();
        }
        Disposable disposable2 = this.subscribe;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Disposable disposable3 = this.getThermostatSubscribe;
        if (disposable3 != null) {
            disposable3.dispose();
        }
        EventBus.getDefault().unregister(this);
        hideProgress();
        super.onDestroy();
    }

    public void setTargetTemp(String str) {
        if (!isFinishing()) {
            showProgress();
            if (this.isNewWifiVersion) {
                sendMessage(new MessageDataBuilder().TheromstatManualTemp(Float.valueOf(str).floatValue()).thermostat(this.therid).control());
            } else {
                sendMessage(new MessageDataBuilder().TheromstatComfortTemp(Float.valueOf(str).floatValue()).thermostat(this.therid).control());
            }
        }
    }

    public void changeMode(int i) {
        if (!isFinishing()) {
            long holidayTime = getHolidayTime(-1);
            MessageDataBuilder thermostat = new MessageDataBuilder().TheromstatMode(i).thermostat(this.therid);
            if (i == 2) {
                thermostat.TheromstatHoliday(holidayTime + 172800, getHolidayTime(-1) + 86400);
            }
            sendMessage(thermostat.control());
        }
    }

    public void ChangeOverride(boolean z) {
        if (!isFinishing()) {
            showProgress();
            sendMessage(new MessageDataBuilder().TheromstatOverride(z).thermostat(this.therid).control());
        }
    }

    public void ChangeOverrideTemp(String str) {
        if (!isFinishing()) {
            showProgress();
            sendMessage(new MessageDataBuilder().TheromstatOverrideTemp(Float.valueOf(str).floatValue()).thermostat(this.therid).control());
        }
    }

    public void ChangeOffFrostTemp(String str) {
        if (!isFinishing()) {
            showProgress();
            sendMessage(new MessageDataBuilder().TheromstatOffFrostTemp(Float.valueOf(str).floatValue()).thermostat(this.therid).control());
        }
    }

    private long getHolidayTime(long j) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(timeZone);
        Calendar instance = Calendar.getInstance();
        instance.setTimeZone(timeZone);
        String format = simpleDateFormat.format(j == -1 ? instance.getTime() : Long.valueOf(j));
        Log.d(TAG, " changeMode HOLIDAY DAY STR = " + format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        if (date != null) {
            currentTimeMillis = date.getTime() / 1000;
        }
        Log.d(TAG, " HOLIDAY CURRENT TIME = " + currentTimeMillis);
        return currentTimeMillis;
    }

    public void SetHolidayDate(long j) {
        if (!isFinishing()) {
            long holidayTime = getHolidayTime(j);
            LogUtil.m3315d(TAG, "SetHolidayDate endTime:" + j + " time = " + holidayTime);
            showProgress();
            sendMessage(new MessageDataBuilder().TheromstatHoliday(holidayTime, getHolidayTime(-1) + 86400).thermostat(this.therid).control());
        }
    }

    public void SyncTime(int i, int i2) {
        if (!isFinishing()) {
            showProgress();
            MessageDataBuilder messageDataBuilder = new MessageDataBuilder();
            boolean z = true;
            if (i2 != 1) {
                z = false;
            }
            sendMessage(messageDataBuilder.daylightTime(z).thermostat(this.therid).control());
        }
    }

    private boolean checkIsDayLight(int i) {
        boolean inDaylightTime = TimeZone.getDefault().inDaylightTime(new Date((long) (i * 1000)));
        Log.d(TAG, " checkIsDayLight bDayLight = " + inDaylightTime);
        return inDaylightTime;
    }

    public void showToast(String str) {
        if (!isFinishing()) {
            hideProgress();
            ToastUtil.showShortToast(this.mContext, str);
        }
    }

    private class MyHandler extends Handler {
        private WeakReference<ThermostatActivity> activityWeakReference;

        public MyHandler(ThermostatActivity thermostatActivity) {
            this.activityWeakReference = new WeakReference<>(thermostatActivity);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            ThermostatActivity thermostatActivity = (ThermostatActivity) this.activityWeakReference.get();
            if (thermostatActivity != null) {
                switch (message.what) {
                    case 1:
                        thermostatActivity.setTargetTemp((String) message.obj);
                        return;
                    case 2:
                        thermostatActivity.changeMode(message.arg1);
                        return;
                    case 4:
                        if (!thermostatActivity.isFinishing()) {
                            ThermostatActivity.this.checkDeviceStatus();
                            return;
                        }
                        return;
                    case 5:
                        thermostatActivity.SetHolidayDate(((Long) message.obj).longValue());
                        return;
                    case 6:
                        thermostatActivity.SyncTime(message.arg1, message.arg2);
                        return;
                    case 7:
                        thermostatActivity.ChangeOverride(((Boolean) message.obj).booleanValue());
                        return;
                    case 8:
                        thermostatActivity.ChangeOverrideTemp((String) message.obj);
                        return;
                    case 9:
                        thermostatActivity.ChangeOffFrostTemp((String) message.obj);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private String getformatDateStr(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long currentTimeMillis = (System.currentTimeMillis() / 1000) + 172800;
        if (j < currentTimeMillis) {
            return simpleDateFormat.format(new Date(currentTimeMillis * 1000));
        }
        return simpleDateFormat.format(new Date(j * 1000));
    }

    public int getResId(String str) {
        try {
            return C1683R.C1684drawable.class.getField(str).getInt(str);
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public void showChooseLocationDialog() {
        if (!this.bOutTempPayState) {
            if (getFragmentManager().findFragmentByTag("chooseLocationFragment") == null) {
                this.chooseLocationFragment = new ChooseLocationFragment();
            }
            if (this.chooseLocationFragment == null || getFragmentManager().findFragmentByTag("chooseLocationFragment") == null || this.chooseLocationFragment.getDialog() == null || !this.chooseLocationFragment.getDialog().isShowing()) {
                this.chooseLocationFragment.show(getFragmentManager(), "chooseLocationFragment");
            }
        }
    }

    private void chooseLocation() {
        if (!this.bOutTempPayState) {
            if (this.chooseLocationDialog == null) {
                this.chooseLocationDialog = new ChooseLocationDialog(this);
            }
            if (!this.chooseLocationDialog.isShowing()) {
                this.chooseLocationDialog.show();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MqttEvent mqttEvent) {
        if (mqttEvent.getState() == 1 && NetworkUtils.isNetworkConnected(this.mContext)) {
            Log.d(TAG, " Ther onEvent MQTT CONNECT SUCCESS Subscribe");
            subscribe();
        }
    }

    /* access modifiers changed from: private */
    public void checkDeviceStatus() {
        if (!MQTTManager.getInstance(this).checkMQTTStatus()) {
            Log.d(TAG, " thermostat mqtt disconnect reconnect");
        } else {
            this.checkDisposable = HttpUtils.getRetrofit().getDeviceInfo(this.authorzation, UserCenter.getUserInfo().getSlug(), UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DeviceListResponse.ResultsBean>() {
                public void accept(DeviceListResponse.ResultsBean resultsBean) throws Exception {
                    if (!resultsBean.isOnline()) {
                        ThermostatActivity thermostatActivity = ThermostatActivity.this;
                        thermostatActivity.showToast(thermostatActivity.getString(C1683R.string.dialog_device_offline));
                        return;
                    }
                    ThermostatActivity thermostatActivity2 = ThermostatActivity.this;
                    thermostatActivity2.showToast(thermostatActivity2.getString(C1683R.string.dialog_device_not_response));
                    ThermostatActivity.this.initData(false);
                }
            }, new Consumer<Throwable>() {
                public void accept(Throwable th) throws Exception {
                    th.printStackTrace();
                    ThermostatActivity.this.hideProgress();
                    ThermostatActivity thermostatActivity = ThermostatActivity.this;
                    thermostatActivity.showToast(thermostatActivity.getString(C1683R.string.dialog_device_not_response));
                }
            });
        }
    }

    private void sendJsonMessage(byte[] bArr) {
        if (bArr != null) {
            AppYunManager.getInstance().sendJsonMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    if (!ThermostatActivity.this.isFinishing()) {
                        Log.d(ThermostatActivity.TAG, " sendJsonMessage Remove Thermostat Msg Send Success");
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    if (!ThermostatActivity.this.isFinishing()) {
                        Log.d(ThermostatActivity.TAG, " sendJsonMessage Remove Thermostat Msg Send Failed");
                        ToastUtil.showShortToast(ThermostatActivity.this.mContext, ThermostatActivity.this.getString(C1683R.string.public_failed_to_set));
                    }
                }
            });
        }
    }

    private void sendNetWeatherTemp(String str) {
        sendJsonMessage("{\"type\":\"status\",\"data\":{\"gatway\":{\"temperature\":\"temperatureData\"}}}".replace("temperatureData", str).getBytes());
    }

    /* access modifiers changed from: private */
    public boolean checkDataNPE() {
        return this.therBean != null;
    }
}
