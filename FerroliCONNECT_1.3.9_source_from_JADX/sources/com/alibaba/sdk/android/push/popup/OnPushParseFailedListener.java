package com.alibaba.sdk.android.push.popup;

import android.content.Intent;

public interface OnPushParseFailedListener {
    void onNotPushData(Intent intent);

    void onParseFailed(Intent intent);
}
