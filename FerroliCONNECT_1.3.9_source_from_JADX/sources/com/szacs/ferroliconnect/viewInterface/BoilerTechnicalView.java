package com.szacs.ferroliconnect.viewInterface;

public interface BoilerTechnicalView {
    int getGatewayPosition();

    void getTSP(String str);

    void onGetAlarmRecordFailed();

    void onGetAlarmRecordSuccess();

    void onGetDHWCurrentTemperatureFailed();

    void onGetDHWCurrentTemperatureSuccess();

    void onGetDHWTargetTemperatureFailed();

    void onGetDHWTargetTemperatureSuccess();

    void onGetFlameStatusFailed();

    void onGetFlameStatusSuccess();

    void onGetHeatingFlowTemperatureFailed();

    void onGetHeatingFlowTemperatureSuccess();

    void onGetHeatingHoursFailed();

    void onGetHeatingHoursSuccess();

    void onGetHeatingTargetTempSettingOptionsFailed(int i, boolean z);

    void onGetHeatingTargetTempSettingOptionsSuccess();

    void onGetHeatingTargetTemperatureFailed();

    void onGetHeatingTargetTemperatureSuccess();

    void onGetHotWaterFlowRateFailed();

    void onGetHotWaterFlowRateSuccess();

    void onGetMaxHeatingTargetTemperatureFailed();

    void onGetMaxHeatingTargetTemperatureSuccess();

    void onGetModulationRatioFailed();

    void onGetModulationRatioSuccess();

    void onGetSystemPressureFailed();

    void onGetSystemPressureSuccess();

    void onGetTSPFailed();

    void onGetTSPSuccess(int i);

    void onSetHeatingTargetTempSettingOptionsFailed(int i, boolean z);

    void onSetHeatingTargetTempSettingOptionsSuccess();

    void onSetMaxHeatingTargetTemperatureFailed(int i, boolean z);

    void onSetMaxHeatingTargetTemperatureSuccess();

    void onSetTSPFailed();

    void onSetTSPSuccess();

    void setHeatingTargetTempSettingOptions(int i);

    void setTSP(String str, String str2);
}
