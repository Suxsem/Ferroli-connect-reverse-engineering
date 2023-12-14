package com.alibaba.sdk.android.push.p027g;

import android.support.p000v4.app.NotificationCompat;
import android.util.Log;
import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.push.common.p020a.C0804d;
import com.alibaba.sdk.android.push.common.util.p021a.C0812d;
import com.alibaba.sdk.android.push.p018b.C0799f;
import com.taobao.accs.common.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alibaba.sdk.android.push.g.i */
public class C0858i {
    /* renamed from: a */
    public static String m900a(int i, int i2, String str) {
        if (i2 == 200) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String string = jSONObject.getString("code");
                if (string.equals("OK")) {
                    return jSONObject.has(Constants.KEY_DATA) ? m901a(i, jSONObject.getString(Constants.KEY_DATA)) : "";
                }
                throw new C0799f(C0804d.m776b(string, jSONObject.getString(Constants.SHARED_MESSAGE_ID_FILE)));
            } catch (JSONException e) {
                ErrorBuilder msg = C0804d.f1099i.copy().msg(e.getMessage());
                throw new C0799f(msg.detail("content: " + str).build());
            }
        } else {
            ErrorBuilder copy = C0804d.f1106p.copy();
            ErrorBuilder msg2 = copy.msg("请求失败" + i2);
            throw new C0799f(msg2.detail("requestType:" + i).build());
        }
    }

    /* renamed from: a */
    public static String m901a(int i, String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (i == C0812d.LIST_TAGS.mo9894a()) {
                return jSONObject.getString("tags");
            }
            if (i == C0812d.LIST_ALIASES.mo9894a()) {
                return jSONObject.getString("alias");
            }
            if (i == C0812d.CONFIG.mo9894a()) {
                return jSONObject.getString("deviceId");
            }
            if (i == C0812d.CHECK_PUSH_STATUS.mo9894a()) {
                return jSONObject.getBoolean(NotificationCompat.CATEGORY_STATUS) ? "on" : "off";
            }
            ErrorBuilder copy = C0804d.f1099i.copy();
            ErrorBuilder detail = copy.detail("unknown request type " + i);
            throw new C0799f(detail.detail("data : " + str).build());
        } catch (JSONException e) {
            ErrorBuilder detail2 = C0804d.f1099i.copy().detail(e.getMessage());
            throw new C0799f(detail2.detail("data : " + str).build());
        }
    }

    /* renamed from: a */
    public static String m902a(int i, HttpURLConnection httpURLConnection) {
        try {
            if (httpURLConnection.getResponseCode() == 200) {
                JSONObject jSONObject = new JSONObject(m903a(httpURLConnection));
                String string = jSONObject.getString("code");
                if (string.equals("OK")) {
                    return jSONObject.has(Constants.KEY_DATA) ? m901a(i, jSONObject.getString(Constants.KEY_DATA)) : "";
                }
                throw new C0799f(C0804d.m776b(string, jSONObject.getString(Constants.SHARED_MESSAGE_ID_FILE)));
            }
            ErrorBuilder copy = C0804d.f1106p.copy();
            ErrorBuilder msg = copy.msg("请求失败" + httpURLConnection.getResponseCode());
            throw new C0799f(msg.detail("requestType:" + i).build());
        } catch (IOException e) {
            throw new C0799f(C0804d.f1098h.copy().msg(e.getMessage()).detail(Log.getStackTraceString(e)).build());
        } catch (JSONException e2) {
            ErrorBuilder msg2 = C0804d.f1099i.copy().msg(e2.getMessage());
            throw new C0799f(msg2.detail("content: " + null).build());
        }
    }

    /* renamed from: a */
    private static String m903a(HttpURLConnection httpURLConnection) {
        int read;
        InputStream inputStream = httpURLConnection.getInputStream();
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        while (!Thread.interrupted() && (read = inputStream.read(bArr)) != -1) {
            byteArrayOutputStream.write(bArr, 0, read);
        }
        return new String(byteArrayOutputStream.toByteArray(), "utf-8");
    }
}
