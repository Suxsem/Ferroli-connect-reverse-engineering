package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;
import com.triggertrap.seekarc.SeekArc;

public class BoilerActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private BoilerActivity target;

    @UiThread
    public BoilerActivity_ViewBinding(BoilerActivity boilerActivity) {
        this(boilerActivity, boilerActivity.getWindow().getDecorView());
    }

    @UiThread
    public BoilerActivity_ViewBinding(BoilerActivity boilerActivity, View view) {
        super(boilerActivity, view);
        this.target = boilerActivity;
        boilerActivity.mainLinearLayout = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.boilerLayout, "field 'mainLinearLayout'", LinearLayout.class);
        boilerActivity.LLheat = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llHeat, "field 'LLheat'", LinearLayout.class);
        boilerActivity.heatLayout = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.heatLayout, "field 'heatLayout'", LinearLayout.class);
        boilerActivity.llWeather = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llWeather, "field 'llWeather'", LinearLayout.class);
        boilerActivity.llWinter = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.RLWinter, "field 'llWinter'", LinearLayout.class);
        boilerActivity.tvHeatingCurrentTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeatingCurrentTemp, "field 'tvHeatingCurrentTemp'", TextView.class);
        boilerActivity.tvSystemPressure = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvPressure, "field 'tvSystemPressure'", TextView.class);
        boilerActivity.tvHeatingStatus = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeating, "field 'tvHeatingStatus'", TextView.class);
        boilerActivity.tvFlameStatus = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvFlame, "field 'tvFlameStatus'", TextView.class);
        boilerActivity.tvError = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvError, "field 'tvError'", TextView.class);
        boilerActivity.tvOutdoorTemperature = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTempOut, "field 'tvOutdoorTemperature'", TextView.class);
        boilerActivity.tvWeatherLocation = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvLocation, "field 'tvWeatherLocation'", TextView.class);
        boilerActivity.tvHeatingTargetTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvSetTemperature, "field 'tvHeatingTargetTemp'", TextView.class);
        boilerActivity.tvDhwTargetTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTempDomesticTarget, "field 'tvDhwTargetTemp'", TextView.class);
        boilerActivity.SummerTargetTv = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.summer_dhw, "field 'SummerTargetTv'", TextView.class);
        boilerActivity.OFFTv = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.OFFTv, "field 'OFFTv'", TextView.class);
        boilerActivity.ivHeatingStatus = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivHeating, "field 'ivHeatingStatus'", ImageView.class);
        boilerActivity.ivFlameStatus = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivFlame, "field 'ivFlameStatus'", ImageView.class);
        boilerActivity.ivError = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivError, "field 'ivError'", ImageView.class);
        boilerActivity.ivReset = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivRS, "field 'ivReset'", ImageView.class);
        boilerActivity.ivWeather = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivWeather, "field 'ivWeather'", ImageView.class);
        boilerActivity.ivWinter = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivWinter, "field 'ivWinter'", ImageView.class);
        boilerActivity.ivSummer = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivSummer, "field 'ivSummer'", ImageView.class);
        boilerActivity.ivStandby = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivStandby, "field 'ivStandby'", ImageView.class);
        boilerActivity.rlWinter = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlWinter, "field 'rlWinter'", RelativeLayout.class);
        boilerActivity.rlSummer = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlSummer, "field 'rlSummer'", RelativeLayout.class);
        boilerActivity.rlStandby = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlStandby, "field 'rlStandby'", RelativeLayout.class);
        boilerActivity.RlSummer = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.RLSummer, "field 'RlSummer'", RelativeLayout.class);
        boilerActivity.seekArcTarget = (SeekArc) Utils.findRequiredViewAsType(view, C1683R.C1685id.seekArcTarget, "field 'seekArcTarget'", SeekArc.class);
        boilerActivity.seekArcDhw = (SeekArc) Utils.findRequiredViewAsType(view, C1683R.C1685id.seekArcDhw, "field 'seekArcDhw'", SeekArc.class);
        boilerActivity.SummerSeekArc = (SeekArc) Utils.findRequiredViewAsType(view, C1683R.C1685id.seekArcSummerDhw, "field 'SummerSeekArc'", SeekArc.class);
        boilerActivity.heatIcon = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.heat_icon, "field 'heatIcon'", ImageView.class);
        boilerActivity.modeTv = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.mode_tv, "field 'modeTv'", TextView.class);
        boilerActivity.ivWifiSignal = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_wifi, "field 'ivWifiSignal'", ImageView.class);
    }

    public void unbind() {
        BoilerActivity boilerActivity = this.target;
        if (boilerActivity != null) {
            this.target = null;
            boilerActivity.mainLinearLayout = null;
            boilerActivity.LLheat = null;
            boilerActivity.heatLayout = null;
            boilerActivity.llWeather = null;
            boilerActivity.llWinter = null;
            boilerActivity.tvHeatingCurrentTemp = null;
            boilerActivity.tvSystemPressure = null;
            boilerActivity.tvHeatingStatus = null;
            boilerActivity.tvFlameStatus = null;
            boilerActivity.tvError = null;
            boilerActivity.tvOutdoorTemperature = null;
            boilerActivity.tvWeatherLocation = null;
            boilerActivity.tvHeatingTargetTemp = null;
            boilerActivity.tvDhwTargetTemp = null;
            boilerActivity.SummerTargetTv = null;
            boilerActivity.OFFTv = null;
            boilerActivity.ivHeatingStatus = null;
            boilerActivity.ivFlameStatus = null;
            boilerActivity.ivError = null;
            boilerActivity.ivReset = null;
            boilerActivity.ivWeather = null;
            boilerActivity.ivWinter = null;
            boilerActivity.ivSummer = null;
            boilerActivity.ivStandby = null;
            boilerActivity.rlWinter = null;
            boilerActivity.rlSummer = null;
            boilerActivity.rlStandby = null;
            boilerActivity.RlSummer = null;
            boilerActivity.seekArcTarget = null;
            boilerActivity.seekArcDhw = null;
            boilerActivity.SummerSeekArc = null;
            boilerActivity.heatIcon = null;
            boilerActivity.modeTv = null;
            boilerActivity.ivWifiSignal = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
