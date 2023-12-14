package anet.channel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import anet.channel.util.ALog;
import com.taobao.accs.messenger.MessengerService;

/* renamed from: anet.channel.h */
/* compiled from: Taobao */
class C0525h implements ServiceConnection {

    /* renamed from: a */
    final /* synthetic */ Intent f267a;

    /* renamed from: b */
    final /* synthetic */ Context f268b;

    /* renamed from: c */
    final /* synthetic */ SessionRequest f269c;

    C0525h(SessionRequest sessionRequest, Intent intent, Context context) {
        this.f269c = sessionRequest;
        this.f267a = intent;
        this.f268b = context;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        ALog.m325d("awcn.SessionRequest", "onServiceConnected", (String) null, new Object[0]);
        try {
            Messenger messenger = new Messenger(iBinder);
            Message message = new Message();
            message.getData().putParcelable(MessengerService.INTENT, this.f267a);
            messenger.send(message);
        } catch (Exception e) {
            ALog.m326e("awcn.SessionRequest", "onServiceConnected sendMessage error.", (String) null, e, new Object[0]);
        } catch (Throwable th) {
            this.f268b.unbindService(this);
            throw th;
        }
        this.f268b.unbindService(this);
    }

    public void onServiceDisconnected(ComponentName componentName) {
        ALog.m325d("awcn.SessionRequest", "onServiceDisconnected", (String) null, new Object[0]);
        this.f268b.unbindService(this);
    }
}
