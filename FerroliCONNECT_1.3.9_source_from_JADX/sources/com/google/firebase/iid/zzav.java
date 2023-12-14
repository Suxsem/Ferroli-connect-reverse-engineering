package com.google.firebase.iid;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.internal.firebase_messaging.zze;

final class zzav extends zze {
    private final /* synthetic */ zzas zzdj;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzav(zzas zzas, Looper looper) {
        super(looper);
        this.zzdj = zzas;
    }

    public final void handleMessage(Message message) {
        this.zzdj.zzb(message);
    }
}
