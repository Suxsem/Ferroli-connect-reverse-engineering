package com.igexin.push.core;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.config.C1235l;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.util.C1578c;
import com.igexin.push.util.C1581f;
import com.igexin.push.util.C1593r;
import com.igexin.push.util.EncryptUtils;
import com.igexin.sdk.IPushCore;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushService;
import com.igexin.sdk.p091a.C1598a;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.igexin.push.core.s */
public class C1356s {

    /* renamed from: a */
    public static Context f2214a;

    /* renamed from: b */
    private IPushCore f2215b;

    /* renamed from: c */
    private final AtomicBoolean f2216c;

    /* renamed from: d */
    private ExecutorService f2217d;

    private C1356s() {
        this.f2216c = new AtomicBoolean(false);
        this.f2217d = Executors.newSingleThreadExecutor();
    }

    /* synthetic */ C1356s(C1357t tVar) {
        this();
    }

    /* renamed from: a */
    private int m2136a(Service service) {
        C1179b.m1354a("ServiceManager|start by system ####");
        if (m2141a((Context) service, false)) {
            C1179b.m1354a("ServiceManager|intent = null");
            if (!this.f2216c.getAndSet(true)) {
                m2139a(service, (Intent) null);
            }
            return 1;
        }
        C1179b.m1354a("ServiceManager|start by system, needLook = " + C1234k.f1859t + ", firstInit = true or (ss = 1 switchOn = false), stop");
        service.stopSelf();
        return 2;
    }

    /* renamed from: a */
    private int m2137a(Intent intent, int i, int i2) {
        if (this.f2215b == null) {
            return 1;
        }
        C1179b.m1354a("ServiceManager|inInit = true, call onServiceStartCommand...");
        return this.f2215b.onServiceStartCommand(intent, i, i2);
    }

    /* renamed from: a */
    public static C1356s m2138a() {
        return C1359v.f2226a;
    }

    /* renamed from: a */
    private void m2139a(Service service, Intent intent) {
        C1179b.m1354a("ServiceManager|startPushCore ++++");
        if (EncryptUtils.isLoadSuccess()) {
            C1598a.m3293a().mo15289a((Context) service);
            this.f2215b = C1598a.m3293a().mo15290b();
            IPushCore iPushCore = this.f2215b;
            if (iPushCore != null) {
                iPushCore.start(service);
                return;
            }
            return;
        }
        C1578c.m3224a(new C1357t(this, service), service);
    }

    /* renamed from: a */
    private boolean m2140a(Context context, Intent intent, int i) {
        this.f2217d.execute(new C1358u(this, context, intent, i));
        return true;
    }

    /* renamed from: a */
    private boolean m2141a(Context context, boolean z) {
        if (!C1593r.m3269b(context) || C1581f.m3233a(context)) {
            return false;
        }
        if (!z) {
            return true;
        }
        C1235l.m1626a(context);
        return C1234k.f1859t;
    }

    /* renamed from: b */
    private int m2142b(Service service, Intent intent, int i, int i2) {
        C1179b.m1354a("ServiceManager|start from initialize...");
        m2139a(service, intent);
        IPushCore iPushCore = this.f2215b;
        if (iPushCore != null) {
            return iPushCore.onServiceStartCommand(intent, i, i2);
        }
        return 1;
    }

    /* renamed from: b */
    private void m2143b(Intent intent) {
        try {
            if (!TextUtils.isEmpty(intent.getStringExtra("from"))) {
                Message obtain = Message.obtain();
                obtain.what = C1275b.f1907k;
                obtain.obj = intent;
                C1340e.m2032a().mo14702a(obtain);
            }
        } catch (Throwable th) {
            C1179b.m1354a("ServiceManager|" + th.toString());
        }
    }

    /* renamed from: c */
    private int m2144c(Service service, Intent intent, int i, int i2) {
        if (m2141a((Context) service, true)) {
            m2143b(intent);
            m2139a(service, intent);
            IPushCore iPushCore = this.f2215b;
            if (iPushCore != null) {
                return iPushCore.onServiceStartCommand(intent, i, i2);
            }
            return 2;
        }
        this.f2216c.set(false);
        C1179b.m1354a("ServiceManager|start by g, availabe = false|" + C1234k.f1859t);
        service.stopSelf();
        return 2;
    }

