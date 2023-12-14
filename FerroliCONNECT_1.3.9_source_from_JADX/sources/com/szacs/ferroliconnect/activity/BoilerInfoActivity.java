package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.Constant;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.BoilerInfoBean;
import com.szacs.ferroliconnect.bean.LocationGroup;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.fragment.ChooseLocationFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.taobao.accs.common.Constants;
import java.util.Calendar;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class BoilerInfoActivity extends MyAppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    protected String app_slug;
    protected String authorzation;
    @BindView(2131296335)
    QMUIRoundButton button;
    /* access modifiers changed from: private */
    public String cityId;
    @BindView(2131296262)
    EditText edBrand;
    @BindView(2131296263)
    TextView edCity;
    @BindView(2131296264)
    TextView edInstallTime;
    @BindView(2131296265)
    EditText edModel;
    @BindView(2131296266)
    EditText edSerial;
    @BindView(2131296548)
    LinearLayout install_layout;
    protected String wifi_box_slug;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        setContentView((int) C1683R.C1686layout.bloiler_info);
        EventBus.getDefault().register(this);
        ButterKnife.bind((Activity) this);
        initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BoilerInfoActivity.this.finish();
            }
        });
        this.authorzation = AppYunManager.getInstance().getAuthorization();
        this.app_slug = MainApplication.appSlug;
        this.wifi_box_slug = UserCenter.getResultsBean().getSlug();
        this.button.setOnClickListener(this);
        this.install_layout.setOnClickListener(this);
        this.edCity.setOnClickListener(this);
        this.edInstallTime.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        getBoilerInfo();
    }

    /* access modifiers changed from: private */
    public void getBoilerInfo() {
        HttpUtils.getRetrofit().getBoilerInfo(this.authorzation, this.app_slug, this.wifi_box_slug).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BoilerInfoBean>() {
            public void accept(BoilerInfoBean boilerInfoBean) throws Exception {
                if (!BoilerInfoActivity.this.isFinishing()) {
                    BoilerInfoActivity.this.edBrand.setText(boilerInfoBean.getBrand());
                    BoilerInfoActivity.this.edModel.setText(boilerInfoBean.getModel());
                    BoilerInfoActivity.this.edSerial.setText(boilerInfoBean.getSn());
                    BoilerInfoActivity.this.edInstallTime.setText(boilerInfoBean.getInstallation_date());
                    BoilerInfoActivity.this.edCity.setText(boilerInfoBean.getLocation());
                    String unused = BoilerInfoActivity.this.cityId = boilerInfoBean.getCity_id();
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(BoilerInfoActivity.this.TAG, "onGet user info fail");
                th.printStackTrace();
            }
        });
    }

    private void updateBoilerInfo() {
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(this.edInstallTime.getText().toString())) {
            ShowProgressDialog((String) null);
            hashMap.put("installation_date", this.edInstallTime.getText().toString());
            if (!TextUtils.isEmpty(this.edBrand.getText().toString())) {
                hashMap.put(Constants.KEY_BRAND, this.edBrand.getText().toString());
            }
            if (!TextUtils.isEmpty(this.edModel.getText().toString())) {
                hashMap.put(Constants.KEY_MODEL, this.edModel.getText().toString());
            }
            if (!TextUtils.isEmpty(this.edSerial.getText().toString())) {
                hashMap.put("sn", this.edSerial.getText().toString());
            }
            if (!TextUtils.isEmpty(this.edCity.getText().toString())) {
                hashMap.put(Constant.WEATHER_LOCATION, this.edCity.getText().toString());
            }
            if (!TextUtils.isEmpty(this.cityId)) {
                hashMap.put("city_id", this.cityId);
            }
            HttpUtils.getRetrofit().UpdateBoilerInfo(this.authorzation, this.app_slug, this.wifi_box_slug, hashMap).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                public void accept(String str) throws Exception {
                    Log.i("testHide", "hide7");
                    BoilerInfoActivity.this.HideProgressDialog();
                    BoilerInfoActivity.this.getBoilerInfo();
                }
            }, new Consumer<Throwable>() {
                public void accept(Throwable th) throws Exception {
                    LogUtil.m3315d(BoilerInfoActivity.this.TAG, "set user info fail");
                    Log.i("testHide", "hide8");
                    BoilerInfoActivity.this.HideProgressDialog();
                    th.printStackTrace();
                }
            });
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C1683R.C1685id.EdtextCity:
                showSelectCityDialog();
                return;
            case C1683R.C1685id.EdtextInstallTime:
            case C1683R.C1685id.layout_install_time:
                Calendar instance = Calendar.getInstance();
                new DatePickerDialog(this, this, instance.get(1), instance.get(2), instance.get(5)).show();
                return;
            case C1683R.C1685id.btn_forget:
                updateBoilerInfo();
                return;
            default:
                return;
        }
    }

    private void showSelectCityDialog() {
        new ChooseLocationFragment().show(getFragmentManager(), "chooseLocationFragment");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLocation(LocationGroup locationGroup) {
        String en = locationGroup.getName().getEn();
        String str = this.TAG;
        Log.d(str, " system language is en and city = " + en);
        this.cityId = locationGroup.getCity_id();
        SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
        edit.putString("city", en);
        edit.putString("editor", this.cityId);
        edit.commit();
        this.edCity.setText(en);
        updateBoilerInfo();
    }

    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        if (!isFinishing()) {
            this.edInstallTime.setText(String.format("%d-%02d-%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2 + 1), Integer.valueOf(i3)}));
        }
    }
}
