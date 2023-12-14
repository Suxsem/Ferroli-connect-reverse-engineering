package com.alibaba.sdk.android.push.register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.push.register.ReporterFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.control.AgooFactory;

public class ThirdPushManager {
    private static final String TAG = "com.alibaba.sdk.android.push.register.ThirdPushManager";
    @SuppressLint({"StaticFieldLeak"})
    private static AgooFactory agooFactory;

    public static void registerImpl(BaseNotifyClickActivity.INotifyListener iNotifyListener) {
        if (iNotifyListener != null) {
            BaseNotifyClickActivity.addNotifyListener(iNotifyListener);
        } else {
            ALog.m3727e(TAG, "BaseNotifyClickActivity.INotifyListener cannot be empty.", new Object[0]);
        }
    }

    public static void reportToken(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            ALog.m3727e(TAG, "Incorrect parameter", new Object[0]);
            return;
        }
        ReporterFactory.ITokenReporter tokenRepoter = ReporterFactory.getTokenRepoter();
        String addPrefix = ReporterFactory.addPrefix(str, str3);
        if (tokenRepoter instanceof ReporterFactory.ITokenReporterV2) {
            try {
                String str4 = TAG;
                ALog.m3728i(str4, "report sdkVer:3.7.6, source: " + str + ", ThirdToken: " + str2 + ", version: " + addPrefix, new Object[0]);
                ((ReporterFactory.ITokenReporterV2) tokenRepoter).reportToken(context, "3.7.6", str, addPrefix, str2);
            } catch (Throwable th) {
                ALog.m3726e(TAG, "reportToken", th, new Object[0]);
            }
        } else {
            try {
                String str5 = TAG;
                ALog.m3728i(str5, "report " + str + " ThirdToken: " + str2 + ", version: " + addPrefix, new Object[0]);
                tokenRepoter.reportToken(context, str, addPrefix, str2);
            } catch (Throwable th2) {
                ALog.m3726e(TAG, "reportToken", th2, new Object[0]);
            }
        }
    }

    public static void onPushMsg(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            ALog.m3727e(TAG, "Incorrect parameter", new Object[0]);
            return;
        }
        try {
            ReporterFactory.getMsgReporter().sendMsgToDecryptor(context, str, str2.getBytes("UTF-8"), (String) null);
        } catch (Throwable th) {
            ALog.m3726e(TAG, "onPushMsg", th, new Object[0]);
        }
    }

    public enum ThirdPushReportKeyword {
        HUAWEI("HW_TOKEN", AgooConstants.MESSAGE_SYSTEM_SOURCE_HUAWEI, "h_"),
        XIAOMI("MI_TOKEN", AgooConstants.MESSAGE_SYSTEM_SOURCE_XIAOMI, "mi_"),
        OPPO("OPPO_TOKEN", AgooConstants.MESSAGE_SYSTEM_SOURCE_OPPO, "o_"),
        VIVO("VIVO_TOKEN", AgooConstants.MESSAGE_SYSTEM_SOURCE_VIVO, "v_"),
        MEIZU("MZ_TOKEN", AgooConstants.MESSAGE_SYSTEM_SOURCE_MEIZU, "mz_"),
        FCM(AgooConstants.MESSAGE_SYSTEM_SOURCE_GCM, AgooConstants.MESSAGE_SYSTEM_SOURCE_GCM, "g_");
        
        public String thirdMsgKeyword;
        public String thirdSdkVersionPrefix;
        public String thirdTokenKeyword;

        private ThirdPushReportKeyword(String str, String str2, String str3) {
            this.thirdTokenKeyword = str;
            this.thirdMsgKeyword = str2;
            this.thirdSdkVersionPrefix = str3;
        }
    }
}
