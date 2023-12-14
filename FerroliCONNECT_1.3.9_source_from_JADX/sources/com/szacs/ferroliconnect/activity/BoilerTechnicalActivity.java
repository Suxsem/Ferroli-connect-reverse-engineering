package com.szacs.ferroliconnect.activity;

import android.os.Bundle;
import android.support.p003v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.baoyz.widget.PullRefreshLayout;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.BoilerBean;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.BoilerHeatTargetTempSetOptions;
import com.szacs.ferroliconnect.event.Event;
import com.szacs.ferroliconnect.fragment.AlarmRecordFragment;
import com.szacs.ferroliconnect.fragment.TSPFragment;
import com.szacs.ferroliconnect.fragment.TargetTempSettingOptionFragment;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.szacs.ferroliconnect.widget.wheel.StrericWheelAdapter;
import com.szacs.ferroliconnect.widget.wheel.WheelView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BoilerTechnicalActivity extends MyNavigationActivity implements View.OnClickListener, PullRefreshLayout.OnRefreshListener {
    protected AlarmRecordFragment alarmRecordFragment;
    @BindView(2131296560)
    LinearLayout llAlarmRecord;
    @BindView(2131296566)
    LinearLayout llHeatingTargetTempSettingOptions;
    @BindView(2131296570)
    LinearLayout llMaxHeatingTargetTemperature;
    @BindView(2131296601)
    LinearLayout mainLinearLayout;
    @BindView(2131296664)
    PullRefreshLayout refreshLayout;
    protected TargetTempSettingOptionFragment targetTempSettingOptionFragment;
    protected TSPFragment tspFragment;
    @BindView(2131296897)
    TextView tvBathWaterSupplySwith;
    @BindView(2131296794)
    TextView tvBathWaterTempRange;
    @BindView(2131296795)
    TextView tvBathWaterTempRangeUnit;
    @BindView(2131296800)
    TextView tvConnectType;
    @BindView(2131296870)
    TextView tvDHWCurrentTemperature;
    @BindView(2131296869)
    TextView tvDHWTargetTemperature;
    @BindView(2131296867)
    TextView tvFlameStatus;
    @BindView(2131296810)
    TextView tvFlueTemp;
    @BindView(2131296813)
    TextView tvGasType;
    @BindView(2131296819)
    TextView tvHeatStatus;
    @BindView(2131296820)
    TextView tvHeatTempRange;
    @BindView(2131296821)
    TextView tvHeatTempRangeUnit;
    @BindView(2131296822)
    TextView tvHeatType;
    @BindView(2131296817)
    TextView tvHeatingCurrentTemperature;
    @BindView(2131296871)
    TextView tvHeatingHours;
    @BindView(2131296827)
    TextView tvHeatingTargetTempSettingOption;
    @BindView(2131296868)
    TextView tvHeatingTargetTemperature;
    @BindView(2131296834)
    TextView tvMachineType;
    @BindView(2131296872)
    TextView tvMaxHeatingTargetTemperature;
    @BindView(2131296873)
    TextView tvModulationRatio;
    @BindView(2131296846)
    TextView tvOutTemp;
    @BindView(2131296847)
    TextView tvOutWaterTemp;
    @BindView(2131296853)
    TextView tvReturnWaterTemp;
    @BindView(2131296874)
    TextView tvSystemPressure;
    @BindView(2131296896)
    TextView tvWaterRate;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_boiler_technical;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        initWidget();
        initViews(UserCenter.getBoilerBean());
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
    }

    /* access modifiers changed from: protected */
    public void initWidget() {
        EventBus.getDefault().register(this);
        this.llAlarmRecord.setOnClickListener(this);
        this.llMaxHeatingTargetTemperature.setOnClickListener(this);
        this.llHeatingTargetTempSettingOptions.setOnClickListener(this);
        this.alarmRecordFragment = new AlarmRecordFragment();
        this.targetTempSettingOptionFragment = new TargetTempSettingOptionFragment();
        this.tspFragment = new TSPFragment();
        this.drawer.setDrawerLockMode(1);
        setTitle(getString(C1683R.string.menu_technical));
        this.refreshLayout.setOnRefreshListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1683R.C1685id.llAlarmCode) {
            this.alarmRecordFragment.show(getFragmentManager(), "alarmRecordFragment");
        } else if (id == C1683R.C1685id.llHeatingTargetTempSettingOptions) {
            this.targetTempSettingOptionFragment.show(getFragmentManager(), "targetTempSettingOptionFragment");
        } else if (id == C1683R.C1685id.llMaxHeatingSetPoint) {
            View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_adjust_max_heating_target_temp, (ViewGroup) null);
            final AlertDialog show = new AlertDialog.Builder(this).setView(inflate).show();
            initWheel((WheelView) inflate.findViewById(C1683R.C1685id.passw_1), new String[]{"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80"}, -5);
            ((ImageView) inflate.findViewById(C1683R.C1685id.ivCancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    show.cancel();
                }
            });
            ((ImageView) inflate.findViewById(C1683R.C1685id.ivConfirm)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    show.cancel();
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBeseEvent(BaseEvent baseEvent) {
        if (baseEvent.getEvent() == Event.EVENT_CHANGE_TARGET_SETTING_OPTION) {
            BoilerHeatTargetTempSetOptions boilerHeatTargetTempSetOptions = (BoilerHeatTargetTempSetOptions) baseEvent;
        }
    }

    private void initViews(BoilerBean boilerBean) {
        if (boilerBean != null) {
            this.tvMaxHeatingTargetTemperature.setText(String.valueOf(boilerBean.getHeatMaxSetTemp()));
            this.tvHeatingTargetTemperature.setText(String.valueOf(boilerBean.getHeatTargetTemp()));
            this.tvHeatingCurrentTemperature.setText(String.valueOf(boilerBean.getHeatCurrentTemp()));
            TextView textView = this.tvHeatTempRange;
            textView.setText(boilerBean.getHeatMinTemp() + " ~ " + boilerBean.getHeatMaxTemp());
            this.tvDHWTargetTemperature.setText(String.valueOf(boilerBean.getBathTargetTemp()));
            this.tvDHWCurrentTemperature.setText(String.valueOf(boilerBean.getBathCurrentTemp()));
            TextView textView2 = this.tvBathWaterTempRange;
            textView2.setText(boilerBean.getBathMinTemp() + " ~ " + boilerBean.getBathMaxTemp());
            this.tvSystemPressure.setText(String.valueOf(boilerBean.getWaterPressure()));
            this.tvFlameStatus.setText(getString(boilerBean.getFlameStatus() == 1 ? C1683R.string.boiler_tech_have_flame : C1683R.string.boiler_tech_no_flame));
            this.tvModulationRatio.setText(String.valueOf(boilerBean.getPwm()));
            this.tvHeatStatus.setText(getString(boilerBean.getHeatStatus() == 1 ? C1683R.string.boiler_heating_status_heating : C1683R.string.boiler_heating_status_stop));
            this.tvBathWaterSupplySwith.setText(getString(boilerBean.getWaterSwitchState() == 1 ? C1683R.string.boiler_tech_has_water : C1683R.string.boiler_tech_has_no_water));
            this.tvMachineType.setText(getString(boilerBean.getFunctionType() == 1 ? C1683R.string.boiler_tech_mutil : C1683R.string.boiler_tech_sigle));
            this.tvHeatType.setText(getString(boilerBean.getInstallType() == 1 ? C1683R.string.boiler_tech_wall : C1683R.string.boiler_tech_ground));
            this.tvGasType.setText(getString(boilerBean.getGasType() == 1 ? C1683R.string.boiler_tech_natural_gas : C1683R.string.boiler_tech_Liquefied_gas));
            this.tvConnectType.setText(getString(boilerBean.getControlType() == 1 ? C1683R.string.boiler_tech_ot : C1683R.string.boiler_tech_on_off));
            TextView textView3 = this.tvWaterRate;
            textView3.setText(boilerBean.getWaterRate() + " L/min");
            TextView textView4 = this.tvReturnWaterTemp;
            textView4.setText(boilerBean.getReturnTemp() + " ℃");
            TextView textView5 = this.tvOutTemp;
            textView5.setText(checkTemp(boilerBean.getOutTemp()) + " ℃");
            TextView textView6 = this.tvOutWaterTemp;
            textView6.setText(boilerBean.getOutWaterTemp() + " ℃");
            TextView textView7 = this.tvFlueTemp;
            textView7.setText(boilerBean.getFlueTemp() + " ℃");
        }
    }

    private String checkTemp(float f) {
        double d = (double) f;
        if (d > 127.0d || d < -40.0d) {
            return "--";
        }
        return String.format("%.1f", new Object[]{Float.valueOf(f)}).replace(",", ".");
    }

    /* access modifiers changed from: protected */
    public void initWheel(WheelView wheelView, String[] strArr, int i) {
        wheelView.setAdapter(new StrericWheelAdapter(strArr));
        wheelView.setCurrentItem(i);
        wheelView.setCyclic(true);
        wheelView.setInterpolator(new AnticipateOvershootInterpolator());
    }

    /* access modifiers changed from: protected */
    public String getWheelValue(WheelView wheelView) {
        return wheelView.getCurrentItemValue();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onRefresh() {
        initViews(UserCenter.getBoilerBean());
        this.refreshLayout.setRefreshing(false);
    }
}
