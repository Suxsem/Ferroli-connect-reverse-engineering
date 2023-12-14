package com.szacs.ferroliconnect.viewInterface;

public interface BindGatewayView {
    void onBindGatewayFailed(int i, boolean z);

    void onBindGatewaySuccess();

    void onGetGatewayListFailed(int i, boolean z);

    void onGetGatewayListSuccess();
}
