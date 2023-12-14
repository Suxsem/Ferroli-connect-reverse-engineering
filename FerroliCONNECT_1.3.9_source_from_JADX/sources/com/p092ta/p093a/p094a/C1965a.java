package com.p092ta.p093a.p094a;

import android.content.Context;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.p092ta.p093a.C1964a;
import com.p092ta.utdid2.p097a.p098a.C1988d;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.ta.a.a.a */
public class C1965a {
    /* renamed from: a */
    public static String m3334a(String str) {
        Context context = C1964a.mo18112a().getContext();
        if (context == null) {
            return "";
        }
        return C1988d.m3382e(String.format("{\"type\":\"%s\",\"timestamp\":%s,\"data\":%s}", new Object[]{"audid", C1964a.mo18112a().mo18112a(), m3335a(str, "", "", context.getPackageName())}));
    }

    /* renamed from: a */
    private static String m3335a(String str, String str2, String str3, String str4) {
        HashMap hashMap = new HashMap();
        hashMap.put("audid", str2);
        hashMap.put("utdid", str);
        hashMap.put("appkey", str3);
        hashMap.put(DispatchConstants.APP_NAME, str4);
        return new JSONObject(C1988d.m3380a((Map<String, String>) hashMap)).toString();
    }
}
