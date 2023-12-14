package com.gyf.barlibrary;

import android.support.p000v4.app.Fragment;

@Deprecated
public abstract class ImmersionFragment extends Fragment {
    /* access modifiers changed from: protected */
    @Deprecated
    public boolean immersionEnabled() {
        return true;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public abstract void immersionInit();

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (z && isResumed()) {
            onResume();
        }
    }

    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && immersionEnabled()) {
            immersionInit();
        }
    }
}
