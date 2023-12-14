package anet.channel;

import android.content.Intent;
import anet.channel.util.ALog;
import java.util.Iterator;

/* renamed from: anet.channel.a */
/* compiled from: Taobao */
class C0475a implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Intent f149a;

    /* renamed from: b */
    final /* synthetic */ AccsSessionManager f150b;

    C0475a(AccsSessionManager accsSessionManager, Intent intent) {
        this.f150b = accsSessionManager;
        this.f149a = intent;
    }

    public void run() {
        Iterator it = AccsSessionManager.f38c.iterator();
        while (it.hasNext()) {
            try {
                ((ISessionListener) it.next()).onConnectionChanged(this.f149a);
            } catch (Exception e) {
                ALog.m326e("awcn.AccsSessionManager", "notifyListener exception.", (String) null, e, new Object[0]);
            }
        }
    }
}
