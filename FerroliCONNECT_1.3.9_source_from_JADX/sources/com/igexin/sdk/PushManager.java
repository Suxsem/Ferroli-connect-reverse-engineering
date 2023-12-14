package com.igexin.sdk;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.p000v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1346i;
import com.igexin.push.core.C1356s;
import com.igexin.push.util.C1576a;
import com.igexin.push.util.C1581f;
import com.igexin.push.util.C1593r;
import com.igexin.sdk.p091a.C1600c;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.android.agoo.common.AgooConstants;

public class PushManager {

    /* renamed from: j */
    private static String[] f3044j = {"DE", "FR", "IT", "ES", "PL", "RO", "NL", "BE", "GR", "CZ", AssistPushConsts.MSG_VALUE_PAYLOAD, "SE", "HU", "AT", "BG", "DK", "FI", "SK", "IE", "HR", "LT", "SI", "LV", "EE", "CY", "LU", "MT"};

    /* renamed from: a */
    private long f3045a;

    /* renamed from: b */
    private long f3046b;

    /* renamed from: c */
    private long f3047c;

    /* renamed from: d */
    private byte[] f3048d;

    /* renamed from: e */
    private Class f3049e;

    /* renamed from: f */
    private String f3050f;

    /* renamed from: g */
    private String f3051g;

    /* renamed from: h */
    private int f3052h;

    /* renamed from: i */
    private C1346i f3053i;

    private PushManager() {
        this.f3052h = -1;
    }

