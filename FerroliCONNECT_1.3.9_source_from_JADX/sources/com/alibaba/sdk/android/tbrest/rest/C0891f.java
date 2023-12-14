package com.alibaba.sdk.android.tbrest.rest;

import android.content.Context;
import com.alibaba.sdk.android.tbrest.SendService;
import com.alibaba.sdk.android.tbrest.request.BizRequest;
import com.alibaba.sdk.android.tbrest.request.UrlWrapper;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.alibaba.sdk.android.tbrest.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.tbrest.rest.f */
/* compiled from: RestReqSend */
public class C0891f {
    /* renamed from: a */
    public static boolean m1025a(SendService sendService, String str, Context context, String str2, long j, String str3, int i, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        SendService sendService2 = sendService;
        try {
            LogUtil.m1032i("RestAPI start send log!");
            String a = C0890e.m1021a(sendService, str, j, str3, i, obj, obj2, obj3, map);
            if (StringUtils.isNotBlank(a)) {
                LogUtil.m1032i("RestAPI build data succ!");
                HashMap hashMap = new HashMap(1);
                hashMap.put(String.valueOf(i), a);
                byte[] bArr = null;
                String str4 = str;
                Context context2 = context;
                bArr = BizRequest.getPackRequest(sendService, str, context, hashMap);
                if (bArr == null) {
                    return false;
                }
                LogUtil.m1032i("packRequest success!");
                String str5 = str2;
                return UrlWrapper.sendRequest(sendService, str2, bArr).isSuccess();
            }
            LogUtil.m1032i("UTRestAPI build data failure!");
            return false;
        } catch (Exception e) {
            LogUtil.m1030e(e.toString());
        } catch (Throwable th) {
            LogUtil.m1031e("system error!", th);
            return false;
        }
    }

    /* renamed from: b */
    public static boolean m1026b(SendService sendService, String str, Context context, String str2, long j, String str3, int i, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        SendService sendService2 = sendService;
        String str4 = str;
        try {
            LogUtil.m1032i("RestAPI start send log by url!");
            String a = C0890e.m1021a(sendService, str, j, str3, i, obj, obj2, obj3, map);
            if (StringUtils.isNotBlank(a)) {
                LogUtil.m1032i("RestAPI build data succ by url!");
                HashMap hashMap = new HashMap(1);
                hashMap.put(String.valueOf(i), a);
                byte[] bArr = null;
                Context context2 = context;
                bArr = BizRequest.getPackRequest(sendService, str, context, hashMap);
                if (bArr == null) {
                    return false;
                }
                LogUtil.m1032i("packRequest success by url!");
                return UrlWrapper.sendRequest(sendService, str, str2, bArr).isSuccess();
            }
            LogUtil.m1032i("UTRestAPI build data failure by url!");
            return false;
        } catch (Exception e) {
            LogUtil.m1030e(e.toString());
        } catch (Throwable th) {
            LogUtil.m1031e("system error by url!", th);
            return false;
        }
    }

    @Deprecated
    /* renamed from: a */
    public static String m1024a(SendService sendService, String str, String str2, Context context, long j, String str3, int i, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        try {
            LogUtil.m1032i("sendLogByUrl RestAPI start send log!");
            C0889d a = C0890e.m1020a(sendService, str2, str, context, j, str3, i, obj, obj2, obj3, map);
            if (a != null) {
                LogUtil.m1032i("sendLogByUrl RestAPI build data succ!");
                Map a2 = a.mo10143a();
                if (a2 == null) {
                    LogUtil.m1032i("sendLogByUrl postReqData is null!");
                    return null;
                }
                String a3 = a.mo10143a();
                if (StringUtils.isEmpty(a3)) {
                    LogUtil.m1032i("sendLogByUrl reqUrl is null!");
                    return null;
                }
                byte[] a4 = C0884b.m1013a(2, a3, a2, true);
                if (a4 != null) {
                    String str4 = new String(a4, "UTF-8");
                    if (!StringUtils.isEmpty(str4)) {
                        return str4;
                    }
                }
            } else {
                LogUtil.m1032i("sendLogByUrl UTRestAPI build data failure!");
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.m1031e("sendLogByUrl result encoding UTF-8 error!", e);
        } catch (Throwable th) {
            LogUtil.m1031e("sendLogByUrl system error!", th);
        }
        return null;
    }
}
