package com.alibaba.sdk.android.beacon;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.alibaba.sdk.android.beacon.Beacon;
import com.p092ta.utdid2.device.UTDevice;
import com.taobao.accs.common.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.alibaba.sdk.android.beacon.b */
final class C0686b {

    /* renamed from: a */
    private static final String f828a = (C0685a.f827a ? "100.67.64.54" : "beacon-api.aliyuncs.com");

    /* renamed from: b */
    private static final String f829b = ("https://" + f828a + "/beacon/fetch/config");

    /* renamed from: a */
    private final Beacon f830a;

    /* renamed from: a */
    private final C0688a f831a;

    /* renamed from: c */
    private final List<Beacon.Config> f832c = new ArrayList();

    /* renamed from: com.alibaba.sdk.android.beacon.b$a */
    private final class C0688a {
        private C0688a() {
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x00aa A[SYNTHETIC, Splitter:B:42:0x00aa] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x00af A[Catch:{ IOException -> 0x00b2 }] */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x00b8 A[SYNTHETIC, Splitter:B:49:0x00b8] */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd A[Catch:{ IOException -> 0x00c0 }] */
        /* JADX WARNING: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String mo9576a(java.lang.String r7, byte[] r8) {
            /*
                r6 = this;
                r0 = 0
                java.net.URL r1 = new java.net.URL     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                r1.<init>(r7)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                java.net.URLConnection r7 = r1.openConnection()     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                java.net.HttpURLConnection r7 = (java.net.HttpURLConnection) r7     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                r1 = 10000(0x2710, float:1.4013E-41)
                r7.setReadTimeout(r1)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                r7.setConnectTimeout(r1)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                java.lang.String r1 = "POST"
                r7.setRequestMethod(r1)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                r1 = 1
                r7.setDoOutput(r1)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                r7.setDoInput(r1)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                r1 = 0
                r7.setUseCaches(r1)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                boolean r1 = com.alibaba.sdk.android.beacon.C0685a.f827a     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                if (r1 == 0) goto L_0x002f
                java.lang.String r1 = "Host"
                java.lang.String r2 = "beacon-api.aliyuncs.com"
                r7.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
            L_0x002f:
                java.io.OutputStream r1 = r7.getOutputStream()     // Catch:{ Exception -> 0x0092, all -> 0x008f }
                r1.write(r8)     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                r1.flush()     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                int r8 = r7.getResponseCode()     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                boolean r2 = r6.mo9577a(r8)     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                if (r2 == 0) goto L_0x0048
                java.io.InputStream r7 = r7.getInputStream()     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                goto L_0x004c
            L_0x0048:
                java.io.InputStream r7 = r7.getErrorStream()     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
            L_0x004c:
                java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                java.lang.String r5 = "UTF-8"
                r4.<init>(r7, r5)     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                r3.<init>(r4)     // Catch:{ Exception -> 0x008b, all -> 0x0087 }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
                r7.<init>()     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
            L_0x005d:
                java.lang.String r0 = r3.readLine()     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
                if (r0 == 0) goto L_0x0067
                r7.append(r0)     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
                goto L_0x005d
            L_0x0067:
                if (r2 != 0) goto L_0x0076
                com.alibaba.sdk.android.beacon.b r0 = com.alibaba.sdk.android.beacon.C0686b.this     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
                java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
                java.lang.String r2 = r7.toString()     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
                r0.m561a(r8, r2)     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
            L_0x0076:
                java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0085, all -> 0x0083 }
                if (r1 == 0) goto L_0x007f
                r1.close()     // Catch:{ IOException -> 0x0082 }
            L_0x007f:
                r3.close()     // Catch:{ IOException -> 0x0082 }
            L_0x0082:
                return r7
            L_0x0083:
                r7 = move-exception
                goto L_0x0089
            L_0x0085:
                r7 = move-exception
                goto L_0x008d
            L_0x0087:
                r7 = move-exception
                r3 = r0
            L_0x0089:
                r0 = r1
                goto L_0x00b6
            L_0x008b:
                r7 = move-exception
                r3 = r0
            L_0x008d:
                r0 = r1
                goto L_0x0094
            L_0x008f:
                r7 = move-exception
                r3 = r0
                goto L_0x00b6
            L_0x0092:
                r7 = move-exception
                r3 = r0
            L_0x0094:
                java.lang.String r8 = "beacon"
                java.lang.String r1 = r7.getMessage()     // Catch:{ all -> 0x00b5 }
                android.util.Log.i(r8, r1, r7)     // Catch:{ all -> 0x00b5 }
                com.alibaba.sdk.android.beacon.b r8 = com.alibaba.sdk.android.beacon.C0686b.this     // Catch:{ all -> 0x00b5 }
                java.lang.String r1 = "-100"
                java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x00b5 }
                r8.m561a(r1, r7)     // Catch:{ all -> 0x00b5 }
                if (r0 == 0) goto L_0x00ad
                r0.close()     // Catch:{ IOException -> 0x00b2 }
            L_0x00ad:
                if (r3 == 0) goto L_0x00b2
                r3.close()     // Catch:{ IOException -> 0x00b2 }
            L_0x00b2:
                java.lang.String r7 = ""
                return r7
            L_0x00b5:
                r7 = move-exception
            L_0x00b6:
                if (r0 == 0) goto L_0x00bb
                r0.close()     // Catch:{ IOException -> 0x00c0 }
            L_0x00bb:
                if (r3 == 0) goto L_0x00c0
                r3.close()     // Catch:{ IOException -> 0x00c0 }
            L_0x00c0:
                goto L_0x00c2
            L_0x00c1:
                throw r7
            L_0x00c2:
                goto L_0x00c1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.beacon.C0686b.C0688a.mo9576a(java.lang.String, byte[]):java.lang.String");
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public boolean mo9577a(int i) {
            return i >= 200 && i < 300;
        }
    }

    /* renamed from: com.alibaba.sdk.android.beacon.b$b */
    private static final class C0689b {

        /* renamed from: a */
        final Map<String, String> f834a;

        /* renamed from: c */
        final String f835c;

        /* renamed from: d */
        final String f836d;

        /* renamed from: e */
        final String f837e;

        /* renamed from: f */
        final String f838f;

        /* renamed from: g */
        final String f839g;

        /* renamed from: h */
        final String f840h;

        /* renamed from: i */
        final String f841i;
        final String mAppKey;
        final Map<String, String> mExtras;

        /* renamed from: com.alibaba.sdk.android.beacon.b$b$a */
        static final class C0690a {

            /* renamed from: b */
            Map<String, String> f842b = new HashMap();

            /* renamed from: j */
            String f843j;

            /* renamed from: k */
            String f844k;

            /* renamed from: l */
            String f845l;

            /* renamed from: m */
            String f846m;

            /* renamed from: n */
            String f847n;

            /* renamed from: o */
            String f848o;

            /* renamed from: p */
            String f849p;

            C0690a() {
            }

            /* access modifiers changed from: package-private */
            /* renamed from: a */
            public C0690a mo9578a(String str) {
                this.f843j = str;
                return this;
            }

            /* access modifiers changed from: package-private */
            /* renamed from: a */
            public C0690a mo9579a(Map<String, String> map) {
                this.f842b.putAll(map);
                return this;
            }

            /* renamed from: a */
            public C0689b mo9580a() {
                return new C0689b(this);
            }

            /* access modifiers changed from: package-private */
            /* renamed from: b */
            public C0690a mo9581b(String str) {
                this.f844k = str;
                return this;
            }

            /* access modifiers changed from: package-private */
            /* renamed from: c */
            public C0690a mo9582c(String str) {
                this.f845l = str;
                return this;
            }

            /* access modifiers changed from: package-private */
            /* renamed from: d */
            public C0690a mo9583d(String str) {
                this.f846m = str;
                return this;
            }

            /* access modifiers changed from: package-private */
            /* renamed from: e */
            public C0690a mo9584e(String str) {
                this.f847n = str;
                return this;
            }

            /* access modifiers changed from: package-private */
            /* renamed from: f */
            public C0690a mo9585f(String str) {
                this.f848o = str;
                return this;
            }

            /* access modifiers changed from: package-private */
            /* renamed from: g */
            public C0690a mo9586g(String str) {
                this.f849p = str;
                return this;
            }
        }

        private C0689b(C0690a aVar) {
            this.f834a = new TreeMap();
            this.mAppKey = aVar.f843j;
            this.f835c = aVar.f844k;
            this.f836d = aVar.f845l;
            this.f837e = aVar.f846m;
            this.f838f = aVar.f847n;
            this.f839g = aVar.f848o;
            this.f840h = aVar.f849p;
            this.mExtras = aVar.f842b;
            this.f841i = m567a();
        }

        /* renamed from: a */
        private String m567a() {
            this.f834a.put(Constants.KEY_APP_KEY, this.mAppKey);
            this.f834a.put("appVer", this.f836d);
            this.f834a.put(Constants.KEY_OS_TYPE, this.f837e);
            this.f834a.put("osVer", this.f838f);
            this.f834a.put("deviceId", this.f839g);
            this.f834a.put("beaconVer", this.f840h);
            for (String next : this.mExtras.keySet()) {
                this.f834a.put(next, this.mExtras.get(next));
            }
            StringBuilder sb = new StringBuilder();
            for (String next2 : this.f834a.keySet()) {
                sb.append(next2);
                sb.append(this.f834a.get(next2));
            }
            String a = C0691c.m578a(this.f835c, sb.toString());
            this.f834a.put("sign", a);
            return a;
        }
    }

    C0686b(Beacon beacon) {
        this.f830a = beacon;
        this.f831a = new C0688a();
    }

    /* renamed from: a */
    private C0689b mo9575a(Context context, String str, String str2, Map<String, String> map) {
        return new C0689b.C0690a().mo9578a(str).mo9581b(str2).mo9582c(C0691c.m577a(context)).mo9583d("Android").mo9584e(String.valueOf(Build.VERSION.SDK_INT)).mo9585f(UTDevice.getUtdid(context)).mo9586g("1.0.7").mo9579a(map).mo9580a();
    }

    /* renamed from: a */
    private String m558a(C0689b bVar) {
        Map<String, String> map = bVar.f834a;
        StringBuilder sb = new StringBuilder();
        for (String next : map.keySet()) {
            sb.append(encode(next));
            sb.append("=");
            sb.append(encode(map.get(next)));
            sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /* renamed from: a */
    private void m560a(String str) {
        m562b(str);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m561a(String str, String str2) {
        this.f830a.mo9558a(new Beacon.Error(str, str2));
    }

    /* renamed from: b */
    private void m562b(String str) {
        JSONArray optJSONArray;
        try {
            if (!TextUtils.isEmpty(str) && (optJSONArray = new JSONObject(str).optJSONArray("result")) != null && optJSONArray.length() > 0) {
                this.f832c.clear();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject = (JSONObject) optJSONArray.get(i);
                    this.f832c.add(new Beacon.Config(jSONObject.optString("key"), jSONObject.optString("value")));
                }
            }
        } catch (Exception unused) {
        }
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public List<Beacon.Config> mo9574a() {
        return Collections.unmodifiableList(this.f832c);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void m564a(Context context, String str, String str2, Map<String, String> map) {
        C0689b a = mo9575a(context, str, str2, map);
        String str3 = f829b + "/" + "byappkey";
        Log.i("beacon", "url=" + str3);
        String a2 = this.f831a.mo9576a(str3, m558a(a).getBytes());
        Log.i("beacon", "[fetchByAppKey] result: " + a2);
        m560a(a2);
    }
}
