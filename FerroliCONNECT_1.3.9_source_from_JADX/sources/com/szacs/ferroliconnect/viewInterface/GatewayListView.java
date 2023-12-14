package com.szacs.ferroliconnect.viewInterface;

public interface GatewayListView {
    void getTitleImage(int i);

    void onBindGatewayFailed(int i, boolean z);

    void onBindGatewaySuccess(String str);

    void onGetGatewayDataFailed(int i, boolean z);

    void onGetGatewayDataSuccess();

    void onGetGatewayListFailed(int i, boolean z);

    void onGetGatewayListSuccess();

    void onGetTitleImageFailed(int i, boolean z);

    void onGetTitleImageSuccess(int i, String str);

    void onRemoveGatewayFailed(int i, boolean z);

    void onRemoveGatewaySuccess(int i);

    void onSetGatewayNameFailed(int i, boolean z);

    void onSetGatewayNameSuccess(int i);
}