    /* renamed from: a */
    public int mo14778a(Service service, Intent intent, int i, int i2) {
        if (intent == null) {
            try {
                return m2136a(service);
            } catch (Throwable th) {
                C1179b.m1354a("ServiceManager|" + th.toString());
                return 2;
            }
        } else {
            C1593r.m3265a(service, intent);
            String stringExtra = intent.getStringExtra(PushConsts.CMD_ACTION);
            if (PushConsts.ACTION_SERVICE_INITIALIZE.equals(stringExtra)) {
                C1581f.m3237b((Context) service);
            }
            if (this.f2216c.get()) {
                return m2137a(intent, i, i2);
            }
            this.f2216c.set(true);
            return PushConsts.ACTION_SERVICE_INITIALIZE.equals(stringExtra) ? m2142b(service, intent, i, i2) : m2144c(service, intent, i, i2);
        }
    }

    /* renamed from: a */
    public IBinder mo14779a(Intent intent) {
        C1179b.m1354a("ServiceManager|onBind...");
        IPushCore iPushCore = this.f2215b;
        if (iPushCore != null) {
            return iPushCore.onServiceBind(intent);
        }
        return null;
    }

    /* renamed from: a */
    public void mo14780a(Activity activity) {
        Intent intent;
        try {
            Intent intent2 = activity.getIntent();
            intent = new Intent(activity, C1257f.m1711a().mo14471a((Context) activity));
            if (intent2 != null) {
                if (intent2.hasExtra(PushConsts.CMD_ACTION) && intent2.hasExtra("isSlave")) {
                    intent.putExtra(PushConsts.CMD_ACTION, intent2.getStringExtra(PushConsts.CMD_ACTION));
                    intent.putExtra("isSlave", intent2.getBooleanExtra("isSlave", false));
                    if (intent2.hasExtra("op_app")) {
                        intent.putExtra("op_app", intent2.getStringExtra("op_app"));
                    }
                    C1179b.m1354a("da action = " + intent2.getStringExtra(PushConsts.CMD_ACTION) + ", isSlave = " + intent2.getBooleanExtra("isSlave", false));
                }
            }
        } catch (Exception e) {
            C1179b.m1354a("ServiceManager|put extra exception" + e.toString());
        } catch (Throwable th) {
            try {
                C1179b.m1354a("ServiceManager" + th.toString());
            } catch (Throwable th2) {
                activity.finish();
                throw th2;
            }
        }
        mo14782a((Context) activity, intent);
        C1179b.m1354a("ServiceManager|start PushService from da");
        activity.finish();
    }

    /* renamed from: a */
    public void mo14781a(Context context) {
        f2214a = context.getApplicationContext();
    }

    /* renamed from: a */
    public boolean mo14782a(Context context, Intent intent) {
        return m2140a(context, intent, 1902141359);
    }

    /* renamed from: b */
    public void mo14783b() {
        C1179b.m1354a("ServiceManager|onLowMemory...");
    }

    /* renamed from: b */
    public boolean mo14784b(Context context) {
        try {
            String str = (String) C1593r.m3270c(context, "us", "");
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            Class.forName(str);
            return true;
        } catch (Exception e) {
            C1179b.m1354a("ServiceManager|" + e.toString());
            return false;
        }
    }

    /* renamed from: b */
    public boolean mo14785b(Context context, Intent intent) {
        return m2140a(context, intent, 1902131808);
    }

    /* renamed from: c */
    public Class mo14786c(Context context) {
        try {
            String str = (String) C1593r.m3270c(context, "us", "");
            return TextUtils.isEmpty(str) ? PushService.class : Class.forName(str);
        } catch (Throwable th) {
            C1179b.m1354a("ServiceManager|" + th.toString());
            return PushService.class;
        }
    }

    /* renamed from: c */
    public void mo14787c() {
        C1179b.m1354a("ServiceManager|onDestroy...");
        IPushCore iPushCore = this.f2215b;
        if (iPushCore != null) {
            iPushCore.onServiceDestroy();
        }
    }

    /* renamed from: d */
    public Class mo14788d(Context context) {
        try {
            String str = (String) C1593r.m3270c(context, "uis", "");
            if (!TextUtils.isEmpty(str)) {
                return Class.forName(str);
            }
            return null;
        } catch (Throwable th) {
            C1179b.m1354a("ServiceManager|" + th.toString());
            return null;
        }
    }

    /* renamed from: e */
    public String mo14789e(Context context) {
        return (String) C1593r.m3270c(context, "ua", "");
    }
}
