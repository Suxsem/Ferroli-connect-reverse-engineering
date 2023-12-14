package com.p107tb.appyunsdk.network;

import com.p107tb.appyunsdk.utils.YunLog;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import p110io.reactivex.Observable;
import p110io.reactivex.ObservableSource;
import p110io.reactivex.functions.Function;

/* renamed from: com.tb.appyunsdk.network.RetryRequest */
public class RetryRequest implements Function<Observable<Throwable>, ObservableSource<?>> {
    private static final String TAG = "RetryRequest";
    /* access modifiers changed from: private */
    public int currentRetryCount = 0;
    /* access modifiers changed from: private */
    public int maxConnectCount = 10;
    /* access modifiers changed from: private */
    public int waitRetryTime = 0;

    static /* synthetic */ int access$008(RetryRequest retryRequest) {
        int i = retryRequest.currentRetryCount;
        retryRequest.currentRetryCount = i + 1;
        return i;
    }

    public RetryRequest(int i, int i2) {
        this.maxConnectCount = i;
        this.waitRetryTime = i2;
    }

    public ObservableSource<?> apply(Observable<Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            public ObservableSource<?> apply(Throwable th) throws Exception {
                YunLog.m3843d(RetryRequest.TAG, "registerDevice" + th);
                YunLog.m3843d(RetryRequest.TAG, "第" + RetryRequest.this.currentRetryCount + "次");
                if (!(th instanceof IOException)) {
                    return Observable.error(new Throwable("发生了非网络异常（非I/O异常"));
                }
                if (RetryRequest.this.currentRetryCount < RetryRequest.this.maxConnectCount) {
                    RetryRequest.access$008(RetryRequest.this);
                    return Observable.just(1).delay((long) RetryRequest.this.waitRetryTime, TimeUnit.SECONDS);
                }
                return Observable.error(new Throwable("重试次数已超过设置次数 = " + RetryRequest.this.currentRetryCount + "，即不再重试"));
            }
        });
    }
}
