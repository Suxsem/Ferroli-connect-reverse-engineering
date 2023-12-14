package com.alibaba.sdk.android.push.common.util.p021a;

import android.content.Context;
import android.os.AsyncTask;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.util.C0680a;
import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.push.common.p020a.C0804d;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.push.common.util.a.c */
public abstract class C0811c extends AsyncTask<Map<String, String>, Void, C0810b> {

    /* renamed from: a */
    static AmsLogger f1123a = AmsLogger.getLogger("MPS:SendRequestTask");

    /* renamed from: b */
    public String f1124b = "POST";

    /* renamed from: c */
    private Context f1125c;

    /* renamed from: d */
    private String f1126d;

    /* renamed from: e */
    private int f1127e = 0;

    public C0811c(Context context, String str) {
        this.f1125c = context;
        this.f1126d = str;
    }

    /* renamed from: a */
    private void m780a(String str, Map<String, String> map) {
        try {
            AmsLogger amsLogger = f1123a;
            amsLogger.mo9538d("request url :" + str);
            for (Map.Entry next : map.entrySet()) {
                AmsLogger amsLogger2 = f1123a;
                amsLogger2.mo9538d("key: " + ((String) next.getKey()) + " value: " + ((String) next.getValue()));
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public int mo9887a() {
        return this.f1127e;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public C0810b doInBackground(Map<String, String>... mapArr) {
        C0810b bVar;
        Map<String, String> map = mapArr[0];
        if (map.containsKey(C0812d.f1148u)) {
            this.f1127e = Integer.parseInt(map.get(C0812d.f1148u));
            bVar = new C0810b(Integer.parseInt(map.get(C0812d.f1148u)));
        } else {
            bVar = new C0810b();
        }
        try {
            String a = mo9889a(this.f1125c, this.f1126d, map);
            bVar.f1120b = 200;
            bVar.f1119a = a;
        } catch (C0809a e) {
            bVar.f1121c = e.mo9886a();
            bVar.f1120b = -1;
            bVar.f1119a = e.getMessage();
        }
        return bVar;
    }

    /* renamed from: a */
    public String mo9889a(Context context, String str, Map<String, String> map) {
        HttpURLConnection httpURLConnection = null;
        try {
            Map<String, String> a = mo9890a(context, map);
            m780a(str, a);
            httpURLConnection = C0680a.m512a(str, a, this.f1124b);
            if (httpURLConnection == null) {
                f1123a.mo9541e("failed to access VIP service.");
                throw new C0809a(C0804d.f1106p.copy().msg("创建请求连接失败").build());
            } else if (httpURLConnection.getResponseCode() == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                byte[] bArr = new byte[1024];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                while (true) {
                    if (Thread.interrupted()) {
                        break;
                    }
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                String str2 = new String(byteArrayOutputStream.toByteArray(), "utf-8");
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return str2;
            } else {
                ErrorBuilder copy = C0804d.f1106p.copy();
                throw new C0809a(copy.msg("请求失败：" + httpURLConnection.getResponseCode()).build());
            }
        } catch (C0809a e) {
            throw e;
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract Map<String, String> mo9890a(Context context, Map<String, String> map);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(C0810b bVar) {
        AmsLogger amsLogger = f1123a;
        amsLogger.mo9544i("HTTP Return code: " + bVar.f1120b);
    }
}
