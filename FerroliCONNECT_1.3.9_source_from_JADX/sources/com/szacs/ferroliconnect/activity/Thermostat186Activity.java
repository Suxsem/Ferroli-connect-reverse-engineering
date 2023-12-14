package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.p003v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.fragment.ChooseLocationFragment;
import com.szacs.ferroliconnect.viewInterface.ThermostatView;
import com.szacs.ferroliconnect.widget.MyProgressDialog;
import com.szacs.ferroliconnect.widget.wheel.StrericWheelAdapter;
import com.szacs.ferroliconnect.widget.wheel.WheelView;
import java.util.Timer;
import java.util.TimerTask;

public class Thermostat186Activity extends MyNavigationActivity implements ThermostatView {
    /* access modifiers changed from: private */
    public int gatewayPosition;
    private ImageView ivHeating;
    private ImageView ivHeating2;
    private ImageView ivTempSettingIcon;
    private ImageView ivWeather;
    private LinearLayout llManual;
    private LinearLayout llOff;
    private LinearLayout llTempOff;
    private LinearLayout llTempSetting;
    private LinearLayout llWeather;
    private LinearLayout mainLinearLayout;
    private MyTimerTask myTimerTask;
    private MyProgressDialog progressDialog;
    private RelativeLayout rlManualButton;
    private RelativeLayout rlStandbyButton;
    private RelativeLayout rlTimingButton;
    private ScaleAnimation saGone;
    private ScaleAnimation saVisible;
    private int thermostatPosition;
    private Timer timer;
    private TextView tvCurrentTemp;
    private TextView tvHeating;
    private TextView tvHeating2;
    private TextView tvOutdoorTemperature;
    private TextView tvTargetTemp;
    private TextView tvWeatherLocation;
    private View vManualRedLine;
    private View vStandbyRedLine;
    private View vTimingRedLine;
    /* access modifiers changed from: private */
    public int waitRespondNum = 0;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_thermostat_186;
    }

    public void onGetThermostatInfoFailed(int i, boolean z) {
    }

    public void onGetThermostatInfoSuccess() {
    }

    public void onGetThermostatProgramFailed(int i, boolean z) {
    }

    public void onGetThermostatProgramSuccess(int i) {
    }

    public void onGetWeatherFailed(int i, boolean z) {
    }

    public void onGetWeatherSuccess() {
    }

    public void onSetThermostatWorkModeSuccess() {
    }

    public void onSetTimeZoneFailed(int i, boolean z) {
    }

    public void onSetWeatherLocationFailed(int i, boolean z) {
    }

    public void setTimeZone(int i, boolean z) {
    }

    public void setWeatherLocation(String str, String str2, String str3, String str4, String str5, String str6) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.gatewayPosition = intent.getIntExtra("gatewayPosition", 0);
        this.thermostatPosition = intent.getIntExtra("thermostatPosition", 0);
        initWidget();
        initWidgetFunction();
        initAnimation();
        this.progressDialog.show();
        this.timer = new Timer(true);
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
        this.myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() != C1683R.C1685id.muGatewayInfo) {
                    return true;
                }
                Intent intent = new Intent();
                intent.setClass(Thermostat186Activity.this, GatewayInfoActivity.class);
                intent.putExtra("gatewayPosition", Thermostat186Activity.this.gatewayPosition);
                Thermostat186Activity.this.startActivity(intent);
                return true;
            }
        });
    }

    private void initWidget() {
        this.mainLinearLayout = (LinearLayout) findViewById(C1683R.C1685id.mainLinearLayout);
        this.tvTargetTemp = (TextView) findViewById(C1683R.C1685id.tvTempSet);
        this.tvCurrentTemp = (TextView) findViewById(C1683R.C1685id.tvTempNow);
        this.tvOutdoorTemperature = (TextView) findViewById(C1683R.C1685id.tvTempOut);
        this.tvWeatherLocation = (TextView) findViewById(C1683R.C1685id.tvLocation);
        this.llOff = (LinearLayout) findViewById(C1683R.C1685id.llOff);
        this.llManual = (LinearLayout) findViewById(C1683R.C1685id.llManual);
        this.rlManualButton = (RelativeLayout) findViewById(C1683R.C1685id.rlManualButton);
        this.rlTimingButton = (RelativeLayout) findViewById(C1683R.C1685id.rlTimingButton);
        this.rlStandbyButton = (RelativeLayout) findViewById(C1683R.C1685id.rlStandbyButton);
        this.vManualRedLine = findViewById(C1683R.C1685id.vManualRedLine);
        this.vTimingRedLine = findViewById(C1683R.C1685id.vTimingRedLine);
        this.vStandbyRedLine = findViewById(C1683R.C1685id.vStandbyRedLine);
        this.llTempSetting = (LinearLayout) findViewById(C1683R.C1685id.llTempSetting);
        this.ivTempSettingIcon = (ImageView) findViewById(C1683R.C1685id.ivTempSettingIcon);
        this.ivWeather = (ImageView) findViewById(C1683R.C1685id.ivWeather);
        this.llTempOff = (LinearLayout) findViewById(C1683R.C1685id.llTempOff);
        this.llWeather = (LinearLayout) findViewById(C1683R.C1685id.llWeather);
        this.ivHeating = (ImageView) findViewById(C1683R.C1685id.ivHeating);
        this.ivHeating2 = (ImageView) findViewById(C1683R.C1685id.ivHeating2);
        this.tvHeating = (TextView) findViewById(C1683R.C1685id.tvHeating);
        this.tvHeating2 = (TextView) findViewById(C1683R.C1685id.tvHeating2);
        this.progressDialog = new MyProgressDialog(this);
        this.progressDialog.setMessage(getString(C1683R.string.public_loading));
    }

    private void initWidgetFunction() {
        WidgetActionListener widgetActionListener = new WidgetActionListener();
        this.llTempSetting.setOnClickListener(widgetActionListener);
        this.rlManualButton.setOnClickListener(widgetActionListener);
        this.rlTimingButton.setOnClickListener(widgetActionListener);
        this.rlStandbyButton.setOnClickListener(widgetActionListener);
        this.llWeather.setOnClickListener(widgetActionListener);
    }

    private class WidgetActionListener implements View.OnClickListener {
        private WidgetActionListener() {
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case C1683R.C1685id.llWeather:
                    new ChooseLocationFragment().show(Thermostat186Activity.this.getFragmentManager(), "chooseLocationFragment");
                    return;
                default:
                    return;
            }
        }
    }

    public int getGatewayPosition() {
        return this.gatewayPosition;
    }

    public int getThermostatPosition() {
        return this.thermostatPosition;
    }

    public void onGetThermostatSuccess() {
        reGetThermostatData();
    }

    public void onSetThermostatTemperatureSuccess() {
        this.waitRespondNum--;
    }

    public void onSetWeatherLocationSuccess() {
        Snackbar.make((View) this.llMain, (CharSequence) getString(C1683R.string.public_set_successfully), -1).show();
    }

    public void onGetThermostatFailed(int i, boolean z) {
        reGetThermostatData();
    }

    public void onSetThermostatTemperatureFailed(int i, boolean z) {
        this.waitRespondNum--;
    }

    public void onSetThermostatWorkModeFailed(int i, boolean z) {
        this.waitRespondNum--;
    }

    public void onSetTimeZoneSuccess() {
        Snackbar.make((View) this.llMain, (CharSequence) getString(C1683R.string.public_set_successfully), -1).show();
    }

    private void initWheel(WheelView wheelView, String[] strArr, int i) {
        wheelView.setAdapter(new StrericWheelAdapter(strArr));
        wheelView.setCurrentItem(i);
        wheelView.setCyclic(true);
        wheelView.setInterpolator(new AnticipateOvershootInterpolator());
    }

    private void initAnimation() {
        this.saVisible = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        this.saVisible.setDuration(200);
        this.saGone = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f);
        this.saGone.setDuration(200);
    }

    private void modeVisible(int i, boolean z) {
        if (i == 0) {
            this.llTempSetting.setVisibility(0);
            this.ivTempSettingIcon.setVisibility(0);
            this.llTempOff.setVisibility(8);
            this.llOff.setVisibility(8);
            this.llManual.setVisibility(0);
            this.vManualRedLine.setVisibility(0);
            this.vTimingRedLine.setVisibility(4);
            this.vStandbyRedLine.setVisibility(4);
        } else if (i == 1) {
            this.llTempSetting.setVisibility(0);
            this.ivTempSettingIcon.setVisibility(0);
            this.llTempOff.setVisibility(8);
            this.llOff.setVisibility(8);
            this.llManual.setVisibility(0);
            this.vManualRedLine.setVisibility(4);
            this.vTimingRedLine.setVisibility(0);
            this.vStandbyRedLine.setVisibility(4);
        } else if (i == 2) {
            this.llTempSetting.setVisibility(8);
            this.llTempOff.setVisibility(0);
            this.ivTempSettingIcon.setVisibility(0);
            this.llManual.setVisibility(8);
            this.llOff.setVisibility(0);
            this.vManualRedLine.setVisibility(4);
            this.vTimingRedLine.setVisibility(4);
            this.vStandbyRedLine.setVisibility(0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1683R.C1687menu.menu_thermostat_186, menu);
        return true;
    }

    private class MyTimerTask extends TimerTask {
        private MyTimerTask() {
        }

        public void run() {
            if (Thermostat186Activity.this.allowRefreshUI && Thermostat186Activity.this.waitRespondNum == 0) {
                Log.d("Refresh", "ThermostatData");
            }
        }
    }

    private void reGetThermostatData() {
        MyTimerTask myTimerTask2 = this.myTimerTask;
        if (myTimerTask2 != null) {
            myTimerTask2.cancel();
        }
        this.myTimerTask = new MyTimerTask();
        this.timer.schedule(this.myTimerTask, 5000);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }
}
