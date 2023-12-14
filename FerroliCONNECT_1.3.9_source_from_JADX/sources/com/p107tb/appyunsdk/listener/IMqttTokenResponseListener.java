package com.p107tb.appyunsdk.listener;

/* renamed from: com.tb.appyunsdk.listener.IMqttTokenResponseListener */
public interface IMqttTokenResponseListener {
    void onFailure(int i, String str);

    void onSuccess(String str, String str2, String str3);
}
