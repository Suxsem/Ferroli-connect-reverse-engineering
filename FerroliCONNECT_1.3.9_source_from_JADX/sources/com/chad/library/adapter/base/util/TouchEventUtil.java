package com.chad.library.adapter.base.util;

public class TouchEventUtil {
    public static String getTouchAction(int i) {
        String str = "Unknow:id=" + i;
        if (i == 0) {
            return "ACTION_DOWN";
        }
        if (i == 1) {
            return "ACTION_UP";
        }
        if (i == 2) {
            return "ACTION_MOVE";
        }
        if (i != 3) {
            return i != 4 ? str : "ACTION_OUTSIDE";
        }
        return "ACTION_CANCEL";
    }
}
