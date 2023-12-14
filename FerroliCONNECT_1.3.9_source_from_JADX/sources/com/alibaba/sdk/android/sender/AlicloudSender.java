package com.alibaba.sdk.android.sender;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.sdk.android.logger.ILog;
import com.alibaba.sdk.android.tbrest.SendService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.UTMini;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.android.agoo.common.AgooConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class AlicloudSender {

    /* renamed from: a */
    private static final String f1306a = null;

    /* renamed from: b */
    private static final ExecutorService f1307b = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    /* access modifiers changed from: private */

    /* renamed from: c */
    public static Map<String, SdkInfo> f1308c;

    /* renamed from: d */
    private static Map<String, C0872a> f1309d;

    /* renamed from: e */
    private static final AtomicBoolean f1310e = new AtomicBoolean(false);

    /* renamed from: f */
    private static final AtomicBoolean f1311f = new AtomicBoolean(false);

    /* renamed from: g */
    private static SendService f1312g;

    /* renamed from: h */
    private static final ILog f1313h = SenderLog.getLogger(AlicloudSender.class);

    /* renamed from: i */
    private static boolean f1314i = false;
    @SuppressLint({"SimpleDateFormat"})

    /* renamed from: j */
    private static final SimpleDateFormat f1315j = new SimpleDateFormat("yyyyMMdd");

    /* renamed from: com.alibaba.sdk.android.sender.AlicloudSender$a */
    private static class C0872a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public int f1319a;
        /* access modifiers changed from: private */

        /* renamed from: b */
        public String f1320b;
        /* access modifiers changed from: private */

        /* renamed from: c */
        public String f1321c;

        private C0872a() {
            this.f1319a = -1;
            this.f1320b = "";
            this.f1321c = "";
        }
    }

    /* renamed from: a */
    private static void m990a(Application application) {
        if (f1311f.compareAndSet(false, true) && Build.VERSION.SDK_INT >= 14) {
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                public void onActivityDestroyed(Activity activity) {
                }

                public void onActivityPaused(Activity activity) {
                }

                public void onActivityResumed(Activity activity) {
                    if (AlicloudSender.f1308c != null && !AlicloudSender.f1308c.isEmpty()) {
                        for (SdkInfo a : AlicloudSender.f1308c.values()) {
                            AlicloudSender.m996b(activity.getApplicationContext(), a);
                        }
                    }
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                public void onActivityStarted(Activity activity) {
                }

                public void onActivityStopped(Activity activity) {
                }
            });
        }
    }

    /* renamed from: a */
    private static void m991a(Context context) {
        if (f1310e.compareAndSet(false, true)) {
            f1308c = new ConcurrentHashMap();
            f1309d = m998c(context);
            f1312g = new SendService();
            f1312g.openHttp = Boolean.valueOf(f1314i);
            f1312g.init(context, "24527540@android", "24527540", m995b(context), (String) null, (String) null);
            f1312g.appSecret = "56fc10fbe8c6ae7d0d895f49c4fb6838";
        }
    }

    /* renamed from: a */
    private static void m994a(Context context, Map<String, C0872a> map) {
        SharedPreferences.Editor editor;
        if (map == null || map.isEmpty()) {
            editor = context.getSharedPreferences("sp_emas_info", 0).edit().remove("emas_sdk_info");
        } else {
            JSONArray jSONArray = new JSONArray();
            for (String next : map.keySet()) {
                C0872a aVar = map.get(next);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(AgooConstants.MESSAGE_ID, next);
                    jSONObject.put(Constants.SP_KEY_VERSION, aVar.f1321c);
                    jSONObject.put(AgooConstants.MESSAGE_TIME, aVar.f1320b);
                    jSONObject.put("statu", aVar.f1319a);
                    jSONArray.put(jSONObject);
                } catch (Exception unused) {
                }
            }
            editor = context.getSharedPreferences("sp_emas_info", 0).edit().putString("emas_sdk_info", jSONArray.toString());
        }
        editor.apply();
    }

    public static void asyncSend(Application application, SdkInfo sdkInfo) {
        if (application == null) {
            f1313h.mo9706d("asyncSend failed. application is null. ");
        } else if (sdkInfo == null) {
            f1313h.mo9706d("asyncSend failed. sdk info is null. ");
        } else {
            String a = sdkInfo.mo10107a();
            if (TextUtils.isEmpty(a)) {
                f1313h.mo9706d("asyncSend failed. sdk id is empty. ");
            } else if (TextUtils.isEmpty(sdkInfo.mo10108b())) {
                f1313h.mo9706d("asyncSend failed. sdk version is empty. ");
            } else {
                m991a(application.getApplicationContext());
                m990a(application);
                f1308c.put(a, sdkInfo);
                m996b(application.getApplicationContext(), sdkInfo);
            }
        }
    }

    @Deprecated
    public static void asyncSend(Context context, SdkInfo sdkInfo) {
        if (context == null) {
            f1313h.mo9706d("asyncSend failed. context is null. ");
        } else if (sdkInfo == null) {
            f1313h.mo9706d("asyncSend failed. sdk info is null. ");
        } else {
            String a = sdkInfo.mo10107a();
            if (TextUtils.isEmpty(a)) {
                f1313h.mo9706d("asyncSend failed. sdk id is empty. ");
            } else if (TextUtils.isEmpty(sdkInfo.mo10108b())) {
                f1313h.mo9706d("asyncSend failed. sdk version is empty. ");
            } else {
                m991a(context.getApplicationContext());
                f1308c.put(a, sdkInfo);
                m996b(context.getApplicationContext(), sdkInfo);
            }
        }
    }

    /* renamed from: b */
    private static String m995b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static void m996b(final Context context, final SdkInfo sdkInfo) {
        final String format = f1315j.format(new Date(System.currentTimeMillis()));
        try {
            C0872a aVar = f1309d.get(sdkInfo.mo10107a());
            if (aVar == null || !TextUtils.equals(format, aVar.f1320b) || !TextUtils.equals(sdkInfo.mo10108b(), aVar.f1321c) || aVar.f1319a != 0) {
                f1307b.execute(new Runnable() {
                    public void run() {
                        AlicloudSender.m997b(context, sdkInfo, format);
                    }
                });
                return;
            }
            ILog iLog = f1313h;
            iLog.mo9706d(sdkInfo.mo10107a() + " " + sdkInfo.mo10108b() + " send abort send. ");
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static void m997b(Context context, SdkInfo sdkInfo, String str) {
        C0872a aVar = f1309d.get(sdkInfo.mo10107a());
        if (aVar == null) {
            aVar = new C0872a();
            f1309d.put(sdkInfo.mo10107a(), aVar);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("sdkId", sdkInfo.mo10107a());
        hashMap.put(Constants.KEY_PACKAGE_NAME, context.getPackageName());
        hashMap.put(Constants.KEY_SDK_VERSION, sdkInfo.mo10108b());
        hashMap.put("kVersion", "1.1.4");
        if (!TextUtils.isEmpty(sdkInfo.mo10109c())) {
            hashMap.put(Constants.KEY_APP_KEY, sdkInfo.mo10109c());
        }
        if (sdkInfo.f1323a != null) {
            hashMap.putAll(sdkInfo.f1323a);
        }
        hashMap.put("_aliyun_biz_id", "emas-active");
        ILog iLog = f1313h;
        iLog.mo9706d(sdkInfo.mo10107a() + " " + sdkInfo.mo10108b() + " start send. ");
        SendService sendService = f1312g;
        long currentTimeMillis = System.currentTimeMillis();
        String str2 = f1306a;
        boolean booleanValue = sendService.sendRequest("adash-emas.cn-hangzhou.aliyuncs.com", currentTimeMillis, str2, UTMini.EVENTID_AGOO, sdkInfo.mo10107a() + "_biz_active", (Object) null, (Object) null, hashMap).booleanValue();
        ILog iLog2 = f1313h;
        StringBuilder sb = new StringBuilder();
        sb.append(sdkInfo.mo10107a());
        sb.append(" ");
        sb.append(sdkInfo.mo10108b());
        sb.append(" send ");
        sb.append(booleanValue ? "success. " : "failed. ");
        iLog2.mo9706d(sb.toString());
        String unused = aVar.f1320b = str;
        String unused2 = aVar.f1321c = sdkInfo.mo10108b();
        int unused3 = aVar.f1319a = booleanValue ? 0 : -1;
        m994a(context, f1309d);
    }

    /* renamed from: c */
    private static Map<String, C0872a> m998c(Context context) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        String string = context.getSharedPreferences("sp_emas_info", 0).getString("emas_sdk_info", "");
        if (!TextUtils.isEmpty(string)) {
            try {
                JSONArray jSONArray = new JSONArray(string);
                if (jSONArray.length() > 0) {
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        String string2 = jSONObject.getString(AgooConstants.MESSAGE_ID);
                        C0872a aVar = new C0872a();
                        String unused = aVar.f1320b = jSONObject.getString(AgooConstants.MESSAGE_TIME);
                        int unused2 = aVar.f1319a = jSONObject.getInt("statu");
                        String unused3 = aVar.f1321c = jSONObject.getString(Constants.SP_KEY_VERSION);
                        concurrentHashMap.put(string2, aVar);
                    }
                }
            } catch (Exception unused4) {
            }
        }
        return concurrentHashMap;
    }

    public static void openHttp() {
        f1314i = true;
    }
}
