package com.taobao.agoo.p105a;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.android.agoo.common.Config;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.taobao.agoo.a.a */
/* compiled from: Taobao */
public class C2123a {
    public static final String SP_AGOO_BIND_FILE_NAME = "EMAS_AGOO_BIND";

    /* renamed from: a */
    private ConcurrentMap<String, Integer> f3576a = new ConcurrentHashMap();

    /* renamed from: b */
    private long f3577b;

    /* renamed from: c */
    private Context f3578c;

    /* renamed from: d */
    private String f3579d;

    public C2123a(Context context) {
        if (context != null) {
            this.f3578c = context.getApplicationContext();
            StringBuilder sb = new StringBuilder();
            try {
                sb.append("EMAS_AGOO_BIND");
                sb.append(Config.m3896c(context));
                sb.append(AccsClientConfig.getConfigByTag(Config.m3896c(context)).getInappHost());
                this.f3579d = sb.toString();
            } catch (Throwable unused) {
            }
        } else {
            throw new RuntimeException("Context is null!!");
        }
    }

    /* renamed from: a */
    public void mo18618a(String str) {
        Integer num = (Integer) this.f3576a.get(str);
        if (num == null || num.intValue() != 2) {
            this.f3576a.put(str, 2);
            m3820a(this.f3578c, this.f3579d, this.f3577b, this.f3576a);
        }
    }

    /* renamed from: b */
    public boolean mo18619b(String str) {
        if (this.f3576a.isEmpty()) {
            m3821b();
        }
        Integer num = (Integer) this.f3576a.get(str);
        ALog.m3728i("AgooBindCache", "isAgooRegistered", Constants.KEY_PACKAGE_NAME, str, "appStatus", num, "agooBindStatus", this.f3576a);
        if (!UtilityImpl.utdidChanged(Config.PREFERENCES, this.f3578c) && num != null && num.intValue() == 2) {
            return true;
        }
        return false;
    }

    /* renamed from: b */
    private void m3821b() {
        try {
            String string = this.f3578c.getSharedPreferences(this.f3579d, 0).getString("bind_status", (String) null);
            if (TextUtils.isEmpty(string)) {
                ALog.m3731w("AgooBindCache", "restoreAgooClients packs null return", new Object[0]);
                return;
            }
            JSONArray jSONArray = new JSONArray(string);
            this.f3577b = jSONArray.getLong(0);
            if (System.currentTimeMillis() < this.f3577b + Constants.CLIENT_FLUSH_INTERVAL) {
                for (int i = 1; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    this.f3576a.put(jSONObject.getString("p"), Integer.valueOf(jSONObject.getInt("s")));
                }
                ALog.m3728i("AgooBindCache", "restoreAgooClients", "mAgooBindStatus", this.f3576a);
                return;
            }
            ALog.m3728i("AgooBindCache", "restoreAgooClients expired", "agooLastFlushTime", Long.valueOf(this.f3577b));
            this.f3577b = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    private void m3820a(Context context, String str, long j, Map<String, Integer> map) {
        try {
            String[] strArr = (String[]) map.keySet().toArray(new String[0]);
            JSONArray jSONArray = new JSONArray();
            if (j <= 0 || j >= System.currentTimeMillis()) {
                double currentTimeMillis = (double) System.currentTimeMillis();
                double random = Math.random() * 8.64E7d;
                Double.isNaN(currentTimeMillis);
                jSONArray.put(currentTimeMillis - random);
            } else {
                jSONArray.put(j);
            }
            for (String str2 : strArr) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("p", str2);
                jSONObject.put("s", map.get(str2).intValue());
                jSONArray.put(jSONObject);
            }
            SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
            edit.putString("bind_status", jSONArray.toString());
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void mo18617a() {
        this.f3576a.clear();
        this.f3577b = 0;
        try {
            this.f3578c.getSharedPreferences(this.f3579d, 0).edit().clear().commit();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
