package com.taobao.accs.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/* renamed from: com.taobao.accs.messenger.d */
/* compiled from: Taobao */
public class C2044d implements ServiceConnection {

    /* renamed from: a */
    private Context f3357a;

    /* renamed from: b */
    private int f3358b = 1;

    /* renamed from: c */
    private Messenger f3359c;

    /* renamed from: d */
    private String f3360d;

    /* renamed from: e */
    private C2041a f3361e;

    /* renamed from: f */
    private long f3362f = System.currentTimeMillis();

    public C2044d(Context context, String str, C2041a aVar) {
        this.f3357a = context;
        this.f3360d = str;
        this.f3361e = aVar;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            this.f3357a.unbindService(this);
            this.f3358b = 0;
            return;
        }
        this.f3359c = new Messenger(iBinder);
        this.f3358b = 2;
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.f3361e.mo18454a(this.f3360d, this);
        this.f3358b = 0;
        this.f3359c = null;
    }

    /* renamed from: a */
    public void mo18458a(Intent intent) throws RemoteException {
        Message message = new Message();
        message.getData().putParcelable(MessengerService.INTENT, intent);
        this.f3359c.send(message);
    }

    /* renamed from: a */
    public boolean mo18459a() {
        return this.f3358b == 2;
    }

    /* renamed from: b */
    public boolean mo18460b() {
        int i = this.f3358b;
        return i == 1 || i == 2;
    }

    /* renamed from: c */
    public boolean mo18461c() {
        return this.f3358b == 1 && System.currentTimeMillis() - this.f3362f > 5000;
    }
}
