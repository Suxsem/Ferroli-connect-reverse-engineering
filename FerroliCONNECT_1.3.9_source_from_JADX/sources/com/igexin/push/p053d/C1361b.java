package com.igexin.push.p053d;

import android.content.Intent;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.sdk.GActivity;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* renamed from: com.igexin.push.d.b */
class C1361b extends C1575h {

    /* renamed from: a */
    final /* synthetic */ C1360a f2229a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1361b(C1360a aVar, long j) {
        super(j);
        this.f2229a = aVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        boolean z = false;
        try {
            Intent intent = new Intent();
            intent.setClassName(this.f2229a.f2227a, GActivity.TAG);
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            C1343f.f2169f.startActivity(intent);
            z = true;
            C1179b.m1354a(this.f1613l + "|startActivity success pkg = " + this.f2229a.f2227a + " activityName = " + GActivity.TAG);
        } catch (Exception e) {
            C1179b.m1354a(this.f1613l + "|startActivity exception pkg = " + this.f2229a.f2227a + " activityName = " + GActivity.TAG + " " + e.toString());
        }
        if (!z && this.f2229a.f2228b != null) {
            this.f2229a.f2228b.mo14806a();
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
