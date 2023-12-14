package com.qmuiteam.qmui.util;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class QMUIKeyboardHelper {
    private static final int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;
    public static final int SHOW_KEYBOARD_DELAY_TIME = 200;
    private static final String TAG = "QMUIKeyboardHelper";

    public interface KeyboardVisibilityEventListener {
        void onVisibilityChanged(boolean z);
    }

    public static void showKeyboard(EditText editText, boolean z) {
        showKeyboard(editText, z ? 200 : 0);
    }

    public static void showKeyboard(final EditText editText, int i) {
        if (editText != null) {
            if (!editText.requestFocus()) {
                Log.w(TAG, "showSoftInput() can not get focus");
            } else if (i > 0) {
                editText.postDelayed(new Runnable() {
                    public void run() {
                        ((InputMethodManager) editText.getContext().getApplicationContext().getSystemService("input_method")).showSoftInput(editText, 1);
                    }
                }, (long) i);
            } else {
                ((InputMethodManager) editText.getContext().getApplicationContext().getSystemService("input_method")).showSoftInput(editText, 1);
            }
        }
    }

    public static boolean hideKeyboard(View view) {
        if (view == null) {
            return false;
        }
        return ((InputMethodManager) view.getContext().getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 2);
    }

    public static void setVisibilityEventListener(final Activity activity, final KeyboardVisibilityEventListener keyboardVisibilityEventListener) {
        if (activity == null) {
            throw new NullPointerException("Parameter:activity must not be null");
        } else if (keyboardVisibilityEventListener != null) {
            final View activityRoot = QMUIViewHelper.getActivityRoot(activity);
            final C16282 r1 = new ViewTreeObserver.OnGlobalLayoutListener() {

                /* renamed from: r */
                private final Rect f3104r = new Rect();
                private final int visibleThreshold = Math.round((float) QMUIDisplayHelper.dp2px(activity, 100));
                private boolean wasOpened = false;

                public void onGlobalLayout() {
                    activityRoot.getWindowVisibleDisplayFrame(this.f3104r);
                    boolean z = activityRoot.getRootView().getHeight() - this.f3104r.height() > this.visibleThreshold;
                    if (z != this.wasOpened) {
                        this.wasOpened = z;
                        keyboardVisibilityEventListener.onVisibilityChanged(z);
                    }
                }
            };
            activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(r1);
            activity.getApplication().registerActivityLifecycleCallbacks(new QMUIActivityLifecycleCallbacks(activity) {
                /* access modifiers changed from: protected */
                public void onTargetActivityDestroyed() {
                    if (Build.VERSION.SDK_INT >= 16) {
                        activityRoot.getViewTreeObserver().removeOnGlobalLayoutListener(r1);
                    } else {
                        activityRoot.getViewTreeObserver().removeGlobalOnLayoutListener(r1);
                    }
                }
            });
        } else {
            throw new NullPointerException("Parameter:listener must not be null");
        }
    }

    public static boolean isKeyboardVisible(Activity activity) {
        Rect rect = new Rect();
        View activityRoot = QMUIViewHelper.getActivityRoot(activity);
        int round = Math.round((float) QMUIDisplayHelper.dp2px(activity, 100));
        activityRoot.getWindowVisibleDisplayFrame(rect);
        return activityRoot.getRootView().getHeight() - rect.height() > round;
    }
}
