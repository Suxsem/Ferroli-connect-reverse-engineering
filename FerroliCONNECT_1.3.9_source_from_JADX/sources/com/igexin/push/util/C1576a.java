package com.igexin.push.util;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.C1280c;
import com.taobao.accs.utl.UtilityImpl;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.eclipse.jetty.util.security.Constraint;
import org.json.JSONArray;
import org.json.JSONObject;
import p110io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.igexin.push.util.a */
public class C1576a {

    /* renamed from: a */
    private static final String f3008a = "com.igexin.push.util.a";

    /* renamed from: a */
    public static void m3197a(Context context) {
        if ((context.getApplicationInfo().flags & 2) != 0) {
            File file = new File(context.getApplicationInfo().nativeLibraryDir, "libgetuiext3.so");
            if (!file.exists()) {
                String str = "libgetuiext3.so not found in path: " + file.getAbsolutePath();
                new Handler(Looper.getMainLooper()).post(new C1577b(context, str));
                Log.e(f3008a, str);
            }
        }
    }

    /* renamed from: a */
    private static void m3198a(Map<String, C1280c> map, String str) {
        map.remove(str);
        for (String next : map.get(str).mo14557b()) {
            C1280c cVar = map.get(next);
            if (cVar != null) {
                cVar.mo14560e();
                if (cVar.mo14558c() == 0) {
                    m3198a(map, next);
                }
            }
        }
    }

