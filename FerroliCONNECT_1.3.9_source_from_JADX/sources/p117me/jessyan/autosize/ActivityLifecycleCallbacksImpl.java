package p117me.jessyan.autosize;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.p000v4.app.FragmentActivity;

/* renamed from: me.jessyan.autosize.ActivityLifecycleCallbacksImpl */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    private AutoAdaptStrategy mAutoAdaptStrategy;
    private FragmentLifecycleCallbacksImpl mFragmentLifecycleCallbacks;

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public ActivityLifecycleCallbacksImpl(AutoAdaptStrategy autoAdaptStrategy) {
        this.mFragmentLifecycleCallbacks = new FragmentLifecycleCallbacksImpl(autoAdaptStrategy);
        this.mAutoAdaptStrategy = autoAdaptStrategy;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (AutoSizeConfig.getInstance().isCustomFragment() && (activity instanceof FragmentActivity)) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(this.mFragmentLifecycleCallbacks, true);
        }
        AutoAdaptStrategy autoAdaptStrategy = this.mAutoAdaptStrategy;
        if (autoAdaptStrategy != null) {
            autoAdaptStrategy.applyAdapt(activity, activity);
        }
    }

    public void onActivityStarted(Activity activity) {
        AutoAdaptStrategy autoAdaptStrategy = this.mAutoAdaptStrategy;
        if (autoAdaptStrategy != null) {
            autoAdaptStrategy.applyAdapt(activity, activity);
        }
    }

    public void setAutoAdaptStrategy(AutoAdaptStrategy autoAdaptStrategy) {
        this.mAutoAdaptStrategy = autoAdaptStrategy;
        this.mFragmentLifecycleCallbacks.setAutoAdaptStrategy(autoAdaptStrategy);
    }
}
