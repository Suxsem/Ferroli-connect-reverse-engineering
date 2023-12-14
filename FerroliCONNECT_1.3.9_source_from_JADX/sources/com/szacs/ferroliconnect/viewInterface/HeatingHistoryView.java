package com.szacs.ferroliconnect.viewInterface;

public interface HeatingHistoryView {
    int getGatewayPosition();

    int getThermostatPosition();

    void onGetHeatingHistoryFailed(int i, boolean z);
}
