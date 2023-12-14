package com.qmuiteam.qmui.util;

import android.os.Build;
import android.view.WindowManager;

public class QMUIWindowHelper {
    public static void setWindowType(WindowManager.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT >= 19) {
            layoutParams.type = 2005;
        } else {
            layoutParams.type = 2002;
        }
    }
}
