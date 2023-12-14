package com.alibaba.sdk.android.tbrest.rest;

import android.content.Context;
import com.alibaba.sdk.android.tbrest.utils.MD5Utils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class RestUrlWrapper {
    public static final String FIELD_APPKEY = "appkey";
    public static final String FIELD_APPVERSION = "app_version";
    public static final String FIELD_CHANNEL = "channel";
    public static final String FIELD_PLATFORM = "platform";
    public static final String FIELD_SDK_VERSION = "sdk_version";
    public static final String FIELD_T = "t";
    public static final String FIELD_UTDID = "utdid";
    public static final String FIELD_V = "v";
    static boolean enableSecuritySDK = false;
    static Context mContext;

    public static void enableSecuritySDK() {
        enableSecuritySDK = true;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static String getSignedTransferUrl(String str, Map<String, Object> map, Map<String, Object> map2, Context context, String str2, String str3, String str4, String str5, String str6, String str7) throws Exception {
        Map<String, Object> map3 = map2;
        String str8 = "";
        if (map3 != null && map2.size() > 0) {
            Set<String> keySet = map2.keySet();
            String[] strArr = new String[keySet.size()];
            keySet.toArray(strArr);
            for (String str9 : C0885c.m1014a().mo10138a(strArr, true)) {
                str8 = str8 + str9 + MD5Utils.getMd5Hex((byte[]) map3.get(str9));
            }
        }
        try {
            return wrapUrl(str, (String) null, (String) null, str8, context, str2, str3, str4, str5, str6, str7);
        } catch (Exception unused) {
            return wrapUrl(RestConstants.getTransferUrl(), (String) null, (String) null, str8, context, str2, str3, str4, str5, str6, str7);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0087  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String wrapUrl(java.lang.String r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, android.content.Context r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14) throws java.lang.Exception {
        /*
            long r0 = java.lang.System.currentTimeMillis()
            java.lang.String r8 = java.lang.String.valueOf(r0)
            boolean r13 = enableSecuritySDK
            java.lang.String r0 = ""
            java.lang.String r1 = "4.1.0"
            java.lang.String r2 = "3.0"
            if (r13 == 0) goto L_0x006d
            android.content.Context r13 = mContext
            if (r13 == 0) goto L_0x006d
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0064 }
            r13.<init>()     // Catch:{ Exception -> 0x0064 }
            r13.append(r9)     // Catch:{ Exception -> 0x0064 }
            r13.append(r10)     // Catch:{ Exception -> 0x0064 }
            r13.append(r11)     // Catch:{ Exception -> 0x0064 }
            r13.append(r12)     // Catch:{ Exception -> 0x0064 }
            r13.append(r1)     // Catch:{ Exception -> 0x0064 }
            r13.append(r14)     // Catch:{ Exception -> 0x0064 }
            r13.append(r8)     // Catch:{ Exception -> 0x0064 }
            r13.append(r2)     // Catch:{ Exception -> 0x0064 }
            r13.append(r0)     // Catch:{ Exception -> 0x0064 }
            if (r6 != 0) goto L_0x0039
            r6 = r0
        L_0x0039:
            r13.append(r6)     // Catch:{ Exception -> 0x0064 }
            if (r7 != 0) goto L_0x003f
            r7 = r0
        L_0x003f:
            r13.append(r7)     // Catch:{ Exception -> 0x0064 }
            java.lang.String r6 = r13.toString()     // Catch:{ Exception -> 0x0064 }
            byte[] r6 = r6.getBytes()     // Catch:{ Exception -> 0x0064 }
            java.lang.String r6 = com.alibaba.sdk.android.tbrest.utils.MD5Utils.getMd5Hex(r6)     // Catch:{ Exception -> 0x0064 }
            com.alibaba.sdk.android.tbrest.rest.g r7 = new com.alibaba.sdk.android.tbrest.rest.g     // Catch:{ Exception -> 0x0064 }
            android.content.Context r13 = mContext     // Catch:{ Exception -> 0x0064 }
            r7.<init>(r13, r9)     // Catch:{ Exception -> 0x0064 }
            java.lang.String r6 = r7.mo10146b(r6)     // Catch:{ Exception -> 0x0064 }
            boolean r7 = com.alibaba.sdk.android.tbrest.utils.StringUtils.isNotBlank(r1)     // Catch:{ Exception -> 0x0062 }
            if (r7 == 0) goto L_0x006b
            java.lang.String r7 = "1"
            goto L_0x006f
        L_0x0062:
            r7 = move-exception
            goto L_0x0066
        L_0x0064:
            r7 = move-exception
            r6 = r0
        L_0x0066:
            java.lang.String r13 = "security sdk signed"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1033w(r13, r7)
        L_0x006b:
            r7 = r0
            goto L_0x006f
        L_0x006d:
            r6 = r0
            r7 = r6
        L_0x006f:
            boolean r13 = com.alibaba.sdk.android.tbrest.utils.StringUtils.isEmpty(r5)
            if (r13 != 0) goto L_0x0087
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r5)
            java.lang.String r5 = "&"
            r13.append(r5)
            java.lang.String r5 = r13.toString()
            goto L_0x0088
        L_0x0087:
            r5 = r0
        L_0x0088:
            r13 = 13
            java.lang.Object[] r13 = new java.lang.Object[r13]
            r3 = 0
            r13[r3] = r4
            r4 = 1
            r13[r4] = r5
            r4 = 2
            java.lang.String r5 = getEncoded(r9)
            r13[r4] = r5
            r4 = 3
            java.lang.String r5 = getEncoded(r11)
            r13[r4] = r5
            r4 = 4
            java.lang.String r5 = getEncoded(r10)
            r13[r4] = r5
            r4 = 5
            java.lang.String r5 = getEncoded(r2)
            r13[r4] = r5
            r4 = 6
            java.lang.String r5 = getEncoded(r6)
            r13[r4] = r5
            r4 = 7
            java.lang.String r5 = getEncoded(r14)
            r13[r4] = r5
            r4 = 8
            r13[r4] = r1
            r4 = 9
            r13[r4] = r12
            r4 = 10
            r13[r4] = r8
            r4 = 11
            r13[r4] = r0
            r4 = 12
            r13[r4] = r7
            java.lang.String r4 = "%s?%sak=%s&av=%s&c=%s&v=%s&s=%s&d=%s&sv=%s&p=%s&t=%s&u=%s&is=%s"
            java.lang.String r4 = java.lang.String.format(r4, r13)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.rest.RestUrlWrapper.wrapUrl(java.lang.String, java.lang.String, java.lang.String, java.lang.String, android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    private static String getEncoded(String str) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }
}
