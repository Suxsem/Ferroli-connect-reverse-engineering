package com.taobao.accs.internal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.base.IBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.net.C2049b;
import com.taobao.accs.net.C2071w;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.Utils;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.taobao.accs.internal.d */
/* compiled from: Taobao */
public abstract class C2040d implements IBaseService {

    /* renamed from: a */
    protected static ConcurrentHashMap<String, C2049b> f3347a = new ConcurrentHashMap<>(2);

    /* renamed from: b */
    private Context f3348b;

    /* renamed from: c */
    private Service f3349c = null;

    /* renamed from: a */
    public abstract int mo18449a(Intent intent);

    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public C2040d(Service service) {
        this.f3349c = service;
        this.f3348b = service.getApplicationContext();
    }

    public void onCreate() {
        ALog.m3728i("ElectionServiceImpl", "onCreate,", Constants.KEY_SDK_VERSION, Integer.valueOf(Constants.SDK_VERSION_CODE));
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return 2;
        }
        String action = intent.getAction();
        ALog.m3728i("ElectionServiceImpl", "onStartCommand begin", PushConsts.CMD_ACTION, action);
        if (TextUtils.equals(action, Constants.ACTION_START_SERVICE)) {
            m3571b(intent);
        } else if (TextUtils.isEmpty(action)) {
            m3570a(true);
        } else {
            m3570a(false);
        }
        return mo18449a(intent);
    }

    public void onDestroy() {
        ALog.m3727e("ElectionServiceImpl", "Service onDestroy", new Object[0]);
        this.f3348b = null;
        this.f3349c = null;
    }

    /* renamed from: b */
    private void m3571b(Intent intent) {
        Intent intent2 = intent;
        try {
            String stringExtra = intent2.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent2.getStringExtra(Constants.KEY_APP_KEY);
            String stringExtra3 = intent2.getStringExtra(Constants.KEY_TTID);
            String stringExtra4 = intent2.getStringExtra("app_sercet");
            String stringExtra5 = intent2.getStringExtra(Constants.KEY_CONFIG_TAG);
            int intExtra = intent2.getIntExtra(Constants.KEY_MODE, 0);
            ALog.m3728i("ElectionServiceImpl", "handleStartCommand", Constants.KEY_CONFIG_TAG, stringExtra5, "appkey", stringExtra2, "appSecret", stringExtra4, Constants.KEY_TTID, stringExtra3, "pkg", stringExtra);
            if (!TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2) && stringExtra.equals(this.f3348b.getPackageName())) {
                Utils.setMode(this.f3348b, intExtra);
                C2049b a = m3569a(this.f3348b, stringExtra5, false);
                if (a != null) {
                    a.f3373a = stringExtra3;
                } else {
                    ALog.m3727e("ElectionServiceImpl", "handleStartCommand start action, no connection", Constants.KEY_CONFIG_TAG, stringExtra5);
                }
                UtilityImpl.m3761e(this.f3348b, stringExtra2);
            }
        } catch (Throwable th) {
            ALog.m3726e("ElectionServiceImpl", "handleStartCommand", th, new Object[0]);
        }
    }

    /* renamed from: a */
    private void m3570a(boolean z) {
        for (String next : AccsClientConfig.tags()) {
            try {
                if (!AccsClientConfig.getConfigByTag(next).getDisableChannel()) {
                    m3569a(this.f3348b, next, z);
                }
            } catch (Throwable th) {
                ALog.m3730w("ElectionServiceImpl", "tryStartAllConnections " + next, th, new Object[0]);
            }
        }
    }

    /* renamed from: a */
    protected static C2049b m3569a(Context context, String str, boolean z) {
        C2071w wVar = null;
        try {
            if (TextUtils.isEmpty(str)) {
                ALog.m3731w("ElectionServiceImpl", "getConnection configTag null or env invalid", "conns.size", Integer.valueOf(f3347a.size()));
                if (f3347a.size() > 0) {
                    return f3347a.elements().nextElement();
                }
                return null;
            }
            ALog.m3728i("ElectionServiceImpl", "getConnection", Constants.KEY_CONFIG_TAG, str, "start", Boolean.valueOf(z));
            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
            if (configByTag == null || !configByTag.getDisableChannel()) {
                int mode = Utils.getMode(context);
                String str2 = str + "|" + mode;
                C2049b bVar = f3347a.get(str2);
                if (bVar != null) {
                    return bVar;
                }
                try {
                    AccsClientConfig.mEnv = mode;
                    wVar = new C2071w(context, 0, str);
                    if (z) {
                        wVar.mo18469a();
                    }
                    if (f3347a.size() < 10) {
                        f3347a.put(str2, wVar);
                        return wVar;
                    }
                    ALog.m3727e("ElectionServiceImpl", "getConnection fail as exist too many conns!!!", new Object[0]);
                    return wVar;
                } catch (Throwable th) {
                    th = th;
                    wVar = bVar;
                    ALog.m3726e("ElectionServiceImpl", "getConnection", th, new Object[0]);
                    return wVar;
                }
            } else {
                ALog.m3727e("ElectionServiceImpl", "getConnection channel disabled!", Constants.KEY_CONFIG_TAG, str);
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            ALog.m3726e("ElectionServiceImpl", "getConnection", th, new Object[0]);
            return wVar;
        }
    }
}
