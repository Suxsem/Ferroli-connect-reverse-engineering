package anet.channel.strategy;

import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import com.p107tb.appyunsdk.Constant;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.BaseMonitor;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: anet.channel.strategy.l */
/* compiled from: Taobao */
public class C0583l {
    /* renamed from: a */
    public static C0587d m302a(JSONObject jSONObject) {
        try {
            return new C0587d(jSONObject);
        } catch (Exception e) {
            ALog.m326e("StrategyResultParser", "Parse HttpDns response failed.", (String) null, e, "JSON Content", jSONObject.toString());
            return null;
        }
    }

    /* renamed from: anet.channel.strategy.l$e */
    /* compiled from: Taobao */
    public static class C0588e {

        /* renamed from: a */
        public final String f545a;

        /* renamed from: b */
        public final C0584a f546b;

        /* renamed from: c */
        public final String f547c;

        public C0588e(JSONObject jSONObject) {
            this.f545a = jSONObject.optString("ip");
            this.f547c = jSONObject.optString("path");
            this.f546b = new C0584a(jSONObject);
        }
    }

    /* renamed from: anet.channel.strategy.l$a */
    /* compiled from: Taobao */
    public static class C0584a {

        /* renamed from: a */
        public final int f515a;

        /* renamed from: b */
        public final String f516b;

        /* renamed from: c */
        public final int f517c;

        /* renamed from: d */
        public final int f518d;

        /* renamed from: e */
        public final int f519e;

        /* renamed from: f */
        public final int f520f;

        /* renamed from: g */
        public final String f521g;

        /* renamed from: h */
        public final String f522h;

        public C0584a(JSONObject jSONObject) {
            this.f515a = jSONObject.optInt("port");
            this.f516b = jSONObject.optString("protocol");
            this.f517c = jSONObject.optInt("cto");
            this.f518d = jSONObject.optInt("rto");
            this.f519e = jSONObject.optInt("retry");
            this.f520f = jSONObject.optInt("heartbeat");
            this.f521g = jSONObject.optString("rtt", "");
            this.f522h = jSONObject.optString("publickey");
        }
    }

    /* renamed from: anet.channel.strategy.l$c */
    /* compiled from: Taobao */
    public static class C0586c {

        /* renamed from: a */
        public final String f535a;

        /* renamed from: b */
        public final C0588e[] f536b;

        public C0586c(JSONObject jSONObject) {
            this.f535a = jSONObject.optString(Constants.KEY_HOST);
            JSONArray optJSONArray = jSONObject.optJSONArray("strategies");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                this.f536b = new C0588e[length];
                for (int i = 0; i < length; i++) {
                    this.f536b[i] = new C0588e(optJSONArray.optJSONObject(i));
                }
                return;
            }
            this.f536b = null;
        }
    }

    /* renamed from: anet.channel.strategy.l$b */
    /* compiled from: Taobao */
    public static class C0585b {

        /* renamed from: a */
        public final String f523a;

        /* renamed from: b */
        public final int f524b;

        /* renamed from: c */
        public final String f525c;

        /* renamed from: d */
        public final String f526d;

        /* renamed from: e */
        public final String f527e;

        /* renamed from: f */
        public final String[] f528f;

        /* renamed from: g */
        public final String[] f529g;

        /* renamed from: h */
        public final C0584a[] f530h;

        /* renamed from: i */
        public final C0588e[] f531i;

        /* renamed from: j */
        public final boolean f532j;

        /* renamed from: k */
        public final boolean f533k;

        /* renamed from: l */
        public final int f534l;

        public C0585b(JSONObject jSONObject) {
            this.f523a = jSONObject.optString(Constants.KEY_HOST);
            this.f524b = jSONObject.optInt("ttl");
            this.f525c = jSONObject.optString("safeAisles");
            this.f526d = jSONObject.optString("cname", (String) null);
            this.f527e = jSONObject.optString(Constant.WEATHER_UNIT, (String) null);
            this.f532j = jSONObject.optInt("clear") != 1 ? false : true;
            this.f533k = jSONObject.optBoolean("effectNow");
            this.f534l = jSONObject.optInt(Constants.SP_KEY_VERSION);
            JSONArray optJSONArray = jSONObject.optJSONArray("ips");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                this.f528f = new String[length];
                for (int i = 0; i < length; i++) {
                    this.f528f[i] = optJSONArray.optString(i);
                }
            } else {
                this.f528f = null;
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("sips");
            if (optJSONArray2 == null || optJSONArray2.length() <= 0) {
                this.f529g = null;
            } else {
                int length2 = optJSONArray2.length();
                this.f529g = new String[length2];
                for (int i2 = 0; i2 < length2; i2++) {
                    this.f529g[i2] = optJSONArray2.optString(i2);
                }
            }
            JSONArray optJSONArray3 = jSONObject.optJSONArray("aisles");
            if (optJSONArray3 != null) {
                int length3 = optJSONArray3.length();
                this.f530h = new C0584a[length3];
                for (int i3 = 0; i3 < length3; i3++) {
                    this.f530h[i3] = new C0584a(optJSONArray3.optJSONObject(i3));
                }
            } else {
                this.f530h = null;
            }
            JSONArray optJSONArray4 = jSONObject.optJSONArray("strategies");
            if (optJSONArray4 == null || optJSONArray4.length() <= 0) {
                this.f531i = null;
                return;
            }
            int length4 = optJSONArray4.length();
            this.f531i = new C0588e[length4];
            for (int i4 = 0; i4 < length4; i4++) {
                this.f531i[i4] = new C0588e(optJSONArray4.optJSONObject(i4));
            }
        }
    }

    /* renamed from: anet.channel.strategy.l$d */
    /* compiled from: Taobao */
    public static class C0587d {

        /* renamed from: a */
        public final String f537a;

        /* renamed from: b */
        public final C0585b[] f538b;

        /* renamed from: c */
        public final C0586c[] f539c;

        /* renamed from: d */
        public final String f540d;

        /* renamed from: e */
        public final String f541e;

        /* renamed from: f */
        public final int f542f;

        /* renamed from: g */
        public final int f543g;

        /* renamed from: h */
        public final int f544h;

        public C0587d(JSONObject jSONObject) {
            this.f537a = jSONObject.optString("ip");
            this.f540d = jSONObject.optString("uid", (String) null);
            this.f541e = jSONObject.optString("utdid", (String) null);
            this.f542f = jSONObject.optInt(DispatchConstants.CONFIG_VERSION);
            this.f543g = jSONObject.optInt("fcl");
            this.f544h = jSONObject.optInt("fct");
            JSONArray optJSONArray = jSONObject.optJSONArray(BaseMonitor.COUNT_POINT_DNS);
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                this.f538b = new C0585b[length];
                for (int i = 0; i < length; i++) {
                    this.f538b[i] = new C0585b(optJSONArray.optJSONObject(i));
                }
            } else {
                this.f538b = null;
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("hrTask");
            if (optJSONArray2 != null) {
                int length2 = optJSONArray2.length();
                this.f539c = new C0586c[length2];
                for (int i2 = 0; i2 < length2; i2++) {
                    this.f539c[i2] = new C0586c(optJSONArray2.optJSONObject(i2));
                }
                return;
            }
            this.f539c = null;
        }
    }
}
