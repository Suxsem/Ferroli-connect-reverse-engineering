package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.p000v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.XZWeatherResponse;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.BoilerBean;
import com.szacs.ferroliconnect.bean.BoilerInfoBean;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.bean.LocationGroup;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.Event;
import com.szacs.ferroliconnect.fragment.ChooseLocationFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.ACache;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.taobao.accs.common.Constants;
import com.topband.sdk.boiler.MessageDataBuilder;
import com.topband.sdk.boiler.util.BinaryUtils;
import com.triggertrap.seekarc.SeekArc;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
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

public class BoilerActivity extends MyNavigationActivity implements SeekArc.OnSeekArcChangeListener {
    private static final int CHANGE_MODE = 2;
    private static final int CHANGE_TEMP = 1;
    private static final int HIDE_PROGRESS = 3;
    private static final int NETWORK_ERROR = 4;
    private static final String TAG = "BoilerActivity";
    @BindView(2131296564)
    LinearLayout LLheat;
    @BindView(2131296270)
    TextView OFFTv;
    @BindView(2131296271)
    RelativeLayout RlSummer;
    @BindView(2131296723)
    SeekArc SummerSeekArc;
    @BindView(2131296753)
    TextView SummerTargetTv;
    /* access modifiers changed from: private */
    public boolean allowControl;
    /* access modifiers changed from: private */
    public boolean allowFlash = true;
    private boolean bGetBoilerInfo = false;
    private boolean bOutTempPayState = false;
    private boolean bShowLocation = false;
    /* access modifiers changed from: private */
    public BoilerInfoBean boilerInfoBean;
    private String devCode;
    private int dhwMin;
    private int dhwValue;
    private Disposable disposable;
    private Disposable getBoilerSubscribe;
    @BindView(2131296474)
    ImageView heatIcon;
    @BindView(2131296473)
    LinearLayout heatLayout;
    private int heatMin;
    private int heatValue;
    @BindView(2131296504)
    ImageView ivError;
    @BindView(2131296505)
    ImageView ivFlameStatus;
    @BindView(2131296507)
    ImageView ivHeatingStatus;
    @BindView(2131296517)
    ImageView ivReset;
    @BindView(2131296521)
    ImageView ivStandby;
    @BindView(2131296522)
    ImageView ivSummer;
    @BindView(2131296528)
    ImageView ivWeather;
    @BindView(2131296543)
    ImageView ivWifiSignal;
    @BindView(2131296529)
    ImageView ivWinter;
    @BindView(2131296585)
    LinearLayout llWeather;
    @BindView(2131296272)
    LinearLayout llWinter;
    @BindView(2131296315)
    LinearLayout mainLinearLayout;
    /* access modifiers changed from: private */
    public MyHandler mhandler;
    @BindView(2131296608)
    TextView modeTv;
    private String productCode;
    @BindView(2131296680)
    RelativeLayout rlStandby;
    @BindView(2131296682)
    RelativeLayout rlSummer;
    @BindView(2131296688)
    RelativeLayout rlWinter;
    @BindView(2131296722)
    SeekArc seekArcDhw;
    @BindView(2131296724)
    SeekArc seekArcTarget;
    @BindView(2131296877)
    TextView tvDhwTargetTemp;
    @BindView(2131296808)
    TextView tvError;
    @BindView(2131296809)
    TextView tvFlameStatus;
    @BindView(2131296826)
    TextView tvHeatingCurrentTemp;
    @BindView(2131296823)
    TextView tvHeatingStatus;
    @BindView(2131296856)
    TextView tvHeatingTargetTemp;
    @BindView(2131296883)
    TextView tvOutdoorTemperature;
    @BindView(2131296850)
    TextView tvSystemPressure;
    @BindView(2131296830)
    TextView tvWeatherLocation;
    private String xzWeatherTemp = "";

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_boiler_ferroli;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        initWidget();
        initWidgetFunction();
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == C1683R.C1685id.muGatewayInfo) {
            Intent intent = new Intent();
            intent.setClass(this, BoilerInfoActivity.class);
            startActivityImmediately(intent);
        } else if (itemId == C1683R.C1685id.muReflash) {
            ShowProgressDialog((String) null);
            initData();
        }
        return super.onNavigationItemSelected(menuItem);
    }

    private void getBoilerInfo() {
        HttpUtils.getRetrofit().getBoilerInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BoilerInfoBean>() {
            public void accept(BoilerInfoBean boilerInfoBean) throws Exception {
                LogUtil.m3315d(BoilerActivity.TAG, "onGet boiler info success");
                BoilerActivity.this.getWeather(boilerInfoBean.getCity_id());
                BoilerInfoBean unused = BoilerActivity.this.boilerInfoBean = boilerInfoBean;
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(BoilerActivity.TAG, "onGet boiler info fail");
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
                Log.d(BoilerActivity.TAG, " on get weather success");
                BoilerActivity.this.setWeatherInfo(xZWeatherResponse);
            }

            public void onFailure(int i, String str) {
                ToastUtil.showShortToast(BoilerActivity.this.mContext, HttpError.getMessage(BoilerActivity.this.mContext, i));
            }
        });
    }

    /* access modifiers changed from: private */
    public void setWeatherInfo(XZWeatherResponse xZWeatherResponse) {
        this.xzWeatherTemp = xZWeatherResponse.getResults().get(0).getNow().getTemperature();
        if (!this.bOutTempPayState) {
            this.tvOutdoorTemperature.setText(this.xzWeatherTemp);
        }
        this.tvWeatherLocation.setVisibility(this.bOutTempPayState ? 8 : 0);
        this.tvWeatherLocation.setText(xZWeatherResponse.getResults().get(0).getLocation().getName());
        int resId = getResId(Constant.WEATHER_IMG_PREFEX + xZWeatherResponse.getResults().get(0).getNow().getCode());
        Log.d(TAG, " setWeatherInfo weather img  = " + resId);
        this.ivWeather.setImageDrawable(getResources().getDrawable(resId));
    }

    /* access modifiers changed from: protected */
    public void initWidget() {
        EventBus.getDefault().register(this);
        this.mhandler = new MyHandler(this);
        int i = 0;
        this.allowControl = getIntent().getBooleanExtra("AllowControl", false);
        this.seekArcDhw.setEnabled(this.allowControl);
        this.seekArcTarget.setEnabled(this.allowControl);
        this.SummerSeekArc.setEnabled(this.allowControl);
        TextView textView = this.modeTv;
        if (this.allowControl) {
            i = 8;
        }
        textView.setVisibility(i);
        if (UserCenter.getResultsBean() != null) {
            this.productCode = UserCenter.getResultsBean().getProduct_code();
            this.devCode = UserCenter.getResultsBean().getDevice_code();
        }
        if (this.devCode == null) {
            this.devCode = "";
        }
        BoilerBean boilerBean = (BoilerBean) ACache.get(this.mContext, this.devCode).getAsObject("boiler");
        if (boilerBean != null) {
            onMessage(boilerBean);
        }
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muGatewayInfo, 11, getString(C1683R.string.boiler_tech_info)).setIcon(C1683R.C1684drawable.ic_menu_dev_info);
        this.navigationView.getMenu().add(C1683R.C1685id.nav_grp, C1683R.C1685id.muReflash, 13, getString(C1683R.string.menu_refresh)).setIcon(C1683R.C1684drawable.ic_menu_reflash);
        setTitle(getString(C1683R.string.boiler_title));
    }

    /* access modifiers changed from: protected */
    public void initWidgetFunction() {
        WidgetOnClickListener widgetOnClickListener = new WidgetOnClickListener();
        this.rlWinter.setOnClickListener(widgetOnClickListener);
        this.rlSummer.setOnClickListener(widgetOnClickListener);
        this.rlStandby.setOnClickListener(widgetOnClickListener);
        this.ivReset.setOnClickListener(widgetOnClickListener);
        this.llWeather.setOnClickListener(widgetOnClickListener);
        this.seekArcTarget.setOnSeekArcChangeListener(this);
        this.seekArcDhw.setOnSeekArcChangeListener(this);
        this.SummerSeekArc.setOnSeekArcChangeListener(this);
        this.llWeather.setClickable(false);
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
    }

    public void onProgressChanged(SeekArc seekArc, int i, boolean z) {
        switch (seekArc.getId()) {
            case C1683R.C1685id.seekArcDhw:
                this.dhwValue = this.dhwMin + i;
                TextView textView = this.tvDhwTargetTemp;
                textView.setText(this.dhwValue + "");
                return;
            case C1683R.C1685id.seekArcSummerDhw:
                this.dhwValue = this.dhwMin + i;
                TextView textView2 = this.SummerTargetTv;
                textView2.setText(this.dhwValue + "");
                return;
            case C1683R.C1685id.seekArcTarget:
                this.heatValue = this.heatMin + i;
                TextView textView3 = this.tvHeatingTargetTemp;
                textView3.setText(this.heatValue + "");
                return;
            default:
                return;
        }
    }

    public void onStartTrackingTouch(SeekArc seekArc) {
        this.allowFlash = false;
    }

    public void onStopTrackingTouch(SeekArc seekArc) {
        switch (seekArc.getId()) {
            case C1683R.C1685id.seekArcDhw:
            case C1683R.C1685id.seekArcSummerDhw:
                this.mhandler.removeMessages(1);
                Message obtainMessage = this.mhandler.obtainMessage(1);
                obtainMessage.arg1 = 1;
                obtainMessage.arg2 = this.dhwValue;
                this.mhandler.sendMessageDelayed(obtainMessage, 3000);
                return;
            case C1683R.C1685id.seekArcTarget:
                this.mhandler.removeMessages(1);
                Message obtainMessage2 = this.mhandler.obtainMessage(1);
                obtainMessage2.arg1 = 0;
                obtainMessage2.arg2 = this.heatValue;
                this.mhandler.sendMessageDelayed(obtainMessage2, 3000);
                return;
            default:
                return;
        }
    }

    protected class WidgetOnClickListener implements View.OnClickListener {
        protected WidgetOnClickListener() {
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case C1683R.C1685id.llWeather:
                    BoilerActivity.this.showChooseLocationDialog();
                    return;
                case C1683R.C1685id.rlStandby:
                    if (BoilerActivity.this.allowControl) {
                        BoilerActivity.this.setWorkMode(2);
                        BoilerActivity.this.mhandler.removeMessages(2);
                        Message obtainMessage = BoilerActivity.this.mhandler.obtainMessage(2);
                        obtainMessage.arg1 = 2;
                        BoilerActivity.this.mhandler.sendMessageDelayed(obtainMessage, 3000);
                        boolean unused = BoilerActivity.this.allowFlash = false;
                        return;
                    }
                    return;
                case C1683R.C1685id.rlSummer:
                    if (BoilerActivity.this.allowControl) {
                        BoilerActivity.this.setWorkMode(0);
                        BoilerActivity.this.mhandler.removeMessages(2);
                        Message obtainMessage2 = BoilerActivity.this.mhandler.obtainMessage(2);
                        obtainMessage2.arg1 = 0;
                        BoilerActivity.this.mhandler.sendMessageDelayed(obtainMessage2, 3000);
                        boolean unused2 = BoilerActivity.this.allowFlash = false;
                        return;
                    }
                    return;
                case C1683R.C1685id.rlWinter:
                    if (BoilerActivity.this.allowControl) {
                        BoilerActivity.this.setWorkMode(1);
                        BoilerActivity.this.mhandler.removeMessages(2);
                        Message obtainMessage3 = BoilerActivity.this.mhandler.obtainMessage(2);
                        obtainMessage3.arg1 = 1;
                        BoilerActivity.this.mhandler.sendMessageDelayed(obtainMessage3, 3000);
                        boolean unused3 = BoilerActivity.this.allowFlash = false;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void updateBoilerInfo(String str, String str2, String str3, String str4, String str5, String str6) {
        ShowProgressDialog((String) null);
        HashMap hashMap = new HashMap();
        hashMap.put(Constants.KEY_BRAND, str);
        hashMap.put(Constants.KEY_MODEL, str2);
        hashMap.put("sn", str3);
        hashMap.put(com.p107tb.appyunsdk.Constant.WEATHER_LOCATION, str4);
        hashMap.put("city_id", str5);
        hashMap.put("installation_date", str6);
        HttpUtils.getRetrofit().UpdateBoilerInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug(), hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) throws Exception {
                LogUtil.m3315d(BoilerActivity.TAG, "set user info success");
                Log.i("testHide", "hide3");
                BoilerActivity.this.HideProgressDialog();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(BoilerActivity.TAG, "set user info fail");
                Log.i("testHide", "hide4");
                BoilerActivity.this.HideProgressDialog();
                th.printStackTrace();
            }
        });
    }

    private void setMaxValue(SeekArc seekArc, int i) {
        try {
            Field declaredField = SeekArc.class.getDeclaredField("mMax");
            declaredField.setAccessible(true);
            declaredField.set(seekArc, Integer.valueOf(i));
            declaredField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLocation(LocationGroup locationGroup) {
        this.city = locationGroup.getName().getZh();
        this.cityID = locationGroup.getCity_id();
        SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
        edit.putString("city", this.city);
        edit.putString("editor", this.cityID);
        edit.commit();
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
        initData();
    }

    private String checkTemp(float f) {
        double d = (double) f;
        if (d > 127.0d || d < -40.0d) {
            return !this.xzWeatherTemp.equals("") ? this.xzWeatherTemp : "--";
        }
        return String.format("%.1f", new Object[]{Float.valueOf(f)}).replace(",", ".");
    }

    /* access modifiers changed from: protected */
    public void setWorkMode(int i) {
        int i2 = 0;
        if (!(i == 1 || i == 0)) {
            i = 0;
        }
        this.rlWinter.setBackgroundColor(i == 1 ? getResources().getColor(C1683R.color.ferroli_green) : 0);
        this.rlSummer.setBackgroundColor(i == 0 ? getResources().getColor(C1683R.color.ferroli_green) : 0);
        this.ivWinter.setImageResource(i == 1 ? C1683R.C1684drawable.winter_button : C1683R.C1684drawable.winter_button_gray);
        this.ivSummer.setImageResource(i == 0 ? C1683R.C1684drawable.summer_button : C1683R.C1684drawable.summer_button_gray);
        this.llWinter.setVisibility(i == 1 ? 0 : 8);
        RelativeLayout relativeLayout = this.RlSummer;
        if (i != 0) {
            i2 = 8;
        }
        relativeLayout.setVisibility(i2);
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

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        subscribe();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        unSubscribe();
    }

    private void unSubscribe() {
        AppYunManager.getInstance().unsubscribeDeviceMsg(this.productCode, this.devCode, (IAppYunManagerActionListener) null);
        AppYunManager.getInstance().unsubscribeDeviceStatus(this.productCode, this.devCode, (IAppYunManagerActionListener) null);
    }

    private void subscribe() {
        ShowProgressDialog((String) null);
        this.mhandler.sendEmptyMessageDelayed(4, 10000);
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
                BoilerActivity.this.initData();
            }
        });
    }

    /* access modifiers changed from: private */
    public void initData() {
        sendMessage(new MessageDataBuilder().warmingWaterRealTemp(0).warmingWaterTargetTemp(0).warmingMaxTemp(0).warmingMinTemp(0).warmingMaxSetPoint(0).bathWaterRealTemp(0).bathWaterTargetTemp(0).bathMaxTemp(0).bathMinTemp(0).windPressureSwitch(true).winterSummerMode(0).waterSupplySwitch(true).flameStatus(false).warmingUpStatus(true).deviceError().ErrorInfo().fuelGasType().deviceFunctionType().deviceInstallationType().boilerControllType().waterPressure(0.0f).pwmValue(0).ReturnWaterTemp(0).OutWaterTemp(0).outTemp(0.0f).FlueTemperature(0).WaterRate(0.0f).outTemperaturePayState(0).boiler().query());
        this.mhandler.sendEmptyMessageDelayed(4, 10000);
    }

    private void sendMessage(final byte[] bArr) {
        if (bArr != null) {
            LogUtil.m3315d(TAG, "send bytes " + Arrays.toString(bArr));
            BinaryUtils.printBytes(TAG, bArr);
            AppYunManager.getInstance().sendMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    Log.d(BoilerActivity.TAG, "sendMessage " + BinaryUtils.bytes2String(bArr));
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    ToastUtil.showShortToast(BoilerActivity.this.mContext, BoilerActivity.this.getString(C1683R.string.public_failed_to_set));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBeseEvent(BaseEvent baseEvent) {
        if (baseEvent.getEvent() == Event.SERVER_RESPONSE_SUCCESS) {
            Log.i("testHide", "hide5");
            HideProgressDialog();
            this.mhandler.removeMessages(4);
            this.allowFlash = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(BoilerBean boilerBean) {
        if (boilerBean != null && this.allowFlash) {
            UserCenter.setBoilerBean(boilerBean);
            this.dhwValue = (int) boilerBean.getBathTargetTemp();
            this.dhwMin = (int) boilerBean.getBathMinTemp();
            this.heatMin = (int) boilerBean.getHeatMinTemp();
            this.heatValue = (int) boilerBean.getHeatTargetTemp();
            setWorkMode(boilerBean.getMode());
            TextView textView = this.tvHeatingCurrentTemp;
            textView.setText(Math.round(boilerBean.getHeatCurrentTemp()) + "");
            this.tvSystemPressure.setText(String.valueOf(boilerBean.getWaterPressure()));
            TextView textView2 = this.tvHeatingTargetTemp;
            textView2.setText(Math.round(boilerBean.getHeatTargetTemp()) + "");
            setMaxValue(this.seekArcTarget, (int) (boilerBean.getHeatMaxSetTemp() - boilerBean.getHeatMinTemp()));
            this.seekArcTarget.setProgress((int) (boilerBean.getHeatTargetTemp() - boilerBean.getHeatMinTemp()));
            TextView textView3 = this.tvDhwTargetTemp;
            textView3.setText(Math.round(boilerBean.getBathTargetTemp()) + "");
            setMaxValue(this.seekArcDhw, (int) (boilerBean.getBathMaxTemp() - boilerBean.getBathMinTemp()));
            this.seekArcDhw.setProgress((int) (boilerBean.getBathTargetTemp() - boilerBean.getBathMinTemp()));
            TextView textView4 = this.SummerTargetTv;
            textView4.setText(Math.round(boilerBean.getBathTargetTemp()) + "");
            setMaxValue(this.SummerSeekArc, (int) (boilerBean.getBathMaxTemp() - boilerBean.getBathMinTemp()));
            this.SummerSeekArc.setProgress((int) (boilerBean.getBathTargetTemp() - boilerBean.getBathMinTemp()));
            this.tvHeatingStatus.setText(getString(boilerBean.getHeatStatus() == 1 ? C1683R.string.boiler_heating_status_heating : C1683R.string.boiler_heating_status_stop));
            this.tvFlameStatus.setText(getString(boilerBean.getFlameStatus() == 1 ? C1683R.string.boiler_tech_have_flame : C1683R.string.boiler_tech_no_flame));
            this.ivHeatingStatus.setImageResource(boilerBean.getHeatStatus() == 1 ? C1683R.C1684drawable.heating_enable : C1683R.C1684drawable.heating_disable);
            this.ivFlameStatus.setImageResource(boilerBean.getFlameStatus() == 1 ? C1683R.C1684drawable.flame : C1683R.C1684drawable.noflame);
            this.heatIcon.setImageResource(boilerBean.getHeatStatus() == 1 ? C1683R.C1684drawable.heating_icon : C1683R.C1684drawable.heating_icon_gray);
            Log.d(TAG, " errInfo = " + boilerBean.getErrorInfo());
            if (boilerBean.getErrorCode() != 0) {
                String str = boilerBean.getErrorInfo() == 1 ? "A" : "F";
                TextView textView5 = this.tvError;
                textView5.setText(str + boilerBean.getErrorCode());
                this.tvError.setTextColor(ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
                this.ivError.setImageResource(C1683R.C1684drawable.error);
            } else {
                this.tvError.setText(getString(C1683R.string.boiler_normal));
                this.tvError.setTextColor(ContextCompat.getColor(this, C1683R.color.cloudwarm_grey));
                this.ivError.setImageResource(C1683R.C1684drawable.normal);
            }
            int i = 0;
            this.bOutTempPayState = boilerBean.getOutProbeModel() == 1;
            this.llWeather.setClickable(true);
            TextView textView6 = this.tvWeatherLocation;
            if (this.bOutTempPayState) {
                i = 8;
            }
            textView6.setVisibility(i);
            if (this.bOutTempPayState) {
                this.tvOutdoorTemperature.setText(checkTemp(boilerBean.getOutTemp()));
            }
            if (!this.bGetBoilerInfo) {
                this.bGetBoilerInfo = true;
                Log.d(TAG, " getBoilerInfo");
                getBoilerInfo();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (UserCenter.getBoilerBean() != null) {
            ACache.get(this.mContext, this.devCode).put("boiler", (Serializable) UserCenter.getBoilerBean());
        }
        Disposable disposable2 = this.disposable;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Disposable disposable3 = this.getBoilerSubscribe;
        if (disposable3 != null) {
            disposable3.dispose();
        }
        super.onDestroy();
    }

    public void setTemp(int i, int i2) {
        if (!isFinishing()) {
            ShowProgressDialog((String) null);
            this.allowFlash = false;
            if (i == 1) {
                sendMessage(new MessageDataBuilder().bathWaterTargetTemp(i2).boiler().control());
            } else {
                sendMessage(new MessageDataBuilder().warmingWaterTargetTemp(i2).boiler().control());
            }
            this.mhandler.sendEmptyMessageDelayed(4, 10000);
        }
    }

    public void changeMode(int i) {
        if (!isFinishing()) {
            ShowProgressDialog((String) null);
            this.allowFlash = false;
            sendMessage(new MessageDataBuilder().winterSummerMode(i).boiler().control());
            this.mhandler.sendEmptyMessageDelayed(4, 10000);
        }
    }

    public void showToast(String str) {
        if (!isFinishing()) {
            Log.i("testHide", "hide6");
            HideProgressDialog();
            this.allowFlash = true;
            ToastUtil.showShortToast(this.mContext, str);
        }
    }

    private class MyHandler extends Handler {
        private WeakReference<BoilerActivity> activityWeakReference;

        public MyHandler(BoilerActivity boilerActivity) {
            this.activityWeakReference = new WeakReference<>(boilerActivity);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            BoilerActivity boilerActivity = (BoilerActivity) this.activityWeakReference.get();
            if (boilerActivity != null) {
                int i = message.what;
                if (i == 1) {
                    boilerActivity.setTemp(message.arg1, message.arg2);
                } else if (i == 2) {
                    boilerActivity.changeMode(message.arg1);
                } else if (i != 3 && i == 4 && !boilerActivity.isFinishing()) {
                    boilerActivity.showToast(boilerActivity.getString(C1683R.string.dialog_device_not_response));
                    BoilerActivity.this.initData();
                }
            }
        }
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
            new ChooseLocationFragment().show(getFragmentManager(), "chooseLocationFragment");
        }
    }
}
