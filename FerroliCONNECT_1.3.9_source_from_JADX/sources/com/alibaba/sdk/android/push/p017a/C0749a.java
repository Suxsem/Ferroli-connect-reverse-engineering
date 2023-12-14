package com.alibaba.sdk.android.push.p017a;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.beacon.Beacon;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alibaba.sdk.android.push.a.a */
public class C0749a {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static AmsLogger f958a = AmsLogger.getLogger("MPS:BeaconManager");

    /* renamed from: b */
    private static C0749a f959b = null;

    /* renamed from: c */
    private Context f960c = null;

    /* renamed from: d */
    private C0752b f961d = null;

    /* renamed from: e */
    private Beacon f962e = null;

    /* renamed from: f */
    private final Beacon.OnUpdateListener f963f = new Beacon.OnUpdateListener() {
        public void onUpdate(List<Beacon.Config> list) {
            C0749a.this.m708a(list);
        }
    };

    /* renamed from: g */
    private final Beacon.OnServiceErrListener f964g = new Beacon.OnServiceErrListener() {
        public void onErr(Beacon.Error error) {
            AmsLogger b = C0749a.f958a;
            b.mo9541e("beacon error. errorCode:" + error.errCode + ", errorMsg:" + error.errMsg);
        }
    };

    private C0749a() {
    }

    /* renamed from: a */
    public static C0749a m706a() {
        if (f959b == null) {
            synchronized (C0749a.class) {
                if (f959b == null) {
                    f959b = new C0749a();
                }
            }
        }
        return f959b;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m708a(List<Beacon.Config> list) {
        f958a.mo9538d("parse beacon config");
        if (list != null && list.size() != 0) {
            for (Beacon.Config next : list) {
                AmsLogger amsLogger = f958a;
                amsLogger.mo9538d("beacon key:" + next.key + "; beacon value:" + next.value);
                if (next.key.equalsIgnoreCase("___push_service___")) {
                    m709a(next);
                }
            }
        }
    }

    /* renamed from: a */
    private boolean m709a(Beacon.Config config) {
        if (config == null || !config.key.equalsIgnoreCase("___push_service___")) {
            return false;
        }
        String str = config.value;
        if (str != null) {
            AmsLogger amsLogger = f958a;
            amsLogger.mo9538d("push configs:" + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ut")) {
                    m710a(jSONObject.getString("ut"));
                }
            } catch (JSONException e) {
                f958a.mo9542e("parse push configs failed.", e);
                return false;
            }
        }
        return true;
    }

    /* renamed from: a */
    private boolean m710a(String str) {
        if (str == null || this.f961d == null) {
            return false;
        }
        AmsLogger amsLogger = f958a;
        amsLogger.mo9538d("is report enabled:" + str);
        this.f961d.mo9817a(str.equalsIgnoreCase("disabled") ^ true);
        return true;
    }

    /* renamed from: a */
    public void mo9815a(Context context, String str, String str2, String str3) {
        this.f960c = context;
        AmsLogger amsLogger = f958a;
        amsLogger.mo9538d("appkey:" + str);
        if (this.f960c != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("sdkId", "push");
            hashMap.put("sdkVer", str3);
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                AmsLogger amsLogger2 = f958a;
                amsLogger2.mo9541e("invalid appkey or appsecret. appkey:" + str + ", appsecret:" + str2);
                return;
            }
            this.f962e = new Beacon.Builder().appKey(str).appSecret(str2).extras(hashMap).startPoll(false).build();
            this.f962e.addUpdateListener(this.f963f);
            this.f962e.addServiceErrListener(this.f964g);
            this.f962e.start(this.f960c.getApplicationContext());
            return;
        }
        f958a.mo9541e("context is null !!");
    }

    /* renamed from: a */
    public void mo9816a(C0752b bVar) {
        this.f961d = bVar;
    }
}
