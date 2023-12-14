package com.igexin.push.core.p047a;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p035b.p036a.p037a.C1161f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1238a;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1354q;
import com.igexin.push.core.C1355r;
import com.igexin.push.core.C1356s;
import com.igexin.push.core.bean.C1286i;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p050c.C1308d;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.core.p051d.C1333b;
import com.igexin.push.p043a.p044a.C1199c;
import com.igexin.push.p046c.C1214i;
import com.igexin.push.p053d.C1362c;
import com.igexin.push.p054e.p056b.C1369a;
import com.igexin.push.p054e.p056b.C1370b;
import com.igexin.push.p054e.p057c.C1372a;
import com.igexin.push.p054e.p057c.C1374c;
import com.igexin.push.p054e.p057c.C1375d;
import com.igexin.push.p054e.p057c.C1376e;
import com.igexin.push.p054e.p057c.C1379h;
import com.igexin.push.p054e.p057c.C1381j;
import com.igexin.push.p054e.p057c.C1384m;
import com.igexin.push.p054e.p057c.C1385n;
import com.igexin.push.p054e.p057c.C1387p;
import com.igexin.push.p054e.p057c.C1388q;
import com.igexin.push.p087f.C1560a;
import com.igexin.push.p088g.p089a.C1562a;
import com.igexin.push.p088g.p090b.C1569b;
import com.igexin.push.p088g.p090b.C1570c;
import com.igexin.push.p088g.p090b.C1574g;
import com.igexin.push.util.C1576a;
import com.igexin.push.util.C1581f;
import com.igexin.push.util.C1584i;
import com.igexin.push.util.C1593r;
import com.igexin.push.util.C1594s;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.common.Constants;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.a.f */
public class C1257f extends C1239a {

    /* renamed from: a */
    private static SparseArray<C1239a> f1881a;

    /* renamed from: b */
    private static C1257f f1882b;

    private C1257f() {
        f1881a = new SparseArray<>();
        f1881a.put(0, new C1260i());
        f1881a.put(5, new C1262k());
        f1881a.put(37, new C1266o());
        f1881a.put(9, new C1270s());
        f1881a.put(26, new C1259h());
        f1881a.put(28, new C1256e());
        f1881a.put(97, new C1261j());
    }

    /* renamed from: a */
    public static C1257f m1711a() {
        if (f1882b == null) {
            f1882b = new C1257f();
        }
        return f1882b;
    }

    /* renamed from: a */
    private void m1712a(C1374c cVar, PushTaskBean pushTaskBean, String str, String str2) {
        cVar.mo14833a(new C1569b(pushTaskBean, str, C1594s.m3271a()));
        C1343f.f2146ah.put(str2, cVar);
    }

    /* renamed from: a */
    public static boolean m1713a(long j) {
        return C1576a.m3200a(j);
    }

    /* renamed from: l */
    private void m1714l() {
        C1340e.m2032a().mo14710g().mo15199i();
        if (C1355r.m2114a().mo14771b()) {
            C1179b.m1354a("CoreAction|network changed check condition status");
            C1355r.m2114a().mo14776e();
        }
    }

    /* renamed from: a */
    public Class mo14471a(Context context) {
        return C1356s.m2138a().mo14786c(context);
    }

    /* renamed from: a */
    public String mo14472a(String str, String str2) {
        return str + ":" + str2;
    }

    /* renamed from: a */
    public void mo14473a(Intent intent) {
        C1179b.m1354a("CoreAction|onServiceInitialize ##");
        if (intent != null) {
            C1340e.m2032a().mo14704a(false);
            C1343f.f2111C = intent.hasExtra("op_app") ? intent.getStringExtra("op_app") : "";
            C1343f.f2176m = false;
            if (C1343f.f2175l) {
                C1238a.m1630a().mo14461c();
                C1343f.f2176m = true;
            }
            if (C1593r.m3267a(C1343f.f2169f) && C1356s.m2138a().mo14784b(C1343f.f2169f) && C1343f.f2133Y != null) {
                String name = C1356s.m2138a().mo14786c(C1343f.f2169f).getName();
                if (!name.equals(C1275b.f1913q)) {
                    byte[] b = C1196a.m1438b(name.getBytes());
                    if (b != null) {
                        C1581f.m3232a(b, C1343f.f2133Y, false);
                    }
                } else if (new File(C1343f.f2133Y).delete()) {
                    C1179b.m1354a("del " + C1343f.f2133Y + " success ~~~");
                }
            }
        }
    }

