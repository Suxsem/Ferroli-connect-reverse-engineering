package com.igexin.push.core;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.p048a.C1240a;
import com.igexin.push.core.p047a.p048a.C1241b;
import com.igexin.push.core.p047a.p048a.C1242c;
import com.igexin.push.core.p047a.p048a.C1243d;
import com.igexin.push.core.p047a.p048a.C1244e;
import com.igexin.push.core.p047a.p048a.C1245f;
import com.igexin.push.core.p047a.p048a.C1246g;
import com.igexin.push.core.p047a.p048a.C1247h;
import com.igexin.push.core.p047a.p048a.C1248i;
import com.igexin.push.core.p047a.p048a.C1249j;
import com.igexin.push.core.p047a.p048a.C1250k;
import com.igexin.push.core.p047a.p048a.C1251l;
import com.igexin.push.p045b.C1203b;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.UtilityImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;
import p110io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.igexin.push.core.r */
public class C1355r {

    /* renamed from: a */
    private static C1355r f2211a;

    /* renamed from: b */
    private static Map<String, C1240a> f2212b;

    /* renamed from: c */
    private static Set<String> f2213c;

    private C1355r() {
        f2213c = new HashSet();
        f2212b = new HashMap();
        f2213c.add("goto");
        f2213c.add("notification");
        f2213c.add("terminatetask");
        f2213c.add("startmyactivity");
        f2213c.add("startapp");
        f2213c.add("null");
        f2213c.add("wakeupsdk");
        f2213c.add("startweb");
        f2213c.add("checkapp");
        f2213c.add("enablelog");
        f2213c.add("disablelog");
    }

