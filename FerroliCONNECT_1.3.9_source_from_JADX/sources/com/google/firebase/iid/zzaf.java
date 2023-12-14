package com.google.firebase.iid;

import android.os.Handler;
import android.os.Message;

final /* synthetic */ class zzaf implements Handler.Callback {
    private final zzac zzcj;

    zzaf(zzac zzac) {
        this.zzcj = zzac;
    }

    public final boolean handleMessage(Message message) {
        return this.zzcj.zza(message);
    }
}
