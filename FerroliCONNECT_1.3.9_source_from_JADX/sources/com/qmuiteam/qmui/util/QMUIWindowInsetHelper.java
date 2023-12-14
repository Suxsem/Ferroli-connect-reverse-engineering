package com.qmuiteam.qmui.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.p000v4.view.OnApplyWindowInsetsListener;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.qmuiteam.qmui.widget.IWindowInsetLayout;
import java.lang.ref.WeakReference;

public class QMUIWindowInsetHelper {
    private final int KEYBOARD_HEIGHT_BOUNDARY;
    private final WeakReference<IWindowInsetLayout> mWindowInsetLayoutWR;

    public QMUIWindowInsetHelper(ViewGroup viewGroup, IWindowInsetLayout iWindowInsetLayout) {
        this.mWindowInsetLayoutWR = new WeakReference<>(iWindowInsetLayout);
        this.KEYBOARD_HEIGHT_BOUNDARY = QMUIDisplayHelper.dp2px(viewGroup.getContext(), 100);
        ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return QMUIWindowInsetHelper.this.setWindowInsets(windowInsetsCompat);
            }
        });
    }

    /* access modifiers changed from: private */
    public WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        return (Build.VERSION.SDK_INT < 21 || this.mWindowInsetLayoutWR.get() == null || !((IWindowInsetLayout) this.mWindowInsetLayoutWR.get()).applySystemWindowInsets21(windowInsetsCompat)) ? windowInsetsCompat : windowInsetsCompat.consumeSystemWindowInsets();
    }

    @TargetApi(19)
    public boolean defaultApplySystemWindowInsets19(ViewGroup viewGroup, Rect rect) {
        if (rect.bottom >= this.KEYBOARD_HEIGHT_BOUNDARY) {
            QMUIViewHelper.setPaddingBottom(viewGroup, rect.bottom);
            rect.bottom = 0;
        } else {
            QMUIViewHelper.setPaddingBottom(viewGroup, 0);
        }
        boolean z = false;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (!jumpDispatch(childAt)) {
                Rect rect2 = new Rect(rect);
                computeInsetsWithGravity(childAt, rect2);
                if (!isHandleContainer(childAt)) {
                    childAt.setPadding(rect2.left, rect2.top, rect2.right, rect2.bottom);
                } else if (childAt instanceof IWindowInsetLayout) {
                    ((IWindowInsetLayout) childAt).applySystemWindowInsets19(rect2);
                } else {
                    defaultApplySystemWindowInsets19((ViewGroup) childAt, rect2);
                }
                z = true;
            }
        }
        return z;
    }

    @TargetApi(21)
    public boolean defaultApplySystemWindowInsets21(ViewGroup viewGroup, WindowInsetsCompat windowInsetsCompat) {
        boolean z;
        int i;
        if (!windowInsetsCompat.hasSystemWindowInsets()) {
            return false;
        }
        if (windowInsetsCompat.getSystemWindowInsetBottom() >= this.KEYBOARD_HEIGHT_BOUNDARY) {
            QMUIViewHelper.setPaddingBottom(viewGroup, windowInsetsCompat.getSystemWindowInsetBottom());
            z = true;
        } else {
            QMUIViewHelper.setPaddingBottom(viewGroup, 0);
            z = false;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (!jumpDispatch(childAt)) {
                int systemWindowInsetLeft = windowInsetsCompat.getSystemWindowInsetLeft();
                int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                int systemWindowInsetRight = windowInsetsCompat.getSystemWindowInsetRight();
                if (z) {
                    i = 0;
                } else {
                    i = windowInsetsCompat.getSystemWindowInsetBottom();
                }
                Rect rect = new Rect(systemWindowInsetLeft, systemWindowInsetTop, systemWindowInsetRight, i);
                computeInsetsWithGravity(childAt, rect);
                if (ViewCompat.dispatchApplyWindowInsets(childAt, windowInsetsCompat.replaceSystemWindowInsets(rect)).isConsumed()) {
                    z2 = true;
                }
            }
        }
        return z2;
    }

    @TargetApi(19)
    public static boolean jumpDispatch(View view) {
        return !view.getFitsSystemWindows() && !isHandleContainer(view);
    }

    public static boolean isHandleContainer(View view) {
        return (view instanceof IWindowInsetLayout) || (view instanceof CoordinatorLayout);
    }

    @SuppressLint({"RtlHardcoded"})
    private void computeInsetsWithGravity(View view, Rect rect) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int i = layoutParams instanceof FrameLayout.LayoutParams ? ((FrameLayout.LayoutParams) layoutParams).gravity : -1;
        if (i == -1) {
            i = 51;
        }
        if (layoutParams.width != -1) {
            int i2 = i & 7;
            if (i2 == 3) {
                rect.right = 0;
            } else if (i2 == 5) {
                rect.left = 0;
            }
        }
        if (layoutParams.height != -1) {
            int i3 = i & 112;
            if (i3 == 48) {
                rect.bottom = 0;
            } else if (i3 == 80) {
                rect.top = 0;
            }
        }
    }
}