    /* renamed from: a */
    public void mo14474a(Bundle bundle) {
        C1354q.m2102a().mo14754a(bundle);
    }

    /* renamed from: a */
    public void mo14475a(PushTaskBean pushTaskBean) {
        C1374c cVar = new C1374c();
        cVar.mo14831a();
        cVar.f2265c = "RCV" + pushTaskBean.getMessageId();
        cVar.f2266d = C1343f.f2182s;
        cVar.f2263a = (int) System.currentTimeMillis();
        C1560a g = C1340e.m2032a().mo14710g();
        g.mo15186a("C-" + C1343f.f2182s, cVar);
        C1179b.m1354a("CoreAction|cdnreceive " + pushTaskBean.getTaskId() + "|" + pushTaskBean.getMessageId());
    }

    /* renamed from: a */
    public void mo14476a(PushTaskBean pushTaskBean, String str) {
        mo14477a(pushTaskBean, AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE + str, "ok");
    }

    /* renamed from: a */
    public void mo14477a(PushTaskBean pushTaskBean, String str, String str2) {
        long currentTimeMillis = System.currentTimeMillis();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "pushmessage_feedback");
            jSONObject.put("appid", pushTaskBean.getAppid());
            jSONObject.put(AgooConstants.MESSAGE_ID, String.valueOf(currentTimeMillis));
            jSONObject.put("appkey", pushTaskBean.getAppKey());
            jSONObject.put("messageid", pushTaskBean.getMessageId());
            jSONObject.put("taskid", pushTaskBean.getTaskId());
            jSONObject.put("actionid", str);
            jSONObject.put("result", str2);
            jSONObject.put("timestamp", String.valueOf(System.currentTimeMillis()));
        } catch (Exception unused) {
        }
        String jSONObject2 = jSONObject.toString();
        C1375d dVar = new C1375d();
        dVar.mo14836a();
        dVar.f2270a = (int) currentTimeMillis;
        dVar.f2273d = "17258000";
        dVar.f2274e = jSONObject2;
        dVar.f2276g = C1343f.f2182s;
        C1308d a = C1308d.m1924a();
        if (a != null) {
            a.mo14662a(new C1286i(currentTimeMillis, jSONObject2, (byte) 3, currentTimeMillis));
        }
        if (C1340e.m2032a().mo14710g() != null) {
            C1560a g = C1340e.m2032a().mo14710g();
            g.mo15186a("C-" + C1343f.f2182s, dVar);
        }
        C1179b.m1354a("feedback|" + pushTaskBean.getTaskId() + "|" + pushTaskBean.getMessageId() + "|" + str);
    }

    /* renamed from: a */
    public void mo14478a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "received");
            jSONObject.put(AgooConstants.MESSAGE_ID, str);
        } catch (JSONException unused) {
        }
        String jSONObject2 = jSONObject.toString();
        C1387p pVar = new C1387p();
        pVar.mo14836a();
        pVar.f2270a = (int) System.currentTimeMillis();
        pVar.f2273d = "17258000";
        pVar.f2274e = jSONObject2;
        pVar.f2276g = C1343f.f2182s;
        C1560a g = C1340e.m2032a().mo14710g();
        g.mo15186a("C-" + C1343f.f2182s, pVar);
    }

    /* renamed from: a */
    public void mo14479a(String str, C1372a aVar, PushTaskBean pushTaskBean) {
        C1174c.m1310b().mo14317a(new C1562a(new C1333b(str, aVar, pushTaskBean)), false, true);
    }

    /* renamed from: a */
    public void mo14480a(JSONObject jSONObject, String str) {
        try {
            PushTaskBean pushTaskBean = new PushTaskBean();
            pushTaskBean.parse(jSONObject);
            mo14476a(pushTaskBean, str);
        } catch (Exception e) {
            C1179b.m1354a("CoreAction " + e.toString());
        }
    }

    /* renamed from: a */
    public boolean mo14464a(C1190e eVar) {
        return false;
    }

    /* renamed from: a */
    public boolean mo14481a(C1376e eVar) {
        if (eVar == null) {
            return false;
        }
        C1239a aVar = f1881a.get(eVar.f2278i);
        if ((eVar instanceof C1381j) || (eVar instanceof C1384m) || (eVar instanceof C1385n) || (eVar instanceof C1388q) || (eVar instanceof C1379h)) {
            C1179b.m1354a("CoreAction|receive : " + eVar.getClass().getName() + " resp ~~~~");
            C1161f.m1252a().mo14238a(eVar.getClass().getName());
        }
        if ((eVar instanceof C1384m) || (eVar instanceof C1385n) || (eVar instanceof C1388q)) {
            C1343f.f2112D = 0;
            C1214i.m1500a().mo14385e().mo14366b();
        }
        if (aVar != null) {
            aVar.mo14465a((Object) eVar);
        }
        C1570c.m3170i().mo15208j();
        return true;
    }

    /* renamed from: a */
    public boolean mo14465a(Object obj) {
        C1560a g = C1340e.m2032a().mo14710g();
        if ((obj instanceof C1376e) && g != null) {
            g.mo15189a((C1376e) obj);
        } else if (obj instanceof C1370b) {
            C1179b.m1354a("CoreAction|TcpExceptionNotify###");
            g.mo15196f();
        } else if (obj instanceof C1369a) {
            C1179b.m1354a("CoreAction|TcpDisconnectSuccessNotify ###");
            if (C1343f.f2175l) {
                C1343f.f2175l = false;
                C1179b.m1354a("CoreAction|broadcast online state = offline");
                C1238a.m1630a().mo14458b();
            }
            g.mo15197g();
        }
        return false;
    }

    /* renamed from: a */
    public boolean mo14482a(String str, String str2, String str3) {
        return C1355r.m2114a().mo14766a(str, str2, str3);
    }

    /* renamed from: a */
    public boolean mo14483a(JSONObject jSONObject, PushTaskBean pushTaskBean) {
        return C1355r.m2114a().mo14767a(jSONObject, pushTaskBean);
    }

    /* renamed from: b */
    public int mo14484b() {
        C1179b.m1354a("CoreAction|send heart beat data ........");
        C1560a g = C1340e.m2032a().mo14710g();
        return g.mo15187a("H-" + C1343f.f2182s, new C1379h(), true);
    }

    /* renamed from: b */
    public void mo14485b(Intent intent) {
        if (intent != null && intent.hasExtra("isSlave")) {
            boolean booleanExtra = intent.getBooleanExtra("isSlave", false);
            C1179b.m1354a("CoreAction|onServiceInitializeForSlave isSlave =" + booleanExtra);
            if (booleanExtra) {
                C1340e.m2032a().mo14704a(true);
                C1343f.f2111C = intent.hasExtra("op_app") ? intent.getStringExtra("op_app") : "";
                if (C1343f.f2175l) {
                    C1238a.m1630a().mo14461c();
                }
            }
        }
    }

    /* renamed from: b */
    public void mo14486b(PushTaskBean pushTaskBean, String str) {
        if (pushTaskBean.isCDNType()) {
            mo14489c(pushTaskBean, str);
        } else {
            mo14477a(pushTaskBean, str, "ok");
        }
    }

    /* renamed from: c */
    public void mo14487c() {
        if (C1343f.f2175l || (C1343f.f2112D <= C1234k.f1861v && C1343f.f2112D != 0)) {
            C1179b.m1354a("CoreAction|resetDelayTime, ignore ~~~~~");
            return;
        }
        int random = (int) ((Math.random() * 50.0d) + 100.0d);
        C1179b.m1354a("CoreAction|screen on or onresume, reConnectDelayTime = " + C1234k.f1861v + ", resetDelay = " + random);
        C1343f.f2112D = (long) random;
        C1574g.m3187i().mo15213j();
    }

    /* renamed from: c */
    public void mo14488c(Intent intent) {
        if (intent != null && intent.getAction() != null) {
            try {
                String action = intent.getAction();
                if (PushConsts.ACTION_BROADCAST_NETWORK_CHANGE.equals(action)) {
                    if (C1174c.m1310b() != null) {
                        m1714l();
                    }
                } else if ("com.igexin.sdk.action.execute".equals(action)) {
                    C1355r.m2114a().mo14763a(intent);
                } else if ("com.igexin.sdk.action.doaction".equals(action)) {
                    C1355r.m2114a().mo14769b(intent);
                } else if ("android.intent.action.TIME_SET".equals(action)) {
                    if (C1234k.f1841b != 0) {
                        C1199c.m1446c().mo14351d();
                    }
                } else if ("android.intent.action.SCREEN_ON".equals(action)) {
                    C1343f.f2180q = 1;
                    if (C1355r.m2114a().mo14771b()) {
                        C1355r.m2114a().mo14776e();
                    }
                    if (Build.VERSION.SDK_INT >= 26) {
                        mo14487c();
                    }
                } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                    C1343f.f2180q = 0;
                } else if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                    C1362c.m2166a().mo14809a(intent.getDataString());
                } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                    C1362c.m2166a().mo14811b(intent.getDataString());
                }
            } catch (Throwable th) {
                C1179b.m1354a("CoreAction" + th.toString());
            }
        }
    }

    /* renamed from: c */
    public void mo14489c(PushTaskBean pushTaskBean, String str) {
        C1374c cVar;
        if (pushTaskBean != null && !TextUtils.isEmpty(pushTaskBean.getMessageId())) {
            String str2 = pushTaskBean.getMessageId() + "|" + str;
            if (C1343f.f2146ah.containsKey(str2)) {
                cVar = C1343f.f2146ah.get(str2);
                if (cVar.mo14834c() < 2) {
                    C1340e.m2032a().mo14710g().mo15186a("C-" + C1343f.f2182s, cVar);
                    cVar.mo14832a(cVar.mo14834c() + 1);
                }
                C1179b.m1354a("cdnfeedback|" + pushTaskBean.getTaskId() + "|" + pushTaskBean.getMessageId() + "|" + str);
            }
            cVar = new C1374c();
            long currentTimeMillis = System.currentTimeMillis();
            cVar.mo14831a();
            cVar.f2265c = "FDB" + pushTaskBean.getMessageId() + "|" + pushTaskBean.getTaskId() + "|" + str + "|" + "ok" + "|" + currentTimeMillis;
            cVar.f2266d = C1343f.f2182s;
            cVar.f2263a = (int) currentTimeMillis;
            C1560a g = C1340e.m2032a().mo14710g();
            StringBuilder sb = new StringBuilder();
            sb.append("C-");
            sb.append(C1343f.f2182s);
            g.mo15186a(sb.toString(), cVar);
            m1712a(cVar, pushTaskBean, str, str2);
            C1179b.m1354a("cdnfeedback|" + pushTaskBean.getTaskId() + "|" + pushTaskBean.getMessageId() + "|" + str);
        }
    }

    /* renamed from: d */
    public void mo14490d() {
        try {
            for (C1286i next : C1308d.m1924a().mo14664b()) {
                if (next.mo14598d() + 20000 <= System.currentTimeMillis()) {
                    long currentTimeMillis = System.currentTimeMillis();
                    JSONObject jSONObject = new JSONObject(next.mo14596b());
                    C1375d dVar = new C1375d();
                    dVar.mo14836a();
                    dVar.f2270a = (int) currentTimeMillis;
                    dVar.f2273d = "17258000";
                    if (jSONObject.has("extraData")) {
                        dVar.f2275f = C1584i.m3247a(jSONObject.optString("extraData").getBytes(), 0);
                        jSONObject.remove("extraData");
                    }
                    dVar.f2274e = next.mo14596b();
                    dVar.f2276g = C1343f.f2182s;
                    C1179b.m1354a("freshral|" + next.mo14596b());
                    C1308d.m1924a().mo14663a(next.mo14594a(), System.currentTimeMillis() + 20000);
                    C1560a g = C1340e.m2032a().mo14710g();
                    g.mo15186a("C-" + C1343f.f2182s, dVar);
                    return;
                }
            }
        } catch (Throwable th) {
            C1179b.m1354a("CoreActionfreshRAL error :" + th.toString());
        }
    }

    /* renamed from: e */
    public void mo14491e() {
        long currentTimeMillis = System.currentTimeMillis();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "request_deviceid");
            jSONObject.put(AgooConstants.MESSAGE_ID, String.valueOf(currentTimeMillis));
        } catch (JSONException unused) {
        }
        String jSONObject2 = jSONObject.toString();
        C1375d dVar = new C1375d();
        dVar.mo14836a();
        dVar.f2270a = (int) currentTimeMillis;
        dVar.f2273d = "17258000";
        dVar.f2274e = jSONObject2;
        dVar.f2276g = C1343f.f2182s;
        C1560a g = C1340e.m2032a().mo14710g();
        g.mo15186a("C-" + C1343f.f2182s, dVar);
        C1179b.m1354a("CoreAction|deviceidReq");
    }

    /* renamed from: f */
    public void mo14492f() {
        try {
            if ((System.currentTimeMillis() - C1343f.f2114F) - Constants.CLIENT_FLUSH_INTERVAL > 0) {
                C1312h.m1937a().mo14672b(0);
                C1312h.m1937a().mo14692h(System.currentTimeMillis());
            }
            if (C1343f.f2164az <= 5) {
                C1312h.m1937a().mo14672b(C1343f.f2164az + 1);
                C1174c.m1310b().mo14317a(new C1258g(this), false, true);
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: g */
    public void mo14493g() {
        String[] list;
        if (C1343f.f2169f != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String format = simpleDateFormat.format(new Date());
            String str = C1343f.f2169f.getExternalFilesDir("gtpush") + "/log/";
            File file = new File(str);
            String str2 = C1343f.f2168e;
            if (str2 == null) {
                str2 = "unknowPacageName";
            }
            if (file.exists() && (list = file.list()) != null) {
                int length = list.length;
                for (int i = 0; i < length; i++) {
                    int length2 = list[i].length();
                    if (list[i].startsWith(str2) && list[i].endsWith(".log") && length2 > str2.length() + 14 && str2.equals(list[i].substring(0, length2 - 15))) {
                        try {
                            if (Math.abs((simpleDateFormat.parse(format).getTime() - simpleDateFormat.parse(list[i].substring(str2.length() + 1, length2 - 4)).getTime()) / Constants.CLIENT_FLUSH_INTERVAL) > 6) {
                                File file2 = new File(str + list[i]);
                                if (file2.exists()) {
                                    file2.delete();
                                }
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }
    }

    /* renamed from: h */
    public void mo14494h() {
        C1576a.m3219e();
    }

    /* renamed from: i */
    public void mo14495i() {
        int i = C1343f.f2148aj - 100;
        if (i < 0) {
            i = 0;
        }
        C1343f.f2148aj = i;
        long currentTimeMillis = System.currentTimeMillis();
        Iterator<Map.Entry<String, Long>> it = C1343f.f2147ai.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            String str = (String) next.getKey();
            if (currentTimeMillis - ((Long) next.getValue()).longValue() > 3600000) {
                it.remove();
            }
        }
    }

    /* renamed from: j */
    public void mo14496j() {
        if (C1343f.f2122N < System.currentTimeMillis()) {
            C1312h.m1937a().mo14670a(false);
        }
    }

    /* renamed from: k */
    public void mo14497k() {
        if (!C1343f.f2139aa) {
            C1343f.f2139aa = C1174c.m1310b().mo14317a(C1570c.m3170i(), false, true);
        }
        if (!C1343f.f2140ab) {
            C1343f.f2140ab = C1174c.m1310b().mo14317a(C1574g.m3187i(), true, true);
        }
        if (!C1343f.f2141ac) {
            C1340e.m2032a().mo14707d();
        }
    }
}
