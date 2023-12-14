package com.taobao.accs.client;

import android.content.Context;
import com.taobao.accs.ChannelService;
import com.taobao.accs.IProcessName;
import com.taobao.accs.data.Message;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.OrangeAdapter;

/* compiled from: Taobao */
public class GlobalConfig {
    public static boolean enableCookie = true;

    public static void setControlFrameMaxRetry(int i) {
        Message.f3240a = i;
    }

    public static void setMainProcessName(String str) {
        AdapterGlobalClientInfo.mMainProcessName = str;
    }

    public static void setChannelProcessName(String str) {
        AdapterGlobalClientInfo.mChannelProcessName = str;
    }

    public static void setCurrProcessNameImpl(IProcessName iProcessName) {
        AdapterGlobalClientInfo.mProcessNameImpl = iProcessName;
    }

    public static void setEnableForground(Context context, boolean z) {
        int i = 0;
        ALog.m3728i("GlobalConfig", "setEnableForground", "enable", Boolean.valueOf(z));
        if (z) {
            i = 24;
        }
        OrangeAdapter.saveConfigToSP(context, ChannelService.SUPPORT_FOREGROUND_VERSION_KEY, i);
    }
}
