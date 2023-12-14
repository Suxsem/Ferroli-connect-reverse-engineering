package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.igexin.sdk.PushConsts;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.Constant;
import com.p107tb.appyunsdk.bean.BindDeviceResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.ApConfigResponse;
import com.szacs.ferroliconnect.bean.ApConfigWifiBean;
import com.szacs.ferroliconnect.bean.EasyLinkBean;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.dialog.ApAddDeviceDialog;
import com.szacs.ferroliconnect.fragment.ConfigWifiOIFragment;
import com.szacs.ferroliconnect.fragment.EnterKeyFragment;
import com.szacs.ferroliconnect.fragment.WifiConfiguringFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.APConfig.UDPSocket;
import com.szacs.ferroliconnect.util.GsonUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.taobao.accs.utl.UtilityImpl;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import p110io.fogcloud.sdk.easylink.api.EasylinkP2P;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkParams;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class ConfigWiFiActivity extends MyAppCompatActivity implements EasyLinkCallBack, View.OnClickListener, UDPSocket.ApConfigListener, ApAddDeviceDialog.ApAddDeviceListener {
    @BindView(2131296776)
    TextView TitleTv;
    /* access modifiers changed from: private */
    public ApAddDeviceDialog apAddDeviceDialog;
    private ConfigWifiOIFragment configWifiOIFragment;
    /* access modifiers changed from: private */
    public EasylinkP2P easylinkP2P;
    /* access modifiers changed from: private */
    public EnterKeyFragment enterKeyFragment;
    @BindView(2131296451)
    FrameLayout flFragment;
    /* access modifiers changed from: private */
    public boolean isFindDevice;
    @BindView(2131296494)
    ImageView ivBack;
    @BindView(2131296515)
    ImageView ivNext;
    @BindView(2131296568)
    RelativeLayout llMain;
    /* access modifiers changed from: private */
    public Handler mViewHandler = new Handler();
    private BroadcastReceiver mWifiChangedReceiver;
    private RxPermissions rxPermissions;
    /* access modifiers changed from: private */
    public int step = 0;
    /* access modifiers changed from: private */
    public int time;
    /* access modifiers changed from: private */
    public UDPSocket udpSocket;
    private WifiConfiguringFragment wifiConfiguringFragment;

    /* access modifiers changed from: private */
    public void refusePermission() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        setContentView((int) C1683R.C1686layout.activity_config_wifi2);
        ButterKnife.bind((Activity) this);
        initToolbar();
        this.isFindDevice = getIntent().getBooleanExtra("FindDevice", false);
        this.easylinkP2P = new EasylinkP2P(this);
        this.enterKeyFragment = new EnterKeyFragment();
        this.udpSocket = new UDPSocket(this);
        this.udpSocket.setApConfigListener(this);
        this.mWifiChangedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (!ConfigWiFiActivity.this.isFinishing()) {
                    Log.d(ConfigWiFiActivity.this.TAG, " mWifiChangedReceiver onReceiver111");
                    NetworkInfo activeNetworkInfo = ((ConnectivityManager) ConfigWiFiActivity.this.getSystemService("connectivity")).getActiveNetworkInfo();
                    if (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) {
                        Log.d(ConfigWiFiActivity.this.TAG, " mWifiChangedReceiver onReceiver333");
                        FragmentTransaction beginTransaction = ConfigWiFiActivity.this.getFragmentManager().beginTransaction();
                        int unused = ConfigWiFiActivity.this.step = 0;
                        ConfigWiFiActivity.this.ivBack.setVisibility(4);
                        if (ConfigWiFiActivity.this.enterKeyFragment == null) {
                            EnterKeyFragment unused2 = ConfigWiFiActivity.this.enterKeyFragment = new EnterKeyFragment();
                        }
                        beginTransaction.replace(C1683R.C1685id.flFragment, ConfigWiFiActivity.this.enterKeyFragment);
                        beginTransaction.commitAllowingStateLoss();
                        ConfigWiFiActivity.this.ivNext.setVisibility(4);
                        ConfigWiFiActivity.this.enterKeyFragment.setWiFiConnected(false);
                        return;
                    }
                    Log.d(ConfigWiFiActivity.this.TAG, " mWifiChangedReceiver onReceiver222");
                    ConfigWiFiActivity.this.enterKeyFragment.setWiFiConnected(true);
                    ConfigWiFiActivity.this.ivNext.setVisibility(0);
                }
            }
        };
        this.TitleTv.setText(this.isFindDevice ? C1683R.string.nav_tab_add_device_title : C1683R.string.menu_config_wifi);
        this.ivBack.setVisibility(4);
        this.ivBack.setOnClickListener(this);
        this.ivNext.setOnClickListener(this);
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(C1683R.C1685id.flFragment, this.enterKeyFragment);
        beginTransaction.commit();
        registerReceiver(this.mWifiChangedReceiver, new IntentFilter(PushConsts.ACTION_BROADCAST_NETWORK_CHANGE));
        this.rxPermissions = new RxPermissions(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ConfigWiFiActivity.this.checkPermission();
            }
        }, 5);
    }

    /* access modifiers changed from: private */
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 27 && (!this.rxPermissions.isGranted("android.permission.ACCESS_FINE_LOCATION") || !this.rxPermissions.isGranted("android.permission.ACCESS_COARSE_LOCATION"))) {
            Log.d(this.TAG, " request permission for find location");
            requestPermission();
        } else if (checkLocationIsClose()) {
            Log.d(this.TAG, " has location permission and openLocation set ssid");
        } else {
            Log.d(this.TAG, " didn't open location, emm..open");
            openLocation();
        }
    }

    private void requestPermission() {
        this.rxPermissions.request("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION").subscribe(new Consumer<Boolean>() {
            public void accept(Boolean bool) throws Exception {
                if (!bool.booleanValue()) {
                    Log.d(ConfigWiFiActivity.this.TAG, " refuse permission");
                    ConfigWiFiActivity.this.refusePermission();
                } else if (ConfigWiFiActivity.this.checkLocationIsClose()) {
                    Log.d(ConfigWiFiActivity.this.TAG, " allow fine location and set ssid");
                } else {
                    Log.d(ConfigWiFiActivity.this.TAG, " allow fine location but didn't open location");
                    ConfigWiFiActivity.this.openLocation();
                }
            }
        });
    }

    public boolean checkLocationIsClose() {
        return ((LocationManager) getApplication().getSystemService(Constant.WEATHER_LOCATION)).isProviderEnabled("network");
    }

    public void openLocation() {
        startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 887);
    }

    public void onDestroy() {
        try {
            this.easylinkP2P.stopEasyLink(this);
            this.udpSocket.stopUDPSocket();
            this.mContext.unregisterReceiver(this.mWifiChangedReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public boolean isLocationEnabled() {
        if (Build.VERSION.SDK_INT < 19) {
            return !TextUtils.isEmpty(Settings.Secure.getString(getContentResolver(), "location_providers_allowed"));
        }
        try {
            if (Settings.Secure.getInt(getContentResolver(), "location_mode") != 0) {
                return true;
            }
            return false;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ConfigWiFiActivity.this.finish();
            }
        });
    }

    public String getSSID() {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(UtilityImpl.NET_TYPE_WIFI);
        if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return getResources().getString(C1683R.string.enter_ssid);
        }
        String ssid = connectionInfo.getSSID();
        if (ssid.length() <= 2 || !ssid.startsWith("\"") || !ssid.endsWith("\"")) {
            return ssid.equals("<unknown ssid>") ? getResources().getString(C1683R.string.enter_ssid) : ssid;
        }
        return ssid.substring(1, ssid.length() - 1);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getGroupId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onClick(View view) {
        if (!isFinishing()) {
            FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
            int id = view.getId();
            if (id == C1683R.C1685id.ivBack) {
                int i = this.step;
                if (i == 2) {
                    stopConfig();
                    this.step = 1;
                    this.ivNext.setVisibility(0);
                    if (this.configWifiOIFragment == null) {
                        this.configWifiOIFragment = new ConfigWifiOIFragment();
                    }
                    String str = this.TAG;
                    Log.d(str, " IS AP CONFIG = " + this.enterKeyFragment.isApConfig());
                    this.configWifiOIFragment.setbApConfig(this.enterKeyFragment.isApConfig());
                    beginTransaction.replace(C1683R.C1685id.flFragment, this.configWifiOIFragment);
                } else if (i == 1) {
                    this.step = 0;
                    this.ivBack.setVisibility(4);
                    this.ivNext.setVisibility(0);
                    if (this.enterKeyFragment == null) {
                        this.enterKeyFragment = new EnterKeyFragment();
                    }
                    beginTransaction.replace(C1683R.C1685id.flFragment, this.enterKeyFragment);
                }
            } else if (id == C1683R.C1685id.ivNext) {
                int i2 = this.step;
                if (i2 == 0) {
                    this.step = 1;
                    this.ivBack.setVisibility(0);
                    if (this.configWifiOIFragment == null) {
                        this.configWifiOIFragment = new ConfigWifiOIFragment();
                    }
                    String str2 = this.TAG;
                    Log.d(str2, " IS AP CONFIG = " + this.enterKeyFragment.isApConfig());
                    this.configWifiOIFragment.setbApConfig(this.enterKeyFragment.isApConfig());
                    beginTransaction.replace(C1683R.C1685id.flFragment, this.configWifiOIFragment);
                } else if (i2 == 1) {
                    this.step = 2;
                    this.ivNext.setVisibility(4);
                    if (this.wifiConfiguringFragment == null) {
                        this.wifiConfiguringFragment = new WifiConfiguringFragment();
                    }
                    beginTransaction.replace(C1683R.C1685id.flFragment, this.wifiConfiguringFragment);
                    configWifi();
                }
            }
            beginTransaction.commitAllowingStateLoss();
        }
    }

    private void startEasyLink() {
        this.time = 60;
        EasyLinkParams easyLinkParams = new EasyLinkParams();
        easyLinkParams.ssid = getSSID();
        easyLinkParams.password = this.enterKeyFragment.getPassword();
        easyLinkParams.runSecond = 60000;
        easyLinkParams.sleeptime = 50;
        this.easylinkP2P.startEasyLink(easyLinkParams, this);
        this.mViewHandler.post(new Runnable() {
            public void run() {
                if (ConfigWiFiActivity.this.isFinishing() || ConfigWiFiActivity.this.time < 1) {
                    ConfigWiFiActivity.this.easylinkP2P.stopEasyLink(ConfigWiFiActivity.this);
                    Snackbar.make((View) ConfigWiFiActivity.this.llMain, (CharSequence) ConfigWiFiActivity.this.getString(C1683R.string.configure_wifi_timeout), -1).show();
                    int unused = ConfigWiFiActivity.this.step = 1;
                    ConfigWiFiActivity.this.ivBack.performClick();
                    return;
                }
                ConfigWiFiActivity configWiFiActivity = ConfigWiFiActivity.this;
                int unused2 = configWiFiActivity.time = configWiFiActivity.time - 1;
                String str = ConfigWiFiActivity.this.TAG;
                LogUtil.m3315d(str, "Progress : " + ConfigWiFiActivity.this.time);
                ConfigWiFiActivity.this.mViewHandler.postDelayed(this, 1000);
            }
        });
    }

    /* access modifiers changed from: private */
    public void stopEasyLink() {
        this.easylinkP2P.stopEasyLink(this);
        this.time = 0;
        this.mViewHandler.removeCallbacksAndMessages((Object) null);
    }

    public void onSuccess(int i, String str) {
        SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
        edit.putString("config_wifi_ssid", this.enterKeyFragment.getInputSSID());
        edit.apply();
        LogUtil.m3315d(this.TAG, String.format("onSuccess code:%d, message:%s", new Object[]{Integer.valueOf(i), str}));
        if (str.contains("MAC")) {
            final EasyLinkBean easyLinkBean = (EasyLinkBean) new Gson().fromJson(str, EasyLinkBean.class);
            if (!this.isFindDevice) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ConfigWiFiActivity.this.stopEasyLink();
                        Intent intent = new Intent();
                        intent.putExtra("MAC", easyLinkBean.getMAC());
                        intent.putExtra("DID", easyLinkBean.getDID());
                        ConfigWiFiActivity.this.setResult(-1, intent);
                        ConfigWiFiActivity.this.finish();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ConfigWiFiActivity.this.stopEasyLink();
                        String str = ConfigWiFiActivity.this.TAG;
                        Log.d(str, " DID = " + easyLinkBean.getDID() + " mac = " + easyLinkBean.getMAC());
                        ConfigWiFiActivity.this.bindDevice(easyLinkBean.getDID(), easyLinkBean.getMAC());
                    }
                });
            }
        } else {
            str.contains("stop easylink");
        }
    }

    private void setDeviceTimeZone(String str) {
        int i = (((Calendar.getInstance(Locale.getDefault()).get(15) / 60) / 60) / 1000) + 12;
        String str2 = this.TAG;
        Log.d(str2, " TimeZone = " + i);
        String format = String.format("JWT %s", new Object[]{getSharedPreferences("ferroli_user", 0).getString("token", "")});
        String str3 = this.TAG;
        Log.d(str3, " bindDevice setDeviceInfo token = " + format);
        ShowProgressDialog((String) null);
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.TIMEZONE, i + "");
        HttpUtils.getRetrofit().UpdateBoilerInfo(format, MainApplication.appSlug, str, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) throws Exception {
                LogUtil.m3315d(ConfigWiFiActivity.this.TAG, "set device info success");
                ConfigWiFiActivity.this.HideProgressDialog();
                ConfigWiFiActivity.this.finish();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                th.printStackTrace();
                LogUtil.m3315d(ConfigWiFiActivity.this.TAG, "set device info fail");
                ConfigWiFiActivity.this.HideProgressDialog();
                ConfigWiFiActivity.this.finish();
            }
        });
    }

    public void onFailure(int i, String str) {
        LogUtil.m3315d(this.TAG, String.format("onFailure code:%d, message:%s", new Object[]{Integer.valueOf(i), str}));
    }

    /* access modifiers changed from: private */
    public void bindDevice(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            ToastUtil.showShortToast(this.mContext, getString(C1683R.string.add_device_dev_empty));
        } else if (TextUtils.isEmpty(str2)) {
            ToastUtil.showShortToast(this.mContext, getString(C1683R.string.add_device_mac_empty));
        } else {
            int i = (((Calendar.getInstance(Locale.getDefault()).get(15) / 60) / 60) / 1000) + 12;
            String str3 = this.TAG;
            Log.d(str3, " TimeZone = " + i);
            AppYunManager.getInstance().bindDeviceNew(str, str2, i, new IAppYunResponseListener<BindDeviceResponse>() {
                public void onSuccess(BindDeviceResponse bindDeviceResponse) {
                    Toast.makeText(ConfigWiFiActivity.this.mContext, ConfigWiFiActivity.this.getString(C1683R.string.configure_wifi_device_config_success_simple), 1).show();
                    if (ConfigWiFiActivity.this.apAddDeviceDialog != null && ConfigWiFiActivity.this.apAddDeviceDialog.isShowing()) {
                        ConfigWiFiActivity.this.apAddDeviceDialog.dismiss();
                    }
                    ConfigWiFiActivity.this.finish();
                }

                public void onFailure(int i, String str) {
                    ToastUtil.showShortToast(ConfigWiFiActivity.this.mContext, HttpError.getMessage(ConfigWiFiActivity.this.mContext, i));
                    ConfigWiFiActivity.this.resetStep();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void resetStep() {
        runOnUiThread(new Runnable() {
            public void run() {
                ConfigWiFiActivity.this.getFragmentManager().beginTransaction().replace(C1683R.C1685id.flFragment, ConfigWiFiActivity.this.enterKeyFragment).commitAllowingStateLoss();
                ConfigWiFiActivity.this.ivNext.setVisibility(0);
                ConfigWiFiActivity.this.ivBack.setVisibility(4);
                int unused = ConfigWiFiActivity.this.step = 0;
            }
        });
    }

    private void startApConfig() {
        ApConfigWifiBean apConfigWifiBean = new ApConfigWifiBean();
        apConfigWifiBean.setSSID(this.enterKeyFragment.getInputSSID());
        apConfigWifiBean.setPASSWORD(this.enterKeyFragment.getPassword());
        this.udpSocket.setSendMsg(GsonUtil.toJson(apConfigWifiBean));
        this.udpSocket.startUDPSocket();
    }

    private void configWifi() {
        if (this.enterKeyFragment.isApConfig()) {
            Log.d(this.TAG, " CONFIG WIFI AP MODE");
            startApConfig();
            return;
        }
        Log.d(this.TAG, " CONFIG WIFI WPS MODE");
        try {
            startEasyLink();
            if (!TextUtils.isEmpty(getSSID())) {
                SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
                edit.putString(this.enterKeyFragment.getInputSSID(), this.enterKeyFragment.getPassword());
                edit.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopConfig() {
        if (this.enterKeyFragment.isApConfig()) {
            this.udpSocket.stopUDPSocket();
        } else {
            stopEasyLink();
        }
    }

    public void onApConfigSuccess(final String str) {
        runOnUiThread(new Runnable() {
            public void run() {
                ApConfigResponse apConfigResponse = (ApConfigResponse) GsonUtil.fromJson(str, ApConfigResponse.class);
                ConfigWiFiActivity.this.udpSocket.stopUDPSocket();
                if (ConfigWiFiActivity.this.isFindDevice) {
                    ConfigWiFiActivity.this.showApAddDeviceDialog(apConfigResponse);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("MAC", apConfigResponse.getMAC());
                intent.putExtra("DID", apConfigResponse.getDID());
                ConfigWiFiActivity.this.setResult(-1, intent);
                Toast.makeText(ConfigWiFiActivity.this.mContext, ConfigWiFiActivity.this.getString(C1683R.string.configure_wifi_device_config_success_simple), 1).show();
                ConfigWiFiActivity.this.finish();
            }
        });
    }

    public void onApConfigFailed() {
        this.udpSocket.stopUDPSocket();
        this.step = 1;
        runOnUiThread(new Runnable() {
            public void run() {
                Snackbar.make((View) ConfigWiFiActivity.this.llMain, (CharSequence) ConfigWiFiActivity.this.getString(C1683R.string.configure_wifi_timeout), -1).show();
                ConfigWiFiActivity.this.ivBack.performClick();
            }
        });
    }

    /* access modifiers changed from: private */
    public void showApAddDeviceDialog(ApConfigResponse apConfigResponse) {
        if (this.apAddDeviceDialog == null) {
            this.apAddDeviceDialog = new ApAddDeviceDialog(this);
            this.apAddDeviceDialog.setCanceledOnTouchOutside(false);
            this.apAddDeviceDialog.setApAddDeviceListener(this);
        }
        this.apAddDeviceDialog.setApConfigResponse(apConfigResponse);
        this.apAddDeviceDialog.show();
    }

    public void onApAddClick(ApConfigResponse apConfigResponse) {
        bindDevice(apConfigResponse.getDID(), apConfigResponse.getMAC());
    }

    private String getConnectWifiSsid() {
        WifiInfo connectionInfo = ((WifiManager) getApplicationContext().getSystemService(UtilityImpl.NET_TYPE_WIFI)).getConnectionInfo();
        Log.d("wifiInfo", connectionInfo.toString());
        Log.d("SSID", connectionInfo.getSSID());
        return connectionInfo.getSSID();
    }
}
