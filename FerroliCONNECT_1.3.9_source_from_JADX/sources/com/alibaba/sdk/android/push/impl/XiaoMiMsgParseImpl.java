package com.alibaba.sdk.android.push.impl;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.common.AgooConstants;

public class XiaoMiMsgParseImpl implements BaseNotifyClickActivity.INotifyListener {
    private static final String TAG = "MPS:MiPushRegistar";

    public String getMsgSource() {
        return AgooConstants.MESSAGE_SYSTEM_SOURCE_XIAOMI;
    }

    public String parseMsgFromIntent(Intent intent) {
        String str;
        try {
            str = intent.getSerializableExtra("key_message").getContent();
        } catch (Exception unused) {
            str = null;
        }
        ALog.m3728i(TAG, "parseMsgFromIntent msg:" + str, new Object[0]);
        return str;
    }

    public String toString() {
        return "INotifyListener: " + getMsgSource();
    }
}
