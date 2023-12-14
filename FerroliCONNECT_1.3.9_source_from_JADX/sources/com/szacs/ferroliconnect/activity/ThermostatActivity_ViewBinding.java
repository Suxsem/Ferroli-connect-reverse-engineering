package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.widget.TemperLayout;
import com.szacs.ferroliconnect.widget.wheel.BarViewBeSmart196;

public class ThermostatActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private ThermostatActivity target;

    @UiThread
    public ThermostatActivity_ViewBinding(ThermostatActivity thermostatActivity) {
        this(thermostatActivity, thermostatActivity.getWindow().getDecorView());
    }

    @UiThread
    public ThermostatActivity_ViewBinding(ThermostatActivity thermostatActivity, View view) {
        super(thermostatActivity, view);
        this.target = thermostatActivity;
        thermostatActivity.mainLinearLayout = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.mainLinearLayout, "field 'mainLinearLayout'", LinearLayout.class);
        thermostatActivity.llProgram = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llProgram, "field 'llProgram'", LinearLayout.class);
        thermostatActivity.llManual = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llManual, "field 'llManual'", LinearLayout.class);
        thermostatActivity.llOff = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llOff, "field 'llOff'", LinearLayout.class);
        thermostatActivity.llWeather = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llWeather, "field 'llWeather'", LinearLayout.class);
        thermostatActivity.proHeatLayout = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.pro_heat_layout, "field 'proHeatLayout'", LinearLayout.class);
        thermostatActivity.llholiday = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llholiday, "field 'llholiday'", LinearLayout.class);
        thermostatActivity.bvProgram = (BarViewBeSmart196) Utils.findRequiredViewAsType(view, C1683R.C1685id.bvProgram, "field 'bvProgram'", BarViewBeSmart196.class);
        thermostatActivity.hsvProgram = (HorizontalScrollView) Utils.findRequiredViewAsType(view, C1683R.C1685id.hsvProgram, "field 'hsvProgram'", HorizontalScrollView.class);
        thermostatActivity.tvCurrentTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTempNow, "field 'tvCurrentTemp'", TextView.class);
        thermostatActivity.tvOutdoorTemperature = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTempOut, "field 'tvOutdoorTemperature'", TextView.class);
        thermostatActivity.tvWeatherLocation = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvLocation, "field 'tvWeatherLocation'", TextView.class);
        thermostatActivity.tvHeating = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeating, "field 'tvHeating'", TextView.class);
        thermostatActivity.tvHeating3 = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeating3, "field 'tvHeating3'", TextView.class);
        thermostatActivity.tvNotice = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvNotice, "field 'tvNotice'", TextView.class);
        thermostatActivity.proHeatTextview = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.pro_heat_tv, "field 'proHeatTextview'", TextView.class);
        thermostatActivity.tvEndDate = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.end_date, "field 'tvEndDate'", TextView.class);
        thermostatActivity.rlManualButton = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlManualButton, "field 'rlManualButton'", RelativeLayout.class);
        thermostatActivity.rlTimingButton = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlTimingButton, "field 'rlTimingButton'", RelativeLayout.class);
        thermostatActivity.rlHoliday = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlHoliday, "field 'rlHoliday'", RelativeLayout.class);
        thermostatActivity.rlStandbyButton = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlStandbyButton, "field 'rlStandbyButton'", RelativeLayout.class);
        thermostatActivity.rlNotice = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlNotice, "field 'rlNotice'", RelativeLayout.class);
        thermostatActivity.ivTempSettingIcon = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivTempSettingIcon, "field 'ivTempSettingIcon'", ImageView.class);
        thermostatActivity.ivWeather = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivWeather, "field 'ivWeather'", ImageView.class);
        thermostatActivity.ivHeating = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivHeating, "field 'ivHeating'", ImageView.class);
        thermostatActivity.ivHeating3 = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivHeating3, "field 'ivHeating3'", ImageView.class);
        thermostatActivity.ivManual = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivManual, "field 'ivManual'", ImageView.class);
        thermostatActivity.ivTiming = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivTimming, "field 'ivTiming'", ImageView.class);
        thermostatActivity.ivHoliday = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivHoliday, "field 'ivHoliday'", ImageView.class);
        thermostatActivity.ivStandby = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivStandby, "field 'ivStandby'", ImageView.class);
        thermostatActivity.proHeatImageView = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.pro_heat_img, "field 'proHeatImageView'", ImageView.class);
        thermostatActivity.holidaySetImg = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.holiday_set, "field 'holidaySetImg'", ImageView.class);
        thermostatActivity.llTempSetting = (TemperLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llTempSetting, "field 'llTempSetting'", TemperLayout.class);
        thermostatActivity.tvLocation1 = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tv_location1, "field 'tvLocation1'", TextView.class);
        thermostatActivity.ivLowBattery = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_low_battery, "field 'ivLowBattery'", ImageView.class);
    }

    public void unbind() {
        ThermostatActivity thermostatActivity = this.target;
        if (thermostatActivity != null) {
            this.target = null;
            thermostatActivity.mainLinearLayout = null;
            thermostatActivity.llProgram = null;
            thermostatActivity.llManual = null;
            thermostatActivity.llOff = null;
            thermostatActivity.llWeather = null;
            thermostatActivity.proHeatLayout = null;
            thermostatActivity.llholiday = null;
            thermostatActivity.bvProgram = null;
            thermostatActivity.hsvProgram = null;
            thermostatActivity.tvCurrentTemp = null;
            thermostatActivity.tvOutdoorTemperature = null;
            thermostatActivity.tvWeatherLocation = null;
            thermostatActivity.tvHeating = null;
            thermostatActivity.tvHeating3 = null;
            thermostatActivity.tvNotice = null;
            thermostatActivity.proHeatTextview = null;
            thermostatActivity.tvEndDate = null;
            thermostatActivity.rlManualButton = null;
            thermostatActivity.rlTimingButton = null;
            thermostatActivity.rlHoliday = null;
            thermostatActivity.rlStandbyButton = null;
            thermostatActivity.rlNotice = null;
            thermostatActivity.ivTempSettingIcon = null;
            thermostatActivity.ivWeather = null;
            thermostatActivity.ivHeating = null;
            thermostatActivity.ivHeating3 = null;
            thermostatActivity.ivManual = null;
            thermostatActivity.ivTiming = null;
            thermostatActivity.ivHoliday = null;
            thermostatActivity.ivStandby = null;
            thermostatActivity.proHeatImageView = null;
            thermostatActivity.holidaySetImg = null;
            thermostatActivity.llTempSetting = null;
            thermostatActivity.tvLocation1 = null;
            thermostatActivity.ivLowBattery = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
