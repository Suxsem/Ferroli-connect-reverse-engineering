package android.support.design.widget;

import android.graphics.PorterDuff;

class ViewUtils {
    ViewUtils() {
    }

    static PorterDuff.Mode parseTintMode(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        if (i != 14) {
            return i != 15 ? mode : PorterDuff.Mode.SCREEN;
        }
        return PorterDuff.Mode.MULTIPLY;
    }
}
