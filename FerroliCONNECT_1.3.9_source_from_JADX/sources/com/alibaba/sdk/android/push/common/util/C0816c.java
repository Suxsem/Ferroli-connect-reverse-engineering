package com.alibaba.sdk.android.push.common.util;

import android.content.Context;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;

/* renamed from: com.alibaba.sdk.android.push.common.util.c */
public class C0816c {

    /* renamed from: a */
    protected static final String f1160a = "com.alibaba.sdk.android.push.common.util.c";

    /* renamed from: b */
    private static AmsLogger f1161b = AmsLogger.getLogger(f1160a);

    /* renamed from: a */
    public static boolean m790a(Context context) {
        return AppInfoUtil.isMainProcess(context);
    }
}
