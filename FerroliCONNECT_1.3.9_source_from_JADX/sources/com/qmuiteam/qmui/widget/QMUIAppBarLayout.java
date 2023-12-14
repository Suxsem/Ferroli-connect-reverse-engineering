package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import com.qmuiteam.qmui.util.QMUIWindowInsetHelper;
import java.lang.reflect.Field;

public class QMUIAppBarLayout extends AppBarLayout implements IWindowInsetLayout {
    public boolean applySystemWindowInsets21(WindowInsetsCompat windowInsetsCompat) {
        return true;
    }

    public QMUIAppBarLayout(Context context) {
        super(context);
    }

    public QMUIAppBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean applySystemWindowInsets19(final Rect rect) {
        if (!ViewCompat.getFitsSystemWindows(this)) {
            return false;
        }
        try {
            Field declaredField = AppBarLayout.class.getDeclaredField("mLastInsets");
            declaredField.setAccessible(true);
            declaredField.set(this, new WindowInsetsCompat((WindowInsetsCompat) null) {
                public int getSystemWindowInsetTop() {
                    return rect.top;
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (!QMUIWindowInsetHelper.jumpDispatch(childAt)) {
                if (!QMUIWindowInsetHelper.isHandleContainer(childAt)) {
                    childAt.setPadding(rect.left, rect.top, rect.right, rect.bottom);
                } else if (childAt instanceof IWindowInsetLayout) {
                    ((IWindowInsetLayout) childAt).applySystemWindowInsets19(rect);
                }
            }
        }
        return true;
    }
}
