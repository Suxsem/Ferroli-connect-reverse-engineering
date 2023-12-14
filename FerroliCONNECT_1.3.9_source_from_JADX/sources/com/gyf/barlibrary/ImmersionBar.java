package com.gyf.barlibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.database.ContentObserver;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.p000v4.app.DialogFragment;
import android.support.p000v4.app.Fragment;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.ColorUtils;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@TargetApi(19)
public class ImmersionBar {
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    private static Map<String, BarParams> mMap = new HashMap();
    private static Map<String, ArrayList<String>> mTagKeyMap = new HashMap();
    private static Map<String, BarParams> mTagMap = new HashMap();
    /* access modifiers changed from: private */
    public Activity mActivity;
    private String mActivityName;
    /* access modifiers changed from: private */
    public BarParams mBarParams;
    /* access modifiers changed from: private */
    public BarConfig mConfig;
    /* access modifiers changed from: private */
    public ViewGroup mContentView;
    private ViewGroup mDecorView;
    private Dialog mDialog;
    private String mFragmentName;
    private String mImmersionBarName;
    private Window mWindow;

    private ImmersionBar(Activity activity) {
        this.mActivity = (Activity) new WeakReference(activity).get();
        this.mWindow = this.mActivity.getWindow();
        this.mActivityName = activity.getClass().getName();
        this.mImmersionBarName = this.mActivityName;
        initParams();
    }

    private ImmersionBar(Fragment fragment) {
        this((Activity) fragment.getActivity(), fragment);
    }

    private ImmersionBar(Activity activity, Fragment fragment) {
        if (activity != null) {
            WeakReference weakReference = new WeakReference(activity);
            WeakReference weakReference2 = new WeakReference(fragment);
            this.mActivity = (Activity) weakReference.get();
            this.mWindow = this.mActivity.getWindow();
            this.mActivityName = this.mActivity.getClass().getName();
            this.mFragmentName = this.mActivityName + "_AND_" + ((Fragment) weakReference2.get()).getClass().getName();
            this.mImmersionBarName = this.mFragmentName;
            initParams();
            return;
        }
        throw new IllegalArgumentException("Activity不能为空!!!");
    }

    private ImmersionBar(DialogFragment dialogFragment, Dialog dialog) {
        WeakReference weakReference = new WeakReference(dialogFragment);
        WeakReference weakReference2 = new WeakReference(dialog);
        this.mActivity = ((DialogFragment) weakReference.get()).getActivity();
        this.mDialog = (Dialog) weakReference2.get();
        this.mWindow = this.mDialog.getWindow();
        this.mActivityName = this.mActivity.getClass().getName();
        this.mImmersionBarName = this.mActivityName + "_AND_" + ((DialogFragment) weakReference.get()).getClass().getName();
        initParams();
    }

    private ImmersionBar(Activity activity, Dialog dialog, String str) {
        WeakReference weakReference = new WeakReference(activity);
        WeakReference weakReference2 = new WeakReference(dialog);
        this.mActivity = (Activity) weakReference.get();
        this.mDialog = (Dialog) weakReference2.get();
        this.mWindow = this.mDialog.getWindow();
        this.mActivityName = this.mActivity.getClass().getName();
        this.mImmersionBarName = this.mActivityName + "_AND_" + str;
        initParams();
    }

    private void initParams() {
        this.mDecorView = (ViewGroup) this.mWindow.getDecorView();
        this.mContentView = (ViewGroup) this.mDecorView.findViewById(16908290);
        this.mConfig = new BarConfig(this.mActivity);
        if (mMap.get(this.mImmersionBarName) == null) {
            this.mBarParams = new BarParams();
            if (!isEmpty(this.mFragmentName)) {
                if (mMap.get(this.mActivityName) != null) {
                    if (Build.VERSION.SDK_INT == 19 || OSUtils.isEMUI3_1()) {
                        this.mBarParams.statusBarView = mMap.get(this.mActivityName).statusBarView;
                        this.mBarParams.navigationBarView = mMap.get(this.mActivityName).navigationBarView;
                    }
                    this.mBarParams.keyboardPatch = mMap.get(this.mActivityName).keyboardPatch;
                } else {
                    throw new IllegalArgumentException("在Fragment里使用时，请先在加载Fragment的Activity里初始化！！！");
                }
            }
            mMap.put(this.mImmersionBarName, this.mBarParams);
            return;
        }
        this.mBarParams = mMap.get(this.mImmersionBarName);
    }

    public static ImmersionBar with(@NonNull Activity activity) {
        if (activity != null) {
            return new ImmersionBar(activity);
        }
        throw new IllegalArgumentException("Activity不能为null");
    }

    public static ImmersionBar with(@NonNull Fragment fragment) {
        if (fragment != null) {
            return new ImmersionBar(fragment);
        }
        throw new IllegalArgumentException("Fragment不能为null");
    }

