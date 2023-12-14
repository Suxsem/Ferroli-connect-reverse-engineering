package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.baoyz.widget.PullRefreshLayout;
import com.szacs.ferroliconnect.C1683R;

public class BoilerTechnicalActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private BoilerTechnicalActivity target;

    @UiThread
    public BoilerTechnicalActivity_ViewBinding(BoilerTechnicalActivity boilerTechnicalActivity) {
        this(boilerTechnicalActivity, boilerTechnicalActivity.getWindow().getDecorView());
    }

    @UiThread
    public BoilerTechnicalActivity_ViewBinding(BoilerTechnicalActivity boilerTechnicalActivity, View view) {
        super(boilerTechnicalActivity, view);
        this.target = boilerTechnicalActivity;
        boilerTechnicalActivity.mainLinearLayout = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.mainLinearLayout, "field 'mainLinearLayout'", LinearLayout.class);
        boilerTechnicalActivity.llAlarmRecord = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llAlarmCode, "field 'llAlarmRecord'", LinearLayout.class);
        boilerTechnicalActivity.llMaxHeatingTargetTemperature = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llMaxHeatingSetPoint, "field 'llMaxHeatingTargetTemperature'", LinearLayout.class);
        boilerTechnicalActivity.llHeatingTargetTempSettingOptions = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llHeatingTargetTempSettingOptions, "field 'llHeatingTargetTempSettingOptions'", LinearLayout.class);
        boilerTechnicalActivity.tvSystemPressure = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTechValueSysPress, "field 'tvSystemPressure'", TextView.class);
        boilerTechnicalActivity.tvHeatingTargetTemperature = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTechValueHeatingSetPoint, "field 'tvHeatingTargetTemperature'", TextView.class);
        boilerTechnicalActivity.tvMaxHeatingTargetTemperature = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTechValueMaxHeatingSetPoint, "field 'tvMaxHeatingTargetTemperature'", TextView.class);
        boilerTechnicalActivity.tvDHWTargetTemperature = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTechValueHotWaterSetPoint, "field 'tvDHWTargetTemperature'", TextView.class);
        boilerTechnicalActivity.tvHeatingCurrentTemperature = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeatCurrentTemp, "field 'tvHeatingCurrentTemperature'", TextView.class);
        boilerTechnicalActivity.tvDHWCurrentTemperature = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTechValueHotWaterTemp, "field 'tvDHWCurrentTemperature'", TextView.class);
        boilerTechnicalActivity.tvFlameStatus = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTechValueFlameStatus, "field 'tvFlameStatus'", TextView.class);
        boilerTechnicalActivity.tvHeatingHours = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTechValueHoursHeatExchangerCleaning, "field 'tvHeatingHours'", TextView.class);
        boilerTechnicalActivity.tvModulationRatio = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTechValueModulationRadio, "field 'tvModulationRatio'", TextView.class);
        boilerTechnicalActivity.tvHeatingTargetTempSettingOption = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeatingTargetTempSettingOption, "field 'tvHeatingTargetTempSettingOption'", TextView.class);
        boilerTechnicalActivity.tvHeatTempRange = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeatTempRange, "field 'tvHeatTempRange'", TextView.class);
        boilerTechnicalActivity.tvHeatTempRangeUnit = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeatTempRangeUnit, "field 'tvHeatTempRangeUnit'", TextView.class);
        boilerTechnicalActivity.tvBathWaterTempRange = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvBathTempRange, "field 'tvBathWaterTempRange'", TextView.class);
        boilerTechnicalActivity.tvBathWaterTempRangeUnit = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvBathTempUnit, "field 'tvBathWaterTempRangeUnit'", TextView.class);
        boilerTechnicalActivity.tvHeatStatus = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeatStatus, "field 'tvHeatStatus'", TextView.class);
        boilerTechnicalActivity.tvBathWaterSupplySwith = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvWaterSwitch, "field 'tvBathWaterSupplySwith'", TextView.class);
        boilerTechnicalActivity.tvMachineType = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvMachineType, "field 'tvMachineType'", TextView.class);
        boilerTechnicalActivity.tvHeatType = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHeatType, "field 'tvHeatType'", TextView.class);
        boilerTechnicalActivity.tvGasType = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvGasType, "field 'tvGasType'", TextView.class);
        boilerTechnicalActivity.tvConnectType = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvConnectType, "field 'tvConnectType'", TextView.class);
        boilerTechnicalActivity.tvOutTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvOutTemp, "field 'tvOutTemp'", TextView.class);
        boilerTechnicalActivity.tvFlueTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvFlueTemp, "field 'tvFlueTemp'", TextView.class);
        boilerTechnicalActivity.tvReturnWaterTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvReturnWaterTemp, "field 'tvReturnWaterTemp'", TextView.class);
        boilerTechnicalActivity.tvOutWaterTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvOutWaterTemp, "field 'tvOutWaterTemp'", TextView.class);
        boilerTechnicalActivity.tvWaterRate = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvWaterRate, "field 'tvWaterRate'", TextView.class);
        boilerTechnicalActivity.refreshLayout = (PullRefreshLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.refreshLayout, "field 'refreshLayout'", PullRefreshLayout.class);
    }

    public void unbind() {
        BoilerTechnicalActivity boilerTechnicalActivity = this.target;
        if (boilerTechnicalActivity != null) {
            this.target = null;
            boilerTechnicalActivity.mainLinearLayout = null;
            boilerTechnicalActivity.llAlarmRecord = null;
            boilerTechnicalActivity.llMaxHeatingTargetTemperature = null;
            boilerTechnicalActivity.llHeatingTargetTempSettingOptions = null;
            boilerTechnicalActivity.tvSystemPressure = null;
            boilerTechnicalActivity.tvHeatingTargetTemperature = null;
            boilerTechnicalActivity.tvMaxHeatingTargetTemperature = null;
            boilerTechnicalActivity.tvDHWTargetTemperature = null;
            boilerTechnicalActivity.tvHeatingCurrentTemperature = null;
            boilerTechnicalActivity.tvDHWCurrentTemperature = null;
            boilerTechnicalActivity.tvFlameStatus = null;
            boilerTechnicalActivity.tvHeatingHours = null;
            boilerTechnicalActivity.tvModulationRatio = null;
            boilerTechnicalActivity.tvHeatingTargetTempSettingOption = null;
            boilerTechnicalActivity.tvHeatTempRange = null;
            boilerTechnicalActivity.tvHeatTempRangeUnit = null;
            boilerTechnicalActivity.tvBathWaterTempRange = null;
            boilerTechnicalActivity.tvBathWaterTempRangeUnit = null;
            boilerTechnicalActivity.tvHeatStatus = null;
            boilerTechnicalActivity.tvBathWaterSupplySwith = null;
            boilerTechnicalActivity.tvMachineType = null;
            boilerTechnicalActivity.tvHeatType = null;
            boilerTechnicalActivity.tvGasType = null;
            boilerTechnicalActivity.tvConnectType = null;
            boilerTechnicalActivity.tvOutTemp = null;
            boilerTechnicalActivity.tvFlueTemp = null;
            boilerTechnicalActivity.tvReturnWaterTemp = null;
            boilerTechnicalActivity.tvOutWaterTemp = null;
            boilerTechnicalActivity.tvWaterRate = null;
            boilerTechnicalActivity.refreshLayout = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
