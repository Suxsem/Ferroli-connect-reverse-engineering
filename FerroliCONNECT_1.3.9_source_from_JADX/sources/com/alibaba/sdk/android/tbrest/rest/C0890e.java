package com.alibaba.sdk.android.tbrest.rest;

import android.content.Context;
import android.os.Build;
import com.alibaba.sdk.android.tbrest.SendService;
import com.alibaba.sdk.android.tbrest.utils.DeviceUtils;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.alibaba.sdk.android.tbrest.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.tbrest.rest.e */
/* compiled from: RestReqDataBuilder */
public class C0890e {

    /* renamed from: c */
    private static long f1379c = System.currentTimeMillis();

    /* renamed from: a */
    private static String m1022a(String str) {
        if (StringUtils.isBlank(str)) {
            return "-";
        }
        if ("".equals(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (!(charArray[i] == 10 || charArray[i] == 13 || charArray[i] == 9 || charArray[i] == '|')) {
                sb.append(charArray[i]);
            }
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static String m1021a(SendService sendService, String str, long j, String str2, int i, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        long j2;
        SendService sendService2 = sendService;
        String str3 = null;
        if (i == 0) {
            return null;
        }
        try {
            String utdid = DeviceUtils.getUtdid(sendService2.context);
            if (utdid == null) {
                LogUtil.m1030e("get utdid failure, so build report failure, now return");
                return null;
            }
            String[] networkType = DeviceUtils.getNetworkType(sendService2.context);
            String str4 = networkType[0];
            if (networkType.length > 1 && str4 != null && !DeviceUtils.NETWORK_CLASS_WIFI.equals(str4)) {
                str3 = networkType[1];
            }
            if (j > 0) {
                j2 = j;
            } else {
                j2 = System.currentTimeMillis();
            }
            String str5 = "" + j2;
            String a = m1022a(str2);
            String a2 = m1022a(String.valueOf(i));
            String a3 = m1022a(StringUtils.convertObjectToString(obj));
            String a4 = m1022a(StringUtils.convertObjectToString(obj2));
            String a5 = m1022a(StringUtils.convertObjectToString(obj3));
            String a6 = m1022a(StringUtils.convertMapToString(map));
            String a7 = m1022a(DeviceUtils.getImei(sendService2.context));
            String a8 = m1022a(DeviceUtils.getImsi(sendService2.context));
            String a9 = m1022a(Build.BRAND);
            m1022a(DeviceUtils.getCpuName());
            m1022a(a7);
            String str6 = a6;
            String a10 = m1022a(Build.MODEL);
            String str7 = a5;
            String a11 = m1022a(DeviceUtils.getResolution(sendService2.context));
            String str8 = a4;
            String a12 = m1022a(DeviceUtils.getCarrier(sendService2.context));
            String a13 = m1022a(str4);
            String a14 = m1022a(str3);
            String str9 = a3;
            String a15 = m1022a(str);
            String str10 = a2;
            String a16 = m1022a(sendService2.appVersion);
            String str11 = a;
            String a17 = m1022a(sendService2.channel);
            String str12 = str5;
            String a18 = m1022a(sendService2.userNick);
            Object obj4 = "-";
            String a19 = m1022a(sendService2.userNick);
            m1022a(DeviceUtils.getCountry());
            String str13 = a19;
            String a20 = m1022a(DeviceUtils.getLanguage());
            String str14 = sendService2.appId;
            String str15 = "a";
            String str16 = a18;
            String a21 = m1022a(Build.VERSION.RELEASE);
            Object obj5 = "mini";
            Object obj6 = "1.0";
            StringBuilder sb = new StringBuilder();
            sb.append("");
            String str17 = a16;
            String str18 = a15;
            sb.append(f1379c);
            sb.toString();
            String a22 = m1022a(utdid);
            String a23 = m1022a(sendService2.country);
            StringUtils.isBlank("");
            if (str14 != null && str14.contains("aliyunos")) {
                str15 = "y";
            }
            HashMap hashMap = new HashMap();
            hashMap.put(C0883a.IMEI.toString(), a7);
            hashMap.put(C0883a.IMSI.toString(), a8);
            hashMap.put(C0883a.BRAND.toString(), a9);
            hashMap.put(C0883a.DEVICE_MODEL.toString(), a10);
            hashMap.put(C0883a.RESOLUTION.toString(), a11);
            hashMap.put(C0883a.CARRIER.toString(), a12);
            hashMap.put(C0883a.ACCESS.toString(), a13);
            hashMap.put(C0883a.ACCESS_SUBTYPE.toString(), a14);
            hashMap.put(C0883a.CHANNEL.toString(), a17);
            hashMap.put(C0883a.f1357j.toString(), str18);
            hashMap.put(C0883a.APPVERSION.toString(), str17);
            hashMap.put(C0883a.LL_USERNICK.toString(), str16);
            hashMap.put(C0883a.USERNICK.toString(), str13);
            Object obj7 = obj4;
            hashMap.put(C0883a.LL_USERID.toString(), obj7);
            hashMap.put(C0883a.USERID.toString(), obj7);
            hashMap.put(C0883a.LANGUAGE.toString(), a20);
            hashMap.put(C0883a.OS.toString(), str15);
            hashMap.put(C0883a.OSVERSION.toString(), a21);
            hashMap.put(C0883a.SDKVERSION.toString(), obj6);
            hashMap.put(C0883a.START_SESSION_TIMESTAMP.toString(), "" + f1379c);
            hashMap.put(C0883a.UTDID.toString(), a22);
            hashMap.put(C0883a.SDKTYPE.toString(), obj5);
            hashMap.put(C0883a.RESERVE2.toString(), a22);
            hashMap.put(C0883a.RESERVE3.toString(), obj7);
            hashMap.put(C0883a.RESERVE4.toString(), obj7);
            hashMap.put(C0883a.RESERVE5.toString(), obj7);
            hashMap.put(C0883a.RESERVES.toString(), a23);
            hashMap.put(C0883a.RECORD_TIMESTAMP.toString(), str12);
            hashMap.put(C0883a.PAGE.toString(), str11);
            hashMap.put(C0883a.EVENTID.toString(), str10);
            hashMap.put(C0883a.ARG1.toString(), str9);
            hashMap.put(C0883a.ARG2.toString(), str8);
            hashMap.put(C0883a.ARG3.toString(), str7);
            hashMap.put(C0883a.ARGS.toString(), str6);
            return m1023a((Map<String, String>) hashMap);
        } catch (Exception e) {
            LogUtil.m1031e("UTRestAPI buildTracePostReqDataObj catch!", e);
            return "";
        }
    }

    /* renamed from: a */
    public static String m1023a(Map<String, String> map) {
        boolean z;
        C0883a aVar;
        StringBuffer stringBuffer = new StringBuffer();
        C0883a[] values = C0883a.values();
        int length = values.length;
        int i = 0;
        while (true) {
            String str = null;
            if (i < length && (aVar = values[i]) != C0883a.ARGS) {
                if (map.containsKey(aVar.toString())) {
                    str = StringUtils.convertObjectToString(map.get(aVar.toString()));
                    map.remove(aVar.toString());
                }
                stringBuffer.append(m1022a(str));
                stringBuffer.append("||");
                i++;
            }
        }
        if (map.containsKey(C0883a.ARGS.toString())) {
            stringBuffer.append(m1022a(StringUtils.convertObjectToString(map.get(C0883a.ARGS.toString()))));
            map.remove(C0883a.ARGS.toString());
            z = false;
        } else {
            z = true;
        }
        for (String next : map.keySet()) {
            String convertObjectToString = map.containsKey(next) ? StringUtils.convertObjectToString(map.get(next)) : null;
            if (z) {
                if ("StackTrace".equals(next)) {
                    stringBuffer.append("StackTrace=====>");
                    stringBuffer.append(convertObjectToString);
                } else {
                    stringBuffer.append(m1022a(next));
                    stringBuffer.append("=");
                    stringBuffer.append(convertObjectToString);
                }
                z = false;
            } else if ("StackTrace".equals(next)) {
                stringBuffer.append(",");
                stringBuffer.append("StackTrace=====>");
                stringBuffer.append(convertObjectToString);
            } else {
                stringBuffer.append(",");
                stringBuffer.append(m1022a(next));
                stringBuffer.append("=");
                stringBuffer.append(convertObjectToString);
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        if (StringUtils.isEmpty(stringBuffer2) || !stringBuffer2.endsWith("||")) {
            return stringBuffer2;
        }
        return stringBuffer2 + "-";
    }

    /* renamed from: a */
    public static C0889d m1020a(SendService sendService, String str, String str2, Context context, long j, String str3, int i, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        long j2;
        SendService sendService2 = sendService;
        if (i == 0) {
            return null;
        }
        try {
            String utdid = DeviceUtils.getUtdid(sendService2.context);
            if (utdid == null) {
                LogUtil.m1030e("get utdid failure, so build report failure, now return");
                return null;
            }
            String[] networkType = DeviceUtils.getNetworkType(sendService2.context);
            String str4 = networkType[0];
            String str5 = (networkType.length <= 1 || str4 == null || DeviceUtils.NETWORK_CLASS_WIFI.equals(str4)) ? null : networkType[1];
            if (j > 0) {
                j2 = j;
            } else {
                j2 = System.currentTimeMillis();
            }
            String str6 = "" + j2;
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(j2));
            String a = m1022a(str3);
            String a2 = m1022a(String.valueOf(i));
            String a3 = m1022a(StringUtils.convertObjectToString(obj));
            String a4 = m1022a(StringUtils.convertObjectToString(obj2));
            String a5 = m1022a(StringUtils.convertObjectToString(obj3));
            String a6 = m1022a(StringUtils.convertMapToString(map));
            String a7 = m1022a(DeviceUtils.getImei(sendService2.context));
            String str7 = a6;
            String a8 = m1022a(DeviceUtils.getImsi(sendService2.context));
            String str8 = a5;
            String a9 = m1022a(Build.BRAND);
            String str9 = a4;
            String a10 = m1022a(DeviceUtils.getCpuName());
            String str10 = a3;
            String a11 = m1022a(a7);
            String str11 = a2;
            String a12 = m1022a(Build.MODEL);
            String str12 = a;
            String a13 = m1022a(DeviceUtils.getResolution(sendService2.context));
            String str13 = str6;
            String a14 = m1022a(DeviceUtils.getCarrier(sendService2.context));
            String a15 = m1022a(str4);
            String a16 = m1022a(str5);
            String str14 = format;
            String a17 = m1022a(str);
            String str15 = "-";
            String a18 = m1022a(sendService2.appVersion);
            String a19 = m1022a(sendService2.channel);
            String str16 = a17;
            String a20 = m1022a(sendService2.userNick);
            String a21 = m1022a(sendService2.userNick);
            String a22 = m1022a(DeviceUtils.getCountry());
            String a23 = m1022a(DeviceUtils.getLanguage());
            String str17 = sendService2.appId;
            String str18 = a23;
            String str19 = "aliyunos";
            if (str17 != null) {
                if (str17.contains(str19)) {
                    String a24 = m1022a(Build.VERSION.RELEASE);
                    String a25 = m1022a(utdid);
                    StringUtils.isBlank("");
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("5.0.1");
                    stringBuffer.append("||");
                    stringBuffer.append(a7);
                    stringBuffer.append("||");
                    stringBuffer.append(a8);
                    stringBuffer.append("||");
                    stringBuffer.append(a9);
                    stringBuffer.append("||");
                    stringBuffer.append(a10);
                    stringBuffer.append("||");
                    stringBuffer.append(a11);
                    stringBuffer.append("||");
                    stringBuffer.append(a12);
                    stringBuffer.append("||");
                    stringBuffer.append(a13);
                    stringBuffer.append("||");
                    stringBuffer.append(a14);
                    stringBuffer.append("||");
                    stringBuffer.append(a15);
                    stringBuffer.append("||");
                    stringBuffer.append(a16);
                    stringBuffer.append("||");
                    stringBuffer.append(a19);
                    stringBuffer.append("||");
                    String str20 = str16;
                    stringBuffer.append(str20);
                    stringBuffer.append("||");
                    String str21 = a18;
                    stringBuffer.append(str21);
                    stringBuffer.append("||");
                    stringBuffer.append(a20);
                    stringBuffer.append("||");
                    stringBuffer.append(a21);
                    stringBuffer.append("||");
                    String str22 = str15;
                    stringBuffer.append(str22);
                    stringBuffer.append("||");
                    stringBuffer.append(a22);
                    stringBuffer.append("||");
                    stringBuffer.append(str18);
                    stringBuffer.append("||");
                    stringBuffer.append(str19);
                    stringBuffer.append("||");
                    stringBuffer.append(a24);
                    stringBuffer.append("||");
                    stringBuffer.append("mini");
                    stringBuffer.append("||");
                    stringBuffer.append("1.0");
                    stringBuffer.append("||");
                    stringBuffer.append("" + f1379c);
                    stringBuffer.append("||");
                    stringBuffer.append(a25);
                    stringBuffer.append("||");
                    stringBuffer.append(str22);
                    stringBuffer.append("||");
                    stringBuffer.append(str22);
                    stringBuffer.append("||");
                    stringBuffer.append(str22);
                    stringBuffer.append("||");
                    stringBuffer.append(str22);
                    stringBuffer.append("||");
                    stringBuffer.append(str14);
                    stringBuffer.append("||");
                    stringBuffer.append(str13);
                    stringBuffer.append("||");
                    stringBuffer.append(str12);
                    stringBuffer.append("||");
                    stringBuffer.append(str11);
                    stringBuffer.append("||");
                    stringBuffer.append(str10);
                    stringBuffer.append("||");
                    stringBuffer.append(str9);
                    stringBuffer.append("||");
                    stringBuffer.append(str8);
                    stringBuffer.append("||");
                    stringBuffer.append(str7);
                    String stringBuffer2 = stringBuffer.toString();
                    HashMap hashMap = new HashMap();
                    hashMap.put("stm_x", stringBuffer2.getBytes());
                    C0889d dVar = new C0889d();
                    dVar.mo10145b(RestUrlWrapper.getSignedTransferUrl(str2, (Map<String, Object>) null, hashMap, context, str20, a19, str21, str19, "", a25));
                    dVar.mo10144a(hashMap);
                    return dVar;
                }
            }
            str19 = "Android";
            String a242 = m1022a(Build.VERSION.RELEASE);
            String a252 = m1022a(utdid);
            StringUtils.isBlank("");
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("5.0.1");
            stringBuffer3.append("||");
            stringBuffer3.append(a7);
            stringBuffer3.append("||");
            stringBuffer3.append(a8);
            stringBuffer3.append("||");
            stringBuffer3.append(a9);
            stringBuffer3.append("||");
            stringBuffer3.append(a10);
            stringBuffer3.append("||");
            stringBuffer3.append(a11);
            stringBuffer3.append("||");
            stringBuffer3.append(a12);
            stringBuffer3.append("||");
            stringBuffer3.append(a13);
            stringBuffer3.append("||");
            stringBuffer3.append(a14);
            stringBuffer3.append("||");
            stringBuffer3.append(a15);
            stringBuffer3.append("||");
            stringBuffer3.append(a16);
            stringBuffer3.append("||");
            stringBuffer3.append(a19);
            stringBuffer3.append("||");
            String str202 = str16;
            stringBuffer3.append(str202);
            stringBuffer3.append("||");
            String str212 = a18;
            stringBuffer3.append(str212);
            stringBuffer3.append("||");
            stringBuffer3.append(a20);
            stringBuffer3.append("||");
            stringBuffer3.append(a21);
            stringBuffer3.append("||");
            String str222 = str15;
            stringBuffer3.append(str222);
            stringBuffer3.append("||");
            stringBuffer3.append(a22);
            stringBuffer3.append("||");
            stringBuffer3.append(str18);
            stringBuffer3.append("||");
            stringBuffer3.append(str19);
            stringBuffer3.append("||");
            stringBuffer3.append(a242);
            stringBuffer3.append("||");
            stringBuffer3.append("mini");
            stringBuffer3.append("||");
            stringBuffer3.append("1.0");
            stringBuffer3.append("||");
            stringBuffer3.append("" + f1379c);
            stringBuffer3.append("||");
            stringBuffer3.append(a252);
            stringBuffer3.append("||");
            stringBuffer3.append(str222);
            stringBuffer3.append("||");
            stringBuffer3.append(str222);
            stringBuffer3.append("||");
            stringBuffer3.append(str222);
            stringBuffer3.append("||");
            stringBuffer3.append(str222);
            stringBuffer3.append("||");
            stringBuffer3.append(str14);
            stringBuffer3.append("||");
            stringBuffer3.append(str13);
            stringBuffer3.append("||");
            stringBuffer3.append(str12);
            stringBuffer3.append("||");
            stringBuffer3.append(str11);
            stringBuffer3.append("||");
            stringBuffer3.append(str10);
            stringBuffer3.append("||");
            stringBuffer3.append(str9);
            stringBuffer3.append("||");
            stringBuffer3.append(str8);
            stringBuffer3.append("||");
            stringBuffer3.append(str7);
            String stringBuffer22 = stringBuffer3.toString();
            HashMap hashMap2 = new HashMap();
            hashMap2.put("stm_x", stringBuffer22.getBytes());
            C0889d dVar2 = new C0889d();
            dVar2.mo10145b(RestUrlWrapper.getSignedTransferUrl(str2, (Map<String, Object>) null, hashMap2, context, str202, a19, str212, str19, "", a252));
            dVar2.mo10144a(hashMap2);
            return dVar2;
        } catch (Exception e) {
            LogUtil.m1031e("UTRestAPI buildTracePostReqDataObj catch!", e);
            return null;
        }
    }
}
