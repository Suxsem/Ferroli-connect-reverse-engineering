package com.igexin.assist.util;

import android.content.Context;
import android.text.TextUtils;
import com.igexin.push.util.C1593r;
import com.igexin.sdk.PushManager;

public class AssistUtils {
    public static void startGetuiService(Context context) {
        if (context != null) {
            try {
                String str = (String) C1593r.m3270c(context, "us", "");
                Class<?> cls = null;
                if (!TextUtils.isEmpty(str)) {
                    cls = Class.forName(str);
                }
                PushManager.getInstance().initialize(context, cls);
            } catch (Throwable unused) {
            }
        }
    }
}
