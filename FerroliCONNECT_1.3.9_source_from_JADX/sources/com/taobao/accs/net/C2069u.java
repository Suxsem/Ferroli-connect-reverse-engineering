package com.taobao.accs.net;

import android.content.Context;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* renamed from: com.taobao.accs.net.u */
/* compiled from: Taobao */
public class C2069u extends C2053f {

    /* renamed from: c */
    private ScheduledFuture f3446c;

    protected C2069u(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo18467a(int i) {
        ScheduledFuture scheduledFuture = this.f3446c;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.f3446c = null;
        }
        long j = (long) i;
        this.f3446c = ThreadPoolExecutorFactory.getScheduledExecutor().scheduleAtFixedRate(new C2070v(this), j, j, TimeUnit.SECONDS);
    }
}
