package com.szacs.ferroliconnect.viewInterface;

public interface ThermostatView {
    int getGatewayPosition();

    int getThermostatPosition();

    void onGetThermostatFailed(int i, boolean z);

    void onGetThermostatInfoFailed(int i, boolean z);

    void onGetThermostatInfoSuccess();

    void onGetThermostatProgramFailed(int i, boolean z);

    void onGetThermostatProgramSuccess(int i);

    void onGetThermostatSuccess();

    void onGetWeatherFailed(int i, boolean z);

    void onGetWeatherSuccess();

    void onSetThermostatTemperatureFailed(int i, boolean z);

    void onSetThermostatTemperatureSuccess();

    void onSetThermostatWorkModeFailed(int i, boolean z);

    void onSetThermostatWorkModeSuccess();

    void onSetTimeZoneFailed(int i, boolean z);

    void onSetTimeZoneSuccess();

    void onSetWeatherLocationFailed(int i, boolean z);

    void onSetWeatherLocationSuccess();

    void setTimeZone(int i, boolean z);

    void setWeatherLocation(String str, String str2, String str3, String str4, String str5, String str6);
}
