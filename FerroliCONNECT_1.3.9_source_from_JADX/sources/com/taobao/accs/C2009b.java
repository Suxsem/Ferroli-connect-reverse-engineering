package com.taobao.accs;

import android.app.Notification;
import com.taobao.accs.ChannelService;
import com.taobao.accs.utl.ALog;

/* renamed from: com.taobao.accs.b */
/* compiled from: Taobao */
class C2009b implements Runnable {

    /* renamed from: a */
    final /* synthetic */ ChannelService.KernelService f3219a;

    C2009b(ChannelService.KernelService kernelService) {
        this.f3219a = kernelService;
    }

    public void run() {
        try {
            ChannelService instance = ChannelService.getInstance();
            int i = this.f3219a.f3209b.getPackageManager().getPackageInfo(this.f3219a.getPackageName(), 0).applicationInfo.icon;
            if (i != 0) {
                Notification.Builder builder = new Notification.Builder(this.f3219a.f3209b);
                builder.setSmallIcon(i);
                instance.startForeground(9371, builder.build());
                Notification.Builder builder2 = new Notification.Builder(this.f3219a.f3209b);
                builder2.setSmallIcon(i);
                ChannelService.KernelService.f3208a.startForeground(9371, builder2.build());
                ChannelService.KernelService.f3208a.stopForeground(true);
            }
            ChannelService.KernelService.f3208a.stopSelf();
        } catch (Throwable th) {
            ALog.m3726e("ChannelService", " onStartCommand run", th, new Object[0]);
        }
    }
}
