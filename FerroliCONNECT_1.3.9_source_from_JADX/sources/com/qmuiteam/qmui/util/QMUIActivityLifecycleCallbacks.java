package com.qmuiteam.qmui.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public abstract class QMUIActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private final Activity mTargetActivity;

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    /* access modifiers changed from: protected */
    public abstract void onTargetActivityDestroyed();

    public QMUIActivityLifecycleCallbacks(Activity activity) {
        this.mTargetActivity = activity;
    }

    public void onActivityDestroyed(Activity activity) {
        Activity activity2 = this.mTargetActivity;
        if (activity == activity2) {
            activity2.getApplication().unregisterActivityLifecycleCallbacks(this);
            onTargetActivityDestroyed();
        }
    }
}
