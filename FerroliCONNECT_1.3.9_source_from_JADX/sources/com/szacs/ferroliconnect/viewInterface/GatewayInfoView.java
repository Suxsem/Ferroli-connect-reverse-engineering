package com.szacs.ferroliconnect.viewInterface;

public interface GatewayInfoView {
    int getGatewayPosition();

    void onBindGatewayFailed(int i, boolean z);

    void onBindGatewaySuccess();

    void onGetGatewayInfoFailed(int i, boolean z);

    void onGetGatewayInfoSuccess();

    void onGetTitleImageFailed(int i, boolean z);

    void onGetTitleImageSuccess();

    void onRemoveGatewayFailed(int i, boolean z);

    void onRemoveGatewaySuccess(int i);

    void onSetGatewayNameFailed(int i, boolean z);

    void onSetGatewayNameSuccess();

    void onSetOutdoorTempSourceFailed(int i, boolean z);

    void onSetOutdoorTempSourceSuccess();

    void onSetTimeZoneFailed(int i, boolean z);

    void onSetTimeZoneSuccess();

    void onSetTitleImageFailed(int i, boolean z);

    void onSetTitleImageSuccess();

    void onSetWeatherLocationFailed(int i, boolean z);

    void onSetWeatherLocationSuccess();

    void setTimeZone(int i, boolean z);

    void setWeatherLocation(String str, String str2, String str3, String str4, String str5, String str6);
}
