package com.google.firebase.iid;

import android.os.Bundle;
import com.taobao.accs.utl.BaseMonitor;

final class zzai extends zzal<Void> {
    zzai(int i, int i2, Bundle bundle) {
        super(i, 2, bundle);
    }

    /* access modifiers changed from: package-private */
    public final boolean zzab() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Bundle bundle) {
        if (bundle.getBoolean(BaseMonitor.COUNT_ACK, false)) {
            finish(null);
        } else {
            zza(new zzak(4, "Invalid response to one way request"));
        }
    }
}