    /* renamed from: a */
    private Class m3281a(Context context) {
        Class cls = this.f3049e;
        return cls != null ? cls : C1356s.m2138a().mo14786c(context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String m3282a(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "MD5"
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0)     // Catch:{ Exception -> 0x003a }
            byte[] r6 = r6.getBytes()     // Catch:{ Exception -> 0x003a }
            r0.update(r6)     // Catch:{ Exception -> 0x003a }
            byte[] r6 = r0.digest()     // Catch:{ Exception -> 0x003a }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003a }
            java.lang.String r1 = ""
            r0.<init>(r1)     // Catch:{ Exception -> 0x003a }
            int r1 = r6.length     // Catch:{ Exception -> 0x003a }
            r2 = 0
        L_0x001a:
            if (r2 >= r1) goto L_0x0035
            byte r3 = r6[r2]     // Catch:{ Exception -> 0x003a }
            if (r3 >= 0) goto L_0x0022
            int r3 = r3 + 256
        L_0x0022:
            r4 = 16
            if (r3 >= r4) goto L_0x002b
            java.lang.String r4 = "0"
            r0.append(r4)     // Catch:{ Exception -> 0x003a }
        L_0x002b:
            java.lang.String r3 = java.lang.Integer.toHexString(r3)     // Catch:{ Exception -> 0x003a }
            r0.append(r3)     // Catch:{ Exception -> 0x003a }
            int r2 = r2 + 1
            goto L_0x001a
        L_0x0035:
            java.lang.String r6 = r0.toString()     // Catch:{ Exception -> 0x003a }
            return r6
        L_0x003a:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.sdk.PushManager.m3282a(java.lang.String):java.lang.String");
    }

    /* renamed from: a */
    private boolean m3283a() {
        try {
            String country = Locale.getDefault().getCountry();
            for (String equalsIgnoreCase : f3044j) {
                if (country.equalsIgnoreCase(equalsIgnoreCase)) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    /* renamed from: a */
    private boolean m3284a(Context context, Intent intent) {
        if (m3283a()) {
            Log.e("PushManager", "EU countries not support !!!");
            return false;
        } else if (C1593r.m3269b(context) || this.f3052h != -1) {
            return C1356s.m2138a().mo14782a(context, intent);
        } else {
            return false;
        }
    }

    /* renamed from: b */
    private void m3285b(Context context) {
        if (((Boolean) C1593r.m3270c(context, "pri_alert", false)).booleanValue() && this.f3053i == null && Build.VERSION.SDK_INT >= 14) {
            try {
                Application d = m3287d(context);
                if (d != null) {
                    synchronized (this) {
                        if (this.f3053i == null) {
                            this.f3053i = new C1346i();
                            d.registerActivityLifecycleCallbacks(this.f3053i);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: c */
    private void m3286c(Context context) {
        if (this.f3053i != null && Build.VERSION.SDK_INT >= 14) {
            try {
                Application d = m3287d(context);
                if (d != null) {
                    d.unregisterActivityLifecycleCallbacks(this.f3053i);
                    this.f3053i = null;
                }
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: d */
    private Application m3287d(Context context) {
        if (context == null || !m3288e(context)) {
            return null;
        }
        return context instanceof Application ? (Application) context : (Application) context.getApplicationContext();
    }

    /* renamed from: e */
    private boolean m3288e(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        try {
            int myPid = Process.myPid();
            String str = "";
            ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
            if (!(activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null)) {
                if (!runningAppProcesses.isEmpty()) {
                    Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            ActivityManager.RunningAppProcessInfo next = it.next();
                            if (next != null && next.pid == myPid) {
                                str = next.processName;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    return str.equals(context.getPackageName());
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static PushManager getInstance() {
        return C1607g.f3070a;
    }

    private <T extends Activity> void registerPushActivity(Context context, Class<T> cls) {
        String str;
        if (m3283a()) {
            Log.e("PushManager", "EU countries not support !!!");
            return;
        }
        if (cls != null) {
            try {
                Class.forName(cls.getName());
                if (C1576a.m3201a(context, (Class) cls)) {
                    str = cls.getName();
                } else {
                    return;
                }
            } catch (Exception e) {
                Log.e("PushManager", "can't load activity = " + e.toString());
                C1179b.m1354a("PushManager|registerPushActiviy|" + e.toString());
                return;
            } catch (Throwable th) {
                C1179b.m1354a("PushManager|registerPushActiviy|" + th.toString());
                return;
            }
        } else {
            Log.d("PushManager", "call -> registerPushActiviy, parameter [activity] is null");
            str = "";
        }
        this.f3051g = str;
        if (this.f3049e != null) {
            Intent intent = new Intent(context.getApplicationContext(), this.f3049e);
            intent.putExtra("ua", this.f3051g);
            if (this.f3052h != -1) {
                intent.putExtra("pri_authorized", this.f3052h == 0);
            }
            m3284a(context, intent);
        }
    }

    public boolean bindAlias(Context context, String str) {
        return bindAlias(context, str, "bindAlias_" + System.currentTimeMillis());
    }

    public boolean bindAlias(Context context, String str, String str2) {
        C1179b.m1354a("PushManager|call bindAlias");
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f3047c < 5000) {
            Log.e("PushManager", "call - > bindAlias failed, it be called too frequently");
            return false;
        }
        this.f3047c = currentTimeMillis;
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "bindAlias");
        bundle.putString("alias", str);
        bundle.putString("sn", str2);
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        return m3284a(context, intent);
    }

    public String getClientid(Context context) {
        if (this.f3048d == null) {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                    String string = applicationInfo.metaData.getString(AssistPushConsts.GETUI_APPID);
                    String string2 = applicationInfo.metaData.getString(AssistPushConsts.GETUI_APPSECRET);
                    String string3 = applicationInfo.metaData.getString(AssistPushConsts.GETUI_APPKEY);
                    if (string != null) {
                        string = string.trim();
                    }
                    if (string2 != null) {
                        string2 = string2.trim();
                    }
                    if (string3 != null) {
                        string3 = string3.trim();
                    }
                    if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                        String a = m3282a(string + string2 + string3 + context.getPackageName());
                        if (a != null) {
                            this.f3048d = a.getBytes();
                        }
                    }
                }
            } catch (Exception e) {
                C1179b.m1354a("PushManager|" + e.toString());
            }
        }
        if (this.f3048d == null) {
            return null;
        }
        byte[] a2 = C1581f.m3234a(context.getFilesDir().getPath() + "/" + "init.pid");
        byte[] bArr = this.f3048d;
        if (bArr == null || a2 == null || bArr.length != a2.length) {
            return null;
        }
        byte[] bArr2 = new byte[a2.length];
        for (int i = 0; i < bArr2.length; i++) {
            bArr2[i] = (byte) (this.f3048d[i] ^ a2[i]);
        }
        return new String(bArr2);
    }

    public String getVersion(Context context) {
        return PushBuildConfig.sdk_conf_version;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003a, code lost:
        if (r0 != false) goto L_0x003c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T extends android.app.Service> void initialize(android.content.Context r5, java.lang.Class<T> r6) {
        /*
            r4 = this;
            java.lang.String r0 = "PushManager"
            if (r5 != 0) goto L_0x000a
            java.lang.String r5 = "context is null"
        L_0x0006:
            android.util.Log.e(r0, r5)
            return
        L_0x000a:
            boolean r1 = r4.m3283a()
            if (r1 == 0) goto L_0x0013
            java.lang.String r5 = "EU countries not support !!!"
            goto L_0x0006
        L_0x0013:
            android.content.Context r1 = r5.getApplicationContext()     // Catch:{ Throwable -> 0x008c }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ Throwable -> 0x008c }
            boolean r0 = com.igexin.push.util.C1576a.m3208a((java.lang.String) r0, (android.content.Context) r5, r6)     // Catch:{ Throwable -> 0x008c }
            if (r0 != 0) goto L_0x0027
            java.lang.String r5 = "PushManager|init checkServiceSetCorrectly false"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)     // Catch:{ Throwable -> 0x008c }
            return
        L_0x0027:
            android.content.Context r0 = r5.getApplicationContext()     // Catch:{ Throwable -> 0x008c }
            com.igexin.push.util.C1576a.m3197a((android.content.Context) r0)     // Catch:{ Throwable -> 0x008c }
            if (r6 == 0) goto L_0x003c
            java.lang.String r0 = com.igexin.push.core.C1275b.f1913q     // Catch:{ Throwable -> 0x008c }
            java.lang.String r2 = r6.getName()     // Catch:{ Throwable -> 0x008c }
            boolean r0 = r0.equals(r2)     // Catch:{ Throwable -> 0x008c }
            if (r0 == 0) goto L_0x003e
        L_0x003c:
            java.lang.Class<com.igexin.sdk.PushService> r6 = com.igexin.sdk.PushService.class
        L_0x003e:
            r4.f3049e = r6     // Catch:{ Throwable -> 0x008c }
            android.content.Intent r0 = new android.content.Intent     // Catch:{ Throwable -> 0x008c }
            android.content.Context r2 = r5.getApplicationContext()     // Catch:{ Throwable -> 0x008c }
            r0.<init>(r2, r6)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r2 = "action"
            java.lang.String r3 = com.igexin.sdk.PushConsts.ACTION_SERVICE_INITIALIZE     // Catch:{ Throwable -> 0x008c }
            r0.putExtra(r2, r3)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r2 = "op_app"
            r0.putExtra(r2, r1)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r1 = "us"
            java.lang.String r6 = r6.getName()     // Catch:{ Throwable -> 0x008c }
            r0.putExtra(r1, r6)     // Catch:{ Throwable -> 0x008c }
            int r6 = r4.f3052h     // Catch:{ Throwable -> 0x008c }
            r1 = -1
            if (r6 == r1) goto L_0x006f
            java.lang.String r6 = "pri_authorized"
            int r1 = r4.f3052h     // Catch:{ Throwable -> 0x008c }
            if (r1 != 0) goto L_0x006b
            r1 = 1
            goto L_0x006c
        L_0x006b:
            r1 = 0
        L_0x006c:
            r0.putExtra(r6, r1)     // Catch:{ Throwable -> 0x008c }
        L_0x006f:
            java.lang.String r6 = r4.f3050f     // Catch:{ Throwable -> 0x008c }
            if (r6 == 0) goto L_0x007a
            java.lang.String r6 = "uis"
            java.lang.String r1 = r4.f3050f     // Catch:{ Throwable -> 0x008c }
            r0.putExtra(r6, r1)     // Catch:{ Throwable -> 0x008c }
        L_0x007a:
            java.lang.String r6 = r4.f3051g     // Catch:{ Throwable -> 0x008c }
            if (r6 == 0) goto L_0x0085
            java.lang.String r6 = "ua"
            java.lang.String r1 = r4.f3051g     // Catch:{ Throwable -> 0x008c }
            r0.putExtra(r6, r1)     // Catch:{ Throwable -> 0x008c }
        L_0x0085:
            r4.m3284a(r5, r0)     // Catch:{ Throwable -> 0x008c }
            r4.m3285b(r5)     // Catch:{ Throwable -> 0x008c }
            goto L_0x00a5
        L_0x008c:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "PushManager|initialize|"
            r6.append(r0)
            java.lang.String r5 = r5.toString()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)
        L_0x00a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.sdk.PushManager.initialize(android.content.Context, java.lang.Class):void");
    }

    public boolean isPushTurnedOn(Context context) {
        return new C1600c(context).mo15295c();
    }

    public <T extends GTIntentService> void registerPushIntentService(Context context, Class<T> cls) {
        String str;
        if (m3283a()) {
            Log.e("PushManager", "EU countries not support !!!");
            return;
        }
        C1179b.m1354a("PushManager|call registerPushIntentService");
        if (cls != null) {
            try {
                Class.forName(cls.getName());
                if (!C1576a.m3214b(new Intent(context, cls), context)) {
                    Log.e("PushManager", "call - > registerPushIntentService, parameter [userIntentService] is set, but didn't find class \"" + cls.getName() + "\", please check your AndroidManifest");
                    return;
                }
                str = cls.getName();
            } catch (Exception e) {
                Log.e("PushManager", "can't load IntentService = " + e.toString());
                C1179b.m1354a("PushManager|registerPushIntentService|" + e.toString());
                return;
            } catch (Throwable th) {
                C1179b.m1354a("PushManager|registerPushIntentService|" + th.toString());
                return;
            }
        } else {
            Log.d("PushManager", "call -> registerPushIntentService, parameter [userIntentService] is null, use default Receiver");
            str = "";
        }
        this.f3050f = str;
        if (this.f3049e != null) {
            Intent intent = new Intent(context.getApplicationContext(), this.f3049e);
            intent.putExtra("uis", this.f3050f);
            if (this.f3052h != -1) {
                intent.putExtra("pri_authorized", this.f3052h == 0);
            }
            m3284a(context, intent);
        }
    }

    public boolean sendApplinkFeedback(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("PushManager", "call - > sendApplinkFeedback failed, parameter is illegal");
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "sendApplinkFeedback");
        bundle.putString("url", str);
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        return m3284a(context, intent);
    }

    public boolean sendFeedbackMessage(Context context, String str, String str2, int i) {
        if (str == null || str2 == null || i < 90001 || i > 90999) {
            Log.e("PushManager", "call - > sendFeedbackMessage failed, parameter is illegal");
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "sendFeedbackMessage");
        bundle.putString("taskid", str);
        bundle.putString("messageid", str2);
        bundle.putString("actionid", String.valueOf(i));
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        return m3284a(context, intent);
    }

    public boolean sendMessage(Context context, String str, byte[] bArr) {
        long currentTimeMillis = System.currentTimeMillis();
        if (str == null || bArr == null || ((long) bArr.length) > PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM || currentTimeMillis - this.f3046b < 1000) {
            Log.e("PushManager", "call - > sendMessage failed, parameter is illegal or it be called too frequently");
            return false;
        }
        this.f3046b = currentTimeMillis;
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "sendMessage");
        bundle.putString("taskid", str);
        bundle.putByteArray("extraData", bArr);
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        return m3284a(context, intent);
    }

    public boolean setHeartbeatInterval(Context context, int i) {
        if (i < 0) {
            Log.e("PushManager", "call -> setHeartbeatInterval failed, parameter [interval] < 0, illegal");
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "setHeartbeatInterval");
        bundle.putInt("interval", i);
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        return m3284a(context, intent);
    }

    public void setPrivacyPolicyStrategy(Context context, boolean z) {
        this.f3052h = z ^ true ? 1 : 0;
        Class cls = this.f3049e;
        if (cls != null) {
            initialize(context, cls);
        }
        C1593r.m3266a(context, "pri_alert", true);
        C1593r.m3266a(context, "pri_authorized", Boolean.valueOf(z));
    }

    public boolean setSilentTime(Context context, int i, int i2) {
        if (i < 0 || i >= 24 || i2 < 0 || i2 > 23) {
            Log.e("PushManager", "call - > setSilentTime failed, parameter [beginHour] or [duration] value exceeding");
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "setSilentTime");
        bundle.putInt("beginHour", i);
        bundle.putInt("duration", i2);
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        return m3284a(context, intent);
    }

    public boolean setSocketTimeout(Context context, int i) {
        if (i < 0) {
            Log.e("PushManager", "call - > setSocketTimeout failed, parameter [timeout] < 0, illegal");
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "setSocketTimeout");
        bundle.putInt("timeout", i);
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        return m3284a(context, intent);
    }

    public int setTag(Context context, Tag[] tagArr, String str) {
        if (tagArr == null) {
            Log.e("PushManager", "call -> setTag failed, parameter [tags] is null");
            C1179b.m1354a("PushManager|tags is null");
            return PushConsts.SETTAG_ERROR_NULL;
        } else if (str == null) {
            Log.e("PushManager", "call -> setTag failed, parameter [sn] is null");
            C1179b.m1354a("PushManager|sn is null");
            return PushConsts.SETTAG_SN_NULL;
        } else if (((long) tagArr.length) > 200) {
            Log.e("PushManager", "call -> setTag failed, parameter [tags] len > 200 is exceeds");
            C1179b.m1354a("PushManager|tags len > 200 is exceeds");
            return PushConsts.SETTAG_ERROR_COUNT;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.f3045a < 1000) {
                Log.e("PushManager", "call - > setTag failed, it be called too frequently");
                return PushConsts.SETTAG_ERROR_FREQUENCY;
            }
            StringBuilder sb = new StringBuilder();
            for (Tag tag : tagArr) {
                if (!(tag == null || tag.getName() == null)) {
                    if (tag.getName().contains(" ") || tag.getName().contains(",")) {
                        Log.e("PushManager", "call -> setTag failed, the tag [" + tag.getName() + "]" + " is not illegal");
                        return PushConsts.SETTAG_TAG_ILLEGAL;
                    }
                    sb.append(tag.getName());
                    sb.append(",");
                }
            }
            if (sb.length() <= 0) {
                return PushConsts.SETTAG_ERROR_NULL;
            }
            sb.deleteCharAt(sb.length() - 1);
            Bundle bundle = new Bundle();
            bundle.putString(PushConsts.CMD_ACTION, "setTag");
            bundle.putString("tags", sb.toString());
            bundle.putString("sn", str);
            this.f3045a = currentTimeMillis;
            Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
            intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
            intent.putExtra("bundle", bundle);
            m3284a(context, intent);
            return 0;
        }
    }

    public void turnOffPush(Context context) {
        if (m3283a()) {
            Log.e("PushManager", "EU countries not support !!!");
            return;
        }
        C1179b.m1354a("PushManager|call turnOffPush");
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "turnOffPush");
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        m3284a(context, intent);
        m3286c(context);
    }

    public void turnOnPush(Context context) {
        if (m3283a()) {
            Log.e("PushManager", "EU countries not support !!!");
            return;
        }
        C1179b.m1354a("PushManager|call turnOnPush");
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_SERVICE_INITIALIZE_SLAVE);
        intent.putExtra("op_app", context.getApplicationContext().getPackageName());
        intent.putExtra("isSlave", true);
        m3284a(context, intent);
        m3285b(context);
    }

    public boolean unBindAlias(Context context, String str, boolean z) {
        return unBindAlias(context, str, z, "unBindAlias_" + System.currentTimeMillis());
    }

    public boolean unBindAlias(Context context, String str, boolean z, String str2) {
        C1179b.m1354a("PushManager|call unBindAlias");
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f3047c < 5000) {
            Log.e("PushManager", "call - > unBindAlias failed, it be called too frequently");
            return false;
        }
        this.f3047c = currentTimeMillis;
        Bundle bundle = new Bundle();
        bundle.putString(PushConsts.CMD_ACTION, "unbindAlias");
        bundle.putString("alias", str);
        bundle.putBoolean("isSeft", z);
        bundle.putString("sn", str2);
        Intent intent = new Intent(context.getApplicationContext(), m3281a(context));
        intent.putExtra(PushConsts.CMD_ACTION, PushConsts.ACTION_BROADCAST_PUSHMANAGER);
        intent.putExtra("bundle", bundle);
        return m3284a(context, intent);
    }
}
