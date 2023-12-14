package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import anet.channel.strategy.dispatch.DispatchConstants;

public class NavigationBarUtil {
    private ViewGroup.LayoutParams layoutParams = this.mObserved.getLayoutParams();
    private View mObserved;
    private int usableHeightView;

    public static void initActivity(View view) {
        new NavigationBarUtil(view);
    }

    private NavigationBarUtil(View view) {
        this.mObserved = view;
        this.mObserved.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                NavigationBarUtil.this.resetViewHeight();
            }
        });
    }

    /* access modifiers changed from: private */
    public void resetViewHeight() {
        int CalculateAvailableHeight = CalculateAvailableHeight();
        if (CalculateAvailableHeight != this.usableHeightView) {
            this.layoutParams.height = CalculateAvailableHeight;
            this.mObserved.requestLayout();
            this.usableHeightView = CalculateAvailableHeight;
        }
    }

    private int CalculateAvailableHeight() {
        Rect rect = new Rect();
        this.mObserved.getWindowVisibleDisplayFrame(rect);
        return rect.bottom - rect.top;
    }

    public static boolean hasNavigationBar(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", DispatchConstants.ANDROID);
        boolean z = identifier > 0 ? resources.getBoolean(identifier) : false;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str = (String) cls.getMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{"qemu.hw.mainkeys"});
            if ("1".equals(str)) {
                return false;
            }
            if ("0".equals(str)) {
                return true;
            }
            return z;
        } catch (Exception unused) {
            return z;
        }
    }
}
