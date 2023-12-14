package com.qmuiteam.qmui.link;

import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

public class QMUIScrollingMovementMethod extends ScrollingMovementMethod {
    private static QMUILinkTouchDecorHelper sHelper = new QMUILinkTouchDecorHelper();
    private static QMUIScrollingMovementMethod sInstance;

    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        return sHelper.onTouchEvent(textView, spannable, motionEvent) || Touch.onTouchEvent(textView, spannable, motionEvent);
    }

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new QMUIScrollingMovementMethod();
        }
        return sInstance;
    }
}
