package com.alibaba.sdk.android.push.p027g;

import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.p010b.C0672b;
import com.alibaba.sdk.android.ams.common.p010b.C0673c;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.common.p020a.C0804d;
import com.alibaba.sdk.android.push.p023d.C0817a;

/* renamed from: com.alibaba.sdk.android.push.g.b */
public class C0836b {
    /* renamed from: a */
    public static void m852a(CommonCallback commonCallback, C0839e eVar) {
        ErrorCode a = eVar.mo9926a();
        AmsLogger importantLogger = AmsLogger.getImportantLogger();
        importantLogger.mo9544i("errorCode:" + a);
        if (!a.getCode().contains(C0804d.f1091a.getCode())) {
            if (commonCallback != null) {
                commonCallback.onFailed(a.getCode(), a.getMsg());
            }
            m853a(a.getCode(), a.getMsg());
        } else if (commonCallback != null) {
            commonCallback.onSuccess(a.getMsg());
        }
    }

    /* renamed from: a */
    private static void m853a(String str, String str2) {
        C0672b a = C0673c.m489a();
        C0817a a2 = C0817a.m791a();
        if (a2 != null && a != null) {
            a2.mo9898a(str, str2, a.mo9528b());
        }
    }
}
