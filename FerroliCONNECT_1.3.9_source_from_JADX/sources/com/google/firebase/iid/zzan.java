package com.google.firebase.iid;

import android.os.Bundle;
import com.taobao.accs.common.Constants;

final class zzan extends zzal<Bundle> {
    zzan(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    /* access modifiers changed from: package-private */
    public final boolean zzab() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(Constants.KEY_DATA);
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        finish(bundle2);
    }
}