    /* renamed from: a */
    public static boolean m3199a() {
        try {
            if (!SchedulerSupport.NONE.equals(C1234k.f1855p)) {
                for (String a : C1234k.f1855p.split(",")) {
                    if (m3207a(a, C1343f.f2169f)) {
                        return false;
                    }
                }
                if (!SchedulerSupport.NONE.equals(C1234k.f1856q)) {
                    String[] split = C1234k.f1856q.split(",");
                    Class<?> cls = Class.forName("android.os.ServiceManager");
                    Method method = cls.getMethod("getService", new Class[]{String.class});
                    method.setAccessible(true);
                    for (String a2 : split) {
                        if (m3205a(cls, method, a2)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003f A[RETURN] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean m3200a(long r4) {
        /*
            java.util.Date r0 = new java.util.Date
            r0.<init>(r4)
            java.util.Calendar r4 = java.util.Calendar.getInstance()
            r4.setTime(r0)
            r5 = 11
            int r4 = r4.get(r5)
            int r5 = com.igexin.push.config.C1234k.f1840a
            int r0 = com.igexin.push.config.C1234k.f1841b
            int r5 = r5 + r0
            r0 = 24
            if (r5 < r0) goto L_0x001d
            int r5 = r5 + -24
        L_0x001d:
            int r1 = com.igexin.push.config.C1234k.f1841b
            r2 = 0
            if (r1 != 0) goto L_0x0023
            return r2
        L_0x0023:
            int r1 = com.igexin.push.config.C1234k.f1840a
            r3 = 1
            if (r1 >= r5) goto L_0x002f
            int r0 = com.igexin.push.config.C1234k.f1840a
            if (r4 < r0) goto L_0x003f
            if (r4 >= r5) goto L_0x003f
            return r3
        L_0x002f:
            int r1 = com.igexin.push.config.C1234k.f1840a
            if (r1 <= r5) goto L_0x003f
            if (r4 < 0) goto L_0x0038
            if (r4 >= r5) goto L_0x0038
            return r3
        L_0x0038:
            int r5 = com.igexin.push.config.C1234k.f1840a
            if (r4 < r5) goto L_0x003f
            if (r4 >= r0) goto L_0x003f
            return r3
        L_0x003f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.util.C1576a.m3200a(long):boolean");
    }

    /* renamed from: a */
    public static boolean m3201a(Context context, Class cls) {
        if (context == null) {
            try {
                Log.e(f3008a, "context can not set null ");
                return false;
            } catch (Throwable th) {
                C1179b.m1354a(f3008a + "|" + th.toString());
                return false;
            }
        } else {
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(new Intent(context, cls), 0);
            if (queryIntentActivities != null) {
                if (queryIntentActivities.size() > 0) {
                    if (packageManager.getActivityInfo(new ComponentName(context.getPackageName(), cls.getName()), 128).theme == 16973840) {
                        return true;
                    }
                    String str = f3008a;
                    Log.e(str, cls.getName() + " need set theme Theme.Translucent.NoTitleBar");
                    return false;
                }
            }
            String str2 = f3008a;
            Log.e(str2, "not regist " + cls.getName() + "in manifest");
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m3202a(Context context, String str) {
        try {
            return context.getPackageManager().getLaunchIntentForPackage(str) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m3203a(Intent intent, Context context) {
        if (intent == null || context == null) {
            return false;
        }
        try {
            List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
            return queryIntentServices != null && queryIntentServices.size() > 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m3204a(ServiceInfo serviceInfo, PackageInfo packageInfo) {
        return C1275b.f1914r.equals(serviceInfo.name) || C1275b.f1913q.equals(serviceInfo.name) || C1275b.f1915s.equals(serviceInfo.name);
    }

    /* renamed from: a */
    private static boolean m3205a(Class<?> cls, Method method, String str) {
        try {
            return method.invoke(cls, new Object[]{str}) != null;
        } catch (Exception unused) {
            return true;
        }
    }

    /* renamed from: a */
    public static boolean m3206a(String str) {
        try {
            if (!TextUtils.isEmpty(C1234k.f1829K)) {
                if (!SchedulerSupport.NONE.equals(C1234k.f1829K)) {
                    List<String> asList = Arrays.asList(C1234k.f1829K.split(","));
                    if (asList.isEmpty()) {
                        return false;
                    }
                    for (String startsWith : asList) {
                        if (str.startsWith(startsWith)) {
                            return true;
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    /* renamed from: a */
    private static boolean m3207a(String str, Context context) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static <T extends Service> boolean m3208a(String str, Context context, Class<T> cls) {
        if (cls == null) {
            try {
                if (!m3214b(new Intent(context, Class.forName(C1275b.f1913q)), context)) {
                    Log.e(str, "call - > initialize, parameter [userServiceName] is null use default PushService, but didn't find class \"com.igexin.sdk.PushService\", please check your AndroidManifest");
                    return false;
                }
            } catch (Throwable th) {
                C1179b.m1354a(f3008a + "|" + th.toString());
                return false;
            }
        }
        if (cls != null && C1275b.f1913q.equals(cls.getName()) && !m3214b(new Intent(context, cls), context)) {
            Log.e(str, "call - > initialize, parameter [userServiceName] is default PushService, but didn't find class \"com.igexin.sdk.PushService\", please check your AndroidManifest");
            return false;
        } else if (cls != null && !m3214b(new Intent(context, cls), context)) {
            Log.e(str, "call - > initialize, parameter [userServiceName] is set, but didn't find class \"" + cls.getName() + "\", please check your AndroidManifest");
            return false;
        } else if (cls == null) {
            return true;
        } else {
            Class.forName(cls.getName());
            return true;
        }
    }

    /* renamed from: a */
    public static boolean m3209a(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(str2)) {
                    Intent intent = new Intent();
                    intent.setClassName(str, str2);
                    if (C1343f.f2169f.getPackageManager().resolveActivity(intent, 0) != null) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m3210a(String str, List<String> list) {
        boolean z;
        if (list != null && !list.isEmpty()) {
            for (String next : list) {
                try {
                    if (!TextUtils.isEmpty(next)) {
                        if (!next.contains(Constraint.ANY_ROLE)) {
                            if (next.equals(str)) {
                                return true;
                            }
                        } else if (next.indexOf(Constraint.ANY_ROLE) != next.length() - 1) {
                            String[] split = str.split("\\.");
                            String[] split2 = next.split("\\.");
                            if (split.length >= split2.length) {
                                int i = 0;
                                while (true) {
                                    if (i < split2.length) {
                                        if (!split2[i].equals(Constraint.ANY_ROLE) && !split2[i].equals(split[i])) {
                                            z = false;
                                            break;
                                        }
                                        i++;
                                    } else {
                                        z = true;
                                        break;
                                    }
                                }
                                if (z) {
                                    return true;
                                }
                            }
                        } else if (str.startsWith(next.replace(Constraint.ANY_ROLE, ""))) {
                            return true;
                        }
                    }
                } catch (Throwable unused) {
                }
            }
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m3211a(JSONObject jSONObject) {
        String str;
        String str2;
        String str3;
        String string;
        String str4 = "buttons";
        String str5 = "type";
        String str6 = "actionid";
        try {
            HashMap hashMap = new HashMap();
            JSONArray jSONArray = jSONObject.getJSONArray("action_chains");
            int i = 0;
            while (i < jSONArray.length()) {
                JSONObject jSONObject2 = (JSONObject) jSONArray.get(i);
                C1280c cVar = new C1280c();
                if (jSONObject2.has(str6)) {
                    cVar.mo14555a(jSONObject2.getString(str6));
                    if (hashMap.containsKey(cVar.mo14554a())) {
                        return true;
                    }
                    ArrayList arrayList = new ArrayList();
                    if (jSONObject2.has(str5)) {
                        String string2 = jSONObject2.getString(str5);
                        str3 = str5;
                        str2 = str6;
                        if (AgooConstants.MESSAGE_POPUP.equals(string2)) {
                            if (jSONObject2.has(str4)) {
                                JSONArray jSONArray2 = jSONObject2.getJSONArray(str4);
                                str = str4;
                                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                    if (((JSONObject) jSONArray2.get(i2)).has("do")) {
                                        arrayList.add(((JSONObject) jSONArray2.get(i2)).getString("do"));
                                    }
                                }
                            } else {
                                str = str4;
                            }
                            if (jSONObject2.has("do")) {
                                string = jSONObject2.getString("do");
                            }
                            cVar.mo14556a((List<String>) arrayList);
                            hashMap.put(cVar.mo14554a(), cVar);
                            i++;
                            str5 = str3;
                            str6 = str2;
                            str4 = str;
                        } else {
                            str = str4;
                            if ("startapp".equals(string2)) {
                                if (jSONObject2.has("noinstall_action")) {
                                    arrayList.add(jSONObject2.getString("noinstall_action"));
                                }
                                if (jSONObject2.has("do")) {
                                    string = jSONObject2.getString("do");
                                }
                                cVar.mo14556a((List<String>) arrayList);
                                hashMap.put(cVar.mo14554a(), cVar);
                                i++;
                                str5 = str3;
                                str6 = str2;
                                str4 = str;
                            } else if ("checkapp".equals(string2)) {
                                if (jSONObject2.has("do_installed")) {
                                    arrayList.add(jSONObject2.getString("do_installed"));
                                }
                                if (jSONObject2.has("do_uninstalled")) {
                                    string = jSONObject2.getString("do_uninstalled");
                                }
                                cVar.mo14556a((List<String>) arrayList);
                                hashMap.put(cVar.mo14554a(), cVar);
                                i++;
                                str5 = str3;
                                str6 = str2;
                                str4 = str;
                            } else if ("checkversions".equals(string2)) {
                                if (jSONObject2.has("do_match")) {
                                    arrayList.add(jSONObject2.getString("do_match"));
                                }
                                if (jSONObject2.has("do_dismatch")) {
                                    arrayList.add(jSONObject2.getString("do_dismatch"));
                                }
                                if (jSONObject2.has("do")) {
                                    string = jSONObject2.getString("do");
                                }
                                cVar.mo14556a((List<String>) arrayList);
                                hashMap.put(cVar.mo14554a(), cVar);
                                i++;
                                str5 = str3;
                                str6 = str2;
                                str4 = str;
                            } else if ("startintent".equals(string2)) {
                                if (jSONObject2.has("do_failed")) {
                                    arrayList.add(jSONObject2.getString("do_failed"));
                                }
                                if (jSONObject2.has("do")) {
                                    string = jSONObject2.getString("do");
                                }
                                cVar.mo14556a((List<String>) arrayList);
                                hashMap.put(cVar.mo14554a(), cVar);
                                i++;
                                str5 = str3;
                                str6 = str2;
                                str4 = str;
                            } else {
                                if (!"null".equals(string2) && jSONObject2.has("do")) {
                                    string = jSONObject2.getString("do");
                                }
                                cVar.mo14556a((List<String>) arrayList);
                                hashMap.put(cVar.mo14554a(), cVar);
                                i++;
                                str5 = str3;
                                str6 = str2;
                                str4 = str;
                            }
                        }
                        arrayList.add(string);
                        cVar.mo14556a((List<String>) arrayList);
                        hashMap.put(cVar.mo14554a(), cVar);
                        i++;
                        str5 = str3;
                        str6 = str2;
                        str4 = str;
                    }
                }
                str = str4;
                str3 = str5;
                str2 = str6;
                i++;
                str5 = str3;
                str6 = str2;
                str4 = str;
            }
            ArrayList<C1280c> arrayList2 = new ArrayList<>(hashMap.values());
            for (Map.Entry value : hashMap.entrySet()) {
                List<String> b = ((C1280c) value.getValue()).mo14557b();
                if (b != null) {
                    for (String str7 : b) {
                        C1280c cVar2 = (C1280c) hashMap.get(str7);
                        if (cVar2 != null) {
                            cVar2.mo14559d();
                            if (arrayList2.contains(cVar2)) {
                                arrayList2.remove(cVar2);
                            }
                        }
                    }
                }
            }
            for (C1280c a : arrayList2) {
                m3198a((Map<String, C1280c>) hashMap, a.mo14554a());
            }
            if (hashMap.size() <= 0) {
                return false;
            }
            C1179b.m1354a(f3008a + "|action_chains have loop nodeMap not empty");
            return true;
        } catch (Throwable th) {
            C1179b.m1354a(f3008a + "|isHaveLoop exception :" + th.toString());
            return false;
        }
    }

    /* renamed from: b */
    public static boolean m3212b() {
        return System.currentTimeMillis() > C1234k.f1842c;
    }

    /* renamed from: b */
    public static boolean m3213b(Context context) {
        if (context == null) {
            return false;
        }
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getRunningAppProcesses();
            if (runningAppProcesses != null && !runningAppProcesses.isEmpty()) {
                String packageName = context.getPackageName();
                for (ActivityManager.RunningAppProcessInfo next : runningAppProcesses) {
                    if (packageName.equals(next.processName) && next.importance == 100) {
                        return true;
                    }
                }
            }
        } catch (Throwable th) {
            C1179b.m1354a(f3008a + "|" + th.toString());
        }
        return false;
    }

    /* renamed from: b */
    public static boolean m3214b(Intent intent, Context context) {
        if (!(intent == null || context == null)) {
            try {
                List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
                return queryIntentServices != null && queryIntentServices.size() > 0;
            } catch (Throwable th) {
                C1179b.m1354a(f3008a + "|" + th.toString());
            }
        }
        return false;
    }

    /* renamed from: b */
    public static boolean m3215b(String str) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(C1234k.f1831M)) {
                if (!SchedulerSupport.NONE.equals(C1234k.f1831M)) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.addAll(Arrays.asList(C1234k.f1831M.split(",")));
                    if (arrayList.isEmpty()) {
                        return false;
                    }
                    for (String contains : arrayList) {
                        if (str.contains(contains)) {
                            return true;
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    /* renamed from: c */
    public static boolean m3216c() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) C1343f.f2169f.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }

    /* renamed from: c */
    public static boolean m3217c(Intent intent, Context context) {
        if (intent == null || context == null) {
            return false;
        }
        try {
            List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            return queryIntentActivities != null && queryIntentActivities.size() > 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: d */
    public static void m3218d() {
        NetworkInfo.State state = ((ConnectivityManager) C1343f.f2169f.getSystemService("connectivity")).getNetworkInfo(1).getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            C1343f.f2179p = 1;
        } else {
            C1343f.f2179p = 0;
        }
    }

    /* renamed from: e */
    public static void m3219e() {
        C1343f.f2180q = ((PowerManager) C1343f.f2169f.getSystemService("power")).isScreenOn() ? 1 : 0;
    }

    /* renamed from: f */
    public static boolean m3220f() {
        try {
            for (String lowerCase : C1234k.f1828J.split(",")) {
                if (Build.MODEL.toLowerCase().contains(lowerCase.toLowerCase())) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return true;
        }
    }

    /* renamed from: g */
    public static boolean m3221g() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) C1343f.f2169f.getSystemService("connectivity");
            if (connectivityManager == null) {
                C1179b.m1354a(f3008a + "|ConnectivityManager is null");
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            C1179b.m1354a(f3008a + "|activeNetworkInfo = " + activeNetworkInfo);
            if (activeNetworkInfo == null || activeNetworkInfo.getState() != NetworkInfo.State.CONNECTED) {
                C1179b.m1354a(f3008a + "|network available = false");
                return false;
            }
            String str = activeNetworkInfo.getType() == 0 ? "mobile" : activeNetworkInfo.getType() == 1 ? UtilityImpl.NET_TYPE_WIFI : SchedulerSupport.NONE;
            C1179b.m1354a(f3008a + str + "|connected");
            return true;
        } catch (Throwable th) {
            C1179b.m1354a(f3008a + "|network available ex =" + th.toString());
        }
    }

    /* renamed from: h */
    public static boolean m3222h() {
        return System.currentTimeMillis() >= 1182566108138L;
    }

    /* renamed from: i */
    public static boolean m3223i() {
        String str = C1234k.f1839U;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            for (String str2 : str.split(",")) {
                if (str2.contains("|")) {
                    if (str2.contains("~")) {
                        String substring = str2.substring(0, str2.indexOf("|"));
                        String[] split = str2.substring(str2.indexOf("|") + 1).split("~");
                        if (split.length == 2) {
                            int parseInt = Integer.parseInt(split[0]);
                            int parseInt2 = Integer.parseInt(split[1]);
                            if (Build.BRAND.equalsIgnoreCase(substring) && Build.VERSION.SDK_INT >= parseInt && Build.VERSION.SDK_INT <= parseInt2) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }
}
