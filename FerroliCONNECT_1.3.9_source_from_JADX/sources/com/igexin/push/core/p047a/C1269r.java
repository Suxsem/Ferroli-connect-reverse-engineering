package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1233j;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p051d.C1336e;
import com.igexin.push.p046c.C1210e;
import com.igexin.push.p046c.C1214i;
import com.igexin.push.p088g.p089a.C1566e;
import com.igexin.push.util.EncryptUtils;
import com.igexin.sdk.PushConsts;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.r */
public class C1269r extends C1253b {

    /* renamed from: a */
    private static final String f1892a = (C1233j.f1818a + "_RedirectServerAction");

    /* renamed from: a */
    private void m1769a(String str, JSONArray jSONArray) {
        try {
            C1179b.m1354a(f1892a + "|start fetch idc config, url : " + str);
            C1174c.m1310b().mo14317a(new C1566e(new C1336e(str, jSONArray)), false, true);
        } catch (Exception e) {
            C1179b.m1354a(f1892a + e.toString());
        }
    }

    /* renamed from: a */
    public static String[] m1770a(JSONArray jSONArray) {
        String[] strArr = null;
        try {
            strArr = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                strArr[i] = "https://" + jSONArray.getString(i);
            }
        } catch (Exception e) {
            C1179b.m1354a(f1892a + "|parseIDCConfigURL exception" + e.toString());
        }
        return strArr;
    }

    /* renamed from: a */
    public boolean mo14470a(Object obj, JSONObject jSONObject) {
        long j;
        String str;
        String str2;
        JSONObject jSONObject2 = jSONObject;
        C1179b.m1354a(f1892a + "|redirect server resp data : " + jSONObject2);
        try {
            if (jSONObject2.has(PushConsts.CMD_ACTION) && jSONObject2.getString(PushConsts.CMD_ACTION).equals("redirect_server")) {
                String string = jSONObject2.getString("delay");
                ArrayList arrayList = new ArrayList();
                JSONArray jSONArray = jSONObject2.getJSONArray("address_list");
                C1179b.m1354a("redirect|" + string + "|" + jSONArray.toString());
                int i = 0;
                int i2 = 0;
                while (i2 < jSONArray.length()) {
                    String string2 = jSONArray.getString(i2);
                    int indexOf = string2.indexOf(44);
                    if (indexOf > 0) {
                        String substring = string2.substring(i, indexOf);
                        String substring2 = string2.substring(indexOf + 1);
                        long currentTimeMillis = System.currentTimeMillis();
                        try {
                            long parseLong = Long.parseLong(substring2);
                            C1210e eVar = new C1210e();
                            eVar.f1744a = "socket://" + substring;
                            Long.signum(parseLong);
                            eVar.f1745b = currentTimeMillis + (parseLong * 1000);
                            arrayList.add(eVar);
                        } catch (NumberFormatException unused) {
                        }
                    }
                    i2++;
                    i = 0;
                }
                try {
                    j = Long.parseLong(string) * 1000;
                } catch (Exception unused2) {
                    j = 0;
                }
                if (j >= 0) {
                    C1343f.f2112D = j;
                }
                if (jSONObject2.has("loc") && jSONObject2.has("conf")) {
                    try {
                        SDKUrlConfig.setLocation(jSONObject2.getString("loc"));
                        C1179b.m1354a(f1892a + " set group id : " + C1343f.f2167d);
                        JSONArray jSONArray2 = jSONObject2.getJSONArray("conf");
                        String[] a = m1770a(jSONArray2);
                        if (a != null && a.length > 1) {
                            String[] idcConfigUrl = SDKUrlConfig.getIdcConfigUrl();
                            if (idcConfigUrl != null) {
                                if (idcConfigUrl.length <= 1 || a[1].equals(idcConfigUrl[1])) {
                                    str = f1892a + "|current idc config url == new idc config url, return";
                                    C1179b.m1354a(str);
                                }
                            }
                            if (C1343f.f2155aq == 0) {
                                str2 = a[1];
                            } else if (System.currentTimeMillis() - C1343f.f2155aq > 7200000) {
                                str2 = a[1];
                            } else {
                                str = f1892a + "|get idc cfg last time less than 2 hours return";
                                C1179b.m1354a(str);
                            }
                            m1769a(str2, jSONArray2);
                        }
                    } catch (Exception e) {
                        C1179b.m1354a(f1892a + e.toString());
                    }
                }
                C1214i.m1500a().mo14385e().mo14364a((List<C1210e>) arrayList);
                if (EncryptUtils.isLoadSuccess()) {
                    C1179b.m1354a(f1892a + "|redirect reInit so ~~~~~");
                    EncryptUtils.reset();
                }
            }
        } catch (Exception e2) {
            C1179b.m1354a(f1892a + e2.toString());
        }
        return true;
    }
}
