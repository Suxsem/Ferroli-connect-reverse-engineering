package com.alibaba.sdk.android.push.impl;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.common.AgooConstants;

public class HuaweiMsgParseImpl implements BaseNotifyClickActivity.INotifyListener {
    private static final String TAG = "MPS:HuaweiMsgParseImpl";

    public String getMsgSource() {
        return AgooConstants.MESSAGE_SYSTEM_SOURCE_HUAWEI;
    }

    public String parseMsgFromIntent(Intent intent) {
        if (intent == null) {
            ALog.m3727e(TAG, "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            return intent.getStringExtra("extras");
        } catch (Throwable th) {
            ALog.m3726e(TAG, "parseMsgFromIntent", th, new Object[0]);
            return null;
        }
    }
}
