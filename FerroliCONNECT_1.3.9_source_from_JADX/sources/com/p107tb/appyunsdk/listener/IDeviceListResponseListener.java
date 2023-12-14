package com.p107tb.appyunsdk.listener;

import com.p107tb.appyunsdk.bean.ResultsBean;
import java.util.List;

/* renamed from: com.tb.appyunsdk.listener.IDeviceListResponseListener */
public interface IDeviceListResponseListener {
    void onFailure(int i, String str);

    void onSuccess(int i, String str, String str2, List<ResultsBean> list);
}
