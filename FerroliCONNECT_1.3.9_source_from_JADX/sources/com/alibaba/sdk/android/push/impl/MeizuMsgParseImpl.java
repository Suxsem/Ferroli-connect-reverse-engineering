package com.alibaba.sdk.android.push.impl;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.common.AgooConstants;

public class MeizuMsgParseImpl implements BaseNotifyClickActivity.INotifyListener {
    public static final String TAG = "MPS:MeizuPush";

    public String getMsgSource() {
        return AgooConstants.MESSAGE_SYSTEM_SOURCE_MEIZU;
    }

    public String parseMsgFromIntent(Intent intent) {
        String str = null;
        if (intent == null) {
            ALog.m3727e(TAG, "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            str = intent.getStringExtra(AgooConstants.MESSAGE_MEIZU_PAYLOAD);
            ALog.m3728i(TAG, "parseMsgFromIntent msg:" + str, new Object[0]);
            return str;
        } catch (Throwable th) {
            ALog.m3726e(TAG, "parseMsgFromIntent", th, new Object[0]);
            return str;
        }
    }
}
