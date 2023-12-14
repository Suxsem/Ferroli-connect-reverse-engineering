package com.alibaba.sdk.android.ams.common.util;

import android.content.Context;
import android.util.Base64;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.ams.common.util.a */
public class C0680a {

    /* renamed from: a */
    private static final AmsLogger f813a = AmsLogger.getLogger("MPS:httpClient");

    /* renamed from: com.alibaba.sdk.android.ams.common.util.a$a */
    private static class C0681a extends IOException {
        public C0681a(IOException iOException, int i) {
            super(iOException.getMessage() + " code " + i, iOException);
        }
    }

    /* renamed from: a */
    public static HttpURLConnection m512a(String str, Map<String, String> map, String str2) {
        return "POST".equals(str2) ? m513a(str, map, str2, 0, (Context) null) : m514b(str, map, str2, 0, (Context) null);
    }

    /* renamed from: a */
    private static HttpURLConnection m513a(String str, Map<String, String> map, String str2, int i, Context context) {
        if (i < 3) {
            StringBuilder sb = new StringBuilder();
            if (map != null) {
                for (Map.Entry next : map.entrySet()) {
                    if (next.getValue() == null) {
                        AmsLogger amsLogger = f813a;
                        amsLogger.mo9547w("skip empty entry " + ((String) next.getKey()));
                    } else {
                        sb.append((String) next.getKey());
                        sb.append("=");
                        sb.append(Base64.encodeToString(((String) next.getValue()).getBytes(), 8));
                        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
                    }
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(6000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(6000);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(sb.toString().getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            try {
                int responseCode = httpURLConnection.getResponseCode();
                AmsLogger amsLogger2 = f813a;
                amsLogger2.mo9538d("responseCode: " + responseCode);
                return (responseCode != 200 && responseCode / 3 == 100) ? m513a(httpURLConnection.getHeaderField("Location"), map, str2, i, context) : httpURLConnection;
            } catch (IOException e) {
                f813a.mo9539d("openConnection: ", e);
                throw new C0681a(e, httpURLConnection.getResponseCode());
            }
        } else {
            throw new C0681a(new IOException("redirectCount > 3"), 300);
        }
    }

    @Deprecated
    /* renamed from: b */
    private static HttpURLConnection m514b(String str, Map<String, String> map, String str2, int i, Context context) {
        if (i < 3) {
            StringBuilder sb = new StringBuilder();
            if (map != null) {
                for (Map.Entry next : map.entrySet()) {
                    if (next.getValue() == null) {
                        AmsLogger amsLogger = f813a;
                        amsLogger.mo9547w("skip empty entry " + ((String) next.getKey()));
                    } else {
                        sb.append((String) next.getKey());
                        sb.append("=");
                        sb.append(URLEncoder.encode((String) next.getValue(), "utf-8"));
                        sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
                    }
                }
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str + "?" + sb).openConnection();
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod(str2 == null ? "GET" : str2);
            httpURLConnection.setReadTimeout(6000);
            httpURLConnection.setConnectTimeout(6000);
            httpURLConnection.connect();
            try {
                int responseCode = httpURLConnection.getResponseCode();
                AmsLogger amsLogger2 = f813a;
                amsLogger2.mo9538d("responseCode: " + responseCode);
                return (responseCode != 200 && responseCode / 3 == 100) ? m514b(httpURLConnection.getHeaderField("Location"), map, str2, i, context) : httpURLConnection;
            } catch (IOException e) {
                f813a.mo9539d("openConnection: ", e);
                throw new C0681a(e, httpURLConnection.getResponseCode());
            }
        } else {
            throw new C0681a(new IOException("redirectCount > 3"), 300);
        }
    }
}
