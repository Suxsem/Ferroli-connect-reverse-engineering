package com.p107tb.appyunsdk.listener;

import com.p107tb.appyunsdk.bean.MqttConfigResponse;

/* renamed from: com.tb.appyunsdk.listener.IMqttConfigResponseListener */
public interface IMqttConfigResponseListener {
    void onFailure(int i, String str);

    void onSuccess(MqttConfigResponse mqttConfigResponse);
}
