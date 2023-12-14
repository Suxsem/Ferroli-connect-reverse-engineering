package com.szacs.ferroliconnect.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.DeviceListResponse;
import com.p107tb.appyunsdk.bean.ModifyDeviceMsgResponse;
import com.p107tb.appyunsdk.bean.XZWeatherResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.BoilerInfoBean;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.bean.LocationGroup;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.event.DayLightEvent;
import com.szacs.ferroliconnect.event.TimeZoneEvent;
import com.szacs.ferroliconnect.fragment.ChooseLocationFragment;
import com.szacs.ferroliconnect.fragment.TimeZoneFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.ACache;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.FileUtil;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.taobao.accs.common.Constants;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p109de.hdodenhof.circleimageview.CircleImageView;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class GatewayInfoActivity extends MyCameraRequestActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private static final String TAG = "GatewayInfoActivity";
    /* access modifiers changed from: private */
    public BoilerInfoBean boilerInfoBean;
    @BindView(2131296382)
    CircleImageView civHouse;
    /* access modifiers changed from: private */
    public String devCode;
    private String deviceName;
    /* access modifiers changed from: private */
    public String deviceSlug;
    private String folder = "";
    private String imageFilePath;
    private boolean isDayLight;
    @BindView(2131296502)
    ImageView ivEditGatewayName;
    @BindView(2131296583)
    LinearLayout llTimeZone;
    @BindView(2131296586)
    LinearLayout llWebWeatherLocation;
    @BindView(2131296600)
    RelativeLayout mainLayout;
    private String productCode;
    @BindView(2131296661)
    RadioButton rbBoilerSensor;
    @BindView(2131296662)
    RadioButton rbInternet;
    @BindView(2131296666)
    RadioGroup rgOutdoorTempSource;
    private String therid;
    @BindView(2131296796)
    TextView tvBindGateway;
    @BindView(2131296814)
    TextView tvGatewayId;
    @BindView(2131296815)
    TextView tvGatewayName;
    @BindView(2131296828)
    TextView tvHost;
    @BindView(2131296831)
    TextView tvMAC;
    @BindView(2131296888)
    TextView tvTimeZone;
    @BindView(2131296891)
    TextView tvUnbindGateway;
    @BindView(2131296898)
    TextView tvWebWeatherLocation;

    /* access modifiers changed from: private */
    public void setWeatherInfo(XZWeatherResponse xZWeatherResponse) {
    }

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_gateway_info;
    }

    public void onCheckedChanged(RadioGroup radioGroup, int i) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeChange(TimeZoneEvent timeZoneEvent) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);
        this.isDayLight = getIntent().getBooleanExtra("is_daylight", true);
        DeviceListResponse.ResultsBean resultsBean = UserCenter.getResultsBean();
        DeviceListResponse.ResultsBean.ChildDevicesBean childDevicesBean = UserCenter.getChildDevicesBean();
        setTitle(getString(C1683R.string.menu_thermostat_infomation));
        if (resultsBean != null) {
            this.productCode = resultsBean.getProduct_code();
            this.devCode = resultsBean.getDevice_code();
        }
        if (!(resultsBean == null || childDevicesBean == null)) {
            this.folder = resultsBean.getDevice_code();
            this.imageFilePath = FileUtil.CLOUDWARM_ROOT_PATH + this.folder;
            this.deviceSlug = childDevicesBean.getSlug();
            this.deviceName = childDevicesBean.getName();
            this.tvGatewayName.setText(childDevicesBean.getName());
            this.tvGatewayId.setText(childDevicesBean.getSdid());
            this.tvMAC.setText(resultsBean.getMac_address());
            this.tvHost.setText(MainApplication.getBaseUrl());
        }
        this.drawer.setDrawerLockMode(1);
        this.rgOutdoorTempSource.setOnCheckedChangeListener(this);
        findViewById(C1683R.C1685id.llWebWeatherLocation).setOnClickListener(this);
        findViewById(C1683R.C1685id.llTimeZone).setOnClickListener(this);
        findViewById(C1683R.C1685id.tvBindGateway).setOnClickListener(this);
        findViewById(C1683R.C1685id.tvUnbindGateway).setOnClickListener(this);
        findViewById(C1683R.C1685id.ivEditGatewayName).setOnClickListener(this);
        getBoilerInfo();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C1683R.C1685id.civHouse:
                View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_choose_pic_source, (ViewGroup) null);
                final AlertDialog show = new AlertDialog.Builder(this, C1683R.style.mAlertDialog).setView(inflate).setNegativeButton((int) C1683R.string.public_cancel, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
                ((Button) inflate.findViewById(C1683R.C1685id.btFromCamera)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(GatewayInfoActivity.this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(GatewayInfoActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                            GatewayInfoActivity.this.openCamera();
                        } else {
                            GatewayInfoActivity.this.permissionNotify(1, "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE");
                        }
                        show.cancel();
                    }
                });
                ((Button) inflate.findViewById(C1683R.C1685id.btFromAlbum)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(GatewayInfoActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                            GatewayInfoActivity.this.permissionNotify(2, "android.permission.WRITE_EXTERNAL_STORAGE");
                        } else {
                            GatewayInfoActivity.this.openAlbum();
                        }
                        show.cancel();
                    }
                });
                return;
            case C1683R.C1685id.ivEditGatewayName:
                editGateway();
                return;
            case C1683R.C1685id.llTimeZone:
                TimeZoneFragment timeZoneFragment = new TimeZoneFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("is_daylight", this.isDayLight);
                timeZoneFragment.setArguments(bundle);
                timeZoneFragment.show(getFragmentManager(), "timeZoneFragment");
                return;
            case C1683R.C1685id.llWebWeatherLocation:
                new ChooseLocationFragment().show(getFragmentManager(), "chooseLocationFragment");
                return;
            case C1683R.C1685id.tvBindGateway:
                addGateway();
                return;
            case C1683R.C1685id.tvUnbindGateway:
                removeGateway(0);
                return;
            default:
                return;
        }
    }

    private void addGateway() {
        View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_bind_gateway, (ViewGroup) null);
        EditText editText = (EditText) inflate.findViewById(C1683R.C1685id.etDeviceName);
        EditText editText2 = (EditText) inflate.findViewById(C1683R.C1685id.etDeviceID);
        new AlertDialog.Builder(this, C1683R.style.mAlertDialog).setView(inflate).setNegativeButton((int) C1683R.string.public_cancel, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton((int) C1683R.string.public_confirm, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private void editGateway() {
        View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_edit_gateway, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(C1683R.C1685id.etDeviceName);
        TextView textView = (TextView) inflate.findViewById(C1683R.C1685id.tvDeviceID);
        TextView textView2 = (TextView) inflate.findViewById(C1683R.C1685id.tv_cancel);
        final TextView textView3 = (TextView) inflate.findViewById(C1683R.C1685id.tv_confirm);
        final AlertDialog create = new AlertDialog.Builder(this).setView(inflate).create();
        create.show();
        if (UserCenter.getChildDevicesBean() != null) {
            textView.setText(UserCenter.getChildDevicesBean().getSdid());
            editText.setText(this.deviceName);
        }
        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    AppYunManager.getInstance().modifyChildDeviceName(GatewayInfoActivity.this.devCode, GatewayInfoActivity.this.deviceSlug, editText.getText().toString(), new IAppYunResponseListener<ModifyDeviceMsgResponse>() {
                        public void onSuccess(ModifyDeviceMsgResponse modifyDeviceMsgResponse) {
                            ToastUtil.showShortToast(GatewayInfoActivity.this.mContext, GatewayInfoActivity.this.getString(C1683R.string.device_list_change_dev_name_success));
                            GatewayInfoActivity.this.tvGatewayName.setText(modifyDeviceMsgResponse.getName());
                            GatewayInfoActivity.this.setResult(1, GatewayInfoActivity.this.getIntent().putExtra("device_name", modifyDeviceMsgResponse.getName()));
                            Log.i("testHide", "hide14");
                            GatewayInfoActivity.this.HideProgressDialog();
                        }

                        public void onFailure(int i, String str) {
                            ToastUtil.showShortToast(GatewayInfoActivity.this.mContext, HttpError.getMessage(GatewayInfoActivity.this.mContext, i));
                            Log.i("testHide", "hide15");
                            GatewayInfoActivity.this.HideProgressDialog();
                        }
                    });
                    create.dismiss();
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    textView3.setTextColor(ContextCompat.getColor(GatewayInfoActivity.this, C1683R.color.cloudwarm_grey));
                } else {
                    textView3.setTextColor(ContextCompat.getColor(GatewayInfoActivity.this, C1683R.color.cloudwarm_green));
                }
            }
        });
    }

    private void removeGateway(int i) {
        new AlertDialog.Builder(this, C1683R.style.mAlertDialog).setMessage((int) C1683R.string.dialog_confirm_remove_device).setPositiveButton((int) C1683R.string.public_confirm, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNegativeButton((int) C1683R.string.public_cancel, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private void initGatewayImage(ImageView imageView) {
        File latestFile = FileUtil.getLatestFile(this.imageFilePath, "jpg");
        if (latestFile != null && latestFile.exists()) {
            imageView.setImageURI(Uri.parse(latestFile.getPath()));
        }
    }

    private void restartApp(String str, boolean z) {
        if (z) {
            View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_notification, (ViewGroup) null);
            new AlertDialog.Builder(this, C1683R.style.mAlertDialog).setView(inflate).setNegativeButton((int) C1683R.string.public_cancel, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).setPositiveButton((int) C1683R.string.public_confirm, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent launchIntentForPackage = GatewayInfoActivity.this.getPackageManager().getLaunchIntentForPackage(GatewayInfoActivity.this.getPackageName());
                    launchIntentForPackage.addFlags(67108864);
                    launchIntentForPackage.addFlags(32768);
                    GatewayInfoActivity.this.startActivity(launchIntentForPackage);
                }
            }).show();
            ((TextView) inflate.findViewById(C1683R.C1685id.tvNotification)).setText(str);
            return;
        }
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
        launchIntentForPackage.addFlags(67108864);
        launchIntentForPackage.addFlags(32768);
        startActivity(launchIntentForPackage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLocation(LocationGroup locationGroup) {
        this.city = locationGroup.getName().getEn();
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
        Log.d(TAG, " GatewayInfo onChangeLocation city = " + this.city + " cityId = " + this.cityID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDayLightChange(DayLightEvent dayLightEvent) {
        this.isDayLight = dayLightEvent.isDayLight();
        Log.d(TAG, " onDayLightChange isDayLight = " + this.isDayLight);
    }

    /* access modifiers changed from: package-private */
    public void setPicToView(Uri uri) {
        LogUtil.m3315d("setPicToView", " uri: " + uri + " photo=" + null);
        Bitmap decodeUriAsBitmap = uri != null ? FileUtil.decodeUriAsBitmap(this, uri) : null;
        if (decodeUriAsBitmap != null && !TextUtils.isEmpty(this.folder)) {
            this.civHouse.setImageDrawable(new BitmapDrawable((Resources) null, decodeUriAsBitmap));
            String valueOf = String.valueOf(new Date().getTime());
            String str = this.folder;
            FileUtil.saveFile(this, str, valueOf + ".jpg", decodeUriAsBitmap);
        }
    }

    private String formatTimeZone(int i) {
        int i2 = i % ACache.TIME_DAY;
        String str = "";
        String str2 = i2 >= 0 ? MqttTopic.SINGLE_LEVEL_WILDCARD : str;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        int i3 = i2 / ACache.TIME_HOUR;
        sb.append((i3 < 10 || i2 < 0) ? "0" : str);
        sb.append(String.valueOf(i3));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(sb.toString() + ":");
        int i4 = i2 % ACache.TIME_HOUR;
        if (i4 < 10 || i2 < 0) {
            str = "0";
        }
        sb2.append(str);
        sb2.append(String.valueOf(i4));
        return sb2.toString();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public void getBoilerInfo() {
        HttpUtils.getRetrofit().getBoilerInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BoilerInfoBean>() {
            public void accept(BoilerInfoBean boilerInfoBean) throws Exception {
                LogUtil.m3315d(GatewayInfoActivity.TAG, "onGet boiler info success");
                GatewayInfoActivity.this.getWeather(boilerInfoBean.getCity_id());
                BoilerInfoBean unused = GatewayInfoActivity.this.boilerInfoBean = boilerInfoBean;
                GatewayInfoActivity.this.tvWebWeatherLocation.setText(boilerInfoBean.getLocation());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(GatewayInfoActivity.TAG, "onGet boiler info fail");
                GatewayInfoActivity.this.getWeather((String) null);
                th.printStackTrace();
            }
        });
    }

    /* access modifiers changed from: private */
    public void getWeather(String str) {
        String str2 = LanguageUtil.getSetLanguageLocale(getApplicationContext()).equals(Locale.CHINA) ? Constant.WEATHER_LANGUAGE_ZH : "en";
        if (str == null || str.equals("")) {
            showChooseLocationDialog();
            return;
        }
        Log.d(TAG, " weather location  = " + str + " weather language = " + str2);
        getWeather(str, str2, Constant.WEATHER_TEMP_C);
    }

    private void getWeather(String str, String str2, String str3) {
        AppYunManager.getInstance().getWeather(str, str2, str3, new IAppYunResponseListener<XZWeatherResponse>() {
            public void onSuccess(XZWeatherResponse xZWeatherResponse) {
                Log.d(GatewayInfoActivity.TAG, " on get weather success");
                GatewayInfoActivity.this.setWeatherInfo(xZWeatherResponse);
            }

            public void onFailure(int i, String str) {
                ToastUtil.showShortToast(GatewayInfoActivity.this.mContext, HttpError.getMessage(GatewayInfoActivity.this.mContext, i));
                Log.d(GatewayInfoActivity.TAG, " on get weather fail");
            }
        });
    }

    private void showChooseLocationDialog() {
        new ChooseLocationFragment().show(getFragmentManager(), "chooseLocationFragment");
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
                LogUtil.m3315d(GatewayInfoActivity.TAG, "set user info success");
                GatewayInfoActivity.this.HideProgressDialog();
                GatewayInfoActivity.this.setResult(2);
                GatewayInfoActivity.this.getBoilerInfo();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.e(GatewayInfoActivity.TAG, "set user info fail", th);
                GatewayInfoActivity.this.HideProgressDialog();
                th.printStackTrace();
            }
        });
    }
}
