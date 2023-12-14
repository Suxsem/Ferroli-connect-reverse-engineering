package com.taobao.accs.net;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.p000v4.app.NotificationCompat;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import java.util.Calendar;

/* renamed from: com.taobao.accs.net.a */
/* compiled from: Taobao */
public class C2047a extends C2053f {

    /* renamed from: c */
    private PendingIntent f3370c;

    /* renamed from: d */
    private AlarmManager f3371d;

    public C2047a(Context context) {
        super(context);
        try {
            this.f3371d = (AlarmManager) this.f3398a.getSystemService(NotificationCompat.CATEGORY_ALARM);
        } catch (Throwable th) {
            ALog.m3726e("AlarmHeartBeatMgr", "AlarmHeartBeatMgr", th, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18467a(int i) {
        if (this.f3371d == null) {
            this.f3371d = (AlarmManager) this.f3398a.getSystemService(NotificationCompat.CATEGORY_ALARM);
        }
        if (this.f3371d == null) {
            ALog.m3727e("AlarmHeartBeatMgr", "setInner null", new Object[0]);
            return;
        }
        if (this.f3370c == null) {
            Intent intent = new Intent();
            intent.setPackage(this.f3398a.getPackageName());
            intent.setAction(Constants.ACTION_COMMAND);
            intent.putExtra("command", 201);
            if (Build.VERSION.SDK_INT >= 23) {
                this.f3370c = PendingIntent.getBroadcast(this.f3398a, 0, intent, 67108864);
            } else {
                this.f3370c = PendingIntent.getBroadcast(this.f3398a, 0, intent, 0);
            }
        }
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        instance.add(13, i);
        this.f3371d.set(0, instance.getTimeInMillis(), this.f3370c);
    }
}