    /* renamed from: a */
    private C1240a m2113a(String str) {
        Map<String, C1240a> map;
        Object cVar;
        if (TextUtils.isEmpty(str) || !f2213c.contains(str)) {
            return null;
        }
        if (!f2212b.containsKey(str) || f2212b.get(str) == null) {
            String str2 = "goto";
            if (str.equals(str2)) {
                map = f2212b;
                cVar = new C1245f();
            } else {
                str2 = "notification";
                if (str.equals(str2)) {
                    map = f2212b;
                    cVar = new C1246g();
                } else {
                    str2 = "terminatetask";
                    if (str.equals(str2)) {
                        map = f2212b;
                        cVar = new C1250k();
                    } else {
                        str2 = "startmyactivity";
                        if (str.equals(str2)) {
                            map = f2212b;
                            cVar = new C1247h();
                        } else {
                            str2 = "startapp";
                            if (str.equals(str2)) {
                                map = f2212b;
                                cVar = new C1249j();
                            } else {
                                str2 = "null";
                                if (str.equals(str2)) {
                                    map = f2212b;
                                    cVar = new C1244e();
                                } else {
                                    str2 = "wakeupsdk";
                                    if (str.equals(str2)) {
                                        map = f2212b;
                                        cVar = new C1251l();
                                    } else {
                                        str2 = "startweb";
                                        if (str.equals(str2)) {
                                            map = f2212b;
                                            cVar = new C1248i();
                                        } else {
                                            str2 = "checkapp";
                                            if (str.equals(str2)) {
                                                map = f2212b;
                                                cVar = new C1241b();
                                            } else {
                                                str2 = "enablelog";
                                                if (str.equals(str2)) {
                                                    map = f2212b;
                                                    cVar = new C1243d();
                                                } else {
                                                    str2 = "disablelog";
                                                    if (str.equals(str2)) {
                                                        map = f2212b;
                                                        cVar = new C1242c();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            map.put(str2, cVar);
        }
        return f2212b.get(str);
    }

    /* renamed from: a */
    public static C1355r m2114a() {
        if (f2211a == null) {
            f2211a = new C1355r();
        }
        return f2211a;
    }

    /* renamed from: a */
    private void m2115a(int i, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(i));
        C1340e.m2032a().mo14712i().mo14356a(Constants.SHARED_MESSAGE_ID_FILE, contentValues, new String[]{"taskid"}, new String[]{str});
    }

    /* renamed from: a */
    private boolean m2116a(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("condition");
            return !jSONObject2.has(UtilityImpl.NET_TYPE_WIFI) && !jSONObject2.has("screenOn") && !jSONObject2.has("ssid") && !jSONObject2.has("duration") && !jSONObject2.has("netConnected");
        } catch (Exception unused) {
            return true;
        }
    }

    /* renamed from: b */
    private void m2117b(JSONObject jSONObject, PushTaskBean pushTaskBean) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("condition");
            HashMap hashMap = new HashMap();
            if (jSONObject2.has(UtilityImpl.NET_TYPE_WIFI)) {
                hashMap.put(UtilityImpl.NET_TYPE_WIFI, jSONObject2.getString(UtilityImpl.NET_TYPE_WIFI));
            }
            if (jSONObject2.has("screenOn")) {
                hashMap.put("screenOn", jSONObject2.getString("screenOn"));
            }
            if (jSONObject2.has("ssid")) {
                hashMap.put("ssid", jSONObject2.getString("ssid"));
                if (jSONObject2.has(DispatchConstants.BSSID)) {
                    hashMap.put(DispatchConstants.BSSID, jSONObject2.getString(DispatchConstants.BSSID));
                }
            }
            if (jSONObject2.has("duration")) {
                String string = jSONObject2.getString("duration");
                if (string.contains("-")) {
                    int indexOf = string.indexOf("-");
                    String substring = string.substring(0, indexOf);
                    String substring2 = string.substring(indexOf + 1, string.length());
                    hashMap.put("startTime", substring);
                    hashMap.put("endTime", substring2);
                }
            }
            if (jSONObject2.has("netConnected")) {
                hashMap.put("netConnected", jSONObject2.getString("netConnected"));
            }
            if (jSONObject2.has("expiredTime")) {
                String string2 = jSONObject2.getString("expiredTime");
                if (!TextUtils.isEmpty(string2) && TextUtils.isDigitsOnly(string2)) {
                    hashMap.put("expiredTime", string2);
                }
            }
            pushTaskBean.setConditionMap(hashMap);
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00f1, code lost:
        if (r2 != null) goto L_0x0111;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x010f, code lost:
        if (r2 == null) goto L_0x011b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0111, code lost:
        r2.close();
     */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m2118g() {
        /*
            r13 = this;
            java.lang.String r0 = "cdnType"
            java.lang.String r1 = "status"
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r2 = com.igexin.push.core.C1343f.f2142ad
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x011b
            boolean r2 = com.igexin.push.core.C1343f.f2177n
            if (r2 == 0) goto L_0x011b
            r2 = 0
            com.igexin.push.core.e r3 = com.igexin.push.core.C1340e.m2032a()     // Catch:{ Throwable -> 0x00f6 }
            com.igexin.push.b.b r4 = r3.mo14712i()     // Catch:{ Throwable -> 0x00f6 }
            java.lang.String r5 = "message"
            java.lang.String[] r6 = new java.lang.String[]{r1}     // Catch:{ Throwable -> 0x00f6 }
            java.lang.String r3 = "0"
            java.lang.String[] r7 = new java.lang.String[]{r3}     // Catch:{ Throwable -> 0x00f6 }
            r8 = 0
            r9 = 0
            android.database.Cursor r2 = r4.mo14355a(r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x00f6 }
            if (r2 == 0) goto L_0x00ee
        L_0x002d:
            boolean r3 = r2.moveToNext()     // Catch:{ Throwable -> 0x00f6 }
            if (r3 == 0) goto L_0x00ee
            java.lang.String r3 = "msgextra"
            int r3 = r2.getColumnIndex(r3)     // Catch:{ Throwable -> 0x00f6 }
            byte[] r3 = r2.getBlob(r3)     // Catch:{ Throwable -> 0x00f6 }
            java.lang.String r4 = "info"
            int r4 = r2.getColumnIndex(r4)     // Catch:{ Throwable -> 0x00f6 }
            byte[] r4 = r2.getBlob(r4)     // Catch:{ Throwable -> 0x00f6 }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x002d }
            java.lang.String r6 = new java.lang.String     // Catch:{ JSONException -> 0x002d }
            byte[] r4 = com.igexin.p032b.p042b.C1196a.m1439c(r4)     // Catch:{ JSONException -> 0x002d }
            r6.<init>(r4)     // Catch:{ JSONException -> 0x002d }
            r5.<init>(r6)     // Catch:{ JSONException -> 0x002d }
            java.lang.String r4 = "id"
            java.lang.String r4 = r5.getString(r4)     // Catch:{ JSONException -> 0x002d }
            java.lang.String r6 = "appid"
            java.lang.String r6 = r5.getString(r6)     // Catch:{ JSONException -> 0x002d }
            java.lang.String r7 = "messageid"
            java.lang.String r7 = r5.getString(r7)     // Catch:{ JSONException -> 0x002d }
            java.lang.String r8 = "taskid"
            java.lang.String r8 = r5.getString(r8)     // Catch:{ JSONException -> 0x002d }
            java.lang.String r9 = "appkey"
            java.lang.String r9 = r5.getString(r9)     // Catch:{ JSONException -> 0x002d }
            java.lang.String r10 = "action_chains"
            org.json.JSONArray r10 = r5.getJSONArray(r10)     // Catch:{ JSONException -> 0x002d }
            com.igexin.push.core.a.f r11 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ JSONException -> 0x002d }
            java.lang.String r11 = r11.mo14472a((java.lang.String) r8, (java.lang.String) r7)     // Catch:{ JSONException -> 0x002d }
            com.igexin.push.core.bean.PushTaskBean r12 = new com.igexin.push.core.bean.PushTaskBean     // Catch:{ JSONException -> 0x002d }
            r12.<init>()     // Catch:{ JSONException -> 0x002d }
            r12.setAppid(r6)     // Catch:{ JSONException -> 0x002d }
            r12.setMessageId(r7)     // Catch:{ JSONException -> 0x002d }
            r12.setTaskId(r8)     // Catch:{ JSONException -> 0x002d }
            r12.setId(r4)     // Catch:{ JSONException -> 0x002d }
            r12.setAppKey(r9)     // Catch:{ JSONException -> 0x002d }
            r4 = 1
            r12.setCurrentActionid(r4)     // Catch:{ JSONException -> 0x002d }
            int r4 = r2.getColumnIndex(r1)     // Catch:{ JSONException -> 0x002d }
            int r4 = r2.getInt(r4)     // Catch:{ JSONException -> 0x002d }
            r12.setStatus(r4)     // Catch:{ JSONException -> 0x002d }
            if (r3 == 0) goto L_0x00a9
            r12.setMsgExtra(r3)     // Catch:{ JSONException -> 0x002d }
        L_0x00a9:
            boolean r3 = r5.has(r0)     // Catch:{ JSONException -> 0x002d }
            if (r3 == 0) goto L_0x00b6
            boolean r3 = r5.getBoolean(r0)     // Catch:{ JSONException -> 0x002d }
            r12.setCDNType(r3)     // Catch:{ JSONException -> 0x002d }
        L_0x00b6:
            java.lang.String r3 = "condition"
            boolean r3 = r5.has(r3)     // Catch:{ JSONException -> 0x002d }
            if (r3 == 0) goto L_0x00c1
            r13.m2117b((org.json.JSONObject) r5, (com.igexin.push.core.bean.PushTaskBean) r12)     // Catch:{ JSONException -> 0x002d }
        L_0x00c1:
            if (r10 == 0) goto L_0x00e7
            int r3 = r10.length()     // Catch:{ JSONException -> 0x002d }
            if (r3 <= 0) goto L_0x00e7
            boolean r3 = r13.mo14767a((org.json.JSONObject) r5, (com.igexin.push.core.bean.PushTaskBean) r12)     // Catch:{ JSONException -> 0x002d }
            if (r3 != 0) goto L_0x00e7
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x002d }
            r3.<init>()     // Catch:{ JSONException -> 0x002d }
            java.lang.String r4 = "PushMessageExecutor|load task from db parseActionChains result = false ####### "
            r3.append(r4)     // Catch:{ JSONException -> 0x002d }
            java.lang.String r4 = r5.toString()     // Catch:{ JSONException -> 0x002d }
            r3.append(r4)     // Catch:{ JSONException -> 0x002d }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x002d }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r3)     // Catch:{ JSONException -> 0x002d }
        L_0x00e7:
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r3 = com.igexin.push.core.C1343f.f2142ad     // Catch:{ JSONException -> 0x002d }
            r3.put(r11, r12)     // Catch:{ JSONException -> 0x002d }
            goto L_0x002d
        L_0x00ee:
            r0 = 0
            com.igexin.push.core.C1343f.f2177n = r0     // Catch:{ Throwable -> 0x00f6 }
            if (r2 == 0) goto L_0x011b
            goto L_0x0111
        L_0x00f4:
            r0 = move-exception
            goto L_0x0115
        L_0x00f6:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f4 }
            r1.<init>()     // Catch:{ all -> 0x00f4 }
            java.lang.String r3 = "PushMessageExecutor|checkPushMessageMapValue error:"
            r1.append(r3)     // Catch:{ all -> 0x00f4 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00f4 }
            r1.append(r0)     // Catch:{ all -> 0x00f4 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x00f4 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x00f4 }
            if (r2 == 0) goto L_0x011b
        L_0x0111:
            r2.close()
            goto L_0x011b
        L_0x0115:
            if (r2 == 0) goto L_0x011a
            r2.close()
        L_0x011a:
            throw r0
        L_0x011b:
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r0 = com.igexin.push.core.C1343f.f2142ad
            boolean r0 = r0.isEmpty()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1355r.m2118g():boolean");
    }

    /* renamed from: h */
    private void m2119h() {
        try {
            List<ScanResult> scanResults = ((WifiManager) C1343f.f2169f.getSystemService(UtilityImpl.NET_TYPE_WIFI)).getScanResults();
            C1343f.f2149ak.clear();
            if (scanResults != null && !scanResults.isEmpty()) {
                for (int i = 0; i < scanResults.size(); i++) {
                    C1343f.f2149ak.put(scanResults.get(i).BSSID, scanResults.get(i).SSID);
                }
            }
        } catch (Throwable th) {
            C1179b.m1354a("PushMessageExecutor|" + th.toString());
        }
    }

    /* renamed from: a */
    public void mo14762a(ContentValues contentValues) {
        try {
            if (C1343f.f2136aA >= 2000) {
                int a = C1340e.m2032a().mo14712i().mo14354a(Constants.SHARED_MESSAGE_ID_FILE, "id IN (SELECT id from message where status IS NULL or status=1 or status=2 order by id asc limit 500)");
                C1343f.f2136aA -= a;
                if (a < 500) {
                    C1343f.f2136aA -= C1340e.m2032a().mo14712i().mo14354a(Constants.SHARED_MESSAGE_ID_FILE, "id IN (SELECT id from message where status=0 order by id asc limit " + (500 - a) + ")");
                }
                if (!C1340e.m2032a().mo14712i().mo14358a(Constants.SHARED_MESSAGE_ID_FILE, contentValues)) {
                    return;
                }
            } else if (!C1340e.m2032a().mo14712i().mo14358a(Constants.SHARED_MESSAGE_ID_FILE, contentValues)) {
                return;
            }
            C1343f.f2136aA++;
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public void mo14763a(Intent intent) {
        String stringExtra = intent.getStringExtra("taskid");
        intent.getStringExtra("messageid");
        String stringExtra2 = intent.getStringExtra("appid");
        String stringExtra3 = intent.getStringExtra("pkgname");
        C1179b.m1354a("PushMessageExecutor do processActionExecuteBroadcast");
        if (C1343f.f2168e.equals(stringExtra3)) {
            C1179b.m1354a("PushMessageExecutor|discard own exec broadcast, taskid = " + stringExtra);
            return;
        }
        ContentValues contentValues = new ContentValues();
        String str = "EXEC_" + stringExtra;
        contentValues.put("taskid", stringExtra);
        contentValues.put("appid", stringExtra2);
        contentValues.put("key", str);
        contentValues.put("createtime", Long.valueOf(System.currentTimeMillis()));
        Cursor cursor = null;
        try {
            cursor = C1340e.m2032a().mo14712i().mo14355a(Constants.SHARED_MESSAGE_ID_FILE, new String[]{"key"}, new String[]{str}, (String[]) null, (String) null);
            if (cursor != null && cursor.getCount() == 0) {
                mo14762a(contentValues);
            }
            if (cursor == null) {
                return;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        cursor.close();
    }

    /* renamed from: a */
    public void mo14764a(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("taskid", str);
        bundle.putString("messageid", str2);
        Message obtain = Message.obtain();
        obtain.what = C1275b.f1903g;
        obtain.obj = bundle;
        C1340e.m2032a().mo14702a(obtain);
    }

    /* renamed from: a */
    public void mo14765a(String str, String str2, String str3, String str4) {
        C1179b.m1354a("PushMessageExecutor start broadcastExecute");
        if (C1343f.f2169f != null) {
            Intent intent = new Intent("com.igexin.sdk.action.execute");
            intent.putExtra("taskid", str);
            intent.putExtra("messageid", str2);
            intent.putExtra("appid", C1343f.f2135a);
            intent.putExtra("pkgname", C1343f.f2168e);
            C1343f.f2169f.sendBroadcast(intent);
        }
    }

    /* renamed from: a */
    public boolean mo14766a(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("taskid", str);
        bundle.putString("messageid", str2);
        bundle.putString("actionid", str3);
        Message obtain = Message.obtain();
        obtain.what = C1275b.f1904h;
        obtain.obj = bundle;
        return C1340e.m2032a().mo14702a(obtain);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x008a A[Catch:{ Throwable -> 0x00b6 }, LOOP:3: B:27:0x008a->B:30:0x009a, LOOP_START, PHI: r8 
      PHI: (r8v1 com.igexin.push.core.bean.BaseAction) = (r8v0 com.igexin.push.core.bean.BaseAction), (r8v7 com.igexin.push.core.bean.BaseAction) binds: [B:26:0x007d, B:30:0x009a] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14767a(org.json.JSONObject r11, com.igexin.push.core.bean.PushTaskBean r12) {
        /*
            r10 = this;
            java.lang.String r0 = "PushMessageExecutor------parse pushmessage actionchain json start-------"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 1
            java.lang.String r2 = "action_chains"
            org.json.JSONArray r11 = r11.getJSONArray(r2)     // Catch:{ Throwable -> 0x00b6 }
            r2 = 0
            r3 = 0
        L_0x0013:
            int r4 = r11.length()     // Catch:{ Throwable -> 0x00b6 }
            java.lang.String r5 = "type"
            if (r3 >= r4) goto L_0x006a
            java.lang.Object r4 = r11.get(r3)     // Catch:{ Throwable -> 0x00b6 }
            org.json.JSONObject r4 = (org.json.JSONObject) r4     // Catch:{ Throwable -> 0x00b6 }
            java.lang.String r4 = r4.getString(r5)     // Catch:{ Throwable -> 0x00b6 }
            if (r4 == 0) goto L_0x0067
            com.igexin.push.extension.a r5 = com.igexin.push.extension.C1395a.m2261a()     // Catch:{ Throwable -> 0x00b6 }
            java.util.List r5 = r5.mo14846b()     // Catch:{ Throwable -> 0x00b6 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ Throwable -> 0x00b6 }
        L_0x0033:
            boolean r6 = r5.hasNext()     // Catch:{ Throwable -> 0x00b6 }
            if (r6 == 0) goto L_0x0047
            java.lang.Object r6 = r5.next()     // Catch:{ Throwable -> 0x00b6 }
            com.igexin.push.extension.stub.IPushExtension r6 = (com.igexin.push.extension.stub.IPushExtension) r6     // Catch:{ Throwable -> 0x00b6 }
            boolean r6 = r6.isActionSupported(r4)     // Catch:{ Throwable -> 0x00b6 }
            if (r6 == 0) goto L_0x0033
            r5 = 1
            goto L_0x0048
        L_0x0047:
            r5 = 0
        L_0x0048:
            if (r5 != 0) goto L_0x0067
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00b6 }
            r5.<init>()     // Catch:{ Throwable -> 0x00b6 }
            java.lang.String r6 = "PushMessageExecutor|extension not suport type = "
            r5.append(r6)     // Catch:{ Throwable -> 0x00b6 }
            r5.append(r4)     // Catch:{ Throwable -> 0x00b6 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x00b6 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ Throwable -> 0x00b6 }
            java.util.Set<java.lang.String> r5 = f2213c     // Catch:{ Throwable -> 0x00b6 }
            boolean r4 = r5.contains(r4)     // Catch:{ Throwable -> 0x00b6 }
            if (r4 != 0) goto L_0x0067
            return r2
        L_0x0067:
            int r3 = r3 + 1
            goto L_0x0013
        L_0x006a:
            r3 = 0
        L_0x006b:
            int r4 = r11.length()     // Catch:{ Throwable -> 0x00b6 }
            if (r3 >= r4) goto L_0x00cf
            java.lang.Object r4 = r11.get(r3)     // Catch:{ Throwable -> 0x00b6 }
            org.json.JSONObject r4 = (org.json.JSONObject) r4     // Catch:{ Throwable -> 0x00b6 }
            java.lang.String r6 = r4.getString(r5)     // Catch:{ Throwable -> 0x00b6 }
            if (r6 == 0) goto L_0x00b3
            com.igexin.push.extension.a r7 = com.igexin.push.extension.C1395a.m2261a()     // Catch:{ Throwable -> 0x00b6 }
            java.util.List r7 = r7.mo14846b()     // Catch:{ Throwable -> 0x00b6 }
            r8 = 0
            java.util.Iterator r7 = r7.iterator()     // Catch:{ Throwable -> 0x00b6 }
        L_0x008a:
            boolean r9 = r7.hasNext()     // Catch:{ Throwable -> 0x00b6 }
            if (r9 == 0) goto L_0x009c
            java.lang.Object r8 = r7.next()     // Catch:{ Throwable -> 0x00b6 }
            com.igexin.push.extension.stub.IPushExtension r8 = (com.igexin.push.extension.stub.IPushExtension) r8     // Catch:{ Throwable -> 0x00b6 }
            com.igexin.push.core.bean.BaseAction r8 = r8.parseAction(r4)     // Catch:{ Throwable -> 0x00b6 }
            if (r8 == 0) goto L_0x008a
        L_0x009c:
            if (r8 != 0) goto L_0x00ad
            com.igexin.push.core.a.a.a r6 = r10.m2113a((java.lang.String) r6)     // Catch:{ Throwable -> 0x00b6 }
            if (r6 == 0) goto L_0x00ad
            com.igexin.push.core.bean.BaseAction r8 = r6.mo14466a(r4)     // Catch:{ Throwable -> 0x00b6 }
            if (r8 == 0) goto L_0x00ad
            r8.setSupportExt(r2)     // Catch:{ Throwable -> 0x00b6 }
        L_0x00ad:
            if (r8 != 0) goto L_0x00b0
            return r2
        L_0x00b0:
            r0.add(r8)     // Catch:{ Throwable -> 0x00b6 }
        L_0x00b3:
            int r3 = r3 + 1
            goto L_0x006b
        L_0x00b6:
            r11 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "PushMessageExecutor|"
            r2.append(r3)
            java.lang.String r11 = r11.toString()
            r2.append(r11)
            java.lang.String r11 = r2.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)
        L_0x00cf:
            r12.setActionChains(r0)
            java.lang.String r11 = "PushMessageExecutor------parse pushmessage actionchain json end-------"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r11)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1355r.mo14767a(org.json.JSONObject, com.igexin.push.core.bean.PushTaskBean):boolean");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14768a(org.json.JSONObject r24, byte[] r25, boolean r26) {
        /*
            r23 = this;
            r1 = r23
            r0 = r24
            r2 = r25
            r3 = r26
            java.lang.String r4 = "extra_actionid"
            java.lang.String r5 = "cdnType"
            java.lang.String r6 = "messageid"
            java.lang.String r7 = "appid"
            java.lang.String r8 = "action"
            java.lang.String r9 = "|"
            java.lang.String r10 = "taskid"
            boolean r12 = r0.has(r8)     // Catch:{ Exception -> 0x0232 }
            if (r12 == 0) goto L_0x0230
            java.lang.String r8 = r0.getString(r8)     // Catch:{ Exception -> 0x0232 }
            java.lang.String r12 = "pushmessage"
            boolean r8 = r8.equals(r12)     // Catch:{ Exception -> 0x0232 }
            if (r8 == 0) goto L_0x0230
            java.lang.String r8 = "id"
            java.lang.String r8 = r0.getString(r8)     // Catch:{ Exception -> 0x0232 }
            java.lang.String r12 = r0.getString(r7)     // Catch:{ Exception -> 0x0232 }
            java.lang.String r13 = r0.getString(r6)     // Catch:{ Exception -> 0x0232 }
            java.lang.String r14 = r0.getString(r10)     // Catch:{ Exception -> 0x0232 }
            java.lang.String r15 = "appkey"
            java.lang.String r15 = r0.getString(r15)     // Catch:{ Exception -> 0x0232 }
            java.lang.String r11 = "action_chains"
            org.json.JSONArray r11 = r0.getJSONArray(r11)     // Catch:{ Exception -> 0x0232 }
            r16 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0232 }
            r4.<init>()     // Catch:{ Exception -> 0x0232 }
            java.lang.String r1 = "pushmessage|"
            r4.append(r1)     // Catch:{ Exception -> 0x022c }
            r4.append(r14)     // Catch:{ Exception -> 0x022c }
            r4.append(r9)     // Catch:{ Exception -> 0x022c }
            r4.append(r13)     // Catch:{ Exception -> 0x022c }
            r4.append(r9)     // Catch:{ Exception -> 0x022c }
            r4.append(r12)     // Catch:{ Exception -> 0x022c }
            r4.append(r9)     // Catch:{ Exception -> 0x022c }
            r4.append(r3)     // Catch:{ Exception -> 0x022c }
            java.lang.String r1 = r4.toString()     // Catch:{ Exception -> 0x022c }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r1)     // Catch:{ Exception -> 0x022c }
            if (r12 == 0) goto L_0x0222
            if (r8 == 0) goto L_0x0222
            if (r13 == 0) goto L_0x0222
            if (r14 == 0) goto L_0x0222
            if (r11 == 0) goto L_0x0222
            java.lang.String r1 = com.igexin.push.core.C1343f.f2135a     // Catch:{ Exception -> 0x022c }
            boolean r1 = r12.equals(r1)     // Catch:{ Exception -> 0x022c }
            if (r1 == 0) goto L_0x0222
            com.igexin.push.core.bean.PushTaskBean r1 = new com.igexin.push.core.bean.PushTaskBean     // Catch:{ Exception -> 0x022c }
            r1.<init>()     // Catch:{ Exception -> 0x022c }
            r1.setAppid(r12)     // Catch:{ Exception -> 0x022c }
            r1.setMessageId(r13)     // Catch:{ Exception -> 0x022c }
            r1.setTaskId(r14)     // Catch:{ Exception -> 0x022c }
            r1.setId(r8)     // Catch:{ Exception -> 0x022c }
            r1.setAppKey(r15)     // Catch:{ Exception -> 0x022c }
            r4 = 1
            r1.setCurrentActionid(r4)     // Catch:{ Exception -> 0x022c }
            boolean r4 = r0.has(r5)     // Catch:{ Exception -> 0x022c }
            if (r4 == 0) goto L_0x00a5
            boolean r4 = r0.getBoolean(r5)     // Catch:{ Exception -> 0x022c }
            r1.setCDNType(r4)     // Catch:{ Exception -> 0x022c }
        L_0x00a5:
            com.igexin.push.core.a.f r4 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x022c }
            java.lang.String r4 = r4.mo14472a((java.lang.String) r14, (java.lang.String) r13)     // Catch:{ Exception -> 0x022c }
            if (r3 == 0) goto L_0x00f2
            com.igexin.push.core.a.f r5 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x022c }
            java.lang.String r8 = "0"
            r5.mo14486b(r1, r8)     // Catch:{ Exception -> 0x022c }
            boolean r5 = com.igexin.push.util.C1576a.m3206a((java.lang.String) r14)     // Catch:{ Exception -> 0x022c }
            if (r5 == 0) goto L_0x00d9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x022c }
            r0.<init>()     // Catch:{ Exception -> 0x022c }
            java.lang.String r1 = "PushMessageExecutor|"
            r0.append(r1)     // Catch:{ Exception -> 0x022c }
            r0.append(r14)     // Catch:{ Exception -> 0x022c }
            java.lang.String r1 = " in blacklist ###"
            r0.append(r1)     // Catch:{ Exception -> 0x022c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x022c }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x022c }
            r1 = 1
            return r1
        L_0x00d9:
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x022c }
            boolean r5 = com.igexin.push.util.C1576a.m3200a((long) r8)     // Catch:{ Exception -> 0x022c }
            if (r5 == 0) goto L_0x00e5
            r5 = 1
            return r5
        L_0x00e5:
            r5 = 1
            boolean r8 = com.igexin.push.util.C1576a.m3211a((org.json.JSONObject) r24)     // Catch:{ Exception -> 0x022c }
            if (r8 == 0) goto L_0x00f2
            java.lang.String r0 = "PushMessageExecutor|message have loop"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x022c }
            return r5
        L_0x00f2:
            android.content.ContentValues r5 = new android.content.ContentValues     // Catch:{ Exception -> 0x022c }
            r5.<init>()     // Catch:{ Exception -> 0x022c }
            r5.put(r6, r13)     // Catch:{ Exception -> 0x022c }
            r5.put(r10, r14)     // Catch:{ Exception -> 0x022c }
            r5.put(r7, r12)     // Catch:{ Exception -> 0x022c }
            java.lang.String r6 = "key"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x022c }
            r7.<init>()     // Catch:{ Exception -> 0x022c }
            java.lang.String r8 = "CACHE_"
            r7.append(r8)     // Catch:{ Exception -> 0x022c }
            r7.append(r4)     // Catch:{ Exception -> 0x022c }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x022c }
            r5.put(r6, r7)     // Catch:{ Exception -> 0x022c }
            java.lang.String r6 = "info"
            java.lang.String r7 = r24.toString()     // Catch:{ Exception -> 0x022c }
            byte[] r7 = r7.getBytes()     // Catch:{ Exception -> 0x022c }
            byte[] r7 = com.igexin.p032b.p042b.C1196a.m1438b(r7)     // Catch:{ Exception -> 0x022c }
            r5.put(r6, r7)     // Catch:{ Exception -> 0x022c }
            java.lang.String r6 = "createtime"
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x022c }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x022c }
            r5.put(r6, r7)     // Catch:{ Exception -> 0x022c }
            if (r2 == 0) goto L_0x013e
            java.lang.String r6 = "msgextra"
            r5.put(r6, r2)     // Catch:{ Exception -> 0x022c }
            r1.setMsgExtra(r2)     // Catch:{ Exception -> 0x022c }
        L_0x013e:
            int r2 = r11.length()     // Catch:{ Exception -> 0x022c }
            if (r2 <= 0) goto L_0x0153
            r2 = r23
            boolean r6 = r2.mo14767a((org.json.JSONObject) r0, (com.igexin.push.core.bean.PushTaskBean) r1)     // Catch:{ Exception -> 0x022a }
            if (r6 != 0) goto L_0x0155
            java.lang.String r0 = "PushMessageExecutor parseActionChains result = false #######"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x022a }
            r1 = 1
            return r1
        L_0x0153:
            r2 = r23
        L_0x0155:
            java.lang.String r6 = "condition"
            if (r3 == 0) goto L_0x020e
            r3 = 0
            com.igexin.push.core.e r7 = com.igexin.push.core.C1340e.m2032a()     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            com.igexin.push.b.b r17 = r7.mo14712i()     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.String r18 = "message"
            java.lang.String[] r19 = new java.lang.String[]{r10}     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r7 = 1
            java.lang.String[] r8 = new java.lang.String[r7]     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r7 = 0
            r8[r7] = r14     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r21 = 0
            r22 = 0
            r20 = r8
            android.database.Cursor r3 = r17.mo14355a(r18, r19, r20, r21, r22)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            if (r3 == 0) goto L_0x01fd
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r7.<init>()     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.String r8 = "PushMessageExecutor|taskid = "
            r7.append(r8)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r7.append(r14)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.String r8 = ", db cnt = "
            r7.append(r8)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            int r8 = r3.getCount()     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r7.append(r8)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            int r7 = r3.getCount()     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            if (r7 != 0) goto L_0x01f6
            r7 = r16
            boolean r8 = r0.has(r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            if (r8 == 0) goto L_0x01b3
            com.igexin.push.core.a.f r8 = com.igexin.push.core.p047a.C1257f.m1711a()     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.String r7 = r0.getString(r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r8.mo14486b(r1, r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
        L_0x01b3:
            boolean r7 = r0.has(r6)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.String r8 = "status"
            if (r7 == 0) goto L_0x01cd
            r2.m2117b((org.json.JSONObject) r0, (com.igexin.push.core.bean.PushTaskBean) r1)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            int r7 = com.igexin.push.core.C1275b.f1910n     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r1.setStatus(r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            int r7 = com.igexin.push.core.C1275b.f1910n     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
        L_0x01c9:
            r5.put(r8, r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            goto L_0x01d9
        L_0x01cd:
            int r7 = com.igexin.push.core.C1275b.f1911o     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r1.setStatus(r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            int r7 = com.igexin.push.core.C1275b.f1911o     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            goto L_0x01c9
        L_0x01d9:
            r2.mo14762a((android.content.ContentValues) r5)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r5 = com.igexin.push.core.C1343f.f2142ad     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r5.put(r4, r1)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            boolean r0 = r0.has(r6)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            if (r0 == 0) goto L_0x01eb
            r23.mo14776e()     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            goto L_0x01fd
        L_0x01eb:
            r2.mo14764a((java.lang.String) r14, (java.lang.String) r13)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.String r0 = com.igexin.push.core.C1343f.f2135a     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            java.lang.String r1 = com.igexin.push.core.C1343f.f2168e     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            r2.mo14765a(r14, r13, r0, r1)     // Catch:{ Exception -> 0x020a, all -> 0x0203 }
            goto L_0x01fd
        L_0x01f6:
            if (r3 == 0) goto L_0x01fb
            r3.close()     // Catch:{ Exception -> 0x022a }
        L_0x01fb:
            r1 = 1
            return r1
        L_0x01fd:
            if (r3 == 0) goto L_0x024c
        L_0x01ff:
            r3.close()     // Catch:{ Exception -> 0x022a }
            goto L_0x024c
        L_0x0203:
            r0 = move-exception
            if (r3 == 0) goto L_0x0209
            r3.close()     // Catch:{ Exception -> 0x022a }
        L_0x0209:
            throw r0     // Catch:{ Exception -> 0x022a }
        L_0x020a:
            if (r3 == 0) goto L_0x024c
            goto L_0x01ff
        L_0x020e:
            boolean r3 = r0.has(r6)     // Catch:{ Exception -> 0x022a }
            if (r3 == 0) goto L_0x0217
            r2.m2117b((org.json.JSONObject) r0, (com.igexin.push.core.bean.PushTaskBean) r1)     // Catch:{ Exception -> 0x022a }
        L_0x0217:
            int r0 = com.igexin.push.core.C1275b.f1911o     // Catch:{ Exception -> 0x022a }
            r1.setStatus(r0)     // Catch:{ Exception -> 0x022a }
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r0 = com.igexin.push.core.C1343f.f2142ad     // Catch:{ Exception -> 0x022a }
            r0.put(r4, r1)     // Catch:{ Exception -> 0x022a }
            goto L_0x024c
        L_0x0222:
            r2 = r23
            java.lang.String r0 = "PushMessageExecutor receieve error pushmessage +++++++++++++++++++"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x022a }
            goto L_0x024c
        L_0x022a:
            r0 = move-exception
            goto L_0x0234
        L_0x022c:
            r0 = move-exception
            r2 = r23
            goto L_0x0234
        L_0x0230:
            r2 = r1
            goto L_0x024c
        L_0x0232:
            r0 = move-exception
            r2 = r1
        L_0x0234:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "PushMessageExecutor "
            r1.append(r3)
            java.lang.String r0 = r0.toString()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
        L_0x024c:
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1355r.mo14768a(org.json.JSONObject, byte[], boolean):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14769b(android.content.Intent r9) {
        /*
            r8 = this;
            java.lang.String r0 = "taskid"
            java.lang.String r0 = r9.getStringExtra(r0)
            java.lang.String r1 = "messageid"
            java.lang.String r1 = r9.getStringExtra(r1)
            java.lang.String r2 = "actionid"
            java.lang.String r2 = r9.getStringExtra(r2)
            java.lang.String r3 = "accesstoken"
            java.lang.String r3 = r9.getStringExtra(r3)
            java.lang.String r4 = "title"
            boolean r5 = r9.hasExtra(r4)
            java.lang.String r6 = ""
            if (r5 == 0) goto L_0x0027
            java.lang.String r4 = r9.getStringExtra(r4)
            goto L_0x0028
        L_0x0027:
            r4 = r6
        L_0x0028:
            java.lang.String r5 = "content"
            boolean r7 = r9.hasExtra(r5)
            if (r7 == 0) goto L_0x0034
            java.lang.String r6 = r9.getStringExtra(r5)
        L_0x0034:
            r5 = 0
            java.lang.String r7 = "notifID"
            int r9 = r9.getIntExtra(r7, r5)
            android.content.Context r5 = com.igexin.push.core.C1343f.f2169f
            java.lang.String r7 = "notification"
            java.lang.Object r5 = r5.getSystemService(r7)
            android.app.NotificationManager r5 = (android.app.NotificationManager) r5
            if (r9 == 0) goto L_0x004b
        L_0x0047:
            r5.cancel(r9)
            goto L_0x0060
        L_0x004b:
            java.util.Map<java.lang.String, java.lang.Integer> r9 = com.igexin.push.core.C1343f.f2143ae
            java.lang.Object r9 = r9.get(r0)
            if (r9 == 0) goto L_0x0060
            java.util.Map<java.lang.String, java.lang.Integer> r9 = com.igexin.push.core.C1343f.f2143ae
            java.lang.Object r9 = r9.get(r0)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            goto L_0x0047
        L_0x0060:
            java.lang.String r9 = com.igexin.push.core.C1343f.f2153ao
            boolean r9 = r3.equals(r9)
            if (r9 != 0) goto L_0x0069
            goto L_0x0073
        L_0x0069:
            com.igexin.push.core.a r9 = com.igexin.push.core.C1238a.m1630a()
            r9.mo14463c(r0, r1, r4, r6)
            r8.mo14772b(r0, r1, r2)
        L_0x0073:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1355r.mo14769b(android.content.Intent):void");
    }

    /* renamed from: b */
    public void mo14770b(String str, String str2) {
        C1179b.m1354a("PushMessageExecutor do processActionExecute");
        if (str2 != null && str != null) {
            try {
                if (C1340e.m2032a() != null && mo14773c(str, str2) == C1294c.success) {
                    mo14766a(str, str2, "1");
                }
            } catch (Throwable th) {
                C1179b.m1354a("PushMessageExecutor|" + th.toString());
            }
        }
    }

    /* renamed from: b */
    public boolean mo14771b() {
        long currentTimeMillis = System.currentTimeMillis();
        if (C1343f.f2116H <= 0) {
            C1343f.f2116H = currentTimeMillis - 60000;
            return true;
        } else if (currentTimeMillis - C1343f.f2116H <= 60000) {
            return false;
        } else {
            C1343f.f2116H = currentTimeMillis;
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x009a, code lost:
        if (r8 != null) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x009c, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00bf, code lost:
        if (r8 == null) goto L_0x00c8;
     */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo14772b(java.lang.String r17, java.lang.String r18, java.lang.String r19) {
        /*
            r16 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            com.igexin.push.core.a.f r3 = com.igexin.push.core.p047a.C1257f.m1711a()
            java.lang.String r3 = r3.mo14472a((java.lang.String) r0, (java.lang.String) r1)
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r4 = com.igexin.push.core.C1343f.f2142ad
            java.lang.Object r4 = r4.get(r3)
            com.igexin.push.core.bean.PushTaskBean r4 = (com.igexin.push.core.bean.PushTaskBean) r4
            java.lang.String r5 = "PushMessageExecutor|"
            r6 = 1
            r7 = 0
            if (r4 != 0) goto L_0x00c8
            r8 = 0
            com.igexin.push.core.e r9 = com.igexin.push.core.C1340e.m2032a()     // Catch:{ Throwable -> 0x00a8 }
            com.igexin.push.b.b r10 = r9.mo14712i()     // Catch:{ Throwable -> 0x00a8 }
            java.lang.String r11 = "message"
            java.lang.String r9 = "taskid"
            java.lang.String r12 = "messageid"
            java.lang.String[] r12 = new java.lang.String[]{r9, r12}     // Catch:{ Throwable -> 0x00a8 }
            r9 = 2
            java.lang.String[] r13 = new java.lang.String[r9]     // Catch:{ Throwable -> 0x00a8 }
            r13[r7] = r0     // Catch:{ Throwable -> 0x00a8 }
            r13[r6] = r1     // Catch:{ Throwable -> 0x00a8 }
            r14 = 0
            r15 = 0
            android.database.Cursor r8 = r10.mo14355a(r11, r12, r13, r14, r15)     // Catch:{ Throwable -> 0x00a8 }
            if (r8 == 0) goto L_0x00a0
            int r9 = r8.getCount()     // Catch:{ Throwable -> 0x00a8 }
            if (r9 > 0) goto L_0x0045
            goto L_0x00a0
        L_0x0045:
            boolean r9 = r8.moveToNext()     // Catch:{ Throwable -> 0x00a8 }
            if (r9 == 0) goto L_0x009a
            java.lang.String r9 = "info"
            int r9 = r8.getColumnIndexOrThrow(r9)     // Catch:{ Throwable -> 0x00a8 }
            byte[] r9 = r8.getBlob(r9)     // Catch:{ Throwable -> 0x00a8 }
            java.lang.String r10 = "msgextra"
            int r10 = r8.getColumnIndexOrThrow(r10)     // Catch:{ Throwable -> 0x00a8 }
            byte[] r10 = r8.getBlob(r10)     // Catch:{ Throwable -> 0x00a8 }
            java.lang.String r11 = new java.lang.String     // Catch:{ Throwable -> 0x00a8 }
            byte[] r9 = com.igexin.p032b.p042b.C1196a.m1439c(r9)     // Catch:{ Throwable -> 0x00a8 }
            r11.<init>(r9)     // Catch:{ Throwable -> 0x00a8 }
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Throwable -> 0x00a8 }
            r9.<init>(r11)     // Catch:{ Throwable -> 0x00a8 }
            com.igexin.push.core.r r11 = m2114a()     // Catch:{ Throwable -> 0x00a8 }
            r11.mo14768a((org.json.JSONObject) r9, (byte[]) r10, (boolean) r7)     // Catch:{ Throwable -> 0x00a8 }
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r9 = com.igexin.push.core.C1343f.f2142ad     // Catch:{ Throwable -> 0x00a8 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a8 }
            r10.<init>()     // Catch:{ Throwable -> 0x00a8 }
            r10.append(r0)     // Catch:{ Throwable -> 0x00a8 }
            java.lang.String r11 = ":"
            r10.append(r11)     // Catch:{ Throwable -> 0x00a8 }
            r10.append(r1)     // Catch:{ Throwable -> 0x00a8 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x00a8 }
            java.lang.Object r9 = r9.get(r10)     // Catch:{ Throwable -> 0x00a8 }
            com.igexin.push.core.bean.PushTaskBean r9 = (com.igexin.push.core.bean.PushTaskBean) r9     // Catch:{ Throwable -> 0x00a8 }
            if (r9 != 0) goto L_0x0098
            if (r8 == 0) goto L_0x0097
            r8.close()
        L_0x0097:
            return r7
        L_0x0098:
            r4 = r9
            goto L_0x0045
        L_0x009a:
            if (r8 == 0) goto L_0x00c8
        L_0x009c:
            r8.close()
            goto L_0x00c8
        L_0x00a0:
            if (r8 == 0) goto L_0x00a5
            r8.close()
        L_0x00a5:
            return r7
        L_0x00a6:
            r0 = move-exception
            goto L_0x00c2
        L_0x00a8:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a6 }
            r1.<init>()     // Catch:{ all -> 0x00a6 }
            r1.append(r5)     // Catch:{ all -> 0x00a6 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00a6 }
            r1.append(r0)     // Catch:{ all -> 0x00a6 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x00a6 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ all -> 0x00a6 }
            if (r8 == 0) goto L_0x00c8
            goto L_0x009c
        L_0x00c2:
            if (r8 == 0) goto L_0x00c7
            r8.close()
        L_0x00c7:
            throw r0
        L_0x00c8:
            int r0 = r4.getExecuteTimes()
            r1 = 50
            if (r0 < r1) goto L_0x00ee
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r0 = com.igexin.push.core.C1343f.f2142ad     // Catch:{ Exception -> 0x00d6 }
            r0.remove(r3)     // Catch:{ Exception -> 0x00d6 }
            goto L_0x00ed
        L_0x00d6:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r5)
            java.lang.String r0 = r0.toString()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
        L_0x00ed:
            return r6
        L_0x00ee:
            int r0 = r0 + r6
            r4.setExecuteTimes(r0)
            com.igexin.push.core.a.f r0 = com.igexin.push.core.p047a.C1257f.m1711a()
            r0.mo14486b(r4, r2)
            com.igexin.push.core.bean.BaseAction r0 = r4.getBaseAction(r2)     // Catch:{ Throwable -> 0x0140 }
            if (r0 != 0) goto L_0x0100
            return r7
        L_0x0100:
            boolean r1 = r0.isSupportExt()     // Catch:{ Throwable -> 0x0140 }
            if (r1 == 0) goto L_0x0125
            com.igexin.push.extension.a r1 = com.igexin.push.extension.C1395a.m2261a()     // Catch:{ Throwable -> 0x0140 }
            java.util.List r1 = r1.mo14846b()     // Catch:{ Throwable -> 0x0140 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Throwable -> 0x0140 }
        L_0x0112:
            boolean r2 = r1.hasNext()     // Catch:{ Throwable -> 0x0140 }
            if (r2 == 0) goto L_0x0125
            java.lang.Object r2 = r1.next()     // Catch:{ Throwable -> 0x0140 }
            com.igexin.push.extension.stub.IPushExtension r2 = (com.igexin.push.extension.stub.IPushExtension) r2     // Catch:{ Throwable -> 0x0140 }
            boolean r2 = r2.executeAction(r4, r0)     // Catch:{ Throwable -> 0x0140 }
            if (r2 == 0) goto L_0x0112
            return r6
        L_0x0125:
            java.lang.String r1 = r0.getType()     // Catch:{ Throwable -> 0x0140 }
            r2 = r16
            com.igexin.push.core.a.a.a r1 = r2.m2113a((java.lang.String) r1)     // Catch:{ Throwable -> 0x013e }
            if (r1 == 0) goto L_0x013d
            boolean r3 = r4.isStop()     // Catch:{ Throwable -> 0x013e }
            if (r3 == 0) goto L_0x0138
            goto L_0x013d
        L_0x0138:
            boolean r0 = r1.mo14468b(r4, r0)     // Catch:{ Throwable -> 0x013e }
            return r0
        L_0x013d:
            return r7
        L_0x013e:
            r0 = move-exception
            goto L_0x0143
        L_0x0140:
            r0 = move-exception
            r2 = r16
        L_0x0143:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r5)
            java.lang.String r0 = r0.toString()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1355r.mo14772b(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003d A[LOOP:1: B:11:0x003d->B:14:0x004f, LOOP_START, PHI: r4 
      PHI: (r4v1 com.igexin.push.core.c) = (r4v0 com.igexin.push.core.c), (r4v7 com.igexin.push.core.c) binds: [B:10:0x0031, B:14:0x004f] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.igexin.push.core.C1294c mo14773c(java.lang.String r8, java.lang.String r9) {
        /*
            r7 = this;
            com.igexin.push.core.c r0 = com.igexin.push.core.C1294c.success
            com.igexin.push.core.a.f r1 = com.igexin.push.core.p047a.C1257f.m1711a()
            java.lang.String r9 = r1.mo14472a((java.lang.String) r8, (java.lang.String) r9)
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r1 = com.igexin.push.core.C1343f.f2142ad
            java.lang.Object r9 = r1.get(r9)
            com.igexin.push.core.bean.PushTaskBean r9 = (com.igexin.push.core.bean.PushTaskBean) r9
            if (r9 != 0) goto L_0x0017
            com.igexin.push.core.c r8 = com.igexin.push.core.C1294c.stop
            return r8
        L_0x0017:
            java.util.List r1 = r9.getActionChains()
            java.util.Iterator r1 = r1.iterator()
            r2 = 0
        L_0x0020:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0075
            java.lang.Object r3 = r1.next()
            com.igexin.push.core.bean.BaseAction r3 = (com.igexin.push.core.bean.BaseAction) r3
            com.igexin.push.core.c r4 = com.igexin.push.core.C1294c.stop
            if (r3 != 0) goto L_0x0031
            return r4
        L_0x0031:
            com.igexin.push.extension.a r5 = com.igexin.push.extension.C1395a.m2261a()
            java.util.List r5 = r5.mo14846b()
            java.util.Iterator r5 = r5.iterator()
        L_0x003d:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0051
            java.lang.Object r4 = r5.next()
            com.igexin.push.extension.stub.IPushExtension r4 = (com.igexin.push.extension.stub.IPushExtension) r4
            com.igexin.push.core.c r4 = r4.prepareExecuteAction(r9, r3)
            com.igexin.push.core.c r6 = com.igexin.push.core.C1294c.stop
            if (r4 == r6) goto L_0x003d
        L_0x0051:
            com.igexin.push.core.c r5 = com.igexin.push.core.C1294c.stop
            if (r4 != r5) goto L_0x0069
            java.lang.String r5 = r3.getType()
            com.igexin.push.core.a.a.a r5 = r7.m2113a((java.lang.String) r5)
            if (r5 != 0) goto L_0x0060
            return r4
        L_0x0060:
            com.igexin.push.core.c r4 = r5.mo14467a(r9, r3)
            com.igexin.push.core.c r3 = com.igexin.push.core.C1294c.stop
            if (r4 != r3) goto L_0x0069
            return r4
        L_0x0069:
            com.igexin.push.core.c r3 = com.igexin.push.core.C1294c.success
            if (r0 != r3) goto L_0x006e
            r0 = r4
        L_0x006e:
            com.igexin.push.core.c r3 = com.igexin.push.core.C1294c.wait
            if (r4 != r3) goto L_0x0020
            int r2 = r2 + 1
            goto L_0x0020
        L_0x0075:
            if (r2 == 0) goto L_0x0084
            java.lang.Integer r9 = java.lang.Integer.valueOf(r2)
            r1 = 1
            boolean r8 = com.igexin.push.core.C1343f.m2078a(r8, r9, r1)
            if (r8 != 0) goto L_0x0084
            com.igexin.push.core.c r0 = com.igexin.push.core.C1294c.success
        L_0x0084:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1355r.mo14773c(java.lang.String, java.lang.String):com.igexin.push.core.c");
    }

    /* renamed from: c */
    public void mo14774c() {
        C1203b i = C1340e.m2032a().mo14712i();
        i.mo14354a(Constants.SHARED_MESSAGE_ID_FILE, "createtime <= " + (System.currentTimeMillis() - 604800000));
    }

    /* renamed from: d */
    public void mo14775d() {
        try {
            if (TextUtils.isEmpty(C1234k.f1829K)) {
                return;
            }
            if (!SchedulerSupport.NONE.equals(C1234k.f1829K)) {
                List<String> asList = Arrays.asList(C1234k.f1829K.split(","));
                if (!asList.isEmpty()) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<Map.Entry<String, PushTaskBean>> it = C1343f.f2142ad.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry next = it.next();
                        String str = (String) next.getKey();
                        PushTaskBean pushTaskBean = (PushTaskBean) next.getValue();
                        if (!TextUtils.isEmpty(str)) {
                            for (String str2 : asList) {
                                if (!TextUtils.isEmpty(str2) && str.startsWith(str2)) {
                                    arrayList.add(pushTaskBean.getTaskId());
                                    it.remove();
                                }
                            }
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        String[] strArr = new String[arrayList.size()];
                        for (int i = 0; i < arrayList.size(); i++) {
                            strArr[i] = (String) arrayList.get(i);
                        }
                        C1340e.m2032a().mo14712i().mo14357a(Constants.SHARED_MESSAGE_ID_FILE, new String[]{"taskid"}, strArr);
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v8, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v13, resolved type: java.lang.Integer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v24, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v25, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v34, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v35, resolved type: java.lang.String} */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x016a, code lost:
        if (java.lang.Integer.valueOf(r15.get("netConnected")).intValue() != com.igexin.push.util.C1576a.m3221g()) goto L_0x018c;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14776e() {
        /*
            r21 = this;
            r1 = r21
            java.lang.String r2 = "netConnected"
            java.lang.String r3 = "startTime"
            java.lang.String r4 = "bssid"
            java.lang.String r5 = "ssid"
            java.lang.String r6 = "screenOn"
            java.lang.String r7 = "wifi"
            java.lang.String r8 = "endTime"
            java.lang.String r9 = "expiredTime"
            java.lang.String r10 = "PushMessageExecutor|"
            boolean r0 = r21.m2118g()     // Catch:{ Exception -> 0x01ae }
            if (r0 == 0) goto L_0x001b
            return
        L_0x001b:
            java.lang.String r0 = "PushMessageExecutor--------checkConditionStatus start to read pushMessageMap data..."
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x01ae }
            java.util.Map<java.lang.String, com.igexin.push.core.bean.PushTaskBean> r0 = com.igexin.push.core.C1343f.f2142ad     // Catch:{ Exception -> 0x01ae }
            java.util.Set r0 = r0.entrySet()     // Catch:{ Exception -> 0x01ae }
            java.util.Iterator r11 = r0.iterator()     // Catch:{ Exception -> 0x01ae }
        L_0x002a:
            boolean r0 = r11.hasNext()     // Catch:{ Exception -> 0x01ae }
            if (r0 == 0) goto L_0x01c5
            java.lang.Object r0 = r11.next()     // Catch:{ Exception -> 0x0192 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Exception -> 0x0192 }
            java.lang.Object r12 = r0.getKey()     // Catch:{ Exception -> 0x0192 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Exception -> 0x0192 }
            java.lang.Object r0 = r0.getValue()     // Catch:{ Exception -> 0x0192 }
            com.igexin.push.core.bean.PushTaskBean r0 = (com.igexin.push.core.bean.PushTaskBean) r0     // Catch:{ Exception -> 0x0192 }
            java.lang.String r13 = ""
            if (r0 == 0) goto L_0x0188
            int r14 = r0.getStatus()     // Catch:{ Exception -> 0x0192 }
            int r15 = com.igexin.push.core.C1275b.f1910n     // Catch:{ Exception -> 0x0192 }
            if (r14 != r15) goto L_0x0188
            java.lang.String r14 = r0.getTaskId()     // Catch:{ Exception -> 0x0192 }
            java.util.Map r15 = r0.getConditionMap()     // Catch:{ Exception -> 0x0192 }
            if (r15 != 0) goto L_0x0059
            return
        L_0x0059:
            boolean r16 = com.igexin.push.util.C1576a.m3206a((java.lang.String) r14)     // Catch:{ Exception -> 0x0192 }
            if (r16 == 0) goto L_0x006a
            int r13 = com.igexin.push.core.C1275b.f1912p     // Catch:{ Exception -> 0x0192 }
            r1.m2115a((int) r13, (java.lang.String) r14, (java.lang.String) r12)     // Catch:{ Exception -> 0x0192 }
            int r12 = com.igexin.push.core.C1275b.f1911o     // Catch:{ Exception -> 0x0192 }
        L_0x0066:
            r0.setStatus(r12)     // Catch:{ Exception -> 0x0192 }
            goto L_0x002a
        L_0x006a:
            boolean r16 = r15.containsKey(r9)     // Catch:{ Exception -> 0x0192 }
            if (r16 == 0) goto L_0x008e
            java.lang.Object r16 = r15.get(r9)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r16 = (java.lang.String) r16     // Catch:{ Exception -> 0x0192 }
            java.lang.Long r16 = java.lang.Long.valueOf(r16)     // Catch:{ Exception -> 0x0192 }
            long r16 = r16.longValue()     // Catch:{ Exception -> 0x0192 }
            long r18 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0192 }
            int r20 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r20 >= 0) goto L_0x008e
            int r13 = com.igexin.push.core.C1275b.f1912p     // Catch:{ Exception -> 0x0192 }
            r1.m2115a((int) r13, (java.lang.String) r14, (java.lang.String) r12)     // Catch:{ Exception -> 0x0192 }
            int r12 = com.igexin.push.core.C1275b.f1911o     // Catch:{ Exception -> 0x0192 }
            goto L_0x0066
        L_0x008e:
            boolean r16 = r15.containsKey(r8)     // Catch:{ Exception -> 0x0192 }
            if (r16 == 0) goto L_0x00b2
            java.lang.Object r16 = r15.get(r8)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r16 = (java.lang.String) r16     // Catch:{ Exception -> 0x0192 }
            java.lang.Long r16 = java.lang.Long.valueOf(r16)     // Catch:{ Exception -> 0x0192 }
            long r16 = r16.longValue()     // Catch:{ Exception -> 0x0192 }
            long r18 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0192 }
            int r20 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r20 >= 0) goto L_0x00b2
            int r13 = com.igexin.push.core.C1275b.f1912p     // Catch:{ Exception -> 0x0192 }
            r1.m2115a((int) r13, (java.lang.String) r14, (java.lang.String) r12)     // Catch:{ Exception -> 0x0192 }
            int r12 = com.igexin.push.core.C1275b.f1911o     // Catch:{ Exception -> 0x0192 }
            goto L_0x0066
        L_0x00b2:
            boolean r16 = r15.containsKey(r7)     // Catch:{ Exception -> 0x0192 }
            if (r16 == 0) goto L_0x00d6
            java.lang.Object r16 = r15.get(r7)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r16 = (java.lang.String) r16     // Catch:{ Exception -> 0x0192 }
            java.lang.Integer r16 = java.lang.Integer.valueOf(r16)     // Catch:{ Exception -> 0x0192 }
            r17 = r7
            int r7 = r16.intValue()     // Catch:{ Exception -> 0x00d3 }
            com.igexin.push.util.C1576a.m3218d()     // Catch:{ Exception -> 0x00d3 }
            r16 = r8
            int r8 = com.igexin.push.core.C1343f.f2179p     // Catch:{ Exception -> 0x0186 }
            if (r7 == r8) goto L_0x00da
            goto L_0x018c
        L_0x00d3:
            r0 = move-exception
            goto L_0x0195
        L_0x00d6:
            r17 = r7
            r16 = r8
        L_0x00da:
            boolean r7 = r15.containsKey(r6)     // Catch:{ Exception -> 0x0186 }
            if (r7 == 0) goto L_0x00f7
            java.lang.Object r7 = r15.get(r6)     // Catch:{ Exception -> 0x0186 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0186 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x0186 }
            int r7 = r7.intValue()     // Catch:{ Exception -> 0x0186 }
            com.igexin.push.util.C1576a.m3219e()     // Catch:{ Exception -> 0x0186 }
            int r8 = com.igexin.push.core.C1343f.f2180q     // Catch:{ Exception -> 0x0186 }
            if (r7 == r8) goto L_0x00f7
            goto L_0x018c
        L_0x00f7:
            boolean r7 = r15.containsKey(r5)     // Catch:{ Exception -> 0x0186 }
            if (r7 == 0) goto L_0x0111
            java.lang.Object r7 = r15.get(r5)     // Catch:{ Exception -> 0x0186 }
            r13 = r7
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ Exception -> 0x0186 }
            r21.m2119h()     // Catch:{ Exception -> 0x0186 }
            java.util.Map<java.lang.String, java.lang.String> r7 = com.igexin.push.core.C1343f.f2149ak     // Catch:{ Exception -> 0x0186 }
            boolean r7 = r7.containsValue(r13)     // Catch:{ Exception -> 0x0186 }
            if (r7 != 0) goto L_0x0111
            goto L_0x018c
        L_0x0111:
            boolean r7 = r15.containsKey(r4)     // Catch:{ Exception -> 0x0186 }
            if (r7 == 0) goto L_0x0135
            java.lang.Object r7 = r15.get(r4)     // Catch:{ Exception -> 0x0186 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0186 }
            java.util.Map<java.lang.String, java.lang.String> r8 = com.igexin.push.core.C1343f.f2149ak     // Catch:{ Exception -> 0x0186 }
            boolean r8 = r8.containsKey(r7)     // Catch:{ Exception -> 0x0186 }
            if (r8 != 0) goto L_0x0126
            goto L_0x018c
        L_0x0126:
            java.util.Map<java.lang.String, java.lang.String> r8 = com.igexin.push.core.C1343f.f2149ak     // Catch:{ Exception -> 0x0186 }
            java.lang.Object r7 = r8.get(r7)     // Catch:{ Exception -> 0x0186 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0186 }
            boolean r7 = r7.equals(r13)     // Catch:{ Exception -> 0x0186 }
            if (r7 != 0) goto L_0x0135
            goto L_0x018c
        L_0x0135:
            boolean r7 = r15.containsKey(r3)     // Catch:{ Exception -> 0x0186 }
            if (r7 == 0) goto L_0x0152
            java.lang.Object r7 = r15.get(r3)     // Catch:{ Exception -> 0x0186 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0186 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x0186 }
            long r7 = r7.longValue()     // Catch:{ Exception -> 0x0186 }
            long r18 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0186 }
            int r13 = (r7 > r18 ? 1 : (r7 == r18 ? 0 : -1))
            if (r13 <= 0) goto L_0x0152
            goto L_0x018c
        L_0x0152:
            boolean r7 = r15.containsKey(r2)     // Catch:{ Exception -> 0x0186 }
            if (r7 == 0) goto L_0x016d
            java.lang.Object r7 = r15.get(r2)     // Catch:{ Exception -> 0x018c }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x018c }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x018c }
            int r7 = r7.intValue()     // Catch:{ Exception -> 0x018c }
            boolean r8 = com.igexin.push.util.C1576a.m3221g()     // Catch:{ Exception -> 0x018c }
            if (r7 == r8) goto L_0x016d
            goto L_0x018c
        L_0x016d:
            java.lang.String r7 = r0.getMessageId()     // Catch:{ Exception -> 0x0186 }
            r1.mo14764a((java.lang.String) r14, (java.lang.String) r7)     // Catch:{ Exception -> 0x0186 }
            java.lang.String r8 = com.igexin.push.core.C1343f.f2135a     // Catch:{ Exception -> 0x0186 }
            java.lang.String r13 = com.igexin.push.core.C1343f.f2168e     // Catch:{ Exception -> 0x0186 }
            r1.mo14765a(r14, r7, r8, r13)     // Catch:{ Exception -> 0x0186 }
            int r7 = com.igexin.push.core.C1275b.f1911o     // Catch:{ Exception -> 0x0186 }
            r1.m2115a((int) r7, (java.lang.String) r14, (java.lang.String) r12)     // Catch:{ Exception -> 0x0186 }
            int r7 = com.igexin.push.core.C1275b.f1911o     // Catch:{ Exception -> 0x0186 }
            r0.setStatus(r7)     // Catch:{ Exception -> 0x0186 }
            goto L_0x018c
        L_0x0186:
            r0 = move-exception
            goto L_0x0197
        L_0x0188:
            r17 = r7
            r16 = r8
        L_0x018c:
            r8 = r16
            r7 = r17
            goto L_0x002a
        L_0x0192:
            r0 = move-exception
            r17 = r7
        L_0x0195:
            r16 = r8
        L_0x0197:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01ae }
            r7.<init>()     // Catch:{ Exception -> 0x01ae }
            r7.append(r10)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x01ae }
            r7.append(r0)     // Catch:{ Exception -> 0x01ae }
            java.lang.String r0 = r7.toString()     // Catch:{ Exception -> 0x01ae }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)     // Catch:{ Exception -> 0x01ae }
            goto L_0x018c
        L_0x01ae:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r10)
            java.lang.String r0 = r0.toString()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
        L_0x01c5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1355r.mo14776e():void");
    }

    /* renamed from: f */
    public void mo14777f() {
        Cursor cursor = null;
        try {
            C1203b i = C1340e.m2032a().mo14712i();
            cursor = i.mo14355a(Constants.SHARED_MESSAGE_ID_FILE, new String[]{NotificationCompat.CATEGORY_STATUS}, new String[]{"0"}, (String[]) null, (String) null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    byte[] blob = cursor.getBlob(cursor.getColumnIndex("info"));
                    long j = cursor.getLong(cursor.getColumnIndex("createtime"));
                    JSONObject jSONObject = new JSONObject(new String(C1196a.m1439c(blob)));
                    String string = jSONObject.getString("taskid");
                    if (jSONObject.has("condition") && !m2116a(jSONObject) && System.currentTimeMillis() - j > 259200000) {
                        C1179b.m1354a("PushMessageExecutor|del condition taskid = " + string);
                        i.mo14357a(Constants.SHARED_MESSAGE_ID_FILE, new String[]{"taskid"}, new String[]{string});
                    }
                }
            }
            if (cursor == null) {
            }
        } catch (Throwable th) {
            try {
                C1179b.m1354a("PushMessageExecutor|del condition" + th.toString());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }
}
