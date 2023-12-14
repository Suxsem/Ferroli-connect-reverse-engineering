package com.qmuiteam.qmui.link;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

public class QMUILinkTouchMovementMethod extends LinkMovementMethod {
    private static QMUILinkTouchDecorHelper sHelper = new QMUILinkTouchDecorHelper();
    private static QMUILinkTouchMovementMethod sInstance;

    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        return sHelper.onTouchEvent(textView, spannable, motionEvent) || Touch.onTouchEvent(textView, spannable, motionEvent);
    }

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new QMUILinkTouchMovementMethod();
        }
        return sInstance;
    }
}
