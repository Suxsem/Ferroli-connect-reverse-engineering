package com.taobao.accs.net;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import com.taobao.accs.ServiceReceiver;
import com.taobao.accs.internal.AccsJobService;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.OrangeAdapter;

/* renamed from: com.taobao.accs.net.f */
/* compiled from: Taobao */
public abstract class C2053f {

    /* renamed from: b */
    protected static volatile C2053f f3396b;

    /* renamed from: c */
    private static final int[] f3397c = {270, 360, 480};

    /* renamed from: a */
    protected Context f3398a;

    /* renamed from: d */
    private int f3399d;

    /* renamed from: e */
    private long f3400e;

    /* renamed from: f */
    private boolean f3401f = false;

    /* renamed from: g */
    private int[] f3402g = {0, 0, 0};

    /* renamed from: h */
    private boolean f3403h = true;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo18467a(int i);

    protected C2053f(Context context) {
        try {
            this.f3398a = context;
            this.f3399d = 0;
            this.f3400e = System.currentTimeMillis();
            this.f3403h = OrangeAdapter.isSmartHb();
        } catch (Throwable th) {
            ALog.m3726e("HeartbeatManager", "HeartbeatManager", th, new Object[0]);
        }
    }

    /* renamed from: a */
    public static C2053f m3619a(Context context) {
        if (f3396b == null) {
            synchronized (C2053f.class) {
                if (f3396b == null) {
                    if (Build.VERSION.SDK_INT >= 21 && m3620b(context)) {
                        ALog.m3728i("HeartbeatManager", "hb use job", new Object[0]);
                        f3396b = new C2068t(context);
                    } else if (m3621c(context)) {
                        ALog.m3728i("HeartbeatManager", "hb use alarm", new Object[0]);
                        f3396b = new C2047a(context);
                    } else {
                        ALog.m3728i("HeartbeatManager", "hb use thread", new Object[0]);
                        f3396b = new C2069u(context);
                    }
                }
            }
        }
        return f3396b;
    }

    /* renamed from: b */
    private static boolean m3620b(Context context) {
        return context.getPackageManager().getComponentEnabledSetting(new ComponentName(context.getPackageName(), AccsJobService.class.getName())) == 1;
    }

    /* renamed from: c */
    private static boolean m3621c(Context context) {
        return context.getPackageManager().getComponentEnabledSetting(new ComponentName(context.getPackageName(), ServiceReceiver.class.getName())) == 1;
    }

    /* renamed from: a */
    public synchronized void mo18501a() {
        try {
            if (this.f3400e < 0) {
                this.f3400e = System.currentTimeMillis();
            }
            int b = mo18502b();
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.m3725d("HeartbeatManager", "set " + b, new Object[0]);
            }
            mo18467a(b);
        } catch (Throwable th) {
            ALog.m3726e("HeartbeatManager", "set", th, new Object[0]);
        }
        return;
    }

    /* renamed from: b */
    public int mo18502b() {
        int i = this.f3403h ? f3397c[this.f3399d] : 270;
        this.f3403h = OrangeAdapter.isSmartHb();
        return i;
    }

    /* renamed from: c */
    public void mo18503c() {
        this.f3400e = -1;
        if (this.f3401f) {
            int[] iArr = this.f3402g;
            int i = this.f3399d;
            iArr[i] = iArr[i] + 1;
        }
        int i2 = this.f3399d;
        this.f3399d = i2 > 0 ? i2 - 1 : 0;
        ALog.m3725d("HeartbeatManager", "onNetworkTimeout", new Object[0]);
    }

    /* renamed from: d */
    public void mo18504d() {
        this.f3400e = -1;
        ALog.m3725d("HeartbeatManager", "onNetworkFail", new Object[0]);
    }

    /* renamed from: e */
    public void mo18505e() {
        ALog.m3725d("HeartbeatManager", "onHeartbeatSucc", new Object[0]);
        if (System.currentTimeMillis() - this.f3400e > 7199000) {
            int i = this.f3399d;
            if (i < f3397c.length - 1 && this.f3402g[i] <= 2) {
                ALog.m3725d("HeartbeatManager", "upgrade", new Object[0]);
                this.f3399d++;
                this.f3401f = true;
                this.f3400e = System.currentTimeMillis();
                return;
            }
            return;
        }
        this.f3401f = false;
        this.f3402g[this.f3399d] = 0;
    }

    /* renamed from: f */
    public void mo18506f() {
        this.f3399d = 0;
        this.f3400e = System.currentTimeMillis();
        ALog.m3725d("HeartbeatManager", "resetLevel", new Object[0]);
    }
}
