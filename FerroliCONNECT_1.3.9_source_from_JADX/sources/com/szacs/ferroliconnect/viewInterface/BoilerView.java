package com.szacs.ferroliconnect.viewInterface;

public interface BoilerView {
    int getGatewayPosition();

    void onGetBoilerDataFailed(int i, boolean z);

    void onGetBoilerDataSuccess();

    void onGetWeatherFailed(int i, boolean z);

    void onGetWeatherSuccess();

    void onResetBoilerFailed(int i, boolean z);

    void onResetBoilerSuccess();

    void onSetDHWTargetTemperatureFailed(int i, boolean z);

    void onSetDHWTargetTemperatureSuccess();

    void onSetHeatingTargetTemperatureFailed(int i, boolean z);

    void onSetHeatingTargetTemperatureSuccess();

    void onSetMaxHeatingTargetTemperatureFailed(int i, boolean z);

    void onSetMaxHeatingTargetTemperatureSuccess();

    void onSetWeatherLocationFailed(int i, boolean z);

    void onSetWeatherLocationSuccess();

    void onSetWorkModeFailed(int i, boolean z);

    void onSetWorkModeSuccess();

    void setWeatherLocation(String str, String str2, String str3, String str4, String str5, String str6);
}