    public static ImmersionBar with(@NonNull Activity activity, @NonNull Fragment fragment) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity不能为null");
        } else if (fragment != null) {
            return new ImmersionBar(activity, fragment);
        } else {
            throw new IllegalArgumentException("Fragment不能为null");
        }
    }

    public static ImmersionBar with(@NonNull DialogFragment dialogFragment, @NonNull Dialog dialog) {
        if (dialogFragment == null) {
            throw new IllegalArgumentException("DialogFragment不能为null");
        } else if (dialog != null) {
            return new ImmersionBar(dialogFragment, dialog);
        } else {
            throw new IllegalArgumentException("Dialog不能为null");
        }
    }

    public static ImmersionBar with(@NonNull Activity activity, @NonNull Dialog dialog, @NonNull String str) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity不能为null");
        } else if (dialog == null) {
            throw new IllegalArgumentException("Dialog不能为null");
        } else if (!isEmpty(str)) {
            return new ImmersionBar(activity, dialog, str);
        } else {
            throw new IllegalArgumentException("tag不能为null或空");
        }
    }

    public ImmersionBar transparentStatusBar() {
        this.mBarParams.statusBarColor = 0;
        return this;
    }

    public ImmersionBar transparentNavigationBar() {
        BarParams barParams = this.mBarParams;
        barParams.navigationBarColor = 0;
        barParams.navigationBarColorTemp = barParams.navigationBarColor;
        this.mBarParams.fullScreen = true;
        return this;
    }

    public ImmersionBar transparentBar() {
        BarParams barParams = this.mBarParams;
        barParams.statusBarColor = 0;
        barParams.navigationBarColor = 0;
        barParams.navigationBarColorTemp = barParams.navigationBarColor;
        this.mBarParams.fullScreen = true;
        return this;
    }

    public ImmersionBar statusBarColor(@ColorRes int i) {
        return statusBarColorInt(ContextCompat.getColor(this.mActivity, i));
    }

    public ImmersionBar statusBarColor(@ColorRes int i, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return statusBarColorInt(ContextCompat.getColor(this.mActivity, i), f);
    }

    public ImmersionBar statusBarColor(@ColorRes int i, @ColorRes int i2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return statusBarColorInt(ContextCompat.getColor(this.mActivity, i), ContextCompat.getColor(this.mActivity, i2), f);
    }

    public ImmersionBar statusBarColor(String str) {
        return statusBarColorInt(Color.parseColor(str));
    }

    public ImmersionBar statusBarColor(String str, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return statusBarColorInt(Color.parseColor(str), f);
    }

    public ImmersionBar statusBarColor(String str, String str2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return statusBarColorInt(Color.parseColor(str), Color.parseColor(str2), f);
    }

    public ImmersionBar statusBarColorInt(@ColorInt int i) {
        this.mBarParams.statusBarColor = i;
        return this;
    }

    public ImmersionBar statusBarColorInt(@ColorInt int i, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.statusBarColor = i;
        barParams.statusBarAlpha = f;
        return this;
    }

    public ImmersionBar statusBarColorInt(@ColorInt int i, @ColorInt int i2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.statusBarColor = i;
        barParams.statusBarColorTransform = i2;
        barParams.statusBarAlpha = f;
        return this;
    }

    public ImmersionBar navigationBarColor(@ColorRes int i) {
        return navigationBarColorInt(ContextCompat.getColor(this.mActivity, i));
    }

    public ImmersionBar navigationBarColor(@ColorRes int i, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return navigationBarColorInt(ContextCompat.getColor(this.mActivity, i), f);
    }

    public ImmersionBar navigationBarColor(@ColorRes int i, @ColorRes int i2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return navigationBarColorInt(ContextCompat.getColor(this.mActivity, i), ContextCompat.getColor(this.mActivity, i2), f);
    }

    public ImmersionBar navigationBarColor(String str) {
        return navigationBarColorInt(Color.parseColor(str));
    }

    public ImmersionBar navigationBarColor(String str, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return navigationBarColorInt(Color.parseColor(str), f);
    }

    public ImmersionBar navigationBarColor(String str, String str2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return navigationBarColorInt(Color.parseColor(str), Color.parseColor(str2), f);
    }

    public ImmersionBar navigationBarColorInt(@ColorInt int i) {
        BarParams barParams = this.mBarParams;
        barParams.navigationBarColor = i;
        barParams.navigationBarColorTemp = barParams.navigationBarColor;
        return this;
    }

    public ImmersionBar navigationBarColorInt(@ColorInt int i, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.navigationBarColor = i;
        barParams.navigationBarAlpha = f;
        barParams.navigationBarColorTemp = barParams.navigationBarColor;
        return this;
    }

    public ImmersionBar navigationBarColorInt(@ColorInt int i, @ColorInt int i2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.navigationBarColor = i;
        barParams.navigationBarColorTransform = i2;
        barParams.navigationBarAlpha = f;
        barParams.navigationBarColorTemp = barParams.navigationBarColor;
        return this;
    }

    public ImmersionBar barColor(@ColorRes int i) {
        return barColorInt(ContextCompat.getColor(this.mActivity, i));
    }

    public ImmersionBar barColor(@ColorRes int i, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return barColorInt(ContextCompat.getColor(this.mActivity, i), (float) i);
    }

    public ImmersionBar barColor(@ColorRes int i, @ColorRes int i2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return barColorInt(ContextCompat.getColor(this.mActivity, i), ContextCompat.getColor(this.mActivity, i2), f);
    }

    public ImmersionBar barColor(String str) {
        return barColorInt(Color.parseColor(str));
    }

    public ImmersionBar barColor(String str, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return barColorInt(Color.parseColor(str), f);
    }

    public ImmersionBar barColor(String str, String str2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        return barColorInt(Color.parseColor(str), Color.parseColor(str2), f);
    }

    public ImmersionBar barColorInt(@ColorInt int i) {
        BarParams barParams = this.mBarParams;
        barParams.statusBarColor = i;
        barParams.navigationBarColor = i;
        barParams.navigationBarColorTemp = barParams.navigationBarColor;
        return this;
    }

    public ImmersionBar barColorInt(@ColorInt int i, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.statusBarColor = i;
        barParams.navigationBarColor = i;
        barParams.navigationBarColorTemp = barParams.navigationBarColor;
        BarParams barParams2 = this.mBarParams;
        barParams2.statusBarAlpha = f;
        barParams2.navigationBarAlpha = f;
        return this;
    }

    public ImmersionBar barColorInt(@ColorInt int i, @ColorInt int i2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.statusBarColor = i;
        barParams.navigationBarColor = i;
        barParams.navigationBarColorTemp = barParams.navigationBarColor;
        BarParams barParams2 = this.mBarParams;
        barParams2.statusBarColorTransform = i2;
        barParams2.navigationBarColorTransform = i2;
        barParams2.statusBarAlpha = f;
        barParams2.navigationBarAlpha = f;
        return this;
    }

    public ImmersionBar statusBarColorTransform(@ColorRes int i) {
        return statusBarColorTransformInt(ContextCompat.getColor(this.mActivity, i));
    }

    public ImmersionBar statusBarColorTransform(String str) {
        return statusBarColorTransformInt(Color.parseColor(str));
    }

    public ImmersionBar statusBarColorTransformInt(@ColorInt int i) {
        this.mBarParams.statusBarColorTransform = i;
        return this;
    }

    public ImmersionBar navigationBarColorTransform(@ColorRes int i) {
        return navigationBarColorTransformInt(ContextCompat.getColor(this.mActivity, i));
    }

    public ImmersionBar navigationBarColorTransform(String str) {
        return navigationBarColorTransformInt(Color.parseColor(str));
    }

    public ImmersionBar navigationBarColorTransformInt(@ColorInt int i) {
        this.mBarParams.navigationBarColorTransform = i;
        return this;
    }

    public ImmersionBar barColorTransform(@ColorRes int i) {
        return barColorTransformInt(ContextCompat.getColor(this.mActivity, i));
    }

    public ImmersionBar barColorTransform(String str) {
        return barColorTransformInt(Color.parseColor(str));
    }

    public ImmersionBar barColorTransformInt(@ColorInt int i) {
        BarParams barParams = this.mBarParams;
        barParams.statusBarColorTransform = i;
        barParams.navigationBarColorTransform = i;
        return this;
    }

    public ImmersionBar addViewSupportTransformColor(View view) {
        return addViewSupportTransformColorInt(view, this.mBarParams.statusBarColorTransform);
    }

    public ImmersionBar addViewSupportTransformColor(View view, @ColorRes int i) {
        return addViewSupportTransformColorInt(view, ContextCompat.getColor(this.mActivity, i));
    }

    public ImmersionBar addViewSupportTransformColor(View view, @ColorRes int i, @ColorRes int i2) {
        return addViewSupportTransformColorInt(view, ContextCompat.getColor(this.mActivity, i), ContextCompat.getColor(this.mActivity, i2));
    }

    public ImmersionBar addViewSupportTransformColor(View view, String str) {
        return addViewSupportTransformColorInt(view, Color.parseColor(str));
    }

    public ImmersionBar addViewSupportTransformColor(View view, String str, String str2) {
        return addViewSupportTransformColorInt(view, Color.parseColor(str), Color.parseColor(str2));
    }

    public ImmersionBar addViewSupportTransformColorInt(View view, @ColorInt int i) {
        if (view != null) {
            HashMap hashMap = new HashMap();
            hashMap.put(Integer.valueOf(this.mBarParams.statusBarColor), Integer.valueOf(i));
            this.mBarParams.viewMap.put(view, hashMap);
            return this;
        }
        throw new IllegalArgumentException("View参数不能为空");
    }

    public ImmersionBar addViewSupportTransformColorInt(View view, @ColorInt int i, @ColorInt int i2) {
        if (view != null) {
            HashMap hashMap = new HashMap();
            hashMap.put(Integer.valueOf(i), Integer.valueOf(i2));
            this.mBarParams.viewMap.put(view, hashMap);
            return this;
        }
        throw new IllegalArgumentException("View参数不能为空");
    }

    public ImmersionBar viewAlpha(@FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        this.mBarParams.viewAlpha = f;
        return this;
    }

    public ImmersionBar removeSupportView(View view) {
        if (view != null) {
            if (this.mBarParams.viewMap.get(view).size() != 0) {
                this.mBarParams.viewMap.remove(view);
            }
            return this;
        }
        throw new IllegalArgumentException("View参数不能为空");
    }

    public ImmersionBar removeSupportAllView() {
        if (this.mBarParams.viewMap.size() != 0) {
            this.mBarParams.viewMap.clear();
        }
        return this;
    }

    public ImmersionBar fullScreen(boolean z) {
        this.mBarParams.fullScreen = z;
        return this;
    }

    public ImmersionBar statusBarAlpha(@FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        this.mBarParams.statusBarAlpha = f;
        return this;
    }

    public ImmersionBar navigationBarAlpha(@FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        this.mBarParams.navigationBarAlpha = f;
        return this;
    }

    public ImmersionBar barAlpha(@FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.statusBarAlpha = f;
        barParams.navigationBarAlpha = f;
        return this;
    }

    public ImmersionBar statusBarDarkFont(boolean z) {
        return statusBarDarkFont(z, 0.0f);
    }

    public ImmersionBar statusBarDarkFont(boolean z, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.darkFont = z;
        if (!z) {
            barParams.flymeOSStatusBarFontColor = 0;
        }
        if (isSupportStatusBarDarkFont()) {
            this.mBarParams.statusBarAlpha = 0.0f;
        } else {
            this.mBarParams.statusBarAlpha = f;
        }
        return this;
    }

    public ImmersionBar flymeOSStatusBarFontColor(@ColorRes int i) {
        this.mBarParams.flymeOSStatusBarFontColor = ContextCompat.getColor(this.mActivity, i);
        return this;
    }

    public ImmersionBar flymeOSStatusBarFontColor(String str) {
        this.mBarParams.flymeOSStatusBarFontColor = Color.parseColor(str);
        return this;
    }

    public ImmersionBar flymeOSStatusBarFontColorInt(@ColorInt int i) {
        this.mBarParams.flymeOSStatusBarFontColor = i;
        return this;
    }

    public ImmersionBar hideBar(BarHide barHide) {
        this.mBarParams.barHide = barHide;
        if (Build.VERSION.SDK_INT == 19 || OSUtils.isEMUI3_1()) {
            if (this.mBarParams.barHide == BarHide.FLAG_HIDE_NAVIGATION_BAR || this.mBarParams.barHide == BarHide.FLAG_HIDE_BAR) {
                BarParams barParams = this.mBarParams;
                barParams.navigationBarColor = 0;
                barParams.fullScreenTemp = true;
            } else {
                BarParams barParams2 = this.mBarParams;
                barParams2.navigationBarColor = barParams2.navigationBarColorTemp;
                this.mBarParams.fullScreenTemp = false;
            }
        }
        return this;
    }

    public ImmersionBar fitsSystemWindows(boolean z) {
        this.mBarParams.fits = z;
        return this;
    }

    public ImmersionBar fitsSystemWindows(boolean z, @ColorRes int i) {
        return fitsSystemWindows(z, i, 17170444, 0.0f);
    }

    public ImmersionBar fitsSystemWindows(boolean z, @ColorRes int i, @ColorRes int i2, @FloatRange(from = 0.0d, mo101to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.fits = z;
        barParams.statusBarColorContentView = ContextCompat.getColor(this.mActivity, i);
        this.mBarParams.statusBarColorContentViewTransform = ContextCompat.getColor(this.mActivity, i2);
        BarParams barParams2 = this.mBarParams;
        barParams2.statusBarContentViewAlpha = f;
        barParams2.statusBarColorContentView = ContextCompat.getColor(this.mActivity, i);
        this.mContentView.setBackgroundColor(ColorUtils.blendARGB(this.mBarParams.statusBarColorContentView, this.mBarParams.statusBarColorContentViewTransform, this.mBarParams.statusBarContentViewAlpha));
        return this;
    }

    public ImmersionBar statusBarView(View view) {
        if (view != null) {
            this.mBarParams.statusBarViewByHeight = view;
            return this;
        }
        throw new IllegalArgumentException("View参数不能为空");
    }

    public ImmersionBar statusBarView(@IdRes int i) {
        View findViewById = this.mActivity.findViewById(i);
        if (findViewById != null) {
            return statusBarView(findViewById);
        }
        throw new IllegalArgumentException("未找到viewId");
    }

    public ImmersionBar statusBarView(@IdRes int i, View view) {
        View findViewById = view.findViewById(i);
        if (findViewById != null) {
            return statusBarView(findViewById);
        }
        throw new IllegalArgumentException("未找到viewId");
    }

    public ImmersionBar supportActionBar(boolean z) {
        this.mBarParams.isSupportActionBar = z;
        return this;
    }

    public ImmersionBar titleBar(View view) {
        if (view != null) {
            return titleBar(view, true);
        }
        throw new IllegalArgumentException("View参数不能为空");
    }

    public ImmersionBar titleBar(View view, boolean z) {
        if (view != null) {
            BarParams barParams = this.mBarParams;
            barParams.titleBarView = view;
            barParams.statusBarFlag = z;
            setTitleBar();
            return this;
        }
        throw new IllegalArgumentException("View参数不能为空");
    }

    public ImmersionBar titleBar(@IdRes int i) {
        View findViewById = this.mActivity.findViewById(i);
        if (findViewById != null) {
            return titleBar(findViewById, true);
        }
        throw new IllegalArgumentException("参数错误");
    }

    public ImmersionBar titleBar(@IdRes int i, boolean z) {
        View findViewById = this.mActivity.findViewById(i);
        if (findViewById != null) {
            return titleBar(findViewById, z);
        }
        throw new IllegalArgumentException("参数错误");
    }

    public ImmersionBar titleBar(@IdRes int i, View view) {
        View findViewById = view.findViewById(i);
        if (findViewById != null) {
            return titleBar(findViewById, true);
        }
        throw new IllegalArgumentException("参数错误");
    }

    public ImmersionBar titleBar(@IdRes int i, View view, boolean z) {
        View findViewById = view.findViewById(i);
        if (findViewById != null) {
            return titleBar(findViewById, z);
        }
        throw new IllegalArgumentException("参数错误");
    }

    public ImmersionBar titleBarMarginTop(@IdRes int i) {
        return titleBarMarginTop(this.mActivity.findViewById(i));
    }

    public ImmersionBar titleBarMarginTop(@IdRes int i, View view) {
        return titleBarMarginTop(view.findViewById(i));
    }

    public ImmersionBar titleBarMarginTop(View view) {
        if (view != null) {
            BarParams barParams = this.mBarParams;
            barParams.titleBarViewMarginTop = view;
            if (!barParams.titleBarViewMarginTopFlag) {
                setTitleBarMarginTop();
            }
            return this;
        }
        throw new IllegalArgumentException("参数错误");
    }

    public ImmersionBar statusBarColorTransformEnable(boolean z) {
        this.mBarParams.statusBarFlag = z;
        return this;
    }

    public ImmersionBar reset() {
        BarParams barParams = this.mBarParams;
        this.mBarParams = new BarParams();
        if (Build.VERSION.SDK_INT == 19 || OSUtils.isEMUI3_1()) {
            this.mBarParams.statusBarView = barParams.statusBarView;
            this.mBarParams.navigationBarView = barParams.navigationBarView;
        }
        this.mBarParams.keyboardPatch = barParams.keyboardPatch;
        mMap.put(this.mImmersionBarName, this.mBarParams);
        return this;
    }

    public ImmersionBar addTag(String str) {
        String str2 = this.mActivityName + "_TAG_" + str;
        if (!isEmpty(str2)) {
            mTagMap.put(str2, this.mBarParams.clone());
            ArrayList arrayList = mTagKeyMap.get(this.mActivityName);
            if (arrayList == null) {
                arrayList = new ArrayList();
                arrayList.add(str2);
            } else if (!arrayList.contains(str2)) {
                arrayList.add(str2);
            }
            mTagKeyMap.put(this.mActivityName, arrayList);
        }
        return this;
    }

    public ImmersionBar getTag(String str) {
        if (!isEmpty(str)) {
            Map<String, BarParams> map = mTagMap;
            BarParams barParams = map.get(this.mActivityName + "_TAG_" + str);
            if (barParams != null) {
                this.mBarParams = barParams.clone();
            }
        }
        return this;
    }

    public ImmersionBar keyboardEnable(boolean z) {
        return keyboardEnable(z, 18);
    }

    public ImmersionBar keyboardEnable(boolean z, int i) {
        BarParams barParams = this.mBarParams;
        barParams.keyboardEnable = z;
        barParams.keyboardMode = i;
        return this;
    }

    public ImmersionBar keyboardMode(int i) {
        this.mBarParams.keyboardMode = i;
        return this;
    }

    public ImmersionBar setOnKeyboardListener(OnKeyboardListener onKeyboardListener) {
        if (this.mBarParams.onKeyboardListener == null) {
            this.mBarParams.onKeyboardListener = onKeyboardListener;
        }
        return this;
    }

    public ImmersionBar navigationBarEnable(boolean z) {
        this.mBarParams.navigationBarEnable = z;
        return this;
    }

    public ImmersionBar navigationBarWithKitkatEnable(boolean z) {
        this.mBarParams.navigationBarWithKitkatEnable = z;
        return this;
    }

    @Deprecated
    public ImmersionBar fixMarginAtBottom(boolean z) {
        this.mBarParams.fixMarginAtBottom = z;
        return this;
    }

    public void init() {
        mMap.put(this.mImmersionBarName, this.mBarParams);
        initBar();
        setStatusBarView();
        transformView();
        keyboardEnable();
        registerEMUI3_x();
    }

    public void destroy() {
        unRegisterEMUI3_x();
        if (this.mBarParams.keyboardPatch != null) {
            this.mBarParams.keyboardPatch.disable(this.mBarParams.keyboardMode);
            this.mBarParams.keyboardPatch = null;
        }
        if (this.mDecorView != null) {
            this.mDecorView = null;
        }
        if (this.mContentView != null) {
            this.mContentView = null;
        }
        if (this.mConfig != null) {
            this.mConfig = null;
        }
        if (this.mWindow != null) {
            this.mWindow = null;
        }
        if (this.mDialog != null) {
            this.mDialog = null;
        }
        if (this.mActivity != null) {
            this.mActivity = null;
        }
        if (!isEmpty(this.mImmersionBarName)) {
            if (this.mBarParams != null) {
                this.mBarParams = null;
            }
            ArrayList arrayList = mTagKeyMap.get(this.mActivityName);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    mTagMap.remove((String) it.next());
                }
                mTagKeyMap.remove(this.mActivityName);
            }
            mMap.remove(this.mImmersionBarName);
        }
    }

    private void initBar() {
        if (Build.VERSION.SDK_INT >= 19) {
            int i = 256;
            if (Build.VERSION.SDK_INT < 21 || OSUtils.isEMUI3_1()) {
                initBarBelowLOLLIPOP();
                solveNavigation();
            } else {
                i = setStatusBarDarkFont(initBarAboveLOLLIPOP(256));
                supportActionBar();
            }
            this.mWindow.getDecorView().setSystemUiVisibility(hideBar(i));
        }
        if (OSUtils.isMIUI6Later()) {
            setMIUIStatusBarDarkFont(this.mWindow, this.mBarParams.darkFont);
        }
        if (!OSUtils.isFlymeOS4Later()) {
            return;
        }
        if (this.mBarParams.flymeOSStatusBarFontColor != 0) {
            FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(this.mActivity, this.mBarParams.flymeOSStatusBarFontColor);
        } else if (Build.VERSION.SDK_INT < 23) {
            FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(this.mActivity, this.mBarParams.darkFont);
        }
    }

    @RequiresApi(api = 21)
    private int initBarAboveLOLLIPOP(int i) {
        int i2 = i | 1024;
        if (this.mBarParams.fullScreen && this.mBarParams.navigationBarEnable) {
            i2 |= 512;
        }
        this.mWindow.clearFlags(67108864);
        if (this.mConfig.hasNavigtionBar()) {
            this.mWindow.clearFlags(134217728);
        }
        this.mWindow.addFlags(Integer.MIN_VALUE);
        if (this.mBarParams.statusBarFlag) {
            this.mWindow.setStatusBarColor(ColorUtils.blendARGB(this.mBarParams.statusBarColor, this.mBarParams.statusBarColorTransform, this.mBarParams.statusBarAlpha));
        } else {
            this.mWindow.setStatusBarColor(ColorUtils.blendARGB(this.mBarParams.statusBarColor, 0, this.mBarParams.statusBarAlpha));
        }
        if (this.mBarParams.navigationBarEnable) {
            this.mWindow.setNavigationBarColor(ColorUtils.blendARGB(this.mBarParams.navigationBarColor, this.mBarParams.navigationBarColorTransform, this.mBarParams.navigationBarAlpha));
        }
        return i2;
    }

    private void initBarBelowLOLLIPOP() {
        this.mWindow.addFlags(67108864);
        setupStatusBarView();
        if (this.mConfig.hasNavigtionBar()) {
            if (!this.mBarParams.navigationBarEnable || !this.mBarParams.navigationBarWithKitkatEnable) {
                this.mWindow.clearFlags(134217728);
            } else {
                this.mWindow.addFlags(134217728);
            }
            setupNavBarView();
        }
    }

    private void setupStatusBarView() {
        if (this.mBarParams.statusBarView == null) {
            this.mBarParams.statusBarView = new View(this.mActivity);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, this.mConfig.getStatusBarHeight());
        layoutParams.gravity = 48;
        this.mBarParams.statusBarView.setLayoutParams(layoutParams);
        if (this.mBarParams.statusBarFlag) {
            this.mBarParams.statusBarView.setBackgroundColor(ColorUtils.blendARGB(this.mBarParams.statusBarColor, this.mBarParams.statusBarColorTransform, this.mBarParams.statusBarAlpha));
        } else {
            this.mBarParams.statusBarView.setBackgroundColor(ColorUtils.blendARGB(this.mBarParams.statusBarColor, 0, this.mBarParams.statusBarAlpha));
        }
        this.mBarParams.statusBarView.setVisibility(0);
        ViewGroup viewGroup = (ViewGroup) this.mBarParams.statusBarView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mBarParams.statusBarView);
        }
        this.mDecorView.addView(this.mBarParams.statusBarView);
    }

    private void setupNavBarView() {
        FrameLayout.LayoutParams layoutParams;
        if (this.mBarParams.navigationBarView == null) {
            this.mBarParams.navigationBarView = new View(this.mActivity);
        }
        if (this.mConfig.isNavigationAtBottom()) {
            layoutParams = new FrameLayout.LayoutParams(-1, this.mConfig.getNavigationBarHeight());
            layoutParams.gravity = 80;
        } else {
            layoutParams = new FrameLayout.LayoutParams(this.mConfig.getNavigationBarWidth(), -1);
            layoutParams.gravity = GravityCompat.END;
        }
        this.mBarParams.navigationBarView.setLayoutParams(layoutParams);
        if (!this.mBarParams.navigationBarEnable || !this.mBarParams.navigationBarWithKitkatEnable) {
            this.mBarParams.navigationBarView.setBackgroundColor(0);
        } else if (this.mBarParams.fullScreen || this.mBarParams.navigationBarColorTransform != 0) {
            this.mBarParams.navigationBarView.setBackgroundColor(ColorUtils.blendARGB(this.mBarParams.navigationBarColor, this.mBarParams.navigationBarColorTransform, this.mBarParams.navigationBarAlpha));
        } else {
            this.mBarParams.navigationBarView.setBackgroundColor(ColorUtils.blendARGB(this.mBarParams.navigationBarColor, ViewCompat.MEASURED_STATE_MASK, this.mBarParams.navigationBarAlpha));
        }
        this.mBarParams.navigationBarView.setVisibility(0);
        ViewGroup viewGroup = (ViewGroup) this.mBarParams.navigationBarView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mBarParams.navigationBarView);
        }
        this.mDecorView.addView(this.mBarParams.navigationBarView);
    }

    private void solveNavigation() {
        int childCount = this.mContentView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mContentView.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                if (childAt instanceof DrawerLayout) {
                    View childAt2 = ((DrawerLayout) childAt).getChildAt(0);
                    if (childAt2 != null) {
                        this.mBarParams.systemWindows = childAt2.getFitsSystemWindows();
                        if (this.mBarParams.systemWindows) {
                            this.mContentView.setPadding(0, 0, 0, 0);
                            return;
                        }
                    } else {
                        continue;
                    }
                } else {
                    this.mBarParams.systemWindows = childAt.getFitsSystemWindows();
                    if (this.mBarParams.systemWindows) {
                        this.mContentView.setPadding(0, 0, 0, 0);
                        return;
                    }
                }
            }
        }
        if (!this.mConfig.hasNavigtionBar() || this.mBarParams.fullScreenTemp || this.mBarParams.fullScreen) {
            if (this.mBarParams.isSupportActionBar) {
                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, 0, 0);
            } else if (this.mBarParams.fits) {
                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, 0);
            } else {
                this.mContentView.setPadding(0, 0, 0, 0);
            }
        } else if (this.mConfig.isNavigationAtBottom()) {
            if (!this.mBarParams.isSupportActionBar) {
                if (!this.mBarParams.navigationBarEnable || !this.mBarParams.navigationBarWithKitkatEnable) {
                    if (this.mBarParams.fits) {
                        this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, 0);
                    } else {
                        this.mContentView.setPadding(0, 0, 0, 0);
                    }
                } else if (this.mBarParams.fits) {
                    this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, this.mConfig.getNavigationBarHeight());
                } else {
                    this.mContentView.setPadding(0, 0, 0, this.mConfig.getNavigationBarHeight());
                }
            } else if (!this.mBarParams.navigationBarEnable || !this.mBarParams.navigationBarWithKitkatEnable) {
                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, 0, 0);
            } else {
                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, 0, this.mConfig.getNavigationBarHeight());
            }
        } else if (!this.mBarParams.isSupportActionBar) {
            if (!this.mBarParams.navigationBarEnable || !this.mBarParams.navigationBarWithKitkatEnable) {
                if (this.mBarParams.fits) {
                    this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, 0);
                } else {
                    this.mContentView.setPadding(0, 0, 0, 0);
                }
            } else if (this.mBarParams.fits) {
                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), this.mConfig.getNavigationBarWidth(), 0);
            } else {
                this.mContentView.setPadding(0, 0, this.mConfig.getNavigationBarWidth(), 0);
            }
        } else if (!this.mBarParams.navigationBarEnable || !this.mBarParams.navigationBarWithKitkatEnable) {
            this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, 0, 0);
        } else {
            this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, this.mConfig.getNavigationBarWidth(), 0);
        }
    }

    private void registerEMUI3_x() {
        if ((OSUtils.isEMUI3_1() || OSUtils.isEMUI3_0()) && this.mConfig.hasNavigtionBar() && this.mBarParams.navigationBarEnable && this.mBarParams.navigationBarWithKitkatEnable) {
            if (this.mBarParams.navigationStatusObserver == null && this.mBarParams.navigationBarView != null) {
                this.mBarParams.navigationStatusObserver = new ContentObserver(new Handler()) {
                    public void onChange(boolean z) {
                        if (Settings.System.getInt(ImmersionBar.this.mActivity.getContentResolver(), ImmersionBar.NAVIGATIONBAR_IS_MIN, 0) == 1) {
                            ImmersionBar.this.mBarParams.navigationBarView.setVisibility(8);
                            ImmersionBar.this.mContentView.setPadding(0, ImmersionBar.this.mContentView.getPaddingTop(), 0, 0);
                            return;
                        }
                        ImmersionBar.this.mBarParams.navigationBarView.setVisibility(0);
                        if (ImmersionBar.this.mBarParams.systemWindows) {
                            ImmersionBar.this.mContentView.setPadding(0, ImmersionBar.this.mContentView.getPaddingTop(), 0, 0);
                        } else if (ImmersionBar.this.mConfig.isNavigationAtBottom()) {
                            ImmersionBar.this.mContentView.setPadding(0, ImmersionBar.this.mContentView.getPaddingTop(), 0, ImmersionBar.this.mConfig.getNavigationBarHeight());
                        } else {
                            ImmersionBar.this.mContentView.setPadding(0, ImmersionBar.this.mContentView.getPaddingTop(), ImmersionBar.this.mConfig.getNavigationBarWidth(), 0);
                        }
                    }
                };
            }
            this.mActivity.getContentResolver().registerContentObserver(Settings.System.getUriFor(NAVIGATIONBAR_IS_MIN), true, this.mBarParams.navigationStatusObserver);
        }
    }

    private void unRegisterEMUI3_x() {
        if ((OSUtils.isEMUI3_1() || OSUtils.isEMUI3_0()) && this.mConfig.hasNavigtionBar() && this.mBarParams.navigationBarEnable && this.mBarParams.navigationBarWithKitkatEnable && this.mBarParams.navigationStatusObserver != null && this.mBarParams.navigationBarView != null) {
            this.mActivity.getContentResolver().unregisterContentObserver(this.mBarParams.navigationStatusObserver);
        }
    }

    /* renamed from: com.gyf.barlibrary.ImmersionBar$4 */
    static /* synthetic */ class C11114 {
        static final /* synthetic */ int[] $SwitchMap$com$gyf$barlibrary$BarHide = new int[BarHide.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.gyf.barlibrary.BarHide[] r0 = com.gyf.barlibrary.BarHide.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$gyf$barlibrary$BarHide = r0
                int[] r0 = $SwitchMap$com$gyf$barlibrary$BarHide     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.gyf.barlibrary.BarHide r1 = com.gyf.barlibrary.BarHide.FLAG_HIDE_BAR     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$gyf$barlibrary$BarHide     // Catch:{ NoSuchFieldError -> 0x001f }
                com.gyf.barlibrary.BarHide r1 = com.gyf.barlibrary.BarHide.FLAG_HIDE_STATUS_BAR     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$gyf$barlibrary$BarHide     // Catch:{ NoSuchFieldError -> 0x002a }
                com.gyf.barlibrary.BarHide r1 = com.gyf.barlibrary.BarHide.FLAG_HIDE_NAVIGATION_BAR     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$gyf$barlibrary$BarHide     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.gyf.barlibrary.BarHide r1 = com.gyf.barlibrary.BarHide.FLAG_SHOW_BAR     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.gyf.barlibrary.ImmersionBar.C11114.<clinit>():void");
        }
    }

    private int hideBar(int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            int i2 = C11114.$SwitchMap$com$gyf$barlibrary$BarHide[this.mBarParams.barHide.ordinal()];
            if (i2 == 1) {
                i |= 518;
            } else if (i2 == 2) {
                i |= 1028;
            } else if (i2 == 3) {
                i |= 514;
            } else if (i2 == 4) {
                i |= 0;
            }
        }
        return i | 4096;
    }

    private int setStatusBarDarkFont(int i) {
        return (Build.VERSION.SDK_INT < 23 || !this.mBarParams.darkFont) ? i : i | 8192;
    }

    private void transformView() {
        if (this.mBarParams.viewMap.size() != 0) {
            for (Map.Entry next : this.mBarParams.viewMap.entrySet()) {
                View view = (View) next.getKey();
                Integer valueOf = Integer.valueOf(this.mBarParams.statusBarColor);
                Integer valueOf2 = Integer.valueOf(this.mBarParams.statusBarColorTransform);
                for (Map.Entry entry : ((Map) next.getValue()).entrySet()) {
                    Integer num = (Integer) entry.getKey();
                    valueOf2 = (Integer) entry.getValue();
                    valueOf = num;
                }
                if (view != null) {
                    if (Math.abs(this.mBarParams.viewAlpha - 0.0f) == 0.0f) {
                        view.setBackgroundColor(ColorUtils.blendARGB(valueOf.intValue(), valueOf2.intValue(), this.mBarParams.statusBarAlpha));
                    } else {
                        view.setBackgroundColor(ColorUtils.blendARGB(valueOf.intValue(), valueOf2.intValue(), this.mBarParams.viewAlpha));
                    }
                }
            }
        }
    }

    private void setStatusBarView() {
        if (Build.VERSION.SDK_INT >= 19 && this.mBarParams.statusBarViewByHeight != null) {
            ViewGroup.LayoutParams layoutParams = this.mBarParams.statusBarViewByHeight.getLayoutParams();
            layoutParams.height = this.mConfig.getStatusBarHeight();
            this.mBarParams.statusBarViewByHeight.setLayoutParams(layoutParams);
        }
    }

    private void setTitleBar() {
        if (Build.VERSION.SDK_INT >= 19 && this.mBarParams.titleBarView != null) {
            final ViewGroup.LayoutParams layoutParams = this.mBarParams.titleBarView.getLayoutParams();
            if (layoutParams.height == -2 || layoutParams.height == -1) {
                this.mBarParams.titleBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        ImmersionBar.this.mBarParams.titleBarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        if (ImmersionBar.this.mBarParams.titleBarHeight == 0) {
                            ImmersionBar.this.mBarParams.titleBarHeight = ImmersionBar.this.mBarParams.titleBarView.getHeight() + ImmersionBar.this.mConfig.getStatusBarHeight();
                        }
                        if (ImmersionBar.this.mBarParams.titleBarPaddingTopHeight == 0) {
                            ImmersionBar.this.mBarParams.titleBarPaddingTopHeight = ImmersionBar.this.mBarParams.titleBarView.getPaddingTop() + ImmersionBar.this.mConfig.getStatusBarHeight();
                        }
                        layoutParams.height = ImmersionBar.this.mBarParams.titleBarHeight;
                        ImmersionBar.this.mBarParams.titleBarView.setPadding(ImmersionBar.this.mBarParams.titleBarView.getPaddingLeft(), ImmersionBar.this.mBarParams.titleBarPaddingTopHeight, ImmersionBar.this.mBarParams.titleBarView.getPaddingRight(), ImmersionBar.this.mBarParams.titleBarView.getPaddingBottom());
                        ImmersionBar.this.mBarParams.titleBarView.setLayoutParams(layoutParams);
                    }
                });
                return;
            }
            if (this.mBarParams.titleBarHeight == 0) {
                this.mBarParams.titleBarHeight = layoutParams.height + this.mConfig.getStatusBarHeight();
            }
            if (this.mBarParams.titleBarPaddingTopHeight == 0) {
                BarParams barParams = this.mBarParams;
                barParams.titleBarPaddingTopHeight = barParams.titleBarView.getPaddingTop() + this.mConfig.getStatusBarHeight();
            }
            layoutParams.height = this.mBarParams.titleBarHeight;
            this.mBarParams.titleBarView.setPadding(this.mBarParams.titleBarView.getPaddingLeft(), this.mBarParams.titleBarPaddingTopHeight, this.mBarParams.titleBarView.getPaddingRight(), this.mBarParams.titleBarView.getPaddingBottom());
            this.mBarParams.titleBarView.setLayoutParams(layoutParams);
        }
    }

    private void setTitleBarMarginTop() {
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBarParams.titleBarViewMarginTop.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin + this.mConfig.getStatusBarHeight(), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            this.mBarParams.titleBarViewMarginTopFlag = true;
        }
    }

    private void supportActionBar() {
        if (Build.VERSION.SDK_INT >= 21 && !OSUtils.isEMUI3_1()) {
            int childCount = this.mContentView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.mContentView.getChildAt(i);
                if (childAt instanceof ViewGroup) {
                    this.mBarParams.systemWindows = childAt.getFitsSystemWindows();
                    if (this.mBarParams.systemWindows) {
                        this.mContentView.setPadding(0, 0, 0, 0);
                        return;
                    }
                }
            }
            if (this.mBarParams.isSupportActionBar) {
                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight(), 0, 0);
            } else if (this.mBarParams.fits) {
                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, 0);
            } else {
                this.mContentView.setPadding(0, 0, 0, 0);
            }
        }
    }

    private void keyboardEnable() {
        if (Build.VERSION.SDK_INT >= 19) {
            if (this.mBarParams.keyboardPatch == null) {
                this.mBarParams.keyboardPatch = KeyboardPatch.patch(this.mActivity, this.mWindow);
            }
            this.mBarParams.keyboardPatch.setBarParams(this.mBarParams);
            if (this.mBarParams.keyboardEnable) {
                this.mBarParams.keyboardPatch.enable(this.mBarParams.keyboardMode);
            } else {
                this.mBarParams.keyboardPatch.disable(this.mBarParams.keyboardMode);
            }
        }
    }

    private void setMIUIStatusBarDarkFont(Window window, boolean z) {
        if (window != null) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", new Class[]{Integer.TYPE, Integer.TYPE});
                if (z) {
                    method.invoke(window, new Object[]{Integer.valueOf(i), Integer.valueOf(i)});
                    return;
                }
                method.invoke(window, new Object[]{0, Integer.valueOf(i)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setTitleBar(final Activity activity, final View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams.height == -2) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        layoutParams.height = view.getHeight() + ImmersionBar.getStatusBarHeight(activity);
                        View view = view;
                        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + ImmersionBar.getStatusBarHeight(activity), view.getPaddingRight(), view.getPaddingBottom());
                    }
                });
                return;
            }
            layoutParams.height += getStatusBarHeight(activity);
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(activity), view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    public static void setStatusBarView(Activity activity, View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = getStatusBarHeight(activity);
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setTitleBarMarginTop(Activity activity, @NonNull View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin + getStatusBarHeight(activity), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        }
    }

    public static void setFitsSystemWindows(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(16908290);
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                childAt.setFitsSystemWindows(true);
                ((ViewGroup) childAt).setClipToPadding(true);
            }
        }
    }

    @TargetApi(14)
    public static boolean hasNavigationBar(Activity activity) {
        return new BarConfig(activity).hasNavigtionBar();
    }

    @TargetApi(14)
    public static int getNavigationBarHeight(Activity activity) {
        return new BarConfig(activity).getNavigationBarHeight();
    }

    @TargetApi(14)
    public static int getNavigationBarWidth(Activity activity) {
        return new BarConfig(activity).getNavigationBarWidth();
    }

    @TargetApi(14)
    public static boolean isNavigationAtBottom(Activity activity) {
        return new BarConfig(activity).isNavigationAtBottom();
    }

    @TargetApi(14)
    public static int getStatusBarHeight(Activity activity) {
        return new BarConfig(activity).getStatusBarHeight();
    }

    @TargetApi(14)
    public static int getActionBarHeight(Activity activity) {
        return new BarConfig(activity).getActionBarHeight();
    }

    public static boolean isSupportStatusBarDarkFont() {
        return OSUtils.isMIUI6Later() || OSUtils.isFlymeOS4Later() || Build.VERSION.SDK_INT >= 23;
    }

    public static void hideStatusBar(Window window) {
        window.setFlags(1024, 1024);
    }

    public BarParams getBarParams() {
        return this.mBarParams;
    }

    public BarParams getTagBarParams(String str) {
        if (isEmpty(str)) {
            return null;
        }
        Map<String, BarParams> map = mTagMap;
        return map.get(this.mActivityName + "_TAG_" + str);
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
