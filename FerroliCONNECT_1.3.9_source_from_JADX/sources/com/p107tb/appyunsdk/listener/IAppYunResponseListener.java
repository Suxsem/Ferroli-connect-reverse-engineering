package com.p107tb.appyunsdk.listener;

/* renamed from: com.tb.appyunsdk.listener.IAppYunResponseListener */
public interface IAppYunResponseListener<T> {
    void onFailure(int i, String str);

    void onSuccess(T t);
}
