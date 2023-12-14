package com.taobao.accs.internal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.text.TextUtils;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.IChannelInit;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.data.C2033g;
import com.taobao.accs.data.Message;
import com.taobao.accs.messenger.MessengerService;
import com.taobao.accs.net.C2049b;
import com.taobao.accs.net.C2071w;
import com.taobao.accs.p103ut.p104a.C2078c;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.C2089f;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
public class ServiceImpl extends C2040d {

    /* renamed from: b */
    private Service f3337b = null;

    /* renamed from: c */
    private Context f3338c;

    /* renamed from: d */
    private long f3339d;

    /* renamed from: e */
    private String f3340e = "unknown";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public ServiceImpl(Service service) {
        super(service);
        this.f3337b = service;
        this.f3338c = service.getApplicationContext();
    }

    public void onCreate() {
        super.onCreate();
        m3558a();
    }

    /* renamed from: a */
    public int mo18449a(Intent intent) {
        String str;
        Bundle extras;
        int i = 2;
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.m3728i("ServiceImpl", "onHostStartCommand", MessengerService.INTENT, intent);
        }
        try {
            if (!(!ALog.isPrintLog(ALog.Level.D) || intent == null || (extras = intent.getExtras()) == null)) {
                for (String str2 : extras.keySet()) {
                    ALog.m3725d("ServiceImpl", "onHostStartCommand", "key", str2, " value", extras.get(str2));
                }
            }
            int c = C2089f.m3781c();
            if (c > 3) {
                try {
                    ALog.m3727e("ServiceImpl", "onHostStartCommand load SO fail 4 times, don't auto restart", new Object[0]);
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_SOFAIL, UtilityImpl.m3735a(c), 0.0d);
                } catch (Throwable th) {
                    th = th;
                }
            } else {
                i = 1;
            }
            if (intent == null) {
                str = null;
            } else {
                str = intent.getAction();
            }
            if (TextUtils.isEmpty(str)) {
                m3563b();
                m3562a(false, false);
                AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
                return i;
            }
            m3560a(intent, str);
            AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
            return i;
        } catch (Throwable th2) {
            th = th2;
            i = 1;
            try {
                ALog.m3726e("ServiceImpl", "onHostStartCommand", th, new Object[0]);
                AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
                return i;
            } catch (Throwable th3) {
                AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
                throw th3;
            }
        }
    }

    /* renamed from: a */
    private void m3559a(Context context) {
        List<String> m = UtilityImpl.m3771m(context);
        ArrayList arrayList = new ArrayList();
        if (m != null && m.size() > 0) {
            for (int i = 0; i < m.size(); i++) {
                try {
                    Class<?> cls = Class.forName(m.get(i));
                    if (IChannelInit.class.isAssignableFrom(cls)) {
                        ((IChannelInit) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0])).init(context);
                    } else {
                        arrayList.add(m.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
        if (arrayList.size() > 0) {
            UtilityImpl.m3744a(context, (List<String>) arrayList);
        }
    }

    /* renamed from: a */
    private void m3558a() {
        ALog.m3725d("ServiceImpl", "init start", new Object[0]);
        m3559a(this.f3338c);
        GlobalClientInfo.getInstance(this.f3338c);
        AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
        this.f3339d = System.currentTimeMillis();
        this.f3340e = UtilityImpl.m3763f(this.f3338c);
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.m3728i("ServiceImpl", "init", Constants.KEY_SDK_VERSION, Integer.valueOf(Constants.SDK_VERSION_CODE), "procStart", Integer.valueOf(AdapterGlobalClientInfo.mStartServiceTimes.intValue()));
        }
        UTMini.getInstance().commitEvent(66001, "START", UtilityImpl.m3762f(), "PROXY");
        long h = UtilityImpl.m3766h(this.f3338c);
        ALog.m3725d("ServiceImpl", "getServiceAliveTime", "aliveTime", Long.valueOf(h));
        if (h > 20000) {
            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_SERVICE_ALIVE, "", (double) (h / 1000));
        }
        UtilityImpl.m3743a(this.f3338c, Constants.SP_KEY_SERVICE_START, System.currentTimeMillis());
        UTMini.getInstance().commitEvent(66001, "NOTIFY", UtilityImpl.m3769k(this.f3338c));
    }

    /* renamed from: a */
    private void m3560a(Intent intent, String str) {
        ALog.m3725d("ServiceImpl", "handleAction", PushConsts.CMD_ACTION, str);
        try {
            m3563b();
            if (!TextUtils.equals(str, "android.intent.action.PACKAGE_REMOVED")) {
                if (TextUtils.equals(str, PushConsts.ACTION_BROADCAST_NETWORK_CHANGE)) {
                    String f = UtilityImpl.m3763f(this.f3338c);
                    boolean g = UtilityImpl.m3765g(this.f3338c);
                    ALog.m3728i("ServiceImpl", "network change:" + this.f3340e + " to " + f, new Object[0]);
                    if (g) {
                        this.f3340e = f;
                        m3565c();
                        m3562a(true, false);
                        UTMini.getInstance().commitEvent(66001, "CONNECTIVITY_CHANGE", f, UtilityImpl.m3762f(), "0");
                    }
                    if (f.equals("unknown")) {
                        m3565c();
                        this.f3340e = f;
                    }
                } else if (TextUtils.equals(str, PushConsts.ACTION_BROADCAST_TO_BOOT)) {
                    m3562a(true, false);
                } else if (TextUtils.equals(str, PushConsts.ACTION_BROADCAST_USER_PRESENT)) {
                    ALog.m3725d("ServiceImpl", "action android.intent.action.USER_PRESENT", new Object[0]);
                    m3562a(true, false);
                } else if (str.equals(Constants.ACTION_COMMAND)) {
                    m3564b(intent);
                }
            }
        } catch (Throwable th) {
            ALog.m3726e("ServiceImpl", "handleAction", th, new Object[0]);
        }
    }

    /* renamed from: b */
    private void m3564b(Intent intent) {
        Message message;
        Message.ReqType reqType;
        URL url;
        Message a;
        Intent intent2 = intent;
        int intExtra = intent2.getIntExtra("command", -1);
        ALog.m3728i("ServiceImpl", "handleCommand", "command", Integer.valueOf(intExtra));
        String stringExtra = intent2.getStringExtra(Constants.KEY_PACKAGE_NAME);
        String stringExtra2 = intent2.getStringExtra(Constants.KEY_SERVICE_ID);
        String stringExtra3 = intent2.getStringExtra(Constants.KEY_USER_ID);
        String stringExtra4 = intent2.getStringExtra(Constants.KEY_APP_KEY);
        String stringExtra5 = intent2.getStringExtra(Constants.KEY_CONFIG_TAG);
        String stringExtra6 = intent2.getStringExtra(Constants.KEY_TTID);
        intent2.getStringExtra("sid");
        intent2.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
        if (intExtra == 201) {
            m3561a(Message.m3460a(true, 0), true);
            m3566d();
        }
        if (intExtra > 0 && !TextUtils.isEmpty(stringExtra)) {
            C2049b a2 = m3569a(this.f3338c, stringExtra5, true);
            if (a2 != null) {
                a2.mo18469a();
                if (intExtra != 1) {
                    if (intExtra == 2) {
                        ALog.m3727e("ServiceImpl", "onHostStartCommand COMMAND_UNBIND_APP", new Object[0]);
                        if (a2.mo18491j().mo18382e(stringExtra)) {
                            Message a3 = Message.m3454a(a2, stringExtra);
                            ALog.m3728i("ServiceImpl", stringExtra + " isAppUnbinded", new Object[0]);
                            a2.mo18471a(a3, AccsErrorCode.SUCCESS);
                            return;
                        }
                    } else if (intExtra == 5) {
                        message = Message.m3458a(stringExtra, stringExtra2);
                    } else if (intExtra == 6) {
                        message = Message.m3467b(stringExtra, stringExtra2);
                    } else if (intExtra == 3) {
                        message = Message.m3469c(stringExtra, stringExtra3);
                    } else if (intExtra == 4) {
                        message = Message.m3456a(stringExtra);
                    } else if (intExtra == 100) {
                        byte[] byteArrayExtra = intent2.getByteArrayExtra(Constants.KEY_DATA);
                        String stringExtra7 = intent2.getStringExtra(Constants.KEY_DATA_ID);
                        String stringExtra8 = intent2.getStringExtra(Constants.KEY_TARGET);
                        String stringExtra9 = intent2.getStringExtra(Constants.KEY_BUSINESSID);
                        String stringExtra10 = intent2.getStringExtra(Constants.KEY_EXT_TAG);
                        try {
                            reqType = (Message.ReqType) intent2.getSerializableExtra(Constants.KEY_SEND_TYPE);
                        } catch (Exception unused) {
                            reqType = null;
                        }
                        if (byteArrayExtra != null) {
                            try {
                                url = new URL("https://" + ((C2071w) a2).mo18527r());
                            } catch (Exception unused2) {
                                url = null;
                            }
                            ACCSManager.AccsRequest accsRequest = new ACCSManager.AccsRequest(stringExtra3, stringExtra2, byteArrayExtra, stringExtra7, stringExtra8, url, stringExtra9);
                            accsRequest.setTag(stringExtra10);
                            if (reqType == null) {
                                a = Message.m3451a(a2, this.f3338c, stringExtra, accsRequest, false);
                            } else if (reqType == Message.ReqType.REQ) {
                                a = Message.m3452a(a2, this.f3338c, stringExtra, Constants.TARGET_SERVICE_PRE, accsRequest, false);
                            }
                            message = a;
                        }
                    } else if (intExtra == 106) {
                        intent2.setAction(Constants.ACTION_RECEIVE);
                        intent2.putExtra("command", -1);
                        C2033g.m3534a(this.f3338c, intent2);
                        return;
                    }
                    message = null;
                } else if (!stringExtra.equals(this.f3338c.getPackageName())) {
                    ALog.m3727e("ServiceImpl", "handleCommand bindapp pkg error", new Object[0]);
                    return;
                } else {
                    String str = stringExtra4;
                    String str2 = stringExtra6;
                    String str3 = stringExtra4;
                    message = Message.m3448a(this.f3338c, stringExtra5, str, intent2.getStringExtra("app_sercet"), stringExtra, str2, intent2.getStringExtra("appVersion"));
                    a2.f3373a = str2;
                    UtilityImpl.m3761e(this.f3338c, str3);
                    if (a2.mo18491j().mo18381d(stringExtra) && !intent2.getBooleanExtra(Constants.KEY_FOUCE_BIND, false)) {
                        ALog.m3728i("ServiceImpl", stringExtra + " isAppBinded", new Object[0]);
                        a2.mo18471a(message, AccsErrorCode.SUCCESS);
                        return;
                    }
                }
                if (message != null) {
                    ALog.m3725d("ServiceImpl", "try send message", new Object[0]);
                    if (message.mo18393e() != null) {
                        message.mo18393e().onSend();
                    }
                    a2.mo18482b(message, true);
                    return;
                }
                ALog.m3727e("ServiceImpl", "message is null", new Object[0]);
                a2.mo18471a(Message.m3457a(stringExtra, intExtra), AccsErrorCode.PARAMETER_ERROR);
                return;
            }
            ALog.m3727e("ServiceImpl", "no connection", Constants.KEY_CONFIG_TAG, stringExtra5, "command", Integer.valueOf(intExtra));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        ALog.m3727e("ServiceImpl", "Service onDestroy", new Object[0]);
        UtilityImpl.m3743a(this.f3338c, Constants.SP_KEY_SERVICE_END, System.currentTimeMillis());
        this.f3337b = null;
        this.f3338c = null;
        m3567e();
        Process.killProcess(Process.myPid());
    }

    /* renamed from: b */
    private synchronized void m3563b() {
        if (f3347a != null) {
            if (f3347a.size() != 0) {
                for (Map.Entry entry : f3347a.entrySet()) {
                    C2049b bVar = (C2049b) entry.getValue();
                    if (bVar == null) {
                        ALog.m3727e("ServiceImpl", "tryConnect connection null", "appkey", bVar.mo18490i());
                        return;
                    }
                    ALog.m3728i("ServiceImpl", "tryConnect", "appkey", bVar.mo18490i(), Constants.KEY_CONFIG_TAG, entry.getKey());
                    if (!bVar.mo18492k() || !TextUtils.isEmpty(bVar.f3381i.getAppSecret())) {
                        bVar.mo18469a();
                    } else {
                        ALog.m3727e("ServiceImpl", "tryConnect secret is null", new Object[0]);
                    }
                }
                return;
            }
        }
        ALog.m3731w("ServiceImpl", "tryConnect no connections", new Object[0]);
    }

    /* renamed from: a */
    private void m3561a(Message message, boolean z) {
        if (f3347a != null && f3347a.size() != 0) {
            for (Map.Entry value : f3347a.entrySet()) {
                ((C2049b) value.getValue()).mo18482b(message, z);
            }
        }
    }

    /* renamed from: a */
    private void m3562a(boolean z, boolean z2) {
        if (f3347a != null && f3347a.size() != 0) {
            for (Map.Entry value : f3347a.entrySet()) {
                C2049b bVar = (C2049b) value.getValue();
                bVar.mo18475a(z, z2);
                ALog.m3728i("ServiceImpl", "ping connection", "appkey", bVar.mo18490i());
            }
        }
    }

    /* renamed from: c */
    private void m3565c() {
        if (f3347a != null && f3347a.size() != 0) {
            for (Map.Entry value : f3347a.entrySet()) {
                ((C2049b) value.getValue()).mo18479b();
            }
        }
    }

    /* renamed from: d */
    private void m3566d() {
        if (f3347a != null && f3347a.size() != 0) {
            for (Map.Entry value : f3347a.entrySet()) {
                C2078c c = ((C2049b) value.getValue()).mo18483c();
                if (c != null) {
                    c.f3505h = this.f3339d;
                    c.mo18539a();
                }
            }
        }
    }

    /* renamed from: e */
    private void m3567e() {
        if (f3347a != null && f3347a.size() != 0) {
            for (Map.Entry value : f3347a.entrySet()) {
                ((C2049b) value.getValue()).mo18486e();
            }
        }
    }
}
