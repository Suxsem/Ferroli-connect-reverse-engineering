package com.alibaba.sdk.android.crashdefend;

public interface CrashDefendCallback {
    void onSdkClosed(int i);

    void onSdkStart(int i, int i2, int i3);

    void onSdkStop(int i, int i2, int i3, long j);
}
